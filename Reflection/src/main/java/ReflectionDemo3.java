import domain.Person;

import java.lang.reflect.Constructor;

/**
 * @author yqs 2020/12/24
 */
public class ReflectionDemo3 {
//    * Constructor:构造方法
//	* 创建对象：
//            * T newInstance(Object... initargs)
//
//		* 如果使用空参数构造方法创建对象，操作可以简化：Class对象的newInstance方法
    public static void main(String[] args) throws Exception {
        Person person = new Person("guison_yiu", 24);
        Class<? extends Person> personClass = person.getClass();
        Constructor<? extends Person> constructor = personClass.getConstructor(String.class, int.class);
        Person person1 = constructor.newInstance("chunchun",23);
        System.out.println(person1);

//        * 如果使用空参数构造方法创建对象，操作可以简化：Class对象的newInstance方法
        Person person2 = personClass.newInstance();
        System.out.println(person2);
    }
}
