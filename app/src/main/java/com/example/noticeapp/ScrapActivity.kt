package com.example.noticeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class ScrapActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrap)

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

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // 내비게이션 메뉴 아이템 클릭시 수행
        when (item.itemId){
            R.id.board -> {
                val boardIntent = Intent(this, MainActivity::class.java)
                startActivity(boardIntent)
                finish()
            }
            R.id.scrap -> {}
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