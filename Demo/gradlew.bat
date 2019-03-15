/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.internal.os;

import android.os.FileUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Helper class for performing atomic operations on a file, by creating a
 * backup file until a write has successfully completed.
 * <p>
 * Atomic file guarantees file integrity by ensuring that a file has
 * been completely written and sync'd to disk before removing its backup.
 * As long as the backup file exists, the original file is considered
 * to be invalid (left over from a previous attempt to write the file).
 * </p><p>
 * Atomic file does not confer any file locking semantics.
 * Do not use this class when the file may be accessed or modified concurrently
 * by multiple threads or processes.  The caller is responsible for ensuring
 * appropriate mutual exclusion invariants whenever it accesses the file.
 * </p>
 */
public final class AtomicFile {
    private final File mBaseName;
    private final File mBackupName;
    
    public AtomicFile(File baseName) {
        mBaseName = baseName;
        mBackupName = new File(baseName.getPath() + ".bak");
    }
    
    public File getBaseFile() {
        return mBaseName;
    }
    
    public FileOutputStream startWrite() throws IOException {
        // Rename the current file so it may be used as a backup during the next read
        if (mBaseName.exists()) {
            if (!mBackupName.exists()) {
                if (!mBaseName.renameTo(mBackupName)) {
                    Log.w("AtomicFile", "Couldn't rename file