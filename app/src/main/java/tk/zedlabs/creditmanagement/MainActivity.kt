package tk.zedlabs.creditmanagement

import tk.zedlabs.creditmanagement.*
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

       val callbackIth = object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                userViewModel.delete(userAdapter.getUserAt(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "User deleted", Toast.LENGTH_SHORT).show()
            }

        }
        val ith = ItemTouchHelper(callbackIth)
        ith.attachToRecyclerView(recycler_view)

        button_add_user.setOnClickListener {
            val intent = Intent(this@MainActivity, AddUserActivity::class.java)
            startActivityForResult(intent,ADD_USER_REQUEST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_activity_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId  == R.id.delete_all_users)
        { userViewModel.deleteAllUsers()
          Toast.makeText(this,"All users have been removed",Toast.LENGTH_LONG).show()
            return true}
        else
        return super.onOptionsItemSelected(item)
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
