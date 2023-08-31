package com.example.project1

import android.widget.TextView


class Calculator {
    // storage for numbers we're operating on
    var numBuffer = ArrayList<Float>()
    // storage for operands we will apply to numbers
    var opBuffer = ArrayList<String>()
    // text displayed on calculator textview
    var display = ""
    var shouldClearDisplayInput = false
    // main textview of the calculator
    var mainTextView: TextView? = null

    // cache reference to calculator textview for text display
    fun SetMainDisplay(mainTextView: TextView)
    {
        this.mainTextView = mainTextView
    }
    // actually update the textview
    fun DisplayUpdate()
    {
        mainTextView?.setText(display)
    }

    // clear all buffered operations/numbers and clear display
    fun Clear() {
        display = ""
        numBuffer.clear()
        opBuffer.clear()
        DisplayUpdate()
    }
    // overload to take single chars too
    fun PushOp(op: Char) {
        PushOp(op.toString())
    }

    // main logic, take some "op" (operation - could be a number or a symbol) cache and process it
    fun PushOp(op: String) {
        // C is clear
        if (op == "C")
        {
            Clear()
            return
        }
        // flip sign
        else if (op == "+/-")
        {
            if (display[0] == '-') { display = display.substring(1) }
            else { display = '-'.plus(display) }
            DisplayUpdate()
            return
        }
        // percentage -> divide by 100
        else if (op == "%")
        {
            var tryParseNum = display.toFloatOrNull()
            if (tryParseNum != null)
            {
                display = (tryParseNum / 100.0f).toString()
            }
            DisplayUpdate()
            return
        }

        // see if our op is a number or a symbol
        var tryParseNum = op.toIntOrNull()
        if (tryParseNum == null)
        {
            // op (I.E. +, -, /, etc)

            // take current number on screen and add to num buffer
            var tryParseFullDisplayNum = display.toFloatOrNull()
            if (tryParseFullDisplayNum == null)
            {
                // error state, display should only ever display numbers
                println("invalid display result")
                return
            }
            else
            {
                // take the number on the display and make it a num, then add to buf
                numBuffer.add(tryParseFullDisplayNum)
            }
            // add ops to buffer if we aren't computing a result
            if (op != "=")
            {
                opBuffer.add(op)
            }

            if (numBuffer.size == 2)
            {
                // we have more than 1 number, and can apply the operation
                var operation: String? = ""
                if (op == "=")
                {
                    operation = opBuffer.removeLastOrNull()
                }
                if (operation != null)
                {
                    // actually apply the operation and display the result
                    var opResult = ApplyOp(numBuffer.get(0), numBuffer.get(1), operation)
                    display = opResult
                    numBuffer.clear()
                    opBuffer.clear()
                    shouldClearDisplayInput = true
                }

            }

        }
        else
        {
            if (!opBuffer.isEmpty() && shouldClearDisplayInput)
            {
                // only clear previous number once we enter another number
                display = ""
                shouldClearDisplayInput = false
            }
            // number
            display = display.plus(op)
        }
        DisplayUpdate()

        println(this.numBuffer)
        println(this.opBuffer)
    }


    fun ApplyOp(num1: Float, num2: Float, op: String): String {
        var result = "invalid op"
        if (op == "+")
        {
            result = (num1+num2).toString()
        }
        else if (op == "-")
        {
            result = (num1-num2).toString()
        }
        else if (op == "*")
        {
            result = (num1*num2).toString()
        }
        else if (op == "/")
        {
            result = (num1/num2).toString()
        }
        if (result.endsWith(".0"))
        {
            result = result.split(".")[0].toInt().toString()
        }
        return result
    }

    companion object {
        var calc = Calculator()
        fun Instance(): Calculator {
            return calc
        }
    }
}