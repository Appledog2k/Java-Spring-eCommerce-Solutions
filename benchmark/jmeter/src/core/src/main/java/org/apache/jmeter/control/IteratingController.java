/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jmeter.control;

import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;

/**
 * Identify controller that does iterations
 * @since 5.0
 */
public interface IteratingController extends LoopIterationListener {

    /**
     * Start next iteration ("continue" keyword equivalent in loops)
     */
    public void startNextLoop();

    /**
     * Break loop ("break" keyword equivalent)
     */
    public void breakLoop();

    /**
     * @param elementName Test Element
     * @param iterCount iteration count
     */
    default void updateIterationIndex(String elementName, int iterCount) {
        JMeterVariables variables = JMeterContextService.getContext().getVariables();
        if(variables != null) {
            variables.putObject(
                    JMeterUtils.formatJMeterExportedVariableName(elementName+GenericController.INDEX_VAR_NAME_SUFFIX), iterCount);
        }
    }

}
