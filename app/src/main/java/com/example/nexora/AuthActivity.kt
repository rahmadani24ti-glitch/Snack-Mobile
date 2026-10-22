package com.example.nexora

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Username dan Password harus diisi",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Retrieve registered credentials from SharedPreferences
            val registeredUsername = sharedPref.getString("registered_username", "") ?: ""
            val registeredPassword = sharedPref.getString("registered_password", "") ?: ""

            // Rule 1: username == password
            // Rule 2: username == registeredUsername && password == registeredPassword
            val isRule1Valid = (username == password)
            val isRule2Valid = (registeredUsername.isNotEmpty() && registeredPassword.isNotEmpty() &&
                    username == registeredUsername && password == registeredPassword)

            if (isRule1Valid || isRule2Valid) {
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username atau password salah. Silahkan coba lagi.")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }
}