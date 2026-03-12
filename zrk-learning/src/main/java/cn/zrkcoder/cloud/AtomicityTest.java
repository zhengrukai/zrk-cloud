package cn.zrkcoder.cloud;

/**
 * Hello world!
 *
 */
public class AtomicityTest
{
    private static int count = 0;

    public static void main( String[] args ) throws InterruptedException {
        int size = 10;
        int times = 10000;
        Thread[] threads = new Thread[size];
        for(int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < times; j++) {
                    count++;
                }
            });
            threads[i].start();
        }

        for(Thread t: threads) {
            t.join();
        }

        System.out.println("实际结果：" + count + " 期望结果：" + times * size);
    }
}
