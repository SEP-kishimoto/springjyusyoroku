package com.example.demo.service;

import java.io.UnsupportedEncodingException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

/**
 * ユーザー情報 Service
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class UserService {

	/**
	 * ユーザー情報 Repository
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * ユーザー情報の検索
	 */
	public Page<User> searchAddress(String address, Pageable pageable) {
		Page<User> user = null;

		if (address == null) {
			user = userRepository.findAll(pageable);
		} else {
			user = userRepository.findByNameContaining(address, pageable);
		}

		return user;
	}




	/**
	 * ユーザー情報のCreate
	 * @param userRequest
	 */

	public void create(UserRequest userRequest) {

		User user = new User();
		user.setName(userRequest.getName());
		user.setAddress(userRequest.getAddress());
		user.setTel(userRequest.getTel().replace("-", ""));
		userRepository.save(user);
	}

	public String[] getErr(String name, String address, String tel) {

		// 定数宣言
		final String ERRMSG_NAME01 = "名前は全角20文字以内で入力してください";
		final String ERRMSG_NAME02 = "名前は必須項目です";
		final String ERRMSG_ADDRESS01 = "住所は全角40文字以内で入力してください";
		final String ERRMSG_ADDRESS02 = "住所は必須項目です";
		final String ERRMSG_TEL01 = "電話番号は「000-0000-0000」の形式で入力してください";

		// 戻り値の変数
		String err1 = "";
		String err2 = "";
		String err3 = "";
		String returnVal[] = {err1, err2, err3};

		if (Bytes(name) > 40) {
			returnVal[0] = ERRMSG_NAME01;
		} else if (Bytes(name) == 0) {
			returnVal[0] = ERRMSG_NAME02;
		}

		if (Bytes(address) > 80) {
			returnVal[1] = ERRMSG_ADDRESS01;
		} else if (Bytes(address) == 0) {
			returnVal[1] = ERRMSG_ADDRESS02;
		}

		if (Bytes(tel) != 0 && !(tel.matches("\\d{3}-\\d{4}-\\d{4}"))) {//"^\\d{3}-\\d{4}-\\d{4}$" "^0\\d{2,3}-\\d{1,4}-\\d{4}$"
			returnVal[2] = ERRMSG_TEL01;
		}

		return returnVal;
	}

	public int Bytes(String value) {
		int bytes = 0;

		if (value == null) value = "";

		try {
			bytes = value.getBytes("SJIS").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bytes;

	}


	/**
	 * ユーザー情報 主キー検索
	 * @return 検索結果
	 */
	public User findById(int id) {
		return userRepository.findById(id).get();
	}

	/**
	 * ユーザー情報 更新
	 * @param user ユーザー情報
	 */
	public void update(UserUpdateRequest userUpdateRequest) {
		User user = findById(userUpdateRequest.getId());

		user.setName(userUpdateRequest.getName());
		user.setAddress(userUpdateRequest.getAddress());
		user.setTel(userUpdateRequest.getTel().replace("-", ""));
		userRepository.save(user);
	}

	/**
	 * ユーザー情報 削除
	 * @param user ユーザー情報
	 */
	public void delete(UserUpdateRequest userUpdateRequest) {
		User user = findById(userUpdateRequest.getId());

		user.setDelete_flg(true);
		userRepository.save(user);
	}

}
