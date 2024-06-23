package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.red
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


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
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var imageView: ImageView
    var jelaList = ArrayList<String>()
    private val Trefle: TrefleDAO = TrefleDAO()
    private lateinit var biljkaRepository: BiljkaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_biljka)

        var isEditMode = false
        var editIndex = -1
//dohvacanje id-eva
        biljkaNaziv = findViewById(R.id.nazivET)
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
        imageView = findViewById(R.id.imageView)


        biljkaMedicinskaKorist.setAdapter(
            ArrayAdapter<MedicinskaKorist>(
                this,
                android.R.layout.select_dialog_multichoice,
                MedicinskaKorist.values()
            )
        )
        biljkaKlimatskiTip.setAdapter(
            ArrayAdapter<KlimatskiTip>(
                this,
                android.R.layout.select_dialog_multichoice,
                KlimatskiTip.values()
            )
        )
        biljkaZemljisniTip.setAdapter(
            ArrayAdapter<Zemljiste>(
                this,
                android.R.layout.select_dialog_multichoice,
                Zemljiste.values()
            )
        )
        biljkaProfilOkusa.setAdapter(
            ArrayAdapter<ProfilOkusaBiljke>(
                this,
                android.R.layout.select_dialog_singlechoice,
                ProfilOkusaBiljke.values()
            )
        )
        biljkaJelaList.setAdapter(
            ArrayAdapter<String>(
                this,
                android.R.layout.select_dialog_multichoice,
                jelaList
            )
        )

        biljkaMedicinskaKorist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        biljkaKlimatskiTip.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        biljkaZemljisniTip.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        biljkaProfilOkusa.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        val defaultImage = BitmapFactory.decodeResource(resources, R.drawable.nana)
        imageView.setImageBitmap(defaultImage)

        biljkaJelaList.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = jelaList[position]
            biljkaJelo.setText(selectedItem)
            isEditMode = true
            editIndex = position
            dodajJelo.setText("Izmijeni jelo")
        }

        uslikajBiljku.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }



        dodajJelo.setOnClickListener {
            val newJelo = biljkaJelo.text.toString().trim()
            if (!isEditMode) {
                if (newJelo.isNotEmpty()) {
                    if (jelaList.any { it.equals(newJelo.lowercase(), ignoreCase = true) }) {
                        // Ako je jelo već prisutno, prikazati poruku o grešci
                        Toast.makeText(this, "Ovo jelo već postoji!", Toast.LENGTH_SHORT).show()
                    } else {
                        jelaList.add(biljkaJelo.getText().toString().trim())
                        (biljkaJelaList.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                        //???????????????????????????????
                        biljkaJelo.setText("")
                    }
                } else {
                    Toast.makeText(this, "Dodajte barem jedno jelo !", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (newJelo.isNotEmpty()) {
                    jelaList[editIndex] = newJelo
                    (biljkaJelaList.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                } else {
                    jelaList.removeAt(editIndex)
                    (biljkaJelaList.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                }
                dodajJelo.setText("Dodaj jelo")
                editIndex = -1
                isEditMode = false
                biljkaJelo.setText("")
            }
        }


        dodajBiljku.setOnClickListener {
            if (validacijaPostoji()) {
                if (validacija()) {
                    val naziv = biljkaNaziv.text.toString().trim()
                    val porodica = biljkaPorodica.text.toString().trim()
                    val medicinskoUpozorenje = biljkaMedicinskoUpozorenje.text.toString().trim()
                    val jela = jelaList.toList()
                    val koristi: List<MedicinskaKorist> = checkedList(biljkaMedicinskaKorist)
                    val klima: List<KlimatskiTip> = checkedList(biljkaKlimatskiTip)
                    val zemlja: List<Zemljiste> = checkedList(biljkaZemljisniTip)
                    val okus = (biljkaProfilOkusa.adapter.getItem(biljkaProfilOkusa.checkedItemPosition) as ProfilOkusaBiljke)
                    val novaBiljka = Biljka(naziv, porodica, medicinskoUpozorenje, koristi, okus, jela, klima, zemlja)
                    lifecycleScope.launch {
                        val biljkaPostoji = BiljkaSingleton.getSveBiljkeList().any { it.naziv.equals(novaBiljka.naziv, ignoreCase = true) }
                        if (!biljkaPostoji) {
                            var ispravnaBiljka = TrefleDAO(this@NovaBiljkaActivity).fixData(novaBiljka)

                            BiljkaSingleton.addBiljka(ispravnaBiljka)
                            biljkaRepository.saveBiljka(ispravnaBiljka)
                            val resultIntent = Intent();
                            setResult(Activity.RESULT_OK, resultIntent)  // Postavljanje rezultata
                            finish()  // Zatvaranje trenutne aktivnosti
                        } else {
                            Toast.makeText(this@NovaBiljkaActivity, "Biljka s tim nazivom već postoji!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else{
                if (biljkaNaziv.text.toString().isBlank()) {
                    Toast.makeText(this, "Naziv biljke prazan !", Toast.LENGTH_SHORT).show()
                    biljkaNaziv.setError("Naziv biljke je obavezan")
                }
                if (biljkaPorodica.text.toString().isBlank()) {
                    Toast.makeText(this, "Naziv porodice prazan !", Toast.LENGTH_SHORT).show()
                    biljkaPorodica.setError("Naziv porodice je obavezan")
                }
                if (biljkaMedicinskoUpozorenje.text.toString().isBlank()) {
                    Toast.makeText(this, "Medicinsko upozorenje prazno !", Toast.LENGTH_SHORT).show()
                    biljkaMedicinskoUpozorenje.setError("Medicinsko upozorenje je obavezno")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
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

        val nazivPattern = Regex(".*\\(.*\\).*")

        return naziv.length in 2..40 && naziv.matches(nazivPattern) && porodica.length in 2..20 &&
                medicinskoUpozorenje.length in 2..20 && brojKoristi>0 &&
                brojKlima>0 && brojOkus>0 && brojZemlja>0
    }

    fun validacijaPostoji() : Boolean{
        return biljkaNaziv.text.toString().isNotBlank() && biljkaPorodica.text.toString().isNotBlank() &&
                biljkaMedicinskoUpozorenje.text.toString().isNotBlank() &&
                biljkaMedicinskaKorist.checkedItemCount > 0 &&
                biljkaKlimatskiTip.checkedItemCount > 0 &&
                biljkaZemljisniTip.checkedItemCount > 0 &&
                biljkaProfilOkusa.checkedItemCount > 0 &&
                jelaList.size > 0
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