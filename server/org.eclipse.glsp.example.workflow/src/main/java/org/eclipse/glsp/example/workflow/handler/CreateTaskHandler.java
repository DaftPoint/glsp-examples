/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package org.eclipse.glsp.example.workflow.handler;

import java.util.Optional;
import java.util.function.Function;

import org.eclipse.glsp.api.action.kind.AbstractOperationAction;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.example.workflow.utils.ModelTypes;
import org.eclipse.glsp.example.workflow.utils.WorkflowBuilder.TaskNodeBuilder;
import org.eclipse.glsp.example.workflow.wfgraph.WfgraphPackage;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.operationhandler.CreateNodeOperationHandler;
import org.eclipse.glsp.server.util.GModelUtil;

public abstract class CreateTaskHandler extends CreateNodeOperationHandler {

   private final Function<Integer, String> labelProvider;

   public CreateTaskHandler(final String elementTypeId, final Function<Integer, String> labelProvider) {
      super(elementTypeId);
      this.labelProvider = labelProvider;
   }

   @Override
   protected GNode createNode(final Optional<GPoint> point, final GraphicalModelState modelState) {
      int nodeCounter = GModelUtil.generateId(WfgraphPackage.Literals.TASK_NODE, "task", modelState);
      String name = labelProvider.apply(nodeCounter);
      String taskType = ModelTypes.toNodeType(elementTypeId);
      return new TaskNodeBuilder(elementTypeId, name, taskType, 0) //
         .position(point.orElse(null)) //
         .build();
   }

   @Override
   public String getLabel(final AbstractOperationAction action) {
      return "Create task";
   }

}
