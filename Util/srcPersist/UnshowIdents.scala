/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

class UnshowIdents[A](val typeStr: String, val pairs: List[(String, A)]) extends Unshow[A]
{
  override def fromExpr(expr: Expr): EMon[A] = expr match {
    case IdentifierToken(str) => pairs.find(pair => pair._1 == str).map(_._2).toEMon
    case _ => bad1(expr, typeStr -- "not found.")
  }
}

object UnshowIdents
{
  def apply[A](typeStr: String, pairs: (String, A)*): UnshowIdents[A] = new UnshowIdents[A](typeStr, pairs.toList)
  //def apply[A](typeStr: String, pairsArr: ArrPairStr[A]): UnshowIdents[A] = new UnshowIdents[A](typeStr, pairsArr)
}