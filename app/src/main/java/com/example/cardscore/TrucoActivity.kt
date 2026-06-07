package com.example.cardscore

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cardscore.databinding.ActivityTrucoBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("SourceLockedOrientationActivity")
class TrucoActivity : AppCompatActivity() {

    private var nomeEquipeUm: String = "Equipe 1"
    private var nomeEquipeDois: String = "Equipe 2"
    private lateinit var binding: ActivityTrucoBinding

    private val historicoPlacares = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityTrucoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nomeEquipeUm = intent.getStringExtra("nomeEquipeUm").toString()
        nomeEquipeDois = intent.getStringExtra("nomeEquipeDois").toString()

        binding.nomeEquipeUm.text = nomeEquipeUm
        binding.nomeEquipeDois.text = nomeEquipeDois
        binding.nomeEquipeUmPontos.text = getString(R.string.add_pontos_para_equipe_1, nomeEquipeUm)
        binding.nomeEquipeDoisPontos.text = getString(R.string.add_pontos_para_equipe_2, nomeEquipeDois)

    }

    fun addPontosEquipeUm(view: View) {
        val pontosAAdicionar = when (view.id) {
            R.id.add_um_pontos_equipe_um -> 1
            R.id.add_tres_pontos_equipe_um -> 3
            R.id.add_seis_pontos_equipe_um -> 6
            R.id.add_nove_pontos_equipe_um -> 9
            R.id.add_doze_pontos_equipe_um -> 12
            else -> 0
        }
        atualizarPlacarEquipeUm(pontosAAdicionar)
    }

    private fun atualizarPlacarEquipeUm(pontos: Int) {
        val placarAtual1 = binding.placarPontosEquipeUm.text.toString()
        val placarAtual2 = binding.placarPontosEquipeDois.text.toString()

        historicoPlacares.add("$placarAtual1-$placarAtual2")

        val novoPlacar = (placarAtual1.toIntOrNull() ?: 0) + pontos
        binding.placarPontosEquipeUm.text = novoPlacar.toString()

        if (novoPlacar >= 12) {
            jogoFinalizado(nomeEquipeUm)
        }
    }

    fun addPontosEquipeDois(view: View) {
        val pontosAAdicionar = when (view.id) {
            R.id.add_um_pontos_equipe_dois -> 1
            R.id.add_tres_pontos_equipe_dois -> 3
            R.id.add_seis_pontos_equipe_dois -> 6
            R.id.add_nove_pontos_equipe_dois -> 9
            R.id.add_doze_pontos_equipe_dois -> 12
            else -> 0
        }
        atualizarPlacarEquipeDois(pontosAAdicionar)
    }

    private fun atualizarPlacarEquipeDois(pontos: Int) {
        val placarAtual1 = binding.placarPontosEquipeUm.text.toString()
        val placarAtual2 = binding.placarPontosEquipeDois.text.toString()

        historicoPlacares.add("$placarAtual1-$placarAtual2")

        val novoPlacar = (placarAtual2.toIntOrNull() ?: 0) + pontos
        binding.placarPontosEquipeDois.text = novoPlacar.toString()

        if (novoPlacar >= 12) {
            jogoFinalizado(nomeEquipeDois)
        }
    }

    @SuppressLint("SetTextI18n")
    fun jogoFinalizado(nomeVencedor: String) {
        val placarUm = binding.placarPontosEquipeUm.text.toString()
        val placarDois = binding.placarPontosEquipeDois.text.toString()

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

                if (historicoAtual == getString(R.string.nenhuma_jogada_ainda)) {
                    binding.historicoResultado.text = novaLinha
                    binding.historicoResultado.setTypeface(null, android.graphics.Typeface.NORMAL)
                } else {
                    binding.historicoResultado.text = "$novaLinha\n$historicoAtual"
                }
                binding.placarPontosEquipeUm.text = "0"
                binding.placarPontosEquipeDois.text = "0"

                historicoPlacares.clear()
                dialog.dismiss()
            }

            .setNegativeButton("Novas Equipes") { dialog, _ ->
                val intent = Intent(this, NomesJogaresActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }

            .setNeutralButton("Voltar para Home") { dialog, _ ->
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            .show()
    }

    fun reverterUltimaPontuacao(view: View) {
        if (historicoPlacares.isNotEmpty()) {
            val ultimoRegistro = historicoPlacares.removeAt(historicoPlacares.size - 1)
            val placares = ultimoRegistro.split("-")

            binding.placarPontosEquipeUm.text = placares[0]
            binding.placarPontosEquipeDois.text = placares[1]
        }
    }

    fun reiniciarPartida(view: View) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Reiniciar Partida?")
            .setMessage("Tem certeza que deseja zerar o placar atual?")
            .setPositiveButton("Sim, zerar") { dialog, _ ->
                binding.placarPontosEquipeUm.text = "0"
                binding.placarPontosEquipeDois.text = "0"
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
                val p1 = binding.placarPontosEquipeUm.text.toString().toIntOrNull() ?: 0
                val p2 = binding.placarPontosEquipeDois.text.toString().toIntOrNull() ?: 0

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

}