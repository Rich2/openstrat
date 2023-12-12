/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** Immutable Array based class for [[String]]s. */
final class StrArr(val unsafeArray: Array[String]) extends AnyVal with ArrNoParam[String]
{ override type ThisT = StrArr
  override def typeStr: String = "Strings"
  override def unsafeSameSize(length: Int): StrArr = new StrArr(new Array[String](length))
  override def setElemUnsafe(i: Int, newElem: String): Unit = unsafeArray(i) = newElem
  override def fElemStr: String => String = s => s
  override def apply(index: Int): String = unsafeArray(index)
  override def length: Int = unsafeArray.length

  /** Make 1 string, by appending with separator from this collection of strings. */
  def mkStr(separator: String =""): String = if(empty) ""
  else
  { var acc = head
    tailForeach{ s => acc += separator + s }
    acc
  }

  /** Make with semicolons. Make 1 string, by appending with "; " separator from this collection of strings. */
  def mkSemi: String = mkStr("; ")

  /** Make a combined [[String]] with semicolons. Make 1 string, by appending with "; " separator from this collection of strings, but add a semicolon
   * ";" if the final string. */
  def mkSemiSpaceSpecial: String = mkSpaceSpecial(";")

  /** Make with semicolons. Make 1 string, by appending with "; " separator from this collection of strings. */
  def mkCommaSpaceSpecial: String = mkSpaceSpecial(",")

  def mkSpaceSpecial(sep: String): String = length match{
    case 0 => ""
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

  override def reverse: StrArr =
  { val newArray = new Array[String](length)
    iUntilForeach(0, length) { i => newArray(i) = unsafeArray(length - 1 - i) }
    new StrArr(newArray)
  }

  override def drop(n: Int): StrArr =
  { val nn = n.max0
    val newArray = new Array[String]((length - nn).max0)
    iUntilForeach(length - nn) { i => newArray(i) = unsafeArray(i + nn) }
    new StrArr(newArray)
  }

  @targetName("appendArr") override def ++(operand: StrArr): StrArr =
  { val newArray: Array[String] = new Array[String](length + operand.length)
    var i = 0
    while (i < length) {
      newArray(i) = unsafeArray(i)
      i += 1
    }
    i = 0
    while (i < operand.length) {
      newArray(i + length) = unsafeArray(i)
      i += 1
    }
    new StrArr(newArray)
  }

  /** append. Functionally appends the operand [[String]]. */
  @targetName("append") @inline def +%(operand: String): StrArr =
  { val newArray = new Array[String](length + 1)
    unsafeArray.copyToArray(newArray)
    newArray(length) = operand
    new StrArr(newArray)
  }

  /** prepend. Functionally prepends the operand [[String]]. */
  @targetName("prepend") @inline def %:(operand: String): StrArr =
  { val newArray = new Array[String](length + 1)
    newArray(0) = operand
    Array.copy(unsafeArray, 0, newArray, 1, length)
    new StrArr(newArray)
  }

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

  def ifHead(f: String => Boolean): Boolean = if (unsafeArray.isEmpty) false else f(head)
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
class StringBuff(val unsafeBuffer: ArrayBuffer[String]) extends AnyVal with BuffSequ[String]
{   override type ThisT = StringBuff

  override def typeStr: String = "Stringsbuff"
  override def apply(index: Int): String = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def setElemUnsafe(i: Int, newElem: String): Unit = unsafeBuffer(i) = newElem
  override def fElemStr: String => String = s => s
  override def grow(newElem: String): Unit = unsafeBuffer.append(newElem)
  def toArray: Array[String] = unsafeBuffer.toArray
  def toArr: StrArr = new StrArr(toArray)
}

object StringBuff
{ def apply(startSize: Int = 4): StringBuff = new StringBuff(new ArrayBuffer[String](startSize))
}

object BuilderArrString extends BuilderArrMap[String, StrArr] with BuilderArrFlat[StrArr]
{ type BuffT = StringBuff
  override def uninitialised(length: Int): StrArr = new StrArr(new Array[String](length))
  override def indexSet(seqLike: StrArr, index: Int, newElem: String): Unit = seqLike.unsafeArray(index) = newElem
  override def newBuff(length: Int = 4): StringBuff = new StringBuff(new ArrayBuffer[String](length))
  override def buffGrow(buff: StringBuff, newElem: String): Unit = buff.unsafeBuffer.append(newElem)
  override def buffToSeqLike(buff: StringBuff): StrArr = new StrArr(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: StringBuff, arr: StrArr): Unit = arr.unsafeArray.foreach(el => buff.unsafeBuffer.append(el))
}