package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
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

    private var trenutniMod = "Medicinski"
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    //dohvacanje id-a RecyclerView-a, Spinner-a i Button-a
        RecyclerViewMain = findViewById(R.id.biljkeRV)
        spinoza = findViewById(R.id.modSpinner)
        resetButton = findViewById(R.id.resetBtn)
    //Dodavanje opcija u Spinner
        val arraySpinner = arrayOf(
            "Medicinski", "Kuharski", "Botanički"
        )
    //Spinner adapter koji omogucava drop down menu i biranje
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinoza.setAdapter(adapter)
    //deklaracija Adaptera za modove aplikacije
        medicinskiAdapter = MedicinskiListAdapter(listOf()){biljka -> onClickUpdateBiljke(biljka)}
        kuharskiAdapter = KuharskiListAdapter(listOf()){biljka -> onClickUpdateBiljke(biljka)}
        botanickiAdapter = BotanickiListAdapter(listOf()){biljka -> onClickUpdateBiljke(biljka)}
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
                    trenutniMod = "Medicinski"
                }
                else if(spinoza.selectedItem.toString() == "Kuharski"){
                    RecyclerViewMain.adapter = kuharskiAdapter
                    kuharskiAdapter.updateBiljke(biljkeList)
                    trenutniMod = "Kuharski"
                }else{
                    RecyclerViewMain.adapter = botanickiAdapter
                    botanickiAdapter.updateBiljke(biljkeList)
                    trenutniMod = "Botanički"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }



        resetButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if(trenutniMod == "Medicinski")
                    medicinskiAdapter.updateBiljke(dajBiljke())
                else if(trenutniMod == "Kuharski")
                    kuharskiAdapter.updateBiljke(dajBiljke())
                else
                    botanickiAdapter.updateBiljke(dajBiljke())
            }
        })


    }


    private fun onClickUpdateBiljke(biljka : Biljka){
//medicinski
        if(trenutniMod == "Medicinski") {
            biljkeList = dajBiljke().filter { it -> it.medicinskeKoristi.any { korist -> korist in biljka.medicinskeKoristi }}
            medicinskiAdapter.updateBiljke(biljkeList)
//botanicki
        }else if(trenutniMod == "Botanički") {
            biljkeList = dajBiljke().filter { it -> it.porodica == biljka.porodica && it.klimatskiTipovi.any { tip -> tip in biljka.klimatskiTipovi} &&
                    it.zemljisniTipovi.any {tip -> tip in biljka.zemljisniTipovi}}
            botanickiAdapter.updateBiljke(biljkeList)
//kuharski
        }else {
            biljkeList = dajBiljke().filter { it -> it.jela.any { jelo -> jelo in biljka.jela} || it.profilOkusa == biljka.profilOkusa}
            kuharskiAdapter.updateBiljke(biljkeList)
        }
    }





}