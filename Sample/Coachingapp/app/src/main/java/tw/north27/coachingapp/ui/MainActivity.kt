package tw.north27.coachingapp.ui

import androidx.navigation.findNavController
import com.yujie.basemodule.BaseAppCompatActivity
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.ActivityMainBinding

class MainActivity : BaseAppCompatActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || findNavController(R.id.frag_container_view).navigateUp()
    }

}
