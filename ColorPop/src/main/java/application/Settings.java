package application;

import application.model.Block;
import application.model.Color;

import java.util.List;

public class Settings {

	public static final int DEFAULT_HEIGHT = 600;
	public static final int DEFAULT_WIDTH = 600;
	public static final int ROWS = 10;
	public static final int COLUMNS = 8;
	public static final int TOTAL_COLORS = Color.values().length - 1;
	public static final int MAX_TIME = 120;
	public static final boolean DEBUG = false;
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


	public static final int GENERATION_TIME = 5;
	public static final String FAVICON = "/favicon.png";
	public static final int MIN_NEIGHBORS = 2;
	public static final int BLOCK_SCORE = 11;
}
