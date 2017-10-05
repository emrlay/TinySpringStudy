package zjlin.tinyioc.beans;

/**
 * bean的内容及元数据，保存在BeanFactory中，包装bean的实体
 * Original author : Yihua.Huang
 */
public class BeanDefinition {

    private Object bean;

    private Class beanClass;

    private String beanClassName;

    private PropertyValues propertyValues = new PropertyValues();

    public BeanDefinition() {
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        //在set了string型的beanClassName的同时，也通过Class.forName来拿到相应的Bean的Class对象
        this.beanClassName = beanClassName;
        try {
            this.beanClass = Class.forName(beanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Object getBean() {
        return bean;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}