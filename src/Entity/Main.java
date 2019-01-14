package Entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;



public class Main {
	public static void main(String args[]) throws Exception{
		long sum=0;
		int n=256;
		for(int i=0;i<10;i++){
			dataProcess data=new dataProcess();
			QuerySpace querySpace=new QuerySpace(400,4300,21900,30800,n);
			List<User> userList=data.dataGen("src/now.txt",querySpace,n);
			Map<Integer, Object> gridMap=data.gridGen(querySpace, n, userList);
			LBS lbs=new LBS();
			Anonymizer anonymizer=new Anonymizer();
			long sumtime=0;
			int k=20;
			int wait=userList.size();
			System.out.println("start");
			PrintWriter pw=new PrintWriter(new File("src/out.txt"));
			for(User user:userList){
				Map<String, Object> userMSG=user.generateMSG();
				pw.write(userMSG.toString());
				long time1=System.currentTimeMillis();
				List<User> kanonymityList=anonymizer.createKAnonymity2(userList, wait, k, n,user,gridMap);//匿名器生成k匿名
				//Map<String, Double> kanonymityArea=anonymizer.createAnonymityArea(kanonymityList);
				long time2=System.currentTimeMillis();
				List<User> result=lbs.getSearchResult(kanonymityList, 2, userList);//lbs查询匿名区域的poi
				pw.write(result.toString());
				long time3=System.currentTimeMillis();
				List<User> afterList=anonymizer.filterResult(result, user, 2);
				pw.write(afterList.toString());
				long time4=System.currentTimeMillis();
				long exetime=time2-time1+time4-time3;
				sumtime+=exetime;
			}
			sum+=sumtime;
			//System.out.println("匿名器运行时间："+sumtime+" ms");
		}
		System.out.println("平均运行时间："+sum/10+" ms");
		
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
