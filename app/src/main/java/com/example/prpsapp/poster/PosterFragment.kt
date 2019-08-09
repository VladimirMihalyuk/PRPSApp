package com.example.prpsapp.poster


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.prpsapp.R
import com.example.prpsapp.databinding.FragmentPosterBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PosterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPosterBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_poster, container, false)

        binding.button.setOnClickListener{
            //safe args huli need to add smth to gradle
           findNavController().navigate(PosterFragmentDirections.actionPosterFragmentToBuyTicketsFragment())
        }

        return binding.root
    }


}
