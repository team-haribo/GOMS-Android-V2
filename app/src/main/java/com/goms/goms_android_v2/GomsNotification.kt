package com.goms.goms_android_v2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.goms.design_system.R
import com.goms.model.util.ResourceKeys

class GomsNotification : FirebaseMessagingService() {
    companion object {
        private const val CHANNEL_NAME = "GOMS"
        private const val CHANNEL_DESCRIPTION = "GOMS NOTIFICATION"
        private const val CHANNEL_ID = "goms_channel_id"
        private const val GROUP_NAME = "com.goms.goms_android_v2"
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        createNotificationChannel()
        message.notification?.let { sendNotification(it.title, it.body) }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        saveDeviceToken(token)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply { description = CHANNEL_DESCRIPTION }

        getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
    }

    private fun sendNotification(title: String?, body: String?) {
        val messageId = System.currentTimeMillis().toInt()
        val intent = Intent(this, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, messageId, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_goms)
            setContentTitle(title)
            setContentText(body)
            setPriority(NotificationCompat.PRIORITY_HIGH)
            setVibrate(longArrayOf(1_000L, 1_000L, 1_000L))
            setContentIntent(pendingIntent)
            setAutoCancel(true)
            setGroup(GROUP_NAME)
            setGroupSummary(true)
            setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
        }

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager?.notify(messageId, notificationBuilder.build())
    }

    private fun saveDeviceToken(token: String) {
        val deviceTokenSF = getSharedPreferences(ResourceKeys.DEVICE_TOKEN, MODE_PRIVATE)
        deviceTokenSF.edit().putString(ResourceKeys.DEVICE, token).apply()
    }
}