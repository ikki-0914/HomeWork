package jp.ac.gifu_u.info.kondo.test1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MyView extends View {
    private ArrayList array_x, array_y;
    private ArrayList array_status;

    //コンストラクタ
    public MyView(Context context){
        super(context);

        array_x = new ArrayList();
        array_y = new ArrayList();
        array_status = new ArrayList();
    }

    //ビューの描画を行うときに呼ばれるメソッド
    @Override
    protected void onDraw(@NonNull Canvas canvas){
        super.onDraw(canvas);

        //背景を白く塗りつぶす
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.WHITE);
        canvas.drawRect(new Rect(0,0,canvas.getWidth(),canvas.getHeight()),p);

        //描画用のPaintオブジェクトを用意
        p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.RED);

        //配列内の座標を読みだして線（軌道）を描画
        for (int i=1; i< array_status.size(); i++){
            if((Boolean) array_status.get(i)){
                int x1 = (Integer) array_x.get(i-1);
                int x2 = (Integer) array_x.get(i);
                int y1 = (Integer) array_y.get(i-1);
                int y2 = (Integer) array_y.get(i);
                //線を描画
                canvas.drawLine(x1,y1,x2,y2,p);
            }
        }


        //どのように描画するのかを指定する Paintオブジェクト
        @SuppressLint("DrawAllocation") Paint paint = new Paint();
        paint.setColor(Color.BLUE); //描画色を青色に設定

        // (10, 20) から (30, 40)へ青線を描画
        canvas.drawLine(10, 20, 30, 40, paint);

    }

    //タッチパネルを操作したときに呼ばれるメソッド
    @Override
    public boolean onTouchEvent(MotionEvent event){
        //座標取得
        int x = (int) event.getX();
        int y = (int) event.getY();
        //イベントに応じて動作を変更
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: //タッチパネルが押されたケース
            case MotionEvent.ACTION_POINTER_DOWN:
                array_x.add(new Integer(x)); //座標を配列に保存
                array_y.add(new Integer(y)); //線の描画はしない(false)
                array_status.add(new Boolean(false));
                invalidate(); //画面を強制的に再描画
                break;
            case MotionEvent.ACTION_MOVE:
                array_x.add(new Integer(x)); //座標を配列に保存
                array_y.add(new Integer(y)); //線の描画をする(true)
                array_status.add(new Boolean(true));
                invalidate();//画面を強制的に再描画
                break;
            case MotionEvent.ACTION_UP: //タッチパネルから離れた時
            case MotionEvent.ACTION_POINTER_UP:
                array_x.add(new Integer(x)); //座標に配列を保存
                array_y.add(new Integer(y)); //線の描画をする(true)
                array_status.add(new Boolean(true));
                invalidate();//画面を強制的に再描画
                break;


        }
        return true;
    }
}
