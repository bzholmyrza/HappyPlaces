package com.happyplaces.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.happyplaces.R

class PlacesActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places)

        var create_account_button = findViewById<Button>(R.id.createActID)

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        mAuth = FirebaseAuth.getInstance()

        //Sign existing users in
        mAuth!!.signInWithEmailAndPassword("beibaryszholmyrza@gmail.com", "admin_SE01")
            .addOnCompleteListener{
                    task: Task<AuthResult> ->
                if (task.isSuccessful){
                    Toast.makeText(this, "Signed In Successful", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Signed In Unsuccessful", Toast.LENGTH_LONG).show()
                }
            }

        create_account_button.setOnClickListener{view ->
            var email = findViewById<EditText>(R.id.emailID).text.toString().trim()
            var password = findViewById<EditText>(R.id.passwordID).text.toString().trim()
            mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task : Task<AuthResult> ->
                    if (task.isSuccessful) {
                        var user: FirebaseUser = mAuth!!.currentUser!!
                        Log.d("User:", user.email.toString())
                    } else {
                        Log.d("Error:", task.toString())
                    }
                }
            register(email, password)
        }

        //Read data from db
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var value = dataSnapshot!!.value as HashMap<String, Any>
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ERROR: ", error!!.message)
            }
        })
    }
    fun register(email: String, password: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("password", password)
        startActivity(intent);
    }

    override fun onStart() {
        super.onStart()
        currentUser = mAuth!!.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "User is logged in", Toast.LENGTH_LONG).show()
        }
    }
}