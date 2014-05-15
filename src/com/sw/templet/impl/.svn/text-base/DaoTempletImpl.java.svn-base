package com.sw.templet.impl;

import java.util.ArrayList;
import java.util.List;

import com.sw.AliasCreator;
import com.sw.Director;
import com.sw.metadata.EntityDefination;
import com.sw.metadata.FieldDefination;
import com.sw.templet.ITemplet;
import com.sw.tool.ISysCharset;
import com.sw.tool.MessageUtil;
import com.sw.tool.StrOper;

public class DaoTempletImpl implements ITemplet,ISysCharset {
	private String view ; 
	private String tableName;
	private String aliasName;
	private String queryConditions;
	/*主键查询条件 开头没有and*/
	private String keyConditions;
	
	
	
	
    private EntityDefination entityDefination;
    private String ClassNameBl = null;
    private String ClassName = null;
    private static final String PRE = "obj";
    private final String INSERT_NAME = "insert";
    
    public DaoTempletImpl(EntityDefination entityDefination){
        this.entityDefination = entityDefination;
        this.ClassNameBl = StrOper.delLine(entityDefination.getTableCode());
        this.ClassName = StrOper.upShouAndDelLine(entityDefination.getTableCode());
    }
    /**
     * 获取实体类的字符串描述
     * @return
     */
    public String templetContent(){
        return createContent();
    }
    
    /**
     * 整个类的字符串描述
     * @return
     */
    private String createContent(){
        StringBuffer entityCon = new StringBuffer();
        entityCon.append("package "+entityDefination.getPackPath()+"."+PACK_DAO+";"+TWO_HC);
        entityCon.append("import java.util.*;"+TWO_HC);
        entityCon.append("import net.paoding.rose.jade.annotation.DAO;"+ONE_HC);
        entityCon.append("import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;"+ONE_HC);
        entityCon.append("import net.paoding.rose.jade.annotation.SQL;"+ONE_HC);
        entityCon.append("import net.paoding.rose.jade.annotation.SQLParam;"+TWO_HC);
        entityCon.append("import "+entityDefination.getPackPath()+"."+PACK_ENTITY+"."+ClassName+";"+TWO_HC);
        entityCon.append("/** ClassName ("+notic+") */"+ONE_HC);
        entityCon.append("@DAO(catalog=\""+MessageUtil.getMessage("tools").getProperty("catalog")+"\")"+ONE_HC);
        entityCon.append("public interface "+ClassName+PACK_DAO_CLASS_POSTFIX+" {"+TWO_HC);
       
        //获取表名
        tableName = entityDefination.getTableCode();
        //获取别名
        aliasName = AliasCreator.createAlias(tableName);
        //获取view试图
        view = createView(entityDefination.getFieldList());
        //主键查询条件
        keyConditions = getIdCondtions(entityDefination.getFieldList());
        
        
        
        //创建静态属性
        entityCon.append("public static final String ALIGNNAME =\""+aliasName+"\";"+TWO_HC);
        entityCon.append("public static final String VIEW =\""+view+"\";"+TWO_HC);
        entityCon.append("public static final String TABLENAME =\""+tableName+"\";"+TWO_HC);
      
        //创建查询条件的常量
        entityCon.append(createConditions(entityDefination.getFieldList()));
        
        //添加方法
        entityCon.append(createInsert(entityDefination.getFieldList())+TWO_HC); 
        //创建删除方法
        entityCon.append(createDelete(entityDefination.getFieldList())+TWO_HC); 
        //如果全部都是主键，则不提供更新的方法
        if(!isAllKey(entityDefination.getFieldList()))
        {
        	 //按实例更新方法
            entityCon.append(createUpdate(entityDefination.getFieldList())+TWO_HC); //创建修改方法
            //按属性更新方法
            entityCon.append(createUpdateByProperty(entityDefination.getFieldList())+TWO_HC);
        }
        
        //按ID查询
        entityCon.append(createFindById(entityDefination.getFieldList())+TWO_HC);
        //按对象查询
        entityCon.append(createFindByExample(entityDefination.getFieldList())+TWO_HC);
        //按对象和属性集合模糊查询
        entityCon.append(createFindByExampleAndColletion(entityDefination.getFieldList())+TWO_HC);
        //按对象模糊查询
        entityCon.append(createFindFuzzzyByExample(entityDefination.getFieldList())+TWO_HC);
        //按对象和属性集合模糊查询
        entityCon.append(createFindFuzzzyByExampleAndColletion(entityDefination.getFieldList())+TWO_HC);
        
        //按属性属性查询
        entityCon.append(createFindByProperty(entityDefination.getFieldList())+TWO_HC);
        //按属性模糊查询
        entityCon.append(createFindFuzzyByProperty(entityDefination.getFieldList())+TWO_HC);

        
        if(Director.supportPage)
        {
        	//创建分页查询的方法
        	entityCon.append(createFindPage(entityDefination.getFieldList())+TWO_HC);
        	entityCon.append(createCountPage(entityDefination.getFieldList())+TWO_HC);
        	
        	//创建分页查询的方法
        	entityCon.append(createFindFuzzyPage(entityDefination.getFieldList())+TWO_HC);
        	entityCon.append(createCountFuzzyPage(entityDefination.getFieldList())+TWO_HC);
        	
        	//创建按属性集合查询分页查询
        	entityCon.append(createFindPageByColletion(entityDefination.getFieldList())+TWO_HC);
        	entityCon.append(countFindPageByColletion(entityDefination.getFieldList())+TWO_HC);
        	
        	//创建按属性集合模糊查询查询分页查询
        	entityCon.append(createFuzzyFindPageByColletion(entityDefination.getFieldList())+TWO_HC);
        	entityCon.append(countFuzzyFindPageByColletion(entityDefination.getFieldList())+TWO_HC);
        	
        }
        //自己拼sql
        entityCon.append(createAssembling(entityDefination.getFieldList())+TWO_HC);
        
        entityCon.append("}"+ONE_HC);
        return entityCon.toString();
    }
    private String createView(List<FieldDefination>  fieldList){
    	return createOneAllFilesCon(fieldList);
    }
    /**
     * 是否全部都是主键
     */
    private boolean isAllKey(List<FieldDefination>  list){
    	boolean flag = true;
    	for (FieldDefination fieldDefination : list) {
			flag = fieldDefination.getIsKey();
			if(!flag)
				return flag;
		}
    	return true;
    }
    
