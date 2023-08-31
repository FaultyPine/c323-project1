package com.example.project1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Calculator.Instance().SetMainDisplay(findViewById<TextView>(R.id.textView));
        var constraintLayoutRoot = findViewById<ConstraintLayout>(R.id.root);
        for (i in 0 until constraintLayoutRoot.getChildCount()) {
            val child: View = constraintLayoutRoot.getChildAt(i)
            if (child is Button) {
                child.setOnClickListener {
                    Calculator.Instance().PushOp(child.text.toString());
                }
            }
        }

    }
}