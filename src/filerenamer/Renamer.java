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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {

        FileRenamer fr = new FileRenamer();
        SeriesRenamer sr = new SeriesRenamer();
        CharacterCheck c = new CharacterCheck();
        String result = "";

        String folderName = JOptionPane.showInputDialog(null, "Please enter folder path", "Folder path", JOptionPane.QUESTION_MESSAGE);
        if (!folderName.substring(folderName.length() - 2).equals("\\"))
        {
            folderName += "\\";
        }

        String series = JOptionPane.showInputDialog(null, "Is this a series folder? (YES / NO)", "Series folder", JOptionPane.QUESTION_MESSAGE);
        while (!series.toUpperCase().contains("YES") && !series.toUpperCase().contains("NO"))
        {
            JOptionPane.showMessageDialog(null, "Please enter \'YES\' or \'NO\'", "Invalid", JOptionPane.WARNING_MESSAGE);
            series = JOptionPane.showInputDialog(null, "Is this a series folder? (YES / NO)", "Series folder", JOptionPane.QUESTION_MESSAGE);
        }

        if (series.toUpperCase().equals("NO"))
        {
            String replaceFrom = JOptionPane.showInputDialog(null, "Please enter the character you wish to replace", "Character to replace", JOptionPane.QUESTION_MESSAGE);

            String replaceWith = JOptionPane.showInputDialog(null, "Please enter the character you wish to replace with", "Character to replace with", JOptionPane.QUESTION_MESSAGE);
            while (c.checkProhibitedCharacters(replaceWith))
            {
                JOptionPane.showMessageDialog(null, "A file name cannot contain " + replaceWith, "Invalid", JOptionPane.ERROR_MESSAGE);
                replaceWith = JOptionPane.showInputDialog(null, "Please enter the character you wish to replace with", "Character to replace with", JOptionPane.QUESTION_MESSAGE);
            }

            String removeNumbers = JOptionPane.showInputDialog(null, "Remove numbers from name? (YES / NO)", "Remove numbers", JOptionPane.QUESTION_MESSAGE);
            while (!removeNumbers.toUpperCase().contains("YES") && !removeNumbers.toUpperCase().contains("NO"))
            {
                JOptionPane.showMessageDialog(null, "Please enter \'YES\' or \'NO\'", "Invalid", JOptionPane.ERROR_MESSAGE);
                removeNumbers = JOptionPane.showInputDialog(null, "Remove numbers from name? (YES / NO)", "Remove numbers", JOptionPane.QUESTION_MESSAGE);
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
