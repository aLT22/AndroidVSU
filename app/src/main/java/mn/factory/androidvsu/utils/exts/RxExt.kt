package mn.factory.androidvsu.utils.exts

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.exceptions.CompositeException
import mn.factory.androidvsu.utils.exceptions.RxException

/**
 * Created by Turkin A. on 06/12/2018.
 */

inline fun <T> Observable<T>.extendedErrorMessage(): Observable<T> {
    val breadcrumb = RxException()
    return this.onErrorResumeNext { error: Throwable ->
        throw CompositeException(error, breadcrumb)
    }
}

inline fun <T> Single<T>.extendedErrorMessage(): Single<T> {
    val breadcrumb = RxException()
    return this.onErrorResumeNext { error: Throwable ->
        throw CompositeException(error, breadcrumb)
    }
}

inline fun <T> Maybe<T>.extendedErrorMessage(): Maybe<T> {
    val breadcrumb = RxException()
    return this.onErrorResumeNext { error: Throwable ->
        throw CompositeException(error, breadcrumb)
    }
}

inline fun Completable.extendedErrorMessage(): Completable {
    val breadcrumb = RxException()
    return this.onErrorResumeNext { error: Throwable ->
        throw CompositeException(error, breadcrumb)
    }
}