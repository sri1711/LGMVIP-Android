package com.example.covidmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class home_screen_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        findViewById<LinearLayout>(R.id.ll_state_wise_data).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val queue = Volley.newRequestQueue(this)
        val url = "https://corona.lmao.ninja/v2/countries/India?yesterday=true&strict=true&query"

// Request a string response from the provided URL.
        val request = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                // Display the first 500 characters of the response string.
                Log.i("sri1711","IN2")
                Log.i("sri1711","Response: ${response}")
                Log.i("sri1711","IN3")
                try {
                    val jsonObject = JSONObject(response.toString().trim())
                    val totalCases = jsonObject.get("cases")
                    val totalRecovered = jsonObject.get("recovered")
                    findViewById<TextView>(R.id.tvtotalCovidCases).text = totalCases.toString()
                    findViewById<TextView>(R.id.tvtotalRecovered).text = totalRecovered.toString()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            {it.printStackTrace()
                Log.i("sri1711Error","That didn't work!" )})

// Add the request to the RequestQueue.
        queue.add(request)

    }
}