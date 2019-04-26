/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

trait UnShow[T]
{
  def typeStr: String
  
  def fromExpr(expr: Expr): EMon[T]
  
  def fromClauses(clauses: Seq[Clause]): EMon[T]
  def fromClauses2[A1, A2, B](f: (A1, A2) => B, clauses: Seq[Clause])(implicit ev1: Persist[A1], ev2: Persist[A2]): EMon[B] = clauses match
  { case Seq(c1, c2) => ev1.fromExpr(c1.expr).map2(ev2.fromExpr(c2.expr), f)
    case _ => excep("from clauses exception")
  }
   
  def fromClauses3[A1, A2, A3, B](f: (A1, A2, A3) => B, clauses: Seq[Clause])(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3]): EMon[B] =
    clauses match
  { case Seq(c1, c2, c3) => ev1.fromExpr(c1.expr).map3(ev2.fromExpr(c2.expr), ev3.fromExpr(c3.expr), f)
  }
  
  def fromClauses4[A1, A2, A3, A4, B](f: (A1, A2, A3, A4) => B, clauses: Seq[Clause])(implicit ev1: Persist[A1], ev2: Persist[A2],
      ev3: Persist[A3], ev4: Persist[A4]): EMon[B] = clauses match
  { case Seq(c1, c2, c3, c4) => ev1.fromExpr(c1.expr).map4(ev2.fromExpr(c2.expr), ev3.fromExpr(c3.expr), ev4.fromExpr(c4.expr), f)
  }
  
  /** Trys to build an object of type T from the statement */
  def fromStatement(st: Statement): EMon[T]
  def listFromStatementList(l: List[Statement]): List[T] = l.map(fromStatement(_)).collect{ case Good(value) => value }
  
  def findFromStatementList(l: List[Statement]): EMon[T] = listFromStatementList(l) match
  {
    case Nil => FilePosn.emptyError("No values of type found")
    case h :: Nil => Good(h)
    case s3 => bad1(l.startPosn, s3.length.toString -- "values of" -- typeStr -- "found.") 
  }
  
  def settingFromStatement(settingSym: Symbol, st: Statement): EMon[T] = st match
  {
    case MonoStatement(AsignExpr(_, AlphaToken(_, sym), rightExpr), _) if sym == settingSym => fromExpr(rightExpr)
    case _ => bad1(st.startPosn, typeStr -- "not found.")
  }
  
  def settingFromStatementList(list: List[Statement], settingSym: Symbol): EMon[T] = list match
  { case Seq() => FilePosn.emptyError("No Statements")
    case Seq(e1) => settingFromStatement(settingSym, e1)
    case s2 => list.map(settingFromStatement(settingSym, _)).collect{ case g: Good[T] => g } match
    { case Seq(t) => t
      case Nil => bad1(list.startPosn, typeStr -- "not found.")
      case s3 => bad1(list.startPosn, s3.length.toString -- "values of" -- typeStr -- "found.")
    }
  }
}