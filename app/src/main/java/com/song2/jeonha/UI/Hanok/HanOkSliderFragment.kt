package com.song2.jeonha.UI.Hanok


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.song2.jeonha.R

/**
 * A simple [Fragment] subclass.
 */
class HanOkSliderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_han_ok_slider, container, false)
    }


}
