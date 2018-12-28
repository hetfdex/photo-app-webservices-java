package com.hetfdex.webservices.mobileapp.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "addresses")
public class AddressEntity implements Serializable {
	private static final long serialVersionUID = 7374971455814333679L;

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false, unique = true)
	private String addressID;

	@Column(nullable = false, length = 12)
	private String type;

	@Column(nullable = false, length = 24)
	private String city;

	@Column(nullable = false, length = 24)
	private String country;

	@Column(nullable = false, length = 8)
	private String postCode;

	@ManyToOne
	@JoinColumn(name = "users_id")
	private UserEntity userEntity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddressID() {
		return addressID;
	}

	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
}