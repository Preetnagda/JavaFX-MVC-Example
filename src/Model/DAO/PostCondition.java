package src.model.dao;

import java.util.HashMap;
import java.util.Map;

import src.model.AuthUser;

/**
 * PostCondition class provides a way to define and manage conditions for filtering
 * and sorting posts when querying the database.
 */
public class PostCondition {
    private String conditionString;

    private HashMap<String, HashMap<String, String>> conditions;

    /**
     * Initialize a new PostCondition object with an empty condition string and conditions map.
     */
    public PostCondition() {
        conditions = new HashMap<String, HashMap<String, String>>();
        conditionString = "";
    }

    /**
     * Add a condition to filter posts by a specific user.
     * 
     * @param user The AuthUser object to filter posts by user ID.
     */
    public void addCondition(AuthUser user) {
        HashMap<String, String> fieldMap = conditions.get("where");
        if (fieldMap == null) {
            fieldMap = new HashMap<String, String>();
        }
        fieldMap.put("user_id", Integer.toString(user.getUserId()));
        conditions.put("where", fieldMap);
    }

    /**
     * Add a custom condition to filter or sort posts.
     * 
     * @param type  The type of condition (e.g., "where", "sort", "limit").
     * @param field The field to condition on (e.g., "user_id", "created_at").
     * @param value The value to apply as a condition (e.g., "1", "DESC").
     */
    public void addCondition(String type, String field, String value) {
        HashMap<String, String> fieldMap = conditions.get(type);
        if (fieldMap == null) {
            fieldMap = new HashMap<String, String>();
        }
        fieldMap.put(field, value);
        conditions.put(type, fieldMap);
    }

    /**
     * Delete a specific condition of a given type and field.
     * 
     * @param type  The type of condition to remove (e.g., "where", "sort", "limit").
     * @param field The field of the condition to remove.
     */
    public void deleteCondition(String type, String field) {
        HashMap<String, String> typeMap = conditions.get(type);
        if (typeMap == null) {
            return;
        }
        typeMap.remove(field);
        conditions.put(type, typeMap);
    }

    /**
     * Reset all conditions, clearing the condition map.
     */
    public void resetCondition() {
        conditions = new HashMap<String, HashMap<String, String>>();
    }

    /**
     * Convert the PostCondition object into a string representation for use in database queries.
     * 
     * @return A string representing the conditions that can be used in a SQL query.
     */
    public String toString() {
        conditionString = "";
        int index = 0;
        HashMap<String, String> whereMap = conditions.get("where");
        if (whereMap != null) {
            for (Map.Entry<String, String> set : whereMap.entrySet()) {
                if (index == 0) {
                    conditionString += "WHERE";
                } else {
                    conditionString += " AND";
                }
                conditionString += (" " + set.getKey() + " = " + set.getValue());
                index++;
            }
        }

        index = 0;
        HashMap<String, String> sortMap = conditions.get("sort");

        if (sortMap != null) {
            for (Map.Entry<String, String> set : sortMap.entrySet()) {
                conditionString += (" ORDER BY " + set.getKey() + " " + set.getValue());
            }
        }

        index = 0;
        HashMap<String, String> limitMap = conditions.get("limit");

        if (limitMap != null) {
            for (Map.Entry<String, String> set : limitMap.entrySet()) {
                conditionString += (" LIMIT " + set.getValue());
            }
        }

        return conditionString;
    }
}
