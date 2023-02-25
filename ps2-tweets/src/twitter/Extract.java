package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        if(tweets.isEmpty()) {
            throw new IllegalArgumentException("tweets size == 0");
        }
        
        Instant early = tweets.get(0).getTimestamp();
        Instant late = tweets.get(0).getTimestamp();
        for(Tweet t : tweets) {
            if(t.getTimestamp().isBefore(early)) {
                early = t.getTimestamp();
            }
            if(t.getTimestamp().isAfter(late)) {
                late = t.getTimestamp();
            }
        }
        return new Timespan(early, late);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();

        // Regex pattern for matching Twitter usernames
        Pattern pattern = Pattern.compile("@([A-Za-z0-9_]{1,15})\\b");

        // Iterate over all tweets
        for (Tweet tweet : tweets) {
            // Get the text of the tweet
            String text = tweet.getText();

            // Find all matches of the pattern in the tweet text
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                // Add the matched username (in lowercase) to the set of mentioned users
                String username = matcher.group(1).toLowerCase();
                mentionedUsers.add(username);
            }
        }

        return mentionedUsers;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
