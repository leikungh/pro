/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 订单Entity
 
 * @version 2018-01-13
 */
public class ProOrder extends DataEntity<ProOrder> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 订单编号
	private String receiveName;		// 收件人姓名
	private String receivePhone;		// 收件人手机
	private String receiveAddr;		// 收件人地址
	private Double weight;		// 重量
	private String sendName;		// 寄件人姓名
	private String sendPhone;		// 寄件人手机
	private String sendAddr;		// 寄件人地址
	private String comment;		// 评价
	private String status;		// 物流状态 1已接单 2已发货 3已签收
	
	public ProOrder() {
		super();
	}

	public ProOrder(String id){
		super(id);
	}

	@Length(min=0, max=255, message="订单编号长度必须介于 0 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=64, message="收件人姓名长度必须介于 0 和 64 之间")
	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	
	@Length(min=0, max=64, message="收件人手机长度必须介于 0 和 64 之间")
	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
	
	@Length(min=0, max=255, message="收件人地址长度必须介于 0 和 255 之间")
	public String getReceiveAddr() {
		return receiveAddr;
	}

	public void setReceiveAddr(String receiveAddr) {
		this.receiveAddr = receiveAddr;
	}
	
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	@Length(min=0, max=64, message="寄件人姓名长度必须介于 0 和 64 之间")
	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	
	@Length(min=0, max=64, message="寄件人手机长度必须介于 0 和 64 之间")
	public String getSendPhone() {
		return sendPhone;
	}

	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone;
	}
	
	@Length(min=0, max=255, message="寄件人地址长度必须介于 0 和 255 之间")
	public String getSendAddr() {
		return sendAddr;
	}

	public void setSendAddr(String sendAddr) {
		this.sendAddr = sendAddr;
	}
	
	@Length(min=0, max=255, message="评价长度必须介于 0 和 255 之间")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Length(min=0, max=1, message="物流状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}