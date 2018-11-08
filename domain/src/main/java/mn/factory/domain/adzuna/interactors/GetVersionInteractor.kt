package mn.factory.domain.adzuna.interactors

import io.reactivex.Observable
import mn.factory.domain.adzuna.model.Version
import mn.factory.domain.adzuna.repositories.VersionRepository
import mn.factory.domain.utils.NoArgumentInteractor
import mn.factory.domain.utils.RxSchedulers

/**
 * Created by Turkin A. on 05/10/2018.
 */
class GetVersionInteractor(
        private val repository: VersionRepository,
        private val rxSchedulers: RxSchedulers
) : NoArgumentInteractor<Version> {

    override fun execute(): Observable<Version> =
            repository
                    .getVersion()
                    .subscribeOn(rxSchedulers.io())
                    .toObservable()
                    .observeOn(rxSchedulers.ui())
}