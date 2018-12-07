package mn.factory.androidvsu.utils.exts

import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation

/**
 * Created by Turkin A. on 09/11/2018.
 */

/**
 * View extension block for animations starts
 * */
private const val DEFAULT_DURATION = 400L
private val DEFAULT_INTERPOLATOR = AccelerateDecelerateInterpolator()

fun View.rotate(start: Int,
                end: Int,
                duration: Long = DEFAULT_DURATION) {
    val rotateAnimator = ObjectAnimator.ofFloat(this, "rotation", start.toFloat(), end.toFloat())
    rotateAnimator.apply {
        setDuration(duration)
        interpolator = DEFAULT_INTERPOLATOR
    }
    rotateAnimator.start()
}

fun View.expand(): Long {
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val targetHeight = measuredHeight

    layoutParams.height = 0
    visibility = View.VISIBLE
    val transformation: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            layoutParams.height =
                    if (interpolatedTime == 1f) {
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    } else {
                        (targetHeight * interpolatedTime).toInt()
                    }
//                    (targetHeight * interpolatedTime).toInt()
            requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    transformation.duration = DEFAULT_DURATION
    startAnimation(transformation)

    return transformation.duration
}

fun View.collapse(): Long {
    val initialHeight = measuredHeight

    val transformation: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f) visibility = View.GONE
            else {
                layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    transformation.duration = DEFAULT_DURATION
    startAnimation(transformation)

    return transformation.duration
}
/**
 * View extension block for animations ends
 * */