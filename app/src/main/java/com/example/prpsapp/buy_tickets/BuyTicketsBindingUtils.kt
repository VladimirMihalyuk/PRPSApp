package com.example.prpsapp.buy_tickets

import android.R
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.example.prpsapp.FIVE
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.spans.DotSpan

@BindingAdapter("filmImage")
fun ImageView.setFilmImage(item: BuyTicketsViewModel?) {
    item?.image?.let {
        val resID = context.resources.getIdentifier(item.image, "drawable", context.packageName)
        setImageResource(resID)
    }
}

@BindingAdapter("entries")
fun Spinner.setEntries(entries:MutableLiveData<MutableList<String>>) {
    entries.let{
        val arrayAdapter = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, entries.value ?: listOf<String>())
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        this.adapter = arrayAdapter
    }
}

@BindingAdapter("tickets")
fun Spinner.setTickets(entries:MutableLiveData<Long>) {
    entries.let{
        val size:Int = (if(entries.value ?: 0 > FIVE) 5 else entries.value ?: 0).toInt()
        val list = List(size) { index -> "${ index + 1L} ${if(index +1L == 1L) "ticket" else "tickets"}"}
        val arrayAdapter = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, list)
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        this.adapter = arrayAdapter
    }
}

@BindingAdapter("decorator")
fun MaterialCalendarView.setDecor(dates: MutableLiveData<HashSet<CalendarDay>>?) {
    dates?.value?.let{
       val decorator = Decorator(dates.value)
       this.addDecorator(decorator)
    }
}

@BindingAdapter("clickListener")
fun MaterialCalendarView.setClickListener(viewModel: BuyTicketsViewModel){
    this.setOnDateChangedListener { widget, date,selected ->
        viewModel.selectedDate = date
        if(viewModel.dates.value?.contains(date) != true){
            viewModel.thereIsNoSuchEvent.value = true
            viewModel.cinema.value = mutableListOf<String>()
            viewModel.isSelected = false
        } else {
            viewModel.cinema.value = viewModel.map[date]
            viewModel.isSelected = true
        }
    }
}

@BindingAdapter("cinemaSelectedListener")
fun Spinner.selectedCinema(viewModel: BuyTicketsViewModel){
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            viewModel.selectedCinema = parent?.getItemAtPosition(position).toString()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            viewModel.selectedCinema = viewModel.cinema.value?.getOrNull(0) ?: ""
        }
    }
}


@BindingAdapter("ticketsSelectedListener")
fun Spinner.selectedTickets(viewModel: BuyTicketsViewModel){
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            viewModel.selectedTickets = parent?.getItemAtPosition(position).toString()[0].toInt() - 48
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            viewModel.selectedTickets = 1
        }
    }
}




class Decorator(val hashSet: HashSet<CalendarDay>?) :DayViewDecorator{

    override fun shouldDecorate(day: CalendarDay?): Boolean {
       return  hashSet?.contains(day) ?: false
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(10F, Color.RED))
    }
}