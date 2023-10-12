package nz.ac.auckland.se206;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PlayHistory implements Serializable {
  private double score;
  private int timeTook;
  private int difficulty;
  private String name;
  private PlayHistory childPlayHistory = null;
  private PlayHistory parentPlayHistory = null;
  private int playerAvatar;

  // Compare this snippet from src/main/java/nz/ac/auckland/se206/PlayHistory.java:
  public PlayHistory(int time, int difficulty, String name, int playerAvatar) {
    this.name = name;
    this.score = time / difficulty;
    this.timeTook = time;
    this.difficulty = difficulty;
    this.playerAvatar = playerAvatar;
  }

  // Compare this snippet from src/main/java/nz/ac/auckland/se206/PlayHistory.java:
  // you can Add a play history to the play history using this method with any given play history
  // node
  // it finds the correct place to add the play history
  public void addHistory(PlayHistory playHistory) {
    // if the score is greater than the current score,, then it is added to the parent
    if (playHistory.getScore() > this.score) {
      if (this.parentPlayHistory == null) {
        playHistory.setChildPlayHistory(this);
        this.parentPlayHistory = playHistory;
        return;
      } else {
        // otherwise it is added to the parent of the current play history
        if (getParentPlayHistory().getScore() > playHistory.getScore()) {
          playHistory.setParentPlayHistory(this.parentPlayHistory);
          playHistory.setChildPlayHistory(this);
          getParentPlayHistory().setChildPlayHistory(playHistory);
          this.setParentPlayHistory(playHistory);
          return;
        } else {
          this.parentPlayHistory.addHistory(playHistory);
          return;
        }
      }
    } else {
      // if the score is less than the current score, then it is added to the child
      if (this.childPlayHistory == null) {
        this.childPlayHistory = playHistory;
        playHistory.setParentPlayHistory(this);
        return;
      }
      if (this.childPlayHistory.getScore() < playHistory.getScore()) {
        playHistory.setChildPlayHistory(this.childPlayHistory);
        playHistory.setParentPlayHistory(this);
        this.childPlayHistory.setParentPlayHistory(playHistory);
        this.childPlayHistory = playHistory;
        return;
      } else {
        this.childPlayHistory.addHistory(playHistory);
        return;
      }
    }
  }

  // This is the function that creates a string to display the play history
  // It dis plays the name, time and difficulty of the player
  public List<List<Object>> getFullList() {
    // setting up the variables
    List<List<Object>> playHistoryList = new ArrayList<>();
    PlayHistory playHistory = this;
    if (this.childPlayHistory != null) {
      return this.childPlayHistory.getFullList();
    } else {
      int rank = 1;
      do {
        List<Object> playHistoryHBox = new ArrayList<>();

        // HBox playHistoryHBox = new HBox();
        // get past results and format in the right way
         String result =
            ("Rank "
                + rank
                + ":\n "
                + playHistory.getName()
                + "\n"
                + " Time: "
                + playHistory.getTimeTook()
                + "\n Difficulty: "
                + playHistory.getDifficulty()
                + "\n\n");
        playHistoryHBox.add(result);
        Integer avatarNumber = playHistory.getPlayerAvatar();
        avatarNumber++;
        playHistoryHBox.add(avatarNumber);
        playHistoryList.add(playHistoryHBox);
        // Text text = new Text(result);
        // Image image = playerAvatarOne;
        // int avatarNumber = playHistory.getPlayerAvatar();
        // avatarNumber++;
        // if(avatarNumber == 1){
        //   image = playerAvatarOne;
        // }else if(avatarNumber == 2){
        //   image = playerAvatarTwo;
        // }else if(avatarNumber == 3){
        //   image = playerAvatarThree;
        // }else if(avatarNumber == 4){
        //   image = playerAvatarFour;
        // }else if(avatarNumber == 5){
        //   image = playerAvatarFive;
        // }
        // ImageView avatar = new ImageView(image);
        // text.setWrappingWidth(150);
        // avatar.setFitHeight(70);
        // avatar.setFitWidth(70);
        // playHistoryHBox.getChildren().add(avatar);
        // playHistoryHBox.getChildren().add(text);
        // playHistoryVBox.getChildren().add(playHistoryHBox);
        // playHistory = playHistory.getParentPlayHistory();
        // rank++;
      } while (playHistory != null);
      return playHistoryList;
    }
  }

  // This is the method for saving the play history
  public void saveHistory() {
    try (ObjectOutputStream oos =
        new ObjectOutputStream(new FileOutputStream("player_history.dat"))) {
      oos.writeObject(this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // the getters and setters for the play history
  public double getScore() {
    return score;
  }

  public int getTimeTook() {
    return timeTook;
  }

  public int getDifficulty() {
    return difficulty;
  }

  public String getName() {
    return name;
  }

  public PlayHistory getParentPlayHistory() {
    return parentPlayHistory;
  }

  public PlayHistory getChildPlayHistory() {
    return childPlayHistory;
  }

  public void setParentPlayHistory(PlayHistory parentPlayHistory) {
    this.parentPlayHistory = parentPlayHistory;
  }

  public void setChildPlayHistory(PlayHistory childPlayHistory) {
    this.childPlayHistory = childPlayHistory;
  }

  public int getPlayerAvatar() {
    return playerAvatar;
  }

  public void setPlayerAvatar(int playerAvatar) {
    this.playerAvatar = playerAvatar;
  }
}
