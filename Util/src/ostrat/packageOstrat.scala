/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */

/** This is the root package for the Openstrat project. The top of this package contains, 32 bit Int based Colours, the Multiple type class, a
 *  show and persistence library using RCON (Name may change), Rich Compact Object Notation, array based compound value collections of same length
 *   elements, an Either based errors framework and general utilities. */
package object ostrat
{ import collection.mutable.ArrayBuffer, reflect.ClassTag
  //type Buffer[A] = ArrayBuffer[A]
  type EArr[A <: AnyRef] = EMon[Arr[A]]
  type RefsMulti[A <: AnyRef] = Arr[Multiple[A]]
  type ShowEq[A] = ShowT[A] with EqT[A]
  type AnyRefs = Arr[AnyRef]
  type Not[T] = { type L[U] = U NotSubTypeOf T }

  val TwoPower0: Int = 1
  val TwoPower1: Int = 2
  val TwoPower2: Int = 4
  val TwoPower3: Int = 8
  val TwoPower4: Int = 16
  val TwoPower5: Int = 32
  val TwoPower6: Int = 64
  val TwoPower7: Int = 128
  val TwoPower8: Int = 256
  val TwoPower9: Int = 512
  val TwoPower10: Int = 1024
  val TwoPower11: Int = 2048
  val TwoPower12: Int = 4096
  val TwoPower13: Int = 8192
  val TwoPower14: Int = 16384
  val TwoPower15: Int = 32768
  val TwoPower16: Int = 65536
  val TwoPower17: Int = 131072
  val TwoPower18: Int = 262144
  val TwoPower19: Int = 524288
  val TwoPower20: Int = 524288
  val TwoPower21: Int = 1048576

  /** The tangent of 30 degrees or π/6 radians. */
  val Tan30: Double = 0.57735026919

  /** The cosine of 30 degrees or π/6 radians. */
  val Cos30: Double = 0.86602540378

  /** the cosine of 60 degrees or π/3 radians. */
  val Cos60: Double = 0.5

  /** The sine of 30 degrees or π/6 radians. */
  val Sin30: Double = 0.5

  /** The sine of 60 degrees or π/3 radians. */
  val Sin60: Double = 0.86602540378

  /** Pi. This has been named Pi1 to avoid a name clash if math._ is imported. */
  val Pi1: Double = math.Pi

  /** 2π */
  val Pi2 = math.Pi * 2

  /** π/2 */
  val PiOn2: Double = math.Pi / 2

  /** π/3 */
  val PiOn3: Double = math.Pi / 3

  /** π/4 */
  val PiOn4: Double = math.Pi / 4

  /** The square root of 2. */
  val Sqrt2: Double = 2.sqrt

  /** The square root of 3. */
  val Sqrt3: Double = 3.sqrt

  /** Gives the scalar hypotenuse length for a right angled triangle form the paramter lengths of the other 2 sides. */
  def hypotenuse(side1: Double, side2: Double): Double = (side1.squared + side2.squared).sqrt

  /** Gives the average of the supplied values. */
  def average(d1: Double, tail: Double *): Double = (d1 + tail.sum) / (tail.length + 1)

  def NoRef[A <: AnyRef]: OptRef[A] = new OptRef[A](null.asInstanceOf[A])

  /** onlyIf-do. Only if the condition is true, perform the effect. */
  inline def onlyIf[U](b: Boolean, vTrue: => Unit): Unit = if(b) vTrue else ()

  /** if-else. If the condition is true, use 2nd parameter, else use 3rd parameter. */
  inline def ife[A](b: Boolean, vTrue: => A, vFalse: => A): A = if (b) vTrue else vFalse

  /** ifNot-else. If the condition is false, use 2nd parameter, else use 3rd parameter. */
  @inline def ifne[A](b: Boolean, vNotTrue: => A, visTrue: => A): A = if (b) vNotTrue else vNotTrue

  /** if-elseif-else. If the first condition is true, use 2nd parameter, else if the second condition in parameter 3 is true use 4th parameter. */
  @inline def ife2[A](b1: Boolean, vTrue1: => A, b2: => Boolean, vTrue2: => A, vElse: => A): A = if (b1) vTrue1 else if (b2) vTrue2 else vElse

  @inline def ife3[A](b1: Boolean, vTrue1: => A, b2: => Boolean, vTrue2: => A, b3: => Boolean, vTrue3: => A, vElse: => A): A =
    if (b1) vTrue1 else if (b2) vTrue2 else if (b3) vTrue3 else vElse

  @inline def ife4[A](b1: Boolean, vTrue1: => A, b2: => Boolean, vTrue2: => A, b3: => Boolean, vTrue3: => A, b4: => Boolean, vTrue4: => A, vElse: => A): A =
    if (b1) vTrue1 else if (b2) vTrue2 else if (b3) vTrue3 else if(b4) vTrue4 else vElse

  @inline def ife5[A](b1: Boolean, vTrue1: => A, b2: => Boolean, vTrue2: => A, b3: => Boolean, vTrue3: => A, b4: => Boolean, vTrue4: => A,
    b5: => Boolean, vTrue5: => A, vElse: => A): A =
    if (b1) vTrue1 else if (b2) vTrue2 else if (b3) vTrue3 else if(b4) vTrue4 else if(b5) vTrue5 else vElse

  @inline def if2Excep[A](b1: Boolean, vTrue1: => A, b2: Boolean, vTrue2: => A, excepStr: => String): A =
    if (b1) vTrue1 else if (b2) vTrue2 else  throw new Exception(excepStr)

  @inline def if3Excep[A](b1: Boolean, vTrue1: => A, b2: Boolean, vTrue2: => A, b3: Boolean, vTrue3: => A, excepStr: => String): A =
    if (b1) vTrue1 else if (b2) vTrue2 else if (b3) vTrue3 else throw new Exception(excepStr)

  @inline def excep(str: => String): Nothing = throw new Exception(str)
  @inline def ifExcep(b: Boolean, str: => String): Unit = if(b) throw new Exception(str)
  @inline def ifNotExcep(b: Boolean, str: => String): Unit = if(!b) throw new Exception(str)

  def eqOf[A](leftValue: A, rightValues: A *): Boolean = rightValues.contains(leftValue)

  /** Not sure what this method does. */
  def readT[T](implicit ev: Persist[T]): T =
  { val artStr = ev.typeStr.prependIndefiniteArticle
    def loop(inp: EMon[T]): T = inp match
    { case Good(t) => t
      case a =>
      { println(a)
        loop(io.StdIn.readLine ("That was not a single "+ ev.typeStr + ". Please enter " + artStr).asType[T])
      }
    }
    loop(io.StdIn.readLine ("Please enter " + artStr).asType[T])
  }

  def readInt: Int = readT[Int]
  def readDouble: Double = readT[Double]

  @inline def Buffer[A](inp: A*): ArrayBuffer[A] = ArrayBuffer[A](inp :_*)


  @inline def Buff[A](initialLength: Int = 5): ArrayBuffer[A] = new ArrayBuffer[A](initialLength)
  @inline def buffInt(initialLength: Int = 5): ArrayBuffer[Int] = new ArrayBuffer[Int](initialLength)

  type RefTag[A] = AnyRef with reflect.ClassTag[A]

  /** Not sure about this method. */
  def parseErr(fp: TextPosn, detail: String): String = fp.fileName -- fp.lineNum.toString + ", " + fp.linePosn.toString + ": " + detail

  def bad1[B](fs: TextSpan, detail: String): Bad[B] = Bad[B](StringArr(parseErr(fs.startPosn, detail)))
  def badNone[B](detail: String): Bad[B] = Bad[B](StringArr(detail))

  def eTry[A](res: => A): EMon[A] =
    try Good[A](res) catch { case scala.util.control.NonFatal(e) => TextPosn("Java Exception", 1, 1).bad(e.getMessage) }

  def commaedInts(iSeq: Int*) = iSeq.map(_.toString).commaFold

  val two32: Long = 4294967296L
  def twoIntsToDouble(i1: Int, i2: Int): Double = { val lg  = (i1.toLong << 32) | (i2 & 0xFFFFFFFFL); java.lang.Double.longBitsToDouble(lg) }

  /** Not sure if this correct. This might throw on iStep = 0. */
  def iDblToMap[A, AA <: SeqImut[A]](iFrom: Double, iTo: Double, iStep: Double = 1)(f: Double => A)(implicit ev: ArrBuilder[A, AA]): AA =
  { val iLen = (iTo - iFrom + 1).min(0) / iStep
    val res: AA = ev.newArr(iLen.toInt)
    var count = 0
    @inline def i: Double = iFrom + count * iStep

    while(i <= iTo)
    { ev.arrSet(res, count, f(i))
      count += 1
    }
    res
  }

  /** Foreachs over a range of integers from parameter 1 to parameter 2 in steps of parameter 3. Throws on non termination. Method name over loaded
   * with a first parameter list of a single iTo parameter, where iFrom is 0 and iStep is 1. */
  def iToForeach(iFrom: Int, iTo: Int, iStep: Int = 1)(f: Int => Unit): Unit =
  { if (iTo != iFrom & iStep == 0) throw excep("Loop step can not be 0.")
    var i: Int = iFrom
    while(ife(iStep > 0, i <= iTo, i >= iTo)) { f(i); i += iStep }
  }

  /** Foreachs over a range of integers from 0 to the given parameter in steps of 1. Throws on non termination. Method name over loaded with a range
   *  of integers from parameter 1 to parameter 2 in steps of parameter 3. */
  def iToForeach(iTo: Int)(f: Int => Unit): Unit =
  { if (iTo < 0) throw excep("Loop will not terminate with positive step.")
    var i: Int = 0
    while(i <= iTo) { f(i); i += 1 }
  }

  /** Foreachs over a range of integers from parameter 1 until parameter 2 in steps of parameter 3. Throws on non termination. Method name over loaded
   * with a first parameter list of a single iTo parameter, where iFrom is 0 and iStep is 1. Method name over loaded with a first parameter list of a
   * single iUntil parameter, where iFrom is 0 and iStep is 1. */
  def iUntilForeach(iFrom: Int, iUntil: Int, iStep: Int = 1)(f: Int => Unit): Unit =
  { if (iStep == 0) throw excep("Loop step can not be 0.")
    var i: Int = iFrom
    while(ife(iStep > 0, i < iUntil, i > iUntil)) { f(i); i += iStep }
  }

  /** Foreachs over a range of integers from 0 until the parameter in steps of 1. Throws on non termination. Method name over loaded with a range
   *  of integers from parameter 1 until parameter 2 in steps of parameter 3. */
  def iUntilForeach(iUntil: Int)(f: Int => Unit): Unit =
  { if (iUntil < 0) throw excep(s"Loop will not reach $iUntil and terminate with positive step.")
    var i: Int = 0
    while(i < iUntil) { f(i); i += 1 }
  }

  /** Maps over a range of Ints returning a [[SeqImut]][A]. From the iFrom parameter value to the iTo parameter value in integer steps. Default step
   *  value is 1.Throws on non termination. Method name over loaded with a first parameter list of a single iUntil parameter, where iFrom is 0 and
   *  iStep is 1.  */
  def iToMap[A, AA <: SeqImut[A]](iFrom: Int, iTo: Int, iStep: Int = 1)(f: Int => A)(implicit ev: ArrBuilder[A, AA]): AA =
  { val iLen = (iTo - iFrom + iStep).max(0) / iStep
    val res: AA = ev.newArr(iLen)
    var index = 0
    iToForeach(iFrom, iTo, iStep){ count => ev.arrSet(res, index, f(count)); index += 1  }
    res
  }

  /** Maps over a range of Ints returning a [[SeqImut]][A]. From 0 to to the iTo value in steps of 1. Throws on non termination. Method name over
   *  loaded with a range of integers from parameter 1 to parameter 2 in steps of parameter 3. */
  def iToMap[A, AA <: SeqImut[A]](iTo: Int)(f: Int => A)(implicit ev: ArrBuilder[A, AA]): AA =
  { val iLen = (iTo + 1).max(0)
    val res: AA = ev.newArr(iLen)
    var index = 0
    iToForeach(iTo){ count => ev.arrSet(res, index, f(count)); index += 1  }
    res
  }

  /** Maps a range of Ints returning a [[SeqImut]][A]. From the iFrom value until the iUntil value in steps of iStep. Default step value is 1. Throws
   *  on non termination. Method name over loaded with a first parameter list of a single iUntil parameter, where iFrom is 0 and iStep is 1. */
  def iUntilMap[A, AA <: SeqImut[A]](iFrom: Int, iUntil: Int, iStep: Int = 1)(f: Int => A)(implicit ev: ArrBuilder[A, AA]): AA =
  { val iLen = (iUntil - iFrom).max(0) / iStep
    val res: AA = ev.newArr(iLen)
    var index = 0
    iUntilForeach(iFrom, iUntil, iStep){ count => ev.arrSet(res, index, f(count)); index += 1  }
    res
  }

  /** Maps a range of Ints to returning a [[SeqImut]][A]. From 0 until the iUntil parameter value in steps of 1. Throws on non termination. Method
   *  name over loaded with a range of integers from parameter 1 until parameter 2 in steps of parameter 3. */
  def iUntilMap[A, AA <: SeqImut[A]](iUntil: Int)(f: Int => A)(implicit ev: ArrBuilder[A, AA]): AA =
  { val iLen = (iUntil).max(0)
    val res: AA = ev.newArr(iLen)
    var index = 0
    iUntilForeach(iUntil){ count => ev.arrSet(res, index, f(count)); index += 1  }
    res
  }

  /** FlatMaps over a range of Ints returning a [[SeqImut]][A]. From the iFrom parameter value to the iTo parameter value in steps of iStep parameter.
   *  Default step value is 1. Throws on non termination. Method name over loaded with a first parameter list of a single iTo parameter, where iFrom
   *  is 0 and iStep is 1. */
  def iToFlatMap[AA <: SeqImut[_]](iFrom: Int, iTo: Int, iStep: Int = 1)(f: Int => AA)(implicit ev: ArrFlatBuilder[AA]): AA =
  { val buff = ev.newBuff()
    iToForeach(iFrom, iTo, iStep){ i => ev.buffGrowArr(buff, f(i)) }
    ev.buffToBB(buff)
  }

  /** FlatMaps over a range of Ints returning a [[SeqImut]][A]. From 0 to the iTo parameter value steps of 1. Throws on non termination.Method name
   *  over loaded with a range of integers from parameter 1 to parameter 2 in steps of parameter 3. */
  def iToFlatMap[AA <: SeqImut[_]](iTo: Int)(f: Int => AA)(implicit ev: ArrFlatBuilder[AA]): AA =
  { val buff = ev.newBuff()
    iToForeach(iTo){ i => ev.buffGrowArr(buff, f(i)) }
    ev.buffToBB(buff)
  }

  /** FlatMaps over a range of Ints returning a [[SeqImut]][A]. From the iFrom parameter value until the iUntil paraemter value in integer steps of
   * iStep. Default step value is 1. Throws on non termination. Method name over loaded with a first parameter list of a single iUntil parameter,
   * where iFrom is 0 and iStep is 1. */
  def iUntilFlatMap[AA <: SeqImut[_]](iFrom: Int, iUntil: Int, iStep: Int = 1)(f: Int => AA)(implicit ev: ArrFlatBuilder[AA]): AA =
  { val buff = ev.newBuff()
    iUntilForeach(iFrom, iUntil, iStep){ i => ev.buffGrowArr(buff, f(i)) }
    ev.buffToBB(buff)
  }

  /** FlatMaps over a range of Ints returning a [[SeqImut]][A]. From 0 until the iUntil parameter value in integer steps of 1. Throws on non
   *  termination. Method name over loaded with a range of integers from parameter 1 until parameter 2 in steps of parameter 3. */
  def iUntilFlatMap[AA <: SeqImut[_]](iUntil: Int)(f: Int => AA)(implicit ev: ArrFlatBuilder[AA]): AA =
  { val buff = ev.newBuff()
    iUntilForeach(iUntil){ i => ev.buffGrowArr(buff, f(i)) }
    ev.buffToBB(buff)
  }

  /** Folds over a range of Ints to an Int, adding the return [[Int]] value to the accumulator. From the start value to (while index is less than or
   *  equal to) the end value in integer steps. Default step value is 1. Throws on non termination. */
  def iToIntSum(iFrom: Int, iTo: Int, iStep: Int = 1, accInit: Int = 0)(f: Int => Int): Int =
  { var acc = accInit
    iToForeach(iFrom, iTo, iStep){ i => acc += f(i) }
    acc
  }

  /** Foreachs over a looped range of integers from parameter 1 to parameter 2 in steps of parameter 3. Throws on non termination. */
  def iLoopToForeach(loopEnd: Int, loopStart: Int = 0)(iFrom: Int, iTo: Int, iStep: Int = 1)(f: Int => Unit): Unit =
  { if (iTo == iFrom & iStep == 0) throw excep("Loop step can not be 0.")

    var i: Int = iFrom
    var continue = true
    if(iStep > 0) while(continue)
    { f(i)
      if (i < iTo & (i + iStep) > iTo) continue = false
      i += iStep
      if (i > loopEnd) i = loopStart + i - loopEnd
    }
    else while(continue)
    { f(i)
      if (i > iTo & (i + iStep) < iTo) continue = false
      i += iStep
      if (i < loopStart) i = loopEnd + i - loopStart
    }
  }

  /** Folds over a range of Ints to an Int adding the return [[Int]] value to the accumulator. From the start value until (while index is less than)
   *  the end value in integer steps. Default step value is 1. */
  def iUntilIntSum(iFrom: Int, iUntil: Int, iStep: Int = 1, accInit: Int = 0)(f: Int => Int): Int =
  { var acc = accInit
    iUntilForeach(iFrom, iUntil, iStep){ i => acc += f(i) }
    acc
  }

  /** 2 dimensional from-to-step foreach loop. Throws on non termination. */
  def ijToForeach(iFrom: Int, iTo: Int, iStep: Int = 1)(jFrom: Int, jTo: Int, jStep: Int = 1)(f: (Int, Int) => Unit): Unit =
    iToForeach(iFrom, iTo, iStep){ i => iToForeach(jFrom, jTo, jStep){ j => f(i, j)}}

  /** 2 dimensional from 0-to-step foreach loop. Throws on non termination. */
  def ijToForeach(iTo: Int)(jTo: Int)(f: (Int, Int) => Unit): Unit =
    iToForeach(iTo){ i => iToForeach(jTo){ j => f(i, j)}}

  /** 2 dimensional from-until-step foreach loop. Throws on non termination. i is the index for the outer loop. j is the index for the inner loop.
   *  The method name is overloaded with a variant that takes single iUntil and jUntil parameters where the loop starts at 0 and has a step of 1. */
  def ijUntilForeach(iFrom: Int, iUntil: Int, iStep: Int = 1)(jFrom: Int, jUntil: Int, jStep: Int = 1)(f: (Int, Int) => Unit): Unit =
    iUntilForeach(iFrom, iUntil, iStep){ i => iUntilForeach(jFrom, jUntil, jStep){ j => f(i, j)}}

  /** 2 dimensional from-until-step foreach loop. Throws on non termination. i is the index for the outer loop. j is the index for the inner loop. The
   *  method name is overloaded with a variant that takes 3 parameters for the i and j loops.*/
  def ijUntilForeach(iUntil: Int)(jUntil: Int)(f: (Int, Int) => Unit): Unit =
    iUntilForeach(iUntil){ i => iUntilForeach(jUntil){ j => f(i, j)}}

  /** 2 dimensional map function. i is the index for the outer loop. j is the index for the inner loop. maps over 2 ranges of Ints to an ArrBase[A].
   * From the start value to (while index is less than or equal to) the end value in integer steps. Default step values are 1. */
  def ijToMap[A, AA <: SeqImut[A]](iFrom: Int, iTo: Int, iStep: Int = 1)(jFrom: Int, jTo: Int, jStep: Int = 1)(f: (Int, Int) => A)
    (implicit ev: ArrBuilder[A, AA]): AA =
  { val iLen = (iTo - iFrom + iStep).max(0) / iStep
    val jLen = (jTo - jFrom + jStep).max(0) / jStep
    val arrLen = iLen * jLen
    val res = ev.newArr(arrLen)
    var arrIndex = 0

    ijToForeach(iFrom, iTo, iStep)(jFrom, jTo, jStep){ (i, j) =>
      ev.arrSet(res, arrIndex, f(i, j))
      arrIndex += 1
    }
    res
  }

  /** 2 dimensional map function. i is the index for the outer loop. j is the index for the inner loop. maps over 2 ranges of Ints to an ArrBase[A].
   * From the start value to (while index is less than or equal to) the end value in integer steps. Default step values are 1. */
  def ijToMap[A, AA <: SeqImut[A]](iTo: Int)(jTo: Int)(f: (Int, Int) => A)
    (implicit ev: ArrBuilder[A, AA]): AA =
  { val iLen = (iTo + 1).max(0)
    val jLen = (jTo + 1).max(0)
    val arrLen = iLen * jLen
    val res = ev.newArr(arrLen)
    var arrIndex = 0

    ijToForeach(iTo)(jTo){ (i, j) =>
      ev.arrSet(res, arrIndex, f(i, j))
      arrIndex += 1
    }
    res
  }

  /** 2 dimensional map function.  i is the index for the outer loop. j is the index for the inner loop. maps over 2 ranges of Ints to an ArrBase[A].
   * From the start value until (while index is less than) the end value in integer steps. Default step values are 1. */
  def ijUntilMap[A, AA <: SeqImut[A]](iFrom: Int, iUntil: Int, iStep: Int = 1)(jFrom: Int, jUntil: Int, jStep: Int = 1)(f: (Int, Int) => A)(
    implicit ev: ArrBuilder[A, AA]): AA =
  { val iLen = (iUntil - iFrom).max(0) / iStep
    val jLen = (jUntil - jFrom).max(0) / jStep
    val arrLen = iLen * jLen
    val res = ev.newArr(arrLen)
    var arrIndex = 0

    ijUntilForeach(iFrom, iUntil, iStep)(jFrom, jUntil, jStep){ (i, j) =>
      ev.arrSet(res, arrIndex, f(i, j))
      arrIndex += 1
    }
    res
  }

  /** 2 dimensional map function.  i is the index for the outer loop. j is the index for the inner loop. maps over 2 ranges of Ints to an ArrBase[A].
   * From the start value until (while index is less than) the end value in integer steps. Default step values are 1. */
  def ijUntilMap[A, AA <: SeqImut[A]](iUntil: Int)(jUntil: Int)(f: (Int, Int) => A)(
    implicit ev: ArrBuilder[A, AA]): AA =
  { val iLen = (iUntil).max(0)
    val jLen = (jUntil).max(0)
    val arrLen = iLen * jLen
    val res = ev.newArr(arrLen)
    var arrIndex = 0

    ijUntilForeach(iUntil)(jUntil){ (i, j) =>
      ev.arrSet(res, arrIndex, f(i, j))
      arrIndex += 1
    }
    res
  }

  /** 3 dimensional from-to-step foreach loop. Throws on non termination. */
  def ijkToForeach(iFrom: Int, iTo: Int, iStep: Int = 1)(jFrom: Int, jTo: Int, jStep: Int = 1)(kFrom: Int, kTo: Int, kStep: Int = 1)
    (f: (Int, Int, Int) => Unit): Unit =
    iToForeach(iFrom, iTo, iStep){ i => iToForeach(jFrom, jTo, jStep){ j => iToForeach(kFrom, kTo, kStep){ k => f(i, j, k) } } }

  /** 2 dimensional map function.  i is the index for the outer loop. j is the index for the inner loop. maps over 2 ranges of Ints to an ArrBase[A].
   * From the start value to (while index is less than or equal to) the end value in integer steps. Default step values are 1. */
  def ijkToMap[A, AA <: SeqImut[A]](iFrom: Int, iTo: Int, iStep: Int = 1)(jFrom: Int, jTo: Int, jStep: Int = 1)(kFrom: Int, kTo: Int, kStep: Int = 1)
    (f: (Int, Int, Int) => A)(implicit ev: ArrBuilder[A, AA]): AA =
  { val iLen = (iTo - iFrom + iStep).max(0) / iStep
    val jLen = (jTo - jFrom + jStep).max(0) / jStep
    val kLen = (kTo - kFrom + kStep).max(0) / jStep
    val arrLen = iLen * jLen * kLen
    val res = ev.newArr(arrLen)
    var arrIndex = 0

    ijkToForeach(iFrom, iTo, iStep)(jFrom, jTo, jStep)(kFrom, kTo, kStep){ (i, j, k) =>
      ev.arrSet(res, arrIndex, f(i, j, k))
      arrIndex += 1
    }
    res
  }

  /** Extension class for String interpolation. */
  implicit class StringContextExtensions(sc: StringContext) {
    /** String interpolator for base 32 numbers. */
    def t(): Int = sc.parts(0).foldLeft(0){ (acc, ch) =>
      val newDig: Int = ch match {
        case d if d.isDigit => d - '0'
        case l if l >= 'A' & l <= 'N' => l - 'A' + 10
        case l if l >= 'P' & l <= 'W' => l - 'P' + 24
        case ch => excep(ch.toString + "is an invalid char for base 32.")
      }
      acc * 32 + newDig
    }
  }

  implicit class MapExtensions[K, V](val thisMap: Map[K, V])
  {
    def replaceValue(key: K, newValue: V): Map[K, V] = thisMap.map{
      case (k, _) if k == key => (k, newValue)
      case p => p
    }

    def modValue(key: K)(f: V => V): Map[K, V] = thisMap.map{
      case (k, v) if k == key => (k, f(v))
      case p => p
    }
  }

  implicit class ArrayBufferDoubleExtensions(thisBuff: ArrayBuffer[Double])
  { def app2(prod: ElemDbl2): Unit = { thisBuff.append(prod.dbl1); thisBuff.append(prod.dbl2) }
  }

  /* This needs to be removed. */
  implicit class FunitRichImp(fu: () => Unit)
  { def +(operand: () => Unit): () => Unit = () => {fu() ; operand()}
  }

  implicit class Tuple2Implicit[A1, A2](thisTuple: Tuple2[A1, A2])
  {
    def bimap[B1, B2](f1: A1 => B1, f2: A2 => B2): Tuple2[B1, B2] = (f1(thisTuple._1), f2(thisTuple._2))
    def f2[B](f: (A1, A2) => B): B = f(thisTuple._1, thisTuple._2)
  }

  implicit class EqerImplicit[T](thisT: T)(implicit ev: EqT[T])
  { /** Equals. An alternative type class based equals method. */
    def ===(operand: T): Boolean = ev.eqT(thisT, operand)

    /** Not-equals. An alternative type class based not-equals method. */
    def !==(operand: T): Boolean = !ev.eqT(thisT, operand)
  }

  /** Extension methods for approximation type class. */
  implicit class ApproxImplicitClass[D, T](thisT: T)(implicit ev: ApproxT[D, T])
  { /** tests if operand is approximately equal. */
    def =~(operand: T, precision: D = ev.precisionDefault): Boolean = ev.approxT(thisT, operand, precision)

    /** tests if operand is approximately not equal. */
    def !=~(operand: T, precision: D = ev.precisionDefault): Boolean = !ev.approxT(thisT, operand, precision)
  }

  /** Needs Changing. */
  implicit class RefBufferExtensions[A <: AnyRef](thisBuff: ArrayBuffer[A])
  { @inline def toArr(implicit ct: ClassTag[A]): Arr[A] = new Arr[A](thisBuff.toArray[A])
    def goodRefs(implicit ct: ClassTag[A]): Good[Arr[A]] = Good(new Arr(thisBuff.toArray))

    def toReverseRefs(implicit ct: ClassTag[A]): Arr[A] =
    { val len = thisBuff.length
      val acc: Array[A] = new Array[A](len)
      var count = 0

      while (count < len)
      { acc(count) = thisBuff(len - 1 - count)
        count += 1
      }
      new Arr(acc)
    }
  }
  
  implicit class ArrayBufferExtensions[A](thisBuff: ArrayBuffer[A])(implicit ct: ClassTag[A])
  { /** If length of this ArrayBuffer is one, perform side effecting function on the sole element. */
    def foreachLen1(f: A => Unit): Unit = if (thisBuff.length == 1) f(thisBuff(0))
  }

  implicit class RangeExtensions(range: Range)
  {
    /** maps to a [[SeqImut]] rather than a standard Scala collection class. */
    def mapArr[B <: ElemValueN , M <: SeqImut[B]](f: Int => B)(implicit build: ArrBuilder[B, M]): M =
    { val res = build.newArr(range.size)
      var count: Int = 0
      range.foreach { orig =>
        val newValue: B = f(orig)
        res.unsafeSetElem(count, newValue)
        count += 1
      }
      res
    }
  }

  implicit class ArrayIntExtension(array: Array[Int])
  {
    def set2Elems(index: Int, i1: Int, i2: Int): Unit = { array(index * 2) = i1; array(index * 2 + 1) = i2 }
  }

  implicit def AnyTypeToExtensions[T](thisT: T): AnyTypeExtensions[T] = new AnyTypeExtensions[T](thisT)
  implicit def AnyRefTypeToExtensions[T <: AnyRef](thisT: T): AnyRefTypeExtensions[T] = new AnyRefTypeExtensions[T](thisT)
  implicit def arrayToExtensions[A](arr: Array[A]): ArrayExtensions[A] = new ArrayExtensions[A](arr)
  implicit def arrayValueNElemToExtensions[A <: ElemValueN](arr: Array[A]): ArrayValueNElemExtensions[A] = new ArrayValueNElemExtensions[A](arr)
  implicit def booleanToExtensions(b: Boolean): BooleanExtensions = new BooleanExtensions(b)
  implicit def doubleToExtensions(d: Double): DoubleImplicit = new DoubleImplicit(d)
  implicit def intToExtensions(i: Int): IntExtensions = new IntExtensions(i)
  implicit def iterableToExtensions[A](iter: Iterable[A]): IterableExtensions[A] = new IterableExtensions[A](iter)
  implicit def iterableValueNElemToExtensions[A <: ElemValueN](iter: Iterable[A]): IterableValueNElemExtensions[A] = new IterableValueNElemExtensions[A](iter)
  implicit def listToExtensions[A](thisList: List[A]): ListExtensions[A] = new ListExtensions[A](thisList)

  implicit def charToExtensions(thisChar: Char): CharExtensions = new CharExtensions(thisChar)
  implicit def longToImplicit(i: Long): LongExtensions = new LongExtensions(i)
  implicit def optionToExtension[A](thisOption: Option[A]): OptionExtensions[A] = new OptionExtensions(thisOption)

  implicit def seqToExtensions[A](thisSeq: Seq[A]): SeqExtensions[A] = new SeqExtensions(thisSeq)
  implicit def showTToExtensions[A](thisVal: A)(implicit ev: ShowT[A]): ShowTExtensions[A] = new ShowTExtensions[A](ev, thisVal)
  implicit def show2TypeToExtensions[A1, A2,  T](thisVal: T)(implicit ev: Show2T[A1, A2, T]): Show2TExtensions[A1, A2, T] =
    new Show2TExtensions[A1, A2, T](ev, thisVal)

  implicit def stringToExtensions(s: String): StringImplicit = new StringImplicit(s)
  implicit def stringIterableToExtensions(strIter: Iterable[String]): StringIterableExtensions = StringIterableExtensions(strIter)
  implicit def stringArrayToExtensions(strArray: Array[String]): StringIterableExtensions = StringIterableExtensions(strArray)
}