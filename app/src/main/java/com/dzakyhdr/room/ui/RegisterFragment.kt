package com.dzakyhdr.room.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dzakyhdr.room.R
import com.dzakyhdr.room.SharedPreference
import com.dzakyhdr.room.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private var sharedPref: SharedPreference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreference(view.context)
        binding.btnRegister.setOnClickListener {

            val username = binding.edtUsername.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (binding.edtEmail.text.isNullOrBlank() || binding.edtUsername.text.isNullOrBlank() || binding.edtPassword.text.isNullOrBlank()){
                Snackbar.make(binding.root, "Lengkapi Field diatas", Snackbar.LENGTH_LONG).show()
            } else {
                sharedPref?.saveKey(username, email, password)
                Snackbar.make(binding.root, "Akun Berhasil Dibuat", Snackbar.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }


}