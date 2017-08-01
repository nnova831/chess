
public class Location {
	
	Column col;
	int row;

	public Location (Column c, int r)
	{
		col = c;
		row = r;
	}
	public Location (int cN, int r)
	{
		row = r;
		switch (cN) {
		case 0:
			col = Column.A;
			break;
		case 1:
			col = Column.B;
			break;
		case 2:
			col = Column.C;
			break;
		case 3:
			col = Column.D;
			break;
		case 4:
			col = Column.E;
			break;
		case 5:
			col = Column.F;
			break;
		case 6:
			col = Column.G;
			break;
		case 7:
			col = Column.H;
			break;
		default:
			break;
		}
	}
	
	public boolean equals (Location that)
	{
		return (this.col.getX() == that.col.getX() && this.row == that.row);
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
