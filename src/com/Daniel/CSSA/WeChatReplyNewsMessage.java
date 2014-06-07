package com.Daniel.CSSA;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.Daniel.CSSA.GeoUtil.GaussSphere;
import com.baidu.bae.api.util.BaeEnv;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class WeChatReplyNewsMessage {

	private static Logger logger = Logger. getLogger("WeChatReplyNewsMessage");
	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String msgType;
	private int articleCount;
	private List<Item> items;
	private String funcFlag;
	static Date date = new Date();
	
	
	public static String getWeChatReplyNewsMessageByBaiduPlace(List<BaiduPlaceResponse> placeList, double lat, double lng,String userName, int size){

		WeChatReplyNewsMessage newsMessage = new WeChatReplyNewsMessage();
		List<Item> items = new ArrayList<Item>();
		StringBuffer strBuf = new StringBuffer();
		logger.log(Level.INFO,"placeList count="+placeList.size());
		newsMessage.setItems(items);
		if(placeList.size()>size){
			newsMessage.setArticleCount(size);
		}
		else{
			newsMessage.setArticleCount(placeList.size());
		}
		logger.log(Level.INFO,"article count="+newsMessage.getArticleCount());
		newsMessage.setCreateTime(new Date().getTime()+"");
		newsMessage.setMsgType("news");
		newsMessage.setFuncFlag("0");
		newsMessage.setToUserName(userName);
		newsMessage.setFromUserName(WeChatConstant.FROMUSERNAME);
		for(int i = 0;i <newsMessage.getArticleCount();i++){
			BaiduPlaceResponse place = placeList.get(i);
			Double distance = GeoUtil.DistanceOfTwoPoints(Double.valueOf(place.getLng()), Double.valueOf(place.getLat()), lng, lat, GaussSphere.Beijing54);
			Item item = new Item();
          
         if(i ==1 ||i == 3)
         {
         item.setTitle(place.getName()+"\ue057"+"["+distance+"米]"+"\n"+place.getAddress()+"\n"+place.getTelephone());
         }
         else
         {
         item.setTitle(place.getName()+"["+distance+"米]"+"\n"+place.getAddress()+"\n"+place.getTelephone());
         }
			
			item.setPicUrl("");
			item.setUrl(place.getDetailUrl());
			item.setDescription("");
			items.add(item);
		}
		logger.log(Level.INFO,"newMessage="+newsMessage.toString());
		strBuf = strBuf.append(getWeChatNewsMessage(newsMessage));
		
		return strBuf.toString();
	}
	
	
	public static String getWeChatReplyNewsMessageByBaiduPlace2(List<BaiduPlaceResponse> placeList, double lat, double lng,String userName, int size){
		Statement st = null;
		ResultSet set = null;
		String host = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_IP);
		String port = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_PORT);
		String username = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_AK);
		String password = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_SK);
		String driverName = "com.mysql.jdbc.Driver";
		String dbUrl = "jdbc:mysql://";
		String serverName = host + ":" + port + "/";
		String Name = null, Start =null, End = null, Address = null;
		String databaseName = "FhyuGuhfGBhNDORhvdGB";
		String connName = dbUrl + serverName + databaseName;
		Connection connection = null;
	try{					
		String StatusSql = "SELECT * FROM LIBRARY WHERE lib_name = 'Robarts Library'";
		Class.forName(driverName);
	                //具体的数据库操作逻辑
			connection = DriverManager.getConnection(connName, username,
					password);
			st = connection.createStatement();
			set = st.executeQuery(StatusSql);
			
			if(set.next())
			{
			//Name = set.getString("lib_name");
			Start = set.getString("start_time");
			End = set.getString("end_time");
			Address = set.getString("address");
			}
			
			
			
			
	} catch (SQLException e) {
		logger.log(Level.INFO,e.getMessage());
	} catch (ClassNotFoundException e) {
		logger.log(Level.INFO,e.getMessage());
	}
		
	WeChatReplyNewsMessage newsMessage = new WeChatReplyNewsMessage();
	List<Item> items = new ArrayList<Item>();
	StringBuffer strBuf = new StringBuffer();
	logger.log(Level.INFO,"placeList count="+placeList.size());
	newsMessage.setItems(items);
	newsMessage.setArticleCount(1);
	logger.log(Level.INFO,"article count="+newsMessage.getArticleCount());
	newsMessage.setCreateTime(new Date().getTime()+"");
	newsMessage.setMsgType("news");
	newsMessage.setFuncFlag("0");
	newsMessage.setToUserName(userName);
	newsMessage.setFromUserName(WeChatConstant.FROMUSERNAME);
	for(int i = 0;i <newsMessage.getArticleCount();i++){
		Item item = new Item();
		item.setTitle(Name+"\n"+Address+"\n"+"Today "+"\n"+" Open at: "+Start+"\n"+"Close at: "+End+"\n");
		item.setPicUrl("");
		//item.setUrl(place.getDetailUrl());
		item.setUrl("http://resource.library.utoronto.ca/hours/month.cfm?library_id=109");
		item.setDescription("");
		items.add(item);
	}
	logger.log(Level.INFO,"newMessage="+newsMessage.toString());
	strBuf = strBuf.append(getWeChatNewsMessage(newsMessage));
	return strBuf.toString();
	}
	
	
	public static boolean UpdateLibChoice(String userName, ArrayList<String> NewList)
	{
			Statement st = null;
			boolean set = false;		
			String host = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_IP);
			String port = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_PORT);
			String username = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_AK);
			String password = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_SK);
			String driverName = "com.mysql.jdbc.Driver";
			String dbUrl = "jdbc:mysql://";
			String serverName = host + ":" + port + "/";
			String databaseName = "FhyuGuhfGBhNDORhvdGB";
			String connName = dbUrl + serverName + databaseName;
			Connection connection = null;
		
		
			
			
			try{				

			Class.forName(driverName);
		                //具体的数据库操作逻辑
				connection = DriverManager.getConnection(connName, username,
						password);
				st = connection.createStatement();
				
				String DeleteLibSql = "DELETE FROM LibChoice WHERE userName = '"+userName+"'";
				st.executeUpdate(DeleteLibSql);
				
		} catch (SQLException e) {
			logger.log(Level.INFO,e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.log(Level.INFO,e.getMessage());
		}
			
			
			for(int i = 0; i<NewList.size();i++)
			{	
			try{				
		
			//INSERT INTO 'LibChoice'(lib_name, start_time, end_time, address, phone, comment) VALUES ('Academic Success Centre, Koffler Centre', '9:00am','5:00pm','214 College St.','4169781033', 'no')
			String UpdteLibSql = "INSERT INTO LibChoice(LibName, userName) VALUES ('"+NewList.get(i)+"', '"+userName+"') ";
				 st.executeUpdate(UpdteLibSql);
				set = true;

		} catch (SQLException e) {
			logger.log(Level.INFO,e.getMessage());
		}
			
	} // This is for the FOR LOOP
		
		return set;
	}
	
	
	
	public static boolean SetLibFlag(String userName)
	{
			Statement st = null;
			boolean set = false;		
			String host = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_IP);
			String port = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_PORT);
			String username = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_AK);
			String password = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_SK);
			String driverName = "com.mysql.jdbc.Driver";
			String dbUrl = "jdbc:mysql://";
			String serverName = host + ":" + port + "/";
			String databaseName = "FhyuGuhfGBhNDORhvdGB";
			String connName = dbUrl + serverName + databaseName;
			Connection connection = null;
		
			try{				
		
			//INSERT INTO 'LibChoice'(lib_name, start_time, end_time, address, phone, comment) VALUES ('Academic Success Centre, Koffler Centre', '9:00am','5:00pm','214 College St.','4169781033', 'no')
			String UpdteLibSql = "INSERT INTO UserFlag (UserName, LibFlag) VALUES ('"+userName+"', '1') ";
			Class.forName(driverName);
		                //具体的数据库操作逻辑
				connection = DriverManager.getConnection(connName, username,
						password);
				st = connection.createStatement();
				 set = st.execute(UpdteLibSql);
				

		} catch (SQLException e) {
			logger.log(Level.INFO,e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.log(Level.INFO,e.getMessage());
		}

		
		
		return set;
	}
	
	
	public static boolean CheckFlagLib(String userName)
	{
			Statement st = null;
			ResultSet set;		
			boolean flag = false;
			String host = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_IP);
			String port = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_PORT);
			String username = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_AK);
			String password = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_SK);
			String driverName = "com.mysql.jdbc.Driver";
			String dbUrl = "jdbc:mysql://";
			String serverName = host + ":" + port + "/";
			String databaseName = "FhyuGuhfGBhNDORhvdGB";
			String connName = dbUrl + serverName + databaseName;
			Connection connection = null;
		
			try{				
		
			//INSERT INTO 'LibChoice'(lib_name, start_time, end_time, address, phone, comment) VALUES ('Academic Success Centre, Koffler Centre', '9:00am','5:00pm','214 College St.','4169781033', 'no')
			String UpdteLibSql = "select LibFlag from  UserFlag WHERE userName = '"+userName+"'";
			Class.forName(driverName);
		                //具体的数据库操作逻辑
				connection = DriverManager.getConnection(connName, username,
						password);
				st = connection.createStatement();
				 set = st.executeQuery(UpdteLibSql);
				 int FlagResult = 0;
				if(set.next())
				{
					//Name = set.getString("lib_name");
					FlagResult = set.getInt("LibFlag");

				}
				if(FlagResult == 1)
				{
					flag = true;
				}
				else
				{
					flag = false;
				}
			//	logger.log(Level.INFO,);
					
				String DeleteLibSql = "DELETE FROM UserFlag WHERE userName = '"+userName+"'";
				st.executeUpdate(DeleteLibSql);

		} catch (SQLException e) {
			logger.log(Level.INFO,e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.log(Level.INFO,e.getMessage());
		}
	return flag;
	}
	
	
	public static boolean CheckUserContent(String userName)
	{
			Statement st = null;
			ResultSet set;		
			boolean flag = false;
			String host = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_IP);
			String port = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_PORT);
			String username = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_AK);
			String password = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_SK);
			String driverName = "com.mysql.jdbc.Driver";
			String dbUrl = "jdbc:mysql://";
			String serverName = host + ":" + port + "/";
			String databaseName = "FhyuGuhfGBhNDORhvdGB";
			String connName = dbUrl + serverName + databaseName;
			Connection connection = null;
		
			try{				
		
			//INSERT INTO 'LibChoice'(lib_name, start_time, end_time, address, phone, comment) VALUES ('Academic Success Centre, Koffler Centre', '9:00am','5:00pm','214 College St.','4169781033', 'no')
			String UpdteLibSql = "SELECT LibName FROM LibChoice WHERE userName = '"+userName+"'";
			Class.forName(driverName);
		                //具体的数据库操作逻辑
				connection = DriverManager.getConnection(connName, username,
						password);
				st = connection.createStatement();
				 set = st.executeQuery(UpdteLibSql);
				 int FlagResult = 0;
				if(set.next())
				{
					//Name = set.getString("lib_name");
					FlagResult = set.getInt("libName");

				}
				if(FlagResult == 1)
				{
					flag = true;
				}
				else
				{
					flag = false;
				}

		} catch (SQLException e) {
			logger.log(Level.INFO,e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.log(Level.INFO,e.getMessage());
		}
	return flag;
	}
	
	
	
	
	
	
	
	
	
	public static String GetLibChoice(String userName, int size){
		
		
		Statement st = null;
		ResultSet set = null;
		Statement LibCon = null;
		ResultSet LibSet = null;
		
		String host = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_IP);
		String port = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_PORT);
		String username = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_AK);
		String password = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_SK);
		String driverName = "com.mysql.jdbc.Driver";
		String dbUrl = "jdbc:mysql://";
		String serverName = host + ":" + port + "/";
		String Name = null, Start =null, End = null, Extend = null, Url = null;
		List<String> LibList = new ArrayList<String>();
		String databaseName = "FhyuGuhfGBhNDORhvdGB";
		String connName = dbUrl + serverName + databaseName;
		Connection connection = null;
	try{					
		String StatusSql = "SELECT LibName FROM LibChoice WHERE userName = '"+userName+"'";
		Class.forName(driverName);
	                //具体的数据库操作逻辑
			connection = DriverManager.getConnection(connName, username,password);
			st = connection.createStatement();
			set = st.executeQuery(StatusSql);
			
			while(set.next())
			{
			Name = set.getString("libName");
			LibList.add(Name);
			}
			
	} catch (SQLException e) {
		logger.log(Level.INFO,e.getMessage());
	} catch (ClassNotFoundException e) {
		logger.log(Level.INFO,e.getMessage());
	}
	
	 Calendar cal = Calendar.getInstance(Locale.US);
	 cal.setTime(new Date());
	
		WeChatReplyNewsMessage newsMessage = new WeChatReplyNewsMessage();
		List<Item> items = new ArrayList<Item>();
		StringBuffer strBuf = new StringBuffer();

		newsMessage.setItems(items);
		newsMessage.setArticleCount(LibList.size());
		logger.log(Level.INFO,"article count="+newsMessage.getArticleCount());
		newsMessage.setCreateTime(new Date().getTime()+"");
		newsMessage.setMsgType("news");
		newsMessage.setFuncFlag("0");
		newsMessage.setToUserName(userName);
		newsMessage.setFromUserName(WeChatConstant.FROMUSERNAME);


		
		int i = 0;
		
		int tempDate = cal.get(Calendar.DAY_OF_WEEK)-1;
		while(LibList.isEmpty() == false)
		{
		
	 try{					
		 //String LibSql = "SELECT * FROM LibraryTable WHERE lib_name = '"++"' and DAYS = "+cal.get(Calendar.DAY_OF_WEEK);
		 String LibSql = "select * from LibraryTable where Lib_Num = '"+LibList.get(i)+"' and DAYS like '%"+tempDate+"%'";
		// String LibSql = "SELECT * FROM  `LibraryTable` WHERE DAYS LIKE  '%"+cal.get(Calendar.DAY_OF_WEEK)+"%' ";
			logger.log(Level.INFO,LibSql);		
		 Class.forName(driverName);
		                //具体的数据库操作逻辑v
				connection = DriverManager.getConnection(connName, username,
						password);
				LibCon = connection.createStatement();
				LibSet = LibCon.executeQuery(LibSql);
				
				if(LibSet.next())
				{
				Name = LibSet.getString("lib_name");
				Start = LibSet.getString("start_time");
				End = LibSet.getString("end_time");
				Extend = LibSet.getString("Extend");
				Url = LibSet.getString("comment");
				//logger.log(Level.INFO,"the following are:"+Name+" "+Start+" "+End);
				
				Item item = new Item();
				item.setTitle("您的列表：\n"+Name+"\n"+date+"\n"+"Open at: "+Start+"\n"+"Close at: "+End+"\n"+"Extend:"+Extend);
				item.setDescription("");
				item.setPicUrl("");
				//item.setUrl(place.getDetailUrl());
				item.setUrl(Url);
				
				items.add(item);
				}
	
				LibList.remove(i);	

		} catch (SQLException e) {
			logger.log(Level.INFO,e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.log(Level.INFO,e.getMessage());
		}
	
	  
} /// this is for the while loop 
	
	
	logger.log(Level.INFO,"newMessage="+newsMessage.toString());
	strBuf = strBuf.append(getWeChatNewsMessage(newsMessage));
	return strBuf.toString();
	
	}	
	public static String getWeChatReplyNewsMessageByBaiduPlace3(String userName, int size){
		Statement st = null;
		ResultSet set = null;
		String host = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_IP);
		String port = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_PORT);
		String username = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_AK);
		String password = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_SK);
		String driverName = "com.mysql.jdbc.Driver";
		String dbUrl = "jdbc:mysql://";
		String serverName = host + ":" + port + "/";

	     //��ƽ̨��ѯӦ��Ҫʹ�õ���ݿ���
		String databaseName = "MuhrRBNBzcVUhLATDnRx";
		String connName = dbUrl + serverName + databaseName;
		Connection connection = null;
		String choice = "fail";
		int point = 1;	
		
	WeChatReplyNewsMessage newsMessage = new WeChatReplyNewsMessage();
	List<Item> items = new ArrayList<Item>();
	StringBuffer strBuf = new StringBuffer();
	newsMessage.setItems(items);

	newsMessage.setArticleCount(1);
	logger.log(Level.INFO,"article count="+newsMessage.getArticleCount());
	newsMessage.setCreateTime(new Date().getTime()+"");
	newsMessage.setMsgType("news");
	newsMessage.setFuncFlag("0");
	newsMessage.setToUserName(userName);
	newsMessage.setFromUserName(WeChatConstant.FROMUSERNAME);
	for(int i = 0;i <newsMessage.getArticleCount();i++){
		//BaiduPlaceResponse place = placeList.get(i);
		//Double distance = GeoUtil.DistanceOfTwoPoints(Double.valueOf(place.getLng()), Double.valueOf(place.getLat()), lng, lat, GaussSphere.Beijing54);
		Item item = new Item();
		item.setTitle("您目前一共获得了 "+point+" 枚图章 "+"\n"+"再收集 4 枚就可以获得我们"+"\n"+"的免费奶茶哦");
		item.setPicUrl("http://www.geekpics.net/images/2013/09/15/DS1r9X4k.png");
		//item.setUrl(place.getDetailUrl());
		item.setUrl("");
		item.setDescription("stamp card");
		items.add(item);
	}
	logger.log(Level.INFO,"newMessage="+newsMessage.toString());
	strBuf = strBuf.append(getWeChatNewsMessage(newsMessage));
	
	return strBuf.toString();
	
	

	}
	
	
	
	
	
	
	public static String getNewMessage( String userName,int point){

	WeChatReplyNewsMessage newsMessage = new WeChatReplyNewsMessage();
	List<Item> items = new ArrayList<Item>();
	StringBuffer strBuf = new StringBuffer();
	//logger.log(Level.INFO,"placeList count="+placeList.size());
	newsMessage.setItems(items);
//	if(placeList.size()>size){
//		newsMessage.setArticleCount(size);
//	}
//	else{
//		newsMessage.setArticleCount(placeList.size());
//	}
	newsMessage.setArticleCount(1);
	logger.log(Level.INFO,"article count="+newsMessage.getArticleCount());
	newsMessage.setCreateTime(new Date().getTime()+"");
	newsMessage.setMsgType("news");
	newsMessage.setFuncFlag("0");
	newsMessage.setToUserName(userName);
	newsMessage.setFromUserName(WeChatConstant.FROMUSERNAME);
	for(int i = 0;i <newsMessage.getArticleCount();i++){
		//BaiduPlaceResponse place = placeList.get(i);
		//Double distance = GeoUtil.DistanceOfTwoPoints(Double.valueOf(place.getLng()), Double.valueOf(place.getLat()), lng, lat, GaussSphere.Beijing54);
		Item item = new Item();
		item.setTitle("");
		item.setPicUrl("");
		//item.setUrl(place.getDetailUrl());
		item.setUrl("http://www.geekpics.net/Ffw");
		item.setDescription("");
		items.add(item);
	}
	logger.log(Level.INFO,"newMessage="+newsMessage.toString());
	strBuf = strBuf.append(getWeChatNewsMessage(newsMessage));
	
	return strBuf.toString();
	
	

	}
	
	
	public static String getWeChatNewsMessage(WeChatReplyNewsMessage newsMessage){
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("xml", WeChatReplyNewsMessage.class);
		xstream.aliasField("ToUserName", WeChatReplyNewsMessage.class, "toUserName");
		xstream.aliasField("FromUserName", WeChatReplyNewsMessage.class, "fromUserName");
		xstream.aliasField("CreateTime", WeChatReplyNewsMessage.class, "createTime");
		xstream.aliasField("MsgType", WeChatReplyNewsMessage.class, "msgType");
		xstream.aliasField("ArticleCount", WeChatReplyNewsMessage.class, "articleCount");
		xstream.aliasField("Content", WeChatReplyNewsMessage.class, "content");
		xstream.aliasField("FuncFlag", WeChatReplyNewsMessage.class, "funcFlag");
		xstream.aliasField("Articles", WeChatReplyNewsMessage.class, "items");
		
		xstream.alias("item", Item.class);
		xstream.aliasField("Title", Item.class, "title");
		xstream.aliasField("Description", Item.class, "description");
		xstream.aliasField("PicUrl", Item.class, "picUrl");
		xstream.aliasField("Url", Item.class, "url");
		
		return xstream.toXML(newsMessage);
	}
	
	
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public int getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public String getFuncFlag() {
		return funcFlag;
	}
	public void setFuncFlag(String funcFlag) {
		this.funcFlag = funcFlag;
	}

	@Override
	public String toString() {
		return "WeChatReplyNewsMessage [toUserName=" + toUserName
				+ ", fromUserName=" + fromUserName + ", createTime="
				+ createTime + ", msgType=" + msgType + ", articleCount="
				+ articleCount + ", items=" + items + ", funcFlag=" + funcFlag
				+ "]";
	}
}

class Item{
	private String title;
	private String description;
	private String picUrl;
	private String url;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
