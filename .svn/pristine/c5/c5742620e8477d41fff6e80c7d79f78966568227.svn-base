package com.yuchengtech.mobile.server.adapter.common.annotation.demowebservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * request、dto生成工具实例
 * 
 * 此类是通过解析文档，生成东莞农商银行移动营销项目代码的工具类
 * 可参考此类，编写生成指定request、dto的工具类
 * 
 * @author sunliang
 *
 */
public class RequestDTOTool {
	
	//需要修改
	private static String EXPORT_DIRECTORY = "/Users/sunliang/Desktop/export/";
	//需要修改
	private static String templatePath = "/Users/sunliang/Workspace/JavaWorkspace/drcbms/WebContent/WEB-INF/classes/export/";
	
//	//需要修改
//	private static String EXPORT_DIRECTORY = "D:/export/";
//	//需要修改
//	private static String templatePath = "D:/export/template/";
	
	private static String requestTemplateName = "requestJavaTemplate.ftl";
	private static String dtoTemplateName = "dtoJavaTemplate.ftl";
		
	private static String FIELD_TYPE_STRING = ",fieldType=MappingFieldType.FIELD_STRING";
	private static String FIELD_TYPE_IMAGE = ",fieldType=MappingFieldType.FIELD_IMAGE";
	private static String FIELD_TYPE_BYTE = ",fieldType=MappingFieldType.FIELD_BYTE";
	private static String FIELD_TYPE_SHORT = ",fieldType=MappingFieldType.FIELD_SHORT";
	private static String FIELD_TYPE_INT24 = ",fieldType=MappingFieldType.FIELD_INT24";
	private static String FIELD_TYPE_INT = ",fieldType=MappingFieldType.FIELD_INT";
	private static String FIELD_TYPE_LONG = ",fieldType=MappingFieldType.FIELD_LONG";
	private static String FIELD_TYPE_FLOAT = ",fieldType=MappingFieldType.FIELD_FLOAT";
	private static String FIELD_TYPE_DOUBLE = ",fieldType=MappingFieldType.FIELD_DOUBLE";

	private String filePath;
	private String sheetName;
	private String className;
	private String packageName;
	private String description;

	private List<Map<String, String>> requestPropertyList;
	private List<Map<String, String>> dtoPropertyList;
	

	private static String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	public static String fieldNameTopropertyName(String fieldName) {
		System.out
				.println("fieldNameToPropertyName fieldName:" + fieldName);
		
		String result = fieldName.toLowerCase();

		while (result.indexOf("_") != -1) {
			int index = result.indexOf("_");
			result = result.substring(0, index)
					+ String.valueOf(result.charAt(index + 1)).toUpperCase()
					+ result.substring(index + 2);
		}

		System.out.println("fieldNameToPropertyName result:" + result);

		return result;
	}
	
	public static String propertyNameToPropertyName(String propertyName) {
		System.out.println("propertyNameToPropertyName propertyName:" + propertyName);
		
		String result = propertyName;
		
		result = result.substring(0, 1).toUpperCase() + result.substring(1);
		
		System.out.println("propertyNameToPropertyName result:" + result);
		
		return result;
	}

	private RequestDTOTool() {
		super();
	}

	public RequestDTOTool(String filePath, String sheetName, String className, String packageName,String description)
			throws Exception {
		super();

		this.filePath = filePath;
		this.sheetName = sheetName;
		this.className = className;
		this.packageName = packageName;
		this.description = description;

		this.parseExcel();
	}

