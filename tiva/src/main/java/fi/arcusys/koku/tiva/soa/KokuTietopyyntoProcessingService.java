package fi.arcusys.koku.tiva.soa;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 17, 2011
 */
@WebService(targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
public interface KokuTietopyyntoProcessingService {

    @WebResult(name = "tietopyyntoId")
    @WebMethod(operationName = "luoTietopyynto")
    Long createInformationRequest(
            @WebParam(name = "tietopyynto") final InformationRequestTO request);
    
    @WebMethod(operationName = "hyvaksyTietopyynto")
    void approveRequest(
            @WebParam(name = "tietoPynnonVastaus") final InformationRequestReplyTO reply);
    
    @WebMethod(operationName = "hylkaTietopyynto")
    void declineRequest(
            @WebParam(name = "tietopyyntoId") final Long requestId, 
            @WebParam(name = "explanation") final String explanation);
    
    @WebResult(name = "record")
    @WebMethod(operationName = "getTietoelementit")
    InformationCategoryTO getCategories();
}
