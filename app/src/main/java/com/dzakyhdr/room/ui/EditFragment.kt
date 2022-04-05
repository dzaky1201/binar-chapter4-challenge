package com.dzakyhdr.room.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.dzakyhdr.room.data.StudentDatabase
import com.dzakyhdr.room.data.model.Student
import com.dzakyhdr.room.databinding.FragmentEditBinding
import com.dzakyhdr.room.runOnUiThread
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditFragment : DialogFragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private var mDb: StudentDatabase? = null

//    private val args: EditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val objectStudent = arguments?.getParcelable<Student>("student")

        mDb = StudentDatabase.getInstance(view.context)
        binding.apply {
            edtJudul.setText(objectStudent?.judul)
            edtCatatan.setText(objectStudent?.catatan)
            btnSave.setOnClickListener {

                objectStudent?.judul = edtJudul.text.toString()
                objectStudent?.catatan = edtCatatan.text.toString()

                GlobalScope.launch {
                    val result = mDb?.studentDao()?.updateStudent(objectStudent!!)
                    runOnUiThread {
                        if (result != 0) {
                            Toast.makeText(
                                view.context,
                                "Berhasil Diupdate",
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
                    dialog?.dismiss()

                }
            }

            btnBatal.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        StudentDatabase.destroyInstance()
    }
}