	public void parseExcel() throws Exception {

		InputStream is = new FileInputStream(filePath);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

		HSSFSheet hssfSheet = null;

		// 循环工作表Sheet
		for (int i = 0; i < hssfWorkbook.getNumberOfSheets(); i++) {
			hssfSheet = hssfWorkbook.getSheetAt(i);
			if (hssfSheet != null
					&& hssfWorkbook.getSheetName(i).equals(sheetName)) {
				break;
			}
		}

		if (hssfSheet == null) {
			throw new Exception("未找到sheet:" + sheetName);
		}

		List<Map<String, String>> requestPropertyList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> dtoPropertyList = new ArrayList<Map<String, String>>();

		// 循环行Row，解析输入参数
		int i = 0;
		boolean inputBegin = false;
		for (; i <= hssfSheet.getLastRowNum(); i++) {
			HSSFRow hssfRow = hssfSheet.getRow(i);
			if (hssfRow == null) {
				continue;
			}

			// 输入开始位置
			HSSFCell inputCell = hssfRow.getCell(0);
			if (inputCell == null) {
				continue;
			}
			if (getValue(inputCell).equals("输入")) {
				inputBegin = true;
				continue;
			}

			// 输入结束
			if (getValue(inputCell).equals("输出")) {
				break;
			}

			//开始解析
			if (inputBegin) {
				// 如果第一列为空则跳过
				HSSFCell firstCell = hssfRow.getCell(0);
				if (firstCell == null) {
					continue;
				}
				String firstCellValue = getValue(firstCell);
				if (firstCellValue.equals("")) {
					continue;
				}

				Map<String, String> requestPropertyMap = new HashMap<String, String>();

				// 解析fieldName
				HSSFCell fieldNameCell = hssfRow.getCell(7);
				if (fieldNameCell == null) {
					throw new Exception("未设置fieldName: sheet " + sheetName
							+ "——" + i + "行");
				}
				String fieldNameCellValue = getValue(fieldNameCell);
				if (fieldNameCellValue.equals("")) {
					throw new Exception("未设置fieldName: sheet " + sheetName
							+ "——" + i + "行");
				}
				requestPropertyMap.put("fieldName", fieldNameCellValue);
				String propertyName = RequestDTOTool.fieldNameTopropertyName(fieldNameCellValue);
				requestPropertyMap.put("propertyName", propertyName);
				requestPropertyMap.put("PropertyName", RequestDTOTool.propertyNameToPropertyName(propertyName));

				// 解析field中文描述
				HSSFCell fieldDesCell = hssfRow.getCell(8);
				if (fieldDesCell == null) {
					throw new Exception("未设置field描述: sheet " + sheetName + "——"
							+ i + "行");
				}
				String fieldDesCellValue = getValue(fieldDesCell);
				if (fieldDesCellValue.equals("")) {
					throw new Exception("未设置field描述: sheet " + sheetName + "——"
							+ i + "行");
				}
				requestPropertyMap.put("fieldDes", fieldDesCellValue);

				// 解析field类型
				HSSFCell fieldTypeCell = hssfRow.getCell(9);
				if (fieldTypeCell == null) {
					throw new Exception("未设置fieldType: sheet " + sheetName
							+ "——" + i + "行");
				}
				String fieldTypeCellValue = getValue(fieldTypeCell);
				if (fieldTypeCellValue.equals("")) {
					throw new Exception("未设置fieldType: sheet " + sheetName
							+ "——" + i + "行");
				}

				if (fieldTypeCellValue.startsWith("STRING")) {
					requestPropertyMap.put("fieldType", FIELD_TYPE_STRING);
				}
				if (fieldTypeCellValue.startsWith("IMAGE")) {
					requestPropertyMap.put("fieldType", FIELD_TYPE_IMAGE);
				}
				if (fieldTypeCellValue.startsWith("BYTE")) {
					requestPropertyMap.put("fieldType", FIELD_TYPE_BYTE);
				}
				if (fieldTypeCellValue.startsWith("SHORT")) {
					requestPropertyMap.put("fieldType", FIELD_TYPE_SHORT);
				}
				if (fieldTypeCellValue.startsWith("INT24")) {
					requestPropertyMap.put("fieldType", FIELD_TYPE_INT24);
				} else if (fieldTypeCellValue.startsWith("INT")) {
					requestPropertyMap.put("fieldType", FIELD_TYPE_INT);
				}
				if (fieldTypeCellValue.startsWith("LONG")) {
					requestPropertyMap.put("fieldType", FIELD_TYPE_LONG);
				}
				if (fieldTypeCellValue.startsWith("FLOAT")) {
					requestPropertyMap.put("fieldType", FIELD_TYPE_FLOAT);
				}
				if (fieldTypeCellValue.startsWith("DOUBLE")) {
					requestPropertyMap.put("fieldType", FIELD_TYPE_DOUBLE);
				}

				if (!fieldTypeCellValue.startsWith("IMAGE")) {
					if (fieldTypeCellValue.indexOf("(") == -1
							|| fieldTypeCellValue.indexOf(")") == -1
							|| fieldTypeCellValue.indexOf("(") > fieldTypeCellValue
									.indexOf(")")) {
						throw new Exception("fieldType有误: sheet " + sheetName
								+ "——" + i + "行");
					}

					if (!fieldTypeCellValue.startsWith("DOUBLE")) {
						String length = fieldTypeCellValue.substring(
								fieldTypeCellValue.indexOf("(") + 1,
								fieldTypeCellValue.indexOf(")"));
						requestPropertyMap.put("fieldLength", ",length="
								+ length);
					} else {
						if (fieldTypeCellValue.indexOf(",") == -1
								|| fieldTypeCellValue.indexOf("(") > fieldTypeCellValue
										.indexOf(",")
								|| fieldTypeCellValue.indexOf(",") > fieldTypeCellValue
										.indexOf(")")) {
							throw new Exception("fieldType有误: sheet "
									+ sheetName + "——" + i + "行");
						}
						String length = fieldTypeCellValue.substring(
								fieldTypeCellValue.indexOf("(") + 1,
								fieldTypeCellValue.indexOf(","));
						requestPropertyMap.put("fieldLength", ",length="
								+ length);
						String scale = fieldTypeCellValue.substring(
								fieldTypeCellValue.indexOf(",") + 1,
								fieldTypeCellValue.indexOf(")"));
						requestPropertyMap.put("fieldScale", ",scale=" + scale);
					}
				}

				// 解析field所在位置
				HSSFCell fieldAreaCell = hssfRow.getCell(10);
				if (fieldAreaCell != null) {
					String fieldAreaCellValue = getValue(fieldAreaCell);
					if (fieldAreaCellValue.equals("APP_HEAD")) {
						requestPropertyMap.put("fieldArea",
								",area=Area.APPHEADER");
					}
				}
			
				requestPropertyList.add(requestPropertyMap);
			}

			System.out.println("i：" + i);
		}

		System.out.println("!!!!!!i：" + i);
		// 循环行Row，解析输出字段
		boolean outputArrayBegin = false;
		for (; i <= hssfSheet.getLastRowNum(); i++) {
			HSSFRow hssfRow = hssfSheet.getRow(i);
			if (hssfRow == null) {
				continue;
			}

			// 输出开始位置
			HSSFCell firstCell = hssfRow.getCell(0);
			if (firstCell == null) {
				continue;
			}
			if (getValue(firstCell).equals("输出")) {
				continue;
			}
			// 如果第一列为空则跳过
			if (getValue(firstCell).equals("")) {
				continue;
			}
			
			// 判断输出字段结束位置
			HSSFCell typeCell = hssfRow.getCell(9);
			if (typeCell == null) {
				continue;
			}
			if (getValue(typeCell).equals("ARRAY")) {
				if (!outputArrayBegin) {
					outputArrayBegin = true;
					continue;
				} else {
					// 第二个ARRAY类型（数组结束标志），结束
					break;
				}
			}
			
			// 开始解析
			Map<String, String> dtoPropertyMap = new HashMap<String, String>();

			// 解析fieldName
			HSSFCell fieldNameCell = hssfRow.getCell(7);
			if (fieldNameCell == null) {
				throw new Exception("未设置fieldName: sheet " + sheetName + "——"
						+ i + "行");
			}
			String fieldNameCellValue = getValue(fieldNameCell);
			if (fieldNameCellValue.equals("")) {
				throw new Exception("未设置fieldName: sheet " + sheetName + "——"
						+ i + "行");
			}
			dtoPropertyMap.put("fieldName", fieldNameCellValue);
			String propertyName = RequestDTOTool.fieldNameTopropertyName(fieldNameCellValue);
			dtoPropertyMap.put("propertyName", propertyName);
			dtoPropertyMap.put("PropertyName", RequestDTOTool.propertyNameToPropertyName(propertyName));

			// 解析field中文描述
			HSSFCell fieldDesCell = hssfRow.getCell(8);
			if (fieldDesCell == null) {
				throw new Exception("未设置field描述: sheet " + sheetName + "——" + i
						+ "行");
			}
			String fieldDesCellValue = getValue(fieldDesCell);
			if (fieldDesCellValue.equals("")) {
				throw new Exception("未设置field描述: sheet " + sheetName + "——" + i
						+ "行");
			}
			dtoPropertyMap.put("fieldDes", fieldDesCellValue);

			// 解析field所在位置
			HSSFCell fieldAreaCell = hssfRow.getCell(10);
			if (fieldAreaCell != null) {
				String fieldAreaCellValue = getValue(fieldAreaCell);
				if (fieldAreaCellValue.equals("APP_HEAD")) {
					dtoPropertyMap.put("fieldArea", ",area=Area.APPHEADER");
				}
			}
			
			// field是否为日期
			HSSFCell fieldDateTypeCell = hssfRow.getCell(12);
			if (fieldDateTypeCell != null) {
				String fieldDateTypeCellValue = getValue(fieldDateTypeCell);
				if (fieldDateTypeCellValue.equals("YYYYMMDD")) {
					dtoPropertyMap.put("fieldDateType",
							",dateType=DateType.YYYYMMDD");
				}
				if (fieldDateTypeCellValue.equals("YYYYMMDDHHMMSS")) {
					dtoPropertyMap.put("fieldDateType",
							",dateType=DateType.YYYYMMDDHHMMSS");
				}
			}

			dtoPropertyList.add(dtoPropertyMap);

		}

		this.requestPropertyList=requestPropertyList;
		this.dtoPropertyList=dtoPropertyList;
	}
	
