package com.dzakyhdr.room.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzakyhdr.room.R
import com.dzakyhdr.room.SharedPreference
import com.dzakyhdr.room.StudentAdapter
import com.dzakyhdr.room.data.StudentDatabase
import com.dzakyhdr.room.data.model.Student
import com.dzakyhdr.room.databinding.FragmentHomeBinding
import com.dzakyhdr.room.runOnUiThread
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var mDb: StudentDatabase? = null
    private var sharedPref: SharedPreference? = null
    private var data: List<Student>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreference(view.context)
        mDb = StudentDatabase.getInstance(view.context)
        binding.homeToolbar.inflateMenu(R.menu.home_menu)
        binding.homeToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.refresh -> {
                    fetchData()
                    Snackbar.make(
                        binding.root,
                        "Data Terbaru Berhasil  Didapatkan",
                        Snackbar.LENGTH_LONG
                    ).show()
                    true
                }

                R.id.logout -> {
                    sharedPref!!.clearUsername()
                    findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                    true
                }
                else -> false
            }
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fetchData()
        binding.apply {
            fabAdd.setOnClickListener {
                val add = AddFragment()
                add.show(childFragmentManager, "add")
            }
            txtUsername.text = getString(R.string.hello_user, sharedPref?.getPrefKey("username"))
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData() {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
           data = mDb?.studentDao()?.getAllStudent()

            runOnUiThread {
                val adapter = StudentAdapter()
                adapter.submitList(data)
                binding.recyclerView.adapter = adapter
            }
        }



    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        StudentDatabase.destroyInstance()
    }


}