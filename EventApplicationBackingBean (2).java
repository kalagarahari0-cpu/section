/**
 * 
 */
package com.honda.cart2.web.eventprocessing;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager; import org.apache.logging.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.TabChangeEvent;

import com.honda.cart2.common.dvo.AbstractDVO;
import com.honda.cart2.common.dvo.CodeDVO;
import com.honda.cart2.common.dvo.DropdownDVO;
import com.honda.cart2.common.dvo.HashMapDVO;
import com.honda.cart2.common.dvo.ListDVO;
import com.honda.cart2.common.dvo.PartLookupDVO;
import com.honda.cart2.common.dvo.RequestDVO;
import com.honda.cart2.common.dvo.ResponseDVO;
import com.honda.cart2.common.dvo.SupplierLookupDVO;
import com.honda.cart2.common.dvo.UserInfoDVO;
import com.honda.cart2.common.exception.ApplicationException;
import com.honda.cart2.common.util.ApplicationConstantsIF;
import com.honda.cart2.common.util.Utility;
import com.honda.cart2.common.web.AbstractBackingBean;
import com.honda.cart2.common.web.LookupBean;
import com.honda.cart2.dataaccess.eventprocessing.EventApplicationDVO;
import com.honda.cart2.dataaccess.eventprocessing.EventPartDVO;
import com.honda.cart2.dataaccess.eventprocessing.EventProcessingSQLIF;
import com.honda.cart2.dataaccess.report.ReportCostCmpResultDVO;
import com.honda.cart2.service.eventprocessing.EventApplicationService;
import com.honda.cart2.web.report.StckPart_ReportBackingBean;

/**
 * @author VCC49890
 *
 */
@ManagedBean(name="eventApplicationBackingBean")
@SessionScoped
public class EventApplicationBackingBean extends AbstractBackingBean{
	private static Logger logger = LogManager
	.getLogger(EventApplicationBackingBean.class);
	
	@ManagedProperty(value = "#{eventApplicationService}")
	EventApplicationService eventApplicationService;
	
	@ManagedProperty(value="#{lookupBean}") 
	LookupBean lookupBean;
	
	private String m_strAction;
	private String m_strMode;
	
	//Filter Criteria
	private String m_strEventName;
	private String m_strDummyEventName;
	private String m_strEventNameDesc;
	private String m_strPartNumber;
	private String m_strPartColor;
	private String m_strTargetPlant;
	private String m_strSupplier;
	private String m_strRPTCurrency;
	private String m_strDesignSection;
	private String m_procSection;
	private String m_strFeatureCode;
	private String m_strModel;
	private String m_strMTCType;
	private ArrayList<String> m_strMTCType1;
	public ArrayList<String> getM_strMTCType1() {
		return m_strMTCType1;
	}

	public void setM_strMTCType1(ArrayList<String> type1) {
		m_strMTCType1 = type1;
	}

	private String m_strMTCOption;
	private String m_strGrade;
	private String m_strAtMt;
	private String m_strCategory;
	private String m_strOperator;

	private Integer m_intQty;
	private String m_strPartName;
	private String m_strSupplierName;
	private String m_strHPlant;
	private String m_strTPlant;
	private Integer m_intRate;
	private BigDecimal m_decEventRevisionNo;
	private String m_strEventType;
	private String m_strIsSupply;
	private String m_strSpecificSearch;
	private Integer m_intQtyForSelected;
	private BigDecimal m_bRateForSelected;
	private String m_strIsSupplyForSelected;
	private String m_strValidateActualAttachedToBudgetMessage;
	private ArrayList<EventApplicationDVO> m_selectedEventApplication;
	

	

	//Filter Drop downs
	private LinkedHashMap m_hmpEventName;
	private LinkedHashMap m_hmpAllEventName;
	private LinkedHashMap m_hmpPartColor;
	private LinkedHashMap m_hmpTargetPlant;
	private LinkedHashMap m_hmpRPTCurrency;
	private LinkedHashMap m_hmpDesignSection;
	private LinkedHashMap m_hmpProcSection;
	private LinkedHashMap m_hmpFeatureCode;
	private LinkedHashMap m_hmpModel;
	private LinkedHashMap m_hmpMTCType;
	private LinkedHashMap m_hmpMTCOption;
	private LinkedHashMap m_hmpCategory;

	//Search list
	private List<EventApplicationDVO> m_eventApplicationList;
	private Map<String,EventApplicationDVO> m_hmpSelectedEventApplicationDetails = new HashMap<String, EventApplicationDVO>();
	
	private EventApplicationDVO m_selectedApplication;
	
    private Boolean m_bolgbEntireEvt = false;
    private Boolean m_bolgbAllApp = false;
    private Boolean m_bolgbBaseCost = false;
    private Boolean m_bolgbFeature = false;
    private Boolean m_bolgbAllInv = false;
    private Boolean m_bolgbComHamInv = false;
    private Boolean m_bolgbExclHamInv = false;
    private Boolean m_bolgbExclSupInv = false;
    private Boolean m_bolgbDrawCost = false;
    private Boolean m_bolgbAllSupCost = false;
    private Boolean m_bolgbTgtCost = false;
    private Boolean m_bolgbDataCheckOk = false;
    private Boolean isvalid = true;
    
    private Boolean m_bolAddNewEnabled = true;
    private Boolean m_bolApplyEnabled = true;
    private Boolean m_bolDeleteEnabled = true;
    private Boolean m_bolCopyEnabled = false;
    private Boolean m_bolEditableGrid = false;
    private Boolean m_bolEventFreeze = false;
    private Boolean m_bolDetailsEnabled = true;
    private Boolean m_bolIsGridEmpty = true;
    private Boolean m_bolSummaryEnabled = true;
    private Boolean m_bolShowCopyBtn = false;
    private FacesMessage m_displayMessage;
	
	// Detail page attributes : Start
	private String m_strEventNameDlg;
	private String m_strEventNameDescDlg;
	private String m_strPartNumberDlg;
	private String m_strPartColorDlg;
	private String m_strTargetPlantDlg;
	private String m_strOldTargetPlantDlg;
	private String m_strSupplierDlg;
	private String m_strDesignSectionDlg;
	private String m_strOldDesignSectionDlg;
	private String m_strModelDlg;
	private String m_strOldModelDlg;
	private String m_strMTCTypeDlg;
	private ArrayList<String> m_strMTCTypeDlg1;
	public ArrayList<String> getM_strMTCTypeDlg1() {
		return m_strMTCTypeDlg1;
	}

	public void setM_strMTCTypeDlg1(ArrayList<String> typeDlg1) {
		m_strMTCTypeDlg1 = typeDlg1;
	}

	private String m_strOldMTCTypeDlg;
	private ArrayList<String> m_strOldMTCTypeDlg1;
	private String m_strMTCOptionDlg;
	private String m_strOldMTCOptionDlg;
	private String m_strCategoryDlg;
	private String m_strOldCategoryDlg;
	private String m_strOperatorDlg;
	private Integer m_intQtyDlg;
	private BigDecimal m_bQtyDlg;
	private String m_strPartNameDlg;
	private String m_strSupplierNameDlg;
	private BigDecimal m_bRateDlg;
	private BigDecimal m_decEventRevisionNoDlg;
	private Boolean m_bolChkAssociation = false;
	private String m_strEventTypeDlg;
	private List m_eventApplicationListDlg;
	private String m_strShipToCode;
	private String m_strOldShipToCode;
	private EventApplicationDVO m_selectedApplicationDlg;
	private Boolean m_bolShowFeatures = false;
	
    private Boolean m_bolEnableAddDlg = true;
    private Boolean m_bolEnableRemoveDlg = true;
    private Boolean m_bolEnablePartNumberDlg = true;
    private Boolean m_bolEnableSupplierNumberDlg = true;
    private Boolean m_bolEnablePartSearchDlg = true;
    private Boolean m_bolEnableSupplierSearchDlg = true;
    private Boolean m_bolEnablePartColorDlg = true;
    private Boolean m_bolEnableTargetPlantDlg = true;
    private Boolean m_bolEnableModelDlg = true;
    private Boolean m_bolEnableMTCTypeDlg = true;
    private Boolean m_bolEnableMTCOptionDlg = true;
    private Boolean m_bolEnableCategoryDlg = true;
    private Boolean m_bolEnablePartSectionDlg = true;
    private Boolean m_bolEnableApplyDlg = true;
    private Boolean m_bolEnableDeleteDlg = true;
    private Boolean m_bolEnableOkDlg = true;
    
    private CodeDVO m_selectedAssignedFeature;
    private CodeDVO m_selectedAvailableFeature;
	
	//Drop downs in dialog
	private LinkedHashMap m_hmpPartColorDlg;
	private LinkedHashMap m_hmpTargetPlantDlg;
	private LinkedHashMap m_hmpDesignSectionDlg;
	private LinkedHashMap m_hmpModelDlg;
	private LinkedHashMap m_hmpMTCTypeDlg;
	private LinkedHashMap m_hmpMTCOptionDlg;
	private LinkedHashMap m_hmpCategoryDlg;
	
	private List m_availableFeaturesList;
	private List m_assignedFeaturesList;
	
	private String validationMsg;
	private String focusField;
	
	//Check Flags
	private HashMap<String , Boolean> checkFlagMap;
	private String m_strParentPage ;
	
	// Dialog attributes : End
	
	// Copy screen attributes : Start
	private String m_strEventNameCopy;
	private BigDecimal m_decEventRevisionNoCopy;
	private String m_strEventNameDescCopy;
	private String m_strPartNumberCopy;
	private String m_strPartNameCopy;
	private String m_strPartColorCopy;
	private String m_strSupplierCopy;
	private String m_strSupplierNameCopy;
	private LinkedHashMap m_hmpPartColorCopy;

	private String m_strTargetPlantFromCopy;
	private String m_strDesignSectionFromCopy;
	private String m_strModelFromCopy;
	private String m_strShipToCodeFromCopy;
	private String m_strMTCTypeFromCopy;
	private ArrayList<String> m_strMTCTypeFromCopy1;
	public ArrayList<String> getM_strOldMTCTypeDlg1() {
		return m_strOldMTCTypeDlg1;
	}

	public void setM_strOldMTCTypeDlg1(ArrayList<String> oldMTCTypeDlg1) {
		m_strOldMTCTypeDlg1 = oldMTCTypeDlg1;
	}

	public ArrayList<String> getM_strMTCTypeFromCopy1() {
		return m_strMTCTypeFromCopy1;
	}

	public void setM_strMTCTypeFromCopy1(ArrayList<String> typeFromCopy1) {
		m_strMTCTypeFromCopy1 = typeFromCopy1;
	}

	private String m_strMTCOptionFromCopy;
	private String m_strCategoryFromCopy;
	private Integer m_intQtyFromCopy;
	private Integer m_intShareFromCopy;
	private String m_strIsSupplyFromCopy;
	

	private LinkedHashMap m_hmpTargetPlantFromCopy;
	private LinkedHashMap m_hmpDesignSectionFromCopy;
	private LinkedHashMap m_hmpModelFromCopy;
	private LinkedHashMap m_hmpModelFromCopyOne;
	private LinkedHashMap m_hmpShipToCodeFromCopy;
	private LinkedHashMap m_hmpMTCTypeFromCopy;
	private LinkedHashMap m_hmpMTCTypeFromCopyOne;
	private LinkedHashMap m_hmpMTCOptionFromCopy;
	private LinkedHashMap m_hmpCategoryFromCopy;
	private LinkedHashMap m_hmpCategoryFromCopyOne;
	
	private String m_strTargetPlantToCopy;
	private String m_strDesignSectionToCopy;
	private String m_strModelToCopy;
	private String m_strShipToCodeToCopy;
	private String m_strMTCTypeToCopy;
	private ArrayList<String> m_strMTCTypeToCopy1;
	public ArrayList<String> getM_strMTCTypeToCopy1() {
		return m_strMTCTypeToCopy1;
	}

	public void setM_strMTCTypeToCopy1(ArrayList<String> typeToCopy1) {
		m_strMTCTypeToCopy1 = typeToCopy1;
	}

	private String m_strMTCOptionToCopy;
	private String m_strCategoryToCopy;
	private Integer m_intQtyToCopy;
	private Integer m_intShareToCopy;
	private String m_strShareToCopy;
	private String m_strIsSupplyToCopy;

	private LinkedHashMap m_hmpTargetPlantToCopy;
	private LinkedHashMap m_hmpDesignSectionToCopy;
	private LinkedHashMap m_hmpModelToCopy;
	private LinkedHashMap m_hmpShipToCodeToCopy;
	private LinkedHashMap m_hmpMTCTypeToCopy;
	private LinkedHashMap m_hmpMTCOptionToCopy;
	private LinkedHashMap m_hmpCategoryToCopy;
	
	private EventApplicationDVO m_selectedApplicationCopy;
    private CodeDVO m_selectedAssignedFeatureCopy;
    private CodeDVO m_selectedAvailableFeatureCopy;
    
	private List m_eventApplicationListCopy;
	private List m_availableFeaturesListCopy;
	private List m_assignedFeaturesListCopy;
	
    private Boolean m_bolEnableAddCopy = true;
    private Boolean m_bolEnableRemoveCopy = true;
    
    private EventPartDVO m_selectedEventPart;
    private Integer tabActiveIndex;
    public boolean closeWindow = true;
    private Boolean m_bToggleFlag = false;
    private Boolean m_bOldValueFlag = true;
    
    private boolean fromCcdScrn;
    
    /* Data table Check box Handling logic START */
    //Changes in datatable of event Part Application Screen
    private ArrayList<EventApplicationDVO> m_CheckedEventApplication;
    private DataTable m_dtEventApplicationTable;
	private int m_nTotalSelectedRows;
	private Boolean m_bSelectAllRecord;
	private Boolean m_bSelectAllChkBoxMaintained;//This variable is used to maintain the actual check box
	/* Data table Check box Handling logic END */
    
    
    //New variable added for CR- Copy BOM MTO's will be differentiated on screen
    private String m_strViewMTO;
    
    //New variable
    private String m_strIsCopyBOM;
    
    //Variable used to render the Qty and Rate columns based on Qty or rate entered in Test fields
    private Boolean m_bolEditableGridOnQtyRateChange = false;
    
