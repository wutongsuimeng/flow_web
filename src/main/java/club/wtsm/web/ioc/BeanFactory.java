package club.wtsm.web.ioc;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {
    /**
     * 完成初始化的bean
     */
    private final Map<String, Object> beans = new ConcurrentHashMap<>();
    /**
     * 仅完成实例化的bean，用来防止重复加载bean
     */
    private final Map<String, Object> earlyBeans = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        try {
            Object bean;
            if ((bean = earlyBeans.get(clazz.getName())) != null || (bean = beans.get(clazz.getName())) != null) {
                return (T) bean;
            }
            //1. 解析类定义
//        BeanDefinition bd=parseBean(clazz);
            //2. 初始化类
            bean = clazz.getDeclaredConstructor().newInstance();
            earlyBeans.put(clazz.getName(), bean);
            //3. 初始化类的依赖
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                //需要注入的
                if (field.getAnnotation(Resource.class) != null) {
                    Class<?> fieldClazz = field.getType();
                    Object fieldObject = getBean(fieldClazz);

                    //4. 将类的依赖注入到属性
                    if (!field.canAccess(bean)) {
                        field.setAccessible(true);
                    }
                    field.set(bean, fieldObject);
                }
            }
            beans.put(clazz.getName(), bean);
            earlyBeans.remove(clazz.getName());
            System.out.println(bean);
            return (T) bean;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
