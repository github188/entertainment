package com.BC.androidtool.views;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

/**
 * @author pengyiming
 * @date 2013-9-30
 * @function �Զ���ؼ�
 */
public class GalleryFlow extends Gallery {
	/* ���ݶ�begin */
	private final String TAG = "GalleryFlow";

	// ��ԵͼƬ�����ת�Ƕ�
	private final float MAX_ROTATION_ANGLE = 15;
	// ����ͼƬ���ǰ�þ���
	private final float MAX_TRANSLATE_DISTANCE = 1;
	// ����ͼƬ���ǰ�þ���
	private final float MAX_DISTANCE = 30;
	// GalleryFlow����X����
	private int mGalleryFlowCenterX;
	// 3D�任Camera
	private Camera mCamera = new Camera();

	Timer timer;

	/* ���ݶ�end */

	/* ������begin */

	public GalleryFlow(Context context, AttributeSet attrs) {
		super(context, attrs);

		// �������ڻ��������У��ص�getChildStaticTransformation()
		this.setStaticTransformationsEnabled(true);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			timer.cancel();
			timer = null;
			break;
		case MotionEvent.ACTION_UP:
			beginAutoRun();
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	public void beginAutoRun() {
		timer = new Timer();
		TimerTask autoRunTask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				GalleryFlow.this.onFling(null, null, -GalleryFlow.this.getChildAt(0).getWidth()*3, 0);
				
//				Log.d("shuzhi", "getCenterXOfCoverflow() = "+GalleryFlow.this.getChildAt(0).getWidth()*3);
			}
		};
		timer.schedule(autoRunTask, 5000, 5000);
	}

	/**
	 * @function ��ȡGalleryFlow����X����
	 * @return
	 */
	private int getCenterXOfCoverflow() {
		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2
				+ getPaddingLeft();
	}

	/**
	 * @function ��ȡGalleryFlow��view������X����
	 * @param childView
	 * @return
	 */
	private int getCenterXOfView(View childView) {
		return childView.getLeft() + childView.getWidth() / 2;
	}

	/**
	 * @note step1 ϵͳ����measure()����ʱ���ص��˷�����������ʱϵͳ���ڼ���view�Ĵ�С
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		mGalleryFlowCenterX = getCenterXOfCoverflow();
		Log.d(TAG, "onMeasure, mGalleryFlowCenterX = " + mGalleryFlowCenterX);
	}

	/**
	 * @note step2 ϵͳ����layout()����ʱ���ص��˷�����������ʱϵͳ���ڸ�child view����ռ�
	 * @note �ض���onMeasure()֮��ص�������onSizeChanged()�Ⱥ�˳��һ��
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		mGalleryFlowCenterX = getCenterXOfCoverflow();
		Log.d(TAG, "onLayout, mGalleryFlowCenterX = " + mGalleryFlowCenterX);
	}

	/**
	 * @note step2
	 *       ϵͳ����measure()�����󣬵���Ҫ���ƴ�viewʱ���ص��˷�����������ʱϵͳ�Ѽ���
	 *       ��view�Ĵ�С
	 * @note �ض���onMeasure()֮��ص�������onSizeChanged()�Ⱥ�˳��һ��
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mGalleryFlowCenterX = getCenterXOfCoverflow();
		Log.d(TAG, "onSizeChanged, mGalleryFlowCenterX = "
				+ mGalleryFlowCenterX);
	}

	@Override
	protected boolean getChildStaticTransformation(View childView,
			Transformation t) {
		// ������ת�Ƕ�
		float rotationAngle = calculateRotationAngle(childView);

		// ����ǰ�þ���
		float translateDistance = calculateTranslateDistance(childView);

		// ��ʼ3D�任
		transformChildView(childView, t, rotationAngle, translateDistance);

		return true;
	}

	/**
	 * @function ����GalleryFlow��view����ת�Ƕ�
	 * @note1 λ��Gallery���ĵ�ͼƬ����ת
	 * @note2 λ��Gallery���������ͼƬ���������ĵ�ľ�����ת
	 * @param childView
	 * @return
	 */
	private float calculateRotationAngle(View childView) {
		final int childCenterX = getCenterXOfView(childView);
		float rotationAngle = 0;

		rotationAngle = (mGalleryFlowCenterX - childCenterX)
				/ (float) mGalleryFlowCenterX * MAX_ROTATION_ANGLE;

		if (rotationAngle > MAX_ROTATION_ANGLE) {
			rotationAngle = MAX_ROTATION_ANGLE;
		} else if (rotationAngle < -MAX_ROTATION_ANGLE) {
			rotationAngle = -MAX_ROTATION_ANGLE;
		}

		return rotationAngle;
	}

	/**
	 * @function ����GalleryFlow��view��ǰ�þ���
	 * @note1 λ��Gallery���ĵ�ͼƬǰ��
	 * @note2 λ��Gallery���������ͼƬ��ǰ��
	 * @param childView
	 * @return
	 */
	private float calculateTranslateDistance(View childView) {
		final int childCenterX = getCenterXOfView(childView);
		float translateDistance = 0;

		if (mGalleryFlowCenterX != childCenterX) {
			translateDistance = MAX_TRANSLATE_DISTANCE;
		}

		return translateDistance;
	}

	/**
	 * @function ��ʼ�任GalleryFlow��view
	 * @param childView
	 * @param t
	 * @param rotationAngle
	 * @param translateDistance
	 */
	private void transformChildView(View childView, Transformation t,
			float rotationAngle, float translateDistance) {
		t.clear();
		t.setTransformationType(Transformation.TYPE_MATRIX);

		final Matrix imageMatrix = t.getMatrix();
		final int imageWidth = childView.getWidth();
		final int imageHeight = childView.getHeight();

		mCamera.save();

		/* rotateY */
		// ��Y������ת��λ�����ĵ�ͼƬ����ת�����������ͼƬ������������ⷭת��
		mCamera.rotateY(rotationAngle);
		/* rotateY */

		/* translateZ */
		// ��Z����ǰ�ã�λ�����ĵ�ͼƬ���зŴ��Ч��
		mCamera.translate(0, 0, translateDistance);
		/* translateZ */

		// ��ʼ�任���ҵ�����ǣ��ƶ�Camera����2D��ͼ�ϲ���3DЧ����
		mCamera.getMatrix(imageMatrix);
		imageMatrix.preTranslate(-imageWidth / 2, -imageHeight / 2);
		imageMatrix.postTranslate(imageWidth / 2, imageHeight / 2);

		mCamera.restore();
	}
	/* ������end */
}
