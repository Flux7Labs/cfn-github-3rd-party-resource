package com.flux7.service.github;

import org.json.JSONObject;
import org.kohsuke.github.*;
import software.amazon.cloudformation.proxy.AmazonWebServicesClientProxy;
import software.amazon.cloudformation.proxy.Logger;
import software.amazon.cloudformation.proxy.ProgressEvent;
import software.amazon.cloudformation.proxy.OperationStatus;
import software.amazon.cloudformation.proxy.ResourceHandlerRequest;

import java.io.IOException;

public class UpdateHandler extends BaseHandler<CallbackContext> {

    @Override
    public ProgressEvent<ResourceModel, CallbackContext> handleRequest(
            final AmazonWebServicesClientProxy proxy,
            final ResourceHandlerRequest<ResourceModel> request,
            final CallbackContext callbackContext,
            final Logger logger) {

        final ResourceModel model = request.getDesiredResourceState();
        try {
            // TODO : put your code here

//            String repoName = model.getRepositoryName();
//            String gitToken = model.getRepositoryAccessToken();
//            String repoDescription = model.getRepositoryDescription();

//            GitHub github = new GitHubBuilder().withOAuthToken(gitToken).build();



            GitHub github = new GitHubBuilder().withOAuthToken(model.getRepositoryAccessToken()).build();

            GHMyself ghm = github.getMyself();
            String username = ghm.getLogin();

            if (model.getOrganizationName() == null) {
                GHRepository repo = github.getRepository(username + "/" + model.getRepositoryName());
                repo.setDescription(model.getRepositoryDescription());
            } else {
                GHRepository repo = github.getRepository(model.getOrganizationName()+"/" + model.getRepositoryName());
                repo.setDescription(model.getRepositoryDescription());
            }

//            System.out.println("New description : " + repoDescription);

        } catch (NullPointerException e) {

            e.printStackTrace();

        }  catch (IOException e) {

            e.printStackTrace();

        }
        catch (IllegalStateException e) {
            e.printStackTrace();
        }
        // TODO : your code end here
        return ProgressEvent.<ResourceModel, CallbackContext>builder()
                .resourceModel(model)
                .status(OperationStatus.SUCCESS)
                .build();
    }
}
