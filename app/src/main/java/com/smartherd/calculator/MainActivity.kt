package com.smartherd.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Numbers
        tvZero.setOnClickListener { appendOnExpression("0", true)}
        tvOne.setOnClickListener { appendOnExpression("1", true)}
        tvTwo.setOnClickListener { appendOnExpression("2", true)}
        tvThree.setOnClickListener { appendOnExpression("3", true)}
        tvFour.setOnClickListener { appendOnExpression("4", true)}
        tvFive.setOnClickListener { appendOnExpression("5", true)}
        tvSix.setOnClickListener { appendOnExpression("6", true)}
        tvSeven.setOnClickListener { appendOnExpression("7", true)}
        tvEight.setOnClickListener { appendOnExpression("8", true)}
        tvNine.setOnClickListener { appendOnExpression("9", true)}
        tvDot.setOnClickListener { appendOnExpression(".", true)}

        //Operators
//        tvPlus.setOnClickListener{ appendOnExpression("sin", false)}
        tvMinus.setOnClickListener { appendOnExpression("-", false)}
        tvMultiple.setOnClickListener { appendOnExpression("*", false)}
        tvDivide.setOnClickListener { appendOnExpression("/", false)}
        tvLeftBracket.setOnClickListener { appendOnExpression("(", true)}
        tvRightBracket.setOnClickListener { appendOnExpression(")", false)}

        tvClear.setOnClickListener {
            tvExpression.text = ""
            tvResult.text = ""
        }

        tvPlus.setOnClickListener {
            try {
                var expression: Expression? = null
                var string = tvExpression.text.toString()
                if (string[0] == '(' && string[string.length-1] == ')') {
                    expression = ExpressionBuilder("cos(${tvExpression.text})").build()
                } else {
                    var i = string.length - 1
                    var newString = ""
                    var boolean = true
                    if (string.contains("+") || string.contains("-") || string.contains("*") || string.contains("/")) {
                        while (boolean) {
                            if (string[i] == '+' || string[i] == '-' || string[i] == '*' || string[i] == '/') {
                                boolean = false
                            }
                            newString = string[i] + newString
                            i -= 1
                            string = string.substring(0, i)
                        }
                        expression = ExpressionBuilder(string + "cos($newString)").build()
                    } else {
                        expression = ExpressionBuilder("cos(${tvExpression.text})").build()
                    }
                }

                tvExpression.append("cos")
                val result = expression!!.evaluate()
                val longResult = result.toLong()
                if (result == longResult.toDouble()) {
                    tvResult.text = longResult.toString()
                } else {
                    tvResult.text = result.toString()
                }

            } catch (e: Exception) {
                Log.d("Exception", "message: " + e.message)
            }
        }
        tvBack.setOnClickListener{
            val string = tvExpression.text.toString()
            if (string.isNotEmpty()) {
                tvExpression.text = string.substring(0, string.length-1)
            }
            tvResult.text = ""
        }

        tvEqual.setOnClickListener {
            try {
                val expression = ExpressionBuilder(tvExpression.text.toString()).build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                if (result == longResult.toDouble()) {
                    tvResult.text = longResult.toString()
                } else {
                    tvResult.text = result.toString()
                }
            } catch (e: Exception) {
                Log.d("Exception", "message: " + e.message)
            }
        }
    }

    private fun appendOnExpression (string: String, canClear: Boolean) {
        if (tvResult.text.isNotEmpty()) {
            tvExpression.text = ""
        }

        if (canClear) {
            tvResult.text = ""
            tvExpression.append(string)
        } else {
            tvExpression.append(tvResult.text)
            tvExpression.append(string)
            tvResult.text = ""
        }
    }
}
