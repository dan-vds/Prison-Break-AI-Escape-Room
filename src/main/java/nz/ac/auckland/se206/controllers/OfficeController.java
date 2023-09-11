package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

public class OfficeController {

  @FXML private Rectangle deskDrawers;
  @FXML private Rectangle bin;
  @FXML private Rectangle blackBoard;
  @FXML private Rectangle phone;

  @FXML
  private void initialize() {
    System.out.println("OfficeController initialized");
  }

  @FXML
  private void deskDrawersClicked() {
    System.out.println("deskDrawersClicked");
  }

  @FXML
  private void binClicked() {
    System.out.println("binClicked");
  }

  @FXML
  private void blackBoardClicked() {
    System.out.println("blackBoardClicked");
  }

  @FXML
  private void phoneClicked() {
    System.out.println("phoneClicked");
  }

  @FXML
  private void deskDrawersMouseEntered() {
    //TODO make enlarged drawer visable
    System.out.println("deskDrawersEntered");
  }

  @FXML
  private void deskDrawersMouseExited() {
    //TODO make enlarged drawer not visable
    System.out.println("deskDrawersExited");
  }

  @FXML
  private void binMouseEntered() {
    //TODO make enlarged bin visable
    System.out.println("binEntered");
  }

  @FXML
  private void binMouseExited() {
    //TODO make enlarged bin not visable
    System.out.println("binExited");
  }

  @FXML
  private void blackBoardMouseEntered() {
    //TODO make enlarged blackboard visable
    System.out.println("blackBoardEntered");
  }

  @FXML
  private void blackBoardMouseExited() {
    //TODO make enlarged blackboard not visable
    System.out.println("blackBoardExited");
  }

  @FXML
  private void phoneMouseEntered() {
    //TODO make enlarged phone visable
    System.out.println("phoneEntered");
  }

  @FXML
  private void phoneMouseExited() {
    //TODO make enlarged phone not visable
    System.out.println("phoneExited");
  }
}
