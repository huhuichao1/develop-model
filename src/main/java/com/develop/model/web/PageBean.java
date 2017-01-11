package com.develop.model.web;

import java.io.Serializable;
import java.util.Collection;

/**
 * 分页结果信息
 * User: wuqingming
 * Date: 13-8-30
 * Time: 下午4:03
 */
public class PageBean<T> implements Serializable {
    //默认每页条数
    public static final int DEFAULT_PAGE_SIZE = 10;
    //开始位置，默认为0
    private long offset = 0;
    //每页的条数
    private int limit = DEFAULT_PAGE_SIZE;
    //此次查询的总记录数（并不一定是当前页的记录条数）
    private long totalCount;
    //存放查询的返回结果
    private Collection<T> data;

    public PageBean() {

    }

    /**
     * 作为查询条件的PageBean构造方法
     * @param offset    当前页号
     * @param limit  页大小
     */
    public PageBean(long offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    /**
     * 作为查询结果的PageBean的构造方法
     * @param totalCount    总记录数
     * @param data    分页查询结果数据
     */
    public PageBean(long totalCount, Collection<T> data) {
        this.totalCount = totalCount;
        this.data = data;
    }

    public int getPageSize() {
        return limit;
    }

    public <C extends Collection<T>> C getData() {
        return data == null ? null : (C)data;
    }

    public long getTotalCount() {
        return totalCount;
    }

    /**
     * 获取总页数
     * @return
     */
    public long getTotalPage() {
        long totalPage = 0;
        if (totalCount > 0) {
            totalPage = ((totalCount % limit == 0) ? (totalCount / limit)
                    : (totalCount / limit + 1));
        }
        return totalPage;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }
}
