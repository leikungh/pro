/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.pro.entity.ProOrder;
import com.thinkgem.jeesite.modules.pro.service.ProOrderService;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 订单Controller
 */
@Controller
@RequestMapping(value = "${addOrderPath}")
public class AddOrderController extends BaseController {

	@Autowired
	private ProOrderService proOrderService;

	@ModelAttribute
	public ProOrder get(@RequestParam(required = false) String id) {
		ProOrder entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = proOrderService.get(id);
		}
		if (entity == null) {
			entity = new ProOrder();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(ProOrder proOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProOrder> page = proOrderService.findPageByCode(new Page<ProOrder>(request, response), proOrder);
		model.addAttribute("page", page);
		return "modules/pro/addOrderList";
	}

	@RequestMapping(value = "form")
	public String form(ProOrder proOrder, Model model) {
		model.addAttribute("proOrder", proOrder);
		return "modules/pro/addOrderForm";
	}

	@RequestMapping(value = "save")
	public String save(ProOrder proOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, proOrder)) {
			return form(proOrder, model);
		}
		String code = proOrder.getCode();
		if (StringUtils.isEmpty(code)) {
			// 默认状态是1，已收订单
			proOrder.setStatus("1");
			code = getOrderIdByTime();
			proOrder.setCode(code);
		}
		User u = new User();
		u.setId("1");
		proOrder.setCreateBy(u);
		proOrder.setUpdateBy(u);
		proOrderService.save(proOrder);
		addMessage(redirectAttributes, "保存订单成功,订单编号为：" + code);
		return "redirect:" + Global.getFrontPath() + "/?code=" + code;
	}
	
	@RequestMapping(value = "saveComment")
	public String saveComment(ProOrder proOrder, HttpServletRequest request, HttpServletResponse response) {
		String code=request.getParameter("code");
		String comment=request.getParameter("comment");
		proOrder.setCode(code);
		List<ProOrder>proOrderList=proOrderService.findList(proOrder);
		if(null!=proOrderList&&0<proOrderList.size()){
			ProOrder po=new ProOrder();
			po=proOrderList.get(0);
			po.setComment(comment);
			proOrderService.save(po);
		}
		return "redirect:" + Global.getFrontPath();
	}

	/**
	 * 获得一个UUID
	 * 
	 * @return String UUID
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		// 去掉“-”符号
		return uuid.replaceAll("-", "");
	}

	// 时间戳+随机数三位获取订单号
	public static String getOrderIdByTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String newDate = sdf.format(new Date());
		String result = "";
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			result += random.nextInt(10);
		}
		return newDate + result;
	}
}