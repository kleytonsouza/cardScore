package com.example.cardscore

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cardscore.databinding.ActivityMainBinding
import com.example.cardscore.databinding.ActivityMarcadorPontosBinding
import com.example.cardscore.databinding.ActivityTrucoBinding

class MarcadorPontosActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMarcadorPontosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMarcadorPontosBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

    fun iniciarPartida(view: View) {
        val intent = Intent(this, OutrosJogosActivity::class.java)
        intent.putExtra("nomeEquipeUm", binding.nomeEquipeUm.text.toString())
        intent.putExtra("nomeEquipeDois", binding.nomeEquipeDois.text.toString())
        intent.putExtra("tipoJogo", binding.nomeJogo.text.toString())
        intent.putExtra("pontuacaoMaxima", binding.pontuacaoMaxima.text.toString())
        startActivity(intent)

    }

}