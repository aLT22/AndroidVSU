package mn.factory.androidvsu.utils.listener

import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * Created by Turkin A. on 11/10/2018.
 */
abstract class EndlessScrollListener() : RecyclerView.OnScrollListener() {

    var visibleThreshold: Int = 5
    var currentPage: Int = 1
    var previousTotalItemCount = 0
    var startingPageIndex = 1

    val loading: MutableLiveData<Boolean> = MutableLiveData()

    var mLayoutManager: RecyclerView.LayoutManager? = null

    init {
        loading.postValue(true)
    }

    constructor(layoutManager: RecyclerView.LayoutManager) : this() {
        this.mLayoutManager = layoutManager
    }

    constructor(linearLayoutManager: LinearLayoutManager) : this() {
        this.mLayoutManager = linearLayoutManager
    }

    constructor(gridLayoutManager: GridLayoutManager) : this() {
        this.mLayoutManager = gridLayoutManager
    }

    constructor(staggeredGridLayoutManager: StaggeredGridLayoutManager) : this() {
        this.mLayoutManager = staggeredGridLayoutManager
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager?.itemCount ?: 0

        if (mLayoutManager is StaggeredGridLayoutManager) {
            val lastVisibleItemPositions = (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
        } else if (mLayoutManager is GridLayoutManager) {
            lastVisibleItemPosition = (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
        } else if (mLayoutManager is LinearLayoutManager) {
            lastVisibleItemPosition = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading.postValue(true)
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading.value!! && totalItemCount > previousTotalItemCount) {
            loading.postValue(false)
            previousTotalItemCount = totalItemCount
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading.value!! && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, recyclerView)
            loading.postValue(true)
        }
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int, recyclerView: RecyclerView)

    fun resetState() {
        this.currentPage = startingPageIndex
        this.previousTotalItemCount = 0
        this.loading.postValue(true)
    }

    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }
}