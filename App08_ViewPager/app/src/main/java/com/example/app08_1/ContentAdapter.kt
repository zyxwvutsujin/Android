
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.app08_1.Tab1Fragment
import com.example.app08_1.Tab2Fragment
import com.example.app08_1.Tab3Fragment
import com.example.app08_1.Tab4Fragment
import com.example.app08_1.Tab5Fragment

class ContentAdapter(val fragmentActivity: FragmentActivity)
    :FragmentStateAdapter(fragmentActivity) {
    var fragments = listOf<Fragment>(Tab1Fragment(), Tab2Fragment(), Tab3Fragment(), Tab4Fragment(), Tab5Fragment())
    override fun getItemCount(): Int {
        return  fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}