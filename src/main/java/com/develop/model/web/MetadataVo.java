package com.develop.model.web;



/**
 * 元数据
 *
 * @author linligang
 * @since 2016/2/15 12:13
 */
public class MetadataVo {
    private long totalCount;
    private long offset;
    private int limit;

    public MetadataVo(PageBean pageBean) {
        this.totalCount = pageBean.getTotalCount();
        this.offset = pageBean.getOffset();
        this.limit = pageBean.getLimit();
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
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
}
