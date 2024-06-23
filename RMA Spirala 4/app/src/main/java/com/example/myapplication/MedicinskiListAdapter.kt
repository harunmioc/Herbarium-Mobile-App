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

class MedicinskiListAdapter(private var biljke: List<Biljka>,private val biljkaRepository: BiljkaRepository, private val onItemClicked: (movie:Biljka) -> Unit) : RecyclerView.Adapter<MedicinskiListAdapter.BiljkaViewHolder>(){

    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val biljkaImage: ImageView = itemView.findViewById(R.id.slikaItem)
        val biljkaNaziv: TextView = itemView.findViewById(R.id.nazivItem)
        val biljkaUpozorenje: TextView = itemView.findViewById(R.id.upozorenjeItem)
        val biljkaKorist1: TextView = itemView.findViewById(R.id.korist1Item)
        val biljkaKorist2: TextView = itemView.findViewById(R.id.korist2Item)
        val biljkaKorist3: TextView = itemView.findViewById(R.id.korist3Item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.medicinski_card, parent, false)
        return BiljkaViewHolder(view);
    }

    override fun getItemCount(): Int {
        return biljke.size
    }

    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        holder.biljkaNaziv.text = biljke[position].naziv
        holder.biljkaUpozorenje.text = biljke.get(position).medicinskoUpozorenje

//potrebna bolja implementacija
        holder.biljkaKorist1.text = biljke[position].medicinskeKoristi[0].opis
        if(1<biljke[position].medicinskeKoristi.size){
            holder.biljkaKorist2.text = biljke[position].medicinskeKoristi[1].opis
        }else {
            holder.biljkaKorist2.text = "" }
        if(2<biljke[position].medicinskeKoristi.size) {
            holder.biljkaKorist3.text = biljke[position].medicinskeKoristi[2].opis
        }else {
            holder.biljkaKorist3.text = "" }

        val context: Context = holder.biljkaImage.context

       /* var id: Int = context.resources.getIdentifier(biljke[position].naziv, "drawable", context.packageName)
        if (id==0) id=context.resources
            .getIdentifier("nana", "drawable", context.packageName)
        holder.biljkaImage.setImageResource(id)*/

        CoroutineScope(Dispatchers.Main).launch {
            val biljkaBitmap = biljke[position].id?.let {
                biljkaRepository.getImage(it)
            }
            if (biljkaBitmap != null) {
                holder.biljkaImage.setImageBitmap(biljkaBitmap.bitmap)
            } else {
                val bitmap = TrefleDAO(context).getImage(biljke[position], biljkaRepository)
                holder.biljkaImage.setImageBitmap(bitmap)
            }
        }


        holder.itemView.setOnClickListener{ onItemClicked(biljke[position]) }

    }


    fun updateBiljke(biljke : List<Biljka>){
        this.biljke = biljke
        notifyDataSetChanged()
    }

    fun dajListu():List<Biljka>{
        return biljke
    }

}