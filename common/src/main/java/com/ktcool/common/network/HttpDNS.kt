package com.ktcool.common.network

import okhttp3.Dns
import java.net.InetAddress
import java.net.UnknownHostException

/**
 * 网络优化-DNS优化
 *
 * 一个 Http 请求在建立 Tcp 连接的过程中，肯定会产生一次 DNS，那么我们是不是可以通过内存缓存的方式，
 * 通过一个 HashMap 持有这个 Host 的 IP，当下次发起 Tcp 连接的时候，就可以用直接用内存中的这个 Ip，
 * 而不需要再去走一遍 Dns 服务
 *
 * Okhttp在封装的时候就已经考虑到这个部分了，其内部提供了Dns的接口，可以让外部在构造Client的时候传入
 */
class HttpDNS : Dns {
    private val cacheHost = hashMapOf<String, InetAddress>()

    override fun lookup(hostname: String): MutableList<InetAddress> {
        if (cacheHost.containsKey(hostname)) {
            cacheHost[hostname]?.apply {
                return mutableListOf(this)
            }
        }

        return try {
            InetAddress.getAllByName(hostname)?.first()?.apply {
                cacheHost[hostname] = this
            }
            mutableListOf(*InetAddress.getAllByName(hostname))
        } catch (e: NullPointerException) {
            val unknownHostException =
                UnknownHostException("Broken system behaviour for dns lookup of $hostname")
            unknownHostException.initCause(e)
            throw unknownHostException
        }
    }
}