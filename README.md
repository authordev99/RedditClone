# RedditClone

# 1. Home Screen
  - Maintain a list of topics and its upvotes/downvotes
  - Allow the user to upvote or downvote a topic. For this challenge, the user may upvote or
    downvote the same topic multiple times.
  - Always return a list of top 20 topics (sorted by upvotes, descending) on the homepage
  - So for UpVote it will plus 1 vote and downVote -1 (it can be minus count)
  
![Screenshot_20201008-095853_Reddit Clone](https://user-images.githubusercontent.com/29091648/95406535-34543e00-094d-11eb-957b-742f131b7652.jpg)


# 2. Create Topic
  - Here i created topic with title and description like Reddit
  - Allow the user to submit topics. For this challenge, a “topic” is simply a string that does not exceed 255 characters.
![Screenshot_20201008-095918_Reddit Clone](https://user-images.githubusercontent.com/29091648/95406547-3c13e280-094d-11eb-8fae-d49a2e2d206c.jpg)

  


# 3. Detail Topic
  - Tapping a topic should expand that topic into a separate view (page)
  - In this page, user can upVote and downVote then when going back, it will update to list
 
![Screenshot_20201008-095925_Reddit Clone](https://user-images.githubusercontent.com/29091648/95406542-3ae2b580-094d-11eb-8b95-d5f0b4698052.jpg)
  
 

# Repository
  - Using shared Preference to store data as Repository in local (Consider data in small size)
  - a topic contains id, title, description, isUpVote, isDownVote and voteCount

# Testing
  - I created sample unit test for TopicViewModel ,
    - testing get topic list first initialize on repository whether is null or empty.
    - testing create and save topic, once saved will check list is still empty or have a data already.
    - testing update topic, create one topic and save it then will update with new topic based on position then will check with oldTopic and newTopic is it updated or not

