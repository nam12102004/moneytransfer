package com.example.myapplication
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextAmount: EditText
    private lateinit var spinnerFromCurrency: Spinner
    private lateinit var spinnerToCurrency: Spinner
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextAmount = findViewById(R.id.editTextAmount)
        spinnerFromCurrency = findViewById(R.id.spinnerFromCurrency)
        spinnerToCurrency = findViewById(R.id.spinnerToCurrency)
        textViewResult = findViewById(R.id.textViewResult)
        val buttonConvert = findViewById<Button>(R.id.buttonConvert)

        ArrayAdapter.createFromResource(
            this,
            R.array.currency_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFromCurrency.adapter = adapter
            spinnerToCurrency.adapter = adapter
        }

        buttonConvert.setOnClickListener {
            convertCurrency()
        }
    }

    private fun convertCurrency() {
        val fromCurrency = spinnerFromCurrency.selectedItem.toString()
        val toCurrency = spinnerToCurrency.selectedItem.toString()
        val amountString = editTextAmount.text.toString()

        if (amountString.isEmpty()) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountString.toDouble()
        val conversionRate = getConversionRate(fromCurrency, toCurrency)
        val result = amount * conversionRate

        textViewResult.text = "Result: $result $toCurrency"
    }

    private fun getConversionRate(fromCurrency: String, toCurrency: String): Double {
        return when ("${fromCurrency}_$toCurrency") {
            "USD_EUR" -> 0.85
            "USD_VND" -> 24000.0
            "EUR_USD" -> 1.18
            "VND_USD" -> 0.000042
            "USD_JPY" -> 110.0
            "JPY_USD" -> 0.0091
            "GBP_USD" -> 1.38
            "USD_GBP" -> 0.72
            else -> 1.0
        }
    }
}