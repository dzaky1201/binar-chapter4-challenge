package com.dzakyhdr.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dzakyhdr.room.data.StudentDatabase
import com.dzakyhdr.room.data.model.Student
import com.dzakyhdr.room.databinding.StudentItemBinding
import com.dzakyhdr.room.ui.HomeFragment
import com.dzakyhdr.room.ui.HomeFragmentDirections
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class StudentAdapter : ListAdapter<Student, StudentAdapter.ViewHolder>(StudentDiffCallback()) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = StudentItemBinding.bind(itemView)

        fun bind(data: Student) {
            binding.tvID.text = data.id.toString()
            binding.tvNama.text = data.judul
            binding.tvEmail.text = data.catatan
            binding.ivEdit.setOnClickListener {
                val sendData = HomeFragmentDirections.actionHomeFragmentToEditFragment(data)
                it.findNavController().navigate(sendData)
            }

            binding.ivDelete.setOnClickListener {
                AlertDialog.Builder(it.context)
                    .setMessage("Apakah Anda Yakin Ingin Menghapus Catatan ini ?")
                    .setCancelable(false)
                    .setPositiveButton("Ya") { _, _ ->
                        val db = StudentDatabase.getInstance(itemView.context)

                        val executor = Executors.newCachedThreadPool()

                        executor.execute {
                            val result = db?.studentDao()?.deleteStudent(data)
                            (itemView.context as MainActivity).runOnUiThread {
                                if (result != 0) {
                                    Toast.makeText(
                                        itemView.context,
                                        "Data Berhasil Di delete",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        itemView.context,
                                        "Data Gagal Di delete",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }

                    }
                    .setNegativeButton("Tidak") { dialog, _ ->
                        dialog?.dismiss()
                    }
                    .show()

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

}

class StudentDiffCallback : DiffUtil.ItemCallback<Student>() {
    override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem == newItem
    }


}