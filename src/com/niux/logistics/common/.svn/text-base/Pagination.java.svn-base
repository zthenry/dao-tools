package com.niux.logistics.common;

import java.util.logging.Logger;

/* 
 * Copyright (C) 2010 Aspire
 * All Rights Reserved 
 * Description: 翻页bean，该类存放了翻页需要的基本信息
 * 
 * Modification History: 
 **********************************************************
 * Date		      Author		  Comments
 **********************************************************
 * 2010/8/11      ZhanZhicheng    Init Version
 */

public class Pagination {
	/*
	 * 排序属性名（po中的属性名）
	 */
	private String orderBy;
	
	/*
	 * 升序，降序
	 * asc：升序
	 * desc：降序
	 */
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	private String ascDesc = ASC;
	
	/*
	 * 每页数量 
	 */
	private int countEachPage = 10;
	
	/*
	 * 页数, start from 0
	 */
	private int pageNum = 0;
	
	/*
	 * 起始索引， 
	 */
	private int startIndex = 0;
	/*
	 * 结束索引， 
	 */
	private int endIndex = 3;
	
	/*
	 * 数据库中所有页数
	 */
	private long totalPage;
	
	/*
	 * 数据库中元素个数
	 */
	private long totalNum;
	/*
	 *当前页元素的个数 
	 */
	private int currentNum;
	private String reqUrl;
	public Pagination(){
		
	}
	
	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getAscDesc() {
		return ascDesc;
	}

	public void setAscDesc(String ascDesc) {
		this.ascDesc = ascDesc;
	}

	public int getCountEachPage() {
		return countEachPage;
	}

	public void setCountEachPage(int countEachPage) {
		this.countEachPage = countEachPage;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public long getTotalNum() {
		return totalNum;
	}
	/**
	 * 设置总个数
	 * 总页数
	 * 当前个数
	 * @param long1
	 */
	public void setTotalNum(Long long1) {
		if(long1>0)
		{
			totalPage = long1/countEachPage;
			if(long1%countEachPage!=0)totalPage++;
			
			currentNum = countEachPage;
			if((pageNum+1)==totalPage)
			{
				currentNum = (int) (long1 -pageNum*countEachPage);	
			}
		}else{
			totalPage = 1;
			currentNum = 0;
		}
		
		this.totalNum = long1;
	}
	
	public void packParam(){
		startIndex = pageNum*countEachPage;
	}

	public int getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}

	public static String getAsc() {
		return ASC;
	}

	public static String getDesc() {
		return DESC;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	
//	public void setTotalNum(long totalNum) {
//		this.totalNum = totalNum;
//	}
	
}
