package com.sw.templet.impl;

import java.util.List;

import com.sw.metadata.EntityDefination;
import com.sw.metadata.FieldDefination;
import com.sw.templet.ITemplet;
import com.sw.tool.ISysCharset;
import com.sw.tool.StrOper;

public class EntityTempletImpl implements ITemplet, ISysCharset {

  private EntityDefination entityDefination;

  private String ClassNameBl = null;

  private String ClassName = null;

  public EntityTempletImpl(EntityDefination entityDefination) {
    this.entityDefination = entityDefination;
    this.ClassNameBl = StrOper.delLine(entityDefination.getTableCode());
    this.ClassName = StrOper.upShouAndDelLine(entityDefination.getTableCode());
  }

  /**
   * 获取实体类的字符串描述
   * 
   * @return
   */
  public String templetContent() {
    return createContent();
  }

  /**
   * 整个类的字符串描述
   * 
   * @return
   */
  private String createContent() {
    StringBuffer entityCon = new StringBuffer();
    entityCon.append("package " + entityDefination.getPackPath() + "." + PACK_ENTITY + ";" + TWO_HC);
    entityCon.append("import java.util.*;" + TWO_HC);
    entityCon.append("/** " + entityDefination.getTableName() + " (" + notic + ") */" + ONE_HC);
    entityCon.append("public class " + ClassName + PACK_ENTITY_CLASS_POSTFIX + " {" + TWO_HC);
    entityCon.append(createEntityMethod(entityDefination.getFieldList()));
    entityCon.append("}" + ONE_HC);
    return entityCon.toString();
  }

  /**
   * 属性和方法的字符串描述
   * 
   * @param fieldList
   * @return
   */
  private String createEntityMethod(List<FieldDefination> fieldList) {
    StringBuffer strAttr = new StringBuffer();
    StringBuffer strMeth = new StringBuffer();

    String blFileUpszm = null;
    String blFileDwszm = null;
    for(FieldDefination fieldDefination : fieldList) {
      blFileDwszm = StrOper.delLine(fieldDefination.getFieldCode());
      blFileUpszm = StrOper.upShouzm(blFileDwszm);
      // 属性
      if(fieldDefination.getFieldComment() == null || "".equals(fieldDefination.getFieldComment().trim())) {
        strAttr.append(ONE_TAB + "/** " + fieldDefination.getFieldName() + " */" + ONE_HC);
      }
      else {
        strAttr.append(ONE_TAB + "/** " + fieldDefination.getFieldComment() + " */" + ONE_HC);
      }
      strAttr.append(ONE_TAB + "protected " + fieldDefination.getFieldType() + " " + blFileDwszm + ";" + TWO_HC);
      // 方法
      strMeth.append(ONE_TAB + "public " + fieldDefination.getFieldType() + " get" + blFileUpszm + "() {" + ONE_HC);
      strMeth.append(TWO_TAB + "return " + blFileDwszm + ";" + ONE_HC);
      strMeth.append(ONE_TAB + "}" + ONE_HC);
      strMeth.append(ONE_TAB + "public void set" + blFileUpszm + "(" + fieldDefination.getFieldType() + " "
          + blFileDwszm + ") {" + ONE_HC);
      strMeth.append(TWO_TAB + "this." + blFileDwszm + " = " + blFileDwszm + ";" + ONE_HC);
      strMeth.append(ONE_TAB + "}" + ONE_HC);
    }
    return strAttr.toString() + strMeth.toString();
  }
}
