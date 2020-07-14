/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bird.mm.ui.common

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.bird.mm.AppExecutors

/**
 * A generic RecyclerView adapter that uses Data Binding & DiffUtil.
 *
 * @param <T> Type of the items in the list
 * @param <V> The type of the ViewDataBinding
</V></T> */
abstract class DataBoundListAdapter<T, V : ViewDataBinding>(
    appExecutors: AppExecutors,
    diffCallback: DiffUtil.ItemCallback<T>
) : PagedListAdapter<T, DataBoundViewHolder<V>>(
    AsyncDifferConfig.Builder<T>(diffCallback)
        .setBackgroundThreadExecutor(appExecutors.diskIO())
        .build()
) {

    private val holders : MutableList<DataBoundViewHolder<V>> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        val binding = createBinding(parent)
        val holder = DataBoundViewHolder(binding)
        binding.lifecycleOwner = holder
        holder.markCreated()
        holders.add(holder)
        return holder
    }

    open fun testForObserver(context: Context, holder: DataBoundViewHolder<V>) {
    }

    override fun onViewAttachedToWindow(holder: DataBoundViewHolder<V>) {
        super.onViewAttachedToWindow(holder)
        holder.markAttach()
    }

    override fun onViewDetachedFromWindow(holder: DataBoundViewHolder<V>) {
        super.onViewDetachedFromWindow(holder)
        holder.markDetach()
    }

    protected abstract fun createBinding(parent: ViewGroup): V

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {
        bind(holder.binding, getItem(position)!!,position)
        testForObserver(holder.itemView.context,holder)
        holder.binding.executePendingBindings()
    }

    protected abstract fun bind(binding: V, item: T, position: Int )

    fun destory(){
        holders.forEach { it.markDestroyed() }
    }
}
