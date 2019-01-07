package Entity;

import java.util.List;
import java.util.Map;



public class Main {
	public static void main(String args[]){
		long sum=0;
		int n=128;
		for(int i=0;i<10;i++){
			dataProcess data=new dataProcess();
			QuerySpace querySpace=new QuerySpace(400,4300,21900,30800,n);
			List<User> userList=data.dataGen("src/now.txt",querySpace,n);
			Map<Integer, Object> gridMap=data.gridGen(querySpace, n, userList);
			LBS lbs=new LBS();
			Anonymizer anonymizer=new Anonymizer();
			long sumtime=0;
			int wait=userList.size();
			System.out.println("start");
			for(User user:userList){
				long time1=System.currentTimeMillis();
				anonymizer.createKAnonymity2(userList, wait, 50, user,gridMap);
				List<User> kanonymityList=anonymizer.createKAnonymity(userList, wait, 50, user);//����������k����
				long time2=System.currentTimeMillis();
				List<User> result=lbs.getSearchResult(kanonymityList, 3, userList);//lbs��ѯ���������poi
				long time3=System.currentTimeMillis();
				List<User> afterList=anonymizer.filterResult(result, user, 3);
				long time4=System.currentTimeMillis();
				long exetime=time2-time1+time4-time3;
				sumtime+=exetime;
			}
			sum+=sumtime;
			System.out.println("����������ʱ�䣺"+sumtime+" ms");
		}
		System.out.println("ƽ������ʱ�䣺"+sum/10+" ms");
		
		/*for(int i=0;i<10;i++){
			User user=userList.get(i);
			System.out.println("��������  x:"+user.getGridx()+" y:"+user.getGridy());
			lbs.decodeHilbert(n, user.getGridIdentifier(), user);
			System.out.println("��������  x:"+user.getGridx()+" y:"+user.getGridy());
			System.out.println("�����ʶ�� "+user.getGridIdentifier());
			System.out.println("ƫ�ƣ�"+user.getOffset());
			System.out.println("��Ԫ���С��"+querySpace.getXgrid()+","+querySpace.getYgrid());
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
