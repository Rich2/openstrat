/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pImplicit
import math.Pi, collection.immutable.ArraySeq, reflect.ClassTag
 
class IntImplicit(val thisInt: Int) extends AnyVal
{
  /** modulus */
  def %%(divisor: Int): Int =
  { val r = thisInt % divisor
    if (r < 0) divisor + r else r
  }

  def isEven: Boolean = thisInt % 2 == 0
  def isOdd: Boolean = thisInt % 2 != 0
  def ifZero[A](vZero: => A, vNonZero: => A): A = if (thisInt == 0) vZero else vNonZero
  def ifOdd[A](vOdd: => A, vEven: => A): A = if (thisInt % 2 == 0) vEven else vOdd
  def ifEven[A](vEven: => A, vOdd: => A): A = if (thisInt % 2 == 0) vEven else vOdd
  def pos: Boolean = thisInt >= 0
  def neg: Boolean = thisInt < 0
  def ifSumEven[A](evenVal: => A, oddVal: => A, operand: Int): A = if((thisInt + operand).isEven) evenVal else oddVal
  def ifSumOdd[A](oddVal: => A, evenVal: => A, operand: Int): A = if((thisInt + operand).isOdd) oddVal else evenVal
  //def ifinc(b: Boolean): Int = if (b) thisInt + 1 else thisInt
  //def ifdec(b: Boolean): Int = if (b) thisInt - 1 else thisInt
  def diff(operand: Int): Int = (thisInt - operand).abs
  def div2RoundUp: Int = thisInt / 2 + thisInt % 2
  def div2RoundDown: Int = thisInt / 2 - thisInt % 2
  def million: Int = thisInt *             1000000
  def billion: Long = thisInt.toLong *     1000000000L
  def trillion: Long = thisInt.toLong *    1000000000000L
  def quadrillion: Long = thisInt.toLong * 1000000000000000L
  //@inline def x2 = thisInt * 2
  @inline def degreesToRadians: Double = thisInt * Pi / 180
  @inline def radiansToDegrees: Double = thisInt * 180.0 / Pi
  def toSpaces: String = (1 to thisInt).foldLeft("")((a, b) => a + " ")
  //override def persistStr: String = thisInt.toString
  //def ifNeg[A](vNeg: => A, vPos: => A): A = if (thisInt < 0) vNeg else vPos
  def fori(f: Int => Unit): Unit = (0 until thisInt).foreach(f)
  def imap[A](f: Int => A): IndexedSeq[A] = (0 until thisInt).map(f(_))
  def iFlatMap[A](f: Int => Seq[A]): Seq[A] = (0 until thisInt).flatMap(f(_))

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

  /** maps across the Integer range starting with this Int to the given end of range. */
  def mapTo[A](toValue: Int, initialValue: A)(f: Int => A)(implicit ct: ClassTag[A]): ArraySeq[A] =
  { val len = (toValue - thisInt + 1).min(0)
    val arr = new Array[A](len)
    var count: Int = thisInt
    while(count <= toValue)
    { arr(count - thisInt) = f(count)
      count += 1
    }
    ArraySeq.unsafeWrapArray[A](arr)
  }

  def str2Dig: String = thisInt match
  { case i if (i > 9) || (i < -9) => i.toString
    case i if (i >= 0) => "0" - i.toString
    case i => "-0" - (-i).toString
  }

  /** Increases the value of an integer while that integer does not match condition */
  def incrementTill(f : Int => Boolean): Int = thisInt  match
  { case i if f(i) => i
    case i => (i + 1).incrementTill(f)
  }
  
  def roundUpToOdd = thisInt.ifEven(thisInt + 1, thisInt)
  def roundDownToOdd = thisInt.ifEven(thisInt - 1, thisInt)    
   
  /** Decreases the value of an integer while that integer does not match condition */
  def decrementTill(f : Int => Boolean): Int = thisInt match
  { case i if f(i) => i
    case i => (i - 1).decrementTill(f)
  }
   
  @inline def hexStr: String = thisInt.toHexString.toUpperCase
  @inline def hexStr2: String = ife(hexStr.length == 1, "0" - hexStr, hexStr)
  def isDivBy3: Boolean = thisInt % 3 == 0
   
  def isDivBy4: Boolean = thisInt % 4 == 0
  def div4Rem1: Boolean = thisInt %% 4 == 1
  def div4Rem2: Boolean = thisInt %% 4 == 2
  def div4Rem3: Boolean = thisInt %% 4 == 3
   
  def isDivBy8: Boolean = thisInt % 8 == 0
  def div8Rem2: Boolean = thisInt %% 8 == 2
  def div8Rem4: Boolean = thisInt %% 8 == 4
  def div8Rem6: Boolean = thisInt %% 8 == 6
   
  def tenthsStr(tenths: Int): String =
  { val res = thisInt.abs * tenths.abs
    val mod = res % 10
    val str1 = (mod != 0).ifMod((res / 10).toString)(_ - "." - mod.toString)
    (thisInt < 0 |!& tenths < 0).ifMod(str1)("-" - _)
  }
   
  def hundredthsStr(hundreds: Int): String =
  { val res = thisInt.abs * hundreds.abs
    val rs = (res / 100).toString
    val str1 = res % 100 match
    { case 0 => rs
      case m if m % 10 == 0 => rs.dotAppend((m / 10).toString)
      case m => rs.dotAppend((m / 10).toString) - (m % 10).toString
    }
    (thisInt < 0  |!& hundreds < 0).ifMod(str1)("-" - _)
  }
  /** Only use positive value that won't overflow int) */
  def intToPower(operand: Int): Int = (0 to operand).foldLeft(thisInt)((acc, i) => acc * thisInt)
  @inline def plus1: Int = thisInt + 1
  @inline def minus1: Int = thisInt - 1
} 
   