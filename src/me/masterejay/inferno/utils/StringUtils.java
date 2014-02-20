package me.masterejay.inferno.utils;

/**
 * @author MasterEjay
 */
public class StringUtils {

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch(NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}
}
