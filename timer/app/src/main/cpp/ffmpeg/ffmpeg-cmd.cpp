//
// Created by xulin on 2018/6/28 0028.
//

#include <jni.h>
#include <string>
#include "android/log.h"
#include <sstream>
#include <unistd.h>

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, "ffmpeg-cmd", __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, "ffmpeg-cmd", __VA_ARGS__)

extern "C"{
#include "ffmpeg.h"
#include "libavcodec/jni.h"
#include <libswscale/swscale.h>
#include <libavformat/avformat.h>
#include <libavutil/avutil.h>
#include <android/native_window_jni.h>
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_timer_com_jni_FFmpegCmd_run(JNIEnv *env, jclass type, jint cmdLen,
                                             jobjectArray cmd) {
    //set java vm
    JavaVM *jvm = NULL;
    env->GetJavaVM(&jvm);
    av_jni_set_java_vm(jvm, NULL);

    char *argCmd[cmdLen] ;
    jstring buf[cmdLen];

    for (int i = 0; i < cmdLen; ++i) {
        buf[i] = static_cast<jstring>(env->GetObjectArrayElement(cmd, i));
        char *string = const_cast<char *>(env->GetStringUTFChars(buf[i], JNI_FALSE));
        argCmd[i] = string;
        LOGD("argCmd=%s",argCmd[i]);
    }
    int retCode = ffmpeg_exec(cmdLen, argCmd);
    LOGD("ffmpeg-invoke: retCode=%d",retCode);
    return retCode;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_timer_com_jni_FFmpegCmd_getProgress(JNIEnv *env, jclass clazz) {
    return get_progress();
}

extern "C"
JNIEXPORT jdouble JNICALL
Java_com_timer_com_jni_FFmpegCmd_getSpeed(JNIEnv *env, jclass clazz) {
    return get_speed();
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_timer_com_jni_FFmpegCmd_retrieveInfo(JNIEnv *env, jclass clazz, jstring _path) {
    const char* path=env->GetStringUTFChars(_path, JNI_FALSE);
    AVFormatContext* ctx = nullptr;

    av_register_all();
    avcodec_register_all();

    int ret = avformat_open_input(&ctx, path, nullptr, nullptr);
    avformat_find_stream_info(ctx, nullptr);
    env->ReleaseStringUTFChars(_path,path);
    if (ret != 0) {
        LOGE("avformat_open_input() open failed! path:%s, err:%s", path, av_err2str(ret));
        return nullptr;
    }
    int nStreams = ctx->nb_streams;

    AVStream **pStream = ctx->streams;
    AVStream *vStream = nullptr;

    for (int i = 0; i < nStreams; i++) {
        if (pStream[i]->codecpar->codec_type == AVMEDIA_TYPE_VIDEO) {
            vStream = pStream[i];
        }
    }

    int width = 0;
    int height = 0;
    int rotation = 0;
    long fps = 0;
    char *vCodecName = nullptr;
    if(vStream!=nullptr){
        width = vStream->codecpar->width;
        height = vStream->codecpar->height;
        rotation = static_cast<int>(get_rotation(vStream));
        int num = vStream->avg_frame_rate.num;
        int den = vStream->avg_frame_rate.den;
        if (den > 0) {
            fps = lround(num * 1.0 / den);
        }

        const char *codecName = avcodec_get_name(vStream->codecpar->codec_id);
        vCodecName = const_cast<char *>(codecName);
    }

    long bitrate = ctx->bit_rate;
    long duration = ctx->duration / 1000;//ms

    avformat_close_input(&ctx);
    std::ostringstream buffer;
    buffer << "{\"rotation\":"<<rotation<<",\"width\":"<<width<<",\"height\":"<<height<<",\"duration\":"<<duration<<",\"bitrate\":"<< bitrate<<",\"fps\":"<<fps<<R"(,"videoCodec":")"<<vCodecName<<"\"}";
    std::string result = buffer.str();
    return env->NewStringUTF(result.c_str());
}

#define LOGI(FORMAT,...) __android_log_print(ANDROID_LOG_INFO,"^_^",FORMAT,##__VA_ARGS__);
#define LOGE(FORMAT,...) __android_log_print(ANDROID_LOG_ERROR,">_<",FORMAT,##__VA_ARGS__);

extern "C"
JNIEXPORT int  JNICALL
Java_com_timer_com_jni_FFmpegCmd_playVideo(JNIEnv *env, jclass type, jstring input_jstr,jstring output_jstr) {
    const char *input_cstr = (*env).GetStringUTFChars(input_jstr, NULL);
    const char *output_cstr = (*env).GetStringUTFChars(output_jstr, NULL);

    //1.注册所有组件
    av_register_all();

    //封装格式上下文，统领全局的结构体，保存了视频文件封装格式的相关信息
    AVFormatContext *pFormatCtx = avformat_alloc_context();

    //2.打开输入视频文件
    if (avformat_open_input(&pFormatCtx, input_cstr, NULL, NULL) != 0){
        LOGE("%s","无法打开输入视频文件");
        return -1;
    }

    //3.获取视频文件信息
    if (avformat_find_stream_info(pFormatCtx,NULL) < 0){
        LOGE("%s","无法获取视频文件信息");
        return -1;
    }

    //获取视频流的索引位置
    //遍历所有类型的流（音频流、视频流、字幕流），找到视频流
    int v_stream_idx = -1;
    int i = 0;
    //number of streams
    for (; i < pFormatCtx->nb_streams; i++){
        //流的类型
        if (pFormatCtx->streams[i]->codec->codec_type == AVMEDIA_TYPE_VIDEO){
            v_stream_idx = i;
            break;
        }
    }

    if (v_stream_idx == -1){
        LOGE("%s","找不到视频流\n");
        return -1;
    }

    //只有知道视频的编码方式，才能够根据编码方式去找到解码器
    //获取视频流中的编解码上下文
    AVCodecContext *pCodecCtx = pFormatCtx->streams[v_stream_idx]->codec;
    //4.根据编解码上下文中的编码id查找对应的解码
    AVCodec *pCodec = avcodec_find_decoder(pCodecCtx->codec_id);
    //（迅雷看看，找不到解码器，临时下载一个解码器）
    if (pCodec == NULL){
        LOGE("%s","找不到解码器\n");
        return -1;
    }

    //5.打开解码器
    if (avcodec_open2(pCodecCtx,pCodec,NULL)<0){
        LOGE("%s","解码器无法打开\n");
        return -1;
    }

    //输出视频信息
    LOGI("视频的文件格式：%s",pFormatCtx->iformat->name);
    LOGI("视频时长：" PRId64 "\n", (pFormatCtx->duration)/1000000);
    LOGI("视频的宽高：%d,%d",pCodecCtx->width,pCodecCtx->height);
    LOGI("解码器的名称：%s",pCodec->name);

    //准备读取
    //AVPacket用于存储一帧一帧的压缩数据（H264）
    //缓冲区，开辟空间
    AVPacket *packet = (AVPacket*)av_malloc(sizeof(AVPacket));

    //AVFrame用于存储解码后的像素数据(YUV)
    //内存分配
    AVFrame *pFrame = av_frame_alloc();
    //YUV420
    AVFrame *pFrameYUV = av_frame_alloc();
    //只有指定了AVFrame的像素格式、画面大小才能真正分配内存
    //缓冲区分配内存
    uint8_t *out_buffer = (uint8_t *)av_malloc(
            (size_t) avpicture_get_size(AV_PIX_FMT_YUV420P,
                                        pCodecCtx->width, pCodecCtx->height));
    //初始化缓冲区
    avpicture_fill((AVPicture *)pFrameYUV,
                   out_buffer,
                   AV_PIX_FMT_YUV420P,
                   pCodecCtx->width,
                   pCodecCtx->height);

    //用于转码（缩放）的参数，转之前的宽高，转之后的宽高，格式等
    struct SwsContext *sws_ctx = sws_getContext(pCodecCtx->width,pCodecCtx->height,pCodecCtx->pix_fmt,pCodecCtx->width,
            pCodecCtx->height,AV_PIX_FMT_YUV420P,SWS_BICUBIC, NULL, NULL, NULL);

    int got_picture, ret;

    FILE *fp_yuv = fopen(output_cstr, "wb+");

    int frame_count = 0;

    //6.一帧一帧的读取压缩数据
    while (av_read_frame(pFormatCtx, packet) >= 0){
        //只要视频压缩数据（根据流的索引位置判断）
        if (packet->stream_index == v_stream_idx){
            //7.解码一帧视频压缩数据，得到视频像素数据
            ret = avcodec_decode_video2(pCodecCtx,pFrame,&got_picture,packet);
            if (ret < 0){
                LOGE("%s","解码错误");
                return -1;
            }

            //为0说明解码完成，非0正在解码
            if (got_picture){
                //AVFrame转为像素格式YUV420，宽高
                //2 6输入、输出数据
                //3 7输入、输出画面一行的数据的大小 AVFrame 转换是一行一行转换的
                //4 输入数据第一列要转码的位置 从0开始
                //5 输入画面的高度
                sws_scale(sws_ctx,(const uint8_t *const *) pFrame->data,pFrame->linesize, 0,
                          pCodecCtx->height,pFrameYUV->data,pFrameYUV->linesize);
                //输出到YUV文件
                //AVFrame像素帧写入文件
                //data解码后的图像像素数据（音频采样数据）
                //Y 亮度 UV 色度（压缩了） 人对亮度更加敏感
                //U V 个数是Y的1/4
                int y_size = pCodecCtx->width * pCodecCtx->height;
                fwrite(pFrameYUV->data[0], 1, y_size, fp_yuv);
                fwrite(pFrameYUV->data[1], 1, y_size / 4, fp_yuv);
                fwrite(pFrameYUV->data[2], 1, y_size / 4, fp_yuv);

                frame_count++;
                LOGI("解码第%d帧",frame_count);
            }
        }
        //释放资源
        av_free_packet(packet);
    }

    fclose(fp_yuv);

    (*env).ReleaseStringUTFChars(input_jstr,input_cstr);
    (*env).ReleaseStringUTFChars(output_jstr,output_cstr);

    av_frame_free(&pFrame);

    avcodec_close(pCodecCtx);

    avformat_free_context(pFormatCtx);

    return  frame_count;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_timer_com_jni_FFmpegCmd_getPic(JNIEnv *env, jclass type, jstring input_jstr,jlong positions,jstring output_jstr) {{
        AVFormatContext *pFormatCtx;
        AVOutputFormat *fmt;
        AVStream *video_st;
        AVCodecContext *pCodecCtx;
        AVCodec *pCodec;

        uint8_t *picture_buf;
        AVFrame *picture;
        AVPacket pkt;
        int y_size;
        int got_picture = 0;
        int size;
        int frame_num = positions;

        FILE *in_file = NULL;
        const char *out_file = reinterpret_cast<const char *>(output_jstr);

        in_file = fopen(reinterpret_cast<const char *>(input_jstr), "rb");

        const char* path=env->GetStringUTFChars(input_jstr, JNI_FALSE);
        AVFormatContext* ctx = nullptr;

        av_register_all();
        avcodec_register_all();

        int ret = avformat_open_input(&ctx, path, nullptr, nullptr);
        avformat_find_stream_info(ctx, nullptr);
        env->ReleaseStringUTFChars(input_jstr,path);
        if (ret != 0) {
            LOGE("avformat_open_input() open failed! path:%s, err:%s", path, av_err2str(ret));
            return ;
        }
        int nStreams = ctx->nb_streams;

        AVStream **pStream = ctx->streams;
        AVStream *vStream = nullptr;

        for (int i = 0; i < nStreams; i++) {
            if (pStream[i]->codecpar->codec_type == AVMEDIA_TYPE_VIDEO) {
                vStream = pStream[i];
            }
        }

        int width = 0;
        int height = 0;
        int den = 0;
        int num = 0;
        if(vStream!=nullptr){
            width = vStream->codecpar->width;
            height = vStream->codecpar->height;
            num = vStream->avg_frame_rate.num;
            den = vStream->avg_frame_rate.den;
        }
// 计算YUV420帧数量
        const int EachFrameSize = (int) (width * height * 1.5);
        if (in_file == NULL) {
            printf("couldn't open file.\n");
            return;
        }
// 将文件指针移到文件末尾
        fseek(in_file, 0, SEEK_END);
// 得到文件尾相对于文件首的位移，即文件的总字节数
// 该函数对大于2^31 -1文件，即2.1G以上的文件操作时可能出错
        long total_size = ftell(in_file);
// 重置文件指针指向文件头部
        rewind(in_file);

// size/(WIDTH*HEIGHT*1.5)可获得了yuv420文件的总帧数
        long nFrame = total_size / EachFrameSize;
        if (frame_num >nFrame) {
            printf("couldn't open file.\n");
            return;
        }
        fseek(in_file, (frame_num - 1) * EachFrameSize, SEEK_SET);


        av_register_all();
        pFormatCtx = avformat_alloc_context();

// 返回一个已经注册的最合适的输出格式
        fmt = av_guess_format("mjpeg", NULL, NULL);
        pFormatCtx->oformat = fmt;

        if (avio_open(&pFormatCtx->pb, out_file, AVIO_FLAG_READ_WRITE) < 0) {
            printf("Couldn't open output file.");
            return;
        }

// 上述更简单的方法：
//avformat_alloc_output_context2(&pFormatCtx, NULL, NULL, out_file);
//fmt = pFormatCtx->oformat;

        video_st = avformat_new_stream(pFormatCtx, 0);
        if (video_st == NULL) {
            return ;
        }
        pCodecCtx = video_st->codec;
        pCodecCtx->codec_id = fmt->video_codec;
        pCodecCtx->codec_type = AVMEDIA_TYPE_VIDEO;
        pCodecCtx->pix_fmt = AV_PIX_FMT_YUVJ420P;

        pCodecCtx->width = width;
        pCodecCtx->height = height;

        pCodecCtx->time_base.num = num;
        pCodecCtx->time_base.den = den;

// Output some information
        av_dump_format(pFormatCtx, 0, out_file, 1);

        pCodec = avcodec_find_encoder(pCodecCtx->codec_id);
        if (!pCodec) {
            printf("Codec not found.");
            return;
        }
        if (avcodec_open2(pCodecCtx, pCodec, NULL) < 0) {
            printf("Could not open codec.");
            return;
        }
        picture = av_frame_alloc();
        size = avpicture_get_size(pCodecCtx->pix_fmt, pCodecCtx->width, pCodecCtx->height);
        picture_buf = (uint8_t *) av_malloc(size);
        if (!picture_buf) {
            return;
        }

        avpicture_fill((AVPicture *) picture, picture_buf, pCodecCtx->pix_fmt, pCodecCtx->width,
                       pCodecCtx->height);

// 写头部
        avformat_write_header(pFormatCtx, NULL);

        y_size = pCodecCtx->width * pCodecCtx->height;
        av_new_packet(&pkt, y_size * 3);

// Read YUV
        if (fread(picture_buf, 1, y_size * 3 / 2, in_file) <= 0) {
            printf("Could not read input file.");
            return;
        }
        picture->data[0] = picture_buf;                        // Y
        picture->data[1] = picture_buf + y_size;                // U
        picture->data[2] = picture_buf + y_size * 5 / 4;        // V

// 编码
        ret = avcodec_encode_video2(pCodecCtx, &pkt, picture, &got_picture);
        if (ret < 0) {
            printf("Encode Error.\n");
            return;
        }
        if (got_picture == 1) {
            pkt.stream_index = video_st->index;
            ret = av_write_frame(pFormatCtx, &pkt);
        }

        av_free_packet(&pkt);

        av_write_trailer(pFormatCtx);

        printf("Encode Successful.\n");

        if (video_st) {
            avcodec_close(video_st->codec);
            av_free(picture);
            av_free(picture_buf);
        }
        avio_close(pFormatCtx->pb);
        avformat_free_context(pFormatCtx);
        fclose(in_file);
        return;
    }
}