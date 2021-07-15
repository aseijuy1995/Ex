package com.yujie.utilmodule.http.retrofit

import retrofit2.Retrofit

object RetrofitManager {

		private var retrofit: Retrofit? = null

		fun get(config: RetrofitConfig): Entity {
				retrofit = Retrofit.Builder().configRetrofit(config = config)
				return Entity(retrofit = retrofit!!)
		}

		fun reGet(config: RetrofitConfig): Entity {
				val retrofit = retrofit?.newBuilder()?.configRetrofit(config = config)
						?: throw RuntimeException("Retrofit is not initialized")
				return Entity(retrofit = retrofit)
		}

		private fun Retrofit.Builder.configRetrofit(config: RetrofitConfig): Retrofit {
				return baseUrl(config.baseUrl)
						.client(config.entity.client)
						.addConverterFactory(config.converterFactory.factory)
						.apply {
								if (config.callAdapterFactory?.factory != null) addCallAdapterFactory(config.callAdapterFactory.factory)
						}
						.build()
		}

		class Entity(val retrofit: Retrofit) {

				inline fun <reified T> create(): T {
						return retrofit.create(T::class.java)
								?: throw RuntimeException("Retrofit is not initialized")
				}
		}
}