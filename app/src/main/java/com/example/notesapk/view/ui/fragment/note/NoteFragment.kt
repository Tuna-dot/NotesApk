package com.example.notesapk.view.ui.fragment.note

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapk.R
import com.example.notesapk.model.data.models.NoteModel
import com.example.notesapk.databinding.FragmentNoteBinding
import com.example.notesapk.MainActivity
import com.example.notesapk.view.ui.adapters.NoteAdapter
import com.example.notesapk.view.ui.interfaces.onClickItem
import com.example.notesapk.model.data.utils.PreferenceHelper
import com.example.notesapk.presenter.notes.NoteContract
import com.example.notesapk.presenter.notes.NotePresenter

class NoteFragment : Fragment(), onClickItem, NoteContract.View {
    var binding: FragmentNoteBinding? = null
    private val noteAdapter = NoteAdapter(onLongClick = this, onClick = this)// Создаем адаптер для RecyclerView
    private val sharedPreferences = PreferenceHelper()// Создаем экземпляр класса PreferenceHelper
    private var isGridLayout = false // Переменная для отслеживания текущего макета
    private val presenter by lazy { NotePresenter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences.unit(requireContext())
        loadLayoutManagerState()
        initialize()
        setupListener()
        presenter.loadNotes()
      //  getData()
    }

    private fun loadLayoutManagerState() {
        isGridLayout = !sharedPreferences.saveLayoutManager
    }

    private fun initialize() {
        // Устанавливаем RecyclerView с начальным LayoutManager (LinearLayoutManager)
        binding?.rvNote?.apply {
            layoutManager = if (isGridLayout){
                GridLayoutManager(requireContext(), 2)
            }else{
                LinearLayoutManager(requireContext())
            }
            adapter = noteAdapter
        }
        updateLayoutManagerIcon()
    }

    private fun updateLayoutManagerIcon() {
        binding?.layoutManager?.setImageResource(if (isGridLayout) R.drawable.shape2 else R.drawable.shape)
    }

    private fun setupListener() = with(binding!!) {
        btnAction.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_noteDetailFragment)
        }

        // Переключаем макет при нажатии на ImageView с id "layout_manager"
        layoutManager.setOnClickListener {
            toggleLayoutManager()
        }

        drawerMenu.setOnClickListener{
            (activity as? MainActivity)?.openDrawer()
        }
    }

    // Метод для переключения между LinearLayoutManager и GridLayoutManager
    private fun toggleLayoutManager() {
        binding?.rvNote?.apply {
            if (isGridLayout) {
                // Если текущий макет сетка, меняем на LinearLayoutManager
                layoutManager = LinearLayoutManager(requireContext())
                isGridLayout = false
            } else {
                // Если текущий макет список, меняем на GridLayoutManager
                layoutManager = GridLayoutManager(requireContext(), 2) // 2 столбца в сетке
                isGridLayout = true
            }
            saveLayoutManagerState()
            updateLayoutManagerIcon()
        }
    }

    private fun saveLayoutManagerState() {
        sharedPreferences.saveLayoutManager = !isGridLayout
    }

 /*   private fun getData() {
        App.appDataBase?.noteDao()?.getAll()?.observe(viewLifecycleOwner) { listModel ->
            noteAdapter.submitList(listModel)
        }
    }*/

    override fun onLongClick(noteModel: NoteModel) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog2, null)
        val builder = AlertDialog.Builder(requireContext())

        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val dialogNegative = dialogView.findViewById<TextView>(R.id.negative_button)
        val dialogPositive = dialogView.findViewById<TextView>(R.id.positive_button)
        dialogNegative?.setOnClickListener {
            dialog.dismiss()
        }
        dialogPositive?.setOnClickListener {
            Toast.makeText(requireContext(), "Заметка удалена", Toast.LENGTH_SHORT).show()
            presenter.deleteNote(noteModel)
           // App.appDataBase?.noteDao()?.delete(noteModel)
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onClick(noteModel: NoteModel) {
        val action = NoteFragmentDirections.actionNoteFragmentToNoteDetailFragment(noteModel.id)
        findNavController().navigate(action)
    }

    override fun showNotes(notes: List<NoteModel>) {
        noteAdapter.submitList(notes)
        noteAdapter.notifyDataSetChanged()
    }

    override fun showErorr(message: String) {
        Log.e("ololo", message)
    }
}
