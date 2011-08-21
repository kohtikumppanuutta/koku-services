package fi.koku.services.entity.community.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


/**
 * Customer entity.
 * 
 * @author Ixonos / aspluma
 */
@Entity
public class Customer {
  @Id
  @GeneratedValue
  private Long id; // FIXME
  
  private String status;
  
  @Column(name="status_date")
  @Temporal(TemporalType.DATE)
  private Date statusDate;

  private String pic;
  
  @Column(name="birth_date")
  @Temporal(TemporalType.DATE)
  private Date birthDate;
  
  private String lastName;
  
  @Column(name="first_name")
  private String firstName;

  @Column(name="first_names")
  private String firstNames;

  private String nationality;
  
  private String municipality;
  
  private boolean turvakielto;
  
  @Version
  private int version;

  public Customer() {
  }
  
  public Long getId() {
    return id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getStatusDate() {
    return statusDate;
  }

  public void setStatusDate(Date statusDate) {
    this.statusDate = statusDate;
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstNames() {
    return firstNames;
  }

  public void setFirstNames(String firstNames) {
    this.firstNames = firstNames;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public String getMunicipality() {
    return municipality;
  }

  public void setMunicipality(String municipality) {
    this.municipality = municipality;
  }

  public boolean isTurvakielto() {
    return turvakielto;
  }

  public void setTurvakielto(boolean turvakielto) {
    this.turvakielto = turvakielto;
  }

  public int getVersion() {
    return version;
  }

}
