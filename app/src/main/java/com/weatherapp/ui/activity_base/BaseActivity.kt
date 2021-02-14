package com.weatherapp.ui.activity_base

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sembozdemir.permissionskt.askPermissions
import com.weatherapp.R
import com.weatherapp.data.sharedPrefs.UserSharedPrefs
import com.weatherapp.databinding.ActivityMainBinding
import com.weatherapp.ui.fragments.CurrentFrag
import com.weatherapp.ui.fragments.DateFrag
import com.weatherapp.ui.fragments.SettingsFrag
import com.weatherapp.ui.fragments.WeekReportFrag

class BaseActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var userSharedPrefs: UserSharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        userSharedPrefs = UserSharedPrefs.getSharedPref(this)

        askPermissions(Manifest.permission.ACCESS_COARSE_LOCATION) {
            onDenied {
                Toast.makeText(this@BaseActivity,"app wont work without location permissions",Toast.LENGTH_SHORT).show()
            }
        }

        if(userSharedPrefs.getUsername().isEmpty()){
            binding.linearLayout.visibility = VISIBLE
        } else {

            binding.linearLayout.visibility = GONE
            binding.flFragment.visibility = VISIBLE
            binding.bottomNavigationView.visibility = VISIBLE

            val firstFragment= CurrentFrag.newInstance()
            val secondFragment=DateFrag.newInstance()
            val thirdFragment=WeekReportFrag.newInstance()
            val fourthFragment=SettingsFrag.newInstance()

            setCurrentFragment(firstFragment)

            binding.bottomNavigationView.setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.current->setCurrentFragment(firstFragment)
                    R.id.date->setCurrentFragment(secondFragment)
                    R.id.week_report->setCurrentFragment(thirdFragment)
                    R.id.settings->setCurrentFragment(fourthFragment)

                }
                true
            }

        }

        binding.button.setOnClickListener {
            if (binding.etUsername.text.trim().isNotEmpty()){
                userSharedPrefs.setUsername(binding.etUsername.text.toString())
                recreate()
            } else {
                Toast.makeText(this,"enter name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
    }
}