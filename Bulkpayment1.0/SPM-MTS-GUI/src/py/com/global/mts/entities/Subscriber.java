package py.com.global.mts.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SUBSCRIBER database table.
 * 
 */
@Entity
@Table(name="SUBSCRIBER")
@NamedQuery(name="Subscriber.findAll", query="SELECT s FROM Subscriber s")
public class Subscriber implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SUBSCRIBER_ID", unique=true, nullable=false, precision=9)
	private long subscriberId;

	@Column(length=20)
	private String apartment;

	@Temporal(TemporalType.DATE)
	@Column(name="BIRTH_DATE", nullable=false)
	private Date birthDate;

	@Column(name="BLOCKADE_DATE")
	private Date blockadeDate;

	@Column(name="BRAND_ID", nullable=false, precision=4)
	private BigDecimal brandId;

	@Column(length=50)
	private String city;

	@Column(name="CITY_ID", length=20)
	private String cityId;

	@Column(name="CONTACT_PHONE", length=20)
	private String contactPhone;

	@Column(name="CREATION_DATE", nullable=false)
	private Date creationDate;

	private Date deleted;

	@Column(length=50)
	private String department;

	@Column(name="DOCUMENT_NUMBER", nullable=false, length=50)
	private String documentNumber;

	@Column(name="DOCUMENT_TYPE", length=20)
	private String documentType;

	@Column(name="DOOR_NUMBER", length=20)
	private String doorNumber;

//	@Column(name="\"FLOOR\"", length=20)
//	private String floor;

	@Column(nullable=false, length=1)
	private String gender;

	@Column(name="KEY_WORD", length=50)
	private String keyWord;

	@Column(name="KYC_LEVEL_ID", precision=2)
	private BigDecimal kycLevelId;

//	@Column(name="\"LANGUAGE\"", nullable=false, length=4)
//	private String language;

	@Column(name="LEVEL_ID", nullable=false, precision=4)
	private BigDecimal levelId;

	@Column(name="MOBILE_OPERATOR_ID", precision=9)
	private BigDecimal mobileOperatorId;

	@Column(nullable=false, length=20)
	private String msisdn;

	@Column(length=50)
	private String name;

	@Column(length=50)
	private String nationality;

	@Column(length=50)
	private String neighborhood;

	@Column(name="NORMAL_STATUS_TS")
	private Date normalStatusTs;

	@Column(name="NORMAL_STATUS_USER_ID", length=20)
	private String normalStatusUserId;

	@Column(name="PARENT_ID", precision=9)
	private BigDecimal parentId;

	@Column(nullable=false, length=32)
	private String pin;

	@Column(name="PROFILE_ID", nullable=false, precision=4)
	private BigDecimal profileId;

	@Column(precision=1)
	private BigDecimal remittance;

	@Temporal(TemporalType.DATE)
	@Column(name="REMITTANCE_DATE")
	private Date remittanceDate;

	@Column(name="REMITTANCE_VENDOR_ID", precision=9)
	private BigDecimal remittanceVendorId;

	@Column(name="RESET_PIN", nullable=false, length=1)
	private String resetPin;

	@Column(length=50)
	private String ruc;

	@Column(length=100)
	private String street;

	@Column(name="SUBSCRIBER_STATUS_ID", nullable=false, precision=4)
	private BigDecimal subscriberStatusId;

	@Column(length=50)
	private String surname;

	@Column(name="VENDOR_ID", precision=9)
	private BigDecimal vendorId;

	public Subscriber() {
	}

	public long getSubscriberId() {
		return this.subscriberId;
	}

	public void setSubscriberId(long subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getApartment() {
		return this.apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getBlockadeDate() {
		return this.blockadeDate;
	}

	public void setBlockadeDate(Date blockadeDate) {
		this.blockadeDate = blockadeDate;
	}

	public BigDecimal getBrandId() {
		return this.brandId;
	}

	public void setBrandId(BigDecimal brandId) {
		this.brandId = brandId;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityId() {
		return this.cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDocumentNumber() {
		return this.documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDoorNumber() {
		return this.doorNumber;
	}

	public void setDoorNumber(String doorNumber) {
		this.doorNumber = doorNumber;
	}

//	public String getFloor() {
//		return this.floor;
//	}
//
//	public void setFloor(String floor) {
//		this.floor = floor;
//	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public BigDecimal getKycLevelId() {
		return this.kycLevelId;
	}

	public void setKycLevelId(BigDecimal kycLevelId) {
		this.kycLevelId = kycLevelId;
	}

//	public String getLanguage() {
//		return this.language;
//	}
//
//	public void setLanguage(String language) {
//		this.language = language;
//	}

	public BigDecimal getLevelId() {
		return this.levelId;
	}

	public void setLevelId(BigDecimal levelId) {
		this.levelId = levelId;
	}

	public BigDecimal getMobileOperatorId() {
		return this.mobileOperatorId;
	}

	public void setMobileOperatorId(BigDecimal mobileOperatorId) {
		this.mobileOperatorId = mobileOperatorId;
	}

	public String getMsisdn() {
		return this.msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getNeighborhood() {
		return this.neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public Date getNormalStatusTs() {
		return this.normalStatusTs;
	}

	public void setNormalStatusTs(Date normalStatusTs) {
		this.normalStatusTs = normalStatusTs;
	}

	public String getNormalStatusUserId() {
		return this.normalStatusUserId;
	}

	public void setNormalStatusUserId(String normalStatusUserId) {
		this.normalStatusUserId = normalStatusUserId;
	}

	public BigDecimal getParentId() {
		return this.parentId;
	}

	public void setParentId(BigDecimal parentId) {
		this.parentId = parentId;
	}

	public String getPin() {
		return this.pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public BigDecimal getProfileId() {
		return this.profileId;
	}

	public void setProfileId(BigDecimal profileId) {
		this.profileId = profileId;
	}

	public BigDecimal getRemittance() {
		return this.remittance;
	}

	public void setRemittance(BigDecimal remittance) {
		this.remittance = remittance;
	}

	public Date getRemittanceDate() {
		return this.remittanceDate;
	}

	public void setRemittanceDate(Date remittanceDate) {
		this.remittanceDate = remittanceDate;
	}

	public BigDecimal getRemittanceVendorId() {
		return this.remittanceVendorId;
	}

	public void setRemittanceVendorId(BigDecimal remittanceVendorId) {
		this.remittanceVendorId = remittanceVendorId;
	}

	public String getResetPin() {
		return this.resetPin;
	}

	public void setResetPin(String resetPin) {
		this.resetPin = resetPin;
	}

	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public BigDecimal getSubscriberStatusId() {
		return this.subscriberStatusId;
	}

	public void setSubscriberStatusId(BigDecimal subscriberStatusId) {
		this.subscriberStatusId = subscriberStatusId;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public BigDecimal getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(BigDecimal vendorId) {
		this.vendorId = vendorId;
	}

}