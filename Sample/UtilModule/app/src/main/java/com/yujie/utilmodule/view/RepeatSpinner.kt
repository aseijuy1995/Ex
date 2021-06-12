package com.yujie.utilmodule.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView

class RepeatSpinner(cxt: Context, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatSpinner(cxt, attrs) {
    var onItemSelectedEvenIfUnchangedListener: OnItemSelectedListener? = null
    private var lastParent: AdapterView<*>? = null
    private var lastView: View? = null
    private var lastId: Long = 0

    init {
        initListener()
    }

    override fun setSelection(position: Int) {
        if (position == selectedItemPosition && onItemSelectedEvenIfUnchangedListener != null) {
            onItemSelectedEvenIfUnchangedListener?.onItemSelected(lastParent, lastView, position, lastId)
        } else {
            super.setSelection(position)
        }
    }

    private fun initListener() {
        // TODO Auto-generated method stub
        super.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View,
                position: Int, id: Long
            ) {
                // TODO Auto-generated method stub
                lastParent = parent
                lastView = view
                lastId = id
                if (onItemSelectedEvenIfUnchangedListener != null) {
                    onItemSelectedEvenIfUnchangedListener?.onItemSelected(parent, view, position, id)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO Auto-generated method stub
                if (onItemSelectedEvenIfUnchangedListener != null) {
                    onItemSelectedEvenIfUnchangedListener?.onNothingSelected(parent)
                }
            }
        })
    }
}