package pl.codeforfun;

public class Board {
	private int row;

	private String state;
	private int column;
	
	public Board(int row, int cols, String state){
		this.row = row;
		column = cols;
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
