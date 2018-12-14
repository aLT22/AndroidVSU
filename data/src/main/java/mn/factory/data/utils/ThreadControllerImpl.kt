package mn.factory.data.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import mn.factory.domain.utils.CoroutinesDispatchers
import mn.factory.domain.utils.RxSchedulers

/**
 * Created by Turkin A. on 14/12/2018.
 */
class CoroutinesDispatchersImpl : CoroutinesDispatchers {
    override fun io(): CoroutineDispatcher = Dispatchers.IO

    override fun ui(): MainCoroutineDispatcher = Dispatchers.Main
}

class RxSchedulersImpl : RxSchedulers {
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun io(): Scheduler = Schedulers.io()

    override fun computation(): Scheduler = Schedulers.computation()
}