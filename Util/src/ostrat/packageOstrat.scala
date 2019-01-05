/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
/** This is the root package for the Openstrat project. The top of this package contains, 32 bit Int based Colours, the Multiple type class, a
 *  show and persistence library using RCON (Name may change), Rich Compact Object Notation, array based compound value collections of same length
 *   elements, an Either based errors framework and general utilities. */
package object ostrat
{ /** This vital implicit class kills off the vile and insidious any2stringadd implicit from the Scala Compiler. I strongly recommend it for
   *  everyone's utility file. */
  implicit class any2stringadd[A](a: A) {}
  val Tan30 = 0.577350269f;
  val Cos30 = 0.866025404f;
  val Cos60 = 0.5
  val Sin30 = 0.5
  val Sin60 = 0.866025404f;
  val Pi2 = math.Pi * 2
  val PiH = math.Pi / 2
  def prints(objs: Any*): Unit = println(objs.map(_.toString).commaFold)
  @inline def ife[A](b: Boolean, vTrue: => A, vFalse: => A): A = if (b) vTrue else vFalse
  def ifSeq[A](b: Boolean, vTrue: => Seq[A]): Seq[A] = if (b) vTrue else Seq()
  def ifSeq1[A](b: Boolean, vTrue: => A): Seq[A] = if (b) Seq(vTrue) else Seq()
  def ifSome[A](b: Boolean, vTrue: => A): Option[A] = if (b) Some(vTrue) else None  
  
  type ParseExpr = pParse.Expr
  type RefTag[A] = AnyRef with reflect.ClassTag[A]
  //type LeftRight[A] = Either[A, A]
  type Trav[A] = Traversable[A]  
  type FStr = Function0[String]
  type FStrSeq = Seq[Function0[String]]  
  type EMonList[A] = EMon[List[A]]
  type StrList = List[String]
  
  /** Product2[Int, Int] with Stringer. These are used in IntProduct2s Array[Double] based collections. */
  type ProdI2 = Product2[Int, Int]
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
  /** Not sure about this method. */
  def parseErr(fp: TextPosn, detail: String): String = fp.toString + detail
  def bad1[B](fp: TextPosn, detail: String): Bad[B] = Bad[B](parseErr(fp, detail) :: Nil)
  def bad1[B](fs: TextSpan, detail: String): Bad[B] = Bad[B](parseErr(fs.startPosn, detail) :: Nil)
  def eTry[A](res: => A): EMon[A] =
    try Good[A](res) catch { case scala.util.control.NonFatal(e) => bad1(FilePosn("Java Exception", 1, 1), e.getMessage) }
  def commaedInts(iSeq: Int*) = iSeq.map(_.toString).commaFold

  val two32: Long = 4294967296l
  def twoIntsToDouble(i1: Int, i2: Int): Double = { val lg  = (i1.toLong << 32) | (i2 & 0xFFFFFFFFL); java.lang.Double.longBitsToDouble(lg) }
    
  def nullRef[A <: AnyRef]: OptRef[A] = new OptRef[A](null.asInstanceOf[A])
  
  @inline def doubleFromTo(fromValue: Double, toValue: Double, step: Double): List[Double] =
  { var count = fromValue
    var acc: List[Double] = Nil
    while (count <= toValue)
    { acc ::= count
      count += step
    }
    acc.reverse
  }
  
  implicit class AnyTImplicit[T](thisT: T)
  {
    def nextFromList(list: List[T]): T =
    {
      val i: Int = list.indexOf[T](thisT)
      if (i >= list.length - 1) list(0) else list(i + 1)
    }
  }
  
  /** Extension methods for Any */
  implicit class AnyRefImplicit[T <: Any](thisT: T)
  {
    def *(operand: Int): Multiple[T] = Multiple(thisT, operand) 
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
  implicit def AnyAToStringerImplicit[A](thisVal: A)(implicit ev: Persist[A]): StringerImplicit[A] = new StringerImplicit[A](ev, thisVal)
  implicit def seqToPersistDirect[A](thisSeq: Seq[A])(implicit ev: Persist[A]): PersistSeqDirect[A] = new PersistSeqDirect[A](thisSeq, ev)
  implicit def traversableToImplicit[A](trav: Traversable[A]): TraversableImplicit[A] = new TraversableImplicit[A](trav)  
  implicit def stringTraverableToImplict(strTrav: Traversable[String]): StringTraversableImplicit = StringTraversableImplicit(strTrav)   
  implicit def stringArrayToStringTraversibleRichImp(strArray: Array[String]): StringTraversableImplicit = StringTraversableImplicit(strArray)  
}
