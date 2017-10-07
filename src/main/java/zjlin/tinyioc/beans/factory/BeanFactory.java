package zjlin.tinyioc.beans.factory;

/**
 * bean的容器
 * 最顶层的基类，定义了getBean这个接口方法：来容器里通过名字找对应的Bean
 * Original author: Yihua.Huang
 */
public interface BeanFactory {

    /**
     * 原始的BeanFactory接口中最重要的接口方法，从Bean容器中获取相应的Bean
     * 实际的Spring实现中，如果是Singleton Bean，从ConcurrentHashMap中拿出相应的实例
     * 如果是prototype Bean，则创建Bean并返回
     * @param name bean名称
     * @return bean的实例
     * @throws Exception
     */
    Object getBean(String name) throws Exception;

}