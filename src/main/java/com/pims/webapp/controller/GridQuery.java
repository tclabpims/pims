package com.pims.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by 909436637@qq.com on 2016/10/1.
 * Description:common grid query parameter
 */
public class GridQuery {

    private HttpServletRequest request;

    private int page;

    private int row;

    private int start;

    private int end;

    private String query;

    private String sidx;

    private String sord;

    public GridQuery(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        this.request = request;
        String pages = request.getParameter("page");
        String rows = request.getParameter("rows");
        this.query = request.getParameter("query");
        this.sidx = request.getParameter("sidx");
        this.sord = request.getParameter("sord");
        this.page = Integer.parseInt(pages);
        this.row = Integer.parseInt(rows);
        this.start = row * (page - 1);
        this.end = row * page;
    }

    public GridQuery() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }
}
