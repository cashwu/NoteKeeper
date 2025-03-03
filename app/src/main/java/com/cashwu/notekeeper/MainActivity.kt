package com.cashwu.notekeeper

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.cashwu.notekeeper.databinding.ActivityMainBinding
import com.cashwu.notekeeper.databinding.ContentMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var notePosition = POSITION_NOT_SET

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val adapterCourses = ArrayAdapter<CourseInfo>(
            this,
            android.R.layout.simple_spinner_item,
            DataManager.courses.values.toList())

        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerCourses = binding.contentMain.spinnerCourses
        spinnerCourses.adapter = adapterCourses

        notePosition = intent.getIntExtra(EXTRA_NOTE_POSITION, POSITION_NOT_SET)

        if (notePosition != POSITION_NOT_SET) {
            displayNote()
        }
    }

    private fun displayNote() {
        val contentMain = binding.contentMain
        val note = DataManager.notes[notePosition]

        contentMain.textNoteTitle.setText(note.title)
        contentMain.textNoteText.setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        contentMain.spinnerCourses.setSelection(coursePosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

       if (notePosition >= DataManager.notes.lastIndex) {
           val menuItem = menu?.findItem(R.id.action_next)

           if (menuItem!=null) {
               menuItem.icon = ContextCompat.getDrawable(this, R.drawable.ic_block_black_24)
               menuItem.isEnabled = false
           }
       }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]

        note.title = binding.contentMain.textNoteTitle.text.toString()
        note.text = binding.contentMain.textNoteText.text.toString()
        note.course = binding.contentMain.spinnerCourses.selectedItem as CourseInfo
    }
}