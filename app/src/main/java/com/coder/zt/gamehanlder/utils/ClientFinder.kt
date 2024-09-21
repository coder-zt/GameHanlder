package com.coder.zt.gamehanlder.utils

import android.util.Log
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.*
import java.util.*

object ClientFinder {
    private const val TAG = "ClientFinder"
    val baseIp = StringBuilder()
    fun initBaseIp(ip: String): Int {
        val splitNum = ip.split(".")
        baseIp.clear()
        baseIp.run {
            append(splitNum[0])
            append(".")
            append(splitNum[1])
            append(".")
            append(splitNum[2])
            append(".")
        }
        return splitNum.last().toInt()
    }

    private suspend fun getLocalHostIP(): String {
        return withContext(Dispatchers.IO) {
            var hostIp = ""
            val nis: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (nis.hasMoreElements()) {
                val ni: NetworkInterface = nis.nextElement()
                val ias: Enumeration<InetAddress> = ni.inetAddresses
                while (ias.hasMoreElements()) {
                    val ia: InetAddress = ias.nextElement()
                    if (ia is Inet6Address) {
                        continue
                    }
                    val ip = ia.hostAddress
                    if ("127.0.0.1" != ip) {
                        hostIp = ip
                        return@withContext hostIp
                    }
                }
            }
            hostIp
        }
    }


    suspend fun findTargetDevices(): List<String> {
        return withContext(Dispatchers.IO) {
            val list = mutableListOf<String>()
            val hostIp = getLocalHostIP()
            println("===========>hostIp: $hostIp")
            val hostFinNum = initBaseIp(hostIp)
            val awaitList = mutableListOf<Deferred<String>>()
            for (i in 0..255) {
                if (i != hostFinNum) {
                    val testIp = "$baseIp$i"
                    val a = async { testClientIP(testIp, 80) }
                    awaitList.add(a)
                }
            }
            awaitList.forEach {
                val res = it.await()
                if (res.isNotEmpty()) {
                    list.add(res)
                }
            }
            list
        }
    }

    private fun testClientIP(clientIP: String, port: Int): String {
        println("=====> $clientIP test start  ${Thread.currentThread().name}")
        val socket = Socket()
        val socketAddress: SocketAddress = InetSocketAddress(clientIP, port)
        try {
            socket.connect(socketAddress, 50)
            socket.close()
            println("=====> $clientIP success ${Thread.currentThread().name}")
            return clientIP
        } catch (e: IOException) {
//            e.printStackTrace()
            println("=====> $clientIP fail ${Thread.currentThread().name}l")
        }
        return ""
    }
}