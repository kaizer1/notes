package com.work.vanya.notes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.work.vanya.notes.SignInPleaseScreen
import com.work.vanya.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var share : SharedPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        //setContentView(R.layout.activity_main)

          binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSystemUI()

        share = baseContext.getSharedPreferences("main", Context.MODE_PRIVATE)
        val authoEnter  = share.getString("main", "")

        println(" my get authoEnter = \${authoEnter}")

        if (authoEnter!!.isEmpty()) {

            startActivity(Intent(this, SignInPleaseScreen::class.java))

            println(" my authoEnter = \${authoEnter}")
           }


            // create navigation controller
         val navView: BottomNavigationView = binding.navView

         val navController = findNavController(R.id.nav_host_fragment_activity_main)
          navView.setupWithNavController(navController)

    }


  private fun hideSystemUI() {
      WindowCompat.setDecorFitsSystemWindows(window, false)
      WindowInsetsControllerCompat(window, window.decorView).let { controller ->
          controller.hide(WindowInsetsCompat.Type.systemBars())
          controller.systemBarsBehavior =
              WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
      }

  }
}