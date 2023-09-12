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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Calculator.Instance().SetMainDisplay(findViewById<TextView>(R.id.textView));
        Calculator.Instance().DisplayUpdate()
        var constraintLayoutRoot = findViewById<LinearLayout>(R.id.root);
        setCallbacks(constraintLayoutRoot)
    }
}