package com.sample.app.demo.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {

	private static final String DATE_PATTERN = "^((?:BCBSNC_PRIME_Medical_Eligibility_))((?:19|20)[0-9][0-9](0?[1-9]|1[012])(0?[1-9]|[12][0-9]|3[01])_([01][0-9]|[2][0-3])([0-5][0-9])([0-5][0-9]))(\\.(?:csv))$";

	private static final Pattern pattern = Pattern.compile(DATE_PATTERN);

	public static void main(String[] args) {
		String fileName="BCBSNC_PRIME_Medical_Eligibility_20201130_235959.csv";
		validateFile(fileName);

	}

	private static boolean validateFile(String fileName) {
		Matcher matcher = pattern.matcher(fileName);
		if(matcher.matches()) {
			String formattedDate= matcher.group(2);
			try {
				LocalDate.parse(formattedDate,
		                DateTimeFormatter.ofPattern("uuuuMMdd_HHmmss")
		                        .withResolverStyle(ResolverStyle.STRICT));
				return true;
			}catch(Exception e) {
				return false;
			}
			
		}
		return false;
	}

}
