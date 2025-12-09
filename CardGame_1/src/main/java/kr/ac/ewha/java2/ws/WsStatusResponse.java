package kr.ac.ewha.java2.ws;

import java.util.List;

public class WsStatusResponse {

    private String meName;
    private String opponentName;

    private int myScore;
    private int opponentScore;

    private String myCardName;
    private String opponentCardName;

    private int turn;
    private String phase;
    private String roundResult;

    private List<String> myDeckList;

    private boolean meIsP1;

    private int resolvedTurn;


    public String getMeName() {
        return meName;
    }

    public void setMeName(String meName) {
        this.meName = meName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public int getMyScore() {
        return myScore;
    }

    public void setMyScore(int myScore) {
        this.myScore = myScore;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }

    public String getMyCardName() {
        return myCardName;
    }

    public void setMyCardName(String myCardName) {
        this.myCardName = myCardName;
    }

    public String getOpponentCardName() {
        return opponentCardName;
    }

    public void setOpponentCardName(String opponentCardName) {
        this.opponentCardName = opponentCardName;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getRoundResult() {
        return roundResult;
    }

    public void setRoundResult(String roundResult) {
        this.roundResult = roundResult;
    }

    public List<String> getMyDeckList() {
        return myDeckList;
    }

    public void setMyDeckList(List<String> myDeckList) {
        this.myDeckList = myDeckList;
    }

    public int getResolvedTurn() {
        return resolvedTurn;
    }

    public void setResolvedTurn(int resolvedTurn) {
        this.resolvedTurn = resolvedTurn;
    }

    public boolean isMeIsP1() {
        return meIsP1;
    }

    public void setMeIsP1(boolean meIsP1) {
        this.meIsP1 = meIsP1;
    }
}
