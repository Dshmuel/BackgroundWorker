package com.dimovsoft.backgroundworker

const val QUEUE_LENGTH = 100
object Queue {
    val queue: ArrayList<Work> = ArrayList<Work>()
    var head = 0
    var tail = 0

    fun enqueue(work: Work) {
        if (head+1==tail || head+1== QUEUE_LENGTH && tail==0) return // throw the new work as the queue is full
        head++
        if (head >= QUEUE_LENGTH) head=0
        if (head>=queue.size)
            queue.add(work)
        else
            queue[head] = work
    }

    fun dequeue(): Work {
        val work = queue[tail]
        tail++
        if (tail >= QUEUE_LENGTH) tail=0
        return work
    }

    fun isEmpty() = head==tail
}

data class Work(val id: Int = 0)