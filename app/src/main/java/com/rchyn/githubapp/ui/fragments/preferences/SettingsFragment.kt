package com.rchyn.githubapp.ui.fragments.preferences

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import com.rchyn.githubapp.R
import com.rchyn.githubapp.databinding.FragmentSettingsBinding
import com.rchyn.githubapp.util.Constant.DARK
import com.rchyn.githubapp.util.Constant.DEFAULT
import com.rchyn.githubapp.util.Constant.LIGHT
import com.rchyn.githubapp.util.Constant.THEME_KEY

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val defaultView = super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        defaultView.id = View.generateViewId()
        binding.container.addView(defaultView)
        ConstraintSet().apply {
            clone(binding.container)
            connect(defaultView.id, ConstraintSet.START, defaultView.id, ConstraintSet.END)
        }.applyTo(binding.container)

        binding.toolbarMain.apply {
            title = getString(R.string.settings)
            navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarMain.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onSharedPreferenceChanged(pref: SharedPreferences, key: String) {
        when (key) {
            THEME_KEY -> {
                when (pref.getString(THEME_KEY, DEFAULT)) {
                    DARK -> AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                    )
                    LIGHT -> AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                    )
                    else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}