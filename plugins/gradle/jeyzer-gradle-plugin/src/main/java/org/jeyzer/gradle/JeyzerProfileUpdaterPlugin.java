package org.jeyzer.gradle;

/*-
 * ---------------------------LICENSE_START---------------------------
 * Jeyzer Profile Updater Maven Plugin
 * --
 * Copyright (C) 2020 - 2023 Jeyzer
 * --
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 * ----------------------------LICENSE_END----------------------------
 */

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class JeyzerProfileUpdaterPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getExtensions().create("profileUpdateJeyzerTaskSettings", JeyzerProfileUpdaterPluginExtension.class);
        project.getTasks().create("profileUpdateJeyzerTask", JeyzerProfileUpdaterTask.class);
    }
	
}
