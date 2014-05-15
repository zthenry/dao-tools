package com.sw;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.sql.DataSource;

import net.paoding.rose.scanning.context.RoseAppContext;

import com.sw.metadata.EntityDefination;
import com.sw.metadata.FieldDefination;
import com.sw.tool.MessageUtil;
import com.sw.tool.StrOper;
import com.sw.write.IEntityWriter;
import com.sw.write.impl.EntityWriterImpl;

public class Director {
	public static final Properties colMapper = MessageUtil.getMessage("mapping");
	public static Boolean supportPage = false;
	public static String pageClass = "";
	public static final String PAGECONDITIONS = " #if(:page.orderBy){ order by ##(:page.orderBy) ##(:page.ascDesc) }  limit :page.startIndex , :page.countEachPage ";
	public static List<EntityDefination> fetchEntityDefination() throws SQLException {
		Set<String> tables = MessageUtil.getMessage("tableName").stringPropertyNames();
		String packagePath = MessageUtil.getMessage("tools").getProperty("packagePath");
		if("true".equals(MessageUtil.getMessage("tools").getProperty("supportpage")))
		{
			supportPage = true;
			pageClass = MessageUtil.getMessage("tools").getProperty("pageClass");
		}
		
		RoseAppContext rose = new RoseAppContext();
		DataSource dataSource = (DataSource) rose.getBean("dataSource");
		DatabaseMetaData metadata = null;
		Connection connection = dataSource.getConnection();
		metadata = connection.getMetaData();
		
		List<EntityDefination> list = null;
		if ( null != metadata ) {
			list = new ArrayList<EntityDefination>();
			for ( String table : tables ){
				ResultSet resultSet = metadata.getColumns(null, null,table, null);
				ResultSet keySet = metadata.getIndexInfo(null, null, table, false, false);
				List<String> keyList = new ArrayList<String>();
				while(keySet.next())
				{
					//如果是非主键，rs.getString("COLUMN_NAME")与rs.getString("INDEX_NAME")相同
	                if(keySet.getString("INDEX_NAME").equalsIgnoreCase("PRIMARY") && !keySet.getString("COLUMN_NAME").equalsIgnoreCase(keySet.getString("INDEX_NAME"))){
	                	keyList.add(keySet.getString("COLUMN_NAME"));
	                }
				}
				EntityDefination entityDefination = new EntityDefination();
				entityDefination.setKeyList(keyList);
				entityDefination.setTableCode(table);
				entityDefination.setPackPath(packagePath);
				wrapBean(resultSet,keyList ,entityDefination);
				
				list.add(entityDefination);
				 
			}
		}

		connection.close();
		
		return list;
		
	}
//	public static String getDBTableKeyField(String tableName)
//    {
//        try
//        {
//            //用“show  INDEX from tableName” 语句也能得到一系列的键
//            Connection conn = EasyJDBEngine.getInstance().getConnection();//这里用你自己的数据库连接
//            DatabaseMetaData meta = conn.getMetaData();
//            ResultSet rs = meta.getIndexInfo(null, null, tableName, false, false);
//            while(rs.next())
//            {
//                //如果是非主键，rs.getString("COLUMN_NAME")与rs.getString("INDEX_NAME")相同
//                if(rs.getString("INDEX_NAME").equalsIgnoreCase("PRIMARY") && !rs.getString("COLUMN_NAME").equalsIgnoreCase(rs.getString("INDEX_NAME"))){
//                    return rs.getString("COLUMN_NAME");
//                }
//            }
//            rs.close();
//            conn.close();
//        }
//        catch(SQLException e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }
	public static Enumeration<String> fetchTableName(String param){
		ResourceBundle rb = ResourceBundle.getBundle(param);
		return rb.getKeys();
	}
	
	public static String fetch(String param, String key){
		ResourceBundle rb = ResourceBundle.getBundle(param);
		return rb.getString(key);
	}
	
	public static void wrapBean(ResultSet resultSet,List<String> keyList,EntityDefination entityDefination) throws SQLException{

		List<FieldDefination> list = new ArrayList<FieldDefination>();
		while (resultSet.next()) {
			FieldDefination fieldDefination = new FieldDefination();
			String name = resultSet.getString("COLUMN_NAME");
			fieldDefination.setAutoIncreate(resultSet.getBoolean("IS_AUTOINCREMENT"));
			fieldDefination.setFieldCode(name);
			fieldDefination.setFieldName(StrOper.delLine(name));
			String type = getFieldType( resultSet );
			fieldDefination.setFieldType(type);
			fieldDefination.setIsKey(false);
			for (String key : keyList) {
				//如果当前字段是Key
				if(key.equals(name))
				{
					fieldDefination.setIsKey(true);
				}
			}
			list.add(fieldDefination);
		}
		entityDefination.setFieldList(list);
	}
	
	private static String getFieldType( ResultSet resultSet ) throws SQLException {
		
		String typeName = resultSet.getString("TYPE_NAME");
		String type = (String)colMapper.get(typeName);
		
		if ( null == type ) {
			int size = resultSet.getInt("COLUMN_SIZE");
			int digits = resultSet.getInt("DECIMAL_DIGITS");
			
			typeName = typeName + "(" + size + "," + digits + ")";
			type = (String)colMapper.get(typeName);
		}
		
		return type;
	}
	
	public static void main( String[] args ) {
		List<EntityDefination> list = null;
		try {
			list = fetchEntityDefination();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if ( null != list && ! list.isEmpty() ) {
			String exportPath = fetch("tools", "exportPath");
			IEntityWriter iew = new EntityWriterImpl(list,exportPath);
			iew.initEntityWriter();
		}
		
	}
	
}
