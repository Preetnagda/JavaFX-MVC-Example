package src.Model.DAO;

import java.util.HashMap;
import java.util.Map;

import src.Model.AuthUser;

public class PostCondition {
    private String conditionString;

    private HashMap<String, HashMap<String, String>> conditions;

    public PostCondition(){
        conditions = new HashMap<String, HashMap<String, String>>();
        conditionString = "";
    }

    public void addCondition(AuthUser user){
        HashMap<String, String> fieldMap = conditions.get("where");
        if(fieldMap == null){fieldMap = new HashMap<String, String>();};
        fieldMap.put("user_id", Integer.toString(user.getUserId()));
        conditions.put("where", fieldMap);
    }


    public void addCondition(String type, String field, String value){
        HashMap<String, String> fieldMap = conditions.get(type);
        if(fieldMap == null){fieldMap = new HashMap<String, String>();}
        fieldMap.put(field, value);
        conditions.put(type, fieldMap);
    }

    public void deleteCondition(String type, String field){
        HashMap<String, String> typeMap = conditions.get(type);
        if(typeMap == null){return;}
        typeMap.remove(field);
        conditions.put(type, typeMap);
    }

    public void resetCondition(){
        conditions = new HashMap<String, HashMap<String, String>>();
    }


    public String toString(){
        conditionString = "";
        int index = 0;
        HashMap<String, String> whereMap = conditions.get("where");
        if(whereMap != null){
            for (Map.Entry<String, String> set :
                whereMap.entrySet())
             {
                if(index == 0){
                    conditionString += "WHERE";
                }else{
                    conditionString += " AND";
                }
                conditionString += (" " +  set.getKey() + " = " + set.getValue());
                index++;
             }
        }

        
        index = 0;
        HashMap<String, String> sortMap = conditions.get("sort");

        if(sortMap != null){
            for (Map.Entry<String, String> set :
             sortMap.entrySet())
             {
                conditionString += (" ORDER BY " +  set.getKey() + " " + set.getValue());
             }
            
        }

        index = 0;
        HashMap<String, String> limitMap = conditions.get("limit");

        if(limitMap != null){
            for (Map.Entry<String, String> set :
                limitMap.entrySet())
                {
                    conditionString += (" LIMIT " + set.getValue());
                }
        }

        return conditionString;
    }
}
