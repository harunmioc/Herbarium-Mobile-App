package com.example.myapplication

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Adapter
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
    private var trenutnaDuzinaListe: Int = biljkeList.size
    private var trenutniMod = "Medicinski"
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    //dohvacanje id-a RecyclerView-a, Spinner-a i Button-a
        RecyclerViewMain = findViewById(R.id.RecyclerViewMain)
        spinoza = findViewById(R.id.spinner)
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
                    if(trenutnaDuzinaListe != biljkeList.size) {
                        if(trenutniMod == "Kuharski"){
                            medicinskiAdapter.updateBiljke(kuharskiAdapter.dajListu())
                        }else if( trenutniMod == "Botanički"){
                            medicinskiAdapter.updateBiljke(botanickiAdapter.dajListu())
                        }
                    }else {
                        medicinskiAdapter.updateBiljke(biljkeList)
                    }
                    trenutniMod = "Medicinski"
                }
                else if(spinoza.selectedItem.toString() == "Kuharski"){
                    RecyclerViewMain.adapter = kuharskiAdapter
                    if(trenutnaDuzinaListe != biljkeList.size) {
                        println("KUHARSKI TACNO")
                        if(trenutniMod == "Medicinski"){
                            kuharskiAdapter.updateBiljke(medicinskiAdapter.dajListu())
                        }else if( trenutniMod == "Botanički"){
                            kuharskiAdapter.updateBiljke(botanickiAdapter.dajListu())
                        }
                    }else {
                        println("BOTANICKI TACNO")

                        kuharskiAdapter.updateBiljke(biljkeList)
                    }
                    trenutniMod = "Kuharski"
                }else{
                    RecyclerViewMain.adapter = botanickiAdapter
                    if(trenutnaDuzinaListe != biljkeList.size) {
                        println("BOTANICKI TACNO")
                        if(trenutniMod == "Kuharski"){
                            botanickiAdapter.updateBiljke(kuharskiAdapter.dajListu())
                        }else if( trenutniMod == "Medicinski"){
                            botanickiAdapter.updateBiljke(medicinskiAdapter.dajListu())
                        }
                    }else {
                        println("BOTANICKI NETACNO")

                        botanickiAdapter.updateBiljke(biljkeList)
                    }
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
                    medicinskiAdapter.updateBiljke(biljke)
                else if(trenutniMod == "Kuharski")
                    kuharskiAdapter.updateBiljke(biljke)
                else
                    botanickiAdapter.updateBiljke(biljke)
            }
        })


    }


    private fun onClickUpdateBiljke(biljka : Biljka){
        var slicneBiljke : ArrayList<Biljka> = ArrayList()
        slicneBiljke.add(biljka)

    //medicinski
        if(trenutniMod == "Medicinski") {
            for (i in biljke) {
                if (i != biljka) {
                    for (j in i.medicinskeKoristi) {
                        if (biljka.medicinskeKoristi.contains(j)) {
                            slicneBiljke.add(i)
                            break
                        }
                    }
                }
            }
//kuharski
        }else if(trenutniMod == "Kuharski") {
            var jela: Boolean = false
            for (i in biljke) {
                jela = false
                if (i != biljka) {
                    for (j in i.jela) {
                        if (biljka.jela.contains(j)) {
                            jela = true
                            continue
                        }
                    }
                    if (jela || i.profilOkusa == biljka.profilOkusa) {
                        slicneBiljke.add(i)
                    }
                }
            }
//botanicki
        }else {

            var klimatski: Boolean = false
            var zemljisni: Boolean = false
            for (i in biljke) {
                klimatski = false
                zemljisni = false
                if (i != biljka && i.porodica == biljka.porodica) {
                    for (j in i.klimatskiTipovi)
                        if (biljka.klimatskiTipovi.contains(j)) {
                            klimatski = true
                            continue
                        }
                    for (j in i.zemljaniTipovi)
                        for (j in i.zemljaniTipovi)
                            if (biljka.zemljaniTipovi.contains(j)) {
                                klimatski = true
                                continue
                            }
                    if (zemljisni && klimatski)
                        slicneBiljke.add(i)
                }
            }
        }

        var listBiljke: List<Biljka> = slicneBiljke
        trenutnaDuzinaListe = listBiljke.size
        if(trenutniMod == "Medicinski")
            medicinskiAdapter.updateBiljke(listBiljke)
        else if(trenutniMod == "Kuharski")
            kuharskiAdapter.updateBiljke(listBiljke)
        else
            botanickiAdapter.updateBiljke(listBiljke)
    }





}