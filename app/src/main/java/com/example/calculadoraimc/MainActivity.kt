package com.example.calculadoraimc

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    fun hideKeyboard() {
        val view : View? = currentFocus
        view ?.let {
            val imm  =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

    }
    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.calcIMc).setOnClickListener {
            calcular_IMC();
            hideKeyboard();

        }
        findViewById<Button>(R.id.limparDados).setOnClickListener {
            findViewById<EditText>(R.id.altura).text = null;
            findViewById<EditText>(R.id.peso).text = null;
            findViewById<TextView>(R.id.resultado).text = null;
            findViewById<TextView>(R.id.classificacao).text = null;
        }
    }

    private fun calcular_IMC() {
        var altura : Double? = findViewById<EditText>(R.id.altura).text.toString().toDoubleOrNull();
        var peso : Double? = findViewById<EditText>(R.id.peso).text.toString().toDoubleOrNull();
        var calc : Double? = 0.0;


        fun checkIMC(value: Double): String{
            return when(value){
                in 0..17 -> "Muito abaixo do peso"
                in 17.0..18.49 -> "Abaixo do peso"
                in 18.5..24.99 -> "Peso normal"
                in 25.0..29.99 -> "Acima do peso"
                in 30.0..34.99 -> "Obesidade I"
                in 35.0..39.99 -> "Obesidade II"
                else -> "Obesidade III"
            }
        }

        if(altura == null|| peso == null){
            Toast.makeText(applicationContext,"Todos os campos precisam ser inseridos",Toast.LENGTH_SHORT).show();
        }
        else {
            calc = (peso / altura.pow(2))
            var imcRounded = calc.round(2)

            Toast.makeText(applicationContext,"Sucesso",Toast.LENGTH_SHORT).show();

            findViewById<TextView>(R.id.resultado).text = "O resultado é: $imcRounded";
            findViewById<TextView>(R.id.classificacao).text = checkIMC(calc)
        }

    }

}