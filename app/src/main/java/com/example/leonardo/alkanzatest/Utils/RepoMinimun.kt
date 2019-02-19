package com.example.leonardo.alkanzatest.Utils

import java.lang.Math.abs

open class RepoMinimun {

    open fun calculateMinimun(distanceVector:ArrayList<Int>): Calculation{

        var minimum = Double.POSITIVE_INFINITY
        var pos = 0
        var count = 0


        for (i in distanceVector) {

            var minimumTemp = 0.0

            for (j in  distanceVector){
                minimumTemp +=  abs(i-j).toDouble()
            }

            if (minimumTemp <= minimum){
                minimum = minimumTemp
                pos = count
            }
            count+=1
        }

        return Calculation(pos, minimum)
    }
}
data class Calculation(val pos:Int , val minumum: Double)