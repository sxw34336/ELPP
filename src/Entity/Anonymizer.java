package Entity;





import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
	
	
	
	public List<User> createKAnonymity(List<User> userList,int k,User user){
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
			candidateList.sort(user.comparator);
			for(int i=0;i<k-1;i++){
				kanonymityList.add(candidateList.get(i));
			}
			return kanonymityList;	
		}else {
			while(count<k-1){
				for(User secondsearch:userList){
					if(secondsearch.getGridIdentifier()==user.getGridIdentifier()){
						candidateList.add(secondsearch);
						count++;
					}
				}
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


}
