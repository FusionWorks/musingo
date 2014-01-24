package iis.production.musingo.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by AGalkin on 1/19/14.
 */
public class TextViewArchitects extends TextView {


    public TextViewArchitects(Context context) {
        super(context);

        if(!isInEditMode()){
            Typeface face= Typeface.createFromAsset(context.getAssets(), "ArchitectsDaughter.ttf");
            this.setTypeface(face);
        }
    }

    public TextViewArchitects(Context context, AttributeSet attrs) {
        super(context, attrs);

        if(!isInEditMode()){
            Typeface face=Typeface.createFromAsset(context.getAssets(), "ArchitectsDaughter.ttf");
            this.setTypeface(face);
        }
    }

    public TextViewArchitects(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if(!isInEditMode()){
            Typeface face=Typeface.createFromAsset(context.getAssets(), "ArchitectsDaughter.ttf");
            this.setTypeface(face);
        }
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }
}
