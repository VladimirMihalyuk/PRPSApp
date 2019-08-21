package com.example.prpsapp.buy_tickets


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.prpsapp.R
import com.example.prpsapp.database.CinemaDatabase
import com.example.prpsapp.databinding.FragmentBuyTicketsBinding

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

        Log.d("WTF", "$id $image $description $filmName $ticketsLeft")

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(BuyTicketsViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        return binding.root
    }


}
