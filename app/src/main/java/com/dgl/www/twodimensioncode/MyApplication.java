package com.dgl.www.twodimensioncode;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import com.dgl.www.twodimensioncode.utils.LogUtil;
import com.xiaomi.ad.AdSdk;

import org.litepal.LitePalApplication;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500Principal;

/**
 * @author hzzhengrui
 * @Date 16/10/27
 * @Description
 */
public class MyApplication extends Application {

    public static MyApplication instance;
    private static String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //debg版本，不加密和有log
        if (isDebuggable(getApplicationContext())) {
            LogUtil.debug = true;
            LogUtil.showLog(TAG,"LogUtil.debug = true");
        } else {
            //release本,没有log
            LogUtil.debug = false;
        }

        LitePalApplication.initialize(this);
        //配置小米广告的sdk
        AdSdk.initialize(this, "2882303761517587904");
        AdSdk.setDebugOn(); // 打开调试，输出调试信息
        AdSdk.setMockOn();  // 调试时打开，正式发布时关闭

    }

    //全局实例
    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    //判断是否是debug版本，用来数据库加密和log自动判断,true表示debug版本，false表示release版本
    public boolean isDebuggable(Context ctx) {
        boolean debuggable = false;
        try {
            PackageInfo pinfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature signatures[] = pinfo.signatures;
            for (int i = 0; i < signatures.length; i++) {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream stream = new ByteArrayInputStream(signatures[i].toByteArray());
                X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);
                // 判断是否含有debug默认的签名信息
//                debuggable = cert.getSubjectX500Principal().equals(DEBUG_DN);
                debuggable = isX500PrincipalEqual(cert.getSubjectX500Principal());
                if (debuggable) {
                    break;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return debuggable;
    }

    private final boolean isX500PrincipalEqual(X500Principal x500){
        if (x500 == null || x500.getClass() != DEBUG_DN.getClass()) {
            return false;
        }
        String canonical = x500.getName(X500Principal.CANONICAL);
        String[] values = canonical.split(",");

        String debugCanonical = DEBUG_DN.getName(X500Principal.CANONICAL);
        String[] debugValues = debugCanonical.split(",");

        int count = 0;
        for(String s:values){
            for(String v:debugValues){
                if(s.equals(v)){
                    count++;
                }
            }
        }
        if(count==debugValues.length){
            return true;
        }
        return false;
    }
    //debug默认签名中含有的信息
    private final static X500Principal DEBUG_DN = new X500Principal("CN=Android Debug,O=Android,C=US");
    //位于栈顶的activity
//    public String getRunningActivityName() {
//        android.app.ActivityManager mActivityManager = (android.app.ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        return mActivityManager.getRunningTasks(1).get(0).topActivity.getClassName();
//    }

}
