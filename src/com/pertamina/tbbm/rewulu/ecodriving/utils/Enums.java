package com.pertamina.tbbm.rewulu.ecodriving.utils;

public interface Enums {
	public enum Type {
		INTRO("Intro"), GUIDE("Guide");
		private String text = new String();

		private Type(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return this.text;
		}
	}

}
