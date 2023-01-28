package com.example.age_in_minute

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker = findViewById<Button>(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance() // java.util에서 현재 날짜의 달력 가져와 사용
        val year = myCalendar.get(Calendar.YEAR) // 년도 가져오기
        val month = myCalendar.get(Calendar.MONTH) // 달 가져오기 (Month는 0부터 시작하기 때문에 +1을 해줘야 함)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH) // 일 가져오기 (DAY는 없음)
        val dpd = DatePickerDialog(this,
            { _, year, month, dayOfMonth ->
                Toast.makeText(this, "Year was $year, Month was ${month+1}, Day was $dayOfMonth", Toast.LENGTH_SHORT).show()

                val selectedDate = "$dayOfMonth/${month+1}/$year"
                tvSelectedDate?.text = selectedDate

                // M을 쓴 이유는, m은 시간 분을 나타내기 때문
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                // 선택한 날짜를 parse()로 문자열 파싱
                val theDate = sdf.parse(selectedDate)
                theDate?.let {
                    // 60000으로 나눠주면 1970 ~ 선택한 날짜까지 시간이 얼마나 지났는지 알 수 있음
                    val selectedDateInMinutes = theDate.time / 60000

                    // 1970년 1월 1일 ~ 현재까지 지난 시간을 ms로 알 수 있음
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        // 60000으로 나눠주면 1970 ~ 현재까지 시간이 얼마나 지났는지 알 수 있음
                        val currentDateInMinutes = currentDate.time / 60000

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                    }
                }
            },
            year, month, day
        )

        // 어제까지만 선택 가능하도록 구현 (한 시간은 360만 밀리초 * 24 = 현재에서 하루만큼만 빠짐)
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}