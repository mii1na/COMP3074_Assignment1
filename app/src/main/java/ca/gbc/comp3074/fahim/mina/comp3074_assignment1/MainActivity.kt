package ca.gbc.comp3074.fahim.mina.comp3074_assignment1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // Input fields
        val editHours = findViewById<EditText>(R.id.editHours)
        val editRate = findViewById<EditText>(R.id.editRate)
        val editTax = findViewById<EditText>(R.id.editTax)

        // Output fields
        val resultPay = findViewById<TextView>(R.id.resultPay)
        val resultOvertime = findViewById<TextView>(R.id.resultOvertime)
        val resultTotal = findViewById<TextView>(R.id.resultTotal)
        val resultTax = findViewById<TextView>(R.id.resultTax)

        // Buttons
        val buttonCalculate = findViewById<Button>(R.id.buttonCalculate)
        val buttonAbout = findViewById<Button>(R.id.buttonAbout)

        // Calculate button
        buttonCalculate.setOnClickListener {

            val hours = editHours.text.toString().toDoubleOrNull()
            val hourlyRate = editRate.text.toString().toDoubleOrNull()
            val taxRate = editTax.text.toString().toDoubleOrNull()

            // Check input values
            if (hours == null || hourlyRate == null || taxRate == null) {
                Toast.makeText(
                    this,
                    "Please enter all values",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (hours < 0 || hourlyRate < 0 || taxRate < 0 || taxRate > 1) {
                Toast.makeText(
                    this,
                    "Please enter valid values",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Calculate regular pay and overtime pay
            val pay: Double
            val overtimePay: Double

            if (hours <= 40) {
                pay = hours * hourlyRate
                overtimePay = 0.0
            } else {
                pay = 40 * hourlyRate
                overtimePay = (hours - 40) * hourlyRate * 1.5
            }

            // Calculate total pay and tax
            val totalPay = pay + overtimePay
            val tax = pay * taxRate

            // Display results
            val currency = NumberFormat.getCurrencyInstance(Locale.CANADA)

            resultPay.text = "Pay: ${currency.format(pay)}"
            resultOvertime.text = "Overtime Pay: ${currency.format(overtimePay)}"
            resultTotal.text = "Total Pay: ${currency.format(totalPay)}"
            resultTax.text = "Tax: ${currency.format(tax)}"
        }

        // About button
        buttonAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }
}
