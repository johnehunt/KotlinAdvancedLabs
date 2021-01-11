package com.coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

suspend fun sendMessage(channel: Channel<String>, message: String) {
    repeat (10) {
        delay(500)
        val timeStamp = SimpleDateFormat("HH-mm-ss").format(Date())
        val nextMessage = "$message - $timeStamp"
        channel.send(nextMessage)
    }
}

suspend fun messageConsumer(msgChannel: Channel<String>) {
    while(true) { // receive three
        println("message received: ${msgChannel.receive()}")
    }
}

fun main() {
    println("Starting coroutine example")

    val msgChannel = Channel<String>()

    GlobalScope.launch {
        GlobalScope.launch { sendMessage(msgChannel, "Hello World") }
        GlobalScope.launch { messageConsumer(msgChannel) }
    }

    println("After launching coroutines")
    println("Waiting for task - press enter to continue:")
    readLine()
    println("Done")
}