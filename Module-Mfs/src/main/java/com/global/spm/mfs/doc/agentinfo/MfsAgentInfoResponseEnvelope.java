package com.global.spm.mfs.doc.agentinfo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.global.spm.mfs.doc.MfsResponseHeader;

@JacksonXmlRootElement(localName = "Envelope")
public class MfsAgentInfoResponseEnvelope {

	@JacksonXmlProperty(localName = "Body")
	private MfsAgentInfoResponseBody body = null;

	public MfsAgentInfoResponseBody getBody() {
		return body;
	}

	public void setBody(MfsAgentInfoResponseBody body) {
		this.body = body;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MfsAgentInfoResponseEnvelope [");
		if (body != null) {
			builder.append("body=");
			builder.append(body);
		}
		builder.append("]");
		return builder.toString();
	}

	@JacksonXmlRootElement(localName = "Body")
	public class MfsAgentInfoResponseBody {
		@JacksonXmlProperty(localName = "GetAgentInfoResponse")
		MfsResponse successResponse;

		@JacksonXmlProperty(localName = "getAgentInfoResponse")
		MfsResponse errorResponse;

		public MfsResponse getSuccessResponse() {
			return successResponse;
		}

		public void setSuccessResponse(MfsResponse successResponse) {
			this.successResponse = successResponse;
		}

		public MfsResponse getErrorResponse() {
			return errorResponse;
		}

		public void setErrorResponse(MfsResponse errorResponse) {
			this.errorResponse = errorResponse;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("MfsAgentInfoResponseBody [");
			if (successResponse != null) {
				builder.append("successResponse=");
				builder.append(successResponse);
				builder.append(", ");
			}
			if (errorResponse != null) {
				builder.append("errorResponse=");
				builder.append(errorResponse);
			}
			builder.append("]");
			return builder.toString();
		}

		@JacksonXmlRootElement(localName = "MfsResponse")
		public class MfsResponse {

			@JacksonXmlProperty(localName = "ResponseHeader")
			private MfsResponseHeader responseHeader = null;

			@JacksonXmlProperty(localName = "responseBody")
			private MfsAgentInfoResponseDetail responseBody = null;

			public MfsResponseHeader getResponseHeader() {
				return responseHeader;
			}

			public void setResponseHeader(MfsResponseHeader responseHeader) {
				this.responseHeader = responseHeader;
			}

			public MfsAgentInfoResponseDetail getResponseBody() {
				return responseBody;
			}

			public void setResponseBody(MfsAgentInfoResponseDetail responseBody) {
				this.responseBody = responseBody;
			}

			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("MfsResponse [");
				if (responseHeader != null) {
					builder.append("responseHeader=");
					builder.append(responseHeader);
					builder.append(", ");
				}
				if (responseBody != null) {
					builder.append("responseBody=");
					builder.append(responseBody);
				}
				builder.append("]");
				return builder.toString();
			}

			@JacksonXmlRootElement(localName = "MfsAgentInfoResponseDetail")
			public class MfsAgentInfoResponseDetail {
				@JacksonXmlProperty(localName = "agent")
				private MfsAgentInfoDetail agent = null;

				public MfsAgentInfoDetail getAgent() {
					return agent;
				}

				public void setAgent(MfsAgentInfoDetail agent) {
					this.agent = agent;
				}

				@Override
				public String toString() {
					StringBuilder builder = new StringBuilder();
					builder.append("MfsAgentInfoResponseDetail [");
					if (agent != null) {
						builder.append("agent=");
						builder.append(agent);
					}
					builder.append("]");
					return builder.toString();
				}

				@JacksonXmlRootElement(localName = "MfsAgentInfoResponseDetail")
				public class MfsAgentInfoDetail {

					@JacksonXmlProperty(localName = "agentClass")
					private AgentClass agentClass = null;

					@JacksonXmlProperty(localName = "agentGroup")
					private AgentGroup agentGroup = null;

					@JacksonXmlProperty(localName = "agentStatus")
					private AgentStatus agentStatus = null;

					@JacksonXmlProperty(localName = "category")
					private Category category = null;

					@JacksonXmlProperty(localName = "city")
					private City city = null;

					@JacksonXmlProperty(localName = "level")
					private Level level = null;

					// <sch:parent>21</sch:parent>
					@JacksonXmlProperty(localName = "parent")
					private String parent = null;

					// <sch:pointOfSale>1234</sch:pointOfSale>
					@JacksonXmlProperty(localName = "pointOfSale")
					private String pointOfSale = null;

					// <sch:vendor/>
					@JacksonXmlProperty(localName = "vendor")
					private Vendor vendor = null;

					// <sch:address>PRUEBAS EMPE</sch:address>
					// @JacksonXmlProperty(localName = "address")
					// private String address = null;

					// <sch:address2>PRUEBAS EMPE</sch:address2>
					// @JacksonXmlProperty(localName = "address2")
					// private String address2 = null;

					// <sch:birthDate>2016-03-29</sch:birthDate>
					@JacksonXmlProperty(localName = "birthDate")
					private String birthDate = null;

					// <sch:blockadeDate/>
					@JacksonXmlProperty(localName = "blockadeDate")
					private String blockadeDate = null;

					// <sch:brands/>
					// @JacksonXmlProperty(localName = "brands")
					// private String brands = null;

					// <sch:centre>PRUEBAS EMPE</sch:centre>
					@JacksonXmlProperty(localName = "centre")
					private String centre = null;

					// <sch:contact>PRUEBAS EMPE</sch:contact>
					@JacksonXmlProperty(localName = "contact")
					private String contact = null;

					// <sch:documentNumber>PRUEBAS EMPE</sch:documentNumber>
					@JacksonXmlProperty(localName = "documentNumber")
					private String documentNumber = null;

					// <sch:neighborhood>PRUEBAS EMPE</sch:neighborhood>
					// @JacksonXmlProperty(localName = "neighborhood")
					// private String neighborhood = null;

					// <sch:ownerName>PRUEBAS EMPE</sch:ownerName>
					@JacksonXmlProperty(localName = "ownerName")
					private String ownerName = null;

					// <sch:phoneNumber>PRUEBAS EMPE</sch:phoneNumber>
					@JacksonXmlProperty(localName = "phoneNumber")
					private String phoneNumber = null;

					// <sch:reference/>
					@JacksonXmlProperty(localName = "reference")
					private String reference = null;

					// <sch:ruc>PRUEBAS EMPE</sch:ruc>
					@JacksonXmlProperty(localName = "ruc")
					private String ruc = null;

					// <sch:tradeName>PRUEBAS EMPE</sch:tradeName>
					@JacksonXmlProperty(localName = "tradeName")
					private String tradeName = null;

					// <sch:zone/>
					@JacksonXmlProperty(localName = "zone")
					private String zone = null;

					// <sch:agentId>71749</sch:agentId>
					@JacksonXmlProperty(localName = "agentId")
					private String agentId = null;

					public AgentClass getAgentClass() {
						return agentClass;
					}

					public void setAgentClass(AgentClass agentClass) {
						this.agentClass = agentClass;
					}

					public AgentGroup getAgentGroup() {
						return agentGroup;
					}

					public void setAgentGroup(AgentGroup agentGroup) {
						this.agentGroup = agentGroup;
					}

					public AgentStatus getAgentStatus() {
						return agentStatus;
					}

					public void setAgentStatus(AgentStatus agentStatus) {
						this.agentStatus = agentStatus;
					}

					public Category getCategory() {
						return category;
					}

					public void setCategory(Category category) {
						this.category = category;
					}

					public City getCity() {
						return city;
					}

					public void setCity(City city) {
						this.city = city;
					}

					public Level getLevel() {
						return level;
					}

					public void setLevel(Level level) {
						this.level = level;
					}

					public String getParent() {
						return parent;
					}

					public void setParent(String parent) {
						this.parent = parent;
					}

					public String getPointOfSale() {
						return pointOfSale;
					}

					public void setPointOfSale(String pointOfSale) {
						this.pointOfSale = pointOfSale;
					}

					public Vendor getVendor() {
						return vendor;
					}

					public void setVendor(Vendor vendor) {
						this.vendor = vendor;
					}

					// public String getAddress() {
					// return address;
					// }
					//
					// public void setAddress(String address) {
					// this.address = address;
					// }
					//
					// public String getAddress2() {
					// return address2;
					// }
					//
					// public void setAddress2(String address2) {
					// this.address2 = address2;
					// }

					public String getBirthDate() {
						return birthDate;
					}

					public void setBirthDate(String birthDate) {
						this.birthDate = birthDate;
					}

					public String getBlockadeDate() {
						return blockadeDate;
					}

					public void setBlockadeDate(String blockadeDate) {
						this.blockadeDate = blockadeDate;
					}

					// public String getBrands() {
					// return brands;
					// }
					//
					// public void setBrands(String brands) {
					// this.brands = brands;
					// }

					public String getCentre() {
						return centre;
					}

					public void setCentre(String centre) {
						this.centre = centre;
					}

					public String getContact() {
						return contact;
					}

					public void setContact(String contact) {
						this.contact = contact;
					}

					public String getDocumentNumber() {
						return documentNumber;
					}

					public void setDocumentNumber(String documentNumber) {
						this.documentNumber = documentNumber;
					}

					// public String getNeighborhood() {
					// return neighborhood;
					// }
					//
					// public void setNeighborhood(String neighborhood) {
					// this.neighborhood = neighborhood;
					// }

					public String getOwnerName() {
						return ownerName;
					}

					public void setOwnerName(String ownerName) {
						this.ownerName = ownerName;
					}

					public String getPhoneNumber() {
						return phoneNumber;
					}

					public void setPhoneNumber(String phoneNumber) {
						this.phoneNumber = phoneNumber;
					}

					public String getReference() {
						return reference;
					}

					public void setReference(String reference) {
						this.reference = reference;
					}

					public String getRuc() {
						return ruc;
					}

					public void setRuc(String ruc) {
						this.ruc = ruc;
					}

					public String getTradeName() {
						return tradeName;
					}

					public void setTradeName(String tradeName) {
						this.tradeName = tradeName;
					}

					public String getZone() {
						return zone;
					}

					public void setZone(String zone) {
						this.zone = zone;
					}

					public String getAgentId() {
						return agentId;
					}

					public void setAgentId(String agentId) {
						this.agentId = agentId;
					}

					@Override
					public String toString() {
						StringBuilder builder = new StringBuilder();
						builder.append("MfsAgentInfoDetail [");
						if (agentClass != null) {
							builder.append("agentClass=");
							builder.append(agentClass);
							builder.append(", ");
						}
						if (agentGroup != null) {
							builder.append("agentGroup=");
							builder.append(agentGroup);
							builder.append(", ");
						}
						if (agentStatus != null) {
							builder.append("agentStatus=");
							builder.append(agentStatus);
							builder.append(", ");
						}
						if (category != null) {
							builder.append("category=");
							builder.append(category);
							builder.append(", ");
						}
						if (city != null) {
							builder.append("city=");
							builder.append(city);
							builder.append(", ");
						}
						if (level != null) {
							builder.append("level=");
							builder.append(level);
							builder.append(", ");
						}
						if (parent != null) {
							builder.append("parent=");
							builder.append(parent);
							builder.append(", ");
						}
						if (pointOfSale != null) {
							builder.append("pointOfSale=");
							builder.append(pointOfSale);
							builder.append(", ");
						}
						if (vendor != null) {
							builder.append("vendor=");
							builder.append(vendor);
							builder.append(", ");
						}
//						if (address != null) {
//							builder.append("address=");
//							builder.append(address);
//							builder.append(", ");
//						}
//						if (address2 != null) {
//							builder.append("address2=");
//							builder.append(address2);
//							builder.append(", ");
//						}
						if (birthDate != null) {
							builder.append("birthDate=");
							builder.append(birthDate);
							builder.append(", ");
						}
						if (blockadeDate != null) {
							builder.append("blockadeDate=");
							builder.append(blockadeDate);
							builder.append(", ");
						}
//						if (brands != null) {
//							builder.append("brands=");
//							builder.append(brands);
//							builder.append(", ");
//						}
						if (centre != null) {
							builder.append("centre=");
							builder.append(centre);
							builder.append(", ");
						}
						if (contact != null) {
							builder.append("contact=");
							builder.append(contact);
							builder.append(", ");
						}
						if (documentNumber != null) {
							builder.append("documentNumber=");
							builder.append(documentNumber);
							builder.append(", ");
						}
						// if (neighborhood != null) {
						// builder.append("neighborhood=");
						// builder.append(neighborhood);
						// builder.append(", ");
						// }
						if (ownerName != null) {
							builder.append("ownerName=");
							builder.append(ownerName);
							builder.append(", ");
						}
						if (phoneNumber != null) {
							builder.append("phoneNumber=");
							builder.append(phoneNumber);
							builder.append(", ");
						}
						if (reference != null) {
							builder.append("reference=");
							builder.append(reference);
							builder.append(", ");
						}
						if (ruc != null) {
							builder.append("ruc=");
							builder.append(ruc);
							builder.append(", ");
						}
						if (tradeName != null) {
							builder.append("tradeName=");
							builder.append(tradeName);
							builder.append(", ");
						}
						if (zone != null) {
							builder.append("zone=");
							builder.append(zone);
							builder.append(", ");
						}
						if (agentId != null) {
							builder.append("agentId=");
							builder.append(agentId);
						}
						builder.append("]");
						return builder.toString();
					}

					@JacksonXmlRootElement(localName = "agentClass")
					public class AgentClass {
						// <sch:agentClassId>0</sch:agentClassId>
						@JacksonXmlProperty(localName = "agentClassId")
						private String agentClassId = null;

						// <sch:description>Migracion</sch:description>
						@JacksonXmlProperty(localName = "description")
						private String description = null;

						public String getAgentClassId() {
							return agentClassId;
						}

						public void setAgentClassId(String agentClassId) {
							this.agentClassId = agentClassId;
						}

						public String getDescription() {
							return description;
						}

						public void setDescription(String description) {
							this.description = description;
						}

						@Override
						public String toString() {
							StringBuilder builder = new StringBuilder();
							builder.append("AgentClass [");
							if (agentClassId != null) {
								builder.append("agentClassId=");
								builder.append(agentClassId);
								builder.append(", ");
							}
							if (description != null) {
								builder.append("description=");
								builder.append(description);
							}
							builder.append("]");
							return builder.toString();
						}
					}

					@JacksonXmlRootElement(localName = "agentGroup")
					public class AgentGroup {
						// <sch:agentGroupId>142</sch:agentGroupId>
						@JacksonXmlProperty(localName = "agentGroupId")
						private String agentGroupId = null;

						// <sch:name>Grupo Mobile Cash</sch:name>
						@JacksonXmlProperty(localName = "name")
						private String name = null;

						public String getAgentGroupId() {
							return agentGroupId;
						}

						public void setAgentGroupId(String agentGroupId) {
							this.agentGroupId = agentGroupId;
						}

						public String getName() {
							return name;
						}

						public void setName(String name) {
							this.name = name;
						}

						@Override
						public String toString() {
							StringBuilder builder = new StringBuilder();
							builder.append("AgentGroup [");
							if (agentGroupId != null) {
								builder.append("agentGroupId=");
								builder.append(agentGroupId);
								builder.append(", ");
							}
							if (name != null) {
								builder.append("name=");
								builder.append(name);
							}
							builder.append("]");
							return builder.toString();
						}

					}

					@JacksonXmlRootElement(localName = "agentStatus")
					public class AgentStatus {
						// <sch:agentStatusId>0</sch:agentStatusId>
						@JacksonXmlProperty(localName = "agentStatusId")
						private String agentStatusId = null;

						// <sch:description>AC</sch:description>
						@JacksonXmlProperty(localName = "description")
						private String description = null;

						public String getAgentStatusId() {
							return agentStatusId;
						}

						public void setAgentStatusId(String agentStatusId) {
							this.agentStatusId = agentStatusId;
						}

						public String getDescription() {
							return description;
						}

						public void setDescription(String description) {
							this.description = description;
						}

						@Override
						public String toString() {
							StringBuilder builder = new StringBuilder();
							builder.append("AgentStatus [");
							if (agentStatusId != null) {
								builder.append("agentStatusId=");
								builder.append(agentStatusId);
								builder.append(", ");
							}
							if (description != null) {
								builder.append("description=");
								builder.append(description);
							}
							builder.append("]");
							return builder.toString();
						}
					}

					@JacksonXmlRootElement(localName = "category")
					public class Category {
						// <sch:categoryId>1</sch:categoryId>
						@JacksonXmlProperty(localName = "categoryId")
						private String categoryId = null;

						// <sch:description>A</sch:description>
						@JacksonXmlProperty(localName = "description")
						private String description = null;

						public String getCategoryId() {
							return categoryId;
						}

						public void setCategoryId(String categoryId) {
							this.categoryId = categoryId;
						}

						public String getDescription() {
							return description;
						}

						public void setDescription(String description) {
							this.description = description;
						}

						@Override
						public String toString() {
							StringBuilder builder = new StringBuilder();
							builder.append("Category [");
							if (categoryId != null) {
								builder.append("categoryId=");
								builder.append(categoryId);
								builder.append(", ");
							}
							if (description != null) {
								builder.append("description=");
								builder.append(description);
							}
							builder.append("]");
							return builder.toString();
						}
					}

					@JacksonXmlRootElement(localName = "city")
					public class City {
						// <sch:cityId>165</sch:cityId>
						@JacksonXmlProperty(localName = "cityId")
						private String cityId = null;

						// <sch:department>15</sch:department>
						@JacksonXmlProperty(localName = "department")
						private String department = null;

						// <sch:name>ABAI - CAAZAPA</sch:name>
						@JacksonXmlProperty(localName = "name")
						private String name = null;

						public String getCityId() {
							return cityId;
						}

						public void setCityId(String cityId) {
							this.cityId = cityId;
						}

						public String getDepartment() {
							return department;
						}

						public void setDepartment(String department) {
							this.department = department;
						}

						public String getName() {
							return name;
						}

						public void setName(String name) {
							this.name = name;
						}

						@Override
						public String toString() {
							StringBuilder builder = new StringBuilder();
							builder.append("city [");
							if (cityId != null) {
								builder.append("cityId=");
								builder.append(cityId);
								builder.append(", ");
							}
							if (department != null) {
								builder.append("department=");
								builder.append(department);
								builder.append(", ");
							}
							if (name != null) {
								builder.append("name=");
								builder.append(name);
							}
							builder.append("]");
							return builder.toString();
						}
					}

					@JacksonXmlRootElement(localName = "level")
					public class Level {
						// <sch:description>PDV Giros Tigo</sch:description>
						@JacksonXmlProperty(localName = "description")
						private String description = null;

						// <sch:levelId>31</sch:levelId>
						@JacksonXmlProperty(localName = "levelId")
						private String levelId = null;

						// <sch:maxSellAmount>0</sch:maxSellAmount>
						@JacksonXmlProperty(localName = "maxSellAmount")
						private String maxSellAmount = null;

						// <sch:minSellAmount>0</sch:minSellAmount>
						@JacksonXmlProperty(localName = "minSellAmount")
						private String minSellAmount = null;

						public String getDescription() {
							return description;
						}

						public void setDescription(String description) {
							this.description = description;
						}

						public String getLevelId() {
							return levelId;
						}

						public void setLevelId(String levelId) {
							this.levelId = levelId;
						}

						public String getMaxSellAmount() {
							return maxSellAmount;
						}

						public void setMaxSellAmount(String maxSellAmount) {
							this.maxSellAmount = maxSellAmount;
						}

						public String getMinSellAmount() {
							return minSellAmount;
						}

						public void setMinSellAmount(String minSellAmount) {
							this.minSellAmount = minSellAmount;
						}

						@Override
						public String toString() {
							StringBuilder builder = new StringBuilder();
							builder.append("level [");
							if (description != null) {
								builder.append("description=");
								builder.append(description);
								builder.append(", ");
							}
							if (levelId != null) {
								builder.append("levelId=");
								builder.append(levelId);
								builder.append(", ");
							}
							if (maxSellAmount != null) {
								builder.append("maxSellAmount=");
								builder.append(maxSellAmount);
								builder.append(", ");
							}
							if (minSellAmount != null) {
								builder.append("minSellAmount=");
								builder.append(minSellAmount);
							}
							builder.append("]");
							return builder.toString();
						}
					}

					@JacksonXmlRootElement(localName = "vendor")
					public class Vendor {
						// <sch:name>ENRIQUE</sch:name>
						@JacksonXmlProperty(localName = "name")
						private String name = null;
						// <sch:surname>PAVÓN</sch:surname>
						@JacksonXmlProperty(localName = "surname")
						private String surname = null;
						// <sch:vendorId>7381</sch:vendorId>
						@JacksonXmlProperty(localName = "vendorId")
						private String vendorId = null;

						public String getName() {
							return name;
						}

						public void setName(String name) {
							this.name = name;
						}

						public String getSurname() {
							return surname;
						}

						public void setSurname(String surname) {
							this.surname = surname;
						}

						public String getVendorId() {
							return vendorId;
						}

						public void setVendorId(String vendorId) {
							this.vendorId = vendorId;
						}

						@Override
						public String toString() {
							StringBuilder builder = new StringBuilder();
							builder.append("Vendor [");
							if (name != null) {
								builder.append("name=");
								builder.append(name);
								builder.append(", ");
							}
							if (surname != null) {
								builder.append("surname=");
								builder.append(surname);
								builder.append(", ");
							}
							if (vendorId != null) {
								builder.append("vendorId=");
								builder.append(vendorId);
							}
							builder.append("]");
							return builder.toString();
						}
					}
				}
			}
		}
	}

}
