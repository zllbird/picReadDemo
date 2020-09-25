package com.bird.mm.ui.scheme

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil
import com.bird.mm.AppExecutors
import com.bird.mm.R
import com.bird.mm.databinding.GirlDetailItemBinding
import com.bird.mm.databinding.ShcemeItemBinding
import com.bird.mm.databinding.ShcemeItemBindingImpl
import com.bird.mm.databinding.TitleItemBinding
import com.bird.mm.ui.common.DataBoundListAdapter
import com.bird.mm.ui.common.DataBoundPageAdapter
import com.bird.mm.util.Util
import com.bird.mm.vo.SchemeItem

class TitleAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val click: ((SchemeItem?)->Unit) = {}
): DataBoundListAdapter<SchemeItem, TitleItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<SchemeItem>(){
        override fun areItemsTheSame(oldItem: SchemeItem, newItem: SchemeItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SchemeItem, newItem: SchemeItem): Boolean {
            return oldItem.id == newItem.id
        }

    }
) {
    override fun createBinding(parent: ViewGroup): TitleItemBinding {
        val binding = DataBindingUtil.inflate<TitleItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.title_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            click(binding.schmeItem)
        }

        binding.root.setOnLongClickListener {
            return@setOnLongClickListener true
        }

        return binding
    }

    override fun bind(binding: TitleItemBinding, item: SchemeItem, position: Int) {
        binding.schmeItem = item
//        binding.vm = viewModel
    }


}