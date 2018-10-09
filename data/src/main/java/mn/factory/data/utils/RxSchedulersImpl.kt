package mn.factory.data.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mn.factory.domain.utils.RxSchedulers

/**
 * Created by Turkin A. on 05/10/2018.
 */
class RxSchedulersImpl : RxSchedulers {
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun io(): Scheduler = Schedulers.io()

    override fun computation(): Scheduler = Schedulers.computation()
}