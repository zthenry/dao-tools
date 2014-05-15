package com.sw.write.impl;

import java.util.List;

import com.sw.metadata.EntityDefination;
import com.sw.templet.ITemplet;
import com.sw.templet.impl.DaoTempletImpl;
import com.sw.templet.impl.EntityTempletImpl;
import com.sw.tool.ISysCharset;
import com.sw.tool.StrOper;
import com.sw.tool.XmlCreater;
import com.sw.write.IEntityWriter;

public class EntityWriterImpl implements IEntityWriter,ISysCharset {

    /**
     * @param entityList 实体类数据
     * @param exportPath 输入路径
     */
    public EntityWriterImpl(List<EntityDefination> entityList,String exportPath){
        this.entityList = entityList;
        this.exportPath = exportPath;
    }
    
    private String exportPath;
    private List<EntityDefination> entityList;
    
    /**
     * 创建实体类
     */
    public void initEntityWriter(){
        for (EntityDefination entityDefination : entityList) {
            ITemplet bean = new EntityTempletImpl(entityDefination);
            ITemplet dao = new DaoTempletImpl(entityDefination);
            createEntity(bean.templetContent(), entityDefination.getTableCode(), PACK_ENTITY_CLASS_POSTFIX, PACK_ENTITY);
            createEntity(dao.templetContent(), entityDefination.getTableCode(), PACK_DAO_CLASS_POSTFIX, PACK_DAO);
        }
    }
    
    /**
     * 创建类
     * @param entityCon
     * @param className
     * @param saveWjjPath
     */
    public void createEntity(String entityCon, String tableCode, String postfix, String packPath){
        String fileName = StrOper.upShouAndDelLine(tableCode)+postfix+".java";
        String filePath = exportPath + "/" + packPath;
        XmlCreater.CreatFile(filePath, fileName);
        
        String exPath = filePath + "/" +fileName;
        XmlCreater.appendMethodA(exPath, StrOper.changeContentChar(entityCon, oldChar, newChar));
    }
    
    public void createSQL(String entityCon, String tableCode, String folderPath) {
    	String fileName = StrOper.delLine(tableCode)+".sql";
        String filePath = exportPath + "/" + folderPath;
        XmlCreater.CreatFile(filePath, fileName);
        String exPath = filePath + "/" +fileName;
        XmlCreater.appendMethodA(exPath, StrOper.changeContentChar(entityCon, oldChar, newChar));
    }
}
