/**
 * 
 */
package com.honda.cart2.dataaccess.eventprocessing;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager; import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.honda.cart2.common.dataaccess.DAOHelper;
import com.honda.cart2.common.dvo.AbstractDVO;
import com.honda.cart2.common.dvo.CodeDVO;
import com.honda.cart2.common.dvo.HashMapDVO;
import com.honda.cart2.common.dvo.ListDVO;
import com.honda.cart2.common.dvo.RequestDVO;
import com.honda.cart2.common.dvo.ResponseDVO;
import com.honda.cart2.common.exception.ApplicationException;
import com.honda.cart2.common.util.ApplicationConstantsIF;
import com.honda.cart2.common.util.Utility;

/**
 * @author VCC89569
 *
 */
@Component
public class EventApplicationDAO extends DAOHelper implements EventProcessingSQLIF{
	private String CLASS_NAME = EventProcessingDAO.class.getName();
    private ResponseDVO responseDVO = null;
    private EventPartDVO eventPartDVO = null;
    private Logger logger = LogManager.getLogger(EventApplicationDAO.class);
    
    
    public ResponseDVO populateDropDown() throws ApplicationException {
        String location = CLASS_NAME + ".populateDropdowns()";
        logger.debug("Entered : " + location);
        responseDVO = new ResponseDVO();
        
        ArrayList arlEventNameList = new ArrayList();
        ArrayList arlHamPlantList = new ArrayList();
        ArrayList arlProcGrpList = new ArrayList();
        ArrayList arlPaymentCurrList = new ArrayList();
        ArrayList arlDesignSectionList = new ArrayList();
        ArrayList arlCarryExclusiveList = new ArrayList();
        ArrayList arlCostCategoryList = new ArrayList();
        
       
        try{
        	arlEventNameList = (ArrayList) getJdbcTemplateObject().queryForList(EventProcessingSQLIF.EVENT_NAME_DD);        	
        	arlHamPlantList = (ArrayList) getJdbcTemplateObject().queryForList(replaceSchemaNames(EventProcessingSQLIF.HAM_PLANT_DD));
        	arlProcGrpList = (ArrayList) getJdbcTemplateObject().queryForList(replaceSchemaNames(EventProcessingSQLIF.PROC_GRP_DD));
        	arlPaymentCurrList = (ArrayList) getJdbcTemplateObject().queryForList(replaceSchemaNames(EventProcessingSQLIF.PAYMENT_CURR_DD));
        	arlDesignSectionList = (ArrayList) getJdbcTemplateObject().queryForList(replaceSchemaNames(EventProcessingSQLIF.DESIGN_SECTION_DD));
        	arlCarryExclusiveList = (ArrayList) getJdbcTemplateObject().queryForList(replaceSchemaNames(EventProcessingSQLIF.CARRY_EXCLUSIVE_DD));
        	arlCostCategoryList = (ArrayList) getJdbcTemplateObject().queryForList(replaceSchemaNames(EventProcessingSQLIF.COST_CATEGORY_DD));
        	
            HashMap hMap = new HashMap();
            hMap.put(ApplicationConstantsIF.HMAP_KEY.EVENT_NAME,arlEventNameList);
            hMap.put(ApplicationConstantsIF.HMAP_KEY.HAM_PLANT,arlHamPlantList);
            hMap.put(ApplicationConstantsIF.HMAP_KEY.PROC_GRP,arlProcGrpList);
            hMap.put(ApplicationConstantsIF.HMAP_KEY.PAYMENT_CURR,arlPaymentCurrList);
            hMap.put(ApplicationConstantsIF.HMAP_KEY.DESIGN_SECT,arlDesignSectionList);
            hMap.put(ApplicationConstantsIF.HMAP_KEY.CARRY_EXCLUSIVE,arlCarryExclusiveList);
            hMap.put(ApplicationConstantsIF.HMAP_KEY.COST_CATEGORY,arlCostCategoryList);
            
            HashMapDVO hashMapDVO = new HashMapDVO();
            hashMapDVO.setM_hmpMap(hMap);
            responseDVO.setM_abstractDVO(hashMapDVO);
        } catch (Exception sqlException) {
            
        } finally {
        }
        logger.debug("Exiting : " + location);
        return responseDVO;
    }
    
    
	public ResponseDVO searchEventApplications(RequestDVO requestDVO){
		
		logger.debug("\n Entering searchEventApplications()");
		
		Connection connection = null;
        CallableStatement callableSt = null;
        ResultSet rs = null;
        EventApplicationDVO eventApplicationDVO = null;
        
        responseDVO = new ResponseDVO();
        ListDVO outDVO = new ListDVO(); 
        ArrayList arlEventApplicationList = new ArrayList();
        String mtcType1[]=new String[15];
       
        try{
        	eventApplicationDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
        	 for (int i = 0; i < eventApplicationDVO.getM_strMTCType1().size(); i++) {
        		 mtcType1[i] = eventApplicationDVO.getM_strMTCType1().get(i);
             }
        	//checkFlagMap = freezeCheck(eventPartDVO);
        	final String procedureCall = replaceSchemaNames("{call --PROC_SCHEMA--.FCS628P(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
        			"?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            connection = getJdbcTemplateObject().getDataSource().getConnection();
            System.out.println();
            callableSt = connection.prepareCall(procedureCall);
            callableSt.setString(1, eventApplicationDVO.getM_strEventName());
            callableSt.setBigDecimal(2, eventApplicationDVO.getM_decEventRevNo());
            callableSt.setString(3, eventApplicationDVO.getM_strPartNumber());
            callableSt.setString(4, eventApplicationDVO.getM_strPartColor());
            callableSt.setString(5, eventApplicationDVO.getM_strSupplierNumber());
            callableSt.setString(6, eventApplicationDVO.getM_strTargetPlant());
            callableSt.setString(7, eventApplicationDVO.getM_strProcSection());
            callableSt.setString(8, eventApplicationDVO.getM_strDesignSection());
            callableSt.setString(9, eventApplicationDVO.getM_strFeatureCode()); 
            callableSt.setString(10, eventApplicationDVO.getM_strModel());
            callableSt.setString(11, mtcType1[0]);
            callableSt.setString(12, mtcType1[1]);
            callableSt.setString(13, mtcType1[2]);
            callableSt.setString(14, mtcType1[3]);
            callableSt.setString(15, mtcType1[4]);
            callableSt.setString(16, mtcType1[5]);
            callableSt.setString(17, mtcType1[6]);
            callableSt.setString(18, mtcType1[7]);
            callableSt.setString(19, mtcType1[8]);
            callableSt.setString(20, mtcType1[9]);
            callableSt.setString(21, mtcType1[10]);
            callableSt.setString(22, mtcType1[11]);
            callableSt.setString(23, mtcType1[12]);
            callableSt.setString(24, mtcType1[13]);
            callableSt.setString(25, mtcType1[14]);
            callableSt.setString(26, eventApplicationDVO.getM_strMTCOption());
            callableSt.setString(27, eventApplicationDVO.getM_strCategory());
            callableSt.setString(28, null);
            callableSt.setBigDecimal(29, new BigDecimal(0));
            callableSt.setString(30, 
            		((null != eventApplicationDVO.getM_strSearchType() && !eventApplicationDVO.getM_strSearchType().isEmpty()) ? 
            				eventApplicationDVO.getM_strSearchType(): "SEARCH"));
            callableSt.setString(31, "TRUE".equalsIgnoreCase(eventApplicationDVO.getM_strIsSupply()) ? "Y" : "N" );
            callableSt.setString(32, "true".equalsIgnoreCase(eventApplicationDVO.getM_strSpecificSearch()) ? "Y" : "N" );
            callableSt.setString(33, eventApplicationDVO.getM_strViewMTO());
            callableSt.setString(34, "FCEPAS1");
            //callableSt.setString(18, "MMT0323"); //TODO: replace mmt id with logged in user id
            callableSt.setString(35, requestDVO.getM_userInfoDVO().getM_strUserLogonId()); //TODO: replace mmt id with logged in user id
            callableSt.registerOutParameter(36, Types.VARCHAR);//need to pass is supply flag to get result from mainframe
            callableSt.registerOutParameter(37, Types.VARCHAR);
            boolean results = false;
            results = callableSt.execute();
            callableSt.getString(36);
            callableSt.getString(37);
          
			int rscount = -1;
		    do {
		       if (results) {
		    	  rs = callableSt.getResultSet();
		          while(rs.next()) {
		        	  /*System.out.println(rs.getString(1)+" | "+rs.getString(2)+" | "+rs.getString(3)+" | "+rs.getString(4)+" | "
		        			  +rs.getString(5)+" | "+rs.getString(6)+" |"+rs.getString(7)+" | "+rs.getString(8)+" | "+rs.getString(9)+" | "
		        			  +rs.getString(10)+" | "+rs.getString(11)+" | "+rs.getString(12)+" | "+rs.getString(13)+" | "+rs.getString(14)
		        			  +" | "+rs.getString(15)+" | "+rs.getString(16)+" | "+rs.getString(17)+" | "+rs.getString(18)+" | "+rs.getString(19)
		        			  +" | "+rs.getString(20)+" | "+rs.getString(21));*/
		        	  
		        	  EventApplicationDVO obj = new EventApplicationDVO();
		        	  
		        	  obj.setM_strEventName(eventApplicationDVO.getM_strEventName());
		        	  obj.setM_decEventRevNo(eventApplicationDVO.getM_decEventRevNo());
		        	  obj.setM_strPartNumber(Utility.trimStringValue(rs.getString(3)));
		        	  obj.setM_strPartName(Utility.trimStringValue(rs.getString(4)));
		        	  obj.setM_strPartColor(Utility.trimStringValue(rs.getString(5)));
		        	  obj.setM_strSupplierNumber(Utility.trimStringValue(rs.getString(6)));
		        	  obj.setM_strSupplierName(Utility.trimStringValue(rs.getString(7)));
		        	  obj.setM_strDesignSection(Utility.trimStringValue(rs.getString(11)));
		        	  obj.setM_strHPlant(Utility.trimStringValue(rs.getString(8)));
		        	  obj.setM_strTPlant(Utility.trimStringValue(rs.getString(9)));
		        	  obj.setM_strModel(Utility.trimStringValue(rs.getString(10)));
		        	  obj.setM_strMTCType(Utility.trimStringValue(rs.getString(12)));
		        	  obj.setM_strMTCOption(Utility.trimStringValue(rs.getString(13)));
		        	  obj.setM_strGrade(rs.getString(19));//19 grade
		        	  obj.setM_strAtMt(rs.getString(21));//21 trans type
		        	  obj.setM_strCategory(Utility.trimStringValue(rs.getString(14)));
		        	  obj.setM_intQuantity(rs.getBigDecimal(16).intValue());
		        	  //obj.setM_intRate(rs.getBigDecimal(15).intValue());
		        	  obj.setM_bRate(rs.getBigDecimal(15));
		        	  //need to fill is supply flag here
		        	  obj.setM_strShipToCode(Utility.trimStringValue(rs.getString(17)));
		        	  obj.setM_strIsSupply(rs.getString(20).trim().toUpperCase());//20
		        	  obj.setM_strIsCopyBOM(rs.getString(22).trim());//22
		        	  obj.setM_strDataMaintProgNo(rs.getString(23).trim());//23
		        	  obj.setM_bSelected(false);
		        	  arlEventApplicationList.add(obj);
	              }
		          rs.close();
		          break;
		       } else {
		    	   rscount = callableSt.getUpdateCount();
		       }
		       results = callableSt.getMoreResults();
		    } while (results || rscount != -1);
		    
		 
           }catch (SQLException e) {

           e.printStackTrace();

           } finally {
        	   if(connection != null){
        		   try {
        			   connection.close();
        		   } catch (SQLException e) {
        			   e.printStackTrace();
        		   }
        	   }   
        	   if(callableSt != null){
        		   try {
        			   callableSt.close();
        		   } catch (SQLException e) {
        			   e.printStackTrace();
        		   }
        	   }
        	   if(rs != null){
        		   try {
        			   rs.close();
        		   } catch (SQLException e) {
        			   e.printStackTrace();
        		   }
        	   }
            }
           
           outDVO.setList(arlEventApplicationList);
           responseDVO.setM_abstractDVO(outDVO);

           logger.debug("\n Exiting searchEventApplications()");
        return responseDVO;
	}
	
	public ResponseDVO freezeCheck(RequestDVO requestDVO) {
		
		logger.debug("\n Entering freezeCheck()");
		HashMap<String , Boolean> checkFlagMap =  new HashMap<String, Boolean>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		EventApplicationDVO eventApplicationDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
		ResponseDVO responseDVO = new ResponseDVO();
		
		String scode = null;
		if(null != eventApplicationDVO){  
			if(null != eventApplicationDVO.getM_strEventName() && !eventApplicationDVO.getM_strEventName().trim().isEmpty()){
				resultList = getJdbcTemplateObject().queryForList(EventProcessingSQLIF.FREEZE_CODE_CHECK , new Object[]{eventApplicationDVO.getM_strEventName() , eventApplicationDVO.getM_decEventRevNo()});  
				for(Map<String , Object> result : resultList){
					scode = String.valueOf(result.get(ApplicationConstantsIF.APP_CONSTANTS.UPD_ACTIVITY_CODE_COLUMN));
					if(null != scode){
						scode = scode.trim();
						if("EE".equalsIgnoreCase(scode)){
							checkFlagMap.put(ApplicationConstantsIF.HMAP_KEY.ENTIRE_EVENT_FLAG, true);
						} else if("AI".equalsIgnoreCase(scode)){
							checkFlagMap.put(ApplicationConstantsIF.HMAP_KEY.ALL_INV_FLAG, true);
						} else if("AP".equalsIgnoreCase(scode)){
							checkFlagMap.put(ApplicationConstantsIF.HMAP_KEY.ALL_APP_FLAG, true);
						} else if("BC".equalsIgnoreCase(scode)){
							checkFlagMap.put(ApplicationConstantsIF.HMAP_KEY.BASE_COST_FLAG, true);
						} else if("CHI".equalsIgnoreCase(scode)){
							checkFlagMap.put(ApplicationConstantsIF.HMAP_KEY.COM_HAM_INV_FLAG, true);
						} else if("DC".equalsIgnoreCase(scode)){
							checkFlagMap.put(ApplicationConstantsIF.HMAP_KEY.DRAW_COST_FLAG, true);
						} else if("EHI".equalsIgnoreCase(scode)){
							checkFlagMap.put(ApplicationConstantsIF.HMAP_KEY.EXCL_HAM_INV_FLAG, true);
						} else if("ESI".equalsIgnoreCase(scode)){
							checkFlagMap.put(ApplicationConstantsIF.HMAP_KEY.EXCL_SUPL_INV_FLAG, true);
						} else if("F".equalsIgnoreCase(scode)){
							checkFlagMap.put(ApplicationConstantsIF.HMAP_KEY.FEATURE_FLAG, true);
						} else if("SC".equalsIgnoreCase(scode)){
							checkFlagMap.put(ApplicationConstantsIF.HMAP_KEY.ALL_SUP_COST_FLAG, true);
						} else if("TC".equalsIgnoreCase(scode)){
							checkFlagMap.put(ApplicationConstantsIF.HMAP_KEY.TGT_COST_FLAG, true);
						}
					}
				}
			}
		}
		
		
		HashMapDVO hashMapDVO = new HashMapDVO();
		
		hashMapDVO.setM_hmpMap(checkFlagMap);
		responseDVO.setM_abstractDVO(hashMapDVO);
		
		logger.debug("\n Exiting freezeCheck()");
		return responseDVO;
	}
	
	public ResponseDVO getPartName(RequestDVO requestDVO){
		
		logger.debug("\n Entering getPartName()");
		
		ResponseDVO responseDVO = new ResponseDVO();
		EventApplicationDVO inDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
		ListDVO outDVO = new ListDVO();
		
		List<String> outList = getJdbcTemplateObject().query(replaceSchemaNames(GET_PART_NAME_EVENT_APPLICATION),new Object[]{inDVO.getM_strPartNumber()},
				new RowMapper<String>() {

					@Override
					public String mapRow(
							ResultSet rs,
							int arg1)
							throws SQLException {

						return rs.getString("BASIC_PART_NAME");
					}
					}
				);
		
		outDVO.setList(outList);
		responseDVO.setM_abstractDVO(outDVO);
		
		logger.debug("\n Exiting getPartName()");
		
		return responseDVO;
	}
	
	public ResponseDVO getNewPartName(RequestDVO requestDVO){
		
		logger.debug("\n Entering getNewPartName()");
		
		ResponseDVO responseDVO = new ResponseDVO();
		EventApplicationDVO inDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
		ListDVO outDVO = new ListDVO();
		
		List<String> outList = getJdbcTemplateObject().query(GET_NEW_PART_NAME_APPLICATION,new Object[]{inDVO.getM_strPartNumber()},
				new RowMapper<String>() {

					@Override
					public String mapRow(
							ResultSet rs,
							int arg1)
							throws SQLException {

						return rs.getString("PART_NAME");
					}
					}
				);
		
		outDVO.setList(outList);
		responseDVO.setM_abstractDVO(outDVO);
		
		logger.debug("\n Exiting getNewPartName()");
		
		return responseDVO;
	}
	
	public ResponseDVO getSupplierName(RequestDVO requestDVO){
		
		logger.debug("\n Entering getSupplierName()");
		
		ResponseDVO responseDVO = new ResponseDVO();
		EventApplicationDVO inDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
		AbstractDVO outDVO = new AbstractDVO();
		
		List<Map<String,Object>> outList  = getJdbcTemplateObject().queryForList(replaceSchemaNames(GET_SUPPLIER_NAME_EVENT_APPLICATION),new Object[]{inDVO.getM_strSupplierNumber()}/*,
				new RowMapper<AbstractDVO>() {

					@Override
					public AbstractDVO mapRow(
							ResultSet rs,
							int arg1)
							throws SQLException {

						
						dvo.setObject(Utility.trimIfString(rs.getString("SUPPLIER_NAME")));

						return dvo;
					}
					}*/
				);
		
		if(outList != null && outList.size() > 0)
			outDVO.setObject((String)outList.get(0).get("SUPPLIER_NAME"));
		
		responseDVO.setM_abstractDVO(outDVO);
		
		logger.debug("\n Exiting getSupplierName()");
		
		return responseDVO;
	}
	
	public ResponseDVO getAvailableFeatures(RequestDVO requestDVO){
		
		logger.debug("\n Entering getAvailableFeatures()");
		
		ResponseDVO responseDVO = new ResponseDVO();
		List<Map<String,Object>> results = null;
		List<CodeDVO> outList = new ArrayList<CodeDVO>();
	    LinkedHashMap<String, Object> paramMap = new LinkedHashMap<String, Object>();
		
		EventApplicationDVO inDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
		
	    paramMap.put("PH_COST_EVENT_NAME", inDVO.getM_strEventName());
	    paramMap.put("AND EVENT_REV_NO", inDVO.getM_decEventRevNo());
	    paramMap.put("AND PART_NO", inDVO.getM_strPartNumber());
	    paramMap.put("AND TRIM(PART_COLOR_CODE)", inDVO.getM_strPartColor());
	    paramMap.put("AND TRIM(SUPPLIER_NO)", inDVO.getM_strSupplierNumber());
	    paramMap.put("AND TRIM(PART_SECTION_CODE)", inDVO.getM_strDesignSection());
	    paramMap.put("AND TRIM(TGT_PLANT_LOC_CODE)",inDVO.getM_strTargetPlant());
	    paramMap.put("AND TRIM(TGT_MODEL_DEV_CODE)", inDVO.getM_strModel());
	    paramMap.put("AND TRIM(MTC_TYPE)", inDVO.getM_strMTCType1().get(0));
	    paramMap.put("AND TRIM(MTC_OPTION)", inDVO.getM_strMTCOption());

	    String whereStr = Utility.buildWhereClause(paramMap);
	    
		results = getJdbcTemplateObject().queryForList(GET_AVAILABLE_FEATURES.replace("--WHERE_CONDITION--", (whereStr == null || whereStr.equalsIgnoreCase("")) ? whereStr : " WHERE "+whereStr));
		
		for(Map map: results){
			
			CodeDVO obj = new CodeDVO();
			obj.setM_strCode((String)map.get("PRODUCT_EQUIP_CODE"));
			obj.setM_strDescription((String)map.get("PRODUCT_EQUIP_TEXT"));
			
			outList.add(obj);
		}
		
		ListDVO outDVO = new ListDVO();
		outDVO.setList(outList);
		
		responseDVO.setM_abstractDVO(outDVO);
		logger.debug("\n Exiting getAvailableFeatures()");
		
		return responseDVO;
	}
	
	public ResponseDVO getAssignedFeatures(RequestDVO requestDVO){
		
		logger.debug("\n Entering getAssignedFeatures()");
		
		ResponseDVO responseDVO = new ResponseDVO();
		List<Map<String,Object>> results = null;
		List<CodeDVO> outList = new ArrayList<CodeDVO>();
		
	    LinkedHashMap<String, Object> paramMap = new LinkedHashMap<String, Object>();
		
		EventApplicationDVO inDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
		
	    paramMap.put("PH_COST_EVENT_NAME", inDVO.getM_strEventName());
	    paramMap.put("AND EVENT_REV_NO", inDVO.getM_decEventRevNo());
	    paramMap.put("AND PART_NO", inDVO.getM_strPartNumber());
	    paramMap.put("AND TRIM(PART_COLOR_CODE)", inDVO.getM_strPartColor());
	    paramMap.put("AND TRIM(SUPPLIER_NO)", inDVO.getM_strSupplierNumber());
	    paramMap.put("AND TRIM(PART_SECTION_CODE)", inDVO.getM_strDesignSection());
	    paramMap.put("AND TRIM(TGT_PLANT_LOC_CODE)",inDVO.getM_strTargetPlant());
	    paramMap.put("AND TRIM(TGT_MODEL_DEV_CODE)", inDVO.getM_strModel());
	    paramMap.put("AND TRIM(MTC_TYPE)", inDVO.getM_strMTCType1().get(0));
	    paramMap.put("AND TRIM(MTC_OPTION)", inDVO.getM_strMTCOption());

	    String whereStr = Utility.buildWhereClause(paramMap);

		results = getJdbcTemplateObject().queryForList(GET_ASSIGNED_FEATURES.replace("--WHERE_CONDITION--", (whereStr == null || whereStr.equalsIgnoreCase("")) ? whereStr : " WHERE "+whereStr));
		
		for(Map map: results){
			
			CodeDVO obj = new CodeDVO();
			obj.setM_strCode((String)map.get("PRODUCT_EQUIP_CODE"));
			obj.setM_strDescription((String)map.get("PRODUCT_EQUIP_TEXT"));
			
			outList.add(obj);
		}
		
		ListDVO outDVO = new ListDVO();
		outDVO.setList(outList);
		
		responseDVO.setM_abstractDVO(outDVO);
		logger.debug("\n Exiting getAssignedFeatures()");
		
		return responseDVO;
	}
	
	public ResponseDVO addAvailableFeature(RequestDVO requestDVO){
		
		logger.debug("\n Entering addAvailableFeature()");
		
		ResponseDVO responseDVO = new ResponseDVO();
		EventApplicationDVO inDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
		
		getJdbcTemplateObject().update(ADD_FEATURE_EVENT_APPLICATION,   (inDVO.getM_strEventName() == null || inDVO.getM_strEventName().equalsIgnoreCase("") ) ? "":inDVO.getM_strEventName(),
																	     inDVO.getM_decEventRevNo(),
																	    (inDVO.getM_strPartNumber() == null || inDVO.getM_strPartNumber().equalsIgnoreCase("") ) ? "":inDVO.getM_strPartNumber(),
																	    (inDVO.getM_strPartColor() == null || inDVO.getM_strPartColor().equalsIgnoreCase("") ) ? "":inDVO.getM_strPartColor(),
																	    (inDVO.getM_strSupplierNumber() == null || inDVO.getM_strSupplierNumber().equalsIgnoreCase("") ) ? "":inDVO.getM_strSupplierNumber(),
																	    (inDVO.getM_strDesignSection() == null || inDVO.getM_strDesignSection().equalsIgnoreCase("") ) ? "":inDVO.getM_strDesignSection(),
																		(inDVO.getM_strTargetPlant() == null || inDVO.getM_strTargetPlant().equalsIgnoreCase("") ) ? "":inDVO.getM_strTargetPlant(),
																		(inDVO.getM_strModel() == null || inDVO.getM_strModel().equalsIgnoreCase("") ) ? "":inDVO.getM_strModel(),
																		(inDVO.getM_strMTCType1() == null || inDVO.getM_strMTCType1().size()==0 ) ? "":inDVO.getM_strMTCType1(),
																		(inDVO.getM_strMTCOption() == null || inDVO.getM_strMTCOption().equalsIgnoreCase("") ) ? "":inDVO.getM_strMTCOption(),
																		(inDVO.getM_strAvailableProductEquipCode() == null || inDVO.getM_strAvailableProductEquipCode().equalsIgnoreCase("") ) ? "":inDVO.getM_strAvailableProductEquipCode().trim(),requestDVO.getM_userInfoDVO().getM_strUserLogonId());
		
		HashMapDVO outDVO = new HashMapDVO();
		HashMap<String, String> outMap = new HashMap<String, String>();
		outMap.put(ApplicationConstantsIF.APP_CONSTANTS.OPERATION_STATUS, ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS);
		outDVO.setM_hmpMap(outMap);
		responseDVO.setM_abstractDVO(outDVO);
		
		logger.debug("\n Exiting addAvailableFeature()");
		
		return responseDVO;
	}
	
	public ResponseDVO removeAvailableFeature(RequestDVO requestDVO){
		
		logger.debug("\n Entering removeAvailableFeature()");
		
		ResponseDVO responseDVO = new ResponseDVO();
		EventApplicationDVO inDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
		
		getJdbcTemplateObject().update(REMOVE_FEATURE_EVENT_APPLICATION,  (inDVO.getM_strEventName() == null || inDVO.getM_strEventName().equalsIgnoreCase("") ) ? "":inDVO.getM_strEventName(),
																     	   inDVO.getM_decEventRevNo(),
																	      (inDVO.getM_strPartNumber() == null || inDVO.getM_strPartNumber().equalsIgnoreCase("") ) ? "":inDVO.getM_strPartNumber(),
																	      (inDVO.getM_strPartColor() == null || inDVO.getM_strPartColor().equalsIgnoreCase("") ) ? "":inDVO.getM_strPartColor(),
																	      (inDVO.getM_strSupplierNumber() == null || inDVO.getM_strSupplierNumber().equalsIgnoreCase("") ) ? "":inDVO.getM_strSupplierNumber(),
																	      (inDVO.getM_strDesignSection() == null || inDVO.getM_strDesignSection().equalsIgnoreCase("") ) ? "":inDVO.getM_strDesignSection(),
																		  (inDVO.getM_strTargetPlant() == null || inDVO.getM_strTargetPlant().equalsIgnoreCase("") ) ? "":inDVO.getM_strTargetPlant(),
																		  (inDVO.getM_strModel() == null || inDVO.getM_strModel().equalsIgnoreCase("") ) ? "":inDVO.getM_strModel(),
																		  (inDVO.getM_strMTCType1() == null || inDVO.getM_strMTCType1().size()==0 ) ? "":inDVO.getM_strMTCType1(),
																		  (inDVO.getM_strMTCOption() == null || inDVO.getM_strMTCOption().equalsIgnoreCase("") ) ? "":inDVO.getM_strMTCOption(),
																		  (inDVO.getM_strAssignedProductEquipCode() == null || inDVO.getM_strAssignedProductEquipCode().equalsIgnoreCase("") ) ? "":inDVO.getM_strAssignedProductEquipCode().trim());
		
		HashMapDVO outDVO = new HashMapDVO();
		HashMap<String, String> outMap = new HashMap<String, String>();
		outMap.put(ApplicationConstantsIF.APP_CONSTANTS.OPERATION_STATUS, ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS);
		outDVO.setM_hmpMap(outMap);
		responseDVO.setM_abstractDVO(outDVO);
		
		logger.debug("\n Exiting removeAvailableFeature()");
		
		return responseDVO;
	}
	
	public ResponseDVO getAvailableFeaturesCopy(RequestDVO requestDVO){
		
		logger.debug("\n Entering getAvailableFeaturesCopy()");
		
		ResponseDVO responseDVO = new ResponseDVO();
		List<Map<String,Object>> results = null;
		List<CodeDVO> outList = new ArrayList<CodeDVO>();
	    LinkedHashMap<String, Object> paramMap = new LinkedHashMap<String, Object>();
		
		EventApplicationDVO inDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
		
	    paramMap.put("PH_COST_EVENT_NAME", inDVO.getM_strEventName());
	    paramMap.put("AND EVENT_REV_NO", inDVO.getM_decEventRevNo());
	    paramMap.put("AND PART_NO", inDVO.getM_strPartNumber());
	    paramMap.put("AND TRIM(PART_COLOR_CODE)", inDVO.getM_strPartColor());
	    paramMap.put("AND TRIM(SUPPLIER_NO)", inDVO.getM_strSupplierNumber());
	    paramMap.put("AND TRIM(PART_SECTION_CODE)", inDVO.getM_strDesignSectionFromCopy());
	    paramMap.put("AND TRIM(TGT_PLANT_LOC_CODE)",inDVO.getM_strTargetPlantFromCopy());
	    paramMap.put("AND TRIM(TGT_MODEL_DEV_CODE)", inDVO.getM_strModelFromCopy());
	    paramMap.put("AND TRIM(MTC_TYPE)", inDVO.getM_strMTCTypeFromCopy1().get(0));
	    paramMap.put("AND TRIM(MTC_OPTION)", inDVO.getM_strMTCOptionFromCopy());

	    String whereStr = Utility.buildWhereClause(paramMap);
	    
		results = getJdbcTemplateObject().queryForList(GET_AVAILABLE_FEATURES_COPY.replace("--WHERE_CONDITION--", (whereStr == null || whereStr.equalsIgnoreCase("")) ? whereStr : " WHERE "+whereStr));
		
		for(Map map: results){
			
			CodeDVO obj = new CodeDVO();
			obj.setM_strCode((String)map.get("PRODUCT_EQUIP_CODE"));
			obj.setM_strDescription((String)map.get("PRODUCT_EQUIP_TEXT"));
			
			outList.add(obj);
		}
		
		ListDVO outDVO = new ListDVO();
		outDVO.setList(outList);
		
		responseDVO.setM_abstractDVO(outDVO);
		logger.debug("\n Exiting getAvailableFeaturesCopy()");
		
		return responseDVO;
	}
	
	public ResponseDVO getAssignedFeaturesCopy(RequestDVO requestDVO){
		
		logger.debug("\n Entering getAssignedFeaturesCopy()");
		
		ResponseDVO responseDVO = new ResponseDVO();
		List<Map<String,Object>> results = null;
		List<CodeDVO> outList = new ArrayList<CodeDVO>();
		
	    LinkedHashMap<String, Object> paramMap = new LinkedHashMap<String, Object>();
		
		EventApplicationDVO inDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();

		results = getJdbcTemplateObject().queryForList(GET_ASSIGNED_FEATURES_COPY);
		
		for(Map map: results){
			
			CodeDVO obj = new CodeDVO();
			obj.setM_strCode((String)map.get("PRODUCT_EQUIP_CODE"));
			obj.setM_strDescription((String)map.get("PRODUCT_EQUIP_TEXT"));
			
			outList.add(obj);
		}
		
		ListDVO outDVO = new ListDVO();
		outDVO.setList(outList);
		
		responseDVO.setM_abstractDVO(outDVO);
		logger.debug("\n Exiting getAssignedFeaturesCopy()");
		
		return responseDVO;
	}
	
	public ResponseDVO deleteBtnEventApplication(RequestDVO requestDVO){

		ResponseDVO responseDVO = new ResponseDVO();
		EventApplicationDVO inDVO = (EventApplicationDVO) requestDVO.getM_abstractDVO();
		
		return responseDVO;
	}
	
	public ResponseDVO cancelBtnEventApplication(RequestDVO requestDVO){

		ResponseDVO responseDVO = new ResponseDVO();
		EventApplicationDVO inDVO = (EventApplicationDVO) requestDVO.getM_abstractDVO();
		
		return responseDVO;
	}
	
	public ResponseDVO updateEventPartDetailsApplicationToAttachedBudget(RequestDVO requestDVO){
		logger.debug("\n Entering applyEventPartDetailsApplicationToAttachedBudget ");
		EventApplicationDVO evDVO = (EventApplicationDVO) requestDVO.getM_abstractDVO();
		RequestDVO requestDVOForBudget = new RequestDVO();
		requestDVOForBudget.setM_abstractDVO(evDVO);
		requestDVOForBudget.setM_userInfoDVO(requestDVO.getM_userInfoDVO());
		//String attachedBudgetEvent = validatePriorBudgetEventFreezCheck(requestDVOForFreez);
		String[] attachedBudgetEvent = validateActEventIsAttachedToBudget(requestDVOForBudget);
		ResponseDVO responseDVO = new ResponseDVO(); 
		if(attachedBudgetEvent!=null && !"".equalsIgnoreCase(attachedBudgetEvent[0])){
			EventApplicationDVO inDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
			List<EventApplicationDVO> inList = new ArrayList<EventApplicationDVO>();
			inList.add(inDVO);
			for(EventApplicationDVO obj : inList){
				//Need to add a parameter IsSupply to be update
				getJdbcTemplateObject().update(APPLY_EVENT_APPLICATION,obj.getM_bRate(),
																	   new BigDecimal(obj.getM_intQuantity()),
																	   obj.getM_strIsSupply()==null ? "": obj.getM_strIsSupply(),
																	   requestDVO.getM_userInfoDVO().getM_strUserLogonId(),
																	   (obj.getM_strEventName().contains("ACT")&&obj.getM_strIsCopyBOM().equalsIgnoreCase("Y")&&obj.getM_strDataMaintProgNo()!=null)
																	   ?obj.getM_strDataMaintProgNo():"FCEPAS1",
																	   attachedBudgetEvent[0].trim(),
																	   attachedBudgetEvent[1].trim(),
																	   obj.getM_strPartNumber(),
																	   obj.getM_strPartColor()==null?"":obj.getM_strPartColor(),
																	   obj.getM_strSupplierNumber(),
																	   obj.getM_strShipToCode()==null?"":obj.getM_strShipToCode(),
																       obj.getM_strOldDesignSection() == null ? "" : obj.getM_strOldDesignSection(),
																	   obj.getM_strTargetPlant(),
																	   obj.getM_strTargetPlant(),
																	   obj.getM_strModel()==null?"":obj.getM_strModel(),
																	   obj.getM_strMTCType1()==null?"":obj.getM_strMTCType1(),
																	   obj.getM_strMTCOption()==null?"":obj.getM_strMTCOption(),
																	   obj.getM_strCategory()==null?"":obj.getM_strCategory());
			}
		}
		logger.debug("\n Exiting applyEventPartDetailsApplicationToAttachedBudget ");
		return responseDVO;
	}
	
	public ResponseDVO updateEventApplication(RequestDVO requestDVO) throws ApplicationException {
		
		logger.debug("\n Entering updateEventApplication()");

		ResponseDVO responseDVO = new ResponseDVO();
		Connection conn = null;
		CallableStatement cstmt = null;
		int result = 0;
		boolean isSuccess = false;
		String returnCode = null;
		String returnMessage = null;
		AbstractDVO abstractDVO = new AbstractDVO();
		
	    try{
	    	
			EventApplicationDVO inDVO = (EventApplicationDVO) requestDVO.getM_abstractDVO();
	    	//need to pass is supply flag to persist
	    	final String procedureCall = replaceSchemaNames("{call --PROC_SCHEMA--.FCS608P(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
	        conn = getJdbcTemplateObject().getDataSource().getConnection();
	    
	        cstmt = conn.prepareCall(procedureCall);
	        cstmt.setString(1, Utility.convertNullToBlank(inDVO.getM_strEventName()));
	        cstmt.setBigDecimal(2, inDVO.getM_decEventRevNo());
	        cstmt.setString(3, Utility.convertNullToBlank(inDVO.getM_strPartNumber()));
	        cstmt.setString(4, Utility.convertNullToBlank(inDVO.getM_strPartColor()));
	        cstmt.setString(5, Utility.convertNullToBlank(inDVO.getM_strSupplierNumber()));
	        cstmt.setString(6, Utility.convertNullToBlank(inDVO.getM_strOldShipToCode())); 
	        cstmt.setString(7, Utility.convertNullToBlank(inDVO.getM_strOldDesignSection()));
	        cstmt.setString(8, Utility.convertNullToBlank(inDVO.getM_strOldTargetPlant()));
	        cstmt.setString(9, Utility.convertNullToBlank(inDVO.getM_strOldModel()));
	        cstmt.setString(10, Utility.convertNullToBlank(inDVO.getM_strOldMTCType()));
	        cstmt.setString(11, Utility.convertNullToBlank(inDVO.getM_strOldMTCOption()));
	        cstmt.setString(12, Utility.convertNullToBlank(inDVO.getM_strOldCategory())); 
	        cstmt.setString(13, Utility.convertNullToBlank(inDVO.getM_strShipToCode()));
	        cstmt.setString(14, Utility.convertNullToBlank(inDVO.getM_strDesignSection()));
	        cstmt.setString(15, Utility.convertNullToBlank(inDVO.getM_strTargetPlant()));
	        cstmt.setString(16, Utility.convertNullToBlank(inDVO.getM_strModel()));
	        cstmt.setString(17, Utility.convertNullToBlank(inDVO.getM_strMTCType1().get(0)));
	        cstmt.setString(18, Utility.convertNullToBlank(inDVO.getM_strMTCOption()));
	        cstmt.setString(19, Utility.convertNullToBlank(inDVO.getM_strCategory()));
	        /*
	        cstmt.setString(16, inDVO.getM_strModel());
	        cstmt.setString(17, Utility.convertNullToBlank(inDVO.getM_strMTCType()));
	        cstmt.setString(18, Utility.convertNullToBlank(inDVO.getM_strMTCOption()));
	        cstmt.setString(19, Utility.convertNullToBlank(inDVO.getM_strCategory()));
	        */
	        //cstmt.setBigDecimal(20, new BigDecimal(inDVO.getM_intRate()) );12
	        cstmt.setBigDecimal(20, inDVO.getM_bRate());
	        cstmt.setBigDecimal(21, new BigDecimal(inDVO.getM_intQuantity()) );
	        cstmt.setString(22, inDVO.getM_strIsSupply());
	        cstmt.setString(23, "FCEPAD1");
	        //cstmt.setString(23, "mmt9569");
	        cstmt.setString(24, requestDVO.getM_userInfoDVO().getM_strUserLogonId());
	        cstmt.registerOutParameter(25, Types.VARCHAR);
	        cstmt.registerOutParameter(26, Types.VARCHAR);
	        result = cstmt.executeUpdate();
		    isSuccess = true;
		    	   
		    logger.debug("\n FCS608P SQL Code : "+cstmt.getString(25));
		    logger.debug("\n FCS608P SQL Message : "+cstmt.getString(26));
		    
		    returnCode = cstmt.getString(25);
		    returnMessage = cstmt.getString(26);
		    
            if(null != returnCode && returnCode.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_RETURN_CODE)){
            		//&& (null == returnMessage || returnMessage.trim().isEmpty())){
            	
            	abstractDVO.setObject(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS);
            	
            }else if(returnCode.equals("000000100+") && returnMessage.contains("FCS608P 0451 SELECT")){
		    	abstractDVO.setObject(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS);
		    } 
            else {
            	
            	abstractDVO.setObject(returnMessage);
            }
            
		    
	       }catch (SQLException e) {
	    	   e.printStackTrace();
	    	   throwDatabaseException(e);
	       } finally {
	    	   if(conn != null){
	    		   try {
	    			   conn.close();
	    		   } catch (SQLException e) {
	    			   e.printStackTrace();
	    		   }
	    	   }
	    	   if(cstmt != null){
	    		   try {
	    			   cstmt.close();
	    		   } catch (SQLException e) {
	    			   e.printStackTrace();
	    		   }
	    	   }
	        }
		
		logger.debug("\n Exiting updateEventApplication()");
		
		responseDVO.setM_abstractDVO(abstractDVO);
		return responseDVO;
	}
	
	public ResponseDVO checkPartSupplierValidation(RequestDVO requestDVO){

		logger.debug("\n Entering checkPartSupplierValidation()");
		
		ResponseDVO responseDVO = new ResponseDVO();
		EventApplicationDVO inDVO = (EventApplicationDVO) requestDVO.getM_abstractDVO();
		List<Map<String,Object>> results = null;
		AbstractDVO outDVO = new AbstractDVO();
		
		results = getJdbcTemplateObject().queryForList(PART_SUPPLIER_VALIDATION_CHECK,inDVO.getM_strEventName(),
																	  inDVO.getM_decEventRevNo(),
																	  inDVO.getM_strPartNumber(),
																	  (inDVO.getM_strPartColor() == null) ? "" : inDVO.getM_strPartColor(),
																	  inDVO.getM_strSupplierNumber());
		if(results == null || results.size() == 0)
			outDVO.setObject(false);
		else 
			outDVO.setObject(true);
		
		responseDVO.setM_abstractDVO(outDVO);
		
		logger.debug("\n Exiting checkPartSupplierValidation()");
		
		return responseDVO;
	}
	
	public ResponseDVO getEventApplicationDetails(RequestDVO requestDVO) throws Exception{
		
		logger.debug("\n Entering getEventApplicationDetails()");
		
		ResponseDVO responseDVO = new ResponseDVO();
		EventApplicationDVO inDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
		EventApplicationDVO outDVO = null;
		List<Map<String, Object>> listDVO = null;
		LinkedHashMap<String, Object> paramMap = new LinkedHashMap<String, Object>();
		
	    paramMap.put("PH_COST_EVENT_NAME", inDVO.getM_strEventName());
	    paramMap.put("AND EVENT_REV_NO", inDVO.getM_decEventRevNo());
	    paramMap.put("AND PART_NO", inDVO.getM_strPartNumber());
	    paramMap.put("AND TRIM(PART_COLOR_CODE)", inDVO.getM_strPartColor());
	    paramMap.put("AND TRIM(SUPPLIER_NO)", inDVO.getM_strSupplierNumber());
	    paramMap.put("AND TRIM(SHIP_TO_CODE)", inDVO.getM_strShipToCode());
	    paramMap.put("AND TRIM(PART_SECTION_CODE)", inDVO.getM_strDesignSection());
	    paramMap.put("AND TRIM(TGT_PLANT_LOC_CODE)",inDVO.getM_strTargetPlant());
	    paramMap.put("AND TRIM(TGT_MODEL_DEV_CODE)", inDVO.getM_strModel());
	    paramMap.put("AND TRIM(MTC_TYPE)", inDVO.getM_strMTCType());
	    paramMap.put("AND TRIM(MTC_OPTION)", inDVO.getM_strMTCOption());
	    paramMap.put("AND TRIM(MODEL_CAT_CODE)", inDVO.getM_strCategory());
	    paramMap.put("AND TRIM(IS_DSPS)", inDVO.getM_strIsSupply());
	    
	    String whereStr = Utility.buildWhereClause(paramMap);
	    
	    try{
			
	    	listDVO = getJdbcTemplateObject().queryForList(GET_EVENT_APPLICATION_DETAILS.replace("--WHERE_CONDITION--", (whereStr == null || whereStr.equalsIgnoreCase("")) ? whereStr : " WHERE "+whereStr),
	    			new Object[]{});
															/*new RowMapper<EventApplicationDVO>(){
												
															@Override
															public EventApplicationDVO mapRow(
																	ResultSet rs,
																	int arg1)
																	throws SQLException {
												
																EventApplicationDVO dvo = new EventApplicationDVO();
															
																dvo.setM_strEventName(rs.getString("PH_COST_EVENT_NAME"));
																dvo.setM_decEventRevNo(rs.getBigDecimal("EVENT_REV_NO"));
																dvo.setM_strPartNumber(rs.getString("PART_NO"));
																dvo.setM_strPartColor(rs.getString("PART_COLOR_CODE"));
																dvo.setM_strSupplierNumber(rs.getString("SUPPLIER_NO"));
																dvo.setM_strShipToCode(rs.getString("SHIP_TO_CODE"));
																dvo.setM_strDesignSection(rs.getString("PART_SECTION_CODE"));
																dvo.setM_strTargetPlant(rs.getString("TGT_PLANT_LOC_CODE"));
																dvo.setM_strModel(rs.getString("TGT_MODEL_DEV_CODE"));
																dvo.setM_strMTCType(rs.getString("MTC_TYPE"));
																dvo.setM_strMTCOption(rs.getString("MTC_OPTION"));
																dvo.setM_strCategory(rs.getString("MODEL_CAT_CODE"));
																dvo.setM_intRate(rs.getBigDecimal("SHARE_RATE_PERCENT").intValue());
																dvo.setM_intQuantity(rs.getBigDecimal("PART_QTY").intValue());
																   
																return dvo;
															}});*/
	    	if(null != listDVO && !listDVO.isEmpty()){
	    		HashMap<String, Object> hmap = (HashMap<String, Object>) listDVO.get(0);
	    		outDVO = new EventApplicationDVO();
	    		outDVO.setM_strEventName(String.valueOf(hmap.get("PH_COST_EVENT_NAME")));
				outDVO.setM_decEventRevNo((BigDecimal)hmap.get("EVENT_REV_NO"));
				outDVO.setM_strPartNumber(String.valueOf(hmap.get("PART_NO")));
				outDVO.setM_strPartColor(String.valueOf(hmap.get("PART_COLOR_CODE")));
				outDVO.setM_strSupplierNumber(String.valueOf(hmap.get("SUPPLIER_NO")));
				outDVO.setM_strShipToCode(String.valueOf(hmap.get("SHIP_TO_CODE")));
				outDVO.setM_strDesignSection(String.valueOf(hmap.get("PART_SECTION_CODE")));
				outDVO.setM_strTargetPlant(String.valueOf(hmap.get("TGT_PLANT_LOC_CODE")));
				outDVO.setM_strModel(String.valueOf(hmap.get("TGT_MODEL_DEV_CODE")));
				outDVO.setM_strMTCType(String.valueOf(hmap.get("MTC_TYPE")));
				outDVO.setM_strMTCOption(String.valueOf(hmap.get("MTC_OPTION")));
				outDVO.setM_strCategory(String.valueOf(hmap.get("MODEL_CAT_CODE")));
				outDVO.setM_intRate(((BigDecimal)hmap.get("SHARE_RATE_PERCENT")).intValue());
				outDVO.setM_bRate(((BigDecimal)hmap.get("SHARE_RATE_PERCENT")));
				outDVO.setM_intQuantity(((BigDecimal)hmap.get("PART_QTY")).intValue());
				outDVO.setM_strIsSupply(String.valueOf(hmap.get("IS_DSPS")).trim());
				outDVO.setM_strIsCopyBOM(String.valueOf(hmap.get("IS_COPY_BOM")).trim());
	    	}
	    }catch(Exception e){
	    	
	    	e.printStackTrace();
	    	throw e;
	    }
		/*outDVO = getJdbcTemplateObject().queryForObject(GET_EVENT_APPLICATION_DETAILS,
			
		}, 
		new Object[]{inDVO.getM_strEventName(),
            inDVO.getM_decEventRevNo(),
            inDVO.getM_strPartNumber(),
            inDVO.getM_strPartColor(),
            inDVO.getM_strSupplierNumber(),
            inDVO.getM_strShipToCode(),
            inDVO.getM_strDesignSection(),
            inDVO.getM_strTPlant(),
            inDVO.getM_strModel(),
            inDVO.getM_strMTCType(),
            inDVO.getM_strMTCOption(),
            inDVO.getM_strCategory()}); */
		
		responseDVO.setM_abstractDVO(null != outDVO ? outDVO : inDVO);
		
		logger.debug("\n Exiting getEventApplicationDetails()");
		
		return responseDVO;
	}
	
	public ResponseDVO deleteEventApplication(RequestDVO requestDVO){
		
		logger.debug("\n Entering deleteEventApplication()");
	
		ResponseDVO responseDVO = new ResponseDVO();
		Connection conn = null;
		CallableStatement cstmt = null;
		int result = 0;
		boolean isSuccess = false;
		String returnCode = null;
		String returnMessage = null;
		AbstractDVO abstractDVO = new AbstractDVO();
		
	    try{
	    	
			EventApplicationDVO inDVO = (EventApplicationDVO) requestDVO.getM_abstractDVO();
	    	
	    	final String procedureCall = replaceSchemaNames("{call --PROC_SCHEMA--.FCS606P(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
	        conn = getJdbcTemplateObject().getDataSource().getConnection();
	        
	        cstmt = conn.prepareCall(procedureCall);
	        cstmt.setString(1, Utility.convertNullToBlank(inDVO.getM_strEventName()));
	        cstmt.setBigDecimal(2, inDVO.getM_decEventRevNo());
	        cstmt.setString(3, Utility.convertNullToBlank(inDVO.getM_strPartNumber()));
	        cstmt.setString(4, Utility.convertNullToBlank(inDVO.getM_strPartColor()));
	        cstmt.setString(5, Utility.convertNullToBlank(inDVO.getM_strSupplierNumber()));
	        cstmt.setString(6, Utility.convertNullToBlank(inDVO.getM_strShipToCode()));
	        cstmt.setString(7, Utility.convertNullToBlank(inDVO.getM_strDesignSection()));
	        cstmt.setString(8, Utility.convertNullToBlank(inDVO.getM_strTargetPlant()));
	        cstmt.setString(9, Utility.convertNullToBlank(inDVO.getM_strModel()));
	        cstmt.setString(10, Utility.convertNullToBlank(inDVO.getM_strMTCType1().get(0)));
	        cstmt.setString(11, Utility.convertNullToBlank(inDVO.getM_strMTCOption()));
	        cstmt.setString(12, Utility.convertNullToBlank(inDVO.getM_strCategory()));
	        //cstmt.setString(13, "mmt9569");
	        cstmt.setString(13, requestDVO.getM_userInfoDVO().getM_strUserLogonId());
	        cstmt.registerOutParameter(14, Types.VARCHAR);
	        cstmt.registerOutParameter(15, Types.VARCHAR);
	        result = cstmt.executeUpdate();
		    isSuccess = true;;
		    
		    logger.debug("\n FCS606P SQL Code : "+cstmt.getString(14));
		    logger.debug("\n FCS606P SQL Message : "+cstmt.getString(15));
		    
		    returnCode = cstmt.getString(14);
		    returnMessage = cstmt.getString(15);
		    
            if(null != returnCode && returnCode.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_RETURN_CODE)
            		&& (null == returnMessage || returnMessage.trim().isEmpty())){
            	
            	abstractDVO.setObject(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS);
            	
            } else if(returnMessage.contains("REC NOT FOUND FCEPF1")){
            	abstractDVO.setObject(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS);
            } else {
            	
            	abstractDVO.setObject(returnMessage);
            }
		    
	       }catch (SQLException e) {
	    	   e.printStackTrace();
	       } finally {
	    	   if(conn != null){
	    		   try {
	    			   conn.close();
	    		   } catch (SQLException e) {
	    			   e.printStackTrace();
	    		   }
	    	   }
	    	   if(cstmt != null){
	    		   try {
	    			   cstmt.close();
	    		   } catch (SQLException e) {
	    			   e.printStackTrace();
	    		   }
	    	   }
	        }
		
		
		logger.debug("\n Exiting deleteEventApplication()");
		
		responseDVO.setM_abstractDVO(abstractDVO);
		
		return responseDVO;
	}
	
	public ResponseDVO validatePlantMTOColor(RequestDVO requestDVO){
		
		logger.debug("\n Entering validatePlantMTOColor()");
		ResponseDVO responseDVO = new ResponseDVO();
		AbstractDVO m_abstractdvo = new AbstractDVO();
		EventApplicationDVO inDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
		List<Map<String,Object>> results = null;
		Boolean valid = null;
		
		results = getJdbcTemplateObject().queryForList(PLANT_MTO_COLOR_VALIDATION,
														 inDVO.getM_strEventName(),
														 inDVO.getM_decEventRevNo(),
														 (inDVO.getM_strTargetPlantToCopy() == null || inDVO.getM_strTargetPlantToCopy().equalsIgnoreCase("")) ? "" : inDVO.getM_strTargetPlantToCopy(),
														 (inDVO.getM_strModelToCopy() == null || inDVO.getM_strModelToCopy().equalsIgnoreCase("")) ? "" : inDVO.getM_strModelToCopy(),
														 (inDVO.getM_strMTCTypeToCopy() == null || inDVO.getM_strMTCTypeToCopy().equalsIgnoreCase("")) ? "" : inDVO.getM_strMTCTypeToCopy(),
														 (inDVO.getM_strMTCOptionToCopy() == null || inDVO.getM_strMTCOptionToCopy().equalsIgnoreCase("")) ? "" : inDVO.getM_strMTCOptionToCopy(),
														 (inDVO.getM_strPartColor() == null || inDVO.getM_strPartColor().equalsIgnoreCase("")) ? "" : inDVO.getM_strPartColor());
		
		if(results == null || results.size() == 0)
			valid = false;
		else
			valid = true;
		
		m_abstractdvo.setObject(valid);
		responseDVO.setM_abstractDVO(m_abstractdvo);
		
		logger.debug("\n Exiting validatePlantMTOColor()");
		return responseDVO;
		
	}
public ResponseDVO addNewEventPart(RequestDVO requestDVO) throws ApplicationException{
		
		String location = CLASS_NAME + ".addNewEventPart()";
        responseDVO = new ResponseDVO();
        ListDVO listDVO = new ListDVO();
        eventPartDVO = (EventPartDVO)requestDVO.getM_abstractDVO();
        ArrayList arlEventList = new ArrayList();
        
        //String strEventName = eventPartDVO.getM_strEventName();
            
          //  listDVO.setM_arlAbstractDVO(arlEventList);
            responseDVO.setM_abstractDVO(listDVO);
        
        
        return responseDVO;
		
	}

public boolean updateEventPart(RequestDVO requestDVO) throws ApplicationException{
	
	String location = CLASS_NAME + ".updateEventPart()";
	logger.debug("\n Entering "+location);
	int result = 0;
	boolean isSuccess = false;
	
	Map<String, Object> parameters = new HashMap<String, Object>();
	eventPartDVO = (EventPartDVO)requestDVO.getM_abstractDVO();
	parameters.put("eventName", eventPartDVO.getM_strEventName());
	parameters.put("eventRevNo", eventPartDVO.getM_intRevNum());
	parameters.put("partNo", eventPartDVO.getM_strPartNumber());
	parameters.put("partColorCode", eventPartDVO.getM_strPartColor());
	parameters.put("supplierNo", eventPartDVO.getM_strSupplierNo());
	parameters.put("procSectCode", eventPartDVO.getM_strProcGroup());
	parameters.put("partStageCode", ""); //TODO: Do not know where does Part Stage code comes from

	result = getJdbcTemplateObject().update(replaceSchemaNames(EventProcessingSQLIF.UPDATE_EVENT_PART_DETAILS) , parameters );
	if(result > 0){
		isSuccess = true;
	}
    logger.debug("\n Exiting "+location);
    return isSuccess;
}

public boolean deleteEventPart(RequestDVO requestDVO) throws ApplicationException{
	
	String location = CLASS_NAME + ".deleteEventPart()";
	logger.debug("\n Entering "+location);
	
	Connection conn = null;
	CallableStatement cstmt = null;
	int result = 0;
	boolean isSuccess = false;
    try{
    	eventPartDVO = (EventPartDVO)requestDVO.getM_abstractDVO();
    	
    	final String procedureCall = replaceSchemaNames("{call --PROC_SCHEMA--.FCS602P(?, ?, ?, ?, ?, ?, ?, ?)}");
        conn = getJdbcTemplateObject().getDataSource().getConnection();

        cstmt = conn.prepareCall(procedureCall);
        cstmt.setString(1, eventPartDVO.getM_strEventName());
        cstmt.setInt(2, eventPartDVO.getM_intRevNum());
        cstmt.setString(3, eventPartDVO.getM_strPartNumber());
        cstmt.setString(4, eventPartDVO.getM_strPartColor()); //TODO: set to "NULL"
        cstmt.setString(5, eventPartDVO.getM_strSupplierNo());
        cstmt.setString(6, "MMT0323"); //TODO: replace mmt id with logged in user id
        cstmt.registerOutParameter(7, Types.VARCHAR);
        cstmt.registerOutParameter(8, Types.VARCHAR);
        result = cstmt.executeUpdate();
	    isSuccess = true;
       }catch (SQLException e) {
    	   e.printStackTrace();
       } finally {
    	   if(conn != null){
    		   try {
    			   conn.close();
    		   } catch (SQLException e) {
    			   e.printStackTrace();
    		   }
    	   }
    	   if(cstmt != null){
    		   try {
    			   cstmt.close();
    		   } catch (SQLException e) {
    			   e.printStackTrace();
    		   }
    	   }
        }
    logger.debug("\n Exiting "+location);
    return isSuccess;
	}

public ResponseDVO getEventPartDetails(RequestDVO requestDVO) throws ApplicationException{
	
	String location = CLASS_NAME + ".getEventPartDetails()";
    responseDVO = new ResponseDVO();
    ListDVO listDVO = new ListDVO();
    List<EventPartCostDVO> arlEventPartCostOutList = new ArrayList<EventPartCostDVO>();
    //String strEventName = eventPartDVO.getM_strEventName();
    responseDVO.setM_abstractDVO(listDVO);
    logger.debug("\n Exiting getEventPartDetails()");
    return responseDVO;
	
}

/*
 * This method Finds Event Part Cost based on the input provided
 * Returns List of DVO
 * Stored Proc getting called is 
 * CALL FCS615P
   (|PH_COST_EVENT_NAME|
   ,|EVENT_REV_NO|
   ,|PART_NO|
   ,|PART_COLOR_CODE|
   ,|PLANT_LOC_CODE|
   ,|SUPPLIER_NO|
   ,|RPT_CURRENCY_CODE|
   ,|ISO_CURRENCY_CODE|
   ,|PROC_SECT_CODE|
   ,|PART_SECTION_CODE|
   ,|COST_CHG_CAT_CODE|
   ,|OPERATOR|
   ,|COST_CHANGE_AMT|
   ,|MAINT_LOGON_ID_NO|
   ,|SQLCODE|
   ,|PROCLOC|)
 */
public ResponseDVO searchEventPartCost_MPCE(RequestDVO requestDVO) throws ApplicationException{
	logger.debug("\n Entering getEventPartCostDetails()");
	String location = CLASS_NAME + ".getEventPartCostDetails()";
    responseDVO = new ResponseDVO();
    ListDVO listDVO = new ListDVO();
    List<EventPartCostDVO> arlEventPartCostOutList = new ArrayList<EventPartCostDVO>();
    
    EventPartCostFindDVO inDVO = (EventPartCostFindDVO)requestDVO.getM_abstractDVO();
    String str_EventName = inDVO.getM_strEventName();
    //String str_EventRev = inDVO.getM_strEventRev();
    BigDecimal big_EventRev = inDVO.getM_bigEventRev();
    String str_PartNumber = inDVO.getM_strPartNo();
    String str_PartColor = inDVO.getM_strPartColor();
    String str_HamPlant = inDVO.getM_strPlantLoc();
    String str_SupplierNo = inDVO.getM_SupplierNumber();
    String str_PaymentCurrRpt = inDVO.getM_strPaymentCurrency(); //rpt currency
    String str_PaymentCurrIso = inDVO.getM_strPaymentCurrency(); //isocurrency
    //procurement sect missing
    //String str_PartStageCode = inDVO.get missing
    String str_CostCat = inDVO.getM_strCostCategory();
    //Operator
    //cost change amount
    //main logon id mmt0323
    //sqlcode out
    //procloc out 
    
    //below are not in use in the Stored Procedure
    String str_EventDesc = inDVO.getM_strEventDesc();
    String str_DesignSect = inDVO.getM_strDesignSect();
    String str_CarryExclusive = inDVO.getM_strCarryExclusive();
    //String str_PartSect = inDVO.getPart sect missing 

    final String procedureCall = replaceSchemaNames("{call --PROC_SCHEMA--.FCS615P(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
    Connection connection = null;
    try {
   //Get Connection instance from dataSource
    connection = getJdbcTemplateObject().getDataSource().getConnection();
    CallableStatement callableSt = connection.prepareCall(procedureCall);
    System.out.println(str_EventName);
    System.out.println(big_EventRev);
    System.out.println(str_PartNumber);
    System.out.println(str_PartColor);
    System.out.println(str_HamPlant);
    System.out.println(str_SupplierNo);
    System.out.println(str_PaymentCurrRpt);
    System.out.println(str_PaymentCurrIso);
    logger.debug(str_EventName);
    logger.debug(big_EventRev);
    logger.debug(str_PartNumber);
    logger.debug(str_PartColor);
    logger.debug(str_HamPlant);
    logger.debug(str_SupplierNo);
    logger.debug(str_PaymentCurrRpt);
    logger.debug(str_PaymentCurrIso);

    callableSt.setString(1, str_EventName);
    //callableSt.setString(2, str_EventRev);
    callableSt.setBigDecimal(2, big_EventRev);
    callableSt.setString(3, str_PartNumber);
    callableSt.setString(4, str_PartColor);
    callableSt.setString(5, str_HamPlant);
    callableSt.setString(6, str_SupplierNo);
    callableSt.setString(7, str_PaymentCurrRpt); //Rpt
    callableSt.setString(8, str_PaymentCurrIso); //Iso
    
    /*test data
    callableSt.setString(1, "201509ACT");
    callableSt.setString(2, "0");
    callableSt.setString(3, "40600T2F A000H1");
    callableSt.setString(4, "");
    callableSt.setString(5, "");
    callableSt.setString(6, "110310");
    callableSt.setString(7, "USD");
    callableSt.setString(8, "USD");
    callableSt.setString(10, "I");
    callableSt.setString(11, "");*/
    
    callableSt.setString(9, "");
    callableSt.setString(10, "");
    callableSt.setString(11, ""); //Cost cat code - str_CostCat
    callableSt.setString(12, "");
    callableSt.setString(13, "123415.112"); // similar values set in VB
    callableSt.setString(14, "mmt0323"); // similar values set in VB
    callableSt.registerOutParameter(15, Types.VARCHAR); 
    callableSt.registerOutParameter(16, Types.VARCHAR);
    
    //Call Stored Procedure
    ResultSet rs = callableSt.executeQuery();
    
    
    if(rs != null){
    	int rowKeyid = 0;
    	while(rs.next()){
    		
        	
        	EventPartCostDVO eventCostDVO =  new EventPartCostDVO();
        	eventCostDVO.setM_strPartNo(rs.getString(1));
        	eventCostDVO.setM_strPartColor(rs.getString(2));
        	eventCostDVO.setM_partName(rs.getString(3));
        	eventCostDVO.setM_strPlantLoc(rs.getString(4));
        	eventCostDVO.setM_nSupplierNumber(rs.getString(5));
        	eventCostDVO.setM_strSupplierName(rs.getString(6));
        	eventCostDVO.setM_strProcGroup(rs.getString(7));
        	eventCostDVO.setM_strCarryExclusive(rs.getString(8));
        	eventCostDVO.setM_strRptCurr(rs.getString(9));
        	eventCostDVO.setM_strBaseCost(new DecimalFormat(ApplicationConstantsIF.APP_CONSTANTS.FOUR_DECIMAL_FORMAT).format(rs.getDouble(10)));
        	eventCostDVO.setM_strCurrMnthCstChng(new DecimalFormat(ApplicationConstantsIF.APP_CONSTANTS.FOUR_DECIMAL_FORMAT).format(rs.getDouble(11)));
        	eventCostDVO.setM_strNewCost(new DecimalFormat(ApplicationConstantsIF.APP_CONSTANTS.FOUR_DECIMAL_FORMAT).format(rs.getDouble(12)));
        	eventCostDVO.setM_strPartQuoteProcess(new DecimalFormat(ApplicationConstantsIF.APP_CONSTANTS.FOUR_DECIMAL_FORMAT).format(rs.getDouble(13)));// check in table
        	eventCostDVO.setM_strTBACost(new DecimalFormat(ApplicationConstantsIF.APP_CONSTANTS.FOUR_DECIMAL_FORMAT).format(rs.getDouble(14))); // check in table
        	
        	eventCostDVO.setM_bSp506(false);
        	eventCostDVO.setM_bSp615(true);
        	eventCostDVO.setM_nEventPartCostRowKey(rowKeyid++);
        	
        	arlEventPartCostOutList.add(eventCostDVO);
    	}
    	
    }

   }catch (SQLException e) {
	   throwDatabaseException(e);
   } finally {

   if(connection != null)
    try {
    connection.close();
    } catch (SQLException e) {
    //e.printStackTrace();
    throwDatabaseException(e);
    }
    }
 
    listDVO.setList(arlEventPartCostOutList);
     responseDVO.setM_abstractDVO(listDVO);
    
     logger.debug("\n Exiting "+location+" location ");
    return responseDVO;
	
  }

/*
 * This method Finds Event Part Cost based on the input provided
 * Returns List of DVO
 *Stored Proc getting called is 
 *   * Calling DB2 Stored Procedure
     * Pass jdbcTemlate and name of the stored Procedure.
     * CALL FCS506P
     * (|PH_COST_EVENT_NAME|returns the ResponseDVO to service
     * ,|EVENT_REV_NO|
     * ,|PART_NO|
     * ,|PART_COLOR_CODE|
     * ,|SUPPLIER_NO|
     * ,|PART_STAGE_CODE|
     * ,|PROC_SECT_CODE|
     * ,|RPT_CURRENCY_CODE|
     * ,|ISO_CURRENCY_CODE|
     * ,|PLANT_LOC_CODE|
     * ,|PART_SECTION_CODE|
     * ,|IMPACT_MONTHS|
     * ,|DATA_MAINT_PROG_NO|
     * ,|MAINT_LOGON_ID_NO|
     * ,|SQLCODE|
     * ,|ERROR_MSG|)
     *
 */
public ResponseDVO searchEventPartCost_BUCE(RequestDVO requestDVO) throws ApplicationException{
	//got EventPartCostFindDVO
	logger.debug("\n Entering searchEventPartCost_BUCE ");
	String location = CLASS_NAME + ".searchEventPartCost_BUCE";
    responseDVO = new ResponseDVO();
    ListDVO listDVO = new ListDVO();
    List<EventPartCostDVO> arlEventPartCostOutList = new ArrayList<EventPartCostDVO>();
    
    EventPartCostFindDVO inDVO = (EventPartCostFindDVO)requestDVO.getM_abstractDVO();
    String str_EventName = inDVO.getM_strEventName();
    //String str_EventRev = inDVO.getM_strEventRev();
    BigDecimal big_EventRev = inDVO.getM_bigEventRev();
    String str_PartNumber = inDVO.getM_strPartNo();
    String str_PartColor = inDVO.getM_strPartColor();
    String str_SupplierNo = inDVO.getM_SupplierNumber();
    String str_PartStage  = inDVO.getM_strPartStageCode();
    String str_ProcGrp = inDVO.getM_strProcGroup(); //proc sect
    String str_PaymentCurrRpt = inDVO.getM_strPaymentCurrency(); //rpt currency
    String str_PaymentCurrIso = inDVO.getM_strPaymentCurrency(); //isocurrency
    String str_HamPlant = inDVO.getM_strPlantLoc();
    String str_PartSect = inDVO.getM_strDesignSect();
    String str_ImpMnths = "15";
    String str_DataMaintProg = "FCS506P";
    String str_MainLogonId = "mmt0323";
    String str_SpName = "FCS506P";
    
    //below are not in use in the Stored Procedure
    //String str_PartStageCode = inDVO.get missing
    String str_EventDesc = inDVO.getM_strEventDesc();
    String str_DesignSect = inDVO.getM_strDesignSect();
    String str_CarryExclusive = inDVO.getM_strCarryExclusive();
    String str_CostCat = inDVO.getM_strCostCategory();
    
    
    //Test code starts For Connection object
    final String procedureCall = replaceSchemaNames("{call --PROC_SCHEMA--.FCS506P(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
    Connection connection = null;
    try {
   //Get Connection instance from dataSource
    connection = getJdbcTemplateObject().getDataSource().getConnection();
    CallableStatement callableSt = connection.prepareCall(procedureCall);
    
    callableSt.setString(1, str_EventName);
    //callableSt.setString(2, str_EventRev);
    callableSt.setBigDecimal(2, big_EventRev);
    callableSt.setString(3, str_PartNumber);
    callableSt.setString(4, str_PartColor);
    callableSt.setString(5, str_SupplierNo);
    callableSt.setString(6, str_PartStage);
    callableSt.setString(7, str_ProcGrp);
    callableSt.setString(8, str_PaymentCurrRpt); //Rpt
    callableSt.setString(9, str_PaymentCurrIso); //Iso
    callableSt.setString(10, str_HamPlant);
    callableSt.setString(11, str_PartSect);
    callableSt.setString(12, str_ImpMnths);
    callableSt.setString(13, str_DataMaintProg);
    callableSt.setString(14, str_MainLogonId);
    callableSt.registerOutParameter(15, Types.VARCHAR);
    callableSt.registerOutParameter(16, Types.VARCHAR);
      
    /* test data
    callableSt.setString(1, "201509ACT");
    callableSt.setString(2, "0");
    callableSt.setString(3, "");
    callableSt.setString(4, "");
    callableSt.setString(5, "110310");
    callableSt.setString(6, "");
    callableSt.setString(7, "");
    callableSt.setString(8, "USD");
    callableSt.setString(9, "USD");
    callableSt.setString(10, "I");
    callableSt.setString(11, "");*/
    //Call Stored Procedure
    ResultSet rs = callableSt.executeQuery();
    
    
    
    if(rs != null){
    	int rowKeyid = 0;
    	while(rs.next()){
    		
        	
        	EventPartCostDVO eventCostDVO =  new EventPartCostDVO();
        	eventCostDVO.setM_strPartNo(rs.getString(3));
        	eventCostDVO.setM_strPartColor(rs.getString(4));
        	eventCostDVO.setM_partName(rs.getString(10));
        	eventCostDVO.setM_strPlantLoc(rs.getString(6));
        	eventCostDVO.setM_nSupplierNumber(rs.getString(5));
        	eventCostDVO.setM_strSupplierName(rs.getString(11));
        	eventCostDVO.setM_strProcGroup(rs.getString(8));
        	eventCostDVO.setM_strPartStage(rs.getString(7));
        	eventCostDVO.setM_strRptCurr(rs.getString(9));
        	eventCostDVO.setM_strBaseCost(new DecimalFormat(ApplicationConstantsIF.APP_CONSTANTS.FOUR_DECIMAL_FORMAT).format(rs.getDouble(12)));
        	//eventCostDVO.setM_strSpName(str_SpName);
        	eventCostDVO.setM_bSp506(true);
        	eventCostDVO.setM_bSp615(false);
        	/* will be required for FCS620P
        	eventCostDVO.setM_strCarryExclusive(rs.getString(7));
        	eventCostDVO.setM_dCurrMnthCstChng(rs.getDouble(13));
        	eventCostDVO.setM_dNewCost(rs.getDouble(14));
        	eventCostDVO.setM_dPartQuoteProcess(rs.getDouble(15));// check in table
        	eventCostDVO.setM_dTBACost(rs.getDouble(16)); // check in table
        	*/
        	eventCostDVO.setM_nEventPartCostRowKey(rowKeyid++);
        	arlEventPartCostOutList.add(eventCostDVO);
    	}
    	
    }

   }catch (SQLException e) {

   e.printStackTrace();

   } finally {

   if(connection != null)
    try {
    connection.close();
    } catch (SQLException e) {
    e.printStackTrace();
    }
    }
    
 
//Test code ends
    listDVO.setList(arlEventPartCostOutList);
     responseDVO.setM_abstractDVO(listDVO);
    
     logger.debug("\n Exiting "+location+" location ");
    return responseDVO;
	
}


public ResponseDVO getCostChangesGrid(RequestDVO requestDVO) throws ApplicationException{
	logger.debug("\n Entering getCostChangesGrid ");
	String location = CLASS_NAME + ".getCostChangesGrid";
	ResponseDVO responseDVO = new ResponseDVO();
	List<ReviewEventCostChangesDVO> reviewEventCostChangesListDVO = new ArrayList<ReviewEventCostChangesDVO>();
	EventPartCostDVO inDVO = (EventPartCostDVO)requestDVO.getM_abstractDVO();
	
	
	
	List<Map<String,Object>> results = null;
	results = getJdbcTemplateObject().queryForList(COST_CHANGES_GRID, new Object[] {inDVO.getM_strEventName(),inDVO.getM_bigEventRev(),inDVO.getM_strPartNo(),inDVO.getM_strPartColor(),inDVO.getM_strPlantLoc(),inDVO.getM_nSupplierNumber()});
	if(results != null){
		
		
		for(Map map: results){
			ReviewEventCostChangesDVO reviewEventCostChangesDVO = new ReviewEventCostChangesDVO();
			reviewEventCostChangesDVO.setM_strCostChangeCode(Utility.trimStringValue(((String)map.get("COST_CHANGE_CODE"))));
			reviewEventCostChangesDVO.setM_strCodeDesc(Utility.trimStringValue(((String)map.get("CODE_DESC_TEXT"))));
			reviewEventCostChangesDVO.setM_bigCostChangeAmt(((BigDecimal)map.get("COST_CHANGE_AMT")));
			//reviewEventCostChangesDVO.setM_dtNewQuoteDate(((Date)map.get("NEW_QUOTE_EFF_DATE")));
			reviewEventCostChangesDVO.setM_strNewQuoteDate((Utility.convertFromUtilDateToStr(Utility.convertSqlDateToUtilDate((java.sql.Date)map.get("NEW_QUOTE_EFF_DATE")), "MM/dd/yyyy")));
			reviewEventCostChangesDVO.setM_strComment(((String)map.get("COMMENT_TEXT")));
			
			reviewEventCostChangesListDVO.add(reviewEventCostChangesDVO);
		}
	}
	ListDVO listDVO = new ListDVO();
	listDVO.setList(reviewEventCostChangesListDVO);
	
	responseDVO.setM_abstractDVO(listDVO);
	return responseDVO;
	}

/*
?sWork
		SELECT CODE_NAME
		      ,CODE
		      ,ABBR_CDE_DESC_TEXT  
		      ,CODE_DESC_TEXT
		FROM FCCOD1
		WHERE CODE_NAME=  CODE_NAME 
		AND CODE= CODE 

		?m_sSQLString
		SELECT CODE_NAME
		      ,CODE
		      ,ABBR_CDE_DESC_TEXT  
		      ,CODE_DESC_TEXT
		FROM FCCOD1
		WHERE CODE_NAME= 'ANNUAL_BILLBACK'
		AND CODE= CODE 

		? m_sSQLString
		SELECT CODE_NAME
		      ,CODE
		      ,ABBR_CDE_DESC_TEXT  
		      ,CODE_DESC_TEXT
		FROM FCCOD1
		WHERE CODE_NAME=  CODE_NAME 
		AND CODE='22'*/

public ResponseDVO searchFocusCodes(RequestDVO requestDVO) throws ApplicationException{
	logger.debug("\n Entering searchFocusCodes ");
	ResponseDVO responseDVO = new ResponseDVO();
	ListDVO listDVO = new ListDVO();
    List<FocusCodesDVO> arlFocusCodesOutList = new ArrayList<FocusCodesDVO>();
    int n_rowKeyId=0;
    List<Map<String,Object>> results = null;
    FocusCodesFindDVO focusCodeFindinDVO = (FocusCodesFindDVO)requestDVO.getM_abstractDVO();
    String codeNm = (focusCodeFindinDVO.getM_strCodeName()).trim();
    String code = (focusCodeFindinDVO.getM_strCode()).trim();
    //no values filled
    if(codeNm.equals("CODE_NAME") && code.equals("CODE")){
    	results = getJdbcTemplateObject().queryForList(CODES_SELECT_GRID);
    }
    //only code name selected and code is blank
    if(!(codeNm.equals("CODE_NAME")) && (code.equals("CODE"))){
    	String query = "SELECT CODE_NAME, CODE, ABBR_CDE_DESC_TEXT, CODE_DESC_TEXT FROM FCCOD1 " +
    	"WHERE CODE_NAME='"+ codeNm +"'AND CODE="+ code;
    	results = getJdbcTemplateObject().queryForList(query);
    }
    //only code filled and codename is blank
    if(codeNm.equals("CODE_NAME") && !(code.equals("CODE"))){
    	String query = "SELECT CODE_NAME, CODE, ABBR_CDE_DESC_TEXT, CODE_DESC_TEXT FROM FCCOD1 " +
    	"WHERE CODE_NAME="+ codeNm +" AND CODE='"+ code+"'";
    	results = getJdbcTemplateObject().queryForList(query);
    }
    //both code name and code are filled
    if(!(codeNm.equals("CODE_NAME")) && !(code.equals("CODE"))){
    	String query = "SELECT CODE_NAME, CODE, ABBR_CDE_DESC_TEXT, CODE_DESC_TEXT FROM FCCOD1 " +
    	"WHERE CODE_NAME='"+ codeNm +"'AND CODE='"+ code+"'";
    	results = getJdbcTemplateObject().queryForList(query);
    }
    
	if(results != null){
		for(Map map : results){
			FocusCodesDVO focCodesDVO = new FocusCodesDVO();
			focCodesDVO.setM_strCodeName(Utility.trimStringValue(((String)map.get("CODE_NAME"))));
			focCodesDVO.setM_strCode(Utility.trimStringValue(((String)map.get("CODE"))));
			focCodesDVO.setM_strAbbrDesc(Utility.trimStringValue(((String)map.get("ABBR_CDE_DESC_TEXT"))));
			focCodesDVO.setM_strDesc(Utility.trimStringValue(((String)map.get("CODE_DESC_TEXT"))));
			focCodesDVO.setN_rowKeyFocusCodes(n_rowKeyId++);
			
			arlFocusCodesOutList.add(focCodesDVO);
		}
	}
	listDVO.setList(arlFocusCodesOutList);
	
	responseDVO.setM_abstractDVO(listDVO);
    logger.debug("\n Exiting searchFocusCodes ");
	return responseDVO;
}

public ResponseDVO searchCstGrp(RequestDVO requestDVO) throws ApplicationException{
	logger.debug("\n Exiting searchCstGrp ");
	ResponseDVO responseDVO = new ResponseDVO();
	ListDVO listDVO = new ListDVO();
    List<FocusCodesCostGrpDVO> arlCstGrpOutList = new ArrayList<FocusCodesCostGrpDVO>();
    int n_rowKeyId=0;
    List<Map<String,Object>> results = null;
    FocusCodesCostGrpDVO cstGrpFindinDVO = (FocusCodesCostGrpDVO)requestDVO.getM_abstractDVO();
	String m_strGrpCode = cstGrpFindinDVO.getM_strCstFnGrpCode();
	String m_strPrCstChg = cstGrpFindinDVO.getM_strPrCstChgCode();
	String m_strCstChg = cstGrpFindinDVO.getM_strCstChgCode();
	
	if(m_strGrpCode.equals("COST_FUNC_GRP_CODE") && m_strPrCstChg.equals("PRI_COST_CHG_CODE") && m_strCstChg.equals("COST_CHANGE_CODE")){
		results = getJdbcTemplateObject().queryForList(GROUP_CODES_FIND);
	}
	if(results != null){
		for(Map map : results){
			FocusCodesCostGrpDVO fCdCstGrpDVO = new FocusCodesCostGrpDVO();
			fCdCstGrpDVO.setM_strCstFnGrpCodeDt((String)map.get("COST_FUNC_GRP_CODE"));
			fCdCstGrpDVO.setM_strPrCstChgCodeDt((String)map.get("PRI_COST_CHG_CODE"));
			fCdCstGrpDVO.setM_strCstChgCodeDt((String)map.get("COST_CHANGE_CODE"));
			/*focCodesDVO.setM_strCodeName(Utility.trimStringValue(((String)map.get("CODE_NAME"))));
			focCodesDVO.setM_strCode(Utility.trimStringValue(((String)map.get("CODE"))));
			focCodesDVO.setM_strAbbrDesc(Utility.trimStringValue(((String)map.get("ABBR_CDE_DESC_TEXT"))));
			focCodesDVO.setM_strDesc(Utility.trimStringValue(((String)map.get("CODE_DESC_TEXT"))));
			focCodesDVO.setN_rowKeyFocusCodes(n_rowKeyId++);
			*/
			arlCstGrpOutList.add(fCdCstGrpDVO);
		}
	}
	listDVO.setList(arlCstGrpOutList);
	
	responseDVO.setM_abstractDVO(listDVO);
	logger.debug("\n Exiting searchCstGrp ");
	return responseDVO;
}


public void insertFocusCodes(RequestDVO requestDVO){
	logger.debug("\n Entering insertFocusCodes ");
	FocusCodeNewAddDVO fcNewAddDVO = (FocusCodeNewAddDVO)requestDVO.getM_abstractDVO();
	String MAINT_LOGON_ID = "mmt8135";
	int status = getJdbcTemplateObject().update(EventProcessingSQLIF.INSERT_FOCUS_CODE, new Object[]{fcNewAddDVO.getM_strCodeNameNadd(),fcNewAddDVO.getM_strCodeNadd(),fcNewAddDVO.getM_strAbbrDescNadd(),fcNewAddDVO.getM_strDescNadd(),MAINT_LOGON_ID});
	logger.debug("\n Row inserted : "+status);
	logger.debug("\n Entering insertFocusCodes ");
}

public void deleteFocusCodes(RequestDVO requestDVO)throws ApplicationException{
	logger.debug("\n Entering deleteFocusCodes ");
	int n_rowKeyId=0;
    FocusCodesDVO focusCodeinDVO = (FocusCodesDVO)requestDVO.getM_abstractDVO();
    String codeNm = (focusCodeinDVO.getM_strCodeName()).trim();
    String code = (focusCodeinDVO.getM_strCode()).trim();
    
    
    int rowUpdated = getJdbcTemplateObject().update(EventProcessingSQLIF.DELETE_CODES, new Object[]{focusCodeinDVO.getM_strCodeName(), focusCodeinDVO.getM_strCode()});
	logger.debug("\n Row updated : "+ rowUpdated);

    //List<Map<String,Object>> results = null;
	
	
	logger.debug("\n Exiting deleteFocusCodes ");
}

public void updateFocusCodes (RequestDVO requestDVO) throws ApplicationException{
	logger.debug("\n Entering updateFocusCodes ");
	FocusCodesDVO focusCodeinDVO = (FocusCodesDVO)requestDVO.getM_abstractDVO();
	String MAINT_LOGON_ID_NO = "mmt8135";
	String DATA_MAINT_PROG_NO = "FCCODS1";
	int rowUpdated = getJdbcTemplateObject().update(EventProcessingSQLIF.UPDATE_CODES_GRID, new Object[]{focusCodeinDVO.getM_strCode(), focusCodeinDVO.getM_strAbbrDesc(), focusCodeinDVO.getM_strDesc(), MAINT_LOGON_ID_NO, DATA_MAINT_PROG_NO, focusCodeinDVO.getM_strCodeName(), focusCodeinDVO.getM_strCode()});
	logger.debug("\n Row updated : "+ rowUpdated);
	
	logger.debug("\n Exiting updateFocusCodes ");
}

/*
 * Apply buitton clicked on Add New Focus codes page
 */
public void applyFocusCodes (RequestDVO requestDVO) throws ApplicationException{
	logger.debug("\n Entering applyFocusCodes ");
	FocusCodeNewAddDVO focusCodeNewAddinDVO = (FocusCodeNewAddDVO)requestDVO.getM_abstractDVO();
	String MAINT_LOGON_ID_NO = "mmt8135";
	String DATA_MAINT_PROG_NO = "FCCODS1";
	int rowUpdated = getJdbcTemplateObject().update(EventProcessingSQLIF.UPDATE_CODES_GRID, new Object[]{focusCodeNewAddinDVO.getM_strCodeNadd(), focusCodeNewAddinDVO.getM_strAbbrDescNadd(), focusCodeNewAddinDVO.getM_strDescNadd(), MAINT_LOGON_ID_NO, DATA_MAINT_PROG_NO, focusCodeNewAddinDVO.getM_strCodeNameNadd(), focusCodeNewAddinDVO.getM_strCodeNadd()});
	logger.debug("\n Row updated : "+ rowUpdated);
	
	logger.debug("\n Exiting applyFocusCodes ");
}

public ResponseDVO getEventProdVolCostGrid(RequestDVO requestDVO) throws ApplicationException{
	logger.debug("\n Entering getEventProdVolCostGrid ");
	/*CALL FCS803P ( |PH_COST_EVENT_NAME|,
	   |EVENT_REV_NO|,
	   |TGT_PLANT_LOC_CODE|,
	   |TGT_MODEL_DEV_CODE|,
	   |MTC_TYPE|,
	   |MTC_OPTION|,
	   |MODEL_CAT_CODE|,
	   |PROD_VOL_TYPE|,
	   |MAINT_LOGON_ID_NO|,
	   |DATA_MAINT_PROG_NO|,
	   |SQLCODE|,
	   |ERROR_MSG|
	)*/
	
	String location = CLASS_NAME + ".getCostChangesGrid";
	ResponseDVO responseDVO = new ResponseDVO();
	ListDVO listDVO = new ListDVO();
    List<EventProdVolDVO> arlEventPrdVolOutList = new ArrayList<EventProdVolDVO>();
    
    EventProdVolFindDVO inDVO = (EventProdVolFindDVO)requestDVO.getM_abstractDVO();
    String str_EventName = inDVO.getM_strEventName();
    BigDecimal big_EventRev = inDVO.getM_bigEventRev();
    String str_Plant = inDVO.getM_strPlantLoc();
    String str_Model = inDVO.getM_strModel();
    String str_Type = inDVO.getM_strType();
    String str_Option = inDVO.getM_strOption();
    String str_ModelCatCode = "";
    String str_ProdVolType = inDVO.getM_strPrdVolRadio();
    int int_ProdVolMnth = inDVO.getM_intPrdVolMnthRadio();
    String str_DataMaintProg = "FCS803P";
    String str_MainLogonId = "mmt0323";
    
  //Test code starts For Connection object
    final String procedureCall = replaceSchemaNames("{call --PROC_SCHEMA--.FCS803P(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
    Connection connection = null;
    
    try{
    	//Get Connection instance from dataSource
        connection = getJdbcTemplateObject().getDataSource().getConnection();
        CallableStatement callableSt = connection.prepareCall(procedureCall);
        
        callableSt.setString(1, str_EventName);
        callableSt.setBigDecimal(2, big_EventRev);
        callableSt.setString(3, str_Plant);
        callableSt.setString(4, str_Model);
        callableSt.setString(5, str_Type);
        callableSt.setString(6, str_Option);
        callableSt.setString(7, str_ModelCatCode);
        callableSt.setString(8, str_ProdVolType);
        callableSt.setString(9, str_MainLogonId);
        callableSt.setString(10, str_DataMaintProg);
        callableSt.registerOutParameter(11, Types.VARCHAR);
        callableSt.registerOutParameter(12, Types.VARCHAR);
        
        ResultSet rs = callableSt.executeQuery();
        
        if(rs != null){
        	int rowKeyid = 0;
        	while(rs.next()){
        		
            	
            	EventProdVolDVO eventProdVolDVO = new EventProdVolDVO();
            	
            	if(int_ProdVolMnth == 15){
            		eventProdVolDVO.setM_strModel(rs.getString(4));
            		eventProdVolDVO.setM_strType(rs.getString(5));
	            	eventProdVolDVO.setM_strOption(rs.getString(6));
	            	eventProdVolDVO.setM_strCatCode(rs.getString(7));
	            	eventProdVolDVO.setM_bigTotal(rs.getBigDecimal(8));
	            	eventProdVolDVO.setM_bigTotal(rs.getBigDecimal(8));
            		eventProdVolDVO.setM_bigJan(rs.getBigDecimal(9));
            		eventProdVolDVO.setM_bigFeb(rs.getBigDecimal(10));
            		eventProdVolDVO.setM_bigMar(rs.getBigDecimal(11));
            		eventProdVolDVO.setM_bigApr(rs.getBigDecimal(12));
                	eventProdVolDVO.setM_bigMay(rs.getBigDecimal(13));
                	eventProdVolDVO.setM_bigJun(rs.getBigDecimal(14));
                	eventProdVolDVO.setM_bigJul(rs.getBigDecimal(15));
                	eventProdVolDVO.setM_bigAug(rs.getBigDecimal(16));
                	eventProdVolDVO.setM_bigSep(rs.getBigDecimal(17));
                	eventProdVolDVO.setM_bigOct(rs.getBigDecimal(18));
                	eventProdVolDVO.setM_bigNov(rs.getBigDecimal(19));
                	eventProdVolDVO.setM_bigDec(rs.getBigDecimal(20));
                	eventProdVolDVO.setM_bigNjan(rs.getBigDecimal(21));
                	eventProdVolDVO.setM_bigNfeb(rs.getBigDecimal(22));
                	eventProdVolDVO.setM_bigNmar(rs.getBigDecimal(23));
                	eventProdVolDVO.setM_intMnth(15);
                	eventProdVolDVO.setM_Mnth15(true);
                	eventProdVolDVO.setM_Mnth12(false);
                	eventProdVolDVO.setM_intRowKeyId(rowKeyid++);
                	
                	arlEventPrdVolOutList.add(eventProdVolDVO);
            	}
            	if(int_ProdVolMnth == 12){
            		eventProdVolDVO.setM_strModel(rs.getString(4));
                	eventProdVolDVO.setM_strType(rs.getString(5));
                	eventProdVolDVO.setM_strOption(rs.getString(6));
                	eventProdVolDVO.setM_strCatCode(rs.getString(7));
                	eventProdVolDVO.setM_bigTotal(rs.getBigDecimal(8));
                	eventProdVolDVO.setM_bigTotal(rs.getBigDecimal(8));
            		eventProdVolDVO.setM_bigApr(rs.getBigDecimal(12));
                	eventProdVolDVO.setM_bigMay(rs.getBigDecimal(13));
                	eventProdVolDVO.setM_bigJun(rs.getBigDecimal(14));
                	eventProdVolDVO.setM_bigJul(rs.getBigDecimal(15));
                	eventProdVolDVO.setM_bigAug(rs.getBigDecimal(16));
                	eventProdVolDVO.setM_bigSep(rs.getBigDecimal(17));
                	eventProdVolDVO.setM_bigOct(rs.getBigDecimal(18));
                	eventProdVolDVO.setM_bigNov(rs.getBigDecimal(19));
                	eventProdVolDVO.setM_bigDec(rs.getBigDecimal(20));
                	eventProdVolDVO.setM_bigNjan(rs.getBigDecimal(21));
                	eventProdVolDVO.setM_bigNfeb(rs.getBigDecimal(22));
                	eventProdVolDVO.setM_bigNmar(rs.getBigDecimal(23));
                	eventProdVolDVO.setM_intMnth(12);
                	eventProdVolDVO.setM_Mnth15(false);
                	eventProdVolDVO.setM_Mnth12(true);
                	eventProdVolDVO.setM_intRowKeyId(rowKeyid++);
                	
                	arlEventPrdVolOutList.add(eventProdVolDVO);
            	}
            	
            	
        	}
        }
        
    }catch(SQLException e){
    	e.printStackTrace();
    }finally {

    	   if(connection != null)
    	    try {
    	    connection.close();
    	    } catch (SQLException e) {
    	    e.printStackTrace();
    	    }
    	    }
    
    listDVO.setList(arlEventPrdVolOutList);
	responseDVO.setM_abstractDVO(listDVO);
	
	logger.debug("\n Exiting getEventProdVolCostGrid ");
	return responseDVO;
}
//change pscc-6605
public ResponseDVO applyEventApplication(RequestDVO requestDVO) {

    logger.debug("\n Entering applyEventApplication()");
    ResponseDVO responseDVO = new ResponseDVO();

    ListDVO inDVO = (ListDVO) requestDVO.getM_abstractDVO();
    List<EventApplicationDVO> inList = inDVO.getList();

    try {

        for (EventApplicationDVO obj : inList) {

            String progNo = (obj.getM_strEventName().contains("ACT")
                    && "Y".equalsIgnoreCase(obj.getM_strIsCopyBOM())
                    && obj.getM_strDataMaintProgNo() != null)
                            ? obj.getM_strDataMaintProgNo()
                            : "FCEPAS1";

            // If old design section is null or blank, use current design section
            String oldDesignSection = obj.getM_strOldDesignSection();

            if (oldDesignSection == null || oldDesignSection.trim().isEmpty()) {
                oldDesignSection = obj.getM_strDesignSection();
            }

            

            int rowsUpdated = getJdbcTemplateObject().update(
                    APPLY_EVENT_APPLICATION,
                    obj.getM_bRate(),
                    new BigDecimal(obj.getM_intQuantity()),
                    obj.getM_strDesignSection(),
                    obj.getM_strIsSupply() == null ? "" : obj.getM_strIsSupply(),
                    requestDVO.getM_userInfoDVO().getM_strUserLogonId(),
                    progNo,
                    obj.getM_strEventName(),
                    obj.getM_decEventRevNo(),
                    obj.getM_strPartNumber(),
                    obj.getM_strPartColor(),
                    obj.getM_strSupplierNumber(),
                    obj.getM_strShipToCode(),
                    oldDesignSection,
                    obj.getM_strHPlant(),
                    obj.getM_strTPlant(),
                    obj.getM_strModel(),
                    obj.getM_strMTCType(),
                    obj.getM_strMTCOption(),
                    obj.getM_strCategory());

            System.out.println("\nRows Updated = " + rowsUpdated);
            System.out.println("=========================================================\n");
        }

    } catch (org.springframework.dao.DuplicateKeyException e) {

        if (e.getMessage() != null && e.getMessage().contains("SQLCODE=-803")) {
            throw new RuntimeException(
                    "One or more selected parts have the same Part Number, Part Color, Supplier, Ship To, Plant, Model, MTC Type, MTC Option and Category. Please select a different Design Section.");
        }

        throw e;
    }

    logger.debug("\n Exiting applyEventApplication()");
    return responseDVO;
}
	//change-pscc-6605
	
	/*public ResponseDVO applyEventApplicationToAttachedBudget(RequestDVO requestDVO){
		logger.debug("\n Entering applyEventApplicationToAttachedBudget ");
		EventApplicationDVO evDVO = (EventApplicationDVO) ((ListDVO)requestDVO.getM_abstractDVO()).getList().get(0);
		RequestDVO requestDVOForBudget = new RequestDVO();
		requestDVOForBudget.setM_abstractDVO(evDVO);
		requestDVOForBudget.setM_userInfoDVO(requestDVO.getM_userInfoDVO());
		//String attachedBudgetEvent = validatePriorBudgetEventFreezCheck(requestDVOForFreez);
		String[] attachedBudgetEvent = validateActEventIsAttachedToBudget(requestDVOForBudget);
		ResponseDVO responseDVO = new ResponseDVO(); 
		if(attachedBudgetEvent!=null && !"".equalsIgnoreCase(attachedBudgetEvent[0])){
			ListDVO inDVO = (ListDVO)requestDVO.getM_abstractDVO();
			List<EventApplicationDVO> inList = inDVO.getList();
			for(EventApplicationDVO obj : inList){
				//Need to add a parameter IsSupply to be update
				getJdbcTemplateObject().update(APPLY_EVENT_APPLICATION,obj.getM_bRate(),
																	   new BigDecimal(obj.getM_intQuantity()),
																	   obj.getM_strIsSupply()==null ? "": obj.getM_strIsSupply(),
																	   requestDVO.getM_userInfoDVO().getM_strUserLogonId(),
																	   "FCEPAS1",
																	   attachedBudgetEvent[0].trim(),
																	   attachedBudgetEvent[1].trim(),
																	   obj.getM_strPartNumber(),
																	   obj.getM_strPartColor(),
																	   obj.getM_strSupplierNumber(),
																	   obj.getM_strShipToCode(),
																	   obj.getM_strDesignSection(),
																	   obj.getM_strHPlant(),
																	   obj.getM_strTPlant(),
																	   obj.getM_strModel(),
																	   obj.getM_strMTCType(),
																	   obj.getM_strMTCOption(),
																	   obj.getM_strCategory());
			}
		}
		logger.debug("\n Exiting applyEventApplicationToAttachedBudget ");
		return responseDVO;
	}*/
	//change pscc-6605
public ResponseDVO applyEventApplicationToAttachedBudget(RequestDVO requestDVO) {

    logger.debug("\n Entering applyEventApplicationToAttachedBudget ");

    EventApplicationDVO evDVO = (EventApplicationDVO) ((ListDVO) requestDVO.getM_abstractDVO()).getList().get(0);
    RequestDVO requestDVOForBudget = new RequestDVO();
    requestDVOForBudget.setM_abstractDVO(evDVO);
    requestDVOForBudget.setM_userInfoDVO(requestDVO.getM_userInfoDVO());

    String[] attachedBudgetEvent = validateActEventIsAttachedToBudget(requestDVOForBudget);

    ResponseDVO responseDVO = new ResponseDVO();

    if (attachedBudgetEvent != null && !"".equalsIgnoreCase(attachedBudgetEvent[0])) {

        ListDVO inDVO = (ListDVO) requestDVO.getM_abstractDVO();
        List<EventApplicationDVO> inList = inDVO.getList();

        String str_DataMaintProg = "FCS699P";
        String str_UserInfo = "FCS699P";

        for (EventApplicationDVO obj : inList) {

            final String procedureCall = replaceSchemaNames(
                    "{call --PROC_SCHEMA--.FCS699P(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            Connection connection = null;

            try {

                connection = getJdbcTemplateObject().getDataSource().getConnection();

                CallableStatement callableSt = connection.prepareCall(procedureCall);

                if (obj.getM_strEventName().contains("KI")) {
                    callableSt.setString(1, obj.getM_strEventName().trim());
                    callableSt.setString(2, String.valueOf(obj.getM_decEventRevNo()));
                } else {
                    callableSt.setString(1, attachedBudgetEvent[0].trim());
                    callableSt.setString(2, attachedBudgetEvent[1].trim());
                }

                callableSt.setString(3, obj.getM_strTPlant());
                callableSt.setString(4, obj.getM_strPartNumber());
                callableSt.setString(5, obj.getM_strPartColor());
                callableSt.setString(6, obj.getM_strSupplierNumber());
                callableSt.setString(7, obj.getM_strShipToCode());
                callableSt.setString(8, obj.getM_strOldDesignSection());
                callableSt.setString(9, obj.getM_strModel());
                callableSt.setString(10, obj.getM_strMTCType());
                callableSt.setString(11, obj.getM_strMTCOption());
                callableSt.setString(12, obj.getM_strCategory());
                callableSt.setString(13, obj.getM_strHPlant());
                callableSt.setBigDecimal(14, obj.getM_bRate());
                callableSt.setInt(15, obj.getM_intQuantity());
                callableSt.setString(16, str_DataMaintProg);
                callableSt.setString(17, str_UserInfo);

                callableSt.registerOutParameter(18, Types.VARCHAR);
                callableSt.registerOutParameter(19, Types.VARCHAR);

                callableSt.executeUpdate();

                logger.debug("\n FCS699P SQL Code : " + callableSt.getString(18));
                logger.debug("\n FCS699P SQL Message : " + callableSt.getString(19));

            } catch (SQLException e) {

                if (e.getErrorCode() == -803) {

                    throw new RuntimeException(
                            "Duplicate part found. Parts with all the same values must have different Design Sections.");
                }

                throw new RuntimeException(e);

            } catch (Exception e) {

                throw new RuntimeException(e);

            } finally {

                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    logger.debug("\n Exiting applyEventApplicationToAttachedBudget ");

    return responseDVO;
}
	//change pscc-6605
	
	public String validateEventIsActualOrBudget(RequestDVO requestDVO){
		
		logger.debug("\n Entering validateEventIsActualOrBudget ");
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String type = null;
		
		if(null != requestDVO){
			EventApplicationDVO eaDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
			if(null != eaDVO.getM_strEventName() && !eaDVO.getM_strEventName().trim().isEmpty()){
				resultList = getJdbcTemplateObject().queryForList(GET_PH_CMS_ITEM_CODE , new Object[]{eaDVO.getM_strEventName() , eaDVO.getM_decEventRevNo()});  
				for(Map<String , Object> result : resultList){
					type = Utility.convertNullToBlank(
							Utility.trimStringValue(String.valueOf(result.get(ApplicationConstantsIF.APP_CONSTANTS.PH_CMS_ITEM_CODE_COLUMN))));
					break;
				}
			}
		}
		logger.debug("\n Exiting validateEventIsActualOrBudget ");
		return type;
	}
	
	public String[] validateActEventIsAttachedToBudget(RequestDVO requestDVO){
		
		logger.debug("\n Entering validateActEventIsAttachedToBudget ");
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String[] typeRev = new String[2];
		
		if(null != requestDVO){
			EventApplicationDVO eaDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
			if(null != eaDVO.getM_strEventName() && !eaDVO.getM_strEventName().trim().isEmpty()){
				logger.debug("params - "+eaDVO.getM_strEventName()+" "+eaDVO.getM_decEventRevNo() );
				resultList = getJdbcTemplateObject().queryForList(CHECK_ACTEVENT_INANYPRIOR_EVENT , new Object[]{eaDVO.getM_strEventName() , eaDVO.getM_decEventRevNo()});  
				for(Map<String , Object> result : resultList){
					typeRev[0] = Utility.convertNullToBlank(Utility.trimStringValue(String.valueOf(result.get(ApplicationConstantsIF.APP_CONSTANTS.PH_COST_EVENT_NAME))));
					typeRev[1] = Utility.convertNullToBlank(Utility.trimStringValue(String.valueOf(result.get("EVENT_REV_NO"))));
					break;
				}
			}
		}
		logger.debug("\n Exiting validateActEventIsAttachedToBudget ");
		return typeRev;
	}
	
	public String validatePriorBudgetEventFreezCheck(RequestDVO requestDVO,String[] priorBudgetEvent){
		
		logger.debug("\n Entering validatePriorBudgetEventFreezCheck ");
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String type = null;
		
		if(null != requestDVO){
			EventApplicationDVO eaDVO = (EventApplicationDVO)requestDVO.getM_abstractDVO();
			if(null != eaDVO.getM_strEventName() && !eaDVO.getM_strEventName().trim().isEmpty() 
					&& priorBudgetEvent!=null && !"".equalsIgnoreCase(priorBudgetEvent[0]) && !"".equalsIgnoreCase(priorBudgetEvent[1])){
				resultList = getJdbcTemplateObject().queryForList(FREEZE_CHECK_QUERY , new Object[]{priorBudgetEvent[0] , priorBudgetEvent[1]});  
				for(Map<String , Object> result : resultList){
					type = Utility.convertNullToBlank(
							Utility.trimStringValue(String.valueOf(result.get(ApplicationConstantsIF.APP_CONSTANTS.UPD_ACTIVITY_CODE_COLUMN))));
					break;
				}
			}
		}
		logger.debug("\n Exiting validatePriorBudgetEventFreezCheck ");
		return type;
	}

/*
 * Method updates data from Event prod vol cost grid in DB
 */
public void updateEventProdVolCost(RequestDVO requestDVO) throws ApplicationException{
	logger.debug("\n Entering updateEventProdVolCost ");
	/*CALL FCS804P (|1|,
            |2|,
            |3|,
            |4|,
            |5|,
            |6|,
            |7|,                          
            |9|,
            |10|,
            |11|,
            |12|,
            |13|,
            |14|,
            |15|,
            |16|,
            |17|,
            |18|,
            |19|,
            |20|,
            |21|,
            |22|,
            |23|,                          
|PROD_VOL_TYPE|,
            |MODEL_YEAR_DATE|, 
            |MAINT_LOGON_ID_NO|,
            |DATA_MAINT_PROG_NO|,
            |SQLCODE|,
            |ERROR_MSG|)
	*/
	/*
	PI-MONTH-PLAN-QTY-01 
    PI-MONTH-PLAN-QTY-02 
    PI-MONTH-PLAN-QTY-03 
    PI-MONTH-PLAN-QTY-04 
    PI-MONTH-PLAN-QTY-05 
    PI-MONTH-PLAN-QTY-06 
    PI-MONTH-PLAN-QTY-07 
    PI-MONTH-PLAN-QTY-08 
    PI-MONTH-PLAN-QTY-09 
    PI-MONTH-PLAN-QTY-10 
    PI-MONTH-PLAN-QTY-11 
    PI-MONTH-PLAN-QTY-12 
    PI-MONTH-PLAN-QTY-13 
    PI-MONTH-PLAN-QTY-14 
    PI-MONTH-PLAN-QTY-15 */
    
	String location = CLASS_NAME + ".updateEventProdVolCost";
	ResponseDVO responseDVO = new ResponseDVO();
	ListDVO listDVO = new ListDVO();
    //List<EventProdVolDVO> arlEventPrdVolOutList = new ArrayList<EventProdVolDVO>();
    
    EventProdVolDVO inDVO = (EventProdVolDVO)requestDVO.getM_abstractDVO();



    String str_EventName = inDVO.getM_strEventName();// PI-PH-COST-EVENT-NAME
    BigDecimal big_EventRev = inDVO.getN_bigEventRev();// PI-EVENT-REV-NO      
    String str_PlantLoc = inDVO.getM_strPlantLoc();// PI-TGT-PLANT-LOC-CODE
    String str_Model = inDVO.getM_strModel(); // 4PI-TGT-MODEL-DEV-CODE
    String str_Type = inDVO.getM_strType(); // PI-MTC-TYPE 
    String str_Option = inDVO.getM_strOption(); // PI-MTC-OPTION         
    String str_catCode = inDVO.getM_strCatCode(); //PI-MODEL-CAT-CODE 
    //BigDecimal big_Total = inDVO.getM_bigTotal();
    BigDecimal big_JanMnth = inDVO.getM_bigJan();
    BigDecimal big_FebMnth = inDVO.getM_bigFeb();
    BigDecimal big_MarMnth = inDVO.getM_bigMar();
    BigDecimal big_AprMnth = inDVO.getM_bigApr();
    BigDecimal big_MayMnth = inDVO.getM_bigMay();
    BigDecimal big_JunMnth = inDVO.getM_bigJun();
    BigDecimal big_JulMnth = inDVO.getM_bigJul();
    BigDecimal big_AugMnth = inDVO.getM_bigAug();
    BigDecimal big_SepMnth = inDVO.getM_bigSep();
    BigDecimal big_OctMnth = inDVO.getM_bigOct();
    BigDecimal big_NovMnth = inDVO.getM_bigNov();
    BigDecimal big_DecMnth = inDVO.getM_bigDec();
    BigDecimal big_NjanMnth = inDVO.getM_bigNjan();
    BigDecimal big_NfebMnth = inDVO.getM_bigNfeb();
    BigDecimal big_NmarMnth = inDVO.getM_bigNmar();
    String str_PrdVolType = inDVO.getM_strPrdVolRadio(); //_  PI-PROD-VOL-TYPE     
    BigDecimal big_ModelYear = inDVO.getN_bigModelYear(); // model year PI-MODEL-YEAR-DATE          
    String str_DataMaintProg = "FCS803P"; // PI-DATA-MAINT-PROG-NO
    String str_UserInfo = "mmt0323"; // PI-USER-INFO
    //PO-SQLCODE out   
    //PO-ERROR-MSG out  
     
  //Test code starts For Connection object
    final String procedureCall = replaceSchemaNames("{call --PROC_SCHEMA--.FCS804P(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
    Connection connection = null;

    try{
    	//Get Connection instance from dataSource
        connection = getJdbcTemplateObject().getDataSource().getConnection();
        CallableStatement callableSt = connection.prepareCall(procedureCall);
        
        callableSt.setString(1, str_EventName);
        callableSt.setBigDecimal(2, big_EventRev);
        callableSt.setString(3, str_PlantLoc);
        callableSt.setString(4, str_Model);
        callableSt.setString(5, str_Type);
        callableSt.setString(6, str_Option);
        callableSt.setString(7, str_catCode);
        callableSt.setBigDecimal(8, big_JanMnth);
        callableSt.setBigDecimal(9, big_FebMnth);
        callableSt.setBigDecimal(10, big_MarMnth);
        callableSt.setBigDecimal(11, big_AprMnth);
        callableSt.setBigDecimal(12, big_MayMnth);
        callableSt.setBigDecimal(13, big_JunMnth);
        callableSt.setBigDecimal(14, big_JulMnth);
        callableSt.setBigDecimal(15, big_AugMnth);
        callableSt.setBigDecimal(16, big_SepMnth);
        callableSt.setBigDecimal(17, big_OctMnth);
        callableSt.setBigDecimal(18, big_NovMnth);
        callableSt.setBigDecimal(19, big_DecMnth);
        callableSt.setBigDecimal(20, big_NjanMnth);
        callableSt.setBigDecimal(21, big_NfebMnth);
        callableSt.setBigDecimal(22, big_NmarMnth);
        callableSt.setString(23, str_PrdVolType);// vol type
        callableSt.setBigDecimal(24, big_ModelYear);
        callableSt.setString(25, str_DataMaintProg);
        callableSt.setString(26, str_UserInfo);
        callableSt.registerOutParameter(27, Types.VARCHAR);
        callableSt.registerOutParameter(28, Types.VARCHAR);
        
        int status = callableSt.executeUpdate();
        
        
        
    } catch(Exception e){
    	e.printStackTrace();
    }finally {

 	   if(connection != null)
 	    try {
 	    connection.close();
 	    } catch (SQLException e) {
 	    e.printStackTrace();
 	    }
 	    }
    
    
    //return responseDVO;
}


	public ResponseDVO addEventApplication(RequestDVO requestDVO) throws Exception {
		
		ResponseDVO responseDVO = new ResponseDVO();
		AbstractDVO abstractDVO = new AbstractDVO();
		Connection conn = null;
		CallableStatement cstmt = null;
		int result = 0;
		boolean isSuccess = false;
		String returnCode = null;
		String returnMessage = null;
		String mtcType1[]=new String[15];
	    try{
	    	
			EventApplicationDVO inDVO = (EventApplicationDVO) requestDVO.getM_abstractDVO();
			for (int i = 0; i < inDVO.getM_strMTCType1().size(); i++) {
        		 mtcType1[i] = inDVO.getM_strMTCType1().get(i);
            }
			//need to pass is supply flag to persist
	    	final String procedureCall = replaceSchemaNames("{call --PROC_SCHEMA--.FCS618P(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
	    			"?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
	        conn = getJdbcTemplateObject().getDataSource().getConnection();
	    
	        cstmt = conn.prepareCall(procedureCall);
	        cstmt.setString(1, Utility.convertNullToBlank(inDVO.getM_strEventName()));
	        cstmt.setBigDecimal(2, inDVO.getM_decEventRevNo());
	        cstmt.setString(3, Utility.convertNullToBlank(inDVO.getM_strPartNumber()));
	        cstmt.setString(4, Utility.convertNullToBlank(inDVO.getM_strPartColor()));
	        cstmt.setString(5, Utility.convertNullToBlank(inDVO.getM_strSupplierNumber()));
	        cstmt.setString(6, Utility.convertNullToBlank(inDVO.getM_strShipToCode()));
	        cstmt.setString(7, Utility.convertNullToBlank(inDVO.getM_strDesignSection()));
	        cstmt.setString(8, Utility.convertNullToBlank(inDVO.getM_strTargetPlant()));
	        cstmt.setString(9, Utility.convertNullToBlank(inDVO.getM_strModel()));
	        cstmt.setString(10, Utility.convertNullToBlank( mtcType1[0]));
	        cstmt.setString(11, Utility.convertNullToBlank( mtcType1[1]));
	        cstmt.setString(12, Utility.convertNullToBlank( mtcType1[2]));
	        cstmt.setString(13, Utility.convertNullToBlank( mtcType1[3]));
	        cstmt.setString(14, Utility.convertNullToBlank( mtcType1[4]));
	        cstmt.setString(15, Utility.convertNullToBlank( mtcType1[5]));
	        cstmt.setString(16, Utility.convertNullToBlank( mtcType1[6]));
	        cstmt.setString(17, Utility.convertNullToBlank( mtcType1[7]));
	        cstmt.setString(18, Utility.convertNullToBlank( mtcType1[8]));
	        cstmt.setString(19, Utility.convertNullToBlank( mtcType1[9]));
	        cstmt.setString(20, Utility.convertNullToBlank( mtcType1[10]));
	        cstmt.setString(21, Utility.convertNullToBlank( mtcType1[11]));
	        cstmt.setString(22, Utility.convertNullToBlank( mtcType1[12]));
	        cstmt.setString(23, Utility.convertNullToBlank( mtcType1[13]));
	        cstmt.setString(24, Utility.convertNullToBlank( mtcType1[14]));
	        cstmt.setString(25, Utility.convertNullToBlank(inDVO.getM_strMTCOption()));
	        cstmt.setString(26, Utility.convertNullToBlank(inDVO.getM_strCategory()));
	        /*
	        cstmt.setString(9, inDVO.getM_strModel());
	        cstmt.setString(10, Utility.convertNullToBlank(inDVO.getM_strMTCType()));
	        cstmt.setString(11, Utility.convertNullToBlank(inDVO.getM_strMTCOption()));
	        cstmt.setString(12, Utility.convertNullToBlank(inDVO.getM_strCategory()));
	        */
	        //cstmt.setBigDecimal(13, new BigDecimal(inDVO.getM_intRate()));
	        cstmt.setBigDecimal(27, inDVO.getM_bRate());
	        cstmt.setBigDecimal(28, new BigDecimal(inDVO.getM_intQuantity()));
	        //Commented below line as it will be moved with Purchase Changes.
	        //cstmt.setString(15, inDVO.getM_strIsSupply());
	        cstmt.setString(29, "");
	        cstmt.setString(30, "FCEPAD1");
	        //cstmt.setString(16, "mmt9569");
	        cstmt.setString(31, requestDVO.getM_userInfoDVO().getM_strUserLogonId());
	        cstmt.registerOutParameter(32, Types.VARCHAR);
	        cstmt.registerOutParameter(33, Types.VARCHAR);
	        result = cstmt.executeUpdate();
		    isSuccess = true;

		    logger.debug("\n FCS618P SQL Code : "+cstmt.getString(32));
		    logger.debug("\n FCS618P SQL Message : "+cstmt.getString(33));
		    
		    returnCode = cstmt.getString(32);
		    returnMessage = cstmt.getString(33);
		    
		    
            if(null != returnCode && returnCode.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_RETURN_CODE)
            		&& (null == returnMessage || returnMessage.trim().isEmpty())){
            	
            	abstractDVO.setObject(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS);
            	
            } else if(returnCode.equals("000000100+") && returnMessage.contains("FCS618P 0250 SELECT")){
		    	abstractDVO.setObject(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS);
		    } else if(returnMessage.contains("APPLICATION RECORDS INSER")){
		    	abstractDVO.setObject(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS);
		    }            
            else {
            	
            	abstractDVO.setObject(returnMessage);
            }
		    
	       }catch (SQLException e) {
	    	   e.printStackTrace();
	    	   throwDatabaseException(e);
	       } finally {
	    	   if(conn != null){
	    		   try {
	    			   conn.close();
	    		   } catch (SQLException e) {
	    			   e.printStackTrace();
	    		   }
	    	   }
	    	   if(cstmt != null){
	    		   try {
	    			   cstmt.close();
	    		   } catch (SQLException e) {
	    			   e.printStackTrace();
	    		   }
	    	   }
	        }
		
	       responseDVO.setM_abstractDVO(abstractDVO);
	       
	       return responseDVO;
	
	}
	
	public ResponseDVO performCopyEventApplication(RequestDVO requestDVO) throws Exception {

		logger.debug("\n Entering performCopyEventApplication() ");
		
		ResponseDVO responseDVO = new ResponseDVO();
		Connection conn = null;
		CallableStatement cstmt = null;
		int result = 0;
		boolean isSuccess = false;
		String returnCode = null;
		String returnMessage = null;
		AbstractDVO abstractDVO = new AbstractDVO();
		
	    try{
	    	
			EventApplicationDVO inDVO = (EventApplicationDVO) requestDVO.getM_abstractDVO();
	 
			
	    	final String procedureCall = replaceSchemaNames("{call --PROC_SCHEMA--.FCS618P(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
	        conn = getJdbcTemplateObject().getDataSource().getConnection();
	    
	        cstmt = conn.prepareCall(procedureCall);
	        cstmt.setString(1, Utility.convertNullToBlank(inDVO.getM_strEventName()));
	        cstmt.setBigDecimal(2, inDVO.getM_decEventRevNo());
	        cstmt.setString(3, Utility.convertNullToBlank(inDVO.getM_strPartNumber()));
	        cstmt.setString(4, Utility.convertNullToBlank(inDVO.getM_strPartColor()));
	        cstmt.setString(5, Utility.convertNullToBlank(inDVO.getM_strSupplierNumber()));
	        cstmt.setString(6, Utility.convertNullToBlank(inDVO.getM_strShipToCodeToCopy()));
	        cstmt.setString(7, Utility.convertNullToBlank(inDVO.getM_strDesignSectionToCopy()));
	        cstmt.setString(8, Utility.convertNullToBlank(inDVO.getM_strTargetPlantToCopy()));
	        cstmt.setString(9, Utility.convertNullToBlank(inDVO.getM_strModelToCopy()));
	        cstmt.setString(10, Utility.convertNullToBlank(inDVO.getM_strMTCTypeToCopy()));
	        cstmt.setString(11, Utility.convertNullToBlank(inDVO.getM_strMTCOptionToCopy()));
	        cstmt.setString(12, Utility.convertNullToBlank(inDVO.getM_strCategoryToCopy()));
	        //cstmt.setBigDecimal(13, new BigDecimal(inDVO.getM_intRateToCopy()) );
	        cstmt.setBigDecimal(13, new BigDecimal(inDVO.getM_strRateToCopy()) );
	        cstmt.setBigDecimal(14, new BigDecimal(String.valueOf(inDVO.getM_intQuantityToCopy())) );
	      //Commented below line as it will be moved with Purchase Changes.
	        //cstmt.setString(15, inDVO.getM_strIsSupplyToCopy());
	        cstmt.setString(15, "");
	        cstmt.setString(16, "FCEPAD2");
	        //cstmt.setString(16, "mmt9569");
	        cstmt.setString(17, requestDVO.getM_userInfoDVO().getM_strUserLogonId());
	        cstmt.registerOutParameter(18, Types.VARCHAR);
	        cstmt.registerOutParameter(19, Types.VARCHAR);
	        result = cstmt.executeUpdate();
		    isSuccess = true;
		    
		    logger.debug("\n FCS618P SQL Code : "+cstmt.getString(18));
		    logger.debug("\n FCS618P SQL Message : "+cstmt.getString(19));
		    
		    returnCode = cstmt.getString(18);
		    returnMessage = cstmt.getString(19);
		    
            /*if(null != returnCode && returnCode.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_RETURN_CODE)
            		&& (null == returnMessage || returnMessage.trim().isEmpty())){
            	
            	abstractDVO.setObject(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS);
            	
            } else {
            	
            	abstractDVO.setObject(returnMessage);
            }*/
		    
		    if(null != returnCode && returnCode.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_RETURN_CODE)
            		&& (null == returnMessage || returnMessage.trim().isEmpty())){
            	
            	abstractDVO.setObject(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS);
            	
            } else if(returnCode.equals("000000100+") && returnMessage.contains("FCS618P 0250 SELECT")){
		    	abstractDVO.setObject(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS);
		    } else if(returnMessage.contains("APPLICATION RECORDS INSER")){
		    	abstractDVO.setObject(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS);
		    }            
            else {
            	
            	abstractDVO.setObject(returnMessage);
            }
		    
	       }catch (SQLException e) {
	    	   e.printStackTrace();
	    	   throwDatabaseException(e);
	       } finally {
	    	   if(conn != null){
	    		   try {
	    			   conn.close();
	    		   } catch (SQLException e) {
	    			   e.printStackTrace();
	    		   }
	    	   }
	    	   if(cstmt != null){
	    		   try {
	    			   cstmt.close();
	    		   } catch (SQLException e) {
	    			   e.printStackTrace();
	    		   }
	    	   }
	        }
		
	       logger.debug("\n Exiting performCopyEventApplication() ");
	       responseDVO.setM_abstractDVO(abstractDVO);
	       
		return responseDVO;		
		
	}


	public ResponseDVO getFEMDModel(RequestDVO requestDVO) {
		ResponseDVO responseDVO = new ResponseDVO();
		String modelCode = "";
		String query = GET_FEMD_MODEL;
		HashMapDVO hMapDVO = (HashMapDVO) requestDVO.getM_abstractDVO();
		HashMap<String, String> hMap = hMapDVO.getM_hmpMap();
		HashMap<String, String> resultMap = new HashMap<String, String>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		if(hMap.get(ApplicationConstantsIF.APP_CONSTANTS.CATEGORY_DROPDOWN).equalsIgnoreCase("F"))
		{
			query = query.replace("--COLUMN--", "TGT_MODEL_DEV_CODE AS MODEL, MTC_TYPE AS TYPE , MTC_OPTION AS OPTION");
		}
		else if(hMap.get(ApplicationConstantsIF.APP_CONSTANTS.CATEGORY_DROPDOWN).equalsIgnoreCase("E"))
		{
			query = query.replace("--COLUMN--", "TGT_ENG_FR_MOD_CDE AS MODEL , TGT_ENG_FR_TY_CODE AS TYPE , TGT_ENGFR_OPT_CODE AS OPTION");
		}
		else if(hMap.get(ApplicationConstantsIF.APP_CONSTANTS.CATEGORY_DROPDOWN).equalsIgnoreCase("M"))
		{
			query = query.replace("--COLUMN--", "TGT_MIS_FR_MOD_CDE AS MODEL , TGT_ENG_FR_TY_CODE AS TYPE , TGT_MISFR_OPT_CODE AS OPTION");
		}
		else if(hMap.get(ApplicationConstantsIF.APP_CONSTANTS.CATEGORY_DROPDOWN).equalsIgnoreCase("D"))
		{
			query = query.replace("--COLUMN--", "TGT_DIF_FR_MOD_CDE AS MODEL , TGT_DIF_FR_TY_CODE AS TYPE , TGT_DIFFR_OPT_CODE AS OPTION");
		}
		else
		{
			resultMap.put("model", "");
			resultMap.put("type", "");
			resultMap.put("option", "");
			hMapDVO.setM_hmpMap(resultMap);
			responseDVO.setM_abstractDVO(hMapDVO);
			
			return responseDVO;
		}
		resultList =getJdbcTemplateObject().queryForList(query, new Object[]{Utility.isStringNullOrEmpty(hMap.get(ApplicationConstantsIF.APP_CONSTANTS.PH_COST_EVENT_NAME_COLUMN))? "" : hMap.get(ApplicationConstantsIF.APP_CONSTANTS.PH_COST_EVENT_NAME_COLUMN)
				, Utility.isStringNullOrEmpty(hMap.get(ApplicationConstantsIF.APP_CONSTANTS.EVENT_REV_NO_COLUMN)) ? "" :hMap.get(ApplicationConstantsIF.APP_CONSTANTS.EVENT_REV_NO_COLUMN)
				,Utility.isStringNullOrEmpty(hMap.get(ApplicationConstantsIF.APP_CONSTANTS.MODEL_CODE)) ? "" : hMap.get(ApplicationConstantsIF.APP_CONSTANTS.MODEL_CODE)
				, Utility.isStringNullOrEmpty(hMap.get(ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE)) ? "" : hMap.get(ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE)
				, Utility.isStringNullOrEmpty(hMap.get(ApplicationConstantsIF.APP_CONSTANTS.MTC_OPTION)) ? "" : hMap.get(ApplicationConstantsIF.APP_CONSTANTS.MTC_OPTION)});  
		for(Map<String , Object> result  : resultList){
			resultMap.put(ApplicationConstantsIF.APP_CONSTANTS.MODEL_CODE, (String) result.get("MODEL"));
			resultMap.put(ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE, (String) result.get("TYPE"));
			resultMap.put(ApplicationConstantsIF.APP_CONSTANTS.MTC_OPTION, (String) result.get("OPTION"));
			break;
		}
		hMapDVO.setM_hmpMap(resultMap);
		responseDVO.setM_abstractDVO(hMapDVO);
		
		return responseDVO;
	}
	
	public List<Map<String, Object>> populateCategoryOnModelSelection(String m_strModelDlg) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		resultList =getJdbcTemplateObject().queryForList(replaceSchemaNames(GET_MODEL_CAT_CODE_ON_MODEL_SELECTION), new Object[]{m_strModelDlg});
		return resultList;
	}


	public Integer checkSuppByPartAndEvent(String m_strPartNumber, String eventName, Integer eventRev) throws ApplicationException {
		
		int rowCount = 0;
		try{
			
			rowCount = getJdbcTemplateObject().queryForObject(replaceSchemaNames(CHECK_FOR_SUPP_BY_EVENT_AND_PART) , new Object[]{m_strPartNumber, eventName, eventRev.toString()}, Integer.class);
			
		}
		catch (Exception e) {
			throwDatabaseException(e);
		}
		return rowCount;
	}

}

