package com.senmas.lab5

import ImageCardAdapter
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem

class MainActivity : AppCompatActivity() {
    companion object {
        const val HOME_ID = 1L
        const val TIMER_ID = 2L
        const val BUTTONS_ID = 3L
        const val CALCULATOR_ID = 4L
        const val LOGIN_ID = 5L
        const val RECYCLER_VIEW_ID = 6L
    }

    private fun replaceView(old: View, layoutId: Int) {
        val parent = old.parent as ViewGroup
        val index = parent.indexOfChild(old)
        parent.removeViewAt(index)

        val new = layoutInflater.inflate(layoutId, parent, false)
        parent.addView(new, index)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val home = PrimaryDrawerItem()
            .withIdentifier(HOME_ID)
            .withName(R.string.drawer_home)

        val timer = PrimaryDrawerItem()
            .withIdentifier(TIMER_ID)
            .withName(R.string.drawer_timer)

        val buttons = PrimaryDrawerItem()
            .withIdentifier(BUTTONS_ID)
            .withName(R.string.drawer_buttons)

        val calculator = PrimaryDrawerItem()
            .withIdentifier(CALCULATOR_ID)
            .withName(R.string.drawer_calculator)

        val login = PrimaryDrawerItem()
            .withIdentifier(LOGIN_ID)
            .withName(R.string.drawer_login)

        val recyclerView = PrimaryDrawerItem()
            .withIdentifier(RECYCLER_VIEW_ID)
            .withName(R.string.drawer_recycler_view)

        DrawerBuilder()
            .withActivity(this)
            .withToolbar(findViewById(R.id.toolbar))
            .addDrawerItems(
                home,
                DividerDrawerItem(),
                timer,
                buttons,
                calculator,
                login,
                DividerDrawerItem(),
                recyclerView
            )
            .withOnDrawerItemClickListener { view, pos, drawerItem ->
                val layoutId = when(drawerItem.identifier) {
                    HOME_ID -> R.layout.drawer_home
                    TIMER_ID -> R.layout.drawer_timer
                    BUTTONS_ID -> R.layout.drawer_buttons
                    CALCULATOR_ID -> R.layout.drawer_calculator
                    LOGIN_ID -> R.layout.drawer_login
                    RECYCLER_VIEW_ID -> R.layout.drawer_recycler_view
                    else -> throw IllegalArgumentException("drawerItem.identifier")
                }

                val contentView = findViewById<View>(R.id.content)
                replaceView(contentView, layoutId)

                if (layoutId == R.layout.drawer_recycler_view) {
                    val urls = arrayOf(
                        "https://i.imgur.com/2KxoIsa.jpg",
                        "https://i.imgur.com/EZHXbA0.jpg",
                        "https://i.imgur.com/qb7Ev7A.jpg",
                        "https://i.imgur.com/ZtXXAwX.jpg",
                        "https://i.imgur.com/lrE9vha.jpg"
                    )

val recycler = findViewById<RecyclerView>(R.id.content)
recycler.adapter = ImageCardAdapter(urls)
recycler.layoutManager = LinearLayoutManager(this)
}

true
}
.build()
}
}
