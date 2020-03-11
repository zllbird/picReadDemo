package com.bird.mm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.bird.mm.repository.UserRepository
import com.bird.mm.util.AbsentLiveData
import com.bird.mm.vo.Girl
import com.bird.mm.vo.Resource
import com.bird.mm.vo.User
import javax.inject.Inject

class HomeViewModel @Inject constructor(userRepository: UserRepository): ViewModel() {

    private val _currentPage = MutableLiveData<Int>()
    private val _currentMTEPage = MutableLiveData<Int>()
    private val _currentTDPage = MutableLiveData<Int>()

    val currentPage: LiveData<Int>
        get() = _currentPage

    val currentMTEPage: LiveData<Int>
        get() = _currentMTEPage

    val currentTDPage: LiveData<Int>
        get() = _currentTDPage

    val users: LiveData<List<Girl>> = _currentPage.switchMap { current ->
        if (current == 0){
            AbsentLiveData.create()
        }else{
            userRepository.loadHome(_cacheGirls,current)
        }
    }

    val usersMTW: LiveData<List<Girl>> = _currentMTEPage.switchMap { current ->
        if (current == 0){
            AbsentLiveData.create()
        }else{
            userRepository.loadHomeMTW(_cacheMTWGirls,current)
        }
    }

    val usersTD: LiveData<List<Girl>> = _currentTDPage.switchMap { current ->
        if (current == 0){
            AbsentLiveData.create()
        }else{
            userRepository.loadHomeTD(_cacheTDGirls,current)
        }
    }

    private val _cacheGirls : List<Girl>?
        get() = users.value

    private val _cacheMTWGirls : List<Girl>?
        get() = usersMTW.value

    private val _cacheTDGirls : List<Girl>?
        get() = usersTD.value

    fun setCurrentPage(current:Int){
        if (_currentPage.value != current){
            _currentPage.value = current
        }
    }

    fun setCurrentMTWPage(current:Int){
        if (_currentMTEPage.value != current){
            _currentMTEPage.value = current
        }
    }

    fun setCurrentTDPage(current:Int){
        if (_currentTDPage.value != current){
            _currentTDPage.value = current
        }
    }

    fun loadNextPage() {
        val now = currentPage.value
        _currentPage.value = now?.plus(1)
    }

    fun loadNextTDPage() {
        val now = _currentTDPage.value
        _currentTDPage.value = now?.plus(1)
    }

}