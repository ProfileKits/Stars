package com.predictor.galaxy.ui;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.flycode.data.constant.Setting;
import com.predictor.galaxy.R;
import com.predictor.galaxy.bean.Person;
import com.predictor.galaxy.bean.RankingBean;
import com.predictor.galaxy.config.Config;
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
import com.predictor.library.oknet.CNttp;
import com.predictor.library.oknet.callback.HttpCallback;
import com.predictor.library.pickerview.interfaces.SelectTimeCallBack;
import com.predictor.library.rx.NormalSubscriber;
import com.predictor.library.rx.RxTransformerHelper;
import com.predictor.library.utils.CNAnimationUtils;
import com.predictor.library.utils.CNBroadcastUtils;
import com.predictor.library.utils.CNBugly;
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
import com.predictor.library.view.CNTextTool;
import com.predictor.galaxy.view.PickerView;

import java.io.IOException;
import java.util.Map;

import io.reactivex.disposables.Disposable;
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
    private CNDoooArt.YoYoString rope;
    private CNCleanEditText et;
    private CNProgressCircle circle;
    String sign = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //????????????key
        Log.i("KEYSGIN", CNBaseInvoke.getInstance().getSign(this));
        //2023-3-3???11????????????
        CNLog.PRINTDATA(new Setting().getTESTVALUE());//????????????Library????????????????????????????????????????????????????????????????????????4??????

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
        bean.setStr1("??????????????????????????????????????????" + sign);
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
     * circleView ????????????
     */
    private void circleView() {
        circle = findViewById(R.id.progress_circle);
        circle.setMaxProgress(100);
        handler.postDelayed(runnable, 200);
//        circle.setProgress(20);
//        circle.setProgress(100, 9000);
    }


    /**
     * EditText ???????????????????????????
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
     * ??????bugly??????????????????bugly??????????????????
     */
    private void testBuglyCrash() {
        CNBugly.testCrash();//??????Bugly Crash
    }


    /**
     * ?????????Toast??????
     */
    private void customToast() {
        String text = et.getText().toString();
        if (!text.isEmpty()) {
            //    CNToast.show(MainActivity.this,et.getText().toString());
            //    CNCustomToast.ToastLongTopCenter(MainActivity.this,et.getText().toString());
            //    CNToastCustom.showWhiteToast(MainActivity.this,"??????");
            //    CNToastCustom.setCustom(MainActivity.this,et.getText().toString(),100,100,10,255,28,false);
            CNToastCustom.showBlackToast(MainActivity.this, text);
        }
    }

    /**
     * ??????????????????????????????
     */
    private void isPhoneNumber() {
        CNToast.show(mContext, "?????????????????????" + CNValidatorUtil.isPhone("17022222222", true) + "");
    }

    @Override
    protected void initView() {
        ll_layout = findViewById(R.id.ll_layout);
        textView = findViewById(R.id.tv);
        textView2 = findViewById(R.id.tv2);
        btn_viewpage = findViewById(R.id.btn_viewpage);
        et = findViewById(R.id.et);
        circleView();
        CNTextViewUtil.setTextLeftIcon(this, R.drawable.kaixin, textView2, 0);

        CNLogUtil.i("key22222222222:");


    }


    private void showDialog() {
        CNDialogInfo info = new CNDialogInfo();
        info.setTitle("??????????????????");
        info.setContent("?????????????????????????????????");
//        info.setOkButtonColor(getResources().getColor(R.color.green));
//        info.setDarkTheme(true);
        info.setOutsideTouch(true);
        CNDialog.show(this, info, (isOk, v) -> {
            if (isOk) {

            }
        });
    }

    /**
     * ??????DES?????????????????????
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
        //????????????
        CNLogUtil.i("Hello World");
        testDES();
        //?????????????????????
        CNTextTool.getBuilder("???????????????").setContext(this).setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                .append(" ")
                .append("?????????").setTypefaceHNDB().setForegroundColor(Color.parseColor("#008800")).setBold()
                .append(" ")
                .append("?????????").setTypefaceHND().setForegroundColor(Color.parseColor("#67222222")).setNoBold()
                .append(" ")
                .append("?????????").setTypefaceHNDB().setForegroundColor(Color.parseColor("#777888")).setBold()
                .append(" ")
                .append("?????????").setTypefaceHND().setForegroundColor(Color.parseColor("#ee3313")).setNoBold()
                .into(textView);


//        CNSnackbar.show(this,"Hello World",textView);
        //?????????Toast
//        CNCustomToast.ToastLongCenter(MainActivity.this, "?????????Toast");

        //????????????
//        CNAnimationUtils.startWith(textView, CNAnimationUtils.IN_ROTATE360, 1000);
        CNAnimationUtils.startWith(textView, CNAnimationUtils.IN_ROTATE360, 5000, 1000, -1);
//      initNetwork();
//      submitOrder();

        TestCallback testCallback = new TestCallback();
        CNLogUtil.i(testCallback.Go());
//      testNetwork();
        CNLog.PRINTDATA(textView.getText().toString());

        testNetwork();
//      okNetwork();
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
        CNttp.get().url(Config.testUrl2)
                .build().execute(new HttpCallback() {
                    @Override
                    public void onSuccess(Object data, int id) {
                        CNLog.PRINTDATA("????????????1" + data.toString());
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CNLog.PRINTDATA("????????????2" + e.toString());
                    }
                });
    }


    /**
     * ????????????
     * ?????????Map??????
     * Map???????????????
     */
    private void dataTodata() {
        Person person = new Person();
        person.setName("??????");
        person.setId("6");
        Gson gson = new Gson();
        String json = gson.toJson(person);
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map = gson.fromJson(json, type);
        CNLogUtil.i("??????map?????????" + map.toString());

        try {
            Person person1 = (Person) CNJsonUtils.mapToObject(map, Person.class);
            CNLogUtil.i("???????????????person???" + person1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //???????????? ??????????????????
    private void submitOrder() {
        String jsonStr = "{\"agingType\":2,\"appointmentLoadTime\":\"2023-02-08 20:29:35\",\"dist\":6483048.399999999,\"goodsName\":\"??????\",\"goodsType\":\"cake\",\"goodsWeight\":\"1\",\"orderType\":1,\"receiverAddress\":\"????????????????????????????????? 1103\",\"receiverLat\":\"39.164274\",\"receiverLon\":\"117.00036\",\"receiverName\":\"????????????\",\"receiverPhone\":\"18989898989\",\"sendAddress\":\"1%????????? 112\",\"sendLat\":\"39.375546\",\"sendLon\":\"117.054218\",\"sendName\":\"?????????\",\"sendPhone\":\"15080808080\",\"userRemark\":\"???????????????????????????\"}";
        RequestBody body = RequestBody.create(jsonStr, MediaType.parse("application/json; charset=utf-8"));
        RetrofitNetwork.getInstance().submitTcExpressOrder(mContext, body, new RetrofitNetwork.NetResult() {
            @Override
            public void success(Object result) {
                CNLogUtil.i("???????????????" + result.toString());
            }

            @Override
            public void error(String msg) {
                CNLogUtil.i("???????????????" + msg.toString());
            }
        });
    }


    /**
     * ??????????????????
     */
    private void initNetwork() {
        if (CNHttpUtil.isNetConnected(this)) {
            NormalSubscriber<RankingBean> subscriber = new NormalSubscriber<RankingBean>() {
                @Override
                public void onNext(RankingBean result) {
                    if (result.getCode() == 0) {//????????????
                        RankingBean RankingBean = result;
//                        Object object = result.data;
//                        Gson gson = new Gson();
//                        String json =gson.toJson(object,String.class);
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.putExtra("fsdf", "");
                        intent.putExtra("?????????", 3);
                        CNLog.PRINTDATA(result);
//                        CNToast.show(MainActivity.this, "??????????????????:" + result.code +"-msg:"+result.msg +"-data:"+json);
                    } else {
                        //??????????????????
                        CNToast.show(MainActivity.this, "??????????????????");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    CNToast.show(MainActivity.this, "??????????????????" + e.getMessage());
                }
            };

            Disposable disposable = RetrofitService.getInstance().getWebApi().getRankingToDay(RetrofitService.getInstance().getHeader(), "3205")
                    .compose(RxTransformerHelper.applySchedulers())
                    .subscribe(data -> subscriber.onNext((RankingBean) data), e -> subscriber.onError((Throwable) e));
            RetrofitUtil.addSubscription(disposable);

        } else {
            CNToast.show(this, "?????????????????????");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RetrofitUtil.RetrofitRelease();
    }

    /**
     * ?????????????????????
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

        //???????????????????????????
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CNFastClickCheckUtil.check(v);
                //?????????????????????
                pickerView = new PickerView(MainActivity.this, "??????????????????", new SelectTimeCallBack() {
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
     * ??????????????????????????????????????????????????????
     *
     * @return
     */
    @Override
    protected boolean setFullScreen() {
        setStatusBar(0, true, false, false, true
                , null, R.color.white, R.color.white, R.color.white, R.color.white);
        return true;
    }


    //soapXml????????????
    private void httpForSoap(String username, String password) {
        OkHttpClient client = new OkHttpClient();
        // ??????SOAP????????????
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

        // ??????HTTP??????
        Request request = new Request.Builder()
                .url("http://yourwebservice.com/yourmethod.asmx")
                .addHeader("Content-Type", "text/xml; charset=utf-8")
                .addHeader("SOAPAction", "http://tempuri.org/SomeSoapMethod")
                .post(RequestBody.create(MediaType.parse("text/xml; charset=utf-8"), soapXml))
                .build();

        // ????????????
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ????????????
        if (response.isSuccessful()) {
            String responseBody = response.body().toString();
            // ??????SOAP????????????
        } else {
            // ???????????????????????????
        }
    }

}