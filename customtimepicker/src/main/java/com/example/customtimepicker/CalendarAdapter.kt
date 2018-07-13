package com.example.customtimepicker

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.*
import com.example.customtimepicker.extensions.*
import kotlin.collections.ArrayList

class CalendarAdapter(
        private val context: Context,
        private var dateList: ArrayList<Date>,
        private var today: Date,
        private var listener: Listener) : BaseAdapter() {

    private var selectedDate = today
    private var minDate: Date? = null
    private var maxDate: Date? = null

    interface Listener {
        fun onSelectDate(date: Date)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = (convertView ?: CalendarCell(context)) as CalendarCell
        view.selectedColor = selectedColor

        dateList[position].let {
            when {
                it.isSameDay(selectedDate) -> view.setSelected()
                it.isSameDay(today) -> view.setAsToday()
                !areWeekendsEnabled && it.isWeekend -> view.setDisabled()
                it.outOfBounds -> view.setDisabled()
                else -> view.setIDLE()
            }
            view.setDayOfMonth(it.dayOfMonth)
        }

        view.setOnClickListener {
            if (view.cellIsSelected) return@setOnClickListener
            selectedDate = dateList[position]
            notifyDataSetChanged()
        }
        return view
    }

    override fun getItem(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = dateList.size

    fun setSelectedDate(date: Date, daysOfMonth: ArrayList<Date>) {
        if (selectedDate.isSameDay(date)) return
        selectedDate = date
        updateMonth(daysOfMonth)
    }

    fun updateMonth(dateList: ArrayList<Date>) {
        this.dateList = dateList
        notifyDataSetChanged()
    }

    var areWeekendsEnabled : Boolean = true
        set(enabled) {
            field = enabled
            notifyDataSetChanged()
        }

    fun setDateBounds(minDate: Date? = this.minDate, maxDate: Date? = this.maxDate) {
        this.minDate = minDate
        this.maxDate = maxDate
        notifyDataSetChanged()
    }

    var selectedColor : Int = R.color.colorAccent
        set(color) {
            field = color
            notifyDataSetChanged()
        }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
        listener.onSelectDate(selectedDate)
    }

    private val Date.outOfBounds : Boolean
        get() {
            var isOut = false
            isOut = isOut || minDate?.after(this) ?: false
            isOut = isOut || maxDate?.before(this) ?: false
            return isOut
        }


    // Extensions

    private val Date.dayOfMonth : Int
        get() {
            val calendar = this.calendar
            return calendar.dayOfMonth
        }

    private fun Date.isSameDay(other: Date) : Boolean {
        if (this === other) return true
        val thisCalendar = this.calendar
        val otherCalendar = other.calendar

        return (thisCalendar.dayOfYear == otherCalendar.dayOfYear && thisCalendar.year == otherCalendar.year)
    }
}