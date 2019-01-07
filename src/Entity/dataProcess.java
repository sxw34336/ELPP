package Entity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class dataProcess {

	
	
	
	public static List<User> dataGen(String url,QuerySpace querySpace,int n){
		BufferedReader bfr=null;
		try {
			bfr=new BufferedReader(new FileReader(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<User> pointList=new ArrayList<User>();
		String line;
		try {
			while ((line=bfr.readLine())!=null) {
				String[] content=line.split("	");
				User point=new User(Double.parseDouble(content[1]), Double.parseDouble(content[2]),n,querySpace);
/*				int poiClass=new Random().nextInt(4);*/
				point.setPoiClass(Integer.parseInt(content[3]));
				point.setUserID(Integer.parseInt(content[0]));
				pointList.add(point);	
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		return pointList;
	}
	
	public Map<Integer, Object> gridGen(QuerySpace querySpace,int n,List<User> userList){
		Map<Integer, Object> gridMap=new HashMap<>();
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				Grid grid=new Grid(j, i, n);
				List<User> inList=new ArrayList<>();
				for(User user:userList){
					if(user.getGridIdentifier()==grid.getGridIdentifier()){
						inList.add(user);
						
					}
				}
				grid.setUserList(inList);
				gridMap.put(grid.getGridIdentifier(), grid);
			}
		}
		return gridMap;
		
	}
	
	
	
	
}
