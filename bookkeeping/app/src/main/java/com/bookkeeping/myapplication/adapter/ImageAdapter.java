package com.bookkeeping.myapplication.adapter;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.model.bilibili.PicturesModel;
import com.bookkeeping.myapplication.util.GlideUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/7/16  16:41
 * @desc :
 */
public class ImageAdapter extends BaseQuickAdapter<PicturesModel, BaseViewHolder> {

    private List<PicturesModel> data;

    public ImageAdapter(@Nullable List<PicturesModel> data) {
        super(R.layout.item_image, data);
        this.data=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, PicturesModel item) {
        ImageView imageView = (ImageView)helper.getView(R.id.iv_image);
        Glide.with(mContext).load(item.getImg_src()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Matrix m = new Matrix();
                final int width =resource.getWidth();
                final int height =resource.getHeight();
                if (data.size()==1){
                    int dstWidth=400;
                    int dstHeight=400;
                    final float sx = dstWidth / (float) width;
                    final float sy = dstHeight / (float) height;
                    if (height<2*width) {
                        if (width < height) {
                            m.setScale(sx, sx);
                        } else {
                            m.setScale(sy, sy);
                        }
                        imageView.setImageBitmap(Bitmap.createBitmap(resource, 0, 0, width, height, m, true));
                    }else {
                        m.setScale(sx, sx);
                        imageView.setImageBitmap(Bitmap.createBitmap(resource, 0, 0, width, width*4/3, m, true));
                    }
                }
                else {
                    int dstWidth=260;
                    int dstHeight=260;
                    final float sx = dstWidth / (float) width;
                    final float sy = dstHeight / (float) height;
                    int x=0;
                    int y=0;
                    if (height<2*width) {
                        if (width < height) {
                            m.setScale(sx, sx);
                            y=(height-width)/2;
                        } else {
                            m.setScale(sy, sy);
                            x=(width-height)/2;
                        }
                        imageView.setImageBitmap(Bitmap.createBitmap(resource, x, y, width-2*x, height-2*y-10, m, true));
                    }else {
                        m.setScale(sx, sx);
                        imageView.setImageBitmap(Bitmap.createBitmap(resource, 0, 0, width, width*4/3, m, true));
                    }
                }
            }
        });
    }
}
