package com.example.prpsapp.registration


import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.prpsapp.R
import com.example.prpsapp.database.CinemaDatabase
import com.example.prpsapp.databinding.FragmentPosterBinding
import com.example.prpsapp.databinding.FragmentRegistrationBinding
import com.example.prpsapp.poster.PosterViewModel
import com.example.prpsapp.poster.PosterViewModelFactory
import com.google.android.material.snackbar.Snackbar


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RegistrationFragmnet : Fragment() {

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

        binding.setLifecycleOwner(this)

        viewModel.correctFullName.observe(this, Observer { correct ->
            if(!correct){
                binding.fullNameLayout.error = "Full name can't be empty"
            } else {
                binding.fullNameLayout.isErrorEnabled = false
            }
        })


        viewModel.correctPhoneNumber.observe(this, Observer { correct ->
            if(!correct){
                binding.phoneNumberLayout.error = "Example:+375 ** ***-**-**"
            } else {
                binding.phoneNumberLayout.isErrorEnabled = false
            }
        })

        viewModel.correctEmail.observe(this, Observer { correct ->
            if(!correct){
                binding.emailLayout.error = "Wrong email"
            } else {
                binding.emailLayout.isErrorEnabled = false
            }
        })

        viewModel.correctPassword.observe(this, Observer { correct ->
            if(!correct){
                binding.passwordLayout.error = "At least 8 characters"
            } else {
                binding.passwordLayout.isErrorEnabled = false
            }
        })

        viewModel.correctConfirmPassword.observe(this, Observer { correct ->
            if(!correct){
                binding.confirmPasswordLayout.error = "Must match the password"
            } else {
                binding.confirmPasswordLayout.isErrorEnabled = false
            }
        })

        viewModel.problemCreateCode.observe(this, Observer { code ->
            var msg: String = ""
            when(code){
                0 -> msg = "You have successfully registered"
                1 -> msg = "Invalid field values"
                2 -> msg = "Client with this email is already registered"
            }
            Snackbar.make(
                activity!!.findViewById(android.R.id.content), msg,
                Snackbar.LENGTH_SHORT
            ).show()
        })

        return binding.root
    }


}
