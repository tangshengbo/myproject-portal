package com.tangshengbo.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TangShengBo on 2018/3/6.
 */
public class CanvasImage {

    private String img;
    private int x;
    private int y;
    private int z;
    private int nx;
    private int nz;


    public CanvasImage(int x, int y, int z, int nx, int nz) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.nx = nx;
        this.nz = nz;
    }

    public CanvasImage() {
    }

    public CanvasImage(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getNx() {
        return nx;
    }

    public void setNx(int nx) {
        this.nx = nx;
    }

    public int getNz() {
        return nz;
    }

    public void setNz(int nz) {
        this.nz = nz;
    }

    public static List<CanvasImage> canvasImages() {
        List<CanvasImage> imageList = new ArrayList<>();
        // north
        imageList.add(new CanvasImage(-1000, 0, 1500, 0, 1));
        imageList.add(new CanvasImage(0, 0, 1500, 0, 1));
        imageList.add(new CanvasImage(1000, 0, 1500, 0, 1));
        // east
        imageList.add(new CanvasImage(1500, 0, 1000, -1, 0));
        imageList.add(new CanvasImage(1500, 0, 0, -1, 0));
        imageList.add(new CanvasImage(1500, 0, -1000, -1, 0));
        // south
        imageList.add(new CanvasImage(1000, 0, -1500, 0, -1));
        imageList.add(new CanvasImage(0, 0, -1500, 0, -1));
        imageList.add(new CanvasImage(-1000, 0, -1500, 0, -1));
        // west
        imageList.add(new CanvasImage(-1500, 0, -1000, 1, 0));
        imageList.add(new CanvasImage(-1500, 0, 0, 1, 0));
        imageList.add(new CanvasImage(-1500, 0, 1000, 1, 0));
        return imageList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
