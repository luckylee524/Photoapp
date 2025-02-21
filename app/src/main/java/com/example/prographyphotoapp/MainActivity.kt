package com.example.prographyphotoapp

import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.ScrollView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var scrollView: ScrollView
    private var imageCount = 0 // 로드된 이미지 개수 추적

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.photogridse)
        scrollView = findViewById(R.id.gridScroll)

        val allImages = mutableListOf(
            ImageItem("photogriditem0", false),
            ImageItem("photogriditem1", true),
            ImageItem("photogriditem2", false),
            ImageItem("photogriditem3", false),
            ImageItem("photogriditem4", false),
            ImageItem("photogriditem5", false),
            ImageItem("photogriditem6", false),
            ImageItem("photogriditem7", false),
            ImageItem("photogriditem8", false),
            ImageItem("photogriditem9", false)
        )

        // cardsimg 버튼을 클릭했을 때 화면 전환
        val cardsImage: ImageView = findViewById(R.id.cardsimg)
        cardsImage.setOnClickListener {
            // Intent로 객체 리스트를 전달
            val intent = Intent(this, PhotocardActivity::class.java)

            // ImageItem 객체 리스트를 전달
            intent.putExtra("imageItems", ArrayList(allImages))  // ArrayList로 전달

            startActivity(intent)
        }

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val view = scrollView.getChildAt(scrollView.childCount - 1) as View
            val diff = view.bottom - (scrollView.height + scrollView.scrollY)

            if (diff == 0) {  // 맨 하단에 도달하면
                // 이미지 리스트의 끝에 도달했을 때 마지막 2개 이미지를 계속 로드
                if (imageCount < allImages.size) {
                    // imageCount가 allImages.size보다 작을 때는 2개씩 추가
                    val nextImages = allImages.subList(imageCount, (imageCount + 2).coerceAtMost(allImages.size))
                    loadImages(nextImages)
                } else {
                    // 배열 끝에 도달하면 마지막 2개 이미지만 계속 로드
                    val lastTwoImages = allImages.subList(allImages.size - 2, allImages.size)
                    loadImages(lastTwoImages)
                }
            }
        }

        // 처음 8개 이미지를 로드
        loadImages(allImages.subList(imageCount, imageCount + 8))
    }

    // loadImages 함수의 파라미터 타입을 List<ImageItem>로 수정
    fun loadImages(imageList: List<ImageItem>) {

        imageList.forEach { imageItem ->
            val imageName = imageItem.imageName // ImageItem 객체의 imageName을 사용
            val drawableId = resources.getIdentifier(imageName, "drawable", packageName)
            val drawable: Drawable? = ContextCompat.getDrawable(this, drawableId)

            val imageView = ImageView(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 166.toPx()
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    setMargins(15.toPx(), 10.toPx(), 10.toPx(), 10.toPx())
                }
                scaleType = ImageView.ScaleType.CENTER_CROP
                setImageDrawable(drawable)

                // ✅ 이미지 클릭 시 CardActivity로 이동
                setOnClickListener {
                    val intent = Intent(this@MainActivity, CardActivity::class.java)
                    intent.putExtra("imageItem", imageItem)  // ImageItem 객체를 넘김
                    startActivity(intent)
                }
            }

            gridLayout.addView(imageView)
        }

        // 이미지 추가 후 imageCount 업데이트
        imageCount += imageList.size
    }
}


fun Float.toPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun Int.toPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}
