package utils;

import android.util.Log;

import com.hs.userportal.Directory;
import com.hs.userportal.DirectoryFile;

/**
 * Created by ravimathpal on 15/04/17.
 */

public class DirectoryUtility {

    private Directory searchedResults = new Directory("SearchDirectory");

    public static void addObject(Directory directory, DirectoryFile file, String path) {
        //recursive method to set file in directory
        String name;
        if (path.contains("/")) {
            name = path.substring(0, path.indexOf("/", 0));
        } else {
            name = path;
        }

        if (isFile(name)) {
            if (!directory.hasFile(name)) {
                directory.addFile(file);
            }
        } else {
            //if it is a folder, then add new folder object in current directory recursively
            if (name.equals("ZurekaTempPatientConfig")) {

            } else {
                if (directory.hasDirectory(name)) {
                    addObject(directory.getDirectory(name), file, removeOneDirectory(path));
                } else {
                    Directory newDirectory = new Directory(name);
                    directory.addDirectory(newDirectory);
                    String newPath = removeOneDirectory(path);
                    addObject(newDirectory, file, newPath);
                }
            }
        }
    }

    public static boolean isFile(String s) {
//        to check if string is a valid file name.. be sure not to include / in string
        s.toLowerCase();
        if (s.endsWith(".jpg")
                || s.endsWith(".png")
                || s.endsWith(".pdf")
                || s.endsWith(".xlx")) {
            return true;
        } else {
            return false;
        }
    }

    public static String removeOneDirectory(String path) {
        //this method trims the path to one directory short
        String newString = path.substring(path.indexOf("/", 0) + 1);
        return newString;
    }

    public static String removeExtra(String path) {
//        this method removes junk from front
        String[] splitted = path.split("/");
        String reducedString = "";
        for (int i = 3; i < splitted.length; i++) {
            reducedString = reducedString + splitted[i];
            if (i != splitted.length - 1) {
                reducedString = reducedString + "/";
            }
        }
        return reducedString;
    }

    public static String getFileName(String key) {
        if (key.contains("ZurekaTempPatientConfig")) {
            return "ZurekaTempPatientConfig";
        } else {
            //this method just gets the name of file
            Log.e("Rishabh Jain", "All files := "+key);
            String[] split = key.split("/");
            return split[split.length - 1];
        }
    }

    public static Directory searchDirectory(Directory homeDirectory, String searchedItem) {

        Directory searchedResults = new Directory("SearchResults");

        homeDirectory.search(searchedResults, searchedItem);

        return searchedResults;

    }


}
