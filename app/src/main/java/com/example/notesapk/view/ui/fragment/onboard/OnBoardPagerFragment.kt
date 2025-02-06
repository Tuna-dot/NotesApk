package com.example.notesapk.view.ui.fragment.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notesapk.R
import com.example.notesapk.databinding.FragmentOnBoardPagerBinding


class OnBoardPagerFragment : Fragment() {
    private var binding: FragmentOnBoardPagerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardPagerBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialze()
        getTextViewBottomPosition()
    }

    private fun initialze() = with(binding!!) {
        when(requireArguments().getInt(ARG_POSITION)){
            0 -> {
                lottieAnimation.setAnimation(R.raw.animation)
                txtOnBoardTitle.text = "Удобство"
                txtOnBoardDescription.text = "Создавайте заметки в два клика! Записывайте мысли, идеи и важные задачи мгновенно."
            }
            1 -> {
                lottieAnimation.setAnimation(R.raw.animayion_2)
                txtOnBoardTitle.text = "Организация"
                txtOnBoardDescription.text = "Организуйте заметки по папкам и тегам. Легко находите нужную информацию в любое время."
            }
            2 -> {
                lottieAnimation.setAnimation(R.raw.animation_3)
                txtOnBoardTitle.text = "Синхронизация"
                txtOnBoardDescription.text = "Синхронизация на всех устройствах. Доступ к записям в любое время и в любом месте."
            }
        }
    }

    fun getTextViewBottomPosition(): Int {
        binding?.let { bind ->
            val location = IntArray(2)
            bind.txtOnBoardDescription.getLocationOnScreen(location)
            return location[1] + bind.txtOnBoardDescription.height // нижняя граница
        }
        return 0
    }

    companion object{
        const val ARG_POSITION = "ARG_POSITION"
    }
}