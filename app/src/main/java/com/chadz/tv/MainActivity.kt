package com.chadz.tv

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : Activity() {

    private val bg = Color.rgb(10, 12, 18)
    private val white = Color.WHITE
    private val blue = Color.rgb(35, 120, 230)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.setPadding(35, 25, 35, 20)
        root.setBackgroundColor(bg)

        // HEADER
        val header = LinearLayout(this)
        header.gravity = Gravity.CENTER_VERTICAL

        val logo = TextView(this)
        logo.text = "CHADZ TV"
        logo.textSize = 30f
        logo.setTextColor(blue)
        logo.typeface = Typeface.DEFAULT_BOLD

        header.addView(logo)

        val spacer = View(this)
        header.addView(
            spacer,
            LinearLayout.LayoutParams(0, 1, 1f)
        )

        val clock = TextView(this)
        clock.text = SimpleDateFormat(
            "HH:mm",
            Locale.getDefault()
        ).format(Date())

        clock.textSize = 22f
        clock.setTextColor(white)

        header.addView(clock)
        root.addView(header)

        // WELCOME
        val welcome = TextView(this)
        welcome.text = "Bienvenue sur CHADZ TV"
        welcome.textSize = 25f
        welcome.setTextColor(white)
        welcome.setPadding(0, 35, 0, 20)

        root.addView(welcome)

        // SECTION
        val title = TextView(this)
        title.text = "MES APPLICATIONS"
        title.textSize = 18f
        title.setTextColor(blue)
        title.typeface = Typeface.DEFAULT_BOLD

        root.addView(title)

        // APP GRID
        val scroll = ScrollView(this)

        val grid = GridLayout(this)
        grid.columnCount = 5
        grid.setPadding(0, 20, 0, 20)

        val apps = getInstalledApps()

        if (apps.isEmpty()) {
            val empty = TextView(this)
            empty.text = "Aucune application trouvée"
            empty.setTextColor(white)
            grid.addView(empty)
        }

        for (app in apps) {

            val card = LinearLayout(this)
            card.orientation = LinearLayout.VERTICAL
            card.gravity = Gravity.CENTER
            card.isFocusable = true
            card.isClickable = true
            card.setPadding(8, 15, 8, 15)

            fun background(focused: Boolean) {
                val shape = GradientDrawable()
                shape.cornerRadius = 18f
                shape.setColor(
                    if (focused)
                        Color.rgb(35, 65, 110)
                    else
                        Color.rgb(28, 31, 40)
                )
                shape.setStroke(
                    if (focused) 3 else 1,
                    if (focused) blue else Color.DKGRAY
                )
                card.background = shape
            }

            background(false)

            card.setOnFocusChangeListener { _, focused ->
                background(focused)
            }

            val icon = ImageView(this)
            icon.setImageDrawable(app.loadIcon(packageManager))
            icon.layoutParams = LinearLayout.LayoutParams(
                65, 65
            )

            card.addView(icon)

            val name = TextView(this)
            name.text = app.loadLabel(packageManager).toString()
            name.textSize = 13f
            name.setTextColor(white)
            name.gravity = Gravity.CENTER
            name.maxLines = 2
            name.setPadding(0, 10, 0, 0)

            card.addView(name)

            card.setOnClickListener {
                val packageName =
                    app.activityInfo.packageName

                val launchIntent =
                    packageManager.getLeanbackLaunchIntentForPackage(
                        packageName
                    ) ?: packageManager.getLaunchIntentForPackage(
                        packageName
                    )

                if (launchIntent != null) {
                    startActivity(launchIntent)
                }
            }

            val params = GridLayout.LayoutParams()
            params.width = 0
            params.height = 145
            params.columnSpec =
                GridLayout.spec(GridLayout.UNDEFINED, 1f)
            params.setMargins(7, 7, 7, 7)

            grid.addView(card, params)
        }

        scroll.addView(grid)

        root.addView(
            scroll,
            LinearLayout.LayoutParams(
                -1, 0, 1f
            )
        )

        // FOOTER
        val footer = TextView(this)
        footer.text = "CHADZ TV  •  Your Entertainment"
        footer.textSize = 13f
        footer.setTextColor(Color.GRAY)
        footer.gravity = Gravity.CENTER
        footer.setPadding(0, 20, 0, 10)

        root.addView(footer)

        setContentView(root)
    }

    private fun getInstalledApps():
        List<android.content.pm.ResolveInfo> {

        val apps = mutableListOf<android.content.pm.ResolveInfo>()

        val categories = listOf(
            Intent.CATEGORY_LEANBACK_LAUNCHER,
            Intent.CATEGORY_LAUNCHER
        )

        for (category in categories) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(category)

            val found = packageManager.queryIntentActivities(
                intent,
                PackageManager.MATCH_ALL
            )

            for (app in found) {
                if (app.activityInfo.packageName != packageName &&
                    apps.none {
                        it.activityInfo.packageName ==
                        app.activityInfo.packageName
                    }) {
                    apps.add(app)
                }
            }
        }

        return apps.sortedBy {
            it.loadLabel(packageManager).toString()
                .lowercase()
        }
    }
}