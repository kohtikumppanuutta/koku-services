package fi.koku.customer.service;

import javax.ejb.Stateless;
import javax.jws.WebService;

import fi.koku.contract.customer.PtCustomer;
import fi.koku.schema.customer.CustomerSearchCriteriaType;
import fi.koku.schema.customer.CustomerType;
import fi.koku.schema.customer.CustomersType;

@Stateless
@WebService(wsdlLocation="META-INF/wsdl/customerService.wsdl",
        endpointInterface="fi.koku.contract.customer.PtCustomer",
        targetNamespace="http://koku.fi/contract/customer",
        portName="customerService-http-soap11",
        serviceName="svCustomer"
)
public class CustomerServiceBean implements PtCustomer {

	@Override
	public String opAddCustomer(CustomerType customer) {
		System.out.println("opAddCustomer");
		return null;
	}

	@Override
	public CustomerType opGetCustomer(String customerId) {
		System.out.println("opGetCustomer");
		return null;
	}

	@Override
	public void opUpdateCustomer(CustomerType customer) {
		System.out.println("opUpdateCustomer");
	}

	@Override
	public void opDeleteCustomer(String customerId) {
		System.out.println("opDeleteCustomer");
	}

	@Override
	public CustomersType opQueryCustomers(
			CustomerSearchCriteriaType customerSearchCriteria) {
		System.out.println("opQueryCustomers");
		return null;
	}

}
