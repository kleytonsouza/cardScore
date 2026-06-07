package com.example.cardscore

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cardscore.databinding.ActivityMainBinding

@SuppressLint("SourceLockedOrientationActivity")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    fun novo_jogo(view: View) {
        val intent = Intent(this, MarcadorPontosActivity::class.java)
        intent.putExtra("tipoJogo", "Novo Jogo")
        startActivity(intent)
    }
    fun truco(view: View) {
        val intent = Intent(this, NomesJogaresActivity::class.java)
        intent.putExtra("tipoJogo", "Truco")
        startActivity(intent)
    }
    fun buraco(view: View) {
        val intent = Intent(this, MarcadorPontosActivity::class.java)
        intent.putExtra("tipoJogo", "Buraco")
        startActivity(intent)
    }
    fun poker(view: View) {
        val intent = Intent(this, PokerActivity::class.java)
        startActivity(intent)
    }
    fun personalizado(view: View) {
        val intent = Intent(this, MarcadorPontosActivity::class.java)
        intent.putExtra("tipoJogo", "Personalizado")
        startActivity(intent)
    }
}