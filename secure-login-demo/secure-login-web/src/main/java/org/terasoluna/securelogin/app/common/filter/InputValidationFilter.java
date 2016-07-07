package org.terasoluna.securelogin.app.common.filter;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.terasoluna.securelogin.app.common.filter.exception.InvalidCharacterException;

import com.google.common.primitives.Chars;

public class InputValidationFilter extends OncePerRequestFilter {

	private final List<Character> prohibitedChars;

	private final List<Character> prohibitedCharsForFileName;

	public InputValidationFilter(char[] prohibitedChars,
			char[] prohibitedCharsForFileName) {
		this.prohibitedChars = Chars.asList(prohibitedChars);
		this.prohibitedCharsForFileName = Chars
				.asList(prohibitedCharsForFileName);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		validateRequestParams(request);

		if (request instanceof MultipartRequest) {
			validateFileNames((MultipartRequest) request);
		}

		filterChain.doFilter(request, response);
	}

	private void validateRequestParams(HttpServletRequest request) {
		Map<String, String[]> params = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			validate(entry.getKey(), prohibitedChars);
			for (int i = 0; i < entry.getValue().length; i++) {
				validate(entry.getValue()[i], prohibitedChars);
			}
		}
	}

	private void validateFileNames(MultipartRequest request) {
		for (Map.Entry<String, MultipartFile> entry : request.getFileMap()
				.entrySet()) {
			String filename = new File(entry.getValue().getOriginalFilename())
					.getName();
			validate(filename, prohibitedCharsForFileName);
		}
	}

	private void validate(String target, List<Character> prohibited) {
		List<Character> chars = Chars.asList(target.toCharArray());
		for(Character prohibitedChar : prohibited){
			if (chars.contains(prohibitedChar)) {
				throw new InvalidCharacterException(
					"The request contains prohibited charcter.");
			}
		}
	}

}
