package vcmsa.projects.example1

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type

class MainActivity : AppCompatActivity() {

    private val labels = listOf("Math", "Science", "English", "History", "Art")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val inputValues = findViewById<EditText>(R.id.inputValues)
        val plotButton = findViewById<Button>(R.id.plotButton)
        val chartContainer = findViewById<LinearLayout>(R.id.chartContainer)

        plotButton.setOnClickListener {
            chartContainer.removeAllViews()

            val values = inputValues.text.toString()
                .split(",")
                .mapNotNull { it.trim().toFloatOrNull() }
                .take(5)

            if (values.size < 5) {
                inputValues.error = "Please enter 5 numbers"
                return@setOnClickListener
            }

            val max = values.maxOrNull() ?: 100f

            labels.zip(values).forEach { (label, value) ->
                val row = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }

                val labelView = TextView(this).apply {
                    text = label
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                }

                val barView = View(this).apply {
                    layoutParams = LinearLayout.LayoutParams(0, 40).apply {
                        weight = value / max
                        marginStart = 8
                    }
                    setBackgroundColor(Color.BLUE)
                }

                val valueView = TextView(this).apply {
                    text = value.toInt().toString()
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        marginStart = 8
                    }
                }

                row.addView(labelView)
                row.addView(barView)
                row.addView(valueView)

                chartContainer.addView(row)
            }
        }
    }
}
