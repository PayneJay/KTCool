package com.ktcool.module_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ktcool.common.constant.RouterMap
import com.ktcool.common.router.MyRouter
import com.snail.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 首页Fragment
 */
class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider.NewInstanceFactory().create(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_home.setOnClickListener {
            MyRouter.getInstance().navigation(RouterMap.LOGIN_ACTIVITY)
        }

        btn_home_jump.setOnClickListener {
            MyRouter.getInstance()
                .withString("String", "Hello World!")
                .withInt("Integer", 24)
                .withBool("Boolean", true)
                .navigation(RouterMap.SECOND_ACTIVITY)
        }

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            text_home.text = it
        })
    }

}