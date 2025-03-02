package com.cashwu.notekeeper

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.cashwu.notekeeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        class Person (val name: String, var weightLbs: Double){
            var weightKilos: Double
                get() = weightLbs / 2.20462
                set(value) {
                    weightLbs = value * 2.20462
                }
            fun eatDessert(addedIceCream: Boolean = true){
                weightLbs += if (addedIceCream) 4 else 2
            }

            fun calcGoalWeightLbs(lbsToLose: Double = 10.0): Double {
                return weightLbs - lbsToLose
            }
        }

        val p = Person("bob", 200.0)
        val name = p.name

        p.eatDessert(false)
        p.eatDessert()

        p.calcGoalWeightLbs()
        p.calcGoalWeightLbs(10.1)

        val person = Person(weightLbs = 180.0, name = "cc")


        binding.btnUp.setOnClickListener { view ->

            val txt = binding.root.findViewById<TextView>(R.id.textDisplayedValue);

            val originalValue = txt.text.toString().toInt();
            val newValue = originalValue * 2;
            txt.text = newValue.toString();

            Snackbar.make(view, "Value $originalValue doubled to $newValue",
                Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.btnUp).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}