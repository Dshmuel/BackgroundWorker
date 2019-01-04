package com.dimovsoft.backgroundworker

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        addButton.setOnClickListener {
            EventBus.getDefault().post(AddWorkEvent(Work(counter++)))
        }

        doAsync { BackgroundWorker().run() }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onWorkFinished(event: WorkFinishedEvent) {
        Toast.makeText(this,"Work "+event.work.id+" finished", Toast.LENGTH_SHORT).show()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNoMoreWork(event: NoMoreWorkEvent) {
        Toast.makeText(this,"All works are finished. Queue is empty", Toast.LENGTH_SHORT).show()
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}
