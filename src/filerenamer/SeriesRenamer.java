package filerenamer;

import java.io.File;

/**
 * A class to replace characters (underscores, dashes, etc) from files in a
 * specified folder as well as rename files according to folder name
 *
 * <pre>
 * Before: 01-sympathy-for-the-devil.mp4
 * After: S05E01 - Sympathy For The Devil.mp4
 * </pre>
 *
 * @author Michael Hamann <michael.hamann75@gmail.com>
 */
public class SeriesRenamer
{

    String[] characters =
    {
        "_", "-", "\\[", "\\]", "\\(", "\\)", "\\{", "\\}"
    };
    String season = "";

    /**
     * Renames all files in the specified folder in the format:
     *
     * <pre>
     * S(season number)E(episode number) - (episode name)
     * </pre>
     *
     * @param folderName Seasons directory
     * @return Status of execution
     */
    public String SeriesRenamer(String folderName)
    {
        try
        {
            File folder = new File(folderName);
            firstTry(folder);
            File[] filesList = folder.listFiles();
            int numFolders = 0;
            int replaceableNotFound = 0;
            int replaceableFound = 1;
            season = folder.getName();
            season = season.replaceAll("[a-zA-Z]", "").trim();
            if (season.length() == 1)
            {
                season = "0" + season;
            }

            for (int i = 0; i < filesList.length; i++)
            {
                if (filesList[i].isFile())
                {
                    for (int j = 0; j < characters.length; j++)
                    {
                        replaceableFound += 1;
                        String oldName = filesList[i].toPath().getFileName().toString();
                        String path = filesList[i].getAbsolutePath().replace(oldName, "");
                        String newName = filesList[i].toPath().getFileName().toString().replaceAll(characters[j], " ");
                        newName = CapsFirst(newName).trim();
                        String prefix = "";
                        if (j == characters.length - 1)
                        {
                            if (!checkPrefix(newName))
                            {
                                prefix = setSeriesPrefix(newName);
                                newName = newName.replaceAll("[0-9]", "").trim();
                                filesList[i].renameTo(new File(path + prefix + newName));
                            }
                            else
                            {
                                prefix = newName.substring(0, 6);
                                newName = newName.substring(7);
                                filesList[i].renameTo(new File(path + prefix + " - " + newName));
                            }
                        }
                        else
                        {
                            boolean succeeded = false;
                            int count = 1;
                            while (succeeded == false && count < 10)
                            {
                                succeeded = filesList[i].renameTo(new File(path + newName));
                                System.out.println("Failed: " + newName + " [" + count + "]");
                                count++;
                            }
                        }
                    }
                }
                else
                {
                    numFolders++;
                }
            }

            return "Successfully renamed " + ((replaceableFound - numFolders) / 8) + " files.";

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
     * Sets the prefix of the file name based on the folders number (Season
     * number) and the number found within the file name (Episode number)
     *
     * * <pre>
     * S(season number)E(episode number) -
     * </pre>
     *
     * @param str File name
     * @return Formatted prefix
     */
    public String setSeriesPrefix(String str)
    {
        StringBuilder prefix = new StringBuilder();
        prefix.append("S").append(season).append("E");
        String episodeNum = str.replaceAll("[a-zA-Z]", "").trim();
        episodeNum = episodeNum.replaceAll("\\.", "").trim();

        if (episodeNum.length() == 1)
        {
            episodeNum = "0" + episodeNum;
        }
        prefix.append(episodeNum).append(" - ");

        return prefix.toString();
    }

    public void firstTry(File folder)
    {
        File[] filesList = folder.listFiles();
        int numFolders = 0;
        int replaceableNotFound = 0;
        int replaceableFound = 1;

        for (int i = 0; i < filesList.length; i++)
        {
            if (filesList[i].isFile())
            {
                for (int k = 0; k < 2; k++)
                {
                    for (int j = 0; j < characters.length; j++)
                    {
                        if (filesList[i].toString().contains(characters[j]))
                        {
                            replaceableFound += 1;
                            String oldName = filesList[i].toPath().getFileName().toString();
                            String path = filesList[i].getAbsolutePath().replace(oldName, "");
                            String newName = filesList[i].toPath().getFileName().toString().replaceAll(characters[j], " ");
                            newName = CapsFirst(newName).trim();
                            boolean succeeded = false;
                            int count = 1;
                            while (succeeded == false && count < 5)
                            {
                                succeeded = filesList[i].renameTo(new File(path + newName));
                                count++;
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean checkPrefix(String str)
    {
        boolean hasPrefix = false;

        if (str.contains("S" + season + "E"))
        {
            hasPrefix = true;
        }

        return hasPrefix;
    }
}
