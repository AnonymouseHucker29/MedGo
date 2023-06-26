package com.brentokloy.medgo.app

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.NullPointerException

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        try {
            this.supportActionBar!!.hide()
        }
        catch (e: NullPointerException) {
        }

        val regEmailInput: EditText = findViewById(R.id.regEmailInput)
        val regPasswordInput: EditText = findViewById(R.id.regPasswordInput)
        val regLogin: TextView = findViewById(R.id.regLogin)
        val regButton: TextView = findViewById(R.id.regButton)
        val uploadButton: Button = findViewById(R.id.uploadIMG)

        auth = FirebaseAuth.getInstance()

        regButton.setOnClickListener {
            val progressDialog: ProgressDialog = ProgressDialog(this)
            progressDialog.setMessage("Paghuwat!")
            progressDialog.show()
            progressDialog.setCancelable(false)

            if (verify()){

                val useremail: String = regEmailInput.text.toString().trim()
                val userpassword: String = regPasswordInput.text.toString().trim()

                auth.createUserWithEmailAndPassword(useremail, userpassword).addOnCompleteListener {
                    if (it.isSuccessful){
                        progressDialog.dismiss()
                        finish()
                        Toast.makeText(this, "Registration Successful :)", Toast.LENGTH_SHORT).show()
                        intent = Intent(this, Content::class.java)
                        startActivity(intent)
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Registration Failed :(", Toast.LENGTH_SHORT).show()
                    }
                }

            } else{
                progressDialog.dismiss()
            }
        }
        regLogin.setOnClickListener {
            intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        uploadButton.setOnClickListener {
            val i = Intent()
            i.setType("image/*")
            i.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(i, "Choose Picture"), 100)
        }
    }

    private fun verify(): Boolean {
        var result = false
        val regEmailInput: EditText = findViewById(R.id.regEmailInput)
        val regPasswordInput: EditText = findViewById(R.id.regPasswordInput)

        val email = regEmailInput.text.toString()
        val password = regPasswordInput.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please input data in each field.", Toast.LENGTH_SHORT).show()
        }else{
            result = true
        }
        return result
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