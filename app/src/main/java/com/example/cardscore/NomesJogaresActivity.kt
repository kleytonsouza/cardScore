package com.example.cardscore

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cardscore.databinding.ActivityNomesJogaresBinding

@SuppressLint("SourceLockedOrientationActivity")
class NomesJogaresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNomesJogaresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityNomesJogaresBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun iniciarPartidaTruco(view: View) {
        val nomeUm = binding.nomeEquipeUm.text.toString().trim()
        val nomeDois = binding.nomeEquipeDois.text.toString().trim()

        var possuiErro = false

        if (nomeUm.isEmpty()) {
            binding.nomeEquipeUm.error = "Digite o nome da Equipe 1!"
            possuiErro = true
        }

        if (nomeDois.isEmpty()) {
            binding.nomeEquipeDois.error = "Digite o nome da Equipe 2!"
            possuiErro = true
        }

        if (possuiErro) {
            return
        }

        val intent = Intent(this, TrucoActivity::class.java)
        intent.putExtra("nomeEquipeUm", nomeUm)
        intent.putExtra("nomeEquipeDois", nomeDois)
        startActivity(intent)
        finish()
    }
}