package mn.factory.androidvsu.di

import mn.factory.androidvsu.model.adzuna.mapper.JobSearchResultToJobPresentationMapper
import mn.factory.androidvsu.model.adzuna.mapper.VersionToVersionPresentationMapper
import mn.factory.androidvsu.ui.adapter.rv.adzuna.jobs.JobsRecyclerAdapter
import mn.factory.androidvsu.ui.main.MainActivityVM
import mn.factory.androidvsu.ui.main.adzuna.jobs.details.JobDetailsVM
import mn.factory.androidvsu.ui.main.adzuna.jobs.list.JobListVM
import mn.factory.data.api.adzuna.mapper.JobSearchResultNetworkToJobSearchResultMapper
import mn.factory.data.api.adzuna.mapper.VersionNetworkToVersionMapper
import mn.factory.data.api.adzuna.repositories.JobRepositoryImpl
import mn.factory.data.api.adzuna.repositories.VersionRepositoryImpl
import mn.factory.data.utils.RxSchedulersImpl
import mn.factory.domain.adzuna.interactors.GetJobsInteractor
import mn.factory.domain.adzuna.interactors.GetVersionInteractor
import mn.factory.domain.adzuna.interactors.request.GetJobsRequest
import mn.factory.domain.adzuna.repositories.JobRepository
import mn.factory.domain.adzuna.repositories.VersionRepository
import mn.factory.domain.utils.RxSchedulers
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

/**
 * Created by Turkin A. on 05/10/2018.
 */

val appModule: Module = module {
    single<RxSchedulers> { RxSchedulersImpl() }
}

val viewModule: Module = module {
    factory { JobsRecyclerAdapter(get()) }

    //Activity VMs
    viewModel { MainActivityVM() }

    //Fragment VMs
    viewModel { JobListVM(get(), get()) }
    viewModel { JobDetailsVM() }
}

val interactorsModule = module {
    factory { GetVersionInteractor(get(), get()) }
    factory { GetJobsInteractor(get(), get()) }
    factory { GetJobsRequest() }
}

val repositoryModule = module {
    single<JobRepository> { JobRepositoryImpl(get(), get()) }
    single<VersionRepository> { VersionRepositoryImpl(get(), get()) }
}

val mappersModule = module {
    single { VersionNetworkToVersionMapper() }
    single { VersionToVersionPresentationMapper() }

    single { JobSearchResultNetworkToJobSearchResultMapper() }
    single { JobSearchResultToJobPresentationMapper() }
}
