package com.jasonlau.guessdog

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideDogApi(): DogApi {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(DogApi::class.java)
    }

    @Provides
    fun provideGuessDogRepository(
        dogApi: DogApi
    ): GuessDogRepository = GuessDogRepository(dogApi)
}