package org.terasoluna.securelogin.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DomainRestrictedEmailValidator implements
		ConstraintValidator<DomainRestrictedEmail, CharSequence> {

	private String[] allowedDomains;

	private boolean allowSubDomain;

	@Override
	public void initialize(DomainRestrictedEmail constraintAnnotation) {
		allowedDomains = constraintAnnotation.allowedDomains();
		allowSubDomain = constraintAnnotation.allowSubDomain();
	}

	@Override
	public boolean isValid(CharSequence value,
			ConstraintValidatorContext context) {
		if (value == null) {
			return true;
			
		}

		for (String domain : allowedDomains) {
			if (value.toString().endsWith("@" + domain)
					|| (allowSubDomain && value.toString().endsWith(
							"." + domain))) {
				return true;
			}
		}
		return false;
	}

}
