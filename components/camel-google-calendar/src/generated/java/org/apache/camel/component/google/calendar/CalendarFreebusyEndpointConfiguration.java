
/*
 * Camel EndpointConfiguration generated by camel-api-component-maven-plugin
 */
package org.apache.camel.component.google.calendar;

import org.apache.camel.spi.Configurer;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;

/**
 * Camel EndpointConfiguration for com.google.api.services.calendar.Calendar$Freebusy
 */
@UriParams
@Configurer
public final class CalendarFreebusyEndpointConfiguration extends GoogleCalendarConfiguration {

    @UriParam
    private com.google.api.services.calendar.model.FreeBusyRequest content;

    public com.google.api.services.calendar.model.FreeBusyRequest getContent() {
        return content;
    }

    public void setContent(com.google.api.services.calendar.model.FreeBusyRequest content) {
        this.content = content;
    }
}
