package beans;

/**
 * Created by chen.Tian on 2017/5/15.
 */
public class BeanB {
    private BeanA beanA;

    public BeanB() {
        System.out.println("BeanB �����ɹ�");
    }

    public BeanA getBeanA() {
        return beanA;
    }

    public void setBeanA(BeanA beanA) {
        this.beanA = beanA;
    }
}
