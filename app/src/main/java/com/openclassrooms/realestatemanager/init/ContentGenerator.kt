package com.openclassrooms.realestatemanager.init

import com.openclassrooms.realestatemanager.models.Property

class ContentGenerator {

    companion object{
        fun generatePropertyContent() : List<Property>{
            return houseList
        }
        var houseList = listOf(
                Property(0,"13/12/2021","Loft","School,Parc",1200000,"https://www.miresparis.com/wp-content/uploads/2021/11/01-C0542-051.jpg",
                        "320 m²",5,1,2,"A white loft with a lot of luminosity. Two bedrooms and a lot of rooms. Kitchen integrated.",false,null,"Paris",
                        "5 rue des tullipes","France","5A","75000","", "Bob KIRTH"),
                Property(1,"10/12/2021","House","Parc",1800000,"https://q4g9y5a8.rocketcdn.me/wp-content/uploads/2018/08/Villu-min.webp",
                        "310 m²",8,2,3,"Villu is a magnificent log clubhouse with a massive terrace and impressingly grand vestibule.",false,null,"Laval",
                        "5 rue des tullipes","France","","53000","", "Jean FRUIT"),
                Property(2,"01/10/2021","House","Parc",1800000,"https://q4g9y5a8.rocketcdn.me/wp-content/uploads/2018/08/Villu-min.webp",
                        "310 m²",8,2,3,"Villu is a magnificent log clubhouse with a massive terrace and impressingly grand vestibule.",true,null,"Laval",
                        "5 rue des tullipes","France","","53000","", "Paul LIME")
        )
    }

}