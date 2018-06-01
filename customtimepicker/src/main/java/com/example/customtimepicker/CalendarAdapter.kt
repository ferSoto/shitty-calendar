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
        private var selectedDate: Date = today,
        private var listener: Listener) : BaseAdapter() {

    interface Listener {
        fun onSelectedDate(date: Date)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = (convertView ?: CalendarCell(context)) as CalendarCell

        dateList[position].let {
            when {
                it.isSameDay(selectedDate) -> view.setSelected()
                it.isSameDay(today) -> view.setAsToday()
                it.before(today) -> view.setDisabled()
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

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
        listener.onSelectedDate(selectedDate)
    }

    fun setSelectedDate(date: Date, daysOfMonth: ArrayList<Date>) {
        selectedDate = date
        updateMonth(daysOfMonth)
    }

    fun updateMonth(dateList: ArrayList<Date>) {
        this.dateList = dateList
        notifyDataSetChanged()
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