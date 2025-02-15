package com.example.androidsamplekotlinnoncompose.ui.activity

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidsamplekotlinnoncompose.EdgeMode
import com.example.androidsamplekotlinnoncompose.R
import com.example.androidsamplekotlinnoncompose.databinding.ActivityProgressBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressActivity : BaseActivity() {
    private lateinit var binding: ActivityProgressBinding
    private lateinit var progressTest: ProgressBar
    private lateinit var progressContainerCL: ConstraintLayout
    private var progressJob: Job? = null
    private var progressJobFull: Job? = null

    override fun onDestroy() {
        super.onDestroy()
        progressJob?.cancel()
        progressJobFull?.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        enableEdgeToEdgeMode(binding.root, binding.mainContainerCL, EdgeMode.NEW_EDGE_TO_EDGE)
        setContentView(binding.root)
        setUI()
    }
    private fun setUI() {

        binding.btnTestProgress.setOnClickListener {
            if (binding.progressContainerCL.visibility == VISIBLE) {
                progressJob?.cancel()
                binding.tvProgress.visibility = GONE
                binding.progressContainerCL.apply { visibility = GONE }
            } else {
                binding.progressContainerCL.apply { visibility = VISIBLE }
                binding.progressTest.apply { visibility = VISIBLE }
                binding.tvProgress.visibility = VISIBLE

                progressJob = CoroutineScope(Dispatchers.Main).launch {
                    for (i in 1..10) {
                        binding.progressTest.progress = i * 10
                        binding.tvProgress.text = (i * 10).toString()
                        delay(1000)
                    }
                    binding.progressContainerCL.visibility = GONE
                    binding.tvProgress.visibility = GONE
                }
            }
        }

        binding.btnTestProgressFull.setOnClickListener {
            if (binding.progressFullCL.visibility != VISIBLE) {
                binding.progressFullCL.apply { visibility = VISIBLE }
                binding.progressFullTest.apply { visibility = VISIBLE }
                binding.tvProgress.visibility = VISIBLE

                progressJob = CoroutineScope(Dispatchers.Main).launch {
                    binding.tvProgress.visibility = VISIBLE
                    for (i in 1..10) {
                        binding.progressFullTest.progress = i * 10
                        binding.tvProgress.text = (i * 10).toString()
                        delay(1000)
                    }
                    binding.progressFullCL.visibility = GONE
                    binding.tvProgress.visibility = GONE
                }
            }
        }
    }
}