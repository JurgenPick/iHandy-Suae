package com.nssus.ihandy.data.extension

fun String.isErrorTextFieldWith(tfMaxLength: Int): Boolean = isNotEmpty() && isNotEqualsMaxLength(tfMaxLength)


//fun String.isEqualsMaxLength(tfMaxLength: Int): Boolean = this.length == tfMaxLength
fun String.isEqualsMaxLength(tfMaxLength: Int): Boolean = length == tfMaxLength

//fun String.isEqualsMaxLength(tfMaxLength: Int): Boolean {
//    return length == tfMaxLength
//}

fun isEqualMaxLength(text: String, tfMaxLength: Int): Boolean { //
    return text.length == tfMaxLength
}


fun String.isNotEqualsMaxLength(tfMaxLength: Int): Boolean = isEqualsMaxLength(tfMaxLength).not() //  เหมือนกับ !isEqualsMaxLength()

fun String.insert(indexInsert : Int, toInsert : String) : String {
    return this.substring(0,indexInsert) + toInsert + this.substring(indexInsert)
}


// X1 == "G" & ( X2 == "A "| "B") /////**** isValidFirstChar && isValidFirstCharWith
// string ext ตัวแรกเป็น G sepecific first char
// string ext ตัวแรกเป็น A-F ตัวใหญ่อย่างเดียว มั้ย // A1234 regx

fun String.isValidFirstChar(start: Char = 'A', end: Char = 'F'): Boolean = first() in start..end

fun String.isInvalidFirstChar(start: Char = 'A', end: Char = 'F'): Boolean = isValidFirstChar(start, end).not()

//fun String.isValidFirstCharWith(checkedChar: String): Boolean = startsWith(checkedChar)

fun String.isNotStartWith(checkedChar: String): Boolean = startsWith(checkedChar).not()

fun String.replaceStartSpecificTextToNewValue(specificText: String = "-", newValue: String = ""): String {
    if (isNotStartWith(specificText)) return this

    return replaceFirst(oldValue = specificText, newValue = newValue)
}