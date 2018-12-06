package com.dragonx.clearwhatsapp.home;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dragonx.clearwhatsapp.R;
import com.dragonx.clearwhatsapp.line.CleanLineActivity_;
import com.dragonx.clearwhatsapp.whatsapp.CleanWhatsAppActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * @author HaoLeK
 * Created on 05/12/2018
 */
@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @AfterViews
    void afterViews() {
        setSupportActionBar(toolbar);
    }

    @Click(R.id.btnCleanWhatsApp)
    void clickCleanWhatsApp() {
        CleanWhatsAppActivity_.intent(HomeActivity.this).start();
    }

    @Click(R.id.btnCleanLine)
    void clickCleanLine() {
        CleanLineActivity_.intent(HomeActivity.this).start();
    }
}
