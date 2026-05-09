package com.group6.textbooksalesapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "textbooks")
public class Textbook {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;

    public String author;

    public String isbn;
    public String sellerName;
    public double price;
    public int copies;
    public String bankInfo;
}