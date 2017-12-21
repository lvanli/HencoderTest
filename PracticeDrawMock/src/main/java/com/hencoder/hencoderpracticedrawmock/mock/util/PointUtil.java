package com.hencoder.hencoderpracticedrawmock.mock.util;

/**
 * Created by lizhiguang on 2017/12/11.
 */

public class PointUtil {

    public static ImagePosition getImagePosition(int viewWidth, int viewHeight, int imageWidth, int imageHeight) {
        ImagePosition position = new ImagePosition();
        position.viewHeight = viewHeight;
        position.viewWidth = viewWidth;
        position.imageHeight = imageHeight;
        position.imageWidth = imageWidth;
        if ((position.viewWidth * 1.0 / position.viewHeight) > (position.imageWidth * 1.0 / position.imageHeight)) {
            //图片竖着，左右有白边
            position.paddingTop = 0;
            if (position.imageHeight > position.viewHeight)
                position.paddingLeft = (position.viewWidth - (int) (position.viewHeight * 1.0 / position.imageHeight * position.imageWidth)) / 2;
            else
                position.paddingLeft = (position.viewWidth - (int) (position.imageHeight * 1.0 / position.viewHeight * position.imageWidth)) / 2;
        } else {
            //图片横着，上下有白边
            if (position.imageWidth > position.viewWidth)
                position.paddingTop = (position.viewHeight - (int) (position.viewWidth * 1.0 / position.imageWidth * position.imageHeight)) / 2;
            else
                position.paddingTop = (position.viewHeight - (int) (position.imageWidth * 1.0 / position.viewWidth * position.imageHeight)) / 2;
            position.paddingLeft = 0;
        }
        return position;
    }

    /**
     * 获取界面上图片上的点(0,0为界面图片上的左上角)在真正图片上对应的位置
     *
     * @param p
     * @param position
     * @return
     */
    public static Point getRealPointByViewImagePoint(Point p, ImagePosition position) {
        Point nP = new Point();
        nP.x = (int) (p.x * 1.0 / (position.viewWidth - position.paddingLeft * 2) * position.imageWidth);
        nP.y = (int) (p.y * 1.0 / (position.viewHeight - position.paddingTop * 2) * position.imageHeight);
        return nP;
    }

    /**
     * 获取界面上的点在真正图片上对应的位置
     *
     * @param p
     * @param viewWidth
     * @param viewHeight
     * @param imageWidth
     * @param imageHeight
     * @return
     */
    public static Point getRealPointByViewPoint(Point p, int viewWidth, int viewHeight, int imageWidth, int imageHeight) {
        ImagePosition position = getImagePosition(viewWidth, viewHeight, imageWidth, imageHeight);
        return getRealPointByViewPoint(p, position);
    }

    public static Point getRealPointByViewPoint(Point p, ImagePosition position) {
        Point nP = new Point();
        nP.x = p.x - position.paddingLeft;
        nP.y = p.y - position.paddingTop;
        return getRealPointByViewImagePoint(nP, position);
    }

    /**
     * 通过真正图片的点获取界面上图片的对应位置（0,0为界面图片的左上角）
     *
     * @param p
     * @param position
     * @return
     */
    public static Point getImageViewPointByRealPoint(Point p, ImagePosition position) {
        Point nP = new Point();
        nP.x = (int) (p.x * 1.0 / position.imageWidth * (position.viewWidth - position.paddingLeft * 2));
        nP.y = (int) (p.y * 1.0 / position.imageHeight * (position.viewHeight - position.paddingTop * 2));
        return nP;
    }

    /**
     * 通过真正图片的点获取界面上的相对位置(0,0为整个view的左上角)
     *
     * @param p
     * @param viewWidth
     * @param viewHeight
     * @param imageWidth
     * @param imageHeight
     * @return
     */
    public static Point getViewPointByRealPoint(Point p, int viewWidth, int viewHeight, int imageWidth, int imageHeight) {
        ImagePosition position = getImagePosition(viewWidth, viewHeight, imageWidth, imageHeight);
        return getViewPointByRealPoint(p, position);
    }

    public static Point getViewPointByRealPoint(Point p, ImagePosition position) {
        Point nP = getImageViewPointByRealPoint(p, position);
        nP.x = p.x + position.paddingLeft;
        nP.y = p.y + position.paddingTop;
        return nP;
    }

    public static class Point {
        public int x;
        public int y;

        public Point() {
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
