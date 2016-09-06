package org.asciidoctor.extension

import org.asciidoctor.extension.spi.ExtensionRegistry
import org.asciidoctor.extension.JavaExtensionRegistry
import org.asciidoctor.Asciidoctor
 
import groovy.transform.CompileStatic
 
@CompileStatic
class GitIncludeExtension implements ExtensionRegistry {
 
    @Override
    void register(final Asciidoctor asciidoctor) {
        final JavaExtensionRegistry javaExtensionRegistry = asciidoctor.javaExtensionRegistry();
        javaExtensionRegistry.includeProcessor(GitIncludeMacro.class);
    }
}