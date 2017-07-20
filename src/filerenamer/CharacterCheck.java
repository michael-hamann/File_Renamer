package filerenamer;

/**
 * Class to check for certain characters in strings
 *
 * @author Michael Hamann <michael.hamann75@gmail.com>
 */
public class CharacterCheck
{

    /**
     * Finds characters that are prohibited from being in a windows file name.
     * Returns true if illegal characters are present.
     *
     * @param entered Sentence to be checked
     * @return True or False based on whether there are illegal characters
     */
    public boolean checkProhibitedCharacters(String entered)
    {
        boolean prohibited = false;

        switch (entered)
        {
            case "\\":
                prohibited = true;
                break;
            case "/":
                prohibited = true;
                break;
            case ":":
                prohibited = true;
                break;
            case "*":
                prohibited = true;
                break;
            case "?":
                prohibited = true;
                break;
            case "\"":
                prohibited = true;
                break;
            case "<":
                prohibited = true;
                break;
            case ">":
                prohibited = true;
                break;
            case "|":
                prohibited = true;
                break;
        }

        return prohibited;
    }

    public boolean isBracket(String entered)
    {
        boolean escapedNeeded = false;
        switch (entered)
        {
            case "(":
                escapedNeeded = true;
                break;
            case ")":
                escapedNeeded = true;
                break;
            case "[":
                escapedNeeded = true;
                break;
            case "]":
                escapedNeeded = true;
                break;
            case "{":
                escapedNeeded = true;
                break;
            case "}":
                escapedNeeded = true;
                break;
        }

        return escapedNeeded;
    }
}
