package com.ktcool.module_dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class DashboardFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var data: MutableList<DashboardItemBean> = ArrayList()

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider.NewInstanceFactory().create(DashboardViewModel::class.java)
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val refreshLayout: SmartRefreshLayout = view.findViewById(R.id.refresh_layout)
        recyclerView = view.findViewById(R.id.recycler_view)

        //设置下拉刷新上拉加载
        refreshLayout.setRefreshHeader(ClassicsHeader(activity))
            .setRefreshFooter(ClassicsFooter(activity))
            .setOnRefreshListener {
                refreshLayout.finishRefresh(2000)
            }.setOnLoadMoreListener {
                val size = data.size
                if (size > 100) {
                    refreshLayout.finishLoadMoreWithNoMoreData()
                    return@setOnLoadMoreListener
                }
                for (i in size..size + 20) {
                    data.add(DashboardItemBean("这个是第${i}个条目", ""))
                }
                recyclerView?.adapter?.notifyDataSetChanged()
                refreshLayout.finishLoadMore(2000)
            }

        //设置recyclerView的配置
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        data = MutableList(20) { i -> DashboardItemBean("这个是第${i}个条目", "") }
        recyclerView?.adapter = DashboardAdapter(data)

    }
}