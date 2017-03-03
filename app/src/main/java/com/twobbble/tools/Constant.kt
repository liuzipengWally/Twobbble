package com.twobbble.tools

import android.os.Environment
import java.io.File

/**
 * Created by liuzipeng on 2017/2/17.
 */
class Constant {
    companion object {
        val CLIENT_ID = "14736e3fe75031a8eb83a3b9e762ec32bc31de4024bfc7cc238e50abaaf8c760"
        val CLIENT_SECRET = "ef8eb3102694ad0f8ce55172cb0fcacb4363106542987936437fb54b39f39955"
        val ACCESS_TOKEN = "ee607f434ed5fe4ac833c7d22f541245f95f2c99ecdef57900e74e4d6bf7e4cd"
        val RX_RETRY_TIME: Long = 1
        val KEY_TOKEN = "key_token"
        val DRIBBBLE_DATE_FORMAT = "YYYY-MM-DDTHH:MM:SSZ"

        val DETAILS_EVENT_LIKE_COUNT = 0
        val DETAILS_EVENT_ATTACHMENT_COUNT = 1
        val DETAILS_EVENT_BUCKET_COUNT = 2
        val IMAGE_DOWNLOAD_PATH = "${Environment.getExternalStorageDirectory()}${File.separator}Twobbble${File.separator}download"
    }
}

class Parameters {
    companion object {
        //sort
        val COMMENTS = "comments"
        val RECENT = "recent"
        val VIEWS = "views"
        //list
        val ANIMATED = "animated"
        val ATTACHMENTS = "attachments"
        val DEBUTS = "debuts"
        val PLAYOFFS = "playoffs"
        val REBOUNDS = "rebounds"
        val TEAMS = "teams"
        //    timeframe
        val WEEK = "week"
        val MONTH = "month"
        val YEAR = "year"
        val EVER = "ever"
    }
}