	public void generateJava() throws Exception {

		//java文件目录
        File dir=new File(EXPORT_DIRECTORY+packageName);
        if (!dir.exists()) {
        	dir.mkdirs();
        }
        
		//创建一个合适的Configration对象  
        Configuration configuration = new Configuration();  
        configuration.setDirectoryForTemplateLoading(new File(templatePath));  
        configuration.setObjectWrapper(new DefaultObjectWrapper());  
        configuration.setDefaultEncoding("UTF-8");   //这个一定要设置，不然在生成的页面中 会乱码  
        
        //生成request类
        //获取或创建一个模版 
        Template requestTemplate = configuration.getTemplate(requestTemplateName); 
        
        Map<String, Object> paramMap = new HashMap<String, Object>();  
        paramMap.put("sheetName", sheetName);
        paramMap.put("className", className);
        paramMap.put("packageName", packageName);
        paramMap.put("description", description);
        
        paramMap.put("list", requestPropertyList);  

        File file=new File(EXPORT_DIRECTORY+packageName+File.separator+className+"Request.java");
        if (!file.exists()) {
        	file.createNewFile();
        }
        Writer writer  = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");  
        requestTemplate.process(paramMap, writer); 
        
        //生成dto类
        //获取或创建一个模版 
        Template dtoTemplate = configuration.getTemplate(dtoTemplateName); 
        
        Map<String, Object> paramMap2 = new HashMap<String, Object>();  
        paramMap2.put("sheetName", sheetName);
        paramMap2.put("className", className);
        paramMap2.put("packageName", packageName);
        paramMap2.put("description", description);
        
        paramMap2.put("list", dtoPropertyList);  
        
        File file2=new File(EXPORT_DIRECTORY+packageName+File.separator+className+"DTO.java");
        if (!file2.exists()) {
        	file2.createNewFile();
        }
        Writer writer2  = new OutputStreamWriter(new FileOutputStream(file2),"UTF-8");  
        dtoTemplate.process(paramMap2, writer2);
	}
	
