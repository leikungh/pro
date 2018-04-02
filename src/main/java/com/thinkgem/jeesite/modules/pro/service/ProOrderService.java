/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.pro.entity.ProOrder;
import com.thinkgem.jeesite.modules.pro.dao.ProOrderDao;

/**
 * 订单Service
 
 * @version 2018-01-13
 */
@Service
@Transactional(readOnly = true)
public class ProOrderService extends CrudService<ProOrderDao, ProOrder> {

	public ProOrder get(String id) {
		return super.get(id);
	}
	
	public List<ProOrder> findList(ProOrder proOrder) {
		return super.findList(proOrder);
	}
	
	public Page<ProOrder> findPage(Page<ProOrder> page, ProOrder proOrder) {
		return super.findPage(page, proOrder);
	}
	
	public Page<ProOrder> findPageByCode(Page<ProOrder> page, ProOrder proOrder) {
		String code=proOrder.getCode();
		if(StringUtils.isEmpty(code)){
			return null;
		}
		return super.findPage(page, proOrder);
	}
	
	public Page<ProOrder> findPage2(Page<ProOrder> page, ProOrder proOrder) {
		return super.findPage2(page, proOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(ProOrder proOrder) {
		super.save(proOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProOrder proOrder) {
		super.delete(proOrder);
	}
	
}