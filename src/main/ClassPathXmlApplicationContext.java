package main;

import Utils.BeanUtils;
import config.Bean;
import config.Property;
import config.config.parse.ConfigManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 利用反射创建bean实例并将属性注入，在容器初始化的时候，
 * 先将所有作用域为singleton的bean加载到容器中，
 * 而prototype类型的bean不会保存在容器中，每次调用getBean时都会创建一个实例。
 * Created by chen.Tian on 2017/5/15.
 */
public class ClassPathXmlApplicationContext implements BeanFactory {
    //如果希望在ClassPathXmlApplicationContext类一创建就初始化spring容器(装载bean实例)
    private Map<String, Bean> config;//获取配置文件
    //用一个map来作为容器
    private Map<String, Object> context = new HashMap<>();

    @Override
    public Object getBean(String beanName) {
        //根据Bean的名称获取bean实例
        Object bean = context.get(beanName);
        //如果bean的scope属性为protoType,那么context中不会包含该bean,需要创建该bean并返回
        if (null == bean) {
            bean = createBean(config.get(beanName));
        }
        return bean;
    }

    //配置文件加载构造器
    public ClassPathXmlApplicationContext(String path) {
        //读取配置文件获得需要初始化的Bean信息
        config = ConfigManager.getConfig(path);
        //遍历配置,并初始化bean
        if (config != null) {
            for (Entry<String, Bean> en : config.entrySet()) {
                //获取配置中的Bean信息
                String beanName = en.getKey();
                Bean bean = en.getValue();
                Object existBean = context.get(beanName);
                //先判断容器中是否存在该Bean,因为CreateBean方法在创建并将一个bean加载到容器的时候,也会创建并加载引用它的bean
                if (existBean == null && bean.getScope().equals("singleton")) {
                    //如不存在,且bean的scope属性为singleton时,才放入容器
                    //根据bean配置创建bean对象
                    Object beanObj = createBean(bean);
                    //将初始化的bean放入容器
                    context.put(beanName, beanObj);
                }
            }
        }
    }

    private Object createBean(Bean bean) {
        //获取需要创建的bean的class
//        String className = bean.getClass();
        String className = bean.getClassName();
        Class clazz = null; //类的字节码文件
        try {
            clazz = Class.forName(className);   //反射获取字节码文件
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //通过反射创建对象
        Object beanObj = null;
        try {
            beanObj = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 获取bean属性,将其注入
        if (bean.getProperties() != null) {
            for (Property prop : bean.getProperties()) {
                //获得需要注入的元素名称
                String name = prop.getName();
                //根据属性名称获取set方法
                Method setMethod = BeanUtils.getWriteMethod(beanObj, name);

                Object param = null;
                if (prop.getValue() != null) {
                    //注入value属性
                    //实际使用时需要注意类型转换
                    param = prop.getValue();
                }
                if (prop.getRef() != null) {
                    //注入其他引用bean,注入前需要先从容器中查找,当前需要注入的bean是否已经创建并放入容器
                    Object existBean = context.get(prop.getRef());
                    if (existBean == null) {
                        //容器中不存在要注入的bean,创建该bean
                        existBean = createBean(config.get(prop.getRef()));
                        //将创建好的单例bean放入容器中
                        if (config.get(prop.getRef()).getScope().equals("singleton")){
                            context.put(prop.getRef(), existBean);
                        }
                    }
                    param = existBean;
                }
                try {
                    //调用set方法注入属性
                    setMethod.invoke(beanObj, param);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return beanObj;
    }

}
