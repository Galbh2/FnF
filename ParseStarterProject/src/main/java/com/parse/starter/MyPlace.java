package com.parse.starter;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * This class is the data object for holding one place.
 */

public class MyPlace {

    private String id;
    private String name;
    private String address;
    private String photoRef;
    private double grade;
    private boolean openJobs;
    private Bitmap m_Image = null;
    private int[] m_Results;

    /**
     *
     * @param id the id of the place (will be generated by the server)
     * @param  name the name of the place
     * @param address the address of the place (in the future gps support;
     *
     */



    public MyPlace (String id, String name, String address, String photoRef) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.photoRef = photoRef;
        openJobs = false;
    }

    public MyPlace (String id, String name, String address, double grade, boolean openJobs) {
        this(id, name, address, null);
        this.openJobs = openJobs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public double getGrade() {

        return m_Results != null ? m_Results[6] : 0;
    }



    public boolean isOpenJobs() {
        return openJobs;
    }

    public void setOpenJobs(boolean openJobs) {
        this.openJobs = openJobs;
    }

    public Bitmap getImage() {
        return m_Image;
    }

    public void setImage(Bitmap m_Image) {
        this.m_Image = m_Image;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }

    @Override
    public String toString() {
        return "MyPlace{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", photoRef='" + photoRef + '\'' +
                '}' + '\n';
    }

    public int[] getResults() {
        return m_Results;
    }

    public void setResults(int[] m_Result) {
        this.m_Results = m_Result;
    }
}