    /**
     * 创建按集合分页查询方法
     * @return
     */
    private String createFuzzyFindPageByColletion(List<FieldDefination> fieldList){
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" 按属性集合和example分页查询 */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select ");
        strResult.append(view);
        strResult.append(" from "+tableName+" "+aliasName+" "+createFuzzyFilterClause(fieldList)+"   and "+aliasName+".##(:property) in (:list) "+Director.PAGECONDITIONS+" \")"+ONE_HC);
        strResult.append(ONE_TAB+"public List<"+ClassName+"> findFuzzyPage(@SQLParam(\"obj\") "+ClassName+" "+ClassNameBl+", @SQLParam(\"page\") "+Director.pageClass+" page ,@SQLParam(\"property\") String propertyName,@SQLParam(\"list\") List list );");
        return strResult.toString();
    }
    private String countFuzzyFindPageByColletion(List<FieldDefination> fieldList){
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" count 按属性集合和example分页查询 */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select count(1) ");
        strResult.append(" from "+tableName+" "+aliasName+" "+createFuzzyFilterClause(fieldList)+"   and "+aliasName+".##(:property) in (:list)  \")"+ONE_HC);
        strResult.append(ONE_TAB+"public Long countFuzzyPage(@SQLParam(\"obj\") "+ClassName+" "+ClassNameBl+",@SQLParam(\"property\") String propertyName,@SQLParam(\"list\") List list );");
        return strResult.toString();
    }
    
    /**
     * 创建按集合分页查询方法
     * @return
     */
    private String createFindPageByColletion(List<FieldDefination> fieldList){
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" find by page , */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select ");
        strResult.append(view);
        strResult.append(" from "+tableName+" "+aliasName+" "+createFilterClause(fieldList) +"   and "+aliasName+".##(:property) in (:list) "+Director.PAGECONDITIONS+" \")"+ONE_HC);
        strResult.append(ONE_TAB+"public List<"+ClassName+"> findPage(@SQLParam(\"obj\") "+ClassName+" "+ClassNameBl+", @SQLParam(\"page\") "+Director.pageClass+" page ,@SQLParam(\"property\") String propertyName,@SQLParam(\"list\") List list );");
        return strResult.toString();
    }
    private String countFindPageByColletion(List<FieldDefination> fieldList){
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" find by page , */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select count(1)");
        strResult.append(" from "+tableName+" "+aliasName+" "+createFilterClause(fieldList) +"   and "+aliasName+".##(:property) in (:list)  \")"+ONE_HC);
        strResult.append(ONE_TAB+"public Long countPage(@SQLParam(\"obj\") "+ClassName+" "+ClassNameBl+",@SQLParam(\"property\") String propertyName,@SQLParam(\"list\") List list );");
        return strResult.toString();
    }
    
    /**
     * 创建分页查询方法
     * @return
     */
    private String createFindPage(List<FieldDefination> fieldList){
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" find by page , */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select ");
        strResult.append(view);
        strResult.append(" from "+tableName+" "+aliasName+" "+createFilterClause(fieldList) +" "+Director.PAGECONDITIONS+" \")"+ONE_HC);
        strResult.append(ONE_TAB+"public List<"+ClassName+"> findPage(@SQLParam(\"obj\") "+ClassName+" "+ClassNameBl+", @SQLParam(\"page\") "+Director.pageClass+" page);");
        return strResult.toString();
    }
    
    /**
     * 创建分页模糊查询方法
     * @return
     */
    private String createFindFuzzyPage(List<FieldDefination> fieldList){
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" find fuzzy by page  , */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select ");
        strResult.append(view);
        strResult.append(" from "+tableName+" "+aliasName+" "+createFuzzyFilterClause(fieldList) +" "+Director.PAGECONDITIONS+" \")"+ONE_HC);
        strResult.append(ONE_TAB+"public List<"+ClassName+"> findFuzzyPage(@SQLParam(\"obj\") "+ClassName+" "+ClassNameBl+", @SQLParam(\"page\") "+Director.pageClass+" page);");
        return strResult.toString();
    }
    
    private String createCountFuzzyPage(List<FieldDefination> fieldList){
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" count fuzzy page , */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select ");
        strResult.append(" count(1) ");
        strResult.append(" from "+tableName+" "+aliasName+" "+createFuzzyFilterClause(fieldList) +" \")"+ONE_HC);
        strResult.append(ONE_TAB+"public Long countFuzzyPage(@SQLParam(\"obj\") "+ClassName+" "+ClassNameBl+");");
        return strResult.toString();
    }
    private String createCountPage(List<FieldDefination> fieldList){
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" count page , */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select ");
        strResult.append(" count(1) ");
        strResult.append(" from "+tableName+" "+aliasName+" "+createFilterClause(fieldList) +" \")"+ONE_HC);
        strResult.append(ONE_TAB+"public Long countPage(@SQLParam(\"obj\") "+ClassName+" "+ClassNameBl+");");
        return strResult.toString();
    }
    /** 创建添加方法 */
    private String createInsert(List<FieldDefination> fieldList){
    	 
    	 Boolean returnFlag = false;
    	 List<FieldDefination> keyList = findKey(fieldList);
    	  String returnType = "void";
         if((!keyList.isEmpty())&&keyList.size()==1&&keyList.get(0).getAutoIncreate())
         {
        	 returnFlag = true;
        	 returnType = 	keyList.get(0).getFieldType();
         }
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** insert "+ClassName+" */"+ONE_HC);
        if(returnFlag)
        strResult.append(ONE_TAB+"@ReturnGeneratedKeys"+ONE_HC);
        
        strResult.append(ONE_TAB+"@SQL("+'"'+"insert into ");
        strResult.append(tableName+" (");
        //创建字段
        strResult.append(createInsertAllFilesCon(fieldList));
        strResult.append(") values (");
        //创建值
        strResult.append(createInsertdSomeCon(fieldList));
        
        strResult.append(")"+'"'+")"+ONE_HC);
      
      
        strResult.append(ONE_TAB+"public "+returnType+" "+INSERT_NAME+"(@SQLParam("+'"'+"obj"+'"'+") "+ClassName+" "+ClassNameBl+");");
        return strResult.toString();
    }
    /** 添加方法的 一些代码 */
  private String createInsertdSomeCon(List<FieldDefination> fieldList){
      StringBuffer strResult = new StringBuffer();
      String theField = null;
      for(FieldDefination fieldDefination : fieldList) {
      	//如果是自增长的列
      	if(fieldDefination.getAutoIncreate())
      	{
      		
      		theField = addIf(fieldDefination.getFieldType(),fieldDefination.getFieldName(),":"+PRE+"."+StrOper.delLine(fieldDefination.getFieldCode())+", ");
      		strResult.append(theField);
      	}
      	else{
      		theField = StrOper.delLine(fieldDefination.getFieldCode());
              strResult.append(":"+PRE+"."+theField+", ");
      	}
          
      }
      return strResult.substring(0, strResult.length()-2);
  }
    /** 创建insert的字段 */
  private String createInsertAllFilesCon(List<FieldDefination> fieldList){
      StringBuffer strView = new StringBuffer();
      String theZd = null;
      for(FieldDefination fieldDefination : fieldList) {
      	//如果是自增加的
      	if(fieldDefination.getAutoIncreate())
      	{
      		theZd = addIf(fieldDefination.getFieldType(),fieldDefination.getFieldName(),fieldDefination.getFieldCode()+", ");
//      		 #if(:obj.warnMessageId!=null) { warn_message_id,  }
      		strView.append(theZd);
      	}
      	else{
      		theZd = fieldDefination.getFieldCode();
      		strView.append("`"+theZd+"`, ");
      	}
          
      }
      return strView.substring(0, strView.length()-2);
  }
  private String addIf(String type,String column,String target){
  	StringBuffer sb = new StringBuffer();
  	if("String".equals(type))
  	{
  		sb.append(" #if(:"+PRE+"."+column+") { {_target_} }");
  	}
  	else{
  		sb.append(" #if(:"+PRE+"."+column+"!=null) { {_target_} }");
  	}
  	return sb.toString().replace("{_target_}", target);
  }
  /** 创建删除方法 */
	private String createDelete(List<FieldDefination> fieldList){
		List<FieldDefination> keyList = new ArrayList<FieldDefination>();
		for (FieldDefination fieldDefination : fieldList) {
			if(fieldDefination.getIsKey())
			{
				keyList.add(fieldDefination);
			}
		}
	    StringBuffer strResult = new StringBuffer();
	    strResult.append(ONE_TAB+"/** delete "+ClassName+"*/"+ONE_HC);
	    strResult.append(ONE_TAB+"@SQL("+'"'+"delete from "+tableName+" where "+getDeleteConditions(keyList)+" \")"+ONE_HC);
	    strResult.append(ONE_TAB+"public void delete "+getDeleteParam(keyList)+";");
	    return strResult.toString();
	}
	/**
	 * 获ID的查询条件
	 * @param keyList
	 * @return
	 */
	private String getIdCondtions(List<FieldDefination> fieldList){
		StringBuffer sb = new StringBuffer(); 
		int flag = 0;
		List<FieldDefination>  keyList = findKey(fieldList);
		for (FieldDefination fieldDefination : keyList) {
			if(flag !=0)
			{
				sb.append(" and ");
			}
			sb.append(" "+aliasName+"."+ fieldDefination.getFieldCode()+"=:"+fieldDefination.getFieldName());
			flag++;
		}
		return sb.toString();
	}
	private String getDeleteConditions(List<FieldDefination> keyList){
		StringBuffer sb = new StringBuffer(); 
		int flag = 0;
		for (FieldDefination fieldDefination : keyList) {
			if(flag !=0)
			{
				sb.append(" and ");
			}
			sb.append(" "+ fieldDefination.getFieldCode()+"=:"+fieldDefination.getFieldName());
			flag++;
		}
		return sb.toString();
	}
	private String getDeleteParam(List<FieldDefination> keyList ){
			StringBuffer sb = new StringBuffer();
			sb.append("(");
			int flag =0;
			for (FieldDefination fieldDefination : keyList) {
				if(flag !=0 )
				{
					sb.append(",");
				}
				sb.append("@SQLParam(\""+fieldDefination.getFieldName()+"\") "+fieldDefination.getFieldType()+" "+fieldDefination.getFieldName());
				flag++;
			}
			sb.append(")");
			return sb.toString();
	}
	
	private String createUpdateByProperty(List<FieldDefination> fieldList){
		List<FieldDefination> keyList = findKey(fieldList);
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** update "+ClassName+" by property */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"update "+tableName+" set ");
        strResult.append(" ##(:propertyName)=:propertyValue ");
        strResult.append(" where "+getUpdateConditions(keyList)+" \")"+ONE_HC);
        strResult.append(ONE_TAB+"public void update(@SQLParam("+'"'+PRE+'"'+") "+ClassName+" "+ClassNameBl+",@SQLParam("+'"'+"propertyName"+'"'+") String propertyName," +
        		"@SQLParam("+'"'+"propertyValue"+'"'+") Object propertyValue);");
        return strResult.toString();
	}
    /** 创建修改方法 */
    private String createUpdate(List<FieldDefination> fieldList){
    	List<FieldDefination> keyList = findKey(fieldList);
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** update "+ClassName+" by entity */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"update "+tableName+" set ");
        strResult.append(createUpdateSomeCon(fieldList));
        strResult.append(" where  "+getUpdateConditions(keyList)+" \")"+ONE_HC);
        strResult.append(ONE_TAB+"public void update(@SQLParam("+'"'+"obj"+'"'+") "+ClassName+" "+ClassNameBl+");");
        return strResult.toString();
    }
    private List<FieldDefination> findKey(List<FieldDefination> fieldList){
    	List<FieldDefination> keyList = new ArrayList<FieldDefination>();
		for (FieldDefination fieldDefination : fieldList) {
			if(fieldDefination.getIsKey())
			{
				keyList.add(fieldDefination);
			}
		}
		return keyList;
    }
    private String getUpdateConditions(List<FieldDefination> keyList){
    	StringBuffer sb = new StringBuffer(); 
    	int flag = 0; 
		for (FieldDefination fieldDefination : keyList) {
			if(flag !=0)
			{
				sb.append(" and ");
			}
			sb.append(" "+ fieldDefination.getFieldCode()+"=:"+PRE+"."+fieldDefination.getFieldName());
			flag++;
		}
		return sb.toString();
    	
    }
    /** 修改方法的 一些代码 */
    private String createUpdateSomeCon(List<FieldDefination> fieldList){
        StringBuffer strResult = new StringBuffer();
        String theZd = null;
        String theField = null;
        for(FieldDefination fieldDefination : fieldList) {
        	//如果不是主键
        	if(!fieldDefination.getIsKey())
        	{
        		theZd = fieldDefination.getFieldCode();
                theField = StrOper.delLine(fieldDefination.getFieldCode());
                strResult.append("`"+theZd+"`"+"=:obj."+theField+", ");
        	}
        }
        
        return strResult.substring(0, strResult.length()-2);
    }
    
    /** 按ID查询 */
    private String createFindById(List<FieldDefination> fieldList){
    	List<FieldDefination> keyList = findKey(fieldList);
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" find by ID , */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select ");
        strResult.append(view);
        strResult.append(" from "+tableName+" "+aliasName+" where  "+ keyConditions +"\")"+ONE_HC);
        strResult.append(ONE_TAB+"public "+ClassName+" findById"+getDeleteParam(keyList)+";");
        return strResult.toString();
    }
    
    /** 按对象查询 */
    private String createFindByExample(List<FieldDefination> fieldList){
    	List<FieldDefination> keyList = findKey(fieldList);
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" find by example , */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select ");
        strResult.append(view);
        strResult.append(" from "+tableName+" "+aliasName+" "+createFilterClause(fieldList)+"\")"+ONE_HC);
        strResult.append(ONE_TAB+"public List<"+ClassName+"> findByExample(@SQLParam("+'"'+"obj"+'"'+") "+ClassName+" "+ClassNameBl+");");
        return strResult.toString();
    }
    
    /** 按对象和属性集合查询 */
    private String createFindByExampleAndColletion(List<FieldDefination> fieldList){
    	List<FieldDefination> keyList = findKey(fieldList);
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" 按对象和属性集合查询 */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select ");
        strResult.append(view);
        strResult.append(" from "+tableName+" "+aliasName+" "+createFilterClause(fieldList)+"  and ##(:property) in (:list)  \")"+ONE_HC);
        strResult.append(ONE_TAB+"public List<"+ClassName+"> findByExample(@SQLParam("+'"'+"obj"+'"'+") "+ClassName+" "+ClassNameBl+" ,@SQLParam(\"property\") String propertyName,@SQLParam(\"list\") List list );");
        return strResult.toString();
    }
    
    /** 按对象和集合模糊查询 */
    private String createFindFuzzzyByExampleAndColletion(List<FieldDefination> fieldList){
    	List<FieldDefination> keyList = findKey(fieldList);
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" 按对象模糊查询，只会对属性是字符的属性才模糊查询 */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select ");
        strResult.append(view);
        strResult.append(" from "+tableName+" "+aliasName+" "+createFuzzyFilterClause(fieldList)+"  and ##(:property) in (:list)  \")"+ONE_HC);
        strResult.append(ONE_TAB+"public List<"+ClassName+"> findFuzzyByExample(@SQLParam("+'"'+"obj"+'"'+") "+ClassName+" "+ClassNameBl+" ,@SQLParam(\"property\") String propertyName,@SQLParam(\"list\") List list );");
        return strResult.toString();
    }
    
    /** 
     * 按对象模糊查询
     *  */
    private String createFindFuzzzyByExample(List<FieldDefination> fieldList){
    	List<FieldDefination> keyList = findKey(fieldList);
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" 按对象模糊查询 , */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select ");
        strResult.append(view);
        strResult.append(" from "+tableName+" "+aliasName+" "+createFuzzyFilterClause(fieldList)+"\")"+ONE_HC);
        strResult.append(ONE_TAB+"public List<"+ClassName+"> findFuzzyByExample(@SQLParam("+'"'+"obj"+'"'+") "+ClassName+" "+ClassNameBl+");");
        return strResult.toString();
    }
    
    /** 按对象查询 */
    private String createFindFuzzyByProperty(List<FieldDefination> fieldList){
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" find by property fuzzy , */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select ");
        strResult.append(view);
        strResult.append(" from "+tableName+" "+aliasName+" where "+aliasName+".##(:propertyName) like CONCAT('%',CONCAT(:propertyValue,'%'))  \")"+ONE_HC);
        strResult.append(ONE_TAB+"public List<"+ClassName+"> findFuzzyByProperty(@SQLParam("+'"'+"propertyName"+'"'+") " +
        		"String propertyName," + "@SQLParam("+'"'+"propertyValue"+'"'+") String propertyValue);");
        return strResult.toString();
    }
    
    
    /**
     *  按属性查询查询 
     **/
    private String createFindByProperty(List<FieldDefination> fieldList){
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" find by property , */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select ");
        strResult.append(view);
        strResult.append(" from "+tableName+" "+aliasName+" where "+aliasName+".##(:propertyName)=:propertyValue \")"+ONE_HC);
        strResult.append(ONE_TAB+"public List<"+ClassName+"> findByProperty(@SQLParam("+'"'+"propertyName"+'"'+") " +
        		"String propertyName," + "@SQLParam("+'"'+"propertyValue"+'"'+") Object propertyValue);");
        return strResult.toString();
    }
    
   /** 查询、添加用到的字段 */
    private String createOneAllFilesCon(List<FieldDefination> fieldList){
        StringBuffer strView = new StringBuffer();
        String theZd = null;
        for(FieldDefination fieldDefination : fieldList) {
            theZd = fieldDefination.getFieldCode();
            strView.append(aliasName+".`"+theZd+"`, ");
        }
        return strView.substring(0, strView.length()-2);
    }
    /** where 子句  */
    private String createFuzzyFilterClause(List<FieldDefination> fieldList){
        StringBuffer strResult = new StringBuffer(" WHERE 1=1 ");
        String theZd = null;
        String theField = null;
        for(FieldDefination fieldDefination : fieldList) {
        	 if("String".equals(fieldDefination.getFieldType()))
        	 {
        		 theZd = fieldDefination.getFieldCode();
                 theField = StrOper.delLine(fieldDefination.getFieldCode());
                 String condition = addIf(fieldDefination.getFieldType(),fieldDefination.getFieldName()," and "+aliasName+".`" + theZd+"` like CONCAT('%',CONCAT(:obj."+theField+",'%')) ");
                 strResult.append(condition);
        	 }
        	 else if("Date".equalsIgnoreCase(fieldDefination.getFieldType())){
        		 theZd = fieldDefination.getFieldCode();
                 theField = StrOper.delLine(fieldDefination.getFieldCode());
        		 String ifStr = " and " +
        		 		"to_days("+aliasName+".`"+ fieldDefination.getFieldCode()+"`) = to_days(:obj."+StrOper.delLine(fieldDefination.getFieldCode())+")"; 
//        		 		"TIMESTAMPDIFF(DAY,"+aliasName+".`"+ fieldDefination.getFieldCode()+"`,:obj."+StrOper.delLine(fieldDefination.getFieldCode())+")=0  ";
        		 String condition = addIf(fieldDefination.getFieldType(),fieldDefination.getFieldName(),ifStr);
                 strResult.append(condition);
        	 }
        	 else{
        		 theZd = fieldDefination.getFieldCode();
                 theField = StrOper.delLine(fieldDefination.getFieldCode());
                 String condition = addIf(fieldDefination.getFieldType(),fieldDefination.getFieldName()," and " +aliasName+".`"+ theZd+"`=:obj."+theField);
                 strResult.append(condition);
        	 }
        }
        return strResult.toString();
    }
    
    /** where 子句  */
    private String createFilterClause(List<FieldDefination> fieldList){
        StringBuffer strResult = new StringBuffer(" WHERE 1=1 ");
        for(FieldDefination fieldDefination : fieldList) {
        	strResult.append(" \"+ "+ClassName+PACK_DAO_CLASS_POSTFIX+"."+getColumnWhereName(fieldDefination.getFieldName())+"+\"");
        }
        return strResult.toString();
    }
    /** 按对象查询 */
    private String createAssembling(List<FieldDefination> fieldList){
    	List<FieldDefination> keyList = findKey(fieldList);
        StringBuffer strResult = new StringBuffer();
        strResult.append(ONE_TAB+"/** "+ClassName+" find by 自己定义的sql语句 , */"+ONE_HC);
        strResult.append(ONE_TAB+"@SQL("+'"'+"select ");
        strResult.append(view);
        strResult.append(" from "+tableName+" "+aliasName+" where 1=1 and  ##(:where) \")"+ONE_HC);
        strResult.append(ONE_TAB+"public List<"+ClassName+"> findCustomWhere(@SQLParam("+'"'+"where"+'"'+") String where);");
        return strResult.toString();
    }
    public String createConditions(List<FieldDefination> fieldList){
    	StringBuffer sb = new StringBuffer();
    	for (FieldDefination fieldDefination : fieldList) {
    		if("Date".equalsIgnoreCase(fieldDefination.getFieldType()))
    		{
    			String ifStr =" and to_days("+aliasName+".`"+ fieldDefination.getFieldCode()+"`) = to_days(:obj."+StrOper.delLine(fieldDefination.getFieldCode())+")"; 
//    				" and TIMESTAMPDIFF(DAY,"+aliasName+".`"+ fieldDefination.getFieldCode()+"`,:obj."+StrOper.delLine(fieldDefination.getFieldCode())+")=0  ";
    			sb.append("public static final String "+getColumnWhereName(fieldDefination.getFieldName()) +"=\""+addIf(fieldDefination.getFieldType(),fieldDefination.getFieldName(),ifStr)+"\";"+TWO_HC);
    		}
    		else{
    			sb.append("public static final String "+getColumnWhereName(fieldDefination.getFieldName()) +"=\""+addIf(fieldDefination.getFieldType(),fieldDefination.getFieldName()," and " +aliasName+".`"+ fieldDefination.getFieldCode()+"`=:obj."+StrOper.delLine(fieldDefination.getFieldCode()))+"\";"+TWO_HC);
    		}
		}
    	return sb.toString();
    }
    public String getColumnWhereName(String fileName){
    	return "WHERE_"+fileName.toUpperCase();
    }
    
}
