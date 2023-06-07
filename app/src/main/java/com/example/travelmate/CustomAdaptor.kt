package com.example.travelmate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase

class CustomAdapter(var context: Context, var data:ArrayList<Destination>):BaseAdapter() {
    private class ViewHolder(row:View?){
        var destination:TextView
        var duration:TextView
        var weather:TextView
        var delete:Button
        var update:Button
        var imagedestin:ImageView
        init {
            this.imagedestin = row?.findViewById(R.id.mdestin) as ImageView
            this.delete = row?.findViewById(R.id.deletebtn) as Button
            this.update = row?.findViewById(R.id.updatebtn) as Button
            this.destination = row?.findViewById(R.id.mdestination) as TextView
            this.duration = row?.findViewById(R.id.mduration) as TextView
            this.weather = row?.findViewById(R.id.mweather) as TextView
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.destination_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var item:Destination = getItem(position) as Destination
        viewHolder.destination.text = item.destination
        viewHolder.duration.text = item.duration
        viewHolder.weather.text = item.weather
        Glide.with(context).load(item.imagedes).into(viewHolder.imagedestin)
        viewHolder.delete.setOnClickListener {
            val delRef = FirebaseDatabase.getInstance()
                .getReference().child("Destination/"+item.destinationID)
            delRef.removeValue()
        }
        viewHolder.update.setOnClickListener {  }
        return view as View
    }

    override fun getItem(position: Int): Any {
        return  data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.count()
    }
}