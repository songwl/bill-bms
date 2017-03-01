package com.yipeng.bill.bms.core.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.springframework.util.Assert;

import com.yipeng.bill.bms.core.spring.interceptor.HttpLocalThread;
import com.yipeng.bill.bms.core.utils.WebUtils;

/**
 * Created by lancey on 15/1/12.
 */
public class Page<T> implements Serializable {

	private int page;
	private int pageSize;
	private int totalCount;

	private int totalPage;
	private String url;
	private List<T> itemList = Collections.emptyList();
	private List<String> orderBy;

	/**
	 *
	 * @param totalCount 总行数
	 * @param page  当前页
	 * @param pageSize   页行数
	 */
	public Page(int totalCount, int page, int pageSize) {
		this(page, pageSize);
		setTotalCount(totalCount);
	}

	public Page(int page, int pageSize) {
		Assert.isTrue(pageSize > 0);
		this.page = page < 1 ? 1 : page;
		this.pageSize = pageSize;
	}

	/**
	 * 该方法只给序列化使用
	 */
	@Deprecated
	public Page() {

	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		totalPage = (int) Math.ceil(totalCount / (double) pageSize);
	}

	public List<T> getItemList() {
		return itemList;
	}

	public void setItemList(List<T> itemList) {
		this.itemList = itemList;
	}

	public boolean hasNext() {
		return page < totalPage;
	}

	public boolean hasPrev() {
		return page > 1;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getPage() {
		return page;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getBeginRow() {
		return (getPage() - 1) * getPageSize();
	}

	/**
	 * 组建url参数，
	 * 这个方法只能在Action层使用，其中使用到@{HttpLocalThread.getRequest()}
	 */
	public void makeRequestUrl() {
		this.url = WebUtils.getUrl(HttpLocalThread.getRequest());
	}

	public void makeRequestUrl(String basePath) {
		this.url = WebUtils.getUrl(HttpLocalThread.getRequest(), basePath, true, null);
	}

	public List<String> getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(List<String> orderBy) {
		this.orderBy = orderBy;
	}
}
