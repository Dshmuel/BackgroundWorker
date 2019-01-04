package com.dimovsoft.backgroundworker

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class BackgroundWorker: Runnable {
    override fun run() {
        EventBus.getDefault().register(this)

        while (true) {
            if (!Queue.isEmpty()) {
                val work = Queue.dequeue()
                //DO work
                Thread.sleep(2000)
                EventBus.getDefault().post(WorkFinishedEvent(work))
                if (Queue.isEmpty())
                    EventBus.getDefault().post(NoMoreWorkEvent())
            }
            Thread.sleep(100) // Don't kill the CPU

        }
        EventBus.getDefault().unregister(this)

    }

    @Subscribe()
    fun onMessageEvent(event: AddWorkEvent) {
        Queue.enqueue(event.work)
    }

}

class AddWorkEvent(val work: Work)
class WorkFinishedEvent(val work: Work)
class NoMoreWorkEvent