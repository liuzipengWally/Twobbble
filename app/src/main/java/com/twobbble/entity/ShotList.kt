package com.twobbble.entity

/**
 * Created by liuzipeng on 2017/2/21.
 */

class ShotList {

    /**
     * id : 471756
     * title : Sasquatch
     * description :
     *
     *Quick, messy, five minute sketch of something that might become a fictional something.
     * width : 400
     * height : 300
     * images : {"hidpi":null,"normal":"https://d13yacurqjgara.cloudfront.net/users/1/screenshots/471756/sasquatch.png","teaser":"https://d13yacurqjgara.cloudfront.net/users/1/screenshots/471756/sasquatch_teaser.png"}
     * views_count : 4372
     * likes_count : 149
     * comments_count : 27
     * attachments_count : 0
     * rebounds_count : 2
     * buckets_count : 8
     * created_at : 2012-03-15T01:52:33Z
     * updated_at : 2012-03-15T02:12:57Z
     * html_url : https://dribbble.com/shots/471756-Sasquatch
     * attachments_url : https://api.dribbble.com/v1/shots/471756/attachments
     * buckets_url : https://api.dribbble.com/v1/shots/471756/buckets
     * comments_url : https://api.dribbble.com/v1/shots/471756/comments
     * likes_url : https://api.dribbble.com/v1/shots/471756/likes
     * projects_url : https://api.dribbble.com/v1/shots/471756/projects
     * rebounds_url : https://api.dribbble.com/v1/shots/471756/rebounds
     * animated : false
     * tags : ["fiction","sasquatch","sketch","wip"]
     * user : {"id":1,"name":"Dan Cederholm","username":"simplebits","html_url":"https://dribbble.com/simplebits","avatar_url":"https://d13yacurqjgara.cloudfront.net/users/1/avatars/normal/dc.jpg?1371679243","bio":"Co-founder &amp; designer of [@Dribbble<\/a>. Principal of SimpleBits. Aspiring clawhammer banjoist.","location":"Salem, MA","links":{"web":"http://simplebits.com","twitter":"https://twitter.com/simplebits"},"buckets_count":10,"comments_received_count":3395,"followers_count":29262,"followings_count":1728,"likes_count":34954,"likes_received_count":27568,"projects_count":8,"rebounds_received_count":504,"shots_count":214,"teams_count":1,"can_upload_shot":true,"type":"Player","pro":true,"buckets_url":"https://dribbble.com/v1/users/1/buckets","followers_url":"https://dribbble.com/v1/users/1/followers","following_url":"https://dribbble.com/v1/users/1/following","likes_url":"https://dribbble.com/v1/users/1/likes","shots_url":"https://dribbble.com/v1/users/1/shots","teams_url":"https://dribbble.com/v1/users/1/teams","created_at":"2009-07-08T02:51:22Z","updated_at":"2014-02-22T17:10:33Z"}
       * team : {"id":39,"name":"Dribbble","username":"dribbble","html_url":"https://dribbble.com/dribbble","avatar_url":"https://d13yacurqjgara.cloudfront.net/users/39/avatars/normal/apple-flat-precomposed.png?1388527574","bio":"Show and tell for designers. This is Dribbble on Dribbble.","location":"Salem, MA","links":{"web":"http://dribbble.com","twitter":"https://twitter.com/dribbble"},"buckets_count":1,"comments_received_count":2037,"followers_count":25011,"followings_count":6120,"likes_count":44,"likes_received_count":15811,"members_count":7,"projects_count":4,"rebounds_received_count":416,"shots_count":91,"can_upload_shot":true,"type":"Team","pro":false,"buckets_url":"https://dribbble.com/v1/users/39/buckets","followers_url":"https://dribbble.com/v1/users/39/followers","following_url":"https://dribbble.com/v1/users/39/following","likes_url":"https://dribbble.com/v1/users/39/likes","members_url":"https://dribbble.com/v1/teams/39/members","shots_url":"https://dribbble.com/v1/users/39/shots","team_shots_url":"https://dribbble.com/v1/users/39/teams","created_at":"2009-08-18T18:34:31Z","updated_at":"2014-02-14T22:32:11Z"}
     ](\"https://dribbble.com/dribbble\") */

