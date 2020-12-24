import domain.Person;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionDemo2 {
//    * Class对象功能：
//            * 获取功能：
//            1. 获取成员变量们
//			* Field[] getFields() ：获取所有public修饰的成员变量
//			* Field getField(String name)   获取指定名称的 public修饰的成员变量
//
//			* Field[] getDeclaredFields()  获取所有的成员变量，不考虑修饰符
//			* Field getDeclaredField(String name)
//		2. 获取构造方法们
//			* Constructor<?>[] getConstructors()
//			* Constructor<T> getConstructor(类<?>... parameterTypes)
//
//			* Constructor<T> getDeclaredConstructor(类<?>... parameterTypes)
//			* Constructor<?>[] getDeclaredConstructors()
//		3. 获取成员方法们：
//            * Method[] getMethods()
//			* Method getMethod(String name, 类<?>... parameterTypes)
//
//			* Method[] getDeclaredMethods()
//			* Method getDeclaredMethod(String name, 类<?>... parameterTypes)
//
//		4. 获取全类名
//			* String getName()
    public static void main(String[] args) throws Exception {

        Class<? extends Person> aClass = new Person("guison_yiu", 24).getClass();

        //1.
//        Field name = aClass.getField("name");
//        System.out.println(name);

        Field[] fields = aClass.getFields();
        for (Field field: fields) {
            System.out.println(field);
        }

        Field name1 = aClass.getDeclaredField("name");
        System.out.println(name1);

        Field[] fields1 = aClass.getDeclaredFields();
        for (Field field1: fields1) {
            System.out.println(field1);
        }

        //2.
//        Constructor<? extends Person> constructor = aClass.getConstructor();
        Constructor<? extends Person> constructor = aClass.getConstructor(String.class,int.class);
        System.out.println(constructor);

        Constructor<?>[] constructors = aClass.getConstructors();
        for (Constructor constructor1: constructors) {
            System.out.println(constructor1);
        }

        Constructor<? extends Person> constructor1 = aClass.getDeclaredConstructor(String.class, int.class);
        System.out.println(constructor1);

        Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
        for (Constructor constructor2: declaredConstructors) {
            System.out.println(constructor2);
        }

//        3. 获取成员方法们：
//            * Method[] getMethods()
//			* Method getMethod(String name, 类<?>... parameterTypes)
//
//			* Method[] getDeclaredMethods()
//			* Method getDeclaredMethod(String name, 类<?>... parameterTypes)
        System.out.println("---------------------------------");

        Method[] methods = aClass.getMethods();
        for (Method method: methods){
            System.out.println(method);
        }

        System.out.println("---------------------------------");

        Method method = aClass.getMethod("study");
        System.out.println(method);

        System.out.println("---------------------------------");
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method declaredMethod:declaredMethods){
            System.out.println(declaredMethod);
        }

        System.out.println("---------------------------------");
        Method declaredMethod = aClass.getDeclaredMethod("study");
        System.out.println(declaredMethod);

        System.out.println("---------------------------------");

        //4.
        String name = aClass.getName();
        System.out.println(name);
        String simpleName = aClass.getSimpleName();
        System.out.println(simpleName);

    }
}
