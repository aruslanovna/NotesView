/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.Serializable
 *  java.lang.Object
 *  java.lang.String
 */
package net.lab3.myapplication;

import java.io.Serializable;

public class ItemsModel
implements Serializable {
    private String date;
    private String description;
    private int id;
    private byte[] imageByteArray;
    private int priority;
    private String title;

    public ItemsModel(int n, String string2, String string3, int n2, byte[] arrby, String string4) {
        this.id = n;
        this.title = string2;
        this.date = string4;
        this.description = string3;
        this.priority = n2;
        this.imageByteArray = arrby;
    }

    public String getDate() {
        return this.date;
    }

    public String getDescription() {
        return this.description;
    }

    public int getId() {
        return this.id;
    }

    public byte[] getImageByteArray() {
        return this.imageByteArray;
    }

    public int getPriority() {
        return this.priority;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDate(String string2) {
        this.date = string2;
    }

    public void setDescription(String string2) {
        this.description = string2;
    }

    public void setImageByteArray(byte[] arrby) {
        this.imageByteArray = arrby;
    }

    public void setPriority(int n) {
        this.priority = n;
    }

    public void setTitle(String string2) {
        this.title = string2;
    }
}

