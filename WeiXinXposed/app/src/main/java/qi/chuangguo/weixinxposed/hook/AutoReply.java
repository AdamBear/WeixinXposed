package qi.chuangguo.weixinxposed.hook;

import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import qi.chuangguo.weixinxposed.util.HookClass;

/**
 * Created by chuangguo.qi on 2018/4/26.
 */

public class AutoReply {
    private static AutoReply autoReply;
    private static Object thisObject;
    private String TAG="AutoReply";
    public static AutoReply getInstance() {
        if (autoReply==null){
            autoReply = new AutoReply();
        }
        return autoReply;
    }

    public void hook(final XC_LoadPackage.LoadPackageParam loadPackageParam){

        XposedBridge.hookAllConstructors(HookClass.autoReplyConstructorsclasses, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                thisObject=param.thisObject;
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod(HookClass.autoReplyNotificationClass,HookClass.autoReplyNotificationMath,new Object[]{HookClass.storageClass.getName().toString(),new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                String str = (String) XposedHelpers.getObjectField(param.args[0], "field_content");
                String str2 = (String) XposedHelpers.getObjectField(param.args[0], "field_talker");
                Log.i(TAG, "afterHookedMethod: str:"+str+"::str2:"+str2);
                if (!str2.contains("@chatroom") && !str2.startsWith("gh_")) {
                    str = "你好";
                    if (!str.equals("")) {
                       boolean fz= (boolean) XposedHelpers.callMethod(thisObject, HookClass.autoReplyConstructorsMethod, new Object[]{str});
                        Log.i(TAG, "afterHookedMethod: fz"+fz);
                        return;
                    }
                    return;
                }

            }
        }});


    }
}
