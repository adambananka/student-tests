package cz.bald.student_tests.ui.result

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import cz.bald.student_tests.database.StudentTestsDatabase
import cz.bald.student_tests.model.Result
import cz.bald.student_tests.ui.setup.SetupActivity
import cz.bald.studenttests.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var results: List<Result>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setupNavDrawer()
        if (savedInstanceState == null) {
            retrieveResultsAndShow()
        }
    }

    private fun setupNavDrawer() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.result_drawer_layout)
        val navView = findViewById<NavigationView>(R.id.result_nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_menu_open, R.string.nav_menu_close)
        drawerLayout.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        navView.menu.findItem(R.id.nav_menu_maturita).setOnMenuItemClickListener {
            startActivity(Intent(this, SetupActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
            finish()
            true
        }
        navView.menu.findItem(R.id.nav_menu_results).setOnMenuItemClickListener {
            startActivity(Intent(this, ResultActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
            finish()
            true
        }
    }

    private fun retrieveResultsAndShow() {
        CoroutineScope(Dispatchers.IO).launch {
            results = StudentTestsDatabase.getInstance(this@ResultActivity).resultDao().getAll()
        }.invokeOnCompletion {
            Handler(Looper.getMainLooper()).post {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.result_fragment_container, ResultListFragment(results))
                    .commit()
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}