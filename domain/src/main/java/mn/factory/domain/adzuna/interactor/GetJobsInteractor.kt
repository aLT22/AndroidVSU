package mn.factory.domain.adzuna.interactor

import io.reactivex.Observable
import mn.factory.domain.adzuna.AdzunaRepository
import mn.factory.domain.adzuna.model.JobSearchResults
import mn.factory.domain.adzuna.interactor.request.GetJobsRequest
import mn.factory.domain.utils.Interactor
import mn.factory.domain.utils.RxSchedulers

/**
 * Created by Turkin A. on 07/10/2018.
 */
class GetJobsInteractor(
        private val repository: AdzunaRepository,
        private val schedulers: RxSchedulers
) : Interactor<GetJobsRequest, JobSearchResults> {

    override fun execute(request: GetJobsRequest): Observable<JobSearchResults> =
            repository
                    .getJobs(request.country, request.page, request.resultsPerPage)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .toObservable()
}