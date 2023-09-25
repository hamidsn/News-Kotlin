package au.com.news.coding.di

import au.com.news.coding.data.repository.FakeNewsRepositoryImpl
import au.com.news.coding.domain.repositories.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * This class should only be referenced by generated code.
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class TestRepositoriesModule {

    @Binds
    abstract fun bindCharacterRepository(impl: FakeNewsRepositoryImpl): NewsRepository
}