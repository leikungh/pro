/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.ProOrder;
import com.thinkgem.jeesite.modules.pro.service.ProOrderService;

/**
 * 订单Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/proOrder")
public class ProOrderController extends BaseController {

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

	@RequiresPermissions("pro:proOrder:view")
	@RequestMapping(value = { "list", "" })
	public String list(ProOrder proOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProOrder> page = proOrderService.findPage2(new Page<ProOrder>(request, response), proOrder);
		model.addAttribute("page", page);
		return "modules/pro/proOrderList";
	}

	// 已完成订单列表
	@RequiresPermissions("pro:proOrder:view")
	@RequestMapping(value = "list2")
	public String list2(ProOrder proOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		proOrder.setStatus("3");
		Page<ProOrder> page = proOrderService.findPage(new Page<ProOrder>(request, response), proOrder);
		model.addAttribute("page", page);
		return "modules/pro/finishProOrderList";
	}

	// 物流列表
	@RequiresPermissions("pro:proOrder:view")
	@RequestMapping(value = "wllist")
	public String wllist(ProOrder proOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProOrder> page = proOrderService.findPage(new Page<ProOrder>(request, response), proOrder);
		model.addAttribute("page", page);
		return "modules/pro/wuliuProOrderList";
	}

	@RequiresPermissions("pro:proOrder:view")
	@RequestMapping(value = "form")
	public String form(ProOrder proOrder, Model model) {
		model.addAttribute("proOrder", proOrder);
		return "modules/pro/proOrderForm";
	}

	// 物流表单
	@RequiresPermissions("pro:proOrder:view")
	@RequestMapping(value = "wlform")
	public String wlform(ProOrder proOrder, Model model) {
		model.addAttribute("proOrder", proOrder);
		return "modules/pro/wuliuproOrderForm";
	}
	
	// 已完成表单
	@RequiresPermissions("pro:proOrder:view")
	@RequestMapping(value = "finishForm")
	public String finishForm(ProOrder proOrder, Model model) {
		model.addAttribute("proOrder", proOrder);
		return "modules/pro/finishProOrderForm";
	}

	@RequiresPermissions("pro:proOrder:edit")
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
		proOrderService.save(proOrder);
		addMessage(redirectAttributes, "保存订单成功,订单编号为：" + code);
		return "redirect:" + Global.getAdminPath() + "/pro/proOrder/?repage";
	}

	@RequiresPermissions("pro:proOrder:edit")
	@RequestMapping(value = "wlsave")
	public String wlsave(ProOrder proOrder, Model model, RedirectAttributes redirectAttributes) {
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
		proOrderService.save(proOrder);
		addMessage(redirectAttributes, "保存订单成功,订单编号为：" + code);
		return "redirect:" + Global.getAdminPath() + "/pro/proOrder/wllist?repage";
	}

	@RequiresPermissions("pro:proOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(ProOrder proOrder, RedirectAttributes redirectAttributes) {
		proOrderService.delete(proOrder);
		addMessage(redirectAttributes, "删除订单成功");
		return "redirect:" + Global.getAdminPath() + "/pro/proOrder/?repage";
	}

	@RequiresPermissions("pro:proOrder:edit")
	@RequestMapping(value = "wldelete")
	public String wldelete(ProOrder proOrder, RedirectAttributes redirectAttributes) {
		proOrderService.delete(proOrder);
		addMessage(redirectAttributes, "删除订单成功");
		return "redirect:" + Global.getAdminPath() + "/pro/proOrder/wllist?repage";
	}
	
	@RequiresPermissions("pro:proOrder:edit")
	@RequestMapping(value = "finishDelete")
	public String finishDelete(ProOrder proOrder, RedirectAttributes redirectAttributes) {
		proOrderService.delete(proOrder);
		addMessage(redirectAttributes, "删除订单成功");
		return "redirect:" + Global.getAdminPath() + "/pro/proOrder/list2?repage";
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