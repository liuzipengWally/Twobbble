package com.twobbble.tools

import android.net.ParseException
import com.google.gson.JsonParseException
import io.reactivex.Observable
import io.reactivex.functions.Function
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException


/**
 * Created by liuzipeng on 2017/2/20.
 */
class NetExceptionHandler {
    companion object {
        private val UNAUTHORIZED = 401
        private val FORBIDDEN = 403
        private val NOT_FOUND = 404
        private val REQUEST_TIMEOUT = 408
        private val INTERNAL_SERVER_ERROR = 500
        private val BAD_GATEWAY = 502
        private val SERVICE_UNAVAILABLE = 503
        private val GATEWAY_TIMEOUT = 504
    }

    fun handleException(e: Throwable): ResponseException {
        val ex: ResponseException
        if (e is HttpException) {
            ex = ResponseException(e)
            ex.message = when (e.code()) {
                UNAUTHORIZED -> "我们的访问被服务器拒绝啦~(${e.code()})"
                FORBIDDEN -> "服务器资源不可用(${e.code()})"
                NOT_FOUND -> "我们好像迷路了，找不到服务器(${e.code()})"
                REQUEST_TIMEOUT -> "糟糕，我们的请求超时了，请检查网络连接后重试(${e.code()})"
                GATEWAY_TIMEOUT -> "糟糕，我们的请求超时了，请检查网络连接后重试(${e.code()})"
                INTERNAL_SERVER_ERROR -> "服务器正在开小差，请稍后重试(${e.code()})"
                BAD_GATEWAY -> "服务器正在开小差，请稍后重试(${e.code()})"
                SERVICE_UNAVAILABLE -> "服务器可能正在维护，请稍后重试(${e.code()})"
                else -> "网络异常，请检查网络连接后重试(${e.code()})"
            }
            return ex
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException) {
            ex = ResponseException(e)
            ex.message = "数据解析错误，这可能是一个bug，欢迎提交反馈(${ERROR.PARSE_ERROR})"
            return ex
        } else if (e is ConnectException) {
            ex = ResponseException(e)
            ex.message = "连接失败，网络连接可能存在异常，请检查网络后重试(${ERROR.NETWORK_ERROR})"
            return ex
        } else if (e is SSLHandshakeException) {
            ex = ResponseException(e)
            ex.message = "证书验证失败(${ERROR.SSL_ERROR})"
            return ex
        } else if (e is UnknownHostException) {
            ex = ResponseException(e)
            ex.message = "无法连接到服务器，请检查你的网络或稍后重试(${ERROR.HOST_ERROR})"
            return ex
        } else {
            ex = ResponseException(e)
            ex.message = "出现了未知的错误，要不提交一个反馈给作者呗~(${ERROR.UNKNOWN})"
            return ex
        }
    }

    /**
     * 约定异常
     */
    internal object ERROR {
        /**
         * 未知错误
         */
        val UNKNOWN = 1000
        /**
         * 解析错误
         */
        val PARSE_ERROR = 1001
        /**
         * 网络错误
         */
        val NETWORK_ERROR = 1002
        /**
         * 协议出错
         */
        val HTTP_ERROR = 1003

        /**
         * 证书出错
         */
        val SSL_ERROR = 1005

        /**
         * host错误
         */
        val HOST_ERROR = 1006
    }

    class ResponseException(throwable: Throwable) : Exception(throwable) {
        override var message: String? = null
    }

    open class HttpResponseFunc<T> : Function<Throwable, Observable<T>> {
        override fun apply(t: Throwable): Observable<T> {
            return Observable.error(NetExceptionHandler().handleException(t))
        }
    }
}