package tk.zedlabs.creditmanagement

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class User(@PrimaryKey(autoGenerate = true) var id: Int?, var userName: String?, var email: String?, var credit: Int?){
    constructor():this(null,"","",0)
}