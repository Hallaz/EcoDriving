package com.pertamina.tbbm.rewulu.ecodriving.fragment;

public class Backstack {
	private static boolean about;
	private static boolean hisOntories;
	private static boolean mainmenu;
	private static boolean maintracking;
	private static boolean result;
	private static boolean splash;

	private static void falseAll() {
		// TODO Auto-generated method stub
		about = false;
		hisOntories = false;
		mainmenu = false;
		maintracking = false;
		result = false;
		splash = false;
	}

	public static void onAbout() {
		falseAll();
		about = true;
	}

	public static void onHistories() {
		falseAll();
		hisOntories = true;
	}

	public static void onMainmenu() {
		falseAll();
		mainmenu = true;
	}

	public static void onMaintracking() {
		falseAll();
		maintracking = true;
	}

	public static void onResult() {
		falseAll();
		result = true;
	}

	public static void onSplash() {
		falseAll();
		splash = true;
	}

	/**
	 * @return the about
	 */
	public static boolean isOnAbout() {
		return about;
	}

	/**
	 * @return the hisOntories
	 */
	public static boolean isOnHistories() {
		return hisOntories;
	}

	/**
	 * @return the mainmenu
	 */
	public static boolean isOnMainmenu() {
		return mainmenu;
	}

	/**
	 * @return the maintracking
	 */
	public static boolean isOnMaintracking() {
		return maintracking;
	}

	/**
	 * @return the result
	 */
	public static boolean isOnResult() {
		return result;
	}

	/**
	 * @return the splash
	 */
	public static boolean isOnSplash() {
		return splash;
	}
}
