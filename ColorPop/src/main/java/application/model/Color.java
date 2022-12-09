package application.model;

public enum Color {
	EMPTY(0, "#FFFFFF"),
	RED(1, "#FF0000"),
	GREEN(2, "#00FF00"),
	BLUE(3, "#0000FF"),
	YELLOW(4, "#FFFF00");

	private final int number;
	private final String color;

	Color(int number, String color) {
		this.number = number;
		this.color = color;
	}

	public int getNumber() {
		return number;
	}

	public static String getRGB(int number) {
		for (Color color : Color.values()) {
			if (color.number == number) {
				return color.color;
			}
		}
		return Color.EMPTY.color;
	}

	public static String getRGB(Color color) {
		return getRGB(color.number);
	}

	public static Color getColor(int number) {
		for (Color color : Color.values())
			if (color.number == number) return color;
		return Color.EMPTY;
	}


	public boolean isSpecial() {
		return this.number < 0;
	}
}