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
 * The Class MailFromAddressSearch.
 */
public class MailFromAddressSearch extends SearchTerm implements IMailSearchCriteria {

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

			String checkFromAddress = prop.getProperty(IEMailConstantUtil.MATCH_MAIL_FROMADD);

			log.info(" checkFromAddress " + checkFromAddress);

			List<String> fromAddresses = new ArrayList<String>();
			Address[] recipients = message.getFrom();
			for (Address address : recipients) {
				fromAddresses.add(address.toString());
			}
			log.info(" fromAddresses " + fromAddresses);

			for (String fromAddress : fromAddresses) {
				if (fromAddress.contains(checkFromAddress))
					return true;
			}

		} catch (MessagingException ex) {
			log.error("Error " + ex.getMessage());
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
