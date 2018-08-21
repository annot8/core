package io.annot8.core.capabilities;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.annot8.core.bounds.Bounds;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Denotes the annotation types which are processed by the component.
 *
 * To simply use value and required are synonymous and either can be used. If both are used they are
 * merged into a combined list.
 *
 * Optional types are not needed in order for the component to generate output, but they may enhance
 * the processing.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Repeatable(ProcessesAnnotations.class)
public @interface ProcessesAnnotation {

  /**
   * @return required annotation types
   */
  String value();

  /**
   * @return the bound classes
   */
  Class<? extends Bounds> bounds() default Bounds.class;

  boolean optional() default false;

}