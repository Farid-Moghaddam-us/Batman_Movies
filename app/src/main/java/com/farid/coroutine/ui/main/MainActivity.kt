package com.farid.coroutine.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.farid.coroutine.R
import com.farid.coroutine.databinding.ActivityMainBinding
import com.farid.coroutine.ui.movies_list.MoviesListFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setFullScreen()
        mainBinding.viewModel = mainViewModel
        changeFragment(R.id.fl_main, MoviesListFragment.newInstance(), "openMovieDetail")
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        setFullScreen()
        super.onResume()
    }

    private fun setFullScreen(){
        mainBinding.mainLayout.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    fun showMessage(message : String){
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    fun changeFragment(container: Int, fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .addToBackStack(tag)
            .add(container, fragment)
            .commit()
    }
}