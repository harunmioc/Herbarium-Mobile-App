package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var biljkaRepo : BiljkaRepository


    private lateinit var RecyclerViewMain : RecyclerView
    private lateinit var medicinskiAdapter : MedicinskiListAdapter
    private lateinit var kuharskiAdapter : KuharskiListAdapter
    private lateinit var botanickiAdapter : BotanickiListAdapter
    private var biljkeList = dajBiljke()
    private var sveBiljkeList : BiljkaSingleton = BiljkaSingleton
    private lateinit var spinoza: Spinner
    private lateinit var novaBiljkaButton: Button
    private lateinit var brzaPretragaButton : Button
    private lateinit var bojaSpinner : Spinner
    private lateinit var pretragaET: EditText


    private var trenutniMod = "Medicinski"
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val biljkaDatabase = BiljkaDatabase.getInstance(this)
        biljkaRepo = BiljkaRepository(biljkaDatabase.biljkaDao())


        //dohvacanje id-a RecyclerView-a, Spinner-a i Button-a
        RecyclerViewMain = findViewById(R.id.biljkeRV)
        spinoza = findViewById(R.id.modSpinner)
        resetButton = findViewById(R.id.resetBtn)
        novaBiljkaButton = findViewById(R.id.novaBiljkaBtn)
        brzaPretragaButton = findViewById(R.id.brzaPretraga)
        bojaSpinner = findViewById(R.id.bojaSPIN)
        pretragaET = findViewById(R.id.pretragaET)
    //Dodavanje opcija u Spinnere
        val arraySpinner = arrayOf(
            "Medicinski", "Kuharski", "Botanički"
        )
        val arrayBojaSpinner = arrayOf("red", "blue", "yellow", "orange", "purple","brown","green")
    //Spinner adapter koji omogucava drop down menu i biranje
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinoza.setAdapter(adapter)
    //Spinner adapter za biranje boje cvijeta u botanickom modu
        val adapterBoja = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayBojaSpinner)
        adapterBoja.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bojaSpinner.setAdapter(adapterBoja)
        //deklaracija Adaptera za modove aplikacije
        medicinskiAdapter = MedicinskiListAdapter(listOf(), biljkaRepo){biljka -> onClickUpdateBiljke(biljka)}
        kuharskiAdapter = KuharskiListAdapter(listOf(), biljkaRepo){biljka -> onClickUpdateBiljke(biljka)}
        botanickiAdapter = BotanickiListAdapter(listOf(), biljkaRepo){biljka -> onClickUpdateBiljke(biljka)}
    //Defaultni mod app je Medicinski mod
        RecyclerViewMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        RecyclerViewMain.adapter = medicinskiAdapter
        medicinskiAdapter.updateBiljke(sveBiljkeList.getSveBiljkeList())

        toggleBotanickiElements(false)


        //Spinner listener koji mijenja mod app
        spinoza.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(spinoza.selectedItem.toString() == "Medicinski") {
                    RecyclerViewMain.adapter =medicinskiAdapter
                    medicinskiAdapter.updateBiljke(sveBiljkeList.getSveBiljkeList())
                    trenutniMod = "Medicinski"
                    toggleBotanickiElements(false)

                }
                else if(spinoza.selectedItem.toString() == "Kuharski"){
                    RecyclerViewMain.adapter = kuharskiAdapter
                    kuharskiAdapter.updateBiljke(sveBiljkeList.getSveBiljkeList())
                    trenutniMod = "Kuharski"
                    toggleBotanickiElements(false)

                }else{
                    RecyclerViewMain.adapter = botanickiAdapter
                    botanickiAdapter.updateBiljke(sveBiljkeList.getSveBiljkeList())
                    trenutniMod = "Botanički"
                    toggleBotanickiElements(true)

                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }



        resetButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if(trenutniMod == "Medicinski")
                    medicinskiAdapter.updateBiljke(sveBiljkeList.getSveBiljkeList())
                else if(trenutniMod == "Kuharski")
                    kuharskiAdapter.updateBiljke(sveBiljkeList.getSveBiljkeList())
                else
                    botanickiAdapter.updateBiljke(sveBiljkeList.getSveBiljkeList())
            }
        })


        novaBiljkaButton.setOnClickListener {
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            startActivity(intent)
        }


        brzaPretragaButton.setOnClickListener{
            val pretragaText = pretragaET.text.toString().trim()
            val selectedColor = bojaSpinner.selectedItem.toString()
            lifecycleScope.launch(Dispatchers.IO) {
                if (pretragaText.isNotEmpty()) {
                    val listaBiljkiIsteBoje = TrefleDAO(this@MainActivity).getPlantsWithFlowerColor(selectedColor, pretragaText)
                    if(listaBiljkiIsteBoje.isNotEmpty()){
                        launch(Dispatchers.Main) {
                             botanickiAdapter.updateBiljke(listaBiljkiIsteBoje)
                        }
                    }else{
                        System.out.println("Lista prazna!!!!!")
                    }
                } else {
                    launch(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Polje za pretragu je prazno!", Toast.LENGTH_SHORT).show()
                        pretragaET.error = "Polje za pretragu je prazno"
                    }
                }
            }
        }
    }





    private fun onClickUpdateBiljke(biljka : Biljka){
//medicinski
        if(trenutniMod == "Medicinski") {
            biljkeList = sveBiljkeList.getSveBiljkeList().filter { it -> it.medicinskeKoristi.any { korist -> korist in biljka.medicinskeKoristi }}
            medicinskiAdapter.updateBiljke(biljkeList)

//botanicki
        }else if(trenutniMod == "Botanički") {
            biljkeList = sveBiljkeList.getSveBiljkeList().filter { it -> it.family == biljka.family && it.klimatskiTipovi.any { tip -> tip in biljka.klimatskiTipovi} &&
                    it.zemljisniTipovi.any {tip -> tip in biljka.zemljisniTipovi}}
            botanickiAdapter.updateBiljke(biljkeList)
//kuharski
        }else {
            biljkeList = sveBiljkeList.getSveBiljkeList().filter { it -> it.jela.any { jelo -> jelo in biljka.jela} || it.profilOkusa == biljka.profilOkusa}
            kuharskiAdapter.updateBiljke(biljkeList)

        }
    }



    private fun toggleBotanickiElements(show: Boolean) {
        if(show) {
            brzaPretragaButton.visibility = View.VISIBLE
            bojaSpinner.visibility = View.VISIBLE
            pretragaET.visibility = View.VISIBLE
        }else{
            brzaPretragaButton.visibility = View.GONE
            bojaSpinner.visibility = View.GONE
            pretragaET.visibility = View.GONE
        }
    }


}