package com.example.serhiivorobiov.smack.Controller

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Toast
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.Services.AddUserService
import com.example.serhiivorobiov.smack.Services.AuthService
import com.example.serhiivorobiov.smack.Services.LoginService
import com.example.serhiivorobiov.smack.Services.UserDataService
import com.example.serhiivorobiov.smack.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.Random

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var userColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        create_act_spinner.visibility = View.INVISIBLE
    }

    fun generateUserAvatar(view: View) {

        val random = Random()
        val image = random.nextInt(28)
        val color = random.nextInt(2)

        if (color == 0) {
            userAvatar = "light$image"
        } else {
            userAvatar = "dark$image"
        }
        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        UserDataService.avatarResId = resourceId
        create_act_avatar_view.setImageResource(resourceId)
    }

    fun onGenBackgroundColorClicked(view: View) {

        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)
create_act_avatar_view.setBackgroundColor(Color.rgb(r, g, b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = b.toDouble() / 255

        userColor = "[$savedR, $savedG, $savedB, 1]"
    }

    fun onCreateUserButtonClicked(view: View) {
        enableSpinner(true)
        val userName = create_act_user_name_text.text.toString()
        val userEmail = create_act_email_text.text.toString()
        val userPass = create_act_password_text.text.toString()

        if (userPass.isNotEmpty() && userEmail.isNotEmpty() && userName.isNotEmpty()) {
            LoginService.userLogin(userEmail, userPass) { exist ->
                if (exist) {
                    AddUserService.createUser(
                        userName, userEmail,
                        userAvatar, userColor
                    ) { createSuccess ->
                        if (createSuccess) {
                            val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                            LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                            enableSpinner(false)
                            finish()
                        } else {
                            errorToast()
                        }
                    }
                } else {

                    AuthService.userRegister(userEmail, userPass) { registerSuccess ->
                        if (registerSuccess) {
                            LoginService.userLogin(userEmail, userPass) { loginSuccess ->
                                if (loginSuccess) {
                                    AddUserService.createUser(
                                        userName, userEmail,
                                        userAvatar, userColor
                                    ) { createSuccess ->
                                        if (createSuccess) {
                                            val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                            LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                            enableSpinner(false)
                                            finish()
                                        } else {
                                            errorToast()
                                        }
                                    }
                                } else {
                                    errorToast()
                                }
                            }
                        } else {
                            errorToast()
                        }
                    }
                }
            }
        } else {
            Toast.makeText(
                this, "Please, make sure user name, email and/or password are filled in",
                Toast.LENGTH_LONG
            ).show()
            enableSpinner(false)
        }
    }

    fun enableSpinner(enable: Boolean) {

        if (enable) {
            create_act_spinner.visibility = View.VISIBLE
        } else {
            create_act_spinner.visibility = View.INVISIBLE
        }
        create_user_btn.isEnabled = !enable
        create_act_avatar_view.isEnabled = !enable
        background_color_btn.isEnabled = !enable
    }

    fun errorToast() {
        Toast.makeText(this, "Something goes wrong, please try again.",
            Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }
}
