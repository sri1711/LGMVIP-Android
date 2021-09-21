package com.example.covidmate

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class DistrictAdaptor(val context: Context, var item : ArrayList<Districtdata>) :
    RecyclerView.Adapter<DistrictAdaptor.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictAdaptor.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.city_list,parent,false)
        )
    }

    override fun onBindViewHolder(holder: DistrictAdaptor.ViewHolder, position: Int) {
        val model : Districtdata = item[position]
        holder.district_name.text = model.districtName
        holder.active_count.text = "Active : ${model.active}"
        holder.recovered.text = "Recovered : ${model.recovered}"
        holder.deceased.text = "Deceased : ${model.deceased}"
        holder.confirmed.text = "Confirmed : ${model.confirmed}"
    }

    override fun getItemCount(): Int {
        return item.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val state_layout = view.findViewById<LinearLayout>(R.id.state_name)
        val district_name = view.findViewById<TextView>(R.id.tvDistricName)
        val active_count = view.findViewById<TextView>(R.id.tvActive)
        val confirmed = view.findViewById<TextView>(R.id.tvConfirmed)
        val deceased = view.findViewById<TextView>(R.id.tvDeceased)
        val recovered = view.findViewById<TextView>(R.id.tvRecovered)

    }
}