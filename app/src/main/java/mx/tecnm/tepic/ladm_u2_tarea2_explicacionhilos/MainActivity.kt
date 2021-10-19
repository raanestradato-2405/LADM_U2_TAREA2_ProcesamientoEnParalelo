package mx.tecnm.tepic.ladm_u2_tarea2_explicacionhilos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       var hiloGlobal = Hilo(this)

        btniniciar.setOnClickListener {
            try {
                val hilo = Hilo(this)
                   hiloGlobal = hilo
                   hilo.start()
                   btniniciar.isEnabled = false

            } catch (error: Exception) {
                Toast.makeText(this, "Este Hilo Ya Hizo Start", Toast.LENGTH_LONG)
                    .show()
            }
        }

        btnpausar.setOnClickListener {
            hiloGlobal.pausar()
        }

        btnreiniciar.setOnClickListener {
            hiloGlobal.terminar()
            crono.text = "00 : 00 : 00 : 000"
            btniniciar.isEnabled = true
        }


    }
}

class Hilo(p:MainActivity) : Thread() {

    val puntero = p
    var pausado = false
    var continuar = true
    var mili = 0;
    var seg = 0;
    var minutos = 0;
    var horas = 0;
    var h = "";
    var m = "";
    var s = "";
    var mi = "";

    fun pausar() {
        pausado = !pausado
    }

    fun terminar() {
        continuar = false
        mili = 0
        seg = 0
        minutos = 0
        horas = 0

        puntero.crono.text ="00 : 00 : 00 : 000"

    }

    override fun run() {
        super.run()

        while (continuar) {
            if (pausado == false) {
                try {
                    sleep(1)
                } catch (io: InterruptedException) {
                    io.printStackTrace()
                }
                mili++;

                if (mili == 999) {
                    seg++;
                    mili = 0;
                }

                if (seg == 59) {
                    minutos++;
                    seg = 0;
                }
                if (minutos == 59 && seg == 59) {
                    minutos = 0;
                    seg = 0;
                    horas++;
                }

                if (mili < 10) {
                    mi = "00" + mili;
                } else if (mili < 100) {
                    mi = "0" + mili;
                } else {
                    mi = "" + mili;
                }
                if (seg < 10) {
                    s = "0" + seg
                } else {
                    s = "" + seg
                }
                if (minutos < 10) {
                    m = "0" + minutos
                } else {
                    m = "" + minutos
                }
                if (horas < 10) {
                    h = "0" + horas
                } else {
                    h = "" + horas
                }
                puntero.runOnUiThread {
                    puntero.crono.text = "${h} : ${m} : ${s} : ${mi}"
                }
            }

        }
    }
}