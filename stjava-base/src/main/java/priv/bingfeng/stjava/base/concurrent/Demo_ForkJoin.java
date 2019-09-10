package priv.bingfeng.stjava.base.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class Demo_ForkJoin {

    static ArrayList<String> urls = new ArrayList<>();
    static {
        for (int i = 0; i < 100; i++) {
            urls.add(i + "");
        }
    }

    static ForkJoinPool firkJoinPool = new ForkJoinPool(3,
            ForkJoinPool.defaultForkJoinWorkerThreadFactory,
            null,
            true);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Job job = new Job(urls, 1, urls.size());
        ForkJoinTask<String> task = firkJoinPool.submit(job);

        String result = task.get();
        System.out.println(result);
    }

    static class Job extends RecursiveTask<String> {

        List<String> urls;
        int start;
        int end;
        public Job(List<String> urls, int start, int end) {
            this.urls = urls;
            this.start = start;
            this.end = end;
        }

        public static String doRequest(String url) {
            return "";
        }

        @Override
        protected String compute() {
            int count = end - start;

            if (count <= 10) {
                StringBuilder result = new StringBuilder();
                for (int i = start; i < end; i++) {
                    String response = doRequest(urls.get(i));
                    result.append(response);
                }
                return result.toString();
            } else {
                int x = (start + end) / 2;

                Job job1 = new Job(urls, start, x);
                job1.fork();

                Job job2 = new Job(urls, x, end);
                job2.fork();

                String result = "";
                result += job1.join();
                result += job2.join();
                return result;
            }
        }
    }

}
