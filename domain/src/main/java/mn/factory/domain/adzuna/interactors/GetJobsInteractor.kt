package mn.factory.domain.adzuna.interactors

import io.reactivex.Observable
import mn.factory.domain.adzuna.interactors.request.GetJobsRequest
import mn.factory.domain.adzuna.model.JobSearchResults
import mn.factory.domain.adzuna.repositories.JobRepository
import mn.factory.domain.utils.Interactor
import mn.factory.domain.utils.RxSchedulers

/**
 * Created by Turkin A. on 07/10/2018.
 */
class GetJobsInteractor(
        private val repository: JobRepository,
        private val schedulers: RxSchedulers
) : Interactor<GetJobsRequest, JobSearchResults> {

    override fun execute(request: GetJobsRequest): Observable<JobSearchResults> =
            repository
                    .getJobs(request.country, request.page, request.resultsPerPage)
                    .subscribeOn(schedulers.io())
                    .toObservable()
                    .observeOn(schedulers.ui())
}