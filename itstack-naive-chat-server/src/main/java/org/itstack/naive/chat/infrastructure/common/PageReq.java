package org.itstack.naive.chat.infrastructure.common;

import org.springframework.util.StringUtils;

/**
 * 该请求是分页请求
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/28 16:02
 */
public class PageReq {
    private int pageStart; //页面对应的偏移量offset
    private int pageSize; // 每页的大小
    private int currentPage; //所处的当前页数

    public PageReq() {
    }
    /**
     * @param page 当前页数
     * @param pageSize  每一页的大小
     * @author hourui
     * @date 2023/1/28 21:47
     * @return
     */
    public void setPage(String page, String pageSize){
        //默认一页包含的记录为10，总的页数是1页
        this.currentPage = StringUtils.hasText(page) ? Integer.parseInt(page) : 1;
        this.pageSize = StringUtils.hasText(pageSize) ? Integer.parseInt(pageSize) : 10;
        //如果输入的pageTotal为0
        if (0 == this.currentPage) {
            this.currentPage = 1;
        }
        this.pageStart = (this.currentPage - 1) * this.pageSize;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }
}
