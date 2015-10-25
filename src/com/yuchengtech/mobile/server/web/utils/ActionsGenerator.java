package com.yuchengtech.mobile.server.web.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Administrator
 *cd E:\mobile\server\mobileServer\WebContent\WEB-INF\classes
 *java com.yuchengtech.mobile.server.web.utils.ActionsGenerator E:/DLNS/trunk/mobileApp/www/js
 */
public class ActionsGenerator {

	public static void main(String[] args) throws Exception {
String path=null;
if(args.length>0)path=args[0];
if(path==null)path="E:\\DLNS\\trunk\\mobileApp\\www\\js";
File f=new File(path);
   List<String> list=new ArrayList<String>();
	processFile(f,list);
	FileWriter fw=new FileWriter(new File("./actions.txt"));
	fw.write("#数据库类型使用\n");
	for(String s:list){
		StringBuffer sb=new StringBuffer();
		//insert into tbl_mb_actionsdef(id,actionurl,actioncontroller) values(hibernate_sequence.nextval,'signIn.do','com.yuchengtech.mobile.server.web.controller.MobileCommonController');
		
		sb.append("insert into tbl_mb_actionsdef(id,actionurl,actioncontroller) values(");
		sb.append("hibernate_sequence.nextval,'").append(s).append("','com.yuchengtech.mobile.server.web.controller.MobileCommonController');");
		//System.out.println(sb.toString());
		fw.write(sb.toString()+"\n");
	}
	fw.write("\n\n\n");
	fw.write("#文件类型使用\n");
	for(String s:list){
		StringBuffer sb=new StringBuffer();
		//insert into tbl_mb_actionsdef(id,actionurl,actioncontroller) values(hibernate_sequence.nextval,'signIn.do','com.yuchengtech.mobile.server.web.controller.MobileCommonController');
		
		sb.append(s).append("=com.yuchengtech.mobile.server.web.controller.MobileCommonController");
		//System.out.println(sb.toString());
		fw.write(sb.toString()+"\n");
	}
	fw.flush();
	fw.close();
	}

	private static void processFile(File f,List<String> list) throws Exception{
		if (f.isDirectory()) {
			for (File fi : f.listFiles()) {
				processFile(fi,list);
			}
		} else {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = null;
			while ((line = br.readLine()) != null) {
				int idx = line.indexOf(".do");
				if (idx != -1) {
					// ("queryAllAccounts.do "
					String l = line.substring(0, idx + 3);//去除右边的引号
					String l1 = line.substring(idx + 3);//右边引号的字符串

					int idx1 = l.lastIndexOf("\"");
					if (idx1 != -1) {
						String l2 = l.substring(idx1 + 1);//去除左边的引号
						if (l1.trim().startsWith("\"")) {//如果引号右边的字符串不是以引号开头的，则不算是action
                            int idx3=l2.lastIndexOf("/");
							if(idx3!=-1){
                        	l2=l2.substring(idx3+1);  
                          }
							if(l2.equals(".do")){
								continue;
							}
							if(!list.contains(l2))
							list.add(l2);
						}
					}
				}

			}
		}
	}

}
