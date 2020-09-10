package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="jyusyo")
public class User implements Serializable {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 * 	名前
	 */
	@Column(name="name")
	private String name;

	/**
	 * 住所
	 */
	@Column(name="address")
	private String address;

	/**
	 * 電話番号
	 */
	@Column(name="tel")
	private String tel;

	/**
	 * 削除フラグ
	 */
	@Column(name="delete_flg")
	private boolean delete_flg;

	public String getAddress() {
		return address;
	}
}