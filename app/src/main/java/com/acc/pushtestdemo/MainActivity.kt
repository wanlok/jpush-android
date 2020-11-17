package com.acc.pushtestdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.jpush.android.api.JPushInterface
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), Observer {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)

        ObservableObject.instance().addObserver(this)
    }

    override fun onResume() {
        super.onResume()
        val title = intent.getStringExtra("title")
        val body = intent.getStringExtra("body")
        val params = intent.getStringExtra("params")
        if (title != null && body != null && params != null) {
            tv_text.text = "${title}\n\n${body}\n\n${params}"
        }
    }

    override fun update(o: Observable?, arg: Any?) {
        val notification = arg as JPushNotification
        if (notification != null) {
            val title = notification.title
            val body = notification.body
            val params = notification.params
            if (title != null && body != null && params != null) {
                tv_text.text = "${title}\n\n${body}\n\n${params}"
            }
        }
    }
}