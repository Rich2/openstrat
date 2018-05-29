/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
/** This is just my Scala Utility package. Useful methods and classes that I want accessible at all times. I am publishing this,
 *   so as I can

import Aliases.{Aliases => c}
import scala.Left
import scala.Right
import scala.collection.Seq
import scala.language.experimental.macros
import scala.reflect.api.materializeTypeTag
import scala.reflect.api.materializeWeakTypeTag
methods and classes that I want accessible at all times. I am publishing this, so as I can
 *  use it in published code as is. My apologies that this in a very rough state, but it may be useful in understanding other packages / code
 *  that I choose to blog about.  
 */


package object rich
{
   /** This vital implicit class kills off the vile and insidious any2stringadd implicit from the Scala Compiler. I strongly recommend it for
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
   type RefTag[A] = AnyRef with reflect.ClassTag[A]// with AnyRef
   type LeftRight[A] = Either[A, A]
   type Trav[A] = Traversable[A]
   type Funit = Function0[Unit]
   type FStr = Function0[String]
   type FStrSeq = Seq[Function0[String]]
   type Tokens = Seq[Token]
   type EMon[B] = Either[Seq[ParseErr], B]
   type EMonSeq[B] = Either[Seq[ParseErr], Seq[B]]
   type TokensMon = EMon[Tokens]
   type Good[B] = Right[Seq[ParseErr], B]
   type Bad[B] = Left[Seq[ParseErr], B]
   type ProdI2 = Product2[Int, Int]
   type ProdD2 = Product2[Double, Double]
   type ProdD3 = Product3[Double, Double, Double]
   type ProdD4 = Product4[Double, Double, Double, Double]
   
   def excep(str: String): Nothing = throw new Exception(str)  
   def bad1[B](fp: FilePosn, detail: String): Bad[B] = Left[Seq[ParseErr], B](Seq(ParseErr(fp, detail)))
   def bad1[B](fs: FileSpan, detail: String): Bad[B] = Left[Seq[ParseErr], B](Seq(ParseErr(fs.startPosn, detail)))
   def eTry[A](res: => A): EMon[A] =
      try Good[A](res) catch { case scala.util.control.NonFatal(e) => bad1(FilePosn(1, 1, "Java Exception"), e.getMessage) }
   def commaedObjs(objs: Any*) = objs.map(_.toString).commaFold
   
   implicit def AnyToImplicit[A](value: A): AnyImplicit[A] = new AnyImplicit[A](value)   
   implicit def booleanToRichImp(b: Boolean) = new BooleanImplicit(b)
   implicit def intToRichImplicit(i: Int) = new IntImplicit(i)
   implicit def doubleToRichImp(d: Double) = new DoubleImplicit(d)
   implicit def stringToRichImp(s: String) = new StringImplicit(s)
   implicit def seqToRichImp[A](thisSeq: Seq[A]) = new SeqImplicit(thisSeq)
   implicit class PeristImplicit[A](thisVal: A)(implicit ev: Persist[A])
   {
      def persist: String = ev.persist(thisVal)
      def persistComma: String = "unimplemented"
   }

   implicit def traversableToRichImp[A](trav: Traversable[A]) = TraversableRichImp[A](trav)

   implicit def arrayToTraversableRichImp[A](arr: Array[A]) = TraversableRichImp[A](arr)

   implicit def stringTraverableToRichImp(strTrav: Traversable[String]): StringTraversable = StringTraversable(strTrav)   
   implicit def stringArrayToStringTraversibleRichImp(strArray: Array[String]): StringTraversable = StringTraversable(strArray) 
   implicit def EMonToImplicit[A](eMon: EMon[A]): EMonImplicit[A] = new EMonImplicit[A](eMon)
   
   implicit class OptionRichClass[A](thisOption: Option[A])
   { 
      def map2[B, C](ob: Option[B], f: (A, B) => C): Option[C] = 
         thisOption.fold[Option[C]](None)(a => ob.fold[Option[C]](None)(b => Some(f(a, b))))
      def toEMon(errs: Seq[ParseErr]): EMon[A] = thisOption match
      {
         case Some(a) => Good(a)
         case None => Bad(errs)
      }
      def toEMon1(fp: FileSpan, detail: String): EMon[A] = thisOption match
      {
         case Some(a) => Good(a)
         case None => bad1(fp, detail)
      }
   }
   implicit class ImplicitLeftRight[A](thisLR: LeftRight[A])
   {
      /** A LeftRight map. A LeftRight[A] is an Either[A, A]. */ 
      def lrMap[B](f: A => B): LeftRight[B] = thisLR match
      {
         case Left(a) => Left(f(a))
         case Right(a) => Right(f(a))
      }
      def lrFold: A = thisLR match
      {
         case Left(a) => a
         case Right(a) => a
      }
   }
   
   
   implicit class CharRichClass(thisChar: Char)
   {
      def isHexDigit: Boolean = thisChar match
      {
         case d if d.isDigit => true
         case al if ((al <= 'E') && (al >= 'A')) => true
         case al if ((al <= 'e') && (al >= 'a')) => true
         case _ => false
      }
   }
   
   implicit class EitherRichClass[A, B](thisEither: Either[A, B])
   {
      def map[C](f: B => C): Either[A, C] = thisEither match
      {
         case Left(l) => Left[A, C](l)
         case Right(r) => Right[A, C](f(r))
      }
      def flatMap[C](f: B => Either[A, C]): Either[A, C] = thisEither match
      {
         case Left(l) => Left[A, C](l)
         case Right(r) => (f(r))
      }      
   }
   
   implicit class ListRichImplicit[A](thisList: List[A])
   {
      def removeFirst(f: A => Boolean): List[A] =
      {
         def loop(rem: List[A], acc: List[A]): List[A] = rem match
         {
            case ::(h, tail) if f(h) => acc.reverse ::: tail
            case ::(h, tail) => loop(tail, h :: acc)
            case Nil => acc.reverse
         }
         loop(thisList, Nil)
      }
   }
   
   implicit class EitherPairRichImp[A, B, C](thisEither: Either[A, (B, C)])
   {
      def flat2Map[D](f: (B, C) => Either[A, D]): Either[A, D] = thisEither match
      {
         case Left(l) => Left[A, D](l)
         case Right(r) =>
            {
               val (r1, r2) = r
               f(r1, r2)
            }
      }
   }
     
   implicit class FunitRichImp(fu: Funit)
   {
      def +(operand: Funit) : Funit = () => {fu() ; operand()} 
   } 
   
   object OpEqualsRef
   {
	   def apply[A](leftOp: Option[A], rightOp: AnyRef): Boolean = leftOp.fold(false)(_ == rightOp)
   }   
   implicit class OptionTraversable[A](trav: Traversable[Option[A]])
   {
      /** Folds across a Traversable of options returning None if any of the members are None. This the Sequence operation
       *  on the Option monad */
      def optionFold[B](startValue: B)(f: (B, A) => B): Option[B] =
      {
         def loop(rem: Traversable[Option[A]], acc: B): Option[B] = rem.ifEmpty(Some(acc), rem.head match 
               {
            case None => None
            case Some(a) => loop(rem.tail, f(acc, a))
               })
         loop(trav, startValue)
      }
   }
   
   implicit class Tuple2Implicit[A, B](thisTuple: Tuple2[A, B])
   {
      def bimap[C, D](f1: A => C, f2: B => D): Tuple2[C, D] = (f1(thisTuple._1), f2(thisTuple._2))
      def tupleFold[C](f: (A, B) => C): C = f(thisTuple._1, thisTuple._2)
   }
   
