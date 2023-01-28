package com.predictor.galaxy.view;

import android.content.Context;

import com.predictor.galaxy.bean.PickerViewData;
import com.predictor.galaxy.bean.TimeBean;
import com.predictor.library.pickerview.OptionsPickerView;
import com.predictor.library.pickerview.TimePickerView;
import com.predictor.library.pickerview.model.IPickerViewData;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PickerView {
    private ArrayList<TimeBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<IPickerViewData>>> options3Items = new ArrayList<>();
    public TimePickerView pvTime;//时间选择器
    public OptionsPickerView pvOptions;//外卖条件选择器
    private Context context;

    public void dissmiss() {
        if (pvOptions.isShowing() || pvTime.isShowing()) {
            pvOptions.dismiss();
            pvTime.dismiss();
        }
        if (pvTime.isShowing()) {
            pvTime.dismiss();

        }
    }

    public PickerView(Context context, SelectTimeCallBack callBack) {
        this.context = context;
        setPickerListener(callBack);
    }

    private void setPickerListener(SelectTimeCallBack callBack){
        //时间选择器
        pvTime = new TimePickerView(context, TimePickerView.Type.MONTH_DAY_HOUR_MIN);
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                callBack.onTimeSelect(getTime(date));
            }
        });

        //选项选择器
        pvOptions = new OptionsPickerView(context);
        //选项1
        options1Items.add(new TimeBean("今天"));
        options1Items.add(new TimeBean("明天"));

        ArrayList<String> options2Items_01 = getTodayHourData();
        ArrayList<String> options2Items_02 = getHourData();

        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);

        //选项3
        ArrayList<ArrayList<IPickerViewData>> options3Items_01 = new ArrayList<>();
        ArrayList<ArrayList<IPickerViewData>> options3Items_02 = new ArrayList<>();

        options3Items_01=getmD2();
        options3Items_02 = getmD();

        options3Items.add(options3Items_01);
        options3Items.add(options3Items_02);

        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
        //设置选择的三级单位
        pvOptions.setTitle(" ");
        pvOptions.setCyclic(false, false, false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(0, 0, 0);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+" "
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                callBack.onTimeSelect(tx);
            }
        });

    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    /**
     * 今天 点
     */
    private ArrayList<String> getTodayHourData() {
        int max = currentHour();
        if (max < 23 && currentMin() > 45) {
            max = max + 1;
        }
        ArrayList<String> lists = new ArrayList<>();
        for (int i = max; i < 24; i++) {
            lists.add(i + "点");
        }
        return lists;
    }

    /**
     * 明天 后天 点
     */
    private ArrayList<String> getHourData() {
        ArrayList<String> lists = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            lists.add(i + "点");
        }
        return lists;
    }

    /**
     * 明天 后天  分
     */
    private ArrayList<IPickerViewData> getMinData() {
        ArrayList<IPickerViewData> dataArrayList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            dataArrayList.add(new PickerViewData((i * 10) + "分"));
        }
        return dataArrayList;
    }

    /**
     * 明天 后天
     */
    private ArrayList<ArrayList<IPickerViewData>> getmD() {
        ArrayList<ArrayList<IPickerViewData>> d = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            d.add(getMinData());
        }
        return d;
    }

    /**
     * 明天 后天  2222
     */
    private ArrayList<ArrayList<IPickerViewData>> getmD2() {
        //14
        int max = currentHour();
        if (currentMin() > 45) {
            max = max + 1;
        }
        int value = 24 - max;
        ArrayList<ArrayList<IPickerViewData>> d = new ArrayList<>();
        for (int i = 0; i < value; i++) {
            if (i == 0) {
                d.add(getTodyMinData());
            } else {
                d.add(getMinData());
            }

        }
        return d;
    }

    /**
     * 明天 后天  分2222
     */
    private ArrayList<IPickerViewData> getTodyMinData() {

        int min = currentMin();
        int current = 0;
        if (min > 35 && min <= 45) {
            current = 0;
        } else if (min > 45 && min <= 55) {
            current = 1;
        } else if (min > 55) {
            current = 2;
        } else if (min <= 5) {
            current = 2;
        } else if (min > 5 && min <= 15) {
            current = 3;
        } else if (min > 15 && min <= 25) {
            current = 4;
        } else if (min > 25 && min <= 35) {
            current = 5;
        }
        int max = currentHour();
        if (max > 23 && min > 35) {
            current = 5;
        }

        ArrayList<IPickerViewData> dataArrayList = new ArrayList<>();
        for (int i = current; i < 6; i++) {
            dataArrayList.add(new PickerViewData((i * 10) + "分"));
        }
        return dataArrayList;
    }

    private int currentMin() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MINUTE);
    }


    private int currentHour() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);
    }
}
