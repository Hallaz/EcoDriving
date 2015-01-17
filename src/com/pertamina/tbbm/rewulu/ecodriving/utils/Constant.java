package com.pertamina.tbbm.rewulu.ecodriving.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Constant {

	public static final int DIALOG_FINISH_TRACK = 15151515;

	public static final int DIALOG_FUEL_REFILL = 48484848;

	public static final int DIALOG_ERR = 895956;

	public static final int SPEED_TOLERANCE = 1;

	public static final int DIALOG_EXIT_TRACK = 2121212;

	public static final double FAKTOR_EMISI = 2.32d;

	public static final String MENU_CONST = "menu_text";

	public static final String ADDRESS_UNKNOWN = "unknown";

	public static final String TITLE_UNKNOWN = "NN";

	public static ArrayList<HashMap<String, String>> menu_item(String[] menus) {
		ArrayList<HashMap<String, String>> menu = new ArrayList<>();
		for (int w = 0; w < menus.length; w++) {
			HashMap<String, String> val = new HashMap<>();
			val.put(MENU_CONST, menus[w]);
			menu.add(val);
		}
		return menu;
	}
}
