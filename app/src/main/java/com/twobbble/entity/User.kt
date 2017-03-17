package com.twobbble.entity

/**
 * Created by liuzipeng on 2017/3/5.
 */

class User {

    /**
     * id : 1
     * name : Dan Cederholm
     * username : simplebits
     * html_url : https://dribbble.com/simplebits
     * avatar_url : https://d13yacurqjgara.cloudfront.net/users/1/avatars/normal/dc.jpg?1371679243
     * bio : Co-founder &amp; designer of [@Dribbble](https://dribbble.com/dribbble). Principal of SimpleBits. Aspiring clawhammer banjoist.
     * location : Salem, MA
     * links : {"web":"http://simplebits.com","twitter":"https://twitter.com/simplebits"}
     * buckets_count : 10
     * comments_received_count : 3395
     * followers_count : 29262
     * followings_count : 1728
     * likes_count : 34954
     * likes_received_count : 27568
     * projects_count : 8
     * rebounds_received_count : 504
     * shots_count : 214
     * teams_count : 1
     * can_upload_shot : true
     * type : Player
     * pro : true
     * buckets_url : https://dribbble.com/v1/users/1/buckets
     * followers_url : https://dribbble.com/v1/users/1/followers
     * following_url : https://dribbble.com/v1/users/1/following
     * likes_url : https://dribbble.com/v1/users/1/likes
     * shots_url : https://dribbble.com/v1/users/1/shots
     * teams_url : https://dribbble.com/v1/users/1/teams
     * created_at : 2009-07-08T02:51:22Z
     * updated_at : 2014-02-22T17:10:33Z
     */

    var id: Long = 0
    var name: String? = null
    var username: String? = null
    var html_url: String? = null
    var avatar_url: String? = null
    var bio: String? = null
    var location: String? = null
    var links: LinksBean? = null
    var buckets_count: Int = 0
    var comments_received_count: Int = 0
    var followers_count: Int = 0
    var followings_count: Int = 0
    var likes_count: Int = 0
    var likes_received_count: Int = 0
    var projects_count: Int = 0
    var rebounds_received_count: Int = 0
    var shots_count: Int = 0
    var teams_count: Int = 0
    var isCan_upload_shot: Boolean = false
    var type: String? = null
    var isPro: Boolean = false
    var buckets_url: String? = null
    var followers_url: String? = null
    var following_url: String? = null
    var likes_url: String? = null
    var shots_url: String? = null
    var teams_url: String? = null
    var created_at: String? = null
    var updated_at: String? = null

    class LinksBean {
        /**
         * web : http://simplebits.com
         * twitter : https://twitter.com/simplebits
         */

        var web: String? = null
        var twitter: String? = null
    }
}