	public static void main(String[] a) throws Exception{
//		RequestDTOTool requestDTOTool=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000001", "CustomerSearch", "crmcustomer","客户查询");
//		requestDTOTool.generateJava();
		
//		RequestDTOTool requestDTOTool2=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000002", "CustomerDetail", "crmcustomer","客户详情");
//		requestDTOTool2.generateJava();
		
//		RequestDTOTool requestDTOTool3=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000003", "CustomerManager", "crmcustomer","主办客户经理查询");
//		requestDTOTool3.generateJava();

//		RequestDTOTool requestDTOTool4=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000004", "LinkmanSearch", "crmcustomer","联系人查询");
//		requestDTOTool4.generateJava();

//		RequestDTOTool requestDTOTool5=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "Q1000005", "LinkmanEdit", "crmcustomer","关系客户联系人信息维护");
//		requestDTOTool5.generateJava();
		
//		RequestDTOTool requestDTOTool6=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000006", "FinancialSearch", "crmcustomer","客户财务信息查询");
//		requestDTOTool6.generateJava();
//		
//		RequestDTOTool requestDTOTool7=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000007", "FinancialDetail", "crmcustomer","客户财务信息明细");
//		requestDTOTool7.generateJava();
		
//		RequestDTOTool requestDTOTool8=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000008", "RalationClientSearch", "crmcustomer","关联客户（企业）信息查询");
//		requestDTOTool8.generateJava();
		
//		RequestDTOTool requestDTOTool9=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000009", "RalationClientDetail", "crmcustomer","关联客户（企业）信息详情");
//		requestDTOTool9.generateJava();
		
//		RequestDTOTool requestDTOTool10=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000010", "CustomerSituation", "crmsituation","客户业务概况");
//		requestDTOTool10.generateJava();
		
//		RequestDTOTool requestDTOTool11=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000011", "DepositSituation", "crmsituation","存款业务概览");
//		requestDTOTool11.generateJava();
		
//		RequestDTOTool requestDTOTool99=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M4000001", "CRMDict", "crmcustomer","数据字典查询");
//		requestDTOTool99.generateJava();
		
//		RequestDTOTool requestDTOTool16=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000016", "IntermediateSituation", "crmsituation","中间业务概览");
//		requestDTOTool16.generateJava();
		
//		RequestDTOTool requestDTOTool15=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000015", "FinanceDetailSituation", "crmsituation","理财业务概览详情");
//		requestDTOTool15.generateJava();
	
//		RequestDTOTool requestDTOTool14=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000014", "FinanceSituation", "crmsituation","理财业务概览");
//		requestDTOTool14.generateJava();
		
//		RequestDTOTool requestDTOTool13=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000013", "LoanSituation", "crmsituation","贷款业务概览");
//		requestDTOTool13.generateJava();
		
//		RequestDTOTool requestDTOTool12=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000012", "DepositDetailSituation", "crmsituation","存款业务概览详情");
//		requestDTOTool12.generateJava();
		
//		RequestDTOTool requestDTOTool18=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000018", "DepositAccount", "crmsituation","存款账户信息");
//		requestDTOTool18.generateJava();
//		
//		RequestDTOTool requestDTOTool19=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000019", "DepositAccountDetail", "crmsituation","存款账户详情");
//		requestDTOTool19.generateJava();
		
//		RequestDTOTool requestDTOTool20=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000020", "LoanAccount", "crmsituation","贷款账户信息");
//		requestDTOTool20.generateJava();
		
//		RequestDTOTool requestDTOTool21=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000021", "LoanAccountDetail", "crmsituation","贷款账户详情");
//		requestDTOTool21.generateJava();
//		
//		RequestDTOTool requestDTOTool22=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000022", "FinanceAccount", "crmsituation","理财账户信息");
//		requestDTOTool22.generateJava();
		
//		RequestDTOTool requestDTOTool23=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000023", "CustomerAuth", "crmsituation","客户授信信息");
//		requestDTOTool23.generateJava();
//		
//		RequestDTOTool requestDTOTool24=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000024", "OutWatchAuth", "crmsituation","表外授信信息");
//		requestDTOTool24.generateJava();
//		
//		RequestDTOTool requestDTOTool25=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000025", "OutWatchAuthDetail", "crmsituation","表外授信信息详情");
//		requestDTOTool25.generateJava();
//		
//		RequestDTOTool requestDTOTool26=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000026", "OutWatchAuthList", "crmsituation","表外授信明细");
//		requestDTOTool26.generateJava();
//		
//		RequestDTOTool requestDTOTool27=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000027", "OutMortInfo", "crmsituation","对外担保抵押物信息");
//		requestDTOTool27.generateJava();
//		
//		RequestDTOTool requestDTOTool28=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000028", "OutMortInfoDetail", "crmsituation","对外担保抵押物信息详情");
//		requestDTOTool28.generateJava();
//		
//		RequestDTOTool requestDTOTool29=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000029", "OutImpaInfo", "crmsituation","对外担保质押物信息");
//		requestDTOTool29.generateJava();
//		
//		RequestDTOTool requestDTOTool30=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000030", "OutAssurerInfo", "crmsituation","对外担保保证人信息");
//		requestDTOTool30.generateJava();
//		
//		RequestDTOTool requestDTOTool35=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000035", "SignInfo", "crmsituation","签约信息");
//		requestDTOTool35.generateJava();
//		
//		RequestDTOTool requestDTOTool38=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000038", "EtBaseInfo", "crmsituation","银企合作协议信息");
//		requestDTOTool38.generateJava();
		
//		RequestDTOTool requestDTOTool36=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000036", "LoanDepositRate", "crmsituation","贷存比信息");
//		requestDTOTool36.generateJava();
//		
//		RequestDTOTool requestDTOTool37=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000037", "LoanDepositRateDetail", "crmsituation","贷存比信息详情");
//		requestDTOTool37.generateJava();
//		
//		RequestDTOTool requestDTOTool39=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000039", "CustomerDemand", "crmcustomer","客户需求信息");
//		requestDTOTool39.generateJava();
//		
//		RequestDTOTool requestDTOTool40=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "Q1000040", "CustomerDemandUpdate", "crmcustomer","客户需求信息修改");
//		requestDTOTool40.generateJava();
//		
//		RequestDTOTool requestDTOTool41=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000041", "CustomerOtherBank", "crmcustomer","客户他行信息");
//		requestDTOTool41.generateJava();
//		
//		RequestDTOTool requestDTOTool42=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "Q1000042", "CustomerOtherBankUpdate", "crmcustomer","客户他行信息新增/修改");
//		requestDTOTool42.generateJava();
//		
//		RequestDTOTool requestDTOTool43=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000043", "CustomerEvent", "crmcustomer","客户事件信息");
//		requestDTOTool43.generateJava();
//		
//		RequestDTOTool requestDTOTool44=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "Q1000044", "CustomerEventUpdate", "crmcustomer","客户事件信息新增/修改");
//		requestDTOTool44.generateJava();
//		
//		RequestDTOTool requestDTOTool45=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000045", "CustomerVisit", "crmcustomer","客户拜访信息");
//		requestDTOTool45.generateJava();
//		
//		RequestDTOTool requestDTOTool46=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "Q1000046", "CustomerVisitUpdate", "crmcustomer","客户拜访信息新增/修改");
//		requestDTOTool46.generateJava();
		
//		RequestDTOTool requestDTOTool47=new RequestDTOTool("D:/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M1000047", "CustomerManagerGrant", "crmcustomer","客户经理权限查询");
//		requestDTOTool47.generateJava();
		
//		RequestDTOTool requestDTOTool48=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M2000001", "MarketingTaskSearch", "crmmarketing","营销任务查询");
//		requestDTOTool48.generateJava();
//		
//		RequestDTOTool requestDTOTool49=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M2000002", "MarketingTaskDetail", "crmmarketing","营销任务详情");
//		requestDTOTool49.generateJava();
		
//		RequestDTOTool requestDTOTool52=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M2000005", "MarketingPlanSearch", "crmmarketing","营销计划查询");
//		requestDTOTool52.generateJava();
//		
//		RequestDTOTool requestDTOTool53=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M2000006", "MarketingPlanReceive", "crmmarketing","营销计划接收");
//		requestDTOTool53.generateJava();
//		
//		RequestDTOTool requestDTOTool54=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M2000007", "MarketingActivitySearch", "crmmarketing","营销活动查询");
//		requestDTOTool54.generateJava();
//		
//		RequestDTOTool requestDTOTool55=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M2000008", "MarketingActivityReceive", "crmmarketing","营销活动接收/执行");
//		requestDTOTool55.generateJava();
//		
//		RequestDTOTool requestDTOTool56=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M2000009", "MarketingBusinessSearch", "crmmarketing","商机查询");
//		requestDTOTool56.generateJava();
//		
//		RequestDTOTool requestDTOTool57=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M5000002", "MessageTypeSearch", "crmmessage","信息提醒提醒类型查询");
//		requestDTOTool57.generateJava();
		
		RequestDTOTool requestDTOTool58=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M5000003", "MessageSearch", "crmmessage","信息提醒查询");
		requestDTOTool58.generateJava();
		
		RequestDTOTool requestDTOTool59=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M5000004", "MessageWholesaleDetail", "crmmessage","大额定期存款到期提醒详情查询");
		requestDTOTool59.generateJava();
		
		RequestDTOTool requestDTOTool60=new RequestDTOTool("/Users/sunliang/Desktop/export/ESB_DRCBSD_字段映射文档-CRM系统.xls", "M5000005", "MessageLoanExpireDetail", "crmmessage","普通贷款到期及应纳利息提醒详情查询");
		requestDTOTool60.generateJava();
		
	}
}
