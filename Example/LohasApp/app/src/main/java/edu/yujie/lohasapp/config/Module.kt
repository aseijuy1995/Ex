package edu.yujie.lohasapp.config

import edu.yujie.lohasapp.IApiService
import edu.yujie.lohasapp.repo.HomeRepository
import edu.yujie.lohasapp.repo.OriginateRepository
import edu.yujie.lohasapp.viewModel.HomeViewModel
import edu.yujie.lohasapp.viewModel.OriginateViewModel
import edu.yujie.retrofitex.OkHttpUtil
import edu.yujie.retrofitex.RetrofitManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val baseUrl = "https://www.letsgoshopping.com.tw/"
const val adImagePath = "ct/adc_images/"

val appModule = module {

    single<OkHttpUtil> { OkHttpUtil.get(androidContext()) }

    single<IApiService> {
        RetrofitManager.init(baseUrl = baseUrl, client = (get() as OkHttpUtil).client)
        RetrofitManager.create<IApiService>()
    }

    single<OriginateRepository> { OriginateRepository(get()) }

    viewModel<OriginateViewModel> { OriginateViewModel(get()) }

    single<HomeRepository> { HomeRepository(get()) }

    viewModel<HomeViewModel> { HomeViewModel(get()) }

}