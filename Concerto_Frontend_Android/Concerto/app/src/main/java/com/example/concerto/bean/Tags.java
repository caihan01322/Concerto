/**
  * Copyright 2021 bejson.com 
  */
package com.example.concerto.bean;

public class Tags {

    private int tagId;
    private String tagContent;
    private String tagColor;
    public void setTagId(int tagId) {
         this.tagId = tagId;
     }
    public int getTagId() {
         return tagId;
     }

    public void setTagContent(String tagContent) {
         this.tagContent = tagContent;
    }
    public String getTagContent() {
         return tagContent;
     }

    public void setTagColor(String tagColor) {
         this.tagColor = tagColor;
     }
    public String getTagColor() {
         return tagColor;
     }

}