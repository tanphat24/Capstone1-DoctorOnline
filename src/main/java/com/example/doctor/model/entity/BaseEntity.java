package com.example.doctor.model.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@NoArgsConstructor
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;

	@Version
	protected Long version;

	@CreationTimestamp
	protected Date createdOn;

	@UpdateTimestamp
	protected Date updatedOn;

}
