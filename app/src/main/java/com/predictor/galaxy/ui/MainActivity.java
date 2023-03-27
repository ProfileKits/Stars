package com.predictor.galaxy.ui;

import static com.predictor.library.net.lib.annotation.constant.NetworkTypeEnum.NETWORK_2G;
import static com.predictor.library.net.lib.annotation.constant.NetworkTypeEnum.NETWORK_3G;
import static com.predictor.library.net.lib.annotation.constant.NetworkTypeEnum.NETWORK_4G;
import static com.predictor.library.net.lib.annotation.constant.NetworkTypeEnum.NETWORK_NO;
import static com.predictor.library.net.lib.annotation.constant.NetworkTypeEnum.NETWORK_WIFI;
import static com.predictor.library.utils.CNAnimationUtils.IN_ROTATE360;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.flycode.data.constant.Setting;
import com.predictor.galaxy.R;
import com.predictor.galaxy.bean.Person;
import com.predictor.galaxy.bean.RankingBean;
import com.predictor.galaxy.config.Config;
import com.predictor.galaxy.entity.EventKey;
import com.predictor.galaxy.module.TestCallback;
import com.predictor.galaxy.net.RetrofitNetwork;
import com.predictor.galaxy.net.RetrofitService;
import com.predictor.library.artanimation.library.CNDoooArt;
import com.predictor.library.artanimation.library.Techniques;
import com.predictor.library.base.CNBaseActivity;

import com.predictor.library.base.CNBaseInvoke;
import com.predictor.library.bean.BroadcastBean;
import com.predictor.library.bean.CNDialogInfo;
import com.predictor.library.example.SetSelfWithPoint;
import com.predictor.library.listener.OnChangeListener;
import com.predictor.library.net.RetrofitUtil;
import com.predictor.library.net.lib.CNetStateWatcher;
import com.predictor.library.net.lib.annotation.NetworkStateChanged;
import com.predictor.library.oknet.CNHttp;
import com.predictor.library.oknet.callback.HttpCallback;
import com.predictor.library.pickerview.interfaces.SelectTimeCallBack;
import com.predictor.library.rx.NormalSubscriber;
import com.predictor.library.rx.RxTransformerHelper;
import com.predictor.library.rx.rxutil.lifecycle.RxLifecycle;
import com.predictor.library.rx.rxutil.rxbus.RxEvent;
import com.predictor.library.rx.rxutil.rxjava.RxJavaUtils;
import com.predictor.library.rx.rxutil.rxjava.task.RxIOTask;
import com.predictor.library.rx.rxutil.rxjava.task.RxUITask;
import com.predictor.library.rx.rxutil.subsciber.AppExecutors;
import com.predictor.library.rx.rxutil.subsciber.ProgressDialogLoader;
import com.predictor.library.rx.rxutil.subsciber.impl.IProgressLoader;
import com.predictor.library.utils.CNAnimationUtils;
import com.predictor.library.utils.CNBroadcastUtils;
import com.predictor.library.utils.CNBugly;
import com.predictor.library.utils.CNBus;
import com.predictor.library.utils.CNDES;
import com.predictor.library.utils.CNEditTextUtil;
import com.predictor.library.utils.CNFastClickCheckUtil;
import com.predictor.library.utils.CNHttpUtil;
import com.predictor.library.utils.CNJsonUtils;
import com.predictor.library.utils.CNLog;
import com.predictor.library.utils.CNLogUtil;
import com.predictor.library.utils.CNReceiverUtils;
import com.predictor.library.utils.CNTextViewUtil;
import com.predictor.library.utils.CNToast;
import com.predictor.library.utils.CNToastCustom;
import com.predictor.library.utils.CNValidatorUtil;
import com.predictor.library.view.CNCleanEditText;
import com.predictor.library.view.CNDialog;
import com.predictor.library.view.CNProgressCircle;
import com.predictor.library.view.CNTaiJiView;
import com.predictor.library.view.CNTextTool;
import com.predictor.galaxy.view.PickerView;

