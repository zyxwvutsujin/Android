package com.example.app10_member

import android.content.DialogInterface
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.app10_member.databinding.CustomMemberBinding
import com.example.app10_member.databinding.ItemMemberBinding
import retrofit2.Call
import retrofit2.Response

class MemberAdapter(var memberList:MutableList<Member>)
    :RecyclerView.Adapter<MemberAdapter.Holder>() {
    class Holder(val binding: ItemMemberBinding) : RecyclerView.ViewHolder(binding.root) {
    }
    // 추가
    fun addItem(member: Member){
        memberList.add(member)
        notifyDataSetChanged()
    }

    // 수정
    fun updateItem(pos: Int, member:Member){
        memberList.set(pos, member)
        notifyDataSetChanged()
    }

    // 삭제
    fun removeItem(pos:Int) {
        memberList.removeAt(pos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val member = memberList.get(position)
        holder.binding.txtId.text = member.id.toString()
        holder.binding.txtName.text = member.name
        holder.binding.txtPhone.text = member.phone
        holder.binding.txtEmail.text = member.email

//        수정 클릭
        holder.itemView.setOnClickListener {
            val dialogMember = CustomMemberBinding.inflate(LayoutInflater.from(it.context))
            AlertDialog.Builder(it.context).run {
                setTitle("수정")
                setMessage("수정 하실 내용을 입력하세요.")
                setView(dialogMember.root)
                // 사용자가 클릭한 이름, 번호, 이메일 수정 세팅
                dialogMember.edtName.setText(holder.binding.txtName.text)
                dialogMember.edtPhone.setText(holder.binding.txtPhone.text)
                dialogMember.edtEmail.setText(holder.binding.txtEmail.text)
                // 버튼 클릭 이벤트
                setPositiveButton("수정", object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        val m = Member(member.id, dialogMember.edtName.text.toString(), dialogMember.edtPhone.text.toString(), dialogMember.edtEmail.text.toString())
                        MemberClient.retrofit.update(member.id, m).enqueue(object :retrofit2.Callback<Member>{
                            override fun onResponse(
                                call: Call<Member>,
                                response: Response<Member>
                            ) {
//                                수정 성공하면 데이터 저장
                                updateItem(holder.adapterPosition, m)
                            }

                            override fun onFailure(call: Call<Member>, t: Throwable) {
//                                걍실패 로그 알려주는거
                                Log.d("업데이트 실패", t.toString())
                            }

                        })
                    }

                })
                setNegativeButton("닫기",null)
                show()
            }
        }

//        삭제 (길게 클릭하면)
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(it.context).run {
                setTitle("삭제하시겠습니까?")
                setPositiveButton("삭제", object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        MemberClient.retrofit.deleteById(member.id).enqueue(object : retrofit2.Callback<Void>{
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                               removeItem(holder.adapterPosition)
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Log.d("삭제 실패 : ", t.toString())
                            }

                        })
                    }

                })
                setNegativeButton("닫기", null)
                show()
            }
            false
        }
    }


}
