package com.example.demo.dto;

import java.io.Serializable;

import lombok.Data;


@Data
public class UserRequest implements Serializable {

	/**
	 * 名前
	 */
	// @NotEmpty(message = "名前必修項目です")
	// @Size(max = 20, message = "名前は20文字以内で入力してください")
	private String name;

	/**
	 * 住所
	 */
	// @NotEmpty(message = "住所は必修項目です")
	//@Size(max = 40, message = "住所は40文字以内で入力してください")
	private String address;

	/**
	 * 電話番号
	 */
	// @Pattern(regexp = "\\d{1,4}-\\d{1,4}-\\d{4}", message = "電話番号は「000ｰ0000ｰ0000」の形式で入力してください")
	private String tel;
}
