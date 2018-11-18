package com.example.esraa.movieapp.presenter;

import android.app.Activity;
import android.app.ProgressDialog;

import com.example.esraa.movieapp.R;

class BasePresenter implements IBasePresenter {
    protected ProgressDialog progressDialog;
    private Activity activity;

    BasePresenter(Activity activity) {
        this.activity = activity;
        initProgressDialog();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(activity.getString(R.string.dialog_loading_message));
        progressDialog.setMax(100);
    }
}
