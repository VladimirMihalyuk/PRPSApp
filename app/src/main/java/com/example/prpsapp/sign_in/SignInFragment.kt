package com.example.prpsapp.sign_in


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.prpsapp.R
import com.example.prpsapp.database.CinemaDatabase
import com.example.prpsapp.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.Snackbar




class SignInFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentSignInBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_sign_in, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = CinemaDatabase.getInstance(application).databaseDao

        val viewModelFactory = SignInViewModelFactory(dataSource, application)

        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SignInViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.correctEmail.observe(this, Observer { correct ->
            if(!correct){
                binding.emailLayout.error = getString(R.string.email_cant_be)
            } else {
                binding.emailLayout.isErrorEnabled = false
            }
        })


        viewModel.correctPassword.observe(this, Observer { correct ->
            if(!correct){
                binding.passwordLayout.error = getString(R.string.at_least_8)
            } else {
                binding.passwordLayout.isErrorEnabled = false
            }
        })

        viewModel.problemSignIn.observe(this, Observer {code ->
            var msg: String = ""
            when(code){
                0 -> msg = getString(R.string.successfully_signed_in)
                1 -> msg = getString(R.string.invalid_string_values)
                2 -> msg = getString(R.string.wrong_email_or_pass)
                3 -> msg = getString(R.string.successfully_signed_out)
            }
            Snackbar.make(
                activity!!.findViewById(android.R.id.content), msg,
                Snackbar.LENGTH_SHORT
            ).show()
        })

        viewModel.eventSignUp.observe(this, Observer {flag ->
            if(flag){
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToRegistrationFragmnet())
                viewModel.endSignUpEvent()
            }

        })

        binding.lifecycleOwner = this
        return binding.root
    }
}
