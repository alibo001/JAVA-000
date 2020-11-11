package java0.conc0303;

import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 */
public class Homework {
    public static void main(String[] args) throws Exception {
        // main1();
        // main2();
        // main3();
        // main4();
        // main5();
        // main6();
        // main7();
        main8();
    }

    // ======== new Thread===========

    // 使用join等待
    public static void main1() throws Exception {
        long start=System.currentTimeMillis();
        final int[] result = {0};
        Thread thread = new Thread(() -> result[0] = sum());
        thread.start();
        thread.join();
        System.out.println("异步计算结果为："+result[0]);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    // 使用CountDownLatch等待
    public static void main2() throws Exception {
        long start=System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(1);
        final int[] result = {0};
        Thread thread = new Thread(() -> {
            result[0] = sum();
            latch.countDown();
        });
        thread.start();
        latch.await();
        System.out.println("异步计算结果为："+result[0]);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    // 使用CyclicBarrier等待
    public static void main3() throws Exception {
        long start=System.currentTimeMillis();
        CyclicBarrier barrier = new CyclicBarrier(2);
        final int[] result = {0};
        Thread thread = new Thread(() -> {
            result[0] = sum();
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        barrier.await();
        System.out.println("异步计算结果为："+result[0]);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    // ======== 线程池+future ===========

    public static void main4() throws Exception {
        long start=System.currentTimeMillis();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(Homework::sum);
        int result = future.get();
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        executor.shutdown();
    }


    public static void main5() throws Exception {
        long start=System.currentTimeMillis();
        FutureTask<Integer> futureTask = new FutureTask<>(Homework::sum);
        new Thread(futureTask).start();
        int result = futureTask.get();
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    public static void main6() throws Exception {
        long start=System.currentTimeMillis();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<Integer> futureTask = new FutureTask<>(Homework::sum);
        executor.submit(futureTask);
        int result = futureTask.get();
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        executor.shutdown();
    }

    public static void main7() throws Exception {
        long start=System.currentTimeMillis();
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(Homework::sum);
        Integer integer = future.get();
        System.out.println("异步计算结果为："+integer);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    public static void main8() throws Exception {
        long start=System.currentTimeMillis();
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(Homework::sum);
        future.thenAccept(i -> System.out.println("异步计算结果为："+i));
        while (!future.isDone()) {
            // 这里使用while等待, 也可以使用上面的各种方法,不再重复
        }
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }




    private static int sum() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return fibo(10);
    }
    private static int fibo(int a) {
        if ( a < 2) 
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
