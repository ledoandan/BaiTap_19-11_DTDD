package com.example.baitap_1911

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baitap_1911.data.AppDatabase
import com.example.baitap_1911.databinding.ActivityMainBinding
import com.example.baitap_1911.repository.StudentRepository
import com.example.baitap_1911.ui.StudentListFragment
import com.example.baitap_1911.viewmodel.StudentViewModelFactory

class MainActivity : AppCompatActivity() {

    val studentViewModelFactory by lazy {
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = StudentRepository(database)
        StudentViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, StudentListFragment())
                .commit()
        }
    }
}