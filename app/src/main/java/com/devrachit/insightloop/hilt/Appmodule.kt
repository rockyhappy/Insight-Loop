package com.devrachit.insightloop.hilt

import com.devrachit.core.common.Constants.Companion.BASE_URL
import com.devrachit.insightloop.data.remote.services.ApiServices
import com.devrachit.insightloop.data.repository.ServicesRepositoryImpl
import com.devrachit.insightloop.domain.repository.ServicesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun providesRetrofit() =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun providesApi(retrofit: Retrofit) = retrofit.create(ApiServices::class.java)

    @Provides
    fun providesRepository(ApiServices: ApiServices): ServicesRepository = ServicesRepositoryImpl(ApiServices)

}