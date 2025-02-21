package com.example.prographyphotoapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

class PhotocardPagerAdapter(private val context: Context, private val imageList: List<ImageItem>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_photocard, container, false)

        val imageView = view.findViewById<ImageView>(R.id.image)
        val infoButton = view.findViewById<ImageView>(R.id.info)

        val imageItem = imageList[position]

        // ImageItem에서 imageName을 사용하여 리소스를 가져오고 이미지를 설정
        val drawableId = context.resources.getIdentifier(imageItem.imageName, "drawable", context.packageName)
        imageView.setImageResource(drawableId)

        // 상세 정보 버튼 클릭 시 CardActivity로 이동
        infoButton.setOnClickListener {
            val intent = Intent(context, CardActivity::class.java)
            intent.putExtra("imageItem", imageItem)  // ImageItem 객체를 넘김
            context.startActivity(intent)
        }

        container.addView(view)
        return view
    }

    override fun getCount(): Int = imageList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
