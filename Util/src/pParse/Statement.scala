/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** The top level compositional unit of Syntax in CRON: Compact Readable Object Notation. A statement can be claused consisting of comma separated
  * clauses containing a single expression. An empty statement is a special case of the UnClausedStatement where the semicolon character is the
  * expression. */
sealed trait Statement extends TextSpan
{
  /** The expression value of this Statement. */
  def expr: Expr

  /** The opt semicolon token. */
  def optSemi: OptRef[SemicolonToken]

  /** The statement has semicolon as end */
  def hasSemi: Boolean = optSemi.nonEmpty

  /** The statement has no semicolon at end. */
  def noSemi: Boolean = optSemi.empty

  /** Not sure what this is meant to be doing, or whether it can be removed. */
  final def errGet[A](implicit ev: Persist[A]): EMon[A] = ???

  /** Returns the right expression if this Statement is a setting of the given name. */
  def settingExpr(settingName: String): EMon[AssignMemExpr] = this match {
    case NonEmptyStatement(AsignExpr(IdentLowerToken(_, sym), _, rightExpr), _) if sym == settingName => Good(rightExpr)
    case _ => startPosn.bad(settingName -- "not found.")
  }
}

/** Companion object for the [[Statement]] trait, contains implicit extension class for List[Statement]. */
object Statement
{
  /** Extension class for Arr[Statement]. */
  implicit class arrImplicit(statements: Arr[Statement]) extends TextSpan
  { private def ifEmptyTextPosn: TextPosn = TextPosn("Empty Statement Seq", 0, 0)
    def startPosn = statements.headFold(ifEmptyTextPosn)(_.startPosn)
    def endPosn = statements.lastFold(ifEmptyTextPosn)(_.endPosn)

    /** Finds a setting [Expr] from this Arr[Statement] extension method. */
    def findSettingExpr(settingStr: String): EMon[AssignMemExpr] = statements match
    { case Arr0() => TextPosn.emptyError("No Statements")
      case Arr1(st1) => st1.settingExpr(settingStr)
      case sts => sts.map(st => st.settingExpr(settingStr)).collect{ case g @ Good(_) => g } match
      { case Arr1(t) => t
        case Arr0() => sts.startPosn.bad(settingStr -- "Setting not found.")
        case s3 => sts.startPosn.bad(s3.dataLength.toString -- "settings of" -- settingStr -- "not found.")
      }
    }

    /** Find Identifier setting of type T from this Arr[Statement]. Extension method. */
    def findSetting[T](settingStr: String)(implicit ev: Persist[T]): EMon[T] = ev.settingFromStatements(statements, settingStr)

    /** Find Setting of key type KT type T from this Arr[Statement]. Extension method. */
    def findKeySetting[KT, VT](key: KT)(implicit evST: Unshow[KT], ev: Persist[VT]): EMon[VT] = ev.keySettingFromStatements(statements, key)

    /** Searches for the setting of the correct type. If not found it searches for a unique setting / value of the correct type. */
    def findSettingOrUniqueT[T](settingStr: String)(implicit ev: Persist[T]): EMon[T] = findSetting[T](settingStr).goodOrOther(findUniqueT)

    /** Find idnetifier setting of value type T from this Arr[Statement] or return the default value parameter. Extension method */
    def findSettingElse[A](settingStr: String, elseValue: A)(implicit ev: Persist[A]): A = findSetting[A](settingStr).getElse(elseValue)

    /** Find Statement of type T, if its unique from this Arr[Statement] and return value. */
    def findUniqueT[A](implicit ev: Unshow[A]): EMon[A] = statements.mapUniqueGood(ev.fromStatement(_))//  ev.findUniqueFromStatements(statements)

    /** Find unique instance of type from RSON statement. The unique instance can be a plain value or setting. If no value or duplicate values found
     *  use elseValue. */
    def findTypeElse[A](elseValue: A)(implicit ev: Persist[A]): A = findUniqueT[A].getElse(elseValue)

    /** Extension method tries to get value of specified type from the statement at the specified index of this [[Arr]][Statement]. */
    def typeAtIndex[A](index: Int)(implicit ev: Unshow[A]): EMon[A] =
      ife(statements.length > index, ev.fromStatement(statements(index)), badNone("No statement at given index."))

    /** Extension methods tries to get an [[Int]] value from the statement at the specified index of this [[Arr]][Statement]. */
    def intAtIndex(index: Int): EMon[Int] =
      ife(statements.length > index, Unshow.intEv.fromStatement(statements(index)), badNone("No statement at given index."))

    /** Extension methods tries to get a natural non negative [[Int]] value from the statement at the specified index of this [[Arr]][Statement]. */
    def natAtIndex(index: Int): EMon[Int] =
      ife(statements.length > index, Unshow.natEv.fromStatement(statements(index)), badNone("No statement at given index."))

    /** Extension methods tries to get a [[Double]] value from the statement at the specified index of this [[Arr]][Statement]. */
    def dblAtIndex(index: Int): EMon[Double] =
      ife(statements.length > index, Unshow.doubleEv.fromStatement(statements(index)), badNone("No statement at given index."))

    /** Extension methods tries to get a positive, non negative [[Double]] value from the statement at the specified index of this [[Arr]][Statement]. */
    def posDblAtIndex(index: Int): EMon[Double] =
      ife(statements.length > index, Unshow.posDoubleEv.fromStatement(statements(index)), badNone("No statement at given index."))

    /** Extension methods tries to get an [[Boolean]] value from the statement at the specified index of this [[Arr]][Statement]. */
    def boolAtIndex(index: Int): EMon[Boolean] =
      ife(statements.length > index, Unshow.booleanEv.fromStatement(statements(index)), badNone("No statement at given index."))

    /** Extension methods tries to get an [[Long]] value from the statement at the specified index of this [[Arr]][Statement]. */
    def longAtIndex(index: Int): EMon[Long] =
      ife(statements.length > index, Unshow.longEv.fromStatement(statements(index)), badNone("No statement at given index."))

    /** Find the sole Array[Int] expression from this Arr[Statement] extension method. Returns bad if absent or multiple [[Statement]]s resolve to
     * Expr[Array[Int]]. */
    def findIntArray: EMon[Array[Int]] = ???// Unshow.arrayIntImplicit.findUniqueFromStatements(statements)

    /** Find Setting of the given name and type Int from this Arr[Statement] Extension method. */
    def findSettingInt(settingStr: String): EMon[Int] = ShowTDec.intPersistImplicit.settingFromStatements(statements, settingStr)

    /** Find Setting of the given name and type [[Double]] from this Arr[Statement] Extension method. */
    def findSettingDbl(settingStr: String): EMon[Double] = ShowTDec.doublePersistImplicit.settingFromStatements(statements, settingStr)

    /** Find Setting of the given name and type [[Double]] from this Arr[Statement] Extension method. */
    def findSettingPosDbl(settingStr: String): EMon[Double] = Unshow.posDoubleEv.settingFromStatements(statements, settingStr)

    /** Find the [[Boolean]] setting of the given name, from this Arr[Statement] extension method. Returns bad if absent or multiple [[Statement]]s
     *  resolve to Expr[Boolean]. */
    def findSettingBool(settingStr: String): EMon[Boolean] = ShowTDec.booleanPersistImplicit.settingFromStatements(statements, settingStr)
  }
}

/** An un-claused Statement that is not the empty statement. */
case class NonEmptyStatement(expr: Expr, optSemi: OptRef[SemicolonToken]) extends Statement with TextSpanCompound
{ def startMem: TextSpan = expr
  def endMem: TextSpan = optSemi.fld(expr, sc => sc)
}

/** The Semicolon of the Empty statement is the expression of this special case of the unclaused statement */
case class EmptyStatement(st: SemicolonToken) extends Statement with TextSpanCompound
{ override def expr: ClauseMemExpr = st
  override def optSemi: OptRef[SemicolonToken] = OptRef(st)
  override def startMem: TextSpan = st
  override def endMem: TextSpan = st
  def asError[A]: Bad[A] = st.startPosn.bad("Empty Statement")
}

object EmptyStatement
{ def apply(st: SemicolonToken): EmptyStatement = new EmptyStatement(st)
}