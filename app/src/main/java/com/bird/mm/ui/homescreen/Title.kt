/*
 * Copyright 2019, The Android Open Source Project
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

package com.example.android.navigationadvancedsample.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bird.mm.R
import com.bird.mm.util.ParseWebUrlHelper
import timber.log.Timber

/**
 * Shows the main title screen with a button that navigates to [About].
 */
class Title : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_title, container, false)

        view.findViewById<Button>(R.id.about_btn).setOnClickListener {
            findNavController().navigate(R.id.action_title_to_about)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //        val url = "http://www.yhdm.tv/v/4992-10.html"
//        val url = "https://y.qq.com/n/yqq/song/000B4ijs4Ufwql.html"
//        ParseWebUrlHelper.init(activity,url)
//        ParseWebUrlHelper.setOnParseListener(object : ParseWebUrlHelper.OnParseWebUrlListener{
//            override fun onFindUrl(url: String?) {
//                Timber.i("url : $url")
//            }
//
//            override fun onError(errorMsg: String?) {
//                Timber.i("errorMsg : $errorMsg")
//            }
//        })
//        ParseWebUrlHelper.startParse()


    }

}
