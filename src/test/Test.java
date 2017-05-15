package test;

import beans.BeanA;
import beans.BeanB;
import beans.BeanC;
import main.BeanFactory;
import main.ClassPathXmlApplicationContext;

/**
 * Created by chen.Tian on 2017/5/15.
 */
public class Test {
    @org.junit.Test
    public void doTest(){
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("/applicationContext.xml");
        BeanA a = (BeanA) beanFactory.getBean("beanA");
        BeanA a2 = (BeanA) beanFactory.getBean("beanA");
        BeanA a3 = (BeanA) beanFactory.getBean("beanA");


        System.out.println(a.getName());

        BeanB b = (BeanB) beanFactory.getBean("beanB");
        BeanB b2 = (BeanB) beanFactory.getBean("beanB");
        BeanB b3 = (BeanB) beanFactory.getBean("beanB");
        System.out.println(b.getBeanA().getName());
        BeanC c = (BeanC) beanFactory.getBean("beanC");
        BeanC c2 = (BeanC) beanFactory.getBean("beanC");
        BeanC c3 = (BeanC) beanFactory.getBean("beanC");
    }
}
