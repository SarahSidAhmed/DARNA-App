package com.example.darnamob.systems

import java.util.Date

class pricingSystem {
    private final val nightPrice = 3500
    private final val jourFerie = 1500
    private final val urgentPrice = 1200
    private final val sunRiseHour = 6
    private final val sunSetHour = 18


    fun getnightPrice(): Int{return this.nightPrice}
    fun getjourFeriePrice():Int{return this.jourFerie}

    //to see if the schedule is at night time
    fun isNight(hour : String): Boolean{
        val time  = hour.toInt()
        return time < sunRiseHour || time > sunSetHour
    }

    //to see if the schedule is a vacation day
    fun isFerie(date: String): Boolean {
        val jourFeriesAlgeria = arrayOf(
            "01/01",
            "12/01",
            "01/05",
            "24/05",
            "05/07",
            "31/07",
            "20/08",
            "29/08",
            "29/10",
            "01/11"
        )
        var i = 0
        var notEquel =true
        while (notEquel && i<jourFeriesAlgeria.size){
            if (date.equals(jourFeriesAlgeria[i])){
                notEquel =false
            }
            i++
        }
        return notEquel
    }

    fun getUrgentPrice():Int{
        return this.urgentPrice
    }

}