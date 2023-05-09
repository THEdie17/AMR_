package com.example.jankenultra

import android.content.Intent
import android.graphics.Typeface
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class Login : AppCompatActivity() {
    companion object{
        var user_email: String? = null
    }
    private lateinit var emailLogin : EditText
    private lateinit var passLogin : EditText
    private lateinit var login : Button
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //Despleguem les variables que farem servir
        emailLogin = findViewById(R.id.emailLogin)
        passLogin = findViewById(R.id.passLogin)
        auth = FirebaseAuth.getInstance()
        login = findViewById(R.id.login)




        val tf = Typeface.createFromAsset(assets,"fonts/edosz.ttf")
        login.typeface = (tf)

        login.setOnClickListener {
            //Abans de fer el registre validem les dades
            val email: String = emailLogin.text.toString()
            val passw: String = passLogin.text.toString()
            // validació del correu
            // si no es de tipus correu
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailLogin.error = "Invalid Mail"
            } else if (passw.length < 6) {
                passLogin.error = "Password less than 6 chars"
            } else {
                // aquí farem LOGIN al jugador
                playerLogin(email, passw)
            }
        }
    }

    private fun playerLogin(email: String, passw: String) {
        auth.signInWithEmailAndPassword(email, passw)
            .addOnCompleteListener(this)
            { task ->
                if (task.isSuccessful) {
                    val tx = "Benvingut $email"
                    Toast.makeText(this, tx, Toast.LENGTH_LONG).show()
                    auth.currentUser
                    user_email = email
                    updateUI()
                } else {
                    Toast.makeText(
                        this, "ERROR Autentificació",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun updateUI() {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

}
