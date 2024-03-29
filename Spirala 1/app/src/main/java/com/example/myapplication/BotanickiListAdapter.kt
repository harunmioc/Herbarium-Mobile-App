package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BotanickiListAdapter (private var biljke : List<Biljka>, private val onItemClicked: (movie:Biljka) -> Unit) : RecyclerView.Adapter<BotanickiListAdapter.BiljkaViewHolder>() {

    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val biljkaImage: ImageView = itemView.findViewById(R.id.slikaItem)
        val biljkaNaziv: TextView = itemView.findViewById(R.id.nazivItem)
        val biljkaPorodica: TextView = itemView.findViewById(R.id.porodicaItem)
        val biljkaKlima: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        val biljkaZemlja: TextView = itemView.findViewById(R.id.zemljisniTipItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.botanicki_layout, parent, false)
        return BiljkaViewHolder(view);
    }

    override fun getItemCount(): Int {
        return biljke.size
    }

    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        var klima: String = String()
        var zemlja: String = String()
        for(i in biljke[position].klimatskiTipovi){
            if(i == biljke[position].klimatskiTipovi.last())
                zemlja += i.opis
            else
                zemlja += i.opis+", "
        }
        for(i in biljke[position].zemljaniTipovi){
            if(i == biljke[position].zemljaniTipovi.last())
                zemlja += i.naziv
            else
                zemlja += i.naziv+", "
        }

        holder.biljkaNaziv.text = biljke[position].naziv
        holder.biljkaPorodica.text = biljke[position].porodica
        holder.biljkaKlima.text = klima
        holder.biljkaZemlja.text = zemlja

        val context: Context = holder.biljkaImage.context
        var id: Int = context.resources.getIdentifier(biljke[position].naziv, "drawable", context.packageName)
        if (id==0) id=context.resources
            .getIdentifier("nana", "drawable", context.packageName)
        holder.biljkaImage.setImageResource(id)

        holder.itemView.setOnClickListener{ onItemClicked(biljke[position]) }

    }
    fun dajListu():List<Biljka>{
        return biljke
    }

    fun updateBiljke(biljke : List<Biljka>){
        this.biljke = biljke
        notifyDataSetChanged()
    }

     fun onClickUpdateBiljke(biljka : Biljka){
        var slicneBiljke : ArrayList<Biljka> = ArrayList()
        slicneBiljke.add(biljka)
        var klimatski :Boolean = false
        var zemljisni :Boolean = false
        for(i in biljke){
            klimatski = false
            zemljisni = false
            if(i != biljka && i.porodica==biljka.porodica) {
                for (j in i.klimatskiTipovi)
                    if(biljka.klimatskiTipovi.contains(j)){
                        klimatski = true
                        continue
                    }



                for(j in i.zemljaniTipovi)
                    for(j in i.zemljaniTipovi)
                        if(biljka.zemljaniTipovi.contains(j)){
                            klimatski = true
                            continue
                        }
                if(zemljisni && klimatski)
                    slicneBiljke.add(i)
            }
        }
        var listBiljke:List<Biljka> = slicneBiljke
        updateBiljke(listBiljke)
    }
}
