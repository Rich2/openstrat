/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, annotation.unchecked.uncheckedVariance

/** Produces an object in memory or an error sequence from text. */
trait UnShow[+T]
{ def typeStr: String
  def fromExpr(expr: Expr): EMon[T]
  
  /** Trys to build an object of type T from the statement. Not sure if this is useful. */
  final def fromStatement(st: Statement): EMon[T] = fromExpr(st.expr)
  
  def fromClauses1[A1, B](f: A1 => B, clauses: Arr[Clause])(implicit ev1: Persist[A1]): EMon[B] = clauses match
  { case Arr1(c1) => ev1.fromExpr(c1.expr).map(f)
    case _ => excep("from clauses exception")
  }
  
  /*def fromClauses2[A1, A2, B](f: (A1, A2) => B, clauses: Refs[Clause])(implicit ev1: Persist[A1], ev2: Persist[A2]): EMon[B] = clauses match
  { case Refs2(c1, c2) => for { g1 <- ev1.fromExpr(c1.expr); g2 <- ev2.fromExpr(c2.expr) } yield f(g1, g2)
    case _ => excep("from clauses exception")
  }*/
   
  /*def fromClauses3[A1, A2, A3, B](f: (A1, A2, A3) => B, clauses: Refs[Clause])(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3]): EMon[B]
    = clauses match { case Refs3(c1, c2, c3) => for
    { g1 <- ev1.fromExpr(c1.expr); g2 <- ev2.fromExpr(c2.expr); g3 <- ev3.fromExpr(c3.expr) } yield f(g1, g2, g3) }

  
  def fromClauses4[A1, A2, A3, A4, B](f: (A1, A2, A3, A4) => B, clauses: Refs[Clause])(implicit ev1: Persist[A1], ev2: Persist[A2],
      ev3: Persist[A3], ev4: Persist[A4]): EMon[B] = clauses match
  { case Refs4(c1, c2, c3, c4) => for { g1 <- ev1.fromExpr(c1.expr); g2 <- ev2.fromExpr(c2.expr);
      g3 <- ev3.fromExpr(c3.expr); g4 <- ev4.fromExpr(c4.expr)} yield f(g1, g2, g3, g4)
  }

  def fromClauses5[A1, A2, A3, A4, A5, B](f: (A1, A2, A3, A4, A5) => B, clauses: Refs[Clause])(implicit ev1: Persist[A1], ev2: Persist[A2],
    ev3: Persist[A3], ev4: Persist[A4], ev5: Persist[A5]): EMon[B] = clauses match
  {
    case Refs5(c1, c2, c3, c4, c5) =>
      for { g1 <- ev1.fromExpr(c1.expr); g2 <- ev2.fromExpr(c2.expr); g3 <- ev3.fromExpr(c3.expr); g4 <- ev4.fromExpr(c4.expr);
            g5 <- ev5.fromExpr(c5.expr)
          } yield f(g1, g2, g3, g4, g5)
  }*/

  /*def fromClauses6[A1, A2, A3, A4, A5, A6, B](f: (A1, A2, A3, A4, A5, A6) => B, clauses: Refs[Clause])(implicit
  ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4], ev5: Persist[A5], ev6: Persist[A6]): EMon[B] = clauses match
  {
    case Refs6(c1, c2, c3, c4, c5, c6) =>
      for { g1 <- ev1.fromExpr(c1.expr); g2 <- ev2.fromExpr(c2.expr); g3 <- ev3.fromExpr(c3.expr); g4 <- ev4.fromExpr(c4.expr);
            g5 <- ev5.fromExpr(c5.expr); g6 <- ev6.fromExpr(c6.expr)
          } yield f(g1, g2, g3, g4, g5, g6)
  }*/

  /** Produces an ArrImut of the UnShow type from Statements (Refs[Statement]. */
  def valuesFromStatements[ArrT <: ArrBase[T] @uncheckedVariance](sts: Statements)(implicit arrBuild: ArrBuild[T, ArrT] @uncheckedVariance): ArrT =
    sts.mapCollectGoods(fromStatement)(arrBuild)

  /** Produces a List of the UnShow type from List of Statements */
  def valueListFromStatements(l: Statements): List[T] = l.map(fromStatement(_)).collectList{ case Good(value) => value }

  /** Finds value of UnShow type, returns error if more than one match. */
  def findUniqueFromStatements(sts: Statements): EMon[T] = valueListFromStatements(sts) match
  { case Nil => TextPosn.emptyError("No values of type found")
    case h :: Nil => Good(h)
    case s3 => sts.startPosn.bad(s3.length.toString -- "values of" -- typeStr -- "found.")
  }

  /** Finds value of UnShow type, returns error if more than one match. */
  def findUniqueTFromStatements[ArrT <: ArrBase[T] @uncheckedVariance](sts: Statements)(implicit arrBuild: ArrBuild[T, ArrT] @uncheckedVariance): EMon[T] =
    valuesFromStatements(sts) match
  { case s if s.elemsLen == 0 => TextPosn.emptyError("No values of type found")
    case s if s.elemsLen == 1 => Good(s.head)
    case s3 => sts.startPosn.bad(s3.elemsLen.toString -- "values of" -- typeStr -- "found.")
  }
  
  def settingFromStatement(settingStr: String, st: Statement): EMon[T] = st match
  { case MonoStatement(AsignExpr(IdentifierLwToken(_, sym), _, rightExpr), _) if sym == settingStr => fromExpr(rightExpr)
    case _ => st.startPosn.bad(typeStr -- "not found.")
  }
  
  def settingFromStatementList(sts: Arr[Statement], settingStr: String): EMon[T] = sts match
  { case Arr0() => TextPosn.emptyError("No Statements")
    case Arr1(e1) => settingFromStatement(settingStr, e1)
    case s2 => sts.map(settingFromStatement(settingStr, _)).collect{ case g @ Good(_) => g } match
    { case Arr1(t) => t
      case Arr0() => sts.startPosn.bad(settingStr -- typeStr -- "Setting not found.")
      case s3 => sts.startPosn.bad(s3.elemsLen.toString -- "settings of" -- settingStr -- "of" -- typeStr -- "not found.")
    }
  }
}