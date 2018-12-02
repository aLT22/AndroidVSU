package mn.factory.androidvsu.utils.listeners

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import mn.factory.androidvsu.utils.exceptions.InvalidLayoutManagerException

/**
 * Created by Turkin A. on 11/10/2018.
 */
abstract class EndlessScrollListener : RecyclerView.OnScrollListener() {

    var visibleThreshold: Int = 10
    var currentPage: Int = 1
    var previousTotalItemCount: Int = 0
    var startingPageIndex: Int = 1

    val loading: MutableLiveData<Boolean> = MutableLiveData()

    var mLayoutManager: RecyclerView.LayoutManager? = null

    init {
        loading.value = true
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition: Int
        val totalItemCount = mLayoutManager?.itemCount ?: 0

        lastVisibleItemPosition = when (mLayoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                // get maximum element within the list
                getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
            is LinearLayoutManager -> (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            else -> {
                throw InvalidLayoutManagerException(InvalidLayoutManagerException.LAYOUT_MANAGER_IS_NULL_MESSAGE)
            }
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too

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

    fun reset() {
        this.visibleThreshold = 10
        this.currentPage = 1
        this.previousTotalItemCount = 0
        this.startingPageIndex = 1

        this.loading.postValue(true)
    }
}