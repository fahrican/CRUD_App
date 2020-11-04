package de.example.crudapp.di

import dagger.Module
import dagger.Provides
import de.example.crudapp.data.networking.FlaskProductRestApi
import de.example.crudapp.data.networking.FlaskProductRestApiService
import de.example.crudapp.repository.ProductRepository
import de.example.crudapp.repository.ProductRepositoryImpl
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @Provides
    fun provideApi(): FlaskProductRestApi = FlaskProductRestApiService.getClient()

    @Provides
    fun provideRepository(): ProductRepository = ProductRepositoryImpl()

}