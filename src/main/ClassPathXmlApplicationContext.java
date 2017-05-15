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
 * ���÷��䴴��beanʵ����������ע�룬��������ʼ����ʱ��
 * �Ƚ�����������Ϊsingleton��bean���ص������У�
 * ��prototype���͵�bean���ᱣ���������У�ÿ�ε���getBeanʱ���ᴴ��һ��ʵ����
 * Created by chen.Tian on 2017/5/15.
 */
public class ClassPathXmlApplicationContext implements BeanFactory {
    //���ϣ����ClassPathXmlApplicationContext��һ�����ͳ�ʼ��spring����(װ��beanʵ��)
    private Map<String, Bean> config;//��ȡ�����ļ�
    //��һ��map����Ϊ����
    private Map<String, Object> context = new HashMap<>();

    @Override
    public Object getBean(String beanName) {
        //����Bean�����ƻ�ȡbeanʵ��
        Object bean = context.get(beanName);
        //���bean��scope����ΪprotoType,��ôcontext�в��������bean,��Ҫ������bean������
        if (null == bean) {
            bean = createBean(config.get(beanName));
        }
        return bean;
    }

    //�����ļ����ع�����
    public ClassPathXmlApplicationContext(String path) {
        //��ȡ�����ļ������Ҫ��ʼ����Bean��Ϣ
        config = ConfigManager.getConfig(path);
        //��������,����ʼ��bean
        if (config != null) {
            for (Entry<String, Bean> en : config.entrySet()) {
                //��ȡ�����е�Bean��Ϣ
                String beanName = en.getKey();
                Bean bean = en.getValue();
                Object existBean = context.get(beanName);
                //���ж��������Ƿ���ڸ�Bean,��ΪCreateBean�����ڴ�������һ��bean���ص�������ʱ��,Ҳ�ᴴ����������������bean
                if (existBean == null && bean.getScope().equals("singleton")) {
                    //�粻����,��bean��scope����Ϊsingletonʱ,�ŷ�������
                    //����bean���ô���bean����
                    Object beanObj = createBean(bean);
                    //����ʼ����bean��������
                    context.put(beanName, beanObj);
                }
            }
        }
    }

    private Object createBean(Bean bean) {
        //��ȡ��Ҫ������bean��class
//        String className = bean.getClass();
        String className = bean.getClassName();
        Class clazz = null; //����ֽ����ļ�
        try {
            clazz = Class.forName(className);   //�����ȡ�ֽ����ļ�
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //ͨ�����䴴������
        Object beanObj = null;
        try {
            beanObj = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // ��ȡbean����,����ע��
        if (bean.getProperties() != null) {
            for (Property prop : bean.getProperties()) {
                //�����Ҫע���Ԫ������
                String name = prop.getName();
                //�����������ƻ�ȡset����
                Method setMethod = BeanUtils.getWriteMethod(beanObj, name);

                Object param = null;
                if (prop.getValue() != null) {
                    //ע��value����
                    //ʵ��ʹ��ʱ��Ҫע������ת��
                    param = prop.getValue();
                }
                if (prop.getRef() != null) {
                    //ע����������bean,ע��ǰ��Ҫ�ȴ������в���,��ǰ��Ҫע���bean�Ƿ��Ѿ���������������
                    Object existBean = context.get(prop.getRef());
                    if (existBean == null) {
                        //�����в�����Ҫע���bean,������bean
                        existBean = createBean(config.get(prop.getRef()));
                        //�������õĵ���bean����������
                        if (config.get(prop.getRef()).getScope().equals("singleton")){
                            context.put(prop.getRef(), existBean);
                        }
                    }
                    param = existBean;
                }
                try {
                    //����set����ע������
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
