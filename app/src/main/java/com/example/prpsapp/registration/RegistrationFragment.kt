package com.example.prpsapp.registration


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.prpsapp.R
import com.example.prpsapp.database.CinemaDatabase
import com.example.prpsapp.databinding.FragmentRegistrationBinding
import com.google.android.material.snackbar.Snackbar


class RegistrationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentRegistrationBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_registration, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = CinemaDatabase.getInstance(application).databaseDao

        val viewModelFactory = RegistrationViewModelFactory(dataSource, application)

        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(RegistrationViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.correctFullName.observe(this, Observer { correct ->
            if(!correct){
                binding.fullNameLayout.error = getString(R.string.fn_cant_be_empty)
            } else {
                binding.fullNameLayout.isErrorEnabled = false
            }
        })

        viewModel.correctPhoneNumber.observe(this, Observer { correct ->
            if(!correct){
                binding.phoneNumberLayout.error = getString(R.string.example)
            } else {
                binding.phoneNumberLayout.isErrorEnabled = false
            }
        })

        viewModel.correctEmail.observe(this, Observer { correct ->
            if(!correct){
                binding.emailLayout.error = getString(R.string.wrong_email)
            } else {
                binding.emailLayout.isErrorEnabled = false
            }
        })

        viewModel.correctPassword.observe(this, Observer { correct ->
            if(!correct) {
                binding.passwordLayout.error = getString(R.string.at_least_8)
            } else {
                binding.passwordLayout.isErrorEnabled = false
            }
        })

        viewModel.correctConfirmPassword.observe(this, Observer { correct ->
            if(!correct){
                binding.confirmPasswordLayout.error = getString(R.string.must_match_the_pass)
            } else {
                binding.confirmPasswordLayout.isErrorEnabled = false
            }
        })

        viewModel.problemCreateCode.observe(this, Observer { code ->
            var msg = ""
            when(code){
                0 -> msg = getString(R.string.you_have_successfully)
                1 -> msg = getString(R.string.invalid_field_values)
                2 -> msg = getString(R.string.client_with_this_email_is)
            }
            Snackbar.make(
                activity!!.findViewById(android.R.id.content), msg,
                Snackbar.LENGTH_SHORT
            ).show()
        })

        return binding.root
    }


}
