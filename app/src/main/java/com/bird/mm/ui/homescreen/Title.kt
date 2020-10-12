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
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bird.mm.AppExecutors
import com.bird.mm.R
import com.bird.mm.binding.FragmentDataBindingComponent
import com.bird.mm.databinding.FragmentTitleBinding
import com.bird.mm.di.Injectable
import com.bird.mm.ui.homescreen.TitleViewModel
import com.bird.mm.ui.scheme.TitleAdapter
import com.bird.mm.util.QmcDecoder
import com.bird.mm.util.autoCleared
import timber.log.Timber
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import javax.inject.Inject

/**
 * Shows the main title screen with a button that navigates to [About].
 */
class Title : Fragment() , Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var dataBindingComponent = FragmentDataBindingComponent(this)

    val titleViewModel : TitleViewModel by viewModels { viewModelFactory }

    var bind by autoCleared<FragmentTitleBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.fragment_title, container, false)
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            inflater,
            R.layout.fragment_title,
            container,
            false,
            dataBindingComponent
        )
        bind = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.vm = titleViewModel
        bind.lifecycleOwner = viewLifecycleOwner
        bind.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = TitleAdapter(dataBindingComponent, appExecutors) {
        }
        bind.rvItems.adapter = adapter

        titleViewModel.aliCase.observe(viewLifecycleOwner , Observer {
//            bind.tvResult.text = "Current：$it"
        })

//        titleViewModel.currentTimes.observe(viewLifecycleOwner, Observer {
//            bind.tvResult.text = "Current：$it"
//        })

        titleViewModel.datas.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        titleViewModel.successNum.observe(viewLifecycleOwner, Observer {
            bind.tvResultSu.text = "Success：$it"
        })

        titleViewModel.perTimes.observe(viewLifecycleOwner, Observer {
            bind.tvResultPer.text = "AverageTime：%.2f(ms)".format(it)
        })

        titleViewModel.minTime.observe(viewLifecycleOwner, Observer {
            bind.tvResultMin.text = "MinTime：%d(ms)".format(it)
        })

        titleViewModel.maxTime.observe(viewLifecycleOwner, Observer {
            bind.tvResultMax.text = "MaxTime：%d(ms)".format(it)
        })

        titleViewModel.lager300.observe(viewLifecycleOwner, Observer {
            bind.tvResultLager300.text = "OVER 300ms Count ：%d".format(it)
        })

        titleViewModel.lager500.observe(viewLifecycleOwner, Observer {
            bind.tvResultLager500.text = "OVER 500ms Count ：%d".format(it)
        })

    }

    fun cusDecode(){
        val rootDir = context?.getExternalFilesDir(null)
        val qmcFlacDir = File(rootDir,"qmcflac")
        val qmcFlacFile = File(qmcFlacDir , "1.qmcflac")

        Timber.i("qmcFlacFile.isFile ${qmcFlacFile.isFile}")
        Timber.i("qmcFlacFile.path ${qmcFlacFile.path}")
        Timber.i("qmcFlacFile.path ${qmcFlacFile.absolutePath}")

        val result = QmcDecoder().covert(qmcFlacFile)
        Timber.i("QmcDecoder $result")
    }

    fun execNotWork(){
        appExecutors.diskIO().run {
//            val rootDir = Environment.getExternalStorageDirectory()
//            val qmcFlacDir = File(rootDir , "/Huawei/CloudDrive/Download/Huawei Drive/qmcflac")

            val rootDir = context?.getExternalFilesDir(null)
            val qmcFlacDir = File(rootDir,"qmcflac")
            val qmcFlacDecodeCommond = File(qmcFlacDir , "decoder.command")
            val qmcFlacDecode = File(qmcFlacDir , "decoder")

            Timber.i("qmcFlacDir.isDirectory ${qmcFlacDir.isDirectory}")
            Timber.i("qmcFlacDecodeCommond.isFile ${qmcFlacDecodeCommond.isFile}")
            Timber.i("qmcFlacDecodeCommond.canExecute ${qmcFlacDecodeCommond.canExecute()}")
            Timber.i("qmcFlacDecode.canExecute ${qmcFlacDecode.canExecute()}")

            val p = Runtime.getRuntime().exec("decoder.command")
//            val resultCode = result.waitFor()
//            Timber.i("resultCode $resultCode")

            var data: String? = null
            val ie = BufferedReader(InputStreamReader(p.getErrorStream()))
            val `in` = BufferedReader(InputStreamReader(p.getInputStream()))
            var error: String? = null
            while (ie.readLine().also({ error = it }) != null
                && error != "null"
            ) {
                data += """
                    $error
                    
                    """.trimIndent()
            }
            var line: String? = null
            while (`in`.readLine().also({ line = it }) != null
                && line != "null"
            ) {
                data += """
                    $line
                    
                    """.trimIndent()
            }

            Timber.i(data)


        }
    }

//    fun testCommond(){
//        val assetManager = context?.assets
//        assetManager?.list()
//    }
//    }
//
//    fun initConnect(){
//        mDiscoveryManager.registerDefaultDeviceTypes()
//        mDiscoveryManager.pairingLevel = DiscoveryManager.PairingLevel.ON
//        mDiscoveryManager.start()
//        setupPicker()
//    }

