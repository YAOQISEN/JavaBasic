import domain.Person;

import java.lang.reflect.Field;

/**
 * @author yqs 2020/12/24
 */
public class Reflection4 {
    //    * Field：成员变量
    //	* 操作：
    //            1. 设置值
    //			* void set(Object obj, Object value)
    //		2. 获取值
    //			* get(Object obj)
    //
    //		3. 忽略访问权限修饰符的安全检查
    //			* setAccessible(true):暴力反射
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Person person = new Person();
        Class<? extends Person> personClass = person.getClass();
        Field name = personClass.getDeclaredField("name");
        name.setAccessible(true);
        name.set(person,"chunchun");//IllegalAccessException
        System.out.println(person);

        Person chunchun = new Person("Chunchun", 23);
        String o = (String) name.get(chunchun);
        System.out.println(o);
    }
}
