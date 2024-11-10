//tarun rajanala
//trr997
public class Program3 {

    // DO NOT REMOVE OR MODIFY THESE VARIABLES (calculator and treatment_plan)
    ImpactCalculator calculator;    // the impact calculator
    int[] treatment_plan;           // array to store the treatment plan

    public Program3() {
        this.calculator = null;
    }

    /*
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3.
     * 
     *  DO NOT MODIFY THIS METHOD
     * 
     */
    public void initialize(ImpactCalculator ic) {
        this.calculator = ic;
        this.treatment_plan = new int[ic.getNumMedicines()];
    }


    /*
     * This method computes and returns the total impact of the treatment plan. It should
     * also fill in the treatment_plan array with the correct values.
     * 
     * Each element of the treatment_plan array should contain the number of hours
     * that medicine i should be administered for. For example, if treatment_plan[2] = 5,
     * then medicine 2 should be administered for 5 hours.
     * 
     */

     public int computeImpact() {
        int totalTime = calculator.getTotalTime();
        int numMedicines = calculator.getNumMedicines();

        //dp[i][j] holds the maximum impacgt of applying the first i medicines in j hours
        int[][] dp = new int[numMedicines+1][totalTime+1];
        for (int i = 1; i <= numMedicines; i++) { //all medicines
            for (int j = 1; j <= totalTime; j++) {  //all hours
                for (int k = 0; k <= j; k++) {  //all possible hours for medicine i
                    int impact = calculator.calculateImpact(i-1, k); //impact of current medicine for k hours
                    //take the max of not using the current medicine or using the current medicine with the max of the remaining hours
                    dp[i][j] = Math.max(dp[i][j], impact + dp[i-1][j-k]);
                }
            }
        }
        
        //max impact considering all medicines and hours
        int totalImpact = dp[numMedicines][totalTime];
    
        int time = totalTime;
        for (int i = numMedicines; i > 0; i--) {
            for (int j = 0; j <= time; j++) {
                //if the impact of applying the current medicine for j hours plus the impact of applying the remaining medicines in the remaining hours is equal to the max impact
                if (dp[i][time] == calculator.calculateImpact(i-1, j) + dp[i-1][time-j]) {
                    treatment_plan[i-1] = j; //add j hours to total
                    time -= j;
                    break;
                }
            }
        }
    
        return totalImpact;
    }


    /*
     * This method prints the treatment plan.
     */
    public void printTreatmentPlan() {
        System.out.println("Please administer medicines 1 through n for the following amounts of time:\n");
        int hoursForI = 0;
        int n = calculator.getNumMedicines();
        for(int i = 0; i < n; i++){
            // retrieve the amount of hours for medicine i
            hoursForI = treatment_plan[i]; // ... fill in here ...
            System.out.println("Medicine " + i + ": " + hoursForI); 
        }
    }
}


