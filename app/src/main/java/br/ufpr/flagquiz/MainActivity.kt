package br.ufpr.flagquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNome = findViewById<EditText>(R.id.etNome)
        findViewById<Button>(R.id.btnComecar).setOnClickListener {
            val nome = etNome.text.toString().trim()
            if (nome.isEmpty()) {
                Toast.makeText(this, R.string.nome_vazio, Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra("nome", nome)
                startActivity(intent)
            }
        }
    }
}
