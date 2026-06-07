package com.example.cardscore

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cardscore.databinding.ActivityOutrosJogosBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("SourceLockedOrientationActivity")
class OutrosJogosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutrosJogosBinding

    private var nomeEquipeUm: String = "Equipe 1"
    private var nomeEquipeDois: String = "Equipe 2"
    private var pontuacaoMaximaJogo = 0

    private val historicoPlacares = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityOutrosJogosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nomeEquipeUm = intent.getStringExtra("nomeEquipeUm") ?: "Equipe 1"
        nomeEquipeDois = intent.getStringExtra("nomeEquipeDois") ?: "Equipe 2"

        val pontuacaoStr = intent.getStringExtra("pontuacaoMaxima") ?: "0"
        pontuacaoMaximaJogo = pontuacaoStr.toIntOrNull() ?: 0

        binding.nomeEquipe1.text = nomeEquipeUm
        binding.nomeEquipe2.text = nomeEquipeDois
        binding.placarNomeEquipeUm.text = nomeEquipeUm
        binding.placarNomeEquipeDois.text = nomeEquipeDois
        binding.tipoJogo.text = intent.getStringExtra("tipoJogo")
        binding.pontuacaoMaxima.text = pontuacaoStr

    }

    fun adicionarPontuacaoEquipeUm(view: View) {
        val pontosDigitados = binding.pontosEquipeUm.text.toString().toIntOrNull() ?: 0

        if (pontosDigitados <= 0) {
            binding.pontosEquipeUm.error = "Digite uma pontuação!"
            return
        }

        val placarAtual1 = binding.pontuacaoEquipe1.text.toString()
        val placarAtual2 = binding.pontuacaoEquipe2.text.toString()

        historicoPlacares.add("$placarAtual1-$placarAtual2")

        val novoPlacar = (placarAtual1.toIntOrNull() ?: 0) + pontosDigitados
        binding.pontuacaoEquipe1.text = novoPlacar.toString()
        binding.pontosEquipeUm.text?.clear()

        if (novoPlacar >= pontuacaoMaximaJogo) {
            jogoFinalizado(nomeEquipeUm)
        }
    }

    fun adicionarPontuacaoEquipeDois(view: View) {
        val pontosDigitados = binding.pontosEquipeDois.text.toString().toIntOrNull() ?: 0

        if (pontosDigitados <= 0) {
            binding.pontosEquipeDois.error = "Digite uma pontuação!"
            return
        }

        val placarAtual1 = binding.pontuacaoEquipe1.text.toString()
        val placarAtual2 = binding.pontuacaoEquipe2.text.toString()

        historicoPlacares.add("$placarAtual1-$placarAtual2")

        val novoPlacar = (placarAtual2.toIntOrNull() ?: 0) + pontosDigitados
        binding.pontuacaoEquipe2.text = novoPlacar.toString()
        binding.pontosEquipeDois.text?.clear()

        if (novoPlacar >= pontuacaoMaximaJogo) {
            jogoFinalizado(nomeEquipeDois)
        }
    }

    fun reverterUltimaPontuacao(view: View) {
        if (historicoPlacares.isNotEmpty()) {
            val ultimoRegistro = historicoPlacares.removeAt(historicoPlacares.size - 1)
            val placares = ultimoRegistro.split("-")

            binding.pontuacaoEquipe1.text = placares[0]
            binding.pontuacaoEquipe2.text = placares[1]
        }
    }

    fun reiniciarPartida(view: View) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Reiniciar Partida?")
            .setMessage("Tem certeza que deseja zerar o placar atual?")
            .setPositiveButton("Sim, zerar") { dialog, _ ->
                binding.pontuacaoEquipe1.text = "0"
                binding.pontuacaoEquipe2.text = "0"
                historicoPlacares.clear()
                dialog.dismiss()
            }
            .setNegativeButton("Não", null)
            .show()
    }

    fun finalizarPartida(view: View) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Finalizar Partida?")
            .setMessage("Deseja encerrar o jogo agora? Quem tiver mais pontos ganhará!")
            .setPositiveButton("Sim, finalizar") { dialog, _ ->
                val p1 = binding.pontuacaoEquipe1.text.toString().toIntOrNull() ?: 0
                val p2 = binding.pontuacaoEquipe2.text.toString().toIntOrNull() ?: 0

                if (p1 > p2) {
                    jogoFinalizado(nomeEquipeUm)
                } else if (p2 > p1) {
                    jogoFinalizado(nomeEquipeDois)
                } else {
                    jogoFinalizado("Empate")
                }
                dialog.dismiss()
            }
            .setNegativeButton("Não", null)
            .show()
    }

    @SuppressLint("SetTextI18n")
    fun jogoFinalizado(nomeVencedor: String) {
        val placarUm = binding.pontuacaoEquipe1.text.toString()
        val placarDois = binding.pontuacaoEquipe2.text.toString()

        val mensagemAlerta = if (nomeVencedor == "Empate") {
            "O jogo terminou empatado em $placarUm x $placarDois!"
        } else {
            "A equipe $nomeVencedor ganhou a partida!"
        }

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Fim de Jogo!")
            .setMessage(mensagemAlerta)
            .setCancelable(false)
            .setPositiveButton("Novo Jogo") { dialog, _ ->
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                val dataHora = sdf.format(Date())

                val novaLinha = if (nomeVencedor == "Empate") {
                    "Jogo Empatado ($placarUm x $placarDois) - $dataHora"
                } else {
                    "$nomeVencedor ganhou ($placarUm x $placarDois) - $dataHora"
                }

                val historicoAtual = binding.historicoResultado.text.toString()
                if (historicoAtual == getString(R.string.nenhuma_jogada_ainda) || historicoAtual == "Nenhuma jogada ainda") {
                    binding.historicoResultado.text = novaLinha
                    binding.historicoResultado.setTypeface(null, android.graphics.Typeface.NORMAL)
                } else {
                    binding.historicoResultado.text = "$novaLinha\n$historicoAtual"
                }

                binding.pontuacaoEquipe1.text = "0"
                binding.pontuacaoEquipe2.text = "0"
                historicoPlacares.clear()

                dialog.dismiss()
            }
            .setNegativeButton("Novas Equipes") { dialog, _ ->
                val intent = Intent(this, MarcadorPontosActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                dialog.dismiss()
                finish()
            }

            .setNeutralButton("Voltar para Home") { dialog, _ ->
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                dialog.dismiss()
                finish()
            }
            .show()
    }

}