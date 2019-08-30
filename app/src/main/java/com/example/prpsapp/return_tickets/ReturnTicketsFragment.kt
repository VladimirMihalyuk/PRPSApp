package com.example.prpsapp.return_tickets


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.prpsapp.App.Companion.prefs
import com.example.prpsapp.R
import com.example.prpsapp.database.CinemaDatabase
import com.example.prpsapp.databinding.FragmentReturnTicketsBinding
import com.google.android.material.snackbar.Snackbar

class ReturnTicketsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentReturnTicketsBinding = DataBindingUtil.inflate(inflater,
           R.layout.fragment_return_tickets, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = CinemaDatabase.getInstance(application).databaseDao

        val viewModelFactory = ReturnTicketsViewModelFactory(dataSource,prefs?.emailClient.toString() ,application)

        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ReturnTicketsViewModel::class.java)

        binding.viewModel = viewModel

        if(prefs?.emailClient == null){
            Snackbar.make(activity!!.findViewById(android.R.id.content),
                getString(R.string.please_sign_in), Snackbar.LENGTH_SHORT).show()
        }

        viewModel.deleteId.observe(this, Observer { id ->
            val builder = AlertDialog.Builder(this.activity)
            builder.setTitle(getString(R.string.returning_tickets))
            builder.setMessage(getString(R.string.want_to_return_this_tickets))

            builder.setNeutralButton(getString(R.string.yes)){ _, which ->
                viewModel.deleteTicketsFromDB(id ?: 0, prefs?.emailClient.toString())
            }
            builder.setNegativeButton(getString(R.string.no)){ _, which -> }

            val dialog: AlertDialog = builder.create()

            dialog.show()
        })


        binding.lifecycleOwner = this

        val adapter = ReturnTicketsAdapter(ReturnTicketsClickListener { id ->
            viewModel.returnTickets(id)
        })

        binding.tickets.adapter = adapter

        viewModel.tickets.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })
        return binding.root
    }
}
