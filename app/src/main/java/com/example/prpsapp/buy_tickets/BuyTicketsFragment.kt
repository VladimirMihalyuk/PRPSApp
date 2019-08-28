package com.example.prpsapp.buy_tickets


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.prpsapp.R
import com.example.prpsapp.database.CinemaDatabase
import com.example.prpsapp.databinding.FragmentBuyTicketsBinding
import com.google.android.material.snackbar.Snackbar

val message = "There are no sessions on this day"

class BuyTicketsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentBuyTicketsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_buy_tickets, container, false)

        val id = BuyTicketsFragmentArgs.fromBundle(arguments!!).idSession

        val description = BuyTicketsFragmentArgs.fromBundle(arguments!!).description

        val filmName = BuyTicketsFragmentArgs.fromBundle(arguments!!).filmName

        val image = BuyTicketsFragmentArgs.fromBundle(arguments!!).image

        val ticketsLeft = BuyTicketsFragmentArgs.fromBundle(arguments!!).ticketsLeft

        val application = requireNotNull(this.activity).application

        val dataSource = CinemaDatabase.getInstance(application).databaseDao

        val viewModelFactory = BuyTicketsViewModelFactory(dataSource, id, description, ticketsLeft, image, filmName,application)


        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(BuyTicketsViewModel::class.java)


        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        viewModel.buyResultCode.observe(this, Observer { code ->
            var msg = ""
            when(code){
                0 -> msg = "You have bought ${viewModel.selectedTickets} tickets"
                1 -> msg = message
                2 -> msg = "Please sign in"
                3 -> msg = "You can't buy more than 5 tickets per session. Now you have ${viewModel.alreadyBoughtTickets} "
            }
            Snackbar.make(
                activity!!.findViewById(android.R.id.content),
                msg,
                Snackbar.LENGTH_SHORT
            ).show()
            viewModel.endThereIsNoSuchEvent()

        })

        viewModel.thereIsNoSuchEvent.observe(this, Observer { flag ->
            if(flag){
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    message,
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.endThereIsNoSuchEvent()
            }
        })

        return binding.root
    }


}
