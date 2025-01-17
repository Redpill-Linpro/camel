/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.main;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.ExtendedPropertyConfigurerGetter;
import org.apache.camel.spi.PropertyConfigurerGetter;
import org.apache.camel.spi.ConfigurerStrategy;
import org.apache.camel.spi.GeneratedPropertyConfigurer;
import org.apache.camel.util.CaseInsensitiveMap;
import org.apache.camel.main.HealthCheckConfigurationProperties;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@SuppressWarnings("unchecked")
public class HealthCheckConfigurationPropertiesConfigurer extends org.apache.camel.support.component.PropertyConfigurerSupport implements GeneratedPropertyConfigurer, PropertyConfigurerGetter {

    @Override
    public boolean configure(CamelContext camelContext, Object obj, String name, Object value, boolean ignoreCase) {
        org.apache.camel.main.HealthCheckConfigurationProperties target = (org.apache.camel.main.HealthCheckConfigurationProperties) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "enabled":
        case "Enabled": target.setEnabled(property(camelContext, boolean.class, value)); return true;
        case "parent":
        case "Parent": target.setParent(property(camelContext, java.lang.String.class, value)); return true;
        default: return false;
        }
    }

    @Override
    public Class<?> getOptionType(String name, boolean ignoreCase) {
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "enabled":
        case "Enabled": return boolean.class;
        case "parent":
        case "Parent": return java.lang.String.class;
        default: return null;
        }
    }

    @Override
    public Object getOptionValue(Object obj, String name, boolean ignoreCase) {
        org.apache.camel.main.HealthCheckConfigurationProperties target = (org.apache.camel.main.HealthCheckConfigurationProperties) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "enabled":
        case "Enabled": return target.isEnabled();
        case "parent":
        case "Parent": return target.getParent();
        default: return null;
        }
    }
}

