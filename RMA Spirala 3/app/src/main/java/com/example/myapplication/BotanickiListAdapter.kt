package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        holder.biljkaNaziv.text = biljke[position].naziv
        holder.biljkaPorodica.text = biljke[position].porodica
        if (biljke[position].klimatskiTipovi.isNotEmpty()) {
            holder.biljkaKlima.text = biljke[position].klimatskiTipovi.first().opis
        } else {
            holder.biljkaKlima.text = "" // Or any other default value
        }

        if (biljke[position].zemljisniTipovi.isNotEmpty()) {
            holder.biljkaZemlja.text = biljke[position].zemljisniTipovi.first().naziv
        } else {
            holder.biljkaZemlja.text = "" // Or any other default value
        }

            /* val context: Context = holder.biljkaImage.context
             var id: Int = context.resources.getIdentifier(biljke[position].naziv, "drawable", context.packageName)
             if (id==0) id=context.resources
                 .getIdentifier("nana", "drawable", context.packageName)
             holder.biljkaImage.setImageResource(id)*/



        val context: Context = holder.biljkaImage.context
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = TrefleDAO(context).getImage(biljke[position])
            holder.biljkaImage.setImageBitmap(bitmap)
        }


        holder.itemView.setOnClickListener{ onItemClicked(biljke[position]) }

    }
    fun dajListu():List<Biljka>{
        return biljke
    }

    fun updateBiljke(biljke : List<Biljka>){
        this.biljke = biljke
        notifyDataSetChanged()
    }


}
