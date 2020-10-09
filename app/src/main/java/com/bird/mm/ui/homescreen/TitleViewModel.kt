package com.bird.mm.ui.homescreen

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.bird.mm.net.MMResource
import com.bird.mm.repository.MMNetResource
import com.bird.mm.repository.SchemeRepository
import com.bird.mm.vo.SchemeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class TitleViewModel @Inject constructor(private val schemeRepository: SchemeRepository): ViewModel() {

//    var aliWebUrl : String = "http://fengjing-global.kajicam.com/ad/api/hello"
    var aliWebUrl : String = "http://ad-global-rule.kajicam.com/ad/splash/rule/api/v2/"

    var maxTimes = 1000

    private val _load = MutableLiveData<Int>()

    val aliCase = _load.switchMap {
        liveData(Dispatchers.IO) {
            while (maxTimes > 0) {
                maxTimes--
                emit(schemeRepository.alitestSus(aliWebUrl))
            }
        }
    }

    val datas = _load.switchMap {
        schemeRepository.queryAli()
    }

    val successNum = datas.map { list ->
        list.filter { it.status == 200 }.count()
    }

    val perTimes = datas.map { list ->
        list.filter { it.status == 200 }.map { it.useTime }.average()
    }

    val maxTime = datas.map { list ->
        list.filter { it.status == 200 }.map { it.useTime }.max()
    }

    val minTime = datas.map { list ->
        list.filter { it.status == 200 }.map { it.useTime }.min()
    }

    val currentTimes = _load.switchMap {
        schemeRepository.queryALiCount()
    }

    fun load(){
        if (_load.value == null){
            _load.value = 0
        }else{
            _load.value = 1
        }
//        schemeRepository.alitest(aliWebUrl,maxTimes)
    }

}