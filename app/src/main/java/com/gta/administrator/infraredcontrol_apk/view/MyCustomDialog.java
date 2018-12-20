package com.gta.administrator.infraredcontrol_apk.view;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.gta.administrator.infraredcontrol_apk.R;


public class MyCustomDialog {
	private Context mContext;
	private Dialog progressDialog;

	public MyCustomDialog(Context mContext) {
		this.mContext = mContext;
	}

	public void buildProgressDialog(int id, String text) {
		if (progressDialog == null) {
			progressDialog = new Dialog(mContext, R.style.progress_dialog);
		}
		progressDialog.setContentView(R.layout.dialog_my);
		progressDialog.setCancelable(false);
		progressDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		TextView msg = (TextView) progressDialog
				.findViewById(R.id.id_tv_loadingmsg);
		msg.setText(text);
		progressDialog.show();
	}


	public void buildProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new Dialog(mContext, R.style.progress_dialog);
		}
		progressDialog.setContentView(R.layout.dialog_my);
		progressDialog.setCancelable(true);
		progressDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		TextView msg = (TextView) progressDialog
				.findViewById(R.id.id_tv_loadingmsg);
		msg.setText("发送中");
		progressDialog.show();
	}

	public void cancelProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

}
