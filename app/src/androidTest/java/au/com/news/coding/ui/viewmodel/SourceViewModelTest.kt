package au.com.news.coding.ui.viewmodel

import au.com.news.coding.data.FakeNewsDataBase
import au.com.news.coding.data.repository.FakeNewsRepositoryImpl
import au.com.news.coding.di.TestRemoteModule
import au.com.news.coding.domain.repositories.LocalSourceDatabaseRepository
import au.com.news.coding.ui.rule.TestDispatcherRule
import au.com.news.coding.ui.view.UiHeadlineStates
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class SourceViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    // Use a fake repository to be injected into the viewmodel
    private lateinit var remoteRepository: FakeNewsRepositoryImpl
    private lateinit var localDbRepository: LocalSourceDatabaseRepository
    private lateinit var sourceViewModel: SourceViewModel

    @Before
    fun setupViewModel() {

        remoteRepository = FakeNewsRepositoryImpl(TestRemoteModule.provideNewsApi())
        localDbRepository = FakeNewsDataBase.provideDataBaseDao()
        sourceViewModel = SourceViewModel(
            remoteRepository,
            localDbRepository
        )
    }

    @Test
    fun test_get_headlines() {
        sourceViewModel.getHeadlines()
        Assert.assertEquals(
            sourceViewModel.uiHeadlinesStates.value,
            UiHeadlineStates.HeadlinesLoadingState
        )
    }

}