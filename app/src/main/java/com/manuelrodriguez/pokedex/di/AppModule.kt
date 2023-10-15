package com.manuelrodriguez.pokedex.di

import com.google.gson.Gson
import com.manuelrodriguez.pokedex.data.local.LocalDataSource
import com.manuelrodriguez.pokedex.data.local.PokedexListEntryDao
import com.manuelrodriguez.pokedex.data.remote.PokemonServiceApi
import com.manuelrodriguez.pokedex.data.remote.RemoteDatasource
import com.manuelrodriguez.pokedex.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRemoteDatasource(api: PokemonServiceApi) = RemoteDatasource(api)

    @Singleton
    @Provides
    fun provideLocalDataSource(dao: PokedexListEntryDao) = LocalDataSource(dao)

    @Singleton
    @Provides
    fun providePokemonServiceApi(): PokemonServiceApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokemonServiceApi::class.java)

    }
}
