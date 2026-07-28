/**
 * 
 */
package com.honda.cart2.dataaccess.eventprocessing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.honda.cart2.common.dvo.AbstractDVO;
import com.honda.cart2.common.dvo.KeyValuePairDVO;

/**
 * @author VCC89569
 *
 */
public class EventApplicationDVO  extends AbstractDVO{
	
	private String m_strKey;
	private String m_strEventName;
	private String m_strEventNameDesc;
	private int m_intRevNo;
	private String m_strPartNumber;
	private String m_strPartName;
	private String m_strPartColor;
	private String m_strSupplierNumber;
	private String m_strSupplierName;
	private String m_strShipTo;
	private String m_strSection;
	private String m_strHPlant;
	private String m_strTPlant;
	private String m_strModel;
	private String m_strOldModel;
	private String m_strType;
	private String m_strOption;
	private String m_strCategory;
	private String m_strOldCategory;
	private String m_strQty;
	
	private String m_strShareRate;
	private String m_strDesignSec;
	private String m_strShipToCode;
	private String m_strOldShipToCode;
	private List<KeyValuePairDVO> m_arlFeatureList;

	private String m_strMTCType;
	private ArrayList<String> m_strMTCType1;
	public ArrayList<String> getM_strMTCType1() {
		return m_strMTCType1;
	}
	public void setM_strMTCType1(ArrayList<String> type1) {
		m_strMTCType1 = type1;
	}
	private String m_strOldMTCType;
	private ArrayList<String> m_strOldMTCType1;
	public ArrayList<String> getM_strOldMTCType1() {
		return m_strOldMTCType1;
	}
	public void setM_strOldMTCType1(ArrayList<String> oldMTCType1) {
		m_strOldMTCType1 = oldMTCType1;
	}
	private String m_strMTCOption;
	private String m_strOldMTCOption;
	private BigDecimal m_decEventRevNo;
	private String m_strRPTCurrency;
	private String m_strDesignSection;
	private String m_strOldDesignSection;
	private String m_strProcSection;
	private String m_strFeatureCode;
	private String m_strTargetPlant;
	private String m_strOldTargetPlant;
	private Integer m_intRate;
	private BigDecimal m_bRate;
	private Integer m_intQuantity;
	private BigDecimal m_bQuantity;
	
	private String m_strAvailableProductEquipCode;
	private String m_strAssignedProductEquipCode;
	
	
	//Event Application Copy Screen : Start 
	private String m_strDesignSectionFromCopy;
	private String m_strDesignSectionToCopy;
	private String m_strTargetPlantFromCopy;
	private String m_strTargetPlantToCopy;
	private String m_strModelFromCopy;
	private String m_strModelToCopy;
	private String m_strMTCTypeFromCopy;
	private ArrayList<String> m_strMTCTypeFromCopy1;
	public ArrayList<String> getM_strMTCTypeFromCopy1() {
		return m_strMTCTypeFromCopy1;
	}
	public void setM_strMTCTypeFromCopy1(ArrayList<String> typeFromCopy1) {
		m_strMTCTypeFromCopy1 = typeFromCopy1;
	}
	private String m_strMTCTypeToCopy;
	private String m_strMTCOptionFromCopy;
	private String m_strMTCOptionToCopy;
	private String m_strCategoryFromCopy;
	private String m_strCategoryToCopy;
	private Integer m_intQuantityFromCopy;
	private Integer m_intQuantityToCopy;
	private Integer m_intRateFromCopy;
	private Integer m_intRateToCopy;
	private String m_strRateToCopy;
	private String m_strShipToCodeFromCopy;
	private String m_strShipToCodeToCopy;
	private String m_strIsSupplyFromCopy;
	private String m_strIsSupplyToCopy;
	//Event Application Copy Screen : End
	
	private String m_strSearchType;
	private String m_strEditedRowKeyId;
	private String m_strIsSupply;
	private String m_strSpecificSearch;
	private Integer m_intQtyForSelected;
	private BigDecimal m_bRateForSelected;
	private String m_strIsSupplyForSelected;
	
	/* Project Id:- PR03601
	 * Changes Done: Variables to store data of newly added columns" 
	 */
	private String m_strMTAT;
	private String m_strSupply;
	
	private String m_strGrade;
	private String m_strAtMt;
	private String m_strVehicle;
	
	private boolean m_bSelected;
	
	//New variable added for CR- Copy BOM MTO's will be differentiated on screen
	private String m_strIsCopyBOM;
    private String m_strViewMTO;
    private String m_strDataMaintProgNo;
	
