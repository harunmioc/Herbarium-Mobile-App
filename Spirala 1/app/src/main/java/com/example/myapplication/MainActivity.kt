package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var RecyclerViewMain : RecyclerView
    private lateinit var medicinskiAdapter : MedicinskiListAdapter
    private lateinit var kuharskiAdapter : KuharskiListAdapter
    private lateinit var botanickiAdapter : BotanickiListAdapter
    private var biljkeList = dajBiljke()
    private lateinit var spinoza: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    //dohvacanje id-a RecyclerView-a i Spinner-a
        RecyclerViewMain = findViewById(R.id.RecyclerViewMain)
        spinoza = findViewById(R.id.spinner)
    //Dodavanje opcija u Spinner
        val arraySpinner = arrayOf(
            "Medicinski", "Kuharski", "Botanički"
        )
    //Spinner adapter koji omogucava drop down menu i biranje
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinoza.setAdapter(adapter)
    //deklaracija Adaptera za modove aplikacije
        medicinskiAdapter = MedicinskiListAdapter(listOf())
        kuharskiAdapter = KuharskiListAdapter(listOf())
        botanickiAdapter = BotanickiListAdapter(listOf())
    //Defaultni mod app je Medicinski mod
        RecyclerViewMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        RecyclerViewMain.adapter = medicinskiAdapter
        medicinskiAdapter.updateBiljke(biljkeList)

    //Spinner listener koji mijenja mod app
        spinoza.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(spinoza.selectedItem.toString() == "Medicinski") {
                    RecyclerViewMain.adapter =medicinskiAdapter
                    medicinskiAdapter.updateBiljke(biljkeList)
                }
                else if(spinoza.selectedItem.toString() == "Kuharski"){
                    RecyclerViewMain.adapter = kuharskiAdapter
                    kuharskiAdapter.updateBiljke(biljkeList)
                }else{
                    RecyclerViewMain.adapter = botanickiAdapter
                    botanickiAdapter.updateBiljke(biljkeList)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}