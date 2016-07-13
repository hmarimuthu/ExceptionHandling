/*
 * 
 */
package com.etaap.core.infra.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.SearchTerm;

import org.apache.commons.logging.Log;

import com.etaap.common.util.LogUtil;

/**
 * The Class MailToAddressSearch.
 */
public class MailToAddressSearch extends SearchTerm implements IMailSearchCriteria {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Log log = LogUtil.getLog(MailToAddressSearch.class);

	/** The prop. */
	private Properties prop;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.mail.search.SearchTerm#match(javax.mail.Message)
	 */
	@Override
	public boolean match(Message message) {
		try {

			String checkToAddress = prop.getProperty(IEMailConstantUtil.MATCH_MAIL_TOADD);

			log.info(" checkToAddress " + checkToAddress);

			List<String> toAddresses = new ArrayList<>();
			Address[] recipients = message.getRecipients(Message.RecipientType.TO);
			for (Address address : recipients) {
				toAddresses.add(address.toString());
			}
			log.info("To Address " + toAddresses);

			for (String toAddress : toAddresses) {
				if (toAddress.contains(checkToAddress))
					return true;
			}

		} catch (MessagingException ex) {
			log.error(ex.getMessage());
			log.debug(ex);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etaap.infra.mail.IMailSearchCriteria#isMatch(javax.mail.Message,
	 * java.util.Properties)
	 */
	public boolean isMatch(Message message, Properties prop) {

		this.prop = prop;
		return match(message);
	}

}
