/*
 * Copyright (c) 2021 Talent Beyond Boundaries.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License 
 * along with this program. If not, see https://www.gnu.org/licenses/.
 */

package org.tbbtalent.server.service.db;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.tbbtalent.server.util.filesystem.GoogleFileSystemDrive;
import org.tbbtalent.server.util.filesystem.GoogleFileSystemFile;
import org.tbbtalent.server.util.filesystem.GoogleFileSystemFolder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Standard interface to Google filesystem - tailored to Talent Catalog's use for
 * storing candidate data - in particular uploaded files (eg CVs)
 *
 * @author John Cameron
 */
public interface FileSystemService {

    /**
     * Finds a folder with the given name.
     * If there is more than one folder with that name, any one could be
     * returned.
     * @param folderName Name of folder being searched for
     * @return Found folder, null if no folder found
     * @throws IOException If problem accessing file system
     */
    @Nullable
    GoogleFileSystemFolder findAFolder(
        GoogleFileSystemDrive drive, GoogleFileSystemFolder parentFolder, String folderName) 
        throws IOException;
    
    /**
     * Creates a file with the given name.
     * Does not check if file with that name already exists - may create
     * create a duplicate file with the same name if the file system allows it. 
     * @param fileName Name of folder to be created
     * @param mimeType Type of file - see https://developers.google.com/drive/api/v3/mime-types
     * @return File created
     * @throws IOException If there was a problem creating the file
     */
    @NonNull
    GoogleFileSystemFile createFile(
        GoogleFileSystemDrive drive, GoogleFileSystemFolder parentFolder, String fileName, 
        String mimeType) throws IOException;
    
    /**
     * Creates a folder with the given name.
     * Does not check if folder with that name already exists - may create
     * create a duplicate folder with the same name if the file system allows it. 
     * @param folderName Name of folder to be created
     * @return Folder created
     * @throws IOException If there was a problem creating the folder
     */
    @NonNull
    GoogleFileSystemFolder createFolder(
        GoogleFileSystemDrive drive, GoogleFileSystemFolder parentFolder, String folderName) 
        throws IOException;

    /**
     * Deletes the given file
     * @param file Describes file to be deleted
     * @throws IOException If there was a problem deleting the file
     */
    void deleteFile(GoogleFileSystemFile file) throws IOException;

    /**
     * Downloads the given file into the given OutputStream
     * @param file Describes file to be downloaded
     * @param out Stream to write the contents of the file to
     * @throws IOException If there was a problem uploading the file.
     */
    void downloadFile(@NonNull GoogleFileSystemFile file, @NonNull OutputStream out)
            throws IOException;

    /**
     * Renames the given file 
     * @param file Description of file, including id or url, plus its new name
     * @throws IOException If there was a problem renaming the file
     */
    void renameFile(@NonNull GoogleFileSystemFile file) throws IOException;
    
    /**
     * Uploads the given local file.
     * @param parentFolder Folder that file should be uploaded to. If null, 
     *                     file is uploaded to root.
     * @param fileName Name assigned to uploaded file
     * @param file Local file to be uploaded
     * @return Info about uploaded file on the remote file system 
     * @throws IOException If there was a problem uploading the file.
     */
    @NonNull
    GoogleFileSystemFile uploadFile(GoogleFileSystemDrive drive,
        @Nullable GoogleFileSystemFolder parentFolder, String fileName, File file) 
            throws IOException;

    /**
     * This creates a copy of a Google document and places it in the parent folder
     * under the provided copy title.
     * @param parentFolder - this is the folder where the new copy will belong.
     * @param copyTitle - this is the name for the new copy.
     * @param sourceFile - this is the file to be copied.
     * @throws IOException If there was a problem copying the file.
     */
    GoogleFileSystemFile copyFile(
        GoogleFileSystemFolder parentFolder, String copyTitle, GoogleFileSystemFile sourceFile) 
        throws IOException;
}
