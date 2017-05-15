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
 * ��ȡ�ͽ���xml�ļ��õ�bean��Ϣ,�����װΪһ��bean����,����һ��Map<beanName,bean>
 * Created by chen.Tian on 2017/5/15.
 */
public class ConfigManager {
    //��ȡ�����ļ�,�����ض�ȡ���
    public static Map<String, Bean> getConfig(String path){
        Map<String,Bean> map = new HashMap<>();
        //dom4jʵ�ֽ���xml
        //����dom������
        SAXReader reader = new SAXReader();
        //���������ļ�,doxument����
        InputStream inputStream = ConfigManager.class.getResourceAsStream(path);
        Document document = null;
        try {
            document = reader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        //ȡ��bean����
        String xpath = "//bean";
        //����Bean
        List<Element> list = document.selectNodes(xpath);
        if (list != null){
            for (Element beanEle : list) {
                //��beanԪ�ص�name/class���Է�װ��Bean������
                String name = beanEle.attributeValue("name");
                String className = beanEle.attributeValue("class");
                String scope = beanEle.attributeValue("scope");

                Bean bean = new Bean();
                bean.setName(name);
                bean.setClassName(className);
                if (null != scope){         //������ʱ,Ĭ��Ϊsingleton
                    bean.setScope(scope);
                }
                //���BeanԪ��������property��Ԫ��,�����Է�װ��Property����
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
                        //��property��װ��Bean����
                        bean.getProperties().add(prop);
                    }
                }
                //��Bean�����װ��Map��,���ڷ���
                map.put(name,bean);
            }
        }

        return map;
    }
}
