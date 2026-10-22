package com.example.nexora

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvWelcomeName = view.findViewById<TextView>(R.id.tvWelcomeName)
        val tvUserInitial = view.findViewById<TextView>(R.id.tvUserInitial)
        val btnProperty = view.findViewById<Button>(R.id.btnProperty)
        val btnWeb = view.findViewById<Button>(R.id.btnWeb)

        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "User") ?: "User"

        if (username.isNotEmpty()) {
            val capitalized = username.replaceFirstChar { it.uppercase() }
            tvWelcomeName.text = "$capitalized 👋"
            tvUserInitial.text = capitalized.first().toString()
        }

        btnProperty.setOnClickListener {
            val activity = requireActivity() as? MainActivity
            if (activity != null) {
                activity.selectTab(R.id.nav_property)
            } else {
                startActivity(Intent(requireContext(), PropertyActivity::class.java))
            }
        }

        btnWeb.setOnClickListener {
            val activity = requireActivity() as? MainActivity
            if (activity != null) {
                activity.selectTab(R.id.nav_web)
            } else {
                startActivity(Intent(requireContext(), WebViewActivity::class.java))
            }
        }
    }
}