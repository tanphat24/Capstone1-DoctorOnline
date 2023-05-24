package com.example.doctor.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	private boolean status;

}
