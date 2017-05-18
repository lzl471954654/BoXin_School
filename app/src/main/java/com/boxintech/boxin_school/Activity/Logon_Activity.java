package com.boxintech.boxin_school.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.internal.SnackbarContentLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.boxintech.boxin_school.DataClass.AppCache;
import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.DataClass.Student;
import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.OtherClass.ParseDataFromHtml;
import com.boxintech.boxin_school.R;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by LZL on 2017/3/28.
 */

public class Logon_Activity extends AppCompatActivity {
    boolean islogon = false;
    boolean eye_see = false;
    EditText user_id;
    EditText password;
    Button logon_button;
    ProgressDialog progressDialog;
    ImageView clear;
    ImageView eye;
    ImageView secret_image;

    final int CHANGE_PROGRESSBAR = 10;
    final int LOGON_SUCCESS = 9;
    final int CODE_UPDATE = 8;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case LOGON_SUCCESS:
                {
                    progressDialog.dismiss();
                    Intent intent  = new Intent(Logon_Activity.this,MainMenu_Activity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case CHANGE_PROGRESSBAR:
                {
                    progressDialog.dismiss();
                    break;
                }
                case CODE_UPDATE:
                {
                    Bitmap bitmap = (Bitmap)msg.obj;
                    secret_image.setImageBitmap(bitmap);
                    break;
                }
                default:
                {
                    break;
                }
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logon_layout);
        ExitSystem.add(this);
        init();
    }

    public void init()
    {
        eye = (ImageView)findViewById(R.id.logon_eyes);
        clear = (ImageView)findViewById(R.id.logon_clear);
        user_id = (EditText)findViewById(R.id.logon_user_id);
        password = (EditText)findViewById(R.id.logon_password);
        logon_button = (Button)findViewById(R.id.logon_logon_button);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id.getText().clear();
            }
        });
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eye_see)
                {
                    eye_see = false;
                    eye.setImageResource(R.mipmap.eye_cant_see);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else
                {
                    eye_see = true;
                    eye.setImageResource(R.mipmap.eyes_can_see);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        logon_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = user_id.getText().toString();
                String pass = password.getText().toString();
                progressDialog = new ProgressDialog(Logon_Activity.this);
                progressDialog.setMessage("请稍后正在登录");
                progressDialog.setCancelable(false);
                progressDialog.show();

                final Dialog dialog = new Dialog(Logon_Activity.this);
                dialog.setContentView(R.layout.logon_secret_code);
                secret_image = (ImageView) dialog.findViewById(R.id.logon_secret_image);
                final EditText secret_code = (EditText)dialog.findViewById(R.id.logon_secret_code_input);
                Button sure_button = (Button)dialog.findViewById(R.id.logon_sure_button);
                dialog.setCancelable(true);
                dialog.show();

                Request.Builder builder = new Request.Builder();
                builder.url(AppLogonData.getSecret_Image_URL());
                Request request = builder.build();
                if(AppLogonData.getOkHttpClient()==null)
                {
                    AppLogonData.setOkHttpClient(new OkHttpClient());
                }
                Call call = AppLogonData.getOkHttpClient().newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Snackbar.make(logon_button,"对不起网络连接出错",Snackbar.LENGTH_SHORT).show();
                        AppLogonData.setCookies("");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        byte[] bodyarray = response.body().bytes();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bodyarray,0,bodyarray.length);
                        Message message = new Message();
                        message.obj = bitmap;
                        message.what = CODE_UPDATE;
                        handler.sendMessage(message);
                        //secret_image.setImageBitmap(bitmap);
                        String cookies = response.header("Set-Cookie");
                        cookies = cookies.substring(0,cookies.length()-6);
                        AppLogonData.setCookies(cookies);
                    }
                });

                sure_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        final String id = user_id.getText().toString();
                        String pass = password.getText().toString();
                        String code = secret_code.getText().toString();
                        Request.Builder builder1 = new Request.Builder();
                        FormBody formBody = new FormBody.Builder()
                                .add("__VIEWSTATE",AppLogonData.getVIEWSTATE())
                                .add("txtUserName",id)
                                .add("Textbox1","")
                                .add("Textbox2",pass)
                                .add("txtSecretCode",code)
                                .add("RadioButtonList1","学生")
                                .add("Button1","")
                                .add("lbLanguage","")
                                .add("hidPdrs","")
                                .add("hidsc","")
                                .build();
                        Request request1 = builder1
                                .url(AppLogonData.getLogon_Host())
                                .post(formBody)
                                .header("Accept-Encoding", "gzip,deflate")
                                .header("Accept-Language","zh-Hans-CN,zh-Hans;q=0.5")
                                .header("Accept","text/html, application/xhtml+xml, image/jxr, */*")
                                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                                .header("Pragma","no-cache")
                                .header("Referer","http://222.24.62.120/")
                                .header("Cookie",AppLogonData.getCookies())
                                .build();
                        Call logon_call = AppLogonData.getOkHttpClient().newCall(request1);
                        logon_call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                                Snackbar.make(logon_button,"对不起网络连接出错",Snackbar.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String name = ParseDataFromHtml.getName(response.body().string());
                                if(name==null)
                                {
                                    waitProgress(10,CHANGE_PROGRESSBAR);
                                    Snackbar.make(logon_button,"登录失败，请检查用户名密码验证码",Snackbar.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    final Student student = new Student();
                                    student.setName(name);
                                    student.setXh(user_id.getText().toString());
                                    AppLogonData.setStudent(student);
                                    //waitProgress(10,LOGON_SUCCESS);
                                    Request.Builder infoBuilder = new Request.Builder();
                                    infoBuilder.url("http://222.24.62.120/lw_xsxx.aspx?xh=" + user_id.getText().toString() + "&xm=" + name + "&gnmkdm=N121902")
                                                .header("Accept-Encoding", "gzip,deflate")
                                                .header("Accept-Language","zh-Hans-CN,zh-Hans;q=0.5")
                                                .header("Accept","text/html, application/xhtml+xml, image/jxr, */*")
                                                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                                                .header("Pragma","no-cache")
                                                .header("Referer","http://222.24.62.120/")
                                                .header("Cookie",AppLogonData.getCookies());
                                    Request request2 = infoBuilder.build();
                                    Call infocall = AppLogonData.getOkHttpClient().newCall(request2);
                                    infocall.enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            Snackbar.make(logon_button,"对不起获取个人信息失败",Snackbar.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            Map<String,String> studentinfo = ParseDataFromHtml.getPersonalInfomation(response.body().string());
                                            Student student1 = AppLogonData.getStudent();
                                            student1.setZy(studentinfo.get("zy"));
                                            student1.setXy(studentinfo.get("xy"));
                                            student1.setClasses(studentinfo.get("xzb"));
                                            waitProgress(300,LOGON_SUCCESS);
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
                waitProgress(1500,CHANGE_PROGRESSBAR);
            }
        });
    }

    public void waitProgress(final int s,final int method)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(s);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = method;
                handler.sendMessage(message);
            }
        }).start();
    }
}
