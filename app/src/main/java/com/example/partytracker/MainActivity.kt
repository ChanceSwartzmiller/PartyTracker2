package com.example.partytracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// 1. Initialize Preference DataStore at the top level
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "party_tracker_prefs")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameInput = findViewById<EditText>(R.id.counterNameInput)
        val valueInput = findViewById<EditText>(R.id.counterValueInput)
        val startBtn = findViewById<Button>(R.id.startCounterButton)

        // Keys for retrieving data
        val nameKey = stringPreferencesKey("saved_name")
        val valueKey = stringPreferencesKey("saved_value")

        // 2. LOAD DATA: Auto-fill inputs from DataStore when app opens
        lifecycleScope.launch {
            val prefs = dataStore.data.first()
            nameInput.setText(prefs[nameKey] ?: "")
            valueInput.setText(prefs[valueKey] ?: "")
        }

        startBtn.setOnClickListener {
            val intent = Intent(this, ModifierActivity::class.java)
            intent.putExtra("COUNTER_NAME", nameInput.text.toString())
            intent.putExtra("STARTING_VALUE", valueInput.text.toString())
            startActivity(intent)
        }
    }
}