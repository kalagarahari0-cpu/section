package com.honda.cart2.service.eventprocessing;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.cart2.bo.eventprocessing.EventApplicationBO;
import com.honda.cart2.common.dvo.RequestDVO;
import com.honda.cart2.common.dvo.ResponseDVO;
import com.honda.cart2.common.exception.ApplicationException;
import com.honda.cart2.common.service.CommonService;

/**
 * @author VCC49890
 *
 */
@Service
public class EventApplicationService extends CommonService{

	@Autowired
	// inject BO class object
	EventApplicationBO eventApplicationBO;
	
	public EventApplicationService() {
		
	}
	
	public ResponseDVO freezeCheck(RequestDVO requestDVO) {

		return eventApplicationBO.freezeCheck(requestDVO);
	}
	
	public ResponseDVO searchEventApplications(RequestDVO requestDVO){
		
		return eventApplicationBO.searchEventApplications(requestDVO); 
	}
	
	public ResponseDVO getPartName(RequestDVO requestDVO){
		
		return eventApplicationBO.getPartName(requestDVO);
	}
	
	public ResponseDVO getNewPartName(RequestDVO requestDVO){
		
		return eventApplicationBO.getNewPartName(requestDVO);
	}
	
	public ResponseDVO getSupplierName(RequestDVO requestDVO){
		
		return eventApplicationBO.getSupplierName(requestDVO); 
	}
	
	public ResponseDVO getAssignedFeatures(RequestDVO requestDVO){
		
		return eventApplicationBO.getAssignedFeatures(requestDVO);
	}
	
	public ResponseDVO getAvailableFeatures(RequestDVO requestDVO){
		
		return eventApplicationBO.getAvailableFeatures(requestDVO);
	}
	
	public ResponseDVO addAvailableFeature(RequestDVO requestDVO){
		
		return eventApplicationBO.addAvailableFeature(requestDVO);
	}
	
	public ResponseDVO removeAvailableFeature(RequestDVO requestDVO){
		
		return eventApplicationBO.removeAvailableFeature(requestDVO);
	}
	
	public ResponseDVO deleteBtnEventApplication(RequestDVO requestDVO){

		return eventApplicationBO.deleteBtnEventApplication(requestDVO);
	}
	
	public ResponseDVO deleteEventApplication(RequestDVO requestDVO){

		return eventApplicationBO.deleteEventApplication(requestDVO);
	}
	
	public ResponseDVO cancelBtnEventApplication(RequestDVO requestDVO){

		return eventApplicationBO.cancelBtnEventApplication(requestDVO);
	}
	
	public ResponseDVO updateEventApplication(RequestDVO requestDVO) throws ApplicationException{

		return eventApplicationBO.updateEventApplication(requestDVO);
	}
		
	public ResponseDVO checkPartSupplierValidation(RequestDVO requestDVO){
		
		return eventApplicationBO.checkPartSupplierValidation(requestDVO);
	}
	
	public ResponseDVO addEventApplication(RequestDVO requestDVO) throws Exception{
		
		return eventApplicationBO.addEventApplication(requestDVO);
	}
	
	public ResponseDVO getAssignedFeaturesCopy(RequestDVO requestDVO){
		
		return eventApplicationBO.getAssignedFeaturesCopy(requestDVO);
	}
	
	public ResponseDVO getAvailableFeaturesCopy(RequestDVO requestDVO){
		
		return eventApplicationBO.getAvailableFeaturesCopy(requestDVO);
	}
	
	public ResponseDVO getEventApplicationDetails(RequestDVO requestDVO) throws Exception{
		
		return eventApplicationBO.getEventApplicationDetails(requestDVO);
	}
	
	public ResponseDVO validatePlantMTOColor(RequestDVO requestDVO){
		
		return eventApplicationBO.validatePlantMTOColor(requestDVO);
	}
	
	public ResponseDVO performCopyEventApplication(RequestDVO requestDVO) throws Exception {

		return eventApplicationBO.performCopyEventApplication(requestDVO);
	}
	
	public ResponseDVO applyEventApplication(RequestDVO requestDVO){
		
		return eventApplicationBO.applyEventApplication(requestDVO);
	}
	
	public ResponseDVO applyEventApplicationToAttachedBudget(RequestDVO requestDVO){
		
		return eventApplicationBO.applyEventApplicationToAttachedBudget(requestDVO);
	}
	
	public ResponseDVO updateEventPartDetailsApplicationToAttachedBudget(RequestDVO requestDVO){
		
		return eventApplicationBO.updateEventPartDetailsApplicationToAttachedBudget(requestDVO);
	}
	
	public String validateEventIsActualOrBudget(RequestDVO requestDVO){
		
		return eventApplicationBO.validateEventIsActualOrBudget(requestDVO);
	}
	public String[] validateActEventIsAttachedToBudget(RequestDVO requestDVO){
		
		return eventApplicationBO.validateActEventIsAttachedToBudget(requestDVO);
	}
	public String validatePriorBudgetEventFreezCheck(RequestDVO requestDVO,String[] priorBudgetEvent){
		
		return eventApplicationBO.validatePriorBudgetEventFreezCheck(requestDVO,priorBudgetEvent);
	}


	public ResponseDVO getFEMDModel(RequestDVO requestDVO) {
		return eventApplicationBO.getFEMDModel(requestDVO);
	}
	
	public List<Map<String, Object>> populateCategoryOnModelSelection(String m_strModelDlg){
		
		return eventApplicationBO.populateCategoryOnModelSelection(m_strModelDlg);
	}
	
	public Integer checkSuppByPartAndEvent(String m_strPartNumber, String eventName, Integer m_decEventRevisionNo) throws ApplicationException {
		
		return eventApplicationBO.checkSuppByPartAndEvent(m_strPartNumber, eventName, m_decEventRevisionNo);
	}
}
