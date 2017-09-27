package news.chen.yu.ionic;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.apache.cordova.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class UShare extends CordovaPlugin {
    static Vector<SHARE_MEDIA> mediaList = new Vector<SHARE_MEDIA>();
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        UMShareAPI.get(cordova.getActivity().getApplication());
        Config.DEBUG = true;
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.SET_DEBUG_APP,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.WRITE_APN_SETTINGS
            };
            ActivityCompat.requestPermissions(cordova.getActivity(), mPermissionList, 123);
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("share")) {
            String param = args.getString(0);
            this.share(param, callbackContext);
            callbackContext.success();
            return true;
        }

        return false;
    }

    private void share(String param, final CallbackContext callbackContext) {
        try {
            JSONObject options = new JSONObject(param);
            String url = options.getString("url");
            String image = options.getString("image");
            String title = options.getString("title");
            String desc = options.getString("desc");
            LOG.d("u-share", url);
            LOG.d("u-share", image);
            LOG.d("u-share", title);
            LOG.d("u-share", desc);
            UMWeb web = new UMWeb(url);
            web.setTitle(title);
            web.setThumb(new UMImage(cordova.getActivity(), image));
            web.setDescription(desc);
            SHARE_MEDIA[] list = new SHARE_MEDIA[mediaList.size()];
            mediaList.toArray(list);
            new ShareAction(cordova.getActivity())
                    .setDisplayList(list)
                    .withMedia(web)
                    .setCallback(new UMShareListener() {
                        /**
                         * @descrption 分享开始的回调
                         * @param platform 平台类型
                         */
                        @Override
                        public void onStart(SHARE_MEDIA platform) {

                        }

                        /**
                         * @descrption 分享成功的回调
                         * @param platform 平台类型
                         */
                        @Override
                        public void onResult(SHARE_MEDIA platform) {
                            callbackContext.success(1);
                        }

                        /**
                         * @descrption 分享失败的回调
                         * @param platform 平台类型
                         * @param t 错误原因
                         */
                        @Override
                        public void onError(SHARE_MEDIA platform, Throwable t) {
                            LOG.d("u-share", t.getLocalizedMessage());
                            callbackContext.error(t.getLocalizedMessage());
                        }

                        /**
                         * @descrption 分享取消的回调
                         * @param platform 平台类型
                         */
                        @Override
                        public void onCancel(SHARE_MEDIA platform) {
                            callbackContext.success(0);
                        }
                    }).open();
        } catch (Exception e) {
            callbackContext.error(e.toString());
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(cordova.getActivity()).onActivityResult(requestCode,resultCode,data);
    }
}