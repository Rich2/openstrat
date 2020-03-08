/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

/** The top level compositional unit of Syntax in CRON: Compact Readable Object Notation. A statement can be claused consisting of comma separated
  * clauses containing a single expression. An empty statement is a special case of the UnClausedStatement where the semicolon character is the
  * expression. */
sealed trait Statement extends TextSpan
{ def optSemi: EMon[SemicolonToken]
  def hasSemi: Boolean = optSemi.isGood
  def noSemi: Boolean = optSemi.isBad
  final def errGet[A](implicit ev: Persist[A]): EMon[A] = ???
  def expr: Expr
}

object Statement
{
  implicit class StatementListImplicit(statementList: List[Statement]) extends TextSpan
  { private def ifEmptyTextPosn: TextPosn = TextPosn("Empty Statement Seq", 0, 0)
    def startPosn = statementList.ifEmpty(ifEmptyTextPosn, statementList.head.startPosn)
    def endPosn = statementList.ifEmpty(ifEmptyTextPosn, statementList.last.endPosn)

    def errFun1[A1, B](f1: A1 => B)(implicit ev1: Persist[A1]): EMon[B] = statementList match
    { case Seq(h1) => h1.errGet[A1].map(f1)
      case s => bad1(s, s.length.toString -- "statements not 1")
    }

    /*def errFun2[A1, A2, B](f2: (A1, A2) => B)(implicit ev1: Persist[A1], ev2: Persist[A2]): EMon[B] = statementList match
    { case Seq(h1, h2) => for { g1 <- h1.errGet[A1](ev1); g2 <- h2.errGet[A2](ev2) } yield f2(g1, g2)
      case s => bad1(s, s.length.toString -- "statements not 2")
    }

    def errFun3[A1, A2, A3, B](f3: (A1, A2, A3) => B)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3]): EMon[B] =
      statementList match
    { case Seq(h1, h2, h3) => for { g1 <- h1.errGet[A1](ev1); g2 <- h2.errGet[A2](ev2); g3 <- h3.errGet[A3](ev3) } yield f3(g1, g2, g3)
      case s => bad1(s, s.length.toString -- "statements not 3")
    }*/

    /*def errFun4[A1, A2, A3, A4, B](f4: (A1, A2, A3, A4) => B)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4]):
       EMon[B] = statementList match
    {
      case Seq(h1, h2, h3, h4) => for { g1 <- h1.errGet[A1](ev1); g2 <- h2.errGet[A2](ev2); g3 <- h3.errGet[A3](ev3); g4 <-  h4.errGet[A4] }
        yield f4(g1, g2, g3, g4)
       case s => bad1(s, s.length.toString -- "statements not 4")
    }
*/
    def findType[A](implicit ev: Persist[A]): EMon[A] = ev.findUniqueFromStatements(statementList.toRefs)
    /** Find unique instance of type from RSON statement. The unique instance can be a plain value or setting. If no value or duplicate values found
     *  use elseValue. */
    def findTypeElse[A](elseValue: A)(implicit ev: Persist[A]): A = findType[A].getElse(elseValue)

    def findTypeIndex[A](index: Int)(implicit ev: Persist[A]): EMon[A] =
    { val list = ev.valueListFromStatements(statementList.toRefs)
      if (list.length > index) Good(list(index))
        else TextPosn.empty.bad("Element " + index.toString -- "of" -- ev.typeStr -- "not found")
    }

    def findInt: EMon[Int] = Show.intPersistImplicit.findUniqueFromStatements(statementList.toRefs)
    def findDouble: EMon[Double] = Show.doublePersistImplicit.findUniqueFromStatements(statementList.toRefs)
    def findBoolean: EMon[Boolean] = Show.BooleanPersistImplicit.findUniqueFromStatements(statementList.toRefs)
    def findLong: EMon[Long] = Show.longPersistImplicit.findUniqueFromStatements(statementList.toRefs)
    def findIntArray: EMon[Array[Int]] = Show.ArrayIntPersistImplicit.findUniqueFromStatements(statementList.toRefs)

    /** Find setting from RSON statement */
    def findSetting[A](settingStr: String)(implicit ev: Persist[A]): EMon[A] = ev.settingFromStatementList(statementList.toRefs, settingStr)
    def findSettElse[A](settingStr: String, elseValue: A)(implicit ev: Persist[A]): A = findSetting[A](settingStr).getElse(elseValue)
    def findIntSett(settingStr: String): EMon[Int] = Show.intPersistImplicit.settingFromStatementList(statementList.toRefs, settingStr)
    def findDoubleSett(settingStr: String): EMon[Double] = Show.doublePersistImplicit.settingFromStatementList(statementList.toRefs, settingStr)
    def findBooleanSett(settingStr: String): EMon[Boolean] = Show.BooleanPersistImplicit.settingFromStatementList(statementList.toRefs, settingStr)
  }

