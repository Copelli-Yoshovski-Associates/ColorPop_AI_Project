package application;

import application.model.Color;

public class Settings {

	public static final int DEFAULT_HEIGHT = 600;
	public static final int DEFAULT_WIDTH = 600;
	public static final int ROWS = 10;
	public static final int COLUMNS = 8;
	public static final int TOTAL_COLORS = Color.values().length - 1;
	public static final String MAX_TIME = "10";
	public static final boolean DEBUG = true;
	public static final int[][] DEFAULT_SCHEMA = {
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{1, 1, 1, 4, 4, 3, 1, 1},
			{3, 1, 2, 2, 3, 1, 3, 4},
			{1, 3, 1, 1, 3, 3, 3, 1}
	};
}
