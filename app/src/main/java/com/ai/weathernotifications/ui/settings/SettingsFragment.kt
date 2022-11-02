package com.ai.weathernotifications.ui.settings

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.util.StringUtil
import com.ai.weathernotifications.R
import com.ai.weathernotifications.databinding.FragmentSettingsBinding
import java.util.regex.Pattern
import kotlin.math.min

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settingsBtn.setOnClickListener {
            binding.settingsTv.text = parseString(getString(R.string.settings_text_to_parse))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun parseString(string: String): SpannableString {
        //@My Very First Commit@ $by IoanA$ #s. f.# (#Med.#) This fixes most important bugs #2022-10-26# @Weather App@

        var newText = string
        while (newText.contains("#")) {
            val pair = getFirstPairOfSpecialCh("#", newText)
            newText = newText.removeRange(pair.first, pair.second)
        }
        newText = newText.replace("#", "")

        val boldIndexPairs = getSpecialCharacterIndexPair('@', newText)
        val italicIndexPairs = getSpecialCharacterIndexPair('$', newText)
        newText = newText.replace("@"," ")
        newText = newText.replace("$"," ")
        val spannableString = SpannableString(newText)

        boldIndexPairs.forEach { pair ->
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                pair.first,
                pair.second,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }

        italicIndexPairs.forEach { pair ->
            spannableString.setSpan(
                StyleSpan(Typeface.ITALIC),
                pair.first,
                pair.second,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
        return spannableString
    }

    private fun getSpecialCharacterIndexPair(
        specialCh: Char,
        text: String
    ): List<Pair<Int, Int>> {
        val pairList = mutableListOf<Pair<Int, Int>>()
        var first = -1
        var second = -1
        text.forEachIndexed { index, c ->
            if (c == specialCh) {
                if (first == -1) {
                    first = index
                } else if (second == -1) {
                    second = index
                } else {
                    pairList.add(Pair(first, second))
                    first = index
                    second = -1
                }
            }
        }
        if (first != -1 && second != -1) {
            pairList.add(Pair(first, second))
        }
        return pairList
    }

    private fun getFirstPairOfSpecialCh(ch: String, text: String): Pair<Int, Int> {
        val first = text.indexOf(ch)
        val newText = text.removeRange(0, first + 1)
        val second = newText.indexOf(ch)
        return Pair(first, first + second + 2)
    }
}