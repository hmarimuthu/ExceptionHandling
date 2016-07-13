package com.etaap.common.util;

import java.util.HashMap;

import org.openqa.selenium.Keys;

public class KeysMap {

	static HashMap<String, CharSequence> keyMap = new HashMap<>();

	public static CharSequence get(String key) {

		if (keyMap.size() < 1) {
			initialize();
		}

		return keyMap.get(key);

	}

	private static void initialize() {

		for (Keys key : Keys.values()) {
			keyMap.put(key.name(), key);

		}

	}

}
