package com.twobbble.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.twobbble.R
import de.psdev.licensesdialog.LicensesDialogFragment
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20
import de.psdev.licensesdialog.licenses.BSD3ClauseLicense
import de.psdev.licensesdialog.model.Notice
import de.psdev.licensesdialog.model.Notices

class LicenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)
        showLicense()
    }

    private fun showLicense() {
        val notices = Notices()
        notices.addNotice(Notice("EventBus", "https://github.com/greenrobot/EventBus",
                "Copyright (C) 2012-2016 Markus Junginger, greenrobot(http://greenrobot.org)",
                ApacheSoftwareLicense20()))
        notices.addNotice(Notice("Retrofit2", "https://github.com/square/retrofit",
                "Copyright 2013 Square, Inc.",
                ApacheSoftwareLicense20()))
        notices.addNotice(Notice("Gson", "https://github.com/google/gson",
                "Copyright 2008 Google Inc.",
                ApacheSoftwareLicense20()))
        notices.addNotice(Notice("RxJava", "https://github.com/ReactiveX/RxJava",
                "Copyright (c) 2016-present, RxJava Contributors.",
                ApacheSoftwareLicense20()))
        notices.addNotice(Notice("RxAndroid", "https://github.com/ReactiveX/RxAndroid",
                "Copyright 2015 The RxAndroid authors",
                ApacheSoftwareLicense20()))
        notices.addNotice(Notice("Fresco", "https://github.com/facebook/fresco",
                "Copyright (c) 2015-present, Facebook, Inc. All rights reserved.",
                BSD3ClauseLicense()))
        notices.addNotice(Notice("MaterialProgressbar", "https://github.com/DreaminginCodeZH/MaterialProgressBar",
                "Copyright 2015 Zhang Hai",
                ApacheSoftwareLicense20()))
        notices.addNotice(Notice("FileDownloader", "https://github.com/lingochamp/FileDownloader",
                "Copyright (c) 2015 LingoChamp Inc.",
                ApacheSoftwareLicense20()))
        notices.addNotice(Notice("RxPermissions", "https://github.com/tbruyelle/RxPermissions",
                "Copyright tbruyelle",
                ApacheSoftwareLicense20()))
        notices.addNotice(Notice("PhotoDraweeView", "https://github.com/ongakuer/PhotoDraweeView",
                "Copyright 2015-2016 Relex",
                ApacheSoftwareLicense20()))
        notices.addNotice(Notice("RxBinding", "https://github.com/JakeWharton/RxBinding",
                "Copyright (C) 2015 Jake Wharton",
                ApacheSoftwareLicense20()))

        val licensesDialogFragment = LicensesDialogFragment.Builder(this).setNotices(notices)
                .setShowFullLicenseText(false)
                .setIncludeOwnLicense(true)
                .setUseAppCompat(true)
                .build()
        licensesDialogFragment.show(supportFragmentManager, null)
        licensesDialogFragment.setOnDismissListener { finish() }
    }
}
