package fi.koku.services.entity.customer.impl;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.WebServiceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.entity.customer.v1.CustomerQueryCriteriaType;
import fi.koku.services.entity.customer.v1.CustomerServicePortType;
import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.entity.customer.v1.CustomersType;

/**
 * KoKu Customer service implementation class.
 * 
 * TODO
 * - logging (fix log level)
 * - exception handling
 * 
 * @author Ixonos / aspluma
 */
@Stateless
@WebService(wsdlLocation="META-INF/wsdl/customerService.wsdl",
    endpointInterface="fi.koku.services.entity.customer.v1.CustomerServicePortType",
    targetNamespace="http://services.koku.fi/entity/customer/v1",
    portName="customerService-soap11-port",
    serviceName="customerService"
)
//@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@RolesAllowed("koku-role")
@HandlerChain(file="auditInfoHandler.xml")

public class CustomerServiceBean implements CustomerServicePortType {
  private Logger logger = LoggerFactory.getLogger(CustomerServicePortType.class);
  
  //@PersistenceContext
  private EntityManager em;
  
	@Resource
	private WebServiceContext wsCtx;
	
	private CustomerService customerService;
	private CustomerConverter customerConverter;
	
	@PostConstruct
	public void initialize() {
	  customerService = new CustomerServiceImpl(em);
	  customerConverter = new CustomerConverter();
	}

  @Override
  public String opAddCustomer(CustomerType customer) {
    logger.info("opAddCustomer");

    // soap headers
//    if(auditHeader != null)
//      logger.info("audit: "+auditHeader.getComponent()+", "+auditHeader.getUserId());
    
    logger.info("msg: "+wsCtx.getMessageContext().get("myownmsg"));
    
    // message context
    Set<String> keys = wsCtx.getMessageContext().keySet();
    for(Iterator<String> i = keys.iterator(); i.hasNext(); )
      logger.info("i: "+i.next());
    
    String caller = wsCtx.getUserPrincipal().getName();
    logger.info("caller: "+caller);
    
    Long id = customerService.add(customerConverter.fromWsType(customer));
    return id.toString();
  }
	
	@Override
	public CustomerType opGetCustomer(String pic) {
		logger.info("opGetCustomer");
		return customerConverter.toWsType(customerService.get(pic));
	}

	@Override
	public void opUpdateCustomer(CustomerType customer) {
		logger.info("opUpdateCustomer: "+customer);
		customerService.update(customerConverter.fromWsType(customer));
	}

	@Override
	public void opDeleteCustomer(String pic) {
		logger.info("opDeleteCustomer: "+pic);
		customerService.delete(pic);
	}

	@Override
	public CustomersType opQueryCustomers(CustomerQueryCriteriaType criteria) {
		logger.info("opQueryCustomers");
		
		CustomerQueryCriteria customerQueryCriteria = new CustomerQueryCriteria(Long.valueOf(criteria.getId()), criteria.getPic(), criteria.getSelection());
		Collection<Customer> customers = customerService.query(customerQueryCriteria);
		CustomersType r = new CustomersType();
		for(Customer c : customers)
		  r.getCustomer().add(customerConverter.toWsType(c));
		
		return r;
	}

	public void setCustomerService(CustomerService cs) {
	  this.customerService = cs;
	}
 
	/**
	 * Convert between webservice type (CustomerType) and the internal object representation.
	 * 
	 * @author aspluma
	 */
	private static class CustomerConverter {
	  private DatatypeFactory datatypeFactory;
	  
	  public CustomerConverter() {
	    try {
        datatypeFactory = DatatypeFactory.newInstance();
      } catch (DatatypeConfigurationException e) {
        throw new RuntimeException("Failed to create DatatypeFactory", e);
      }
	  }
	  
	  public CustomerType toWsType(Customer c) {
	    CustomerType ct = new CustomerType();
	    ct.setId(c.getId().toString());
	    ct.setHenkiloTunnus(c.getPic());
	    ct.setEtunimetNimi(c.getFirstNames());
	    ct.setEtuNimi(c.getFirstName());
      ct.setSukuNimi(c.getLastName());
	    ct.setKansalaisuusKoodi(c.getNationality());
	    ct.setKuntaKoodi(c.getMunicipality());
	    ct.setStatus(c.getStatus());
	    
	    GregorianCalendar gc = new GregorianCalendar();
	    gc.setTime(c.getStatusDate());
	    ct.setStatusDate(datatypeFactory.newXMLGregorianCalendar(gc));
	    
	    gc.setTime(c.getBirthDate());
	    ct.setSyntymaPvm(datatypeFactory.newXMLGregorianCalendar(gc));

	    ct.setTurvakieltoKytkin(c.isTurvakielto());
	    ct.setKieliKoodi("FI");
	    
	    return ct;
	  }
	  
	  public Customer fromWsType(CustomerType ct) {
	    Customer c = new Customer();
	    
	    c.setBirthDate(ct.getSyntymaPvm().toGregorianCalendar().getTime());
	    c.setFirstName(ct.getEtuNimi());
	    c.setFirstNames(ct.getEtunimetNimi());
	    c.setLastName(ct.getSukuNimi());
	    c.setMunicipality(ct.getKuntaKoodi());
	    c.setNationality(ct.getKansalaisuusKoodi());
	    c.setPic(ct.getHenkiloTunnus());
	    c.setStatus(ct.getStatus());
	    c.setStatusDate(ct.getStatusDate().toGregorianCalendar().getTime());
	    c.setTurvakielto(ct.isTurvakieltoKytkin());
	    
	    return c;
	  }
	  
	}
	
	
}
