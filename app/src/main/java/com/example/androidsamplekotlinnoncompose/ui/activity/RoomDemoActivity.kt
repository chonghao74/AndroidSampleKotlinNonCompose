package com.example.androidsamplekotlinnoncompose.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.androidsamplekotlinnoncompose.EdgeMode
//import androidx.navigation.findNavController
//import androidx.navigation.ui.AppBarConfiguration
//import androidx.navigation.ui.navigateUp
//import androidx.navigation.ui.setupActionBarWithNavController
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.ActivityRoomDemoBinding

class RoomDemoActivity : BaseActivity() {

    private lateinit var binding: ActivityRoomDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomDemoBinding.inflate(layoutInflater)
        enableEdgeToEdgeMode(
            binding.root,
            binding.mainContainerCL,
            EdgeMode.NEW_EDGE_TO_EDGE
        )
        setContentView(binding.root)
    }

}