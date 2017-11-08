
public class Location {
	
	Column col;
	int row;

	public Location (Location c) {
		col = c.col;
		row = c.row;
	}
	public Location (Column c, int r)
	{
		col = c;
		row = r;
	}
	public Location (int cN, int r)
	{
		row = r;
		col = initColumn (cN);
	}
	
	// create second constructor for a specific case regarding en passant
	// if when a new location is creating using this constructor, it means that the potential location
	// in the array is 2 ahead of the pawn, meaning a special case must be called
	
	private Column initColumn (int columnNumber) // efficiency
	{
		switch (columnNumber) {
		case 0:
			return Column.A;
		case 1:
			return Column.B;
		case 2:
			return Column.C;
		case 3:
			return Column.D;
		case 4:
			return Column.E;
		case 5:
			return Column.F;
		case 6:
			return Column.G;
		case 7:
			return Column.H;
		}
		return null;
	}
	
	public boolean equals (Location that)
	{
		return (this.col.getX() == that.col.getX() && this.row == that.row);
	}
	
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ();
		sb.append("" + col + "" + row);
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
