package com.dzakyhdr.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dzakyhdr.room.data.model.Student
import com.dzakyhdr.room.databinding.StudentItemBinding
import com.dzakyhdr.room.ui.HomeFragmentDirections

class StudentAdapter(
    private val studenList: List<Student>
    ) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = StudentItemBinding.bind(itemView)

        fun bind(data: Student) {
            binding.tvID.text = data.id.toString()
            binding.tvNama.text = data.name
            binding.tvEmail.text = data.email
            binding.ivEdit.setOnClickListener {
                val sendData = HomeFragmentDirections.actionHomeFragmentToEditFragment(data)
                it.findNavController().navigate(sendData)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = studenList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = studenList.size
}