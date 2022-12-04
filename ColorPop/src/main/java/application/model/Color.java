package application.model;

public enum Color {
   RED(1, "#FF0000"), GREEN(2, "#00FF00"), BLUE(3, "#0000FF"), YELLOW(4, "#FFFF00"), EMPTY(0, "#FFFFFF");

    public int number;
    public String color;

    Color(int number, String color) {
        this.number = number;
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public static Color getColor(int number) {
        for (Color color : Color.values()) {
            if (color.number == number) {
                return color;
            }
        }
        return null;
    }


}