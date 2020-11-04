package de.example.crudapp.di

import dagger.Component
import de.example.crudapp.repository.ProductRepositoryImpl
import de.example.crudapp.viewmodel.ProductViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(repository: ProductRepositoryImpl)

    fun inject(viewModel: ProductViewModel)

}