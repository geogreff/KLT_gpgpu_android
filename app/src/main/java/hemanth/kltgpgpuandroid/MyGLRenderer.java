package hemanth.kltgpgpuandroid;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Message;
import android.view.SurfaceHolder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by hemanth on 8/29/16.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
    private Context mctx;
    //private int screenWidth, screenHeight;

    public MyGLRenderer(Context ctx){
        mctx = ctx;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        AppHelperFuncs.myLOGD("onSurfaceCreated()");
        //not using opengl3+ even if its available
        boolean isGL3InitSuccess = JNICaller.setupGLES3Native();
        if(isGL3InitSuccess){
            AppHelperFuncs.myLOGD("OpenGL3.0 context has got created!...");
        }
        else{
            AppHelperFuncs.myLOGD("OpenGL3.0 context creation failed!...");
        }

//        JNICaller.loadResourcesNative(0, 0, AppHelperFuncs.getScreenWidth(), AppHelperFuncs.getScreenHeight());
        if(!AppHelperFuncs.is_standalone_test)
            JNICaller.loadResourcesNative(0, 0, 640, 360);
        Message loadresourcesmessage =
                AppHelperFuncs.getUIHandler().obtainMessage(AppHelperFuncs.UI_HANDLER_STATES.LOAD_RESOURCES_DONE
                        .ordinal());
        loadresourcesmessage.sendToTarget();

    }

    public void onDrawFrame(GL10 unused) {
        if(AppHelperFuncs.is_standalone_test)
            JNICaller.standaloneTestNative();
        else
            JNICaller.drawFrameNative();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        AppHelperFuncs.myLOGD("onSurfaceChanged() " + width + "x" + height);
    }

}
