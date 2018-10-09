package mn.factory.androidvsu.di

import mn.factory.androidvsu.model.adzuna.mapper.JobSearchResultToJobPresentationMapper
import mn.factory.androidvsu.model.adzuna.mapper.VersionToVersionPresentationMapper
import mn.factory.androidvsu.ui.adapter.rv.JobsRecyclerAdapter
import mn.factory.androidvsu.ui.main.MainActivityViewModel
import mn.factory.data.api.adzuna.AdzunaRepositoryImpl
import mn.factory.data.api.adzuna.mapper.JobSearchResultNetworkToJobSearchResultMapper
import mn.factory.data.api.adzuna.mapper.VersionNetworkToVersionMapper
import mn.factory.data.utils.RxSchedulersImpl
import mn.factory.domain.adzuna.AdzunaRepository
import mn.factory.domain.adzuna.interactor.GetJobsInteractor
import mn.factory.domain.adzuna.interactor.GetVersionInteractor
import mn.factory.domain.adzuna.interactor.request.GetJobsRequest
import mn.factory.domain.utils.RxSchedulers
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 * Created by Turkin A. on 05/10/2018.
 */

val appModule = module {
    single<RxSchedulers> { RxSchedulersImpl() }
}

val viewModule = module {
    factory { JobsRecyclerAdapter() }
    viewModel { MainActivityViewModel(get(), get()) }
}

val interactorsModule = module {
    factory { GetVersionInteractor(get(), get()) }
    factory { GetJobsInteractor(get(), get()) }
    factory { GetJobsRequest() }
}

val repositoryModule = module {
    single<AdzunaRepository> { AdzunaRepositoryImpl(get(), get(), get()) }
}

val mappersModule = module {
    single { VersionNetworkToVersionMapper() }
    single { VersionToVersionPresentationMapper() }

    single { JobSearchResultNetworkToJobSearchResultMapper() }
    single { JobSearchResultToJobPresentationMapper() }
}