	// Copy screen attributes : End
	public EventApplicationBackingBean() {
		if(!FacesContext.getCurrentInstance().getViewRoot().getViewId().equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.XHTML_FILES_PATH+"maintainEventPart.xhtml") &&  
				!FacesContext.getCurrentInstance().getViewRoot().getViewId().equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.XHTML_FILES_PATH+"reportStackedPartList.xhtml")){
			clearManagedBeansFromSession("eventApplicationBackingBean");
			getFilterValues();
		}
		logger.debug("\nInside EventApplicationBackingBean");
	}
	
	private void getFilterValues() {
		HashMap<String, String> sessionFilterMap = getFilterMapFromSession(ApplicationConstantsIF.SESSIONKEY.SESSION_FILTER_PARAM_EVENT_PROCESSING);
		m_strDummyEventName = Utility.convertNullToBlank(sessionFilterMap.get(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.EVENT_REV_DISPLAY_NAME));
		m_strEventName = Utility.convertNullToBlank(sessionFilterMap.get(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.EVENT_NAME));
		m_decEventRevisionNo = (null == sessionFilterMap.get(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.EVENT_REV) ? new BigDecimal(0) 
				: new BigDecimal((String)sessionFilterMap.get(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.EVENT_REV)));
		
		m_strEventType = Utility.convertNullToBlank(sessionFilterMap.get(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.EVENT_TYPE));
		m_strEventNameDesc = Utility.convertNullToBlank(sessionFilterMap.get(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.EVENT_DESC));
		m_strPartNumber = Utility.convertNullToBlank(sessionFilterMap.get(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.PART_NUM));
		m_strSupplier = Utility.convertNullToBlank(sessionFilterMap.get(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.SUPPLIER_NUM));
		m_strRPTCurrency = Utility.convertNullToBlank(sessionFilterMap.get(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.RPT_CURRENCY));
		m_strDesignSection = Utility.convertNullToBlank(sessionFilterMap.get(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.DESIGN_SECTION));
		m_procSection = Utility.convertNullToBlank(sessionFilterMap.get(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.PROC_GROUP));
	}
	
	/**
	 * @param filterValueMap
	 * @param eventPartDVO
	 */
	private void setFilterValues(HashMap<String, String> filterValueMap,
			EventApplicationDVO eventApplicationDVO) {
		
		filterValueMap.put(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.EVENT_REV_DISPLAY_NAME, m_strDummyEventName);
		filterValueMap.put(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.EVENT_NAME, eventApplicationDVO.getM_strEventName());
		filterValueMap.put(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.EVENT_REV, String.valueOf(eventApplicationDVO.getM_decEventRevNo()));
		filterValueMap.put(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.EVENT_TYPE, m_strEventType);
		filterValueMap.put(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.EVENT_DESC, m_strEventNameDesc);
		filterValueMap.put(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.PART_NUM, eventApplicationDVO.getM_strPartNumber());
		filterValueMap.put(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.SUPPLIER_NUM, eventApplicationDVO.getM_strSupplierNumber());
		filterValueMap.put(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.RPT_CURRENCY, eventApplicationDVO.getM_strRPTCurrency());
		filterValueMap.put(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.DESIGN_SECTION, eventApplicationDVO.getM_strDesignSection());
		filterValueMap.put(ApplicationConstantsIF.MODULE_FILTER_CONSTANTS.PROC_GROUP, eventApplicationDVO.getM_strProcSection());
		//need to check whether to include is supply check here or not
	}
	
	public void onPageload(){
		String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		if(viewId.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.XHTML_FILES_PATH+"searcheventapplications.xhtml")
				&& null != m_strParentPage && m_strParentPage.equalsIgnoreCase("maintainEventPart")){
			m_strParentPage = null;
			clearManagedBeansFromSession("");
			eventApplicationSearchInit();
		}
		if(null != m_displayMessage)
				FacesContext.getCurrentInstance().addMessage("messages", m_displayMessage);
		m_displayMessage = null;
		if(viewId.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.XHTML_FILES_PATH+"searcheventapplications.xhtml"))
				getSecurityAccess("searchEventApplicationForm");
		else if(viewId.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.XHTML_FILES_PATH+"copyeventapplication.xhtml"))
			getSecurityAccess("eventApplicationCopyForm");
		logger.debug("\n Exiting onPageload() ");
	}
	
	public void onKeyUp(CellEditEvent event){
		m_bolApplyEnabled = true;
		RequestContext.getCurrentInstance().update("searchEventApplicationForm:applyBtn");
	}
	
	public void changeFocus(){
		RequestContext.getCurrentInstance().execute("document.getElementById('searchEventApplicationForm:selector').focus();");	
	}
	
	
	

	@PostConstruct
	public void eventApplicationSearchInit(){
		
		logger.debug("\n Entering eventApplicationSearchInit() ");
		
		populateDropdowns(false,true,true,true);
		m_bolAddNewEnabled = true;
		m_bolEditableGrid = true;
		m_bolEventFreeze = false;
		m_bolgbDataCheckOk=true;  //PRB0008005 - make fields editable in add new app screen		
		//required to set as the datatable is being rendered
		m_bSelectAllRecord=false;
		m_bSelectAllChkBoxMaintained=false;
		
		m_strRPTCurrency = "USD";

		m_bolEditableGridOnQtyRateChange=false;
		//CPT-1665 start - Read only for NM staffs
		UserInfoDVO userInfoDVO = getM_userInfoDVO();
		if (userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.NM_COST_STAFF.value) || 
				userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.MEX_NM_STAFF.value) ){
			m_bolEditableGrid = false;
			m_bolAddNewEnabled = false;
			m_bolApplyEnabled = false;
			m_bolDetailsEnabled = true;
			m_bolCopyEnabled = false;
			m_bolEventFreeze = true;
			m_bolEditableGridOnQtyRateChange = true;
		}//CPT-1665 end - Read only for NM staffs
		onSupplierNoChange();
		onPartNoChange();
		setFieldFocus("searchEventApplicationForm:formFocusID","searchEventApplicationForm:eventName");
		logger.debug("\n Exiting eventApplicationSearchInit() ");
	}
	
	public void populateDropdowns(Boolean dependentOnly,Boolean modelDD , Boolean mtcTypeDD, Boolean mtcOptionDD) {

		logger.debug("\n Entering populateDropdowns() ");
		
		RequestDVO requestDVO = new RequestDVO();
		ResponseDVO responseDVO = null;
		AbstractDVO abstractDVO = new AbstractDVO();
		List<DropdownDVO> inList = new ArrayList<DropdownDVO>();
		LinkedHashMap<Object , Object> allDetailMap = new LinkedHashMap<Object, Object>();
		DropdownDVO obj = null;
		
		UserInfoDVO userInfoDVO = getM_userInfoDVO();
		Object[] eventRoleParam = null;
		String eventNameDdQuery = "";
        
        if(userInfoDVO != null){
            boolean isMPCUserRole = userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.ADMINISTRATOR.value)
            || userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.MP_COST_STAFF.value)
            || userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.BILLBACK_ADMIN.value);

            boolean isPSPUserRole = userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.PSPADMINISTRATOR.value)
            || userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.HSC_BILLBACK.value)
	    || userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.HSC_MP_COST.value);
            
            boolean isMEXUserRole = userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.MEX_ADMIN.value)
            || userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.MEX_MP_COST.value)
            || userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.MEX_BILLBACK.value);
            
            if(isMPCUserRole){
                  eventNameDdQuery = EventProcessingSQLIF.EVENT_NAME_DD_ALL_EVENTS + EventProcessingSQLIF.EVENT_NAME_DD_WHERE_CLAUSE_MPC_ROLES + EventProcessingSQLIF.EVENT_NAME_DD_ORDER_BY_CLAUSE;
                  
                  eventRoleParam = new Object[4];
                  eventRoleParam[0]="%ACT";
                  eventRoleParam[1]="%AUT";
                  eventRoleParam[2] = "%E2%";
                  eventRoleParam[3] = "%E2P";
            }
            else if(isPSPUserRole){
                  eventNameDdQuery = EventProcessingSQLIF.EVENT_NAME_DD_ALL_EVENTS + EventProcessingSQLIF.EVENT_NAME_DD_WHERE_CLAUSE_PSP_ROLES + EventProcessingSQLIF.EVENT_NAME_DD_ORDER_BY_CLAUSE;
                  
                  eventRoleParam = new Object[3];
                  eventRoleParam[0] = "%PSP";
                  eventRoleParam[1] = "%E2%";
                  eventRoleParam[2] = "%E2%";
            }
            else if(isMEXUserRole){
                eventNameDdQuery = EventProcessingSQLIF.EVENT_NAME_DD_ALL_EVENTS + EventProcessingSQLIF.EVENT_NAME_DD_WHERE_CLAUSE_PSP_ROLES + EventProcessingSQLIF.EVENT_NAME_DD_ORDER_BY_CLAUSE;
                
                eventRoleParam = new Object[3];
                eventRoleParam[0] = "%MEX";
                eventRoleParam[1] = "%E2%";
                eventRoleParam[2] = "%E2%";
          }
            else {
                  eventNameDdQuery = EventProcessingSQLIF.EVENT_NAME_DD_ALL_EVENTS + EventProcessingSQLIF.EVENT_NAME_DD_WHERE_CLAUSE_OTHER_ROLES +EventProcessingSQLIF.EVENT_NAME_DD_ORDER_BY_CLAUSE;
                  
                  eventRoleParam = new Object[5];
                  eventRoleParam[0]="%ACT";
                  eventRoleParam[1]="%AUT";
                  eventRoleParam[2]="%PSP";
                  eventRoleParam[3] = "%E2%";
                  eventRoleParam[4] = "%MEX%";
            }
        }
		
		if(!dependentOnly){
				
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.EVENT_NAME_DROPDOWN,
						  eventNameDdQuery,
						  ApplicationConstantsIF.APP_CONSTANTS.EVENT_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.EVENT_COLUMN, eventRoleParam, true);
				inList.add(obj);
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.PART_COLOR_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_PART_COLOR_EVENT_APPLICATION,
						  ApplicationConstantsIF.APP_CONSTANTS.CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.DISPLAY_CODE_COLUMN,null);
				inList.add(obj);
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.TARGET_PLANT_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_TARGET_PLANT_EVENT_APPLICATION,
						  ApplicationConstantsIF.APP_CONSTANTS.CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.DISPLAY_CODE_COLUMN,null);
				inList.add(obj);
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.RPT_CURRENCY_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_RPT_CURRENCY_EVENT_APPLICATION,
						  ApplicationConstantsIF.APP_CONSTANTS.CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.DISPLAY_CODE_COLUMN,null);
				inList.add(obj);
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.DESIGN_SEC_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_DESIGN_SECTION_EVENT_APPLICATION,
						  ApplicationConstantsIF.APP_CONSTANTS.CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.DISPLAY_CODE_COLUMN,null);
				inList.add(obj);
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.PROC_SECTION_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_PROC_SCTION_EVENT_APPLICATION,
						  ApplicationConstantsIF.APP_CONSTANTS.CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.PROC_SECT_CODE_COLUMN,null);
				inList.add(obj);
				
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.FEATURE_CODE_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_FEATURE_CODE_EVENT_APPLICATION,
						  ApplicationConstantsIF.APP_CONSTANTS.PRODUCT_EQUIP_CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.PRODUCT_EQUIP_TEXT_COLUMN,null);
				inList.add(obj);
						
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.CATEGORY_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_CATEGORY_EVENT_APPLICATION,
						  ApplicationConstantsIF.APP_CONSTANTS.MODEL_CAT_CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.MODEL_CAT_CODE_COLUMN,null);
				inList.add(obj);
		}
		
				
		if(modelDD){
			
			String whereCondStr = "";
			
			if(m_strEventName != null && !m_strEventName.equalsIgnoreCase(""))
					whereCondStr = whereCondStr + " TRIM(PH_COST_EVENT_NAME) = '"+m_strEventName+"' ";
			
			if(m_decEventRevisionNo != null){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " EVENT_REV_NO = "+m_decEventRevisionNo+" ";
				else
					whereCondStr = whereCondStr + " AND EVENT_REV_NO = "+m_decEventRevisionNo+" ";
			}
					
			if(m_strTargetPlant != null && !m_strTargetPlant.equalsIgnoreCase("")){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlant+"' ";
				else
					whereCondStr = whereCondStr + " AND TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlant+"' ";
			}
			
			obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.MODEL_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_MODEL_EVENT_APPLICATION.replace("--WHERE_CONDITION--", (whereCondStr.equalsIgnoreCase("")) ? whereCondStr : " WHERE "+whereCondStr ),
						  ApplicationConstantsIF.APP_CONSTANTS.TGT_MODEL_DEV_CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.TGT_MODEL_DEV_CODE_COLUMN,null );
				inList.add(obj);
		}
		
		if(mtcTypeDD){
			
			String whereCondStr = "";
			
			if(m_strEventName != null && !m_strEventName.equalsIgnoreCase(""))
					whereCondStr = whereCondStr + " TRIM(PH_COST_EVENT_NAME) = '"+m_strEventName+"' ";
			
			if(m_decEventRevisionNo != null){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " EVENT_REV_NO = "+m_decEventRevisionNo+" ";
				else
					whereCondStr = whereCondStr + " AND EVENT_REV_NO = "+m_decEventRevisionNo+" ";
			}
					
			if(m_strTargetPlant != null && !m_strTargetPlant.equalsIgnoreCase("")){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlant+"' ";
				else
					whereCondStr = whereCondStr + " AND TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlant+"' ";
			}
			
			if(m_strModel != null && !m_strModel.equalsIgnoreCase("")){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " TRIM(TGT_MODEL_DEV_CODE) = '"+m_strModel+"' ";
				else
					whereCondStr = whereCondStr + " AND  TRIM(TGT_MODEL_DEV_CODE) = '"+m_strModel+"' ";
			}
			
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_MTC_TYPE_EVENT_APPLICATION.replace("--WHERE_CONDITION--", (whereCondStr.equalsIgnoreCase("")) ? whereCondStr : " WHERE "+whereCondStr ),
						  ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_COLUMN, null );
				inList.add(obj);
		}
		
		
		
		abstractDVO.setObject(inList);
		requestDVO.setM_abstractDVO(abstractDVO);
		
		try{
			responseDVO = eventApplicationService.populateDropdown(requestDVO);
			LinkedHashMap<String,LinkedHashMap<Object,Object>> outMap = (LinkedHashMap<String,LinkedHashMap<Object,Object>>)responseDVO.getM_abstractDVO().getObject();
			
			if(!dependentOnly){
				
				m_hmpEventName = (LinkedHashMap)(outMap.get(ApplicationConstantsIF.APP_CONSTANTS.EVENT_NAME_DROPDOWN)).get(ApplicationConstantsIF.APP_CONSTANTS.DROPDOWN_MAP);
				m_hmpAllEventName = (LinkedHashMap)(outMap.get(ApplicationConstantsIF.APP_CONSTANTS.EVENT_NAME_DROPDOWN)).get(ApplicationConstantsIF.APP_CONSTANTS.ADDITIONAL_DETAIL_MAP);
				m_hmpPartColor = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.PART_COLOR_DROPDOWN);
				m_hmpTargetPlant = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.TARGET_PLANT_DROPDOWN);
				m_hmpRPTCurrency = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.RPT_CURRENCY_DROPDOWN);
				m_hmpDesignSection = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.DESIGN_SEC_DROPDOWN);
				m_hmpProcSection = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.PROC_SECTION_DROPDOWN);
				m_hmpFeatureCode = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.FEATURE_CODE_DROPDOWN);
				m_hmpCategory = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.CATEGORY_DROPDOWN);
			}
			
			if(modelDD)
				m_hmpModel = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.MODEL_DROPDOWN);					
			
			if(mtcTypeDD)
				m_hmpMTCType = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_DROPDOWN);		
				
			if(mtcOptionDD)
				m_hmpMTCOption = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.MTC_OPTION_DROPDOWN);
			
		} catch(ApplicationException appExce){
			handleApplicationException(appExce);
		}

		logger.debug("\n Exiting populateDropdowns() ");
		
	}

	public void populateDialogDropdowns(Boolean dependentOnly,Boolean modelDD , Boolean mtcTypeDD, Boolean mtcOptionDD, Boolean populateFeaturesInfo) {

		logger.debug("\n Entering populateDialogDropdowns() ");
		
		RequestDVO requestDVO = new RequestDVO();
		ResponseDVO responseDVO = null;
		AbstractDVO abstractDVO = new AbstractDVO();
		List<DropdownDVO> inList = new ArrayList<DropdownDVO>();
		LinkedHashMap<Object , Object> allDetailMap = new LinkedHashMap<Object, Object>();
		DropdownDVO obj = null;
		
		if(!dependentOnly){
	
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.PART_COLOR_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_PART_COLOR_EVENT_APPLICATION,
						  ApplicationConstantsIF.APP_CONSTANTS.CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.DISPLAY_CODE_COLUMN,null);
				inList.add(obj);
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.TARGET_PLANT_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_TARGET_PLANT_EVENT_APPLICATION_DLG,
						  ApplicationConstantsIF.APP_CONSTANTS.CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.CODE_DESC_TEXT_COLUMN,null);
				inList.add(obj);
		
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.DESIGN_SEC_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_DESIGN_SECTION_EVENT_APPLICATION_DLG,
						  ApplicationConstantsIF.APP_CONSTANTS.CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.DISPLAY_CODE_COLUMN,null);
				inList.add(obj);

						
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.CATEGORY_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_CATEGORY_EVENT_APPLICATION,
						  ApplicationConstantsIF.APP_CONSTANTS.MODEL_CAT_CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.MODEL_CAT_CODE_COLUMN,null);
				inList.add(obj);
		}
		
				
		if(modelDD){
			
			String whereCondStr = "";
			
			if(m_strEventNameDlg != null && !m_strEventNameDlg.equalsIgnoreCase(""))
					whereCondStr = whereCondStr + " TRIM(PH_COST_EVENT_NAME) = '"+m_strEventNameDlg+"' ";
			
			if(m_decEventRevisionNoDlg != null){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " EVENT_REV_NO = "+m_decEventRevisionNoDlg+" ";
				else
					whereCondStr = whereCondStr + " AND EVENT_REV_NO = "+m_decEventRevisionNoDlg+" ";
			}
					
			if(m_strTargetPlantDlg != null && !m_strTargetPlantDlg.equalsIgnoreCase("")){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlantDlg+"' ";
				else
					whereCondStr = whereCondStr + " AND TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlantDlg+"' ";
			}
			
			obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.MODEL_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_MODEL_EVENT_APPLICATION.replace("--WHERE_CONDITION--", (whereCondStr.equalsIgnoreCase("")) ? whereCondStr : " WHERE "+whereCondStr ),
						  ApplicationConstantsIF.APP_CONSTANTS.TGT_MODEL_DEV_CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.TGT_MODEL_DEV_CODE_COLUMN,null );
				inList.add(obj);
		}
		
		if(mtcTypeDD){
			
			String whereCondStr = "";
			
			if(m_strEventNameDlg != null && !m_strEventNameDlg.equalsIgnoreCase(""))
					whereCondStr = whereCondStr + " TRIM(PH_COST_EVENT_NAME) = '"+m_strEventNameDlg+"' ";
			
			if(m_decEventRevisionNoDlg != null){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " EVENT_REV_NO = "+m_decEventRevisionNoDlg+" ";
				else
					whereCondStr = whereCondStr + " AND EVENT_REV_NO = "+m_decEventRevisionNoDlg+" ";
			}
					
			if(m_strTargetPlantDlg != null && !m_strTargetPlantDlg.equalsIgnoreCase("")){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlantDlg+"' ";
				else
					whereCondStr = whereCondStr + " AND TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlantDlg+"' ";
			}
			
			if(m_strModelDlg != null && !m_strModelDlg.equalsIgnoreCase("")){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " TRIM(TGT_MODEL_DEV_CODE) = '"+m_strModelDlg+"' ";
				else
					whereCondStr = whereCondStr + " AND  TRIM(TGT_MODEL_DEV_CODE) = '"+m_strModelDlg+"' ";
			}
			
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_MTC_TYPE_EVENT_APPLICATION.replace("--WHERE_CONDITION--", (whereCondStr.equalsIgnoreCase("")) ? whereCondStr : " WHERE "+whereCondStr ),
						  ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_COLUMN, null );
				inList.add(obj);
		}
		
		
		
		abstractDVO.setObject(inList);
		requestDVO.setM_abstractDVO(abstractDVO);
		
		try{
			responseDVO = eventApplicationService.populateDropdown(requestDVO);
			LinkedHashMap<String,LinkedHashMap<Object,Object>> outMap = (LinkedHashMap<String,LinkedHashMap<Object,Object>>)responseDVO.getM_abstractDVO().getObject();
			
			if(!dependentOnly){
		
				m_hmpPartColorDlg = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.PART_COLOR_DROPDOWN);
				m_hmpTargetPlantDlg = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.TARGET_PLANT_DROPDOWN);				
				m_hmpDesignSectionDlg = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.DESIGN_SEC_DROPDOWN);			
				m_hmpCategoryDlg = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.CATEGORY_DROPDOWN);
			}
			
			if(modelDD)
				m_hmpModelDlg = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.MODEL_DROPDOWN);					
			
			if(mtcTypeDD)
				m_hmpMTCTypeDlg = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_DROPDOWN);		
				
			if(mtcOptionDD)
				m_hmpMTCOptionDlg = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.MTC_OPTION_DROPDOWN);
			
			if(populateFeaturesInfo) {
			
				// Populate Available and Assigned features grids
				populateFeatureGrids();
			}
			
		} catch(ApplicationException appExce){
			handleApplicationException(appExce);
		}

		logger.debug("\n Exiting populateDialogDropdowns() ");
		
	}
	
	public void populateCopyDropdowns(Boolean dependentOnly, Boolean modelDD, Boolean mtcTypeDD, Boolean mtcOptionDD, Boolean populateAvailableFeatures, Boolean populateAssignedFeatures) {

		logger.debug("\n Entering populateCopyDropdowns() ");
		
		RequestDVO requestDVO = new RequestDVO();
		ResponseDVO responseDVO = null;
		AbstractDVO abstractDVO = new AbstractDVO();
		List<DropdownDVO> inList = new ArrayList<DropdownDVO>();
		LinkedHashMap<Object , Object> allDetailMap = new LinkedHashMap<Object, Object>();
		DropdownDVO obj = null;

		if(!dependentOnly){
				
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.PART_COLOR_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_PART_COLOR_EVENT_APPLICATION_COPY,
						  ApplicationConstantsIF.APP_CONSTANTS.PART_COLOR_CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.CODE_DESC_TEXT_COLUMN,null);
				inList.add(obj);
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.TARGET_PLANT_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_TARGET_PLANT_EVENT_APPLICATION_COPY,
						  ApplicationConstantsIF.APP_CONSTANTS.CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.CODE_DESC_TEXT_COLUMN,null);
				inList.add(obj);
				
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.DESIGN_SEC_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_DESIGN_SECTION_EVENT_APPLICATION_COPY,
						  ApplicationConstantsIF.APP_CONSTANTS.CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.DISPLAY_CODE_COLUMN,null);
				inList.add(obj);
		
								
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.CATEGORY_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_CATEGORY_EVENT_APPLICATION_COPY,
						  ApplicationConstantsIF.APP_CONSTANTS.MODEL_CAT_CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.MODEL_CAT_CODE_COLUMN,null);
				inList.add(obj);
		}
		
		abstractDVO.setObject(inList);
		requestDVO.setM_abstractDVO(abstractDVO);
			
		try{

			if(!dependentOnly){
				
				responseDVO = eventApplicationService.populateDropdown(requestDVO);
				LinkedHashMap<String,LinkedHashMap<Object,Object>> outMap = (LinkedHashMap<String,LinkedHashMap<Object,Object>>)responseDVO.getM_abstractDVO().getObject();
			
				m_hmpPartColorCopy = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.PART_COLOR_DROPDOWN);
				m_hmpTargetPlantFromCopy = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.TARGET_PLANT_DROPDOWN);
				m_hmpTargetPlantToCopy = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.TARGET_PLANT_DROPDOWN);
				m_hmpDesignSectionFromCopy = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.DESIGN_SEC_DROPDOWN);
				m_hmpDesignSectionToCopy = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.DESIGN_SEC_DROPDOWN);
				m_hmpCategoryFromCopy = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.CATEGORY_DROPDOWN);
				
				m_hmpCategoryFromCopyOne = new LinkedHashMap();		
				m_hmpCategoryFromCopyOne.put(m_strCategoryFromCopy,m_strCategoryFromCopy);
				
				
				m_hmpCategoryToCopy = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.CATEGORY_DROPDOWN);
			}
			
			populateFromDependentDropdowns(modelDD, mtcTypeDD, mtcOptionDD);
			
			populateToDependentDropdowns(modelDD, mtcTypeDD, mtcOptionDD);
			
			// Populate Available and Assigned features grids
			populateFeatureGridsCopy(populateAvailableFeatures, populateAssignedFeatures);	
			
		} catch(ApplicationException appExce){
			handleApplicationException(appExce);
		}

		logger.debug("\n Exiting populateCopyDropdowns() ");
		
	}
	
	public void populateFromDependentDropdowns(Boolean modelDD, Boolean mtcTypeDD, Boolean mtcOptionDD){
		
		logger.debug("\n Entering populateFromDependentDropdowns() ");
		
		RequestDVO requestDVO = new RequestDVO();
		ResponseDVO responseDVO = null;
		AbstractDVO abstractDVO = new AbstractDVO();
		List<DropdownDVO> inList = new ArrayList<DropdownDVO>();
		DropdownDVO obj = null;
		
		if(modelDD){
			
			String whereCondStr = "";
			
			if(m_strEventNameCopy != null && !m_strEventNameCopy.equalsIgnoreCase(""))
					whereCondStr = whereCondStr + " TRIM(PH_COST_EVENT_NAME) = '"+m_strEventNameCopy+"' ";
			
			if(m_decEventRevisionNoCopy != null){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " EVENT_REV_NO = "+m_decEventRevisionNoCopy+" ";
				else
					whereCondStr = whereCondStr + " AND EVENT_REV_NO = "+m_decEventRevisionNoCopy+" ";
			}
					
			if(m_strTargetPlantFromCopy != null && !m_strTargetPlantFromCopy.equalsIgnoreCase("")){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlantFromCopy+"' ";
				else
					whereCondStr = whereCondStr + " AND TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlantFromCopy+"' ";
			}
			
			obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.MODEL_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_MODEL_EVENT_APPLICATION_COPY.replace("--WHERE_CONDITION--", (whereCondStr.equalsIgnoreCase("")) ? whereCondStr : " WHERE "+whereCondStr ),
						  ApplicationConstantsIF.APP_CONSTANTS.TGT_MODEL_DEV_CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.TGT_MODEL_DEV_CODE_COLUMN,null );
				inList.add(obj);
		}

		
		if(mtcTypeDD){
			
			String whereCondStr = "";
			
			if(m_strEventNameCopy != null && !m_strEventNameCopy.equalsIgnoreCase(""))
					whereCondStr = whereCondStr + " TRIM(PH_COST_EVENT_NAME) = '"+m_strEventNameCopy+"' ";
			
			if(m_decEventRevisionNoCopy != null){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " EVENT_REV_NO = "+m_decEventRevisionNoCopy+" ";
				else
					whereCondStr = whereCondStr + " AND EVENT_REV_NO = "+m_decEventRevisionNoCopy+" ";
			}
					
			if(m_strTargetPlantFromCopy != null && !m_strTargetPlantFromCopy.equalsIgnoreCase("")){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlantFromCopy+"' ";
				else
					whereCondStr = whereCondStr + " AND TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlantFromCopy+"' ";
			}
			
			if(m_strModelFromCopy != null && !m_strModelFromCopy.equalsIgnoreCase("")){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " TRIM(TGT_MODEL_DEV_CODE) = '"+m_strModelFromCopy+"' ";
				else
					whereCondStr = whereCondStr + " AND  TRIM(TGT_MODEL_DEV_CODE) = '"+m_strModelFromCopy+"' ";
			}
			
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_MTC_TYPE_EVENT_APPLICATION_COPY.replace("--WHERE_CONDITION--", (whereCondStr.equalsIgnoreCase("")) ? whereCondStr : " WHERE "+whereCondStr ),
						  ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_COLUMN, null );
				inList.add(obj);
		}
		
		
		
		abstractDVO.setObject(inList);
		requestDVO.setM_abstractDVO(abstractDVO);
		
		try{
			responseDVO = eventApplicationService.populateDropdown(requestDVO);
			LinkedHashMap<String,LinkedHashMap<Object,Object>> outMap = (LinkedHashMap<String,LinkedHashMap<Object,Object>>)responseDVO.getM_abstractDVO().getObject();		
			
			if(modelDD)
				m_hmpModelFromCopy = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.MODEL_DROPDOWN);
							
			m_hmpModelFromCopyOne = new LinkedHashMap();		
			m_hmpModelFromCopyOne.put(m_strModelFromCopy,m_strModelFromCopy);
			
			if(mtcTypeDD)
				m_hmpMTCTypeFromCopy = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_DROPDOWN);
			
			m_hmpMTCTypeFromCopyOne = new LinkedHashMap();		
			m_hmpMTCTypeFromCopyOne.put(m_strMTCTypeFromCopy1,m_strMTCTypeFromCopy1);
				
			if(mtcOptionDD)
				m_hmpMTCOptionFromCopy = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.MTC_OPTION_DROPDOWN);
			
		} catch(ApplicationException appExce){
			handleApplicationException(appExce);
		}
		
		logger.debug("\n Exiting populateFromDependentDropdowns() ");
	}
	
	public void populateToDependentDropdowns(Boolean modelDD, Boolean mtcTypeDD, Boolean mtcOptionDD){
		
		logger.debug("\n Entering populateToDependentDropdowns() ");
		
		RequestDVO requestDVO = new RequestDVO();
		ResponseDVO responseDVO = null;
		AbstractDVO abstractDVO = new AbstractDVO();
		List<DropdownDVO> inList = new ArrayList<DropdownDVO>();
		DropdownDVO obj = null;
		
		if(modelDD){
			
			String whereCondStr = "";
			
			if(m_strEventNameCopy != null && !m_strEventNameCopy.equalsIgnoreCase(""))
					whereCondStr = whereCondStr + " TRIM(PH_COST_EVENT_NAME) = '"+m_strEventNameCopy+"' ";
			
			if(m_decEventRevisionNoCopy != null){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " EVENT_REV_NO = "+m_decEventRevisionNoCopy+" ";
				else
					whereCondStr = whereCondStr + " AND EVENT_REV_NO = "+m_decEventRevisionNoCopy+" ";
			}
					
			if(m_strTargetPlantToCopy != null && !m_strTargetPlantToCopy.equalsIgnoreCase("")){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlantToCopy+"' ";
				else
					whereCondStr = whereCondStr + " AND TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlantToCopy+"' ";
			}
			
			obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.MODEL_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_MODEL_EVENT_APPLICATION_COPY.replace("--WHERE_CONDITION--", (whereCondStr.equalsIgnoreCase("")) ? whereCondStr : " WHERE "+whereCondStr ),
						  ApplicationConstantsIF.APP_CONSTANTS.TGT_MODEL_DEV_CODE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.TGT_MODEL_DEV_CODE_COLUMN,null );
				inList.add(obj);
		}

		
		if(mtcTypeDD){
			
			String whereCondStr = "";
			
			if(m_strEventNameCopy != null && !m_strEventNameCopy.equalsIgnoreCase(""))
					whereCondStr = whereCondStr + " TRIM(PH_COST_EVENT_NAME) = '"+m_strEventNameCopy+"' ";
			
			if(m_decEventRevisionNoCopy != null){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " EVENT_REV_NO = "+m_decEventRevisionNoCopy+" ";
				else
					whereCondStr = whereCondStr + " AND EVENT_REV_NO = "+m_decEventRevisionNoCopy+" ";
			}
					
			if(m_strTargetPlantToCopy != null && !m_strTargetPlantToCopy.equalsIgnoreCase("")){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlantToCopy+"' ";
				else
					whereCondStr = whereCondStr + " AND TRIM(TGT_PLANT_LOC_CODE) = '"+m_strTargetPlantToCopy+"' ";
			}
			
			if(m_strModelToCopy != null && !m_strModelToCopy.equalsIgnoreCase("")){
				
				if(whereCondStr.length() == 0)
					whereCondStr = whereCondStr + " TRIM(TGT_MODEL_DEV_CODE) = '"+m_strModelToCopy+"' ";
				else
					whereCondStr = whereCondStr + " AND  TRIM(TGT_MODEL_DEV_CODE) = '"+m_strModelToCopy+"' ";
			}
			
				obj = new DropdownDVO(ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_DROPDOWN,
						  EventProcessingSQLIF.POPULATE_MTC_TYPE_EVENT_APPLICATION_COPY.replace("--WHERE_CONDITION--", (whereCondStr.equalsIgnoreCase("")) ? whereCondStr : " WHERE "+whereCondStr ),
						  ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_COLUMN,
						  ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_COLUMN, null );
				inList.add(obj);
		}
		
		
		
		abstractDVO.setObject(inList);
		requestDVO.setM_abstractDVO(abstractDVO);
		
		try{
			responseDVO = eventApplicationService.populateDropdown(requestDVO);
			LinkedHashMap<String,LinkedHashMap<Object,Object>> outMap = (LinkedHashMap<String,LinkedHashMap<Object,Object>>)responseDVO.getM_abstractDVO().getObject();		
			
			if(modelDD)
				m_hmpModelToCopy = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.MODEL_DROPDOWN);					
			
			if(mtcTypeDD)
				m_hmpMTCTypeToCopy = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE_DROPDOWN);		
				
			if(mtcOptionDD)
				m_hmpMTCOptionToCopy = outMap.get(ApplicationConstantsIF.APP_CONSTANTS.MTC_OPTION_DROPDOWN);
			
		} catch(ApplicationException appExce){
			handleApplicationException(appExce);
		}
		
		logger.debug("\n Exiting populateToDependentDropdowns() ");
	}
	public void populateFeatureGrids(){
		
		logger.debug("\n Entering populateFeatureGrids() ");
		
		RequestDVO requestDVO = new RequestDVO();
		ResponseDVO responseDVO = null;
		
		requestDVO.setM_abstractDVO(getEventApplicationDialogFormObject());
		
		responseDVO = eventApplicationService.getAvailableFeatures(requestDVO);
		m_availableFeaturesList = ((ListDVO)responseDVO.getM_abstractDVO()).getList();
		
		if(m_availableFeaturesList != null && m_availableFeaturesList.size() > 0)
			m_selectedAvailableFeature = (CodeDVO)m_availableFeaturesList.get(0);
		else
			m_selectedAvailableFeature = null;
		
		responseDVO = eventApplicationService.getAssignedFeatures(requestDVO);
		m_assignedFeaturesList = ((ListDVO)responseDVO.getM_abstractDVO()).getList();
		
		if(m_assignedFeaturesList != null && m_assignedFeaturesList.size() > 0)
			m_selectedAssignedFeature = (CodeDVO)m_assignedFeaturesList.get(0);
		else
			m_selectedAssignedFeature = null;

		logger.debug("\n Exiting populateFeatureGrids() ");
	}
	
	public void populateFeatureGridsCopy(Boolean populateAvailableFeatures, Boolean populateAssignedFeatures){
		
		logger.debug("\n Entering populateFeatureGridsCopy() ");
		
		RequestDVO requestDVO = new RequestDVO();
		ResponseDVO responseDVO = null;
		
		requestDVO.setM_abstractDVO(getEventApplicationCopyFormObject());

		if(populateAvailableFeatures){
		
			responseDVO = eventApplicationService.getAvailableFeaturesCopy(requestDVO);
			m_availableFeaturesListCopy = ((ListDVO)responseDVO.getM_abstractDVO()).getList();
		}
		
		if(populateAssignedFeatures){
			
			responseDVO = eventApplicationService.getAssignedFeaturesCopy(requestDVO);
			m_assignedFeaturesListCopy = ((ListDVO)responseDVO.getM_abstractDVO()).getList();
		}

		logger.debug("\n Exiting populateFeatureGridsCopy() ");
	}
	
	public void onEventNameChange() throws SQLException{
		
		logger.debug("\n Entering onEventNameChange() ");
		ResponseDVO responseDVO = null;
		UserInfoDVO userInfoDVO = getM_userInfoDVO();
		
		try{
				if(m_strDummyEventName == null || (m_strDummyEventName != null && m_strDummyEventName.equalsIgnoreCase("")) 
						|| !m_hmpAllEventName.containsKey(m_strDummyEventName)){
					
					m_strEventName = null;
					m_decEventRevisionNo = null;
					m_strEventNameDesc = null;
					m_strEventType = null;
					
				} else{
					
					m_strEventName = (String)((HashMap)m_hmpAllEventName.get(m_strDummyEventName)).get(ApplicationConstantsIF.APP_CONSTANTS.PH_COST_EVENT_NAME_COLUMN);
					m_decEventRevisionNo = (BigDecimal)((HashMap)m_hmpAllEventName.get(m_strDummyEventName)).get(ApplicationConstantsIF.APP_CONSTANTS.EVENT_REV_NO_COLUMN);
					m_strEventNameDesc = (String)((HashMap)m_hmpAllEventName.get(m_strDummyEventName)).get(ApplicationConstantsIF.APP_CONSTANTS.PH_COST_EVENT_TEXT_COLUMN);
					m_strEventType = (String)((HashMap)m_hmpAllEventName.get(m_strDummyEventName)).get(ApplicationConstantsIF.APP_CONSTANTS.PH_CMS_ITEM_CODE_COLUMN);
					
				}
						
				populateDropdowns(true, true, true, true);
			
				
				m_strModel = null;	
				m_strMTCType1 = null;		
				m_strMTCOption = null;
				
				//****Commented as not required****

				m_bolgbDataCheckOk = true;
				if(!m_bolgbDataCheckOk){
					
					m_bolEditableGrid = false;
					m_bolAddNewEnabled = false;
					m_bolApplyEnabled = false;
					m_bolDeleteEnabled = false;
					m_bolCopyEnabled = false;
					
				} else {
					
					m_bolEditableGrid = true;
					m_bolAddNewEnabled = true;
					m_bolApplyEnabled = true;
					m_bolDeleteEnabled = true;
					m_bolDetailsEnabled = true;
					m_bolCopyEnabled = true;
					
				}
				//CPT-1655 start
				if (userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.NM_COST_STAFF.value) || 
						userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.MEX_NM_STAFF.value) ){
					m_bolEditableGrid = false;
					m_bolAddNewEnabled = false;
					m_bolApplyEnabled = false;
					m_bolDetailsEnabled = true;
					m_bolCopyEnabled = false;
					m_bolEventFreeze = true;
				}
				//CPT-1655 end
		}
		catch(Exception e){
			handleSystemException(e, "");
		}
		
		logger.debug("\n Exiting onEventNameChange() ");	
	}
	
	public void onTargetPlantChange() throws SQLException{
		
		logger.debug("\n Entering onTargetPlantChange() ");
		
		if((m_strEventName==null||(m_strEventName!=null&&m_strEventName.trim().isEmpty()))&&
				m_strDummyEventName!=null && !m_strDummyEventName.trim().isEmpty()){
			onEventNameChange();
		}
		
		if(m_strTargetPlant != null && m_strTargetPlant.equalsIgnoreCase(""))
			m_strTargetPlant = null;
				
		populateDropdowns(true, true, true, true);
	
		m_strModel = null;
		m_strMTCType1 = null;		
		m_strMTCOption = null;
		
		logger.debug("\n Exiting onTargetPlantChange() ");	
	}
	
