package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.absoluteValue


class NovaBiljkaActivity : AppCompatActivity() {
    private lateinit var biljkaNaziv: EditText
    private lateinit var biljkaPorodica: EditText
    private lateinit var biljkaMedicinskoUpozorenje: EditText
    private lateinit var biljkaJelo: EditText
    private lateinit var biljkaMedicinskaKorist: ListView
    private lateinit var biljkaKlimatskiTip: ListView
    private lateinit var biljkaZemljisniTip: ListView
    private lateinit var biljkaProfilOkusa: ListView
    private lateinit var biljkaJelaList: ListView
    private lateinit var dodajJelo: Button
    private lateinit var dodajBiljku: Button
    private lateinit var uslikajBiljku: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_biljka)
        var jelaList = ArrayList<String>()
        var isEditMode = false
        var editIndex = -1
//dohvacanje id-eva
        biljkaNaziv= findViewById(R.id.nazivET)
        biljkaPorodica = findViewById(R.id.porodicaET)
        biljkaMedicinskoUpozorenje = findViewById(R.id.medicinskoUpozorenjeET)
        biljkaJelo = findViewById(R.id.jeloET)
        biljkaMedicinskaKorist = findViewById(R.id.medicinskaKoristLV)
        biljkaKlimatskiTip = findViewById(R.id.klimatskiTipLV)
        biljkaZemljisniTip = findViewById(R.id.zemljisniTipLV)
        biljkaProfilOkusa = findViewById(R.id.profilOkusaLV)
        biljkaJelaList = findViewById(R.id.jelaLV)
        dodajJelo = findViewById(R.id.dodajJeloBtn)
        dodajBiljku = findViewById(R.id.dodajBiljkuBtn)
        uslikajBiljku = findViewById(R.id.uslikajBiljkuBtn)

        biljkaMedicinskaKorist.setAdapter(ArrayAdapter<MedicinskaKorist>(this, android.R.layout.simple_list_item_1, MedicinskaKorist.values()))
        biljkaKlimatskiTip.setAdapter(ArrayAdapter<KlimatskiTip>(this, android.R.layout.simple_list_item_1, KlimatskiTip.values()))
        biljkaZemljisniTip.setAdapter(ArrayAdapter<Zemljiste>(this, android.R.layout.simple_list_item_1, Zemljiste.values()))
        biljkaProfilOkusa.setAdapter(ArrayAdapter<ProfilOkusaBiljke>(this, android.R.layout.simple_list_item_1, ProfilOkusaBiljke.values()))
        biljkaJelaList.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jelaList))

        biljkaMedicinskaKorist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        biljkaKlimatskiTip.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        biljkaZemljisniTip.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        biljkaProfilOkusa.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        biljkaJelaList.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = jelaList[position]
            biljkaJelo.setText(selectedItem)
            isEditMode = true
            editIndex = position
            dodajJelo.setText("Izmijeni jelo")
        }

        uslikajBiljku.setOnClickListener{

        }


        dodajJelo.setOnClickListener {
            val newJelo = biljkaJelo.text.toString().trim()
            if(!isEditMode){
                if(newJelo.isNotEmpty()){
                    if (jelaList.any { it.equals(newJelo.lowercase(), ignoreCase = true) }) {
                        // Ako je jelo već prisutno, prikazati poruku o grešci
                        Toast.makeText(this, "Ovo jelo već postoji!", Toast.LENGTH_SHORT).show()
                    } else {
                        jelaList.add(biljkaJelo.getText().toString().trim())
                        (biljkaJelaList.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                        //???????????????????????????????
                        biljkaJelo.setText("")
                    }
                }else{
                    Toast.makeText(this, "Validation failed", Toast.LENGTH_SHORT).show()
                }
            }else{
                if(newJelo.isNotEmpty()){
                    jelaList[editIndex] = newJelo
                    (biljkaJelaList.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                }else{
                    jelaList.removeAt(editIndex)
                    (biljkaJelaList.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                }
                dodajJelo.setText("Dodaj jelo")
                editIndex = -1
                isEditMode = false
                biljkaJelo.setText("")
            }
        }


        dodajBiljku.setOnClickListener{
            val naziv = biljkaNaziv.text.toString().trim()
            val porodica = biljkaPorodica.text.toString().trim()
            val medicinskoUpozorenje = biljkaMedicinskoUpozorenje.text.toString().trim()
            val jela = jelaList.toList()
            val koristi : List<MedicinskaKorist> = checkedList(biljkaMedicinskaKorist)
            val klima : List<KlimatskiTip> = checkedList(biljkaKlimatskiTip)
            val zemlja : List<Zemljiste> = checkedList(biljkaZemljisniTip)
            val okus  : ProfilOkusaBiljke= (biljkaProfilOkusa.adapter.getItem(biljkaProfilOkusa.checkedItemPosition) as ProfilOkusaBiljke)

            if(validacija()){
                val novaBiljka = Biljka(naziv, porodica, medicinskoUpozorenje, koristi, okus, jela, klima, zemlja)
                BiljkaSingleton.addBiljka(novaBiljka)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }else{
                Toast.makeText(this, "Validation failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun validacija(): Boolean {
        val naziv = biljkaNaziv.text.toString().trim()
        val porodica = biljkaPorodica.text.toString().trim()
        val medicinskoUpozorenje = biljkaMedicinskoUpozorenje.text.toString().trim()
        val brojKoristi = biljkaMedicinskaKorist.checkedItemCount
        val brojKlima = biljkaKlimatskiTip.checkedItemCount
        val brojZemlja = biljkaZemljisniTip.checkedItemCount
        val brojOkus = biljkaProfilOkusa.checkedItemCount


        return naziv.length in 2..20 && porodica.length in 2..20 &&
                medicinskoUpozorenje.length in 2..20 && brojKoristi>0 &&
                brojKlima>0 && brojOkus>0 && brojZemlja>0

        TODO("dodaj errore")
    }

    inline fun <reified T> checkedList(listview: ListView): List<T> {
        val checkedPositions = listview.checkedItemPositions
        val list = mutableListOf<T>()

        for (i in 0 until checkedPositions.size()) {
            val position = checkedPositions.keyAt(i)
            val isChecked = checkedPositions.valueAt(i)

            if (isChecked) {
                val item = listview.adapter.getItem(position)
                if (item is T) {
                    list.add(item)
                }
            }
        }
        return list
    }
}