package cz.bald.student_tests.ui.test

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cz.bald.student_tests.model.REQUEST_STORAGE_PERMISSION
import cz.bald.student_tests.model.Test
import cz.bald.student_tests.model.TestSetting
import cz.bald.student_tests.service.DownloadService
import cz.bald.student_tests.ui.listener.FragmentChangeListener
import cz.bald.student_tests.ui.setup.SetupActivity
import cz.bald.studenttests.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class TestActivity : AppCompatActivity(), FragmentChangeListener {

    private lateinit var setting : TestSetting
    private lateinit var fragment : QuestionFragment
    private val downloadService = DownloadService()
    private var downloadID: Long = -100
    private var downloadComplete : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        if (savedInstanceState == null) {
            setting = intent.getParcelableExtra(SetupActivity.ARG_SETUP)
            Toast.makeText(this, setting.toString(), Toast.LENGTH_LONG).show()
            if (downloadService.requestStoragePermission(this)) {
                downloadFile()
            }
            registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {

        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadFile()
            } else {
                createDefaultTest()
                swapFragment(fragment, true)
            }
        }
    }

    override fun swapFragment(newFragment: Fragment, stack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.test_fragment_container, newFragment)
        if (stack) {
            transaction.addToBackStack(newFragment.toString())
        }
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onDownloadComplete)
    }

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadID == id) {
                downloadComplete = true
            }
        }
    }

    private fun createDefaultTest() {
        fragment = createQuestionFragment(downloadService.defaultTemplateTest(setting))
    }

    private fun downloadFile() {
        CoroutineScope(Dispatchers.IO).launch {
            if (!downloadService.fileAlreadyExists()) {
                downloadID = downloadService.downloadFile(this@TestActivity, setting)
                while (!downloadComplete) {
                    delay(100)
                }
            }
        }.invokeOnCompletion {
            Handler(Looper.getMainLooper()).post {
                fragment = createQuestionFragment(downloadService.createTest())
                swapFragment(fragment, true)
            }
        }
    }

    private fun createQuestionFragment(test: Test) : QuestionFragment {
        return QuestionFragment(test, 0, QuestionFragment.NO_QUESTION, QuestionFragment.NO_QUESTION)
    }
}
