package me.masterejay.inferno.teams;

/**
 * @author MasterEjay
 */
public class TeamHandler {

    private static BlueTeam blueTeam = new BlueTeam();
    private static RedTeam redTeam = new RedTeam();
    private static Observers observers = new Observers();

    public static BlueTeam getBlueTeam() {
        return blueTeam;
    }

    public static RedTeam getRedTeam() {
        return redTeam;
    }

    public static Observers getObservers() {
        return observers;
    }
}
