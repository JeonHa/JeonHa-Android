package com.song2.jeonha.UI.Hanok.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.song2.jeonha.UI.Hanok.SliderMainFragment

class SliderMainPagerAdapter(fm: FragmentManager?, val num_fragment: Int, var img : ArrayList<String>): FragmentStatePagerAdapter(fm!!){
    override fun getCount(): Int {
        return num_fragment
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItem(p0: Int): Fragment? {
        //TabLayout , ViewPager가 보여주고자 하는 View의 번호
        var fragment : SliderMainFragment = SliderMainFragment() // 보여질 Fragment는 모두 같은 클래스를 상속
        var bundle : Bundle = Bundle(1) // 한 개 Bundle에 담길 데이터의 개수 지정
        when(p0){
            0->bundle.putString("background_url",img[0])
            1->bundle.putString("background_url",img[1])
            2->bundle.putString("background_url",img[2])
            3->bundle.putString("background_url",img[3])
            4->bundle.putString("background_url",img[4])
            5->bundle.putString("background_url",img[5])
            6->bundle.putString("background_url",img[6])
        }

        fragment.arguments=bundle
        return  fragment
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}