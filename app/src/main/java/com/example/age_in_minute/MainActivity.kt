package com.example.age_in_minute

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker = findViewById<Button>(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    fun clickDatePicker() {
        val myCalendar = Calendar.getInstance() // java.util에서 현재 날짜의 달력 가져와 사용
        val year = myCalendar.get(Calendar.YEAR) // 년도 가져오기
        val month = myCalendar.get(Calendar.MONTH) // 달 가져오기 (Month는 0부터 시작하기 때문에 +1을 해줘야 함)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH) // 일 가져오기 (DAY는 없음)

        DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                Toast.makeText(this, "Year was $year, Month was ${month+1}, Day was $dayOfMonth", Toast.LENGTH_SHORT).show()
            },
            year, month, day
        ).show()
    }
}