	public EventApplicationDVO(){
		
	}
	public EventApplicationDVO(EventApplicationDVO budgetEventPart) {
		// TODO Auto-generated constructor stub
		this.m_strShareRate=budgetEventPart.getM_strShareRate() ;
		this.m_strQty =budgetEventPart.getM_strQty(); 
		this.m_strPartNumber=budgetEventPart.getM_strPartNumber(); 
		this.m_strPartColor=budgetEventPart.getM_strPartColor(); 
		this.m_strSupplierNumber= budgetEventPart.getM_strSupplierNumber() ;
		this.m_strShipTo=budgetEventPart.getM_strShipTo();
		this.m_strSection= budgetEventPart.getM_strSection(); 
		this.m_strTPlant=budgetEventPart.getM_strTPlant() ;
		this.m_strModel= budgetEventPart.getM_strModel();
		this.m_strType= budgetEventPart.getM_strType() ;
		this.m_strOption=budgetEventPart.getM_strOption(); 
		this.m_strCategory=budgetEventPart.getM_strCategory();
		this.m_strGrade=budgetEventPart.getM_strGrade();
		this.m_strMTAT=budgetEventPart.getM_strMTAT();
		this.m_strSupply=budgetEventPart.getM_strSupply();
		this.m_strHPlant=budgetEventPart.getM_strHPlant();
	}
	public String getM_strGrade() {
		return m_strGrade;
	}
	public void setM_strGrade(String grade) {
		m_strGrade = grade;
	}
	public String getM_strAtMt() {
		return m_strAtMt;
	}
	public void setM_strAtMt(String atMt) {
		m_strAtMt = atMt;
	}
	public Integer getM_intQtyForSelected() {
		return m_intQtyForSelected;
	}
	public void setM_intQtyForSelected(Integer qtyForSelected) {
		m_intQtyForSelected = qtyForSelected;
	}
	public BigDecimal getM_bRateForSelected() {
		return m_bRateForSelected;
	}
	public void setM_bRateForSelected(BigDecimal rateForSelected) {
		m_bRateForSelected = rateForSelected;
	}
	
	public String getM_strIsSupplyForSelected() {
		return m_strIsSupplyForSelected;
	}
	public void setM_strIsSupplyForSelected(String isSupplyForSelected) {
		m_strIsSupplyForSelected = isSupplyForSelected;
	}
	