//   implicit class ImpSocketClass(socket: java.net.Socket)
//   {
//      import java.io._
//      def utfOut(str: String): Unit = 
//      {
//         var out: java.io.OutputStream = socket.getOutputStream
//         var out2: OutputStreamWriter = new OutputStreamWriter(out, "UTF-8")
//         out2.write(str)
//         out2.close
//         out.close
//      }
//   }   
   
   implicit class ArrayRichImp[A](arr: Array[A])
   {     
      def updateFrom(startElem: Int, newElems: A *): Unit = newElems.zipWithIndex.foreach(p => arr.update(startElem + p._2, p._1))
   } 
   
//   implicit class PairSeqSeqImplicit[A, B](thisSeq: Seq[(Seq[A], Seq[B])])
//   {
//      /** This takes a Seq of pairs of Sequences and appends the pairs while maintaining the pair of Sequences. Often type A and B will be 
//       *  the same, however the function maintains the ordering. */
//      def pairSeqSeqFlatten: (Seq[A], Seq[B]) = thisSeq.foldLeft[(Seq[A], Seq[B])]((Seq(), Seq()))((acc, el) => (acc._1 ++ el._1, acc._2 ++ el._2))
//   } 
   import language.experimental.macros
   import reflect.macros.blackbox.Context
   
   def macroPosn(c: Context)(posn: c.Position): String =
   {
      val fileName = posn.source.toString
      val lineNum: String = posn.line.toString
      val column: String = posn.column.toString
      fileName.dotAppend(lineNum, column)
   }
   
   def deb(str: String): Unit = macro debImpl
   
   def debImpl(c: Context)(str: c.Expr[String]): c.Expr[Unit] = 
   {
      import c.universe._     
      val pos: Position  = c.macroApplication.pos      
      val s1 = macroPosn(c)(pos)
      val Predef_println = typeOf[Predef.type].decl(TermName("println"))
      c.Expr(q"""$Predef_println($s1 + ": " + $str)""")
   }
   
   def debv(s: Any): Unit = macro debvImpl  
   
   def debvImpl(c: Context)(s: c.Expr[Any]): c.Expr[Unit] = {
      import c.universe._      
      val name = show(s.tree).reverse.takeWhile(_ != '.').reverse      
      val pos: Position  = c.macroApplication.pos      
      val s1: String = macroPosn(c)(pos)
      val Predef_println: Symbol = typeOf[Predef.type].decl(TermName("println"))
      c.Expr(q"""$Predef_println($s1 -- $name + ": " + $s)""")
   }
   
 
}