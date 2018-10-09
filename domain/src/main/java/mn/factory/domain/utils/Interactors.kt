package mn.factory.domain.utils

import io.reactivex.Observable

/**
 * Created by Turkin A. on 05/10/2018.
 */
interface InteractorRequest

interface Interactor<in REQUEST : InteractorRequest, RESULT> {

    fun execute(request: REQUEST): Observable<RESULT>

}

interface NoArgumentInteractor<RESULT> {

    fun execute(): Observable<RESULT>

}