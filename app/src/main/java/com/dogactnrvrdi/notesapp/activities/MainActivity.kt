package com.dogactnrvrdi.notesapp.activities

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment

        val appBarConfig = AppBarConfiguration(navHostFragment.findNavController().graph)
        binding.toolbar.setupWithNavController(navHostFragment.navController, appBarConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == androidx.appcompat.R.id.home) {
            alertDialog(this, this)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun alertDialog(context: Context, activity: MainActivity) {
        println("Alert Dialog")
        val alert = AlertDialog.Builder(context)
        alert.setTitle(R.string.want_to_quit)
        alert.setMessage(R.string.notes_will_be_deleted)
        alert.setPositiveButton(R.string.yes) { p0, p1 ->
            Toast.makeText(
                context, R.string.not_saved, Toast.LENGTH_SHORT
            ).show()
            activity.supportFragmentManager.popBackStack()
        }
        alert.setNegativeButton(R.string.no) { p0, p1 ->
            Toast.makeText(
                context,
                "Continue",
                Toast.LENGTH_SHORT
            ).show()
        }
        alert.show()
    }
}