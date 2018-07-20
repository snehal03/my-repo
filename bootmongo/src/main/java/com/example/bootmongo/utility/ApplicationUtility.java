package com.example.bootmongo.utility;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;


@Component
public class ApplicationUtility {

	/** The message source. */
	@Autowired
	MessageSource messageSource;
	
	public String generateKey() {
		try {
			SecureRandom random = new SecureRandom();
			return new BigInteger(130, random).toString(32);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets the message.
	 *
	 * @param key
	 *            the key
	 * @return the message
	 */

	public String getMessage(String key) {
		try {
			return messageSource.getMessage(key, null, Locale.US);
		} catch (Exception e) {
			return null;
		}
	}

	public String getMessage(String key, String[] args) {
		try {
			return messageSource.getMessage(key, args, Locale.US);
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean isEmpty(Object object) {
		if (object != null) {
			if (object instanceof CharSequence) {
				if (StringUtils.isEmpty((CharSequence) object)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	

}
