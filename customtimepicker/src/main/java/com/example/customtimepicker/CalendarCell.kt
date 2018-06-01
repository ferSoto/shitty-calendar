package com.example.customtimepicker

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import java.util.*

class CalendarCell : LinearLayout {
    private enum class State { IDLE, SELECTED, TODAY, DISABLED }

    private var state = State.IDLE
    private var dateTxt: TextView
    private var selectedColor = Color.BLUE

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_custom_calendar_cell_view, this)
        dateTxt = findViewById(R.id.date_txt)
    }

    fun setDayOfMonth(dayOfMonth: Int) {
        dateTxt.text = dayOfMonth.toString()
    }

    fun setSelectedColor(color: Int) {
        selectedColor = color
    }

    fun setIDLE() {
        if (state == State.IDLE) return
        state = State.IDLE
        isEnabled = true

        background = ColorDrawable(Color.TRANSPARENT)
        dateTxt.setTextColor(Color.BLACK)
    }

    fun setSelected() {
        if (state == State.SELECTED) return
        state = State.SELECTED
        isEnabled = true

        val background = context.getDrawable(R.drawable.background_selected)
        background.setTint(selectedColor)
        this.background = background
        dateTxt.setTextColor(Color.WHITE)
    }

    fun setAsToday() {
        if (state == State.TODAY) return
        state = State.TODAY
        isEnabled = true

        background = ColorDrawable(Color.TRANSPARENT)
        dateTxt.setTextColor(selectedColor)
    }

    fun setDisabled() {
        if (state == State.DISABLED) return
        state = State.DISABLED
        isEnabled = false

        background = ColorDrawable(Color.TRANSPARENT)
        dateTxt.setTextColor(Color.LTGRAY)
    }

    val cellIsSelected: Boolean
        get() = (state == State.SELECTED)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    // Constructors (all empty)

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)
}