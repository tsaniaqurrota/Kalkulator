package com.example.kalkulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private lateinit var inputTextView: TextView
    private lateinit var resultTextView: TextView
    private var currentCalc = ""
    private var currentOperation = ""
    private var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputTextView = findViewById(R.id.input)
        resultTextView = findViewById(R.id.result)

        btnClicked()
    }

    private fun btnClicked() {
        // Button AC
        findViewById<Button>(R.id.buttonAC).setOnClickListener {
            currentCalc = ""
            currentOperation = ""
            result = ""
            inputTextView.text = ""
            resultTextView.text = ""
        }

        // Button Backspace
        findViewById<Button>(R.id.buttonDel).setOnClickListener {
            delLastChar()
        }

        // Operator Button
        findViewById<Button>(R.id.buttonPlus).setOnClickListener {
            inputOperator("+")
        }
        findViewById<Button>(R.id.buttonMin).setOnClickListener {
            inputOperator("-")
        }
        findViewById<Button>(R.id.buttonMultiple).setOnClickListener {
            inputOperator("×")
        }
        findViewById<Button>(R.id.buttonDivided).setOnClickListener {
            inputOperator("÷")
        }
        findViewById<Button>(R.id.buttonPersen).setOnClickListener {
            inputOperator("%")
        }

        // Operand Button
        findViewById<Button>(R.id.button0).setOnClickListener {
            inputOperand("0")
        }
        findViewById<Button>(R.id.button1).setOnClickListener {
            inputOperand("1")
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            inputOperand("2")
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            inputOperand("3")
        }
        findViewById<Button>(R.id.button4).setOnClickListener {
            inputOperand("4")
        }
        findViewById<Button>(R.id.button5).setOnClickListener {
            inputOperand("5")
        }
        findViewById<Button>(R.id.button6).setOnClickListener {
            inputOperand("6")
        }
        findViewById<Button>(R.id.button7).setOnClickListener {
            inputOperand("7")
        }
        findViewById<Button>(R.id.button8).setOnClickListener {
            inputOperand("8")
        }
        findViewById<Button>(R.id.button9).setOnClickListener {
            inputOperand("9")
        }
        findViewById<Button>(R.id.buttonPoint).setOnClickListener {
            inputOperand(".")
        }

        // Button Result
        findViewById<Button>(R.id.buttonResult).setOnClickListener {
            calculateResult()
        }
    }

    private fun delLastChar() {
        if (currentCalc.isNotEmpty()) {
            currentCalc = currentCalc.substring(0, currentCalc.length - 1)
            inputTextView.text = currentCalc
        }
    }

    private fun inputOperator(op: String) {
        if (currentCalc.isNotEmpty() && currentOperation.isEmpty()) {
            currentOperation = op
            currentCalc += op
            inputTextView.text = currentCalc
        }
    }

    private fun inputOperand(operand: String) {
        val operators = setOf("+", "-", "×", "÷", "%")

        if (operand == "." && currentCalc.isEmpty()) {
            currentCalc = "0."
        } else if (currentCalc.isEmpty() && operand == "0") {
            return
        } else if (currentCalc == "0" && operand != ".") {
            currentCalc = operand
        } else {
            if (operators.contains(currentOperation) || currentOperation.isEmpty()) {
                currentCalc += operand
            }
        }

        inputTextView.text = currentCalc
    }

    private fun calculateResult() {
        if (currentCalc.isNotEmpty() && currentOperation.isNotEmpty()) {
            val operators = setOf("+", "-", "×", "÷", "%")

            if (operators.contains(currentOperation)) {
                val operands = currentCalc.split(Regex("[-+×÷%]"))

                if (operands.size == 2) {
                    val num1 = BigDecimal(operands[0])
                    val num2 = BigDecimal(operands[1])
                    when (currentOperation) {
                        "+" -> result = (num1 + num2).toString()
                        "-" -> result = (num1 - num2).toString()
                        "×" -> result = (num1 * num2).toString()
                        "÷" -> {
                            if (num2.compareTo(BigDecimal.ZERO) == 0) {
                                Toast.makeText(this@MainActivity, "Can't divide by zero", Toast.LENGTH_SHORT).show()
                            } else {
                                result = (num1/num2).toString()
                            }
                        }
                        "%" -> result = (num1 % num2).toString()
                    }
                    resultTextView.text = result
                    currentCalc = result
                    currentOperation = ""
                } else {
                    Toast.makeText(this@MainActivity, "Invalid input", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
