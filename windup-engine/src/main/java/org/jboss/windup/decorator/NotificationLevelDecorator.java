/*
 * Copyright (c) 2013 Red Hat, Inc. and/or its affiliates.
 *  
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *      Brad Davis - bradsdavis@gmail.com - Initial API and implementation
*/
package org.jboss.windup.decorator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.windup.hint.MatchingProcessor;
import org.jboss.windup.resource.decoration.AbstractDecoration;
import org.jboss.windup.resource.decoration.AbstractDecoration.NotificationLevel;
import org.jboss.windup.resource.type.FileMeta;


public class NotificationLevelDecorator implements MetaDecorator<FileMeta> {
	private static final Log LOG = LogFactory.getLog(NotificationLevelDecorator.class);

	protected List<MatchingProcessor> matchingProcessors;
	protected NotificationLevel notificationLevel;

	public void setNotificationLevel(NotificationLevel notificationLevel) {
		this.notificationLevel = notificationLevel;
	}

	public void setMatchingProcessors(List<MatchingProcessor> matchingProcessors) {
		this.matchingProcessors = matchingProcessors;
	}

	@Override
	public void processMeta(FileMeta file) {
		for (AbstractDecoration dr : file.getDecorations()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Applying notification to: " + matchingProcessors.size() + " for decoration: " + dr.getPattern());
			}
			for (MatchingProcessor hp : matchingProcessors) {
				// if it matches, set it.
				if (hp.process(dr)) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("Matched.  Setting Notification: " + notificationLevel);
					}
					dr.setLevel(notificationLevel);
				}
			}
		}
	}
}
