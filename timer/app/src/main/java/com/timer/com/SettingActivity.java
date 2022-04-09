package com.timer.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.timer.com.bean.HintModel;
import com.timer.com.util.StorageCustomerInfoUtil;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : qiuyiyang
 * @date : 2022/1/24  16:58
 * @desc :
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ll_local)
    LinearLayout llLocal;
    @BindView(R.id.tv_fps_set)
    TextView tvFpsSet;
    @BindView(R.id.ll_fps_set)
    LinearLayout llFpsSet;
    @BindView(R.id.tv_fps_num)
    TextView tvFpsNum;
    @BindView(R.id.ll_fps_num)
    LinearLayout llFpsNum;
    @BindView(R.id.tv_shutter)
    TextView tvShutter;
    @BindView(R.id.ll_shutter)
    LinearLayout llShutter;
    @BindView(R.id.switch_auto_start)
    Switch switchAutoStart;
    @BindView(R.id.ll_auto_start)
    LinearLayout llAutoStart;
    @BindView(R.id.tv_delay_time)
    TextView tvDelayTime;
    @BindView(R.id.ll_delay_time)
    LinearLayout llDelayTime;
    @BindView(R.id.tv_shooting_delay)
    TextView tvShootingDelay;
    @BindView(R.id.ll_shooting_delay)
    LinearLayout llShootingDelay;
    @BindView(R.id.switch_red_line)
    Switch switchRedLine;
    @BindView(R.id.ll_red_line)
    LinearLayout llRedLine;
    @BindView(R.id.ll_about_us)
    LinearLayout llAboutUs;
    @BindView(R.id.tv_cut)
    TextView tvCut;
    @BindView(R.id.ll_cut)
    LinearLayout llCut;
    @BindView(R.id.switch_name)
    Switch switchName;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.switch_daley)
    Switch switchDaley;
    @BindView(R.id.ll_daley)
    LinearLayout llDaley;
    @BindView(R.id.switch_yun)
    Switch switchYun;
    @BindView(R.id.ll_yun)
    LinearLayout llYun;

    private HintModel hintModel;
    private InputDialog inputDialog;

    @Override
    public int initLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initData() {
        tvTitle.setText("设置");
        switch (StorageCustomerInfoUtil.getIntInfo(context, "fps", 30)){
            case 120:
                tvFpsSet.setText("100");
                break;
            case 240:
                tvFpsSet.setText("200");
                break;
            default:
                tvFpsSet.setText("30");
                break;
        }
        tvFpsNum.setText(StorageCustomerInfoUtil.getIntInfo(context, "fpsJump", 1) + "");
        tvShutter.setText(StorageCustomerInfoUtil.getLongInfo(context, "km", (long) 0) == 0 ? "自动" : 1000000000/StorageCustomerInfoUtil.getLongInfo(context, "km", (long) 0) + "");
        tvDelayTime.setText(new BigDecimal(StorageCustomerInfoUtil.getIntInfo(context, "delayTime", 0)).divide(new BigDecimal("1000")).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "s");
        tvShootingDelay.setText(new BigDecimal(StorageCustomerInfoUtil.getIntInfo(context, "shootingDelay", 0)).divide(new BigDecimal("1000")).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "s");
        tvCut.setText(StorageCustomerInfoUtil.getIntInfo(context, "cut", 10) + "");
        switchAutoStart.setChecked(StorageCustomerInfoUtil.getBooleanInfo("autoStart", context, true));
        switchRedLine.setChecked(StorageCustomerInfoUtil.getBooleanInfo("redLine", context, true));
        switchName.setChecked(StorageCustomerInfoUtil.getBooleanInfo("nameSwitch", context, false));
        switchDaley.setChecked(StorageCustomerInfoUtil.getBooleanInfo("daley", context, true));
        switchYun.setChecked(StorageCustomerInfoUtil.getBooleanInfo("switchYun", context, true));
        switchYun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                StorageCustomerInfoUtil.putInfo(context, "switchYun", isChecked);
            }
        });
        switchDaley.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                StorageCustomerInfoUtil.putInfo(context, "daley", isChecked);
            }
        });
        switchName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                StorageCustomerInfoUtil.putInfo(context, "nameSwitch", isChecked);
            }
        });
        switchAutoStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                StorageCustomerInfoUtil.putInfo(context, "autoStart", isChecked);
            }
        });
        switchRedLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                StorageCustomerInfoUtil.putInfo(context, "redLine", isChecked);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            setResult(RESULT_OK, new Intent().putExtra("path", data.getStringExtra("path")));
            finish();
        }
    }

    @OnClick({R.id.tv_left, R.id.ll_local, R.id.ll_fps_set, R.id.ll_fps_num, R.id.ll_cut, R.id.ll_shutter, R.id.switch_auto_start, R.id.ll_auto_start, R.id.ll_delay_time, R.id.ll_shooting_delay, R.id.switch_red_line, R.id.ll_red_line, R.id.ll_about_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.ll_local:
                startActivityForResult(new Intent(context, LocalListActivity.class), 100);
                break;
            case R.id.ll_fps_set:
                FPSSetDialog fpsSetDialog = new FPSSetDialog();
                fpsSetDialog.setOn30ClickListener(new FPSSetDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        tvFpsSet.setText("30");
                        StorageCustomerInfoUtil.putInfo(context, "fps", 30);
                    }
                });
                fpsSetDialog.setOn120ClickListener(new FPSSetDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        tvFpsSet.setText("100");
                        StorageCustomerInfoUtil.putInfo(context, "fps", 120);
                    }
                });
                fpsSetDialog.setOn240ClickListener(new FPSSetDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        tvFpsSet.setText("200");
                        StorageCustomerInfoUtil.putInfo(context, "fps", 240);
                    }
                });
                fpsSetDialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.ll_fps_num:
                hintModel = new HintModel();
                hintModel.setTitle("请设置帧数跳转");
                inputDialog = InputDialog.getInstance(hintModel);
                inputDialog.setOnClickListener(new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(BigDecimal value) {
                        StorageCustomerInfoUtil.putInfo(context, "fpsJump", value.intValue());
                        tvFpsNum.setText(value.toString());
                    }
                });
                inputDialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.ll_shutter:
                hintModel = new HintModel();
                hintModel.setTitle("请设置快门速度");
                long min =1000000000/ StorageCustomerInfoUtil.getLongInfo(context, "KMmin", (long) 1000 * 1000);
                long max = 1000000000/StorageCustomerInfoUtil.getLongInfo(context, "KMmax", (long) 10 * 1000 * 1000);
                hintModel.setContent("请输入" + max + "-" + min + "的数值(单位分之一秒),输入0为自动");
                inputDialog = InputDialog.getInstance(hintModel);
                inputDialog.setOnClickListener(new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(BigDecimal value) {
                        long values = Math.max(Math.min(min, value.longValue()), max);
                        if (values == 0||value.intValue()==0) {
                            tvShutter.setText("自动");
                            StorageCustomerInfoUtil.putInfo(context, "km", (long)0);
                        } else {
                            tvShutter.setText(values + "");
                            StorageCustomerInfoUtil.putInfo(context, "km", (long)1000000000/values);
                        }
                    }
                });
                inputDialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.ll_delay_time:
                hintModel = new HintModel();
                hintModel.setTitle("请设置延迟时间");
                hintModel.setNum(false);
                inputDialog = InputDialog.getInstance(hintModel);
                inputDialog.setOnClickListener(new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(BigDecimal value) {
                        BigDecimal bigDecimal = new BigDecimal("1000");
                        StorageCustomerInfoUtil.putInfo(context, "delayTime", value.abs().setScale(2, BigDecimal.ROUND_HALF_UP).multiply(bigDecimal).intValue());
                        tvDelayTime.setText(value.abs().setScale(2, BigDecimal.ROUND_HALF_UP) + "s");
                    }
                });
                inputDialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.ll_shooting_delay:
                hintModel = new HintModel();
                hintModel.setTitle("请设置拍摄延迟");
                hintModel.setNum(false);
                inputDialog = InputDialog.getInstance(hintModel);
                inputDialog.setOnClickListener(new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(BigDecimal value) {
                        BigDecimal bigDecimal = new BigDecimal("1000");
                        StorageCustomerInfoUtil.putInfo(context, "shootingDelay", value.abs().setScale(2, BigDecimal.ROUND_HALF_UP).multiply(bigDecimal).intValue());
                        tvShootingDelay.setText(value.abs().setScale(2, BigDecimal.ROUND_HALF_UP) + "s");
                    }
                });
                inputDialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.ll_cut:
                hintModel = new HintModel();
                hintModel.setTitle("请设置视频段数");
                inputDialog = InputDialog.getInstance(hintModel);
                inputDialog.setOnClickListener(new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(BigDecimal value) {
                        StorageCustomerInfoUtil.putInfo(context, "cut", Math.max(Math.min(value.intValue(), 10),1) );
                        tvCut.setText(Math.max(Math.min(value.intValue(), 10),1) + "");
                    }
                });
                inputDialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.ll_about_us:
                break;
        }
    }
}
