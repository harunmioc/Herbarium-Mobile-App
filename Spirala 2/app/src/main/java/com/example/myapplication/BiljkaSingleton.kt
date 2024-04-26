package com.example.myapplication

object BiljkaSingleton {
    private var sveBiljkeList =  presipanje()

    fun getSveBiljkeList(): ArrayList<Biljka> {
        return sveBiljkeList
    }

    fun addBiljka(biljka: Biljka) {
        sveBiljkeList.add(biljka)
    }

    fun removeBiljka(biljka: Biljka) {
        sveBiljkeList.remove(biljka)
    }


    private fun presipanje(): ArrayList<Biljka>{
        var array = ArrayList<Biljka>()
        for(i in biljke){
            array.add(i)
        }
        return array
    }
}