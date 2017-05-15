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
            //�õ�BeanInfo
            BeanInfo beanInfo = Introspector.getBeanInfo(className);
            //����������
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            //��������������
            if (descriptors != null) {
                for (PropertyDescriptor descriptor : descriptors) {
                    //�жϵ�ǰ�����������Ƿ���������Ҫ������
                    if (name.equals(descriptor.getName())) {
                        //����ҵ�����set����,���������û�����set����
                        writeMethod = descriptor.getWriteMethod();
                        break;
                    }
                }
            }
            if (writeMethod == null) {
                new Throwable("�봴��������" + name + "���Ե�set����");
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return writeMethod;
    }
}
