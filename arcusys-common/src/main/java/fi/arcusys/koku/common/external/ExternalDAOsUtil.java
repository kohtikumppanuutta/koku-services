package fi.arcusys.koku.common.external;

import fi.arcusys.koku.common.soa.User;
import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.entity.customer.v1.ElectronicContactInfoType;
import fi.koku.services.entity.customer.v1.ElectronicContactInfosType;
import fi.koku.services.entity.customer.v1.PhoneNumberType;
import fi.koku.services.entity.customer.v1.PhoneNumbersType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 17, 2011
 */
class ExternalDAOsUtil {

    static User convertCustomerToUser(final CustomerType customer, final String userUid) {
        final User user = new User();
        user.setDisplayName(customer.getEtuNimi() + " " + customer.getSukuNimi());
        user.setEmail(getEmailAsString(customer.getElectronicContactInfos()));
        user.setFirstname(customer.getEtuNimi());
        user.setLastname(customer.getSukuNimi());
        final PhoneNumbersType phoneNumbers = customer.getPhoneNumbers();
        user.setPhoneNumber(getPhonesAsString(phoneNumbers));
        user.setUid(userUid);
        return user;
    }

    private static String getEmailAsString(final ElectronicContactInfosType electronicContactInfos) {
        if (electronicContactInfos == null || electronicContactInfos.getEContactInfo() == null || electronicContactInfos.getEContactInfo().isEmpty()) {
            return "";
        }
        
        final StringBuilder result = new StringBuilder();
        for (final ElectronicContactInfoType info : electronicContactInfos.getEContactInfo()) {
            result.append(info.getContactInfo()).append(";");
        }
        
        if (result.length() > 0 ) {
            result.setLength(result.length() - 1);
        }
        
        return result.toString();
    }

    private static String getPhonesAsString(final PhoneNumbersType phoneNumbers) {
        if (phoneNumbers == null || phoneNumbers.getPhone() == null || phoneNumbers.getPhone().isEmpty()) {
            return "";
        }
        final StringBuilder result = new StringBuilder();
        for (final PhoneNumberType number : phoneNumbers.getPhone()) {
            result.append(number.getPuhelinnumeroTeksti()).append(";");
        }
        
        if (result.length() > 0 ) {
            result.setLength(result.length() - 1);
        }
        
        return result.toString();
    }

}
