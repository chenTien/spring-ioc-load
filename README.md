# spring-ioc-load
模仿Spring加载bean的过程

1.首先使用dom4j解析bean的配置文件,将其封装成一个bean对象,返回一个Map<beanName,bean>

2.创建BeanFactory接口,该类目前只有一个获取bean的方法 Object getBean(String beanName)

3.创建ClassPathXmlApplicationContext类实现BeanFactory接口,覆盖beanFactory的 getBean()方法.

4.初始化一个Map容器用来装载bean实例

5.初始化ClassPathXmlApplicationContext构造器,从配置文件读取获得需要初始化的bean的信息

6.先判断容器中是否存在该Bean,因为CreateBean方法在创建并将一个bean加载到容器的时候,也会创建并加载引用它的bean

7.如不存在,且bean的scope属性为singleton时,才放入容器

8.将初始化的bean放入容器

# 创建Bean的方法createBean(Bean bean)
1.根据反射获取bean的字节码,使用newInstance()获得bean的实例

2.获取bean的属性,根据属性名称获取set方法

3.注入value属性

4.注入其他引用bean,注入前需要先从容器中查找,当前需要注入的bean是否已经创建并放入容器

5.容器中不存在要注入的bean,创建该bean

6.将创建好的单例bean放入容器中

7.反射调用使用invoke方法注入属性
