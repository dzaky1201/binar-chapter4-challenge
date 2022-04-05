package com.dzakyhdr.room.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.dzakyhdr.room.data.StudentDatabase
import com.dzakyhdr.room.data.model.Student
import com.dzakyhdr.room.databinding.FragmentAddBinding
import com.dzakyhdr.room.runOnUiThread
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddFragment : DialogFragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private var mDb: StudentDatabase? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = StudentDatabase.getInstance(view.context)
        binding.apply {
            btnSave.setOnClickListener {
                val student = Student(
                    null,
                    judul = edtJudul.text.toString(),
                    catatan = edtCatatan.text.toString()
                )

                GlobalScope.launch {
                    val result = mDb?.studentDao()?.insertStudent(student)
                    runOnUiThread {
                        if (result != 0.toLong()) {
                            Toast.makeText(
                                view.context,
                                "Berhasil Disave",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Snackbar.make(
                                binding.root,
                                "Terjadi Kesalahan",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }


                }

                dialog?.dismiss()
            }

            btnBatal.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        StudentDatabase.destroyInstance()
    }
}