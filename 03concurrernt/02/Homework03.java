package java0.conc0303;

import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
public class Homework03 {
    
    public static void main(String[] args) {
        long start=System.currentTimeMillis();

        // 这里可将方法改为method1~10，观察不同方法的计算结果
        try{
            int result =  method1();
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为："+result);
         
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        }catch(Exception e){
            e.printStackTrace();
        }
        // 然后退出main线程
    }
    
    /**
     * 方法1：使用FutureTask配合Callable
     * @return
     */
    private static int method1() throws Exception{
        FutureTask<Integer> futureTask = new FutureTask(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int result = sum();
                return result;
            }
        });
        futureTask.run();
        Integer result = null;
        result = futureTask.get();

        return result.intValue();
    }

    /**
     * 方法2：使用线程池配合Callable
     * @return
     * @throws Exception
     */
    private static int method2() throws Exception{
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int result = sum();
                return result;
            }
        });
        Integer result = null;
        while(!future.isDone()) {
            result = future.get();
        }
         
        return result.intValue();
    }

    /**
     * 方法3：关闭线程池后读取
     */
    static int result3 = 0;
    private static int method3() throws Exception{
        ExecutorService executor = Executors.newSingleThreadExecutor();
        int result;
        executor.execute(new Runnable() {
            @Override
            public void run(){
                result3 = sum();
            }
        });
        executor.shutdown();
        while(!executor.isTerminated()) {
        }
        return result3;
    }

    /**
     * 方法4：使用thread，并使用join阻塞
     */
    static int result4 = 0;
    private static int method4() throws Exception{
        Thread t = new Thread() {
            @Override
            public void run(){
                result4 = sum();
            }
        };
        t.start();
        t.join();
        return result4;
    }

    /**
     * 方法5：使用CountDownLatch配合线程池
     */
    static int result5 = 0;
    private static int method5() throws Exception{
        final CountDownLatch latch = new CountDownLatch(1);
        ExecutorService es1 = Executors.newSingleThreadExecutor();
        es1.execute(new Runnable() {
            @Override
            public void run(){
                result5 = sum();
                latch.countDown();
            }
        });
        latch.await();
        return result5;
    }

    /**
     * 方法6：使用CyclicBarrier配合线程池
     */
    static int result6 = 0;
    private static int method6() throws Exception{
        final CyclicBarrier barrier = new CyclicBarrier(2);
        ExecutorService es1 = Executors.newSingleThreadExecutor();
        es1.execute(new Runnable() {
            @Override
            public void run() {
                result6 = sum();
                try {
                    barrier.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            });
        try {
            barrier.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result6;
    }

    /**
     * 方法7：使用Exchanger
     * @return
     * @throws Exception
     */
    private static int method7() throws Exception{
        final Exchanger<Integer> exchanger = new Exchanger<Integer>();
        ExecutorService es1 = Executors.newSingleThreadExecutor();
        es1.execute(new Runnable() {
            @Override
            public void run() {
                int result7 = sum();
                try {
                    exchanger.exchange(result7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        int result7 = exchanger.exchange(0);
        return result7;
    }

    /**
     * 方法8：使用ForkJoinPool
     * @return
     * @throws Exception
     */
    static class SumTask extends RecursiveTask<Integer> {
        private int end;
        public SumTask(int end){
            this.end = end;
        }
        @Override
        protected Integer compute() {
            int sum = 0;
            if ( end < 2)
                return 1;
            SumTask task1 = new SumTask(end-1);
            SumTask task2 = new SumTask(end-2);
            task1.fork();
            task2.fork();
            sum = task1.join() + task2.join();
            return sum;
        }
    }
    private static int method8() throws Exception{
        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(36);
        Future<Integer> result8 = pool.submit(task);
        return result8.get();
    }

    /**
     * 方法9：使用CompletionService
     */
    private static int method9() throws Exception{
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletionService completionService = new ExecutorCompletionService(executor);
        completionService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int result = sum();
                return result;
            }
        });
        Future<Integer> pollFuture = completionService.take();
        return pollFuture.get();
    }

    /**
     * 方法10：使用CompletionService
     */
    private static int method10() throws Exception{
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(()->{
            int result = sum();
            return result;
        });
        return cf.get();
    }

    private static int sum() {
        return fibo(36);
    }
    
    private static int fibo(int a) {
        if ( a < 2) 
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}