	public String getM_strIsSupply() {
		return m_strIsSupply!=null?m_strIsSupply.toUpperCase():m_strIsSupply;
	}
	public void setM_strIsSupply(String isSupply) {
		m_strIsSupply = isSupply;
	}
	public String getM_strSpecificSearch() {
		return m_strSpecificSearch;
	}
	public void setM_strSpecificSearch(String specificSearch) {
		m_strSpecificSearch = specificSearch;
	}
	/**
	 * @return the m_strEventName
	 */
	public String getM_strEventName() {
		return m_strEventName;
	}
	/**
	 * @param eventName the m_strEventName to set
	 */
	public void setM_strEventName(String eventName) {
		m_strEventName = null!= eventName ? eventName.toUpperCase() : eventName;
	}
	/**
	 * @return the m_intRevNo
	 */
	public int getM_intRevNo() {
		return m_intRevNo;
	}
	/**
	 * @param revNo the m_intRevNo to set
	 */
	public void setM_intRevNo(int revNo) {
		m_intRevNo = revNo;
	}
	/**
	 * @return the m_strPartNumber
	 */
	public String getM_strPartNumber() {
		return m_strPartNumber;
	}
	/**
	 * @param partNumber the m_strPartNumber to set
	 */
	public void setM_strPartNumber(String partNumber) {
		m_strPartNumber = null!= partNumber ? partNumber.toUpperCase() : partNumber;
	}
	/**
	 * @return the m_strPartName
	 */
	public String getM_strPartName() {
		return m_strPartName;
	}
	/**
	 * @param partName the m_strPartName to set
	 */
	public void setM_strPartName(String partName) {
		m_strPartName = null!= partName ? partName.toUpperCase() : partName;
	}
	/**
	 * @return the m_strPartColor
	 */
	public String getM_strPartColor() {
		return m_strPartColor;
	}
	/**
	 * @param partColor the m_strPartColor to set
	 */
	public void setM_strPartColor(String partColor) {
		m_strPartColor = null!= partColor ? partColor.toUpperCase() : partColor;
	}
	/**
	 * @return the m_strSupplierNumber
	 */
	public String getM_strSupplierNumber() {
		return m_strSupplierNumber;
	}
	/**
	 * @param supplierNumber the m_strSupplierNumber to set
	 */
	public void setM_strSupplierNumber(String supplierNumber) {
		m_strSupplierNumber = null!= supplierNumber ? supplierNumber.toUpperCase() : supplierNumber;
	}
	/**
	 * @return the m_strSupplierName
	 */
	public String getM_strSupplierName() {
		return m_strSupplierName;
	}
	/**
	 * @param supplierName the m_strSupplierName to set
	 */
	public void setM_strSupplierName(String supplierName) {
		m_strSupplierName = null!= supplierName ? supplierName.toUpperCase() : supplierName;
	}
	/**
	 * @return the m_strShipTo
	 */
	public String getM_strShipTo() {
		return m_strShipTo;
	}
	/**
	 * @param shipTo the m_strShipTo to set
	 */
	public void setM_strShipTo(String shipTo) {
		m_strShipTo = null!= shipTo ? shipTo.toUpperCase() : shipTo;
	}
	/**
	 * @return the m_strSection
	 */
	public String getM_strSection() {
		return m_strSection;
	}
	/**
	 * @param section the m_strSection to set
	 */
	public void setM_strSection(String section) {
		m_strSection = null!= section ? section.toUpperCase() : section;
	}
	/**
	 * @return the m_strHPlant
	 */
	public String getM_strHPlant() {
		return m_strHPlant;
	}
	/**
	 * @param plant the m_strHPlant to set
	 */
	public void setM_strHPlant(String plant) {
		m_strHPlant = null!= plant ? plant.toUpperCase() : plant;
	}
	/**
	 * @return the m_strTPlant
	 */
	public String getM_strTPlant() {
		return m_strTPlant;
	}
	/**
	 * @param plant the m_strTPlant to set
	 */
	public void setM_strTPlant(String plant) {
		m_strTPlant = null!= plant ? plant.toUpperCase() : plant;
	}
	/**
	 * @return the m_strModel
	 */
	public String getM_strModel() {
		return m_strModel;
	}
	/**
	 * @param model the m_strModel to set
	 */
	public void setM_strModel(String model) {
		m_strModel = null!= model ? model.toUpperCase() : model;
	}
	/**
	 * @return the m_strType
	 */
	public String getM_strType() {
		return m_strType;
	}
	/**
	 * @param type the m_strType to set
	 */
	public void setM_strType(String type) {
		m_strType = null!= type ? type.toUpperCase() : type;
	}
	/**
	 * @return the m_strOption
	 */
	public String getM_strOption() {
		return m_strOption;
	}
	/**
	 * @param option the m_strOption to set
	 */
	public void setM_strOption(String option) {
		m_strOption = null!= option ? option.toUpperCase() : option;
	}
	/**
	 * @return the m_strCategory
	 */
	public String getM_strCategory() {
		return m_strCategory;
	}
	/**
	 * @param category the m_strCategory to set
	 */
	public void setM_strCategory(String category) {
		m_strCategory = null!= category ? category.toUpperCase() : category;
	}
	/**
	 * @return the m_intQty
	 *//**
	 * @param qty the m_intQty to set
	 *//**
	 * @return the m_dblShareRate
	 *//**
	 * @param shareRate the m_dblShareRate to set
	 */
	/**
	 * @return the m_strDesignSec
	 */
	public String getM_strDesignSec() {
		return m_strDesignSec;
	}
	/**
	 * @param designSec the m_strDesignSec to set
	 */
	public void setM_strDesignSec(String designSec) {
		m_strDesignSec = null!= designSec ? designSec.toUpperCase() : designSec;
	}
	/**
	 * @return the m_arlFeatureList
	 */
	public List<KeyValuePairDVO> getM_arlFeatureList() {
		return m_arlFeatureList;
	}
	/**
	 * @param featureList the m_arlFeatureList to set
	 */
	public void setM_arlFeatureList(List<KeyValuePairDVO> featureList) {
		m_arlFeatureList = featureList;
	}
	/**
	 * @return the m_strKey
	 */
	public String getM_strKey() {
		this.m_strKey = this.m_strEventName + "<-#SEPERATOR#->" + this.m_intRevNo + "<-#SEPERATOR#->" + this.m_strPartNumber
		+ "<-#SEPERATOR#->" + this.m_strPartColor + "<-#SEPERATOR#->" + this.m_strSupplierNumber + "<-#SEPERATOR#->" + this.m_strShipTo
		+ "<-#SEPERATOR#->" + this.m_strSection + "<-#SEPERATOR#->" + this.m_strHPlant + "<-#SEPERATOR#->" + this.m_strTPlant 
		+ "<-#SEPERATOR#->" + this.m_strModel + "<-#SEPERATOR#->" + this.m_strType + "<-#SEPERATOR#->" + this.m_strOption 
		+ "<-#SEPERATOR#->" + this.m_strCategory;
		return m_strKey;
	}
	/**
	 * @param key the m_strKey to set
	 */
	public void setM_strKey(String key) {
		m_strKey = key;
	}
	public BigDecimal getM_decEventRevNo() {
		return m_decEventRevNo;
	}
	public void setM_decEventRevNo(BigDecimal eventRevNo) {
		m_decEventRevNo = eventRevNo;
	}
	public String getM_strMTCType() {
		return m_strMTCType;
	}
	public void setM_strMTCType(String type) {
		m_strMTCType = null!= type ? type.toUpperCase() : type;
	}
	public String getM_strMTCOption() {
		return m_strMTCOption;
	}
	public void setM_strMTCOption(String option) {
		m_strMTCOption = null!= option ? option.toUpperCase() : option;
	}
	public String getM_strRPTCurrency() {
		return m_strRPTCurrency;
	}
	public void setM_strRPTCurrency(String currency) {
		m_strRPTCurrency = null!= currency ? currency.toUpperCase() : currency;
	}
	public String getM_strDesignSection() {
		return m_strDesignSection;
	}
	public void setM_strDesignSection(String designSection) {
		m_strDesignSection = null!= designSection ? designSection.toUpperCase() : designSection;
	}
	public String getM_strProcSection() {
		return m_strProcSection;
	}
	public void setM_strProcSection(String procSection) {
		m_strProcSection = null!= procSection ? procSection.toUpperCase() : procSection;
	}
	public String getM_strFeatureCode() {
		return m_strFeatureCode;
	}
	public void setM_strFeatureCode(String featureCode) {
		m_strFeatureCode = null!= featureCode ? featureCode.toUpperCase() : featureCode;
	}
	public String getM_strTargetPlant() {
		return m_strTargetPlant;
	}
	public void setM_strTargetPlant(String targetPlant) {
		m_strTargetPlant = null!= targetPlant ? targetPlant.toUpperCase() : targetPlant;
	}

