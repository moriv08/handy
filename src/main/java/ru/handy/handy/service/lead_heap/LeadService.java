package ru.handy.handy.service.lead_heap;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.mappers.Labels;
import ru.handy.handy.models.mappers.Statuses;
import ru.handy.handy.repository.lead_heap.LeadRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeadService {
    private final LeadRepository leadRepository;
    public LeadService(LeadRepository leadRepository) { this.leadRepository = leadRepository; }

    /****************** common *******************/
    @Transactional
    public void createNewLead(LeadEntity lead){ leadRepository.save(lead); }

    @Transactional
    @ReadOnlyProperty
    public LeadEntity findLeadById(Long id){ return leadRepository.findLeadEntityById(id); }

    @Transactional
    @ReadOnlyProperty
    public LeadEntity findLeadByTel(String tel){ return leadRepository.findLeadEntityByTel(tel); }
    /*************************************/
    @Transactional
    @ReadOnlyProperty
    public Map<String, List<LeadEntity>> findAllAdvancedAppropriateLeadsByQuery(String wanted){

        Map<String, List<LeadEntity>> answer = new HashMap<>();
        List<LeadEntity> leadsList = new ArrayList<>();

        findAllLeads().stream()
                .filter(leadEntity -> leadEntity.getId().toString().contains(wanted))
                .forEach(leadsList::add);

        findAllLeads().stream()
                .filter(leadEntity -> leadEntity.getName() != null)
                .filter(leadEntity -> leadEntity.getName().contains(wanted))
                .forEach(leadsList::add);

        findAllLeads().stream()
                .filter(leadEntity -> leadEntity.getAddress() != null)
                .filter(leadEntity -> leadEntity.getAddress().contains(wanted))
                .forEach(leadsList::add);

        findAllLeads().stream()
                .filter(leadEntity -> leadEntity.getTel().contains(wanted))
                .forEach(leadsList::add);

        answer.put(wanted, leadsList);

        return answer;
    }

    //**********************************    ADMIN  ***************************//
    @Transactional
    @ReadOnlyProperty
    public Map<String, List<LeadEntity>> findAllFilters(){

        Map<String, List<LeadEntity>> allFiltersList = new HashMap<>();

        allFiltersList.put(Labels.ALL_NEW_LEADS.title, findAllNewLeads());
        allFiltersList.put(Labels.ALL_ACTIVE_LEADS.title, findAllActiveLeads());
        allFiltersList.put(Labels.ALL_LEADS.title, findAllLeads());
        allFiltersList.put(Labels.YESTERDAY_CALL.title, findAllByYesterdayCall());
        allFiltersList.put(Labels.TODAY_CALL.title, findAllByTodayCall());
        allFiltersList.put(Labels.ENDING_BOOKING.title, findAllEndingBookings());
        allFiltersList.put(Labels.REQUIRED_DOCS.title, findAllByRequiredDocs());

        return allFiltersList;
    }

    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllInWorkLeads(){
        return findAllLeads().stream()
                .filter(leadEntity ->
                        leadEntity.getLeadDigitStatus() >= Statuses.DOC_SIGNED.digit &&
                                leadEntity.getLeadDigitStatus() < Statuses.CONTRACT_CLOSED.digit)
                .sorted()
                .collect(Collectors.toList());
    }

    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllLeads(){
        List<LeadEntity> allLeads = new ArrayList<>();
        leadRepository.findAll().forEach(allLeads::add);
        return allLeads.stream()
                .sorted()
                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllActiveLeads(){
        return findAllLeads().stream()
                .filter(leadEntity ->
                        leadEntity.getLeadDigitStatus() >= Statuses.NEW_LEAD.digit &&
                                leadEntity.getLeadDigitStatus() < Statuses.CONTRACT_CLOSED.digit)
                .sorted()
                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllEndingBookings(){
        LocalDate today = LocalDate.now();
        return findAllLeads().stream()
                .filter(leadEntity -> leadEntity.getBooking() != null)
                .filter(leadEntity -> leadEntity.getBooking().getBookingEnd() != null)
                .filter(leadEntity -> leadEntity.getBooking().getBookingEnd().isBefore(today))
                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllByRequiredDocs(){
        Set<LeadEntity> requiredDocsSet = new HashSet<>();
        List<LeadEntity> requiredDocs = new ArrayList<>();

        findAllLeads().stream()
                .filter(leadEntity -> leadEntity.getJuristInfo() != null)
                .filter(leadEntity -> leadEntity.getJuristInfo().getRequiredDocuments() != null)
                .forEach(leadEntity ->
                        leadEntity.getJuristInfo().getRequiredDocuments().stream()
                            .filter(documentEntity -> !(documentEntity.getDocExisting()))
                            .forEach(documentEntity -> {
                                requiredDocsSet.add(documentEntity.getJuristInfo().getLead());
                            }));

        requiredDocsSet.stream()
                .forEach(requiredDocs::add);
        return requiredDocs;
    }
    @Transactional
    @ReadOnlyProperty
    public int findAllByRequiredDocsCounter(){
        List<Integer> allRequiredDocsCounter = new ArrayList<>();

        findAllLeads().stream()
                .filter(leadEntity -> leadEntity.getJuristInfo() != null)
                .filter(leadEntity -> leadEntity.getJuristInfo().getRequiredDocuments() != null)
                .forEach(leadEntity ->
                        leadEntity.getJuristInfo().getRequiredDocuments().stream()
                                .filter(documentEntity -> !(documentEntity.getDocExisting()))
                                .forEach(documentEntity -> allRequiredDocsCounter.add(1)));
        return allRequiredDocsCounter.size();
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllNewLeads(){
        return findAllLeads().stream()
                .filter(leadEntity -> leadEntity.getLeadStatus().equals(Statuses.NEW_LEAD.status))
                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllByYesterdayCall(){
        LocalDate today = LocalDate.now();
        return new ArrayList<>(leadRepository.findAllByControlDateBefore(today));
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllByTodayCall(){
        LocalDate today = LocalDate.now();
        return new ArrayList<>(leadRepository.findAllByControlDateEquals(today));
    }
    @Transactional
    @ReadOnlyProperty
    public Map<String, List<LeadEntity>> findAllOfExpertFilters(){

        Map<String, List<LeadEntity>> allExpertsList = new HashMap<>();
        Set<String> experts = new HashSet<>();

        findAllLeads().stream()
                .forEach(leadEntity -> experts.add(leadEntity.getExpert().getName()));

        experts.stream()
                .forEach(expert -> {
                    allExpertsList.put(expert, findAllByExpertName(expert));
                });

        return allExpertsList;
    }

    //**********************************    SUPERUSER  ***************************//
    @Transactional
    @ReadOnlyProperty
    public Map<String, List<LeadEntity>> findAllSuperuserFiltersById(Long id){

        Map<String, List<LeadEntity>> superUserFilters = new HashMap<>();

        superUserFilters.put(Labels.YOUR_NEW_LEAD.title, findAllSuperuserNewLeadsById(id));
        superUserFilters.put(Labels.ALL_NEW_LEADS.title, findAllNewLeads());

        superUserFilters.put(Labels.YOUR_ACTIVE_LEADS.title, findAllSuperuserActiveLeadsBySuperuserId(id));
        superUserFilters.put(Labels.ALL_ACTIVE_LEADS.title, findAllActiveLeads());

        superUserFilters.put(Labels.YOUR_IN_WORK_LEADS.title, findAllSuperuserInWorkLeadsById(id));

        superUserFilters.put(Labels.YOUR_LEADS.title, findAllSuperuserLeadsBySuperuserId(id));
        superUserFilters.put(Labels.ALL_LEADS.title, findAllLeads());

        superUserFilters.put(Labels.REQUIRED_DOCS.title, findAllRequiredDocsBySuperuserId(id));

        superUserFilters.put(Labels.YESTERDAY_CALL.title, findAllSuperuserYesterdayCallLeadsBySuperuserId(id));

        return superUserFilters;
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllRequiredDocsBySuperuserId(Long id){
        return findAllByRequiredDocs().stream()
                .filter(leadEntity -> leadEntity.getExpert().getSuperuser().getId().equals(id))
                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public int findAllRequiredDocsCounterBySuperuserId(Long id){

        List<Integer> allRequiredDocsCounter = new ArrayList<>();

        findAllRequiredDocsBySuperuserId(id).stream()
                .filter(leadEntity -> leadEntity.getJuristInfo() != null)
                .filter(leadEntity -> leadEntity.getJuristInfo().getRequiredDocuments() != null)
                .forEach(leadEntity ->
                        leadEntity.getJuristInfo().getRequiredDocuments().stream()
                                .filter(documentEntity -> !(documentEntity.getDocExisting()))
                                .forEach(documentEntity -> allRequiredDocsCounter.add(1)));

        return allRequiredDocsCounter.size();
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllSuperuserEndingBookings(Long id){
        return findAllEndingBookings().stream()
                .filter(leadEntity -> leadEntity.getExpert().getSuperuser().getId().equals(id))
                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllSuperuserYesterdayCallLeadsBySuperuserId(Long id){
        return findAllByYesterdayCall().stream()
                .filter(leadEntity -> leadEntity.getExpert().getSuperuser().getId().equals(id))
                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllSuperuserTodayCallLeadsBySuperuserId(Long id){
        return findAllByTodayCall().stream()
                .filter(leadEntity -> leadEntity.getExpert().getSuperuser().getId().equals(id))
                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllSuperuserLeadsBySuperuserId(Long id){
        return findAllLeads().stream()
                .filter(leadEntity -> leadEntity.getExpert().getSuperuser().getId().equals(id))
                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllSuperuserNewLeadsById(Long id){
//        LocalDate today = LocalDate.now();

        return findAllSuperuserLeadsBySuperuserId(id).stream()
                .filter(leadEntity -> leadEntity.getLeadStatus().equals(Statuses.NEW_LEAD.status))
//                .filter(leadEntity -> (leadEntity.getControlDate().equals(today)))
                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllSuperuserActiveLeadsBySuperuserId(Long id){
        return findAllActiveLeads().stream()
                .filter(leadEntity -> leadEntity.getExpert().getSuperuser().getId().equals(id))
                .sorted()
                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllSuperuserInWorkLeadsById(Long id){
        return findAllInWorkLeads().stream()
                .filter(leadEntity -> leadEntity.getExpert().getSuperuser().getId().equals(id))
                .collect(Collectors.toList());
    }
    //    @Transactional
//    @ReadOnlyProperty
//    public List<LeadEntity> findAllSuperuserYesterdayCallLeadsBySuperuserId(Long id){
//        LocalDate currentDate = LocalDate.now();
//        return leadRepository.findAllByControlDateBefore(currentDate).stream()
//                .filter(leadEntity -> leadEntity.getExpert().getSuperuser().getId().equals(id))
//                .sorted()
//                .collect(Collectors.toList());
//    }

//    @Transactional
//    @ReadOnlyProperty
//    public Map<String, List<LeadEntity>> findAllSuperusersExpertFiltersBySuperuserId(Long id){
//
//        Map<String, List<LeadEntity>> superUserFilters = new HashMap<>();
//        Set<String> experts = new TreeSet<>();
//
//        findAllSuperuserLeadsBySuperuserId(id).stream()
//                .filter(leadEntity -> leadEntity.getExpert().getName() != null)
//                .forEach(leadEntity -> experts.add(leadEntity.getExpert().getName()));
//
//        experts.stream()
//                .forEach(expert -> {
//                    List<LeadEntity> leadsList = new ArrayList<>();
//                    findAllSuperuserLeadsBySuperuserId(id).stream()
//                            .filter(leadEntity -> leadEntity.getExpert().getName() != null)
//                            .filter(leadEntity -> leadEntity.getExpert().getName().equals(expert))
//                            .forEach(leadsList::add);
//                    superUserFilters.put(expert, leadsList);
//                });
//
//        return superUserFilters;
//    }
    //*****************************************************************************//

    //**********************************    EXPERT  ***************************//
    @Transactional
    @ReadOnlyProperty
    public Map<String, List<LeadEntity>> findAllExpertFiltersById(Long id){

        Map<String, List<LeadEntity>> expertFilters = new HashMap<>();

        expertFilters.put(Labels.YOUR_NEW_LEAD.title, findAllNewLeadsByExpertId(id));
        expertFilters.put(Labels.TODAY_CALL.title, findAllExpertTodayCallLeadsByExpertId(id));
        expertFilters.put(Labels.YESTERDAY_CALL.title, findAllExpertYesterdayCallLeadsByExpertId(id));
        expertFilters.put(Labels.ALL_LEADS.title, findAllExpertLeadsByExpertId(id));
        expertFilters.put(Labels.REQUIRED_DOCS.title, findAllExpertRequiredDocsLeadsByExpertId(id));
        expertFilters.put(Labels.ENDING_BOOKING.title, findAllExpertEndingBookingLeadsByExpertId(id));
        expertFilters.put(Labels.YOUR_IN_WORK_LEADS.title, findAllInWorkLeadsByExpertId(id));
        expertFilters.put(Labels.YOUR_ACTIVE_LEADS.title, findAllActiveLeadsByExpertId(id));


        return expertFilters;
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllNewLeadsByExpertId(Long id){
//        LocalDate today = LocalDate.now();
//        List<LeadEntity> newLeads = new ArrayList<>();

        return findAllNewLeads().stream()
                .filter(leadEntity -> leadEntity.getExpert().getId().equals(id))
                .collect(Collectors.toList());

//        findAllExpertLeadsByExpertId(id).stream()
//                .filter(leadEntity -> leadEntity.getLeadStatus().equals(Statuses.NEW_LEAD.status))
//                .filter(leadEntity -> leadEntity.getControlDate() == null)
//                .forEach(newLeads::add);
//
//        findAllExpertLeadsByExpertId(id).stream()
//                .filter(leadEntity -> leadEntity.getLeadStatus().equals(Statuses.NEW_LEAD.status))
//                .filter(leadEntity -> leadEntity.getControlDate() != null)
//                .filter(leadEntity -> leadEntity.getControlDate().equals(today) ||
//                        leadEntity.getControlDate().isBefore(today))
//                .forEach(newLeads::add);

//        return newLeads;
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllInWorkLeadsByExpertId(Long id){

        return findAllInWorkLeads().stream()
                .filter(leadEntity -> leadEntity.getExpert().getId().equals(id))
                .collect(Collectors.toList());

//        return findAllExpertLeadsByExpertId(id).stream()
//                .filter(leadEntity -> leadEntity.getLeadStatus().equals(Statuses.NEW_LEAD.status) ||
//                        leadEntity.getLeadStatus().equals(Statuses.CLIENT_TAKING_PRICE.status) ||
//                        leadEntity.getLeadStatus().equals(Statuses.DOC_OK.status) ||
//                        leadEntity.getLeadStatus().equals(Statuses.DOC_SIGNED.status) ||
//                        leadEntity.getLeadStatus().equals(Statuses.DOC_WAITING.status) ||
//                        leadEntity.getLeadStatus().equals(Statuses.REGISTRATION.status) ||
//                        leadEntity.getLeadStatus().equals(Statuses.REGISTRATION_PROBLEM.status) ||
//                        leadEntity.getLeadStatus().equals(Statuses.NEW_SUPER_ANSWER.status))
//                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllActiveLeadsByExpertId(Long id){

        return findAllActiveLeads().stream()
                .filter(leadEntity -> leadEntity.getExpert().getId().equals(id))
                .collect(Collectors.toList());

//        return findAllExpertLeadsByExpertId(id).stream()
//                .filter(leadEntity -> leadEntity.getLeadDigitStatus() >= 0 &&
//                        leadEntity.getLeadDigitStatus() < 100)
//                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllExpertLeadsByExpertId(Long id){
        return leadRepository.findAllByExpertId(id).stream()
                .sorted()
                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllExpertTodayCallLeadsByExpertId(Long id){
        LocalDate currentDate = LocalDate.now();

        List<LeadEntity> list = new ArrayList<>();

//        return findAllExpertLeadsByExpertId(id).stream()
//                .filter(leadEntity -> )

        leadRepository.findAllByControlDateEqualsAndExpertId(currentDate, id).stream()
//                .filter(leadEntity -> !(leadEntity.getLeadStatus().equals(Statuses.NEW_LEAD.status)))
                .forEach(leadEntity -> {
//                    setLeadStatus(leadEntity.getId(), Statuses.CALL_TODAY.status, Statuses.CALL_TODAY.digit);
//                    setLeadStatus(leadEntity.getId(), Statuses.CALL_TODAY.status, Statuses.CALL_TODAY.digit);
                    setStatusEmergency(leadEntity.getId(), Statuses.CALL_TODAY.status, Statuses.CALL_TODAY.digit);
                    list.add(leadEntity);
                });
        return list;
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllExpertYesterdayCallLeadsByExpertId(Long id){
        LocalDate currentDate = LocalDate.now();
        List<LeadEntity> list = new ArrayList<>();

        leadRepository.findAllByControlDateBeforeAndExpertId(currentDate, id).stream()
                .filter(leadEntity -> (!leadEntity.getLeadStatus().equals(Statuses.NEW_LEAD.status)))
                .forEach(leadEntity -> {
                    setLeadStatus(leadEntity.getId(), Statuses.CALL_YESTERDAY.status, Statuses.CALL_YESTERDAY.digit);
                    list.add(leadEntity);
                });
        return list;
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllExpertRequiredDocsLeadsByExpertId(Long id){

        return findAllByRequiredDocs().stream()
                .filter(leadEntity -> leadEntity.getExpert().getId().equals(id))
                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public int findAllRequiredDocsCounterByExpertId(Long id){

        List<Integer> allRequiredDocsCounter = new ArrayList<>();

        findAllExpertRequiredDocsLeadsByExpertId(id).stream()
                .filter(leadEntity -> leadEntity.getJuristInfo() != null)
                .filter(leadEntity -> leadEntity.getJuristInfo().getRequiredDocuments() != null)
                .forEach(leadEntity ->
                        leadEntity.getJuristInfo().getRequiredDocuments().stream()
                                .filter(documentEntity -> !(documentEntity.getDocExisting()))
                                .forEach(documentEntity -> allRequiredDocsCounter.add(1)));

        return allRequiredDocsCounter.size();
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllExpertEndingBookingLeadsByExpertId(Long id){

        return findAllExpertLeadsByExpertId(id).stream()
                .filter(leadEntity -> leadEntity.getBooking() != null)
                .filter(leadEntity -> leadEntity.getBooking().getBookingEnd() != null)
                .filter(leadEntity -> leadEntity.getBooking().getBookingEnd().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
    }
    @Transactional
    @ReadOnlyProperty
    public List<LeadEntity> findAllByExpertName(String name){
        return leadRepository.findAllByExpertName(name).stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Transactional
    @ReadOnlyProperty
    public Map<String, List<LeadEntity>> findAllAppropriateExpertLeadsByQueryAndExpertId(String wanted, Long id){

        Map<String, List<LeadEntity>> answer = new HashMap<>();
        List<LeadEntity> leadsList = new ArrayList<>();

        findAllExpertLeadsByExpertId(id).stream()
                .filter(leadEntity -> leadEntity.getId().toString().contains(wanted))
                .forEach(leadsList::add);

        findAllExpertLeadsByExpertId(id).stream()
                .filter(leadEntity -> leadEntity.getName() != null)
                .filter(leadEntity -> leadEntity.getName().contains(wanted))
                .forEach(leadsList::add);

        findAllExpertLeadsByExpertId(id).stream()
                .filter(leadEntity -> leadEntity.getAddress() != null)
                .filter(leadEntity -> leadEntity.getAddress().contains(wanted))
                .forEach(leadsList::add);

        findAllExpertLeadsByExpertId(id).stream()
                .filter(leadEntity -> leadEntity.getTel().contains(wanted))
                .forEach(leadsList::add);

        answer.put(wanted, leadsList);

        return answer;
    }



    //******************************* PAGES *******************************//
    // *********************    1.1
    @Transactional
    public void setLeadOwnerName(Long leadId, String applOwnerName){
        leadRepository.setLeadOwnerName(leadId, applOwnerName);
    }
    @Transactional
    public void setTel(Long leadId, String tel){
        leadRepository.setTel(leadId, tel);
    }
    @Transactional
    public void setLeadStatus(Long leadId, String newLeadStatus, Integer digit){
        leadRepository.setNewLeadStatus(leadId, newLeadStatus, digit);
    }
    @Transactional
    public void setStatusEmergency(Long leadId, String statusEmergency, Integer digitEmergencyStatus){
        leadRepository.setStatusEmergency(leadId, statusEmergency, digitEmergencyStatus);
    }
    @Transactional
    public void precontractType(Long leadId, String precontractType){
        leadRepository.setPrecontractType(leadId, precontractType);
    }
    @Transactional
    public void setLeadControlDate(Long leadId, LocalDate date){
        leadRepository.setLeadControlDate(leadId, date);
    }
    //  *****************
    // *********************    1.2
    @Transactional
    public void setAddress(Long applId, String address){
        leadRepository.setAddress(applId, address);
    }
    @Transactional
    public void setMetro(Long leadId, String metro){
        leadRepository.setMetro(leadId, metro);
    }
    @Transactional
    public void setBuildYear(Long leadId, String buildYear){
        leadRepository.setBuildYear(leadId, buildYear);
    }
    @Transactional
    public void setHouseType(Long leadId, String type){
        leadRepository.setHouseType(leadId, type);
    }
    @Transactional
    public void setFloor(Long leadId, String floor){
        leadRepository.setFloor(leadId, floor);
    }
    @Transactional
    public void setRooms(Long leadId, String rooms){
        leadRepository.setRooms(leadId, rooms);
    }
    @Transactional
    public void setFlatSquare(Long leadId, String rooms){
        leadRepository.setFlatSquare(leadId, rooms);
    }



    @Transactional
    public void saveExpertPrice(Long id, String expertPrice){
        leadRepository.setExpertPrice(id, expertPrice);
    }
    @Transactional
    public void saveSuperPrice(Long id, String superPrice){
        leadRepository.setSuperPrice(id, superPrice);
    }
    @Transactional
    public void saveSuperComment(Long id, String superComment){
        leadRepository.setSuperComment(id, superComment);
    }
    /***************************************************************/


    //******* 2.1 block
    @Transactional
    public void setFirstCall(Long leadId, boolean call){
        leadRepository.setFirstCall(leadId, call);
    }
    //******* 2.2 block
    @Transactional
    public void setClientAnswer(Long leadId, String answer, Integer digitAnswer){
        leadRepository.setClientAnswer(leadId, answer, digitAnswer);
    }
    //******* 2.3 block
    @Transactional
    public void setClientStatus(Long leadId, String clientStatus, Integer clientDigitStatus){
        leadRepository.setClientStatus(leadId, clientStatus, clientDigitStatus);
    }
//    @Transactional
//    public void setClientDigitStatus(Long leadId, String clientDigitStatus){
//        leadRepository.setClientStatus(leadId, clientDigitStatus);
//    }

    //******* 4.1
    @Transactional
    public void setAllCalls(Long id, Integer allCalls){
        leadRepository.setAllCalls(id, allCalls);
    }
    //******* 4.2
    @Transactional
    public void setAllViews(Long id, Integer allViews){
        leadRepository.setAllViews(id, allViews);
    }
    //******* 4.2
    @Transactional
    public void setBriefComment(Long id, String brief){
        leadRepository.setBriefComment(id, brief);
    }


    //******* 4.7
    @Transactional
    public void setAnnouncementByApplId(Long applId, String sellingText){
        leadRepository.setAdvertismentByApplId(applId, sellingText);
    }














/************************ TEST ****************/


//    @Transactional
//    @ReadOnlyProperty
//    public Map<String, Page<LeadEntity>> TEST_MAP(Long id, Pageable pageable){
//
//        Map<String, Page<LeadEntity>> expertFilters = new HashMap<>();
//
//        expertFilters.put(Labels.YOUR_NEW_LEAD.title, TEST_ALL_LEADS(pageable)); // ??????????????????????????????
////        expertFilters.put(Labels.TODAY_CALL.title, findAllExpertTodayCallLeadsByExpertId(id));
////        expertFilters.put(Labels.YESTERDAY_CALL.title, findAllExpertYesterdayCallLeadsByExpertId(id));
////        expertFilters.put(Labels.ALL_LEADS.title, findAllExpertLeadsByExpertId(id));
////        expertFilters.put(Labels.REQUIRED_DOCS.title, findAllExpertRequiredDocsLeadsByExpertId(id));
////        expertFilters.put(Labels.ENDING_BOOKING.title, findAllExpertEndingBookingLeadsByExpertId(id));
////        expertFilters.put(Labels.YOUR_IN_WORK_LEADS.title, findAllInWorkLeadsByExpertId(id));
////        expertFilters.put(Labels.ALL_ACTIVE_LEADS.title, findAllActiveLeadsByExpertId(id));
//
//
//        return expertFilters;
//    }
@Transactional
@ReadOnlyProperty
public List<LeadEntity> TEST_ALL_LEADS(Integer start, Integer end){
    List<LeadEntity> answer = new ArrayList<>();
    List<LeadEntity> allLeads = leadRepository.findAllByLeadStatus(Statuses.NEW_LEAD.status)
            .stream()
            .sorted()
            .collect(Collectors.toList());

    int index = 0;
    for (int i = start; i < end; i++) {
        answer.add(index++, allLeads.get(i));
    }

    return answer;
}

    //    @Transactional
//    @ReadOnlyProperty
//    public Page<LeadEntity> TEST_ALL_LEADS(Pageable pageable){
//        Page<LeadEntity> allLeads = leadRepository.findAllByLeadStatus(Statuses.NEW_LEAD.status, pageable);
//
//        return allLeads;
//    }
    @Transactional
    @ReadOnlyProperty
    public Map<String, List<LeadEntity>> TEST_MAP(Integer start, Integer end){

        Map<String, List<LeadEntity>> expertFilters = new HashMap<>();

        expertFilters.put(Labels.YOUR_NEW_LEAD.title, TEST_ALL_LEADS(start, end)); // ??????????????????????????????
//        expertFilters.put(Labels.TODAY_CALL.title, findAllExpertTodayCallLeadsByExpertId(id));
//        expertFilters.put(Labels.YESTERDAY_CALL.title, findAllExpertYesterdayCallLeadsByExpertId(id));
//        expertFilters.put(Labels.ALL_LEADS.title, findAllExpertLeadsByExpertId(id));
//        expertFilters.put(Labels.REQUIRED_DOCS.title, findAllExpertRequiredDocsLeadsByExpertId(id));
//        expertFilters.put(Labels.ENDING_BOOKING.title, findAllExpertEndingBookingLeadsByExpertId(id));
//        expertFilters.put(Labels.YOUR_IN_WORK_LEADS.title, findAllInWorkLeadsByExpertId(id));
//        expertFilters.put(Labels.ALL_ACTIVE_LEADS.title, findAllActiveLeadsByExpertId(id));


        return expertFilters;
    }
    @Transactional
    @ReadOnlyProperty
    public Map<String, List<LeadEntity>> findAPrincipalFiltersById(Long id){

        Map<String, List<LeadEntity>> principalFilters = new HashMap<>();

        principalFilters.put(Labels.ALL_NEW_LEADS.title, findAllNewLeads());
        principalFilters.put(Labels.ALL_ACTIVE_LEADS.title, findAllActiveLeads());
        principalFilters.put(Labels.YOUR_IN_WORK_LEADS.title, findAllInWorkLeads());
        principalFilters.put(Labels.ALL_LEADS.title, findAllLeads());
        principalFilters.put(Labels.YESTERDAY_CALL.title, findAllByYesterdayCall());
        principalFilters.put(Labels.TODAY_CALL.title, findAllByTodayCall());

        return principalFilters;
    }




}
