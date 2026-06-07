package com.example.cardscore

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cardscore.databinding.ActivityMarcadorPontosBinding

@SuppressLint("SourceLockedOrientationActivity")
class MarcadorPontosActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMarcadorPontosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = ActivityMarcadorPontosBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if(intent.getStringExtra("tipoJogo") == "Buraco"){
            binding.nomeJogo.setText("Buraco")
            binding.pontuacaoMaxima.setText("1500")

        }
    }

    fun iniciarPartida(view: View) {
        val nomeUm = binding.nomeEquipeUm.text.toString().trim()
        val nomeDois = binding.nomeEquipeDois.text.toString().trim()
        val nomeDoJogo = binding.nomeJogo.text.toString().trim()
        val pontosMaximos = binding.pontuacaoMaxima.text.toString().trim()

        var possuiErro = false

        if (nomeUm.isEmpty()) {
            binding.nomeEquipeUm.error = "Digite o nome da Equipe 1!"
            possuiErro = true
        }

        if (nomeDois.isEmpty()) {
            binding.nomeEquipeDois.error = "Digite o nome da Equipe 2!"
            possuiErro = true
        }

        if (nomeDoJogo.isEmpty()) {
            binding.nomeJogo.error = "Digite o nome do jogo!"
            possuiErro = true
        }

        if (pontosMaximos.isEmpty() || (pontosMaximos.toIntOrNull() ?: 0) <= 0) {
            binding.pontuacaoMaxima.error = "Digite uma pontuação máxima válida!"
            possuiErro = true
        }

        if (possuiErro) {
            return
        }

        val intent = Intent(this, OutrosJogosActivity::class.java)
        intent.putExtra("nomeEquipeUm", nomeUm)
        intent.putExtra("nomeEquipeDois", nomeDois)
        intent.putExtra("tipoJogo", nomeDoJogo)
        intent.putExtra("pontuacaoMaxima", pontosMaximos)
        startActivity(intent)

        finish()

    }

}