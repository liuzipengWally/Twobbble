package com.twobbble.tools.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by liuzipeng on 2017/2/15.
 * 这个代理的用处是为了保护被代理对象，不会重复被实例化
 */
class NotNullSingleValueVar<T>() : ReadWriteProperty<Any?, T> {
    private var value: T? = null

    //Getter函数 如果已经被初始化，则会返回一个值，否则会抛异常。
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("this object not initialized")
    }

    //Setter函数 如果仍然是null，则赋值，否则会抛异常。
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value
        else throw IllegalStateException("object already initialized")
    }
}