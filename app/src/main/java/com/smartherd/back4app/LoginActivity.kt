package com.smartherd.back4app

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
import com.parse.LogInCallback
import com.parse.ParseException
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var username: TextInputEditText

    private lateinit var password: TextInputEditText

    private lateinit var register: TextView

    private lateinit var loginBtn: Button

    private lateinit var imageLoading: ImageView
    private lateinit var textLoading: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        loginAndRegister()

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

    fun loginAndRegister() {

        username = findViewById(R.id.v_username)

        password = findViewById(R.id.v_password)

        register = findViewById(R.id.v_reset_view)



        loginBtn = findViewById(R.id.v_login_btn)

        register.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        loginBtn.setOnClickListener {

            showLoaders()

            ParseUser.logInInBackground(username.text.toString(), password.text.toString(), object : LogInCallback {

                override fun done(user: ParseUser?, e: ParseException?) {
                    hideLoaders()
                    if (user != null) {
                        if (user.getBoolean("emailVerified")) {
                            alertDisplay("Login Success", "Welcome ${user.username}", false)
                        } else {
                            alertDisplay("Login Failed", "${user.username} Please verify your email", true)
                        }
                    } else {
                        alertDisplay("Login Failed", e?.message + "Please Retry", true)
                    }

                }
            })
        }


    }

    fun alertDisplay(title: String, message: String, flag: Boolean) {
        val builder = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)

                .setPositiveButton("OK", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {

                        p0?.cancel()

                        if (!flag) {
                            val intent = Intent(this@LoginActivity, LoginActivity::class.java)
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