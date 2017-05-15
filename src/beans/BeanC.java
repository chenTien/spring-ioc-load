package beans;

/**
 * Created by chen.Tian on 2017/5/15.
 */
public class BeanC {
    private BeanB beanB;

    public BeanC() {
        System.out.println("Bean C创建成功..");
    }

    public BeanB getBeanB() {
        return beanB;
    }

    public void setBeanB(BeanB beanB) {
        this.beanB = beanB;
    }
}
