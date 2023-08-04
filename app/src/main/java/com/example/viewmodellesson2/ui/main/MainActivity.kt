package com.example.viewmodellesson2.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.example.viewmodellesson2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding:ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    var count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        listeners()

        observeFruitList()
        observeCounter()




    }

    private fun listeners() {
        binding.btnAddFruit.setOnClickListener {
            viewModel.addFruit(binding.etFruit.text.toString())
        }

        binding.btnCount.setOnClickListener {
            viewModel.counterButtonClicked()
            //binding.tvCounter.text = viewModel.count.toString()
            //binding.tvCounter.text = count.toString()
            //count++
        }
    }

    private fun observeCounter() {
        viewModel.counterLiveData.observe(this){
            binding.tvCounter.text = it.toString()
        }
    }

    private fun observeFruitList() {

        viewModel.fruitListLiveData.observe(this){
            //it.forEach { println("Meyve: $it") }
            binding.spFruits.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, it)
        }
    }


}