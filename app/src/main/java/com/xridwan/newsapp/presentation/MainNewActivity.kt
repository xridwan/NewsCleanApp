package com.xridwan.newsapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xridwan.newsapp.databinding.ActivityMainNewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}