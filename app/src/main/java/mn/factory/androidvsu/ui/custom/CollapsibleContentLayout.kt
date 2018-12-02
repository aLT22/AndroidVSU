package mn.factory.androidvsu.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import mn.factory.androidvsu.R
import mn.factory.androidvsu.databinding.CustomCollapsibleContentLayoutBinding
import mn.factory.androidvsu.utils.exts.collapse
import mn.factory.androidvsu.utils.exts.expand
import mn.factory.androidvsu.utils.exts.rotate

/**
 * Created by Turkin A. on 09/11/2018.
 */
open class CollapsibleContentLayout : FrameLayout, ViewTreeObserver.OnGlobalLayoutListener {

    private var mPadding = 0
    private var mTitle = ""
    private var mIsExpanded = false

    private lateinit var mBinding: CustomCollapsibleContentLayoutBinding

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    @SuppressLint("Recycle")
    constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attributeSet, defStyleAttr) {
        if (isInEditMode) return
        val typedAttributesArray = context.obtainStyledAttributes(attributeSet, R.styleable.CollapsibleContentLayout) ?: return

        mIsExpanded = typedAttributesArray.getBoolean(R.styleable.CollapsibleContentLayout_expanded, false)
        mTitle = typedAttributesArray.getString(R.styleable.CollapsibleContentLayout_title) ?: ""
        mPadding = typedAttributesArray
                .getDimension(R.styleable.CollapsibleContentLayout_contentPadding, context.resources.getDimension(R.dimen.defaultPadding))
                .toInt()

        typedAttributesArray.recycle()

        viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    private fun init() {
        mBinding = DataBindingUtil
                .inflate(LayoutInflater.from(context), R.layout.custom_collapsible_content_layout, this, false)

        mBinding.title.ellipsize = TextUtils.TruncateAt.MARQUEE
        mBinding.title.marqueeRepeatLimit = MARQUEE_REPEAT_LIMIT_INFINITY
        mBinding.title.setSingleLine(true)
        mBinding.title.isSelected = true
        mBinding.title.setTypeface(null, Typeface.BOLD)

        mBinding.toggle.setOnClickListener { runAnimation() }
        mBinding.title.setOnClickListener { runAnimation() }
        mBinding.toggleContainer.setOnClickListener { runAnimation() }

        if (mIsExpanded) {
            mBinding.toggle.rotation = 180f
            mBinding.contentContainer.visibility = View.VISIBLE
        }

        mBinding.title.text = mTitle
        mBinding.contentContainer.setPadding(mPadding, mPadding, mPadding, mPadding)
        mBinding.contentContainer.addView(getContentView())
        addView(mBinding.root)
    }

    override fun onGlobalLayout() {
        init()
        viewTreeObserver.removeOnGlobalLayoutListener(this)
    }

    open fun setTitle(title: String?) {
        if (title != null && title.isNotEmpty()) mTitle = title
    }

    protected open fun getContentView(): View {
        val view = getChildAt(0)
        removeView(view)

        return view
    }

    private fun runAnimation() {
        if (mBinding.contentContainer.visibility == View.VISIBLE) {
            mBinding.toggle.rotate(180, 0, mBinding.contentContainer.collapse())
        } else {
            mBinding.toggle.rotate(0, 180, mBinding.contentContainer.expand())
        }
    }

    companion object {

        private const val MARQUEE_REPEAT_LIMIT_INFINITY = -1

    }

}