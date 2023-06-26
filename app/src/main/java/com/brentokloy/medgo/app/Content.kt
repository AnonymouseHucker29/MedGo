package com.brentokloy.medgo.app

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.brentokloy.medgo.app.databinding.ActivityContentBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Content : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarContent.toolbar)

        val progressDialog: ProgressDialog = ProgressDialog(this)
        progressDialog.hide()

        binding.appBarContent.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_content)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_chat, R.id.nav_wallet, R.id.nav_accset
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    //menu ni diri
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.content, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("About us")
                builder.setMessage("We are students from Negros Oriental State University (NOrSU).")
                builder.setPositiveButton("Wa koy pake") { dialogInterface: DialogInterface, i: Int ->
                }
                builder.show()
            }
            R.id.action_signout -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Do you really want to sign out?")
                builder.setPositiveButton("Yes") { DialogInterface, i: Int ->
                    val progressDialog: ProgressDialog = ProgressDialog(this)
                    progressDialog.setMessage("Signing you out...")
                    logout()
                    progressDialog.dismiss()
                }
                builder.setNegativeButton("No") { DialogInterface, i: Int ->
                }
                builder.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {

        val user = Firebase.auth.currentUser
        val useremail: TextView = findViewById(R.id.profileEmail)
        auth = FirebaseAuth.getInstance()
        for (profile in user?.providerData!!) {
            val email: String? = profile.email
            useremail.text = "$email"
        }
        val navController = findNavController(R.id.nav_host_fragment_content_content)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //logout
    private fun logout(){
        val progressDialog: ProgressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait.")
        progressDialog.show()
        progressDialog.setCancelable(false)
        Firebase.auth.signOut()
        finish()
        Toast.makeText(this, "Signed Out.", Toast.LENGTH_SHORT).show()
        intent = Intent(this, Login::class.java)
        startActivity(intent)
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