package com.example.covidmate

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class StateAdapter(val context: Context, var item : ArrayList<String>,var response_final : JSONObject) :
    RecyclerView.Adapter<StateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.state_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StateAdapter.ViewHolder, position: Int) {
        holder.state_name.text = item[position].toString()

        holder.state_layout.setOnClickListener {
            Log.i("RESSRI","INS")
            val s = response_final.getJSONObject(item[position])
            Log.i("RESSRI",s.toString())
            val intent = Intent(it.context,district_details_activity::class.java)
            intent.putExtra("key",item[position].toString())
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return item.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val state_layout = view.findViewById<LinearLayout>(R.id.state_name)
        val state_name = view.findViewById<TextView>(R.id.state_display_name)
    }

}