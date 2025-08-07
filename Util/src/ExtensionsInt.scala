/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import math.{Pi, sin}

/** Extension methods for Int. */
class IntExtensions(val thisInt: Int) extends AnyVal
{
  /** Returns 0 if this Int less than 0 else returns this [[Int]]. */
  def max0: Int = ife(thisInt > 0, thisInt, 0)

  /** Returns 0 if this Int more than 0 else returns this [[Int]]. */
  def min0: Int = ife(thisInt > 0, 0, thisInt)

  /** Returns true if this Int is even, false if this Int is odd. */
  def isEven: Boolean = thisInt % 2 == 0

  /** Returns true if this Int is even, false if this Int is odd. */
  def isOdd: Boolean = thisInt % 2 != 0

  /** Returns the first lazily evaluated parameter if this Int is 0 else returns the second lazily evaluated parameter. */
  def if0Else[A](vZero: => A, vNonZero: => A): A = if (thisInt == 0) vZero else vNonZero

  /** Returns the first lazily evaluated parameter if this Int is odd else returns the second lazily evaluated parameter. */
  def ifOddElse[A](vOdd: => A, vEven: => A): A = if (thisInt % 2 == 0) vEven else vOdd

  /** Returns the first lazily evaluated parameter if this Int is even else returns the second lazily evaluated parameter. */
  def ifEvenElse[A](vEven: => A, vOdd: => A): A = if (thisInt % 2 == 0) vEven else vOdd

  /** if the predicate is true apply the function to this Int, else return thisInt unmodified. */
  @inline def ifMod(predicate: Boolean, f: Int => Int): Int = if (predicate) f(thisInt) else thisInt

  /** Divides rounding up. 11.divRoundUp(10) == 2; */
  def divRoundUp(operand: Int): Int = (thisInt / operand).ifMod(thisInt % operand > 0, _ + 1)

  /** The absolute difference between this [[Int]] and the operand [[Int]]. */
  def diff(operand: Int): Int = (thisInt - operand).abs

  /** Divides this [[Int]] by 2. */
  def div2: Int = thisInt / 2

  /** Divides this [[Int]] by 4. */
  def div4: Int = thisInt / 4

  /** multiplies this Int by a million and returns the result as [[Int]]. */
  def million: Int = thisInt * 1000000

  /** multiplies this Int by a billion and returns the result as [[Long]]. */
  def billion: Long = thisInt.toLong * 1000000000L

  /** multiplies this Int by a trillion and returns the result as [[Long]]. */
  def trillion: Long = thisInt.toLong * 1000000000000L

  /** multiplies this Int by a quadrillion and returns the result as [[Long]]. */
  def quadrillion: Long = thisInt.toLong * 1000000000000000L

  /** Returns a [[String]] of the given number of space characters. */
  def spaces: String = (1 to thisInt).foldLeft("")((a, b) => a + " ")

  /** Returns a [[String]] of a new line foloowed by the given number of space characters. */
  def nlSpaces: String = (1 to thisInt).foldLeft("\n")((a, b) => a + " ")

  def scaledStr(pairs: (Int, String)*): String =
  { var res = ""
    var i = 0
    val ps: Seq[(Int, String)] = pairs.sortWith(_._1 > _._1)
    while(res == "" & i < ps.length) if (thisInt >= ps(i)._1) res = ps(i)._2 else i += 1
    res
  }
  
  /** Gives a singular version if this [[Int]] == 1, otherwise gives the plural version. The first parameter gives the word so we can have "1 hat" or "2 hats"
   * "-1 hats" etc. If no plural version of the word is given then an 's' is added to the singular version. */
  def pluralisation(singular: String, plural: String = ""): String = thisInt match
  { case 1 => "1" -- singular
    case n if plural == "" => n.toString -- singular + "s"
    case n => n.toString -- plural
  }

  /** Ordinal abbreviated form. "1st", "2nd", "3rd", "4th", etc. */
  def ordAbbr: String =
  { val endStr = thisInt % 100 match
    { case 11 | 12 | 13 => "th"
      case n if n % 10 == 1 => "st"
      case n if n % 10 == 2 => "nd"
      case n if n % 10 == 3 => "rd"
      case _ => "th"
    }
    thisInt.toString + endStr
  }

  /** French Ordinal abbreviated form. "1er" / "1re", "2e", "3e", "4e", etc. */
  def frenchOrdAbbr(masculine: Boolean): String = thisInt match{
    case 1 if masculine == true => "1er"
    case 1 => "1re"
    case n => n.toString + "e"
  }

  def scaledStr(i1: Int, s1: String, i2:Int, s2: String, i3: Int, s3: String, pairs: (Int, String)*): String =
    scaledStr(List((i1, s1), (i2, s2), (i3, s3)) ++: pairs*)

  /** Returns a [[String]] in which a [[Char]] is repeated the given parameter number of times. */
  def repeatChar(c: Char): String = (1 to thisInt).foldLeft("")((a, b) => a + c)

  def commaInts(otherInts: Int *): String = otherInts.foldLeft(thisInt.toString)(_ + ", " + _.toString)
  def semicolonInts(otherInts: Int *): String = otherInts.foldLeft(thisInt.toString)(_ + "; " + _.toString)

  /** More useful definition of modulus where a negative number divided by a positive divisor produces a non negative modulus. */
  def %%(divisor: Int): Int =
  { val r = thisInt % divisor
    if (r < 0) divisor + r else r
  }

  /** Repeat the side effecting procedure this [[Int]] number of times. Extension method. */
  def doTimes(f: () => Unit): Unit =
  { var count: Int = 0
    while(count < thisInt) {f(); count += 1}
  }

