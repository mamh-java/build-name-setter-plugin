package org.jenkinsci.plugins;

import java.io.IOException;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.jenkinsci.plugins.buildnamesetter.Executor;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class BuildDescriptionStep extends Builder implements SimpleBuildStep {

    private final String descriptionName;

    @DataBoundConstructor
    public BuildDescriptionStep(String descriptionName) {
        this.descriptionName = descriptionName;
    }

    public String getDescriptionName() {
        return descriptionName;
    }

    @Override
    public void perform(Run run, FilePath workspace, Launcher launcher, TaskListener listener)
            throws IOException {
        Executor executor = new Executor(run, listener);
        executor.setDescription(descriptionName);
    }

    @Symbol("setBuildDescription")
    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Builder> {

        @Override
        public String getDisplayName() {
            return "Changes build description";
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> t) {
            return true;
        }
    }
}