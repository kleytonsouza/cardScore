package com.example.cardscore

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cardscore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)



        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun estatisticas(view: View) {}
    fun historico(view: View) {

    }
    fun novo_jogo(view: View) {
        val intent = Intent(this, MarcadorPontosActivity::class.java)
        startActivity(intent)
    }
    fun truco(view: View) {
        val intent = Intent(this, TrucoActivity::class.java)
        startActivity(intent)
    }
    fun buraco(view: View) {
        val intent = Intent(this, OutrosJogosActivity::class.java)
        startActivity(intent)
    }
    fun poker(view: View) {
        val intent = Intent(this, PokerActivity::class.java)
        startActivity(intent)
    }
    fun personalizado(view: View) {
        val intent = Intent(this, MarcadorPontosActivity::class.java)
        startActivity(intent)
    }
}