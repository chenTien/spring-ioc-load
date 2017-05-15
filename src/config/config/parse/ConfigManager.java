package config.config.parse;

import config.Bean;
import config.Property;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取和解析xml文件得到bean信息,将其封装为一个bean对象,返回一个Map<beanName,bean>
 * Created by chen.Tian on 2017/5/15.
 */
public class ConfigManager {
    //读取配置文件,并返回读取结果
    public static Map<String, Bean> getConfig(String path){
        Map<String,Bean> map = new HashMap<>();
        //dom4j实现解析xml
        //创建dom解析器
        SAXReader reader = new SAXReader();
        //加载配置文件,doxument对象
        InputStream inputStream = ConfigManager.class.getResourceAsStream(path);
        Document document = null;
        try {
            document = reader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        //取出bean对象
        String xpath = "//bean";
        //遍历Bean
        List<Element> list = document.selectNodes(xpath);
        if (list != null){
            for (Element beanEle : list) {
                //将bean元素的name/class属性封装到Bean对象中
                String name = beanEle.attributeValue("name");
                String className = beanEle.attributeValue("class");
                String scope = beanEle.attributeValue("scope");

                Bean bean = new Bean();
                bean.setName(name);
                bean.setClassName(className);
                if (null != scope){         //不设置时,默认为singleton
                    bean.setScope(scope);
                }
                //获得Bean元素下所有property子元素,将属性封装到Property对象
                List<Element> children = beanEle.elements("property");
                if (children != null){
                    for (Element child : children) {
                        Property prop = new Property();
                        String pName = child.attributeValue("name");
                        String pValue = child.attributeValue("value");
                        String pRef = child.attributeValue("ref");

                        prop.setName(pName);
                        prop.setValue(pValue);
                        prop.setRef(pRef);
                        //将property封装到Bean对象
                        bean.getProperties().add(prop);
                    }
                }
                //将Bean对象封装到Map中,用于返回
                map.put(name,bean);
            }
        }

        return map;
    }
}
