package tw.north27.coachingapp.ui.activity

import android.os.Bundle
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.view.BaseAppCompatActivity
import tw.north27.coachingapp.databinding.ActivityCoachingBinding
import tw.north27.coachingapp.ext.viewBinding
import tw.north27.coachingapp.viewModel.StartViewModel

class CoachingActivity : BaseAppCompatActivity() {

    protected val binding by viewBinding(ActivityCoachingBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.frag_container_view).navigateUp()
}