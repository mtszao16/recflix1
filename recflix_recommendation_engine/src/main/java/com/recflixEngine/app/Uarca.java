package com.recflixEngine.app;

public class Uarca {

    double[] IA, Cr;
    double[][] FR;
    double Er, Ir, N;
    int i, j, k;
    private final WeightActions WA;

    // WA[]     = Weight Assigned to Action
    // IA[]     = Indication Assigned to Action
    // FR[][]   = Final Rating
    // Er       = Explicit Rating
    // Cr[]     = Correction Factor
    // Ir       = Implicit Rating
    // N        = Total number of ratings for user j
    // i        = Movie ID
    // j        = User ID
    // k        = Temporary Variable

    public Uarca(WeightActions wts) {
        this.WA = wts;
        Er = wts.getExplicitRating();
        IA = new double[] { 1, 1, 1, 1, -1, 1, 1, -1, -1, 1, 1 };
        FR = new double[][] { { 0 } };
        Cr = new double[] { 0 };
        Ir = 0;
        N = 1;
        i = 0;
        j = 0;
    }

    public double getValueForIndex(Integer index) {
        switch (index) {
        case 0:
            return WA.getExplicitRating();
        case 1:
            return WA.getRecomdToUserWt();
        case 2:
            return WA.getAddFavWt();
        case 3:
            return WA.getWatchLstWt();
        case 4:
            return WA.getRemFavWt();
        case 5:
            return WA.getTimeSpendWt();
        case 6:
            return WA.getBckCntrlWt();
        case 7:
            return WA.getFwdCntrlWt();
        case 8:
            return WA.getBckSeekWt();
        case 9:
            return WA.getFwdSeekWt();
        case 10:
            return WA.getViewWt();

        default:
            return 0.0;
        }
    }

    public double getFinalRating() {

        //Equation 1
        // FR[i][j] = Er + (0.5 * Cr[j]);

        //Equation 2

        if (Er < 2.5) {
            for (k = 1; k <= 3; k++) {
                Er = Er + (getValueForIndex(k) * IA[k]);
            }
        } else if (Er > 3.0) {
            Er = Er - getValueForIndex(4);
        }

        //Equation 3
        for (k = 5; k <= 10; k++) { //k <= 11
            Ir = Ir + (getValueForIndex(k) * IA[k]);
        }

        //Equation 4    correction
        for (k = 0; k <= N - 1; k++) {
            Cr[j] = Cr[j] + Er - (2 * Ir);
        }
        Cr[j] = Cr[j] / N;

        //Uarca
        if (Er > 0) {
            FR[i][j] = Er + (0.5 * Cr[j]);
            // System.out.println("FR[" + i + "][" + j + "] = " + FR[i][j]);
        } else {
            FR[i][j] = Ir;
            // System.out.println("FR[" + i + "][" + j + "] = " + FR[i][j]);
        }

        return Math.round(FR[i][j] * 100.0) / 100.0;
    }
}