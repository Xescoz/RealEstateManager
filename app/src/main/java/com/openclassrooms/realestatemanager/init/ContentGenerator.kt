package com.openclassrooms.realestatemanager.init

import com.openclassrooms.realestatemanager.models.Agent
import com.openclassrooms.realestatemanager.models.Property

abstract class ContentGenerator {

    var agentList = listOf(
            Agent("Bob KIRTH","bob_kirth@mail.com","06 23 56 89 47"),
            Agent("Jean FRUIT","jean_fruit@mail.com","06 21 32 89 14"),
            Agent("Paul LIME","paul_lime@mail.com","06 65 47 53 02"),
    )

    var houseList = listOf(
            Property(0,"13/12/2021","Loft","School,Parc",1200000,"https://www.miresparis.com/wp-content/uploads/2021/11/01-C0542-051.jpg",
            "320 m²",5,1,2,"A white loft with a lot of luminosity. Two bedrooms and a lot of rooms. Kitchen integrated.",false,null,"Paris",
                    "5 rue des tullipes","France","5A","75000","", agentList[0]),
            Property(1,"10/12/2021","House","Parc",1800000,"https://q4g9y5a8.rocketcdn.me/wp-content/uploads/2018/08/Villu-min.webp",
                    "310 m²",8,2,3,"Villu is a magnificent log clubhouse with a massive terrace and impressingly grand vestibule.",false,null,"Laval",
                    "5 rue des tullipes","France","5A","53000","", agentList[1]),
            Property(2,"01/10/2021","House","Parc",1800000,"https://q4g9y5a8.rocketcdn.me/wp-content/uploads/2018/08/Villu-min.webp",
                    "310 m²",8,2,3,"Villu is a magnificent log clubhouse with a massive terrace and impressingly grand vestibule.",true,null,"Laval",
                    "5 rue des tullipes","France","5A","53000","", agentList[2])
    )


}