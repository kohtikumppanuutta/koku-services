/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.customer.impl;

import java.util.Collection;
import java.util.HashSet;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import fi.koku.calendar.CalendarUtil;
import fi.koku.services.entity.customer.v1.AddressType;
import fi.koku.services.entity.customer.v1.AddressesType;
import fi.koku.services.entity.customer.v1.ElectronicContactInfoType;
import fi.koku.services.entity.customer.v1.ElectronicContactInfosType;
import fi.koku.services.entity.customer.v1.PhoneNumberType;
import fi.koku.services.entity.customer.v1.PhoneNumbersType;
import fi.koku.services.entity.customer.v1.VoidType;
import fi.koku.services.entity.customer.v1.AuditInfoType;
import fi.koku.services.entity.customer.v1.CustomerQueryCriteriaType;
import fi.koku.services.entity.customer.v1.CustomerServicePortType;
import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.entity.customer.v1.CustomersType;

/**
 * KoKu Customer service endpoint implementation class.
 * 
 * @author aspluma
 * @author laukksa
 */
@Stateless
@Interceptors({CustomerServiceFaultInterceptor.class})
@WebService(wsdlLocation="META-INF/wsdl/customerService.wsdl",
    endpointInterface="fi.koku.services.entity.customer.v1.CustomerServicePortType",
    targetNamespace="http://services.koku.fi/entity/customer/v1",
    portName="customerService-soap11-port",
    serviceName="customerService"
)
@RolesAllowed("koku-role")
public class CustomerServiceEndpointBean implements CustomerServicePortType {
	
	@EJB
	private CustomerService customerService;
	
	private CustomerConverter customerConverter;
	
	public CustomerServiceEndpointBean() {
	  customerConverter = new CustomerConverter();
	}

  @Override
  public String opAddCustomer(CustomerType customer, AuditInfoType auditHeader) {
    Long id = customerService.add(customerConverter.fromWsType(customer));
    return id.toString();
  }
	
	@Override
	public CustomerType opGetCustomer(String pic, AuditInfoType auditHeader) {
		return customerConverter.toWsType(customerService.get(pic), CustomerConstants.FULL_DATA_QUERY);
	}

	@Override
	public VoidType opUpdateCustomer(CustomerType customer, AuditInfoType auditHeader) {
		customerService.update(customerConverter.fromWsType(customer));
    return new VoidType();
	}

	@Override
	public VoidType opDeleteCustomer(String pic, AuditInfoType auditHeader) {
		customerService.delete(pic);
    return new VoidType();
	}

