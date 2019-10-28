/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

trait UnShow[+T]
{ def typeStr: String
  def fromExpr(expr: Expr): EMon[T]  
  def fromClauses(clauses: Arr[Clause]): EMon[T]
  
  /** Trys to build an object of type T from the statement. Not sure if this is useful. */
  final def fromStatement(st: Statement): EMon[T] = fromExpr(st.expr)
  
  def fromStatements(sts: Arr[Statement]): EMon[T]
  
  def fromClauses1[A1, B](f: A1 => B, clauses: Arr[Clause])(implicit ev1: Persist[A1]): EMon[B] = clauses match
  { case Seq(c1, c2) => ev1.fromExpr(c1.expr).map(f)
    case _ => excep("from clauses exception")
  }
  
  def fromClauses2[A1, A2, B](f: (A1, A2) => B, clauses: Arr[Clause])(implicit ev1: Persist[A1], ev2: Persist[A2]): EMon[B] = clauses match
  { case Seq(c1, c2) => for { g1 <- ev1.fromExpr(c1.expr); g2 <- ev2.fromExpr(c2.expr) } yield f(g1, g2)
    case _ => excep("from clauses exception")
  }
   
  def fromClauses3[A1, A2, A3, B](f: (A1, A2, A3) => B, clauses: Arr[Clause])(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3]): EMon[B]
    = clauses match { case Seq(c1, c2, c3) => for
    { g1 <- ev1.fromExpr(c1.expr); g2 <- ev2.fromExpr(c2.expr); g3 <- ev3.fromExpr(c3.expr) } yield f(g1, g2, g3) }

  
  def fromClauses4[A1, A2, A3, A4, B](f: (A1, A2, A3, A4) => B, clauses: Arr[Clause])(implicit ev1: Persist[A1], ev2: Persist[A2],
      ev3: Persist[A3], ev4: Persist[A4]): EMon[B] = clauses match
  { case Seq(c1, c2, c3, c4) => for { g1 <- ev1.fromExpr(c1.expr); g2 <- ev2.fromExpr(c2.expr);
      g3 <- ev3.fromExpr(c3.expr); g4 <- ev4.fromExpr(c4.expr)} yield f(g1, g2, g3, g4)
  }

  def fromClauses5[A1, A2, A3, A4, A5, B](f: (A1, A2, A3, A4, A5) => B, clauses: Arr[Clause])(implicit ev1: Persist[A1], ev2: Persist[A2],
    ev3: Persist[A3], ev4: Persist[A4], ev5: Persist[A5]): EMon[B] = clauses match
  {
    case Seq(c1, c2, c3, c4, c5) =>
      for { g1 <- ev1.fromExpr(c1.expr); g2 <- ev2.fromExpr(c2.expr); g3 <- ev3.fromExpr(c3.expr); g4 <- ev4.fromExpr(c4.expr);
            g5 <- ev5.fromExpr(c5.expr)
          } yield f(g1, g2, g3, g4, g5)
  }

  def fromClauses6[A1, A2, A3, A4, A5, A6, B](f: (A1, A2, A3, A4, A5, A6) => B, clauses: Arr[Clause])(implicit
  ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4], ev5: Persist[A5], ev6: Persist[A6]): EMon[B] = clauses match
  {
    case Seq(c1, c2, c3, c4, c5, c6) =>
      for { g1 <- ev1.fromExpr(c1.expr); g2 <- ev2.fromExpr(c2.expr); g3 <- ev3.fromExpr(c3.expr); g4 <- ev4.fromExpr(c4.expr);
            g5 <- ev5.fromExpr(c5.expr); g6 <- ev6.fromExpr(c6.expr)
          } yield f(g1, g2, g3, g4, g5, g6)
  }
  
  def listFromStatementList(l: List[Statement]): List[T] = l.map(fromStatement(_)).collect{ case Good(value) => value }
  
  def findFromStatementList(l: List[Statement]): EMon[T] = listFromStatementList(l) match
  { case Nil => TextPosn.emptyError("No values of type found")
    case h :: Nil => Good(h)
    case s3 => bad1(l.startPosn, s3.length.toString -- "values of" -- typeStr -- "found.") 
  }
  
  def settingFromStatement(settingStr: String, st: Statement): EMon[T] = st match
  {
    case MonoStatement(AsignExpr(_, AlphaToken(_, sym), rightExpr), _) if sym == settingStr => fromExpr(rightExpr)
    case _ => bad1(st.startPosn, typeStr -- "not found.")
  }
  
  def settingFromStatementList(list: List[Statement], settingStr: String): EMon[T] = list match
  { case Nil => TextPosn.emptyError("No Statements")
    case List(e1) => settingFromStatement(settingStr, e1)
    case s2 => list.map(settingFromStatement(settingStr, _)).collect{ case g: Good[T] => g } match
    { case Seq(t) => t
      case Nil => bad1(list.startPosn, settingStr -- typeStr -- "Setting not found.")
      case s3 => bad1(list.startPosn, s3.length.toString -- "settings of" -- settingStr -- "of" -- typeStr -- "not found.")
    }
  }
}