    var id: Int = 0
    var title: String? = null
    var description: String? = null
    var width: Int = 0
    var height: Int = 0
    var images: ImagesBean? = null
    var views_count: Int = 0
    var likes_count: Int = 0
    var comments_count: Int = 0
    var attachments_count: Int = 0
    var rebounds_count: Int = 0
    var buckets_count: Int = 0
    var created_at: String? = null
    var updated_at: String? = null
    var html_url: String? = null
    var attachments_url: String? = null
    var buckets_url: String? = null
    var comments_url: String? = null
    var likes_url: String? = null
    var projects_url: String? = null
    var rebounds_url: String? = null
    var animated: Boolean = false
    var user: UserBean? = null
    var team: TeamBean? = null
    var tags: List<String>? = null

    class ImagesBean {
        /**
         * hidpi : null
         * normal : https://d13yacurqjgara.cloudfront.net/users/1/screenshots/471756/sasquatch.png
         * teaser : https://d13yacurqjgara.cloudfront.net/users/1/screenshots/471756/sasquatch_teaser.png
         */

        var hidpi: Any? = null
        var normal: String? = null
        var teaser: String? = null
    }

    class UserBean {
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

        var id: Int = 0
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

    class TeamBean {
        /**
         * id : 39
         * name : Dribbble
         * username : dribbble
         * html_url : https://dribbble.com/dribbble
         * avatar_url : https://d13yacurqjgara.cloudfront.net/users/39/avatars/normal/apple-flat-precomposed.png?1388527574
         * bio : Show and tell for designers. This is Dribbble on Dribbble.
         * location : Salem, MA
         * links : {"web":"http://dribbble.com","twitter":"https://twitter.com/dribbble"}
         * buckets_count : 1
         * comments_received_count : 2037
         * followers_count : 25011
         * followings_count : 6120
         * likes_count : 44
         * likes_received_count : 15811
         * members_count : 7
         * projects_count : 4
         * rebounds_received_count : 416
         * shots_count : 91
         * can_upload_shot : true
         * type : Team
         * pro : false
         * buckets_url : https://dribbble.com/v1/users/39/buckets
         * followers_url : https://dribbble.com/v1/users/39/followers
         * following_url : https://dribbble.com/v1/users/39/following
         * likes_url : https://dribbble.com/v1/users/39/likes
         * members_url : https://dribbble.com/v1/teams/39/members
         * shots_url : https://dribbble.com/v1/users/39/shots
         * team_shots_url : https://dribbble.com/v1/users/39/teams
         * created_at : 2009-08-18T18:34:31Z
         * updated_at : 2014-02-14T22:32:11Z
         */

        var id: Int = 0
        var name: String? = null
        var username: String? = null
        var html_url: String? = null
        var avatar_url: String? = null
        var bio: String? = null
        var location: String? = null
        var links: LinksBeanX? = null
        var buckets_count: Int = 0
        var comments_received_count: Int = 0
        var followers_count: Int = 0
        var followings_count: Int = 0
        var likes_count: Int = 0
        var likes_received_count: Int = 0
        var members_count: Int = 0
        var projects_count: Int = 0
        var rebounds_received_count: Int = 0
        var shots_count: Int = 0
        var can_upload_shot: Boolean = false
        var type: String? = null
        var pro: Boolean = false
        var buckets_url: String? = null
        var followers_url: String? = null
        var following_url: String? = null
        var likes_url: String? = null
        var members_url: String? = null
        var shots_url: String? = null
        var team_shots_url: String? = null
        var created_at: String? = null
        var updated_at: String? = null

        class LinksBeanX {
            /**
             * web : http://dribbble.com
             * twitter : https://twitter.com/dribbble
             */

            var web: String? = null
            var twitter: String? = null
        }
    }
}
