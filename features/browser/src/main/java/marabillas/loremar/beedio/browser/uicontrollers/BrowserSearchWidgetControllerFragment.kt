package marabillas.loremar.beedio.browser.uicontrollers

import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import dagger.android.support.DaggerFragment
import marabillas.loremar.beedio.base.web.WebNavigation
import marabillas.loremar.beedio.browser.R
import marabillas.loremar.beedio.sharedui.OnTransitionEndListener
import marabillas.loremar.beedio.sharedui.hideSofKeyboard
import marabillas.loremar.beedio.sharedui.showSoftKeyboard
import javax.inject.Inject
import kotlin.math.roundToInt

class BrowserSearchWidgetControllerFragment @Inject constructor() : DaggerFragment(), BrowserSearchWidgetControllerInterface, TextView.OnEditorActionListener {

    var webViewSwitcher: WebViewSwitcherInterface? = null
    var webNavigation: WebNavigation? = null

    private val searchWidgetTransition = ChangeBounds()
    private val showSearchWidgetEnd = OnTransitionEndListener(this::slideWidgetAcross)
    private val slideWidgetAcrossEnd = OnTransitionEndListener(this::expandWidget)
    private val expandWidgetEnd = OnTransitionEndListener(this::enableInput)
    private val closeSearchWidgetEnd = OnTransitionEndListener(this::hideSearchWidget)

    override fun showSearchWidget() {
        activity?.findViewById<Toolbar>(R.id.browserToolbar)
                ?.visibility = View.GONE

        activity?.findViewById<FrameLayout>(R.id.browser_search_widget_container)
                ?.visibility = View.VISIBLE

        getSearchWidget()?.let { searchWidget ->
            val params = (searchWidget.layoutParams as FrameLayout.LayoutParams).apply {
                width = (56 * resources.displayMetrics.density).roundToInt()
            }
            searchWidget.layoutParams = params
        }

        searchWidgetTransition.addListener(showSearchWidgetEnd)
                .duration = 100
        initSearchWidgetTransition()
    }

    private fun slideWidgetAcross() {
        searchWidgetTransition.removeListener(showSearchWidgetEnd)
        searchWidgetTransition.addListener(slideWidgetAcrossEnd)
                .duration = 200
        initSearchWidgetTransition()
        getSearchWidget()?.let { searchWidget ->
            val params = (searchWidget.layoutParams as FrameLayout.LayoutParams).apply {
                gravity = Gravity.START
            }
            searchWidget.layoutParams = params
        }
    }

    private fun expandWidget() {
        searchWidgetTransition.removeListener(slideWidgetAcrossEnd)
        searchWidgetTransition.addListener(expandWidgetEnd)
        initSearchWidgetTransition()
        getSearchWidget()?.let { searchWidget ->
            val params = (searchWidget.layoutParams as FrameLayout.LayoutParams).apply {
                width = MATCH_PARENT
                gravity = Gravity.CENTER
            }
            searchWidget.layoutParams = params
        }
    }

    private fun enableInput() {
        searchWidgetTransition.removeListener(expandWidgetEnd)
        val editText = activity?.findViewById<EditText>(R.id.browser_search_edit_text)
        val closeBtn = activity?.findViewById<ImageView>(R.id.browser_close_btn)
        editText?.visibility = View.VISIBLE
        closeBtn?.visibility = View.VISIBLE
        editText?.requestFocus()
        activity?.let { showSoftKeyboard(it) }
        editText?.setOnEditorActionListener(this)
    }

    override fun onCloseBtnClicked() {
        val editText = activity?.findViewById<EditText>(R.id.browser_search_edit_text)

        editText?.let { editTxt ->

            if (editTxt.text.isNotEmpty()) {
                editTxt.text?.clear()
            } else {
                closeSearchWidget()
            }
        }
    }

    private fun closeSearchWidget() {

        activity?.let { hideSofKeyboard(it) }

        val editText = activity?.findViewById<EditText>(R.id.browser_search_edit_text)
        val closeBtn = activity?.findViewById<ImageView>(R.id.browser_close_btn)
        editText?.visibility = View.INVISIBLE
        closeBtn?.visibility = View.INVISIBLE

        searchWidgetTransition.addListener(closeSearchWidgetEnd)
                .duration = 100
        initSearchWidgetTransition()

        getSearchWidget()?.let { searchWidget ->
            val params = (searchWidget.layoutParams as FrameLayout.LayoutParams).apply {
                width = (56 * resources.displayMetrics.density).roundToInt()
                gravity = Gravity.END
            }
            searchWidget.layoutParams = params
        }
    }

    private fun hideSearchWidget() {
        searchWidgetTransition.removeListener(closeSearchWidgetEnd)

        activity?.findViewById<FrameLayout>(R.id.browser_search_widget_container)
                ?.visibility = View.GONE

        activity?.findViewById<Toolbar>(R.id.browserToolbar)
                ?.visibility = View.VISIBLE
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        v?.let { editText ->

            val input = editText.text.toString()
            if (input.isNotEmpty()) {

                webNavigation?.let { web ->
                    closeSearchWidget()
                    val validInput = web.navigateTo(input)
                    webViewSwitcher?.newWebView(validInput)

                } ?: return true

            } else {
                return true
            }
        }

        return false
    }

    private fun getSearchWidget(): View? {
        return activity?.findViewById(R.id.browser_search_widget)
    }

    private fun initSearchWidgetTransition() {
        activity?.let { activity ->
            TransitionManager.beginDelayedTransition(
                    activity.findViewById(android.R.id.content), searchWidgetTransition)
        }
    }
}