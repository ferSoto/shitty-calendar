package com.example.customtimepicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList
import com.example.customtimepicker.extensions.*

class CustomCalendarView : LinearLayout, CalendarAdapter.Listener {
    interface Listener {
        fun onSelectDate(date: Date)
    }

    private lateinit var gridView: GridView
    private lateinit var pastMonth: ImageButton
    private lateinit var nextMonth: ImageButton
    private lateinit var monthAndYearTxt: TextView

    private var today: Date
    private var month: Int
    private var year: Int

    private var minDate: Date? = null
    private var maxDate: Date? = null

    private val adapter: CalendarAdapter
    private var listener: Listener? = null

    init {
        inflateView()
        setupButtons()

        val calendar = Calendar.getInstance()
        today = calendar.time
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)

        adapter = CalendarAdapter(context, getDatesOfMonth(month, year), today, this)
        gridView.adapter = adapter
        selectedDate = today
    }

    private fun inflateView() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_custom_calendar_view, this)
        gridView = findViewById(R.id.grid_view)
        pastMonth = findViewById(R.id.past_month_button)
        nextMonth = findViewById(R.id.next_month_button)
        monthAndYearTxt = findViewById(R.id.month_and_year_txt)
    }

    private fun setupButtons() {
        fun updateUI() {
            adapter.updateMonth(getDatesOfMonth(month, year))
            updateDisplayedMonthAndYear()
            updateNavigationButtons()
        }

        pastMonth.setOnClickListener {
            // Do not take taps if button isn't visible
            if (it.visibility != View.VISIBLE) return@setOnClickListener
            month = if (month - 1 < 0) {
                year -= 1
                11
            } else {
                month - 1
            }
            updateUI()
        }

        nextMonth.setOnClickListener {
            // Do not take taps if button isn't visible
            if (it.visibility != View.VISIBLE) return@setOnClickListener
            month = if (month + 1 >= 12) {
                year += 1
                0
            } else {
                month + 1
            }
            updateUI()
        }
    }

    private fun getDatesOfMonth(month: Int, year: Int) : ArrayList<Date> {
        val calendar = getCalendar(month, year)

        val days = ArrayList<Date>()
        for (i in 0 until 42) {
            if (month < calendar.month && year <= calendar.year && i.rem(7) == 0) {
                // Minimum days needed to fill calendar.
                // Corner case:
                // When January is going to be displayed and it doesn't start in sunday
                // It isn't displayed without the year validation.
                break
            }

            days.add(calendar.time)
            calendar.dayOfYear += 1
        }
        return days
    }

    private fun getCalendar(month: Int, year: Int) : Calendar {
        Calendar.getInstance().let {
            it.year = year
            it.month = month
            it.weekOfMonth = 1
            it.dayOfWeek = 1
            return it
        }
    }

    private fun updateDisplayedMonthAndYear() {
        // Set calendar at first day of month to prevent skipping months with less than 31 days
        val calendar = Calendar.getInstance()
        calendar.month = month
        calendar.dayOfMonth = 1
        val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("es", "MX"))
        val text = "${monthName.capitalize()} de $year"
        monthAndYearTxt.text = text
    }

    private fun updateNavigationButtons() {
        if (minDate != null && minDate!!.isCurrentMonth) {
            pastMonth.visibility = View.INVISIBLE
        } else {
            pastMonth.visibility = View.VISIBLE
        }

        if (maxDate != null && maxDate!!.isCurrentMonth) {
            nextMonth.visibility = View.INVISIBLE
        } else {
            nextMonth.visibility = View.VISIBLE
        }
    }

    var selectedDate : Date
        set(date) {
            // Set Selected date, update displayed month and notify change
            field = date
            val calendar = date.calendar
            adapter.setSelectedDate(date, getDatesOfMonth(calendar.month, calendar.year))
            listener?.onSelectDate(selectedDate)
        }

    var areWeekendsEnabled : Boolean
        get() = adapter.areWeekendsEnabled
        set(enabled) {
            adapter.areWeekendsEnabled = enabled
        }

    fun setDateBounds(minDate: Date? = null, maxDate: Date? = null) {
        this.minDate = minDate
        this.maxDate = maxDate
        updateNavigationButtons()
        adapter.setDateBounds(minDate, maxDate)
    }

    var selectedColor : Int = R.color.colorAccent
        set(color) {
            field = color
            adapter.selectedColor = color
        }

    fun setListener(listener: Listener) {
        this.listener = listener
        listener.onSelectDate(selectedDate)
    }


    // CalendarAdapter.Listener

    override fun onSelectDate(date: Date) {
        listener?.onSelectDate(date)
    }


    // Extensions

    private val Date.isCurrentMonth : Boolean
        get() {
            val calendar = this.calendar
            val thisMonth = calendar.month
            val thisYear = calendar.year
            with(this@CustomCalendarView) {
                return (month == thisMonth && year == thisYear)
            }
        }


    // Constructors (All empty)

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)
}
