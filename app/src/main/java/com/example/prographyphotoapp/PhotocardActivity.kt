package com.example.prographyphotoapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.viewpager.widget.ViewPager

class PhotocardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_photorandom)

        // ImageItem 리스트를 Intent로 받아오기
        val imageItems = intent.getSerializableExtra("imageItems") as? List<ImageItem>

        // 이미지 리소스를 저장할 리스트 (이미지를 리스트로 설정하려면 이 리스트를 사용)
        val imageList = imageItems?.map { imageItem ->
            resources.getIdentifier(imageItem.imageName, "drawable", packageName)
        } ?: emptyList()

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val adapter = PhotocardPagerAdapter(this, imageItems ?: emptyList())
        viewPager.adapter = adapter

        // "홈" 버튼 클릭 시 MainActivity로 이동
        val homeButton: ImageView = findViewById(R.id.houseimg)
        homeButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