	@Override
	public CustomersType opQueryCustomers(CustomerQueryCriteriaType criteria, AuditInfoType auditHeader) {
    CustomerQueryCriteria customerQueryCriteria = new CustomerQueryCriteria(new HashSet<String>(
        criteria.getPics() != null ? criteria.getPics().getPic() : new HashSet<String>()), criteria.getSelection());
    Collection<Customer> customers = customerService.query(customerQueryCriteria);
    CustomersType r = new CustomersType();
		for(Customer c : customers) {
		  r.getCustomer().add(customerConverter.toWsType(c, criteria.getSelection()));
		}
		  
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
	  
	  public CustomerConverter() {
	  }
	  
	  public CustomerType toWsType(Customer c, String profileType) {
      CustomerType ct = new CustomerType();
      ct.setId(c.getId().toString());
      ct.setStatus(c.getStatus());
      ct.setStatusDate(CalendarUtil.getXmlDate(c.getStatusDate()));
      ct.setHenkiloTunnus(c.getPic());
      ct.setSyntymaPvm(CalendarUtil.getXmlDate(c.getBirthDate()));
      ct.setSukuNimi(c.getLastName());
      ct.setEtuNimi(c.getFirstName());
	    ct.setEtunimetNimi(c.getFirstNames());
	    ct.setKansalaisuusKoodi(c.getNationality());
	    ct.setKuntaKoodi(c.getMunicipality());
      ct.setKieliKoodi(c.getLanguage());
	    ct.setTurvakieltoKytkin(c.isTurvakielto());

	    if (CustomerConstants.FULL_DATA_QUERY.equals(profileType)) {
	      // addresses
	      ct.setAddresses(new AddressesType());
	      for (Address a : c.getAddresses()) {
	        AddressType at = new AddressType();
	        at.setAddressType(a.getType());
	        at.setKatuNimi(a.getStreetAddress());
	        at.setPostitoimipaikkaNimi(a.getPostalDistrict());
	        at.setPostinumeroKoodi(a.getPostalCode());
	        at.setPostilokeroTeksti(a.getPoBox());
	        at.setMaatunnusKoodi(a.getCountryCode());
	        if (a.getValidFrom() != null) {
	          at.setAlkuPvm(CalendarUtil.getXmlDate(a.getValidFrom()));
	        }
	        if (a.getValidTo() != null) {
	          at.setLoppuPvm(CalendarUtil.getXmlDate(a.getValidTo()));
	        }
	        ct.getAddresses().getAddress().add(at);
	      }

	      // phones
	      ct.setPhoneNumbers(new PhoneNumbersType());
	      for (PhoneNumber n : c.getPhones()) {
	        PhoneNumberType pt = new PhoneNumberType();
	        pt.setNumberType(n.getType());
	        pt.setNumberClass(n.getNumberClass());
	        pt.setPuhelinnumeroTeksti(n.getNumber());
	        ct.getPhoneNumbers().getPhone().add(pt);
	      }

	      // econtacts
	      ct.setElectronicContactInfos(new ElectronicContactInfosType());
	      for (ElectronicContactInfo e : c.getElectronicContacts()) {
	        ElectronicContactInfoType ec = new ElectronicContactInfoType();
	        ec.setContactInfoType(e.getType());
	        ec.setContactInfo(e.getContact());
	        ct.getElectronicContactInfos().getEContactInfo().add(ec);
	      }
	    }
	    
	    return ct;
	  }
	  
	  public Customer fromWsType(CustomerType ct) {
	    Customer c = new Customer();
      c.setStatus(ct.getStatus());
      c.setStatusDate(CalendarUtil.getDate(ct.getStatusDate()));
      c.setPic(ct.getHenkiloTunnus());
	    c.setBirthDate(CalendarUtil.getDate(ct.getSyntymaPvm()));
      c.setLastName(ct.getSukuNimi());
	    c.setFirstName(ct.getEtuNimi());
	    c.setFirstNames(ct.getEtunimetNimi());
      c.setNationality(ct.getKansalaisuusKoodi());
	    c.setMunicipality(ct.getKuntaKoodi());
	    c.setLanguage(ct.getKieliKoodi());
	    c.setTurvakielto(ct.isTurvakieltoKytkin());

      if (ct.getAddresses() != null) {
        for (AddressType at : ct.getAddresses().getAddress()) {
          Address a = new Address();
          a.setType(at.getAddressType());
          a.setStreetAddress(at.getKatuNimi());
          a.setPostalDistrict(at.getPostitoimipaikkaNimi());
          a.setPostalCode(at.getPostinumeroKoodi());
          a.setPoBox(at.getPostilokeroTeksti());
          a.setCountryCode(at.getMaatunnusKoodi());
          if (at.getAlkuPvm() != null) {
            a.setValidFrom(CalendarUtil.getDate(at.getAlkuPvm()));
          }
          if (at.getLoppuPvm() != null) {
            a.setValidTo(CalendarUtil.getDate(at.getLoppuPvm()));
          }
          c.getAddresses().add(a);
          a.setCustomer(c);
        }
      }

      if (ct.getPhoneNumbers() != null) {
        for (PhoneNumberType pt : ct.getPhoneNumbers().getPhone()) {
          PhoneNumber n = new PhoneNumber();
          n.setType(pt.getNumberType());
          n.setNumberClass(pt.getNumberClass());
          n.setNumber(pt.getPuhelinnumeroTeksti());
          c.getPhones().add(n);
          n.setCustomer(c);
        }
      }
      
      if (ct.getElectronicContactInfos() != null) {
        for (ElectronicContactInfoType ec : ct.getElectronicContactInfos().getEContactInfo()) {
          ElectronicContactInfo e = new ElectronicContactInfo();
          e.setType(ec.getContactInfoType());
          e.setContact(ec.getContactInfo());
          c.getElectronicContacts().add(e);
          e.setCustomer(c);
        }
      }
	    
	    return c;
	  }
	  
	}
	
}
