package com.getcraftdone.gradle.internal

import com.getcraftdone.gradle.api.ProjectProvider

/**
 * Created by sfaber on 3/5/16.
 */
class AllPluginsDocGenerator {

  public static void main(String[] args) {
    assert args.length == 3 : "Expected 2 arguments: \n" +
            "  1. fully qualified class name of the ProjectProvider implementation\n" +
            "  2. dir path that contains the the plugin id files (e.g. <plugin-id>.properties files, typically it is src/main/resources)"
            "  3. dir path where the output documentation should be generated"

    def projectProviderImpl = args[0]
    ProjectProvider providerImpl = Class.forName(projectProviderImpl).newInstance() as ProjectProvider
    def pluginsIdDir = new File(args[1])
    def outputDir = new File(args[2])

    def plugins = new PluginsFinder().getAllPlugins(pluginsIdDir)
    plugins.each {
      def output = new File(outputDir, "${it}.txt")
      println "Generating documentation for plugin '$it' to file://$output.absolutePath"
      def p = providerImpl.buildProject()
      new PluginDocGenerator().textDoc(p, it, output)
    }
  }
}