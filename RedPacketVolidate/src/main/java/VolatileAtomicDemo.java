import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yqs 2021/1/9
 * volatile关键字的原子性测试demo
 */
class MyData2{

//    int number = 0;
    /**
     * 此时number前面已经加了volatile，但是不保证原子性
     * 运行效果：
     * 原子性测试
     * main	 int 类型最终number值：19996
     */
    volatile int number = 0;

    AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 解决的方式就是：
     *
     * 1.对addPlusPlus()方法加锁，在方法上加synchronized关键字，验证可行。
     * 2.使用java.util.concurrent.AtomicInteger类。
     */


//    synchronized public void addPlusPlus(){
    public void addPlusPlus(){
        number++;
    }

    public void addAtomic(){
        atomicInteger.getAndIncrement();
    }

}

public class VolatileAtomicDemo {
    public static void main(String[] args) {
        atomicDemo();
    }

    private static void atomicDemo(){
        System.out.println("原子性测试");
        MyData2 myData = new MyData2();
        for (int i = 1; i<=20000; i++){
            new Thread(()->{
                myData.addPlusPlus();
                myData.addAtomic();
            },String.valueOf(i)).start();
        }
        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"\t int 类型最终number值："+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t AtomicInteger类型最终number值: "+myData.atomicInteger);
    }

  }


