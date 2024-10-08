/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, reflect.ClassTag

/** [[Unshow]] type class instances from one of sequence of identity tokens."  */
class UnshowIdents[A](val typeStr: String, val pairs: ArrPairStr[A]) extends Unshow[A]
{
  override def fromExprOld(expr: Expr): EMonOld[A] = expr match {
    case IdentifierToken(str) => pairs.a1FindA2(str).toEMon
    case _ => bad1(expr, typeStr -- "not found.")
  }

  override def fromExpr(expr: Expr): ExcMon[A] = expr match
  { case IdentifierToken(str) => pairs.a1FindA2(str).toErrBi
    case _ => expr.failExc(typeStr -- "not found.")
  }
}

object UnshowIdents
{
  def apply[A](typeStr: String, pairs: (String, A)*)(implicit ct: ClassTag[A]): UnshowIdents[A] = new UnshowIdents[A](typeStr, pairs.toPairArr)
  def apply[A](typeStr: String, pairsArr: ArrPairStr[A]): UnshowIdents[A] = new UnshowIdents[A](typeStr, pairsArr)
}