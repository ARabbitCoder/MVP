package com.liu.mvp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liu.com.liu.viewfactory.AnimationFactory;
import com.liu.com.liu.viewfactory.ViewFactory;
import com.liu.utils.DisplayManager;
import com.liu.utils.PicassoCircleTranform;
import com.liu.viewinterface.MainActivityImp;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements MainActivityImp{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private LinearLayout main_root;
    private ImageView left_head_imageview;
    private ImageView left_animation_background;
    private static String testurl = "http://www.sznews.com/photo/images/attachement/jpg/site3/20150316/4437e6297838167069b219.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayManager.GetInstance().init(this);
        initMainView();
    }

    /**
     * 设置应用沉浸式 setContentView()之前调用
     */
    private void setStateBar() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }


    /**
     * 初始化view
     */
    private void initMainView() {
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_root);
        setDrawerLayoutPullSize(this,drawerLayout,0.3f);

        //main_root = (LinearLayout) findViewById(R.id.main_root);
        //View view = ViewFactory.createView(this,-1,DisplayManager.GetInstance().getStatusBarHeight(),null,1,Color.parseColor("#ff00ddff"));
        //main_root.addView(view,0);

        navigationView = (NavigationView) findViewById(R.id.drawer_left_view);
        initNavigationView(navigationView);

        initListener();
    }

    /**
     * 初始化 NavigationView
     * @param navigationView
     */
    private void initNavigationView(NavigationView navigationView) {
        int imageViewWidth = DisplayManager.GetInstance().Dp2Px(680);  //680 约等于 200÷0.3（平移动画百分比）避免平移过多
        left_animation_background = ViewFactory.createImageView(this,-1,imageViewWidth,null,3,R.drawable.drawer_background);
        navigationView.addView(left_animation_background,0);
        int viewWidth = DisplayManager.GetInstance().Dp2Px(200);//根据屏幕计算topmargin
        View view = ViewFactory.createView(this,-1,-1,new int[]{0,viewWidth,0,0},3);
        navigationView.addView(view,1);
        left_head_imageview = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.left_header_image);
        //获取图片数据添加至左面头像
        Picasso.with(this).load(testurl).transform(new PicassoCircleTranform()).into(left_head_imageview);
        //添加菜单选项
        Menu menu = navigationView.getMenu();
        MenuItem item = menu.add("new");
        item.setCheckable(true);
        item.setIcon(R.drawable.ic_file_download_black_24dp);



    }

    private void initListener() {
        //拖动监听
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                /*if(left_animation_background != null) {
                    left_animation_background.clearAnimation();
                }*/
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if(left_animation_background != null) {
                    left_animation_background.clearAnimation();
                    left_animation_background.startAnimation(AnimationFactory.createTranslateAnimation());
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if(left_animation_background != null) {
                    left_animation_background.clearAnimation();
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        //菜单点击监听
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }
    /**
     * 设置drawerlayout的拉取宽度
     * @param activity
     * @param drawerLayout
     * @param displayWidthPercentage 拉取drawer的边界值百分比
     */
    private void setDrawerLayoutPullSize(Activity activity,DrawerLayout drawerLayout,float displayWidthPercentage) {
        if (activity == null || drawerLayout == null)
            return;
        try {
            // find ViewDragHelper and set it accessible
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField(
                    "mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField
                    .get(drawerLayout);
            // find edgesize and set is accessible
            Field edgeSizeField = leftDragger.getClass().getDeclaredField(
                    "mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            // set new edgesize
            // Point displaySize = new Point();
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize,
                    (int) (dm.widthPixels * displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalArgumentException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        }
    }


    @Override
    public void openDrawer() {

    }
}
