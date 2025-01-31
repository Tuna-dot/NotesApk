package com.example.notesapk.ui.fragment.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.notesapk.R
import com.example.notesapk.databinding.FragmentOnBoardBinding
import com.example.notesapk.ui.adapters.OnBoardAdapter
import com.example.notesapk.utils.PreferenceHelper


class OnBoardFragment : Fragment() {
    private var binding: FragmentOnBoardBinding? = null
    private val sharedPreferences = PreferenceHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences.unit(requireContext())
        initialze()
        setupListeners()
        setupDotsIndicator()
    }

    private fun initialze() {
        binding?.viewPager?.adapter = OnBoardAdapter(this)
    }

    private fun setupListeners() = with(binding?.viewPager){

        this?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2){
                    binding?.btnStart?.visibility = View.VISIBLE
                    binding?.txtSkip?.visibility = View.INVISIBLE
                    binding?.btnStart?.setOnClickListener {
                        sharedPreferences.onBoard = true
                        findNavController().navigate(OnBoardFragmentDirections.actionOnBoardFragmentToAuthFragment())

                    }
                }else{
                    binding?.btnStart?.visibility = View.INVISIBLE
                    binding?.txtSkip?.visibility = View.VISIBLE
                    binding?.txtSkip?.setOnClickListener {
                        setCurrentItem(currentItem + 2, true)
                    }
                }
            }
        })
    }
    private fun setupDotsIndicator() {
        binding?.viewPager?.let { viewPager ->
            binding?.dotsIndicator?.attachTo(viewPager)
         }
    }
}