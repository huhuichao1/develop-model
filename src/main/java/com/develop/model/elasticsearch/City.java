package com.develop.model.elasticsearch;

/**
 * Created by yujun on 2017/2/27.
 */
public class City {
    private Long id;
    private Double lon;// 经度
    private Double lat;// 纬度
    private String city;
    private String title;

    public City(Long id,String city, Double lon, Double lat, String title) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        this.city = city;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
    public Double getLon() {
        return lon;
    }
    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }




}
