package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

/**
 * 情報 Controller
 */
@Controller
public class UserController {
	/**
	 * 情報 Service
	 */
	@Autowired
	private UserService userService;

	/**
	 * 一覧画面、検索機能
	 */
	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String searchList(String address, @ModelAttribute("formModel") User user, Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
		Page<User> result = userService.searchAddress(user.getAddress(), pageable);

		int totalpage = result.getTotalPages();
		int firstpage = 0;
		String tooltripaddress = "<span class=\"tooltip\" th:utext=\"${user.address}\"></span>";

		model.addAttribute("address", address);
		if (totalpage > 5) {
			firstpage = totalpage - 5;
			totalpage = 5;
		}
		model.addAttribute("tooltrip", tooltripaddress);
		model.addAttribute("mnpage", firstpage);
		model.addAttribute("mxpage", totalpage);
		model.addAttribute("page", result);
		model.addAttribute("users", result.getContent());

		return "user/list";
	}

	@RequestMapping(value = "/user/search", method = RequestMethod.POST)
	public String searchAdd(@RequestParam("address") String address, @ModelAttribute("formModel") User user, Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
		Page<User> result = userService.searchAddress(user.getAddress(), pageable);

		int totalpage = result.getTotalPages();
		int firstpage = 0;

		model.addAttribute("address", address);
		if (totalpage > 5) {
			firstpage = totalpage - 5;
			totalpage = 5;
		}
		model.addAttribute("mnpage", firstpage);
		model.addAttribute("mxpage", totalpage);
		model.addAttribute("address", address);
		model.addAttribute("page", result);
		model.addAttribute("users", result.getContent());


		return "/user/list";
	}


	/**
	 * 住所録新規登録画面を表示
	 * @param model Model
	 * @return 情報一覧画面
	*/
	@GetMapping(value = "/user/add")
	public String displayAdd(Model model) {
		model.addAttribute("userRequest", new UserRequest());
		return "user/add";
	}


	/**
	 * 登録確認
	 * @param model Model
	 * @return 登録確認
	 */
	@RequestMapping(value = "/user/addcheck", method = RequestMethod.POST)
	public String displayAddCheck(@ModelAttribute UserRequest userRequest, HttpServletRequest request,  Model model) {

		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String tel = request.getParameter("tel");
		String errmsg[];
		errmsg = userService.getErr(name, address, tel);

		if (errmsg[0] != "" || errmsg[1] != "" || errmsg[2] != "") {
			model.addAttribute("validationError", errmsg);
			return "user/add";
		} else {
			return "user/addcheck";
		}
	}


	/**
	 * 新規登録
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return 一覧画面
	 */
	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	public String create(UserRequest userRequest) {
		// ユーザー情報の登録
		userService.create(userRequest);
		return "redirect:/user/list";
	}


	/**
	 * 編集画面の表示
	 * @param id 表示するユーザーID
	 * @param model Model
	 * @return ユーザー編集の画面
	 */
	@GetMapping("/user/{id}/edit")
	public String displayEdit(@PathVariable int id, Model model) {
		User user = userService.findById(id);

		UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
		userUpdateRequest.setId(user.getId());
		userUpdateRequest.setName(user.getName());
		userUpdateRequest.setAddress(user.getAddress());
		userUpdateRequest.setTel(user.getTel().substring(0, 3) + "-" + user.getTel().substring(3, 7) + "-" + user.getTel().substring(7, 11));
		model.addAttribute("userUpdateRequest", userUpdateRequest);
		return "user/edit";
	}

	/**
	 * 更新確認
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return ユーザー情報の詳細画面
	 */
	@RequestMapping(value="/user/editcheck", method=RequestMethod.POST)
	public String displayEditCheck(@ModelAttribute UserUpdateRequest userUpdateRequest, HttpServletRequest request, Model model) {
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String tel = request.getParameter("tel");
		String errmsg[];
		errmsg = userService.getErr(name, address, tel);

		if (errmsg[0] != "" || errmsg[1] != "" || errmsg[2] != "") {
			model.addAttribute("validationError", errmsg);
			return "user/edit";
		} else {
			return "user/editcheck";
		}
	}

	/**
	 * 更新
	 * @param userUpdateRequest リクエストデータ
	 * @param model Model
	 * @return 一覧画面
	 */
	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	public String update(UserUpdateRequest userUpdateRequest) {

		// ユーザー情報の更新
		userService.update(userUpdateRequest);
		return "redirect:/user/list";
	}

	/**
	 * 削除画面の表示
	 * @param id 表示するユーザーID
	 * @param model Model
	 * @return ユーザー削除の画面
	 */
	@GetMapping("/user/{id}/delete")
	public String displayDelete(@PathVariable int id, Model model) {
		User user = userService.findById(id);

		UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
		userUpdateRequest.setId(user.getId());
		userUpdateRequest.setName(user.getName());
		userUpdateRequest.setAddress(user.getAddress());
		userUpdateRequest.setTel(user.getTel());
		model.addAttribute("userUpdateRequest", userUpdateRequest);
		return "user/delete";
	}

	/**
	 * 削除
	 * @param userUpdateRequest リクエストデータ
	 * @param model Model
	 * @return 一覧画面
	 */
	@RequestMapping(value = "/user/delete", method = RequestMethod.POST)
	public String delete(UserUpdateRequest userUpdateRequest) {

		// ユーザー情報の更新
		userService.delete(userUpdateRequest);
		return "redirect:/user/list";
	}
}
