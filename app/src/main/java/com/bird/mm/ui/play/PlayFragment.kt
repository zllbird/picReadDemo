package com.bird.mm.ui.play

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bird.mm.AppExecutors
import com.bird.mm.R
import com.bird.mm.binding.FragmentDataBindingComponent
import com.bird.mm.databinding.FragmentPlayBinding
import com.bird.mm.di.Injectable
import com.bird.mm.util.XML2List
import com.bird.mm.util.autoCleared
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class PlayFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var dataBindingComponent = FragmentDataBindingComponent(this)

    val playViewModel by viewModels<PlayViewModel> { viewModelFactory }

    var bind by autoCleared<FragmentPlayBinding>()

    //    private val args: HomeDetailFragmentArgs by navArgs()
    private val args by navArgs<PlayFragmentArgs>()
    private lateinit var player :SimpleExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPlayBinding>(
            inflater,
            R.layout.fragment_play,
            container,
            false,
            dataBindingComponent
        )
        bind = binding
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.lifecycleOwner = viewLifecycleOwner
        player = SimpleExoPlayer.Builder(context!!).build()
        // Bind the player to the view.
        bind.playerView.player = player
        // Produces DataSource instances through which media data is loaded.
        // Produces DataSource instances through which media data is loaded.
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context!!, "yourApplicationName")
        )

        playViewModel.playUrl.observe(viewLifecycleOwner, Observer{
            // This is the MediaSource representing the media to be played.
            val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(it))
            // Prepare the player with the source.
            player.prepare(videoSource)
            player.playWhenReady = true
        })

//        playViewModel.setWebUrl(args.link)
        bind.web.settings.javaScriptEnabled =true
        bind.web.webViewClient = PlayWebViewClient()
        bind.web.addJavascriptInterface(PlayJavascriptInterface(),"java_obj")
        bind.web.loadUrl(args.link)
    }

    fun playVideo(url: String){
        appExecutors.mainThread().execute {
            val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context!!, "yourApplicationName")
            )
            val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(url))
            // Prepare the player with the source.
            player.prepare(videoSource)
            player.playWhenReady = true
        }

    }

    inner class PlayJavascriptInterface  {

        @JavascriptInterface
        fun onHtml(html:String) {
            Timber.i(html)
            val url = XML2List.xml2UKnowPlayUrl(html)
            val result = url.replace("amp;","")
            playVideo(result)
        }


    }

    inner class PlayWebViewClient : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            bind.web.loadUrl("javascript:window.java_obj.onHtml('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
        }

    }

}