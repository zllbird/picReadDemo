package com.bird.mm.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.bird.mm.AppExecutors
import com.bird.mm.R
import com.bird.mm.databinding.GirlDetailItemBinding
import com.bird.mm.ui.common.DataBoundListAdapter

class GirlDetailPhotoAdapter (
    private val dataBindingComponent: DataBindingComponent,
    private val appExecutors: AppExecutors
):DataBoundListAdapter<String,GirlDetailItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
){
    override fun createBinding(parent: ViewGroup): GirlDetailItemBinding {
        val binding = DataBindingUtil.inflate<GirlDetailItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.girl_detail_item,
            parent,
            false,
            dataBindingComponent
        )
        return binding
    }

    override fun bind(binding: GirlDetailItemBinding, item: String) {
        binding.imgUrl = item
    }

}