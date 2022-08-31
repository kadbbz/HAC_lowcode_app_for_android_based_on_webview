package com.huozige.lab.container;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import java.util.Locale;


public class HzgWebChromeClient extends WebChromeClient {

    String _title;
    MainActivity _activity;

    HzgWebChromeClient(MainActivity activity){
        _activity = activity;
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress < 100){
            _title = _activity.getString(R.string.ui_title_progressing,newProgress);
            _activity.setTitle(_title);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if(title.toLowerCase().startsWith("http://") || title.toLowerCase().startsWith("https://") ){
            _activity.setTitle(_activity.getString(R.string.ui_title_loading));
        }else{
            _title = title;
            _activity.setTitle(title);
        }
    }

    @Override
    public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
        new AlertDialog.Builder(_activity).setTitle(_title)
                .setMessage(message)
                .setPositiveButton(_activity.getString(R.string.ui_button_ok), (dialogInterface, i) -> result.confirm())
                .setCancelable(false)
                .show();
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView webView, String url, String message, JsResult result) {
        new AlertDialog.Builder(_activity).setTitle(_title)
                .setMessage(message)
                .setPositiveButton(_activity.getString(R.string.ui_button_ok), (dialogInterface, i) -> result.confirm())
                .setNegativeButton(_activity.getString(R.string.ui_button_cancel), (dialogInterface, i) -> result.cancel())
                .setCancelable(false)
                .show();
        return true;
    }

    @Override
    public boolean onJsBeforeUnload(WebView webView, String url, String message, JsResult result) {
        new AlertDialog.Builder(_activity).setTitle(_title)
                .setMessage(message)
                .setPositiveButton(_activity.getString(R.string.ui_button_ok), (dialogInterface, i) -> result.confirm())
                .setNegativeButton(_activity.getString(R.string.ui_button_cancel), (dialogInterface, i) -> result.cancel())
                .setCancelable(false)
                .show();
        return true;
    }

    @Override
    public boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult result) {
        final EditText input = new EditText(_activity);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        new AlertDialog.Builder(_activity).setTitle(_title)
                .setMessage(message)
                .setView(input)
                .setPositiveButton(_activity.getString(R.string.ui_button_ok), (dialogInterface, i) -> result.confirm(input.getText().toString()))
                .setCancelable(false)
                .show();
        return true;
    }
}
