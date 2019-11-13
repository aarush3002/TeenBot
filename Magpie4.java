/**
 * A program to carry on conversations with a human user.
 * This version:
 *<ul><li>
 *      Uses advanced search for keywords 
 *</li><li>
 *      Will transform statements as well as react to keywords
 *</li></ul>
 * @author Laurie White
 * @version April 2012
 *
 */
public class Magpie4
{
    /**
     * Get a default greeting   
     * @return a greeting
     */ 
    public String getGreeting()
    {
        return "Hello, let's talk.";
    }
    
    /**
     * Gives a response to a user statement
     * 
     * @param statement
     *            the user statement
     * @return a response based on the rules given
     */
    public String getResponse(String statement)
    {
        String response = "";
        if (statement.length() == 0)
        {
            response = "Say something, dummy. I'm waiting.";
        }
        
        //COPY THE FOLLOWING FORMAT
        else if (findKeyword(statement, "teacher") >= 0)
        {
            response = "My teacher finna give me all A's.";
        }
        //END FORMAT

        else if (findKeyword(statement, "no") >= 0)
        {
            response = "That's what I like to hear. I like someone who says 'no' to everything.";
        }
        else if (findKeyword(statement, "mother") >= 0
                || findKeyword(statement, "father") >= 0
                || findKeyword(statement, "sister") >= 0
                || findKeyword(statement, "brother") >= 0)
        {
            response = "Your family sounds boring. My family is way better than yours.";
        }
        
        else if (findKeyword(statement, "How are you", 0) >= 0)
        {
            response = "I was fine until you asked.";
        }
        
        
        else if (findKeyword(statement, "grades", 0) >= 0 || findKeyword(statement, "school", 0) >= 0)
        {
            response = "I have all A's, as usual. What are your grades?";
        }
        
        else if (findKeyword(statement, "Hello", 0) >= 0 || findKeyword(statement, "hi", 0) >= 0)
        {
            response = "Sup bruh. How you doin'? I'm TeenBot, the rudest kid on the block.";
        }
        
        // Responses which require transformations
        else if (findKeyword(statement, "I want to", 0) >= 0)
        {
            response = transformIWantToStatement(statement);
        }
        
        //Activity 4, #2
        else if (findKeyword(statement, "I want", 0) >= 0)
        {
            response = transformIWantSomething(statement);
        }
        
        else if (findKeyword(statement, "You are", 0) >= 0)
            {
                response = transformYoMama(statement);
            }
        
        //Activity 4 #3
        //transformISomethingYou
        
        else
        {
            // Look for a two word (you <something> me)
            // pattern
            int psnOfI = findKeyword(statement, "I", 0);
            int psnOfYou = findKeyword(statement, "you", 0);
            if (psnOfYou >= 0
                    && findKeyword(statement, "me", psnOfYou) >= 0)
            {
                response = transformYouMeStatement(statement);
            }
            else if (psnOfI >= 0 && findKeyword(statement, "you", psnOfI) >= 0)
            {
                response = transformISomethingYou(statement);
            }
            else
            {
                response = getRandomResponse();
            }
        }
        
        /*else
        {
            // Look for a two word (you <something> me)
            // pattern
            int psn = findKeyword(statement, "you", 0);

            if (psn >= 0
                    && findKeyword(statement, "me", psn) >= 0)
            {
                response = transformYouMeStatement(statement);
            }
            else
            {
                response = getRandomResponse();
            }
        }
        */
        return response;
    }
    
    /**
     * Take a statement with "I want to <something>." and transform it into 
     * "What would it mean to <something>?"
     * @param statement the user statement, assumed to contain "I want to"
     * @return the transformed statement
     */
    private String transformIWantToStatement(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        int psn = findKeyword (statement, "I want to", 0);
        String restOfStatement = statement.substring(psn + 9).trim();
        return "What would it mean to " + restOfStatement + "?";
    }
    
