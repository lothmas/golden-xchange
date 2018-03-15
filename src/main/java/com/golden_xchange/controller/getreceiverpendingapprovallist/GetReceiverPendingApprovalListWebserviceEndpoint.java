//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.golden_xchange.controller.getreceiverpendingapprovallist;

import com.golden_xchange.domain.mainlist.exception.MainListNotFoundException;
import com.golden_xchange.domain.mainlist.model.MainListEntity;
import com.golden_xchange.domain.mainlist.service.MainListService;
import com.golden_xchange.domain.users.service.GoldenRichesUsersService;
import com.golden_xchange.domain.utilities.DateConversion;
import com.golden_xchange.domain.utilities.Enums.StatusCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

@Controller
public class GetReceiverPendingApprovalListWebserviceEndpoint {
    Logger aprrover = Logger.getLogger(this.getClass().getName());

    private static final String NAMESPACE_URI = "getReceiverPendingApprovalList.webservice.golden_xchange.com";
    @Autowired
    MainListService mainListService;
    @Autowired
    GoldenRichesUsersService goldenRichesUsersService;
    List<MainListEntity> getDonationsByDonationRef = new List<MainListEntity>() {
        public int size() {
            return 0;
        }

        public boolean isEmpty() {
            return false;
        }

        public boolean contains(Object o) {
            return false;
        }

        public Iterator<MainListEntity> iterator() {
            return null;
        }

        public Object[] toArray() {
            return new Object[0];
        }

        public <T> T[] toArray(T[] a) {
            return null;
        }

        public boolean add(MainListEntity mainListEntity) {
            return false;
        }

        public boolean remove(Object o) {
            return false;
        }

        public boolean containsAll(Collection<?> c) {
            return false;
        }

        public boolean addAll(Collection<? extends MainListEntity> c) {
            return false;
        }

        public boolean addAll(int index, Collection<? extends MainListEntity> c) {
            return false;
        }

        public boolean removeAll(Collection<?> c) {
            return false;
        }

        public boolean retainAll(Collection<?> c) {
            return false;
        }

        public void clear() {
        }

        public MainListEntity get(int index) {
            return null;
        }

        public MainListEntity set(int index, MainListEntity element) {
            return null;
        }

        public void add(int index, MainListEntity element) {
        }

        public MainListEntity remove(int index) {
            return null;
        }

        public int indexOf(Object o) {
            return 0;
        }

        public int lastIndexOf(Object o) {
            return 0;
        }

        public ListIterator<MainListEntity> listIterator() {
            return null;
        }

        public ListIterator<MainListEntity> listIterator(int index) {
            return null;
        }

        public List<MainListEntity> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    List<MainListEntity> checkDonationByRef;
    DateConversion dateConversion = new DateConversion();

    public GetReceiverPendingApprovalListWebserviceEndpoint() {
    }

    @PayloadRoot(
        namespace = "getReceiverPendingApprovalList.webservice.golden_xchange.com",
        localPart = "GetReceiverPendingApprovalListRequest"
    )
    @ResponsePayload
    public GetReceiverPendingApprovalListResponse handleGetReceiverPendingApprovalListRequest(@RequestPayload GetReceiverPendingApprovalListRequest request) throws Exception {
        GetReceiverPendingApprovalListResponse response = new GetReceiverPendingApprovalListResponse();

        try {
            List<MainListEntity> payerPendingList = this.mainListService.returnPendingApprovalReceiverList(request.getUsername().trim());

            PayerPendingApprovalList pending;
            for(Iterator var4 = payerPendingList.iterator(); var4.hasNext(); response.getReturnData().add(pending)) {
                MainListEntity pendingList = (MainListEntity)var4.next();
                pending = new PayerPendingApprovalList();
                pending.setMainListReference(pendingList.getMainListReference());
                pending.setDepositReference(pendingList.getDepositReference());
                pending.setAmountPaid(pendingList.getDonatedAmount());
                pending.setStatus(pendingList.getStatus());
                pending.setUsername(pendingList.getPayerUsername());
                if(null != pendingList.getUpdatedDate()) {
                    pending.setDateCreated(this.dateConversion.timeStampToGregorian(pendingList.getUpdatedDate()));
                } else {
                    pending.setDateCreated(this.dateConversion.timeStampToGregorian(pendingList.getDate()));
                }
            }

            response.setMessage("Donations Pending Approval List Successfully Returned");
            response.setStatusCode(StatusCodeEnum.OK.getStatusCode());
            return response;
        } catch (MainListNotFoundException var7) {
            aprrover.warning(var7.getMessage());
            response.setMessage(var7.getMessage());
            response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
            return response;
        }
        catch (Exception exp){
            aprrover.warning(exp.getMessage());
            response.setMessage(exp.getMessage());
            response.setStatusCode(StatusCodeEnum.NOTFOUND.getStatusCode());
            return response;
        }
    }
}

