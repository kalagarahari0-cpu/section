package com.honda.cart2.bo.eventprocessing;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.honda.cart2.common.dvo.RequestDVO;
import com.honda.cart2.common.dvo.ResponseDVO;
import com.honda.cart2.common.exception.ApplicationException;
import com.honda.cart2.dataaccess.eventprocessing.EventApplicationDAO;


@Component
public class EventApplicationBO {
	
	private String CLASS_NAME = EventProcessingBO.class.getName();
	
	@Autowired
	EventApplicationDAO eventApplicationDAO ;

	public EventApplicationBO() {
		// TODO Auto-generated constructor stub
	}

	public ResponseDVO freezeCheck(RequestDVO requestDVO) {

		return eventApplicationDAO.freezeCheck(requestDVO);
	}
	
	public ResponseDVO searchEventApplications(RequestDVO requestDVO){
		
		return eventApplicationDAO.searchEventApplications(requestDVO); 
	}
	
	public ResponseDVO getPartName(RequestDVO requestDVO){
		
		return eventApplicationDAO.getPartName(requestDVO);
	}
	
	public ResponseDVO getNewPartName(RequestDVO requestDVO){
		
		return eventApplicationDAO.getNewPartName(requestDVO);
	}
	
	public ResponseDVO getSupplierName(RequestDVO requestDVO){
		
		return eventApplicationDAO.getSupplierName(requestDVO); 
	}
	
	public ResponseDVO getAssignedFeatures(RequestDVO requestDVO){
		
		return eventApplicationDAO.getAssignedFeatures(requestDVO);
	}
	
	public ResponseDVO getAvailableFeatures(RequestDVO requestDVO){
		
		return eventApplicationDAO.getAvailableFeatures(requestDVO);
	}
	
	public ResponseDVO addAvailableFeature(RequestDVO requestDVO){
		
		return eventApplicationDAO.addAvailableFeature(requestDVO);
	}
	
	public ResponseDVO removeAvailableFeature(RequestDVO requestDVO){
		
		return eventApplicationDAO.removeAvailableFeature(requestDVO);
	}
	
	
	public ResponseDVO deleteBtnEventApplication(RequestDVO requestDVO){

		return eventApplicationDAO.deleteBtnEventApplication(requestDVO);
	}
	
	public ResponseDVO deleteEventApplication(RequestDVO requestDVO){

		return eventApplicationDAO.deleteEventApplication(requestDVO);
	}
	
	public ResponseDVO cancelBtnEventApplication(RequestDVO requestDVO){

		return eventApplicationDAO.cancelBtnEventApplication(requestDVO);
	}
	
	public ResponseDVO updateEventApplication(RequestDVO requestDVO) throws ApplicationException{

		return eventApplicationDAO.updateEventApplication(requestDVO);
	}
	
	public ResponseDVO checkPartSupplierValidation(RequestDVO requestDVO){
		
		return eventApplicationDAO.checkPartSupplierValidation(requestDVO);
	}

	public ResponseDVO addEventApplication(RequestDVO requestDVO) throws Exception {
		
		return eventApplicationDAO.addEventApplication(requestDVO);
	}
	
	public ResponseDVO getAssignedFeaturesCopy(RequestDVO requestDVO){
		
		return eventApplicationDAO.getAssignedFeaturesCopy(requestDVO);
	}
	
	public ResponseDVO getAvailableFeaturesCopy(RequestDVO requestDVO){
		
		return eventApplicationDAO.getAvailableFeaturesCopy(requestDVO);
	}
	
	public ResponseDVO getEventApplicationDetails(RequestDVO requestDVO) throws Exception{
		
		return eventApplicationDAO.getEventApplicationDetails(requestDVO);
	}
	
	public ResponseDVO validatePlantMTOColor(RequestDVO requestDVO){
		
		return eventApplicationDAO.validatePlantMTOColor(requestDVO);
	}
	
	public ResponseDVO performCopyEventApplication(RequestDVO requestDVO) throws Exception {

		return eventApplicationDAO.performCopyEventApplication(requestDVO);
	}
	
	public ResponseDVO applyEventApplication(RequestDVO requestDVO){
	
		return eventApplicationDAO.applyEventApplication(requestDVO);
	}
	public ResponseDVO applyEventApplicationToAttachedBudget(RequestDVO requestDVO){
		
		return eventApplicationDAO.applyEventApplicationToAttachedBudget(requestDVO);
	}
	
	public ResponseDVO updateEventPartDetailsApplicationToAttachedBudget(RequestDVO requestDVO){
		
		return eventApplicationDAO.updateEventPartDetailsApplicationToAttachedBudget(requestDVO);
	}

	public String validateEventIsActualOrBudget(RequestDVO requestDVO){
		
		return eventApplicationDAO.validateEventIsActualOrBudget(requestDVO);
	}
	public String[] validateActEventIsAttachedToBudget(RequestDVO requestDVO){
		
		return eventApplicationDAO.validateActEventIsAttachedToBudget(requestDVO);
	}
	public String validatePriorBudgetEventFreezCheck(RequestDVO requestDVO,String[] priorBudgetEvent){
		
		return eventApplicationDAO.validatePriorBudgetEventFreezCheck(requestDVO,priorBudgetEvent);
	}

	public ResponseDVO getFEMDModel(RequestDVO requestDVO) {
		return eventApplicationDAO.getFEMDModel(requestDVO);
	}
	
	public List<Map<String, Object>> populateCategoryOnModelSelection(String m_strModelDlg) {
		return eventApplicationDAO.populateCategoryOnModelSelection(m_strModelDlg);
	}

	public Integer checkSuppByPartAndEvent(String m_strPartNumber, String eventName, Integer eventRevNo) throws ApplicationException {
		return eventApplicationDAO.checkSuppByPartAndEvent(m_strPartNumber, eventName, eventRevNo);
	}
}