  /** folds across the Integer range starting with this Int to the given end of range. */
  def foldTo[A](toValue: Int, initialValue: A)(f: (A, Int) => A): A =
  { var count: Int = thisInt
    var acc: A = initialValue
    while(count <= toValue) { acc = f(acc, count); count += 1 }
    acc
  }

  /** folds across the Integer range starting with this Int until the given end of range. */
  def foldUntil[A](untilValue: Int, initialValue: A)(f: (A, Int) => A): A =
  { var count: Int = thisInt
    var acc: A = initialValue
    while(count < untilValue) { acc = f(acc, count); count += 1 }
    acc
  }

  def str2Dig: String = thisInt match
  { case i if (i > 9) || (i < -9) => i.toString
    case i if (i >= 0) => "0" + i.toString
    case i => "-0" + (-i).toString
  }

  /** Increments the value of an integer while that integer does not match condition. Not guaranteed to terminate. */
  def roundUpTo(f : Int => Boolean): Int = thisInt  match
  { case i if f(i) => i
    case i => (i + 1).roundUpTo(f)
  }

  /** Decrements the value of an integer while that integer does not match condition. Not guaranteed to terminate. */
  def roundDownTo(f : Int => Boolean): Int = thisInt match
  { case i if f(i) => i
    case i => (i - 1).roundDownTo(f)
  }

  /** Returns this [[Int]]'s value if even, else returns thisInt + 1. */
  def roundUpToEven = thisInt.ifEvenElse(thisInt, thisInt + 1)

  /** Returns this [[Int]]'s value if even, else returns thisInt - 1. */
  def roundDownToEven = thisInt.ifEvenElse(thisInt, thisInt - 1)

  /** Returns this [[Int]]'s value if odd, else returns thisInt + 1. */
  def roundUpToOdd = thisInt.ifEvenElse(thisInt + 1, thisInt)

  /** Returns this [[Int]]'s value if odd, else returns thisInt - 1. */
  def roundDownToOdd = thisInt.ifEvenElse(thisInt - 1, thisInt)
   
  @inline def hexStr: String = thisInt.toHexString.toUpperCase

  /** Hexadecimal representation of this [[Int]] with at least 2 digits, adding 0 to front if required." */
  @inline def hexStr2: String = ife(hexStr.length == 1, "0" + hexStr, hexStr)

  /** Base32 string representation of this Int. */
  def base32: String =
  {
    def loop(rem: Int, acc: String): String =
    {
      val newDigit = rem % 32

      val newChar: Char = newDigit match
      { case i if i < 10 => ('0' + i).toChar
        case i if i < 24 => ('A' + i - 10).toChar
        case i => ('A' + i - 9).toChar
      }
      val newStr = newChar.toString + acc
      val newRem = rem / 32
      ife(newRem == 0, newStr, loop(newRem, newStr))
    }

    val startStr = ife(thisInt < 0, "-", "")
    startStr + loop(thisInt.abs, "")
  }

  def isDivBy3: Boolean = thisInt % 3 == 0

  /** Dividing by 4 gives remainder of 0. */
  def div4Rem0: Boolean = thisInt % 4 == 0

  /** Dividing this [[Int]] by 4 == 1 */
  def div4Rem1: Boolean = thisInt %% 4 == 1

  /** Dividing by 4 gives remainder of 2. */
  def div4Rem2: Boolean = thisInt %% 4 == 2

  def div4Rem3: Boolean = thisInt %% 4 == 3
   
  def isDivBy8: Boolean = thisInt %% 8 == 0
  def div8Rem2: Boolean = thisInt %% 8 == 2
  def div8Rem4: Boolean = thisInt %% 8 == 4
  def div8Rem6: Boolean = thisInt %% 8 == 6
   
  def tenthsStr(tenths: Int): String =
  { val res = thisInt.abs * tenths.abs
    val mod = res % 10
    val str1 = (mod != 0).ifMod((res / 10).toString)(_ + "." + mod.toString)
    (thisInt < 0 |!& tenths < 0).ifMod(str1)("-" + _)
  }
   
  def hundredthsStr(hundreds: Int): String =
  { val res = thisInt.abs * hundreds.abs
    val rs = (res / 100).toString
    val str1 = res % 100 match
    { case 0 => rs
      case m if m % 10 == 0 => rs + "." + (m/10).str
      case m => rs + "." + (m/10).str + (m % 10).str
    }
    (thisInt < 0  |!& hundreds < 0).ifMod(str1)("-" + _)
  }
  /** Only use positive value that won't overflow int) */
  def power(operand: Int): Int = 1.fRepeat(operand)(a => a * thisInt)

  /** Takes this Int as a value in arc degrees and converts it to a value of radians. */
  @inline def degsToRadians: Double = thisInt * Pi / 180.0

  /** Takes this Int as a value in arc degrees and converts it to a value of arc seconds. */
  @inline def degsToSecs: Int = thisInt * 3600

  /** Takes this Int as a value in radians and converts it to a value of arc degrees. */
  @inline def radiansToDegs: Double = thisInt * 180.0 / Pi

  /** Takes this Int as a value in radians and converts it to a value of arc seconds. */
  @inline def radiansToSecs: Double = thisInt * 3600.0 * 180.0 / Pi

  /** Takes this Int as a value in arc seconds and converts it to a value of radians. */
  @inline def secsToRadians = thisInt * Pi / 180.0 / 3600.0

  /** Takes this Int as a value in arc seconds and converts it to a value of arc degrees. */
  @inline def secsToDegs = thisInt / 3600.0
}