  implicit class RefsImplicit(statementRefs: Refs[Statement]) extends TextSpan
  { private def ifEmptyTextPosn: TextPosn = TextPosn("Empty Statement Seq", 0, 0)
    def startPosn = statementRefs.ifEmpty(ifEmptyTextPosn, statementRefs.head.startPosn)
    def endPosn = statementRefs.ifEmpty(ifEmptyTextPosn, statementRefs.last.endPosn)

    def findSetting[A](settingStr: String)(implicit ev: Persist[A]): EMon[A] = ev.settingFromStatementList(statementRefs, settingStr)

   def findSettElse[A](settingStr: String, elseValue: A)(implicit ev: Persist[A]): A = findSett[A](settingStr).getElse(elseValue)
    def findType[A](implicit ev: Persist[A]): EMon[A] = ev.findUniqueFromStatements(statementRefs)
    /** Find unique instance of type from RSON statement. The unique instance can be a plain value or setting. If no value or duplicate values found
     *  use elseValue. */
    def findTypeElse[A](elseValue: A)(implicit ev: Persist[A]): A = findType[A].getElse(elseValue)

    def findTypeIndex[A](index: Int)(implicit ev: Persist[A]): EMon[A] =
    { val list = ev.valueListFromStatements(statementRefs)
      if (list.length > index) Good(list(index))
      else TextPosn.empty.bad("Element " + index.toString -- "of" -- ev.typeStr -- "not found")
    }

    def findInt: EMon[Int] = Show.intPersistImplicit.findUniqueTFromStatements(statementRefs)
    def findDouble: EMon[Double] = Show.doublePersistImplicit.findUniqueTFromStatements(statementRefs)
    def findBoolean: EMon[Boolean] = Show.BooleanPersistImplicit.findUniqueTFromStatements(statementRefs)
    def findLong: EMon[Long] = Show.longPersistImplicit.findUniqueTFromStatements(statementRefs)
    def findIntArray: EMon[Array[Int]] = Show.ArrayIntPersistImplicit.findUniqueFromStatements(statementRefs)

    /** Find setting from RSON statement */
    def findSett[A](settingStr: String)(implicit ev: Persist[A]): EMon[A] = ev.settingFromStatementList(statementRefs, settingStr)

    def findIntSett(settingStr: String): EMon[Int] = Show.intPersistImplicit.settingFromStatementList(statementRefs, settingStr)
    def findDoubleSett(settingStr: String): EMon[Double] = Show.doublePersistImplicit.settingFromStatementList(statementRefs, settingStr)
    def findBooleanSett(settingStr: String): EMon[Boolean] = Show.BooleanPersistImplicit.settingFromStatementList(statementRefs, settingStr)

    def errFun1[A1, B](f1: A1 => B)(implicit ev1: Persist[A1]): EMon[B] = statementRefs match
    { case Refs1(h1) => h1.errGet[A1].map(f1)
      case s => bad1(s, s.length.toString -- "statements not 1")
    }

    /*def errFun2[A1, A2, B](f2: (A1, A2) => B)(implicit ev1: Persist[A1], ev2: Persist[A2]): EMon[B] = statementRefs match
    { case Refs2(h1, h2) => for { g1 <- h1.errGet[A1](ev1); g2 <- h2.errGet[A2](ev2) } yield f2(g1, g2)
      case s => bad1(s, s.length.toString -- "statements not 2")
    }

    def errFun3[A1, A2, A3, B](f3: (A1, A2, A3) => B)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3]): EMon[B] =
      statementRefs match
      { case Refs3(h1, h2, h3) => for { g1 <- h1.errGet[A1](ev1); g2 <- h2.errGet[A2](ev2); g3 <- h3.errGet[A3](ev3) } yield f3(g1, g2, g3)
      case s => bad1(s, s.length.toString -- "statements not 3")
      }*/

    /*def errFun4[A1, A2, A3, A4, B](f4: (A1, A2, A3, A4) => B)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4]):
    EMon[B] = statementRefs match
    {
      case Refs4(h1, h2, h3, h4) => for { g1 <- h1.errGet[A1](ev1); g2 <- h2.errGet[A2](ev2); g3 <- h3.errGet[A3](ev3); g4 <-  h4.errGet[A4] }
        yield f4(g1, g2, g3, g4)
      case s => bad1(s, s.length.toString -- "statements not 4")
    }*/
  }
}

/** This statement has 1 or more comma separated clauses. If there is only 1 Clause, it must be terminated by a comma, otherwise the trailing comma
 *  on the last Clauses is optional. */
case class ClausedStatement(clauses: Refs[Clause], optSemi: EMon[SemicolonToken]) extends Statement with TextSpanCompound
{ def expr: Expr = ??? //ClausesExpr(clauses.map(_.expr))
  def startMem: TextSpan = clauses.head
  def endMem: TextSpan = optSemi.fld[TextSpan](clauses.last, st => st)
  //override def errGet[A](implicit ev: Persist[A]): EMon[A] = ev.fromClauses(clauses)
}

/** An unclaused Statement has a single expression. */
sealed trait UnClausedStatement extends Statement
{ def expr: Expr
  def optSemi: EMon[SemicolonToken]
 // override def errGet[A](implicit ev: Persist[A]): EMon[A] = ev.fromExpr(expr)
}

/** An un-claused Statement that is not the empty statement. */
case class MonoStatement(expr: Expr, optSemi: EMon[SemicolonToken]) extends UnClausedStatement with TextSpanCompound
{
  def startMem: TextSpan = expr
  def endMem: TextSpan = optSemi.fld(expr, sc => sc)
}

/** The Semicolon of the Empty statement is the expression of this special case of the unclaused statement */
case class EmptyStatement(st: SemicolonToken) extends UnClausedStatement with TextSpanCompound
{ override def expr: Expr = st
  override def optSemi: EMon[SemicolonToken] = Good(st)
  override def startMem: TextSpan = st
  override def endMem: TextSpan = st
  def asError[A]: Bad[A] = st.startPosn.bad("Empty Statement")
}

object EmptyStatement
{ def apply(st: SemicolonToken): EmptyStatement = new EmptyStatement(st)
}