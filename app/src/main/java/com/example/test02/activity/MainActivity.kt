package com.example.test02.activity

import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test02.R
import com.example.test02.adatper.UserAdapter
import com.example.test02.databinding.ActivityMainBinding
import com.example.test02.model.UserDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var isFabRotated = false
   private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            if (isFabRotated) {
                animateFabReset(fab)
            } else {
                animateFabRotate(fab)
            }
            isFabRotated = !isFabRotated
            animateFabTranslateAndRotate(fab)
            val intent = Intent(this, UserDetailActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        loadUsers()
    }

    private fun loadUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val userList = UserDatabase.getInstance(applicationContext).userDao().getAllUsers()
            runOnUiThread {
                adapter = UserAdapter(userList)
                binding.recyclerView.adapter = adapter
            }
        }
    }

    private fun animateFabRotate(fab: FloatingActionButton) {
        fab.animate()
            .rotation(360f) // Rotate
            .scaleX(1.2f) // Slight scale up
            .scaleY(1.2f)
            .setDuration(200)
            .start()
    }

    private fun animateFabReset(fab: FloatingActionButton) {
        fab.animate()
            .rotation(0f) // Reset rotation
            .scaleX(1f) // Reset scale
            .scaleY(1f)
            .setDuration(200)
            .start()
    }

    private fun animateFabTranslateAndRotate(fab: FloatingActionButton) {
        fab.animate()
            .translationY(-200f) // Move upward
            .rotationBy(180f) // Rotate halfway
            .setDuration(300)
            .setInterpolator(OvershootInterpolator()) // Smooth bounce effect
            .start()
    }
}