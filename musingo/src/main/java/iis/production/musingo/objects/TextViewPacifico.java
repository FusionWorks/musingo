package iis.production.musingo.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by AGalkin on 1/19/14.
 */
public class TextViewPacifico extends TextView{

    public TextViewPacifico(Context context) {
        super(context);
        if(!isInEditMode()){
            Typeface face= Typeface.createFromAsset(context.getAssets(), "Pacifico.ttf");
            this.setTypeface(face);
        }
    }

    public TextViewPacifico(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()){
            Typeface face=Typeface.createFromAsset(context.getAssets(), "Pacifico.ttf");
            this.setTypeface(face);
        }
    }

    public TextViewPacifico(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(!isInEditMode()){
            Typeface face=Typeface.createFromAsset(context.getAssets(), "Pacifico.ttf");
            this.setTypeface(face);
        }
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }
}
