package com.example.test24;

import java.io.InputStream;
import java.net.URL;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class W_Random extends Activity implements View.OnClickListener {

	String username;
	String userID;
	String photoURL;
	String mapID;
	String spotID;
	String GETmenberID;
	String GETusername;
	String seibetu;
	String title;
	String mapURL;
	int flg;

	Drawable d;
	Bitmap bm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);

		setContentView(R.layout.w_random);
		Intent intent = getIntent();
		flg = Integer.parseInt(intent.getStringExtra("flg"));
		if(flg == 1){
			username = intent.getStringExtra("username");
			userID = intent.getStringExtra("userID");
		}
		mapID = intent.getStringExtra("mapID");
		spotID = intent.getStringExtra("spotID");
		GETmenberID = intent.getStringExtra("GETmenberID");
		GETusername = intent.getStringExtra("GETusername");
		seibetu = intent.getStringExtra("seibetu");
		title = intent.getStringExtra("title");
		photoURL= intent.getStringExtra("photoURL");
		mapURL = intent.getStringExtra("mapURL");

		TextView tv = (TextView)findViewById(R.id.textRandom);
		tv.setText(GETusername + "さんのマップ");

		TextView w_title = (TextView)findViewById(R.id.w_title);
		w_title.setText(title);

		//StrictModeを設定 penaltyDeathを取り除く
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());

         //TextViewを取得
         final ImageView test = (ImageView)findViewById(R.id.w_photo);
         //画像のURL
         String urlString="http://54.68.202.192/img/" + photoURL;

         try {
             //URLクラス
            URL url = new URL(urlString);
             //入力ストリームを開く
            InputStream istream = url.openStream();

             //画像をDrawableで取得
            d = Drawable.createFromStream(istream, "webimg");

            bm = ((BitmapDrawable) d).getBitmap();

             //入力ストリームを閉じる
            istream.close();

             //TextViewの背景に表示
            test.setImageBitmap(bm);

         } catch (Exception e) {
             System.out.println("nuu: "+e);
         }

//         test.setOnClickListener(new View.OnClickListener() {
//   		  @Override
//   		  public void onClick(View v) {
//   			ImageView imageView = new ImageView(getApplicationContext());
//   		    //ImageView imageView = new ImageView(W_Random.this);
//   		    imageView.setImageBitmap(bm);
//   		    // ディスプレイの幅を取得する（API 13以上）
//   		    Display display =  getWindowManager().getDefaultDisplay();
//   		    Point size = new Point();
//   		    display.getSize(size);
//   		    int width = size.x;
//
//   		    float factor =  width / bm.getWidth();
//   		    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//   		    // ダイアログを作成する
//   		    Dialog dialog = new Dialog(W_Random.this);
//   		    // タイトルを非表示にする
//   		    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//   		    dialog.setContentView(imageView);
//   		    dialog.getWindow().setLayout((int)(bm.getWidth()*factor), (int)(bm.getHeight()*factor));
//   		    // ダイアログを表示する
//   		    dialog.show();
//   		  }
//   		});

	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		ImageView back = (ImageView)findViewById(R.id.backbtn);
		back.setOnClickListener(this);
		ImageView watchmap = (ImageView)findViewById(R.id.watchMap);
		watchmap.setOnClickListener(this);
		ImageView w_photo = (ImageView)findViewById(R.id.w_photo);
		w_photo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		Intent intent = null;

		switch(v.getId()){
		case R.id.watchMap:

			intent = new Intent(W_Random.this,Wmap.class);

			if(flg == 1){
		      intent.putExtra("username", username);
		      intent.putExtra("userID", userID);
		      intent.putExtra("flg", "1");
			}else{
				intent.putExtra("flg", "0");
			}
		      intent.putExtra("mapURL",mapURL);
		      startActivity(intent);


			break;

		case R.id.backbtn:
			intent = new Intent(W_Random.this,W_Select.class);
			if(flg == 1){
			      intent.putExtra("username", username);
			      intent.putExtra("userID", userID);
			      intent.putExtra("flg", "1");
			}else{
				intent.putExtra("flg", "0");
			}
			startActivity(intent);
			break;

		case R.id.w_photo:
			intent = new Intent(W_Random.this,W_photo.class);
			if(flg == 1){
			      intent.putExtra("username", username);
			      intent.putExtra("userID", userID);
			      intent.putExtra("flg", "1");
			}else{
				intent.putExtra("flg", "0");
			}
			intent.putExtra("photoURL", photoURL);
			startActivity(intent);
			break;

		}
	}

}