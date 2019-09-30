package com.song2.jeonha.UI.Main.Mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.song2.jeonha.UI.Main.Mypage.TapAdapter.TapPagerAdapter
import com.song2.jeonha.R
import kotlinx.android.synthetic.main.activity_apply_history.*

class ApplyHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_history)
        iv_apply_history_back_btn.setOnClickListener {
            finish()
        }
    }
   

    override fun onResume() {
        super.onResume()
        configureMainTab()
    }
    private fun configureMainTab( ) {
        vp_apply_contents.adapter = TapPagerAdapter(supportFragmentManager, 2)
        vp_apply_contents.offscreenPageLimit = 2
        tl_apply_tabbar.setupWithViewPager(vp_apply_contents)

        val navCategoryMainLayout: View =
            (this.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.tabar_apply_tap,
                null,
                false
            )
        tl_apply_tabbar.getTabAt(0)!!.customView=navCategoryMainLayout.findViewById(R.id.rl_tap_stay_apply_all) as RelativeLayout
        tl_apply_tabbar.getTabAt(1)!!.customView=navCategoryMainLayout.findViewById(R.id.rl_tap_class_apply_all) as RelativeLayout
    }
}
