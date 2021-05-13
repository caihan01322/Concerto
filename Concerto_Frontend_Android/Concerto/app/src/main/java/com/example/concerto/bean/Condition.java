package com.example.concerto.bean;

import java.util.List;

public class Condition {
    public List<String> tags;
    public List<String> names;
    public Condition(List<String> tags, List<String> names){
        this.tags=tags;
        this.names=names;
    }
}
