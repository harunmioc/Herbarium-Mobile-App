package com.example.myapplication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DBTests {


    private lateinit var userDao: BiljkaDAO
    private lateinit var db: BiljkaDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, BiljkaDatabase::class.java).build()
        userDao = db.biljkaDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        db.close()
    }

    @Test
    fun testAdd() = runBlocking {
        val biljka = Biljka(
            id = null,
            naziv = "Lavanda (latinski)",
            family = "Lamiaceae",
            medicinskoUpozorenje = "Upozorenje",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE),
            profilOkusa = ProfilOkusaBiljke.SLATKI,
            jela = listOf("Čaj od lavande", "Kolači"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO),
            onlineChecked = true
        )

        userDao.insertBiljka(biljka)

        val sveBiljke = userDao.getAllBiljkas()
        assertEquals(1, sveBiljke.size)

        val vracenaBiljka = sveBiljke[0]
        assertEquals(biljka.naziv, vracenaBiljka.naziv)
        assertEquals(biljka.family, vracenaBiljka.family)
        assertEquals(biljka.medicinskoUpozorenje, vracenaBiljka.medicinskoUpozorenje)
        assertEquals(biljka.medicinskeKoristi, vracenaBiljka.medicinskeKoristi)
        assertEquals(biljka.profilOkusa, vracenaBiljka.profilOkusa)
        assertEquals(biljka.jela, vracenaBiljka.jela)
        assertEquals(biljka.klimatskiTipovi, vracenaBiljka.klimatskiTipovi)
        assertEquals(biljka.zemljisniTipovi, vracenaBiljka.zemljisniTipovi)
        assertEquals(biljka.onlineChecked, vracenaBiljka.onlineChecked)
    }

    @Test
    fun testClearEverything() = runBlocking {
        val biljka = Biljka(
            id = null,
            naziv = "Lavanda (latinski)",
            family = "Lamiaceae",
            medicinskoUpozorenje = "Upozorenje",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE),
            profilOkusa = ProfilOkusaBiljke.SLATKI,
            jela = listOf("Čaj od lavande", "Kolači"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO),
            onlineChecked = false
        )

        // Ubacivanje biljke u bazu da bi imali nesto u bazi prije poziva clearData()
        userDao.insertBiljka(biljka)
        userDao.clearData()

        val sveBiljke = userDao.getAllBiljkas()
        assertEquals(0, sveBiljke.size)
    }
}
