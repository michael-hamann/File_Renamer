package filerenamer;

import java.io.File;

/**
 * A class to replace characters (underscores, dashes, etc) from files in a
 * specified folder
 *
 * <pre>
 * Before: 11 - Before_the_hangman's_noose.mp3
 * After: 11 - Before The Hangman's Noose.mp3
 * </pre>
 *
 * @author Michael Hamann <michael.hamann75@gmail.com>
 */
public class FileRenamer
{

    /**
     * Renames all files in the specified folder by replacing specified
     * characters with others and removing numbers if specified.
     *
     * @param folderName Absolute path to directory
     * @param replaceFrom Character or string to be replaced
     * @param replaceWith Character or string to be replaced with
     * @param removeNumbers If numbers in file names should be removed (YES /
     * NO)
     * @return Status of execution
     */
    public String FileRenamer(String folderName, String replaceFrom, String replaceWith, String removeNumbers)
    {
        try
        {
            File folder = new File(folderName);
            File[] filesList = folder.listFiles();
            int numFolders = 0;
            int replaceableNotFound = 0;
            int replaceableFound = 1;

            for (int i = 0; i < filesList.length; i++)
            {
                if (filesList[i].isFile())
                {
                    if (filesList[i].toString().contains(replaceFrom))
                    {
                        replaceableFound += 1;
                        String oldName = filesList[i].toPath().getFileName().toString();
                        String path = filesList[i].getAbsolutePath().replace(oldName, "");
                        String newName = filesList[i].toPath().getFileName().toString().replaceAll(replaceFrom, replaceWith);
                        newName = CapsFirst(newName).trim();
                        if (removeNumbers.equals("YES"))
                        {
                            newName = removeNumbers(newName);
                        }
                        filesList[i].renameTo(new File(path + newName));
                    }
                    else if (removeNumbers.equals("YES"))
                    {
                        replaceableFound += 1;
                        String oldName = filesList[i].toPath().getFileName().toString();
                        String path = filesList[i].getAbsolutePath().replace(oldName, "");
                        String newName = filesList[i].toPath().getFileName().toString();
                        newName = removeNumbers(newName);
                        filesList[i].renameTo(new File(path + newName));
                    }
                    else
                    {
                        replaceableNotFound++;
                    }
                }
                else
                {
                    numFolders++;
                }
            }
            if (replaceableNotFound == (filesList.length - numFolders))
            {
                return replaceFrom + " was not found in the folder.";
            }
            else
            {
                return "Successfully renamed " + (replaceableFound - numFolders) + " files.";
            }
        }
        catch (Exception e)
        {
            return (e.getMessage());
        }
    }

    public String FileRenamerWithBracket(String folderName, String replaceFrom, String replaceWith, String removeNumbers)
    {
        try
        {
            File folder = new File(folderName);
            File[] filesList = folder.listFiles();
            int numFolders = 0;
            int replaceableNotFound = 0;
            int replaceableFound = 1;

            for (int i = 0; i < filesList.length; i++)
            {
                if (filesList[i].isFile())
                {
                    String nameWithBracket = insertBracketInFront(filesList[i].toPath().getFileName().toString(), replaceFrom);
                    if (nameWithBracket.contains(replaceFrom))
                    {
                        replaceableFound += 1;
                        String oldName = filesList[i].toPath().getFileName().toString();
                        String path = filesList[i].getAbsolutePath().replace(oldName, "");
                        String newName = filesList[i].toPath().getFileName().toString().replaceAll(replaceFrom, replaceWith);
                        newName = CapsFirst(newName).trim();
                        if (removeNumbers.equals("YES"))
                        {
                            newName = removeNumbers(newName);
                        }
                        filesList[i].renameTo(new File(path + newName));
                    }
                    else if (removeNumbers.equals("YES"))
                    {
                        replaceableFound += 1;
                        String oldName = filesList[i].toPath().getFileName().toString();
                        String path = filesList[i].getAbsolutePath().replace(oldName, "");
                        String newName = filesList[i].toPath().getFileName().toString();
                        newName = removeNumbers(newName);
                        filesList[i].renameTo(new File(path + newName));
                    }
                    else
                    {
                        replaceableNotFound++;
                    }
                }
                else
                {
                    numFolders++;
                }
            }
            if (replaceableNotFound == (filesList.length - numFolders))
            {
                return replaceFrom + " was not found in the folder.";
            }
            else
            {
                return "Successfully renamed files with " + replaceFrom.charAt(replaceFrom.length() - 1) + " in the name.";
            }
        }
        catch (Exception e)
        {
            return (e.getMessage());
        }
    }

    /**
     * Capitalizes each word in a sentence.
     *
     * @param str Sentence to be capitalized
     * @return Capitalized sentence
     */
    public String CapsFirst(String str)
    {
        String[] words = str.split(" ");
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < words.length; i++)
        {
            if (words[i].length() > 0)
            {
                ret.append(Character.toUpperCase(words[i].charAt(0)));
                ret.append(words[i].substring(1));
                if (i < words.length - 1)
                {
                    ret.append(' ');
                }
            }
        }
        return ret.toString();
    }

    /**
     * Removes any numbers in a sentence.
     *
     * @param str Sentence numbers should be removed from
     * @return Sentence without numbers
     */
    public String removeNumbers(String str)
    {
        str = str.replaceAll("[0-9]", "");
        return str;
    }

    public String insertBracketInFront(String str, String bracket)
    {
        StringBuilder newString = new StringBuilder();
        newString = newString.append(bracket).append(str);

        return newString.toString();
    }

}
