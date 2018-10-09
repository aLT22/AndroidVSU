package mn.factory.domain.adzuna.interactor

import io.reactivex.Observable
import mn.factory.domain.adzuna.AdzunaRepository
import mn.factory.domain.adzuna.model.Version
import mn.factory.domain.utils.NoArgumentInteractor
import mn.factory.domain.utils.RxSchedulers

/**
 * Created by Turkin A. on 05/10/2018.
 */
class GetVersionInteractor(
        private val repository: AdzunaRepository,
        private val rxSchedulers: RxSchedulers
) : NoArgumentInteractor<Version> {

    override fun execute(): Observable<Version> =
            repository
                    .getVersion()
                    .subscribeOn(rxSchedulers.io())
                    .observeOn(rxSchedulers.ui())
                    .toObservable()
}