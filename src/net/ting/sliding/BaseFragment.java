package net.ting.sliding;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import xutil.library.ViewUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	protected Activity mActivity;
	protected Object tag;
	protected View mHoseView;
	protected int layoutResourceId;
	protected static String titleName = "冰箱控制";

	protected abstract void getData();

	protected abstract void initView();

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected DisplayImageOptions options;

	protected OnShowMemuListenting onShowMemuListenting;

	public BaseFragment() {
		super();
	}

	protected void ReplaceFragmentMethod(int viewId, Fragment fragment) {
		FragmentTransaction tration = getChildFragmentManager().beginTransaction();
		tration.replace(viewId, fragment);
		tration.commitAllowingStateLoss();
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("InlinedApi")
	public void initBaseSwipeRefreshLayout(BaseSwipeRefreshLayout swipeLayout) {
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
		imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
		options = getOptions(0);
	}

	@SuppressWarnings("deprecation")
	protected DisplayImageOptions getOptions(int cornerRadiusPixels) {
		return new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.defaut_img)
				.showImageForEmptyUri(R.drawable.defaut_img).showImageOnFail(R.drawable.defaut_img).cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true).displayer(new RoundedBitmapDisplayer(cornerRadiusPixels))
				.build();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mHoseView == null) {
			mHoseView = inflater.inflate(layoutResourceId, null);
			ViewUtils.inject(this, mHoseView);
			initView();
			getData();
		}
		// 缓存的mHoseView需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，要不然会发生这个mHoseView已经有parent的错误。
		ViewGroup parent = (ViewGroup) mHoseView.getParent();
		if (parent != null) {
			parent.removeView(mHoseView);
		}
		return mHoseView;
	}

	public OnShowMemuListenting getOnShowMemuListenting() {
		return onShowMemuListenting;
	}

	public void setOnShowMemuListenting(OnShowMemuListenting onShowMemuListenting) {
		this.onShowMemuListenting = onShowMemuListenting;
	}

	public static class SimpleBaseFragment extends BaseFragment {

		@Override
		protected void initView() {
		}

		@Override
		protected void getData() {
			// TODO Auto-generated method stub

		}
	}

	public interface OnShowMemuListenting {
		void onShowMenu(boolean isShow);
	}

}
