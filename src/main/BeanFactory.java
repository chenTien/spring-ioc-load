package main;

/**
 * 利用反射将bean的实例注入,在容器初始化的时候,先将所有作用域为singleton的bean加载到容器中
 * 而protoType类型的bean不会保存在容器中,每次调用getBean的时候都会创建一个bean的实例
 * Created by chen.Tian on 2017/5/15.
 */
public interface BeanFactory {
    //根据bean的name获取对象
    Object getBean(String beanName);
//    void initMethod();
//    void destroyMethod();
}
