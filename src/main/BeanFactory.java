package main;

/**
 * ���÷��佫bean��ʵ��ע��,��������ʼ����ʱ��,�Ƚ�����������Ϊsingleton��bean���ص�������
 * ��protoType���͵�bean���ᱣ����������,ÿ�ε���getBean��ʱ�򶼻ᴴ��һ��bean��ʵ��
 * Created by chen.Tian on 2017/5/15.
 */
public interface BeanFactory {
    //����bean��name��ȡ����
    Object getBean(String beanName);
//    void initMethod();
//    void destroyMethod();
}
