package com.example.nexora

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_profile,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val tvAvatarInitial =
            view.findViewById<TextView>(R.id.tvAvatarInitial)
        val tvProfileName =
            view.findViewById<TextView>(R.id.tvProfileName)
        val tvUsername =
            view.findViewById<TextView>(R.id.tvUsername)

        val tvFullName =
            view.findViewById<TextView>(R.id.tvProfileFullName)
        val tvEmail =
            view.findViewById<TextView>(R.id.tvProfileEmail)
        val tvDob =
            view.findViewById<TextView>(R.id.tvProfileDob)
        val tvGender =
            view.findViewById<TextView>(R.id.tvProfileGender)
        val tvProfileUsername =
            view.findViewById<TextView>(R.id.tvProfileUsername)

        val btnLogout =
            view.findViewById<Button>(R.id.btnLogout)

        val sharedPref = requireActivity()
            .getSharedPreferences(
                "user_pref",
                Context.MODE_PRIVATE
            )

        // Ambil semua data registrasi
        val nama = sharedPref.getString("reg_nama", "") ?: ""
        val email = sharedPref.getString("reg_email", "") ?: ""
        val dob = sharedPref.getString("reg_dob", "") ?: ""
        val gender = sharedPref.getString("reg_gender", "") ?: ""
        val username =
            sharedPref.getString("reg_username", "") ?: ""

        // Tampilkan nama di kartu profil
        tvProfileName.text = nama.ifBlank { "Nama belum tersedia" }

        // Tampilkan username di kartu profil
        tvUsername.text = if (username.isNotBlank()) {
            "@$username"
        } else {
            "Username belum tersedia"
        }

        // Tampilkan inisial avatar
        tvAvatarInitial.text = nama.firstOrNull()
            ?.uppercaseChar()
            ?.toString() ?: "U"

        // Tampilkan semua informasi akun
        tvFullName.text = nama.ifBlank { "-" }
        tvEmail.text = email.ifBlank { "-" }
        tvDob.text = dob.ifBlank { "-" }
        tvGender.text = gender.ifBlank { "-" }
        tvProfileUsername.text = username.ifBlank { "-" }

        // Logout
        btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Apakah kamu yakin ingin logout?")
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Ya") { dialog, _ ->
                    sharedPref.edit().clear().apply()
                    dialog.dismiss()

                    val intent = Intent(
                        requireContext(),
                        AuthActivity::class.java
                    ).apply {
                        flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }

                    startActivity(intent)
                    requireActivity().finish()
                }
                .show()
        }
    }
}