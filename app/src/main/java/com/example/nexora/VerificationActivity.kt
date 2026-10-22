package com.example.nexora

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class VerificationActivity : AppCompatActivity() {

    private lateinit var tvValNama: TextView
    private lateinit var tvValEmail: TextView
    private lateinit var tvValDob: TextView
    private lateinit var tvValGender: TextView
    private lateinit var tvValUsername: TextView
    private lateinit var tvValPassword: TextView
    private lateinit var tvValConfirmPassword: TextView
    private lateinit var btnSubmit: Button
    private lateinit var btnKembali: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        tvValNama = findViewById(R.id.tvValNama)
        tvValEmail = findViewById(R.id.tvValEmail)
        tvValDob = findViewById(R.id.tvValDob)
        tvValGender = findViewById(R.id.tvValGender)
        tvValUsername = findViewById(R.id.tvValUsername)
        tvValPassword = findViewById(R.id.tvValPassword)
        tvValConfirmPassword = findViewById(R.id.tvValConfirmPassword)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnKembali = findViewById(R.id.btnKembali)

        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        // Read stored fields from SharedPreferences
        val nama = sharedPref.getString("reg_nama", "") ?: ""
        val email = sharedPref.getString("reg_email", "") ?: ""
        val dob = sharedPref.getString("reg_dob", "") ?: ""
        val gender = sharedPref.getString("reg_gender", "") ?: ""
        val username = sharedPref.getString("reg_username", "") ?: ""
        val password = sharedPref.getString("reg_password", "") ?: ""
        val confirmPassword = sharedPref.getString("reg_confirm_password", "") ?: ""

        tvValNama.text = if (nama.isNotEmpty()) nama else "-"
        tvValEmail.text = if (email.isNotEmpty()) email else "-"
        tvValDob.text = if (dob.isNotEmpty()) dob else "-"
        tvValGender.text = if (gender.isNotEmpty()) gender else "-"
        tvValUsername.text = if (username.isNotEmpty()) username else "-"
        tvValPassword.text = if (password.isNotEmpty()) password else "-"
        tvValConfirmPassword.text = if (confirmPassword.isNotEmpty()) confirmPassword else "-"

        // "Kembali" button to return to Register page and fix values
        btnKembali.setOnClickListener {
            finish()
        }

        // "Submit" button to validate and complete registration
        btnSubmit.setOnClickListener {
            // Check 1: Semua inputan tidak boleh kosong
            if (nama.isEmpty() || email.isEmpty() || dob.isEmpty() || gender.isEmpty() ||
                username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
            ) {
                AlertDialog.Builder(this)
                    .setTitle("Validasi Gagal")
                    .setMessage("Semua inputan tidak boleh kosong! Silahkan tekan Kembali untuk melengkapi data.")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                return@setOnClickListener
            }

            // Check 2: Password dan Confirm Password harus sama
            if (password != confirmPassword) {
                AlertDialog.Builder(this)
                    .setTitle("Validasi Gagal")
                    .setMessage("Password dan Confirm Password harus sama! Silahkan tekan Kembali untuk memperbaiki.")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                return@setOnClickListener
            }

            // Save registered credentials to SharedPreferences
            val editor = sharedPref.edit()
            editor.putString("registered_username", username)
            editor.putString("registered_password", password)
            editor.apply()

            // Navigate to Success Activity
            val intent = Intent(this, SuccessActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}