
INSERT INTO users (token, expiresIn) VALUES ("token1", "3600"); 
INSERT INTO users (token, expiresIn) VALUES ("token2", "3600"); 
INSERT INTO users (token, expiresIn) VALUES ("token3", "3600"); 

INSERT INTO file (systemId, isFolder, filePath, fileExt, fileName, mimeType, content, fileHash, uploadDate, isPublic, fileUrl, folderId, userId)
VALUES 
-- Carpetas (isFolder = true)
("3", true, "/folder1", NULL, "Folder1", NULL, NULL, NULL, NOW(), false, NULL, NULL, 1),
("3", true, "/folder2", NULL, "Folder2", NULL, NULL, NULL, NOW(), false, NULL, NULL, 1),
("3", true, "/folder3", NULL, "Folder3", NULL, NULL, NULL, NOW(), false, NULL, NULL, 2),
("3", true, "/folder4", NULL, "Folder4", NULL, NULL, NULL, NOW(), false, NULL, NULL, 2), -- Carpeta vacía

-- Archivos (isFolder = false) dentro de carpetas
("3", false, "/folder1/file1.txt", ".txt", "file1.txt", "text/plain", "Contenido del archivo 1", "hash1", NOW(), false, "http://localhost:8082/draiv/file1", 1, 1),
("3", false, "/folder1/file2.jpg", ".jpg", "file2.jpg", "image/jpeg", "Contenido del archivo 2", "hash2", NOW(), true, "http://localhost:8082/draiv/file2", 1, 1),
("3", false, "/folder2/file3.pdf", ".pdf", "file3.pdf", "application/pdf", "Contenido del archivo 3", "hash3", NOW(), false, "http://localhost:8082/draiv/file3", 2, 2),
("3", false, "/folder3/file4.docx", ".docx", "file4.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "Contenido del archivo 4", "hash4", NOW(), true, "http://localhost:8082/draiv/file4", 3, 2),
("3", false, "/folder3/file5.mp3", ".mp3", "file5.mp3", "audio/mpeg", "Contenido del archivo 5", "hash5", NOW(), false, "http://localhost:8082/draiv/file5", 3, 3);