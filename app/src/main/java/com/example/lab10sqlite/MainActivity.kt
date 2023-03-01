package com.example.lab10sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab10sqlite.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHandler: DatabaseHelper
    private var studentList = arrayListOf<Student>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHandler = DatabaseHelper.getInstance(this)
        dbHandler.writableDatabase
        callStudentData()
        binding.recyclerView.adapter = StudentsAdapter(studentList,applicationContext)
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(applicationContext,LinearLayoutManager.VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        callStudentData()
    }
    fun callStudentData(){
        studentList.clear()
        studentList.addAll(dbHandler.getAllStudents())
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    fun addStudentDialog(view: View){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.insert_layout,null)
        val myBuilder = AlertDialog.Builder(this)
        myBuilder.apply {
            setView(mDialogView)

            setPositiveButton("Save"){ dialog,_->
                val id = mDialogView.findViewById(R.id.edtId) as TextInputEditText
                val name = mDialogView.findViewById(R.id.edtName) as TextInputEditText
                val age = mDialogView.findViewById(R.id.edtAge) as TextInputEditText
                var result = dbHandler.insertStudent(Student(id= id.text.toString(),name=name.text.toString(),age=age.text.toString().toInt()))
                if(result>-1){
                    Toast.makeText(applicationContext,"The student is inserted successfully",Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    callStudentData()
                }else{
                    Toast.makeText(applicationContext,"Insert Failure",Toast.LENGTH_SHORT).show()
                }
            }
            setNegativeButton("Cancel",){dialog,_->
                dialog.dismiss()
            }
            show()
        }
    }
}