	public String getM_strEventNameDesc() {
		return m_strEventNameDesc;
	}
	public void setM_strEventNameDesc(String eventNameDesc) {
		m_strEventNameDesc = null!= eventNameDesc ? eventNameDesc.toUpperCase() : eventNameDesc;
	}
	
	public String getCompositeKey() {

	    return m_strEventName + "." +
	           m_decEventRevNo + "." +
	           m_strPartNumber + "." +
	           m_strPartColor + "." +        
	           m_strSupplierNumber + "." +
	           m_strShipToCode + "." +
	           m_strDesignSection + "." +
	           m_strHPlant + "." +
	           m_strTPlant + "." +
	           m_strModel + "." +
	           m_strMTCType + "." +
	           m_strMTCOption + "." +
	           m_strCategory;
	}
	/**
	 * @return the m_strShipToCode
	 */
	public String getM_strShipToCode() {
		return m_strShipToCode;
	}
	/**
	 * @param shipToCode the m_strShipToCode to set
	 */
	public void setM_strShipToCode(String shipToCode) {
		m_strShipToCode = null!= shipToCode ? shipToCode.toUpperCase() : shipToCode;
	}
	/**
	 * @return the m_strAvailableProductEquipCode
	 */
	public String getM_strAvailableProductEquipCode() {
		return m_strAvailableProductEquipCode;
	}
	/**
	 * @param availableProductEquipCode the m_strAvailableProductEquipCode to set
	 */
	public void setM_strAvailableProductEquipCode(String availableProductEquipCode) {
		m_strAvailableProductEquipCode = null!= availableProductEquipCode ? availableProductEquipCode.toUpperCase() : availableProductEquipCode;
	}
	/**
	 * @return the m_strAssignedProductEquipCode
	 */
	public String getM_strAssignedProductEquipCode() {
		return m_strAssignedProductEquipCode;
	}
	/**
	 * @param assignedProductEquipCode the m_strAssignedProductEquipCode to set
	 */
	public void setM_strAssignedProductEquipCode(String assignedProductEquipCode) {
		m_strAssignedProductEquipCode = null!= assignedProductEquipCode ? assignedProductEquipCode.toUpperCase() : assignedProductEquipCode;
	}
	/**
	 * @return the m_strOldModel
	 */
	public String getM_strOldModel() {
		return m_strOldModel;
	}
	/**
	 * @param oldModel the m_strOldModel to set
	 */
	public void setM_strOldModel(String oldModel) {
		m_strOldModel = oldModel;
	}
	/**
	 * @return the m_strOldCategory
	 */
	public String getM_strOldCategory() {
		return m_strOldCategory;
	}
	/**
	 * @param oldCategory the m_strOldCategory to set
	 */
	public void setM_strOldCategory(String oldCategory) {
		m_strOldCategory = oldCategory;
	}
	/**
	 * @return the m_strOldShipToCode
	 */
	public String getM_strOldShipToCode() {
		return m_strOldShipToCode;
	}
	/**
	 * @param oldShipToCode the m_strOldShipToCode to set
	 */
	public void setM_strOldShipToCode(String oldShipToCode) {
		m_strOldShipToCode = oldShipToCode;
	}
	/**
	 * @return the m_strOldMTCType
	 */
	public String getM_strOldMTCType() {
		return m_strOldMTCType;
	}
	/**
	 * @param oldMTCType the m_strOldMTCType to set
	 */
	public void setM_strOldMTCType(String oldMTCType) {
		m_strOldMTCType = oldMTCType;
	}
	/**
	 * @return the m_strOldMTCOption
	 */
	public String getM_strOldMTCOption() {
		return m_strOldMTCOption;
	}
	/**
	 * @param oldMTCOption the m_strOldMTCOption to set
	 */
	public void setM_strOldMTCOption(String oldMTCOption) {
		m_strOldMTCOption = oldMTCOption;
	}
	/**
	 * @return the m_strOldDesignSection
	 */
	public String getM_strOldDesignSection() {
		return m_strOldDesignSection;
	}
	/**
	 * @param oldDesignSection the m_strOldDesignSection to set
	 */
	public void setM_strOldDesignSection(String oldDesignSection) {
		m_strOldDesignSection = oldDesignSection;
	}
	/**
	 * @return the m_strOldTargetPlant
	 */
	public String getM_strOldTargetPlant() {
		return m_strOldTargetPlant;
	}
	/**
	 * @param oldTargetPlant the m_strOldTargetPlant to set
	 */
	public void setM_strOldTargetPlant(String oldTargetPlant) {
		m_strOldTargetPlant = oldTargetPlant;
	}
	/**
	 * @return the m_strDesignSectionFromCopy
	 */
	public String getM_strDesignSectionFromCopy() {
		return m_strDesignSectionFromCopy;
	}
	/**
	 * @param designSectionFromCopy the m_strDesignSectionFromCopy to set
	 */
	public void setM_strDesignSectionFromCopy(String designSectionFromCopy) {
		m_strDesignSectionFromCopy = designSectionFromCopy;
	}
	/**
	 * @return the m_strDesignSectionToCopy
	 */
	public String getM_strDesignSectionToCopy() {
		return m_strDesignSectionToCopy;
	}
	/**
	 * @param designSectionToCopy the m_strDesignSectionToCopy to set
	 */
	public void setM_strDesignSectionToCopy(String designSectionToCopy) {
		m_strDesignSectionToCopy = designSectionToCopy;
	}
	/**
	 * @return the m_strTargetPlantFromCopy
	 */
	public String getM_strTargetPlantFromCopy() {
		return m_strTargetPlantFromCopy;
	}
	/**
	 * @param targetPlantFromCopy the m_strTargetPlantFromCopy to set
	 */
	public void setM_strTargetPlantFromCopy(String targetPlantFromCopy) {
		m_strTargetPlantFromCopy = targetPlantFromCopy;
	}
	/**
	 * @return the m_strTargetPlantToCopy
	 */
	public String getM_strTargetPlantToCopy() {
		return m_strTargetPlantToCopy;
	}
	/**
	 * @param targetPlantToCopy the m_strTargetPlantToCopy to set
	 */
	public void setM_strTargetPlantToCopy(String targetPlantToCopy) {
		m_strTargetPlantToCopy = targetPlantToCopy;
	}
	/**
	 * @return the m_strModelFromCopy
	 */
	public String getM_strModelFromCopy() {
		return m_strModelFromCopy;
	}
	/**
	 * @param modelFromCopy the m_strModelFromCopy to set
	 */
	public void setM_strModelFromCopy(String modelFromCopy) {
		m_strModelFromCopy = modelFromCopy;
	}
	/**
	 * @return the m_strModelToCopy
	 */
	public String getM_strModelToCopy() {
		return m_strModelToCopy;
	}
	/**
	 * @param modelToCopy the m_strModelToCopy to set
	 */
	public void setM_strModelToCopy(String modelToCopy) {
		m_strModelToCopy = modelToCopy;
	}
	/**
	 * @return the m_strMTCTypeFromCopy
	 */
	public String getM_strMTCTypeFromCopy() {
		return m_strMTCTypeFromCopy;
	}
	/**
	 * @param typeFromCopy the m_strMTCTypeFromCopy to set
	 */
	public void setM_strMTCTypeFromCopy(String typeFromCopy) {
		m_strMTCTypeFromCopy = typeFromCopy;
	}
	/**
	 * @return the m_strMTCTypeToCopy
	 */
	public String getM_strMTCTypeToCopy() {
		return m_strMTCTypeToCopy;
	}
	/**
	 * @param typeToCopy the m_strMTCTypeToCopy to set
	 */
	public void setM_strMTCTypeToCopy(String typeToCopy) {
		m_strMTCTypeToCopy = typeToCopy;
	}
	/**
	 * @return the m_strMTCOptionFromCopy
	 */
	public String getM_strMTCOptionFromCopy() {
		return m_strMTCOptionFromCopy;
	}
	/**
	 * @param optionFromCopy the m_strMTCOptionFromCopy to set
	 */
	public void setM_strMTCOptionFromCopy(String optionFromCopy) {
		m_strMTCOptionFromCopy = optionFromCopy;
	}
	/**
	 * @return the m_strMTCOptionToCopy
	 */
	public String getM_strMTCOptionToCopy() {
		return m_strMTCOptionToCopy;
	}
	/**
	 * @param optionToCopy the m_strMTCOptionToCopy to set
	 */
	public void setM_strMTCOptionToCopy(String optionToCopy) {
		m_strMTCOptionToCopy = optionToCopy;
	}
	/**
	 * @return the m_strCategoryFromCopy
	 */
	public String getM_strCategoryFromCopy() {
		return m_strCategoryFromCopy;
	}
	/**
	 * @param categoryFromCopy the m_strCategoryFromCopy to set
	 */
	public void setM_strCategoryFromCopy(String categoryFromCopy) {
		m_strCategoryFromCopy = categoryFromCopy;
	}
	/**
	 * @return the m_strCategoryToCopy
	 */
	public String getM_strCategoryToCopy() {
		return m_strCategoryToCopy;
	}
	/**
	 * @param categoryToCopy the m_strCategoryToCopy to set
	 */
	public void setM_strCategoryToCopy(String categoryToCopy) {
		m_strCategoryToCopy = categoryToCopy;
	}
	/**
	 * @return the m_strShipToCodeFromCopy
	 */
	public String getM_strShipToCodeFromCopy() {
		return m_strShipToCodeFromCopy;
	}
	/**
	 * @param shipToCodeFromCopy the m_strShipToCodeFromCopy to set
	 */
	public void setM_strShipToCodeFromCopy(String shipToCodeFromCopy) {
		m_strShipToCodeFromCopy = shipToCodeFromCopy;
	}
	/**
	 * @return the m_strShipToCodeToCopy
	 */
	public String getM_strShipToCodeToCopy() {
		return m_strShipToCodeToCopy;
	}
	/**
	 * @param shipToCodeToCopy the m_strShipToCodeToCopy to set
	 */
	public void setM_strShipToCodeToCopy(String shipToCodeToCopy) {
		m_strShipToCodeToCopy = shipToCodeToCopy;
	}
	
