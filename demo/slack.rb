require 'slack-notifier'
require 'pp'

WEBHOOK_URL='https://hooks.slack.com/services/XXX/XXX/XXX'

#notifier = Slack::Notifier.new WEBHOOK_URL
#notifier.ping "Hello World"
# => if your webhook is setup, will message "Hello World"
# => to the default channel you set in slack

def sendSlackReview(b, nr)
    notifier = Slack::Notifier.new WEBHOOK_URL

    # holy cow, look at all this awful mutation of the message string!  :)

    message = "New review for #{b.name}: total reviews: #{b.reviews.size}\n"
    message += ">>>"
    # message += nr.pretty_inspect            # like pp nr

    message += nr['author_name'] + ", "
    message += nr['time'].to_s + ", "
    message += nr['title'] + ", "
    message += nr['text'] 
        
    notifier.ping message
end 
