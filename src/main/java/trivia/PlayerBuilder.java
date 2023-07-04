package trivia;

public class PlayerBuilder {

    private String name;
    private boolean inPenaltyBox;

    public static PlayerBuilder playerBuilder() {
        return new PlayerBuilder();
    }

    public PlayerBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PlayerBuilder inPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
        return this;
    }

    public Player build() {
        Player player = new Player();
        player.setPlayerName(name);
        player.setInPenaltyBox(inPenaltyBox);
        return player;
    }

}
