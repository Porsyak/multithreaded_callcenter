import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallCenter {
    private final ConcurrentLinkedDeque<String> callQueue = new ConcurrentLinkedDeque<>();

    Runnable atc = () -> {
        int count = 0;
        while (count != 10) {
            callQueue.add("Звонок" + count + " создал " + Thread.currentThread().getName());
            System.out.println(callQueue);
            count++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    };


    Runnable callTask = () -> {
        int count = 0;
        while (count != 10) {
            if (callQueue.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            callQueue.poll();
            System.out.println("Звонок обработан " + Thread.currentThread().getName());
            count++;
        }
    };


    public static void main(String[] args) throws InterruptedException {
        CallCenter callCenter = new CallCenter();
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        executorService.submit(callCenter.atc);
        executorService.submit(callCenter.callTask);
        executorService.shutdown();



    }


}

