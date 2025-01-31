package com.example.notesapk.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.notesapk.R
import com.example.notesapk.databinding.ActivityMainBinding
import com.example.notesapk.utils.PreferenceHelper
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding
    private val sharedPreferences = PreferenceHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        sharedPreferences.unit(this)

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        if (sharedPreferences.onBoard && FirebaseAuth.getInstance().currentUser != null) {
            graph.setStartDestination(R.id.authFragment)
        } else if (sharedPreferences.onBoard && FirebaseAuth.getInstance().currentUser == null) {
            graph.setStartDestination(R.id.noteFragment)
        }

        navHostFragment.navController.graph = graph

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}