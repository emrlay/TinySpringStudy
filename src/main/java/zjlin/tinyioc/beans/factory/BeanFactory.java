package zjlin.tinyioc.beans.factory;

/**
 * bean的容器
 * Original author: Yihua.Huang
 */
public interface BeanFactory {

    Object getBean(String name) throws Exception;

}