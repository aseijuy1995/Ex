package edu.yujie.navigationex

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import edu.yujie.navigationex.databinding.FragmentFirstBinding
import edu.yujie.navigationex.ex.UserViewModel

class FirstFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        }

        return binding.root
    }
}