package org.terasoluna.securelogin.app.account;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.gfw.common.validator.constraints.Compare;
import org.terasoluna.gfw.common.validator.constraints.Compare.Operator;
import org.terasoluna.securelogin.app.common.validation.DomainRestrictedEmail;
import org.terasoluna.securelogin.app.common.validation.DomainRestrictedURL;
import org.terasoluna.securelogin.app.common.validation.FileExtension;
import org.terasoluna.securelogin.app.common.validation.FileNamePattern;
import org.terasoluna.securelogin.app.common.validation.NotContainControlChars;
import org.terasoluna.securelogin.app.common.validation.UploadFileMaxSize;
import org.terasoluna.securelogin.app.common.validation.UploadFileNotEmpty;
import org.terasoluna.securelogin.app.common.validation.UploadFileRequired;

import lombok.Data;

@Data
@Compare(left="email", right="confirmEmail", operator=Operator.EQUAL, requireBoth=true, node=Compare.Node.ROOT_BEAN)
public class AccountCreateForm implements Serializable {

	public static interface Confirm{};
	
	public static interface CreateAccount{};
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@NotContainControlChars
	@Size(min=4, max=128)
	private String username;
	
	@NotNull
	@NotContainControlChars
	@Size(min=1, max=128)
	private String firstName;
	
	@NotNull
	@NotContainControlChars
	@Size(min=1, max=128)
	private String lastName;
	
	@NotNull
	@NotContainControlChars
	@Size(min=1, max=128)
	@DomainRestrictedEmail(allowedDomains={"ntt.co.jp", "nttdata.co.jp"}, allowSubDomain=true)
	private String email;
	
	@NotNull
	@NotContainControlChars
	private String confirmEmail;
	
	@NotNull
	@NotContainControlChars
	@DomainRestrictedURL(allowedDomains={"jp"})
	private String url;

	@UploadFileRequired(groups=Confirm.class)
	@UploadFileNotEmpty(groups=Confirm.class)
	@UploadFileMaxSize
	@FileExtension(extensions={"jpg", "png", "gif"})
	@FileNamePattern(pattern="[a-zA-Z0-9_-]+\\.[a-zA-Z]{3}")
	private MultipartFile image;
	
	@NotNull(groups=CreateAccount.class)
	@Size(max=40)
	private String imageId;
	
	@NotNull
	@NotContainControlChars(allowCRLF=true)
	private String profile;
	
}
