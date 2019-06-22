package tk.zedlabs.creditmanagement

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun insert(user : User)

    @Update
    fun update(user : User)

    @Delete
    fun delete(user: User)

    //called from the action bar
    @Query("DELETE FROM USER_DATA")
    fun deleteAllUsers()

    @Query("SELECT * FROM USER_DATA order by credit")
    fun getAllUsers() : LiveData<List<User>>


}