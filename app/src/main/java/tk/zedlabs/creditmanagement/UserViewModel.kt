package tk.zedlabs.creditmanagement

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: DbRepository
    private val allUsers: LiveData<List<User>>

    init {
        repository = DbRepository(application)
        allUsers = repository.getAllUsers()
    }

    fun insert(user: User) {
        repository.insert(user)
    }

    fun update(user: User) {
        repository.update(user)
    }

    fun delete(user: User) {
        repository.delete(user)
    }

    fun deleteAllUsers() {
        repository.deleteAllUsers()
    }
    fun getAllUsers() : LiveData<List<User>>{
        return allUsers
    }
}