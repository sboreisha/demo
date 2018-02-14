package com.exadel.automation.listeners.annotations;

/**
 * Created by sboreisha on 2/13/2018.
 */

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomStep {
    String comments();
}