	public Integer getM_intRate() {
		return m_intRate;
	}
	 
	public void setM_intRate(Integer rate) {
		m_intRate = rate;
		
	}
	
	/**
	 * @return the m_intQuantity
	 */
	public Integer getM_intQuantity() {
		return m_intQuantity;
	}
	/**
	 * @param quantity the m_intQuantity to set
	 */
	public void setM_intQuantity(Integer quantity) {
		m_intQuantity = quantity;
	}
	/**
	 * @return the m_intQuantityFromCopy
	 */
	public Integer getM_intQuantityFromCopy() {
		return m_intQuantityFromCopy;
	}
	/**
	 * @param quantityFromCopy the m_intQuantityFromCopy to set
	 */
	public void setM_intQuantityFromCopy(Integer quantityFromCopy) {
		m_intQuantityFromCopy = quantityFromCopy;
	}
	/**
	 * @return the m_intQuantityToCopy
	 */
	public Integer getM_intQuantityToCopy() {
		return m_intQuantityToCopy;
	}
	/**
	 * @param quantityToCopy the m_intQuantityToCopy to set
	 */
	public void setM_intQuantityToCopy(Integer quantityToCopy) {
		m_intQuantityToCopy = quantityToCopy;
	}
	/**
	 * @return the m_intRateFromCopy
	 */
	public Integer getM_intRateFromCopy() {
		return m_intRateFromCopy;
	}
	/**
	 * @param rateFromCopy the m_intRateFromCopy to set
	 */
	public void setM_intRateFromCopy(Integer rateFromCopy) {
		m_intRateFromCopy = rateFromCopy;
	}

