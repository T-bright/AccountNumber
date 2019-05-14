package com.account.number.base

import android.content.Context
import android.os.Bundle
import android.util.SparseArray
import android.view.*
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.account.number.MyApplication
import com.account.number.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


abstract class BaseDialog : DialogFragment(), HasSupportFragmentInjector, BaseView {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return childFragmentInjector
    }

    protected open fun inject() {
        AndroidSupportInjection.inject(this)
    }

    protected var mLayoutResId: Int = -1
    private var mDimAmount = 0.5f//背景昏暗度
    private var mShowBottomEnable = false//是否底部显示
    private var mMargin = 15//左右边距
    private var mAnimStyle = 0//进入退出动画
    private var mOutCancel = true//点击外部取消
    private var mContext: Context? = null
    private var mWidth = 0
    private var mHeight = 0
    private var mView: View? = null
    override fun onAttach(context: Context?) {
        inject()
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BaseDialog)
        mLayoutResId = setUpLayoutId()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = super.onCreateView(inflater, container, savedInstanceState)
        if (mView == null) {
            mView = inflater.inflate(mLayoutResId, container, false)
            creatingView(mView!!)
        }
        return mView
    }

    /**
     * 设置dialog布局
     *
     * @return
     */
    protected abstract fun setUpLayoutId(): Int

    /**
     * 操作dialog布局
     *
     * @param holder
     * @param dialog
     */
    protected abstract fun creatingView(view: View)

    protected abstract fun initData()
    override fun onStart() {
        super.onStart()
        initDialog()
        initData()
    }



    private fun initDialog() {
        dialog.window?.let {
            var params = it.attributes
            params.dimAmount = mDimAmount

            if (mShowBottomEnable) params.gravity = Gravity.BOTTOM

            if (mWidth == 0) {
                params.width = getScreenWidth(getMContext()) - 2 * dp2px(getMContext(), mMargin.toFloat())
            } else {
                params.width = dp2px(getMContext(), mWidth.toFloat())
            }

            if (mHeight == 0) {
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                params.height = dp2px(
                    getMContext(), mHeight.toFloat()
                )
            }
            if (mAnimStyle != 0) it.setWindowAnimations(mAnimStyle)

            it.attributes = params
        }
        isCancelable = mOutCancel
    }

    private fun getMContext(): Context {
        return MyApplication.instance!!.applicationContext
    }

    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }


    fun dp2px(context: Context, dipValue: Float): Int {
        var scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * 设置背景昏暗度
     *
     * @param dimAmount
     * @return
     */
    fun setDimAmout(dimAmount: Float): BaseDialog {
        mDimAmount = dimAmount
        return this
    }

    /**
     * 是否显示底部
     *
     * @param showBottom
     * @return
     */
    fun setShowBottom(showBottom: Boolean): BaseDialog {
        mShowBottomEnable = showBottom
        return this
    }

    /**
     * 设置宽高
     *
     * @param width
     * @param height
     * @return
     */
    fun setSize(width: Int, height: Int): BaseDialog {
        mWidth = width
        mHeight = height
        return this
    }

    /**
     * 设置左右margin
     *
     * @param margin
     * @return
     */
    fun setMargin(margin: Int): BaseDialog {
        mMargin = margin
        return this
    }

    /**
     * 设置进入退出动画
     *
     * @param animStyle
     * @return
     */
    fun setAnimStyle(@StyleRes animStyle: Int): BaseDialog {
        mAnimStyle = animStyle
        return this
    }

    /**
     * 设置是否点击外部取消
     *
     * @param outCancel
     * @return
     */
    fun setOutCancel(outCancel: Boolean): BaseDialog {
        mOutCancel = outCancel
        return this
    }

    override fun showLoading() {
        showLoading("玩命加载中...")
    }

    fun showLoading(text: String) {
        (activity as BaseActivity)!!.showLoading(text)
    }


    override fun hideLoading() {
        (activity as BaseActivity)!!.hideLoading()
    }

    override fun showError(status: String) {
        (activity as BaseActivity)!!.hideLoading()
    }



}