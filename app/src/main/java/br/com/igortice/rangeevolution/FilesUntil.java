package br.com.igortice.rangeevolution;

import android.os.Environment;

import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;
import com.sromku.simple.storage.helpers.OrderType;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by igortice on 18/05/16.
 */
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


}