	/**
	 * @return the m_intRateToCopy
	 */
	public Integer getM_intRateToCopy() {
		return m_intRateToCopy;
	}
	/**
	 * @param rateToCopy the m_intRateToCopy to set
	 */
	public void setM_intRateToCopy(Integer rateToCopy) {
		m_intRateToCopy = rateToCopy;
	}

	/**
	 * @return the m_strSearchType
	 */
	public String getM_strSearchType() {
		return m_strSearchType;
	}
	/**
	 * @param searchType the m_strSearchType to set
	 */
	public void setM_strSearchType(String searchType) {
		m_strSearchType = searchType;
	}
	/**
	 * @return the m_strEditedRowKeyId
	 */
	public String getM_strEditedRowKeyId() {
		return m_strEditedRowKeyId;
	}
	/**
	 * @param editedRowKeyId the m_strEditedRowKeyId to set
	 */
	public void setM_strEditedRowKeyId(String editedRowKeyId) {
		m_strEditedRowKeyId = editedRowKeyId;
	}
	/**
	 * @return the m_strQty
	 */
	public String getM_strQty() {
		return m_strQty;
	}
	/**
	 * @param qty the m_strQty to set
	 */
	public void setM_strQty(String qty) {
		m_strQty = qty;
	}
	/**
	 * @return the m_strShareRate
	 */
	public String getM_strShareRate() {
		return m_strShareRate;
	}
	/**
	 * @param shareRate the m_strShareRate to set
	 */
	public void setM_strShareRate(String shareRate) {
		m_strShareRate = shareRate;
	}
	/**
	 * @return the m_bRate
	 */
	public BigDecimal getM_bRate() {
		return m_bRate;
	}
	/**
	 * @param rate the m_bRate to set
	 */
	public void setM_bRate(BigDecimal rate) {
		m_bRate = rate;
	}
	/**
	 * @return the m_bQuantity
	 */
	public BigDecimal getM_bQuantity() {
		return m_bQuantity;
	}
	/**
	 * @param quantity the m_bQuantity to set
	 */
	public void setM_bQuantity(BigDecimal quantity) {
		m_bQuantity = quantity;
	}
	/**
	 * @return the m_strRateToCopy
	 */
	public String getM_strRateToCopy() {
		return m_strRateToCopy;
	}
	/**
	 * @param rateToCopy the m_strRateToCopy to set
	 */
	public void setM_strRateToCopy(String rateToCopy) {
		m_strRateToCopy = rateToCopy;
	}
	public String getM_strMTAT() {
		return m_strMTAT;
	}
	public void setM_strMTAT(String m_strmtat) {
		m_strMTAT = m_strmtat;
	}
	public String getM_strSupply() {
		return m_strSupply;
	}
	public void setM_strSupply(String supply) {
		m_strSupply = supply;
	}
	
