package com.example.androidsamplekotlinnoncompose.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.ui.AppBarConfiguration
import com.example.androidsamplekotlinnoncompose.EdgeMode
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.ActivityClAblTbNlBinding


class CoordinatorLAppBarLToolbarNestSActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityClAblTbNlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClAblTbNlBinding.inflate(layoutInflater)
        enableEdgeToEdgeMode(binding.root, binding.mainContainerCL, EdgeMode.NEW_EDGE_TO_EDGE, binding.toolbar)
        setContentView(binding.root)

        if (savedInstanceState != null) {
//            binding.tvTitle.text = "ddd"
        } else {
            setUI()
            Log.e("CreateCreate", "Create")
        }
    }

    private fun setUI() {
        binding.tvTitle.text = "HHHHH"
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.close -> {
                    finish()
                    true
                }

                R.id.favorite -> {
                    Toast.makeText(this, "favorite", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.one -> {
                    Toast.makeText(this, "one", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> {
                    false
                }
            }
        }
    }
}