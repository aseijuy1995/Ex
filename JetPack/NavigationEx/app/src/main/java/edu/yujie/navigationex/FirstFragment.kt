package edu.yujie.navigationex

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import edu.yujie.navigationex.databinding.FragFirstBinding

/**
 * @author YuJie on 2020/11/13
 * @describe 說明
 * @param 參數
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragFirstBinding.inflate(inflater, container, false)

        //transition-extras
        binding.ivView.setOnClickListener {
            val extras =
                FragmentNavigatorExtras(binding.ivView to "extras_iv_view")

            it.findNavController().navigate(
                FirstFragmentDirections.actionFragFirstToFragSecond(
                    2
                ), extras
            )
        }

        binding.textView.setOnClickListener {
            findNavController().navigate(FirstFragmentDirections.actionFragFirstToFragSecond(2))
        }

        binding.tvDeepLinkRequest.setOnClickListener {
            val value = "Deep Link:frag_first to frag_third:Request"
            val uri = "https://www.yujie1995.com/$value".toUri()
            val request = NavDeepLinkRequest.Builder.fromUri(uri).build()
            findNavController().navigate(request)
        }

        //can not span nav_graph
        binding.tvDeepLinkBuilder.setOnClickListener {
            val value = "Deep Link:frag_first to frag_third:Builder"
            val intent = NavDeepLinkBuilder(requireContext())
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.frag_second)
                .setArguments(bundleOf("value" to value))
                .createPendingIntent()
            createNotification(intent)
        }

        binding.tvDeepLinkExplicit.setOnClickListener {
            val intent = Navigation.findNavController(it)
                .createDeepLink()
                .setDestination(R.id.nav_graph2)
                .setArguments(bundleOf("value" to "Deep Link:frag_first to frag_third:Explicit"))
                .createPendingIntent()
            createNotification(intent)
        }

        val appBarConfiguration = AppBarConfiguration(findNavController().graph)


        val dispatcher = requireActivity().onBackPressedDispatcher
        val callback = dispatcher.addCallback(viewLifecycleOwner) {
            println("FirstFragment:addCallback()")
            findNavController().apply {
                navigate(
                    R.id.frag_dialog,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.frag_dialog, true).build()
                )
            }
        }

        return binding.root
    }

    private fun createNotification(intent: PendingIntent) {
        val channelId = "1"
        val manager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    "Deep Link",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        val builder = NotificationCompat.Builder(requireContext(), channelId)
            .setContentTitle("Title")
            .setContentText("Text")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(intent)
            .setAutoCancel(true)
        manager.notify(0, builder.build())
    }

}

var ENABLED = true