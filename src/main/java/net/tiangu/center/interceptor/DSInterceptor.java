package net.tiangu.center.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.log.Logger;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.lang.reflect.Field;

public class DSInterceptor
        implements Interceptor {
    private static Logger logger = Logger.getLogger(DSInterceptor.class);

    public void intercept(ActionInvocation ai) {
        BindDS(ai.getController());
        ai.invoke();
    }

    private void BindDS(Object obj) {
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Resource.class)) {
                Resource resource = field.getAnnotation(Resource.class);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                try {
                    field.set(obj, getDS(resource.mappedName()));
                } catch (Exception e) {
                    if (logger.isDebugEnabled()) {
                        logger.debug(e.getMessage(), e);
                    } else {
                        logger.error(String.format("数据源 [%s] 未找到", resource.mappedName()));
                    }
                }
            }
        }
    }

    private DataSource getDS(String name)
            throws Exception {
        return (DataSource) new InitialContext().lookup(name);
    }
}
