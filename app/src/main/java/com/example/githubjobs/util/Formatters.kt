package com.example.githubjobs.util

import android.os.Build
import android.text.Html
import android.text.Spanned

object Formatters {

    @JvmStatic
    fun formatHTML(html: String?): Spanned? {
        return when {

            html == null -> null

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> Html.fromHtml(
                html,
                Html.FROM_HTML_MODE_COMPACT
            )

            else -> Html.fromHtml(html)
        }
    }
}