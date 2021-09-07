package com.xiangzi.learncloudconsumer.annotation;

import java.lang.annotation.*;

/**
 * <p></p >
 *
 * @author zhangxx
 * @since 1.0
 */
@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RabbitFatalEncountered {

}
