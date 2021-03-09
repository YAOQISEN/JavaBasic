import java.io.*;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yqs 2021/3/9
 */
public class OddEventTest {
    private static final int count = 10000;
    private static final int threadGroupCount = 5;
    private static final String inputFile = "testInput.txt";

    public static void generateTestFile() throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(inputFile), true);
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            printWriter.write(Math.abs(random.nextInt())%count+",");
        }
        printWriter.flush();
        printWriter.close();
    }

    public static void main(String[] args) {
        try {
            generateTestFile();
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String str = reader.readLine();
            reader.close();
            String[] strs = str.split(",");
            int index = 0;
            int countForEachFile = count / threadGroupCount;
            for (int i = 0; i < threadGroupCount; i++) {
                int records[] = new int[countForEachFile];
                for (int j = 0; j < countForEachFile; j++) {
                    records[j] = Integer.parseInt(strs[index]);
                    index++;
                }
                PrintGroup printGroup = new PrintGroup(records, i);
                printGroup.startPrint();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class PrintGroup{
    //这个线程组输出数字的个数
    private static volatile int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition oddLock = lock.newCondition();
    private Condition eventLock = lock.newCondition();
    //这个线程需要输出的数字个数
    private int records[];
    private PrintWriter writer;
    private volatile int oddIndex = 0;
    private volatile int eventIndex = 0;
    //奇数输出线程
    private OddPrintThread oddPrintThread;
    //偶数输出线程
    private EventPrintThread eventPrintThread;
    private volatile boolean first = true;
    private int[] result = new int[2000];
    private int index = 0;
    public PrintGroup(int[] records,int id) throws IOException {
        this.records = records;
        this.writer = new PrintWriter(new FileWriter(new File("output"+id+".txt")),true);
    }
    public void startPrint(){
        oddPrintThread = new OddPrintThread();
        oddPrintThread.start();
        eventPrintThread = new EventPrintThread();
        eventPrintThread.start();
    }
    private class OddPrintThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    //第一次运行，先等偶数线程先输出完
                    if (first) {
                        first = false;
                        eventLock.await();
                    }
                    //输出10个奇数，之后等待偶数线程运行
                    for (int i = 0; i < 10; ) {
                        //数组中的偶数和奇数都打印完
                        if (eventIndex >= records.length && oddIndex >= records.length) {
                            writer.flush();
                            writer.close();
                            return;
                        }
                        //如果奇数都打印完了，则不打印奇数，让打印偶数的线程有机会运行
                        if (oddIndex >= records.length) {
                            break;
                        }
                        //打印奇数都文件中，并计数
                        if (records[oddIndex] % 2 == 1) {
                            i++;
                            writer.print(records[oddIndex] + " ");
                            result[index++] = records[oddIndex];
                            writer.flush();
                            //todo 增加同步的count
                            addCount();

                        }
                        oddIndex++;
                    }
                    //通知偶数线程开始运行
                    oddLock.signal();
                    //等待偶数线程结束
                    eventLock.await();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    oddLock.signal();

                }
            }
        }
    }

        private class EventPrintThread extends Thread{
            @Override
            public void run(){
                while(true){
                    try {
                        while (first){
                            sleep(1);
                        }
                        lock.lock();
                        //输出10个奇数，之后等待偶数线程运行
                        for (int i = 0; i < 10;) {
                            //数组中的偶数和奇数都打印完
                            if (eventIndex>=records.length && oddIndex>=records.length){
                                writer.flush();
                                writer.close();
                                return;
                            }
                            //如果奇数都打印完了，则不打印奇数，让打印偶数的线程有机会运行
                            if (eventIndex>=records.length){
                                break;
                            }
                            //打印奇数都文件中，并计数
                            if (records[eventIndex]%2==0){
                                i++;
                                writer.print(records[eventIndex]+" ");
                                result[index++] = records[eventIndex];
                                writer.flush();
                                //todo 增加同步的count
                                addCount();

                            }
                            eventIndex++;
                        }
                        //通知偶数线程开始运行
                        eventLock.signal();
                        //等待偶数线程结束
                        oddLock.await();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        eventLock.signal();

                    }
                }
            }
    }

    private synchronized static void addCount(){
        count++;
        if (count%1000==0){
            System.out.println("已经完成: "+count);
        }
        if (count == 10000){
            System.out.println("Done!");
        }
    }


}

