package tk.zedlabs.creditmanagement

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(User::class),version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        UserDatabase::class.java, "user.db")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()

                }
            }
            return INSTANCE
        }
         private val roomCallback = object :RoomDatabase.Callback(){
             override fun onCreate(db: SupportSQLiteDatabase) {
                 super.onCreate(db)
                 populateDbAsyncTask(INSTANCE).execute()
             }
         }

        private class populateDbAsyncTask : AsyncTask<Void, Void, Void>{
            var userDao : UserDao
            constructor(userDatabase: UserDatabase?){
                userDao = userDatabase!!.userDao()
            }
            override fun doInBackground(vararg params: Void?): Void? {
                userDao.insert(User(null,"zedd","zees@zed.com",399))
                userDao.insert(User(null,"obaid","zeesasa@zed.com",299))
                userDao.insert(User(null,"zohaib","zeetard@zed.com",199))
                return null
            }

        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}