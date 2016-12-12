package com.smarttop.library.utils;

import android.os.Handler;
import android.os.Message;


public abstract class TransactionHandler extends Handler {

	/**
	 * 系统Handler回调接口, 将系统消息根据what区分成普通消息和错误系统.转给handlerError与handlerMessage方法
	 * 
	 * @param msg
	 *            ： 消息对象
	 * 
	 */
	public void handleMessage(Message msg) {
		if (msg.what >= 0) {
			handleMessage(msg.what, msg.arg1, msg.arg2, msg.obj);
		} else {
			handleError(-msg.what, msg.arg1, msg.arg2, msg.obj);
		}
	}

	/**
	 * 失败消息处理
	 * 
	 * @param errCode
	 *            错误码
	 * @param obj
	 *            Obj型参数
	 */
	public abstract void handleError(int errCode, int taskId, int reponseState,
			Object obj);

	/**
	 * 
	 * @param msgCode
	 *            消息码
	 * @param taskId
	 *            任务id
	 * @param reponseState
	 *            状态码 0 为成功
	 * @param jsonStr
	 *            返回的json串
	 */
	public abstract void handleMessage(int msgCode, int taskId,
			int reponseState, Object jsonStr);

	public void onTransactionMessage(int code, int arg1, int arg2, Object arg3) {
		Message msg = Message.obtain(this, code, arg1, arg2, arg3);
		// handleMessage(msg);
		// dispatchMessage(msg);
		this.sendMessage(msg);
	}

	public void onTransactionError(int errCode, int arg1, int arg2, Object arg3) {
		Message msg = Message.obtain(this, errCode, arg1, arg2, arg3);
		// handleMessage(msg);
		// dispatchMessage(msg);
		this.sendMessage(msg);
	}

}
