package net.orcades.gwt.ginmvp.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

/**
 * 
 * Annotation used internally by the GinMvpModule to create 
 * internal bindings without exposing them for Places and
 * PlaceHistoryMapper
 * 
 * @author slynn1324
 */
@BindingAnnotation
@Target({ElementType.FIELD,ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GinMvpDefault {
}