package nz.ac.auckland.se206.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

public class SideConversationController {

  @FXML private TextArea prisonerOneTextArea;
  @FXML private TextArea prisonerTwoTextArea;

  public String prisonerOneText = "";
  public String prisonerTwoText = "";

  public ChatCompletionRequest groupChatCompletionRequest =
      new ChatCompletionRequest().setN(1).setTemperature(0.2).setTopP(0.5).setMaxTokens(100);
  private String playerName = "player";


  private void initialize() {
    String message = GptPromptEngineering.groupConversationPrompt(playerName);
    groupChatCompletionRequest.addMessage(new ChatMessage("user", message));
  }

  private void runGpt(ChatCompletionRequest chatCompletionRequest) throws ApiProxyException {
    // run the GPT model in a background thread
    Task<Void> backgroundTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            try {
              ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
              Choice result = chatCompletionResult.getChoices().iterator().next();
              chatCompletionRequest.addMessage(result.getChatMessage());
              return null;
            } catch (ApiProxyException e) {
              ChatMessage error = new ChatMessage("assistant", "Error: \n" + "GPT not working");
              chatCompletionRequest.addMessage(error);
              e.printStackTrace();
              return null;
            }
          }
        };
    Thread gptThread = new Thread(null, backgroundTask);
    gptThread.start();
  }

  private void displayPrisonerOneMessage() {
    prisonerOneTextArea.setText(prisonerOneText);
  }

  private void displayPrisonerTwoMessage() {
    prisonerTwoTextArea.setText(prisonerTwoText);
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public void refreshMessages(String prompt){
    groupChatCompletionRequest.addMessage(new ChatMessage("user", prompt));
    try {
      runGpt(groupChatCompletionRequest);
    } catch (ApiProxyException e) {
      e.printStackTrace();
    }



  }
}
