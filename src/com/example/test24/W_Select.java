package com.example.test24;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class W_Select extends Activity implements View.OnClickListener ,Runnable{

	private LocationManager mLocationManager;
	private JSONArray rootObjectArray;
	CheckBox ten;
	CheckBox twenty;
	CheckBox thirty;
	CheckBox forty;
	CheckBox fifty;
	CheckBox sixty;
	String sei = null;
	String seibetu = null;

	String set_username;
	String set_userID;
	int flg;
	ProgressDialog dialog;
	private Thread thread;

	ImageView next;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_select);

		Intent intent = getIntent();
		flg = Integer.parseInt(intent.getStringExtra("flg"));
		if(flg == 1){
			set_username = intent.getStringExtra("username");
			set_userID = intent.getStringExtra("userID");
			Log.d("もらったよ",set_username);
		}
		Log.d("遷移","遷移しました。");

		// ラジオグループ
				final RadioGroup rgSelect = (RadioGroup)findViewById(R.id.radiogroup_id);
				rgSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					// ラジオボタンが変更されると何番が選択されたか表示する
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
							case R.id.manbtn:
								sei = "0";
								break;

							case R.id.womanbtn:
								sei = "1";
								break;
						}
					}
				});

				// ラジオグループ
				final RadioGroup seiSelect = (RadioGroup)findViewById(R.id.radioGroupyear);
				seiSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					// ラジオボタンが変更されると何番が選択されたか表示する
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
							case R.id.Ten:
								seibetu = "10";
								break;

							case R.id.Twenty:
								seibetu = "20";
								break;

							case R.id.Thirty:
								seibetu = "30";
								break;

							case R.id.Forty:
								seibetu = "40";
								break;

							case R.id.Fifty:
								seibetu = "50";
								break;

							case R.id.Sixty:
								seibetu = "60";
								break;
						}
					}
				});

	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		next = (ImageView)findViewById(R.id.next);
		next.setOnClickListener(this);
		next.setImageResource(R.drawable.next);
	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ

		switch(v.getId()){

			case R.id.next:
				next.setImageResource(R.drawable.next_sya);
				if(sei != null && seibetu != null){

					setWait();

				}else{
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
					alertDialogBuilder.setMessage("性別と年齢を選択してください。")

					 .setPositiveButton("OK", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int id) {
								// TODO 自動生成されたメソッド・スタブ
								next.setImageResource(R.drawable.next);
							}
				});

					AlertDialog alert = alertDialogBuilder.create();
					alert.show();
				}
			break;
		}
	}

	 private void setWait() {
		// TODO 自動生成されたメソッド・スタブ
		 dialog = new ProgressDialog(this);
		    dialog.setTitle("Please wait");
		    dialog.setMessage("loading...");
		    dialog.show();

		    thread = new Thread(W_Select.this);
		    /* show()メソッドでプログレスダイアログを表示しつつ、
		     * 別スレッドを使い、裏で重い処理を行う。
		     */
		    thread.start();
	}

	private void exec_post() {

		    Log.d("posttest", "postします");

		    HashMap<String,Object> ret = null;

		    // URL
		    URI url = null;
		    try {
		      url = new URI( "http://54.68.202.192/getmap.php" );
		      Log.d("posttest", "URLはOK");
		    } catch (URISyntaxException e) {
		      e.printStackTrace();
		      //String code =toString(ret.getStatusLine().getStatusCode());
		      //ret = e.toString();
		    }

		    // POSTパラメータ付きでPOSTリクエストを構築
		    HttpPost request = new HttpPost( url );

		    /*
		    List<NameValuePair> post_params\e = new ArrayList<NameValuePair>();
		    post_params.add(new BasicNameValuePair("post_1", "ユーザID"));
		    post_params.add(new BasicNameValuePair("post_2", "パスワード"));
		    */


		    HashMap<String, Object> hashMap = new HashMap<String, Object>();
		    hashMap.put("birthyear",seibetu );
		    hashMap.put("seibetu",sei );
		    Log.d("birthyear",seibetu);
		    Log.d("seibetu",sei);


		    //オブジェクトクラスHashMap　キーワードと値をペアでセット

		    try {
			    request.setHeader("Content-Type", "application/json; charset=utf-8");
			    //
			    Type mapType = new TypeToken<HashMap<String, Object>>() {}.getType();
			    //HashMapをJSONに変換
			    request.setEntity(new StringEntity(new Gson().toJson(hashMap, mapType)));
			    //同上

			    /*
			    // 送信パラメータのエンコードを指定
		        request.setEntity(new UrlEncodedFormEntity(post_params, "UTF-8"));
		        */

		    } catch (UnsupportedEncodingException e1) {
		        e1.printStackTrace();
		    }

		    // POSTリクエストを実行
		    DefaultHttpClient httpClient = new DefaultHttpClient();
		    try {
		      Log.d("posttest", "POST開始");

		      // POSTを実行して、戻ってきたJSONをHashMapの形にして受け取る
		      ret = httpClient.execute(request, new MyResponseHandler());
		      //


		    } catch (IOException e) {
		      Log.d("posttest", "通信に失敗：" + e.toString());
		    } finally {
		      // shutdownすると通信できなくなる
		      httpClient.getConnectionManager().shutdown();
		    }

		    // 受信結果をUIに表示
	}
	 public class MyResponseHandler implements ResponseHandler<HashMap<String,Object>> {

			@Override
			public HashMap<String,Object> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				// TODO 自動生成されたメソッド・スタブ
				//		          Log.d(
				//		            "posttest",
				//		            "レスポンスコード：" + response.getStatusLine().getStatusCode()

				HashMap<String,Object> retMap = new HashMap<String,Object>();

	            // 正常に受信できた場合は200
		          switch (response.getStatusLine().getStatusCode()) {
			          case HttpStatus.SC_OK:
			            Log.d("posttest", "レスポンス取得に成功");

			            try {
			            		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			            		response.getEntity().writeTo(outputStream);
			            		String data;
			            		data = outputStream.toString(); // JSONデータ
			            		rootObjectArray = new JSONArray(data);

			            		//ループ処理をしたい
			            		JSONObject jsonobject = rootObjectArray.getJSONObject(0);

//			            		//ランダムな数値の取得
//			            		Random rnd = new Random();
//			                    int ran = rnd.nextInt(10);

			            		String mapID = jsonobject.getString("mapID");
			            		String spotID = jsonobject.getString("spotID");
			            		String menberID = jsonobject.getString("menberID");
			            		String username = jsonobject.getString("username");
			            		String seibetu = jsonobject.getString("seibetu");
			            		String Title = jsonobject.getString("Title");
			            		String photoURL = jsonobject.getString("photoURL");
			            		String mapURL = jsonobject.getString("mapURL");

			            		Log.d("mapID",mapID);
			            		Log.d("spotID",spotID);

			            		retMap.put("mapID", mapID);
			            		retMap.put("spotID", spotID);
			            		retMap.put("menberID", menberID);
			            		retMap.put("username", username);
			            		retMap.put("seibetu", seibetu);
			            		retMap.put("Title", Title);
			            		retMap.put("photoURL", photoURL);
			            		retMap.put("mapURL", mapURL);

			            		String GETmapID = (String)retMap.get("mapID");
			            		String GETspotID = (String)retMap.get("spotID");
			            		String GetmenberID = (String)retMap.get("menberID");
			            		String Getusername = (String)retMap.get("username");
			            		String Getseibetu = (String)retMap.get("seibetu");
			            		String Gettitle = (String)retMap.get("Title");
			            		String GetphotoURL = (String)retMap.get("photoURL");
			            		String GetmapURL = (String)retMap.get("mapURL");
			            		Log.d("response","1");


			      		      	Intent intent = null;
			      		      	intent = new Intent(W_Select.this,W_Random.class);
			      		      	intent.putExtra("mapID", GETmapID);
			      		      	intent.putExtra("spotID",GETspotID );
			      		      	intent.putExtra("GETmenberID",GetmenberID );
			      		      	intent.putExtra("GETusername",Getusername );
			      		      	intent.putExtra("seibetu",Getseibetu );
			      		      	intent.putExtra("title",Gettitle );
			      		      	intent.putExtra("photoURL",GetphotoURL );
			      		      	intent.putExtra("mapURL", GetmapURL);


			      		      	if(flg == 1){
			      		      	intent.putExtra("username", set_username);
			      		      	intent.putExtra("userID", set_userID);
			      		      	Log.d("Select_username",set_username);
			      		      	intent.putExtra("flg","1");
			      		      	}else{
			      		      	intent.putExtra("flg","0");
			      		      	}

								startActivity(intent);


			            } catch (Exception e) {
			            	Log.d("Json取得エラー", "Error");
			            	retMap.put("status_code", "220");
			            }

			            break;

			          case HttpStatus.SC_NOT_FOUND:
			            Log.d("posttest", "データが存在しない");
			            retMap.put("status_code", "404");

			            break;

			          default:
			            Log.d("posttest", "通信エラー");
			            retMap.put("status_code", "500");
			            break;
		          }
		          return retMap;

			}
	 }
	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ
		try {
	        //ダイアログがしっかり見えるように少しだけスリープ
	        //（nnn：任意のスリープ時間・ミリ秒単位）
	        Thread.sleep(1000);
	    } catch (InterruptedException e) {
	        //スレッドの割り込み処理を行った場合に発生、catchの実装は割愛
	    }
	    //run内でUIの操作をしてしまうと、例外が発生する為、
	    //Handlerにバトンタッチ
	    handler.sendEmptyMessage(0);
	}
	private Handler handler = new Handler() {
	    public void handleMessage(Message msg){
	        // HandlerクラスではActivityを継承してないため
	        // 別の親クラスのメソッドにて処理を行うようにした。
	    	//JSONの呼び出し
			exec_post();

	        // プログレスダイアログ終了
	        dialog.dismiss();
	        dialog = null;
	    }
	};

}
