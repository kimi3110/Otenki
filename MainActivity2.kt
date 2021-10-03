package com.example.otenki

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.otenki.R.id.main_screen
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        WeatherTask().execute()
    }

    //非同期で天気データを取得するクラス
    inner class WeatherTask : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()

            //メイン画面表示,読込み進捗の表示,エラーメッセージ表示
            findViewById<RelativeLayout>(main_screen).visibility = View.GONE
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<TextView>(R.id.error_msg).visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {

            //受け取った"都市名"をCITYとする
            val city: String = intent.getStringExtra("CITY_NAME").toString()
            //OpenWeatherホームページの"APIキー"をAPIとする
            val api = "af26a0662dfc9be35ceb33d36ddf3c61"

            //URLを送信してAPI情報を取得する
            var response:String?
            try { response = URL(
                    "https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&lang=ja&appid=$api"
                ).readText(Charsets.UTF_8)
            }
            //例外処理（エラー処理）
            catch (e: Exception) { response = null   }
            //情報を返す
            return response
        }

        @SuppressLint("SetTextI18n")
        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            try {

                //取得したAPIデータからJSONObjectオブジェクトを生成してJSON文字列化する
                val jsonObj = JSONObject(result)
                val datetime:Long = jsonObj.getLong("dt")
                val updatedAtText = SimpleDateFormat("yyyy/MM/dd ah:mm",
                    Locale.JAPAN).format(Date(datetime*1000))+" ▷現在"
                val sys = jsonObj.getJSONObject("sys")
                val address = jsonObj.getString("name")+" "+sys.getString("country")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val weatherMain = weather.getString("main")
//                val icon = weather.getString("icon")
                val weatherDescription = weather.getString("description")
                val main = jsonObj.getJSONObject("main")
                val temp = main.getInt("temp")
                val feelingTemp = main.getInt("feels_like")
                val pressure = main.getString("pressure")+" hPa"
                val humidity = main.getString("humidity")+" %"
                val wind = jsonObj.getJSONObject("wind")
                val windSpeed = wind.getString("speed")+" m/s"
                val sunrise:Long = sys.getLong("sunrise")
                val sunset:Long = sys.getLong("sunset")

                //抽出した文字列データを各TextViewへ渡して表示させる
                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.now).text =  updatedAtText
                findViewById<TextView>(R.id.weather_main).text = weatherMain.toString()
//                findViewById<TextView>(R.id.icon).text = icon.toString()
                findViewById<TextView>(R.id.description).text = weatherDescription.toString()
                findViewById<TextView>(R.id.temperature_value).text = temp.toString()+"℃"
                findViewById<TextView>(R.id.feeling_temp_value).text =
                    "体感温度 : "+feelingTemp.toString()+"℃"
                findViewById<TextView>(R.id.pressure_value).text = pressure
                findViewById<TextView>(R.id.humidity_value).text = humidity
                findViewById<TextView>(R.id.wind_value).text = windSpeed
                findViewById<TextView>(R.id.sunrise_value).text =
                    SimpleDateFormat("ah:mm", Locale.JAPAN).format(Date(sunrise*1000))
                findViewById<TextView>(R.id.sunset_value).text =
                    SimpleDateFormat("ah:mm", Locale.JAPAN).format(Date(sunset*1000))

                //読込み進捗の表示,メイン画面の表示
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(main_screen).visibility = View.VISIBLE

            //例外処理（エラー処理）
            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.error_msg).visibility = View.VISIBLE
            }

            //再検索ボタン
            val btnBack:Button = findViewById(R.id.btnBack)
            btnBack.setOnClickListener {
                finish()
            }
        }
    }
}