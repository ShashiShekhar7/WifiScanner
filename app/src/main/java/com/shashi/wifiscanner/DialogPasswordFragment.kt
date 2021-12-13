package com.shashi.wifiscanner

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.net.wifi.ScanResult
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.thanosfisherman.wifiutils.WifiUtils
import com.thanosfisherman.wifiutils.wifiConnect.ConnectionErrorCode
import com.thanosfisherman.wifiutils.wifiConnect.ConnectionSuccessListener

class DialogPasswordFragment(val cntxt: Context, val scanResult: ScanResult) : DialogFragment() {

    private val TAG = "DialogPasswordFragment"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_dialog_password, null)

        val builder = AlertDialog.Builder(activity)
        builder.setView(view)
            .setTitle("Password")
            .setNegativeButton("Close") {_,_ -> }
            .setPositiveButton("Connect", null)

        val etPassword : TextInputEditText = view.findViewById(R.id.et_pass)

        val dialog = builder.create()

        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                val password = etPassword.text.toString()
                when {
                    password.isEmpty() -> etPassword.error = "Cannot be empty"
                    password.length < 8 -> etPassword.error = "Less than 8 character"
                    else -> {
                        Toast.makeText(cntxt, "Connecting", Toast.LENGTH_SHORT).show()
                        WifiUtils.withContext(cntxt)
                            .connectWith(scanResult.SSID, password)
                            .setTimeout(40000)
                            .onConnectionResult(object : ConnectionSuccessListener{
                                override fun success() {
                                    Toast.makeText(cntxt, "Connection Success", Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                }

                                override fun failed(errorCode: ConnectionErrorCode) {
                                    Toast.makeText(cntxt, "Connection Failed", Toast.LENGTH_SHORT).show()
                                    Log.d(TAG, "Connection failed: $errorCode")
                                }

                            }).start()
                    }
                }
            }
        }

        return dialog
    }
}