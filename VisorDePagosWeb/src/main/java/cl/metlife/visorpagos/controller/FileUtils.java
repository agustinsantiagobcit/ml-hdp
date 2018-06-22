package cl.metlife.visorpagos.controller;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.apache.commons.io.IOUtils.toByteArray;

public class FileUtils {

    private static Logger logger = Logger.getLogger(FileUtils.class);

    /**
     * This method is responsible for checking whether a specific file is reachable.
     *
     * @param filePath The path of the file to be checked.
     * @return <code>true</code> if the file is reachable and <code>false</code> otherwise.
     */
    public static boolean connect(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * This method is responsible for returning an array containing all the files contained in the given Directory.
     *
     * @param theFolder The directory from which the files contained are listed.
     * @return A list with a reference to all the files contained the <code>theFolder</code>.
     * @throws java.io.FileNotFoundException Thrown if <code>theFolder</code> does not exist.
     */
    public static List<File> getFilesFromFolder(File theFolder) throws FileNotFoundException {

        /* If the file does not exists, an exception is thrown */
        if (!theFolder.exists()) {
            throw new FileNotFoundException(theFolder.getAbsolutePath());
        }

        /* If the folder is a file, an empty list is returned */
        if (!theFolder.isDirectory()) {
            return emptyList();
        }

        File[] files = theFolder.listFiles();
        return Arrays.asList(files);
    }

    /**
     * @return
     * @throws java.io.FileNotFoundException
     */
    public static List<SmbFile> getFilesFromSambaFolder(String host, String username, String password,
                                                        String path) throws SmbException, MalformedURLException {
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, username, password);
        SmbFile dir = new SmbFile("smb://" + host + "/" + path, auth);

        SmbFile[] smbFiles = dir.listFiles();
        return Arrays.asList(smbFiles);
    }

    /**
     * This method is responsible for retrieving the folders children of a given folder.
     *
     * @param directory The directory whose content is to be searched.
     * @return A list of folders contained in the given folder.
     * @throws java.io.FileNotFoundException  If the given <code>directory</code> does not exists.
     * @throws cl.blueprintsit.utils.NotADirectoryException If the given <code>directory</code> is not a folder.
     */
    public static List<File> getDirectoryFromFolder(File directory) throws FileNotFoundException,
            NotADirectoryException {

        /* Basic validations */
        if (directory == null) return Collections.emptyList();
        if (!directory.exists()) throw new FileNotFoundException(directory.getPath());
        if (!directory.isDirectory()) throw new NotADirectoryException();

        /* The listing of directories */
        FileFilter directoryFilter = new DirectoryFilter();
        return Arrays.asList(directory.listFiles(directoryFilter));
    }

    /**
     * This method is responsible for reading the bytes from a file and returning them in a byte array.
     *
     * @param file The file from which the contents are read.
     * @return The file's content in an byte array.
     * @throws java.io.IOError Thrown if the file does not exist or if there are problems while reading it..
     */
    public static byte[] getContents(File file) throws IOException {

        /* If there is no such file, the exception is thrown */
        if (!file.exists())
            throw new FileNotFoundException("The file does not exist.");

        FileInputStream input = new FileInputStream(file);
        return toByteArray(input);
    }

    public static File createFile(HttpServletRequest req) throws FileUploadException {
        int maxMemSize = 10000 * 1024;
        int maxFileSize = 10000 * 1024;

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(maxMemSize);
        factory.setRepository(new File("C:\\temp"));

        /* A handler for the file */
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(maxFileSize);
        upload.setHeaderEncoding("UTF-8");

        /* Parse the request to get file items. */
        List<FileItem> fileItems = upload.parseRequest(req);
        logger.debug("Files uploaded: " + fileItems);

        /* Simple validation */
        if (fileItems.size() == 0) {
            throw new FileUploadException("No file was uploaded.");
        }

        /* The content file is retrieved */
        FileItem fileItem = fileItems.get(0);
        String fileName = fileItem.getName();

        File uploadedFile = new File(getFileName(fileName));
        try {
            fileItem.write(uploadedFile);
        } catch (Exception e) {
            logger.error("Error when writing the uploaded file.", e);
        }

        return uploadedFile;
    }

    public static List<FileItem> getFileItemsFromRequest(HttpServletRequest request) throws FileUploadException {
        int maxMemSize = 10000 * 1024;
        int maxFileSize = 10000 * 1024;

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(maxMemSize);
        factory.setRepository(new File("C:\\temp"));

        /* A handler for the file */
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(maxFileSize);
        upload.setHeaderEncoding("UTF-8");

        /* Parse the request to get file items. */
        List<FileItem> fileItems = upload.parseRequest(request);
        logger.debug("Files uploaded: " + fileItems);

        /* Simple validation */
        if (fileItems.size() == 0) {
            throw new FileUploadException("No file was uploaded.");
        }

        return fileItems;
    }

