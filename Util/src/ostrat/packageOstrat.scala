/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */

/** This is the root package for the Openstrat project. The top of this package contains, 32 bit Int based Colours, the Multiple type class, a
 *  show and persistence library using RCON (Name may change), Rich Compact Object Notation, array based compound value collections of same length
 *   elements, an Either based errors framework and general utilities. */
package object ostrat
{
  import scala.reflect.ClassTag
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
  def ifSeq[A](b: Boolean, vTrue: => Seq[A]): Seq[A] = if (b) vTrue else Seq()
  def ifSeq1[A](b: Boolean, vTrue: => A): Seq[A] = if (b) Seq(vTrue) else Seq()
  def ifSome[A](b: Boolean, vTrue: => A): Option[A] = if (b) Some(vTrue) else None  
  def eqOf[A](leftValue: A, rightValues: A *): Boolean = rightValues.contains(leftValue)

  def readT[T](implicit ev: Persist[T]): T =
  { val artStr = ev.typeStr.prependIndefiniteArticle
    def loop(inp: EMon[T]): T = inp match
    { case Good(t) => t
      case _ =>
      {
        println
        loop(io.StdIn.readLine ("That was not a single "+ ev.typeStr + ". Please enter " + artStr).asType[T])
      }
    }
    loop(io.StdIn.readLine ("Please enter " + artStr).asType[T])
  }

  def readInt: Int = readT[Int]
  def readDouble: Double = readT[Double]
  import collection.immutable.ArraySeq, collection.mutable.ArrayBuffer
  type Arr[A] = ArraySeq[A]

  implicit class ArrExtension[A](thisArr: Arr[A])
  {
    def mapWith1[B, C](initC: C)(f: (A, C) => (B, C))(implicit ct: ClassTag[B]): Arr[B] =
    {
      val accB: Buff[B] = newBuff()
      var accC: C = initC
      thisArr.foreach { a =>
        val (newB, newC) = f(a, accC)
        accB += newB
        accC = newC
      }
      ArraySeq.unsafeWrapArray[B](accB.toArray)
    }

    def removeFirst(f: A => Boolean): Arr[A] = ???
    def ifAppendArr[B >: A](b: Boolean, newElems: => Arr[B]): Arr[B] = ife(b, thisArr ++ newElems, thisArr)
  }

  implicit class ArrayExtension[A](thisMutableArray: Array[A])
  {
    def toArr: Arr[A] = ArraySeq.unsafeWrapArray[A](thisMutableArray)
  }

  @inline def ArrWrap[A](mutArray: Array[A]): ArraySeq[A] = ArraySeq.unsafeWrapArray[A](mutArray)
  @inline def ArrWrapBuff[A](buff: ArrayBuffer[A])(implicit ct: ClassTag[A]): ArraySeq[A] = ArraySeq.unsafeWrapArray[A](buff.toArray)
  type Buff[A] = ArrayBuffer[A]
  @inline def newBuff[A](initialLength: Int = 5): ArrayBuffer[A] = new ArrayBuffer[A](initialLength)

  implicit class ArrayBufferExtensions[A](thisBuff: Buff[A])(implicit ct: ClassTag[A])
  {
    @inline def toArr: Arr[A] = ArrWrapBuff[A](thisBuff)
  }

  type ParseExpr = pParse.Expr
  type RefTag[A] = AnyRef with reflect.ClassTag[A]
  //type LeftRight[A] = Either[A, A]
  //type Trav[A] = Traversable[A]  
  type FStr = Function0[String]
  type FStrSeq = Seq[Function0[String]]  
  type EMonList[A] = EMon[List[A]]
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
  
  def excep(str: String): Nothing = throw new Exception(str)
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
  
  @inline def doubleFromTo(fromValue: Double, toValue: Double, step: Double): List[Double] =
  { var count = fromValue
    var acc: List[Double] = Nil
    while (count <= toValue)
    { acc ::= count
      count += step
    }
    acc.reverse
  }
  
  /** Extension methods for AnyT */
  implicit class AnyTImplicit[T](thisT: T)
  {
    def *(operand: Int): Multiple[T] = Multiple(thisT, operand) 
    
    def nextFromList(list: List[T]): T =
    { val i: Int = list.indexOf[T](thisT)
      ife(i >= list.length - 1, list(0), list(i + 1))
    }
  }  

  implicit class OptionRichClass[A](thisOption: Option[A])
  { def map2[B, C](ob: Option[B], f: (A, B) => C): Option[C] = thisOption.fold[Option[C]](None)(a => ob.fold[Option[C]](None)(b => Some(f(a, b))))
    def toEMon(errs: StrList): EMon[A] = thisOption match
    { case Some(a) => Good(a)
      case None => Bad(errs)
    }
    def toEMon1(fp: TextSpan, detail: String): EMon[A] = thisOption match
    { case Some(a) => Good(a)
      case None => bad1(fp, detail)
    }
  }

  implicit class CharRichClass(thisChar: Char)
  {
    def isHexDigit: Boolean = thisChar match
    { case d if d.isDigit => true
      case al if ((al <= 'E') && (al >= 'A')) => true
      case al if ((al <= 'e') && (al >= 'a')) => true
      case _ => false
    }
  }
   
  implicit class FunitRichImp(fu: () => Unit)
  { def +(operand: () => Unit): () => Unit = () => {fu() ; operand()} 
  }   
   
  implicit class Tuple2Implicit[A, B](thisTuple: Tuple2[A, B])
  { def bimap[C, D](f1: A => C, f2: B => D): Tuple2[C, D] = (f1(thisTuple._1), f2(thisTuple._2))
    def tupleFold[C](f: (A, B) => C): C = f(thisTuple._1, thisTuple._2)
  }
  
  import pImplicit._
  implicit def arrayDoubleToImplicit(arr: Array[Double]): ArrayDoubleImplicit = new ArrayDoubleImplicit(arr)
  implicit def arrayRefToImplict[A <: AnyRef](arr: Array[A]): ArrayImplicit[A] = new pImplicit.ArrayImplicit[A](arr)
  implicit def booleanToRichImp(b: Boolean): BooleanImplicit = new BooleanImplicit(b)
  implicit def intToImplicit(i: Int): IntImplicit = new IntImplicit(i)
  implicit def longToImplicit(i: Long): LongImplicit = new LongImplicit(i)
  implicit def doubleToImplicit(d: Double): DoubleImplicit = new DoubleImplicit(d)
  implicit def stringToImplicit(s: String): StringImplicit = new StringImplicit(s)
  implicit def listToImplicit[A](thisList: List[A]): ListImplicit[A] = new ListImplicit[A](thisList)
  implicit def seqToImplicit[A](thisSeq: Seq[A]): SeqImplicit[A] = new SeqImplicit(thisSeq)  
  implicit def persistTToStringerImplicit[A](thisVal: A)(implicit ev: Persist[A]): ShowerImplicit[A] = new ShowerImplicit[A](ev, thisVal) 
  implicit def showTToStringerImplicit[A](thisVal: A)(implicit ev: ShowOnly[A]): ShowerImplicit[A] = new ShowerImplicit[A](ev, thisVal) 
  implicit def traversableToImplicit[A](iter: Iterable[A]): IterableImplicit[A] = new IterableImplicit[A](iter)  
  implicit def stringTraverableToImplict(strIter: Iterable[String]): StringIterableImplicit = StringIterableImplicit(strIter)   
  implicit def stringArrayToStringTraversibleRichImp(strArray: Array[String]): StringIterableImplicit = StringIterableImplicit(strArray) 
}
