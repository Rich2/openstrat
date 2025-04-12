/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** Immutable Array based class for [[String]]s. */
final class StrArr(val arrayUnsafe: Array[String]) extends AnyVal, ArrNoParam[String]
{ override type ThisT = StrArr
  override def typeStr: String = "Strings"
  override def unsafeSameSize(length: Int): StrArr = new StrArr(new Array[String](length))
  override def setElemUnsafe(index: Int, newElem: String): Unit = arrayUnsafe(index) = newElem
  override def fElemStr: String => String = s => s
  override def apply(index: Int): String = arrayUnsafe(index)
  override def elem(index: Int): String = arrayUnsafe(index)
  override def length: Int = arrayUnsafe.length
  override def numElems: Int = arrayUnsafe.length

  /** Make 1 string, by appending with separator from this collection of strings. */
  override def mkStr(separator: String = ""): String = if(empty) "" else
  { var acc = head
    tailForeach{ s => acc += separator + s }
    acc
  }

  /** Make with semicolons. Make 1 string, by appending with "; " separator from this collection of strings. */
  def mkSemi: String = mkStr("; ")

  /** Make a combined [[String]] with semicolons. Make 1 string, by appending with "; " separator from this collection of strings, but add a semicolon
   * ";" if the final string is all white space. */
  def mkSemiSpaceSpecial: String = mkSpaceSpecial(";")

  /** Make a combined [[String]] with semicolons. Make 1 string, by appending with ", " separator from this collection of strings, but add a semicolon
   * ";" if the final string is all white space. */
  def mkCommaSpaceSpecial: String = mkSpaceSpecial(",")

  /** Make a combined [[String]] with a space followed by the given separator [[String]] from this collection of strings, but add a speraotor without
   * a space if the final string is all white space. */
  def mkSpaceSpecial(sep: String): String = length match
  { case 0 => ""
    case 1 => head
    case n =>
    { var acc = head
      var i = 1
      while(i < n)
      { acc = acc + ife(apply(i).forall(_.isWhitespace), sep, sep + " ") + apply(i)
        i += 1
      }
      if (last.forall(_.isWhitespace)) acc += sep
      acc
    }
  }

  /** Make with commas. Make 1 string, by appending with ", " separator from this collection of strings. */
  def mkComma: String = mkStr(", ")

  /** Make with semicolons inside parentheses. Make 1 string, by appending with "; " separator from this collection of strings and then enclose with a
   *  pair of parentheses. */
  def mkSemiParenth: String = mkStr("; ").enParenth

  /** Make with commas inside parentheses. Make 1 string, by appending with ", " separator from this collection of stringsand then enclose with a pair
   *  of parentheses. */
  def mkCommaParenth: String = mkStr(", ").enParenth

  /** Make with new lines. Make 1 string, by appending with "\n" separator from this collection of strings. */
  def mkNewLine: String = mkStr("\n")

  /** Returns new reversed [[StrArr]]. */
  override def reverse: StrArr =
  { val newArray = new Array[String](length)
    iUntilForeach(0, length) { i => newArray(i) = arrayUnsafe(length - 1 - i) }
    new StrArr(newArray)
  }

  /** Returns new [[StrArr]] with the first N [[String]]s dropped. */
  override def drop(n: Int): StrArr =
  { val nn = n.max0
    val newArray = new Array[String]((length - nn).max0)
    iUntilForeach(length - nn) { i => newArray(i) = arrayUnsafe(i + nn) }
    new StrArr(newArray)
  }

  /** Appends the operand [[StrArr]] to this, return a new [[StrArr]]. */
  @targetName("append") override def ++(operand: StrArr): StrArr =
  { val newArray: Array[String] = new Array[String](length + operand.length)
    var i = 0
    while (i < length) { newArray(i) = arrayUnsafe(i); i += 1 }
    i = 0
    while (i < operand.length) { newArray(i + length) = operand.arrayUnsafe(i); i += 1 }
    new StrArr(newArray)
  }

  /** append. Functionally appends the operand [[String]]. */
  @targetName("appendElem") @inline def +%(operand: String): StrArr =
  { val newArray = new Array[String](length + 1)
    arrayUnsafe.copyToArray(newArray)
    newArray(length) = operand
    new StrArr(newArray)
  }

  /** prepend. Functionally prepends the operand [[String]]. */
  @targetName("prepend") @inline def %:(operand: String): StrArr =
  { val newArray = new Array[String](length + 1)
    newArray(0) = operand
    Array.copy(arrayUnsafe, 0, newArray, 1, length)
    new StrArr(newArray)
  }

  /** If operand is a [[Some]] returns [[StrArr]] with the appended [[String]] value, else if [[None]] returns this. */
  def appendOption(optElem: Option[String]): StrArr =
    optElem.fld(this, this +% _)

  def appendsOption(optElem: Option[StrArr]): StrArr =
    optElem.fld(this, this ++ _)

  /** Finds the index of the first [[String]] element that fulfills the predicate parameter or returns -1. */
  def findIndex(f: String => Boolean): Int =
  {
    def loop(index: Int): Int = index match
    { case i if i == length => -1
      case i if f(apply(i)) => i
      case i => loop(i + 1)
    }
    loop(0)
  }

  def ifHead(f: String => Boolean): Boolean = if (arrayUnsafe.isEmpty) false else f(head)
}

/** Companion object of ArrStrings class contains repeat parameter apply factor method. */
object StrArr
{ /** Repeat parameter apply factor method. */
  def apply(input: String*): StrArr = new StrArr(input.toArray)

  implicit val eqImplicit: EqT[StrArr] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var i = 0
      var acc = true
      while (i < a1.length & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

/** [[Buff]] class for [[String]]s. */
class StringBuff(val bufferUnsafe: ArrayBuffer[String]) extends AnyVal, Buff[String]
{ override type ThisT = StringBuff
  override def typeStr: String = "Stringsbuff"
  override def apply(index: Int): String = bufferUnsafe(index)
  override def elem(index: Int): String = bufferUnsafe(index)
  override def length: Int = bufferUnsafe.length
  override def numElems: Int = bufferUnsafe.length 
  override def setElemUnsafe(index: Int, newElem: String): Unit = bufferUnsafe(index) = newElem
  override def fElemStr: String => String = s => s
  override def grow(newElem: String): Unit = bufferUnsafe.append(newElem)
  def toArray: Array[String] = bufferUnsafe.toArray
  def toArr: StrArr = new StrArr(toArray)
}

object StringBuff
{ def apply(startSize: Int = 4): StringBuff = new StringBuff(new ArrayBuffer[String](startSize))
}

object BuilderArrString extends BuilderMapArr[String, StrArr], BuilderFlatArr[StrArr]
{ type BuffT = StringBuff
  override def uninitialised(length: Int): StrArr = new StrArr(new Array[String](length))
  override def indexSet(seqLike: StrArr, index: Int, newElem: String): Unit = seqLike.arrayUnsafe(index) = newElem
  override def newBuff(length: Int = 4): StringBuff = new StringBuff(new ArrayBuffer[String](length))
  override def buffGrow(buff: StringBuff, newElem: String): Unit = buff.bufferUnsafe.append(newElem)
  override def buffToSeqLike(buff: StringBuff): StrArr = new StrArr(buff.bufferUnsafe.toArray)
  override def buffGrowArr(buff: StringBuff, arr: StrArr): Unit = arr.arrayUnsafe.foreach(el => buff.bufferUnsafe.append(el))
}