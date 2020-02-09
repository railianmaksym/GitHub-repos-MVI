package com.railian.mobile.githubrepos.util.extensions

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun EditText.makeClearableEditText(
    onClear: (() -> Unit)? = null,
    clearDrawable: Drawable
) {
    val updateRightDrawable = {
        this.setCompoundDrawablesWithIntrinsicBounds(
            null, null,
            if (text.isNotEmpty()) clearDrawable else null,
            null
        )
    }
    updateRightDrawable()

    this.afterTextChanged {
        updateRightDrawable()
    }
    this.onRightDrawableClicked {
        this.text.clear()
        this.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        onClear?.invoke()
        this.requestFocus()
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

@SuppressLint("ClickableViewAccessibility")
fun EditText.onRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
    this.setOnTouchListener { v, event ->
        var hasConsumed = false
        if (v is EditText) {
            if (event.x >= v.width - v.totalPaddingRight) {
                if (event.action == MotionEvent.ACTION_UP) {
                    onClicked(this)
                }
                hasConsumed = true
            }
        }
        hasConsumed
    }
}