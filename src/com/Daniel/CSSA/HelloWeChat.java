package com.Daniel.CSSA;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 import java.io.*;
import java.net.*;
//import org.json.*;

import com.Daniel.CSSA.Util.SignUtil;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;


import com.baidu.bae.api.util.BaeEnv;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Servlet implementation class HelloWeChat
 */
public class HelloWeChat extends HttpServlet {
    Logger logger = Logger. getLogger("HelloWeChat");
    
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWeChat() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
   
      
      
      String signature = request.getParameter("signature");  
        
        String timestamp = request.getParameter("timestamp");  
     
        String nonce = request.getParameter("nonce");  
       
       String echostr = request.getParameter("echostr");  
        PrintWriter out = response.getWriter();  
       

       if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
           out.print(echostr);  
       }  
       out.close();  
       out = null;  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	       //（1）指定服务地址，其中dbname需要自己修改
     //String dbUrl = "jdbc:mysql://sqld.duapp.com:4050/dbname";
     //（2）直接从请求header中获取ip、端口、用户名和密码信息
	//String host = request.getHeader("BAE_ENV_ADDR_SQL_IP");
	//String port = request.getHeader("BAE_ENV_ADDR_SQL_PORT");
	//String username = request.getHeader("BAE_ENV_AK");
	//String password = request.getHeader("BAE_ENV_SK");
    //（3）从线程变量BaeEnv接口获取ip、端口、用户名和密码信息
		
	
	String host = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_IP);
	String port = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_ADDR_SQL_PORT);
	String username = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_AK);
	String password = BaeEnv.getBaeHeader(BaeEnv.BAE_ENV_SK);
	String driverName = "com.mysql.jdbc.Driver";
	String dbUrl = "jdbc:mysql://";
	String serverName = host + ":" + port + "/";

     //从平台查询应用要使用的数据库名
	String databaseName = "FhyuGuhfGBhNDORhvdGB";
	String connName = dbUrl + serverName + databaseName;
	Connection connection = null;
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		String wxMsgXml = IOUtils.toString(request.getInputStream(),"utf-8");
		logger.log(Level.INFO, " get wechat message "+ wxMsgXml);
		String type = "text";
      
         StringBuffer replyMsg = new StringBuffer();
		String returnXml = "";
		WeChatTextMessage textMsg = null;
		WeChatEventMessage EventMsg = null;
		//text message
		String choice= "";
		
		boolean content = false;
		boolean LibFlag = false;
		boolean UserContentFlag = false;		
		boolean breaker= false;
		String Message = "";
      
      

   
    	boolean flager = false;
			
			
			textMsg = getWeChatTextMessage(wxMsgXml);
    
			
			Statement sts = null;
			ResultSet sets = null;
			
		try{		
			
				String StatusSql = "SELECT * FROM selection_menu2 WHERE userid = '"+textMsg.getFromUserName()+"'";
				Class.forName(driverName);
		                //具体的数据库操作逻辑
				connection = DriverManager.getConnection(connName, username,
						password);
				sts = connection.createStatement();
				sets = sts.executeQuery(StatusSql);

				//这里应该有个  if（rs.next()）  
				if(sets.next())
				{
			    
					
			    flager = true;

				}
	
			
		} catch (SQLException e) {
			logger.log(Level.INFO,e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.log(Level.INFO,e.getMessage());
		}

      //Creating menu option
      
      if(wxMsgXml.indexOf("<Content><![CDATA[创建菜单]]></Content>")>0)
      {
                  //此处改为自己想要的结构体，替换即可
     	//----------------------------------------
		// 获得ACCESS_TOKEN
      
    

            String appId = "wxb3d39e3729626373";   
      String appSecret = "f869f2f9db5610f283b4b6a445a97d36";
      String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appId + "&secret=" +appSecret;
     
      String accessToken = null;
      
       	logger.log(Level.INFO, "Before try");
     try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();    
             
            http.setRequestMethod("GET");      //必须是get方式请求    
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
            http.setDoOutput(true);        
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒

            http.connect();
         
            InputStream is =http.getInputStream();
            int size =is.available();
            byte[] jsonBytes =new byte[size];
            is.read(jsonBytes);
            String message=new String(jsonBytes,"UTF-8");
             
            JSONObject demoJson = new JSONObject(message);
            accessToken = demoJson.getString("access_token");
               	logger.log(Level.INFO, "ACCESS TOKEN "+accessToken);
       
         	logger.log(Level.INFO, "Trying to get access token.");

           
            System.out.println(message);
            } catch (Exception e) {
               	logger.log(Level.INFO, "error out");

				logger.log(Level.INFO,e.getMessage());
     
            }
  
   String user_define_menu = "{\"button\":[{\"name\":\"学在多大\",\"sub_button\":[,{\"type\":\"click\",\"name\":\"二手书\",\"key\":\"02_FINISH\"},{\"type\":\"click\",\"name\":\"图书馆时间\",\"key\":\"03_MYJOB\"}]},{\"type\":\"click\",\"name\":\"情系CSSA\",\"key\":\"30_ORGANIZATION\"},{\"name\":\"活动公告\",\"sub_button\":[{\"type\":\"click\",\"name\":\"春晚\",\"key\":\"01_WAITING\"},{\"type\":\"click\",\"name\":\"已办\",\"key\":\"02_FINISH\"},{\"type\":\"click\",\"name\":\"工单\",\"key\":\"03_MYJOB\"},{\"type\":\"click\",\"name\":\"公告\",\"key\":\"04_MESSAGEBOX\"},{\"type\":\"click\",\"name\":\"新生手册\",\"key\":\"05_SIGN\"}]}]}";  
		
      
          String access_token=  accessToken;

           String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
         
         //String action = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+access_token;
         
      try {
            URL Newurl = new URL(action);
            HttpURLConnection http = (HttpURLConnection) Newurl.openConnection();    
             
            http.setRequestMethod("POST");        
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
            http.setDoOutput(true);        
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒

            http.connect();
            OutputStream os= http.getOutputStream();    
            os.write(user_define_menu.getBytes("UTF-8"));//传入参数    
            os.flush();
            os.close();
         
            InputStream is =http.getInputStream();
            int size =is.available();
            byte[] jsonBytes =new byte[size];
            is.read(jsonBytes);
            String message=new String(jsonBytes,"UTF-8");
            System.out.println(message);
           } catch (MalformedURLException e) {
                logger.log(Level.INFO,e.getMessage());
            } catch (IOException e) {
               logger.log(Level.INFO,e.getMessage());
            }  

		
      
      // This is for system unavailable situations only:
      
 
      //end 
        
        
      }

      
      
      
      
      
      
      if(flager == true)
      {
      
		if(wxMsgXml.indexOf("<MsgType><![CDATA[text]]></MsgType>")>0) {
			type = "text";
		}
		else if(wxMsgXml.indexOf("<MsgType><![CDATA[location]]></MsgType>")>0) {
			type = "location";
		}
		else if(wxMsgXml.indexOf("<MsgType><![CDATA[voice]]></MsgType>")>0) {
			type ="voice";
		}
		else  if(wxMsgXml.indexOf("<Event><![CDATA[subscribe]]></Event>")>0){
			type = "sub";
		}

      
		if(wxMsgXml.indexOf("<Content><![CDATA[a]]></Content>")>0) {
			choice = "饭店";
		}
		else if(wxMsgXml.indexOf("<Content><![CDATA[同意]]></Content>")>0) {
			choice = "b";
		}
		else if(wxMsgXml.indexOf("<Content><![CDATA[b]]></Content>")>0) {
			choice = "图书馆";
		}
		else if(wxMsgXml.indexOf("<Content><![CDATA[c]]></Content>")>0) {
			choice = "test";
		}
		else if(wxMsgXml.indexOf("<Content><![CDATA[d]]></Content>")>0) {
			choice = "point";
		}
		else if(wxMsgXml.indexOf("<Content><![CDATA[X]]></Content>")>0) {
			choice = "改";
		}
	
		
		
		if(type == "sub")
		{
			
			logger.log(Level.INFO, "reply message "+ returnXml);
			
			try {
			
				EventMsg = getWeChatEventMessage(wxMsgXml);
				
			} catch (Exception e) {
									logger.log(Level.INFO, e.getMessage());
									}
			
									replyMsg.append("谢谢关注亲！如果您是第一次使用的话，请发个'同意'过来注册一下");

			
			returnXml = getReplyTextMessage(replyMsg.toString(), EventMsg.getFromUserName());
			
		}

	
		
		
		if("text".equalsIgnoreCase(type)){
			
			
			textMsg = getWeChatTextMessage(wxMsgXml);
          Message = textMsg.getContent(); 
			//检查是不是位置
			BaiduMapService baidu = new BaiduMapService();

			if(textMsg != null)
			
		{

				Statement st = null;
				ResultSet set = null;
				
			try{		
				
					String StatusSql = "SELECT * FROM selection_menu2 WHERE userid = '"+textMsg.getFromUserName()+"'";
					Class.forName(driverName);
			                //具体的数据库操作逻辑
					connection = DriverManager.getConnection(connName, username,
							password);
					st = connection.createStatement();
					set = st.executeQuery(StatusSql);

					//这里应该有个  if（rs.next()）  
					if(set.next())
					{
                      
                      logger.log(Level.INFO,"set is not empty");
					
				    content = true;

					}
					else if(choice != "b")
					{
						replyMsg.append("亲 你现在还不在我们的数据库里。。麻烦发个“同意”过来注册一下。");
						returnXml = getReplyTextMessage(replyMsg.toString(), textMsg.getFromUserName());
					}
				
			} catch (SQLException e) {
				logger.log(Level.INFO,e.getMessage());
			} catch (ClassNotFoundException e) {
				logger.log(Level.INFO,e.getMessage());
			}

			LibFlag = CheckFlag(textMsg.getFromUserName());
			UserContentFlag = Checkcontent(textMsg.getFromUserName());

			
if(LibFlag == true)
				{
		
				String phrase = Message;
				String delims = "/";
				String[] tokens = phrase.split(delims);
				ArrayList<String> LibInput = new ArrayList<String>();
				for (int i = 0; i < tokens.length; i++)
					LibInput.add(tokens[i]);
        	
		        if (UpdateLib(textMsg.getFromUserName(), LibInput))
		        {	
		        replyMsg.append("列表更新成功,请发送'b'查询  么么哒");	
		        returnXml = getReplyTextMessage(replyMsg.toString(), textMsg.getFromUserName());
		        }
		        else
		        {
		        replyMsg.append("由于程序猿的失误。更新失败。");	
			    returnXml = getReplyTextMessage(replyMsg.toString(), textMsg.getFromUserName());    	
		        
		        }

				 
				String  temper = ""; 
					logger.log(Level.INFO,String.valueOf(LibFlag));
		        
		}

			else if(choice == "改")
			 {
				 
				 LibFlag = true;
				    replyMsg.append("请添加您需要的图书馆吧~").append("\n");
				    replyMsg.append("格式:  1/2/3").append("\n\n");
					replyMsg.append("1 - Robarts Library").append("\n");
					replyMsg.append("2 - E.J.Pratt Library").append("\n");
					replyMsg.append("3 - Eng & ComSci Library").append("\n");
					replyMsg.append("4 - Gerstein Library").append("\n");
					replyMsg.append("5 - OISE Library").append("\n");
				 
				 returnXml = getReplyTextMessage(replyMsg.toString(), textMsg.getFromUserName());
				 
				 SetLibFlag(textMsg.getFromUserName());
				 
				 
			 
			 }
				
			
			else if(!LibFlag && (content||choice == "b"))
			{

			 if(choice == "饭店")
			 	{

//------------------------------------------Check if user location exist.

					 try {
					
						Statement stmt = null;
						ResultSet rs = null;
						String sql2 = "SELECT userlocation_x, userlocation_y  FROM selection_menu2 WHERE userid = '"+textMsg.getFromUserName()+"'";
						Class.forName(driverName);
					                //具体的数据库操作逻辑
							connection = DriverManager.getConnection(connName, username,
									password);
							stmt = connection.createStatement();
							rs = stmt.executeQuery(sql2);
						
						
							String X, Y;
							//这里应该有个  if（rs.next()）  
							if(rs.next())
							{
							X = rs.getString("userlocation_x");
							Y = rs.getString("userlocation_y");
								if(X != null && Y != null)
								{
						    	returnXml = getLocMsg(choice, X,Y,textMsg.getFromUserName());
								}
								else
								{
								replyMsg.append("亲先告诉我你在哪呗。。。");
								 returnXml = getReplyTextMessage(replyMsg.toString(), textMsg.getFromUserName());
								}
						    

							}
						    	
							// Call method 1, 2, 3. 

							} catch (SQLException e) {
								logger.log(Level.INFO,e.getMessage());
							} catch (ClassNotFoundException e) {
								logger.log(Level.INFO,e.getMessage());
							}

			}
			 
	
			 
			 else if(choice == "图书馆")
			 {
				 
					if (UserContentFlag == false)
					{
					  replyMsg.append("亲 你的列表是空的...请发送X添加你关注的图书馆吧~");
					  returnXml = getReplyTextMessage(replyMsg.toString(), textMsg.getFromUserName());

					}
					else
					{
				      returnXml = getLIB(textMsg.getFromUserName());
					}
			 }
			 else if(choice =="test")
				 
			 {
				 
	//------------------------------------------Check if user location exist.

						 try {
						
							Statement stmt = null;
							ResultSet rs = null;
							String sql2 = "SELECT userchoice  FROM selection_menu2 WHERE userid = '"+textMsg.getFromUserName()+"'";
							Class.forName(driverName);
						                //具体的数据库操作逻辑
								connection = DriverManager.getConnection(connName, username,
										password);
								stmt = connection.createStatement();
								rs = stmt.executeQuery(sql2);
							
							
								String X;
								//这里应该有个  if（rs.next()）  
								if(rs.next())
								{
								X = rs.getString("userchoice");
							
									
							    
									replyMsg.append("亲 今天的code是： ");
									replyMsg.append(X).append("\n");
									
									replyMsg.append(" 用餐愉快 么么哒 ");
									 returnXml = getReplyTextMessage(replyMsg.toString(), textMsg.getFromUserName());
								
							    

								}
							    	
								// Call method 1, 2, 3. 

								} catch (SQLException e) {
									logger.log(Level.INFO,e.getMessage());
								} catch (ClassNotFoundException e) {
									logger.log(Level.INFO,e.getMessage());
								}

				 
			 }
			 else if(choice =="point") 
			 {
			
               	logger.log(Level.INFO, "choice is : "+ choice);
               
				 String X = null ,Y =null;
	//------------------------------------------Check if user location exist.
				 returnXml = getLocMsg(choice, X,Y,textMsg.getFromUserName());

			 }
				 else if(choice == "b"){
					 try {
							
							Statement stmt = null;
							ResultSet rs = null;	
							String StatusSql = "SELECT * FROM selection_menu2 WHERE userid = '"+textMsg.getFromUserName()+"'";
							Class.forName(driverName);
						                //具体的数据库操作逻辑
								connection = DriverManager.getConnection(connName, username,
										password);
								stmt = connection.createStatement();
								rs = stmt.executeQuery(StatusSql);

								if(rs.next())
								{

									replyMsg.append("亲你已经注册过啦");
							
								}
								else
								{	
									Statement stmt2 = null;
							
									String InsertSql = "INSERT INTO selection_menu2 (userid) VALUES ('"+textMsg.getFromUserName()+"')";
									Class.forName(driverName);
								                //具体的数据库操作逻辑
										connection = DriverManager.getConnection(connName, username,
												password);
										stmt2 = connection.createStatement();
										stmt.executeUpdate(InsertSql);
									
									replyMsg.append("好滴亲，你注册成功了哟！");
									replyMsg.append("我们现在的 menu option:").append("\n");
									replyMsg.append("a - 饭店").append("\n");
									replyMsg.append("b - 查询图书馆时间").append("\n");
									replyMsg.append("c - 索要邀请码").append("\n");
									replyMsg.append("d - 点数查询").append("\n");
									replyMsg.append("X - 修改图书馆列表").append("\n");
									
								}

								
								} catch (SQLException e) {
									logger.log(Level.INFO,e.getMessage());
								} catch (ClassNotFoundException e) {
									logger.log(Level.INFO,e.getMessage());
								}

					 returnXml = getReplyTextMessage(replyMsg.toString(), textMsg.getFromUserName());

				 }
				 else
				 {
						replyMsg.append("不要乱发。。。..").append("\n");
						replyMsg.append("我现在只能看懂 ：").append("\n");
						replyMsg.append("a - 饭店").append("\n");
						replyMsg.append("b - 图书馆(test)").append("\n");
						replyMsg.append("c - 索要邀请码").append("\n");
						replyMsg.append("d - 点数查询").append("\n");
						replyMsg.append("X - 修改图书馆列表").append("\n");
						
						 returnXml = getReplyTextMessage(replyMsg.toString(), textMsg.getFromUserName());
				 }
			}
		}
	}
			
		//location message
		else if("location".equalsIgnoreCase(type)){

logger.log(Level.INFO, "Location entered ");
//------------------------------------------Check if user location exist.

			WeChatLocationMessage localMsg = WeChatLocationMessage.getWeChatLocationMessage(wxMsgXml);
			//returnXml = getLocMsg("", localMsg.getLocationx(),localMsg.getLocaltiony(),localMsg.getFromUserName());
		 try {
		
			Statement stmt = null;
			
			int flag = 0;
			String UpdateSql = "UPDATE selection_menu2 SET userlocation_x = '"+localMsg.getLocationx()+"', userlocation_y = '"+localMsg.getLocaltiony()+"' WHERE userid = '"+localMsg.getFromUserName()+"'";
			
			
			Class.forName(driverName);
		                //具体的数据库操作逻辑
				connection = DriverManager.getConnection(connName, username,
						password);
				stmt = connection.createStatement();
				stmt.executeUpdate(UpdateSql);
				flag = stmt.executeUpdate(UpdateSql);

				replyMsg.append("okay 我知道你在哪里啦，想要找什么？");
				
				returnXml = getReplyTextMessage(replyMsg.toString(), localMsg.getFromUserName());

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					
					logger.log(Level.INFO,e.getMessage());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					logger.log(Level.INFO,e.getMessage());
				}

		 
			
		}
	
    }
	else
	{
	 replyMsg.append("为了给您更好的服务,CSSA 微信平台未来几天将进行升级\n" );
          replyMsg.append("自动回复功能将暂时无法使用  给您造成的不便敬请原谅");
				
	  	returnXml = getReplyTextMessage(replyMsg.toString(), textMsg.getFromUserName());


	}
				
		logger.log(Level.INFO, "reply message "+ returnXml);
		pw.println(returnXml);
	}
	
	private boolean CheckFlag(String userName){
		
		boolean successful = false;
			try {

				successful = WeChatReplyNewsMessage.CheckFlagLib(userName);
			} catch (Exception e) {
				logger.log(Level.INFO, e.getMessage());
			}

		return successful;
	}
	

	private boolean Checkcontent(String userName){
		
		boolean successful = false;
			try {

				successful = WeChatReplyNewsMessage.CheckUserContent(userName);
			} catch (Exception e) {
				logger.log(Level.INFO, e.getMessage());
			}

		return successful;
	}

	private String getLIB(String userName){
		String returnXml = "";

			try {

				returnXml =WeChatReplyNewsMessage.GetLibChoice(userName,WeChatConstant.ARTICLE_SIZE);
			} catch (Exception e) {
				logger.log(Level.INFO, e.getMessage());
			}

		return returnXml;
	}
	
	private boolean SetLibFlag(String userName){
		
		boolean successful = false;
			try {

				successful = WeChatReplyNewsMessage.SetLibFlag(userName);
			} catch (Exception e) {
				logger.log(Level.INFO, e.getMessage());
			}

		return successful;
	}
	

	
	private boolean UpdateLib(String userName, ArrayList<String> NEWlIST){
	
		boolean successful = false;
			try {

				successful = WeChatReplyNewsMessage.UpdateLibChoice(userName,NEWlIST);
			} catch (Exception e) {
				logger.log(Level.INFO, e.getMessage());
			}

		return successful;
	}

		
	private String getLocMsg(String target, String lat,String lng,String userName){
		String returnXml = "";
		//StringBuffer replyMsg = new StringBuffer();
		BaiduMapService baidu = new BaiduMapService();
		String respXml;
		
      logger.log(Level.INFO,"Target is "+target);
      
		if(target == "test")
		{
			try {
					respXml = baidu.getPalace(target, lat, lng);
					List<BaiduPlaceResponse> list = BaiduPlaceResponse.getBaiduPlace(respXml);
					returnXml =WeChatReplyNewsMessage.getWeChatReplyNewsMessageByBaiduPlace(list,Double.valueOf(lat),Double.valueOf(lng),userName,WeChatConstant.ARTICLE_SIZE);
				} catch (Exception e) {
										logger.log(Level.INFO, e.getMessage());
										}
		}
		else if(target == "point")
		{
			try {
					
					returnXml =WeChatReplyNewsMessage.getWeChatReplyNewsMessageByBaiduPlace3(userName,WeChatConstant.ARTICLE_SIZE);
				} catch (Exception e) {
										logger.log(Level.INFO, e.getMessage());
										}
		}
		else
		{
			try {
					respXml = baidu.getPalace(target, lat, lng);
					List<BaiduPlaceResponse> list = BaiduPlaceResponse.getBaiduPlace(respXml);
					returnXml =WeChatReplyNewsMessage.getWeChatReplyNewsMessageByBaiduPlace(list,Double.valueOf(lat),Double.valueOf(lng),userName,WeChatConstant.ARTICLE_SIZE);
				} catch (Exception e) {
										logger.log(Level.INFO, e.getMessage());
										}
		}
		
		return returnXml;
	}
	

	private WeChatTextMessage getWeChatTextMessage(String xml){
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("xml", WeChatTextMessage.class);
		xstream.aliasField("ToUserName", WeChatTextMessage.class, "toUserName");
		xstream.aliasField("FromUserName", WeChatTextMessage.class, "fromUserName");
		xstream.aliasField("CreateTime", WeChatTextMessage.class, "createTime");
		xstream.aliasField("MsgType", WeChatTextMessage.class, "messageType");
		xstream.aliasField("Content", WeChatTextMessage.class, "content");
		xstream.aliasField("MsgId", WeChatTextMessage.class, "msgId");
		WeChatTextMessage wechatTextMessage = (WeChatTextMessage)xstream.fromXML(xml); 
		return wechatTextMessage;
	}
	
	private WeChatEventMessage getWeChatEventMessage(String xml){
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("xml", WeChatEventMessage.class);
		xstream.aliasField("ToUserName", WeChatEventMessage.class, "toUserName");
		xstream.aliasField("FromUserName", WeChatEventMessage.class, "fromUserName");
		xstream.aliasField("CreateTime", WeChatEventMessage.class, "Time");
		xstream.aliasField("MsgType", WeChatEventMessage.class, "Msg");
		xstream.aliasField("Event", WeChatEventMessage.class, "event");
		xstream.aliasField("EventKey", WeChatEventMessage.class, "Key");
		WeChatEventMessage wechatEventMessage = (WeChatEventMessage)xstream.fromXML(xml); 
		return wechatEventMessage;
	}


	private String getReplyTextMessage(String content, String weChatUser){
		WeChatReplyTextMessage we = new WeChatReplyTextMessage();
		we.setMessageType("text");
		we.setFuncFlag("0");
		we.setCreateTime(new Long(new Date().getTime()).toString());
		we.setContent(content);
		we.setToUserName(weChatUser);
		we.setFromUserName(WeChatConstant.FROMUSERNAME);
		XStream xstream = new XStream(new DomDriver()); 
		xstream.alias("xml", WeChatReplyTextMessage.class);
		xstream.aliasField("ToUserName", WeChatReplyTextMessage.class, "toUserName");
		xstream.aliasField("FromUserName", WeChatReplyTextMessage.class, "fromUserName");
		xstream.aliasField("CreateTime", WeChatReplyTextMessage.class, "createTime");
		xstream.aliasField("MsgType", WeChatReplyTextMessage.class, "messageType");
		xstream.aliasField("Content", WeChatReplyTextMessage.class, "content");
		xstream.aliasField("FuncFlag", WeChatReplyTextMessage.class, "funcFlag");
		String xml =xstream.toXML(we);
		return xml;
	}
	
	

}
