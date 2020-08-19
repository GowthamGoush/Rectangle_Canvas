package com.example.rectangle_canvas;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {

    Point point1, point3;
    Point point2, point4;

    int groupId = -1;
    private ArrayList < ColorBall > colorballs = new ArrayList <> ();

    private int balID = 0;

    Paint paint;
    Canvas canvas;

    public DrawView(Context context) {
        super(context);
        paint = new Paint();
        setFocusable(true);
        canvas = new Canvas();

        point1 = new Point();
        point1.x = 50;
        point1.y = 20;

        point2 = new Point();
        point2.x = 150;
        point2.y = 20;

        point3 = new Point();
        point3.x = 150;
        point3.y = 120;

        point4 = new Point();
        point4.x = 50;
        point4.y = 120;

        colorballs.add(new ColorBall(context, R.drawable.ic_corner2, point1));
        colorballs.add(new ColorBall(context, R.drawable.ic_corner1, point2));
        colorballs.add(new ColorBall(context, R.drawable.ic_corner1, point3));
        colorballs.add(new ColorBall(context, R.drawable.ic_corner1, point4));

    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        setFocusable(true);
        canvas = new Canvas();

        point1 = new Point();
        point1.x = 50;
        point1.y = 20;

        point2 = new Point();
        point2.x = 250;
        point2.y = 20;

        point3 = new Point();
        point3.x = 250;
        point3.y = 220;

        point4 = new Point();
        point4.x = 50;
        point4.y = 220;

        colorballs.add(new ColorBall(context, R.drawable.ic_corner2, point1));
        colorballs.add(new ColorBall(context, R.drawable.ic_corner1, point2));
        colorballs.add(new ColorBall(context, R.drawable.ic_corner1, point3));
        colorballs.add(new ColorBall(context, R.drawable.ic_corner1, point4));

    }

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#55000000"));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(5);

        canvas.drawPaint(paint);
        paint.setColor(Color.RED);

        if (groupId == 1) {
            canvas.drawRect(point1.x + colorballs.get(0).getWidthOfBall() / 2,
                    point3.y + colorballs.get(2).getWidthOfBall() / 2, point3.x + colorballs.get(2).getWidthOfBall() / 2, point1.y + colorballs.get(0).getWidthOfBall() / 2, paint);
        }
        else {
            canvas.drawRect(point2.x + colorballs.get(1).getWidthOfBall() / 2,
                    point4.y + colorballs.get(3).getWidthOfBall() / 2, point4.x + colorballs.get(3).getWidthOfBall() / 2, point2.y + colorballs.get(1).getWidthOfBall() / 2, paint);
        }
        BitmapDrawable mBitmap;
        mBitmap = new BitmapDrawable();

        for (ColorBall ball: colorballs) {
            canvas.drawBitmap(ball.getBitmap(), ball.getX(), ball.getY(), new Paint());
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                balID = -1;
                groupId = -1;
                for (ColorBall ball: colorballs) {
                    int centerX = ball.getX() + ball.getWidthOfBall();
                    int centerY = ball.getY() + ball.getHeightOfBall();

                    double radCircle = Math.sqrt(((centerX - X) * (centerX - X)) + (centerY - Y) * (centerY - Y));

                    if (radCircle < ball.getWidthOfBall()) {

                        balID = ball.getID();
                        if (balID == 1 || balID == 3) {
                            groupId = 2;
                            canvas.drawRect(point1.x, point3.y, point3.x, point1.y, paint);

                        }
                        else {
                            groupId = 1;
                            canvas.drawRect(point2.x, point4.y, point4.x, point2.y, paint);

                        }
                        invalidate();
                        break;
                    }
                    invalidate();
                }

                break;

            case MotionEvent.ACTION_MOVE: {
                if (balID > -1) {
                    if (balID == 0) {
                        int offsetX = X - colorballs.get(0).getX();
                        int offsetY = Y - colorballs.get(0).getY();

                        colorballs.get(0).setX(X);
                        colorballs.get(0).setY(Y);

                        colorballs.get(1).setX(colorballs.get(1).getX() + offsetX);
                        colorballs.get(1).setY(colorballs.get(1).getY() + offsetY);

                        colorballs.get(2).setX(colorballs.get(2).getX() + offsetX);
                        colorballs.get(2).setY(colorballs.get(2).getY() + offsetY);

                        colorballs.get(3).setX(colorballs.get(3).getX() + offsetX);
                        colorballs.get(3).setY(colorballs.get(3).getY() + offsetY);

                        canvas.drawRect(X, Y, colorballs.get(2).getX(), colorballs.get(2).getY(), paint);

                        invalidate();
                    }
                    else {
                        colorballs.get(balID).setX(X);
                        colorballs.get(balID).setY(Y);

                        if (groupId == 1) {
                            colorballs.get(1).setX(colorballs.get(0).getX());
                            colorballs.get(1).setY(colorballs.get(2).getY());
                            colorballs.get(3).setX(colorballs.get(2).getX());
                            colorballs.get(3).setY(colorballs.get(0).getY());
                            canvas.drawRect(point1.x, point3.y, point3.x, point1.y, paint);

                        } else {
                            colorballs.get(2).setX(colorballs.get(3).getX());
                            colorballs.get(2).setY(colorballs.get(1).getY());
                            colorballs.get(0).setX(colorballs.get(1).getX());
                            colorballs.get(0).setY(colorballs.get(3).getY());
                            canvas.drawRect(point2.x, point4.y, point4.x, point2.y, paint);
                        }

                        invalidate();
                    }
                }
            }
            break;

        }
        invalidate();
        return true;

    }


}