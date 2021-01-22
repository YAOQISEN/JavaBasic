import java.util.concurrent.TimeUnit;

/**
 * @author yqs 2021/1/9
 */
class MyData{

    /**
     * 虽然一个线程把number修改成了60，但是main线程持有的仍然是最开始的0，所以一直循环，程序不会结束。
     */
//    int number = 0;

    /**
     * 如果对number添加了volatile修饰，，运行结果是：
     * 可见性测试
     * ThreadA	 执行
     * ThreadA	 更新number值: 60
     * main	 main获取number值: 60
     *
     * 可见某个线程对number的修改，会立刻反映到主内存上。
     *
     */
    volatile int number = 0;

    public void setTo50(){
        this.number=50;
    }
}

public class VolatileDemo {
    public static void main(String[] args) {
        volatileVisibilityDemo();
    }

    private static void volatileVisibilityDemo(){
        System.out.println("可见性测试");
        final MyData myData = new MyData(); //资源类
        //启动一个线程操作共享数据
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() +"\t 执行");
            try {
                TimeUnit.SECONDS.sleep(3);
                myData.setTo50();
                System.out.println(Thread.currentThread().getName() + "\t更新number值："+ myData.number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"ThreadA").start();
        while (myData.number==0){
            //main线程持有共享数据的拷贝，一直为0
//            System.out.println(Thread.currentThread().getName() + "\t main获取number值: " + myData.number);
        }
        System.out.println(Thread.currentThread().getName() + "\t main获取number值: " + myData.number);
    }
}
