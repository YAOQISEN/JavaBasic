import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author yqs 2020/12/24
 */
public class ReflectionFrameDemo {
//    * 案例：
//            * 需求：写一个"框架"，不能改变该类的任何代码的前提下，可以帮我们创建任意类的对象，并且执行其中任意方法
//		* 实现：
//            1. 配置文件
//			2. 反射
//		* 步骤：
//            1. 将需要创建的对象的全类名和需要执行的方法定义在配置文件中
//			2. 在程序中加载读取配置文件
//			3. 使用反射技术来加载类文件进内存
//			4. 创建对象
//			5. 执行方法
    public static void main(String[] args) throws Exception {

        //1.加载配置文件
        //1.1创建Properties对象
        Properties properties = new Properties();
        //1.2加载配置文件，转换为一个集合
        //1.2.1获取class目录下的配置文件
        ClassLoader classLoader = ReflectionFrameDemo.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("pro.properties");
        properties.load(is);


        String aClass = properties.getProperty("class");
        String method = properties.getProperty("method");

        Class<?> aClass1 = Class.forName(aClass);
        Object instance = aClass1.newInstance();
        Method declaredMethod = aClass1.getDeclaredMethod(method);
        declaredMethod.invoke(instance);


    }
}
