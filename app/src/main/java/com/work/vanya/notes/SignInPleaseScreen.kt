package com.work.vanya.notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.firebase.auth.FirebaseAuth

class SignInPleaseScreen : AppCompatActivity() {


    private lateinit var editEmailSign : EditText
    private lateinit var editPassSign : EditText
     private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.signin)
         hideSystemUI()

        editPassSign = findViewById(R.id.password_enter_sign)
        editEmailSign = findViewById(R.id.enter_email_sign)
        auth = FirebaseAuth.getInstance()

        val buttRegister = findViewById<Button>(R.id.registetbut_insign).setOnClickListener {

     startActivity(Intent(this, RegistrationScreen::class.java).apply {
            putExtra("keyIdentifier", 0)
          })
        }


        this.onBackPressedDispatcher.addCallback {
            println(" no press and go to another activity ! ")
        }


        val butSignIn = findViewById<Button>(R.id.enter_in_signaa).setOnClickListener {

         if(editEmailSign.text.isEmpty()) {
                ToastSee2("Please enter valid email ")


            }else {
                 if(editPassSign.text.isEmpty()){
            ToastSee2("Please enter valid password ")
               }else {

                    println(" ok, all registrations ")

                     val emailToSign = editEmailSign.text.toString().replace(" ", "")

                     auth.signInWithEmailAndPassword(emailToSign, editPassSign.text.toString())
                         .addOnCompleteListener { task ->
                             if(task.isSuccessful){

                                 ToastSee2("ok you are signIn success ")

                                 val user = auth.currentUser!!.uid
                                 println("my uid = $user")
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
                                 ToastSee2(" You not success user signIn ")
                             }
                         }


                     //ToastSee("Ok, you are registration ")
                    // zaregistStart
                 }
            }


        }
      }


      override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }




      }


//    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
//    override fun onBackPressed() {
//        super.onBackPressed()
//
//        println(" no back to screen ")
//    }





       private fun ToastSee2(text : String ){
           val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(applicationContext, text, duration)
         toast.show()
      }


        private fun hideSystemUI() {
         WindowCompat.setDecorFitsSystemWindows(window, false)
         WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }



}