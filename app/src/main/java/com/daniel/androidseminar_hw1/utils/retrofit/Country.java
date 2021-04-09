package com.daniel.androidseminar_hw1.utils.retrofit;

import java.io.Serializable;
import java.util.Arrays;

public class Country implements Serializable {

    private String name;
    private String nativeName;
    private String alphaCode;
    private String[] borders;

    public Country() {
    }

    public Country(String name, String nativeName, String alphaCode, String[] borders) {
        this.name = name;
        this.nativeName = nativeName;
        this.alphaCode = alphaCode;
        this.borders = borders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(String alphaCode) {
        this.alphaCode = alphaCode;
    }

    public String[] getBorders() {
        return borders;
    }

    public void setBorders(String[] borders) {
        this.borders = borders;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", nativeName='" + nativeName + '\'' +
                ", alphaCode='" + alphaCode + '\'' +
                ", borders=" + Arrays.toString(borders) +
                '}';
    }
}
