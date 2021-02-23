package tw.north27.coachingapp.ui

import android.os.Bundle
import androidx.navigation.findNavController
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.view.BaseAppCompatActivity
import tw.north27.coachingapp.databinding.ActivityCoachingBinding
import tw.north27.coachingapp.ext.viewBinding

class CoachingActivity : BaseAppCompatActivity() {

    protected val binding by viewBinding(ActivityCoachingBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.frag_container_view).navigateUp()
}