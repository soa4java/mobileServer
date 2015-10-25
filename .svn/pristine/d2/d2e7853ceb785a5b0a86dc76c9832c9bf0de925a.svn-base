package com.yuchengtech.mobile.server.web.utils;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;

public class PushIOS implements PushInfomation{

    public static void main(String[] args) throws Exception {
       
       try {
//           机器码   
    	   String deviceToken = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

              //被推送的iphone应用程序标示符      
              PayLoad payLoad = new PayLoad();
              payLoad.addAlert("测试我的push消息");
              payLoad.addBadge(1);
              payLoad.addSound("default");
                       
              PushNotificationManager pushManager = PushNotificationManager.getInstance();
              pushManager.addDevice("iphone", deviceToken);
             
           //Device c = pushManager.getDevice("iphone");
              String host= "gateway.sandbox.push.apple.com";  //测试用的苹果推送服务器,生产用的请修改此服务器

              int port = 2195;
              String certificatePath = "D:/aaa.p12"; //刚才在mac系统下导出的证书
              
              String certificatePassword= "841112";
             
              pushManager.initializeConnection(host, port, certificatePath,certificatePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);//初始化tcp连接，公司网络代理上网，不能连上外网的tcp连接，坑死人
                        
              //Send Push
              Device client = pushManager.getDevice("iphone");
              pushManager.sendNotification(client, payLoad); //推送消息
              pushManager.stopConnection();

              pushManager.removeDevice("iphone");
             }
             catch (Exception e) {
              e.printStackTrace();
             }
             System.out.println("push succeed!");
    }

	public void SendAllPush(String alert, String title)
			throws APIConnectionException, APIRequestException {
		// TODO Auto-generated method stub
		
	}

}