package com.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ListModificationUtility
{

   private static final String GENERATED_NEW_FILE = "NewListWhichAreReused";

   private static final String FILE_PATH_GENERATED = "F:";

   private static final String GENERATED_FILE = "ListWhichAreReused";

   public void modifyExistingList() throws IOException
   {
      try (BufferedReader reader =
         new BufferedReader(new FileReader(FILE_PATH_GENERATED + "\\" + GENERATED_FILE + ".txt")))
      {
         String line = reader.readLine();
         while (line != null)
         {
            String valueToBeChecked = StringUtils.split(line, "====")[0];

            List<String> listOfFlowsWhereAllPresent =
               checkAndTellWhereElsePresent(valueToBeChecked, FILE_PATH_GENERATED, GENERATED_FILE);
            logTheMethodCallInDiffFlows(valueToBeChecked, listOfFlowsWhereAllPresent);
            line = reader.readLine();
         }
      }
      catch (final IOException e)
      {
         e.printStackTrace();
      }

   }

   private static List<String> checkAndTellWhereElsePresent(String methodToBeChecked,
      String filePathOfWhereItHasToBeCHecked, String fileNameOnWhichItHasToBeChecked)
   {
      List<String> listOfValues = new ArrayList<String>();
      try (BufferedReader reader = new BufferedReader(new FileReader(
         filePathOfWhereItHasToBeCHecked + "\\" + fileNameOnWhichItHasToBeChecked + ".txt")))
      {
         String line = reader.readLine();
         while (line != null)
         {
            if (line.contains(methodToBeChecked))
            {
               listOfValues.add(StringUtils.split(line, "====")[1]);
            }
            line = reader.readLine();
         }
      }
      catch (final IOException e)
      {
         e.printStackTrace();
      }
      return listOfValues;
   }

   private static void logTheMethodCallInDiffFlows(String valueWhichIsPresent,
      List<String> listOfFlowsWhereAllPresent) throws IOException
   {
      final File fileWhereNewListIsLogged =
         new File(FILE_PATH_GENERATED + "\\" + GENERATED_NEW_FILE + ".txt");
      if (!fileWhereNewListIsLogged.exists())
      {
         fileWhereNewListIsLogged.createNewFile();
      }
      try (FileWriter fw = new FileWriter(fileWhereNewListIsLogged, true);
               BufferedWriter bw = new BufferedWriter(fw);)
      {
         if (!ListGenerationUtility.checkIfLineExistsInFile(
            valueWhichIsPresent + "====" + listOfFlowsWhereAllPresent.toString(),
            fileWhereNewListIsLogged, FILE_PATH_GENERATED))
         {
            bw.write(valueWhichIsPresent + "====" + listOfFlowsWhereAllPresent.toString());
            bw.newLine();
         }
      }
      catch (final IOException e)
      {
         e.printStackTrace();
      }
   }

}
