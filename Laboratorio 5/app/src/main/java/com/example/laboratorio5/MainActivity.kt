package com.example.laboratorio5

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.laboratorio5.R
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var btn_iniciar: Button
    private lateinit var btn_Navigate: ImageButton
    private lateinit var btn_descargar: Button
    private lateinit var btn_detalles: Button

    private lateinit var nombre_rest: TextView
    private lateinit var direccion_rest: TextView
    private lateinit var horario_rest: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_iniciar = findViewById(R.id.button)
        btn_Navigate = findViewById(R.id.direc_rest)
        btn_descargar = findViewById(R.id.btn_descargar)
        btn_detalles = findViewById(R.id.btn_detalles)

        nombre_rest = findViewById(R.id.nombre_rest)
        direccion_rest = findViewById(R.id.direccion)
        horario_rest = findViewById(R.id.horario)

        initListeners()
    }



    private fun initListeners()
    {
        btn_iniciar.setOnClickListener{
            Toast.makeText(applicationContext, "Elías Alberto Alvarado Raxón", Toast.LENGTH_LONG).show()
        }

        btn_Navigate.setOnClickListener {
            // Explicito. Estamos pidiendo que use google maps
            val location = "https://www.google.com/maps/place/The+Choice+%5B더+초이스%5D/@14.5825591,-90.6753698,16z/data=!4m5!3m4!1s0x0:0xd3b676528e06ceb0!8m2!3d14.5828394!4d-90.6752518"
            //val location = "http://maps.google.com/maps?q=loc:14.603835110728907,-90.48925677205315"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(location))
            startActivity(intent)
        }

        btn_descargar.setOnClickListener {
            val app = "https://play.google.com/store/apps/details?id=com.whatsapp"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(app))
            startActivity(intent)
        }

        btn_detalles.setOnClickListener {
            val name_rest = nombre_rest.text.toString()
            val direction_rest = direccion_rest.text.toString()
            val schedule_rest = horario_rest.text.toString()

            val name ="$name_rest"
            val direction ="$direction_rest"
            val schedule ="$schedule_rest"

            val intent = Intent(this, Detalles::class.java)
            intent.putExtra("nombre_restaurante", name)
            intent.putExtra("direccion_restaurante", direction)
            intent.putExtra("horario_restaurante", schedule)
            startActivity(intent)
        }
    }


}