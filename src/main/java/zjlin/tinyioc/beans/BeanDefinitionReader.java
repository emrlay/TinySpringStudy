package zjlin.tinyioc.beans;


/**
 * 从配置中读取BeanDefinition
 * Original author : Yihua.Huang
 */
public interface BeanDefinitionReader {

    void loadBeanDefinitions(String location) throws Exception;
}