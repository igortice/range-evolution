package br.com.igortice.rangeevolution;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;
import com.sromku.simple.storage.helpers.OrderType;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FilesUntil {
    public static final String FOLDER_RAIZ = "RangeEvolution";

    public static Storage getStorage() {
        return SimpleStorage.getExternalStorage();
    }

    public static void createFolderRaiz() {
        boolean dirExists = getStorage().isDirectoryExists(FOLDER_RAIZ);
        if (!dirExists)
            getStorage().createDirectory(FOLDER_RAIZ);
    }

    public static File createTmpFileVazio() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    public static List<File> getFoldersNames() {
        return getStorage().getFiles(FOLDER_RAIZ, OrderType.DATE);
    }

    public static ArrayList<String> getCategoriasFoldersNames() {
        List<File> files = getFoldersNames();
        ArrayList<String> itensList = new ArrayList<>(Arrays.asList("Escolha a categoria"));
        for (File f : files) {
            itensList.add(f.getName().toString());
        }
        Collections.sort(itensList);

        return itensList;
    }

    public static ArrayList<String> getItensFolderName(String folderName) {
        List<File> files = getStorage().getFiles(getFolderPath(folderName), OrderType.DATE);
        ArrayList<String> itensList = new ArrayList<>();
        for (File f : files) {
            String inputString = f.getName().toString().replace(".jpg", "");
            inputString = dateFormatToHuman(inputString);
            itensList.add(inputString);
        }
        Collections.sort(itensList, Collections.<String>reverseOrder());

        return itensList;
    }

    public static ArrayList<String> getItensFolderNameFullPath(String folderName) {
        List<File> files = getStorage().getFiles(getFolderPath(folderName), OrderType.DATE);
        ArrayList<String> itensList = new ArrayList<>();
        for (File f : files) {
            String pathImg = getFolderPath(folderName) + "/" + f.getName().toString();
            itensList.add(pathImg);
        }

        return itensList;
    }

    private static String dateFormatToHuman(String date_s) {
        String inputString = date_s;
        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = null;
        try {
            date = dt.parse(inputString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        return dt1.format(date);
    }

    static String dateHumanToFormat(String date_s) {

        String inputString = date_s;
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        Date date = null;
        try {
            date = dt.parse(inputString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return dt1.format(date);
    }

    static Bitmap getImageBitmap(String path) {
        String pathImg = Environment.getExternalStorageDirectory() + "/" + path;

        return BitmapFactory.decodeFile(pathImg);
    }

    public static String getFolderPath(String folder) {
        return FOLDER_RAIZ + "/" + folder;
    }
}