	public String getM_strIsSupplyFromCopy() {
		return m_strIsSupplyFromCopy;
	}
	public void setM_strIsSupplyFromCopy(String isSupplyFromCopy) {
		m_strIsSupplyFromCopy = isSupplyFromCopy;
	}
	public String getM_strIsSupplyToCopy() {
		return m_strIsSupplyToCopy;
	}
	public void setM_strIsSupplyToCopy(String isSupplyToCopy) {
		m_strIsSupplyToCopy = isSupplyToCopy;
	}
	public String getM_strVehicle() {
		return m_strVehicle;
	}
	public void setM_strVehicle(String vehicle) {
		m_strVehicle = vehicle;
	}
	public boolean isM_bSelected() {
		return m_bSelected;
	}
	public void setM_bSelected(boolean selected) {
		m_bSelected = selected;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((m_strAtMt == null) ? 0 : m_strAtMt.hashCode());
		result = prime * result
				+ ((m_strCategory == null) ? 0 : m_strCategory.hashCode());
		result = prime
				* result
				+ ((m_strDesignSection == null) ? 0 : m_strDesignSection
						.hashCode());
		result = prime * result
				+ ((m_strGrade == null) ? 0 : m_strGrade.hashCode());
		result = prime * result
				+ ((m_strHPlant == null) ? 0 : m_strHPlant.hashCode());
		result = prime * result
				+ ((m_strIsSupply == null) ? 0 : m_strIsSupply.hashCode());
		result = prime * result
				+ ((m_strMTCOption == null) ? 0 : m_strMTCOption.hashCode());
		result = prime * result
				+ ((m_strMTCType == null) ? 0 : m_strMTCType.hashCode());
		result = prime * result
				+ ((m_strModel == null) ? 0 : m_strModel.hashCode());
		result = prime * result
				+ ((m_strPartColor == null) ? 0 : m_strPartColor.hashCode());
		result = prime * result
				+ ((m_strPartName == null) ? 0 : m_strPartName.hashCode());
		result = prime * result
				+ ((m_strPartNumber == null) ? 0 : m_strPartNumber.hashCode());
		result = prime
				* result
				+ ((m_strSupplierName == null) ? 0 : m_strSupplierName
						.hashCode());
		result = prime
				* result
				+ ((m_strSupplierNumber == null) ? 0 : m_strSupplierNumber
						.hashCode());
		result = prime * result
				+ ((m_strTPlant == null) ? 0 : m_strTPlant.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventApplicationDVO other = (EventApplicationDVO) obj;
		if (m_strAtMt == null) {
			if (other.m_strAtMt != null)
				return false;
		} else if (!m_strAtMt.equals(other.m_strAtMt))
			return false;
		if (m_strCategory == null) {
			if (other.m_strCategory != null)
				return false;
		} else if (!m_strCategory.equals(other.m_strCategory))
			return false;
		if (m_strDesignSection == null) {
			if (other.m_strDesignSection != null)
				return false;
		} else if (!m_strDesignSection.equals(other.m_strDesignSection))
			return false;
		if (m_strGrade == null) {
			if (other.m_strGrade != null)
				return false;
		} else if (!m_strGrade.equals(other.m_strGrade))
			return false;
		if (m_strHPlant == null) {
			if (other.m_strHPlant != null)
				return false;
		} else if (!m_strHPlant.equals(other.m_strHPlant))
			return false;
		if (m_strIsSupply == null) {
			if (other.m_strIsSupply != null)
				return false;
		} else if (!m_strIsSupply.equals(other.m_strIsSupply))
			return false;
		if (m_strMTCOption == null) {
			if (other.m_strMTCOption != null)
				return false;
		} else if (!m_strMTCOption.equals(other.m_strMTCOption))
			return false;
		if (m_strMTCType == null) {
			if (other.m_strMTCType != null)
				return false;
		} else if (!m_strMTCType.equals(other.m_strMTCType))
			return false;
		if (m_strModel == null) {
			if (other.m_strModel != null)
				return false;
		} else if (!m_strModel.equals(other.m_strModel))
			return false;
		if (m_strPartColor == null) {
			if (other.m_strPartColor != null)
				return false;
		} else if (!m_strPartColor.equals(other.m_strPartColor))
			return false;
		if (m_strPartName == null) {
			if (other.m_strPartName != null)
				return false;
		} else if (!m_strPartName.equals(other.m_strPartName))
			return false;
		if (m_strPartNumber == null) {
			if (other.m_strPartNumber != null)
				return false;
		} else if (!m_strPartNumber.equals(other.m_strPartNumber))
			return false;
		if (m_strSupplierName == null) {
			if (other.m_strSupplierName != null)
				return false;
		} else if (!m_strSupplierName.equals(other.m_strSupplierName))
			return false;
		if (m_strSupplierNumber == null) {
			if (other.m_strSupplierNumber != null)
				return false;
		} else if (!m_strSupplierNumber.equals(other.m_strSupplierNumber))
			return false;
		if (m_strTPlant == null) {
			if (other.m_strTPlant != null)
				return false;
		} else if (!m_strTPlant.equals(other.m_strTPlant))
			return false;
		return true;
	}
	public String getM_strIsCopyBOM() {
		return m_strIsCopyBOM;
	}
	public void setM_strIsCopyBOM(String isCopyBOM) {
		m_strIsCopyBOM = isCopyBOM;
	}
	public String getM_strViewMTO() {
		return m_strViewMTO;
	}
	public void setM_strViewMTO(String viewMTO) {
		m_strViewMTO = viewMTO;
	}
	public String getM_strDataMaintProgNo() {
		return m_strDataMaintProgNo;
	}
	public void setM_strDataMaintProgNo(String dataMaintProgNo) {
		m_strDataMaintProgNo = dataMaintProgNo;
	}
	
	private String m_strOldSection;

	public String getM_strOldSection() {
	    return m_strOldSection;
	}

	public void setM_strOldSection(String m_strOldSection) {
	    this.m_strOldSection = m_strOldSection;
	}

}
