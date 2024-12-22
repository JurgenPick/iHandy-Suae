package com.nssus.ihandy.data.extension

//fun String.formatYardDigit() : String {
//    var result = this
//    if (this[1] !in 'A'..'Z'&& this.length == 5)  result = this.insert(1, " ")
//    if (result.length == 6){
//        if (result[1] in '0'..'9' ) result = result.insert(1, " ")
//        else result += "1"
//    }
//    return result
//}

fun String.formatYardDigit() : String {
    var result = this
    if (this.contains('-')){
        result = result.take(result.indexOf('-'))
        println(result)
        if(result.length == 5) result = result.insert(1," ")
        println(result)
        result += this.drop(this.length -1)
        println(result)
        return result
    }
    if (result.length < 7)result = result.insert(1," ")
    if (result.length == 6 ) result +="1"
    println(result)
    return result
}