public void onModelChange() throws SQLException{
		
		logger.debug("\n Entering onModelChange() ");
		
		if((m_strEventName==null||(m_strEventName!=null&&m_strEventName.trim().isEmpty()))&&
				m_strDummyEventName!=null && !m_strDummyEventName.trim().isEmpty()){
			onEventNameChange();
		}
		
		if(m_strModel != null && m_strModel.equalsIgnoreCase(""))
			m_strModel = null;
				
		populateDropdowns(true, false, true, true);
	
		m_strMTCType1 = null;		
		m_strMTCOption = null;
		
		logger.debug("\n Exiting onModelChange() ");	
	}
	
	public void onMTCTypeChange() throws SQLException{
		
		logger.debug("\n Entering onMTCTypeChange() ");
		
		if((m_strEventName==null||(m_strEventName!=null&&m_strEventName.trim().isEmpty()))&&
				m_strDummyEventName!=null && !m_strDummyEventName.trim().isEmpty()){
			onEventNameChange();
		}
		
		if(m_strMTCType1 != null && m_strMTCType1.size()==0)
			m_strMTCType1 = null;
				
		populateDropdowns(true, false, false, true);
		
		m_strMTCOption = null;
	
		logger.debug("\n Exiting onMTCTypeChange() ");	
	}
	
	private boolean getFieldLevelSecurityOK() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void searchEventApplications() throws SQLException{
		
		logger.debug("\n Entering searchEventApplications() ");
		ResponseDVO responseDVO = null;		
		RequestDVO requestDVO = new RequestDVO();
		EventApplicationDVO eventApplicationDVO = null;
		HashMap<String , String> filterValueMap = new HashMap<String, String>();
		resetDataGrid("searchEventApplicationForm:eventApplicationsTable");
		logger.info("Check Event Name");
		UserInfoDVO userInfoDVO = getM_userInfoDVO();
		//Defect 1611: QC "Event Name is required"
		//Found out that m_strEventName is empty but m_strDummyEventName is not(Don't know why & how)
		logger.info("\n searchEventApplications Event Name Before Find ="+m_strEventName);
		logger.info("\n searchEventApplications Dummy Event Name Before Find ="+m_strDummyEventName);
		if((m_strEventName==null||(m_strEventName!=null&&m_strEventName.trim().isEmpty()))&&
				m_strDummyEventName!=null && !m_strDummyEventName.trim().isEmpty()){
			onEventNameChange();
		}
		logger.info("\n searchEventApplications Event Name After Find ="+m_strEventName);
		logger.info("\n searchEventApplications Dummy Event Name After Find ="+m_strDummyEventName);
		if(m_strEventName == null || m_strEventName.equalsIgnoreCase("")){
			logger.info("\n Event Name="+m_strEventName);
			logger.info("\n Dummy Event Name="+m_strDummyEventName);
			addMessage(FacesMessage.SEVERITY_WARN, "Event Name is required", null, null);			
			return;
		}
		
		try{
			if(m_strSupplier!=null&&!m_strSupplier.isEmpty()&&(m_strSupplierName==null||m_strSupplierName.isEmpty())){
				onSupplierNumChange();
				RequestContext.getCurrentInstance().update("searchEventApplicationForm:suppName");
			}

				eventApplicationDVO = getEventApplicationSearchFormObject();
				
				freezeCheck(eventApplicationDVO);
				
				setFilterValues(filterValueMap, eventApplicationDVO);
				setSessionFilterValues(ApplicationConstantsIF.SESSIONKEY.SESSION_FILTER_PARAM_EVENT_PROCESSING , filterValueMap);
				
				if(m_strEventType.equalsIgnoreCase("NMCE")){
					
					if(m_bolgbEntireEvt || m_bolgbAllApp){
						
						m_bolEditableGrid = false;
						m_bolAddNewEnabled = false;
						m_bolApplyEnabled = false;
						m_bolDetailsEnabled = true;
						m_bolCopyEnabled = false;
						refreshSearchGrid(ApplicationConstantsIF.APP_CONSTANTS.SEARCH_ACTION);
						return;
						
					} else {		
						
						if(m_bolgbDataCheckOk){
							
							m_bolAddNewEnabled = true;
							m_bolApplyEnabled = true;
							m_bolDetailsEnabled = true;
							m_bolEditableGrid = true;
						}
						m_bolSummaryEnabled = true;						
						m_bolCopyEnabled = true;
						refreshSearchGrid(ApplicationConstantsIF.APP_CONSTANTS.SEARCH_ACTION);
					}

							
				} else {
					
					if(m_strPartNumber == null ||  m_strPartNumber.equalsIgnoreCase("")) {
						
						addMessage(FacesMessage.SEVERITY_WARN, "Enter Part Number", null, null);			
						return;
					} else {
						Integer count = 0;// Call Service -> BO-> DAO
						count = eventApplicationService.checkSuppByPartAndEvent(m_strPartNumber.trim(), m_strEventName.trim(), m_decEventRevisionNo.intValue());
						if(count > 1) {
							if(m_strSupplier == null ||  m_strSupplier.equalsIgnoreCase("")) {
								addMessage(FacesMessage.SEVERITY_WARN, "The selected part and event has multiuple suppliers. So please enter Supplier Number", null, null);			
								return;
							}
						}
					}
					
					if(m_bolgbEntireEvt || m_bolgbAllApp){
						
						m_bolEditableGrid = false;
						m_bolAddNewEnabled = false;
						m_bolApplyEnabled = false;
						m_bolDetailsEnabled = true;
						m_bolCopyEnabled = false;
						m_bolEventFreeze = true;
						m_bolEditableGridOnQtyRateChange=true;
						refreshSearchGrid(ApplicationConstantsIF.APP_CONSTANTS.SEARCH_ACTION);
						m_bolSummaryEnabled = false;
						m_bolShowCopyBtn = true;
						return;
						
					} else {		
						
						m_bolEditableGrid = true;
						m_bolAddNewEnabled = true;
						m_bolApplyEnabled = true;
						m_bolDetailsEnabled = true;
						m_bolCopyEnabled = true;
						m_bolEventFreeze = false;
						m_bolEditableGridOnQtyRateChange=false;
						m_bolShowCopyBtn = true;
						refreshSearchGrid(ApplicationConstantsIF.APP_CONSTANTS.SEARCH_ACTION);
					}
					m_bolSummaryEnabled = false;
					
				}
				
				String plantParam = null;
				
				if(m_strTargetPlant == null || m_strTargetPlant.equals(""))
					plantParam = "#";
				else
					plantParam = m_strTargetPlant;
				//****Commented as not required****
				
				m_bolgbDataCheckOk = true;
				if(!m_bolgbDataCheckOk){
					
					if(m_bolgbDataCheckOk)
						m_bolgbDataCheckOk = false;
					
					m_bolEditableGrid = false;
					m_bolCopyEnabled = false;
					m_bolApplyEnabled = false;
					m_bolAddNewEnabled = false;
					
				} else {
					
					m_bolEditableGrid = true;
					m_bolCopyEnabled = true;
					m_bolApplyEnabled = true;
					m_bolAddNewEnabled = true;
					m_bolDetailsEnabled = true;
				}
				if(m_selectedEventApplication !=null)//Clearing off the selection
					m_selectedEventApplication.clear();
				
				//Quantity and Rate variables cleared off on click of 'Find Now'
				m_intQtyForSelected=null;
				m_bRateForSelected=null;
				//CPT-1655 start
				if (userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.NM_COST_STAFF.value) || 
						userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.MEX_NM_STAFF.value) ){
					m_bolEditableGrid = false;
					m_bolAddNewEnabled = false;
					m_bolApplyEnabled = false;
					m_bolDetailsEnabled = true;
					m_bolCopyEnabled = false;
					m_bolEventFreeze = true;
					m_bolEditableGridOnQtyRateChange = true;
				}
				//CPT-1655 end
		}
		catch(Exception e){
			
			handleSystemException(e, "");
		}
		
		logger.debug("\n Exiting searchEventApplications() ");	
	}

	public EventApplicationDVO getEventApplicationSearchFormObject() {
		//insert new parameter IsSupply
		EventApplicationDVO eventApplicationDVO = new EventApplicationDVO();
		
		eventApplicationDVO.setM_strEventName(m_strEventName);
		eventApplicationDVO.setM_decEventRevNo(m_decEventRevisionNo);
		eventApplicationDVO.setM_strCategory(m_strCategory);
		eventApplicationDVO.setM_strMTCType1(m_strMTCType1);
		eventApplicationDVO.setM_strMTCOption(m_strMTCOption);
		eventApplicationDVO.setM_strGrade(m_strGrade);
		eventApplicationDVO.setM_strAtMt(m_strAtMt);
		eventApplicationDVO.setM_strPartColor(m_strPartColor);
		eventApplicationDVO.setM_strPartNumber(m_strPartNumber);
		eventApplicationDVO.setM_strRPTCurrency(m_strRPTCurrency);
		eventApplicationDVO.setM_strDesignSection(m_strDesignSection);
		eventApplicationDVO.setM_strProcSection(m_procSection);
		eventApplicationDVO.setM_strFeatureCode(m_strFeatureCode);
		eventApplicationDVO.setM_strSupplierNumber(m_strSupplier);
		eventApplicationDVO.setM_strModel(m_strModel);
		eventApplicationDVO.setM_strTPlant(m_strTargetPlant);
		eventApplicationDVO.setM_strTargetPlant(m_strTargetPlant);
		eventApplicationDVO.setM_strIsSupply(m_strIsSupply);
		eventApplicationDVO.setM_strViewMTO((m_strViewMTO!=null&&!m_strViewMTO.trim().isEmpty())?m_strViewMTO:"B");
		eventApplicationDVO.setM_strSpecificSearch(m_strSpecificSearch);
		eventApplicationDVO.setM_intQtyForSelected(m_intQtyForSelected);
		eventApplicationDVO.setM_bRateForSelected(m_bRateForSelected);
		eventApplicationDVO.setM_strIsSupplyForSelected(m_strIsSupplyForSelected);
		//need to include parameter here for QTY,RATE,ISUPPLY
		if(null != m_strParentPage && !m_strParentPage.isEmpty() ){
			eventApplicationDVO.setM_strSearchType("DETAIL");
		}
		return eventApplicationDVO;
	}

	public void clearSearchEventApplicationForm(){
		
		logger.debug("\n Entering clearSearchEventApplicationForm() ");
		
		m_strAction = null;
		m_strMode = null;
		
		//Filter Criteria
		m_strEventName = null;
		m_strDummyEventName = null;
		m_strEventNameDesc = null;
		m_strPartNumber = null;
		m_strPartColor = null;
		m_strTargetPlant = null;
		m_strSupplier = null;
		m_strRPTCurrency = "USD";
		m_strDesignSection = null;
		m_procSection = null;
		m_strFeatureCode = null;
		m_strModel = null;
		m_strMTCType1 = null;
		m_strMTCOption = null;
		m_strCategory = null;
		m_strOperator = null;
		m_intQty = null;
		m_strPartName = null;
		m_strSupplierName = null;
		m_strHPlant = null;
		m_strTPlant = null;
		m_intRate = null;
		m_decEventRevisionNo = null;
		m_strEventType = null;

		//Search list
		m_eventApplicationList = null;
		
		m_selectedApplication = null;
		
	    m_bolgbEntireEvt = false;
	    m_bolgbAllApp = false;
	    m_bolgbBaseCost = false;
	    m_bolgbFeature = false;
	    m_bolgbAllInv = false;
	    m_bolgbComHamInv = false;
	    m_bolgbExclHamInv = false;
	    m_bolgbExclSupInv = false;
	    m_bolgbDrawCost = false;
	    m_bolgbAllSupCost = false;
	    m_bolgbTgtCost = false;
	    m_bolgbDataCheckOk = false;

	    m_bolDeleteEnabled = true;

	    m_bolEditableGrid = false;

	    m_bolIsGridEmpty = true;
		
		// Detail page attributes : Start
		m_strEventNameDlg = null;
		m_strEventNameDescDlg = null;
		m_strPartNumberDlg = null;
		m_strPartColorDlg = null;
		m_strTargetPlantDlg = null;
		m_strOldTargetPlantDlg = null;
		m_strSupplierDlg = null;
		m_strDesignSectionDlg = null;
		m_strOldDesignSectionDlg = null;
		m_strModelDlg = null;
		m_strOldModelDlg = null;
		m_strMTCTypeDlg1 = null;
		m_strOldMTCTypeDlg1 = null;
		m_strMTCOptionDlg = null;
		m_strOldMTCOptionDlg = null;
		m_strCategoryDlg = null;
		m_strOldCategoryDlg = null;
		m_strOperatorDlg = null;
		m_intQtyDlg = null;
		m_bQtyDlg = null;
		m_strPartNameDlg = null;
		m_strSupplierNameDlg = null;
		m_bRateDlg = null;
		m_decEventRevisionNoDlg = null;
		m_bolChkAssociation = false;
		m_strEventTypeDlg = null;
		m_eventApplicationListDlg = null;
		m_strShipToCode = null;
		m_strOldShipToCode = null;
		m_selectedApplicationDlg = null;
		m_bolShowFeatures = null;
		
	    m_bolEnableAddDlg = true;
	    m_bolEnableRemoveDlg = true;
	    m_bolEnablePartNumberDlg = true;
	    m_bolEnableSupplierNumberDlg = true;
	    m_bolEnablePartSearchDlg = true;
	    m_bolEnableSupplierSearchDlg = true;
	    m_bolEnablePartColorDlg = true;
	    m_bolEnableTargetPlantDlg = true;
	    m_bolEnableModelDlg = true;
	    m_bolEnableMTCTypeDlg = true;
	    m_bolEnableMTCOptionDlg = true;
	    m_bolEnableCategoryDlg = true;
	    m_bolEnablePartSectionDlg = true;
	    m_bolEnableApplyDlg = true;
	    m_bolEnableDeleteDlg = true;
	    m_bolEnableOkDlg = true;
	    
	    m_selectedAssignedFeature = null;
	    m_selectedAvailableFeature = null;
		
		m_availableFeaturesList = null;
	    m_assignedFeaturesList = null;
		
		validationMsg = null;
		focusField = null;
		
		//Check Flags
		checkFlagMap = null;
		m_strParentPage = null;
		
		// Dialog attributes : End
		
		// Copy screen attributes : Start
		m_strEventNameCopy = null;
		m_decEventRevisionNoCopy = null;
		m_strEventNameDescCopy = null;
		m_strPartNumberCopy = null;
		m_strPartNameCopy = null;
		m_strPartColorCopy = null;
		m_strSupplierCopy = null;
		m_strSupplierNameCopy = null;

		m_strTargetPlantFromCopy = null;
		m_strDesignSectionFromCopy = null;
		m_strModelFromCopy = null;
		m_strShipToCodeFromCopy = null;
		m_strMTCTypeFromCopy1 = null;
		m_strMTCOptionFromCopy = null;
		m_strCategoryFromCopy = null;
		m_intQtyFromCopy = null;
		m_intShareFromCopy = null;
		
		m_strTargetPlantToCopy = null;
		m_strDesignSectionToCopy = null;
		m_strModelToCopy = null;
		m_strShipToCodeToCopy = null;
		m_strMTCTypeToCopy1 = null;
		m_strMTCOptionToCopy = null;
		m_strCategoryToCopy = null;
		m_intQtyToCopy = null;
		m_strShareToCopy = null;

		m_selectedApplicationCopy = null;
	    m_selectedAssignedFeatureCopy = null;
	    m_selectedAvailableFeatureCopy = null;
	    
		m_eventApplicationListCopy = null;
		m_availableFeaturesListCopy = null;
		m_assignedFeaturesListCopy = null;
		
	    m_bolEnableAddCopy = true;
	    m_bolEnableRemoveCopy = true;
	    
	    m_strIsSupply = "false";
	    m_strSpecificSearch = "false";
	    
	    m_intQtyForSelected = null;
	    m_bRateForSelected = null;
	    m_strDesignSectionForSelected = null;
	    m_bDesignSectionEnabled = false;
	    
	    //Changes for enabling/disabling 'apply' button and 'Qty' and 'Rate' columns
	    m_bolApplyEnabled=true;
	    if(m_hmpSelectedEventApplicationDetails!=null)
	    	m_hmpSelectedEventApplicationDetails.clear();
	    clearSessionFilterValues(ApplicationConstantsIF.SESSIONKEY.SESSION_FILTER_PARAM_EVENT_PROCESSING);

		logger.debug("\n Exiting clearSearchEventApplicationForm() ");	
	}

	
	public void setEventApplicationDialogForm(EventApplicationDVO inDVO){
		
		logger.debug("\n Entering setEventApplicationDialogForm() ");
		
		m_strPartNumberDlg = (inDVO.getM_strPartNumber() != null && inDVO.getM_strPartNumber().equalsIgnoreCase("") ) ? null : inDVO.getM_strPartNumber() ;
		m_strPartNameDlg = (inDVO.getM_strPartName() != null && inDVO.getM_strPartName().equalsIgnoreCase("") ) ? null : inDVO.getM_strPartName() ;
		m_strPartColorDlg = (inDVO.getM_strPartColor() != null && inDVO.getM_strPartColor().equalsIgnoreCase("") ) ? null : inDVO.getM_strPartColor() ;
		m_strSupplierDlg = (inDVO.getM_strSupplierNumber() != null && inDVO.getM_strSupplierNumber().equalsIgnoreCase("") ) ? null : inDVO.getM_strSupplierNumber() ;
		m_strSupplierNameDlg = (inDVO.getM_strSupplierName() != null && inDVO.getM_strSupplierName().equalsIgnoreCase("") ) ? null : inDVO.getM_strSupplierName() ;
		m_strDesignSectionDlg = (inDVO.getM_strDesignSection() != null && inDVO.getM_strDesignSection().equalsIgnoreCase("") ) ? null : inDVO.getM_strDesignSection() ;
		m_strModelDlg = (inDVO.getM_strModel() != null && inDVO.getM_strModel().equalsIgnoreCase("") ) ? null : inDVO.getM_strModel() ;
		m_strTargetPlantDlg = (inDVO.getM_strTargetPlant() != null && inDVO.getM_strTargetPlant().equalsIgnoreCase("") ) ? null : inDVO.getM_strTargetPlant() ;
		//m_strMTCTypeDlg1 = (inDVO.getM_strMTCType1() != null && inDVO.getM_strMTCType1().size()==0 ) ? null : inDVO.getM_strMTCType1() ;
		//Changes for PSCC-5790 start
		//m_strMTCTypeDlg1 = (m_selectedApplication.getM_strMTCType1() != null && m_selectedApplication.getM_strMTCType1().size()==0 ) ? null : m_selectedApplication.getM_strMTCType1() ;
		if (ApplicationConstantsIF.APP_CONSTANTS.UPDATE_MODE.equals(m_strMode)) {
			// Details button: always use DB value from the selected row
			if (inDVO.getM_strMTCType() != null && !inDVO.getM_strMTCType().trim().isEmpty()) {
				ArrayList<String> mtcTypeList = new ArrayList<String>();
				mtcTypeList.add(inDVO.getM_strMTCType().trim());
				m_strMTCTypeDlg1 = mtcTypeList;
			} else {
				m_strMTCTypeDlg1 = null;
			}
		} else {
			// Add New button: keep old behavior (use search filter / m_selectedApplication values)
			m_strMTCTypeDlg1 = (m_selectedApplication != null && m_selectedApplication.getM_strMTCType1() != null && m_selectedApplication.getM_strMTCType1().size() > 0) 
				? m_selectedApplication.getM_strMTCType1() : null;
		}// Changes for PSCC-5790 end
		m_strMTCOptionDlg = (inDVO.getM_strMTCOption() != null && inDVO.getM_strMTCOption().equalsIgnoreCase("") ) ? null : inDVO.getM_strMTCOption() ;
		m_strCategoryDlg = (inDVO.getM_strCategory() != null && inDVO.getM_strCategory().equalsIgnoreCase("") ) ? null : inDVO.getM_strCategory() ;
		m_intQtyDlg = inDVO.getM_intQuantity();
		m_bRateDlg = inDVO.getM_bRate();
		if(inDVO.getM_strIsSupply()!=null)
			m_strIsSupply = inDVO.getM_strIsSupply().trim();
		m_strIsCopyBOM=inDVO.getM_strIsCopyBOM();
		m_bolChkAssociation = null;
		
		logger.debug("\n Exiting setEventApplicationDialogForm() ");
	}
	
	public EventApplicationDVO getEventApplicationDialogFormObject(){
		
		logger.debug("\n Entering getEventApplicationDialogFormObject() ");
		
		EventApplicationDVO outDVO = new EventApplicationDVO();
		
		outDVO.setM_strEventName(m_strEventNameDlg);
		outDVO.setM_strEventNameDesc(m_strEventNameDescDlg);
		outDVO.setM_decEventRevNo(m_decEventRevisionNoDlg);
		outDVO.setM_strPartNumber(m_strPartNumberDlg);
		outDVO.setM_strPartName(m_strPartNameDlg);
		outDVO.setM_strPartColor(m_strPartColorDlg);
		outDVO.setM_strSupplierNumber(m_strSupplierDlg);
		outDVO.setM_strSupplierName(m_strSupplierNameDlg);
		outDVO.setM_strDesignSection(m_strDesignSectionDlg);
		outDVO.setM_strOldDesignSection(m_strOldDesignSectionDlg);
		outDVO.setM_strModel(m_strModelDlg);
		outDVO.setM_strOldModel(m_strOldModelDlg);
		outDVO.setM_strTargetPlant(m_strTargetPlantDlg);
		outDVO.setM_strOldTargetPlant(m_strOldTargetPlantDlg);
		outDVO.setM_strMTCType1(m_strMTCTypeDlg1);
		outDVO.setM_strOldMTCType1(m_strOldMTCTypeDlg1);
		outDVO.setM_strMTCOption(m_strMTCOptionDlg);
		outDVO.setM_strOldMTCOption(m_strOldMTCOptionDlg);
		outDVO.setM_strCategory(m_strCategoryDlg);
		outDVO.setM_strOldCategory(m_strOldCategoryDlg);
		outDVO.setM_intQuantity(m_intQtyDlg);
		outDVO.setM_bRate(m_bRateDlg);
		outDVO.setM_strShipToCode(m_strShipToCode);
		outDVO.setM_strOldShipToCode(m_strOldShipToCode);
		outDVO.setM_strIsSupply(m_strIsSupply);
		outDVO.setM_strIsCopyBOM(m_strIsCopyBOM);
		/**
		 *  Where after saving when clicked on Cancel the previous screen data is lost (seems an existing prod issue). 
		 *  Defect Id 2008 - Blank Model CAT Code
		 */
		outDVO.setM_strViewMTO((m_strViewMTO!=null&&!m_strViewMTO.trim().isEmpty())?m_strViewMTO:"B");
		
		if(m_selectedAvailableFeature != null)
			outDVO.setM_strAvailableProductEquipCode(m_selectedAvailableFeature.getM_strCode());
		
		if(m_selectedAssignedFeature != null)
			outDVO.setM_strAssignedProductEquipCode(m_selectedAssignedFeature.getM_strCode());
		
		logger.debug("\n Exiting getEventApplicationDialogFormObject() ");
		
		return outDVO;
	}
	
	public void openEventApplicationDetails() throws Exception{
		
		logger.debug("\n Entering openEventApplicationDetails() ");
	
		m_bolShowFeatures = true;
		m_strMode = ApplicationConstantsIF.APP_CONSTANTS.UPDATE_MODE;
		m_CheckedEventApplication=new ArrayList<EventApplicationDVO>();
		if(m_eventApplicationList!=null){
			for(EventApplicationDVO eventApplicationDVO:m_eventApplicationList){
				if(eventApplicationDVO.isM_bSelected()){
					m_CheckedEventApplication.add(eventApplicationDVO);
				}
			}
		}
		openMaintainEventApplicationDialog();
		populateCategoryOnModelSelection();
		
		logger.debug("\n Exiting openEventApplicationDetails() ");
	}
	
	public void openMaintainEventApplicationDialog() throws Exception{
		
		logger.debug("\n Entering openMaintainEventApplicationDialog() ");	
		UserInfoDVO userInfoDVO = getM_userInfoDVO();
		RequestDVO requestDVO = instantiateRequestDVO();
		ResponseDVO responseDVO = null;
		EventApplicationDVO eventApplicationDVO=null;
		eventApplicationDVO = getEventApplicationSearchFormObject();
		m_strEventNameDlg = m_strEventName;
		m_decEventRevisionNoDlg = m_decEventRevisionNo;
		if(!ApplicationConstantsIF.APP_CONSTANTS.ADD_MODE.equals(m_strMode) && (m_selectedEventApplication!=null && (m_selectedEventApplication.size()>1 || m_selectedEventApplication.size()==0))){
			addMessage(FacesMessage.SEVERITY_WARN, "Please select a record to view details", null, null);			
			return;
		}else if(m_selectedEventApplication!=null && m_selectedEventApplication.size()==1 && !ApplicationConstantsIF.APP_CONSTANTS.ADD_MODE.equals(m_strMode)){
			m_selectedApplication = m_selectedEventApplication.get(0);
		}
		
		if(m_selectedApplication != null) {
			
			m_selectedApplication.setM_strEventName(m_strEventNameDlg);
			m_selectedApplication.setM_decEventRevNo(m_decEventRevisionNoDlg);
			m_selectedApplication.setM_strMTCType1(eventApplicationDVO.getM_strMTCType1());	
			requestDVO.setM_abstractDVO(m_selectedApplication);
			responseDVO = eventApplicationService.getEventApplicationDetails(requestDVO);
			setEventApplicationDialogForm((EventApplicationDVO)responseDVO.getM_abstractDVO());
			
			m_intQtyDlg = null;
			m_bRateDlg = new BigDecimal(100);
		} else {
			
			m_strPartNumberDlg = m_strPartNumber;
			m_strSupplierDlg = m_strSupplier;
			m_intQtyDlg = null;
			m_bRateDlg = new BigDecimal(100);
			if("false".equalsIgnoreCase(m_strIsSupply)){
				m_strIsSupply = "";
			}
			EventApplicationDVO inDVO = new EventApplicationDVO();

			inDVO.setM_strEventName(m_strEventNameDlg);
			inDVO.setM_decEventRevNo(m_decEventRevisionNoDlg);
			inDVO.setM_strPartNumber(m_strPartNumber);
			inDVO.setM_strPartColor(m_strPartColor);
			inDVO.setM_strSupplierNumber(m_strSupplier);
			
			requestDVO.setM_abstractDVO(inDVO);	
		}
		
		m_bolEnablePartNumberDlg = true;
		m_bolEnableSupplierNumberDlg = true;
		m_bolEnablePartColorDlg = true;
		m_bolEnablePartSearchDlg = true;
		m_bolEnableSupplierSearchDlg = true;
		
		if(m_bolgbEntireEvt || m_bolgbAllApp || !m_bolgbDataCheckOk){
			
			m_bolEnablePartNumberDlg = false;
			m_bolEnablePartSearchDlg = false;
			m_bolEnableSupplierNumberDlg= false;
			m_bolEnableSupplierSearchDlg = false;
			m_bolEnablePartColorDlg = false;
			m_bolEnableAddDlg = false;
			m_bolEnableRemoveDlg = false;
			m_bolEnableTargetPlantDlg = false;
			m_bolEnableModelDlg = false;
			m_bolEnableMTCTypeDlg = false;
			m_bolEnableMTCOptionDlg = false;
			m_bolEnableCategoryDlg = false;
			m_bolEnablePartSectionDlg = false;
			
			m_bolEnableApplyDlg = false;
			m_bolEnableDeleteDlg = false;
			m_bolEnableOkDlg = false;
		}
			
		if(m_bolgbFeature){
			
			m_bolEnableAddDlg = false;
			m_bolEnableRemoveDlg = false;
		}
		
		responseDVO = eventApplicationService.getPartName(requestDVO);
		if(((ListDVO)responseDVO.getM_abstractDVO()).getList() == null || ((ListDVO)responseDVO.getM_abstractDVO()).getList().size() == 0 ){
			
			responseDVO = eventApplicationService.getNewPartName(requestDVO);
			
			if(((ListDVO)responseDVO.getM_abstractDVO()).getList() == null || ((ListDVO)responseDVO.getM_abstractDVO()).getList().size() == 0 ){
				
				m_strPartNameDlg = null;
				
			} else
				m_strPartNameDlg = (String)((ListDVO)responseDVO.getM_abstractDVO()).getList().get(0);
		
		} else {
			
			m_strPartNameDlg = (String)((ListDVO)responseDVO.getM_abstractDVO()).getList().get(0);
		}
		
		
		responseDVO = eventApplicationService.getSupplierName(requestDVO);
		if(responseDVO.getM_abstractDVO().getObject() == null || ((String)responseDVO.getM_abstractDVO().getObject()).equalsIgnoreCase("") ){
			
			m_strSupplierNameDlg = null;
			
		} else
			m_strSupplierNameDlg = (String)responseDVO.getM_abstractDVO().getObject();
		
		if(m_strMode != null && !m_strMode.equalsIgnoreCase("") && m_strMode.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.ADD_MODE))
			populateDialogDropdowns(false, true, true, true, false);	
		
		if(m_strMode != null && !m_strMode.equalsIgnoreCase("") && m_strMode.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.UPDATE_MODE))
			populateDialogDropdowns(false, true, true, true, true);	
		
		if(null != m_strParentPage && !m_strParentPage.isEmpty() ){
			
			m_selectedApplicationDlg = m_selectedApplication;

			
		} else if(m_selectedApplication != null){
			

			requestDVO.setM_abstractDVO(getEventApplicationSearchFormObject());
			responseDVO = eventApplicationService.searchEventApplications(requestDVO);
			
			ListDVO outDVO = (ListDVO)responseDVO.getM_abstractDVO();
			m_eventApplicationListDlg = outDVO.getList();
			
			if(null != m_strParentPage && !m_strParentPage.isEmpty() ){
				m_selectedApplicationDlg = m_selectedApplication;
			} else if(m_eventApplicationListDlg != null &&  m_eventApplicationListDlg.size() > 0)
				m_selectedApplicationDlg = (EventApplicationDVO)m_eventApplicationListDlg.get(0);

			else
				m_selectedApplicationDlg = null;
			
		} else {
			m_eventApplicationListDlg = null;
			m_strTargetPlantDlg = null;
			m_strModelDlg = null;
			m_strMTCTypeDlg1 = null;
			m_strMTCOptionDlg = null;
			m_strCategoryDlg = null;
			m_strDesignSectionDlg = null;
			
		}
		//CPT-1655 start
		if (userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.NM_COST_STAFF.value) || 
				userInfoDVO.getM_strCurrentRole().equalsIgnoreCase(ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.MEX_NM_STAFF.value) ){
			m_bolEnableApplyDlg = false;
			m_bolEnableDeleteDlg = false;
			m_bolEnableOkDlg = false;
			m_bolEnableAddDlg = false;
			m_bolEnableRemoveDlg = false;
			m_bolEnablePartNumberDlg = false;
			m_bolEnablePartSearchDlg = false;
			m_bolEnableSupplierNumberDlg= false;
			m_bolEnableSupplierSearchDlg = false;
			m_bolEnablePartColorDlg = false;
			m_bolEnableAddDlg = false;
			m_bolEnableRemoveDlg = false;
			m_bolEnableTargetPlantDlg = false;
			m_bolEnableModelDlg = false;
			m_bolEnableMTCTypeDlg = false;
			m_bolEnableMTCOptionDlg = false;
			m_bolEnableCategoryDlg = false;
			m_bolEnablePartSectionDlg = false;
		}
		//CPT- 1655 end
		
		logger.debug("\n Exiting openMaintainEventApplicationDialog() ");
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("maintaineventapplication.xhtml");
	}
	
	
	
	public void getEventApplicationDetails(){
		
		logger.debug("\n Entering getEventApplicationDetails() ");
		//RequestDVO requestDVO = new RequestDVO();
		RequestDVO requestDVO = instantiateRequestDVO();
		m_strAction = ApplicationConstantsIF.APP_CONSTANTS.RETRIEVE_ACTION;
		
		// For Details operation
		if(m_strMode != null && !m_strMode.equalsIgnoreCase("") && m_strMode.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.UPDATE_MODE)){
					
			performValidations();
			
			if(validationMsg == null || validationMsg.equalsIgnoreCase("")){
				
					requestDVO.setM_abstractDVO(getEventApplicationDialogFormObject());
						
					try{
							
						eventApplicationService.updateEventApplication(requestDVO);
					} catch (ApplicationException e) {
							
							handleApplicationException(e);
							return;
					}
			}
		
			m_selectedApplicationDlg.setM_strTargetPlant(m_selectedApplicationDlg.getM_strTPlant());
			setEventApplicationDialogForm(m_selectedApplicationDlg);
			
			m_intQtyDlg = null;
			//m_intRateDlg = 100;
			m_bRateDlg = new BigDecimal(100);
			
			populateDialogDropdowns(false, true, true, true, true);	
		}
		
		//For Add Mode i.e. before an application is added. Once Add is successfully done, mode is changed to Update Mode
		if(m_strMode != null && !m_strMode.equalsIgnoreCase("") && m_strMode.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.ADD_MODE)){
		
			m_selectedApplicationDlg.setM_strTargetPlant(m_selectedApplicationDlg.getM_strTPlant());
			setEventApplicationDialogForm(m_selectedApplicationDlg);
			m_intQtyDlg = null;
			//m_intRateDlg = 100;
			m_bRateDlg = new BigDecimal(100);
			populateDialogDropdowns(false, true, true, true, false);	
		}
		
		logger.debug("\n Exiting getEventApplicationDetails() ");	
	}
	

	public void setApplicationParamsForFetch(boolean isAdd , EventPartDVO selectedEventPart , EventApplicationDVO selectedeventApp , 
			HashMap<String , Boolean> checkflags , String parentPage , List appList){
		m_selectedEventPart = selectedEventPart;
		m_selectedApplication = selectedeventApp;
		checkFlagMap = checkflags;
		m_strParentPage = parentPage;
		m_eventApplicationListDlg = appList;
		fetchEventApplicationDetails(isAdd);
	}
	
	public void fetchEventApplicationDetails(boolean isAdd){
		String plantParam = null;
		ResponseDVO responseDVO = new ResponseDVO();
		if(null != m_selectedEventPart && (isAdd || (null != m_selectedApplication && !isAdd))){
			//Quantity and Share Rate variable in 'Maintain Event Part' and 'Event Part Application' are diff. Inorder to display quantity and share rate 
			//on Event part application screen below loop is used.
			if(m_eventApplicationListDlg!=null){
				for(EventApplicationDVO eventAppDVO:(ArrayList<EventApplicationDVO>)m_eventApplicationListDlg){
					eventAppDVO.setM_intQuantity((eventAppDVO.getM_strQty()!=null && !eventAppDVO.getM_strQty().trim().isEmpty())?Integer.parseInt(eventAppDVO.getM_strQty()):null);
					eventAppDVO.setM_bRate((eventAppDVO.getM_strShareRate()!=null && !eventAppDVO.getM_strShareRate().trim().isEmpty())?new BigDecimal(eventAppDVO.getM_strShareRate()):null);
					eventAppDVO.setM_strIsSupply((eventAppDVO.getM_strSupply()!=null && !eventAppDVO.getM_strSupply().trim().isEmpty())?eventAppDVO.getM_strSupply():null);
				}
			}
			m_strCategory = "";
			m_strMTCType1 = null;
			m_strMTCOption = "";
			m_strRPTCurrency = m_selectedEventPart.getM_strRptCurr();
			m_procSection = m_selectedEventPart.getM_strProcGroup();
			m_strDesignSection = "";
			m_strFeatureCode = "";
			m_strModel = "";
			if(isAdd){
				if(null == m_selectedApplication)
					m_selectedApplication = new EventApplicationDVO();
				m_selectedApplication.setM_strEventName(m_selectedEventPart.getM_strEventName());
				m_selectedApplication.setM_strTargetPlant(m_selectedEventPart.getM_strPlantCode());
				m_selectedApplication.setM_intRevNo(m_selectedEventPart.getM_intRevNum());
				m_selectedApplication.setM_strPartColor(m_selectedEventPart.getM_strPartColor());
				m_selectedApplication.setM_strPartNumber(m_selectedEventPart.getM_strPartNumber());
				m_selectedApplication.setM_strProcSection(m_selectedEventPart.getM_strProcGroup());
				m_selectedApplication.setM_strSupplierNumber(m_selectedEventPart.getM_strSupplierNo());
			}
			if(null != m_selectedApplication ){
				m_strEventName =  m_selectedApplication.getM_strEventName();
				m_strTargetPlant = m_selectedApplication.getM_strTPlant();
				m_decEventRevisionNo = new BigDecimal(m_selectedApplication.getM_intRevNo());
				m_strPartColor = m_selectedApplication.getM_strPartColor();
				m_strPartNumber = m_selectedApplication.getM_strPartNumber();
				m_strSupplier = m_selectedApplication.getM_strSupplierNumber();
			}
			m_bolgbEntireEvt = (null == checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.ENTIRE_EVENT_FLAG) ? false : checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.ENTIRE_EVENT_FLAG));
			m_bolgbAllApp = (null == checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.ALL_APP_FLAG) ? false : checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.ALL_APP_FLAG)); 
			m_bolgbFeature = (null == checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.FEATURE_FLAG) ? false : checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.FEATURE_FLAG)); 
			
			if(m_strTargetPlant == null || m_strTargetPlant.equals(""))
				plantParam = "#";
			else
				plantParam = m_strTargetPlant;
			/*try {
				//****Commented as not required****
				responseDVO = eventApplicationService.getFieldLevelSecurityok(ApplicationConstantsIF.SUB_SYSTEM.EVENT_PROCESSING,
						ApplicationConstantsIF.FORM_NAME.EVENT_PART_APPLICATION_SEARCH, 
						ApplicationConstantsIF.USER_APP_CONSTANTS.USER_ROLES.ADMINISTRATOR.value,
						getM_userInfoDVO().getM_strUserLogonId(), ApplicationConstantsIF.APP_CONSTANTS.TGT_PLANT_LOC_CODE_COLUMN, null, plantParam);
				m_bolgbDataCheckOk = (Boolean)responseDVO.getM_abstractDVO().getObject();
				m_bolgbDataCheckOk = true;
			}*/ /*catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			m_bolgbDataCheckOk = true;
			try {
				if(isAdd)
					addNewEventApplication();
				else
					openEventApplicationDetails();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			addMessage(FacesMessage.SEVERITY_WARN, "Select an event application", null, "maintainEventPartForm:messages");
		}
	}
	
	public void getEventApplicationDetailsCopy(){
		
		logger.debug("\n Entering getEventApplicationDetailsCopy() ");
		RequestDVO requestDVO = new RequestDVO();			
		ResponseDVO responseDVO = null;
		
		m_selectedApplicationCopy.setM_strEventName(m_strEventNameCopy);
		m_selectedApplicationCopy.setM_decEventRevNo(m_decEventRevisionNoCopy);
		
		requestDVO.setM_abstractDVO(m_selectedApplicationCopy);
						
		try{
							
			responseDVO = eventApplicationService.getEventApplicationDetails(requestDVO);
			setFromToPortionCopyForm((EventApplicationDVO)responseDVO.getM_abstractDVO());
			
			m_intQtyFromCopy = null;
			m_intQtyToCopy = null;
			m_intShareFromCopy = null;
			//m_intShareToCopy = null;
			m_strShareToCopy = null;
			
		} catch (Exception e) {
							
			e.printStackTrace();
			addMessage(FacesMessage.SEVERITY_ERROR, "Unable to retrieve the selected record", null , "eventApplicationCopyForm:messages" );
			return;
		}
	
		populateFeatureGridsCopy(true, true);
		
		logger.debug("\n Exiting getEventApplicationDetailsCopy() ");	
	}
	
	public void addNewEventApplication() throws Exception{
		
		logger.debug("\n Entering addNewEventApplication() ");
		
		m_bolShowFeatures = false;
		m_strMode = ApplicationConstantsIF.APP_CONSTANTS.ADD_MODE;
		
		if(null == m_strEventName || m_strEventName.equalsIgnoreCase("")){
			addMessage(FacesMessage.SEVERITY_WARN, "You must select an Event Name and Revision Number", null, null);
			return;
		}else {
			openMaintainEventApplicationDialog();
		}
		populateCategoryOnModelSelection();
		logger.debug("\n Exiting addNewEventApplication() ");	
	}
	
	public void freezeCheck(EventApplicationDVO eventApplicationDVO){
		
		logger.debug("\n Entering freezeCheck() ");
		
		m_bolgbEntireEvt = false;		
		m_bolgbAllInv = false;		
		m_bolgbAllApp = false;	
		m_bolgbBaseCost = false;		
		m_bolgbComHamInv = false;	
		m_bolgbDrawCost = false;		
		m_bolgbExclHamInv = false;	
		m_bolgbExclSupInv = false;	
		m_bolgbFeature = false;		
		m_bolgbAllSupCost = false;		
		m_bolgbTgtCost = false;
		
		RequestDVO requestDVO = new RequestDVO();
		ResponseDVO responseDVO = null;
		HashMapDVO hashMapDVO = null;
		HashMap checkFlagMap = null;
		
		requestDVO.setM_abstractDVO(eventApplicationDVO);
		responseDVO = eventApplicationService.freezeCheck(requestDVO);
		
		hashMapDVO = (HashMapDVO)responseDVO.getM_abstractDVO();
		checkFlagMap = hashMapDVO.getM_hmpMap();
		
		if(checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.ENTIRE_EVENT_FLAG) != null)
			m_bolgbEntireEvt = (Boolean)checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.ENTIRE_EVENT_FLAG);
		
		if(checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.ALL_INV_FLAG) != null)
			m_bolgbAllInv = (Boolean)checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.ALL_INV_FLAG);	
		
		if(checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.ALL_APP_FLAG) != null)
			m_bolgbAllApp = (Boolean)checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.ALL_APP_FLAG);	
		
		if(checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.BASE_COST_FLAG) != null)
			m_bolgbBaseCost = (Boolean)checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.BASE_COST_FLAG);		
		
		if(checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.COM_HAM_INV_FLAG) != null)
			m_bolgbComHamInv = (Boolean)checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.COM_HAM_INV_FLAG);	
		
		if(checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.DRAW_COST_FLAG) != null)
			m_bolgbDrawCost = (Boolean)checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.DRAW_COST_FLAG);		
		
		if(checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.EXCL_HAM_INV_FLAG) != null)
			m_bolgbExclHamInv = (Boolean)checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.EXCL_HAM_INV_FLAG);	
		
		if(checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.EXCL_SUPL_INV_FLAG) != null)
			m_bolgbExclSupInv = (Boolean)checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.EXCL_SUPL_INV_FLAG);	
		
		if(checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.FEATURE_FLAG) != null)
			m_bolgbFeature = (Boolean)checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.FEATURE_FLAG);		
		
		if(checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.ALL_SUP_COST_FLAG) != null)
			m_bolgbAllSupCost = (Boolean)checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.ALL_SUP_COST_FLAG);		
		
		if(checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.TGT_COST_FLAG) != null)
			m_bolgbTgtCost = (Boolean)checkFlagMap.get(ApplicationConstantsIF.HMAP_KEY.TGT_COST_FLAG);
		
		logger.debug("\n Exiting freezeCheck() ");	
	}
	
	public void openPartLookup(Boolean preLoadedPartNumber){
		
		logger.debug("\n Entering openPartLookup() ");	

		lookupBean.clearLookupBean();
		
		lookupBean.setM_bolEventBased(true);
		lookupBean.setM_strEventName(m_strEventName);
		lookupBean.setM_decRevNo(m_decEventRevisionNo);
		
		if(preLoadedPartNumber && m_strPartNumber != null && !m_strPartNumber.equalsIgnoreCase("")){
			lookupBean.setM_strPartNo(m_strPartNumber);
		} else if(m_strPartNumber == null || m_strPartNumber.equalsIgnoreCase("")){
			lookupBean.setM_strPartNo("");
		}
		
		if(m_strPartName == null || m_strPartName.equalsIgnoreCase("")){
			lookupBean.setM_strPartName("");
		}
			
		lookupBean.setM_strBaseTable(ApplicationConstantsIF.APP_CONSTANTS.EVENT_APPLICATION_SEARCH_PART_SUPPLIER_SEARCH_BASE_TABLE);
		lookupBean.setM_strSrcQuery(EventProcessingSQLIF.LOOKUP_PART_EVENT_APPLICATION_SEARCH);
		lookupBean.setM_strPartNumberFieldId("searchEventApplicationForm:partNum");
		lookupBean.setM_partLookupList(new ArrayList<PartLookupDVO>());
		
		lookupBean.findParts();
		
		logger.debug("\n Exiting openPartLookup() ");	
	}

	public void openSupplierLookup(Boolean preLoadedSuppNumber){

		logger.debug("\n Entering openSuplierLookp() ");	
		
		lookupBean.clearLookupBean();
		
		lookupBean.setM_bolEventBased(true);
		lookupBean.setM_strEventName(m_strEventName);
		lookupBean.setM_decRevNo(m_decEventRevisionNo);
		
		if(preLoadedSuppNumber && m_strSupplier != null && !m_strSupplier.equalsIgnoreCase("")){
			lookupBean.setM_strSupplierNo(m_strSupplier);
		}else if(m_strSupplier == null || m_strSupplier.equalsIgnoreCase("")){
			lookupBean.setM_strSupplierNo("");
		}
		
		if(m_strSupplierName == null || m_strSupplierName.equalsIgnoreCase("")){
			lookupBean.setM_strSupplierName("");
		}
		
		lookupBean.setM_strBaseTable(ApplicationConstantsIF.APP_CONSTANTS.EVENT_APPLICATION_SEARCH_PART_SUPPLIER_SEARCH_BASE_TABLE);
		lookupBean.setM_strSrcQuery(EventProcessingSQLIF.LOOKUP_SUPPLIER_EVENT_APPLICATION_SEARCH);
		lookupBean.setM_strSupplierNumberFieldId("searchEventApplicationForm:suppNo");
		lookupBean.setM_strSupplierNameFieldId("searchEventApplicationForm:suppName");
		lookupBean.setM_supplierLookupList(new ArrayList<SupplierLookupDVO>());
		lookupBean.findSuppliers();
		logger.debug("\n Exiting openSuplierLookp() ");	
	}
	
	public void openPartLookupDlg(Boolean preLoadedPartNumber){
		
		logger.debug("\n Entering openPartLookupDlg() ");

		lookupBean.clearLookupBean();
	
		if(preLoadedPartNumber && m_strPartNumberDlg != null && !m_strPartNumberDlg.equalsIgnoreCase("")){
			lookupBean.setM_strPartNo(m_strPartNumberDlg);
		} else if(m_strPartNumberDlg == null || m_strPartNumberDlg.equalsIgnoreCase("")){
			lookupBean.setM_strPartNo("");
		}
		
		if(m_strPartNameDlg == null || m_strPartNameDlg.equalsIgnoreCase("")){
			lookupBean.setM_strPartName("");
		} else if(m_strPartNameDlg != null && !m_strPartNameDlg.equalsIgnoreCase("")){
			lookupBean.setM_strPartName(m_strPartNameDlg);
		}
		lookupBean.setM_bolEventBased(false);
		lookupBean.setM_strSrcQuery(EventProcessingSQLIF.PART_LOOKUP_EVENT_APPLICATION_DIALOG);
		lookupBean.setM_strPartNumberFieldId("eventApplicationDialogForm:partNumDlg");
		lookupBean.setM_strPartNameFieldId("eventApplicationDialogForm:partNameDlg");
		lookupBean.setM_partLookupList(new ArrayList<PartLookupDVO>());
		
		lookupBean.findParts();
		logger.debug("\n Exiting openPartLookupDlg() ");	
	}

	public void openSupplierLookupDlg(Boolean preLoadedSuppNumber){

		logger.debug("\n Entering openSupplierLookupDlg() ");	
		
		lookupBean.clearLookupBean();
		
		if(preLoadedSuppNumber && m_strSupplierDlg != null && !m_strSupplierDlg.equalsIgnoreCase("")){
			lookupBean.setM_strSupplierNo(m_strSupplierDlg);
		}else if(m_strSupplierDlg == null || m_strSupplierDlg.equalsIgnoreCase("")){
			lookupBean.setM_strSupplierNo("");
		}
			
		if(m_strSupplierNameDlg == null || m_strSupplierNameDlg.equalsIgnoreCase("")){
			lookupBean.setM_strSupplierName("");
		}else if(m_strSupplierNameDlg != null && !m_strSupplierNameDlg.equalsIgnoreCase("")){
			lookupBean.setM_strSupplierName(m_strSupplierNameDlg);
		}
		
		lookupBean.setM_bolEventBased(false);
		lookupBean.setM_strSrcQuery(EventProcessingSQLIF.SUPPLIER_LOOKUP_EVENT_APPLICATION_DIALOG);
		lookupBean.setM_strSupplierNumberFieldId("eventApplicationDialogForm:suppNoDlg");
		lookupBean.setM_strSupplierNameFieldId("eventApplicationDialogForm:suppNameDlg");
		lookupBean.setM_supplierLookupList(new ArrayList<SupplierLookupDVO>());
		
		lookupBean.findSuppliers();
		logger.debug("\n Exiting openSupplierLookupDlg() ");	
	}
	
	public void onSupplierNoChange(){
		try {
			if(null == m_strSupplierDlg || m_strSupplierDlg.trim().isEmpty()){
				m_strSupplierNameDlg = "";
			} else {
				m_strSupplierNameDlg = eventApplicationService.getSupplier(m_strSupplierDlg.trim());
			}
		} catch (ApplicationException e) {
			handleApplicationException(e);
		}
	}
	
	public void onSupplierNumChange(){
		try {
			if(null == m_strSupplier || m_strSupplier.trim().isEmpty()){
				m_strSupplierName = "";
			} else {
				m_strSupplierName = eventApplicationService.getSupplier(m_strSupplier.trim());
			}
		} catch (ApplicationException e) {
			handleApplicationException(e);
		}
	}
	
	public void onPartNoChange(){
		try {
			if(m_strPartNumberDlg != null && !m_strPartNumberDlg.equalsIgnoreCase("")){
//				lookupBean.setM_strPartNo(m_strPartNumberDlg);
				m_strPartNameDlg = eventApplicationService.getEventNewPart(m_strPartNumberDlg.trim());
			} else if(m_strPartNumberDlg == null || m_strPartNumberDlg.equalsIgnoreCase("")){
				//lookupBean.setM_strPartName("");
				m_strPartNameDlg = "";
			}
			
		} catch (ApplicationException e) {
			handleApplicationException(e);
		}
	}
	
	
	public void onModelChangeDlg(){
		
		logger.debug("\n Entering onModelChangeDlg() ");
		
		if(m_strModelDlg != null && m_strModelDlg.equalsIgnoreCase(""))
			m_strModelDlg = null;
				
		populateDialogDropdowns(true, false, true, true, false);
		populateCategoryOnModelSelection();
		m_strMTCTypeDlg1 = null;		
		m_strMTCOptionDlg = null;
		
		logger.debug("\n Exiting onModelChangeDlg() ");	
	}
	
	public void onTargetPlantChangeDlg(){
		
		logger.debug("\n Entering onTargetPlantChangeDlg() ");
		
		if(m_strTargetPlantDlg != null && m_strTargetPlantDlg.equalsIgnoreCase(""))
			m_strTargetPlantDlg = null;		
		
		populateDialogDropdowns(true, true, true, true, false);	
	
		m_strModelDlg = null;
		m_strMTCTypeDlg1 = null;		
		m_strMTCOptionDlg = null;
		
		logger.debug("\n Exiting onTargetPlantChangeDlg() ");	
	}
	
	public void onMTCTypeChangeDlg(){
		
		logger.debug("\n Entering onMTCTypeChangeDlg() ");
		
		if(m_strMTCTypeDlg1 != null && m_strMTCTypeDlg1.size()==0)
			m_strMTCTypeDlg1 = null;
				
		populateDialogDropdowns(true, false, false, true, false);
		
		m_strMTCOptionDlg = null;
	
		logger.debug("\n Exiting onMTCTypeChangeDlg() ");	
	}
	
	public void addAvailableFeature(){
		
		logger.debug("\n Entering addAvailableFeature() ");

		RequestDVO requestDVO = instantiateRequestDVO();
		ResponseDVO responseDVO = null;
		
		requestDVO.setM_abstractDVO(getEventApplicationDialogFormObject());
		responseDVO = eventApplicationService.addAvailableFeature(requestDVO);
		
		populateFeatureGrids();
		
		logger.debug("\n Exiting addAvailableFeature() ");
	}
	
	public void removeAvailableFeature(){
		
		logger.debug("\n Entering removeAvailableFeature() ");
		
		RequestDVO requestDVO = new RequestDVO();
		ResponseDVO responseDVO = null;
		
		requestDVO.setM_abstractDVO(getEventApplicationDialogFormObject());
		responseDVO = eventApplicationService.removeAvailableFeature(requestDVO);
		
		 populateFeatureGrids();
		
		logger.debug("\n Exiting removeAvailableFeature() ");		
	}
	/**
	 * This method will call from Event Application details screen when clicking on apply or OK 
	 * To validate event is attached to budget event or not
	 * @throws IOException 
	 * */
	public void validateActualEventIsAttachedToBudgetForDetails() throws IOException{ 
		
		logger.debug("\n Entering validateActualEventIsAttachedToBudgetForDetails() ");
		EventApplicationDVO eventApplicationDVO = null;
		
			RequestDVO requestDVO = instantiateRequestDVO();

			eventApplicationDVO = getEventApplicationSearchFormObject();
			requestDVO.setM_abstractDVO(eventApplicationDVO);
			//validate the event is actual event and if actual then it is attached to any budget event or not
			String eventType = eventApplicationService.validateEventIsActualOrBudget(requestDVO);
			if(eventType!=null && "MPCE".equalsIgnoreCase(eventType)){
				//TODO- Changes to be moved after UC 9 is moved 
				//Un-comment this code once 'Load Budget Event' use case is moved to production
				
				
				//Remove below function call after above code is uncommented
				applyEventApplicationForDetails();
			}else if(eventType!=null && "BUCE".equalsIgnoreCase(eventType)){
				//System shall calculate the Impact amount for budget event and should consider Cross Referenced MTO.
				//1.	It should be consider only when BOM maintenance to done for budget event or for attached budget event as well?
				//2.	And how it should take place in business logic and where to reflect this calcutaion changes?
				//COMPUTE FCBPV-PART-PROD-VEH-QTY = (FCEPA-PART-QTY  *     
                //FCBPU-MONTH-PLAN-QTY)  *     
                //(FCEPA-SHARE-RATE-PERCENT / 100) 
				//COMPUTE FCBPV-FISCAL-VEH-QTY    = (FCEPA-PART-QTY    *   
                //       (Total of Month-plan-qty for 12 months ï¿½ month plan qty) *   
                // (FCEPA-SHARE-RATE-PERCENT / 100)
				applyEventApplicationForDetailsDirctlyToBudget();
			}else{
				applyEventApplication();
			}
			if(m_strAction!=null && m_strAction.equalsIgnoreCase("OK_ACTION")){
				//FacesContext.getCurrentInstance().getExternalContext().redirect("searcheventapplications.xhtml");
				cancelEventApplication("");
			}else{
				addMessage(FacesMessage.SEVERITY_INFO, "Record Updated successfully", null, null);
			}
		logger.debug("\n Exiting validateActualEventIsAttachedToBudget() ");
	}
	
	/***
	 * This method will apply changes to Actual Event Parts as well as Budget Event Parts 
	 * 
	 ***/
	public void updateEventApplicationToActAndBudForDetails(){ 
		
		logger.debug("\n Entering applyEventApplicationToActAndBud() ");
		try {
			RequestDVO requestDVO = instantiateRequestDVO();
			ResponseDVO responseDVO = null;
			
			requestDVO.setM_abstractDVO(getEventApplicationDialogFormObject());
			
			eventApplicationService.updateEventPartDetailsApplicationToAttachedBudget(requestDVO);
			updateEventApplication();
			
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("\n Exiting applyEventApplicationToActAndBud() ");
	}
	
	public void updateEventApplication() throws ApplicationException, IOException{
		
		logger.debug("\n Entering updateEventApplication() ");
			
		//RequestDVO requestDVO = new RequestDVO();
		RequestDVO requestDVO = instantiateRequestDVO();
		ResponseDVO responseDVO = null;
		
		requestDVO.setM_abstractDVO(getEventApplicationDialogFormObject());
		responseDVO = eventApplicationService.updateEventApplication(requestDVO);
		
		if(responseDVO != null && responseDVO.getM_abstractDVO().getObject() != null){
			
			String returnMsg = (String)responseDVO.getM_abstractDVO().getObject();
			
			if(returnMsg.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS)){
				m_bOldValueFlag = true;
				m_strTargetPlantDlg = null;
				setOldValues();
				m_bOldValueFlag = false;
				addMessage(FacesMessage.SEVERITY_INFO, "Record Updated successfully", null, null);
			}
			else{
				m_bOldValueFlag = true;
				m_strOldShipToCode = null;
				m_strOldDesignSectionDlg = null;
				m_strOldTargetPlantDlg = null;
				m_strOldModelDlg = null;
				m_strOldMTCTypeDlg1 = null;
				m_strOldMTCOptionDlg = null;
				m_strOldCategoryDlg = null;
				addMessage(FacesMessage.SEVERITY_ERROR, "Unable to update record for following reason : \n"+returnMsg, null, null);
			}
				
				 
		}
			
		
		if(null != m_selectedApplicationDlg){
			
			requestDVO.setM_abstractDVO(getEventApplicationSearchFormObject());
			responseDVO = eventApplicationService.searchEventApplications(requestDVO);
			ListDVO outDVO = (ListDVO)responseDVO.getM_abstractDVO();
			m_eventApplicationListDlg = outDVO.getList();
			
		}
		
		if(m_eventApplicationListDlg != null &&  m_eventApplicationListDlg.size() > 0)
			m_selectedApplicationDlg = (EventApplicationDVO)m_eventApplicationListDlg.get(0);
		
		if(m_strAction != null && m_strAction.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.APPLY_ACTION)){
				populateFeatureGrids();
		
				requestDVO.setM_abstractDVO(getEventApplicationSearchFormObject());
				responseDVO = eventApplicationService.searchEventApplications(requestDVO);
				
				ListDVO outDVO = (ListDVO)responseDVO.getM_abstractDVO();
				m_eventApplicationListDlg = outDVO.getList();
				
				if(null != m_strParentPage && !m_strParentPage.isEmpty() ){
					m_selectedApplicationDlg = m_selectedApplication;
				} else if(m_eventApplicationListDlg != null &&  m_eventApplicationListDlg.size() > 0)
					m_selectedApplicationDlg = (EventApplicationDVO)m_eventApplicationListDlg.get(0);
		
				else
					m_selectedApplicationDlg = null;
		} else {
			
			if(null != m_strParentPage && m_strParentPage.equalsIgnoreCase("maintainEventPart")){
				clearManagedBeansFromSession("eventPartBackingBean");
				EventPartBackingBean eventPartBean = (EventPartBackingBean) getSessionObject("eventPartBackingBean");
				eventPartBean.fetchEventPartDetails(false);
			} else {
				refreshSearchGrid(null);
				FacesContext.getCurrentInstance().getExternalContext().redirect("searcheventapplications.xhtml");
			}
		}
	
		logger.debug("\n Exiting updateEventApplication() ");
	}
	
	public void addEventApplication() throws Exception{
		
		logger.debug("\n Entering addEventApplication() ");
			
		//RequestDVO requestDVO = new RequestDVO();
		RequestDVO requestDVO = instantiateRequestDVO();
		ResponseDVO responseDVO = null;
		
		requestDVO.setM_abstractDVO(getEventApplicationDialogFormObject());
			
		responseDVO = eventApplicationService.addEventApplication(requestDVO);

		if(responseDVO != null && responseDVO.getM_abstractDVO().getObject() != null){
			
			String returnMsg = (String)responseDVO.getM_abstractDVO().getObject();
			
			if(returnMsg.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS)){
				addMessage(FacesMessage.SEVERITY_INFO, "Record Added successfully", null, null);
				m_bolShowFeatures = true;
				//requestDVO.setM_abstractDVO(getEventApplicationSearchFormObject());
				responseDVO = eventApplicationService.searchEventApplications(requestDVO);
				ListDVO outDVO = (ListDVO)responseDVO.getM_abstractDVO();
				m_eventApplicationListDlg = outDVO.getList();
				m_eventApplicationList = m_eventApplicationListDlg;
				m_strMode = ApplicationConstantsIF.APP_CONSTANTS.UPDATE_MODE;
			}
			else{
				m_bToggleFlag = true;
				m_bolShowFeatures = false;
				addMessage(FacesMessage.SEVERITY_ERROR, "Not able to add record for following reason : \n"+returnMsg, null, null);
			}
		}
		if(!m_bToggleFlag){
			m_bolShowFeatures = true;
		}
		
		//m_strMode = ApplicationConstantsIF.APP_CONSTANTS.UPDATE_MODE;
			
		if(null != m_selectedApplicationDlg){
			
			requestDVO.setM_abstractDVO(getEventApplicationSearchFormObject());
			responseDVO = eventApplicationService.searchEventApplications(requestDVO);
			ListDVO outDVO = (ListDVO)responseDVO.getM_abstractDVO();
			m_eventApplicationListDlg = outDVO.getList();
			
		}
		
		if(m_eventApplicationListDlg != null &&  m_eventApplicationListDlg.size() > 0)
			m_selectedApplicationDlg = (EventApplicationDVO)m_eventApplicationListDlg.get(0);
		
		if(m_strAction != null && m_strAction.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.APPLY_ACTION)){
			populateFeatureGrids();
			//FacesContext.getCurrentInstance().getExternalContext().redirect("maintaineventapplication.xhtml");
		}else {
			if(null != m_strParentPage && m_strParentPage.equalsIgnoreCase("maintainEventPart")){
				clearManagedBeansFromSession("eventPartBackingBean");
				EventPartBackingBean eventPartBean = (EventPartBackingBean) getSessionObject("eventPartBackingBean");
				eventPartBean.fetchEventPartDetails(false);
			} else {
				refreshSearchGrid(null);
				if(m_eventApplicationListDlg != null && !m_eventApplicationListDlg.isEmpty()&&
						m_eventApplicationList!=null && m_eventApplicationList.isEmpty()){
					
					m_eventApplicationList.addAll(m_eventApplicationListDlg);
				}
				FacesContext.getCurrentInstance().getExternalContext().redirect("searcheventapplications.xhtml");
			}
		}
	
		logger.debug("\n Exiting addEventApplication() ");
	}
	
	
	public void deleteEventApplication()throws IOException{
		
		logger.debug("\n Entering deleteEventApplication() ");
		//RequestDVO requestDVO = new RequestDVO();
		RequestDVO requestDVO = instantiateRequestDVO(); 
		ResponseDVO responseDVO = null;
		
		requestDVO.setM_abstractDVO(getEventApplicationDialogFormObject());
		
		responseDVO = eventApplicationService.deleteEventApplication(requestDVO);
		
		
		if(responseDVO != null && responseDVO.getM_abstractDVO().getObject() != null){
			
			String returnMsg = (String)responseDVO.getM_abstractDVO().getObject();
			
			if(returnMsg.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS)){
				addMessage(FacesMessage.SEVERITY_INFO, "Record Deleted successfully", null, null);
				refreshSearchGrid(ApplicationConstantsIF.APP_CONSTANTS.DELETE_ACTION);
				FacesContext.getCurrentInstance().getExternalContext().redirect("searcheventapplications.xhtml");
			}
			else{
				addMessage(FacesMessage.SEVERITY_ERROR, "Not able to delete record for following reason : \n"+returnMsg, null, null);
			}
		}
		
		
		if(null != m_strParentPage && m_strParentPage.equalsIgnoreCase("maintainEventPart")){
			clearManagedBeansFromSession("eventPartBackingBean");
			EventPartBackingBean eventPartBean = (EventPartBackingBean) getSessionObject("eventPartBackingBean");
			eventPartBean.fetchEventPartDetails(false);
		}
		logger.debug("\n Exiting deleteEventApplication() ");
	}
	
	public String cancelSearchEventApplication() throws IOException{
		
		logger.debug("\n Entering cancelSearchEventApplication() ");
		
		m_bolCopyEnabled = false;
		m_bolIsGridEmpty = true;
		
		logger.debug("\n Exiting cancelSearchEventApplication() ");	
		return "index?faces-redirect=true";
	}
	
	public void cancelEventApplication(String breadcrumLink) throws IOException{
		
		logger.debug("\n Entering cancelEventApplication() ");
		//if(null != m_strParentPage && !m_strParentPage.isEmpty()){
		if(null != breadcrumLink && !breadcrumLink.isEmpty()){
			clearManagedBeansFromSession("eventPartBackingBean");
			EventPartBackingBean eventPartBean = (EventPartBackingBean) getSessionObject("eventPartBackingBean");
			if(breadcrumLink.equalsIgnoreCase("MAINTAIN_EVENT_PART")){
				eventPartBean.fetchEventPartDetails(false);
			} else if(breadcrumLink.equalsIgnoreCase("SEARCH_EVENT_PART")){
				eventPartBean.findEventPartList(false);
				eventPartBean.setM_addUpdateEventPart(null);
				FacesContext.getCurrentInstance().getExternalContext().redirect("searchEventParts.xhtml");
			}
		} /*else {
			refreshSearchGrid(ApplicationConstantsIF.APP_CONSTANTS.CANCEL_ACTION);
			FacesContext.getCurrentInstance().getExternalContext().redirect("searcheventapplications.xhtml");
		}*/
		else if(null != m_strParentPage && !m_strParentPage.isEmpty()){
			clearManagedBeansFromSession("eventPartBackingBean");
			EventPartBackingBean eventPartBean = (EventPartBackingBean) getSessionObject("eventPartBackingBean");
			eventPartBean.fetchEventPartDetails(false);
		} else {
			refreshSearchGrid(ApplicationConstantsIF.APP_CONSTANTS.CANCEL_ACTION);
			if(m_CheckedEventApplication!=null&&m_eventApplicationList!=null&&!m_eventApplicationList.isEmpty()&&!m_CheckedEventApplication.isEmpty()){
				for(EventApplicationDVO eventApplicationDVO:m_eventApplicationList){
					for(EventApplicationDVO eventApplicationDVOChecked:m_CheckedEventApplication){
						if(eventApplicationDVO.equals(eventApplicationDVOChecked)){
							eventApplicationDVO.setM_bSelected(eventApplicationDVOChecked.isM_bSelected());
						}
					}
				}
			}
			FacesContext.getCurrentInstance().getExternalContext().redirect("searcheventapplications.xhtml");
		}
		
		//m_bolApplyEnabled = true;
		
		logger.debug("\n Exiting cancelEventApplication() ");
	}
	
	private void refreshSearchGrid(String operation) {

		logger.debug("\n Entering refreshSearchGrid() ");
		
		if(  ((operation != null && operation.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.SEARCH_ACTION))) ||
			 ((m_eventApplicationList != null && m_eventApplicationList.size() != 0 )) ) {
			
				//RequestDVO requestDVO = new RequestDVO();
				RequestDVO requestDVO = instantiateRequestDVO();
				ResponseDVO responseDVO = null;
				
				requestDVO.setM_abstractDVO(getEventApplicationSearchFormObject());
				responseDVO = eventApplicationService.searchEventApplications(requestDVO);
				
				ListDVO outDVO = (ListDVO)responseDVO.getM_abstractDVO();
				m_eventApplicationList = outDVO.getList();
				
				//m_bolApplyEnabled = false;
				
				if(m_eventApplicationList != null && m_eventApplicationList.size() > 0){
				
					m_selectedApplication = (EventApplicationDVO)m_eventApplicationList.get(0);
					m_bolIsGridEmpty = false;
				}
				else {
					
					m_selectedApplication = null;
					m_bolIsGridEmpty = true;
				}
		}
		
		if(  ((operation != null && operation.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.DELETE_ACTION))) ||
				 ((m_eventApplicationListDlg != null && m_eventApplicationListDlg.size() != 0 )) ) {
				
					//RequestDVO requestDVO = new RequestDVO();
					RequestDVO requestDVO = instantiateRequestDVO();
					ResponseDVO responseDVO = null;
					
					requestDVO.setM_abstractDVO(getEventApplicationSearchFormObject());
					responseDVO = eventApplicationService.searchEventApplications(requestDVO);
					
					ListDVO outDVO = (ListDVO)responseDVO.getM_abstractDVO();
					m_eventApplicationListDlg = outDVO.getList();
					
					//m_bolApplyEnabled = false;
					
					if(m_eventApplicationListDlg != null && m_eventApplicationListDlg.size() > 0){
					
						m_selectedApplicationDlg = (EventApplicationDVO)m_eventApplicationListDlg.get(0);
						m_bolIsGridEmpty = false;
					}
					else {
						
						m_selectedApplication = null;
						m_bolIsGridEmpty = true;
					}
			}
		
		
		logger.debug("\n Exiting refreshSearchGrid() ");
	}

	public void performValidations(){
		
		logger.debug("\n Entering performValidations() ");
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String action = params.get("action");
		
		if(action != null && !action.equalsIgnoreCase(""))
			m_strAction = action;
		
		//action param is available only when OK or Apply is clicked and NOT when grid row is selected
		
		validateEventApplicationDialogForm();
		
		if(validationMsg == null && focusField == null){
			
			if(m_bolgbEntireEvt || m_bolgbAllApp)
				validationMsg = "Application Freezed";
			else if (!m_bolgbDataCheckOk)
				validationMsg = "Update Access Denied";
		}
		
		if(validationMsg == null || validationMsg.equalsIgnoreCase(""))
			setOldValues();
		
		logger.debug("\n Exiting performValidations() ");
	}
	
	public void validationTest (){
		
		if(m_strPartNumberDlg == null || m_strPartNumberDlg.equalsIgnoreCase("")){
			addMessage(FacesMessage.SEVERITY_WARN, "Part Number is required" , null, null);
		}
	}
	
	public void validateEventApplicationDialogForm(){
		
		logger.debug("\n Entering validateEventApplicationDialogForm() ");
		
		RequestDVO requestDVO = new RequestDVO();
		ResponseDVO responseDVO = null;
		String validPartName = null;
		String validSupplierName = null;
		Boolean validPartSupplier = null;
		
		validationMsg = null;
		focusField = null;
		
		EventApplicationDVO inDVO = getEventApplicationDialogFormObject();
		requestDVO.setM_abstractDVO(inDVO);
		//Commented below line as it will be moved with Purchase Changes.
		/*if(!"UPDATE_MODE".equalsIgnoreCase(m_strMode)){
			if(m_strIsSupply == null || "".equalsIgnoreCase(m_strIsSupply)
					|| (!"Y".equalsIgnoreCase(m_strIsSupply.toUpperCase()) && !"N".equalsIgnoreCase(m_strIsSupply.toUpperCase()))){
				
				validationMsg = "IsSupply is required. Please enter Y or N";
				focusField = "eventApplicationDialogForm:isSupply";
				
				addMessage(FacesMessage.SEVERITY_WARN, "IsSupply is required. Please enter Y or N" , null, "eventApplicationDialogForm:messages");
				return;
			}
		}*/
		if(m_strPartNumberDlg == null || m_strPartNumberDlg.equalsIgnoreCase("")){
			
			validationMsg = "Part Number is required";
			focusField = "eventApplicationDialogForm:partNumDlg";
			
			addMessage(FacesMessage.SEVERITY_WARN, "Part Number is required" , null, "eventApplicationDialogForm:messages");
			return;
		}
		
		responseDVO = eventApplicationService.getPartName(requestDVO);
		ListDVO outDVO = (ListDVO)responseDVO.getM_abstractDVO();
		
		validPartName = (outDVO.getList() == null || outDVO.getList().size() == 0) ? null : (String)((ListDVO)responseDVO.getM_abstractDVO()).getList().get(0) ;
		
		if(validPartName == null || validPartName.equalsIgnoreCase("")){
			
			responseDVO = eventApplicationService.getNewPartName(requestDVO);
			outDVO = (ListDVO)responseDVO.getM_abstractDVO();
			
			validPartName = (outDVO.getList() == null || outDVO.getList().size() == 0) ? null : (String)((ListDVO)responseDVO.getM_abstractDVO()).getList().get(0) ;
		}
			
		
		if(validPartName == null || validPartName.equalsIgnoreCase("")){
			
			validationMsg = "Part Number is invalid";
			focusField = "eventApplicationDialogForm:partNumDlg";
			addMessage(FacesMessage.SEVERITY_WARN, "Part Number is invalid" , null, "eventApplicationDialogForm:messages");
			return;
		}
		
		if( (m_strPartColorDlg != null && m_strPartColorDlg.equalsIgnoreCase(""))  &&
		    !m_hmpPartColorDlg.containsValue(m_strPartColorDlg) ){

			validationMsg = "Part Color Code is invalid";
			focusField = "eventApplicationDialogForm:partColorDlg";
			addMessage(FacesMessage.SEVERITY_WARN, "Part Color Code is invalid" , null, "eventApplicationDialogForm:messages");
			return;
		}
		
		if(m_strSupplierDlg == null || m_strSupplierDlg.equalsIgnoreCase("")){
			
			validationMsg = "Supplier Number is required";
			focusField = "eventApplicationDialogForm:suppNoDlg";
			addMessage(FacesMessage.SEVERITY_WARN, "Supplier Number is required" , null, "eventApplicationDialogForm:messages");
			return;		
		}
		
		responseDVO = eventApplicationService.getSupplierName(requestDVO);
		validSupplierName = (responseDVO.getM_abstractDVO() == null ) ? null : (String)responseDVO.getM_abstractDVO().getObject();

		if(validSupplierName == null || validSupplierName.equalsIgnoreCase("")){
			
			validationMsg = "Supplier Number is invalid";
			focusField = "eventApplicationDialogForm:suppNoDlg"; 
			addMessage(FacesMessage.SEVERITY_WARN, "Supplier Number is invalid" , null, "eventApplicationDialogForm:messages");
			return;
		}
			
		responseDVO = eventApplicationService.checkPartSupplierValidation(requestDVO);
		validPartSupplier = (responseDVO.getM_abstractDVO().getObject() == null) ? null : (Boolean)responseDVO.getM_abstractDVO().getObject();
		
		if(validPartSupplier == null || !validPartSupplier){

			validationMsg = "Part/Supplier does not exist on this event";
			focusField = "eventApplicationDialogForm:partNumDlg"; 
			addMessage(FacesMessage.SEVERITY_WARN, "Part/Supplier does not exist on this event" , null, "eventApplicationDialogForm:messages");
			return;
		}
		
		if(m_strDesignSectionDlg == null || m_strDesignSectionDlg.equalsIgnoreCase("")){
			
			validationMsg = "Design Section is required";
			focusField = "eventApplicationDialogForm:designSectionDlg_input"; 
			addMessage(FacesMessage.SEVERITY_WARN, "Design Section is required" , null, "eventApplicationDialogForm:messages");
			return;
		}
		
		if( (m_strDesignSectionDlg != null && m_strDesignSectionDlg.equalsIgnoreCase(""))  &&
			    !m_hmpDesignSectionDlg.containsValue(m_strDesignSectionDlg) ){
				
			validationMsg = "Design Section is invalid";
			focusField = "eventApplicationDialogForm:designSectionDlg_input";
			addMessage(FacesMessage.SEVERITY_WARN, "Design Section is invalid" , null, "eventApplicationDialogForm:messages");
			return;
		}
		
		if(m_intQtyDlg == null){
			
			validationMsg = "Quantity is required";
			focusField = "eventApplicationDialogForm:qtyDlg"; 
			addMessage(FacesMessage.SEVERITY_WARN, "Quantity is required" , null, "eventApplicationDialogForm:messages");
			return;
		}
		
		if(!StringUtils.isNumeric(m_intQtyDlg.toString())){
			
			validationMsg = "Quantity is invalid";
			focusField = "eventApplicationDialogForm:qtyDlg";
			addMessage(FacesMessage.SEVERITY_WARN, "Quantity is invalid" , null, "eventApplicationDialogForm:messages");
			return;
		}
		
		//if(m_intRateDlg == null){
		if(m_bRateDlg == null){
			
			validationMsg = "Share % is required";
			focusField = "eventApplicationDialogForm:shareDlg"; 
			addMessage(FacesMessage.SEVERITY_WARN, "Share % is required" , null, "eventApplicationDialogForm:messages");
			return;
		}
		/*condition not prevailing
		if(!StringUtils.isNumeric(m_bRateDlg.toString())){
			
			validationMsg = "Share % is invalid";
			focusField = "eventApplicationDialogForm:shareDlg";
			addMessage(FacesMessage.SEVERITY_WARN, "Share % is invalid" , null, "eventApplicationDialogForm:messages");
			return;
		}*/
		
		if(m_strTargetPlantDlg == null || m_strTargetPlantDlg.equalsIgnoreCase("")){
			
			validationMsg = "Enter Target Plant";
			focusField = "eventApplicationDialogForm:targetPlantDlg_input";
			addMessage(FacesMessage.SEVERITY_WARN, "Enter Target Plant" , null, "eventApplicationDialogForm:messages");
			return;
		} /*else if(m_strTargetPlantDlg.equalsIgnoreCase("D")){
			validationMsg = "Enter Target Plant";
			focusField = "eventApplicationDialogForm:targetPlantDlg_input";
			addMessage(FacesMessage.SEVERITY_WARN, "Enter Target Plant" , null, "eventApplicationDialogForm:messages");
			return;
		}*/
		
		logger.debug("\n Exiting validateEventApplicationDialogForm() ");
	}
	
	public void setOldValues(){
		
		logger.debug("\n Entering setOldValues() ");
		
		if(m_bOldValueFlag){
			m_strOldShipToCode = m_strShipToCode;
			m_strOldDesignSectionDlg = m_strDesignSectionDlg;
			m_strOldTargetPlantDlg = m_strTargetPlantDlg;
			m_strOldModelDlg = m_strModelDlg;
			m_strOldMTCTypeDlg1 = m_strMTCTypeDlg1;
			m_strOldMTCOptionDlg = m_strMTCOptionDlg;
			m_strOldCategoryDlg = m_strCategoryDlg;
		}
			
		
		logger.debug("\n Exiting setOldValues() ");
		
	}
	
	public void onTargetPlantChangeToCopy(){
		
		logger.debug("\n Entering onTargetPlantChangeToCopy() ");
		
		if(m_strTargetPlantToCopy != null && m_strTargetPlantToCopy.equalsIgnoreCase(""))
			m_strTargetPlantToCopy = null;		
		
		populateToDependentDropdowns(true, true, true);
	
		m_strModelToCopy = null;
		m_strMTCTypeToCopy1 = null;		
		m_strMTCOptionToCopy = null;
		
		logger.debug("\n Exiting onTargetPlantChangeToCopy() ");	
	}
	
	public void onModelChangeToCopy(){
		
		logger.debug("\n Entering onModelChangeToCopy() ");
		
		if(m_strModelToCopy != null && m_strModelToCopy.equalsIgnoreCase(""))
			m_strModelToCopy = null;
				
		populateToDependentDropdowns(false, true, true);
	
		m_strMTCTypeToCopy1 = null;		
		m_strMTCOptionToCopy = null;
		
		logger.debug("\n Exiting onModelChangeToCopy() ");	
	}
	
	public void onMTCTypeChangeToCopy(){
		
		logger.debug("\n Entering onMTCTypeChangeToCopy() ");
		
		if(m_strMTCTypeToCopy1 != null && m_strMTCTypeToCopy1.size()==0)
			m_strMTCTypeToCopy1 = null;
				
		populateToDependentDropdowns(false, false, true);
		
		m_strMTCOptionToCopy = null;
	
		logger.debug("\n Exiting onMTCTypeChangeToCopy() ");	
	}
	
	public void performValidationsCopy(){
		
		logger.debug("\n Entering performValidationsCopy() ");
		
		RequestDVO requestDVO = new RequestDVO();
		ResponseDVO responseDVO = null;
		validationMsg = null;
		focusField = null;
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String action = params.get("action");
		
		if(action != null && !action.equalsIgnoreCase(""))
			m_strAction = action;
		
		requestDVO.setM_abstractDVO(getEventApplicationCopyFormObject());
		responseDVO = eventApplicationService.validatePlantMTOColor(requestDVO);
		
		if(m_strDesignSectionToCopy == null || m_strDesignSectionToCopy.equalsIgnoreCase("")){
			
			validationMsg = "Design Section is required";
			focusField = "";
			
			addMessage(FacesMessage.SEVERITY_WARN, "Design Section is required" , null, "eventApplicationCopyForm:messages");
			return;
			
		} else if(m_hmpDesignSectionToCopy != null && !m_hmpDesignSectionToCopy.containsValue(m_strDesignSectionToCopy)) {
			
			validationMsg = "Design Section is invalid";
			focusField = "";
			
			addMessage(FacesMessage.SEVERITY_WARN, "Design Section is invalid" , null, "eventApplicationCopyForm:messages");
			return;
			
		} else if(responseDVO != null && !((Boolean)responseDVO.getM_abstractDVO().getObject())){
			
			validationMsg = "Application (Plant, Model, Type, Option, Color) does not exist on this Event";
			
			addMessage(FacesMessage.SEVERITY_WARN, "Application (Plant, Model, Type, Option, Color) does not exist on this Event" , null, "eventApplicationCopyForm:messages");
			return;
			
		} else if(m_intQtyToCopy == null) {
			
			validationMsg = "Quantity is required";
			focusField = "";
			
			addMessage(FacesMessage.SEVERITY_WARN, "Quantity is required" , null, "eventApplicationCopyForm:messages");
			return;
			
		} else if(!StringUtils.isNumeric(m_intQtyToCopy.toString())){
			
			validationMsg = "Quantity is invalid";
			focusField = "";
			
			addMessage(FacesMessage.SEVERITY_WARN, "Quantity is invalid" , null, "eventApplicationCopyForm:messages");
			return;
			
		} else if(m_strShareToCopy == null || m_strShareToCopy.equalsIgnoreCase("")) {
			
			validationMsg = "Share % is required";
			focusField = "";
			
			addMessage(FacesMessage.SEVERITY_WARN, "Share % is required" , null, "eventApplicationCopyForm:messages");
			return;
			
		} else if(m_strShareToCopy != null) {
			
			Double d =(Double.parseDouble(m_strShareToCopy));
			if(d > 999.999){
				validationMsg = "Numeric value out of range. Share % is required";
				focusField = "";
				
				addMessage(FacesMessage.SEVERITY_WARN, "Numeric value out of range. Share % is required" , null, "eventApplicationCopyForm:messages");
				return;
			}
			
		}
		
		
		/* else if(!StringUtils.isNumeric(m_intShareToCopy.toString())){
			
			validationMsg = "Share % is invalid";
			focusField = "";
			
			addMessage(FacesMessage.SEVERITY_WARN, "Share % is invalid" , null, "eventApplicationCopyForm:messages");
			return;
		}*/
		
		logger.debug("\n Exiting performValidationsCopy() ");
		
	}
	
	public void copyEventApplication() throws Exception{
		
		if(m_eventApplicationList == null || m_eventApplicationList.isEmpty()){
			addMessage(FacesMessage.SEVERITY_WARN, "Please select a record " , null, "searchEventApplicationForm:messages");
			return;
		}
		else{
			logger.debug("\n Entering copyEventApplication() ");

			m_strEventNameCopy = m_strEventName;
			m_decEventRevisionNoCopy = m_decEventRevisionNo;
			
			m_selectedApplication.setM_strEventName(m_strEventNameCopy);
			m_selectedApplication.setM_decEventRevNo(m_decEventRevisionNoCopy);
				
			//RequestDVO requestDVO = new RequestDVO();
			RequestDVO requestDVO = instantiateRequestDVO();
			ResponseDVO responseDVO = null;
			
			requestDVO.setM_abstractDVO(m_selectedApplication);
			
			responseDVO = eventApplicationService.getEventApplicationDetails(requestDVO);
			
			setEventApplicationCopyForm((EventApplicationDVO)responseDVO.getM_abstractDVO());
			
			m_intQtyFromCopy = null;
			m_intQtyToCopy = null;
			m_intShareFromCopy = null;
			m_intShareToCopy = null;
			m_strShareToCopy = null;
			if("FALSE".equalsIgnoreCase(m_strIsSupply)){
				m_strIsSupply = "";
			}
			if(m_bolgbFeature){
				
				m_bolEnableAddCopy = false;
				m_bolEnableRemoveCopy = false;
			}
			
			responseDVO = eventApplicationService.getPartName(requestDVO);
			if(((ListDVO)responseDVO.getM_abstractDVO()).getList() == null || ((ListDVO)responseDVO.getM_abstractDVO()).getList().size() == 0 ){
				
				responseDVO = eventApplicationService.getNewPartName(requestDVO);
				
				if(((ListDVO)responseDVO.getM_abstractDVO()).getList() == null || ((ListDVO)responseDVO.getM_abstractDVO()).getList().size() == 0 ){
					
					m_strPartNameCopy = null;
					
				} else
					m_strPartNameCopy = (String)((ListDVO)responseDVO.getM_abstractDVO()).getList().get(0);
			
			} else {
				
				m_strPartNameCopy = (String)((ListDVO)responseDVO.getM_abstractDVO()).getList().get(0);
			}
			
			
			responseDVO = eventApplicationService.getSupplierName(requestDVO);
			if(responseDVO.getM_abstractDVO().getObject() == null || ((String)responseDVO.getM_abstractDVO().getObject()).equalsIgnoreCase("") ){
				
				m_strSupplierNameCopy = null;
				
			} else
				m_strSupplierNameCopy = (String)responseDVO.getM_abstractDVO().getObject();
			
			populateCopyDropdowns(false, true, true, true, true, true);	
			
			requestDVO.setM_abstractDVO(getEventApplicationSearchFormObject());
			responseDVO = eventApplicationService.searchEventApplications(requestDVO);
			
			ListDVO outDVO = (ListDVO)responseDVO.getM_abstractDVO();
			m_eventApplicationListCopy = outDVO.getList();
			
			if(m_eventApplicationListCopy != null &&  m_eventApplicationListCopy.size() > 0)
				m_selectedApplicationCopy = (EventApplicationDVO)m_eventApplicationListCopy.get(0);
	 		
			logger.debug("\n Exiting copyEventApplication() ");	
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("copyeventapplication.xhtml");
		}
		
		
		
	}
	
	public void addAvailableFeatureCopy(){
		
		logger.debug("\n Entering addAvailableFeatureCopy() ");
		RequestDVO requestDVO = new RequestDVO();
		ResponseDVO responseDVO = null;
		
		/*requestDVO.setM_abstractDVO(getEventApplicationDialogFormObject());
		responseDVO = eventApplicationService.addAvailableFeature(requestDVO);
		
		populateFeatureGrids();*/
		
		logger.debug("\n Exiting addAvailableFeatureCopy() ");
	}
	
	public void removeAvailableFeatureCopy(){
		
		logger.debug("\n Entering removeAvailableFeatureCopy() ");
		
		RequestDVO requestDVO = new RequestDVO();
		ResponseDVO responseDVO = null;
		
		/*requestDVO.setM_abstractDVO(getEventApplicationDialogFormObject());
		responseDVO = eventApplicationService.removeAvailableFeature(requestDVO);
		
		 populateFeatureGrids();*/
		
		logger.debug("\n Exiting removeAvailableFeatureCopy() ");		
	}
	
	public void performCopyEventApplication(){
		
		logger.debug("\n Entering performCopyEventApplication() ");
		
		//RequestDVO requestDVO = new RequestDVO();
		RequestDVO requestDVO = instantiateRequestDVO();
		ResponseDVO responseDVO = null;
		
		try {
			
			requestDVO.setM_abstractDVO(getEventApplicationCopyFormObject());
			responseDVO = eventApplicationService.performCopyEventApplication(requestDVO);
			
			if(responseDVO != null && responseDVO.getM_abstractDVO().getObject() != null){
				
				String returnMsg = (String)responseDVO.getM_abstractDVO().getObject();
				
				if(returnMsg.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.SUCCESS_STATUS)){
					addMessage(FacesMessage.SEVERITY_INFO, "Record Copied successfully", null, null);
				}
				else{
					addMessage(FacesMessage.SEVERITY_ERROR, "Unable to add the record : \n"+returnMsg, null, null);
					m_displayMessage = new FacesMessage(FacesMessage.SEVERITY_WARN,"Unable to add the record : \n"+returnMsg , null);
					closeWindow = false;					
					FacesContext.getCurrentInstance().getExternalContext().redirect("copyeventapplication.xhtml");
				}
					
			}
			
			if(closeWindow){
				if(m_strAction != null && m_strAction.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.OK_ACTION)){
					
					refreshSearchGrid(null);
					FacesContext.getCurrentInstance().getExternalContext().redirect("searcheventapplications.xhtml");
				}
			}
				
				
		} catch (Exception e) {

			e.printStackTrace();
		}
		logger.debug("\n Exiting performCopyEventApplication() ");		
	}
	
	public void setEventApplicationCopyForm(EventApplicationDVO inDVO){
		
		logger.debug("\n Entering setEventApplicationCopyForm() ");
		
		//m_strEventNameDlg = (inDVO.getM_strEventName() != null && inDVO.getM_strEventName().equalsIgnoreCase("") ) ? null : inDVO.getM_strEventName() ;
		//m_strEventNameDescDlg = (inDVO.getM_strEventNameDesc() != null && inDVO.getM_strEventNameDesc().equalsIgnoreCase("") ) ? null : inDVO.getM_strEventNameDesc();
		//m_decEventRevisionNoDlg = inDVO.getM_decEventRevNo();
		m_strPartNumberCopy = (inDVO.getM_strPartNumber() != null && inDVO.getM_strPartNumber().equalsIgnoreCase("") ) ? null : inDVO.getM_strPartNumber() ;
		m_strPartNameCopy = (inDVO.getM_strPartName() != null && inDVO.getM_strPartName().equalsIgnoreCase("") ) ? null : inDVO.getM_strPartName() ;
		m_strPartColorCopy = (inDVO.getM_strPartColor() != null && inDVO.getM_strPartColor().equalsIgnoreCase("") ) ? null : inDVO.getM_strPartColor() ;
		m_strSupplierCopy = (inDVO.getM_strSupplierNumber() != null && inDVO.getM_strSupplierNumber().equalsIgnoreCase("") ) ? null : inDVO.getM_strSupplierNumber() ;
		m_strSupplierNameCopy = (inDVO.getM_strSupplierName() != null && inDVO.getM_strSupplierName().equalsIgnoreCase("") ) ? null : inDVO.getM_strSupplierName() ;
		setFromToPortionCopyForm(inDVO);
		
		logger.debug("\n Exiting setEventApplicationCopyForm() ");
	}
	
	private void setFromToPortionCopyForm(EventApplicationDVO inDVO) {

		m_strTargetPlantFromCopy = (inDVO.getM_strTargetPlant() != null && inDVO.getM_strTargetPlant().equalsIgnoreCase("") ) ? null : inDVO.getM_strTargetPlant() ;
		m_strTargetPlantToCopy = (inDVO.getM_strTargetPlant() != null && inDVO.getM_strTargetPlant().equalsIgnoreCase("") ) ? null : inDVO.getM_strTargetPlant() ;
		m_strDesignSectionFromCopy = (inDVO.getM_strDesignSection() != null && inDVO.getM_strDesignSection().equalsIgnoreCase("") ) ? null : inDVO.getM_strDesignSection() ;
		m_strDesignSectionToCopy = (inDVO.getM_strDesignSection() != null && inDVO.getM_strDesignSection().equalsIgnoreCase("") ) ? null : inDVO.getM_strDesignSection() ;
		m_strModelFromCopy = (inDVO.getM_strModel() != null && inDVO.getM_strModel().equalsIgnoreCase("") ) ? null : inDVO.getM_strModel() ;
		m_strModelToCopy = (inDVO.getM_strModel() != null && inDVO.getM_strModel().equalsIgnoreCase("") ) ? null : inDVO.getM_strModel() ;
		m_strMTCTypeFromCopy1 = (inDVO.getM_strMTCType1() != null && inDVO.getM_strMTCType1().size()==0 ) ? null : inDVO.getM_strMTCType1() ;
		m_strMTCTypeToCopy1 = (inDVO.getM_strMTCType1() != null && inDVO.getM_strMTCType1().size()==0 ) ? null : inDVO.getM_strMTCType1() ;
		m_strMTCOptionFromCopy = (inDVO.getM_strMTCOption() != null && inDVO.getM_strMTCOption().equalsIgnoreCase("") ) ? null : inDVO.getM_strMTCOption() ;
		m_strMTCOptionToCopy = (inDVO.getM_strMTCOption() != null && inDVO.getM_strMTCOption().equalsIgnoreCase("") ) ? null : inDVO.getM_strMTCOption() ;
		m_strCategoryFromCopy = (inDVO.getM_strCategory() != null && inDVO.getM_strCategory().equalsIgnoreCase("") ) ? null : inDVO.getM_strCategory() ;
		m_strCategoryToCopy = (inDVO.getM_strCategory() != null && inDVO.getM_strCategory().equalsIgnoreCase("") ) ? null : inDVO.getM_strCategory() ;
		m_intQtyFromCopy = inDVO.getM_intQuantity();
		m_intQtyToCopy = inDVO.getM_intQuantity();
		m_intShareFromCopy = inDVO.getM_intRate();
		//m_intShareToCopy = inDVO.getM_intRate();
		m_strShareToCopy = inDVO.getM_bRate().toString();
		m_strIsSupplyFromCopy = inDVO.getM_strIsSupply();
		m_strIsSupplyToCopy = inDVO.getM_strIsSupply();
	}

	public EventApplicationDVO getEventApplicationCopyFormObject(){
		
		logger.debug("\n Entering getEventApplicationCopyFormObject() ");
		
		EventApplicationDVO outDVO = new EventApplicationDVO();
		
		outDVO.setM_strEventName(m_strEventNameCopy);
		outDVO.setM_strEventNameDesc(m_strEventNameDescCopy);
		outDVO.setM_decEventRevNo(m_decEventRevisionNoCopy);
		outDVO.setM_strPartNumber(m_strPartNumberCopy);
		outDVO.setM_strPartName(m_strPartNameCopy);
		outDVO.setM_strPartColor(m_strPartColorCopy);
		outDVO.setM_strSupplierNumber(m_strSupplierCopy);
		outDVO.setM_strSupplierName(m_strSupplierNameCopy);
		
		outDVO.setM_strDesignSectionFromCopy(m_strDesignSectionFromCopy);
		outDVO.setM_strDesignSectionToCopy(m_strDesignSectionToCopy);
		outDVO.setM_strTargetPlantFromCopy(m_strTargetPlantFromCopy);
		outDVO.setM_strTargetPlantToCopy(m_strTargetPlantToCopy);
		outDVO.setM_strModelFromCopy(m_strModelFromCopy);
		outDVO.setM_strModelToCopy(m_strModelToCopy);
		outDVO.setM_strMTCTypeFromCopy1(m_strMTCTypeFromCopy1);
		outDVO.setM_strMTCTypeToCopy(m_strMTCTypeToCopy);
		outDVO.setM_strMTCOptionFromCopy(m_strMTCOptionFromCopy);
		outDVO.setM_strMTCOptionToCopy(m_strMTCOptionToCopy);
		outDVO.setM_strCategoryFromCopy(m_strCategoryFromCopy);
		outDVO.setM_strCategoryToCopy(m_strCategoryToCopy);
		outDVO.setM_intQuantityFromCopy(m_intQtyFromCopy);
		outDVO.setM_intQuantityToCopy(m_intQtyToCopy);
		outDVO.setM_intRateFromCopy(m_intShareFromCopy);
		//outDVO.setM_intRateToCopy(m_intShareToCopy);
		outDVO.setM_strRateToCopy(m_strShareToCopy);
		outDVO.setM_strShipToCodeFromCopy(m_strShipToCodeFromCopy);
		outDVO.setM_strShipToCodeToCopy(m_strShipToCodeToCopy);	
		outDVO.setM_strIsSupplyFromCopy(m_strIsSupplyFromCopy);
		outDVO.setM_strIsSupplyToCopy(m_strIsSupplyToCopy);	
		
		if(m_selectedAvailableFeatureCopy != null)
			outDVO.setM_strAvailableProductEquipCode(m_selectedAvailableFeatureCopy.getM_strCode());
		
		if(m_selectedAssignedFeatureCopy != null)
			outDVO.setM_strAssignedProductEquipCode(m_selectedAssignedFeatureCopy.getM_strCode());
		
		logger.debug("\n Exiting getEventApplicationCopyFormObject() ");
		
		return outDVO;
	}
	/*
	public Boolean validateApplyFields (){
		isvalid = true;
		if(m_selectedApplication.getM_intQuantity() == null){
			isvalid = false;
			addMessage(FacesMessage.SEVERITY_WARN, "Quantity is required" , null, null);
			//addMessage(FacesMessage.SEVERITY_WARN, "Quantity is required" , null, "eventApplicationCopyForm:messages");
		} else if(!StringUtils.isNumeric(m_selectedApplication.getM_intQuantity().toString())){
			isvalid = false;
			validationMsg = "Quantity is invalid";
			addMessage(FacesMessage.SEVERITY_WARN, "Quantity "+m_selectedApplication.getM_intQuantity().toString()+" is invalid. Unable to add record" , null, "searchEventApplicationForm:messages");
			
		} else if(m_selectedApplication.getM_bRate() == null){
			isvalid = false;
			addMessage(FacesMessage.SEVERITY_WARN, "Share % is required" , null, null);
			//addMessage(FacesMessage.SEVERITY_WARN, "Quantity is required" , null, "eventApplicationCopyForm:messages");
			
		} else if(!StringUtils.isNumeric(m_selectedApplication.getM_bRate().toString())){
			isvalid = false;
			addMessage(FacesMessage.SEVERITY_WARN, "Share % "+m_selectedApplication.getM_bRate().toString()+" is invalid. Unable to add reocrd" , null, "searchEventApplicationForm:messages");
			
		}
		return isvalid;
		
	}
	*/
	
	public void applyToSelectedButtonClick(){ 
		
		logger.debug("\n Entering applyToSelectedButtonClick() ");
		try{
			EventApplicationDVO eventApplicationDVO = getEventApplicationSearchFormObject();
			if(m_eventApplicationList==null){
				addMessage(FacesMessage.SEVERITY_WARN, "Please click find now.", null, "searchEventApplicationForm:messages");			
				return;
			}
			/*if(m_selectedEventApplication!=null){
				m_selectedEventApplication.clear();
				for(EventApplicationDVO eventApplicationDVOCheck:m_eventApplicationList){
					if(eventApplicationDVOCheck.isM_bSelected()){
						m_selectedEventApplication.add(eventApplicationDVOCheck);
					}
				}
			}*/
			if(m_selectedEventApplication.size()==0){
				addMessage(FacesMessage.SEVERITY_WARN, "No checkbox is selected, please select checkbox and click on 'Apply to Selected' field or use apply changes", null, "searchEventApplicationForm:messages");			
				return;
			}
			/*if("".equalsIgnoreCase(eventApplicationDVO.getM_strIsSupplyForSelected()) || eventApplicationDVO.getM_intQtyForSelected()==null || eventApplicationDVO.getM_bRateForSelected()==null){
				addMessage(FacesMessage.SEVERITY_WARN, "One or more of the fields is blank.  Please input value(s).", null, null);			
				return;
			}*/
			// Check which fields the user actually entered
		    boolean qtyEntered =
		            eventApplicationDVO.getM_intQtyForSelected() != null;

		    boolean rateEntered =
		            eventApplicationDVO.getM_bRateForSelected() != null;

		    boolean sectionEntered =
		            m_strDesignSectionForSelected != null
		            && !m_strDesignSectionForSelected.trim().isEmpty();

		    // Show warning ONLY when nothing is entered
		    if (!qtyEntered && !rateEntered && !sectionEntered) {
		        addMessage(
		            FacesMessage.SEVERITY_WARN,
		            "Please input at least one value to update.",
		            null,
		            null
		        );
		        return;
		    }
			 RequestContext context = RequestContext.getCurrentInstance();
		     context.execute("PF('confirmDialogApplySel').show();");
		} catch(Exception e){
			e.printStackTrace();
			handleSystemException(e, "eventApplicationDialogForm:messages");
		}
		logger.debug("\n Exiting applyToSelectedButtonClick() ");
	}
	
	public void applyButtonClick(){ 
		
		logger.debug("\n Entering applyButtonClick() ");
		if(m_eventApplicationList==null){
			addMessage(FacesMessage.SEVERITY_WARN, "Please click find now.", null, null);			
			return;
		}
		
		
		//Below lines are commented as need to save the data even if checkbox is checkedthe row is checked  
		
		if(m_hmpSelectedEventApplicationDetails!=null&&m_hmpSelectedEventApplicationDetails.size()==0){
			addMessage(FacesMessage.SEVERITY_WARN, "Please update record and then click Apply.", null, null);
			return;
		}
		if(m_eventApplicationList.size()>0){
			 RequestContext context = RequestContext.getCurrentInstance();
		     context.execute("PF('confirmDialogApply').show();");
		}
		logger.debug("\n Exiting applyButtonClick() ");
	}
	
	public void applyEventApplication(){ 
		
		logger.debug("\n Entering applyEventApplication() ");
		//RequestDVO requestDVO = new RequestDVO();
		//Below lines are commented as need to save the data even if checkbox is checkedthe row is checked
			RequestDVO requestDVO = instantiateRequestDVO();
			ListDVO inDVO = new ListDVO();
			
			inDVO.setList(new ArrayList(m_hmpSelectedEventApplicationDetails.values()));
			requestDVO.setM_abstractDVO(inDVO);
			
			eventApplicationService.applyEventApplication(requestDVO);
			m_hmpSelectedEventApplicationDetails.clear();
			m_bolApplyEnabled = false;
			refreshSearchGrid(null);
		
		logger.debug("\n Exiting applyEventApplication() ");
	}
	
	public void applyEventApplicationDirectlyToBudget(){ 
		
		logger.debug("\n Entering applyEventApplicationDirectlyToBudget() ");
		//RequestDVO requestDVO = new RequestDVO();
		//Below lines are commented as need to save the data even if checkbox is checkedthe row is checked

			RequestDVO requestDVO = instantiateRequestDVO();
			ListDVO inDVO = new ListDVO();
			
			inDVO.setList(new ArrayList(m_hmpSelectedEventApplicationDetails.values()));
			requestDVO.setM_abstractDVO(inDVO);
			
			eventApplicationService.applyEventApplicationToAttachedBudget(requestDVO);
			m_bolApplyEnabled = false;
			refreshSearchGrid(null);
		
		logger.debug("\n Exiting applyEventApplicationDirectlyToBudget() ");
	}
	
	public void applyEventApplicationForDetails(){ 
		
		logger.debug("\n Entering applyEventApplicationForDetails() ");
		
			RequestDVO requestDVO = instantiateRequestDVO();
			ListDVO inDVO = new ListDVO();
			
			
			EventApplicationDVO newDVO=getEventApplicationDialogFormObject();
			if(m_selectedEventApplication==null){
				m_selectedEventApplication=new ArrayList<EventApplicationDVO>();
				m_selectedEventApplication.add(m_selectedApplicationDlg);
			}
			newDVO.setM_strTPlant(m_selectedEventApplication.get(0).getM_strTPlant());
			newDVO.setM_strHPlant(m_selectedEventApplication.get(0).getM_strHPlant());
			newDVO.setM_strPartColor(m_selectedEventApplication.get(0).getM_strPartColor());
			newDVO.setM_strShipToCode(m_selectedEventApplication.get(0).getM_strShipToCode()!=null?m_selectedEventApplication.get(0).getM_strShipToCode():"");
			newDVO.setM_strMTCOption(m_selectedEventApplication.get(0).getM_strMTCOption()!=null?m_selectedEventApplication.get(0).getM_strMTCOption():"");
			
			m_selectedEventApplication.clear();
			m_selectedEventApplication.add(newDVO);
			inDVO.setList(new ArrayList(m_selectedEventApplication));
			requestDVO.setM_abstractDVO(inDVO);
			
			eventApplicationService.applyEventApplication(requestDVO);
			m_bolApplyEnabled = false;
			refreshSearchGrid(null);
		
		logger.debug("\n Exiting applyEventApplicationForDetails() ");
	}
	
	public void applyEventApplicationForDetailsDirctlyToBudget(){ 
		
		logger.debug("\n Entering applyEventApplicationForDetailsDirctlyToBudget() ");
		
			RequestDVO requestDVO = instantiateRequestDVO();
			ListDVO inDVO = new ListDVO();
			
			m_selectedEventApplication.clear();
			
			m_selectedEventApplication.add(getEventApplicationDialogFormObject());
			inDVO.setList(new ArrayList(m_selectedEventApplication));
			requestDVO.setM_abstractDVO(inDVO);
			
			eventApplicationService.applyEventApplicationToAttachedBudget(requestDVO);
			m_bolApplyEnabled = false;
			refreshSearchGrid(null);
		
		logger.debug("\n Exiting applyEventApplicationForDetailsDirctlyToBudget() ");
	}
	
	/***
	 * This method will apply changes to Actual Event Parts as well as Budget Event Parts 
	 * 
	 ***/
	public void applyEventApplicationToActAndBud(){ 
		
		logger.debug("\n Entering applyEventApplicationToActAndBud() ");
		applyEventApplication();
		
		RequestDVO requestDVO = instantiateRequestDVO();
		ListDVO inDVO = new ListDVO();
		
		inDVO.setList(new ArrayList(m_hmpSelectedEventApplicationDetails.values()));
		requestDVO.setM_abstractDVO(inDVO);
		//System shall calculate the Impact amount for budget event and should consider Cross Referenced MTO.
		//1.	It should be consider only when BOM maintenance to done for budget event or for attached budget event as well?
		//2.	And how it should take place in business logic and where to reflect this calcutaion changes?
		//COMPUTE FCBPV-PART-PROD-VEH-QTY = (FCEPA-PART-QTY  *     
        //FCBPU-MONTH-PLAN-QTY)  *     
        //(FCEPA-SHARE-RATE-PERCENT / 100) 
		//COMPUTE FCBPV-FISCAL-VEH-QTY    = (FCEPA-PART-QTY    *   
        //       (Total of Month-plan-qty for 12 months ï¿½ month plan qty) *   
        // (FCEPA-SHARE-RATE-PERCENT / 100)
		eventApplicationService.applyEventApplicationToAttachedBudget(requestDVO);
		
		logger.debug("\n Exiting applyEventApplicationToActAndBud() ");
	}
	
	/***
	 * This method will apply changes to Actual Event Parts as well as Budget Event Parts 
	 * 
	 ***/
	public void applyEventApplicationToActAndBudSel(){ 
		
		logger.debug("\n Entering applyEventApplicationToActAndBud() ");
		applyToSelectedEventApplication();
		
		RequestDVO requestDVO = instantiateRequestDVO();
		ListDVO inDVO = new ListDVO();
		
		inDVO.setList(new ArrayList(m_hmpSelectedEventApplicationDetails.values()));
		requestDVO.setM_abstractDVO(inDVO);
		//System shall calculate the Impact amount for budget event and should consider Cross Referenced MTO.
		//1.	It should be consider only when BOM maintenance to done for budget event or for attached budget event as well?
		//2.	And how it should take place in business logic and where to reflect this calcutaion changes?
		//COMPUTE FCBPV-PART-PROD-VEH-QTY = (FCEPA-PART-QTY  *     
        //FCBPU-MONTH-PLAN-QTY)  *     
        //(FCEPA-SHARE-RATE-PERCENT / 100) 
		//COMPUTE FCBPV-FISCAL-VEH-QTY    = (FCEPA-PART-QTY    *   
        //       (Total of Month-plan-qty for 12 months ï¿½ month plan qty) *   
        // (FCEPA-SHARE-RATE-PERCENT / 100)
		eventApplicationService.applyEventApplicationToAttachedBudget(requestDVO);
		
		logger.debug("\n Exiting applyEventApplicationToActAndBud() ");
	}
	
	
	public void validateActualEventIsAttachedToBudget(){ 
		
		logger.debug("\n Entering validateActualEventIsAttachedToBudget() ");
		EventApplicationDVO eventApplicationDVO = null;

		//Below lines are commented as need to save the data even if checkbox is checkedthe row is checked

			RequestDVO requestDVO = instantiateRequestDVO();

			eventApplicationDVO = getEventApplicationSearchFormObject();
			requestDVO.setM_abstractDVO(eventApplicationDVO);
			//validate the event is actual event and if actual then it is attached to any budget event or not
			String eventType = eventApplicationService.validateEventIsActualOrBudget(requestDVO);
			if(eventType!=null && "MPCE".equalsIgnoreCase(eventType)){
				//TODO- Changes to be moved after UC 9 is moved 
				//Un-comment this code once 'Load Budget Event' use case is moved to production
				
				
				//Remove below function call after above code is uncommented
				applyEventApplication();
			}else if(eventType!=null && "BUCE".equalsIgnoreCase(eventType)){
				applyEventApplicationDirectlyToBudget();
			}else{
				applyEventApplication();
			}
		logger.debug("\n Exiting validateActualEventIsAttachedToBudget() ");
	}
	
	public void validateActualEventIsAttachedToBudgetSel(){ 
		
		logger.debug("\n Entering validateActualEventIsAttachedToBudget() ");
		EventApplicationDVO eventApplicationDVO = null;
		
			RequestDVO requestDVO = instantiateRequestDVO();


			eventApplicationDVO = getEventApplicationSearchFormObject();
			requestDVO.setM_abstractDVO(eventApplicationDVO);
			//validate the event is actual event and if actual then it is attached to any budget event or not
			String eventType = eventApplicationService.validateEventIsActualOrBudget(requestDVO);
			if(eventType!=null && "MPCE".equalsIgnoreCase(eventType)){
				//TODO- Changes to be moved after UC 9 is moved 
				//Un-comment this code once 'Load Budget Event' use case is moved to production
				


				
				//Remove below function call after above code is uncommented
				applyToSelectedEventApplication();
			}else if(eventType!=null && "BUCE".equalsIgnoreCase(eventType)){
				applyToSelectedEventApplicationDirectlyToBudget();
			}else{
				applyToSelectedEventApplication();
			}
		logger.debug("\n Exiting validateActualEventIsAttachedToBudget() ");
	}
	
	/*
	 * This method is called on click of Apply for select and m_selectedEventApplication contain's all the select DVO's
	 * Replacing value of all the selected DVO's with values provide on screen for QTY,RATE and Is supply
	 * Calling the same method which is called for Apply to persist values in Data Base
	 * 
	 * */
	public void applyToSelectedEventApplication(){ 
		
		logger.debug("\n Entering applyEventApplication() ");
		EventApplicationDVO eventApplicationDVO = getEventApplicationSearchFormObject();
		if(m_selectedEventApplication.size()==0){
			addMessage(FacesMessage.SEVERITY_WARN, "No checkbox is selected, please select checkbox and click on 'Apply to Selected' field or use apply changes", null, null);			
			return;
		}
		// Check which fields the user actually entered
	    boolean qtyEntered =
	            eventApplicationDVO.getM_intQtyForSelected() != null;

	    boolean rateEntered =
	            eventApplicationDVO.getM_bRateForSelected() != null;

	    boolean sectionEntered =
	            m_strDesignSectionForSelected != null
	            && !m_strDesignSectionForSelected.trim().isEmpty();

	    // Show warning ONLY when nothing is entered
	    if (!qtyEntered && !rateEntered && !sectionEntered) {
	        addMessage(
	            FacesMessage.SEVERITY_WARN,
	            "Please input at least one value to update.",
	            null,
	            null
	        );
	        return;
	    }
			RequestDVO requestDVO = instantiateRequestDVO();
			ListDVO inDVO = new ListDVO();
			
			Integer i = 0;
			if(m_hmpSelectedEventApplicationDetails!=null){
				m_hmpSelectedEventApplicationDetails.clear();
			}
			for (EventApplicationDVO eadvo : m_selectedEventApplication) {
				
				if (!sectionEntered) {
			        eadvo.setM_strOldDesignSection(eadvo.getM_strDesignSection());
			    }

				 /*
		         * IMPORTANT:
		         * Update Qty only when user entered Qty.
		         * Otherwise keep existing Qty.
		         */
		        if (qtyEntered) {
		            eadvo.setM_intQuantity(
		                eventApplicationDVO.getM_intQtyForSelected()
		            );
		        }

		        /*
		         * Update Rate only when user entered Rate.
		         * Otherwise keep existing Rate.
		         */
		        if (rateEntered) {
		            eadvo.setM_bRate(
		                eventApplicationDVO.getM_bRateForSelected()
		            );
		        }

		        /*
		         * Update Section only when user selected Section.
		         */
		        if (sectionEntered) {

		            // Save existing section before changing it
		            String oldSection = eadvo.getM_strDesignSection();

		            // If the actual grid section is stored in another property,
		            // use that property instead.
		            eadvo.setM_strOldDesignSection(oldSection);

		            String sectionCode =
		                    m_strDesignSectionForSelected.trim();

		            // "S15 - IDE CAR BODY" -> "S15"
		            if (sectionCode.contains("-")) {
		                sectionCode = sectionCode
		                        .substring(0, sectionCode.indexOf("-"))
		                        .trim();
		            }

		            // Set new section
		            eadvo.setM_strDesignSection(sectionCode);
		        }
			    m_hmpSelectedEventApplicationDetails.put(i.toString(), eadvo);
			    i++;
			}
			inDVO.setList(new ArrayList(m_hmpSelectedEventApplicationDetails.values()));
			requestDVO.setM_abstractDVO(inDVO);
			try {
			    eventApplicationService.applyEventApplication(requestDVO);
			} catch (RuntimeException e) {

			    addMessage(FacesMessage.SEVERITY_ERROR,
			            e.getMessage(),
			            null,
			            "searchEventApplicationForm:messages");
			    return;
			}
			// Clear Apply to Selected fields
			eventApplicationDVO.setM_intQtyForSelected(null);
			eventApplicationDVO.setM_bRateForSelected(null);
			eventApplicationDVO.setM_strIsSupplyForSelected("");

			m_intQtyForSelected = null;
			m_bRateForSelected = null;
			m_strDesignSectionForSelected = "";
			m_strIsSupplyForSelected = "";

			eventApplicationDVO.setM_strDesignSection("");
			m_bolApplyEnabled = false;
			ArrayList<EventApplicationDVO> m_selectedEventApplicationnew = new ArrayList<EventApplicationDVO>();
			m_selectedEventApplication = m_selectedEventApplicationnew;
			m_hmpSelectedEventApplicationDetails.clear();
			refreshSearchGrid(null);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("searcheventapplications.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		logger.debug("\n Exiting applyEventApplication() ");
	}
	
	public void applyToSelectedEventApplicationDirectlyToBudget(){ 
		
		logger.debug("\n Entering applyToSelectedEventApplicationDirectlyToBudget() ");
		EventApplicationDVO eventApplicationDVO = getEventApplicationSearchFormObject();
		if(m_selectedEventApplication.size()==0){
			addMessage(FacesMessage.SEVERITY_WARN, "No checkbox is selected, please select checkbox and click on 'Apply to Selected' field or use apply changes", null, null);			
			return;
		}
		// Check which fields the user actually entered
	    boolean qtyEntered =
	            eventApplicationDVO.getM_intQtyForSelected() != null;

	    boolean rateEntered =
	            eventApplicationDVO.getM_bRateForSelected() != null;

	    boolean sectionEntered =
	            m_strDesignSectionForSelected != null
	            && !m_strDesignSectionForSelected.trim().isEmpty();

	    // Show warning ONLY when nothing is entered
	    if (!qtyEntered && !rateEntered && !sectionEntered) {
	        addMessage(
	            FacesMessage.SEVERITY_WARN,
	            "Please input at least one value to update.",
	            null,
	            null
	        );
	        return;
	    }
			RequestDVO requestDVO = instantiateRequestDVO();
			ListDVO inDVO = new ListDVO();
			
			Integer i = 0;
			for (EventApplicationDVO eadvo : m_selectedEventApplication) {
				
				if (!sectionEntered) {
			        eadvo.setM_strOldDesignSection(eadvo.getM_strDesignSection());
			    }

				 /*
		         * IMPORTANT:
		         * Update Qty only when user entered Qty.
		         * Otherwise keep existing Qty.
		         */
		        if (qtyEntered) {
		            eadvo.setM_intQuantity(
		                eventApplicationDVO.getM_intQtyForSelected()
		            );
		        }

		        /*
		         * Update Rate only when user entered Rate.
		         * Otherwise keep existing Rate.
		         */
		        if (rateEntered) {
		            eadvo.setM_bRate(
		                eventApplicationDVO.getM_bRateForSelected()
		            );
		        }

		        /*
		         * Update Section only when user selected Section.
		         */
		        if (sectionEntered) {

		            // Save existing section before changing it
		            String oldSection = eadvo.getM_strDesignSection();

		            // If the actual grid section is stored in another property,
		            // use that property instead.
		            eadvo.setM_strOldDesignSection(oldSection);

		            String sectionCode =
		                    m_strDesignSectionForSelected.trim();

		            // "S15 - IDE CAR BODY" -> "S15"
		            if (sectionCode.contains("-")) {
		                sectionCode = sectionCode
		                        .substring(0, sectionCode.indexOf("-"))
		                        .trim();
		            }

		            // Set new section
		            eadvo.setM_strDesignSection(sectionCode);
		        }
			    m_hmpSelectedEventApplicationDetails.put(i.toString(), eadvo);
			    i++;
			}
			inDVO.setList(new ArrayList(m_hmpSelectedEventApplicationDetails.values()));
			requestDVO.setM_abstractDVO(inDVO);
			try {
			    eventApplicationService.applyEventApplicationToAttachedBudget(requestDVO);
			} catch (RuntimeException e) {

			    addMessage(
			        FacesMessage.SEVERITY_ERROR,
			        e.getMessage(),
			        null,
			        "searchEventApplicationForm:messages");

			    return;
			}
			// Clear Apply to Selected fields
						eventApplicationDVO.setM_intQtyForSelected(null);
						eventApplicationDVO.setM_bRateForSelected(null);
						eventApplicationDVO.setM_strIsSupplyForSelected("");

						m_intQtyForSelected = null;
						m_bRateForSelected = null;
						m_strDesignSectionForSelected = "";
						m_strIsSupplyForSelected = "";

						eventApplicationDVO.setM_strDesignSection("");

			m_bolApplyEnabled = false;
			ArrayList<EventApplicationDVO> m_selectedEventApplicationnew = new ArrayList<EventApplicationDVO>();
			m_selectedEventApplication = m_selectedEventApplicationnew;
			refreshSearchGrid(null);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("searcheventapplications.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		logger.debug("\n Exiting applyToSelectedEventApplicationDirectlyToBudget() ");
	}
	
	public void onTabChange(TabChangeEvent event) {
		
		logger.debug("\n Entering onTabChange() ");
		
        String tabTitle = event.getTab().getTitle();
        
        if("Copy Application".equalsIgnoreCase(tabTitle)){
        	
        	tabActiveIndex = 0;
        } else if("Features".equalsIgnoreCase(tabTitle)) {
        	
        	tabActiveIndex = 1;
        } else 
        	tabActiveIndex = 0;
        
        logger.debug("\n Exiting onTabChange() ");
        
	}
	
	public void dblClickEventApplicationDetails() throws Exception{
		
		 logger.debug("\n Entering dblClickEventApplicationDetails() ");
		 
		 if(m_bolCopyEnabled && m_bolAddNewEnabled && m_bolApplyEnabled)
		 	openEventApplicationDetails();
		 else
			 return;
		 
		 logger.debug("\n Exiting dblClickEventApplicationDetails() ");
	}
	
	public void onEventApplicationsCellEdit(CellEditEvent event){
		
		 logger.debug("\n Entering onEventApplicationsCellEdit() ");
		 	BigDecimal oldValue = null;
			BigDecimal newValue = null;
			Integer strNewVal = null;
			Integer strOldVal = null;
			String strOldStringVal = null;
			String strNewStringVal = null;
			
		 if(event.getOldValue() instanceof BigDecimal && event.getNewValue() instanceof BigDecimal) {
				oldValue = (BigDecimal)event.getOldValue();
				newValue = (BigDecimal)event.getNewValue();
			}else if (event.getOldValue() instanceof Number && event.getNewValue() instanceof Number) {
				strNewVal = (Integer)event.getOldValue();
				strOldVal = (Integer)event.getNewValue();
			}else if (event.getOldValue() instanceof String || event.getNewValue() instanceof String) {
				strOldStringVal = event.getOldValue() != null ? (String)event.getOldValue() : "";
				strNewStringVal = event.getNewValue() != null ? (String)event.getNewValue() : "";
			}else if (event.getOldValue() instanceof ArrayList && event.getNewValue() instanceof ArrayList) {
				if(((ArrayList)event.getOldValue()).get(0) instanceof BigDecimal && ((ArrayList)event.getNewValue()).get(0) instanceof BigDecimal) {
					oldValue = (BigDecimal)((ArrayList)event.getOldValue()).get(0);
					newValue = (BigDecimal)((ArrayList)event.getNewValue()).get(0);
				}else if (((ArrayList)event.getOldValue()).get(0) instanceof Number && ((ArrayList)event.getNewValue()).get(0) instanceof Number) {
					strNewVal = (Integer)((ArrayList)event.getOldValue()).get(0);
					strOldVal = (Integer)((ArrayList)event.getNewValue()).get(0);
				}
			}

		 // Check if any value actually changed (BigDecimal, Integer, or String)
		 boolean valueChanged = false;
		 if(null!=oldValue && newValue!= null && oldValue.compareTo(newValue) !=0){
			 valueChanged = true;
		 } else if(null!=strOldVal && strNewVal!= null && !strNewVal.equals(strOldVal)){
			 valueChanged = true;
		 } else if(strOldStringVal != null && strNewStringVal != null && !strOldStringVal.equals(strNewStringVal)){
			 valueChanged = true;
		 }

		 if(valueChanged){	
				m_bolApplyEnabled = true;
				RequestContext.getCurrentInstance().update("searchEventApplicationForm:applyBtn");
				
				String strEditedRowKeyId = null;
				
				if(m_eventApplicationList.get(event.getRowIndex()).getM_strEditedRowKeyId()!=null){
					
					strEditedRowKeyId = m_eventApplicationList.get(event.getRowIndex()).getM_strEditedRowKeyId();
				}else{
					
					strEditedRowKeyId = String.valueOf(event.getRowIndex());
				}
				
				// For Section change: preserve old section value for the WHERE clause in the update SQL
				if(strOldStringVal != null && strNewStringVal != null && !strOldStringVal.equals(strNewStringVal)){
					EventApplicationDVO editedRow = m_eventApplicationList.get(event.getRowIndex());
					// Only set oldDesignSection if not already set (first edit wins)
					if(editedRow.getM_strOldDesignSection() == null || editedRow.getM_strOldDesignSection().trim().isEmpty()){
						editedRow.setM_strOldDesignSection(strOldStringVal);
					}
				}
				
				m_hmpSelectedEventApplicationDetails.put(strEditedRowKeyId, m_eventApplicationList.get(event.getRowIndex()));
				m_eventApplicationList.get(event.getRowIndex()).setM_strEditedRowKeyId(String.valueOf(event.getRowIndex()));
				
			} else{
				m_bolApplyEnabled = false;
				RequestContext.getCurrentInstance().update("searchEventApplicationForm:applyBtn");
			}
		 logger.debug("\n Exiting onEventApplicationsCellEdit() ");
	}
	
	public void selectEventApplicationListener(){
		 logger.debug("\n Entering selectEventApplicationListener() ");
			if(null!=m_selectedEventApplication && m_selectedEventApplication.size()>0){
			}
		 logger.debug("\n Exiting selectEventApplicationListener() ");
	}
	
	public void sortListner(String currentScreen , String sortedList){
		if(null != sortedList){
			if(sortedList.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.EVENT_APP_AVAIL_FEATURES)){
				if(m_availableFeaturesList != null && !m_availableFeaturesList.isEmpty()){
					m_selectedAvailableFeature = (CodeDVO)m_availableFeaturesList.get(0);
				}
			} else if(sortedList.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.EVENT_APP_ASSIGN_FEATURES)){
				if(m_assignedFeaturesList != null && !m_assignedFeaturesList.isEmpty()){
					m_selectedAssignedFeature = (CodeDVO)m_assignedFeaturesList.get(0);
				}
			} else if(sortedList.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.EVENT_APP_DATATABLE)){
				if(m_eventApplicationListDlg != null && !m_eventApplicationListDlg.isEmpty()){
					m_selectedApplicationDlg = (EventApplicationDVO)m_eventApplicationListDlg.get(0);
				}
			}  else if(sortedList.equalsIgnoreCase(ApplicationConstantsIF.APP_CONSTANTS.EVENT_APP_SEARCH_LIST)){
				if(m_eventApplicationList != null && !m_eventApplicationList.isEmpty()){
					m_selectedApplication = (EventApplicationDVO)m_eventApplicationList.get(0);
				}
			}
		}
	}
	
	public String openEventPartCostFromCcdScreen(ReportCostCmpResultDVO selectedReportCostCmpDVO,String eventName) throws SQLException{
		
		
		m_strDummyEventName = eventName;
		m_strEventName = (String)((HashMap)m_hmpAllEventName.get(m_strDummyEventName)).get(ApplicationConstantsIF.APP_CONSTANTS.PH_COST_EVENT_NAME_COLUMN);
		m_decEventRevisionNo = (BigDecimal)((HashMap)m_hmpAllEventName.get(m_strDummyEventName)).get(ApplicationConstantsIF.APP_CONSTANTS.EVENT_REV_NO_COLUMN);
		m_strEventNameDesc = (String)((HashMap)m_hmpAllEventName.get(m_strDummyEventName)).get(ApplicationConstantsIF.APP_CONSTANTS.PH_COST_EVENT_TEXT_COLUMN);
		m_strEventType = (String)((HashMap)m_hmpAllEventName.get(m_strDummyEventName)).get(ApplicationConstantsIF.APP_CONSTANTS.PH_CMS_ITEM_CODE_COLUMN);
		m_strPartNumber = selectedReportCostCmpDVO.getM_strCurrPartNo(); 
		m_strSupplier = selectedReportCostCmpDVO.getM_strCurrSupplier(); 
		m_strTargetPlant = selectedReportCostCmpDVO.getM_strCurrPlant(); 
		ResponseDVO responseDVO = null;		
		RequestDVO requestDVO = instantiateRequestDVO();
		HashMapDVO hmapDVO = new HashMapDVO();
		HashMap<String, String> hMap = new HashMap<String, String>();
		hMap.put(ApplicationConstantsIF.APP_CONSTANTS.PH_COST_EVENT_NAME_COLUMN, m_strEventName);
		hMap.put(ApplicationConstantsIF.APP_CONSTANTS.EVENT_REV_NO_COLUMN, m_decEventRevisionNo.toString());
		hMap.put(ApplicationConstantsIF.APP_CONSTANTS.MODEL_CODE, selectedReportCostCmpDVO.getM_strCurrModel());
		hMap.put(ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE, selectedReportCostCmpDVO.getM_strCurrType());
		hMap.put(ApplicationConstantsIF.APP_CONSTANTS.MTC_OPTION, selectedReportCostCmpDVO.getM_strCurrOption());
		hMap.put(ApplicationConstantsIF.APP_CONSTANTS.CATEGORY_DROPDOWN, selectedReportCostCmpDVO.getM_strCurrModelCatCode());
		hmapDVO.setM_hmpMap(hMap);
		requestDVO.setM_abstractDVO(hmapDVO);
		responseDVO = eventApplicationService.getFEMDModel(requestDVO); //UC_51 show & tell suggestion
		hmapDVO = (HashMapDVO) responseDVO.getM_abstractDVO();
		hMap = hmapDVO.getM_hmpMap();
		m_strModel = hMap.get(ApplicationConstantsIF.APP_CONSTANTS.MODEL_CODE);
		m_strMTCType = hMap.get(ApplicationConstantsIF.APP_CONSTANTS.MTC_TYPE);
		m_strMTCOption = hMap.get(ApplicationConstantsIF.APP_CONSTANTS.MTC_OPTION);
		m_strCategory = selectedReportCostCmpDVO.getM_strCurrModelCatCode();
		m_strViewMTO="B";
		onSupplierNoChange();
		searchEventApplications();   //UC_51 show & tell suggestion
		return "searcheventapplications?faces-redirect=true";
	}
	
	public String returnToCcdScreen(){
		
		clearManagedBeansFromSession("stckPart_ReportBackingBean");
		StckPart_ReportBackingBean reportBean = (StckPart_ReportBackingBean) getSessionObject("stckPart_ReportBackingBean");
		reportBean.findNow();
		reportBean.setActiveIndex(2);
		return "reportStackedPartList?faces-redirect=true";
	}
	
	/**
	 * This method will auto populate the the value in 'Category' dropdown when 'Model' is selected on Details/ Add New Application Screen
	 */
	private void populateCategoryOnModelSelection(){
		
		logger.debug("\n Entering populateCategoryOnModelSelection() ");
		m_hmpCategoryDlg=new LinkedHashMap();
		m_hmpCategoryDlg.put("", "");
		if(m_strModelDlg==null ||m_strModelDlg.trim().isEmpty()){
			m_strCategoryDlg="";
			m_hmpCategoryDlg.put("E", "E");
			m_hmpCategoryDlg.put("F", "F");
			m_hmpCategoryDlg.put("M", "M");
			m_hmpCategoryDlg.put("D", "D");
		}else{
			List<Map<String, Object>> modelCatCodeList = eventApplicationService.populateCategoryOnModelSelection(m_strModelDlg);
			String modelCatCode="";
			
			m_hmpCategoryDlg=new LinkedHashMap();
			m_hmpCategoryDlg.put("", "");
			
			for(int i=0;i<modelCatCodeList.size();i++){
				if(((String) modelCatCodeList.get(i).get("MODEL_CAT_CODE")).trim().equalsIgnoreCase("E"))
					m_hmpCategoryDlg.put("E", "E");
				if(((String) modelCatCodeList.get(i).get("MODEL_CAT_CODE")).trim().equalsIgnoreCase("F"))
					m_hmpCategoryDlg.put("F", "F");
				if(((String) modelCatCodeList.get(i).get("MODEL_CAT_CODE")).trim().equalsIgnoreCase("M"))
					m_hmpCategoryDlg.put("M", "M");
				if(((String) modelCatCodeList.get(i).get("MODEL_CAT_CODE")).trim().equalsIgnoreCase("D"))
					m_hmpCategoryDlg.put("D", "D");
			}
			
			if(modelCatCodeList.size()>1){
				for(int i=0;i<modelCatCodeList.size();i++){
					modelCatCode=modelCatCode+((String) modelCatCodeList.get(i).get("MODEL_CAT_CODE"))+" and ";
				}
				
				modelCatCode=modelCatCode.substring(0, modelCatCode.length()-9).replace("and", " , ")+modelCatCode.substring(modelCatCode.length()-9,modelCatCode.length()-4);
				modelCatCode=modelCatCode.replace("E", "Engine").replace("F", "Frame").replace("M", "Mission").replace("D", "Differential");
				m_strCategoryDlg="";
				addMessage(FacesMessage.SEVERITY_WARN, "Selected Model is in "+modelCatCode, null, "eventApplicationDialogForm:messages");
			}
			else if(modelCatCodeList.isEmpty()){
				m_strCategoryDlg="";
				m_hmpCategoryDlg.put("E", "E");
				m_hmpCategoryDlg.put("F", "F");
				m_hmpCategoryDlg.put("M", "M");
				m_hmpCategoryDlg.put("D", "D");
				addMessage(FacesMessage.SEVERITY_WARN, "No Category Code available for selected Model!!", null, "eventApplicationDialogForm:messages");
			}
			else{
				modelCatCode=(String) modelCatCodeList.get(0).get("MODEL_CAT_CODE");
				m_strCategoryDlg=modelCatCode.trim();
			}
			
		}
		
		logger.debug("\n Exiting populateCategoryOnModelSelection() ");
	}
	
	/**
	 * This method will enable/disable the 'Apply' button on Quantity or Rate change(Text Box)
	 */
	public void onQtyOrRateChange(){
		
		logger.debug("\n Entering onQtyOrRateChange() ");
		
		if(((m_intQtyForSelected!=null&&!String.valueOf(m_intQtyForSelected).trim().isEmpty())||
				(m_bRateForSelected!=null&&!String.valueOf(m_bRateForSelected).trim().isEmpty()))&&m_bolApplyEnabled){
			m_bolApplyEnabled=false;
			m_bolEventFreeze=true;
			m_bolEditableGrid=false;
			RequestContext.getCurrentInstance().update("searchEventApplicationForm:applyBtn");
			RequestContext.getCurrentInstance().update("searchEventApplicationForm:eventApplicationsTable");
		}else if((m_intQtyForSelected==null||String.valueOf(m_intQtyForSelected).trim().isEmpty())&&
				(m_bRateForSelected==null||String.valueOf(m_bRateForSelected).trim().isEmpty())){
			m_bolApplyEnabled=true;
			m_bolEventFreeze=false;
			m_bolEditableGrid=true;
			RequestContext.getCurrentInstance().update("searchEventApplicationForm:applyBtn");
			RequestContext.getCurrentInstance().update("searchEventApplicationForm:eventApplicationsTable");
		}
		
		logger.debug("\n Exiting onQtyOrRateChange() ");	
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		EventApplicationBackingBean.logger = logger;
	}

	public EventApplicationService getEventApplicationService() {
		return eventApplicationService;
	}

	public void setEventApplicationService(
			EventApplicationService eventApplicationService) {
		this.eventApplicationService = eventApplicationService;
	}

	public String getM_strEventName() {
		return m_strEventName;
	}

	public void setM_strEventName(String eventName) {
		m_strEventName = eventName;
	}

	public String getM_strEventNameDesc() {
		return m_strEventNameDesc;
	}

	public void setM_strEventNameDesc(String eventNameDesc) {
		m_strEventNameDesc = eventNameDesc;
	}

	public String getM_strPartNumber() {
		return m_strPartNumber;
	}

	public void setM_strPartNumber(String partNumber) {
		m_strPartNumber = partNumber.toUpperCase();
	}

	public String getM_strPartColor() {
		return m_strPartColor;
	}

	public void setM_strPartColor(String partColor) {
		m_strPartColor = partColor;
	}

	public String getM_strTargetPlant() {
		return m_strTargetPlant;
	}

	public void setM_strTargetPlant(String targetPlant) {
		m_strTargetPlant = targetPlant;
	}

	public String getM_strSupplier() {
		return m_strSupplier;
	}

	public void setM_strSupplier(String supplier) {
		m_strSupplier = supplier.toUpperCase();;
	}

	public String getM_strRPTCurrency() {
		return m_strRPTCurrency;
	}

	public void setM_strRPTCurrency(String currency) {
		m_strRPTCurrency = currency;
	}

	public String getM_strDesignSection() {
		return m_strDesignSection;
	}

	public void setM_strDesignSection(String designSection) {
		m_strDesignSection = designSection;
	}

	public String getM_procSection() {
		return m_procSection;
	}

	public void setM_procSection(String section) {
		m_procSection = section;
	}

	public String getM_strFeatureCode() {
		return m_strFeatureCode;
	}

	public void setM_strFeatureCode(String featureCode) {
		m_strFeatureCode = featureCode;
	}

	public String getM_strModel() {
		return m_strModel;
	}

	public void setM_strModel(String model) {
		m_strModel = model;
	}

	public String getM_strMTCType() {
		return m_strMTCType;
	}

	public void setM_strMTCType(String type) {
		m_strMTCType = type;
	}

	public String getM_strMTCOption() {
		return m_strMTCOption;
	}

	public void setM_strMTCOption(String option) {
		m_strMTCOption = option;
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

	public String getM_strCategory() {
		return m_strCategory;
	}

	public void setM_strCategory(String category) {
		m_strCategory = category;
	}

	public String getM_strOperator() {
		return m_strOperator;
	}

	public void setM_strOperator(String operator) {
		m_strOperator = operator;
	}

	public String getM_strPartName() {
		return m_strPartName;
	}

	public void setM_strPartName(String partName) {
		m_strPartName = partName;
	}

	public String getM_strSupplierName() {
		return m_strSupplierName;
	}

	public void setM_strSupplierName(String supplierName) {
		m_strSupplierName = supplierName;
	}

	public String getM_strHPlant() {
		return m_strHPlant;
	}

	public void setM_strHPlant(String plant) {
		m_strHPlant = plant;
	}

	public String getM_strTPlant() {
		return m_strTPlant;
	}

	public void setM_strTPlant(String plant) {
		m_strTPlant = plant;
	}

	public LinkedHashMap getM_hmpEventName() {
		return m_hmpEventName;
	}

	public void setM_hmpEventName(LinkedHashMap eventName) {
		m_hmpEventName = eventName;
	}

	public LinkedHashMap getM_hmpAllEventName() {
		return m_hmpAllEventName;
	}

	public void setM_hmpAllEventName(LinkedHashMap allEventName) {
		m_hmpAllEventName = allEventName;
	}

	public LinkedHashMap getM_hmpPartColor() {
		return m_hmpPartColor;
	}

	public void setM_hmpPartColor(LinkedHashMap partColor) {
		m_hmpPartColor = partColor;
	}

	public LinkedHashMap getM_hmpTargetPlant() {
		return m_hmpTargetPlant;
	}

	public void setM_hmpTargetPlant(LinkedHashMap targetPlant) {
		m_hmpTargetPlant = targetPlant;
	}

	public LinkedHashMap getM_hmpRPTCurrency() {
		return m_hmpRPTCurrency;
	}

	public void setM_hmpRPTCurrency(LinkedHashMap currency) {
		m_hmpRPTCurrency = currency;
	}

	public LinkedHashMap getM_hmpDesignSection() {
		return m_hmpDesignSection;
	}

	public void setM_hmpDesignSection(LinkedHashMap designSection) {
		m_hmpDesignSection = designSection;
	}

	public LinkedHashMap getM_hmpProcSection() {
		return m_hmpProcSection;
	}

	public void setM_hmpProcSection(LinkedHashMap procSection) {
		m_hmpProcSection = procSection;
	}

	public LinkedHashMap getM_hmpFeatureCode() {
		return m_hmpFeatureCode;
	}

	public void setM_hmpFeatureCode(LinkedHashMap featureCode) {
		m_hmpFeatureCode = featureCode;
	}

	public LinkedHashMap getM_hmpModel() {
		return m_hmpModel;
	}

	public void setM_hmpModel(LinkedHashMap model) {
		m_hmpModel = model;
	}

	public LinkedHashMap getM_hmpMTCType() {
		return m_hmpMTCType;
	}

	public void setM_hmpMTCType(LinkedHashMap type) {
		m_hmpMTCType = type;
	}

	public LinkedHashMap getM_hmpMTCOption() {
		return m_hmpMTCOption;
	}

	public void setM_hmpMTCOption(LinkedHashMap option) {
		m_hmpMTCOption = option;
	}

	public LinkedHashMap getM_hmpCategory() {
		return m_hmpCategory;
	}

	public void setM_hmpCategory(LinkedHashMap category) {
		m_hmpCategory = category;
	}

	public List<EventApplicationDVO> getM_eventApplicationList() {
		return m_eventApplicationList;
	}

	public void setM_eventApplicationList(List<EventApplicationDVO> applicationList) {
		m_eventApplicationList = applicationList;
	}

	public EventApplicationDVO getM_selectedApplication() {
		return m_selectedApplication;
	}

	public void setM_selectedApplication(EventApplicationDVO application) {
		m_selectedApplication = application;
	}

	public BigDecimal getM_decEventRevisionNo() {
		return m_decEventRevisionNo;
	}

	public void setM_decEventRevisionNo(BigDecimal eventRevisionNo) {
		m_decEventRevisionNo = eventRevisionNo;
	}

	public String getM_strEventType() {
		return m_strEventType;
	}

	public void setM_strEventType(String eventType) {
		m_strEventType = eventType;
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
	
	public String getM_strValidateActualAttachedToBudgetMessage() {
		return m_strValidateActualAttachedToBudgetMessage;
	}

	public void setM_strValidateActualAttachedToBudgetMessage(String validateActualAttachedToBudgetMessage) {
		m_strValidateActualAttachedToBudgetMessage = validateActualAttachedToBudgetMessage;
	}
	
	public ArrayList<EventApplicationDVO> getM_selectedEventApplication() {
		return m_selectedEventApplication;
	}

	public void setM_selectedEventApplication(ArrayList<EventApplicationDVO> eventApplication) {
		m_selectedEventApplication = eventApplication;
	}

	public Boolean getM_bolgbEntireEvt() {
		return m_bolgbEntireEvt;
	}

	public void setM_bolgbEntireEvt(Boolean entireEvt) {
		m_bolgbEntireEvt = entireEvt;
	}

	public Boolean getM_bolgbAllApp() {
		return m_bolgbAllApp;
	}

	public void setM_bolgbAllApp(Boolean allApp) {
		m_bolgbAllApp = allApp;
	}

	public Boolean getM_bolgbBaseCost() {
		return m_bolgbBaseCost;
	}

	public void setM_bolgbBaseCost(Boolean baseCost) {
		m_bolgbBaseCost = baseCost;
	}

	public Boolean getM_bolgbFeature() {
		return m_bolgbFeature;
	}

	public void setM_bolgbFeature(Boolean feature) {
		m_bolgbFeature = feature;
	}

	public Boolean getM_bolgbAllInv() {
		return m_bolgbAllInv;
	}

	public void setM_bolgbAllInv(Boolean allInv) {
		m_bolgbAllInv = allInv;
	}

	public Boolean getM_bolgbComHamInv() {
		return m_bolgbComHamInv;
	}

	public void setM_bolgbComHamInv(Boolean comHamInv) {
		m_bolgbComHamInv = comHamInv;
	}

	public Boolean getM_bolgbExclHamInv() {
		return m_bolgbExclHamInv;
	}

	public void setM_bolgbExclHamInv(Boolean exclHamInv) {
		m_bolgbExclHamInv = exclHamInv;
	}

	public Boolean getM_bolgbExclSupInv() {
		return m_bolgbExclSupInv;
	}

	public void setM_bolgbExclSupInv(Boolean exclSupInv) {
		m_bolgbExclSupInv = exclSupInv;
	}

	public Boolean getM_bolgbDrawCost() {
		return m_bolgbDrawCost;
	}

	public void setM_bolgbDrawCost(Boolean drawCost) {
		m_bolgbDrawCost = drawCost;
	}

	public Boolean getM_bolgbAllSupCost() {
		return m_bolgbAllSupCost;
	}

	public void setM_bolgbAllSupCost(Boolean allSupCost) {
		m_bolgbAllSupCost = allSupCost;
	}

	public Boolean getM_bolgbTgtCost() {
		return m_bolgbTgtCost;
	}

	public void setM_bolgbTgtCost(Boolean tgtCost) {
		m_bolgbTgtCost = tgtCost;
	}

	public Boolean getM_bolgbDataCheckOk() {
		return m_bolgbDataCheckOk;
	}

	public void setM_bolgbDataCheckOk(Boolean dataCheckOk) {
		m_bolgbDataCheckOk = dataCheckOk;
	}

	public Boolean getM_bolAddNewEnabled() {
		return m_bolAddNewEnabled;
	}

	public void setM_bolAddNewEnabled(Boolean addNewEnabled) {
		m_bolAddNewEnabled = addNewEnabled;
	}

	public Boolean getM_bolApplyEnabled() {
		return m_bolApplyEnabled;
	}

	public void setM_bolApplyEnabled(Boolean applyEnabled) {
		m_bolApplyEnabled = applyEnabled;
	}

	public Boolean getM_bolDeleteEnabled() {
		return m_bolDeleteEnabled;
	}

	public void setM_bolDeleteEnabled(Boolean deleteEnabled) {
		m_bolDeleteEnabled = deleteEnabled;
	}

	public Boolean getM_bolCopyEnabled() {
		return m_bolCopyEnabled;
	}

	public void setM_bolCopyEnabled(Boolean copyEnabled) {
		m_bolCopyEnabled = copyEnabled;
	}

	public Boolean getM_bolEditableGrid() {
		return m_bolEditableGrid;
	}

	public void setM_bolEditableGrid(Boolean editableGrid) {
		m_bolEditableGrid = editableGrid;
	}

	public Boolean getM_bolDetailsEnabled() {
		return m_bolDetailsEnabled;
	}

	public void setM_bolDetailsEnabled(Boolean detailsEnabled) {
		m_bolDetailsEnabled = detailsEnabled;
	}

	public List getM_eventApplicationListDlg() {
		return m_eventApplicationListDlg;
	}

	public void setM_eventApplicationListDlg(List applicationListDlg) {
		m_eventApplicationListDlg = applicationListDlg;
	}

	public EventApplicationDVO getM_selectedApplicationDlg() {
		return m_selectedApplicationDlg;
	}

	public void setM_selectedApplicationDlg(EventApplicationDVO applicationDlg) {
		m_selectedApplicationDlg = applicationDlg;
	}

	public Boolean getM_bolEnableAddDlg() {
		return m_bolEnableAddDlg;
	}

	public void setM_bolEnableAddDlg(Boolean enableAddDlg) {
		m_bolEnableAddDlg = enableAddDlg;
	}

	public Boolean getM_bolEnableRemoveDlg() {
		return m_bolEnableRemoveDlg;
	}

	public void setM_bolEnableRemoveDlg(Boolean enableRemoveDlg) {
		m_bolEnableRemoveDlg = enableRemoveDlg;
	}

	public Boolean getM_bolEnablePartNumberDlg() {
		return m_bolEnablePartNumberDlg;
	}

	public void setM_bolEnablePartNumberDlg(Boolean enablePartNumberDlg) {
		m_bolEnablePartNumberDlg = enablePartNumberDlg;
	}

	public Boolean getM_bolEnableSupplierNumberDlg() {
		return m_bolEnableSupplierNumberDlg;
	}

	public void setM_bolEnableSupplierNumberDlg(Boolean enableSupplierNumberDlg) {
		m_bolEnableSupplierNumberDlg = enableSupplierNumberDlg;
	}

	public Boolean getM_bolEnableSupplierSearchDlg() {
		return m_bolEnableSupplierSearchDlg;
	}

	public void setM_bolEnableSupplierSearchDlg(Boolean enableSupplierSearchDlg) {
		m_bolEnableSupplierSearchDlg = enableSupplierSearchDlg;
	}

	public Boolean getM_bolEnablePartColorDlg() {
		return m_bolEnablePartColorDlg;
	}

	public void setM_bolEnablePartColorDlg(Boolean enablePartColorDlg) {
		m_bolEnablePartColorDlg = enablePartColorDlg;
	}

	public Boolean getM_bolEnableTargetPlantDlg() {
		return m_bolEnableTargetPlantDlg;
	}

	public void setM_bolEnableTargetPlantDlg(Boolean enableTargetPlantDlg) {
		m_bolEnableTargetPlantDlg = enableTargetPlantDlg;
	}

	public Boolean getM_bolEnableModelDlg() {
		return m_bolEnableModelDlg;
	}

	public void setM_bolEnableModelDlg(Boolean enableModelDlg) {
		m_bolEnableModelDlg = enableModelDlg;
	}

	public Boolean getM_bolEnableMTCTypeDlg() {
		return m_bolEnableMTCTypeDlg;
	}

	public void setM_bolEnableMTCTypeDlg(Boolean enableMTCTypeDlg) {
		m_bolEnableMTCTypeDlg = enableMTCTypeDlg;
	}

	public Boolean getM_bolEnableMTCOptionDlg() {
		return m_bolEnableMTCOptionDlg;
	}

	public void setM_bolEnableMTCOptionDlg(Boolean enableMTCOptionDlg) {
		m_bolEnableMTCOptionDlg = enableMTCOptionDlg;
	}

	public Boolean getM_bolEnableCategoryDlg() {
		return m_bolEnableCategoryDlg;
	}

	public void setM_bolEnableCategoryDlg(Boolean enableCategoryDlg) {
		m_bolEnableCategoryDlg = enableCategoryDlg;
	}

	public Boolean getM_bolEnablePartSectionDlg() {
		return m_bolEnablePartSectionDlg;
	}

	public void setM_bolEnablePartSectionDlg(Boolean enablePartSectionDlg) {
		m_bolEnablePartSectionDlg = enablePartSectionDlg;
	}

	public Boolean getM_bolEnableApplyDlg() {
		return m_bolEnableApplyDlg;
	}

	public void setM_bolEnableApplyDlg(Boolean enableApplyDlg) {
		m_bolEnableApplyDlg = enableApplyDlg;
	}

	public Boolean getM_bolEnableDeleteDlg() {
		return m_bolEnableDeleteDlg;
	}

	public void setM_bolEnableDeleteDlg(Boolean enableDeleteDlg) {
		m_bolEnableDeleteDlg = enableDeleteDlg;
	}

	public Boolean getM_bolEnableOkDlg() {
		return m_bolEnableOkDlg;
	}

	public void setM_bolEnableOkDlg(Boolean enableOkDlg) {
		m_bolEnableOkDlg = enableOkDlg;
	}

	public String getM_strEventNameDlg() {
		return m_strEventNameDlg;
	}

	public void setM_strEventNameDlg(String eventNameDlg) {
		m_strEventNameDlg = eventNameDlg;
	}

	public String getM_strEventNameDescDlg() {
		return m_strEventNameDescDlg;
	}

	public void setM_strEventNameDescDlg(String eventNameDescDlg) {
		m_strEventNameDescDlg = eventNameDescDlg;
	}

	public String getM_strPartNumberDlg() {
		return m_strPartNumberDlg;
	}

	public void setM_strPartNumberDlg(String partNumberDlg) {
		m_strPartNumberDlg = partNumberDlg;
	}

	public String getM_strPartColorDlg() {
		return m_strPartColorDlg;
	}

	public void setM_strPartColorDlg(String partColorDlg) {
		m_strPartColorDlg = partColorDlg;
	}

	public String getM_strTargetPlantDlg() {
		return m_strTargetPlantDlg;
	}

	public void setM_strTargetPlantDlg(String targetPlantDlg) {
		m_strTargetPlantDlg = targetPlantDlg;
	}

	public String getM_strSupplierDlg() {
		return m_strSupplierDlg;
	}

	public void setM_strSupplierDlg(String supplierDlg) {
		m_strSupplierDlg = supplierDlg;
	}

	public String getM_strDesignSectionDlg() {
		return m_strDesignSectionDlg;
	}

	public void setM_strDesignSectionDlg(String designSectionDlg) {
		m_strDesignSectionDlg = designSectionDlg;
	}

	public String getM_strModelDlg() {
		return m_strModelDlg;
	}

	public void setM_strModelDlg(String modelDlg) {
		m_strModelDlg = modelDlg;
	}

	public String getM_strMTCTypeDlg() {
		return m_strMTCTypeDlg;
	}

	public void setM_strMTCTypeDlg(String typeDlg) {
		m_strMTCTypeDlg = typeDlg;
	}

	public String getM_strMTCOptionDlg() {
		return m_strMTCOptionDlg;
	}

	public void setM_strMTCOptionDlg(String optionDlg) {
		m_strMTCOptionDlg = optionDlg;
	}

	public String getM_strCategoryDlg() {
		return m_strCategoryDlg;
	}

	public void setM_strCategoryDlg(String categoryDlg) {
		m_strCategoryDlg = categoryDlg;
	}

	public String getM_strOperatorDlg() {
		return m_strOperatorDlg;
	}

	public void setM_strOperatorDlg(String operatorDlg) {
		m_strOperatorDlg = operatorDlg;
	}

	public String getM_strPartNameDlg() {
		return m_strPartNameDlg;
	}

	public void setM_strPartNameDlg(String partNameDlg) {
		m_strPartNameDlg = partNameDlg;
	}

	public String getM_strSupplierNameDlg() {
		return m_strSupplierNameDlg;
	}

	public void setM_strSupplierNameDlg(String supplierNameDlg) {
		m_strSupplierNameDlg = supplierNameDlg;
	}

	public BigDecimal getM_decEventRevisionNoDlg() {
		return m_decEventRevisionNoDlg;
	}

	public void setM_decEventRevisionNoDlg(BigDecimal eventRevisionNoDlg) {
		m_decEventRevisionNoDlg = eventRevisionNoDlg;
	}

	public String getM_strEventTypeDlg() {
		return m_strEventTypeDlg;
	}

	public void setM_strEventTypeDlg(String eventTypeDlg) {
		m_strEventTypeDlg = eventTypeDlg;
	}

	public LinkedHashMap getM_hmpPartColorDlg() {
		return m_hmpPartColorDlg;
	}

	public void setM_hmpPartColorDlg(LinkedHashMap partColorDlg) {
		m_hmpPartColorDlg = partColorDlg;
	}

	public LinkedHashMap getM_hmpTargetPlantDlg() {
		return m_hmpTargetPlantDlg;
	}

	public void setM_hmpTargetPlantDlg(LinkedHashMap targetPlantDlg) {
		m_hmpTargetPlantDlg = targetPlantDlg;
	}

	public LinkedHashMap getM_hmpDesignSectionDlg() {
		return m_hmpDesignSectionDlg;
	}

	public void setM_hmpDesignSectionDlg(LinkedHashMap designSectionDlg) {
		m_hmpDesignSectionDlg = designSectionDlg;
	}

	public LinkedHashMap getM_hmpModelDlg() {
		return m_hmpModelDlg;
	}

	public void setM_hmpModelDlg(LinkedHashMap modelDlg) {
		m_hmpModelDlg = modelDlg;
	}

	public LinkedHashMap getM_hmpMTCTypeDlg() {
		return m_hmpMTCTypeDlg;
	}

	public void setM_hmpMTCTypeDlg(LinkedHashMap typeDlg) {
		m_hmpMTCTypeDlg = typeDlg;
	}

	public LinkedHashMap getM_hmpMTCOptionDlg() {
		return m_hmpMTCOptionDlg;
	}

	public void setM_hmpMTCOptionDlg(LinkedHashMap optionDlg) {
		m_hmpMTCOptionDlg = optionDlg;
	}

	public LinkedHashMap getM_hmpCategoryDlg() {
		return m_hmpCategoryDlg;
	}

	public void setM_hmpCategoryDlg(LinkedHashMap categoryDlg) {
		m_hmpCategoryDlg = categoryDlg;
	}

	public Boolean getM_bolChkAssociation() {
		return m_bolChkAssociation;
	}

	public void setM_bolChkAssociation(Boolean chkAssociation) {
		m_bolChkAssociation = chkAssociation;
	}

	public List getM_availableFeaturesList() {
		return m_availableFeaturesList;
	}

	public void setM_availableFeaturesList(List featuresList) {
		m_availableFeaturesList = featuresList;
	}

	public List getM_assignedFeaturesList() {
		return m_assignedFeaturesList;
	}

	public void setM_assignedFeaturesList(List featuresList) {
		m_assignedFeaturesList = featuresList;
	}

	/**
	 * @return the lookupBean
	 */
	public LookupBean getLookupBean() {
		return lookupBean;
	}

	/**
	 * @param lookupBean the lookupBean to set
	 */
	public void setLookupBean(LookupBean lookupBean) {
		this.lookupBean = lookupBean;
	}

	/**
	 * @return the m_selectedAssignedFeature
	 */
	public CodeDVO getM_selectedAssignedFeature() {
		return m_selectedAssignedFeature;
	}

	/**
	 * @param assignedFeature the m_selectedAssignedFeature to set
	 */
	public void setM_selectedAssignedFeature(CodeDVO assignedFeature) {
		m_selectedAssignedFeature = assignedFeature;
	}

	/**
	 * @return the m_selectedAvailableFeature
	 */
	public CodeDVO getM_selectedAvailableFeature() {
		return m_selectedAvailableFeature;
	}

	/**
	 * @param availableFeature the m_selectedAvailableFeature to set
	 */
	public void setM_selectedAvailableFeature(CodeDVO availableFeature) {
		m_selectedAvailableFeature = availableFeature;
	}

	/**
	 * @return the validationMsg
	 */
	public String getValidationMsg() {
		return validationMsg;
	}

	/**
	 * @param validationMsg the validationMsg to set
	 */
	public void setValidationMsg(String validationMsg) {
		this.validationMsg = validationMsg;
	}

	/**
	 * @return the focusField
	 */
	public String getFocusField() {
		return focusField;
	}

	/**
	 * @param focusField the focusField to set
	 */
	public void setFocusField(String focusField) {
		this.focusField = focusField;
	}

	/**
	 * @return the m_strOldTargetPlantDlg
	 */
	public String getM_strOldTargetPlantDlg() {
		return m_strOldTargetPlantDlg;
	}

	/**
	 * @param oldTargetPlantDlg the m_strOldTargetPlantDlg to set
	 */
	public void setM_strOldTargetPlantDlg(String oldTargetPlantDlg) {
		m_strOldTargetPlantDlg = oldTargetPlantDlg;
	}

	/**
	 * @return the m_strOldDesignSectionDlg
	 */
	public String getM_strOldDesignSectionDlg() {
		return m_strOldDesignSectionDlg;
	}

	/**
	 * @param oldDesignSectionDlg the m_strOldDesignSectionDlg to set
	 */
	public void setM_strOldDesignSectionDlg(String oldDesignSectionDlg) {
		m_strOldDesignSectionDlg = oldDesignSectionDlg;
	}

	/**
	 * @return the m_strOldModelDlg
	 */
	public String getM_strOldModelDlg() {
		return m_strOldModelDlg;
	}

	/**
	 * @param oldModelDlg the m_strOldModelDlg to set
	 */
	public void setM_strOldModelDlg(String oldModelDlg) {
		m_strOldModelDlg = oldModelDlg;
	}

	/**
	 * @return the m_strOldMTCTypeDlg
	 */
	public String getM_strOldMTCTypeDlg() {
		return m_strOldMTCTypeDlg;
	}

	/**
	 * @param oldMTCTypeDlg the m_strOldMTCTypeDlg to set
	 */
	public void setM_strOldMTCTypeDlg(String oldMTCTypeDlg) {
		m_strOldMTCTypeDlg = oldMTCTypeDlg;
	}

	/**
	 * @return the m_strOldMTCOptionDlg
	 */
	public String getM_strOldMTCOptionDlg() {
		return m_strOldMTCOptionDlg;
	}

	/**
	 * @param oldMTCOptionDlg the m_strOldMTCOptionDlg to set
	 */
	public void setM_strOldMTCOptionDlg(String oldMTCOptionDlg) {
		m_strOldMTCOptionDlg = oldMTCOptionDlg;
	}

	/**
	 * @return the m_strOldCategoryDlg
	 */
	public String getM_strOldCategoryDlg() {
		return m_strOldCategoryDlg;
	}

	/**
	 * @param oldCategoryDlg the m_strOldCategoryDlg to set
	 */
	public void setM_strOldCategoryDlg(String oldCategoryDlg) {
		m_strOldCategoryDlg = oldCategoryDlg;
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
		m_strShipToCode = shipToCode;
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
	 * @return the m_strAction
	 */
	public String getM_strAction() {
		return m_strAction;
	}

	/**
	 * @param action the m_strAction to set
	 */
	public void setM_strAction(String action) {
		m_strAction = action;
	}

	/**
	 * @return the m_bolShowFeatures
	 */
	public Boolean getM_bolShowFeatures() {
		return m_bolShowFeatures;
	}

	/**
	 * @param showFeatures the m_bolShowFeatures to set
	 */
	public void setM_bolShowFeatures(Boolean showFeatures) {
		m_bolShowFeatures = showFeatures;
	}

	/**
	 * @return the m_strMode
	 */
	public String getM_strMode() {
		return m_strMode;
	}

	/**
	 * @param mode the m_strMode to set
	 */
	public void setM_strMode(String mode) {
		m_strMode = mode;
	}


	/**
	 * @return the m_strEventNameCopy
	 */
	public String getM_strEventNameCopy() {
		return m_strEventNameCopy;
	}

	/**
	 * @param eventNameCopy the m_strEventNameCopy to set
	 */
	public void setM_strEventNameCopy(String eventNameCopy) {
		m_strEventNameCopy = eventNameCopy;
	}

	/**
	 * @return the m_strEventNameDescCopy
	 */
	public String getM_strEventNameDescCopy() {
		return m_strEventNameDescCopy;
	}

	/**
	 * @param eventNameDescCopy the m_strEventNameDescCopy to set
	 */
	public void setM_strEventNameDescCopy(String eventNameDescCopy) {
		m_strEventNameDescCopy = eventNameDescCopy;
	}

	/**
	 * @return the m_strPartNumberCopy
	 */
	public String getM_strPartNumberCopy() {
		return m_strPartNumberCopy;
	}

	/**
	 * @param partNumberCopy the m_strPartNumberCopy to set
	 */
	public void setM_strPartNumberCopy(String partNumberCopy) {
		m_strPartNumberCopy = partNumberCopy;
	}

	/**
	 * @return the m_strPartNameCopy
	 */
	public String getM_strPartNameCopy() {
		return m_strPartNameCopy;
	}

	/**
	 * @param partNameCopy the m_strPartNameCopy to set
	 */
	public void setM_strPartNameCopy(String partNameCopy) {
		m_strPartNameCopy = partNameCopy;
	}

	/**
	 * @return the m_strPartColorCopy
	 */
	public String getM_strPartColorCopy() {
		return m_strPartColorCopy;
	}

	/**
	 * @param partColorCopy the m_strPartColorCopy to set
	 */
	public void setM_strPartColorCopy(String partColorCopy) {
		m_strPartColorCopy = partColorCopy;
	}

	/**
	 * @return the m_strSupplierCopy
	 */
	public String getM_strSupplierCopy() {
		return m_strSupplierCopy;
	}

	/**
	 * @param supplierCopy the m_strSupplierCopy to set
	 */
	public void setM_strSupplierCopy(String supplierCopy) {
		m_strSupplierCopy = supplierCopy;
	}

	/**
	 * @return the m_strSupplierNameCopy
	 */
	public String getM_strSupplierNameCopy() {
		return m_strSupplierNameCopy;
	}

	/**
	 * @param supplierNameCopy the m_strSupplierNameCopy to set
	 */
	public void setM_strSupplierNameCopy(String supplierNameCopy) {
		m_strSupplierNameCopy = supplierNameCopy;
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
	 * @return the m_hmpTargetPlantFromCopy
	 */
	public LinkedHashMap getM_hmpTargetPlantFromCopy() {
		return m_hmpTargetPlantFromCopy;
	}

	/**
	 * @param targetPlantFromCopy the m_hmpTargetPlantFromCopy to set
	 */
	public void setM_hmpTargetPlantFromCopy(LinkedHashMap targetPlantFromCopy) {
		m_hmpTargetPlantFromCopy = targetPlantFromCopy;
	}

	/**
	 * @return the m_hmpDesignSectionFromCopy
	 */
	public LinkedHashMap getM_hmpDesignSectionFromCopy() {
		return m_hmpDesignSectionFromCopy;
	}

	/**
	 * @param designSectionFromCopy the m_hmpDesignSectionFromCopy to set
	 */
	public void setM_hmpDesignSectionFromCopy(LinkedHashMap designSectionFromCopy) {
		m_hmpDesignSectionFromCopy = designSectionFromCopy;
	}

	/**
	 * @return the m_hmpModelFromCopy
	 */
	public LinkedHashMap getM_hmpModelFromCopy() {
		return m_hmpModelFromCopy;
	}

	/**
	 * @param modelFromCopy the m_hmpModelFromCopy to set
	 */
	public void setM_hmpModelFromCopy(LinkedHashMap modelFromCopy) {
		m_hmpModelFromCopy = modelFromCopy;
	}

	/**
	 * @return the m_hmpShipToCodeFromCopy
	 */
	public LinkedHashMap getM_hmpShipToCodeFromCopy() {
		return m_hmpShipToCodeFromCopy;
	}

	/**
	 * @param shipToCodeFromCopy the m_hmpShipToCodeFromCopy to set
	 */
	public void setM_hmpShipToCodeFromCopy(LinkedHashMap shipToCodeFromCopy) {
		m_hmpShipToCodeFromCopy = shipToCodeFromCopy;
	}

	/**
	 * @return the m_hmpMTCTypeFromCopy
	 */
	public LinkedHashMap getM_hmpMTCTypeFromCopy() {
		return m_hmpMTCTypeFromCopy;
	}

	/**
	 * @param typeFromCopy the m_hmpMTCTypeFromCopy to set
	 */
	public void setM_hmpMTCTypeFromCopy(LinkedHashMap typeFromCopy) {
		m_hmpMTCTypeFromCopy = typeFromCopy;
	}

	/**
	 * @return the m_hmpMTCOptionFromCopy
	 */
	public LinkedHashMap getM_hmpMTCOptionFromCopy() {
		return m_hmpMTCOptionFromCopy;
	}

	/**
	 * @param optionFromCopy the m_hmpMTCOptionFromCopy to set
	 */
	public void setM_hmpMTCOptionFromCopy(LinkedHashMap optionFromCopy) {
		m_hmpMTCOptionFromCopy = optionFromCopy;
	}

	/**
	 * @return the m_hmpCategoryFromCopy
	 */
	public LinkedHashMap getM_hmpCategoryFromCopy() {
		return m_hmpCategoryFromCopy;
	}

	/**
	 * @param categoryFromCopy the m_hmpCategoryFromCopy to set
	 */
	public void setM_hmpCategoryFromCopy(LinkedHashMap categoryFromCopy) {
		m_hmpCategoryFromCopy = categoryFromCopy;
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
	 * @return the m_hmpTargetPlantToCopy
	 */
	public LinkedHashMap getM_hmpTargetPlantToCopy() {
		return m_hmpTargetPlantToCopy;
	}

	/**
	 * @param targetPlantToCopy the m_hmpTargetPlantToCopy to set
	 */
	public void setM_hmpTargetPlantToCopy(LinkedHashMap targetPlantToCopy) {
		m_hmpTargetPlantToCopy = targetPlantToCopy;
	}

	/**
	 * @return the m_hmpDesignSectionToCopy
	 */
	public LinkedHashMap getM_hmpDesignSectionToCopy() {
		return m_hmpDesignSectionToCopy;
	}

	/**
	 * @param designSectionToCopy the m_hmpDesignSectionToCopy to set
	 */
	public void setM_hmpDesignSectionToCopy(LinkedHashMap designSectionToCopy) {
		m_hmpDesignSectionToCopy = designSectionToCopy;
	}

	/**
	 * @return the m_hmpModelToCopy
	 */
	public LinkedHashMap getM_hmpModelToCopy() {
		return m_hmpModelToCopy;
	}

	/**
	 * @param modelToCopy the m_hmpModelToCopy to set
	 */
	public void setM_hmpModelToCopy(LinkedHashMap modelToCopy) {
		m_hmpModelToCopy = modelToCopy;
	}

	/**
	 * @return the m_hmpShipToCodeToCopy
	 */
	public LinkedHashMap getM_hmpShipToCodeToCopy() {
		return m_hmpShipToCodeToCopy;
	}

	/**
	 * @param shipToCodeToCopy the m_hmpShipToCodeToCopy to set
	 */
	public void setM_hmpShipToCodeToCopy(LinkedHashMap shipToCodeToCopy) {
		m_hmpShipToCodeToCopy = shipToCodeToCopy;
	}

	/**
	 * @return the m_hmpMTCTypeToCopy
	 */
	public LinkedHashMap getM_hmpMTCTypeToCopy() {
		return m_hmpMTCTypeToCopy;
	}

	/**
	 * @param typeToCopy the m_hmpMTCTypeToCopy to set
	 */
	public void setM_hmpMTCTypeToCopy(LinkedHashMap typeToCopy) {
		m_hmpMTCTypeToCopy = typeToCopy;
	}

	/**
	 * @return the m_hmpMTCOptionToCopy
	 */
	public LinkedHashMap getM_hmpMTCOptionToCopy() {
		return m_hmpMTCOptionToCopy;
	}

	/**
	 * @param optionToCopy the m_hmpMTCOptionToCopy to set
	 */
	public void setM_hmpMTCOptionToCopy(LinkedHashMap optionToCopy) {
		m_hmpMTCOptionToCopy = optionToCopy;
	}

	/**
	 * @return the m_hmpCategoryToCopy
	 */
	public LinkedHashMap getM_hmpCategoryToCopy() {
		return m_hmpCategoryToCopy;
	}

	/**
	 * @param categoryToCopy the m_hmpCategoryToCopy to set
	 */
	public void setM_hmpCategoryToCopy(LinkedHashMap categoryToCopy) {
		m_hmpCategoryToCopy = categoryToCopy;
	}

	/**
	 * @return the m_decEventRevisionNoCopy
	 */
	public BigDecimal getM_decEventRevisionNoCopy() {
		return m_decEventRevisionNoCopy;
	}

	/**
	 * @param eventRevisionNoCopy the m_decEventRevisionNoCopy to set
	 */
	public void setM_decEventRevisionNoCopy(BigDecimal eventRevisionNoCopy) {
		m_decEventRevisionNoCopy = eventRevisionNoCopy;
	}

	/**
	 * @return the m_hmpPartColorCopy
	 */
	public LinkedHashMap getM_hmpPartColorCopy() {
		return m_hmpPartColorCopy;
	}

	/**
	 * @param partColorCopy the m_hmpPartColorCopy to set
	 */
	public void setM_hmpPartColorCopy(LinkedHashMap partColorCopy) {
		m_hmpPartColorCopy = partColorCopy;
	}

	/**
	 * @return the m_selectedAssignedFeatureCopy
	 */
	public CodeDVO getM_selectedAssignedFeatureCopy() {
		return m_selectedAssignedFeatureCopy;
	}

	/**
	 * @param assignedFeatureCopy the m_selectedAssignedFeatureCopy to set
	 */
	public void setM_selectedAssignedFeatureCopy(CodeDVO assignedFeatureCopy) {
		m_selectedAssignedFeatureCopy = assignedFeatureCopy;
	}

	/**
	 * @return the m_selectedAvailableFeatureCopy
	 */
	public CodeDVO getM_selectedAvailableFeatureCopy() {
		return m_selectedAvailableFeatureCopy;
	}

	/**
	 * @param availableFeatureCopy the m_selectedAvailableFeatureCopy to set
	 */
	public void setM_selectedAvailableFeatureCopy(CodeDVO availableFeatureCopy) {
		m_selectedAvailableFeatureCopy = availableFeatureCopy;
	}

	/**
	 * @return the m_availableFeaturesListCopy
	 */
	public List getM_availableFeaturesListCopy() {
		return m_availableFeaturesListCopy;
	}

	/**
	 * @param featuresListCopy the m_availableFeaturesListCopy to set
	 */
	public void setM_availableFeaturesListCopy(List featuresListCopy) {
		m_availableFeaturesListCopy = featuresListCopy;
	}

	/**
	 * @return the m_assignedFeaturesListCopy
	 */
	public List getM_assignedFeaturesListCopy() {
		return m_assignedFeaturesListCopy;
	}

	/**
	 * @param featuresListCopy the m_assignedFeaturesListCopy to set
	 */
	public void setM_assignedFeaturesListCopy(List featuresListCopy) {
		m_assignedFeaturesListCopy = featuresListCopy;
	}

	/**
	 * @return the m_bolEnableRemoveCopy
	 */
	public Boolean getM_bolEnableRemoveCopy() {
		return m_bolEnableRemoveCopy;
	}

	/**
	 * @param enableRemoveCopy the m_bolEnableRemoveCopy to set
	 */
	public void setM_bolEnableRemoveCopy(Boolean enableRemoveCopy) {
		m_bolEnableRemoveCopy = enableRemoveCopy;
	}

	/**
	 * @return the m_bolEnableAddCopy
	 */
	public Boolean getM_bolEnableAddCopy() {
		return m_bolEnableAddCopy;
	}

	/**
	 * @param enableAddCopy the m_bolEnableAddCopy to set
	 */
	public void setM_bolEnableAddCopy(Boolean enableAddCopy) {
		m_bolEnableAddCopy = enableAddCopy;
	}

	/**
	 * @return the m_selectedApplicationCopy
	 */
	public EventApplicationDVO getM_selectedApplicationCopy() {
		return m_selectedApplicationCopy;
	}

	/**
	 * @param applicationCopy the m_selectedApplicationCopy to set
	 */
	public void setM_selectedApplicationCopy(EventApplicationDVO applicationCopy) {
		m_selectedApplicationCopy = applicationCopy;
	}

	/**
	 * @return the m_eventApplicationListCopy
	 */
	public List getM_eventApplicationListCopy() {
		return m_eventApplicationListCopy;
	}

	/**
	 * @param applicationListCopy the m_eventApplicationListCopy to set
	 */
	public void setM_eventApplicationListCopy(List applicationListCopy) {
		m_eventApplicationListCopy = applicationListCopy;
	}

	/**
	 * @return the checkFlagMap
	 */
	public HashMap<String, Boolean> getCheckFlagMap() {
		return checkFlagMap;
	}

	/**
	 * @param checkFlagMap the checkFlagMap to set
	 */
	public void setCheckFlagMap(HashMap<String, Boolean> checkFlagMap) {
		this.checkFlagMap = checkFlagMap;
	}

	/**
	 * @return the m_strParentPage
	 */
	public String getM_strParentPage() {
		return m_strParentPage;
	}

	/**
	 * @param parentPage the m_strParentPage to set
	 */
	public void setM_strParentPage(String parentPage) {
		m_strParentPage = parentPage;
	}


	/**
	 * @return the m_strDummyEventName
	 */
	public String getM_strDummyEventName() {
		return m_strDummyEventName;
	}

	/**
	 * @param dummyEventName the m_strDummyEventName to set
	 */
	public void setM_strDummyEventName(String dummyEventName) {
		m_strDummyEventName = dummyEventName;
	}

	/**
	 * @return the m_bolEnablePartSearchDlg
	 */
	public Boolean getM_bolEnablePartSearchDlg() {
		return m_bolEnablePartSearchDlg;
	}

	/**
	 * @param enablePartSearchDlg the m_bolEnablePartSearchDlg to set
	 */
	public void setM_bolEnablePartSearchDlg(Boolean enablePartSearchDlg) {
		m_bolEnablePartSearchDlg = enablePartSearchDlg;
	}

	/**
	 * @return the m_intRate
	 */
	public Integer getM_intRate() {
		return m_intRate;
	}

	/**
	 * @param rate the m_intRate to set
	 */
	public void setM_intRate(Integer rate) {
		m_intRate = rate;
		
	}

	/**
	 * @return the m_intQty
	 */
	public Integer getM_intQty() {
		return m_intQty;
	}

	/**
	 * @param qty the m_intQty to set
	 */
	public void setM_intQty(Integer qty) {
		m_intQty = qty;
	}

	/**
	 * @return the m_intQtyDlg
	 */
	public Integer getM_intQtyDlg() {
		return m_intQtyDlg;
	}

	/**
	 * @param qtyDlg the m_intQtyDlg to set
	 */
	public void setM_intQtyDlg(Integer qtyDlg) {
		m_intQtyDlg = qtyDlg;
	}

	/**
	 * @return the m_intRateDlg
	 */


	/**
	 * @param rateDlg the m_intRateDlg to set
	 */


	/**
	 * @return the m_intQtyFromCopy
	 */
	public Integer getM_intQtyFromCopy() {
		return m_intQtyFromCopy;
	}

	/**
	 * @param qtyFromCopy the m_intQtyFromCopy to set
	 */
	public void setM_intQtyFromCopy(Integer qtyFromCopy) {
		m_intQtyFromCopy = qtyFromCopy;
	}

	/**
	 * @return the m_intShareFromCopy
	 */
	public Integer getM_intShareFromCopy() {
		return m_intShareFromCopy;
	}

	/**
	 * @param shareFromCopy the m_intShareFromCopy to set
	 */
	public void setM_intShareFromCopy(Integer shareFromCopy) {
		m_intShareFromCopy = shareFromCopy;
	}

	/**
	 * @return the m_intQtyToCopy
	 */
	public Integer getM_intQtyToCopy() {
		return m_intQtyToCopy;
	}

	/**
	 * @param qtyToCopy the m_intQtyToCopy to set
	 */
	public void setM_intQtyToCopy(Integer qtyToCopy) {
		m_intQtyToCopy = qtyToCopy;
	}

	/**
	 * @return the m_intShareToCopy
	 */
	public Integer getM_intShareToCopy() {
		return m_intShareToCopy;
	}

	/**
	 * @param shareToCopy the m_intShareToCopy to set
	 */
	public void setM_intShareToCopy(Integer shareToCopy) {
		m_intShareToCopy = shareToCopy;
	}

	/**
	 * @return the m_bolIsGridEmpty
	 */
	public Boolean getM_bolIsGridEmpty() {
		return m_bolIsGridEmpty;
	}

	/**
	 * @param isGridEmpty the m_bolIsGridEmpty to set
	 */
	public void setM_bolIsGridEmpty(Boolean isGridEmpty) {
		m_bolIsGridEmpty = isGridEmpty;
	}

	/**
	 * @return the m_selectedEventPart
	 */
	public EventPartDVO getM_selectedEventPart() {
		return m_selectedEventPart;
	}

	/**
	 * @param eventPart the m_selectedEventPart to set
	 */
	public void setM_selectedEventPart(EventPartDVO eventPart) {
		m_selectedEventPart = eventPart;
	}

	/**
	 * @return the tabActiveIndex
	 */
	public Integer getTabActiveIndex() {
		return tabActiveIndex;
	}

	/**
	 * @param tabActiveIndex the tabActiveIndex to set
	 */
	public void setTabActiveIndex(Integer tabActiveIndex) {
		this.tabActiveIndex = tabActiveIndex;
	}

	/**
	 * @return the m_hmpSelectedEventApplicationDetails
	 */
	public Map<String, EventApplicationDVO> getM_hmpSelectedEventApplicationDetails() {
		return m_hmpSelectedEventApplicationDetails;
	}

	/**
	 * @param selectedEventApplicationDetails the m_hmpSelectedEventApplicationDetails to set
	 */
	public void setM_hmpSelectedEventApplicationDetails(
			Map<String, EventApplicationDVO> selectedEventApplicationDetails) {
		m_hmpSelectedEventApplicationDetails = selectedEventApplicationDetails;
	}

	public Boolean getM_bolSummaryEnabled() {
		return m_bolSummaryEnabled;
	}

	public void setM_bolSummaryEnabled(Boolean m_bolSummaryEnabled) {
		this.m_bolSummaryEnabled = m_bolSummaryEnabled;
	}

	/**
	 * @return the m_displayMessage
	 */
	public FacesMessage getM_displayMessage() {
		return m_displayMessage;
	}

	/**
	 * @param message the m_displayMessage to set
	 */
	public void setM_displayMessage(FacesMessage message) {
		m_displayMessage = message;
	}

	/**
	 * @return the m_hmpModelFromCopyOne
	 */
	public LinkedHashMap getM_hmpModelFromCopyOne() {
		return m_hmpModelFromCopyOne;
	}

	/**
	 * @param modelFromCopyOne the m_hmpModelFromCopyOne to set
	 */
	public void setM_hmpModelFromCopyOne(LinkedHashMap modelFromCopyOne) {
		m_hmpModelFromCopyOne = modelFromCopyOne;
	}

	/**
	 * @return the m_hmpMTCTypeFromCopyOne
	 */
	public LinkedHashMap getM_hmpMTCTypeFromCopyOne() {
		return m_hmpMTCTypeFromCopyOne;
	}

	/**
	 * @param typeFromCopyOne the m_hmpMTCTypeFromCopyOne to set
	 */
	public void setM_hmpMTCTypeFromCopyOne(LinkedHashMap typeFromCopyOne) {
		m_hmpMTCTypeFromCopyOne = typeFromCopyOne;
	}

	/**
	 * @return the m_hmpCategoryFromCopyOne
	 */
	public LinkedHashMap getM_hmpCategoryFromCopyOne() {
		return m_hmpCategoryFromCopyOne;
	}

	/**
	 * @param categoryFromCopyOne the m_hmpCategoryFromCopyOne to set
	 */
	public void setM_hmpCategoryFromCopyOne(LinkedHashMap categoryFromCopyOne) {
		m_hmpCategoryFromCopyOne = categoryFromCopyOne;
	}

	/**
	 * @return the m_bolEventFreeze
	 */
	public Boolean getM_bolEventFreeze() {
		return m_bolEventFreeze;
	}

	/**
	 * @param eventFreeze the m_bolEventFreeze to set
	 */
	public void setM_bolEventFreeze(Boolean eventFreeze) {
		m_bolEventFreeze = eventFreeze;
	}

	/**
	 * @return the m_bQtyDlg
	 */
	public BigDecimal getM_bQtyDlg() {
		return m_bQtyDlg;
	}

	/**
	 * @param qtyDlg the m_bQtyDlg to set
	 */
	public void setM_bQtyDlg(BigDecimal qtyDlg) {
		m_bQtyDlg = qtyDlg;
	}

	/**
	 * @return the m_bRateDlg
	 */
	public BigDecimal getM_bRateDlg() {
		return m_bRateDlg;
	}

	/**
	 * @param rateDlg the m_bRateDlg to set
	 */
	public void setM_bRateDlg(BigDecimal rateDlg) {
		m_bRateDlg = rateDlg;
	}

	/**
	 * @return the m_bOldValueFlag
	 */
	public Boolean getM_bOldValueFlag() {
		return m_bOldValueFlag;
	}

	/**
	 * @param oldValueFlag the m_bOldValueFlag to set
	 */
	public void setM_bOldValueFlag(Boolean oldValueFlag) {
		m_bOldValueFlag = oldValueFlag;
	}

	/**
	 * @return the m_strShareToCopy
	 */
	public String getM_strShareToCopy() {
		return m_strShareToCopy;
	}

	/**
	 * @param shareToCopy the m_strShareToCopy to set
	 */
	public void setM_strShareToCopy(String shareToCopy) {
		m_strShareToCopy = shareToCopy;
	}

	/**
	 * @return the m_bolShowCopyBtn
	 */
	public Boolean getM_bolShowCopyBtn() {
		return m_bolShowCopyBtn;
	}

	/**
	 * @param showCopyBtn the m_bolShowCopyBtn to set
	 */
	public void setM_bolShowCopyBtn(Boolean showCopyBtn) {
		m_bolShowCopyBtn = showCopyBtn;
	}

	/**
	 * @return the isvalid
	 */
	public Boolean getIsvalid() {
		return isvalid;
	}

	/**
	 * @param isvalid the isvalid to set
	 */
	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}

	public boolean isFromCcdScrn() {
		return fromCcdScrn;
	}

	public void setFromCcdScrn(boolean fromCcdScrn) {
		this.fromCcdScrn = fromCcdScrn;
	}
	
	public String getM_strIsSupplyToCopy() {
		return m_strIsSupplyToCopy;
	}

	public void setM_strIsSupplyToCopy(String isSupplyToCopy) {
		m_strIsSupplyToCopy = isSupplyToCopy;
	}
	
	public String getM_strIsSupplyFromCopy() {
		return m_strIsSupplyFromCopy;
	}

	public void setM_strIsSupplyFromCopy(String isSupplyFromCopy) {
		m_strIsSupplyFromCopy = isSupplyFromCopy;
	}

	/**
	 * Data table Check box Handling logic START
	 */
	
	/**
	 * This method is involed on check/ uncheck of the checkbox in the datatable.
	 * 
	 * m_bSelectAllChkBoxMaintained - This is variable is maintained as we have component issue for select all check box which takes it as selected always once it is selected, so if it is unchecked the value is still true at back end.
	 */
	public void handleIndividualChkBoxListener(){
		EventApplicationDVO eventApplicationDVO = (EventApplicationDVO)m_dtEventApplicationTable.getRowData();
	    boolean isSelected = (Boolean)eventApplicationDVO.isM_bSelected();
	    if(null!=m_eventApplicationList){
		    if(isSelected){//Check box is checked by user on screen
		    	m_nTotalSelectedRows++;//Adding the selected row count
		    	if(m_nTotalSelectedRows==m_eventApplicationList.size()){
		    		//Select Select All Check box 
		    		m_bSelectAllRecord=true;
		    		
		    		m_bSelectAllChkBoxMaintained=true;
		    		RequestContext.getCurrentInstance().update("searchEventApplicationForm:eventApplicationsTable");
		    	}
		    } else {//Check box is unchecked by user on screen
		    	m_nTotalSelectedRows--;//decreasing the selected row count
		    	if(m_nTotalSelectedRows==(m_eventApplicationList.size()-1)){
		    		//Un-select Select All Check box
		    		m_bSelectAllRecord=false;
			    	m_bSelectAllChkBoxMaintained=false;
			    	RequestContext.getCurrentInstance().update("searchEventApplicationForm:eventApplicationsTable");
		    	}
		    }
	    }
	}
	
	public void handleSelectAllChkBoxListener(){
		//This block is added to handle the double check box value - description mentioned in the method handleIndividualChkBoxListener() description.
		if(m_bSelectAllChkBoxMaintained){
			m_bSelectAllRecord=false;
		} else {
			m_bSelectAllRecord=true;
		}
	    if(m_bSelectAllRecord){
	    	m_bSelectAllChkBoxMaintained=true;
	    	m_nTotalSelectedRows = m_eventApplicationList.size();
	    	for (EventApplicationDVO eventApplicationDVO : m_eventApplicationList) {
	    		eventApplicationDVO.setM_bSelected(true);
			}
	    	RequestContext.getCurrentInstance().update("searchEventApplicationForm:eventApplicationsTable");
	    } else {
	    	m_bSelectAllChkBoxMaintained=false;
	    	m_nTotalSelectedRows = 0;
	    	for (EventApplicationDVO eventApplicationDVO : m_eventApplicationList) {
	    		eventApplicationDVO.setM_bSelected(false);
			}
	    	RequestContext.getCurrentInstance().update("searchEventApplicationForm:eventApplicationsTable");
	    }
	}
	
	/**
	 * Data table Check box Handling logic END
	 */
	

	public String getM_strViewMTO() {
		return m_strViewMTO;
	}

	public void setM_strViewMTO(String viewMTO) {
		m_strViewMTO = viewMTO;
	}

	public String getM_strIsCopyBOM() {
		return m_strIsCopyBOM;
	}

	public void setM_strIsCopyBOM(String isCopyBOM) {
		m_strIsCopyBOM = isCopyBOM;
	}
	
	public Boolean getM_bSelectAllRecord() {
		return m_bSelectAllRecord;
	}

	public void setM_bSelectAllRecord(Boolean selectAllRecord) {
		m_bSelectAllRecord = selectAllRecord;
	}

	public DataTable getM_dtEventApplicationTable() {
		return m_dtEventApplicationTable;
	}

	public void setM_dtEventApplicationTable(DataTable eventApplicationTable) {
		m_dtEventApplicationTable = eventApplicationTable;
	}

	public int getM_nTotalSelectedRows() {
		return m_nTotalSelectedRows;
	}

	public void setM_nTotalSelectedRows(int totalSelectedRows) {
		m_nTotalSelectedRows = totalSelectedRows;
	}

	public Boolean getM_bSelectAllChkBoxMaintained() {
		return m_bSelectAllChkBoxMaintained;
	}

	public void setM_bSelectAllChkBoxMaintained(Boolean selectAllChkBoxMaintained) {
		m_bSelectAllChkBoxMaintained = selectAllChkBoxMaintained;
	}

	public Boolean getM_bolEditableGridOnQtyRateChange() {
		return m_bolEditableGridOnQtyRateChange;
	}

	public void setM_bolEditableGridOnQtyRateChange(
			Boolean editableGridOnQtyRateChange) {
		m_bolEditableGridOnQtyRateChange = editableGridOnQtyRateChange;
	}
	private String m_strDesignSectionForSelected;

	public String getM_strDesignSectionForSelected() {
	    return m_strDesignSectionForSelected;
	}

	public void setM_strDesignSectionForSelected(String m_strDesignSectionForSelected) {
	    this.m_strDesignSectionForSelected = m_strDesignSectionForSelected;
	}
	private boolean m_bDesignSectionEnabled = false;

	public boolean isM_bDesignSectionEnabled() {
	    return m_bDesignSectionEnabled;
	}

	public void setM_bDesignSectionEnabled(boolean m_bDesignSectionEnabled) {
	    this.m_bDesignSectionEnabled = m_bDesignSectionEnabled;
	}
	public void onSectionChange(EventApplicationDVO eventApp) {

	    if (eventApp.getM_strSection() != null) {

	        String section = eventApp.getM_strSection();

	        if (section.contains("-")) {
	            section = section.substring(0, section.indexOf("-")).trim();
	        }

	        eventApp.setM_strSection(section);
	    }
	}
	
}