//    private fun setupPicker() {
//        dp = DevicePicker(activity)
//        dialog = dp?.getPickerDialog("Device List"
//        ) { arg0, arg1, arg2, arg3 ->
//            mTV = arg0.getItemAtPosition(arg2) as ConnectableDevice
//            mTV?.addListener(deviceListener)
//            mTV?.setPairingType(null)
//            mTV?.connect()
////            connectItem.setTitle(mTV.getFriendlyName())
//            dp?.pickDevice(mTV)
//        }
////        pairingAlertDialog = AlertDialog.Builder(this)
////            .setTitle("Pairing with TV")
////            .setMessage("Please confirm the connection on your TV")
////            .setPositiveButton("Okay", null)
////            .setNegativeButton(
////                "Cancel"
////            ) { dialog, which ->
////                dp.cancelPicker()
////                hConnectToggle()
////            }
////            .create()
////        val input = EditText(this)
////        input.inputType = InputType.TYPE_CLASS_TEXT
////        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
////        pairingCodeDialog = AlertDialog.Builder(this)
////            .setTitle("Enter Pairing Code on TV")
////            .setView(input)
////            .setPositiveButton(
////                android.R.string.ok
////            ) { arg0, arg1 ->
////                if (mTV != null) {
////                    val value = input.text.toString().trim { it <= ' ' }
////                    mTV.sendPairingKey(value)
////                    imm.hideSoftInputFromWindow(input.windowToken, 0)
////                }
////            }
////            .setNegativeButton(
////                android.R.string.cancel
////            ) { dialog, whichButton ->
////                dp.cancelPicker()
////                hConnectToggle()
////                imm.hideSoftInputFromWindow(input.windowToken, 0)
////            }
////            .create()
//    }

//    fun play(playUrl:String){
//
//        val miniType = if(playUrl.contains("\\.mp4"))"video/mp4" else "application/x-mpegURL"
////        val miniType = "application/x-mpegURL"
//        val mediaInfo = MediaInfo.Builder(playUrl,miniType)
//            .setTitle("Play")
//            .setDescription("PlayInfo")
//            .build()
//        val player = mTV?.getCapability(MediaPlayer::class.java)
//        player?.playMedia(mediaInfo, false, object : MediaPlayer.LaunchListener {
//            override fun onSuccess(`object`: MediaPlayer.MediaLaunchObject?) {
//            }
//
//            override fun onError(error: ServiceCommandError?) {
//            }
//
//        })
//    }
//
//    private val deviceListener: ConnectableDeviceListener = object : ConnectableDeviceListener {
//        override fun onPairingRequired(
//            device: ConnectableDevice,
//            service: DeviceService,
//            pairingType: PairingType
//        ) {
//            Log.d("2ndScreenAPP", "Connected to " + mTV!!.ipAddress)
//            when (pairingType) {
//                PairingType.FIRST_SCREEN -> {
//                    Log.d("2ndScreenAPP", "First Screen")
//                }
//                PairingType.PIN_CODE, PairingType.MIXED -> {
//                    Log.d("2ndScreenAPP", "Pin Code")
//                }
//                PairingType.NONE -> {
//                }
//                else -> {
//                }
//            }
//        }
//
//        override fun onConnectionFailed(
//            device: ConnectableDevice,
//            error: ServiceCommandError
//        ) {
//            Log.d("2ndScreenAPP", "onConnectFailed")
//            connectFailed(mTV)
//        }
//
//        override fun onDeviceReady(device: ConnectableDevice) {
//            Log.d("2ndScreenAPP", "onPairingSuccess")
////            if (pairingAlertDialog.isShowing()) {
////                pairingAlertDialog.dismiss()
////            }
////            if (pairingCodeDialog.isShowing()) {
////                pairingCodeDialog.dismiss()
////            }
//            registerSuccess(mTV)
//        }
//
//        override fun onDeviceDisconnected(device: ConnectableDevice) {
//            Log.d("2ndScreenAPP", "Device Disconnected")
//            connectEnded(mTV)
////            connectItem.setTitle("Connect")
////            val frag: BaseFragment = mSectionsPagerAdapter.getFragment(mViewPager.getCurrentItem())
////            if (frag != null) {
////                Toast.makeText(getApplicationContext(), "Device Disconnected", Toast.LENGTH_SHORT)
////                    .show()
////                frag.disableButtons()
////            }
//        }
//
//        override fun onCapabilityUpdated(
//            device: ConnectableDevice,
//            added: List<String>,
//            removed: List<String>
//        ) {
//        }
//    }
//
//    private fun connectFailed(mTV: ConnectableDevice?) {
//        if (mTV != null) {
//            mTV.removeListener(deviceListener)
//            mTV.disconnect()
////            mTV = null
//        }
//    }
//
//    private fun connectEnded(mTV: ConnectableDevice?) {
//
//    }
//
//    private fun registerSuccess(mTV: ConnectableDevice?) {
////
//    }

}
