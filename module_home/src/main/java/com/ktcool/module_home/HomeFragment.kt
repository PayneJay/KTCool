package com.ktcool.module_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ktcool.common.constant.RouterMap
import com.ktcool.common.router.MyRouter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var textView: TextView? = null
    private var btnJump: Button? = null

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
        btnJump = root.findViewById(R.id.btn_home_jump)
        btnJump?.setOnClickListener {
            MyRouter.getInstance()
                .withString("String", "Hello World!")
                .withInt("Integer", 24)
                .withBool("Boolean", true)
                .navigation(RouterMap.SECOND_ACTIVITY)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_home.setOnClickListener {
            MyRouter.getInstance().navigation(RouterMap.LOGIN_ACTIVITY)
        }
    }

}