    public static File getUploadedFile(FileItem fileItem) {

        String fileName = fileItem.getName();
        File uploadedFile = new File(getFileName(fileName));
        try {
            fileItem.write(uploadedFile);
        } catch (Exception e) {
            logger.error("Error when writing the uploaded file.", e);
        }

        return uploadedFile;
    }

    public static File setContents(byte[] fileContent, String fileName) throws IOException {

        String theFileName;
        if (fileName == null || fileName.trim().equals("")) {
            theFileName = "";
        } else {
            theFileName = fileName;
        }

        URL resource = FileUtils.class.getClassLoader().getResource(".");
        File tempFile = new File(resource.getPath() + "/" + theFileName + System.currentTimeMillis() + ".pdf");
        boolean newFile = tempFile.createNewFile();

        if (!newFile) {
            String errorMessage = "The temporary file could not be created.";
            logger.error(errorMessage);
            throw new IOException(errorMessage);
        }

        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        fileOutputStream.write(fileContent);
        fileOutputStream.close();

        return tempFile;
    }

    /**
     * This method extracts the name of the file given by an eventually absolute path.
     *
     * @param filePath The path of a file, eventually absolute.
     * @return The name of the file given.
     */
    public static String getFileName(String filePath) {

        int fileNameIndex = filePath.lastIndexOf("/");
        if (fileNameIndex > 0) {
            return filePath.substring(fileNameIndex + 1);
        }

        /* Otherwise, the filename is extracted */
        fileNameIndex = filePath.lastIndexOf("\\");
        if (fileNameIndex > 0) {
            return filePath.substring(fileNameIndex + 1);
        }

        return filePath;
    }

    /**
     * This method is responsible for determining if a given path is absolute or relative.
     *
     * @param path The path to be checked.
     * @return <code>true</code> if the path is absolute and <code>false</code> otherwise.
     */
    public static boolean isAbsolutePath(String path) {
        return (path.contains("/") || path.contains("\\"));
    }

    public static String contentToString(File theFile) throws FileNotFoundException {
        if (!theFile.exists()) {
            logger.error("The file must exist");
            return "";
        }

        Reader reader = new FileReader(theFile);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        String result = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                result += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return result;
    }

    public static String getFileNameWithoutExtension(String absolutePath) {
        String theName = getFileName(absolutePath);
        int extensionIndex = theName.indexOf('.');
        if (extensionIndex > 0) {
            return theName.substring(0, extensionIndex);
        }

        return theName;
    }

    public static boolean assureFolder(File folder) {

        /* If the job is done... */
        if (folder.exists() && folder.isDirectory()) {
            logger.debug("Folder " + folder + " is assured.");
            return true;
        }

        if (folder.exists() && !folder.isDirectory()) {
            throw new IllegalArgumentException("The given folder '" + folder.getPath() + "' exists and it is a file.");
        }

        return folder.mkdir();
    }

    /**
     * This method is responsible for assuring a given <code>file</code> exists and it is a file (not a folder).
     *
     * @param file The file to be validate.
     * @throws java.io.FileNotFoundException Thrown if the given <code>file</code> does not exist.
     */
    public static void validateFile(File file) throws FileNotFoundException {

        if (file == null) {
            throw new IllegalArgumentException("The file is not expected to be null");
        }

        if (file.exists() && file.isFile()) {
            logger.debug("File " + file + " is assured.");
            return;
        }

        if (!file.exists()) {
            String message = "The file does not exist.";
            logger.error(message);
            throw new FileNotFoundException(message);
        }

        if (!file.isFile()) {
            String message = "The file is not a file, but something else";
            logger.error(message);
            throw new FileNotFoundException(message);
        }
    }

    /**
     * This method is responsible for validating whether the given <code>folderToValidate</code> is not null,
     * exists and it is a folder.
     * <p/>
     * If the folder does not exist no attempt to create it will be performed.
     *
     * @throws IllegalArgumentException Thrown if the given <code>folderToValidate</code> argument is null.
     * @throws java.io.FileNotFoundException    Thrown if the given <code>folderToValidate</code> argument does not exist.
     */
    public static void validateFolder(File folderToValidate) throws FileNotFoundException {

        /* A null folder is not valid argument */
        if (folderToValidate == null) {
            String message = "The folder is not expected to be null.";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }

        /* The folder is validated for existence */
        if (!folderToValidate.exists()) {
            String message = "The specified folder path (" + folderToValidate.getAbsolutePath() + ") does not exist.";
            logger.error(message);
            throw new FileNotFoundException(message);
        }

        /* Validating that the folder is a directory */
        if (!folderToValidate.isDirectory()) {
            logger.error("The specified folder is not a folder.");
            throw new IllegalArgumentException("The specified folder (" + folderToValidate.getAbsolutePath() + ") " +
                    "is not a folder");
        }
    }

    public static boolean isLocked(File file) {
        try {
            org.apache.commons.io.FileUtils.touch(file);
            return false;
        } catch (IOException e) {
            return true;
        }
    }
}

class DirectoryFilter implements FileFilter {
    @Override
    public boolean accept(File f) {
        return f.isDirectory();
    }
}
