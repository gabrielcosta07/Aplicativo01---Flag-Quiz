package br.ufpr.flagquiz

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        val nome = intent.getStringExtra("nome") ?: ""
        val pontos = intent.getIntExtra("pontos", 0)
        findViewById<TextView>(R.id.tvNome).text = getString(R.string.jogador, nome)
        findViewById<TextView>(R.id.tvPontos).text = getString(R.string.placar, pontos)

        findViewById<Button>(R.id.btnJogarNovamente).setOnClickListener {
            finish()
        }
    }
}
