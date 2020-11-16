package edu.yujie.navigationex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.activity
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import edu.yujie.navigationex.databinding.ActivityKotlinDslBinding

class KotlinDslActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityKotlinDslBinding>(
            this,
            R.layout.activity_kotlin_dsl
        )

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_view) as NavHostFragment

        navHostFragment.navController.apply {
            graph = createGraph(nav_graph.id, nav_graph.kotlin_dsl.id) {
                fragment<KotlinDslFragment>(nav_graph.kotlin_dsl.id) {
                    label = "kotlin_dsl"
                    action(nav_graph.kotlin_dsl.action.frag_kotlin_dsl_to_frag_kotlin_dsl2) {
                        destinationId = nav_graph.kotlin_dsl2.id
                        navOptions {
                            anim {
                                enter = R.anim.nav_default_enter_anim
                                exit = R.anim.nav_default_exit_anim
                                popEnter = R.anim.nav_default_pop_enter_anim
                                popExit = R.anim.nav_default_pop_exit_anim
                            }
                        }
                    }
                }
                fragment<KotlinDsl2Fragment>(nav_graph.kotlin_dsl2.id) {
                    label = "kotlin_dsl2"
                    action(nav_graph.kotlin_dsl2.action.frag_kotlin_dsl2_to_frag_kotlin_dsl) {
                        destinationId = nav_graph.kotlin_dsl.id
                    }
                    action(nav_graph.kotlin_dsl2.action.frag_kotlin_dsl2_to_act_main) {
                        destinationId = nav_graph.main.id
                        navOptions {
                            popUpTo(R.id.nav_graph) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                }
                activity(nav_graph.main.id) {
                    label = "main"
                    activityClass = MainActivity::class
                }
            }
        }

    }

    private fun navigateToPlant(actionId: Int) {
        findNavController(R.id.fcv_view).navigate(actionId)
    }
}