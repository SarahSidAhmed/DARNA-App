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
    fun isNight(hour : Int): Int {
        val time = -1

        if((time in 0..sunRiseHour) || (time in sunSetHour..22))
        return getnightPrice()
        else return 0
    }

    //to see if the schedule is a vacation day
    fun isFerie(date: String): Int {
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
        var notEquel =false
        while (!notEquel && i<jourFeriesAlgeria.size){
            if (date.equals(jourFeriesAlgeria[i])){
                notEquel =true
            }
            i++
        }

        if(notEquel) return getjourFeriePrice()
        else return  0
    }

    fun getUrgentPrice(urgent: Boolean):Int{
        if (urgent) return this.urgentPrice
        else return 0
    }

}