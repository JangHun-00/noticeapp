package com.skku.noticeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val TAG: String = "NoticeApp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // toolbar 연결
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)

        // 내비게이션 뷰 (메뉴바)
        val menuBtn: ImageView = findViewById(R.id.menu_btn)
        val naviView: NavigationView = findViewById(R.id.naviView)
        val layoutDrawer: DrawerLayout = findViewById(R.id.layout_drawer)
        menuBtn.setOnClickListener {
            layoutDrawer.openDrawer(GravityCompat.START)
        }
        naviView.setNavigationItemSelectedListener(this)

        // 필터링 드롭바 생성
        var mySelectedNoticeBoard: String = "notice_list"
        var mySelectedNoticeOrder: String = "no"

        val items1 = resources.getStringArray(R.array.my_array2)
        val items2 = resources.getStringArray(R.array.my_array1)

        val myAdapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items1)
        val myAdapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items2)

        spinner2.adapter = myAdapter1
        spinner1.adapter = myAdapter2

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position1: Int, id: Long) {
                when (position1) {
                    0 -> {mySelectedNoticeBoard = "notice_list"}
                    1 -> {mySelectedNoticeBoard = "academic_affairs"}
                    2 -> {mySelectedNoticeBoard = "admission"}
                    3 -> {mySelectedNoticeBoard = "employment"}
                    4 -> {mySelectedNoticeBoard = "recruitment"}
                    5 -> {mySelectedNoticeBoard = "scholarship"}
                    6 -> {mySelectedNoticeBoard = "event"}
                    7 -> {mySelectedNoticeBoard = "common"}
                    else -> {}
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                mySelectedNoticeBoard = "notice_list"
            }
        }

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position2: Int, id: Long) {
                when (position2) {
                    0 -> {mySelectedNoticeOrder = "no"}
                    1 -> {mySelectedNoticeOrder = "date"}
                    else -> {}
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                mySelectedNoticeOrder = "no"
            }
        }

        /*
        notice_list = arrayListOf(
                Notice("10000", "[성균관대 좋은민주주의연구센터] 정기 월례포럼 개최 '미국 민주주의 위기와 해법'", "https://www.skku.edu/skku/campus/skk_comm/notice01.do?mode=view&articleNo=87629&article.offset=0&articleLimit=10", "2020-20-20", "9999", "true"),
                Notice("20000", "‘GAIA-X’#4단계 BK21 미래인문학기반사회혁신창업교육연구단 전문가 초청 강연회", "https://www.skku.edu/skku/campus/skk_comm/notice01.do?mode=view&articleNo=87505&article.offset=0&articleLimit=10", "2020-10-10", "8888", "true")
        )*/

        // Cloud Firestore (Firebase DB) 연결
        val db = Firebase.firestore
        var notice_list = ArrayList<Notice>()

        fun getFBDatabase(mySelectedNoticeBoard: String, mySelectedNoticeOrder: String): ArrayList<Notice>{
            //Log.e(TAG, "Board: ${mySelectedNoticeBoard}, Order: ${mySelectedNoticeOrder}")
            notice_list = ArrayList<Notice>()
            db.collection(mySelectedNoticeBoard)
                .orderBy(mySelectedNoticeOrder, Query.Direction.DESCENDING)
                .limit(50)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        notice_list.add(document.toObject())
                        //Log.e(TAG, "${document.id} => ${document.data}")
                    }
                    notice_recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    notice_recyclerview.setHasFixedSize(true)
                    notice_recyclerview.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
                    notice_recyclerview.adapter = NoticeAdapter(notice_list)
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Error getting documents: ", exception)
                }
            return notice_list
        }

        getFBDatabase(mySelectedNoticeBoard, mySelectedNoticeOrder)

        filter_apply_btn.setOnClickListener {
            getFBDatabase(mySelectedNoticeBoard, mySelectedNoticeOrder)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // 내비게이션 메뉴 아이템 클릭시 수행
        when (item.itemId){
            R.id.board -> {}
            R.id.scrap -> {
                val scrapIntent = Intent(this, ScrapActivity::class.java)
                startActivity(scrapIntent)
                finish()
            }
        }
        val layoutDrawer: DrawerLayout = findViewById(R.id.layout_drawer)
        layoutDrawer.closeDrawers() // 내비게이션 뷰 닫기
        return false
    }

    override fun onBackPressed() {
        val layoutDrawer: DrawerLayout = findViewById(R.id.layout_drawer)
        if(layoutDrawer.isDrawerOpen(GravityCompat.START))
        {
            layoutDrawer.closeDrawers()
        }
        else {
            super.onBackPressed()
        }
    }
}