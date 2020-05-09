package com.smart.smartcity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Publication implements Comparable<Publication> {
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("networkId")
    private int networkId;

    @Expose
    @SerializedName("authorId")
    private int authorId;

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("content")
    private String content;

    @Expose
    @SerializedName("authorName")
    private String authorName;

    public Publication(int networkId, int authorId, String date, String content) {
        this.networkId = networkId;
        this.authorId = authorId;
        this.date = date;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocalDateTime getLocalDate() {
        ZonedDateTime zdt = ZonedDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME).withZoneSameInstant(ZoneId.systemDefault());
        return zdt.toLocalDateTime();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getFormattedDate() {
        final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        return getLocalDate().format(formatter);
    }

    @Override
    public int compareTo(Publication o) {
        return getLocalDate().compareTo(o.getLocalDate());
    }
}
