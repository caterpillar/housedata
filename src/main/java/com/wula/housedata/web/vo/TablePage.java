package com.wula.housedata.web.vo;

import java.util.List;

/**
 * Created by lishaohua on 17-3-10.
 */
public class TablePage<T> {
    private Long total;
    private List<T> rows;

    public TablePage(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public TablePage() {
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
