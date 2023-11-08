// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package emailtemplate.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import emailtemplate.mail.EmailModule;
import emailtemplate.mail.SMTPConfiguration;
import emailtemplate.proxies.Header;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;

public class SendEmail extends CustomJavaAction<java.lang.Boolean>
{
	private java.lang.String SMTPHost;
	private java.lang.String SMTPUserName;
	private java.lang.String SMTPPassword;
	private java.lang.String CCAddresses;
	private java.lang.String BCCAddresses;
	private java.lang.String ToAddresses;
	private java.lang.String FromAddress;
	private java.lang.String ReplyToAddress;
	private java.lang.String HtmlBody;
	private java.lang.String PlainBody;
	private java.lang.String Subject;
	private java.lang.Long SMTPPort;
	private java.util.List<IMendixObject> __AttachmentList;
	private java.util.List<system.proxies.FileDocument> AttachmentList;
	private java.util.List<IMendixObject> __HeaderList;
	private java.util.List<emailtemplate.proxies.Header> HeaderList;
	private java.lang.Boolean UseSSL;
	private java.lang.Boolean UseTLS;
	private java.lang.String FromDisplayName;
	private java.lang.Boolean UseSSLCheckIdentity;

	public SendEmail(IContext context, java.lang.String SMTPHost, java.lang.String SMTPUserName, java.lang.String SMTPPassword, java.lang.String CCAddresses, java.lang.String BCCAddresses, java.lang.String ToAddresses, java.lang.String FromAddress, java.lang.String ReplyToAddress, java.lang.String HtmlBody, java.lang.String PlainBody, java.lang.String Subject, java.lang.Long SMTPPort, java.util.List<IMendixObject> AttachmentList, java.util.List<IMendixObject> HeaderList, java.lang.Boolean UseSSL, java.lang.Boolean UseTLS, java.lang.String FromDisplayName, java.lang.Boolean UseSSLCheckIdentity)
	{
		super(context);
		this.SMTPHost = SMTPHost;
		this.SMTPUserName = SMTPUserName;
		this.SMTPPassword = SMTPPassword;
		this.CCAddresses = CCAddresses;
		this.BCCAddresses = BCCAddresses;
		this.ToAddresses = ToAddresses;
		this.FromAddress = FromAddress;
		this.ReplyToAddress = ReplyToAddress;
		this.HtmlBody = HtmlBody;
		this.PlainBody = PlainBody;
		this.Subject = Subject;
		this.SMTPPort = SMTPPort;
		this.__AttachmentList = AttachmentList;
		this.__HeaderList = HeaderList;
		this.UseSSL = UseSSL;
		this.UseTLS = UseTLS;
		this.FromDisplayName = FromDisplayName;
		this.UseSSLCheckIdentity = UseSSLCheckIdentity;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		this.AttachmentList = java.util.Optional.ofNullable(this.__AttachmentList)
			.orElse(java.util.Collections.emptyList())
			.stream()
			.map(__AttachmentListElement -> system.proxies.FileDocument.initialize(getContext(), __AttachmentListElement))
			.collect(java.util.stream.Collectors.toList());

		this.HeaderList = java.util.Optional.ofNullable(this.__HeaderList)
			.orElse(java.util.Collections.emptyList())
			.stream()
			.map(__HeaderListElement -> emailtemplate.proxies.Header.initialize(getContext(), __HeaderListElement))
			.collect(java.util.stream.Collectors.toList());

		// BEGIN USER CODE

		SMTPConfiguration config = new SMTPConfiguration();
		if (this._logNode.isTraceEnabled()) {
			this._logNode.trace("Setting up SMTP configuration ( host: " + this.SMTPHost + ":" + this.SMTPPort + " user: " + this.SMTPUserName + " from:" + this.FromAddress);
		}

		if (this.SMTPHost == null)
			throw new CoreException("There is no smtp server address specified.");

		if(this.FromAddress != null && !"".equals(this.FromAddress.trim())) {
			config.setFromAddress(this.FromAddress);
		}
		else {
			throw new CoreException("There is no email address configured as the sender.");
		}
		
		if(this.FromDisplayName != null && !"".equals(this.FromDisplayName.trim())) {
			config.setFromDisplayName(this.FromDisplayName);
		}
		
		if(this.ReplyToAddress != null && !"".equals(this.ReplyToAddress.trim())) {
			config.setReplyToAddress(this.ReplyToAddress);
		}
		
		config.setSMTPHost(this.SMTPHost);
		config.setSMTPPort((this.SMTPPort != null ? this.SMTPPort.intValue() : (this.UseSSL ? 465 : 25)));
		config.setUserName((this.SMTPUserName != null ? this.SMTPUserName : ""));
		config.setUserPass((this.SMTPPassword != null ? this.SMTPPassword : ""));
		config.setUseSSLSMTP(this.UseSSL);
		config.setUseTLSSMTP(this.UseTLS);
		config.setUseSSLCheckServerIdentity(this.UseSSLCheckIdentity);

		String separator = (String) emailtemplate.proxies.constants.Constants.getEmailAddressSeparator();
		if (separator == null)
			separator = ",";

		ArrayList<String> toList = null;
		if (this.ToAddresses != null && !"".equals(this.ToAddresses.trim())) {
			String[] addrArr = this.ToAddresses.split(separator);
			toList = new ArrayList<String>();
			for (String addr : addrArr)
				toList.add(addr);
		}

		ArrayList<String> ccList = null;
		if (this.CCAddresses != null && !"".equals(this.CCAddresses.trim())) {
			String[] addrArr = this.CCAddresses.split(separator);
			ccList = new ArrayList<String>();
			for (String addr : addrArr)
				ccList.add(addr);
		}

		ArrayList<String> bccList = null;
		if (this.BCCAddresses != null && !"".equals(this.BCCAddresses.trim())) {
			String[] addrArr = this.BCCAddresses.split(separator);
			bccList = new ArrayList<String>();
			for (String addr : addrArr)
				bccList.add(addr);
		}
		
		Map<String, String> headerMap = new HashMap<String, String>();
		if (this.HeaderList != null) {
			for (Header header : this.HeaderList) {
				headerMap.put(header.getName(), header.getValue());
			}
		}
		
		this._logNode.trace("Send email to: " + (toList != null ? toList.size() : "0") + " addresses in to, " + (ccList != null ? ccList.size() : "0") + " in cc, " + (bccList != null ? bccList.size() : "0") + " in bcc");

		if ((toList != null ? toList.size() : 0) == 0 && (ccList != null ? ccList.size() : 0) == 0 && (bccList != null ? bccList.size() : 0) == 0)
			throw new CoreException("There is no email address found to send this email to.");

		try {
			if (this.PlainBody == null)
				this.PlainBody = ConvertHTMLBodyToPlainText.removeHTML(this.HtmlBody);
		}
		catch (Exception e) {
			throw new CoreException("Unable to convert the Html body to plain text for email: " + this.Subject + " to: " + this.ToAddresses, e);
		}

		if(emailtemplate.proxies.constants.Constants.getSendingEnabled()) {		
			EmailModule.mail(config, this.HtmlBody, this.PlainBody, this.Subject, toList, ccList, bccList, this.getContext(), this.__AttachmentList, headerMap);
		}
		else {
			this._logNode.info("Did not send email '"+this.Subject + "' to '"+ this.ToAddresses + "' since the 'EmailTemplate.SendingEnabled' constant is not enabled."); 
		}

		return true;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "SendEmail";
	}

	// BEGIN EXTRA CODE
	private ILogNode _logNode = Core.getLogger(this.toString());
	// END EXTRA CODE
}
