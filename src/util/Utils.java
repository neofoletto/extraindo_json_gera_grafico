/**
                    GNU GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.
 */
package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public boolean isValidNumber(String imput) {
		if (imput != null && imput.length() > 0) {
			String expression = "^[0-9]+$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(imput);
			if (matcher.matches())
				return true;
		}
		return false;
	}

	public boolean isValidEmailAddressDomainRegex(String imput) {
		imput = imput.replace("@", "");
		if (imput != null && imput.length() > 0) {
			String expression = "^[a-zA-Z0-9.-]+.[a-zA-Z]+$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(imput);
			if (matcher.matches())
				return true;
		}
		return false;
	}

	public boolean isValidEmailAddressRegex(String email) {
		if (email != null && email.length() > 0) {
			String expression = "^[a-zA-Z0-9_!#$%&’*+\\/=?`{|}~^.-]+@[a-zA-Z0-9.-]+.[a-zA-Z]+$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches())
				return true;
		}
		return false;
	}

	public static boolean isValidUrlCurvelloSite(String imput) {
		if (imput != null && imput.length() > 0) {
			String expression = "((http|ftp|https):\\/\\/)?((www.curvello|curvello)\\.([\\w]*))\\/gerador\\/pessoa\\/1000";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(imput);
			if (matcher.matches())
				return true;
		}
		return false;
	}
}
