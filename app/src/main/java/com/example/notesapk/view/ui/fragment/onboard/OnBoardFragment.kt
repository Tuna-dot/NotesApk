package com.example.notesapk.view.ui.fragment.onboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.notesapk.MainActivity
import com.example.notesapk.R
import com.example.notesapk.databinding.FragmentOnBoardBinding
import com.example.notesapk.view.ui.adapters.OnBoardAdapter
import com.example.notesapk.model.data.utils.PreferenceHelper


class OnBoardFragment : Fragment() {
    private var binding: FragmentOnBoardBinding? = null
    private val sharedPreferences = PreferenceHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding?.root ?: throw IllegalStateException("Binding is null")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("ololo", "onViewCreated: " )
        sharedPreferences.unit(requireContext())
        binding?.let {
            initialze()
            setupListeners()
            setupDotsIndicator()
            binding?.btnStart?.visibility = View.INVISIBLE
        }
    }

    private fun initialze() {
       val adapter = OnBoardAdapter(this)
        binding?.viewPager?.adapter = adapter
        binding?.dotsIndicator?.attachTo(binding?.viewPager!!)

        binding?.viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val fragment = childFragmentManager.findFragmentByTag("f$position") as? OnBoardPagerFragment
                fragment?.view?.post {
                    val bottomY = fragment.getTextViewBottomPosition()

                    binding?.dotsIndicator?.let {dots ->
                        val params = dots.layoutParams as ViewGroup.MarginLayoutParams
                        params.topMargin = bottomY + 10
                        params.marginStart = (binding?.root?.width ?: 0) / 2 - dots.width / 2

                        dots.layoutParams = params
                    }

                }
            }
        })
    }

    private fun setupListeners() = with(binding?.viewPager){
        this?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2){
                    Log.e("ololo", "onPageSelected: " )
                    binding?.btnStart?.visibility = View.VISIBLE
                    binding?.txtSkip?.visibility = View.INVISIBLE
                    binding?.btnStart?.let {
                        if (binding?.btnStart?.visibility == View.VISIBLE) {
                            Log.e("ololo", "binding btnStart виден " )
                         binding?.btnStart?.setOnClickListener {
                             Log.e("ololo", "setonclick" )
                        sharedPreferences.onBoard = true
                        findNavController().navigate(R.id.action_onBoardFragment_to_authFragment)
                            }
                        }
                        Log.e("ololo", "не нал" )
                    }
                }else{
                   val animatein= AnimationUtils.loadAnimation(requireContext(), R.anim.btn_anim)
                    binding?.btnStart?.visibility = View.INVISIBLE
                    if (binding?.btnStart?.visibility != View.INVISIBLE){
                        binding?.btnStart?.startAnimation(animatein)
                    }
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        Log.e("ololo", "onDestroyView: ${binding}" )
    }
}