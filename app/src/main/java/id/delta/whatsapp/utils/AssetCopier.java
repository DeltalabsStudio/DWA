package id.delta.whatsapp.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaScannerConnection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AssetCopier {

    private final Context context;
    private final AssetManager assetManager;
    private final List<String> destFiles = new ArrayList<>();
    private boolean shouldScanFiles = false;

    private AssetCopier(){
        throw new IllegalArgumentException("Can't touch this");
    }

    /**
     * Create an AssetCopier with the Context of the app that has the assets to copy
     * @param context the context of the app that has the /assets/
     */
    public AssetCopier(Context context){
        this.context = context;
        this.assetManager = context.getAssets();
    }

    /**
     * Call this if the assets should be scanned for media upon completion of the copy
     * @return the AssetCopier
     */
    public AssetCopier withFileScanning(){
        shouldScanFiles = true;
        return this;
    }

    /**
     * Actually copy the sourcePath to the destDir. The destDir must already exist, but
     * subdirectories will be created as necessary
     * @param sourcePath the path within /assets/ to copy. Can be a directory or file. "" means all of assets.
     * @param destDir The destination directory to copy the file to.
     * @return the number of files copied
     * @throws IOException is thrown if any occurs
     */
    public int copy(final String sourcePath, final File destDir) throws IOException {
        int copies = internalCopy(sourcePath,destDir);
        if (shouldScanFiles){
            MediaScannerConnection.scanFile(context,destFiles.toArray(new String[0]), null, null);
        }
        return copies;
    }

    private int internalCopy(final String sourcePath, final File destDir) throws IOException {
        int count = 0;
        if (!destDir.isDirectory())
            throw new IllegalArgumentException(destDir+" is not a directory");
        if (!destDir.canWrite())
            throw new IllegalArgumentException(destDir+" is not writable");
        String[] list = listIfDirectory(sourcePath);
        if (list==null) {
            // it's a file
            final String destName = sourcePath.contains(File.separator)
                    ? sourcePath.substring(sourcePath.lastIndexOf(File.separator))
                    : sourcePath;
            File destFile = new File(destDir,destName);
            count += copyFile(sourcePath,destFile);
            if (shouldScanFiles) {
                destFiles.add(destFile.getAbsolutePath());
            }
        } else {
            // it's a directory
            for(String item : list){
                final String  itemSourcePath   = sourcePath+File.separator+item;
                final boolean itemIsADirectory = null!=listIfDirectory(itemSourcePath);
                final File    itemDestFile     = itemIsADirectory
                        ? new File(destDir,item)
                        : destDir;
                if (itemIsADirectory) {
                    itemDestFile.mkdirs();
                }
                count += internalCopy(itemSourcePath, itemDestFile);
            }
        }
        return count;
    }

    private int copyFile(final String assetFilePath, final File destFile) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        final byte[] buffer = new byte[1024*16];
        int read;
        try {
            in = assetManager.open(assetFilePath);
            out = new FileOutputStream(destFile);

            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        return 1;
    }

    private String[] listIfDirectory(final String path){
        try {
            String[] list = assetManager.list(path);
            return list.length==0
                    ? null
                    : list;
        } catch (IOException e) {
            return null;
        }
    }
}
