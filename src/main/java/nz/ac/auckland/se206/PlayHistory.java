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

  public PlayHistory(int time, int difficulty, String name, int playerAvatar) {
    this.name = name;
    this.score = time / difficulty;
    this.timeTook = time;
    this.difficulty = difficulty;
    this.playerAvatar = playerAvatar;
  }

  // adds the most recent score to the player history
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

  // This is the function that creates a string to display the play history It dis plays the name,
  // time and difficulty of the player
  public List<List<Object>> getFullList() {
    // setting up the variables
    List<List<Object>> playHistoryList = new ArrayList<>();
    PlayHistory playHistory = this;
    if (this.childPlayHistory != null) {
      return this.childPlayHistory.getFullList();
    } else {
      int rank = 1;
      do {
        List<Object> playHistoryHbox = new ArrayList<>();

        // get past results and format in the right way
        String result =
            ("Rank "
                + rank
                + ":\n\tName: "
                + playHistory.getName()
                + "\n\tTime: "
                + playHistory.getTimeTook()
                + "\n\tDifficulty: "
                + playHistory.getDifficulty()
                + "\n\n");
        playHistoryHbox.add(result);
        Integer avatarNumber = playHistory.getPlayerAvatar();
        playHistoryHbox.add(avatarNumber);
        playHistoryList.add(playHistoryHbox);
        playHistory = playHistory.getParentPlayHistory();
        rank++;
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
