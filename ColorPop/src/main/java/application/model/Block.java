package application.model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.Data;

@Id("BLOCK")
@Data
public class Block implements java.io.Serializable {

	@Param(0)
	private int x;
	@Param(1)
	private int y;

	@Param(2)
	private int colorNumber;

	public Block() {
	}

	public Block(int x, int y, int color) {
		this.x = x;
		this.y = y;
		this.colorNumber = color;
	}

	public Block(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.colorNumber = color.ordinal();
	}

	public Color getColor() {
		for (Color c : Color.values())
			if (c.ordinal() == colorNumber) return c;
		return Color.EMPTY;
	}

	@Override
	public String toString() {
		return "Block [x=" + x + ", y=" + y + ", colorNumber=" + colorNumber + "]";
	}

}
