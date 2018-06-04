package com.example.customtimepicker

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import java.util.*
import com.example.customtimepicker.extensions.calendar

class CustomTimePicker : FrameLayout, CustomCalendarView.Listener {
    private lateinit var currentDateLayout: RelativeLayout
    private lateinit var currentYearTxt: TextView
    private lateinit var currentDateTxt: TextView
    private lateinit var calendarView: CustomCalendarView
    private lateinit var cancelButton: Button
    private lateinit var acceptButton: Button

    init {
        inflateLayout()
        calendarView.setListener(this)
    }

    private fun inflateLayout() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_custom_time_picker, this)

        currentDateLayout = findViewById(R.id.current_date_layout)
        currentYearTxt = findViewById(R.id.current_year_txt)
        currentDateTxt = findViewById(R.id.current_date_txt)
        calendarView = findViewById(R.id.calendar_view)
        cancelButton = findViewById(R.id.cancel_button)
        acceptButton = findViewById(R.id.accept_button)
    }

    fun changeColor(color: Int) {
        currentDateLayout.background = ColorDrawable(color)
        cancelButton.setTextColor(color)
        acceptButton.setTextColor(color)
    }

    var selectedDate : Date
        get() = calendarView.selectedDate
        set(date) {
        calendarView.selectedDate = date
    }

    fun setWeekendsEnabled(enabled: Boolean) {

    }

    fun onAcceptClick(listener: (Date) -> Unit) {
        acceptButton.setOnClickListener {
            listener(calendarView.selectedDate)
        }
    }

    fun onCancelClick(listener: () -> Unit) {
        cancelButton.setOnClickListener {
            listener()
        }
    }


    // CustomCalendarView.Listener

    override fun onSelectDate(date: Date) {
        currentDateTxt.text = date.formatted
    }


    // Extensions

    private val Date.formatted: String
        get() {
            val locale = Locale("es", "MX")
            val calendar = this.calendar
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale)
            val month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale)
            return "${dayOfWeek.capitalize()} $dayOfMonth ${month.capitalize()}"
        }

    // Constructors (All empty)

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)
}