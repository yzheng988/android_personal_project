package com.myProj.sample.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.myProj.sample.model.DogBreed
import com.myProj.sample.db.DogDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): BaseViewModel(application) {
    val dogLiveData = MutableLiveData<DogBreed>()

    fun fetch(uuid: Int) {
        launch {
            val dog = DogDatabase(getApplication()).dogDao().getDog(uuid)
            dogLiveData.value = dog
        }
    }
}