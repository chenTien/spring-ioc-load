package Utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Created by chen.Tian on 2017/5/15.
 */
public class BeanUtils {

    public static Method getWriteMethod(Object beanObj, String name) {
        Class<?> className = beanObj.getClass();
        Method writeMethod = null;
        try {
            //得到BeanInfo
            BeanInfo beanInfo = Introspector.getBeanInfo(className);
            //属性描述器
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            //遍历属性描述器
            if (descriptors != null) {
                for (PropertyDescriptor descriptor : descriptors) {
                    //判断当前描述器属性是否是我们需要的属性
                    if (name.equals(descriptor.getName())) {
                        //如果找到返回set方法,否则提醒用户创建set方法
                        writeMethod = descriptor.getWriteMethod();
                        break;
                    }
                }
            }
            if (writeMethod == null) {
                new Throwable("请创建创建该" + name + "属性的set方法");
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return writeMethod;
    }
}
