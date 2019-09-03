package com.example.prpsapp.poster


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
import com.example.prpsapp.databinding.FragmentPosterBinding



class PosterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding: FragmentPosterBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_poster, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = CinemaDatabase.getInstance(application).databaseDao

        val viewModelFactory = PosterViewModelFactory(dataSource, application)

        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PosterViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.navigateToBuyTicket.observe(this, Observer { idSession ->
            idSession?.let {
                this.findNavController().navigate(
                    PosterFragmentDirections.actionPosterFragmentToBuyTicketsFragment(idSession, viewModel.description, viewModel.ticketsLeft,
                        viewModel.image, viewModel.name))
                    viewModel.doneNavigating()
            }
        })

        val adapter = PosterAdapter(SessionClickListener { sessionId, description, ticketsLeft, image, name ->
            viewModel.onSessionClicked(sessionId, description, ticketsLeft, image, name)
        })

        binding.films.adapter = adapter

        viewModel.sessions.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })
        return binding.root
    }
}
