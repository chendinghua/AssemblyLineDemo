package com.ycexample.ycapp.entry;

/**
 * Created by 16486 on 2020/9/4.
 */

public class ScanCode {

    private Integer id ;

    private String code;

    public ScanCode(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    public ScanCode() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
