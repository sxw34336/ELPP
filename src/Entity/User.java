package Entity;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class User {
	private int userID;//��ǰ�û�id
	private double x;//��ǰ�û���x����
	private double y;//��ǰ�û���y����
	private int poiClass;//��ǰ�û�����Ȥ������
	private int gridx;//��ǰ�û���������Ԫ��x����
	private int gridy;//��ǰ�û���������Ԫ��y����
	private QuerySpace querySpace;//��ǰ�û��Ĳ�ѯ�ռ�
	private int gridIdentifier;//�����ʶ
	private int STP;
	private Map<String, Double> offset;//�û���������������ƫ����
	
	/**
	 * ϣ������ת��
	 * @param n ��Ϊx��y����Ԫ�������ӦΪ2��k�η���
	 * @return ���������ʶ
	 */
	private int Hilbert(int n){
		int rx,ry,s,d=0;
		int x=this.gridx;
		int y=this.gridy;
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
	
	public double getDistance(User user){
		double x=user.getOffset().get("x");
		double y=user.getOffset().get("y");
		double distance=Math.sqrt(Math.pow(x-this.offset.get("x"), 2)+Math.pow(y-this.offset.get("y"), 2));
		return distance;
	}
	
public Comparator<User> comparator=new Comparator<User>() {
		
		@Override
		public int compare(User user1, User user2) {
			double distance1=getDistance(user1);
			double distance2=getDistance(user2);
			return distance1>distance2?1:-1;
		}
	};
	
	//��ʼ��---�û����꣨��ʵ��+��������+ƫ����+�����ʶ
	public User(double x,double y,int n,QuerySpace  querySpace){
		this.x=x;
		this.y=y;
		this.querySpace=querySpace;
		this.offset=new HashMap<String, Double>();
		this.gridx=(int) (x-querySpace.getStartx())/querySpace.getXgrid();
		this.gridy=(int) (y-querySpace.getStarty())/querySpace.getYgrid();
		this.offset.put("x", x-this.gridx*querySpace.getXgrid()-querySpace.getStartx());
		this.offset.put("y", y-this.gridy*querySpace.getYgrid()-querySpace.getStarty());
		this.gridIdentifier=Hilbert(n);
	}
	
	public User(double x,double y,QuerySpace querySpace){
		this.x=x;
		this.y=y;
		this.gridx=(int) (x-querySpace.getStartx())/querySpace.getXgrid();
		this.gridy=(int) (y-querySpace.getStarty())/querySpace.getYgrid();

	}
	
	public Map<String, Object> generateMSG(){
		Map<String, Object> MSGu2a=new HashMap<String, Object>();
		MSGu2a.put("ID",userID );
		MSGu2a.put("KEY", "K1,K2,K3,K4");
		MSGu2a.put("POI", poiClass);
		MSGu2a.put("grid_structure", querySpace);
		MSGu2a.put("offset", this.offset);
		MSGu2a.put("gridIdentifier",gridIdentifier);
		return MSGu2a;
	}
	
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getPoiClass() {
		return poiClass;
	}
	public void setPoiClass(int poiClass) {
		this.poiClass = poiClass;
	}
	public int getGridx() {
		return gridx;
	}
	public void setGridx(int gridx) {
		this.gridx = gridx;
	}
	public int getGridy() {
		return gridy;
	}
	public void setGridy(int gridy) {
		this.gridy = gridy;
	}
	public int getGridIdentifier() {
		return gridIdentifier;
	}
	public void setGridIdentifier(int gridIdentifier) {
		this.gridIdentifier = gridIdentifier;
	}
	public QuerySpace getQuerySpace() {
		return querySpace;
	}
	public void setQuerySpace(QuerySpace querySpace) {
		this.querySpace = querySpace;
	}
	public int getSTP() {
		return STP;
	}
	public void setSTP(int sTP) {
		STP = sTP;
	}
	public Map<String, Double> getOffset() {
		return offset;
	}
	public void setOffset(Map<String, Double> offset) {
		this.offset = offset;
	}
	
}
