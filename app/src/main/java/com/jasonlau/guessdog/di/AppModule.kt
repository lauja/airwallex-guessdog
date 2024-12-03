package com.jasonlau.guessdog.di

import com.google.gson.Gson
import com.jasonlau.guessdog.repository.DogApi
import com.jasonlau.guessdog.repository.GuessDogRepository
import com.jasonlau.guessdog.repository.GuessDogRepositoryImpl
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
    ): GuessDogRepository = GuessDogRepositoryImpl(dogApi)
}