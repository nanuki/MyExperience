package com.example.myrepository

import android.app.Application
import com.example.myrepository.Repository.FireBaceRepo.FireStoreRepo
import com.example.myrepository.Repository.Repository
import com.example.myrepository.Repository.RetrofitRepo.RetrofitRepository
import com.example.myrepository.Repository.Room.RoomRepository
import com.example.myrepository.ViewModel.ApiActivityViewModel
import com.example.myrepository.ViewModel.FireStoreNoteViewModel
import com.example.myrepository.ViewModel.RoomNoteViewModel
import com.example.myrepository.ViewModel.RoomUserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val module = module {
            single { this@MyApplication }
            single<Repository>{FireStoreRepo(get()) }
            single { RoomRepository(get()) }
            single { RetrofitRepository(get()) }
           // single{ MainViewModel() }
            viewModel { FireStoreNoteViewModel() }
            viewModel { RoomUserViewModel() }
            viewModel { RoomNoteViewModel() }
            viewModel { ApiActivityViewModel() }

        }

        startKoin {
            androidContext(applicationContext)
            modules(module)
        }

    }
}