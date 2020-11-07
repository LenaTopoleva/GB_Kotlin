package com.lenakurasheva.notes.ui.splash

import android.content.Context
import android.content.Intent
import com.lenakurasheva.notes.ui.base.BaseActivity
import com.lenakurasheva.notes.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity: BaseActivity<Boolean>() {

    companion object{
        fun start(context: Context) = Intent(context, SplashActivity::class.java).apply {
            context.startActivity(this)
        }
    }

    override val viewModel: SplashViewModel by viewModel()
    override val layoutRes = null

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
    }

    override fun renderData(data: Boolean) {
        data.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        MainActivity.start(this)
        finish()
    }
}
