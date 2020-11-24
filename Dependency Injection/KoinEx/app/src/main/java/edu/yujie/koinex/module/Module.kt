package edu.yujie.koinex.module

import edu.yujie.koinex.*
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module

val appModule = module {
//        single<Service> { ServiceImpl() }

//        single { Controller(get()) }

    single<Service> { ServiceImpl() } bind Service::class

    single { ServiceImpl() } binds arrayOf(Service::class, Service2::class)

    single(named("default")) { ServiceImpl() }
    single(named("test")) { ServiceImpl() }

//        factory { (view: View) -> Presenter(view) }

//        factory { Presenter() }

//        single { Presenter() }

    scope<MainActivity> {
        scoped {
            Presenter()
        }
    }

    single<Service>(
        override = true
//            qualifier = named("testServiceImpl"),
//            createdAtStart = true
    ) { TestServiceImp() } bind Service::class

    single(named("A")) { ComponentA() }
    single { ComponentB(get()) }

    single { Repository(get()) }
    single<DataSource> { LocalDataSource() }
//        single<DataSource> { RemoteDataSource() }

    single { MyService() }

    single { B() }
    single { C() }

    scope<A> {
        scoped {
            B()
        }
    }

    single { DetailRepository() }
    viewModel { DetailViewModel(get()) }

//        single {
//            MyPresenter(androidContext().resources.getString(R.string.app_name))
//        }

    scope<MainActivity> {
        fragment { FirstFragment() }
        fragment { SecondFragment() }
    }


}
