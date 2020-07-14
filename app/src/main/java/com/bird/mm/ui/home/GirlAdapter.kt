package com.bird.mm.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.bird.mm.AppExecutors
import com.bird.mm.R
import com.bird.mm.databinding.GirlListItemBinding
import com.bird.mm.ui.common.DataBoundListAdapter
import com.bird.mm.ui.common.DataBoundViewHolder
import com.bird.mm.ui.scheme.TestLifeCycleObserver
import com.bird.mm.vo.Girl

class GirlAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val girlClickCallBack: ((Girl, View)->Unit)?
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
            binding.girl?.let {girl ->
                girlClickCallBack?.invoke(girl,it)
            }
        }
        return binding
    }

    override fun testForObserver(context: Context,holder: DataBoundViewHolder<GirlListItemBinding>) {
        super.testForObserver(context,holder)
        holder.binding.girl.let {
            val test = TestLifeCycleObserver(context,holder.binding.girl)
            holder.lifecycle.addObserver(test)
        }
    }

    override fun onViewAttachedToWindow(holder: DataBoundViewHolder<GirlListItemBinding>) {
        super.onViewAttachedToWindow(holder)
        holder.markAttach()
    }

    override fun onViewDetachedFromWindow(holder: DataBoundViewHolder<GirlListItemBinding>) {
        super.onViewDetachedFromWindow(holder)
        holder.markDetach()
    }

    override fun onViewRecycled(holder: DataBoundViewHolder<GirlListItemBinding>) {
        super.onViewRecycled(holder)
        holder.markDestroyed()
    }

    override fun bind(binding: GirlListItemBinding, item: Girl,position:Int) {
        binding.girl = item
        binding.index = "$position"
    }

}