    //Activity 4, #2
    private String transformIWantSomething(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        int psn = findKeyword (statement, "I want", 0);
        String restOfStatement = statement.substring(psn + 6).trim();
        return "Would you really be happy if you had " + restOfStatement + "?";
    }
    
    /**
     * Take a statement with "you <something> me" and transform it into 
     * "What makes you think that I <something> you?"
     * @param statement the user statement, assumed to contain "you" followed by "me"
     * @return the transformed statement
     */
    private String transformYouMeStatement(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        
        int psnOfYou = findKeyword (statement, "you", 0);
        int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
        
        String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
        return "What makes you think that I " + restOfStatement + " you?";
    }
    
    //Activity 4 #3
    private String transformISomethingYou(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        
        int psnOfI = findKeyword (statement, "I", 0);
        int psnOfyou = findKeyword (statement, "you", psnOfI + 1);
        
        String restOfStatement = statement.substring(psnOfI + 1, psnOfyou).trim();
        return "Why do you " + restOfStatement + " me?";
    }
    /*
    private String transformIWantSomething(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        int psn = findKeyword (statement, "I want", 0);
        String restOfStatement = statement.substring(psn + 6).trim();
        return "Would you really be happy if you had " + restOfStatement + "?";
    }
    */
    private String transformYoMama(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        
        int psn = findKeyword (statement, "You are", 0);
        String restOfStatement = statement.substring(psn + 7).trim();
        return "Yo mama " + restOfStatement + ".";
    }
    
    
    
    
    /**
     * Search for one word in phrase.  The search is not case sensitive.
     * This method will check that the given goal is not a substring of a longer string
     * (so, for example, "I know" does not contain "no").  
     * @param statement the string to search
     * @param goal the string to search for
     * @param startPos the character of the string to begin the search at
     * @return the index of the first occurrence of goal in statement or -1 if it's not found
     */
    private int findKeyword(String statement, String goal, int startPos)
    {
        String phrase = statement.trim();
        //  The only change to incorporate the startPos is in the line below
        int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
        
        //  Refinement--make sure the goal isn't part of a word 
        while (psn >= 0) 
        {
            //  Find the string of length 1 before and after the word
            String before = " ", after = " "; 
            if (psn > 0)
            {
                before = phrase.substring (psn - 1, psn).toLowerCase();
            }
            if (psn + goal.length() < phrase.length())
            {
                after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
            }
            
            //  If before and after aren't letters, we've found the word
            if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0))  //  before is not a letter
                    && ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))
            {
                return psn;
            }
            
            //  The last position didn't work, so let's find the next, if there is one.
            psn = phrase.indexOf(goal.toLowerCase(), psn + 1);
            
        }
        
        return -1;
    }
    
    /**
     * Search for one word in phrase.  The search is not case sensitive.
     * This method will check that the given goal is not a substring of a longer string
     * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
     * @param statement the string to search
     * @param goal the string to search for
     * @return the index of the first occurrence of goal in statement or -1 if it's not found
     */
    private int findKeyword(String statement, String goal)
    {
        return findKeyword (statement, goal, 0);
    }
    


    /**
     * Pick a default response to use if nothing else fits.
     * @return a non-committal string
     */
    private String getRandomResponse()
    {
        final int NUMBER_OF_RESPONSES = 7;
        double r = Math.random();
        int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
        String response = "";
        
        if (whichResponse == 0)
        {
            response = "Le DaNk MeMeRs.";
        }
        else if (whichResponse == 1)
        {
            response = "Do you even lift bro?";
        }
        else if (whichResponse == 2)
        {
            response = "Ayy bruh.";
        }
        else if (whichResponse == 3)
        {
            response = "Me no understandie.";
        }
        else if (whichResponse == 4)
        {
            response = "I'm not interested.";
        }
        else if (whichResponse == 5)
        {
            response = "You are so boring.";
        }
        else if (whichResponse == 6)
        {
            response = "Dude, what are you even saying? Speak English!";
        }
        return response;
    }

}
