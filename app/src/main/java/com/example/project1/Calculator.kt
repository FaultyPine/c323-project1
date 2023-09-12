package com.example.project1

import android.util.Log
import android.widget.TextView
import java.util.LinkedList
import java.util.Queue
import java.util.Stack
import java.lang.Math
import kotlin.math.ln

class Calculator {
    // storage for numbers we're operating on
    var numBuffer = ArrayList<Float>()
    // storage for operands we will apply to numbers
    var opBuffer = ArrayList<String>()
    // text displayed on calculator textview
    var display = "0"
    var shouldClearDisplayInput = true
    // main textview of the calculator
    var mainTextView: TextView? = null

    var justAddedOp = true
    var justComputedOp = false

    /**
     * this caches the TextView that we will display our results to
     * @param mainTextView The display textview to cache
     */
    fun SetMainDisplay(mainTextView: TextView)
    {
        this.mainTextView = mainTextView
    }

    /**
     * updates the cached textview with the current display string
     */
    fun DisplayUpdate()
    {
        mainTextView?.setText(display)
    }

    /**
     * clear all buffered operations/numbers and clear display
     *
      */
    fun Clear() {
        display = "0"
        justAddedOp = true
        justComputedOp = false
        numBuffer.clear()
        opBuffer.clear()
        DisplayUpdate()
    }

    /**
     * an overload for PushOp(string) that takes a char
     * casts the char to a string and passes it to the other overload
     */
    fun PushOp(op: Char) {
        PushOp(op.toString())
    }

    /**
     * main logic functino of the calculator.
     * @param op a string representing the button pressed on the calculator GUI
     * updates the cached textview display with the result of the current operation/action
     */
    fun PushOp(op: String) {
        Log.v("[DEBUG]","Button pushed: $op");
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
        else if (op == ".")
        {
            display = display.plus(".");
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
        else if (op == "cos")
        {
            var tryParseNum = display.toFloatOrNull()
            if (tryParseNum != null)
            {
                display = (Math.cos(tryParseNum.toDouble())).toString()
            }
            DisplayUpdate()
            return
        }
        else if (op == "sin")
        {
            var tryParseNum = display.toFloatOrNull()
            if (tryParseNum != null)
            {
                display = (Math.sin(tryParseNum.toDouble())).toString()
            }
            DisplayUpdate()
            return
        }
        else if (op == "tan")
        {
            var tryParseNum = display.toFloatOrNull()
            if (tryParseNum != null)
            {
                display = (Math.tan(tryParseNum.toDouble())).toString()
            }
            DisplayUpdate()
            return
        }
        else if (op == "ln")
        {
            var tryParseNum = display.toFloatOrNull()
            if (tryParseNum != null)
            {
                if (tryParseNum > 0)
                    display = (ln(tryParseNum.toDouble())).toString()
            }
            DisplayUpdate()
            return
        }
        else if (op == "Log 10")
        {
            var tryParseNum = display.toFloatOrNull()
            if (tryParseNum != null)
            {
                if (tryParseNum > 0)
                    display = (Math.log10(tryParseNum.toDouble())).toString()
            }
            DisplayUpdate()
            return
        }

        var num = op.toIntOrNull()
        if (num == null)
        {
            // add current display number to num buf
            var tryParseFullDisplayNum = display.toFloatOrNull()
            if (tryParseFullDisplayNum == null)
            {
                // error state, display should only ever display numbers
                Log.e("[ERROR]", "invalid display result")
                return
            }
            else if (!justComputedOp && !justAddedOp)
            {
                // take the number on the display and make it a num, then add to buf
                numBuffer.add(tryParseFullDisplayNum)
                //println("added " + tryParseFullDisplayNum + " to num buf")
            }
            if (op != "=" && op != "." && !justAddedOp)
            {
                opBuffer.add(op)
                justAddedOp = true
                justComputedOp = false
                //println("added " + op + " to op buf")
            }
            if (numBuffer.size > 1 && opBuffer.isNotEmpty())
            {
                DoOperation()
            }
        }
        else
        {
            if (justAddedOp)
            {
                display = ""
                justAddedOp = false
                //println("Clear display")
            }
            display = display.plus(op)
            justComputedOp = false
        }
        DisplayUpdate()
        //println("numBuf = $numBuffer  opBuf = $opBuffer")
    }

    /**
     * computes the current result based on the contents of our numBuffer and opBuffer.
     * displays results to the screen and manages internal calculation state
     */
    fun DoOperation()
    {
        //println("Doing operation... numBuf = $numBuffer  opBuf = $opBuffer")
        var op1 = numBuffer.removeFirst()
        var op2 = numBuffer.removeFirst()
        var operation: String = opBuffer.removeFirstOrNull() ?: return
        var opResult = ApplyOp(op1, op2, operation)
        numBuffer.add(opResult)
        //println("result = $opResult")
        //println("bufs after operation: numBuf = $numBuffer  opBuf = $opBuffer")
        display = opResult.toString()
        shouldClearDisplayInput = true
        justComputedOp = true
    }

    /**
     * computes the result of some operation on two operands
     * @param num1 first operand
     * @param num2 second operand
     * @param op operation to apply to the two operands
     * @return Float returns the result of the operation applied to the two operands
     */
    fun ApplyOp(num1: Float, num2: Float, op: String) : Float
    {
        var result = 0.0f
        if (op == "+")
        {
            result = (num1+num2)
        }
        else if (op == "-")
        {
            result = (num1-num2)
        }
        else if (op == "*" || op == "x")
        {
            result = (num1*num2)
        }
        else if (op == "/")
        {
            result = (num1/num2)
        }
        return result
    }

    /**
     * a static singleton object used to access the state of this calculator
     */
    companion object {
        var calc = Calculator()
        fun Instance(): Calculator {
            return calc
        }
    }


}