package com.example.notesapk.ui.fragment.note

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.notesapk.App
import com.example.notesapk.R
import com.example.notesapk.data.models.NoteModel
import com.example.notesapk.databinding.FragmentNoteDetailBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class NoteDetailFragment : Fragment() {

    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!
    private var noteId: Int = -1
    private var selectedColor: Int = Color.BLACK
    private var date: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateNote()
        setupTime()
        setupListener()
        setupTextWatcher()
    }

    private fun updateNote() {
        arguments?.let { args->
            noteId = args.getInt("noteId", -1)
        }
        if (noteId != -1){
            val id = App.appDataBase?.noteDao()?.getById(noteId)
            id?.let {noteModel->
                _binding?.title?.setText(noteModel.title)
                _binding?.text?.setText(noteModel.description)
            }
        }
    }

    private fun setupTime() {
        setupDate()
        val txtTime: TextView = binding.txtTime
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                txtTime.text = currentTime
                handler.postDelayed(this, 1000) // Обновление каждую секунду
            }
        }
        handler.post(runnable)
    }

    private fun setupDate() {
        val txtDate: TextView = binding.txtDate

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())
        val currentDate = LocalDate.now().format(formatter)
        txtDate.text = currentDate
    }

    private fun setupListener() = with(binding) {
        btnOk.setOnClickListener {
            date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
            val etTitle = title.text.toString()
            val etText = text.text.toString()
            if (noteId != -1) {
                val upDateNote = NoteModel(etTitle, etText, color = selectedColor, date = date.toString())
                upDateNote.id = noteId
                App.appDataBase?.noteDao()?.update(upDateNote)
            } else {
                App.appDataBase?.noteDao()?.insert(NoteModel(title = etTitle, description = etText, color = selectedColor, date = date.toString()))
            }
            findNavController().navigateUp()
        }
        btnBack.setOnClickListener {
            val etTitle = title.text.toString()
            val etText = text.text.toString()
            date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
            if (etTitle.isEmpty() && etText.isEmpty() && noteId != -1 ){

                App.appDataBase?.noteDao()?.insert(NoteModel(title = etTitle, description = etText, color = selectedColor, date = date.toString()))
            }
            findNavController().navigateUp()
        }
        menuColor.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog, null)

            builder.setView(dialogView)

            val alertDialog = builder.create()
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
             alertDialog.setOnShowListener {
                 val window = alertDialog.window
                 val params = window?.attributes
                 val density = requireContext().resources.displayMetrics.density
                 val topMargin = (60 * density).toInt()
                 val rightMargin = (60 * density).toInt()

                 params?.gravity = Gravity.TOP or Gravity.END
                 params?.x = rightMargin
                 params?.y = topMargin

                 window?.attributes = params
             }

            val yellowColor = dialogView.findViewById<View>(R.id.color_yellow).setOnClickListener {
                selectedColor = Color.YELLOW
                alertDialog.dismiss()
            }

            val purpleColor = dialogView.findViewById<View>(R.id.color_purple).setOnClickListener {
                selectedColor = Color.MAGENTA
                alertDialog.dismiss()
            }
            val pinkColor = dialogView.findViewById<View>(R.id.color_pink).setOnClickListener {
                selectedColor = Color.rgb(255, 182, 193)
                alertDialog.dismiss()
            }
            val redColor = dialogView.findViewById<View>(R.id.color_red).setOnClickListener {
                selectedColor = Color.RED
                alertDialog.dismiss()
            }
            val greenColor = dialogView.findViewById<View>(R.id.color_green).setOnClickListener {
                selectedColor = Color.GREEN
                alertDialog.dismiss()
            }
            val blueColor = dialogView.findViewById<View>(R.id.color_blue).setOnClickListener {
                selectedColor = Color.BLUE
                alertDialog.dismiss()
            }

            alertDialog.show()
        }
    }

    private fun setupTextWatcher() = with(binding) {
        val checkInputs = {
            val isNotEmpty = title.text.isNotEmpty() && text.text.isNotEmpty()
            btnOk.visibility = if (isNotEmpty) View.VISIBLE else View.GONE
        }

        title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkInputs()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkInputs()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        checkInputs()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
