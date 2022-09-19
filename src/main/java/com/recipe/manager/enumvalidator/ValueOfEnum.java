package com.recipe.manager.enumvalidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

  @Target({ElementType.FIELD, ElementType.PARAMETER})
  @Retention(RetentionPolicy.RUNTIME)
  @Constraint(validatedBy = ValueOfEnumValidator.class)
  public @interface ValueOfEnum {
    /**
     * @return represents enum class
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * @return error message
     */
    String message() default "must be any of enum {enumClass}";

    /**
     * @return represents group of constraints
     */
    Class<?>[] groups() default {};

    /**
     * @return case sensitive flag
     */
    boolean caseSensitive() default true;
    /**
     * @return represents additional information about annotation
     */
    Class<? extends Payload>[] payload() default {};
  }


