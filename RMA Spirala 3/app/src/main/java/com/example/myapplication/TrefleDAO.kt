package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.content.Context
import android.content.res.Resources
import androidx.core.graphics.get
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.net.URL


class TrefleDAO(private var context :Context ?=null) {

    fun initialize(context: Context){
        this.context = context
    }


    val soilTextureMap = mapOf(
        Zemljiste.SLJUNKOVITO to listOf("9"),
        Zemljiste.KRECNJACKO to listOf("10"),
        Zemljiste.GLINENO to listOf("1", "2"),
        Zemljiste.PJESKOVITO to listOf("3", "4"),
        Zemljiste.ILOVACA to listOf("5", "6"),
        Zemljiste.CRNICA to listOf("7", "8")
    )

    val klimatskiTipoviMap = mapOf(
        KlimatskiTip.SREDOZEMNA to Pair(6..9, 1..5),
        KlimatskiTip.TROPSKA to Pair(8..10, 7..10),
        KlimatskiTip.SUBTROPSKA to Pair(6..9, 5..8),
        KlimatskiTip.UMJERENA to Pair(4..7, 3..7),
        KlimatskiTip.SUHA to Pair(7..9, 1..2),
        KlimatskiTip.PLANINSKA to Pair(0..5, 3..7)
    )




    private val defaultBitmap: Bitmap by lazy {
        context?.let {
            BitmapFactory.decodeResource(it.resources, R.drawable.nana)
        } ?: throw IllegalStateException("Context must be set before accessing defaultBitmap")
    }


