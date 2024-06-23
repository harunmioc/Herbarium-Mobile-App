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

class KuharskiListAdapter(private var biljke : List<Biljka>,private val biljkaRepository: BiljkaRepository, private val onItemClicked: (movie:Biljka) -> Unit) : RecyclerView.Adapter<KuharskiListAdapter.BiljkaViewHolder>() {

    inner class BiljkaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val biljkaImage: ImageView = itemView.findViewById(R.id.slikaItem)
        val biljkaNaziv: TextView = itemView.findViewById(R.id.nazivItem)
        val biljkaOkus: TextView = itemView.findViewById(R.id.profilOkusaItem)
        val biljkaJelo1: TextView = itemView.findViewById(R.id.jelo1Item)
        val biljkaJelo2: TextView = itemView.findViewById(R.id.jelo2Item)
        val biljkaJelo3: TextView = itemView.findViewById(R.id.jelo3Item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kuharski_layout,parent,false)
        return BiljkaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return biljke.size
    }

    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        holder.biljkaNaziv.text = biljke[position].naziv
        holder.biljkaOkus.text = biljke[position].profilOkusa?.opis
/*potrebna bolja implementacija
        holder.biljkaJelo1.text = biljke[position].jela[0]
        if(1<biljke[position].jela.size){
            holder.biljkaJelo2.text = biljke[position].jela[1]
        }else {
            holder.biljkaJelo2.text = "" }
        if(2<biljke[position].jela.size) {
            holder.biljkaJelo3.text = biljke[position].jela[2]
        }else {
            holder.biljkaJelo3.text = "" }*/




        val jelaTextViews = listOf(holder.biljkaJelo1, holder.biljkaJelo2, holder.biljkaJelo3)
        val jela = biljke[position].jela

        // Postavljanje tekstova za jela
        for (i in jelaTextViews.indices) {
            if (i < jela.size) {
                jelaTextViews[i].text = jela[i]
            } else {
                jelaTextViews[i].text = ""
            }
        }



       /* val context: Context = holder.biljkaImage.context
        var id: Int = context.resources.getIdentifier(biljke[position].naziv, "drawable", context.packageName)
        if (id==0) id=context.resources
            .getIdentifier("nana", "drawable", context.packageName)
        holder.biljkaImage.setImageResource(id)
*/

       val context: Context = holder.biljkaNaziv.context
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

    fun dajListu():List<Biljka>{
        return biljke
    }

    fun updateBiljke(biljke : List<Biljka>){
        this.biljke = biljke
        notifyDataSetChanged()
    }

}