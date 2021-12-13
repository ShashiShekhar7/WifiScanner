package com.shashi.wifiscanner

import android.net.wifi.ScanResult

interface ScanResultClickListener {
    fun onClick(scanResult: ScanResult)
}