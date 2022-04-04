package com.dzakyhdr.room.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzakyhdr.room.R
import com.dzakyhdr.room.StudentAdapter
import com.dzakyhdr.room.data.StudentDatabase
import com.dzakyhdr.room.databinding.FragmentHomeBinding
import com.dzakyhdr.room.runOnUiThread
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var mDb: StudentDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = StudentDatabase.getInstance(view.context)
        fetchData()
        binding.apply {
            fabAdd.setOnClickListener {
                val add = AddFragment()
                add.show(childFragmentManager, "add")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData() {
        GlobalScope.launch {
            val data = mDb?.studentDao()?.getAllStudent()

            binding.recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            runOnUiThread {
                data?.let {
                    val adapter = StudentAdapter(it)
                    binding.recyclerView.adapter = adapter
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        StudentDatabase.destroyInstance()
    }


}