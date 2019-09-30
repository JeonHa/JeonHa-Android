package com.song2.jeonha.UI.Hanok


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.song2.jeonha.R
import kotlinx.android.synthetic.main.fragment_slider.*
import org.jetbrains.anko.support.v4.ctx

/**
 * A simple [Fragment] subclass.
 */
class SliderMainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slider, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val image:String=arguments!!.getString("background_url")



        Glide.with(ctx).load(image).into(img_fragment_main)
    }

}
