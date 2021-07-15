package tw.north27.coachingapp.ui.launch2.ask

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.view.clicks
import com.tbruyelle.rxpermissions3.RxPermissions
import com.yujie.core_lib.adapter.bindImg
import com.yujie.core_lib.base.BaseBottomSheetDialogFragment
import com.yujie.core_lib.ext.observe
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentAskRoomModeDialogBinding
import tw.north27.coachingapp.model.SendMode
import java.util.concurrent.TimeUnit

class AskRoomModeDialogFragment : BaseBottomSheetDialogFragment<FragmentAskRoomModeDialogBinding>(R.layout.fragment_ask_room_mode_dialog) {

    private lateinit var rxPermission: RxPermissions

    companion object {
        val REQUEST_KEY_ASK_ROOM_MODE = "REQUEST_KEY_ASK_ROOM_MODE"

        val KEY_SEND_MODE = "KEY_SEND_MODE"
    }

    override val viewBind: (View) -> FragmentAskRoomModeDialogBinding
        get() = FragmentAskRoomModeDialogBinding::bind

    override fun getTheme(): Int {
        return R.style.BottomSheetTheme_TopRadius15
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            itemCamera.apply {
                ivIcon.bindImg(resId = R.drawable.ic_baseline_photo_camera_24_red)
                tvText.text = getString(R.string.camera)
            }

            itemAlbum.apply {
                ivIcon.bindImg(resId = R.drawable.ic_baseline_photo_24_orange)
                tvText.text = getString(R.string.album)
            }

            itemRecording.apply {
                ivIcon.bindImg(resId = R.drawable.ic_baseline_mic_none_24_yellow)
                tvText.text = getString(R.string.recording)
            }

            itemAudio.apply {
                ivIcon.bindImg(resId = R.drawable.ic_baseline_queue_music_24_green)
                tvText.text = getString(R.string.audio)
            }

            itemVideo.apply {
                ivIcon.bindImg(resId = R.drawable.ic_baseline_videocam_24_blue)
                tvText.text = getString(R.string.video)
            }

            itemFilm.apply {
                ivIcon.bindImg(resId = R.drawable.ic_baseline_local_movies_24_blue)
                tvText.text = getString(R.string.film)
            }
        }
        rxPermission = RxPermissions(this)

        //camera
        binding.itemCamera.root.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).compose(
            rxPermission.ensure(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
        ).observe(viewLifecycleOwner) {
            if (it) {
                setFragmentResult(
                    REQUEST_KEY_ASK_ROOM_MODE, bundleOf(KEY_SEND_MODE to SendMode.CAMERA)
                )
                findNavController().navigateUp()
            } else {
                Toast.makeText(cxt, getString(R.string.permission_deny), Toast.LENGTH_SHORT).show()
            }
        }

        //album
        binding.itemAlbum.root.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).compose(
            rxPermission.ensure(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).observe(viewLifecycleOwner) {
            if (it) {
                setFragmentResult(
                    REQUEST_KEY_ASK_ROOM_MODE, bundleOf(KEY_SEND_MODE to SendMode.ALBUM)
                )
                findNavController().navigateUp()
            } else {
                Toast.makeText(cxt, getString(R.string.permission_deny), Toast.LENGTH_SHORT).show()
            }
        }

        //recording
        binding.itemRecording.root.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).compose(
            rxPermission.ensure(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).observe(viewLifecycleOwner) {
            if (it) {
                setFragmentResult(
                    REQUEST_KEY_ASK_ROOM_MODE, bundleOf(KEY_SEND_MODE to SendMode.RECORDING)
                )
                findNavController().navigateUp()
            } else {
                Toast.makeText(cxt, getString(R.string.permission_deny), Toast.LENGTH_SHORT).show()
            }
        }

        //audio
        binding.itemAudio.root.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).compose(
            rxPermission.ensure(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).observe(viewLifecycleOwner) {
            if (it) {
                setFragmentResult(
                    REQUEST_KEY_ASK_ROOM_MODE, bundleOf(KEY_SEND_MODE to SendMode.AUDIO)
                )
                findNavController().navigateUp()
            } else {
                Toast.makeText(cxt, getString(R.string.permission_deny), Toast.LENGTH_SHORT).show()
            }
        }

        //video
        binding.itemVideo.root.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).compose(
            rxPermission.ensure(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).observe(viewLifecycleOwner) {
            if (it) {
                setFragmentResult(
                    REQUEST_KEY_ASK_ROOM_MODE, bundleOf(KEY_SEND_MODE to SendMode.VIDEO)
                )
                findNavController().navigateUp()
            } else {
                Toast.makeText(cxt, getString(R.string.permission_deny), Toast.LENGTH_SHORT).show()
            }
        }

        //film
        binding.itemFilm.root.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).compose(
            rxPermission.ensure(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).observe(viewLifecycleOwner) {
            if (it) {
                setFragmentResult(
                    REQUEST_KEY_ASK_ROOM_MODE, bundleOf(KEY_SEND_MODE to SendMode.FILM)
                )
                findNavController().navigateUp()
            } else {
                Toast.makeText(cxt, getString(R.string.permission_deny), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
