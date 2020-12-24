import domain.Person;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yqs 2020/12/24
 */
public class ReflectionDemo5 {
    //    * Method：方法对象
    //	* 执行方法：
    //            * Object invoke(Object obj, Object... args)
    //
    //	* 获取方法名称：
    //            * String getName:获取方法名
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Person person = new Person();
        Class<? extends Person> personClass = person.getClass();
        Method method = personClass.getMethod("study");
        Person chunchun = new Person("chunchun", 23);
        method.invoke(chunchun);
        Method method2 = personClass.getDeclaredMethod("study",String.class);
        method2.setAccessible(true);
        method2.invoke(chunchun,"美伢一样的优秀女子");
    }
}
