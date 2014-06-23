package com.zerofull800.basedataproccess.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

import com.zerofull800.basedataproccess.lib.R;

/**
 * Created by Novalogiq on 6/23/2014.
 */
public class RobotoAutoCompleteEditText extends AutoCompleteTextView implements IRobotoView {
    public RobotoAutoCompleteEditText(Context context) {
        super(context);
    }

    public RobotoAutoCompleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RobotoAutoCompleteEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    /**
     * This method initialize customizable settings for the view.
     *
     * @param context Application Context
     * @param attrs   View AttributeSet
     */
    @Override
    public void init(Context context, AttributeSet attrs) {
        //Typeface.createFromAsset doesn't work in the layout editor. Skipping...
        if (isInEditMode()) {
            return;
        }

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.roboto_font);
        String fontName = styledAttrs.getString(R.styleable.roboto_font_typeface);
        styledAttrs.recycle();

        if (fontName != null) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            setTypeface(typeface);
        }
    }
}
