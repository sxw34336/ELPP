package Entity;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Anonymizer {
	
	
	/*public double getDistance(User user1,User user2){
		double x1=user1.getOffset().get("x");
		double y1=user1.getOffset().get("y");
		double x2=user2.getOffset().get("x");
		double y2=user2.getOffset().get("y");
		double distance=Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
		return distance;
	}*/
	 public Map<String, Object> generateMSGa2l(User user,List<User> userList){
		 Map<String, Object> MSGa2l=new HashMap<>();
		 MSGa2l.put("GridIdentifier", user.getGridIdentifier());
		 MSGa2l.put("offsetMBR", createAnonymityArea(userList));
		 return MSGa2l;
	 }
	
	/**
	 * 
	 * @param userList 用户列表
	 * @param wait 等待列表
	 * @param k k-匿名
	 * @param user 当前用户
	 * @return
	 */
	 public List<User> searchKnn(User user,int k,List<User> userList){
			List<User> candidate=new ArrayList<User>();
			List<User> kanonymityList=new ArrayList<User>();
			List<Area> kanonymityAreas=new ArrayList<>();
			List<User> topkList=new ArrayList<>();
			int count=0;
			for(int i=0;i<userList.size();i++){
				User helpuser=userList.get(i);
				if(user.getGridIdentifier()==helpuser.getGridIdentifier()){
					candidate.add(user);
				}
			}
			//System.out.println("candidate:"+candidate.size());
			if(candidate.size()<k){
				System.out.println("匿名失败");
				return null;
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
			kanonymityList=topkList;
			return kanonymityList;
		}
		
		
	public List<User> searchKnn2(User user,int k,List<User> poiList){
		List<User> candidate=new ArrayList<User>();
		List<User> userresult=new ArrayList<User>();
		List<User> topkList=new ArrayList<>();
		Iterator<User> iterator=poiList.iterator();
		while(iterator.hasNext()){
			User poi=iterator.next();	
			if(user.getUserID()!=poi.getUserID()){
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
		
		
	public List<User> createKAnonymity(List<User> userList,int wait,int k,User user){
		List<User> candidateList=new ArrayList<User>();
		List<User> kanonymityList=new ArrayList<User>();
		int count=0;
		for(User helpuser:userList){
			if(helpuser.getGridIdentifier()==user.getGridIdentifier()){
				candidateList.add(helpuser);
				count++;
			}
		}
		if(count>k-1){
			searchKnn(user, k-1, candidateList);
			return kanonymityList;	
		}else {
			while(count<k-1){
				User waitUser=new User(user.getX(), user.getY(),user.getQuerySpace()); 
				waitUser.setUserID(wait++);
				candidateList.add(waitUser);
				count++;
			}
			return candidateList;
		}	
	}
	
	public List<User> createKAnonymity2(List<User> userList,int wait,int k,int n,User user,Map<Integer, Object> gridMap){
		List<User> candidateList=new ArrayList<User>();
		List<User> kanonymityList=new ArrayList<User>();	
		Grid grid=(Grid) gridMap.get(user.getGridIdentifier());
		candidateList= grid.getUserList();
		int count=candidateList.size();
		if(count>k-1){
			kanonymityList=searchKnn(user, k-1, candidateList);
			return kanonymityList;	
		}else {
			while(count<k-1){
				User waitUser=new User(user.getX(), user.getY(),n,user.getQuerySpace()); 
				waitUser.setUserID(wait++);
				candidateList.add(waitUser);
				count++;
			}
			return candidateList;
		}	
	}
	
	
	public Map<String, Double> createAnonymityArea(List<User> userList){
		Map<String, Double> kanonymityArea=new HashMap<String, Double>();
		double maxx=0;
		double maxy=0;
		double minx=Double.MAX_VALUE;
		double miny=Double.MAX_VALUE;
		for(User user:userList){
			double x=user.getOffset().get("x");
			double y=user.getOffset().get("y");
			if(x<minx){
				minx=x;
			}
			if(x>maxx){
				maxx=x;
			}
			if(y<miny){
				miny=y;
			}
			if(y>maxy){
				maxy=y;
			}
		}
		kanonymityArea.put("minx", minx);
		kanonymityArea.put("maxx", maxx);
		kanonymityArea.put("miny", miny);
		kanonymityArea.put("maxy", maxy);
		return kanonymityArea;
	}

	private double getDistance(User user1,User user2){
		double x1=user1.getX();
		double x2=user2.getX();
		double y1=user1.getY();
		double y2=user2.getY();
		double distance=Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
		return distance;
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
	
	
	public List<User> filterResult(List<User> beforeresult,User user,int k){
		List<User> afterresult=new ArrayList<>();
		afterresult=searchKnn2(user, k, beforeresult);
		return afterresult;
	}
	

		
	}
	


