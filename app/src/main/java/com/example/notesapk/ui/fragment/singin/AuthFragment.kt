package com.example.notesapk.ui.fragment.singin

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notesapk.databinding.FragmentAuthBinding
import com.example.notesapk.ui.activity.MainActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.snackbar.Snackbar

class AuthFragment : Fragment() {
    private var binding: FragmentAuthBinding? = null
    private val _binding get() = binding!!

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res: FirebaseAuthUIAuthenticationResult ->
        this.onSignInResult(res)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.btnGoogleAuth.setOnClickListener {
            createSignInIntent()}
    }

    private fun createSignInIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
        )

        val signInIntent =
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers)
                .build()
        signInLauncher.launch(signInIntent)
    }


    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            Snackbar.make(_binding.root, "Ваш аккаунт ${response?.email}", 1000).show()
            activity?.finish()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        } else {
            Snackbar.make(_binding.root, "Неудачная попытка", 1000).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
