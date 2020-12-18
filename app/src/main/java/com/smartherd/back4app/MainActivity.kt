package com.smartherd.back4app

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.parse.ParseException
import com.parse.ParseInstallation
import com.parse.ParseUser
import com.parse.SignUpCallback

class MainActivity : AppCompatActivity() {

    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var username: TextInputEditText
    private lateinit var signUpBtn: Button
    private lateinit var logPage: TextView
    private lateinit var imageLoading: ImageView
    private lateinit var textLoading: TextView


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        ParseInstallation.getCurrentInstallation().saveInBackground()


        email = findViewById(R.id.v_email_signup)
        password = findViewById(R.id.v_password_signup)
        username = findViewById(R.id.v_username_signup)
        logPage = findViewById(R.id.v_login_signup)
        signUpBtn = findViewById(R.id.v_sign_up)

        logPage.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }


        signUpBtn.setOnClickListener() {

            showLoaders()


//            ParseUser.getCurrentUser()?.deleteInBackground(object :DeleteCallback{
//                override fun done(e: ParseException?) {
//                    if (e==null)
//                    {
//                        Toast.makeText(signUpBtn.context,"Deleted User",Toast.LENGTH_SHORT).show()
//                    }
//                    else{
//                        Toast.makeText(signUpBtn.context,e.message,Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//            })


            email = findViewById(R.id.v_email_signup)
            password = findViewById(R.id.v_password_signup)
            username = findViewById(R.id.v_username_signup)
            signUpBtn = findViewById(R.id.v_sign_up)


            val user = ParseUser()

            user.email = email.text.toString()
            user.username = username.text.toString()
            user.setPassword(password.text.toString())

            user.signUpInBackground(object : SignUpCallback {

                override fun done(e: ParseException?) {
                    hideLoaders()
                    if (e == null) {
                        ParseUser.logOut()

                        alertDisplay("Sign Up Details", "Sign Up Success,${user.username} Please Confirm Email Before Login ", false)
                    } else {
                        ParseUser.logOut()
                        alertDisplay("Sign Up Details", "Login In Failed " + e.message, true)

                    }
                }
            })
        }


    }

    fun showLoaders() {
        imageLoading = findViewById(R.id.v_loader)
        textLoading = findViewById(R.id.v_loading_text)

        imageLoading.visibility = View.VISIBLE
        textLoading.visibility = View.VISIBLE


    }

    fun hideLoaders() {
        imageLoading = findViewById(R.id.v_loader)
        textLoading = findViewById(R.id.v_loading_text)

        imageLoading.visibility = View.GONE
        textLoading.visibility = View.GONE

    }

    fun alertDisplay(title: String, message: String, flag: Boolean) {
        val builder = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)

                .setPositiveButton("OK", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {

                        p0?.cancel()

                        if (!flag) {
                            //Add your class here
                            val intent = Intent(this@MainActivity, LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                        }


                    }
                })

        val alertDialog = builder.create()
        alertDialog.show()


    }
}
