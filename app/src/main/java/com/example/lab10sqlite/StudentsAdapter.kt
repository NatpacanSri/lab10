package com.example.lab10sqlite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lab10sqlite.databinding.StdItemLayoutBinding

class StudentsAdapter(val items:List<Student>,val context:Context)
    :RecyclerView.Adapter<StudentsAdapter.ViewHolder>(){
    inner class ViewHolder(view: View, val binding: StdItemLayoutBinding)
        :RecyclerView.ViewHolder(view){
        init {
            binding.txtEdit.setOnClickListener{
                val item = items[adapterPosition]
                Toast.makeText(context,"Click on movie: ${item.id} Year: ${item.name}",Toast.LENGTH_SHORT ).show()

                val context:Context = view.context
                val intent = Intent(context,EditDeleteActivity::class.java)
                intent.putExtra("mId",item.id)
                intent.putExtra("mName",item.name)
                intent.putExtra("mAge",item.age.toString())
                context.startActivity(intent)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StdItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ViewHolder(binding.root,binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        binding.txtId.text = "ID: " + items[position].id
        binding.txtName.text = "Name: " + items[position].name
        binding.txtAge.text = "Age: " + items[position].age.toString()
    }

    override fun getItemCount(): Int {
        return  items.size
    }
}