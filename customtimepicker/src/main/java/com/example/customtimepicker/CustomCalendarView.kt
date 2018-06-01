package com.example.customtimepicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList

class CustomCalendarView : LinearLayout, CalendarAdapter.Listener {
    interface Listener {
        fun onSelectedDate(date: Date)
    }

    private lateinit var gridView: GridView
    private lateinit var pastMonth: ImageButton
    private lateinit var nextMonth: ImageButton
    private lateinit var monthAndYearTxt: TextView

    private var today: Date
    private var month: Int
    private var year: Int
    private var selectedDate: Date

    private val adapter: CalendarAdapter
    private var listener: Listener? = null

    init {

        inflateView()
        setupButtons()

        val calendar = Calendar.getInstance()
        today = calendar.time
        selectedDate = today
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)

        adapter = CalendarAdapter(context, getDatesOfMonth(month, year), today, listener = this)
        gridView.adapter = adapter
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
        pastMonth.setOnClickListener {
            month = if (month - 1 < 0) {
                year -= 1
                11
            } else {
                month - 1
            }
            adapter.updateMonth(getDatesOfMonth(month, year))
            updateDisplayedMonthAndYear()
        }

        nextMonth.setOnClickListener {
            month = if (month + 1 >= 12) {
                year += 1
                0
            } else {
                month + 1
            }
            adapter.updateMonth(getDatesOfMonth(month, year))
            updateDisplayedMonthAndYear()
        }
    }

    private fun getDatesOfMonth(month: Int, year: Int) : ArrayList<Date> {
        val calendar = getCalendar(month, year)

        val days = ArrayList<Date>()
        for (i in 0 until 42) {
            if (month < calendar.get(Calendar.MONTH) && year <= calendar.get(Calendar.YEAR) && i.rem(7) == 0) {
                // Minimum days needed to fill calendar.
                // Corner case:
                // When January is going to be displayed and it doesn't start in sunday
                // It isn't displayed without the year validation.
                break
            }

            days.add(calendar.time)
            val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
            calendar.set(Calendar.DAY_OF_YEAR, dayOfYear + 1)
        }
        return days
    }

    private fun getCalendar(month: Int, year: Int) : Calendar {
        with(Calendar.getInstance()) {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.WEEK_OF_MONTH, 1)
            set(Calendar.DAY_OF_WEEK, 1)
            return this
        }
    }

    private fun updateDisplayedMonthAndYear() {
        // Set calendar at first day of month to prevent skipping months with less than 31 days
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("es", "MX"))
        val text = "${monthName.capitalize()} de $year"
        monthAndYearTxt.text = text
    }

    fun setListener(listener: Listener) {
        this.listener = listener
        listener.onSelectedDate(selectedDate)
    }


    // CalendarAdapter.Listener

    override fun onSelectedDate(date: Date) {
        listener?.onSelectedDate(date)
    }


    // Constructors (All empty)

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)
}
