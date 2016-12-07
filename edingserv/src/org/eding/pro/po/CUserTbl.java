package org.eding.pro.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CUserTbl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "c_user_tbl", catalog = "eding")
public class CUserTbl implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String passwd;

	// Constructors

	/** default constructor */
	public CUserTbl() {
	}

	/** full constructor */
	public CUserTbl(String name, String passwd) {
		this.name = name;
		this.passwd = passwd;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "passwd", length = 40)
	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}