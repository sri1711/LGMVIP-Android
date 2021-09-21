package com.example.covidmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

import org.json.JSONObject

import org.json.JSONArray




class MainActivity : AppCompatActivity() {
    private var stateList: ArrayList<String>? = null
    private var result_object : ArrayList<JSONObject> ?= null
    private var response_final : JSONObject ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
// ...
        stateList = ArrayList<String>()
// Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://data.covid19india.org/state_district_wise.json"

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
                    response_final = jsonObject

                    val keys: Iterator<String> = jsonObject.keys()
                    while (keys.hasNext()) {
                        val key = keys.next()
                        val result = jsonObject.get(key)
                        if (result is JSONObject) {
                            stateList!!.add(key)
                            Log.i("SRI1711V","${key}-----${result}")
                        }
                    }
                    setupStateListRecyclerView()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            {it.printStackTrace()
                Log.i("sri1711Error","That didn't work!" )})

// Add the request to the RequestQueue.
        queue.add(request)


    }

    fun setupStateListRecyclerView() {
        val rvStateList = findViewById<RecyclerView>(R.id.rvStateList)
        rvStateList!!.layoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.VERTICAL,
            false)
        val stateAdaptor = StateAdapter(this,stateList!!,response_final!!)
        rvStateList!!.adapter = stateAdaptor

    }


}