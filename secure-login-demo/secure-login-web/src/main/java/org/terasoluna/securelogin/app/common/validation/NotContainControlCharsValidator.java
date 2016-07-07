package org.terasoluna.securelogin.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.terasoluna.gfw.common.codepoints.CodePoints;
import org.terasoluna.gfw.common.codepoints.catalog.ASCIIControlChars;
import org.terasoluna.gfw.common.codepoints.catalog.CRLF;

public class NotContainControlCharsValidator implements
		ConstraintValidator<NotContainControlChars, CharSequence> {

	private CodePoints codePoints;

	@Override
	public void initialize(NotContainControlChars constraintAnnotation) {
		if (constraintAnnotation.allowCRLF()) {
			CodePoints crlf = new CRLF();
			this.codePoints = CodePoints.of(ASCIIControlChars.class).subtract(
					crlf);
		} else {
			this.codePoints = CodePoints.of(ASCIIControlChars.class);
		}
	}

	@Override
	public boolean isValid(CharSequence value,
			ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		
		int len = value.length();
		for (int i = 0; i < len; i++) {
			if (codePoints.firstExcludedCodePoint(String.valueOf(value
					.charAt(i))) == CodePoints.NOT_FOUND) {
				return false;
			}
		}

		return true;
	}

}
