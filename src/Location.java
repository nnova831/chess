
public class Location {
	
	private Column col;
	private int row;

	public Location (Column c, int yCoor)
	{
		col = c;
		row = yCoor;
	}
	
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ();
		sb.append("(" + col + "" + row + ")");
		return sb.toString();
	}
	
	public Column getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setCol(Column col) {
		this.col = col;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
}
