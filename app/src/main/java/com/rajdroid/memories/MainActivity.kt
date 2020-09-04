package com.rajdroid.memories

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rajdroid.memories.adapters.MemoriesAdapter
import com.rajdroid.memories.adapters.suppilers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var list :List<Place>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        floatingbutton.setOnClickListener {
            val intent = Intent(this,add_memories::class.java)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation=LinearLayoutManager.VERTICAL
        recyclerVIEW.layoutManager=layoutManager

        val adapter=MemoriesAdapter(this, suppilers.m)
        recyclerVIEW.adapter=adapter



//        go_to_faltu.setOnClickListener {
//            val intent = Intent(this,faltu::class.java)
//            startActivity(intent)
//        }

//        dataBASE.setOnClickListener {
//            var entity=Place(0,"a","b","2020")
//            val dao= Placedatabase.getInstance(this)!!.placeDao()
////            CoroutineScope(IO).launch {
////                dao.Insert(entity)
////                Log.i("a", "added")
////            }
////
//            CoroutineScope(IO).launch {
//                list = dao.getAllUsers()
//                Log.i("aa",list.toString())
//            }
//            }
    }
}

