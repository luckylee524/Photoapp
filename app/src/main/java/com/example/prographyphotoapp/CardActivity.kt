package com.example.prographyphotoapp

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat

class CardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_card)

        val cardsImage: ImageView = findViewById(R.id.xbutton)
        cardsImage.setOnClickListener {
            // Intent로 화면 전환
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 📌 ImageItem 객체를 Intent로 받음
        val imageItem = intent.getSerializableExtra("imageItem") as? ImageItem
        imageItem?.let {
            // 📌 ImageItem에서 이미지 이름을 사용하여 리소스를 가져옴
            val drawableId = resources.getIdentifier(it.imageName, "drawable", packageName)
            Log.d("DEBUG", "Drawable ID: $drawableId") // 디버깅 로그

            // 📌 이미지 리소스를 Drawable로 가져오기
            val drawable = ContextCompat.getDrawable(this, drawableId) ?: return

            // 📌 XML에 정의된 cardimageview 찾고 이미지 설정
            val imageView = findViewById<ImageView>(R.id.cardimageview)

            // 이미지 비율에 맞춰서 높이 계산하기
            val width = resources.displayMetrics.widthPixels // 가로 사이즈
            val bitmap = (drawable as BitmapDrawable).bitmap
            val aspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat() // 이미지 비율 계산

            // 계산된 비율로 높이 설정
            val height = (width / aspectRatio).toInt()

            // LayoutParams를 설정하여 ImageView의 높이를 조정
            val layoutParams = imageView.layoutParams
            layoutParams.width = width
            layoutParams.height = height
            imageView.layoutParams = layoutParams

            imageView.setImageDrawable(drawable)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP // 이미지 비율에 맞게 크롭

            // 📌 ImageItem의 isTrue 값에 따라 alpha 값 설정
            val bookmarkImageView = findViewById<ImageView>(R.id.bookmark)
            if (it.ismarked) {
                bookmarkImageView.alpha = 1f // alpha를 1로 설정
            } else {
                bookmarkImageView.alpha = 0.3f // alpha를 0.3으로 설정
            }
        }
    }
}
