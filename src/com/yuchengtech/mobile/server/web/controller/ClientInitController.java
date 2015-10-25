package com.yuchengtech.mobile.server.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yuchengtech.mobile.console.entity.ver.Apk;
import com.yuchengtech.mobile.console.entity.ver.WebResource;
import com.yuchengtech.mobile.console.entity.ver.WebResourceIncr;
import com.yuchengtech.mobile.console.service.ver.ApkManager;
import com.yuchengtech.mobile.console.service.ver.WebResourceManager;
import com.yuchengtech.mobile.server.common.Constants;
import com.yuchengtech.mobile.server.security.auth.AuthManager;
import com.yuchengtech.mobile.server.security.auth.ClientInfo;
import com.yuchengtech.mobile.server.web.utils.ThreeDesUtil;
import com.yuchengtech.mobile.server.web.view.JsonView;
import com.yuchengtech.mobile.server.web.view.View;
 /**
 * 客户端启动过程安全检测
 * @author Administrator
 *
 */
public class ClientInitController extends AbstractController {
	private static final Logger log = LoggerFactory
			.getLogger(ClientInitController.class);
	
	public View execute(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		Object reultData = null;
		Map<String, String> values = getReqValues();
		String did = values.get("d");// 设备id，第一步，第二步
		String p = values.get("p");//android,ios
		String random = values.get("r");// md5后的随机数，第一步：2遍md5。第二步，1遍md5
		String chanel = values.get("c");//渠道码，第二步
		String encryptMsg = values.get("e");// 第三步骤传递,客户端完整性校验,加密后字符串：资源文件hash值+签名信息（ios：bundle id，android：签名证书信息）+设备id+版本号
		if (chanel == null&&encryptMsg==null) {
			// 第一回合，服务端记录该随机数
			reultData = sayHello(did, random);
		} else if (chanel != null&&encryptMsg==null) {
			// 第二回合，
			reultData = shakeHand(did, random, chanel);
		}else if (encryptMsg != null) {
			//第三回合,
			try {
				reultData = checkIntegrity(request,did, encryptMsg);
			} catch (Exception e) {
				log.error("Process Error:", e);
				e.printStackTrace();
			}
			
		}
		return new JsonView(request, response, reultData);
	}

	// 打招呼
	private Object sayHello(String did, String random) {
		AuthManager.initClient(did, random);
		Map<String, Object> m = getSuccess();
		return m;
	}

	// 尝试握手
	private Object shakeHand(String did, String random, String channel) {
		int v = AuthManager.validClient(did, random);
		Object result;
		if (v == 0) {
			String toke= AuthManager.genToken();
			ClientInfo c= AuthManager.getClientInfo(did);
			c.setToken(toke);
			result = getToken( toke);
		} else if (v == 1) {
			// 不合法客户端
			result = getError("1003", "无效客户端ID");
		} else {
			// 校验失败
			result = getError("1004", "无效随机数");
		}
		ClientInfo c=  AuthManager.getClientInfo(did);
		c.setChannel(channel);
		return result;
	}
	
