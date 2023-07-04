package trivia;

import java.util.ArrayList;
import java.util.LinkedList;

import static trivia.PlayerBuilder.playerBuilder;

// REFACTOR ME
public class GameBetter implements IGame {
    public static final int TOTAL_NUMBER_OF_POSITIONS = 12;
    ArrayList<Player> players = new ArrayList<>();
    int[] places = new int[6];
    int[] purses = new int[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;

    public GameBetter() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {
        players.add(newPlayer(playerName));
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    private static Player newPlayer(String playerName) {
        return playerBuilder()
                .name(playerName)
                .inPenaltyBox(false)
                .build();
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.println(players.get(currentPlayer).getPlayerName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (players.get(currentPlayer).isInPenaltyBox()) {
            if (isOddNumber(roll)) {
                getOutOfPenaltyBox();
                movePlayer(roll);
                askQuestion();
            } else {
                stayInPenaltyBox();
            }

        } else {
            movePlayer(roll);
            askQuestion();
        }

    }

    private static boolean isOddNumber(int roll) {
        return roll % 2 != 0;
    }

    private void getOutOfPenaltyBox() {
        players.get(currentPlayer).setInPenaltyBox(false);
        System.out.println(players.get(currentPlayer).getPlayerName() + " is getting out of the penalty box");
    }

    private void stayInPenaltyBox() {
        System.out.println(players.get(currentPlayer).getPlayerName() + " is not getting out of the penalty box");
    }

    private void movePlayer(int roll) {
        places[currentPlayer] = places[currentPlayer] + roll;
        if (places[currentPlayer] > 11) {
            places[currentPlayer] = places[currentPlayer] - TOTAL_NUMBER_OF_POSITIONS;
        }
        System.out.println(players.get(currentPlayer).getPlayerName()
                + "'s new location is "
                + places[currentPlayer]);
        System.out.println("The category is " + currentCategory());
    }

    private void askQuestion() {
        if (currentCategory().equals("Pop"))
            System.out.println(popQuestions.removeFirst());
        if (currentCategory().equals("Science"))
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory().equals("Sports"))
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory().equals("Rock"))
            System.out.println(rockQuestions.removeFirst());
    }


    private String currentCategory() {
        if (places[currentPlayer] == 0) return "Pop";
        if (places[currentPlayer] == 4) return "Pop";
        if (places[currentPlayer] == 8) return "Pop";
        if (places[currentPlayer] == 1) return "Science";
        if (places[currentPlayer] == 5) return "Science";
        if (places[currentPlayer] == 9) return "Science";
        if (places[currentPlayer] == 2) return "Sports";
        if (places[currentPlayer] == 6) return "Sports";
        if (places[currentPlayer] == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (!players.get(currentPlayer).isInPenaltyBox()) {
            System.out.println("Answer was correct!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer).getPlayerName()
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
        } else {
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;
            return true;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer).getPlayerName() + " was sent to the penalty box");
        players.get(currentPlayer).setInPenaltyBox(true);

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }

    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
