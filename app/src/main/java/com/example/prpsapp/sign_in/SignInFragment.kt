package com.example.prpsapp.sign_in


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
import com.example.prpsapp.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.Snackbar


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
                binding.emailLayout.error = "E-mail can't be empty"
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

        viewModel.problemSignIn.observe(this, Observer {code ->
            var msg: String = ""
            when(code){
                0 -> msg = "You have successfully signed in"
                1 -> msg = "Invalid field values"
                2 -> msg = "There is no client with this email and password"
            }
            Snackbar.make(
                activity!!.findViewById(android.R.id.content), msg,
                Snackbar.LENGTH_SHORT
            ).show()

        })

        binding.lifecycleOwner = this
        return binding.root
    }


}
