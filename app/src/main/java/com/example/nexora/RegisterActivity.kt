package com.example.nexora

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etEmail: EditText
    private lateinit var etTanggalLahir: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var rbLaki: RadioButton
    private lateinit var rbPerempuan: RadioButton
    private lateinit var etUsernameReg: EditText
    private lateinit var etPasswordReg: EditText
    private lateinit var etConfirmPasswordReg: EditText
    private lateinit var btnSelanjutnya: Button
    private lateinit var btnBack: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etNama = findViewById(R.id.etNama)
        etEmail = findViewById(R.id.etEmail)
        etTanggalLahir = findViewById(R.id.etTanggalLahir)
        rgGender = findViewById(R.id.rgGender)
        rbLaki = findViewById(R.id.rbLaki)
        rbPerempuan = findViewById(R.id.rbPerempuan)
        etUsernameReg = findViewById(R.id.etUsernameReg)
        etPasswordReg = findViewById(R.id.etPasswordReg)
        etConfirmPasswordReg = findViewById(R.id.etConfirmPasswordReg)
        btnSelanjutnya = findViewById(R.id.btnSelanjutnya)
        btnBack = findViewById(R.id.btnBack)

        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        // Pre-fill fields if user came back from Verification page
        val savedNama = sharedPref.getString("reg_nama", "") ?: ""
        val savedEmail = sharedPref.getString("reg_email", "") ?: ""
        val savedDob = sharedPref.getString("reg_dob", "") ?: ""
        val savedGender = sharedPref.getString("reg_gender", "") ?: ""
        val savedUsername = sharedPref.getString("reg_username", "") ?: ""
        val savedPassword = sharedPref.getString("reg_password", "") ?: ""
        val savedConfirmPassword = sharedPref.getString("reg_confirm_password", "") ?: ""

        if (savedNama.isNotEmpty()) etNama.setText(savedNama)
        if (savedEmail.isNotEmpty()) etEmail.setText(savedEmail)
        if (savedDob.isNotEmpty()) etTanggalLahir.setText(savedDob)
        if (savedGender == "Laki-laki") {
            rbLaki.isChecked = true
        } else if (savedGender == "Perempuan") {
            rbPerempuan.isChecked = true
        }
        if (savedUsername.isNotEmpty()) etUsernameReg.setText(savedUsername)
        if (savedPassword.isNotEmpty()) etPasswordReg.setText(savedPassword)
        if (savedConfirmPassword.isNotEmpty()) etConfirmPasswordReg.setText(savedConfirmPassword)

        // DatePicker for Tanggal Lahir
        etTanggalLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    etTanggalLahir.setText(formattedDate)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        btnBack.setOnClickListener {
            finish()
        }

        // Save to SharedPreferences on "Selanjutnya"
        btnSelanjutnya.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val dob = etTanggalLahir.text.toString().trim()

            val selectedGenderId = rgGender.checkedRadioButtonId
            val gender = when (selectedGenderId) {
                R.id.rbLaki -> "Laki-laki"
                R.id.rbPerempuan -> "Perempuan"
                else -> ""
            }

            val username = etUsernameReg.text.toString().trim()
            val password = etPasswordReg.text.toString().trim()
            val confirmPassword = etConfirmPasswordReg.text.toString().trim()

            val editor = sharedPref.edit()
            editor.putString("reg_nama", nama)
            editor.putString("reg_email", email)
            editor.putString("reg_dob", dob)
            editor.putString("reg_gender", gender)
            editor.putString("reg_username", username)
            editor.putString("reg_password", password)
            editor.putString("reg_confirm_password", confirmPassword)
            editor.apply()

            val intent = Intent(this, VerificationActivity::class.java)
            startActivity(intent)
        }
    }
}