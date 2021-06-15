package com.example.concerto.bean;

import java.util.List;

public class Condition {
    public String titile;
    public List<String> tags;
    public List<String> names;
    public Condition(String title,List<String> tags, List<String> names){
        this.titile=title;
        this.tags=tags;
        this.names=names;
    }
}
