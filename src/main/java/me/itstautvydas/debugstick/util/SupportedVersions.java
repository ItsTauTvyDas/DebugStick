package me.itstautvydas.debugstick.util;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;

public class SupportedVersions {
	
	private static List<String> sv = Arrays.asList("1.8", "1.9", "1.10", "1.11", "1.12");
	private static List<Integer> sv2 = Arrays.asList(8, 9, 10, 11, 12);
	
	public static String read(String bukkit_version) {
		if (bukkit_version != null)
			return bukkit_version.split(": ")[1].replace(")", "");
		else
			return Bukkit.getVersion().split(": ")[1].replace(")", "");
	}
	
	public static List<String> getVersions() {
		return sv;
	}
	
	public static List<Integer> getVersionsInt() {
		return sv2;
	}
	
	/**
	 * Gets main version, for example 1.8.8 to 1.8
	 * @param full_version If null, then server version is used instead.
	 * @return
	 */
	public static String getMain(@Nullable String full_version) {
		if (full_version == null)
			full_version = getServerVersion();
		String[] a = full_version.split("\\.");
		return a[0]+"."+a[1];
	}
	
	/**
	 * Gets integer from version, for example 1.9.4 to 9 or 1.8 to 8.
	 * @param full_or_main_version If null, then server version is used instead.
	 * @return Integer of version
	 */
	public static int getInteger(@Nullable String full_or_main_version) {
		if (full_or_main_version != null)
			return Integer.valueOf(full_or_main_version.split("\\.")[1]);
		else
			return Integer.valueOf(getServerVersion().split("\\.")[1]);
	}
	
	public static String getServerVersion() {
		return read(Bukkit.getServer().getVersion());
	}

	/**
	 * Currently supported versions: 1.8.x, 1.9.x, 1.10.x, 1.11.x, 1.12.x.
	 * @return Checks if supported versions list contains server version.
	 */
	public static boolean isSupported() {
		return sv2.contains(getInteger(null));
	}
	
	/**
	 * Currently supported versions: 1.8.x, 1.9.x, 1.10.x, 1.11.x, 1.12.x.
	 * @param int_version Version's integer.
	 * @returnChecks if supported versions list contains specified integer version.
	 */
	public static boolean isSupported(int int_version) {
		return sv2.contains(int_version);
	}
	
	/**
	 * Currently supported versions: 1.8.x, 1.9.x, 1.10.x, 1.11.x, 1.12.x.
	 * @param main_version If null, then server version is used instead.
	 * @return Checks if supported versions list contains specified main version.
	 */
	public static boolean isSupported(@Nullable String main_version) {
		if (main_version != null)
			return sv.contains(main_version);
		else
			return sv.contains(getMain(getServerVersion()));
	}
}
