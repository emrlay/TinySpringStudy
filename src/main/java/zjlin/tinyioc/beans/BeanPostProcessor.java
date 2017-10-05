package zjlin.tinyioc.beans;


/**
 * Bean后处理器接口
 * Original author : Yihua.Huang
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception;
    Object postProcessAfterInitialization(Object bean, String beanName) throws Exception;

}