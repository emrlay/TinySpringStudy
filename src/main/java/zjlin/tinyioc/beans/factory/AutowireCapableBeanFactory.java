package zjlin.tinyioc.beans.factory;

import zjlin.tinyioc.BeanReference;
import zjlin.tinyioc.beans.BeanDefinition;
import zjlin.tinyioc.beans.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 可自动装配内容的BeanFactory
 * Original author: Yihua.Huang
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    protected void applyPropertyValues(Object bean, BeanDefinition mbd) throws Exception {
        //TODO: AOP
//        if (bean instanceof BeanFactoryAware) {
//            ((BeanFactoryAware) bean).setBeanFactory(this);
//        }
        for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getName());
            }

            try {
                Method declaredMethod = bean.getClass().getDeclaredMethod(
                        "set" + propertyValue.getName().substring(0, 1).toUpperCase()
                                + propertyValue.getName().substring(1), value.getClass());
                declaredMethod.setAccessible(true);

                declaredMethod.invoke(bean, value);
            } catch (NoSuchMethodException e) {
                Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
                declaredField.setAccessible(true);
                declaredField.set(bean, value);
            }
        }
    }
}