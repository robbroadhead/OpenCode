package com.sns.core;

public class Point {
	private int x=0;
	private int y=0;
	
	public Point() {
		x=0;
		y=0;
	}
	
	public Point(int a,int b) {
		x=a;
		y=b;
	}

	public int getX() { return x; }	
	public int getY() { return y; }
	public void setX(int val) { x = val; }	
	public void setY(int val) { y = val; }	

	public Point add(Point val) {
		return new Point(x+val.getX(),y+val.getY());
	}

	public void Up(int mxY) {
		if (y< mxY) {
			y++;
		}
	}
	
	public void Down() {
		if (y > 0) {
			y--;
		}
	}
	
	public void Right(int mxX) {
		if (x< mxX) {
			x++;
		}
	}
	
	public void Left() {
		if (x > 0) {
			x--;
		}
	}

	public Point Add(Point val) {
		return new Point(val.getX() + x,val.getY() + y);
	}

	public String toString() {
		return "[" + x + ", " + y + "]";
	}	
	
	public int distance(Point val) {
		int retVal = 0;
		double temp;
		
		temp = (Math.pow(x-val.getX(),2) + Math.pow(y-val.getY(),2));
		retVal = (int) Math.round(Math.sqrt(temp));
		return retVal;
	}
	
	public boolean equals(Point val) {
		return ((x == val.getX() && y == val.getY()));
	}
}

