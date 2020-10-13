package com.bird.mm.ui.homescreen

import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
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

    var ga : ObservableBoolean = ObservableBoolean(true)

//    var aliGaWebUrl : String = "http://ad-global-rule.kajicam.com/ad/splash/rule/api/v2/"
    var aliGaWebUrl : String = "http://149.129.4.199/ad/splash/rule/api/v2/"
//    var aliNotGaWebUrl : String = "http://ad-global-rule2.kajicam.com/ad/splash/rule/api/v2/"
    var aliNotGaWebUrl : String = "http://8.210.192.108.kajicam.com/ad/splash/rule/api/v2/"

    var maxTimes : ObservableInt = ObservableInt(100)

    private val _load = MutableLiveData<Int>()

    val aliCase = _load.switchMap {
        liveData(Dispatchers.IO) {
            schemeRepository.deleteAllAliTest()
            val cache = maxTimes.get()
            while (maxTimes.get() > 0) {
                maxTimes.set(maxTimes.get() -1)
                var url = if (ga.get()) aliGaWebUrl else aliNotGaWebUrl
                emit(schemeRepository.alitestSus(url))
            }
            maxTimes.set(cache)
        }
    }

    val datas = _load.switchMap {
        schemeRepository.queryAli()
    }

    val successNum = datas.map { list ->
        list.filter { it.status == 200 }.count()
    }

    val perTimes = datas.map { list ->
        list.filter { it.status == 200 }.map { it.useTime }.filter { it < 1000 }.average()
    }

    val maxTime = datas.map { list ->
        list.filter { it.status == 200 }.map { it.useTime }.max()
    }

    val minTime = datas.map { list ->
        list.filter { it.status == 200 }.map { it.useTime }.min()
    }

    val lager300 = datas.map { list ->
        list.filter { it.status == 200 }.map { it.useTime }.count { it > 300 }
    }

    val lager500 = datas.map { list ->
        list.filter { it.status == 200 }.map { it.useTime }.count { it > 500 }
    }

    val currentTimes = _load.switchMap {
        schemeRepository.queryALiCount()
    }

    fun load(){
        if (_load.value == null){
            _load.value = 0
        }else{
            _load.value = _load.value!! + 1
        }
    }

}