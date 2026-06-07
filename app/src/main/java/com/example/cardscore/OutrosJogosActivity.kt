package com.example.cardscore

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cardscore.databinding.ActivityOutrosJogosBinding

class OutrosJogosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutrosJogosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOutrosJogosBinding.inflate(layoutInflater)


        enableEdgeToEdge()
        setContentView(binding.root)

        binding.nomeEquipe1.text = intent.getStringExtra("nomeEquipeUm")
        binding.nomeEquipe2.text = intent.getStringExtra("nomeEquipeDois")
        binding.tipoJogo.text = intent.getStringExtra("tipoJogo")
        binding.pontuacaoMaxima.text = intent.getStringExtra("pontuacaoMaxima")


    }

    fun adicionarPontuacaoEquipeUm(view: View) {}
    fun adicionarPontuacaoEquipeDois(view: View) {}
    fun reverterUltimaPontuacao(view: View) {}
    fun reiniciarPartida(view: View) {}
    fun finalizarPartida(view: View) {}
}