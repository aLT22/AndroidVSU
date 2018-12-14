package mn.factory.domain.utils

import io.reactivex.Scheduler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

/**
 * Created by Turkin A. on 14/12/2018.
 */
interface RxSchedulers {

    fun ui(): Scheduler

    fun io(): Scheduler

    fun computation(): Scheduler

}

interface CoroutinesDispatchers {

    fun ui(): MainCoroutineDispatcher

    fun io(): CoroutineDispatcher

}