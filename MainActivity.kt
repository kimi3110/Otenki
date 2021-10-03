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

        //🔍ボタン押下時、画面遷移
        btnSearch.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)

            //入力した都市名を渡す　(国内はローマ字入力がいい)
            intent.putExtra("CITY_NAME", etCity.text.toString())

            //トーストを表示
            Toast.makeText(this, "どこ行くん？", Toast.LENGTH_SHORT).show()

            //画面遷移アクティビティを起動
            startActivity(intent)
        }
    }
}