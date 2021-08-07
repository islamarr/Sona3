/*
 * Last modified 8/7/21 9:16 PM
 */

package com.ihsan.sona3

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber


abstract class BaseFragment<T : ViewBinding> : Fragment() {

    //Disposable for RxCalls
    private lateinit var compositeDisposable: CompositeDisposable

    //Binding View
    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    //this will be used in child classes
    protected val binding: T
        get() = _binding as T


    lateinit var mDialog: Dialog
    fun showProgressDialog(context: Context) {
        mDialog = Dialog(context)
        mDialog.setContentView(R.layout.fragment_base)
        mDialog.show()
    }

    fun hideProgressDialog() {
        mDialog.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(layoutInflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnViewCreated(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

        //Return true if it is Disposed
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
        Timber.d("onFragmentDestroyed ${compositeDisposable.isDisposed}")
    }


    //Implemented into the child class
    abstract fun setupOnViewCreated(view: View)

    @Synchronized
    protected fun addDisposed(disposable: Disposable? = null) {
        Timber.d("Disposed Called ${disposable?.isDisposed}")

        if (disposable == null) return
        compositeDisposable.add(disposable)
    }
}