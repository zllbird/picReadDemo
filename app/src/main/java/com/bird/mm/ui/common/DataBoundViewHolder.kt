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

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView

/**
 * A generic ViewHolder that works with a [ViewDataBinding].
 * @param <T> The type of the ViewDataBinding.
</T> */
class DataBoundViewHolder<out T : ViewDataBinding> constructor(val binding: T) :
    RecyclerView.ViewHolder(binding.root) , LifecycleOwner{
    private val lifecycleRegistry = LifecycleRegistry(this)
    private var wasPaused: Boolean = false

    init {
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
    }

    fun markCreated() {
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }
    fun markAttach() {
        if (wasPaused) {
            lifecycleRegistry.currentState = Lifecycle.State.RESUMED
            wasPaused = false
        } else {
            lifecycleRegistry.currentState = Lifecycle.State.STARTED
        }
    }
    fun markDetach() {
        wasPaused = true
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }
    fun markDestroyed() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

}
