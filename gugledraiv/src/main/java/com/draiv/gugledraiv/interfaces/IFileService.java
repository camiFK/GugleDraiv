package com.draiv.gugledraiv.interfaces;

import com.draiv.gugledraiv.entities.*;
import java.util.List;

public interface IFileService {
    public List<File> GetAllFiles();

    public File GetFileById(Long id);
}
