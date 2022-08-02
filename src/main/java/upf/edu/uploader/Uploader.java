package upf.edu.uploader;

import java.util.List;

public interface Uploader {

    /**
     * Uploads a file to the target specified through its implementation
     * @param file the file to upload
     */
    void upload(String file);
}
