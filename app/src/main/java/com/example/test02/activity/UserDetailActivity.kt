package com.example.test02.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.test02.R
import com.example.test02.databinding.ActivityUserDetailBinding
import com.example.test02.model.User
import com.example.test02.model.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnSave.setOnClickListener {
            saveUser()
        }
    }

    private fun saveUser() {
        val name = binding.etName.text.toString().trim()
        val birthDate = binding.etBirthDate.text.toString().trim()
        val gender = binding.etGender.text.toString().trim()

        if (name.isEmpty() || birthDate.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        val user = User(name = name, birthDate = birthDate, gender = gender)

        CoroutineScope(Dispatchers.IO).launch {
            UserDatabase.getInstance(applicationContext).userDao().insert(user)
            runOnUiThread {
                Toast.makeText(this@UserDetailActivity, "User saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}