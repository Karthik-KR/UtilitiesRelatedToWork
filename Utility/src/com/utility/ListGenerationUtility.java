package com.utility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ListGenerationUtility
{

   private static final String FILE_PATH = "F:\\Utility";

   private static final String FILE_PATH_GENERATED = "F:";

   private static final String GENERATED_FILE = "ListWhichAreReused";

   private static List<String> filesListToBeVerifiedWith = getListOfFilesInThePath("F:\\Utility");

   private static String fileToBeVerified = "Search";

   public static void main(String[] args) throws IOException
   {

      for (String filename : filesListToBeVerifiedWith)
      {
         final File checkWithThisFile = new File(FILE_PATH + "\\" + filename + ".txt");
         if (!checkWithThisFile.exists())
         {
            checkWithThisFile.createNewFile();
         }
         try (BufferedReader reader =
            new BufferedReader(new FileReader(FILE_PATH_GENERATED + "\\" + fileToBeVerified + ".txt")))
         {
            String line = reader.readLine();
            while (line != null)
            {
               if (checkIfLineExistsInFlow(line, checkWithThisFile, FILE_PATH))
               {
                  String logItHere = GENERATED_FILE;
                  loggingIntoNewFile(checkWithThisFile, line, logItHere);
               }
               line = reader.readLine();
            }
         }
         catch (final IOException e)
         {
            e.printStackTrace();
         }

      }

      toGetListofclassesUniqueToThisFlow();

      ListModificationUtility listModificationUtility = new ListModificationUtility();
      listModificationUtility.modifyExistingList();
      System.out.println("Executin Done. Congrats!!");
   }

   private static void toGetListofclassesUniqueToThisFlow()
   {
      try (BufferedReader readerForFileVerified =
         new BufferedReader(new FileReader(FILE_PATH_GENERATED + "\\" + fileToBeVerified + ".txt"));
               BufferedReader readerForFileGenerated = new BufferedReader(
                  new FileReader(FILE_PATH_GENERATED + "\\" + GENERATED_FILE + ".txt")))
      {
         String line = readerForFileVerified.readLine();
         final File checkWithThisFile =
            new File(FILE_PATH_GENERATED + "\\" + GENERATED_FILE + ".txt");
         final File checkForThisFile = new File(FILE_PATH_GENERATED + "\\" + fileToBeVerified + ".txt");
         while (line != null)
         {

            if (!checkIfLineExistsInFile(line, checkWithThisFile, FILE_PATH_GENERATED))
            {
               String logItHere = "ListWhichAreUnique";
               loggingIntoNewFile(checkForThisFile, line, logItHere);
            }
            line = readerForFileVerified.readLine();
         }
      }
      catch (final IOException e)
      {
         e.printStackTrace();
      }
   }

   private static void loggingIntoNewFile(final File fileAgainstWhichItIsLooged, String line,
      String fileNameWhereItIsLogged) throws IOException
   {
      final File destFile = new File(FILE_PATH_GENERATED + "\\" + fileNameWhereItIsLogged + ".txt");
      if (!destFile.exists())
      {
         destFile.createNewFile();
      }
      try (FileWriter fw = new FileWriter(destFile, true);
               BufferedWriter bw = new BufferedWriter(fw);)
      {
         bw.write(line + "******" + fileAgainstWhichItIsLooged.getName());
         bw.newLine();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   public static boolean checkIfLineExistsInFile(String line, File file, String filePath)
   {
      try (BufferedReader reader =
         new BufferedReader(new FileReader(filePath + "\\" + file.getName())))
      {
         String lineOfNewFile = reader.readLine();
         while (lineOfNewFile != null)
         {
            if (lineOfNewFile.contains(line))
            {
               return true;
            }
            lineOfNewFile = reader.readLine();
         }
      }
      catch (final IOException e)
      {
         e.printStackTrace();
      }
      return false;
   }

   private static boolean checkIfLineExistsInFlow(String line, File file, String filePath)
   {
      try (BufferedReader reader =
         new BufferedReader(new FileReader(filePath + "\\" + file.getName())))
      {
         String lineOfNewFile = reader.readLine();
         while (lineOfNewFile != null)
         {
            if (StringUtils.equalsAnyIgnoreCase(lineOfNewFile, line))
            {
               return true;
            }
            lineOfNewFile = reader.readLine();
         }
      }
      catch (final IOException e)
      {
         e.printStackTrace();
      }
      return false;
   }

   private static List<String> getListOfFilesInThePath(String path)
   {
      final File file = new File(path);
      List<String> listOfFiles = new ArrayList<String>();
      File[] files = file.listFiles();
      for (File f : files)
      {
         listOfFiles.add(StringUtils.split(f.getName(), ".")[0]);
      }
      return listOfFiles;
   }
}