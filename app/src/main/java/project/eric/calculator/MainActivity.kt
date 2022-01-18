package project.eric.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private lateinit var tvInput :TextView
    private lateinit var tvOutput :TextView
    private lateinit var btnEqual :Button

    private val list = arrayListOf<String>("")
    var index = 0;

    var firstInput :Boolean = true
    var lastNumeric :Boolean = false
    var lastDot :Boolean = false
    var lastOperator :Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
        tvOutput = findViewById(R.id.tvOutput)
        btnEqual = findViewById(R.id.btnEqual)




    }

    fun onDigit(view : View) {
        var digit = (view as Button).text
        if (firstInput) {
            firstInput = false
            tvInput.text = digit

            list[index] = "$digit"

        } else if (lastOperator && !lastNumeric) {
            index++

            tvInput.append(digit)
            list.add("$digit")

            lastNumeric = true
            lastOperator = false

        } else {
            tvInput.append(digit)
            list[index] = list[index] + digit

            lastNumeric = true
            lastOperator = false
        }

        onUpdateOutput()
        Log.i("test", list.toString())
    }

    fun onClear(view: View) {
        // Reset input TextView
        tvInput.text = "0"
        list.clear()
        index = 0

        // Reset Booleans
        lastNumeric = false
        lastDot = false
        lastOperator = false
        firstInput = true
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view :View) {
        var operator = (view as Button).text
        if (lastOperator) {
            tvInput.text = tvInput.text.substring(0, tvInput.text.length - 1) + operator

            list[index] = "$operator"

        } else {
            index++
            tvInput.append(operator)
            list.add("$operator")

            lastNumeric = false
            lastDot = false
            lastOperator = true
        }
    }


    fun onUpdateOutput() {
        if (lastNumeric) {
            tvOutput.text = calculate()
        }
    }

    private fun calculate():String {
        var equation = list.clone() as ArrayList<String>
        // 1+2/2x2+1
        // 012345678
        // 1+1x2+1=3
        while(equation.size > 1) {
            for (i in 1..equation.size step 2) {
                var total = 0.0
                var num1 = equation[i - 1]
                var num2 = equation[i + 1]
                var operator = equation[i]
                Log.i("test", equation.toString())
                if (operator == "÷") {
                    total = num1.toDouble() / num2.toDouble()
                    equation.add(i + 2, "$total")
                } else if (operator == "x") {
                    total = num1.toDouble() * num2.toDouble()
                    equation.add(i + 2, "$total")
                } else if (operator == "+") {
                    total = num1.toDouble() + num2.toDouble()
                    equation.add(i + 2, "$total")
                } else if (operator == "-"){
                    total = num1.toDouble() - num2.toDouble()
                    equation.add(i + 2, "$total")
                }

                equation.removeAt(i + 1)
                equation.removeAt(i)
                equation.removeAt(i - 1)
                break
            }
        }

        return equation[0]
    }

    fun onEqual(view :View) {
        /*
        if(lastNumeric) {
            var tvValue = tvInput.text.toString()
            var prefix = ""

            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }


                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDot ((one.toDouble() - two.toDouble()).toString())
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }


            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }

         */
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
    }

    private fun removeZeroAfterDot(result :String) :String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }
}