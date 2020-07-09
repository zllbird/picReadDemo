package com.bird.mm.ui.play

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.bird.mm.repository.SchemeRepository
import com.bird.mm.repository.UserRepository
import javax.inject.Inject

class PlayViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _webUrl = MutableLiveData<String>()

    val playUrl: LiveData<String> = _webUrl.switchMap {
        userRepository.loadPlay(it)
    }

    fun setWebUrl(webUrl: String) {
        if (_webUrl.value != webUrl) {
            _webUrl.value = webUrl
        }
    }

}