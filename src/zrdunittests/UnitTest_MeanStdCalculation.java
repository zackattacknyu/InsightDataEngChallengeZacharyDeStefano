/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdunittests;

import java.util.ArrayList;
import java.util.Calendar;
import zrdnetworkdata.TransactionHelper;

/**
 *
 * @author Zach
 */
public class UnitTest_MeanStdCalculation {
    
    /**
     * This will test the mean, std calculation
     */
    public static void runTests(){
        
        /*
         * Test 1: Mean and Std of 5 numbers: 3,4,5,6,7
         *      Mean:5      STD:1
         */
        ArrayList<Double> numbers = new ArrayList<Double>();
        for(double ee = 3; ee <= 7; ee++){
            numbers.add(ee);
        }
        System.out.println("---TEST 1: MEAN, STD CALC (SHOULD BE 5,sqrt(2) RESPECTIVELY)---");
        System.out.println("MEAN=" + TransactionHelper.getMean(numbers));
        System.out.println("STD=" + TransactionHelper.getStd(numbers));
        
        /*
         * Test 2: Mean, Std of 20 Random Numbers
         *      Verified in excel after printing
         */
        System.out.println();
        System.out.println("---TEST 2: MEAN, STD OF THESE RANDOM NUMBERS----");
        ArrayList<Double> numbersTest2 = new ArrayList<Double>();
        double currentNum;
        for(int i=0; i < 20; i++){
            currentNum=Math.random()*100;
            numbersTest2.add(currentNum);
            System.out.println(currentNum);
        }
        System.out.println("MEAN=" + TransactionHelper.getMean(numbersTest2));
        System.out.println("STD=" + TransactionHelper.getStd(numbersTest2));
        
        /*
         * Test 3: Test Anamoly Detection and compute time
         * Make 100000 numbers between 1 and 20,
         *      then test the following: 10, 20, 100, 1000
         * First two should not be anamolous. last two should be
         */
        System.out.println();
        System.out.println("---TEST 3: ANAMOLY DETECTION----");
        long start = Calendar.getInstance().getTimeInMillis();
        ArrayList<Double> numbersTest3 = new ArrayList<>();
        for(int i=0; i < 100000; i++){
            currentNum=Math.random()*20+1;
            numbersTest3.add(currentNum);
        }
        double mean = TransactionHelper.getMean(numbersTest3); 
        double std = TransactionHelper.getStd(numbersTest3);
        double[] testNums = {10,20,100,1000};
        boolean[] testRes = new boolean[4];
        for(int i=0; i<4;i++){
            testRes[i] = TransactionHelper.isAnamoly(testNums[i], mean, std);
        }
        long end = Calendar.getInstance().getTimeInMillis();
        double elapsedSec = (end-start)/1000.0;
        System.out.println("MEAN:" + mean + " \nSTD:" + std);
        for(int i=0; i<4;i++){
            System.out.println("IS " + testNums[i] + " ANOMOLY: " + testRes[i]);
        }
        System.out.println("NUM SEC TO DO ALL MEAN, STD COMPUTATION ON 100,000 ENTRIES: " + elapsedSec);
    }
    
}
