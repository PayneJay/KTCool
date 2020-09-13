package com.ktcool.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ktcool.R
import com.ktcool.common.router.ERouter
import com.ktcool.common.router.MyRouter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var textView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider.NewInstanceFactory().create(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        textView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView?.text = it
        })
        btn_jump?.setOnClickListener {
            MyRouter.getInstance()
                .withString("String", "Hello World!")
                .withInt("Integer", 24)
                .withBool("Boolean", true)
                .navigation("/app/secondActivity")
        }
        return root
    }

}