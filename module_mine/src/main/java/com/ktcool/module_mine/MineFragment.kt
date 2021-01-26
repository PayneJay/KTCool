package com.ktcool.module_mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.ktcool.common.constant.RouterMap
import com.ktcool.common.router.MyRouter
import com.snail.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * 我的Fragment
 */
class MineFragment : BaseFragment() {

    private lateinit var mineViewModel: MineViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mineViewModel =
            ViewModelProvider.NewInstanceFactory().create(MineViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mine, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        textView.text = "点击测试"
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_notifications.setOnClickListener {
            MyRouter.getInstance().navigation(RouterMap.THIRD_ACTIVITY)
        }
    }

}