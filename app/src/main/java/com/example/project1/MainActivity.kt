package com.example.project1

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {

    fun setCallbacks(parent: ViewGroup) {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child is ViewGroup) {
                setCallbacks(child)
                // DO SOMETHING WITH VIEWGROUP, AFTER CHILDREN HAS BEEN LOOPED
            } else {
                if (child != null) {
                    // DO SOMETHING WITH VIEW
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
        var constraintLayoutRoot = findViewById<ConstraintLayout>(R.id.root);
        setCallbacks(constraintLayoutRoot)


        /*
        for (i in 0 until constraintLayoutRoot.getChildCount()) {
            val child: View = constraintLayoutRoot.getChildAt(i)
            if (child is Button) {
                child.setOnClickListener {
                    Calculator.Instance().PushOp(child.text.toString());
                }
            }
        }
        */

    }
}