package com.bookkeeping.myapplication.util;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.bookkeeping.myapplication.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.lang.reflect.Field;

import static com.bookkeeping.myapplication.util.CommonUtils.zoom;

/**
 * @author : qiuyiyang
 * @date : 2021/6/11  13:54
 * @desc :
 */
public class UrlImageSpan extends ImageSpan {

    private String url;
    private TextView tv;

    public UrlImageSpan(Context context, String url, TextView tv) {
        super(context,R.drawable.bilibili_smile);
        this.url = url;
        this.tv = tv;
    }

    @Override
    public Drawable getDrawable() {
        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .build());
            Glide.with(tv.getContext()).load(glideUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    Resources resources = tv.getContext().getResources();
                    int targetWidth = (int) (resources.getDisplayMetrics().heightPixels);
                    Bitmap zoom = zoom(resource, targetWidth);
                    BitmapDrawable b = new BitmapDrawable(resources, zoom);

                    b.setBounds(0, 0, b.getIntrinsicWidth(), b.getIntrinsicHeight());
                    Field mDrawable;
                    Field mDrawableRef;
                    try {
                        mDrawable = ImageSpan.class.getDeclaredField("mDrawable");
                        mDrawable.setAccessible(true);
                        mDrawable.set(UrlImageSpan.this, b);
                        mDrawableRef = DynamicDrawableSpan.class.getDeclaredField("mDrawableRef");
                        mDrawableRef.setAccessible(true);
                        mDrawableRef.set(UrlImageSpan.this, null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
            });
        return super.getDrawable();
    }

}