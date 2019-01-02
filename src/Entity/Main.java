package Entity;

import java.util.List;
import java.util.Map;



public class Main {
	public static void main(String args[]){
		int n=128;
		dataProcess data=new dataProcess();
		QuerySpace querySpace=new QuerySpace(400,4300,21900,30800,n);
		List<User> userList=data.dataGen("src/now.txt",querySpace,n);	
		LBS lbs=new LBS();
		Anonymizer anonymizer=new Anonymizer();
		User user=new User(800, 4400, n, querySpace);
		System.out.println(user.getGridx()+","+user.getGridy());
		lbs.decodeHilbert(n, user.getGridIdentifier(), user);
		System.out.println(user.getGridIdentifier());
		System.out.println(user.getGridx()+","+user.getGridy());
		
		/*for(int i=0;i<10;i++){
			User user=userList.get(i);
			System.out.println("网格坐标  x:"+user.getGridx()+" y:"+user.getGridy());
			lbs.decodeHilbert(n, user.getGridIdentifier(), user);
			System.out.println("网格坐标  x:"+user.getGridx()+" y:"+user.getGridy());
			System.out.println("网格标识： "+user.getGridIdentifier());
			System.out.println("偏移："+user.getOffset());
			System.out.println("单元格大小："+querySpace.getXgrid()+","+querySpace.getYgrid());
			System.out.println();
			
		}*/
		
		
		
		
		
		/*for(User user:userList){
			user.generateMSG();
			List<User> anonymityUsers=anonymizer.createKAnonymity(userList, 10, user);
			Map<String, Double> areaMap=anonymizer.createAnonymityArea(anonymityUsers);
		}
		*/
	}
}
