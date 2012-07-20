package com.tmall.asshole.event.annotation;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author hemaodong
 *
 * @date 2012-5-21 обнГ09:50:30
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Ignore {

}
