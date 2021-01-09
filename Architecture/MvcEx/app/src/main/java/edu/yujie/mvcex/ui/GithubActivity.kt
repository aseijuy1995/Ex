package edu.yujie.mvcex.ui

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import edu.yujie.mvcex.R
import edu.yujie.mvcex.databinding.ActivityGithubBinding

class GithubActivity : BaseActivity<ActivityGithubBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_github


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.switchMaterial.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            if (b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.frag_container_view).navigateUp()
    }

}