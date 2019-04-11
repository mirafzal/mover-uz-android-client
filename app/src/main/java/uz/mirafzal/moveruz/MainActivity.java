package uz.mirafzal.moveruz;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity{

    private WebView webview1;
    private String myurl;

    private Intent intent = new Intent();
    private AlertDialog.Builder dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        initializeLogic();
    }

    private void initialize() {
        webview1 = findViewById(R.id.webView);
        webview1.getSettings().setJavaScriptEnabled(true);
        webview1.getSettings().setSupportZoom(true);
        webview1.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView _view, final String _url, Bitmap _favicon) {

                super.onPageStarted(_view, _url, _favicon);
            }

            @Override
            public void onPageFinished(WebView _view, final String _url) {

                super.onPageFinished(_view, _url);
            }
        });

        dialog = new AlertDialog.Builder(this);
    }

    @Override
    public void onBackPressed()
    {
        if (webview1.canGoBack()) {
            webview1.goBack();
        } else {
            dialog.setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Выход!").setMessage("Вы действительно хотите выйти?")
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("Нет", null).show();
        }
    }

    private void initializeLogic() {
        webview1.loadUrl("https://mover.uz");

        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("watch")) {
                    if      (url.length() == 32) myurl = url.substring(23, 31);
                    else if (url.length() == 31) myurl = url.substring(23, 30);
                    myurl = "https://v.mover.uz/" + myurl + "_m.mp4";
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(myurl));
                    startActivity(intent);
                    view.loadUrl(url);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
//
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        };
        webview1.setWebViewClient(webViewClient);
    }

}
