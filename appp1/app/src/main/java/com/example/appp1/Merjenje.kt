package com.example.appp1

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_STATUS_ACCURACY_HIGH
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class Merjenje : AppCompatActivity(), SensorEventListener {
    lateinit var sensorManager:SensorManager
    var mAccelometer: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_merjenje)
        /*nek komentar, om ga pol dodal*/

        //vzpostavimo upravljalec senzorjev
        sensorManager= getSystemService(Context.SENSOR_SERVICE) as SensorManager

        //definiramo, kateri senzor želimo vzpostaviti
        mAccelometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        //če senzor registriramo, pomeni da ga zaženemo oz. povemo naj se vklopi
        //in obratno z odregistriranjem
        sensorManager.registerListener(this, mAccelometer, SensorManager.SENSOR_DELAY_NORMAL)

        //Tu sem se odločil še, naj program preveri, če naprava sploh ima željeni senzor
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)!=null){
            findViewById<TextView>(R.id.zaznaj).apply{
                text = "pospeškomer je"
            }
        }
        else{
            findViewById<TextView>(R.id.zaznaj).apply{
                text = "pospeškomera ni"
            }
        }
        /*definiramo 2 gumba, za vklapljanje in izklapljanje senzorja, dodamo
        * še natančnost*/
        val start = findViewById<Button>(R.id.meri)
        val stop = findViewById<Button>(R.id.ustavi)
        SENSOR_STATUS_ACCURACY_HIGH

        //definiramo kaj naj se zgodi ob klik na gumb
        start.setOnClickListener{
            sensorManager.registerListener(this, mAccelometer,
                SensorManager.SENSOR_DELAY_NORMAL)
        }
        stop.setOnClickListener {
            sensorManager.unregisterListener(this)
        }
    }
    override fun onAccuracyChanged(sesor:Sensor,accuracy:Int){
        val intent= Intent(this,SenzorChange::class.java)
        startActivity(intent)
    }

    //naredimo zajem podatkov in jih formatiramo
   @RequiresApi(Build.VERSION_CODES.O)
    override fun onSensorChanged(event: SensorEvent){
        val xSmer=event.values[0]
        val ySmer=event.values[1]
        val zSmer=event.values[2]

        val formater = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS").withZone(
            ZoneOffset.UTC).format(Instant.now())

        //Ustvarimo seznam za shranjevanje podatkov
        val seznam = ArrayList<String>()

        findViewById<TextView>(R.id.Vrednost_x).apply{
            text= "x-smer:$xSmer, čas zajema:$formater" }
        findViewById<TextView>(R.id.Vrednost_y).apply{
            text= "y-smer:$xSmer, čas zajema:$formater" }
        findViewById<TextView>(R.id.Vrednost_y).apply{
            text= "z-smer:$xSmer, čas zajema:$formater" }
        seznam.add("x:$xSmer, y:$ySmer, z:$zSmer, ob času:$formater")
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}