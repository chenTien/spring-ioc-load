package config;

/**
 * Created by chen.Tian on 2017/5/15.
 */
public class Property {
    private String name;
    private String value;   //普通属性
    private String ref;     //引用

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }


}
