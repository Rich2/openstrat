/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */

/** This is the root package for the Openstrat project. The top of this package contains, 32 bit Int based Colours, the Multiple type class, a
 *  show and persistence library using RCON (Name may change), Rich Compact Object Notation, array based compound value collections of same length
 *   elements, an Either based errors framework and general utilities. */
package object ostrat
{
  import collection.immutable.ArraySeq, collection.mutable.ArrayBuffer, reflect.ClassTag

  type Arr[A] = ArraySeq[A]
  type Buff[A] = ArrayBuffer[A]
  type EMonList[A] = EMon[List[A]]
  type EMonArr[A] = EMon[Arr[A]]
  type ArrMulti[A] = Arr[Multiple[A]]
  val Tan30 = 0.577350269f;
  val Cos30 = 0.866025404f;
  val Cos60 = 0.5
  val Sin30 = 0.5
  val Sin60 = 0.866025404f;
  val Pi2 = math.Pi * 2
  val PiH = math.Pi / 2
  def prints(objs: Any*): Unit = println(objs.map(_.toString).commaFold)
  @inline def oif[U](b: Boolean, vTrue: => Unit): Unit = if(b) vTrue
  @inline def ife[A](b: Boolean, vTrue: => A, vFalse: => A): A = if (b) vTrue else vFalse
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
  def ifSeq[A](b: Boolean, vTrue: => Seq[A]): Seq[A] = if (b) vTrue else Seq()
  def ifSeq1[A](b: Boolean, vTrue: => A): Seq[A] = if (b) Seq(vTrue) else Seq()
  def ifSome[A](b: Boolean, vTrue: => A): Option[A] = if (b) Some(vTrue) else None
  def eqOf[A](leftValue: A, rightValues: A *): Boolean = rightValues.contains(leftValue)

  def readT[T](implicit ev: Persist[T]): T =
  { val artStr = ev.typeStr.prependIndefiniteArticle
    def loop(inp: EMon[T]): T = inp match
    { case Good(t) => t
      case _ =>
      { println
        loop(io.StdIn.readLine ("That was not a single "+ ev.typeStr + ". Please enter " + artStr).asType[T])
      }
    }
    loop(io.StdIn.readLine ("Please enter " + artStr).asType[T])
  }

  def readInt: Int = readT[Int]
  def readDouble: Double = readT[Double]

  @inline def ArrWrap[A](mutArray: Array[A]): ArraySeq[A] = ArraySeq.unsafeWrapArray[A](mutArray)
  @inline def ArrWrapBuff[A](buff: ArrayBuffer[A])(implicit ct: ClassTag[A]): ArraySeq[A] = ArraySeq.unsafeWrapArray[A](buff.toArray)
  @inline def Buff[A](initialLength: Int = 5): ArrayBuffer[A] = new ArrayBuffer[A](initialLength)

  type ParseExpr = pParse.Expr
  type RefTag[A] = AnyRef with reflect.ClassTag[A]
  //type LeftRight[A] = Either[A, A]
  //type Trav[A] = Traversable[A]  
  type FStr = Function0[String]
  type FStrSeq = Seq[Function0[String]]  

  type StrList = List[String]
  
  /** Product2[Int, Int] with Stringer. These are used in IntProduct2s Array[Double] based collections. */
  type ProdI2 = Product2[Int, Int]
  /** Product2[Int, Int, Int, Int] with Stringer. These are used in IntProduct2s Array[Double] based collections. */
  type ProdI4 = Product4[Int, Int, Int, Int]
  /** Product2[Double, Double] with Stringer. These are used in DoubleProduct2s Array[Double] based collections. */
  type ProdD2 = Product2[Double, Double]  
  /** Product3[Double, Double, Double]. These are used in DoubleProduct3s Array[Double] based collections. */
  type ProdD3 = Product3[Double, Double, Double]
  /** Product4[Double, Double, Double, Double]. These are used in DoubleProduct4s Array[Double] based collections. */
  type ProdD4 = Product4[Double, Double, Double, Double]  
  /** Product5[Double, Double, Double, Double, Double]. These are used in DoubleProduct5s Array[Double] based collections. */
  type ProdD5 = Product5[Double, Double, Double, Double, Double]
  /** Product6[Double, Double, Double, Double, Double, Double]. These are used in DoubleProduct6s Array[Double] based collections. */
  type ProdD6 = Product6[Double, Double, Double, Double, Double, Double]
  /** Product7[Double, Double, Double, Double, Double, Double, Double]. These are used in DoubleProduct7s Array[Double] based collections. */
  type ProdD7 = Product7[Double, Double, Double, Double, Double, Double, Double]

  def Good3[A1, A2, A3](a1: A1, a2: A2, a3: A3): Good[(A1, A2, A3)] = Good[(A1, A2, A3)]((a1, a2, a3)) 
  /** Not sure about this method. */
  def parseErr(fp: TextPosn, detail: String): String = fp.toString + detail
  def bad1[B](fp: TextPosn, detail: String): Bad[B] = Bad[B](parseErr(fp, detail) :: Nil)
  def bad1[B](fs: TextSpan, detail: String): Bad[B] = Bad[B](parseErr(fs.startPosn, detail) :: Nil)
  def eTry[A](res: => A): EMon[A] =
    try Good[A](res) catch { case scala.util.control.NonFatal(e) => bad1(FilePosn("Java Exception", 1, 1), e.getMessage) }
  def commaedInts(iSeq: Int*) = iSeq.map(_.toString).commaFold

  val two32: Long = 4294967296L
  def twoIntsToDouble(i1: Int, i2: Int): Double = { val lg  = (i1.toLong << 32) | (i2 & 0xFFFFFFFFL); java.lang.Double.longBitsToDouble(lg) }
    
  def nullRef[A <: AnyRef]: Opt[A] = new Opt[A](null.asInstanceOf[A])
  
  @inline def doubleFromTo(fromValue: Double, toValue: Double, step: Double): List[Double] = {
    var count = fromValue
    var acc: List[Double] = Nil
    while (count <= toValue) {
      acc ::= count
      count += step
    }
    acc.reverse
  }

  def iUntilMap[A](iFrom: Int, iUntil: Int, iStep: Int = 1)(f: Int => A)(implicit ct: ClassTag[A]): Arr[A] = iToMap[A](iFrom, iUntil - 1, iStep)(f)

  def iToMap[A](iFrom: Int, iTo: Int, iStep: Int = 1)(f: Int => A)(implicit ct: ClassTag[A]): Arr[A] =
  {
    val iLen = (iTo - iFrom + 1).min(0) / iStep
    val array: Array[A] = new Array[A](iLen)
    var count = 0
    @inline def i: Int = iFrom + count * iStep
    while(i <= iTo)
    { array(count) = f(i)
      count += 1
    }
    array.toArr
  }

  def iToForeach(iFrom: Int, iTo: Int, iStep: Int = 1)(f: Int => Unit): Unit =
  { var i: Int = iFrom
    while(i <= iTo) { f(i); i += iStep }
  }

  def ijToForeach(iFrom: Int, iTo: Int, iStep: Int = 1)(jFrom: Int, jTo: Int, jStep: Int = 1)(f: (Int, Int) => Unit): Unit =
  { var i = iFrom

    while(i <= iTo)
    { var j = jFrom
      while(j <= jTo){ f(i, j); j += jStep }
      i += iStep
    }
  }

  def iUntilForeach(iFrom: Int, iUntil: Int, iStep: Int = 1)(f: Int => Unit): Unit = iToForeach(iFrom, iUntil - 1, iStep)(f)

  def ijUntilMap[A](iFrom: Int, iUntil: Int, iStep: Int = 1)(jFrom: Int, jUntil: Int, jStep: Int = 1)(f: (Int, Int) => A)(implicit ct: ClassTag[A]):
    Arr[A] = ijToMap[A](iFrom, iUntil - 1, iStep)(jFrom, jUntil - 1, jStep)(f)

  def ijToMap[A](iFrom: Int, iTo: Int, iStep: Int = 1)(jFrom: Int, jTo: Int, jStep: Int = 1)(f: (Int, Int) => A)(implicit ct: ClassTag[A]): Arr[A] =
  {
    val iLen = (iTo - iFrom + 1).min(0) / iStep
    val jLen = (jTo - jFrom + 1).min(0) / jStep
    val arrLen = iLen * jLen
    val array: Array[A] = new Array[A](arrLen)
    var i: Int = iFrom
    while(i <= iTo)
    {
      var j: Int = jFrom
      while(j <= jTo)
      { array(i * jLen + j) = f(i, j)
        j += 1
      }
      i += 1
    }
    array.toArr
  }

  def iiToMap[A](nFrom: Int, nTo: Int, nStep: Int = 1)(f: (Int, Int) => A)(implicit ct: ClassTag[A]): Arr[A] =
    ijToMap[A](nFrom, nTo, nStep)(nFrom, nTo, nStep)(f)
  
  implicit class ArrayExtension[A](thisMutableArray: Array[A])
  { def toArr: Arr[A] = ArraySeq.unsafeWrapArray[A](thisMutableArray)
  }

  implicit class ArrayBufferExtensions[A](thisBuff: Buff[A])(implicit ct: ClassTag[A])
  { @inline def toArr: Arr[A] = ArrWrapBuff[A](thisBuff)
    @inline def arrAppends(operands: A*): Arr[A] = ArrWrapBuff[A]((thisBuff ++= operands))
    @inline def arrAppend(operand: A): Arr[A] = ArrWrapBuff[A]((thisBuff += operand))
    def pAdd (operand: ProductVals[A]): Buff[A] = { operand.foreach(thisBuff.addOne(_)); thisBuff }
  }
   
  implicit class FunitRichImp(fu: () => Unit)
  { def +(operand: () => Unit): () => Unit = () => {fu() ; operand()} 
  }   
   
  implicit class Tuple2Implicit[A, B](thisTuple: Tuple2[A, B])
  { def bimap[C, D](f1: A => C, f2: B => D): Tuple2[C, D] = (f1(thisTuple._1), f2(thisTuple._2))
    def tupleFold[C](f: (A, B) => C): C = f(thisTuple._1, thisTuple._2)
  }
  
  import pExt._
  implicit def AnyTypeToExtensions[T](thisT: T): AnyTypeExtensions[T] = new AnyTypeExtensions[T](thisT)
  implicit def arrayToExtensions[A](arr: Array[A]): ArrayExtensions[A] = new pExt.ArrayExtensions[A](arr)
  implicit def arrToArrExtensions[A](thisArr: Arr[A]): ArrExtensions[A] = new ArrExtensions[A](thisArr)
  implicit def booleanToExtensions(b: Boolean): BooleanExtensions = new BooleanExtensions(b)
  implicit def doubleToExtensions(d: Double): DoubleImplicit = new DoubleImplicit(d)
  implicit def intToExtensions(i: Int): IntExtensions = new IntExtensions(i)
  implicit def iterableToExtensions[A](iter: Iterable[A]): IterableExtensions[A] = new IterableExtensions[A](iter)
  implicit def listToExtensions[A](thisList: List[A]): ListExtensions[A] = new ListExtensions[A](thisList)

  implicit def charToExtensions(thisChar: Char): CharExtensions = new CharExtensions(thisChar)
  implicit def longToImplicit(i: Long): LongExtensions = new LongExtensions(i)
  implicit def optionToExtension[A](thisOption: Option[A]): OptionExtensions[A] = new OptionExtensions(thisOption)

  implicit def seqToExtensions[A](thisSeq: Seq[A]): SeqExtensions[A] = new SeqExtensions(thisSeq)
  implicit def showTypeToExtensions[A](thisVal: A)(implicit ev: ShowOnly[A]): ShowerTypeExtensions[A] = new ShowerTypeExtensions[A](ev, thisVal)
  implicit def persistTToStringerImplicit[A](thisVal: A)(implicit ev: Persist[A]): ShowerTypeExtensions[A] = new ShowerTypeExtensions[A](ev, thisVal)

  implicit def stringToExtensions(s: String): StringImplicit = new StringImplicit(s)
  implicit def stringIterableToExtensions(strIter: Iterable[String]): StringIterableExtensions = StringIterableExtensions(strIter)
  implicit def stringArrayToExtensions(strArray: Array[String]): StringIterableExtensions = StringIterableExtensions(strArray)
}