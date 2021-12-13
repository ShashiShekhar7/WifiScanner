package com.shashi.wifiscanner

import android.net.wifi.ScanResult
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScanAdapter(val itemClickListener: ScanResultClickListener) : RecyclerView.Adapter<ScanAdapter.ScanViewHolder>() {

    private val TAG = "ScanAdapter"

    private var scanResult : List<ScanResult> = ArrayList<ScanResult>()

    fun setScanResult(scanResult: List<ScanResult>) {
        this.scanResult = scanResult
        notifyDataSetChanged()
        Log.d(TAG, "setScanResult: size : ${scanResult.size}")
    }

    class ScanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSSID : TextView = itemView.findViewById(R.id.tv_ssid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_wifi_ap, parent, false)
        return ScanViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScanViewHolder, position: Int) {
        holder.tvSSID.text = scanResult[position].SSID
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(scanResult[position])
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${scanResult.size}")
        return scanResult.size
    }
}