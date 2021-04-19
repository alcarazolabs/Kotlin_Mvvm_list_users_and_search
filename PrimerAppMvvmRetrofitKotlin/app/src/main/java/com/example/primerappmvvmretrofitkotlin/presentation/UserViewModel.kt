package com.example.primerappmvvmretrofitkotlin.presentation

import android.util.Log
import androidx.lifecycle.*
import com.example.demo.core.Resource
import com.example.primerappmvvmretrofitkotlin.data.model.UserList
import com.example.primerappmvvmretrofitkotlin.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel (private val repo: UserRepository): ViewModel() {

    fun fetchUsers() = liveData(Dispatchers.IO) {
        //Emitir primer estado 'carga'
        emit(Resource.Loading())
        try {
            //Emitir segundo estado 'Exitoso'
            emit(Resource.Success(repo.getUsers()))
        } catch (e: Exception) {
            //Tercer estado Error/Excepcion
            emit(Resource.Failure(e))
        }
    }

    private lateinit var searchUsersResponse: UserList
    private val _usersSearchLiveData = MutableLiveData<Resource<UserList?>>()
    val usersSearchLiveData: MutableLiveData<Resource<UserList?>>//A esta nos vamos a suscribir u observar
        get() = _usersSearchLiveData

    private val _userNameLiveData = MutableLiveData<String>()
    val userNameLiveData: LiveData<String>
       get() = _userNameLiveData

    fun searchUser(query: String) {
        Log.d("query",query)
        _userNameLiveData.value = query
        getUsers()
    }


    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_userNameLiveData.value != null && _userNameLiveData.value!!.isNotEmpty()) {
                _usersSearchLiveData.postValue(Resource.Loading())
                try {
                    searchUsersResponse = repo.searchUsers(_userNameLiveData.value!!)
                    withContext(Dispatchers.Main) {
                        _usersSearchLiveData.postValue(Resource.Success(searchUsersResponse))
                    }
                } catch (e: Exception) {
                    _usersSearchLiveData.postValue(Resource.Failure(e))
                }
            }else{
               // launch {  } equivalente a Dispatchers.Main
                withContext(Dispatchers.Main) {
                    _usersSearchLiveData.postValue(Resource.Failure(Exception("Ingrese un nombre")))
                }
            }
        }
    }

}
class UserViewModelFactory(private val repo: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserRepository::class.java).newInstance(repo)
    }
}