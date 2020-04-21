package com.bird.mm.ui.background

import androidx.lifecycle.*
import com.bird.mm.repository.UserRepository
import javax.inject.Inject

class BGViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel(){

//    private var savedStateHandle : SavedStateHandle? = null
    private val _name = MutableLiveData<String>()

    companion object{
        const val KEY = "key"
        const val DEFAULT = "default"
    }

//    init {
//        if (savedStateHandle != null && !savedStateHandle!!.contains(KEY)){
//            savedStateHandle!!.set(KEY, DEFAULT)
//        }
//    }

//    private val _repoResult = savedStateHandle!!.getLiveData<String>(KEY).map {
//        userRepository.cardOfBGBD(it,30)
//    }

    private val _repoResult =_name.map {
//        userRepository.cardOfBGBD(it,30)
        userRepository.cardOfBGBDPageSize(it,30)
    }

    val bgPhotos = _repoResult.switchMap { it.pagedList }
    val networkState = _repoResult.switchMap { it.networkState!! }

    fun showSubreddit(subreddit: String): Boolean {
        _name.value = subreddit
        return false
    }

}