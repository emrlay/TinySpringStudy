package zjlin.tinyioc.beans.factory;

import zjlin.tinyioc.BeanReference;
import zjlin.tinyioc.beans.BeanDefinition;
import zjlin.tinyioc.beans.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 可自动装配内容的BeanFactory
 * tinySpring实现中唯一一个BeanFactory的具体类
 * Original author: Yihua.Huang
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    protected void applyPropertyValues(Object bean, BeanDefinition mbd) throws Exception {
        //TODO: AOP
//        if (bean instanceof BeanFactoryAware) {
//            ((BeanFactoryAware) bean).setBeanFactory(this);
//        }
        for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
            // 对于Bean定义中的所有成员(都被存储在pv对中)，进行处理
            // 需要注意的是tinySpring似乎并没有处理循环依赖的相关问题。
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getName());
            }

            try {
                //通过反射拿到对应的set方法，通过invoke对应的set方法来注入相应的对象
                Method declaredMethod = bean.getClass().getDeclaredMethod(
                        "set" + propertyValue.getName().substring(0, 1).toUpperCase()
                                + propertyValue.getName().substring(1), value.getClass());
                declaredMethod.setAccessible(true);

                declaredMethod.invoke(bean, value);
            } catch (NoSuchMethodException e) {
                //如果找不到这个方法，那么强行通过反射拿到这个域，将其变成accessible，设置进去
                Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
                declaredField.setAccessible(true);
                declaredField.set(bean, value);
            }
        }
    }
}