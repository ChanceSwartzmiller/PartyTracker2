package com.example.partytracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ModifierActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modifier)

        val displayCounterName = findViewById<TextView>(R.id.displayCounterName)
        val displayCounterValue = findViewById<TextView>(R.id.displayCounterValue)
        val plusBtn = findViewById<Button>(R.id.plusButton)
        val minusBtn = findViewById<Button>(R.id.minusButton)
        val exitBtn = findViewById<Button>(R.id.exitSessionButton)

        val targetName = intent.getStringExtra("COUNTER_NAME") ?: "Active Counter"
        var currentCount = intent.getStringExtra("STARTING_VALUE")?.toIntOrNull() ?: 0

        val nameKey = stringPreferencesKey("saved_name")
        val valueKey = stringPreferencesKey("saved_value")

        displayCounterName.text = targetName
        displayCounterValue.text = currentCount.toString()

        // SAVE DATA: Instantly backup data on launch
        lifecycleScope.launch {
            dataStore.edit { prefs ->
                prefs[nameKey] = targetName
                prefs[valueKey] = currentCount.toString()
            }
        }

        plusBtn.setOnClickListener {
            currentCount++
            displayCounterValue.text = currentCount.toString()
            // SAVE DATA: Backup data on every single tap
            lifecycleScope.launch {
                dataStore.edit { prefs -> prefs[valueKey] = currentCount.toString() }
            }
        }

        minusBtn.setOnClickListener {
            currentCount--
            displayCounterValue.text = currentCount.toString()
            // SAVE DATA: Backup data on every single tap
            lifecycleScope.launch {
                dataStore.edit { prefs -> prefs[valueKey] = currentCount.toString() }
            }
        }

        exitBtn.setOnClickListener {
            // WIPE DATA: Clear preferences when session is officially ended
            lifecycleScope.launch {
                dataStore.edit { prefs -> prefs.clear() }
            }
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}