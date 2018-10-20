package com.example.serhiivorobiov.smack.Controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.Services.AuthService
import com.example.serhiivorobiov.smack.Services.UserDataService
import com.example.serhiivorobiov.smack.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity() {

    private val userDataChangeReciever = object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {

            if(AuthService.isLoggedIn) {
                user_name_nav_header.text = UserDataService.name
                user_email_nav_header.text = UserDataService.email
                 val resourceId = resources.getIdentifier(UserDataService.avatarName,"drawable",
                     packageName)
                user_image_nav_header.setImageResource(resourceId)
                user_image_nav_header.setBackgroundColor(UserDataService.returnAvatarColor(UserDataService.avatarColor))
                login_btn_nav_header.setText(R.string.logout)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        hideKeyboard()

        LocalBroadcastManager.getInstance(this).registerReceiver(userDataChangeReciever, IntentFilter(
            BROADCAST_USER_DATA_CHANGE))
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun onAddChannelButtonClicked(view: View) {

        if(AuthService.isLoggedIn) {
        val builder = AlertDialog.Builder(this)
            val dialogAlert = layoutInflater.inflate(R.layout.add_channel_dialog, null)
            builder.setView(dialogAlert)
                .setPositiveButton("Add") {dialog, which ->
                    val nameText = dialogAlert.findViewById<EditText>(R.id.add_channel_name)
                    val discText = dialogAlert.findViewById<EditText>(R.id.add_channel_disc)
                    val channelName = nameText.text.toString()
                    val channelDisc = discText.text.toString()
                    hideKeyboard()

                }
                .setNegativeButton("Cancel") {dialog, which ->
                    hideKeyboard()
                }
                .show()
        }else{
            Toast.makeText(this, "Please Login!", Toast.LENGTH_LONG).show()
        }

    }

    fun onLoginButtonClicked(view: View) {

        if(AuthService.isLoggedIn) {
            UserDataService.logout()
            user_image_nav_header.setImageResource(R.drawable.profiledefault)
            login_btn_nav_header.setText(R.string.login)
            user_email_nav_header.text =""
            user_name_nav_header.text =""
            user_image_nav_header.setBackgroundColor(Color.TRANSPARENT)

        }else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun onSendMessageButtonClicked(view: View){

    }

    fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(inputManager.isAcceptingText){
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken,0)
        }
    }
}
