package com.mobdeve.group11.assist

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class EditEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        val btnAddGroups = findViewById<Button>(R.id.btn_edit_groups)
        btnAddGroups.setOnClickListener {
            val builder = AlertDialog.Builder(this@EditEventActivity)


            val groupsArray = resources.getStringArray(R.array.sample_groups) //sample
            val checkedGroups = booleanArrayOf(true, true) //sample

            val groupList = listOf(*groupsArray)
            builder.setTitle("Select colors")
            builder.setMultiChoiceItems(groupsArray, checkedGroups) { dialog, which, isChecked ->
                checkedGroups[which] = isChecked
                val currentItem = groupList[which]
                Toast.makeText(applicationContext, "$currentItem $isChecked", Toast.LENGTH_SHORT)
                    .show()
            }
            builder.setPositiveButton("OK") { dialog, which ->
                // Do something when selected
            }
            builder.setNeutralButton("Cancel") { dialog, which ->
                // Do something when unselected
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
}