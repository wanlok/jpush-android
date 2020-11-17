package com.acc.pushtestdemo

import java.util.*

class ObservableObject: Observable() {

    fun update(notification: JPushNotification) {
        setChanged()
        notifyObservers(notification)
    }

    companion object {
        @Volatile
        private var instance: ObservableObject? = null

        fun instance(): ObservableObject {
            val instance = instance
            return if (instance == null) {
                val observableObject = ObservableObject()
                this.instance = observableObject
                observableObject
            } else {
                instance
            }
        }
    }
}
