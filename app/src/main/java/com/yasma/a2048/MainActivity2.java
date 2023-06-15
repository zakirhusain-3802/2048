package com.yasma.a2048;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity2 extends AppCompatActivity {

    public int a;
    public int b;
    private AdView mAdView;
    public int best;
    public String s,gscore;
    public TextView score,bestscore;
    public ImageButton restart;
    public SharedPreferences sharedPreferences,sh;
    public boolean gamefinish,gameover;
    public WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main2);
        gamefinish=false;
        gameover=false;

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-6065082492952635/1835377845");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

         mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        sh=getSharedPreferences("MySharedPref", MODE_PRIVATE);
        gscore=sh.getString("best","0");
        best=Integer.parseInt(gscore);
        System.out.println(best);

        score=findViewById(R.id.textView1);
        bestscore=findViewById(R.id.textView2);
        bestscore.setText(gscore);

// Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();




        restart=findViewById(R.id.restart);

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/index.html");

        webView.addJavascriptInterface(new WebViewJavaScriptInterface(this), "app");



        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartfun(webView);


            }
        });



    }

    private void restartfun(WebView webView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to Restart ?");

        // Set Alert Title
        builder.setTitle("Conformation !");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
            webView.reload();
            score.setText("0");
            gamefinish=false;
            gameover=false;
        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }

    public void onBackPressed() {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to exit ?");

        // Set Alert Title
        builder.setTitle("Conformation !");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
            finish();
        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }


    public class WebViewJavaScriptInterface{

            private Context context;

            /*
             * Need a reference to the context in order to sent a post message
             */
            public WebViewJavaScriptInterface(Context context){
                this.context = context;
            }

            /*
             * This method can be called from Android. @JavascriptInterface
             * required after SDK version 17.
             */
            @JavascriptInterface
            public void makeToast(String scores){
                s=scores;
                score.setText(s.toString());
                a=Integer.parseInt(s);

                if(best<a){


                   storedata();
                }




            }
            @JavascriptInterface
            public void gameover(){
                if(gameover==false){
                gameover=true;
                    Dialog dialog1 = new Dialog(MainActivity2.this);
                    dialog1.setContentView(R.layout.game_finish);
                    dialog1.setCancelable(false);
                    dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog1.show();

                    Dialog dialog = new Dialog(MainActivity2.this);
                    dialog.setContentView(R.layout.game_over);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                    layoutParams.gravity = Gravity.CENTER;



                    TextView okay_text = dialog.findViewById(R.id.okay_text);
                     TextView cancel_text = dialog.findViewById(R.id.cancel_text);

                    okay_text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



                            palyagain();
                            dialog.dismiss();
                            dialog1.dismiss();


                        }
                    });

                    cancel_text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                            dialog.dismiss();
                            Intent i = new Intent(MainActivity2.this,MainActivity.class);
                            startActivity(i);

                        }
                    });
                    dialog.getWindow().setAttributes(layoutParams);
                    dialog.show();
            }}
            @JavascriptInterface
            public void gamefinish(){

                if ( gamefinish==false){


                gamefinish=true;

                    Dialog dialog = new Dialog(MainActivity2.this);
                    dialog.setContentView(R.layout.you_win);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setCancelable(true);
                    dialog.show();
            }
            }

        }

    private void palyagain() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.reload();
                score.setText("0");
                gamefinish=false;
                gameover=false;
            }
        });

    }

    public void storedata() {

        bestscore.setText(s);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("best",s);
        myEdit.commit();
    }




}
