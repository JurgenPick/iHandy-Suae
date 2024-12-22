package com.nssus.ihandy.data.util

import android.os.Environment
import org.apache.commons.net.PrintCommandListener
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.PrintWriter

object FIleUtil {

suspend fun uploadFileUsingFTP(
    filePath: String,
    server: String,
    username: String,
    password: String,
    remoteDirectory: String = "/",
    port: Int = 21,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val ftpClient = FTPClient().apply {
        addProtocolCommandListener(PrintCommandListener(PrintWriter(System.out), true)) // Debugging FTP commands
    }
    var inputStream: FileInputStream? = null
    try {
        println("Connecting to FTP server: $server")
        // Connect to the server
        ftpClient.connect(server, port)
        // Check connection status
        if (!ftpClient.isConnected) {
            throw Exception("Failed to connect to FTP server: $server")
        }
        println("Logging in with username: $username")
        // Login to the server
        if (!ftpClient.login(username, password)) {
            throw Exception("Failed to login. Check username/password.")
        }
        // Set Passive Mode and FTP Type
        ftpClient.enterLocalPassiveMode()
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE)
        // Validate the file to upload
        val file = File(filePath)
        if (!file.exists()) {
            throw Exception("File does not exist: $filePath")
        }
        // Prepare for upload
        inputStream = FileInputStream(file)
        val remoteFilePath = "$remoteDirectory/${file.name}".replace("//", "/")
        println("Uploading file: $remoteFilePath")
        // Upload the file
        val success = ftpClient.storeFile(remoteFilePath, inputStream)
        if (success) {
            println("File uploaded successfully to $remoteFilePath")
            onSuccess()
        } else {
            throw Exception("File upload failed for: $remoteFilePath")
        }
    } catch (e: Exception) {
        println("Error: ${e.message}")
        e.printStackTrace()
        onError(e.message ?: "Unknown error occurred during file upload.")
    } finally {
        // Cleanup resources
        inputStream?.close()
        if (ftpClient.isConnected) {
            try {
                ftpClient.logout()
                ftpClient.disconnect()
            } catch (e: Exception) {
                println("Error closing FTP connection: ${e.message}")
            }
        }
    }
}

    fun writeOrAppendTextToFile(
        fileName: String,
        text: String,
        onWriteFileSuccess: () -> Unit,
        onWriteFileError: (String) -> Unit
    ) {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File(downloadsDir, fileName)

        try {
            // Ensure directories exist if a path is included
            if (file.parentFile != null && file.parentFile?.exists() == false)
                file.parentFile?.mkdirs() // Create directories if they don't exist

            BufferedWriter(FileWriter(file, file.exists())).use { writer -> // file.exists() is the checked flag to append text
                writer.write(text)
                writer.newLine() // Add a newline after writing the text if appending
            }

            onWriteFileSuccess()
        } catch (e: Exception) {
            e.printStackTrace()

            onWriteFileError(e.message ?: "Failed to write to file")
        }
    }
}