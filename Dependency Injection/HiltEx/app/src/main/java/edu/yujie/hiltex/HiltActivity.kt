package edu.yujie.hiltex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * * What went wrong:
 * Execution failed for task ':app:kaptDebugKotlin'.
 * > A failure occurred while executing org.jetbrains.kotlin.gradle.internal.KaptExecution
 * > java.lang.reflect.InvocationTargetException (no error message)
 *
 * gradlew clean > gradle assembleDebug
 * */
@AndroidEntryPoint
class HiltActivity : AppCompatActivity() {
//    //3rd
//    @Inject
//    lateinit var service: ApiService

//    //viewModel
//    private val viewModel: HiltViewModel by viewModels()

//    //viewModel & room
//    private val viewModel: HiltViewModel by viewModels()

//    //binds
//    @Inject
//    lateinit var webService: WorkService

//    //qualifier
//    @Inject
//    lateinit var repo: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilt)

//        supportFragmentManager.beginTransaction()
//            .add(R.id.fcvView, HiltFragment::class.java, null)
//            .commit()

//        //3rd
//        lifecycleScope.launch {
//            val appResult = service.postAppResult()
//            val msg =
//                "AppResult:\napplestoreurl = ${appResult.applestoreurl}\nresult = ${appResult.result}"
//            findViewById<TextView>(R.id.textView).text = msg
//        }

//        //viewModel
//        viewModel.appResult.observe(this) {
//            val msg =
//                "AppResult:\napplestoreurl = ${it.applestoreurl}\nresult = ${it.result}"
//            findViewById<TextView>(R.id.textView).text = msg
//        }

//        //viewModel & room
//        viewModel.getAppResultFromDatabase().observe(this) {
//            it?.let {
//                val msg = "AppResult:\napplestoreurl = ${it.applestoreurl}\nresult = ${it.result}"
//                findViewById<TextView>(R.id.textView).text = msg
//            }
//        }
//        lifecycleScope.launch(Dispatchers.IO) {
//            delay(2000L)
//            viewModel.insertAppResult()
//        }

//        //binds
//        webService.init()

//        //qualifier
//        repo.showTasks()

    }
}