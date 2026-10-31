package com.chadz.tv

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {

    private val bg = Color.rgb(10, 12, 18)
    private val white = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.setPadding(55, 30, 55, 25)
        root.setBackgroundColor(bg)

        // TOP BAR
        val header = LinearLayout(this)
        header.gravity = Gravity.CENTER_VERTICAL

        val logo = TextView(this)
        logo.text = "CHADZ"
        logo.textSize = 27f
        logo.setTextColor(white)

        header.addView(logo)

        val spacer = View(this)
        header.addView(
            spacer,
            LinearLayout.LayoutParams(0, 1, 1f)
        )

        val weather = TextView(this)
        weather.text = "☀ 18°"
        weather.textSize = 17f
        weather.setTextColor(white)
        header.addView(weather)

        header.addView(makeTopItem("📶 Wi-Fi"))
        header.addView(makeTopItem("⚙"))

        root.addView(header)

        // HERO SECTION
        val hero = TextView(this)
        hero.text = "\n\nCHADZ TV\n\nVotre divertissement commence ici\n\n▶  DÉCOUVRIR\n"
        hero.textSize = 27f
        hero.setTextColor(white)
        hero.gravity = Gravity.CENTER_VERTICAL
        hero.setPadding(35, 25, 35, 25)
        hero.setBackgroundColor(Color.rgb(35, 42, 60))

        val heroParams = LinearLayout.LayoutParams(
            -1, 260
        )
        heroParams.topMargin = 45
        root.addView(hero, heroParams)

        // APPLICATIONS
        val title = TextView(this)
        title.text = "Mes applications"
        title.textSize = 23f
        title.setTextColor(white)

        val titleParams = LinearLayout.LayoutParams(
            -1, -2
        )
        titleParams.topMargin = 30
        root.addView(title, titleParams)

        val apps = LinearLayout(this)
        apps.orientation = LinearLayout.HORIZONTAL

        listOf(
            "YouTube",
            "Netflix",
            "Prime Video",
            "Paramètres"
        ).forEach {
            val card = TextView(this)
            card.text = it
            card.textSize = 17f
            card.gravity = Gravity.CENTER
            card.setTextColor(white)
            card.setBackgroundColor(
                Color.rgb(40, 43, 53)
            )
            card.isFocusable = true
            card.isFocusableInTouchMode = true

            val params = LinearLayout.LayoutParams(
                210, 90
            )
            params.setMargins(0, 20, 20, 0)
            apps.addView(card, params)
        }

        root.addView(apps)

        // FOOTER
        val footer = TextView(this)
        footer.text = "CHADZ TV  •  Votre écran, votre univers."
        footer.textSize = 13f
        footer.setTextColor(Color.GRAY)

        val footerParams = LinearLayout.LayoutParams(
            -1, -2
        )
        footerParams.topMargin = 45
        root.addView(footer, footerParams)

        setContentView(root)
    }

    private fun makeTopItem(text: String): TextView {
        val item = TextView(this)
        item.text = "   $text   "
        item.textSize = 15f
        item.setTextColor(white)
        item.gravity = Gravity.CENTER
        return item
    }
}