package edu.yujie.navigationex

import android.app.NotificationChannel
import android.app.NotificationManager
<<<<<<< HEAD
import android.app.PendingIntent
import android.content.Context
import android.os.Build
=======
import android.content.Context.NOTIFICATION_SERVICE
>>>>>>> 5353c08b8648ab256f1ef3dde1fdfbf3127f072e
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
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

=======
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import edu.yujie.navigationex.databinding.FragmentFirstBinding
import edu.yujie.navigationex.ex.UserViewModel

class FirstFragment : Fragment() {


>>>>>>> 5353c08b8648ab256f1ef3dde1fdfbf3127f072e
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
<<<<<<< HEAD
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
=======
        val binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.tvText.setOnClickListener {
            val direction = FirstFragmentDirections.actionFragmentFirstToFragmentSecond()

            //NavHostFragment
//            findNavController().navigate(direction)
            findNavController().navigate(R.id.fragment_second, bundleOf("arg" to "20"))

//            //FragmentControllerView
//            val nacFragment =
//                parentFragmentManager.findFragmentById(R.id.fcv_view) as NavHostFragment
//            val navController = nacFragment.navController
//            navController.navigate(direction)
        }

        //explicit deep link
        binding.btnNotification.setOnClickListener {
            val intent = NavDeepLinkBuilder(requireContext())
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.fragment_second)
//                .setArguments(args)
                .createPendingIntent()

            val builder = NotificationCompat.Builder(requireContext())
                .setContentTitle("Title")
                .setContentText("Context")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(intent)
                .setChannelId("1")
                .setAutoCancel(true)
            (requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                .apply {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        createNotificationChannel(
                            NotificationChannel(
                                "1",
                                "name",
                                NotificationManager.IMPORTANCE_HIGH
                            )
                        )
                    }
                    notify(1, builder.build())
                }
        }

        binding.btnCondition.setOnClickListener {
            findNavController().navigate(FirstFragmentDirections.actionFragmentFirstToFragmentProfile())
>>>>>>> 5353c08b8648ab256f1ef3dde1fdfbf3127f072e
        }

        return binding.root
    }
<<<<<<< HEAD

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
=======
}
>>>>>>> 5353c08b8648ab256f1ef3dde1fdfbf3127f072e
