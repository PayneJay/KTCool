package com.ktcool.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ktcool.R
import com.ktcool.common.constant.RouterMap
import com.ktcool.common.router.MyRouter

class DashboardAdapter(data: MutableList<DashboardItemBean>) :
    RecyclerView.Adapter<DashboardAdapter.DashboardVH>() {
    private var mData: MutableList<DashboardItemBean> = data


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardVH = DashboardVH(
        LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard_layout, null, false)
    )

    override fun onBindViewHolder(holder: DashboardVH, position: Int) {
        holder.tvDesc.text = mData[position].desc
        holder.tvDesc.setOnClickListener {
            MyRouter.getInstance().navigation(RouterMap.TEST_ACTIVITY)
        }
    }

    override fun getItemCount(): Int = mData.size - 1

    class DashboardVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDesc: TextView = itemView.findViewById(R.id.tv_item_desc)
    }
}