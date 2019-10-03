/*
 * Beedio is an Android app for downloading videos
 * Copyright (C) 2019 Loremar Marabillas
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package marabillas.loremar.beedio.browser.viewmodel

import android.webkit.WebView
import androidx.databinding.Bindable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import marabillas.loremar.beedio.base.mvvm.ObservableViewModel

abstract class BrowserTitleStateVM : ObservableViewModel() {
    abstract var title: String?
    abstract var url: String?
    abstract fun observeTitle(lifecycleOwner: LifecycleOwner, observer: Observer<String?>)
    abstract fun observeUrl(lifecycleOwner: LifecycleOwner, observer: Observer<String?>)
}

abstract class WebPageNavigationVM : ObservableViewModel() {
    abstract fun goBack()
    abstract fun goForward()
    abstract fun reloadPage()
    abstract fun observeGoBack(lifecycleOwner: LifecycleOwner, observer: Observer<Any>)
    abstract fun observeGoForward(lifecycleOwner: LifecycleOwner, observer: Observer<Any>)
    abstract fun observeReloadPage(lifecycleOwner: LifecycleOwner, observer: Observer<Any>)
}

abstract class WebViewsControllerVM : ObservableViewModel() {
    abstract fun requestUpdatedWebViews(callback: (List<WebView>, Int) -> Unit)
    abstract fun observeRequestUpdatedWebViews(lifecycleOwner: LifecycleOwner,
                                               observer: Observer<(List<WebView>, Int) -> Unit>)

    abstract fun requestActiveWebView(callback: (WebView?) -> Unit)
    abstract fun observeRequestActiveWebView(lifecycleOwner: LifecycleOwner,
                                             observer: Observer<(WebView?) -> Unit>)

    abstract fun newWebView(url: String)
    abstract fun switchWebView(index: Int)
    abstract fun closeWebView()
    abstract fun observeNewWebView(lifecycleOwner: LifecycleOwner, observer: Observer<String>)
    abstract fun observeSwitchWebView(lifecycleOwner: LifecycleOwner, observer: Observer<Int>)
    abstract fun observeCloseWebView(lifecycleOwner: LifecycleOwner, observer: Observer<Any>)
}

abstract class BrowserSearchWidgetControllerVM : ObservableViewModel() {
    abstract fun showSearchWidget()
    abstract fun onCloseBtnClicked()
    abstract fun observeShowSearchWidget(lifecycleOwner: LifecycleOwner, observer: Observer<Any>)
    abstract fun observeOnCloseBtnClicked(lifecycleOwner: LifecycleOwner, observer: Observer<Any>)
}

abstract class WebViewsCountIndicatorVM : ObservableViewModel() {
    abstract var webViewsCount: Int?
    abstract fun observeWebViewsCount(lifecycleOwner: LifecycleOwner, observer: Observer<Int?>)
}

abstract class BrowserActionBarStateVM : ObservableViewModel() {
    @Bindable
    abstract fun getActionBarVisibility(): Int

    abstract fun setActionBarVisibility(value: Int)
}

abstract class BrowserSearchWidgetStateVM : ObservableViewModel() {
    @Bindable
    abstract fun getSearchWidgetContainerVisibility(): Int

    abstract fun setSearchWidgetContainerVisibility(value: Int)

    @Bindable
    abstract fun getSearchWidgetGravity(): Int

    abstract fun setSearchWidgetGravity(value: Int)

    @Bindable
    abstract fun getSearchWidgetWidth(): Int

    abstract fun setSearchWidgetWidth(value: Int)

    @Bindable
    abstract fun getSearchEditTextVisibility(): Int

    abstract fun setSearchEditTextVisibility(value: Int)

    @Bindable
    abstract fun getSearchCloseBtnVisibility(): Int

    abstract fun setSearchCloseBtnVisibility(value: Int)
}