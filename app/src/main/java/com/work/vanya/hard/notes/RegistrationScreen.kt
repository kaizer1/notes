package com.work.vanya.hard.notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.firebase.auth.FirebaseAuth
import com.work.vanya.hard.notes.R

class RegistrationScreen : AppCompatActivity() {

      private lateinit var editEmail : EditText
      private lateinit var editPass  : EditText
      private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.registrati)
         hideSystemUI()


        // Get Instance Firebase

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser == null){
            println(" current user is null ! ")
        }

        editEmail = findViewById(R.id.enter_email_register)
        editPass  = findViewById(R.id.password_enter_register)

        val butZaregist = findViewById<Button>(R.id.registetbut_inregister).setOnClickListener {

             // check out edit Text is not null
             if(editEmail.text.isEmpty()) {
        ToastSee("Please enter valid email ")


            }else {
                 if(editPass.text.isEmpty()){
        ToastSee("Please enter valid password ")
               }else {

                    println(" ok, all registrations ")
                     // remove  " " and email, was error
                     val emailToRegistter = editEmail.text.toString().replace(" ", "")


                     // send data to register new user
                     auth.createUserWithEmailAndPassword(emailToRegistter, editPass.text.toString())
                         .addOnCompleteListener { task ->
                             if(task.isSuccessful){
                                 ToastSee("ok you are register success ")

                                 val user = auth.currentUser!!.uid


                                 // save UID users, to enter and connect in firebase from anything place in program
                                   val  share = baseContext.getSharedPreferences("main", Context.MODE_PRIVATE)

                                val df = share.edit()

                                 df.let {
                                     df.putString("main", user)
                                 }.apply()

                   startActivity(Intent(this, MainActivity::class.java).apply {
                     putExtra("keyIdentifier", user)
               })

                             }else
                             {
                                 ToastSee(" You not success user registers ")
                             }
                         }


                     //ToastSee("Ok, you are registration ")
                    // zaregistStart
                 }
            }


        }

    }

    // function to see Messages to screen
      private fun ToastSee(text : String ){
           val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(applicationContext, text, duration)
         toast.show()
      }


      override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }



        private fun hideSystemUI() {
         WindowCompat.setDecorFitsSystemWindows(window, false)
         WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }


}