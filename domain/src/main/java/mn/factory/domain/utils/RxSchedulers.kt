package mn.factory.domain.utils

import io.reactivex.Scheduler

/**
 * Created by Turkin A. on 05/10/2018.
 */
interface RxSchedulers {

    fun ui(): Scheduler

    fun io(): Scheduler

    fun computation(): Scheduler

}