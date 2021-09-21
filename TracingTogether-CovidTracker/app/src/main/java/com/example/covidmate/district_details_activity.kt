package com.example.covidmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class district_details_activity : AppCompatActivity() {
    var districtData : ArrayList<Districtdata>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_district_details)

        districtData = ArrayList<Districtdata>()
        val key = intent.getStringExtra("key")
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
                    var jsonObject = JSONObject(response.get(key) .toString().trim())
                    jsonObject = jsonObject.getJSONObject("districtData")
                    val keys: Iterator<String> = jsonObject.keys()
                    while (keys.hasNext()) {
                        val key = keys.next()

                        val result = jsonObject.get(key)
                        if (result is JSONObject) {
                            districtData!!.add(Districtdata("${key}",
                                "${result.get("active").toString()}",
                                "${result.get("confirmed").toString()}",
                               "${result.get("deceased").toString()}",
                            "${result.get("recovered").toString()}"))
                            Log.i("SRI1711V","${key}-----${result}")
                            // do something with jsonObject here
                        }
                    }
//                    Log.i("SRI1711V",districtData!!.get(6).confirmed)
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
        val rvStateList = findViewById<RecyclerView>(R.id.rvCityDetailsList)
        rvStateList!!.layoutManager = LinearLayoutManager(
            this@district_details_activity,
            LinearLayoutManager.VERTICAL,
            false)

        val districtdataAdaptor = DistrictAdaptor(this,districtData!!)

        rvStateList!!.adapter = districtdataAdaptor
    }
}