package com.jens.automation2.kakaodirectory.kespresso

import io.github.kakaocup.kakao.text.KTextView

object KEspressoExtended {
    fun actionFindText(text: String) =
        KTextView { withText(text) }
}