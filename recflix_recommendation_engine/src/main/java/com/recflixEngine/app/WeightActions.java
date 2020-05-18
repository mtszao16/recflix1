package com.recflixEngine.app;

/**
 * Represents a WeightActions of the engine
 */
public class WeightActions {

    private final String userId;
    private final String movieId;
    private final double explicitRating;
    private final double recomdToUserWt;
    private final double addFavWt;
    private final double watchLstWt;
    private final double remFavWt;
    private final double timeSpendWt;
    private final double bckCntrlWt;
    private final double fwdCntrlWt;
    private final double bckSeekWt;
    private final double fwdSeekWt;
    private final double viewWt;

    public WeightActions(String userId, String movieId, double explicitRating, double recomdToUserWt, double addFavWt,
            double watchLstWt, double remFavWt, double timeSpendWt, double bckCntrlWt, double fwdCntrlWt,
            double bckSeekWt, double fwdSeekWt, double viewWt) {
        this.userId = userId;
        this.movieId = movieId;
        this.explicitRating = explicitRating;
        this.recomdToUserWt = recomdToUserWt;
        this.addFavWt = addFavWt;
        this.watchLstWt = watchLstWt;
        this.remFavWt = remFavWt;
        this.timeSpendWt = timeSpendWt;
        this.bckCntrlWt = bckCntrlWt;
        this.fwdCntrlWt = fwdCntrlWt;
        this.bckSeekWt = bckSeekWt;
        this.fwdSeekWt = fwdSeekWt;
        this.viewWt = viewWt;
    }

    public String getUserId() {
        return userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public double getExplicitRating() {
        return explicitRating;
    }

    public double getRecomdToUserWt() {
        return recomdToUserWt;
    }

    public double getAddFavWt() {
        return addFavWt;
    }

    public double getWatchLstWt() {
        return watchLstWt;
    }

    public double getRemFavWt() {
        return remFavWt;
    }

    public double getTimeSpendWt() {
        return timeSpendWt;
    }

    public double getBckCntrlWt() {
        return bckCntrlWt;
    }

    public double getFwdCntrlWt() {
        return fwdCntrlWt;
    }

    public double getBckSeekWt() {
        return bckSeekWt;
    }

    public double getFwdSeekWt() {
        return fwdSeekWt;
    }

    public double getViewWt() {
        return viewWt;
    }
}
