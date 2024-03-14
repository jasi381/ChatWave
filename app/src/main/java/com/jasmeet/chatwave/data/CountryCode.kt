package com.jasmeet.chatwave.data

import com.jasmeet.chatwave.R

data class Country(
    val flag: Int? = null,
    val name: String,
    val code: String,
    val countryCode : String
)


val countryList = listOf(
    Country(R.drawable.flag_usa, "United States", "+1","US"),
    Country(R.drawable.flag_canada, "Canada", "+1","CA"),
    Country(R.drawable.flag_uk, "United Kingdom", "+44","UK"),
    Country(R.drawable.flag_australia, "Australia", "+61","AU"),
    Country(R.drawable.flag_india, "India", "+91","IN"),
    Country(R.drawable.flag_germany, "Germany", "+49","DE"),
    Country(R.drawable.flag_france, "France", "+33","FR"),
    Country(R.drawable.flag_italy, "Italy", "+39","IT"),
    Country(R.drawable.flag_spain, "Spain", "+34","ES"),
    Country(R.drawable.flag_brazil, "Brazil", "+55","BR"),
    Country(R.drawable.flag_mexico, "Mexico", "+52","MX"),
    Country(R.drawable.flag_argentina, "Argentina", "+54", "AR"),
    Country(R.drawable.flag_south_africa, "South Africa", "+27", "ZA"),
    Country(R.drawable.flag_egypt, "Egypt", "+20", "EG"),
    Country(R.drawable.flag_nigeria, "Nigeria", "+234","NG"),
    Country(R.drawable.flag_kenya, "Kenya", "+254","KE"),
    Country(R.drawable.flag_japan, "Japan", "+81","JP"),
    Country(R.drawable.flag_china, "China", "+86","CN"),
    Country(R.drawable.flag_russia, "Russia", "+7","RU"),
    Country(R.drawable.flag_pakistan, "Pakistan", "+92","PK"),
    Country(R.drawable.flag_bangladesh, "Bangladesh", "+880","BD"),
    Country(R.drawable.flag_sri_lanka, "Sri Lanka", "+94","LK"),
    Country(R.drawable.flag_nepal, "Nepal", "+977","NP"),

)