    suspend fun getImage(biljka: Biljka): Bitmap {
        return withContext(Dispatchers.IO) {
            try {
                val latinskiNaziv :String = getLatinName(biljka.naziv)
                //System.out.println(latinskiNaziv)
                val response = ApiAdapter.retrofit.searchPlants(latinskiNaziv)
                if (response.isSuccessful) {
                   // System.out.println("Uspjesan response")
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.data.isNotEmpty()) {
                       // System.out.println("Response body nije prazan i data nije empty")
                        val imageUrl = responseBody.data[0].imageURL
                        if (imageUrl.isNotEmpty()) {
                            //System.out.println("imageURL nije empty")
                            Glide.with(context!!)
                                .asBitmap()
                                .load(imageUrl)
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .submit()
                                .get()
                        } else {
                            //System.out.println("imageURL prazan")
                            defaultBitmap
                        }
                    } else {
                        defaultBitmap
                    }
                } else {
                   // System.out.println("neuspjesan response")
                    defaultBitmap
                }
            } catch (e: Exception) {
                e.printStackTrace()
                defaultBitmap
            }
        }
    }

    fun getLatinName(naziv : String):String{
        val regex = Regex("\\(([^)]+)\\)")
        val matchResult = regex.find(naziv)
        return matchResult?.groups?.get(1)?.value ?: ""
    }


    suspend fun fixData(biljka:Biljka):Biljka{
        var novaBiljka:Biljka = biljka
        return withContext(Dispatchers.IO) {
            try {
                val latinskiNaziv :String = getLatinName(biljka.naziv)
                //System.out.println(latinskiNaziv +", pravo: "+ biljka.naziv)
                // Trazimo biljku po latinskom nazivu da bi dobili njen ID
                val searchResponse = ApiAdapter.retrofit.searchPlants(latinskiNaziv)
                if (searchResponse.isSuccessful) {
                   // System.out.println("Uspjesno pronadjeno po latinskom nazivu u fixData")
                    val plantData = searchResponse.body()?.data?.firstOrNull()
                    if (plantData != null) {
                        // Fetchujemo biljku po ID
                        val plantDetailsResponse = ApiAdapter.retrofit.getPlantById(plantData.id)
                        if (plantDetailsResponse.isSuccessful) {
                          //  System.out.println("Uspjesno pronadjeno po ID-u u fixData  ID : "+ plantData.id)

                            val plantDetails = plantDetailsResponse.body()?.data

                            var porodica = biljka.porodica
                            var jela:List<String> = biljka.jela
                            var upozorenje = biljka.medicinskoUpozorenje

                           /* if(plantDetails?.main_species == null)
                                System.out.println("main species is null")
*/
                            if (plantDetails != null) {
                                // Update family
                                if(biljka.porodica != plantData.family)
                                    porodica = plantData.family

                                // Update jestivost i medicinsko upozorenje
                                if (!plantDetails.main_species.edible) {
                                    jela = emptyList()
                                    if (!biljka.medicinskoUpozorenje.contains("NIJE JESTIVO")) {
                                        upozorenje += ", NIJE JESTIVO"
                                    }
                                }

                              // Update toksicnost
                                if (plantDetails.main_species.specifications.toxicity != "none" &&
                                    plantDetails.main_species.specifications.toxicity != null &&
                                    !biljka.medicinskoUpozorenje.contains("TOKSIČNO")) {
                                    upozorenje += ", TOKSIČNO"
                                }

                                val fetchedSoilTexture = plantDetails.main_species.growth.soilTexture
                                //System.out.println(fetchedSoilTexture.toString() + "ovo je soil texture")
                                var zemljisniTipovi: MutableList<Zemljiste> = emptyList<Zemljiste>().toMutableList()
                            if(fetchedSoilTexture != null) {

                               // println(fetchedSoilTexture.toString() + "ovo je soil texture")

                                zemljisniTipovi = biljka.zemljisniTipovi.filter { texture ->
                                    val validValues = soilTextureMap[texture]
                                    validValues?.contains(fetchedSoilTexture.toString()) == true
                                }.toMutableList()


                                for ((key, value) in soilTextureMap) {
                                    if (value.contains(fetchedSoilTexture.toString()) && !biljka.zemljisniTipovi.contains(
                                            key
                                        )
                                    ) {
                                        //System.out.println("Uspjesno dodan zemljisni tip")
                                        zemljisniTipovi.add(key)
                                    }
                                }
                            }
//provjera postojecih klimatskih tipova
                                val fetchedLight = plantDetails.main_species.growth.light
                                val fetchedHumidity = plantDetails.main_species.growth.atmosphericHumidity
                               // println(fetchedHumidity.toString() + "ovo je humidity texture")
                                // println(fetchedLight.toString() + "ovo je light texture")


                                val validKlimatskiTipovi = biljka.klimatskiTipovi.filter { klimatskiTip ->
                                    klimatskiTipoviMap.entries.any { (tip, range) ->
                                        val (lightRange, humidityRange) = range
                                        klimatskiTip == tip && fetchedLight in lightRange && fetchedHumidity in humidityRange
                                    }
                                }.toMutableList()

//dodavanje novih
                                klimatskiTipoviMap.entries.forEach { (klimatskiTip, range) ->
                                    val (lightRange, humidityRange) = range
                                    if (fetchedLight in lightRange && fetchedHumidity in humidityRange) {
                                        if (!validKlimatskiTipovi.contains(klimatskiTip)) {
                                           //f System.out.println("Uspjesno dodana klima")
                                            validKlimatskiTipovi.add(klimatskiTip)
                                        }
                                    }
                                }
                                
                                novaBiljka = Biljka(biljka.naziv, porodica, upozorenje,biljka.medicinskeKoristi, biljka.profilOkusa, jela, validKlimatskiTipovi, zemljisniTipovi)

                            }

                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            novaBiljka
        }
    }



    suspend fun getPlantsWithFlowerColor(flowerColor:String,substr:String):List<Biljka>{
        val filteredPlants = mutableListOf<Biljka>()

        try {
            // Poziv web servisa za pretragu biljaka sa određenom bojom cvijeta i substringom
            val response = ApiAdapter.retrofit.searchPlantsByColor(flowerColor, substr)

            if (response.isSuccessful) {
                val plants = response.body()?.data ?: emptyList()

                // Filtriranje biljaka koje sadrže željeni podstring u nazivu
                for (plantData in plants) {
                    if (plantData.commonName?.contains(substr, ignoreCase = true) == true ||
                        plantData.scientificName?.contains(substr, ignoreCase = true) == true
                    ) {
                        //System.out.println(plantData.scientificName + ",  " + plantData.family + "  " )
                        // Kreiranje instance Biljka sa dostupnim podacima iz web servisa
                        var biljka = Biljka(plantData.commonName+"("+plantData.scientificName+")",
                                            plantData.family,
                                            "",
                                            emptyList(),
                                            null,
                                            emptyList(),
                                            emptyList(),
                                            emptyList())
                        var ispravnaBiljka = fixData(biljka)
                        filteredPlants.add(ispravnaBiljka)
                        //System.out.println("duzina liste ZEMLJA : "+ ispravnaBiljka.zemljisniTipovi.size)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return filteredPlants.toList()
    }







}