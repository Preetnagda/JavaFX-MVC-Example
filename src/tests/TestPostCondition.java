package src.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import src.model.AuthUser;
import src.model.dao.PostCondition;

public class TestPostCondition {

    private PostCondition postCondition;

    @Before
    public void setUp() {
        postCondition = new PostCondition();
    }

    @Test
    public void testAddConditionWithAuthUser() {
        AuthUser user = new AuthUser(1, "test", "test", "test", "test");
        postCondition.addCondition(user);

        String conditionString = postCondition.toString();
        assertTrue(conditionString.contains("WHERE"));
        assertTrue(conditionString.contains("user_id = 1"));
    }

    @Test
    public void testAddConditionWithTypeFieldAndValue() {
        postCondition.addCondition("where", "user_id", "1");

        String conditionString = postCondition.toString();
        assertTrue(conditionString.contains("WHERE"));
        assertTrue(conditionString.contains("user_id = 1"));
    }

    @Test
    public void testDeleteCondition() {
        postCondition.addCondition("where", "user_id", "1");
        postCondition.deleteCondition("where", "user_id");

        String conditionString = postCondition.toString();
        assertTrue(conditionString.isEmpty());
    }

    @Test
    public void testResetCondition() {
        postCondition.addCondition("where", "user_id", "1");
        postCondition.resetCondition();

        String conditionString = postCondition.toString();
        assertTrue(conditionString.isEmpty());
    }
    
    @Test
    public void testToString() {
        postCondition.addCondition("where", "user_id", "1");
        postCondition.addCondition("sort", "created_at", "DESC");
        postCondition.addCondition("limit", "", "10");

        String conditionString = postCondition.toString();
        assertTrue(conditionString.contains("WHERE"));
        assertTrue(conditionString.contains("user_id = 1"));
        assertTrue(conditionString.contains("ORDER BY created_at DESC"));
        assertTrue(conditionString.contains("LIMIT 10"));
    }
}
