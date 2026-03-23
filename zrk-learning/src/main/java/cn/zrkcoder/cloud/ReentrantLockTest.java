package cn.zrkcoder.cloud;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zrk on 2026/3/12
 */
public class ReentrantLockTest {

    private static final ReentrantLock lock = new ReentrantLock();
    // 空队列条件：消费者等待
    private static final Condition emptyCondition = lock.newCondition();
    // 满队列条件：生产者等待
    private static final Condition fullCondition = lock.newCondition();
    private static final Queue<String> queue = new LinkedList<>();
    private static final int CAPACITY = 5; // 队列容量

    // 生产者
    private static class Producer implements Runnable {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    // 队列满则等待
                    while (queue.size() == CAPACITY) {
                        System.out.println("队列满，生产者等待");
                        fullCondition.await(); // 释放锁，等待被唤醒
                    }
                    // 生产数据
                    String data = "数据-" + System.currentTimeMillis();
                    queue.offer(data);
                    System.out.println("生产者生产：" + data + "，队列大小：" + queue.size());
                    // 唤醒消费者（精准）
                    emptyCondition.signal();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    // 消费者
    private static class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    // 队列空则等待
                    while (queue.isEmpty()) {
                        System.out.println("队列空，消费者等待");
                        emptyCondition.await();
                    }
                    // 消费数据
                    String data = queue.poll();
                    System.out.println("消费者消费：" + data + "，队列大小：" + queue.size());
                    // 唤醒生产者（精准）
                    fullCondition.signal();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();
    }

}
