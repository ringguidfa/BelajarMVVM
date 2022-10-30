package com.chirikualii.materiapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.chirikualii.materiapi.R
import com.chirikualii.materiapi.data.dummy.DataDummy
import com.chirikualii.materiapi.data.model.Movie
import com.chirikualii.materiapi.data.remote.ApiClient
import com.chirikualii.materiapi.databinding.ActivityMainBinding
import com.chirikualii.materiapi.ui.adapter.MovieListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieListAdapter
    private val mViewModel: MainViewModel by viewModels(
        factoryProducer = {
            MainViewModelFactory()
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setup adapter
        adapter = MovieListAdapter()
        binding.rvMovie.adapter = adapter

        binding.progresBar.visibility = View.VISIBLE

        mViewModel.doGetPopularMovie()

        mViewModel.listMovie.observe(this) {
            binding.progresBar.visibility = View.INVISIBLE
            adapter.addItem(it)

        }
        mViewModel.isloding.observe(this) { isLoding ->
            if (isLoding)
                binding.progresBar.visibility = View.VISIBLE
        }
    }
}