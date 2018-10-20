package com.example.serhiivorobiov.smack.Controller

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.Services.AddUserService
import com.example.serhiivorobiov.smack.Services.AuthService
import com.example.serhiivorobiov.smack.Services.LoginService
import com.example.serhiivorobiov.smack.Services.UserDataService
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var userColor = "[0.5,0.5,0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun generateUserAvatar(view: View) {

        val random = Random()
        val image = random.nextInt(28)
        val color = random.nextInt(2)

        if(color==0){
            userAvatar = "light$image"
        }else{
            userAvatar = "dark$image"
        }
val resourceId = resources.getIdentifier(userAvatar,"drawable",packageName)

        create_act_avatar_view.setImageResource(resourceId)
    }

    fun onGenBackgroundColorClicked(view: View) {

        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)
create_act_avatar_view.setBackgroundColor(Color.rgb(r,g,b))

        val savedR = r.toDouble()/255
        val savedG = g.toDouble()/255
        val savedB = b.toDouble()/255

        userColor = "[$savedR, $savedG,$savedB, 1]"
    }

    fun onCreateUserButtonClicked(view: View) {

        val userName = create_act_user_name_text.text.toString()
        val userEmail = create_act_email_text.text.toString()
        val userPass = create_act_password_text.text.toString()

        AuthService.userRegister(this, userEmail, userPass) {registerSuccess ->

            if(registerSuccess) {
LoginService.userLogin(this,userEmail,userPass){loginSuccess ->
    if(loginSuccess) {

       AddUserService.createUser(this, userName, userEmail, userColor, userAvatar) {createSuccess->
           if(createSuccess) {
               println(UserDataService.avatarColor)
               println(UserDataService.name)
               println(UserDataService.avatarName)
               finish()
           }

       }
    }

}
            }
        }
    }
}
