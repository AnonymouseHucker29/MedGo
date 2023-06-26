package com.brentokloy.medgo.app

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.net.NetworkInfo
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainbutton: TextView = findViewById(R.id.mainButton)
        auth = FirebaseAuth.getInstance()
        val user = Firebase.auth.currentUser

        try {
            this.supportActionBar!!.hide()
        }
        catch (e: NullPointerException) {
        }

        mainbutton.setOnClickListener {
            val progressDialog: ProgressDialog = ProgressDialog(this)
            progressDialog.setMessage("Paghuwat!")
            progressDialog.show()
            progressDialog.setCancelable(false)

            if (user != null && verifConn()){
                finish()
                auth = FirebaseAuth.getInstance()
                for (profile in user.providerData) {
                    val email: String? = profile.email
                    Toast.makeText(this, "Welcome back $email", Toast.LENGTH_SHORT).show()
                }
                intent = Intent(this, Content::class.java)
                startActivity(intent)
            }else if (!verifConn()){
                progressDialog.dismiss()
                Toast.makeText(this, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()
            }
            else {
                finish()
                intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
        }
    }

    private fun verifConn(): Boolean {
        var connected: Boolean = false
        try {
            val cm =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
            return connected
        } catch (e: Exception) {
            e.message?.let { Log.e("Connectivity Exception", it) }
        }
        return connected
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you really want to exit the app?")
        builder.setPositiveButton("Yes") { DialogInterface, i: Int ->
            finish()
        }
        builder.setNegativeButton("No"){ DialogInterface, i: Int ->
        }
        builder.show()
        builder.setCancelable(false)
    }
}