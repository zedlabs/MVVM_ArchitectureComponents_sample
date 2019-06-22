package tk.zedlabs.creditmanagement

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

     lateinit var userViewModel : UserViewModel
     val ADD_USER_REQUEST = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        val userAdapter = UserAdapter()
        recycler_view.adapter = userAdapter

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel.getAllUsers().observe(this, Observer<List<User>> {
            userAdapter.setUsers(this.userViewModel.getAllUsers().value!!)
        })

        button_add_user.setOnClickListener {
            val intent = Intent(this@MainActivity,AddUserActivity::class.java)
            startActivityForResult(intent,ADD_USER_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_USER_REQUEST && resultCode == Activity.RESULT_OK){
             val username = data?.getStringExtra(AddUserActivity.EXTRA_USERNAME)
             val email = data?.getStringExtra(AddUserActivity.EXTRA_EMAIL)
             val credit = data?.getIntExtra(AddUserActivity.EXTRA_CREDIT,1)

            var user = User()
            user.userName = username
            user.email = email
            user.credit = credit

            userViewModel.insert(user = user)
            Toast.makeText(this,"added to database",Toast.LENGTH_SHORT).show()
        }
        else
            Toast.makeText(this,"not added to database",Toast.LENGTH_SHORT).show()
    }
}
