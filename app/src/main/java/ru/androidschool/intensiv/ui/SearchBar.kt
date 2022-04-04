package ru.androidschool.intensiv.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.SearchToolbarBinding
import java.util.concurrent.TimeUnit

class SearchBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    lateinit var binding: SearchToolbarBinding

    private var hint: String = ""
    private var isCancelVisible: Boolean = true

    init {
        if (attrs != null) {
            context.obtainStyledAttributes(attrs, R.styleable.SearchBar).apply {
                hint = getString(R.styleable.SearchBar_hint).orEmpty()
                isCancelVisible = getBoolean(R.styleable.SearchBar_cancel_visible, true)
                recycle()
            }
        }
    }

    val onTextChangedObservable by lazy {
        Observable.create(
            ObservableOnSubscribe<String> { subscriber ->
                binding.searchEditText.doAfterTextChanged { text ->
                    subscriber.onNext(text.toString())
                }
            }
        )
    }

    val onTextChangedWithOperatorObservable by lazy {
        onTextChangedObservable
            .debounce(500, TimeUnit.MILLISECONDS)
            .map { it.trim() }
            .filter { it.length > 3 }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun setText(text: String) {
        binding.searchEditText.setText(text)
    }

    fun clear() {
        binding.searchEditText.setText("")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = SearchToolbarBinding.inflate(LayoutInflater.from(context), this, true)
        binding.searchEditText.hint = hint
        binding.deleteTextButton.setOnClickListener {
            binding.searchEditText.text.clear()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.searchEditText.afterTextChanged { text ->
            if (!text.isNullOrEmpty() && !binding.deleteTextButton.isVisible) {
                binding.deleteTextButton.visibility = View.VISIBLE
            }
            if (text.isNullOrEmpty() && binding.deleteTextButton.isVisible) {
                binding.deleteTextButton.visibility = View.GONE
            }
        }
    }
}
