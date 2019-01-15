package com.example.mgalperina.dinnerdecider

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val initialFoodList = arrayListOf("Chinese", "Hamburger", "Pizza", "McDonalds", "Fish and chips")
    var currentFoodList = arrayListOf<String>()
    val KEY_PREF = "dinner_decider"
    val KEY_MY_FOOD_LIST = "food_list"

    lateinit var selectedFoodTxt: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        selectedFoodTxt = findViewById(R.id.selectedFoodTxt)

        decideBtn.setOnClickListener {
            showRandomisedFood()
        }

        addFoodBtn.setOnClickListener {
            addFood()
        }

        findViewById<Button>(R.id.saveFoodItemBtn).setOnClickListener {
            saveData()
        }
    }

    private fun initData() {
        val mypref = getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE)
        val savedFoodList = mypref.getStringSet(KEY_MY_FOOD_LIST, emptySet())
        if (savedFoodList.isEmpty()) {
            // first time launch
            currentFoodList = initialFoodList
        } else {
            savedFoodList.forEach{ currentFoodList.add(it) }
        }
    }

    private fun showRandomisedFood() {
        val random = Random()
        val randomFood = random.nextInt(currentFoodList.count())
        selectedFoodTxt.text = currentFoodList[randomFood]
    }

    private fun addFood() {
        val newFood = addFoodTxt.text.toString()
        currentFoodList.add(newFood)
        addFoodTxt.text.clear()
    }

    private fun saveData() {
        getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE)
            .edit()
            .putStringSet(KEY_MY_FOOD_LIST, currentFoodList.toSet())
            .apply()

    }

}
