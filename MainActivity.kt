package com.example.otenki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etCity: EditText = findViewById(R.id.etCity)
        val btnSearch: Button = findViewById(R.id.btnSearch)

        //πγγΏγ³ζΌδΈζγη»ι’ι·η§»
        btnSearch.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)

            //ε₯εγγι½εΈεγζΈ‘γγ(ε½εγ―γ­γΌγε­ε₯εγγγ)
            intent.putExtra("CITY_NAME", etCity.text.toString())

            //γγΌγΉγγθ‘¨η€Ί
            Toast.makeText(this, "γ©γθ‘γγοΌ", Toast.LENGTH_SHORT).show()

            //η»ι’ι·η§»γ’γ―γγ£γγγ£γθ΅·ε
            startActivity(intent)
        }
    }
}