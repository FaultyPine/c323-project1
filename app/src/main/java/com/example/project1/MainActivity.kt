package com.example.project1

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {

    /**
     * sets the click callbacks of all our buttons in the scene.
     * Passes the button's text to our calculator for computation and updating
     * @param parent the root viewgroup of our xml
     */
    fun setCallbacks(parent: ViewGroup) {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child is ViewGroup) {
                setCallbacks(child)
            } else {
                if (child != null) {
                    val child: View = parent.getChildAt(i)
                    if (child is Button) {
                        child.setOnClickListener {
                            Calculator.Instance().PushOp(child.text.toString());
                        }
                    }
                }
            }
        }
    }

    /**
     * an activity callback that signals the creation of our GUI. Runs at the start of the app
     * and when the phone rotates.
     * @param savedInstanceState a buffer of arbitrary data used for serializing/deserializing data
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Calculator.Instance().SetMainDisplay(findViewById<TextView>(R.id.textView));
        Calculator.Instance().DisplayUpdate()
        var constraintLayoutRoot = findViewById<LinearLayout>(R.id.root);
        setCallbacks(constraintLayoutRoot)
    }
}