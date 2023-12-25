/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fhws.fiw.fds.sutton.server;

import de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "de.fhws.fiw.fds")
public abstract class AbstractStart {

    public static String CONTEXT_PATH;

    @Value("${server.servlet.context-path}")
    private void setContextPath(String contextPath) {
        AbstractStart.CONTEXT_PATH = contextPath;
    }

    protected void startSpring(String[] args) {
        SpringApplication.run(AbstractStart.class, args);

        new DatabaseInstaller().install();
    }
}
