package com.example.doctor.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "address")
	private String address;

	@Column(name = "status")
	private boolean status;

	@Override
	public String toString() {
		return getFullName();
	}

}
