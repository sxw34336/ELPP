package Entity;

import java.util.List;

public class Grid {
	private int x;
	private int y;
	private int gridIdentifier;
	private List<User> userList;
	
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Grid(int x,int y,int n){
		this.x=x;
		this.y=y;
		this.gridIdentifier=Hilbert(n);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getGridIdentifier() {
		return gridIdentifier;
	}

	public void setGridIdentifier(int gridIdentifier) {
		this.gridIdentifier = gridIdentifier;
	}

	private int Hilbert(int n){
		int rx,ry,s,d=0;
		int x=this.x;
		int y=this.y;
		for(s=n/2;s>0;s/=2){
			rx=((x&s)>0)?1:0;
			ry=((y&s)>0)?1:0;
			d+=s*s*((3*rx)^ry);
			rot(s,x,y,rx, ry);
		}
		return d;
	}
	private void rot(int n,int x,Integer y,int rx,int ry){
		if(ry==0){
			x=n-1-x;
			y=n-1-y;
		}
		
		int temp=x;
		x=y;
		y=temp;
		
	}
}
