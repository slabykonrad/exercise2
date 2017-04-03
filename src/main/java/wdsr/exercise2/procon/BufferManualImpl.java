package wdsr.exercise2.procon;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Task: implement Buffer interface without using any *Queue classes from java.util.concurrent package.
 * Any combination of "synchronized", *Lock, *Semaphore, *Condition, *Barrier, *Latch is allowed.
 */
public class BufferManualImpl implements Buffer {
	private Queue<Order> queue = new LinkedList<Order>();
	
	public synchronized void submitOrder(Order order) throws InterruptedException {
		queue.offer(order);
	}
	
	public synchronized Order consumeNextOrder() throws InterruptedException {
		return queue.remove();
	}
}
