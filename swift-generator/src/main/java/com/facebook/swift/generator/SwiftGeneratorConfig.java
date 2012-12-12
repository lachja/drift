/*
 * Copyright (C) 2012 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.facebook.swift.generator;

import java.io.File;

public class SwiftGeneratorConfig
{
    private final File inputFolder;
    private final File [] inputFiles;
    private final File outputFolder;
    private final String defaultPackage;

    public SwiftGeneratorConfig(final File inputFolder,
                                final File [] inputFiles,
                                final File outputFolder,
                                final String defaultPackage)
    {
        this.inputFolder = inputFolder;
        this.inputFiles = inputFiles;
        this.outputFolder = outputFolder;
        this.defaultPackage = defaultPackage;
    }

    public File getInputFolder()
    {
        return inputFolder;
    }

    public File[] getInputFiles()
    {
        return inputFiles;
    }

    public File getOutputFolder()
    {
        return outputFolder;
    }

    public String getDefaultPackage()
    {
        return defaultPackage;
    }
}
