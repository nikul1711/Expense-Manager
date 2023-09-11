package com.example.expense_manager.ui.activities

import android.app.Dialog
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.expense_manager.R
import com.example.expense_manager.utils.InternetConnectionChecker
import com.example.expense_manager.utils.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseActivity :AppCompatActivity(){
    private var mDialog: Dialog? = null
    private var mDoubleBackPress = false

    private lateinit var internetChecker: InternetConnectionChecker
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor =
            ContextCompat.getColor(this, R.color.background)
        checkNetWorkConnection()
    }

    private fun checkNetWorkConnection() {
        internetChecker = InternetConnectionChecker(application)
        internetChecker.observe(this) { isConnected ->
            if (isConnected) {
//                showToast(this, "Internet Connected")
            } else {
                showToast(this, "No Internet connection")
            }
        }
    }

    fun showProgressbar() {
        mDialog = Dialog(this)
        mDialog?.let {
            it.setContentView(R.layout.progressbar_dialog_layout)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            it.show()
        }
    }

    fun hideProgressbar() {
        mDialog?.dismiss()
    }

    fun exitOnDoubleBackPress() {
        if (mDoubleBackPress) {
            return super.onBackPressed()
        }

        mDoubleBackPress = true
        showToast(this, "Please click BACK again to exit")

        lifecycleScope.launch {
            delay(2000)
            mDoubleBackPress = false
        }
    }
}