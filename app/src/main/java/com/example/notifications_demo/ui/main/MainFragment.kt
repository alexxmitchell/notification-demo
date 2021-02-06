package com.example.notifications_demo.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notifications_demo.R
import com.example.notifications_demo.databinding.MainFragmentBinding


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        private const val NOTIFICATION_ID = 0
    }

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding =  MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNotificationChannel(
                getString(R.string.channel_id),
                getString(R.string.channel_name)
        )

        binding.setNotificationButton.setOnClickListener {
            createNotification()
        }
    }

    private fun createNotificationChannel(channelId: String, channelName: String) {

        //creates the channel on API 26 and higher; not supported on those before
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_LOW) //need to set importance

            notificationChannel.description = "Reminder to drink water"

            val notificationManager = requireContext().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
            //created the channel using NotificationManager
        }
    }

    private fun createNotification(){
        //gets an instance of notificationbuilder to build a notification
        val builder = NotificationCompat.Builder(
                requireContext(),
                requireContext().getString(R.string.channel_id)
        ).apply {
            setSmallIcon(R.drawable.baseline_contactless_indigo_a200_24dp)
            setContentTitle(requireContext().getString(R.string.notification_title))
            setContentText(requireContext().getString(R.string.notification_text))
            setAutoCancel(true)
        }
        val notificationManager = requireContext().getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}