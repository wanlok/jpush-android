package com.acc.pushtestdemo

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cn.jpush.android.api.JPushInterface

class JPushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.extras?.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE)
        val body = intent.extras?.getString(JPushInterface.EXTRA_ALERT)
        val params = intent.extras?.getString(JPushInterface.EXTRA_EXTRA)
        if (title != null && body != null && params != null) {
            if (JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action) {
                if (isAppForground(context)) {
                    ObservableObject.instance().update(JPushNotification(title, body, params))
                } else {
                    val i = Intent(context, MainActivity::class.java) //自定义打开的界面
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    i.putExtra("title", title)
                    i.putExtra("body", body)
                    i.putExtra("params", params)
                    context.startActivity(i)
                }
            }
        }
    }

    fun isAppForground(mContext: Context): Boolean {
        val am =
            mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks = am.getRunningTasks(1)
        if (!tasks.isEmpty()) {
            val topActivity = tasks[0].topActivity
            if (topActivity!!.packageName != mContext.packageName) {
                return false
            }
        }
        return true
    }
}