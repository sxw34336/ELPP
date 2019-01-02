package Entity;

public class LBS {
	
	public void decodeHilbert(int n,int d,User user){
		int rx,ry,s,t=d;
		int x=0;
		int y=0;
		for(s=1;s<n;s*=2){
			rx=1&(t/2);
			ry=1&(t^rx);
			rot(s, x,y, rx, ry);
			x+=s*rx;
			y+=s*ry;
			t/=4;
		}
		user.setGridx(x);
		user.setGridy(y);
	}
	
	private void rot(int n, int x,int y,int rx,int ry){
		if(ry==0){
			if(rx==1){
				x=n-1-x;
				y=n-1-y;
			}
			
			int temp=x;
			x=y;
			y=temp;
		}
	}

}
