package filerenamer;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * A simple program to replace characters (underscores, dashes, etc) from files
 * in a specified folder
 *
 * @author Michael Hamann <michael.hamann75@gmail.com>
 */
public class Renamer
{

    static int question = JOptionPane.QUESTION_MESSAGE;
    static int warning = JOptionPane.WARNING_MESSAGE;
    static int error = JOptionPane.ERROR_MESSAGE;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        FileRenamer fr = new FileRenamer();
        SeriesRenamer sr = new SeriesRenamer();
        CharacterCheck c = new CharacterCheck();
        String result = "";

        String folderName = JOptionPane.showInputDialog(null, "Please enter folder path", "Folder path", question);
        if (!folderName.substring(folderName.length() - 2).equals("\\"))
        {
            folderName += "\\";
        }

        String series = JOptionPane.showInputDialog(null, "Is this a series folder? (YES / NO)", "Series folder", question);
        while (!series.toUpperCase().contains("YES") && !series.toUpperCase().contains("NO"))
        {
            JOptionPane.showMessageDialog(null, "Please enter \'YES\' or \'NO\'", "Invalid", warning);
            series = JOptionPane.showInputDialog(null, "Is this a series folder? (YES / NO)", "Series folder", question);
        }

        if (series.toUpperCase().equals("NO"))
        {
            String replaceFrom = JOptionPane.showInputDialog(null, "Please enter the character you wish to replace", "Character to replace", question);

            String replaceWith = JOptionPane.showInputDialog(null, "Please enter the character you wish to replace with", "Character to replace with", question);
            while (c.checkProhibitedCharacters(replaceWith))
            {
                JOptionPane.showMessageDialog(null, "A file name cannot contain " + replaceWith, "Invalid", error);
                replaceWith = JOptionPane.showInputDialog(null, "Please enter the character you wish to replace with", "Character to replace with", question);
            }

            String removeNumbers = JOptionPane.showInputDialog(null, "Remove numbers from name? (YES / NO)", "Remove numbers", question);
            while (!removeNumbers.toUpperCase().contains("YES") && !removeNumbers.toUpperCase().contains("NO"))
            {
                JOptionPane.showMessageDialog(null, "Please enter \'YES\' or \'NO\'", "Invalid", warning);
                removeNumbers = JOptionPane.showInputDialog(null, "Remove numbers from name? (YES / NO)", "Remove numbers", question);
            }

            if (c.isBracket(replaceFrom))
            {
                replaceFrom = "\\" + replaceFrom;
                result = fr.FileRenamerWithBracket(folderName, replaceFrom, replaceWith, removeNumbers.toUpperCase());
            }
            else
            {
                result = fr.FileRenamer(folderName, replaceFrom, replaceWith, removeNumbers.toUpperCase());
            }
        }
        else
        {
            result = sr.SeriesRenamer(folderName);
        }

        JOptionPane.showMessageDialog(null, result, "Result", JOptionPane.INFORMATION_MESSAGE);

        try
        {
            Desktop.getDesktop().open(new File(folderName));
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

    }

}
