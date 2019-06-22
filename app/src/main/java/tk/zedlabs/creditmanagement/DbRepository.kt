package tk.zedlabs.creditmanagement

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

//abstraction layer for MVVM architecture
//serves as an API

class DbRepository {

     private var userDao: UserDao
     private var allUsers : LiveData<List<User>>

    constructor(application : Application){
        val userDatabase : UserDatabase = UserDatabase.getInstance(application)!!
        userDao = userDatabase.userDao()
        allUsers = userDao.getAllUsers()
    }

    fun insert (user : User){

        InsertUserAsyncTask(userDao).execute(user)
    }
    fun update (user : User){
        UpdateUserAsyncTask(userDao).execute(user)
    }
    fun delete (user : User){
        DeleteUserAsyncTask(userDao).execute(user)
    }
    fun deleteAllUsers(){
        DeleteAllUsersAsyncTask(userDao).execute()
    }
    fun getAllUsers() : LiveData<List<User>>{
        return  allUsers
    }

    private class InsertUserAsyncTask : AsyncTask<User, Void, Void>{

        var userDao : UserDao
        constructor(userDao: UserDao){
            this.userDao = userDao
        }

        override fun doInBackground(vararg users: User): Void? {
            userDao.insert(users[0])
            return null
        }
    }

    private class UpdateUserAsyncTask : AsyncTask<User, Void, Void>{

        var userDao : UserDao
        constructor(userDao: UserDao){
            this.userDao = userDao
        }

        override fun doInBackground(vararg users: User): Void? {
            userDao.update(users[0])
            return null
        }
    }

    private class DeleteUserAsyncTask : AsyncTask<User, Void, Void>{

        var userDao : UserDao
        constructor(userDao: UserDao){
            this.userDao = userDao
        }

        override fun doInBackground(vararg users: User): Void? {
            userDao.delete(users[0])
            return null
        }
    }

    private class DeleteAllUsersAsyncTask : AsyncTask<Void, Void, Void>{

        var userDao : UserDao
        constructor(userDao: UserDao){
            this.userDao = userDao
        }

        override fun doInBackground(vararg params: Void?): Void? {
            userDao.deleteAllUsers()
            return null
        }
    }
}