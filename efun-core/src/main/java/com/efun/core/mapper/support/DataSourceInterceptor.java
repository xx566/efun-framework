package com.efun.core.mapper.support;

import com.efun.core.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * DataSourceInterceptor
 * 数据源选择的拦截，应用于service和mapper层。
 *
 * @author Galen
 * @since 2016/6/15
 */
public class DataSourceInterceptor {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {

        Object object = null;
        DataSource dataSource = null;
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            dataSource = methodSignature.getMethod().getAnnotation(DataSource.class);
            if (dataSource == null) {
                Class<?> clz = methodSignature.getDeclaringType();
                dataSource = clz.getAnnotation(DataSource.class);
            }
            if (dataSource != null) {
                DataSourceContext.setDataSourceKey(dataSource.value());
                logger.debug("### rounting to dataSource: " + dataSource.value());
            }
            object = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            if (dataSource != null) {
                DataSourceContext.cleanDataSourceKey(dataSource.value());
            }
        }
        return object;
    }

}