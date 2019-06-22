package tk.zedlabs.creditmanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserHolder>() {

    private var users : List<User> = ArrayList()

    class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val usernameTextView : TextView
         val emailTextView : TextView
         val creditTextView : TextView

        init {
            usernameTextView = itemView.findViewById(R.id.username)
            emailTextView = itemView.findViewById(R.id.email)
            creditTextView = itemView.findViewById(R.id.credit)
        }
    }

    fun setUsers(users :List<User>){
        this.users = users
        notifyDataSetChanged()
    }
    fun getUserAt(position: Int) : User{
        return users[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)
        return UserHolder(itemView)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val currentUser : User = users.get(position)
        holder.usernameTextView.text = currentUser.userName
        holder.emailTextView.text = currentUser.email
        holder.creditTextView.text = currentUser.credit.toString()


    }

}