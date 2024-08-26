package com.example.app08_menu.customMenu

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app08_menu.R
import com.example.app08_menu.databinding.FragmentOneBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentOne.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentOne : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_one, container, false)
        val binding = FragmentOneBinding.inflate(inflater, container, false)

        // 데이터 생성
        val datas = mutableListOf<String>()
        for (i in 1..9) {
            datas.add("Item ${i}")
        }
        // adapter 생성
        val adapter = MyAdapter(datas)
        // recyclerview adapter 연결
        binding.recyclerView.adapter = adapter
        // recyclerview layout 설정
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context))
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentOne.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentOne().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
// recyclerview 추가로 꾸미기 위한 역할 (필수 구성 요소 x)
class MyDecoration(val context:Context) : RecyclerView.ItemDecoration() {
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        // 전체 view size
        val width = parent.width
        val height = parent.height

        // img size
        val dr:Drawable? = ResourcesCompat.getDrawable(context.getResources(), R.drawable.awww, null)
        val drWidth = dr?.intrinsicWidth
        val drHeight = dr?.intrinsicHeight

        // 화면 중간에 위치 시킴
        val left = width/2 - drWidth?.div(2) as Int
        val top = height/2 - drHeight?.div(2) as Int
        c.drawBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.awww),
            left.toFloat(), top .toFloat(), null
        )
    }

    // 각각 항목을 꾸미기 위해서 호출
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val index = parent.getChildAdapterPosition(view) +1
//        3개씩 묶여 있는 듯한 효과
        if (index % 3 ==0)
//            outRect : 항목이 출력되어야 하는 사각형
            outRect.set(10,10,10,60)
        else
            outRect.set(10,10,10,0)
        view.setBackgroundColor(Color.parseColor("#ffffff"))
        ViewCompat.setElevation(view, 20.0f)
    }
}