package com.shashi.wifiscanner

import android.content.Context
import android.net.wifi.ScanResult
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thanosfisherman.wifiutils.WifiUtils
import com.thanosfisherman.wifiutils.wifiConnect.ConnectionErrorCode
import com.thanosfisherman.wifiutils.wifiConnect.ConnectionSuccessListener

class MainActivity : AppCompatActivity(), ScanResultClickListener {
    private val TAG = "MainActivity"
    private lateinit var scanAdapter : ScanAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonScan : Button = findViewById(R.id.btn_scan)
        val recyclerView : RecyclerView = findViewById(R.id.rv_scan_res)

        scanAdapter = ScanAdapter(this)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = scanAdapter
        }

        buttonScan.setOnClickListener {
            WifiUtils.withContext(applicationContext).scanWifi(this::getScanResult).start()
        }
    }

    private fun getScanResult(result: List<ScanResult>) {
        if (result.isEmpty()) {
            Log.d(TAG, "getScanResult: Empty")
            return
        }
        scanAdapter.setScanResult(result)
        Log.d(TAG, "getScanResult: $result")
    }

    override fun onClick(scanResult: ScanResult) {
        val dialog = DialogPasswordFragment(applicationContext, scanResult)
        dialog.show(supportFragmentManager, "DialogPasswordFragment")
    }
}