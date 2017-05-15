package config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen.Tian on 2017/5/15.
 */
public class Bean {
    private String name;
    private String className;
    private String scope="singleton"; //Ä¬ÈÏscopeÎªsingleton

    private List<Property> properties = new ArrayList<Property>();

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
