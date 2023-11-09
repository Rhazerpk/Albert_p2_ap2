package com.kotlin.albert_p2_ap2.di

import com.kotlin.albert_p2_ap2.data.remote.GastosApi
import com.kotlin.albert_p2_ap2.data.repository.GastosRepository
import com.kotlin.albert_p2_ap2.util.DateAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideGastosApi(moshi: Moshi): GastosApi {
        val moshiWithDateAdapter = moshi.newBuilder()
            .add(DateAdapter())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://sag-api.azurewebsites.net")
            .addConverterFactory(MoshiConverterFactory.create(moshiWithDateAdapter))
            .build()
            .create(GastosApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGastosRepository(gastosApi: GastosApi): GastosRepository {
        return GastosRepository(gastosApi)
    }


}