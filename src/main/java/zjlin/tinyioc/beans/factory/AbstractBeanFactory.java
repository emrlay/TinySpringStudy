package zjlin.tinyioc.beans.factory;

import zjlin.tinyioc.beans.BeanDefinition;
import zjlin.tinyioc.beans.BeanPostProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象Bean工厂, AutowireCapableBeanFactory的直接基类
 * 该类中定义
 * Original author: Yihua.Huang
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    /**
     * 用一个Map来保存所有的BeanDefinition，缓存的最基本形式就是通过一个Map来进行存储
     * 真实的Spring中，这个Map定义在DefaultListableBeanFactory中
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    /**
     * 存储所有BeanDefinition的名字
     */
    private final List<String> beanDefinitionNames = new ArrayList<String>();

    /**
     * 存储了所有BeanPostProcessor的实例
     */
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    @Override
    public Object getBean(String name) throws Exception {
        //加载相应的bean定义
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            throw new IllegalArgumentException("No bean named " + name + " is defined");
        }
        //如果bean未创建，那么创建bean，进行初始化（调用BeanPostProcessor的一系列）
        Object bean = beanDefinition.getBean();
        if (bean == null) {
            //创建bean的实例
            bean = doCreateBean(beanDefinition);
            //bean初始化，由定义的BeanPostProcessor来完成，这里实际上是一个扩展点
            bean = initializeBean(bean, name);
            //这步似乎是多余？doCreateBean中已经做过了。
            beanDefinition.setBean(bean);
        }
        return bean;
    }

    protected Object initializeBean(Object bean, String name) throws Exception {
        //依次调用接口中的before方法
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
        }

        // 这个地方实际上还要调用initialize方法，tinySpring省略了

        //依次调用接口中的after方法
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
        }
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
        //通过反射调用默认构造子来创建bean
        return beanDefinition.getBeanClass().newInstance();
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception {
        beanDefinitionMap.put(name, beanDefinition);
        beanDefinitionNames.add(name);
    }

    /**
     * 初始化Singleton Beans
     * @throws Exception
     */
    public void preInstantiateSingletons() throws Exception {
        for (Iterator it = this.beanDefinitionNames.iterator(); it.hasNext();) {
            String beanName = (String) it.next();
            getBean(beanName);
        }
    }

    /**
     * 实际完成创建bean实例的过程
     * @param beanDefinition bean定义
     * @return bean实例
     * @throws Exception aa
     */
    protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        //先通过反射调用构造子拿到对象
        Object bean = createBeanInstance(beanDefinition);
        beanDefinition.setBean(bean);
        //这个方法为bean设置相应的属性，它被留到子类中进行具体的实现(要考虑default-autowire等)
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }

    protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {

    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) throws Exception {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List getBeansForType(Class type) throws Exception {
        List beans = new ArrayList<Object>();
        for (String beanDefinitionName : beanDefinitionNames) {
            if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
                beans.add(getBean(beanDefinitionName));
            }
        }
        return beans;
    }

}