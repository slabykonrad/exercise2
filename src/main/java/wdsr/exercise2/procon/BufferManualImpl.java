package wdsr.exercise2.procon;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Task: implement Buffer interface without using any *Queue classes from java.util.concurrent package.
 * Any combination of "synchronized", *Lock, *Semaphore, *Condition, *Barrier, *Latch is allowed.
 */
public class BufferManualImpl implements Buffer {
	private Queue<Order> queue = new LinkedList<Order>();
	private final Lock lock = new ReentrantLock();
	private final Condition bufferNotFull = lock.newCondition();
	private final Condition bufferNotEmpty = lock.newCondition();
	private final int MAXCAPACITY = 100000;

	public void submitOrder(Order order) throws InterruptedException {
		lock.lock();

		try{
			while(queue.size() == MAXCAPACITY){
				bufferNotFull.await();
			}

			if(order != null){
				queue.offer(order);
				bufferNotEmpty.signalAll();
			}
		}
		finally{
			lock.unlock();
		}
	}
	
	public Order consumeNextOrder() throws InterruptedException {
		lock.lock();

		try{
			while(queue.isEmpty()){
				bufferNotEmpty.await();
			}

			bufferNotFull.signal();
			return queue.remove();
		}
		finally{
			lock.unlock();
		}
	}
}
