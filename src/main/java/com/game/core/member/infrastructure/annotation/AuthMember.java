package com.game.core.member.infrastructure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE}) // 1
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthMember {
}