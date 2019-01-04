package Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sun.security.action.GetBooleanAction;

public class LBS {
	
	public Map<String, Integer> decodeHilbert(int n,int d,User user){
		int rx,ry,s,t=d;
		int x=0;
		int y=0;
		Map<String, Integer> grid=new HashMap<String, Integer>();
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
		grid.put("X", user.getGridx());
		grid.put("Y", user.getGridy());
		return grid;
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
	
	public void UpToDown(User user,int i,int k,List<User> topkList){
		int t1,t2,pos;
		t1=2*i;
		t2=t1+1;
		if(t1>k)
			return;
		else{
			if(t2>k){
				pos=t1;
			}
			else{
				double d1=getDistance(user,topkList.get(t1-1));
				double d2=getDistance(user,topkList.get(t2-1));
				pos=d1>d2?t1:t2;
			}
			double dis1=getDistance(user,topkList.get(i-1));
			double dis2=getDistance(user,topkList.get(pos-1));
			if(dis1<dis2){
				Collections.swap(topkList, i-1, pos-1);
				UpToDown(user,pos, k, topkList);
			}
		}
	}
	
	public void createHeap(User user,int k,List<User> topkList){
		int i;
		int pos=k/2;
		for(i=pos;i>=1;i--){
			UpToDown(user,i, k, topkList);
		}
	}
	private double getDistance(User user1,User user2){
		double x1=user1.getX();
		double x2=user2.getX();
		double y1=user1.getY();
		double y2=user2.getY();
		double distance=Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
		return distance;
	}
	
	
	public List<User> searchKnn(User user,int k,List<User> poiList){
		List<User> candidate=new ArrayList<User>();
		List<User> userresult=new ArrayList<User>();
		List<User> topkList=new ArrayList<>();
		Iterator<User> iterator=poiList.iterator();
		while(iterator.hasNext()){
			User poi=iterator.next();
			
			if(user.getPoiClass()==poi.getPoiClass()&&user.getUserID()!=poi.getUserID()){
				candidate.add(poi);	
			}
		}
		for(int j=0;j<k;j++){
			topkList.add(candidate.get(j));
		}
		//System.out.println("topk:"+topkList.size());
		createHeap(user,k, topkList);
		for(int z=k;z<candidate.size();z++){
			if(getDistance(user,candidate.get(z))<getDistance(user,topkList.get(0))){
				topkList.set(0, candidate.get(z));
				UpToDown(user,1, k, topkList);
			}
		}
		userresult=topkList;
		//System.out.println("user:"+user.getUserID()+"   "+userresult);
		return userresult;
	}
	
	public List<User> getSearchResult(List<User> userList,int k,List<User> poiList){
		List<User> resultList=new ArrayList<>();
		List<Integer> userID=new ArrayList<>();
		int count=0;
		for(User user:userList){
			List<User> userresult=searchKnn(user,k, poiList);//距离每个用户最近的k个点
			//System.out.println("user:"+user.getUserID()+"  poi:"+userresult);
			for(User resultpoi:userresult){
				int id=resultpoi.getUserID();
				//System.out.println(id);
				if((!userID.contains(id))){
					resultList.add(resultpoi);
					userID.add(id);
					count++;
				}
			}
		}
		return resultList;
	}
/*	public List<User> search(List<User> userList,List<User> poisList,int k){
		List<User> resultList=new ArrayList<User>();
		for(User user:userList){
			List<User> topkList=new ArrayList<>();
			createHeap(user, k, topkList);
			for(User poi:topkList){
				if(!(resultList.contains(poi)))
			}
		}
		
	}*/
	
	
	
	

}
