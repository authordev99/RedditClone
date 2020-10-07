package com.teddybrothers.redditclone.models

import java.util.*

class Topic {
    var id = UUID.randomUUID().toString()
    var description = ""
    var isVoteUp = false
    var isVoteDown = false
    var voteCount = 0
}