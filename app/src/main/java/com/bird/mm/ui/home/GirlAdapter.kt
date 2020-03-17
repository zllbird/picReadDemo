package com.bird.mm.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.bird.mm.AppExecutors
import com.bird.mm.R
import com.bird.mm.databinding.GirlListItemBinding
import com.bird.mm.ui.common.DataBoundListAdapter
import com.bird.mm.vo.Girl

class GirlAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val girlClickCallBack: ((Girl)->Unit)?
):DataBoundListAdapter<Girl,GirlListItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Girl>(){
        override fun areItemsTheSame(oldItem: Girl, newItem: Girl): Boolean {
            return oldItem.cover == newItem.cover
        }

        override fun areContentsTheSame(oldItem: Girl, newItem: Girl): Boolean {
            return oldItem.title == newItem.title
        }

    }
){
    override fun createBinding(parent: ViewGroup): GirlListItemBinding {
        val binding = DataBindingUtil.inflate<GirlListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.girl_list_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.girl?.let {
                girlClickCallBack?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: GirlListItemBinding, item: Girl,position:Int) {
        binding.girl = item
        binding.index = "$position"
    }

}