	//校验客户端完整性
	private Object checkIntegrity(HttpServletRequest request,String did,String encryptMsg) throws Exception {
	 	ClientInfo c= AuthManager.getClientInfo(did);
		//解密以后的字符串格式为：资源文件hash值+签名信息+设备id+Native版本号+web版本号
				//如：fb459fa9a4dd803ebcc788ef236c33b5+com.citic.iphone+84:38:35:4A:3A:A8+1.01
		String[] msg = null;
		 
		msg = ThreeDesUtil.decryptThreeDESECB(c.getToken(), encryptMsg).split("\\+");
		String hash=msg[0];
		String apkSign=msg[1];
		String  webVersion = msg[4];
		String  apkVersion = msg[3];
        if(!msg[2].equals(did)){
        	return getError("1006", "设备ID数据无效");
        }
        ClientInfo clientInfo= AuthManager.getClientInfo(did);
        
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		WebResourceManager wm = (WebResourceManager) ctx.getBean("webResourceManager");
		ApkManager am = (ApkManager) ctx.getBean("apkManager");
		WebResource wr_max= wm.getMaxVer();
		WebResource wr_client= wm.getVerByVid(new Long(webVersion));
		if(wr_max==null ||wr_client==null){
			 return getError("1007", "系统资源未初始化");
		}
		 
//		 Apk apk= am.getVerByVid(new Long(apkVersion));
		 
		 //如果是android，则校验apk签名信息
		 //todo
		if (isAndroid(clientInfo.getChannel())) {
			Apk apk= am.getVerByVid(new Long(apkVersion));
			if(apk==null) {
				log.error("Client's Apk Version not found:"+clientInfo);
			} else if (apk!=null&& !apkSign.equals(apk.getSign())) {
				return getError("1008", "客户端文件校验失败");
			}
		}
		
//		//如果是还在使用已被禁用的版本则提示更新。放在版本完整性校验前是因为：在出现服务端某一版本hash值不正确时，禁用改版本即可！
//		if	("0".equals(wr_client.getStatus())){
//			return getError("1009", "资源版本已被禁用");
//		}
		
		System.out.println("!!!!!!!!!!!hash:"+hash);
		System.out.println("!!!!!!!!!!!wr_client.getHash():"+wr_client.getHash());
		
		if(wr_client!=null&&!hash.equals(wr_client.getHash())){
			return getError("1009", "资源文件校验失败");
		}
		
		Version v=getVersion(apkVersion, new Long(webVersion), wm,am, wr_max,clientInfo.getChannel());
		
		return getVersion(v);
	}
	private boolean isAndroid(String chanel){
		boolean is=false;
		if (("1202").equals(chanel)||("1204").equals(chanel)) {
			is=true;
		}
		return is;

	}
	private boolean isIOS(String chanel){
		boolean is=false;
		if (("1201").equals(chanel)||("1203").equals(chanel)) {
			is=true;
		}
		return is;

	}
	private Version getVersion(String apkVersion,Long clientVersion,WebResourceManager wm, ApkManager am,WebResource wr_max,String chanel){
		Version v=new Version();
		if(apkVersion!=null&& isAndroid(chanel)){
			Apk apk=am.getMaxVer();
			
			if(apk==null) return v;
			
			//如果在使用已被禁用的APK版本则提示强制更新。如果版本被禁用，则必然存在更高的版本
			Apk userApk= am.getVerByVid(new Long(apkVersion));
			if ("0".equals(userApk.getStatus())) {
				v.setApkForce("1");
				v.setApkPath(apk.getUrl());
				v.setApkSize(apk.getVsize());
				v.setApkVersion( apk.getVid().toString());
				//如果apk需要强制更新则不再检测web资源
				return v;
			}
			
			if(apk.getVid().longValue()>Long.parseLong(apkVersion)){
				//已改为apk包一定强制更新
//				v.setApkForce(apk.getForceUpdate());
				v.setApkForce("1");
				v.setApkPath(apk.getUrl());
				v.setApkSize(apk.getVsize());
				v.setApkVersion( apk.getVid().toString());
				//如果apk需要强制更新则不再检测web资源，已改为apk包一定强制更新，所以此住一定是强制更新，不用判断，直接返回即可
				return v;
			}
		}
		//客户端请求过来，带上参数version，获取webresource当前最大max_vid版本，
		//如果version等于max_vid不处理
		//如果version大于max_vid，则要回滚版本，取max_vid对应的baseline版本(查询vid=baseline），如果baseline为空，则取max_vid版本。
		//如果version小于max_vid且（大于或等于）baseline，则根据max_vid和version从webresource_fix获取相应版本信息。
		//如果version小于max_vid且小于baseline（做了全量版本合并），则从webresource取合并版本信息（vid为baseline的版本信息），（注意这样情况下，返回的信息中版本为最大vid值，因为做了全量版本更新，url为http://xxxx/upload/baseline/full_vid.zip）。
		//返回包含：版本，url，size，是否强制更新，版本类型（fix，full，如果是full需要先把之前的目录删除然后在解压）
		 
		 if(wr_max.getVid().longValue()==clientVersion.longValue()){
			 return v;
		 }
		 
		 Long baseline=wr_max.getBaseline();
		 if(baseline==null) baseline=wr_max.getVid();
		 
		 
		 //如果是还在使用已被禁用的资源版本则提示强制更新。如果版本被禁用，则必然存在更高的版本
		 WebResource wr_client = wm.getVerByVid(clientVersion);
		 if	("0".equals(wr_client.getStatus())){
			 if(clientVersion.longValue()>=baseline.longValue()){
				 WebResourceIncr inc= wm.getIncrVer(wr_max.getVid(), new Long(clientVersion));
				 v.setWebFull("0");//0：部分更新，1：全量更新
				 v.setWebPath(inc.getUrl());
				 v.setWebSize(inc.getVsize());
				 v.setWebVersion(String.valueOf(inc.getVid()));
				 //强制升级
				 v.setWebForce("1");
			 }else{
				 //客户端小于baseline，直接更新到全量的baseline
				 //全量更新
				 v=getFullVersion(wr_max);
			 }
			 return v;
		 }
		 
		 
		 if(clientVersion.longValue()>wr_max.getVid().longValue()){
			//回退
			//全量更新
			//回退功能已去掉，该情况不会出现
			v=getFullVersion(wr_max);
		 }
		 else if(clientVersion.longValue()<wr_max.getVid().longValue()){
			 if(clientVersion.longValue()>=baseline.longValue()){
				 WebResourceIncr inc= wm.getIncrVer(wr_max.getVid(), new Long(clientVersion));
				 v.setWebFull("0");//0：部分更新，1：全量更新
				 v.setWebPath(inc.getUrl());
				 v.setWebSize(inc.getVsize());
				 v.setWebVersion(String.valueOf(inc.getVid()));
				 //强制升级 1
				 //不强制升级 0
				 //APK强制升级 2
				 //IOS强制升级 3
				 //赞不处理多版本区别
				 v.setWebForce(inc.getForceUpdate());
				 
				 //去除更新包过小，直接更新不提示的功能
//				 //0不提示直接更新
//				 if(Integer.parseInt(inc.getVsize())<=Constants.direct_update_size)
//					v.setWebUpdatePrompt("0"); 
			 }else{
				 //客户端小于baseline，直接更新到全量的baseline
				 //全量更新
				 v=getFullVersion(wr_max);
			 }
		 }
		 return v ;

	}
	private Object getToken( String token) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("ec", "0");
		m.put("em", "");
		m.put("token", token);
		return m;
	}

	private Version getFullVersion(
			WebResource wr) {
		Version v = new Version();
		v.setWebFull("1");
		v.setWebVersion(String.valueOf(wr.getVid()));
		v.setWebSize(wr.getVsize());
		v.setWebForce("1");
		v.setWebPath(wr.getFullUrl());
		return v;
	}
	private Object getVersion(Object obj) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("ec", "0");
		m.put("em", "");
		m.put("version", obj);
		return m;
	}
	
	
	public class Version {
		private String	apkVersion;
		private String apkSize;
		private String apkPath;
		private String apkForce;
		
		private String	webVersion;
		private String webSize;
		private String webPath;
		private String webForce;
		private String webFull="0"; //0：增量，1：全量
		private String webUpdatePrompt="1";//1:提示，0，不提示
		
		
		public String getWebUpdatePrompt() {
			return webUpdatePrompt;
		}
		public void setWebUpdatePrompt(String webUpdatePrompt) {
			this.webUpdatePrompt = webUpdatePrompt;
		}
		public String getWebFull() {
			return webFull;
		}
		public void setWebFull(String webFull) {
			this.webFull = webFull;
		}
		public String getApkVersion() {
			return apkVersion;
		}
		public void setApkVersion(String apkVersion) {
			this.apkVersion = apkVersion;
		}
		public String getApkSize() {
			return apkSize;
		}
		public void setApkSize(String apkSize) {
			this.apkSize = apkSize;
		}
		public String getApkPath() {
			return apkPath;
		}
		public void setApkPath(String apkPath) {
			this.apkPath = apkPath;
		}
		public String getApkForce() {
			return apkForce;
		}
		public void setApkForce(String apkForce) {
			this.apkForce = apkForce;
		}
		public String getWebVersion() {
			return webVersion;
		}
		public void setWebVersion(String webVersion) {
			this.webVersion = webVersion;
		}
		public String getWebSize() {
			return webSize;
		}
		public void setWebSize(String webSize) {
			this.webSize = webSize;
		}
		public String getWebPath() {
			return webPath;
		}
		public void setWebPath(String webPath) {
			this.webPath = webPath;
		}
		public String getWebForce() {
			return webForce;
		}
		public void setWebForce(String webForce) {
			this.webForce = webForce;
		}

		
	     
	}
}
