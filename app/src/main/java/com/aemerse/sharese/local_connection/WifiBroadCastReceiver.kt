package com.aemerse.sharese.local_connection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener

class WiFiP2PBroadcastReceiver(private val manager: WifiP2pManager, private val channel: WifiP2pManager.Channel, private val connectionInfoListener: ConnectionInfoListener) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action != null) {
            when (action) {
                WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                    manager.requestConnectionInfo(channel, connectionInfoListener)
                }
                WifiManager.WIFI_STATE_CHANGED_ACTION -> {

                }
            }
        }
    }
}
