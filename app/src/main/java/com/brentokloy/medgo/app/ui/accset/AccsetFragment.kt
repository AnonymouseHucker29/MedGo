package com.brentokloy.medgo.app.ui.accset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.brentokloy.medgo.app.databinding.FragmentAccsetBinding

class AccsetFragment : Fragment() {

    private lateinit var accsetViewModel: AccsetViewModel
    private var _binding: FragmentAccsetBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accsetViewModel =
            ViewModelProvider(this)[AccsetViewModel::class.java]

        _binding = FragmentAccsetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        accsetViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}