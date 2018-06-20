package com.example.xkfeng.nettest.JavaBean;

import java.io.Serializable;

/**
 * Created by initializing on 2018/6/11.
 */

public class SimpleClass implements Serializable{

    private String id ;

    private String name ;

    private String version ;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }
}
