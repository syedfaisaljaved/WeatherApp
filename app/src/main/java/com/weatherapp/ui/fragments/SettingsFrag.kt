package com.weatherapp.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.weatherapp.data.sharedPrefs.UserSharedPrefs
import com.weatherapp.databinding.FragmentSettingsBinding


class SettingsFrag : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    lateinit var userSharedPrefs: UserSharedPrefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        userSharedPrefs = UserSharedPrefs.getSharedPref(context!!)

        binding.llUnit.setOnClickListener{
            createAlertDialogWithRadioButtonGroup()
        }

        binding.llUsername.setOnClickListener{
            changeUsernameAlertDialog()
        }
        return view
    }

    private fun changeUsernameAlertDialog(){
        val alertDialog = AlertDialog.Builder(context!!)
        alertDialog.setTitle("Enter new username")
        val input = EditText(context!!)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        input.layoutParams = lp
        alertDialog.setView(input)


        alertDialog.setPositiveButton("OK") { dialog, _ ->
            val username: String = input.text.toString()
            userSharedPrefs.setUsername(username)
            dialog.dismiss()
        }

        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        alertDialog.show()
    }

    private fun createAlertDialogWithRadioButtonGroup() {
        val alertDialog = AlertDialog.Builder(context!!)
        alertDialog.setTitle("Select Temp Unit")
        alertDialog.setSingleChoiceItems(
            arrayOf<CharSequence>(" Celsius ", " Fahrenheit "),
            if (userSharedPrefs.getUnit() == "metric") 0 else 1
        ) { dialog, item ->
            when (item) {
                0 -> {
                    userSharedPrefs.setUnit("metric")
                    binding.unit.text = "Celsius"
                }
                1 -> {
                    userSharedPrefs.setUnit("imperial")
                    binding.unit.text = "Fahrenheit"
                }
            }
            dialog.dismiss()
        }
        alertDialog.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFrag()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}