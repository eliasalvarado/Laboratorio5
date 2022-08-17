package com.example.laboratorio5

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class Detalles : AppCompatActivity() {
    private lateinit var nombre_rest: TextView
    private lateinit var direccion_rest: TextView
    private lateinit var horario_rest: TextView

    private lateinit var btn_llamar: MaterialButton
    private lateinit var btn_camara: MaterialButton
    private lateinit var rootLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)

        nombre_rest = findViewById(R.id.text_nombre_rest)
        direccion_rest = findViewById(R.id.text_direccion_rest)
        horario_rest = findViewById(R.id.text_horario_res)

        btn_llamar = findViewById(R.id.btn_llamar)
        btn_camara = findViewById(R.id.btn_iniciar_visita)
        rootLayout = findViewById(R.id.root_permissionActivity)

        val name_rest:String = intent.getSerializableExtra("nombre_restaurante") as String
        val direction_rest:String = intent.getSerializableExtra("direccion_restaurante") as String
        val schedule_rest:String = intent.getSerializableExtra("horario_restaurante") as String

        nombre_rest.text = "${name_rest}"
        direccion_rest.text = "${direction_rest}"
        horario_rest.text = "${schedule_rest}"

        initListeners()
    }

    private fun initListeners()
    {
        btn_llamar.setOnClickListener {
            // Tambien es un intent implicito
            val phoneNum = "+50230455525"
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNum"))
            startActivity(intent)
        }

        btn_camara.setOnClickListener {
            checkCameraPermission()
        }


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /* Código para el permiso de la cámara */

    override fun onRequestPermissionsResult(
        requestCode: Int, // El codigo que mandamos nosotros en el metodo requestPermissions
        permissions: Array<out String>, // Estos son los permisos que solicitamos
        grantResults: IntArray // Aqui vienen los valores (GRANTED o DENIED) de los N permisos que solicitamos
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Usamos el requestCode que enviamos en requestPermissions, en nuestro caso fue 0
        if (requestCode == 0 && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG", "${permissions[i]} was granted")
                }
            }
        }
    }


    // Método que retorna true en caso el usuario ya haya aceptado el permiso. False si no.
    private fun hasCameraPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    private fun checkCameraPermission() {
        if (!hasCameraPermission()) {
            checkRequestRationale()
        } else {
            Toast.makeText(this, "El permiso ya ha sido otorgado", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkRequestRationale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Verificamos si debemos mostrar algun mensale racional
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {

                // En mi caso, lo mostraré en un SnackBar. Asi crean uno básico
                val snackbar = Snackbar.make(
                    rootLayout,
                    "Acceso a cámara es necesario para iniciar la visita al restaurante.",
                    Snackbar.LENGTH_INDEFINITE
                )
                // Así pueden asignarle una acción (no es obligatorio, pero para nuestro caso sí lo será)
                snackbar.setAction("Ok"){
                    // Método a ejecutar al apachar el botón del snackbar
                    requestCameraPermission()
                }
                // Y por último, lo deben mostrar
                snackbar.show()
            } else {
                requestCameraPermission()
            }
        }
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this, // Siempre deben poner el contexto del activity
            arrayOf(Manifest.permission.CAMERA), // La lista con 1 o mas permisos a solicitar
            0 // Codigo que usan en onRequestPermissionsResult
        )
    }


}