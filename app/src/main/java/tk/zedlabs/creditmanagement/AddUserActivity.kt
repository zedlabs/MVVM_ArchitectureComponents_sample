package tk.zedlabs.creditmanagement

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.NumberPicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_user.*

class AddUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME : String =
            "tk.zedlabs.creditmanagement.EXTRA_USERNAME"
        const val EXTRA_EMAIL : String =
            "tk.zedlabs.creditmanagement.EXTRA_EMAIL"
        const val EXTRA_CREDIT : String =
            "tk.zedlabs.creditmanagement.CREDIT"
    }

    private lateinit var np: NumberPicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        username_edt
        email_edt
        np = findViewById(R.id.credit_numberPicker)
        np.minValue = 1
        np.maxValue = 15

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = "Add User"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mi: MenuInflater = menuInflater
        mi.inflate(R.menu.new_user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
         when (item?.itemId) {
            R.id.save_user -> {
                saveUser()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun saveUser() {
        val username : String = username_edt.text.toString()
        val email = email_edt.text.toString()
        val credit  = np.value

        //Log.d("adduser","$username  $email   $credit")
        if(username.trim().isEmpty() || email.trim().isEmpty()){
            Toast.makeText(this,"Please enter username and email",Toast.LENGTH_SHORT)
            return
        }
        var data = Intent()
        data.putExtra(EXTRA_USERNAME,username)
        data.putExtra(EXTRA_EMAIL,email)
        data.putExtra(EXTRA_CREDIT,credit)

        setResult(Activity.RESULT_OK,data)
        finish()
    }
}
