package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     * Test Strategy for testGuessFollowsGraphEMpty
     * 1.  空列表。期望返回一个空的 social network。
     * 2.  多条 tweets，没有人被提到。期望返回一个空的 social network。
     * 3.  多条 tweets，只有一个人被提到。期望返回一个只包含这个人的 social network。
     * 4.  多条 tweets，有多个人被提到。期望返回一个包含这些人的 social network。
     * 5.  多条 tweets，出现无效的 @-mention，即 @-mention 不是 authors。期望程序能够正确处理这些无效数据，返回正确的 social network。
     * 6.  多条 tweets，出现自我 @-mention，即 author @-mention 自己。期望程序能够正确处理这些无效数据，返回正确的 social network。
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "aaa", "is it reasonable to talk about so much?", d1);
    private static final Tweet tweet21 = new Tweet(2, "bbb", "rivest talk in 30 minutes ", d2);
    private static final Tweet tweet2 = new Tweet(2, "bbb", "rivest talk in 30 minutes @aaa ", d2);
    private static final Tweet tweet3 = new Tweet(3, "aaa", "is it reasonable to talk about @bbb so much?", d3);
    private static final Tweet tweet4 = new Tweet(4, "aaa", "is it reasonable to talk about @bbbccc so much?", d3);
    private static final Tweet tweet5 = new Tweet(5, "aaa", "is it reasonable to talk about @aaa so much?", d3);
    private static final Tweet tweet6 = new Tweet(6, "aaa", "is it reasonable to talk about @BBB so much?", d3);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testGuessFollowsGraphNoMention() {
        ArrayList<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet1, tweet1));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testGuessFollowsGraphOneMention() {
        ArrayList<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet1, tweet2));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        
        assertTrue("expected bbb's follow size equals to 1", followsGraph.get("bbb").size() == 1);
    }
    
    @Test
    public void testGuessFollowsGraphTwoMention() {
        ArrayList<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet1, tweet2, tweet3));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        
        assertTrue("expected graph size equals to 2", followsGraph.size() == 2);
    }
    
    @Test
    public void testGuessFollowsGraphInvalidMentionNotAuthor() {
        ArrayList<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet4));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        
        assertTrue("expected graph size equals to 0", followsGraph.isEmpty());
    }
    
    @Test
    public void testGuessFollowsGraphInvalidSelfMention() {
        ArrayList<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet5));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        assertTrue("expected graph size equals to 0", followsGraph.isEmpty());
    }
    
    @Test
    public void testGuessFollowsGraphCaseInsensetive() {
        ArrayList<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet3, tweet6, tweet21));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        
        assertTrue("expected graph size equals to 1", followsGraph.size() == 1);
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }
    
    @Test
    public void testInfluencersNonEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("aaa", Set.of("bbb"));
        followsGraph.put("bbb", Set.of("aaa"));
        followsGraph.put("ccc", Set.of("aaa"));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        String[] target = {"aaa", "bbb", "ccc"};
        assertArrayEquals(influencers.toArray(), target);
    }
    

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
