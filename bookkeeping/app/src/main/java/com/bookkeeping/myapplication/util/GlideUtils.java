package com.bookkeeping.myapplication.util;

import android.content.Context;
import android.widget.ImageView;

import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.view.GlideCircleTransform;
import com.bookkeeping.myapplication.view.GlideCircleTransforms;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/3/29
 */
public class GlideUtils {
    private GlideUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 正常加载图片
     *
     * @param mContext
     * @param url
     * @param imageView
     */
    public static void loadImageCenterCrop(Context mContext, String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .error(R.drawable.ic_launch)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 正常加载图片
     *
     * @param mContext
     * @param url
     * @param imageView
     */
    public static void loadImage(Context mContext, String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .error(R.drawable.ic_launch)
                .into(imageView);

        Glide.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launch)
                .error(R.drawable.ic_launch)
                .into(imageView);
    }

    /**
     * 加载商品图片
     *
     * @param url
     * @param imageView
     */
    public static void loadShopImage(ImageView imageView, String url) {
        if(!url.equals(imageView.getTag())){
            imageView.setTag(null);
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(imageView.getContext())
                    .load(url)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_launch)
                    .centerCrop()
                    .error(R.drawable.ic_launch)
                    .into(imageView);
            imageView.setTag(url);
        }
    }

    /**
     * 加载头像
     *
     * @param mContext
     * @param url
     * @param avatar
     */
    public static void loadAvatar(Context mContext, String url, ImageView avatar) {
        if (mContext != null) {
            Glide.with(mContext)
                    .load(StringUtil.isEmpty(url) ? R.drawable.avatar: url)
                    .error(R.drawable.avatar)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(new GlideCircleTransform(mContext), new CenterCrop(mContext))
                    .into(avatar);
        }
    }


    public static void loadThumnailLocalImage(Context mContext, Integer drawable, ImageView img) {
        if (mContext != null) {
            Glide.with(mContext)
                    .load(drawable)
                    .thumbnail(0.5f)
                    .into(img);
        }
    }

    public static void loadTLocalAvatar(Context mContext, Integer drawable, ImageView img) {
        if (mContext != null) {
            Glide.with(mContext)
                    .load(drawable)
                    .thumbnail(0.5f)
                    .transform(new GlideCircleTransform(mContext), new CenterCrop(mContext))
                    .into(img);
        }
    }

    /**
     * 加载无缓存图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadNoChacheImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.ic_launch)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    /**
     * 加载缩略图图片
     *
     * @param mContext
     * @param url
     * @param imageView
     */
    public static void loadThumbnailImageCenterCrop(Context mContext, String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .thumbnail(0.5f)
                .error(R.drawable.ic_launch)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载缩略图图片
     *
     * @param mContext
     * @param url
     * @param imageView
     */
    public static void loadThumbnailImage(Context mContext, String url, ImageView imageView) {
        Glide.with(mContext)
                .load(StringUtil.isEmpty(url) ? R.drawable.ic_launch : url)
                .thumbnail(0.5f)
                .error(R.drawable.ic_launch)
                .into(imageView);
    }

    /**
     * 加载缩略图图片
     *
     * @param mContext
     * @param url
     * @param imageView
     */
    public static void loadThumbnailCircleImage(Context mContext, String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .thumbnail(0.5f)
                .error(R.drawable.ic_launch)
                .transform(new GlideCircleTransform(mContext), new CenterCrop(mContext))
                .into(imageView);
    }


    /**
     * 加载圆形图片
     *
     * @param mContext
     * @param url
     * @param imageView
     */
    public static void loadCircleImage(Context mContext, String url, ImageView imageView) {
        Glide.with(mContext).load(url)
                .placeholder(R.drawable.ic_launch)
                .error(R.drawable.ic_launch)
                .transform(new CenterCrop(mContext), new GlideCircleTransform(mContext))
                .into(imageView);
    }

    /**
     * 加载原型带边框图片
     *
     * @param mContext
     * @param url
     * @param borderWidth
     * @param borderColor
     * @param imageView
     */
    public static void loadCircleBorderImage(Context mContext, String url, int borderWidth, int borderColor, ImageView imageView) {
        Glide.with(mContext).load(url)
                .placeholder(R.drawable.ic_launch)
                .error(R.drawable.ic_launch)
                .transform(new CenterCrop(mContext), new GlideCircleTransforms(mContext, borderWidth, borderColor))
                .into(imageView);
    }
}
