package br.ufpr.flagquiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

data class Bandeira(val imagem: Int, val pais: String)

class QuizActivity : AppCompatActivity() {
    private val bandeiras = listOf(
        Bandeira(R.drawable.flag_brasil, "Brasil"),
        Bandeira(R.drawable.flag_franca, "França"),
        Bandeira(R.drawable.flag_japao, "Japão"),
        Bandeira(R.drawable.flag_argentina, "Argentina"),
        Bandeira(R.drawable.flag_alemanha, "Alemanha"),
        Bandeira(R.drawable.flag_italia, "Itália"),
        Bandeira(R.drawable.flag_espanha, "Espanha"),
        Bandeira(R.drawable.flag_portugal, "Portugal"),
        Bandeira(R.drawable.flag_canada, "Canadá"),
        Bandeira(R.drawable.flag_mexico, "México"),
        Bandeira(R.drawable.flag_china, "China"),
        Bandeira(R.drawable.flag_india, "Índia"),
        Bandeira(R.drawable.flag_australia, "Austrália"),
        Bandeira(R.drawable.flag_suica, "Suíça"),
        Bandeira(R.drawable.flag_grecia, "Grécia")
    )

    private lateinit var ordem: IntArray
    private var indice = 0
    private var pontos = 0
    private var respondida = false
    private var acertou = false

    private lateinit var tvContador: TextView
    private lateinit var imgFlag: ImageView
    private lateinit var etResposta: EditText
    private lateinit var btnResponder: Button
    private lateinit var tvFeedback: TextView
    private lateinit var btnProxima: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        tvContador = findViewById(R.id.tvContador)
        imgFlag = findViewById(R.id.imgFlag)
        etResposta = findViewById(R.id.etResposta)
        btnResponder = findViewById(R.id.btnResponder)
        tvFeedback = findViewById(R.id.tvFeedback)
        btnProxima = findViewById(R.id.btnProxima)

        val ordemSalva = savedInstanceState?.getIntArray("ordem")
        if (savedInstanceState == null || ordemSalva == null) {
            ordem = bandeiras.indices.shuffled().take(5).toIntArray()
        } else {
            ordem = ordemSalva
            indice = savedInstanceState.getInt("indice")
            pontos = savedInstanceState.getInt("pontos")
            respondida = savedInstanceState.getBoolean("respondida")
            acertou = savedInstanceState.getBoolean("acertou")
            etResposta.setText(savedInstanceState.getString("resposta", ""))
        }

        btnResponder.setOnClickListener { verificarResposta() }
        btnProxima.setOnClickListener { proximaPergunta() }
        mostrarPergunta()
    }

    private fun mostrarPergunta() {
        val bandeira = bandeiras[ordem[indice]]
        tvContador.text = getString(R.string.contador, indice + 1)
        imgFlag.setImageResource(bandeira.imagem)
        etResposta.isEnabled = !respondida
        btnResponder.isEnabled = !respondida
        btnProxima.isEnabled = respondida
        btnProxima.setText(
            if (indice == 4) R.string.ver_resultado else R.string.proxima
        )

        if (!respondida) {
            tvFeedback.visibility = View.GONE
        } else {
            tvFeedback.visibility = View.VISIBLE
            if (acertou) {
                tvFeedback.setText(R.string.correto)
                tvFeedback.setTextColor(getColor(R.color.verde))
            } else {
                tvFeedback.text = getString(R.string.incorreto, bandeira.pais)
                tvFeedback.setTextColor(getColor(R.color.vermelho))
            }
        }
    }

    private fun verificarResposta() {
        if (respondida) return

        val resposta = etResposta.text.toString().trim()
        if (resposta.isEmpty()) {
            Toast.makeText(this, R.string.resposta_vazia, Toast.LENGTH_SHORT).show()
            return
        }

        val paisCorreto = bandeiras[ordem[indice]].pais
        acertou = resposta.equals(paisCorreto, ignoreCase = true)
        if (acertou) {
            pontos += 20
        }
        respondida = true
        mostrarPergunta()
    }

    private fun proximaPergunta() {
        if (!respondida || isFinishing) return

        if (indice == 4) {
            val intent = Intent(this, ResultadoActivity::class.java)
            intent.putExtra("nome", this.intent.getStringExtra("nome"))
            intent.putExtra("pontos", pontos)
            startActivity(intent)
            finish()
        } else {
            indice++
            respondida = false
            acertou = false
            etResposta.setText("")
            mostrarPergunta()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray("ordem", ordem)
        outState.putInt("indice", indice)
        outState.putInt("pontos", pontos)
        outState.putBoolean("respondida", respondida)
        outState.putBoolean("acertou", acertou)
        outState.putString("resposta", etResposta.text.toString())
    }
}
