public enum Column {
	A (0),
	B (1),
	C (2),
	D (3),
	E (4),
	F (5),
	G (6),
	H (7);
	
	private final int x;
	
	private Column(int x)
	{
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	//returns the index of the row number and column (this) referenced.
	// IMPORTANT: int row refers to the row numbers being ordered in decending order
	// i.e 8 - 1 up to down
	public int getIndex(int row)
	{
		return ((8 - row) * 8) + this.getX();	
	}
	
}
