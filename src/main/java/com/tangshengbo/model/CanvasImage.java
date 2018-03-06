package com.tangshengbo.model;

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

    public CanvasImage(String img, int x, int y, int z, int nx, int nz) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.z = z;
        this.nx = nx;
        this.nz = nz;
    }

    public CanvasImage(int x, int y, int z, int nx, int nz) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.nx = nx;
        this.nz = nz;
    }

    public CanvasImage() {
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

    public List<CanvasImage> canvasImages() {
        List<CanvasImage> imageList = new ArrayList<>();
        // north
        /*{img:'${ctx}/img/3d/1.jpg', x:-1000, y:0, z:1500, nx:0, nz:1},
        {img:'${ctx}/img/3d/2.jpg', x:0,     y:0, z:1500, nx:0, nz:1},
        {img:'${ctx}/img/3d/3.jpg', x:1000,  y:0, z:1500, nx:0, nz:1},
        // east
        {img:'${ctx}/img/3d/4.jpg', x:1500,  y:0, z:1000, nx:-1, nz:0},
        {img:'${ctx}/img/3d/5.jpg', x:1500,  y:0, z:0, nx:-1, nz:0},
        {img:'${ctx}/img/3d/6.jpg', x:1500,  y:0, z:-1000, nx:-1, nz:0},
        // south
        {img:'${ctx}/img/3d/7.jpg', x:1000,  y:0, z:-1500, nx:0, nz:-1},
        {img:'${ctx}/img/3d/8.jpg', x:0,     y:0, z:-1500, nx:0, nz:-1},
        {img:'${ctx}/img/3d/9.jpg', x:-1000, y:0, z:-1500, nx:0, nz:-1},
        // west
        {img:'${ctx}/img/3d/10.jpg', x:-1500, y:0, z:-1000, nx:1, nz:0},
        {img:'${ctx}/img/3d/11.jpg', x:-1500, y:0, z:0, nx:1, nz:0},
        {img:'${ctx}/img/3d/12.jpg', x:-1500, y:0, z:1000, nx:1, nz:0}*/
        imageList.add(new CanvasImage(-1000, 0, 1500, 0, 1));
        imageList.add(new CanvasImage(-0, 0, 1500, 0, 1));
        imageList.add(new CanvasImage(-1000, 0, 1500, 0, 1));
        imageList.add(new CanvasImage(-1500, 0, 1000, -1, 0));
        return imageList;
    }
}
