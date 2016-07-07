package org.terasoluna.securelogin.app.common.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {NotContainControlCharsValidator.class})
@Target({ FIELD })
@Retention(RUNTIME)
public @interface NotContainControlChars {
	String message() default "{org.terasoluna.securelogin.app.common.validation.NotContainControlChars.message}";

	Class<?>[] groups() default {};

	boolean allowCRLF() default false;

	@Target({ FIELD })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		NotContainControlChars[] value();
	}

	Class<? extends Payload>[] payload() default {};
}
