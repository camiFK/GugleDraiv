
INSERT INTO folder (name) VALUES ("Folder1");
INSERT INTO folder (name) VALUES ("Folder2");
INSERT INTO folder (name) VALUES ("Folder3");

-- INSERT INTO users (name) VALUES ("Jane Doe");
-- INSERT INTO users (name) VALUES ("John Doe");
-- INSERT INTO users (name) VALUES ("Mary Jane");


INSERT INTO file (token, system_id, is_folder, file_path, file_ext, file_name, mime_type, content, name, file_hash, upload_date, is_public, fileurl, folder_id)
VALUES 
("token1", "system1", false, "/path/to/file1", ".txt", "file1.txt", "text/plain", "Contenido del archivo 1", "TestFile1", "hash1", NOW(), false, "http://example.com/file1", 1),
("token2", "system1", false, "/path/to/file2", ".jpg", "file2.jpg", "image/jpeg", "Contenido del archivo 2", "TestFile2", "hash2", NOW(), true, "http://example.com/file2", 1),
("token3", "system2", false, "/path/to/file3", ".pdf", "file3.pdf", "application/pdf", "Contenido del archivo 3", "TestFile3", "hash3", NOW(), false, "http://example.com/file3", 2),
("token4", "system2", false, "/path/to/file4", ".docx", "file4.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "Contenido del archivo 4", "TestFile4", "hash4", NOW(), true, "http://example.com/file4", 3),
("token5", "system3", false, "/path/to/file5", ".mp3", "file5.mp3", "audio/mpeg", "Contenido del archivo 5", "TestFile5", "hash5", NOW(), false, "http://example.com/file5", 3),
("token6", "system3", true, "/path/to/folder1", NULL, "folder1", NULL, NULL, "Folder1", NULL, NOW(), true, NULL, NULL); 

-- SELECT f.*
-- FROM gugledraivdb.file f
-- JOIN gugledraivdb.users u ON f.user_id = u.user_id
-- WHERE u.user_id = 1;
