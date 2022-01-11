package com.example.xelpmoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    val list = arrayListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signoutButton = findViewById<Button>(R.id.Signoutbutton)

        signoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val submitButton = findViewById<Button>(R.id.submitButton)
        val input = findViewById<EditText>(R.id.editTextTextInput)
        val addButton = findViewById<Button>(R.id.addButton)
        val Mean = findViewById<TextView>(R.id.textMean)
        val Median = findViewById<TextView>(R.id.textMedian)
        val printList = findViewById<TextView>(R.id.textList)
        val textMode = findViewById<TextView>(R.id.textMode)
        val buttonClear = findViewById<Button>(R.id.buttonClear)
        val userid = findViewById<TextView>(R.id.userid)
        val email = findViewById<TextView>(R.id.email)

        userid.text = intent.getStringExtra("user_id")
        email.text = intent.getStringExtra("email_id")

        submitButton.setOnClickListener {
            Mean.text = calcMean().toString()
            Median.text = calcMedian().toString()
            val (mode, _) = list.groupingBy { it }.eachCount().maxByOrNull { it.value }!!
            textMode.text = mode.toString()
        }

        addButton.setOnClickListener {
            val num: Int = input.text.toString().toInt()
            list.add(num)
            input.text.clear()
            printList.text= list.toString()
            var sum =0.0
            for (number in list) {
                sum += number.toDouble()
            }
        }

        buttonClear.setOnClickListener {
            list.clear()
            textMode.text = 0.toString()
            Mean.text = 0.toString()
            Median.text = 0.toString()
            printList.text= list.toString()

        }

    }

    private fun calcMean(): Double {
        var sum: Double = 0.0
        for (number in list) {
            sum += number.toDouble()
        }
        return sum / list.size

    }
    private fun calcMedian(): Int {
        list.sort()
        val n = list.size
        if (list.size % 2 != 0) {
            return list[n / 2]
        }
        return (list[(n - 1) / 2] + list[n / 2]) / 2;
    }

    private fun calcMode(): Int {
        var maxvalue = 0
        var maxcount = 0

        for(i in 0..list.size){
            var count = 0
            for(j in 0..list.size){
                if(list[j] ==list[i])
                    ++count
            }
            if(count > maxcount){
                maxcount =count
                maxvalue = list[i]
            }
        }
        return maxvalue
    }
}


