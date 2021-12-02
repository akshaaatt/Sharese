package com.aemerse.sharese.interfaces

import com.aemerse.sharese.modals.ConnReceiveFileItem
import com.aemerse.sharese.modals.ConnSendFileItem

interface ShareActivityImpl {

    fun onSocketsConnected()

    fun onAddedSenderItem(item: ConnSendFileItem)
    fun onAddedReceiverItem(downloadUrl: String, receiveItem: ConnReceiveFileItem)


    fun onSendingItemStart(item: ConnSendFileItem)

    fun onSendingItemProgress(percentage: Int, eta: Long)
    fun onSendingItemSuccess(item: ConnSendFileItem)

    fun handlePeerDisconnected()
    fun onInitError()

}