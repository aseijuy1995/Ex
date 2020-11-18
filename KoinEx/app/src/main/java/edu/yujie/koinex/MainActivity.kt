package edu.yujie.koinex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

//    private val service: ServiceImpl by inject(qualifier = named("default"))
//
////    private val presenter: Presenter by inject {
////        parametersOf(this)
////    }
//
//    private lateinit var componentA: ComponentA
//
//    private val repository: Repository by inject()
//
//    private val presenter: Presenter by lifecycleScope.inject()
//
//    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupKoinFragmentFactory()
        setContentView(R.layout.activity_main)
////        service.doSomething()
////        service.doSomething2()
//
//        repository.show()
//
//        componentA = get<ComponentA>(named("A"))
//
//        val a: A = A()
//        a.inject(a::b, a::c)
//        println("a::b.inject()${a::b.inject()}")
//        println("a::c.inject():${a::c.inject()}")
//        a.show()
//
//        val scopeForA = getKoin().createScope<A>()
//        val b = scopeForA.get<B>()

//        presenter.show()
//        println("presenter:$presenter")
//
//        findViewById<ConstraintLayout>(R.id.cLayout).setOnClickListener {
//            startActivity(Intent(this, Main2Activity::class.java))
//        }
//
//
//        viewModel.data.observe(this) {
//            println("$TAG:viewModel:dtaa = $it")
//        }

        supportFragmentManager.beginTransaction()
            .add(R.id.fcvView, FirstFragment::class.java, null)
            .add(R.id.fcvView2, SecondFragment::class.java, null)
            .commit()
    }


}