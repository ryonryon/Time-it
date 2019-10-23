package com.example.ryonryon.TimeIt

import android.icu.text.SimpleDateFormat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter

class MainActivity : AppCompatActivity() {
    var number: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timeText1 = findViewById(R.id.timeText1) as TextView
        val timeText2 = findViewById(R.id.timeText2) as TextView
        val timeText3 = findViewById(R.id.timeText3) as TextView
        val timeText4 = findViewById(R.id.timeText4) as TextView
        val actionText = findViewById(R.id.actionText) as TextView
        val arrival1Button = findViewById(R.id.arrival1) as Button
        val getOnButton = findViewById(R.id.getOn) as Button
        val restButton = findViewById(R.id.rest) as Button
        val startButton = findViewById(R.id.start) as Button
        val arrival2Button = findViewById(R.id.arrival2) as Button
        val getOffButton = findViewById(R.id.getOff) as Button
        val saveButton = findViewById(R.id.save) as Button
        val resetButton = findViewById(R.id.reset) as Button

        var flag1: Int = 0
        var flag2: Int = 0
        var flag3: Int = 0
        var flag4: Int = 0

        var getOnTime1: String = ""
        var getOnTime2: String = ""
        var getOnTime3: String = ""
        var getOnTime4: String = ""
        var getOffTime1: String = ""
        var getOffTime2: String = ""
        var getOnElapsedTime1: Int
        var getOnElapsedTime2: Int
        var getOnElapsedTime3: Int
        var getOffElapsedTime1: Int


        arrival1Button.setOnClickListener {
            getOnTime1 = getToday()
            timeText1.text = ("到着" + getToday())
            flag1 = 1
        }
        getOnButton.setOnClickListener {
            getOnTime2 = getToday()
            timeText2.text = ("乗車" + getToday())
            flag2 = 1
        }
        restButton.setOnClickListener {
            getOnTime3 = getToday()
            timeText3.text = ("静止" + getToday())
            flag3 = 1
        }
        startButton.setOnClickListener {
            getOnTime4 = getToday()
            timeText4.text = ("発車" + getToday())
            flag4 = 1
        }
        arrival2Button.setOnClickListener {
            getOffTime1 = getToday()
            timeText1.text = ("到着" + getToday())
            flag1 = 2
        }
        getOffButton.setOnClickListener {
            getOffTime2 = getToday()
            timeText2.text = ("降車" + getToday())
            flag2 = 2
        }
        saveButton.setOnClickListener {
//            getOnElapsedTime1 = calcElapsedTime(getOnTime1, getOnTime2)
//            getOnElapsedTime2 = calcElapsedTime(getOnTime2, getOnTime3)
//            getOnElapsedTime3 = calcElapsedTime(getOnTime3, getOnTime4)
//            getOffElapsedTime1 = calcElapsedTime(getOffTime1, getOffTime2)
            //  && 0 <= getOnElapsedTime1 && 0 <= getOnElapsedTime2 && 0 <= getOnElapsedTime3
            //  && 0 <= getOffElapsedTime1
            if(flag1 == 1 && flag2 == 1 && flag3 == 1 && flag4 == 1) {
                getOnSave()
                Toast.makeText(applicationContext, "Save Get On", Toast.LENGTH_SHORT).show()
            } else if(flag1 == 2 && flag2 == 2 && flag3 == 0 && flag4 == 0) {
                getOffSave()
                Toast.makeText(applicationContext, "Save Get Off", Toast.LENGTH_SHORT).show()
            }
        }
        resetButton.setOnClickListener {
            timeText1.text = ""
            timeText2.text = ""
            timeText3.text = ""
            timeText4.text = ""
            actionText.text = ""
            flag1 = 0
            flag2 = 0
            flag3 = 0
            flag4 = 0
            getOnTime1 = ""
            getOnTime2 = ""
            getOnTime3 = ""
            getOnTime4 = ""
            getOffTime1 = ""
            getOffTime2 = ""
        }
    }

    private fun getOnSave() {
        val dir = "${Environment.getExternalStorageDirectory()}/$packageName"
        File(dir).mkdirs()
        var fileName = "timeit_"+number+".csv"
        var file = "$dir/$fileName"
        if(Files.exists(Paths.get(file))){
            number+=1
            fileName = "timeit_"+number+".csv"
            file = "$dir/$fileName"
        }
        val writer = Files.newBufferedWriter(Paths.get(file))
        val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT
            .withHeader("Label", "Time"))
        csvPrinter.printRecord("Arrival1", getToday())
        csvPrinter.printRecord("GetOn", getToday())
        csvPrinter.printRecord("Rest", getToday())
        csvPrinter.printRecord("Start", getToday())
        csvPrinter.flush()
        csvPrinter.close()
    }

    private fun getOffSave() {
        val dir = "${Environment.getExternalStorageDirectory()}/$packageName"
        File(dir).mkdirs()
        var fileName = "timeit_"+number+".csv"
        var file = "$dir/$fileName"
        if(Files.exists(Paths.get(file))){
            number+=1
            fileName = "timeit_"+number+".csv"
            file = "$dir/$fileName"
        }
        val writer = Files.newBufferedWriter(Paths.get(file))
        val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT
            .withHeader("Label", "Time"))
        csvPrinter.printRecord("Arrival2", getToday())
        csvPrinter.printRecord("GetOff", getToday())
        csvPrinter.flush()
        csvPrinter.close()
    }

    private fun getToday(): String {
        val date = Date()
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }

//    private fun calcElapsedTime(time1: String, time2: String): Int {
//        var time1Sum: Int = 0
//        var time2Sum: Int = 0
//        var diff: Int = 0
//        var diffHour: Int = 0
//        var diffMinute: Int = 0
//        var diffSecond: Int = 0
//        var time1Hour: Int = 0
//        var time1Minute: Int = 0
//        var time1Second: Int = 0
//        var time2Hour: Int = 0
//        var time2Minute: Int = 0
//        var time2Second: Int = 0
//
//        time1Hour = time1.substring(11, 13).toInt()
//        time1Minute = time1.substring(14, 16).toInt()
//        time1Second = time1.substring(17, 19).toInt()
//        time2Hour = time2.substring(11, 13).toInt()
//        time2Minute = time2.substring(14, 16).toInt()
//        time2Second = time2.substring(17, 19).toInt()
//
//        time1Sum = time1Hour*3600 + time1Minute*60 + time1Second
//        time2Sum = time2Hour*3600 + time2Minute*60 + time2Second
//
//        diff = time2Sum - time1Sum
//        diffHour = diff / 3600
//        diffMinute = diff % 3600 / 60
//        diffSecond = diff % 3600 % 60
//
//        return diffHour+diffMinute+diffSecond
//    }
}
