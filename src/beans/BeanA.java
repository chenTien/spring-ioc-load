package beans;

/**
 * Created by chen.Tian on 2017/5/15.
 */
public class BeanA {
    private String name;

    public BeanA() {
        System.out.println("Bean A �����ɹ�");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
