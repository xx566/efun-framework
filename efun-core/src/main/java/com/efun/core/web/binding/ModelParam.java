package com.efun.core.web.binding;

import java.lang.annotation.*;

/**
 * RequestBean
 *
 * @author Galen
 * @since 2016/6/6
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
@Documented
public @interface ModelParam {

    String value() default "";

}
