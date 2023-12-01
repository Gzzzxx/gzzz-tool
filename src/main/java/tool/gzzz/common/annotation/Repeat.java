package tool.gzzz.common.annotation;

import java.lang.annotation.*;

/**
 * Repeat 防止重复提交
 * @author gz
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Repeat {

    // @annotation

}