import java.io.IOException;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class MainActivity extends CNBaseActivity {
    private LinearLayout ll_layout;
    private TextView textView, textView2;
    private PickerView pickerView;
    private Button btn_viewpage;
    private ImageView imageView;
    private CNDoooArt.YoYoString rope;
    private CNCleanEditText et;
    private CNProgressCircle circle;
    String sign = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取签名key
        Log.i("KEYSGIN", CNBaseInvoke.getInstance().getSign(this));
        //2023-3-3晚11点替换的
        CNLog.PRINTDATA(new Setting().getTESTVALUE());//测试覆盖Library会不会更新数据，如果更新成功这个值会变成人工智能4个字

        CNReceiverUtils.getInstance(mContext, (CNReceiverUtils.RegisterStrListener)
                data -> {
                    sign = data;
                });
        CNBroadcastUtils.sendStringCmd(mContext, "@*$(@*#)(@*");

        CNReceiverUtils.getInstance(mContext, new CNReceiverUtils.RegisterObjectListener() {
            @Override
            public void receiver(BroadcastBean obj) {
                CNToast.show(mContext, obj.getStr1() + "-" + obj.isBool1() + "-" + obj.isBool2());
            }
        });
        BroadcastBean bean = new BroadcastBean();
        bean.setStr1("广播发送数据了！！！！！！！" + sign);
        bean.setBool2(false);
        bean.setBool1(true);

        CNBroadcastUtils.sendObjectCmd(mContext, bean);
    }


    private int pos = 0;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            pos++;
            if (pos <= 100) {
                circle.setProgress(pos);
                handler.postDelayed(this, 20);
            }
        }
    };


    /**
     * circleView 进度控件
     */
    private void circleView() {
        circle = findViewById(R.id.progress_circle);
        circle.setMaxProgress(100);
        handler.postDelayed(runnable, 200);
//        circle.setProgress(20);
//        circle.setProgress(100, 9000);
    }


    /**
     * EditText 控件内容变化监听器
     */
    private void editTextChangeListener() {
        CNEditTextUtil.setChangeListener(et, new OnChangeListener() {
            @Override
            public void change() {
                customToast();
            }
        });
    }


    /**
     * 测试bugly崩溃，以判断bugly是否集成成功
     */
    private void testBuglyCrash() {
        CNBugly.testCrash();//测试Bugly Crash
    }


    /**
     * 自定义Toast控件
     */
    private void customToast() {
        String text = et.getText().toString();
        if (!text.isEmpty()) {
            //    CNToast.show(MainActivity.this,et.getText().toString());
            //    CNCustomToast.ToastLongTopCenter(MainActivity.this,et.getText().toString());
            //    CNToastCustom.showWhiteToast(MainActivity.this,"测试");
            //    CNToastCustom.setCustom(MainActivity.this,et.getText().toString(),100,100,10,255,28,false);
            CNToastCustom.showBlackToast(MainActivity.this, text);
        }
    }

    /**
     * 判断号码是否为手机号
     */
    private void isPhoneNumber() {
        CNToast.show(mContext, "是否为手机号：" + CNValidatorUtil.isPhone("17022222222", true) + "");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        ll_layout = findViewById(R.id.ll_layout);
        textView = findViewById(R.id.tv);
        textView2 = findViewById(R.id.tv2);
        imageView = findViewById(R.id.iv_loading);
        btn_viewpage = findViewById(R.id.btn_viewpage);
        et = findViewById(R.id.et);
        circleView();
        CNTextViewUtil.setTextLeftIcon(this, R.drawable.kaixin, textView2, 0);

        CNLogUtil.i("key22222222222:");

        //太极图
        taiJiView = findViewById(R.id.taiji);
        taiJiView.setColor(Color.parseColor("#f0f0f0"),Color.parseColor("#0f0f0f"));
        taiJiView.setVelocity(5000);
        handlerTaiji.post(runnableTaiji);
    }

    private CNTaiJiView taiJiView;
    Handler handlerTaiji = new Handler();
    Runnable runnableTaiji = new Runnable() {
        @Override
        public void run() {
            taiJiView.startLoad();
        }
    };


    private void showDialog() {
        CNDialogInfo info = new CNDialogInfo();
        info.setTitle("确定删除吗？");
        info.setContent("删除数据会引起业务问题");
//        info.setOkButtonColor(getResources().getColor(R.color.green));
//        info.setDarkTheme(true);
        info.setOutsideTouch(true);
        CNDialog.show(this, info, (isOk, v) -> {
            if (isOk) {

            }
        });
    }

    /**
     * 测试DES文本加密与解密
     */
    private void testDES() {
        String key = "1fgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34t";
        try {
            textView.setText(CNDES.encryptDES("Hello World", key));
            textView2.setText(CNDES.decryptDES(textView.getText().toString(), key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pickerView != null && pickerView.pvOptions.isShowing()) {
                pickerView.dissmiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initData() {
        //打印日志
        CNLogUtil.i("Hello World");
        testDES();
        //测试拼装字符串
        CNTextTool.getBuilder("拼装字符串").setContext(this).setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                .append(" ")
                .append("绿色的").setTypefaceHNDB().setForegroundColor(Color.parseColor("#008800")).setBold()
                .append(" ")
                .append("灰色的").setTypefaceHND().setForegroundColor(Color.parseColor("#67222222")).setNoBold()
                .append(" ")
                .append("蓝色的").setTypefaceHNDB().setForegroundColor(Color.parseColor("#777888")).setBold()
                .append(" ")
                .append("红色的").setTypefaceHND().setForegroundColor(Color.parseColor("#ee3313")).setNoBold()
                .into(textView);


//        CNSnackbar.show(this,"Hello World",textView);
        //自定义Toast
//        CNCustomToast.ToastLongCenter(MainActivity.this, "自定义Toast");

        //旋转动画
//        CNAnimationUtils.startWith(textView, CNAnimationUtils.IN_ROTATE360, 1000);
        CNAnimationUtils.startWith(textView, IN_ROTATE360, 5000, 1000, -1);
//      initNetwork();
//      submitOrder();

        TestCallback testCallback = new TestCallback();
        CNLogUtil.i(testCallback.Go());
//      testNetwork();
        CNLog.PRINTDATA(textView.getText().toString());

        testNetwork();
//      okNetwork();
        mProgressLoader = new ProgressDialogLoader(this, "正在加载数据，请稍后...");
        testBusTools();
        loadingAnimation();
//        run5();
//        mainThreadAndChildThread();
    }



    //线程切换
    private void mainThreadAndChildThread() {
        //UI线程
        RxJavaUtils.doInUIThread(new RxUITask<String>("我是入参456") {
            @Override
            public void doInUIThread(String s) {

            }
        });
        //IO线程
        RxJavaUtils.doInIOThread(new RxIOTask<String>("我是入参123") {
            @Override
            public Void doInIOThread(String s) {

                return null;
            }
        });
        //回到IO线程
        AppExecutors.get().poolIO().execute(new Runnable() {
            @Override
            public void run() {
//                CNBus.init().post(EventKey.EVENT_BACK_MAINTHREAD);
            }
        });
        //回到主线程
        AppExecutors.get().mainThread().execute(new Runnable() {
            @Override
            public void run() {
//                CNBus.init().post(EventKey.EVENT_BACK_NORMAL);
            }
        });
        //子线程
        AppExecutors.get().singleIO().execute(new Runnable() {
            @Override
            public void run() {
//                CNBus.init().post(EventKey.EVENT_BACK_NORMAL);
            }
        });
    }

    //旋转动画
    private void loadingAnimation() {
        CNAnimationUtils.startWith(imageView, IN_ROTATE360, 1000);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // 注册网络状态监听
        CNetStateWatcher.getInstance().register(mContext);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 注销网络状态监听
        CNetStateWatcher.getInstance().unRegister(mContext);
    }


    /**
     * 网络发生变化
     *
     * @param type 网络类型
     */
    @NetworkStateChanged(notifyOnAppStart = false)
    void onNetworkStateChanged(int type) {
        switch (type) {
            case NETWORK_2G:
            case NETWORK_3G:
            case NETWORK_4G:
                Toast.makeText(this, "网络状态改变了>>>手机网络", Toast.LENGTH_SHORT).show();
                break;
            case NETWORK_WIFI:
                Toast.makeText(this, "网络状态改变了>>>WIFI", Toast.LENGTH_SHORT).show();
                break;
            case NETWORK_NO:
                Toast.makeText(this, "网络状态改变了>>>无网络", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //每隔5秒执行一次
    private void run5() {
        RxJavaUtils.polling(5)
                .compose(RxLifecycle.with(this).<Long>bindToLifecycle())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long o) throws Exception {
                        CNToast.show(mContext, "12121212121" + o);
                    }
                });
    }


    private IProgressLoader mProgressLoader;

    //测试订阅工具
    private void testBusTools() {
        //第一种方式，使用send方法发送通用式RxEvent消息
        CNBus.init().obtain(EventKey.EVENT_SEND, new Consumer<RxEvent>() {
            @Override
            public void accept(RxEvent rxEvent) throws Exception {
                CNToast.show(mContext, "BUS订阅收到消息");
            }
        });
        CNBus.init().send(EventKey.EVENT_SEND);

        //第二种方式，使用post方法发送自定义消息
//        CNBus.init().obtain(EventKey.EVENT_HAVE_DATA, Event.class, new Consumer<Event>() {
//            @Override
//            public void accept(Event event) throws Exception {
//                CNToast.show(mContext, event.getEventName() + event.getContent());
//            }
//        });
//        //5秒后执行
//        RxJavaUtils.delay("加载完毕！", 5, TimeUnit.SECONDS, new ProgressLoadingSubscriber<String>(mProgressLoader) {
//            @Override
//            public void onSuccess(String s) {
//                CNBus.init().post(EventKey.EVENT_HAVE_DATA, new Event("BUS-POST订阅", "收到消息！"));
//            }
//        });
    }

    private void test2Network() {
        RetrofitNetwork.getInstance().testNetwork(this, new RetrofitNetwork.NetResult() {
            @Override
            public void success(Object result) {
                RankingBean bean = (RankingBean) result;
                CNLog.PRINTDATA(bean.getData());
            }

            @Override
            public void error(String msg) {

                CNLog.PRINTDATA(msg);
            }
        });
    }

    private void testNetwork() {
        RetrofitNetwork.getInstance().getAPIdata(this, new RetrofitNetwork.NetResult() {
            @Override
            public void success(Object result) {
                RankingBean bean = (RankingBean) result;
                CNLog.PRINTDATA(bean.getData());
            }

            @Override
            public void error(String msg) {
                CNLog.PRINTDATA(msg.toString());
            }
        });
    }


    private void okNetwork() {
        //直接使用就可以
        CNHttp.get().url(Config.testUrl2)
                .build().execute(new HttpCallback() {
                    @Override
                    public void onSuccess(Object data, int id) {
                        CNLog.PRINTDATA("网络访问1" + data.toString());
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CNLog.PRINTDATA("网络访问2" + e.toString());
                    }
                });
    }


    /**
     * 数据转换
     * 对象转Map集合
     * Map集合转对象
     */
    private void dataTodata() {
        Person person = new Person();
        person.setName("张三");
        person.setId("6");
        Gson gson = new Gson();
        String json = gson.toJson(person);
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map = gson.fromJson(json, type);
        CNLogUtil.i("打印map集合：" + map.toString());

        try {
            Person person1 = (Person) CNJsonUtils.mapToObject(map, Person.class);
            CNLogUtil.i("打印转后的person：" + person1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //测试数据 模拟提交订单
    private void submitOrder() {
        String jsonStr = "{\"agingType\":2,\"appointmentLoadTime\":\"2023-02-08 20:29:35\",\"dist\":6483048.399999999,\"goodsName\":\"蛋糕\",\"goodsType\":\"cake\",\"goodsWeight\":\"1\",\"orderType\":1,\"receiverAddress\":\"天津杨柳青高尔夫俱乐部 1103\",\"receiverLat\":\"39.164274\",\"receiverLon\":\"117.00036\",\"receiverName\":\"李李李李\",\"receiverPhone\":\"18989898989\",\"sendAddress\":\"1%推理社 112\",\"sendLat\":\"39.375546\",\"sendLon\":\"117.054218\",\"sendName\":\"张张张\",\"sendPhone\":\"15080808080\",\"userRemark\":\"请填写其它配送要求\"}";
        RequestBody body = RequestBody.create(jsonStr, MediaType.parse("application/json; charset=utf-8"));
        RetrofitNetwork.getInstance().submitTcExpressOrder(mContext, body, new RetrofitNetwork.NetResult() {
            @Override
            public void success(Object result) {
                CNLogUtil.i("提交成功：" + result.toString());
            }

            @Override
            public void error(String msg) {
                CNLogUtil.i("提交失败：" + msg.toString());
            }
        });
    }


    /**
     * 测试网络框架
     */
    private void initNetwork() {
        if (CNHttpUtil.isNetConnected(this)) {
            NormalSubscriber<RankingBean> subscriber = new NormalSubscriber<RankingBean>() {
                @Override
                public void onNext(RankingBean result) {
                    if (result.getCode() == 0) {//请求成功
                        RankingBean RankingBean = result;
//                        Object object = result.data;
//                        Gson gson = new Gson();
//                        String json =gson.toJson(object,String.class);
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.putExtra("fsdf", "");
                        intent.putExtra("电视上", 3);
                        CNLog.PRINTDATA(result);
//                        CNToast.show(MainActivity.this, "请求数据成功:" + result.code +"-msg:"+result.msg +"-data:"+json);
                    } else {
                        //请求数据失败
                        CNToast.show(MainActivity.this, "请求数据失败");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    CNToast.show(MainActivity.this, "请求数据出错" + e.getMessage());
                }
            };

            Disposable disposable = RetrofitService.getInstance().getWebApi().getRankingToDay(RetrofitService.getInstance().getHeader(), "3205")
                    .compose(RxTransformerHelper.applySchedulers())
                    .subscribe(data -> subscriber.onNext((RankingBean) data), e -> subscriber.onError((Throwable) e));
            RetrofitUtil.addSubscription(disposable);

        } else {
            CNToast.show(this, "请检查网络连接");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RetrofitUtil.RetrofitRelease();
        // 在app主界面/栈中最后一个Activity停止网络监听
        CNetStateWatcher.getInstance().stopWatch();
        CNBus.init().unregisterAll(EventKey.EVENT_HAVE_DATA);
        CNBus.init().unregisterAll(EventKey.EVENT_SEND);
    }

    /**
     * 测试控件动画库
     */
    private void testAnimation() {
        SetSelfWithPoint.with(true).repeat(200).setTimes(1000);

        if (rope != null) {
            rope.stop(true);
        }
        rope = CNDoooArt.with((Techniques.ZoomInRight))
                .duration(1200)
                .repeat(CNDoooArt.INFINITE)
                .pivot(CNDoooArt.CENTER_PIVOT, CNDoooArt.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Toast.makeText(MainActivity.this, "canceled previous animation", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(textView2);
    }


    @Override
    protected void initListener() {
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SecondActivity.class);
            }
        });

        btn_viewpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ViewPagerActivity.class);
            }
        });

        //点击弹出选项选择器
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CNFastClickCheckUtil.check(v);
                //选择时间选择器
                pickerView = new PickerView(MainActivity.this, "选择预约时间", new SelectTimeCallBack() {
                    @Override
                    public void onTimeSelect(String time) {
                        textView.setText(time);
                    }

                    @Override
                    public void onCancel() {
                        CNAnimationUtils.stopWith(textView);
                    }
                });

            }
        });
        textView.invalidate();
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testAnimation();
            }
        });
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }


    /**
     * 设置是否全屏，状态栏和任务栏状态设置
     *
     * @return
     */
    @Override
    protected boolean setFullScreen() {
        setStatusBar(0, true, false, false, true
                , null, R.color.white, R.color.white, R.color.white, R.color.white);
        return true;
    }


    //soapXml访问示例
    private void httpForSoap(String username, String password) {
        OkHttpClient client = new OkHttpClient();
        // 构造SOAP请求消息
        String soapXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<soap:Body>" +
                "<SomeSoapMethod xmlns=\"http://tempuri.org/\">" +
                "<user1>" + username + "</user1>" +
                "<psw>" + password + "</psw>" +
                "</SomeSoapMethod>" +
                "</soap:Body>" +
                "</soap:Envelope>";

        // 构造HTTP请求
        Request request = new Request.Builder()
                .url("http://yourwebservice.com/yourmethod.asmx")
                .addHeader("Content-Type", "text/xml; charset=utf-8")
                .addHeader("SOAPAction", "http://tempuri.org/SomeSoapMethod")
                .post(RequestBody.create(MediaType.parse("text/xml; charset=utf-8"), soapXml))
                .build();

        // 发送请求
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 处理响应
        if (response.isSuccessful()) {
            String responseBody = response.body().toString();
            // 解析SOAP响应消息
        } else {
            // 处理请求失败的情况
        }
    }

}