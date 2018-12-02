package mn.factory.androidvsu.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * Created by Turkin A. on 09/11/2018.
 */
class CollapsibleTextView : CollapsibleContentLayout {

    private val mTextView: TextView = TextView(context)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attributeSet, defStyleAttr)

    override fun getContentView(): View {
        mTextView.textSize = 18f
        return mTextView
    }

    fun setText(text: String?) {
        if (text != null && text.isNotEmpty()) (getContentView() as TextView).text = text
    }

    override fun setTitle(text: String?) {
        if (text != null && text.isNotEmpty()) super.setTitle(text)
    }

    companion object {

        @BindingAdapter("content")
        @JvmStatic
        fun setContent(view: CollapsibleTextView?, text: String?) {
            if (view != null && text != null && text.isNotEmpty()) {
                view.setText(text)
            }
        }

    }

}