package pl.codeforfun;

public class Board {
	private int row;

	private String state;
	private int column;
	
	public Board(int row, int cols, String state){
//		System.out.println(row + " - " + cols);
		this.row = row;
		column = cols;
//		setColumn(cols);
		this.state = state;
	}

	public int getRow(){
		return row;
	}
	
	public void setRow(int row){
		this.row = row;
	}
	
	public String getState(){
		return state;
	}
	
	public void setState(String state){
		this.state = state;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

}
