/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, annotation.unchecked.uncheckedVariance, reflect.ClassTag

/** The UnShow type class produces an object in memory or an error sequence from RSON syntax strings. */
trait Unshow[+T] extends Persist
{
  /** Tries to return a value of the type from an RSON expression [[Expr]] that has been parsed from a String or text file. This method must be implemented by
   * all instances. */
  def fromExprOld(expr: Expr): EMonOld[T] = ???

  /** Tries to return a value of the type from an RSON expression [[Expr]] that has been parsed from a String or text file. This method must be implemented by
   * all instances. */
  def fromExpr(expr: Expr): ExcMon[T]

  /** Trys to build an object of type T from the statement. */
  final def fromStatementOld(st: Statement): EMonOld[T] = fromExprOld(st.expr)

  /** Trys to build an object of type T from the statement. */
  final def fromStatement(st: Statement): ExcMon[T] = fromExpr(st.expr)
  
  def fromSettingOrExprOld(SettingStr: String, expr: Expr): EMonOld[T] = expr match
  { case AsignExpr(ColonExpr(IdentifierToken(SettingStr), _, IdentifierToken(_)), _, rExpr) => fromExprOld(rExpr)
    case AsignExpr(IdentifierToken(SettingStr), _, rExpr) => fromExprOld(rExpr)
    case e => fromExprOld(e)
  }

  /** Tries to get type from [[Expr]], or from the value [[Expr]] of a setting. */
  def fromSettingOrExpr(SettingStr: String, expr: Expr): ExcMon[T] = expr match
  { case AsignExpr(ColonExpr(IdentifierToken(SettingStr), _, IdentifierToken(_)), _, rExpr) => fromExpr(rExpr)
    case AsignExpr(IdentifierToken(SettingStr), _, rExpr) => fromExpr(rExpr)
    case e => fromExpr(e)
  }

  def fromAnySettingOrExpr(expr: Expr): EMonOld[T] = expr match
  { case AsignExpr(ColonExpr(IdentifierToken(_), _, IdentifierToken(_)), _, rExpr) => fromExprOld(rExpr)
    case AsignExpr(IdentifierToken(_), _, rExpr) => fromExprOld(rExpr)
    case e => fromExprOld(e)
  }

  /** Produces an [[ArrImut]] of the UnShow type from Statements RArr[Statement]. */
  def valuesFromStatements[ArrT <: Arr[T] @uncheckedVariance](sts: RArr[Statement])(implicit arrBuild: BuilderArrMap[T, ArrT] @uncheckedVariance): ArrT =
    sts.mapCollectSuccs(fromStatement)(arrBuild)

  /** Finds value of this UnShow type, returns error if more than one match. */
  def findUniqueTFromStatementsOld[ArrT <: Arr[T] @uncheckedVariance](sts: RArr[Statement])(implicit arrBuild: BuilderArrMap[T, ArrT] @uncheckedVariance):
    EMonOld[T] = valuesFromStatements(sts) match
  { case s if s.length == 0 => TextPosn.emptyErrorOld("No values of type found")
    case s if s.length == 1 => Good(s.head)
    case s3 => sts.startPosn.bad(s3.length.toString -- "values of" -- typeStr -- "found.")
  }

  /** Finds an identifier setting with a value of the type of this UnShow instance from a [Statement]. */
  def settingTFromStatementOld(settingStr: String, st: Statement): EMonOld[T] = st match
  { case StatementNoneEmpty(AsignExpr(IdentLowerToken(_, sym), _, rightExpr), _) if sym == settingStr => fromExprOld(rightExpr)
    case _ => st.startPosn.bad(typeStr -- "not found.")
  }

  /** Finds an identifier setting with a value of the type of this UnShow instance from a [Statement]. */
  def settingTFromStatement(settingStr: String, st: Statement): ExcMon[T] = st match
  { case StatementNoneEmpty(AsignExpr(IdentLowerToken(_, sym), _, rightExpr), _) if sym == settingStr => fromExpr(rightExpr)
    case _ => st.failExc(typeStr -- "not found.")
  }

  /** Finds a setting with a key / code of type KT and a value of the type of this UnShow instance from a [Statement]. */
  def keySettingFromStatementOld[KT](settingCode: KT, st: Statement)(implicit evST: Unshow[KT]): EMonOld[T] = st match
  { case StatementNoneEmpty(AsignExpr(codeExpr, _, rightExpr), _) if evST.fromExprOld(codeExpr) == Good(settingCode) => fromExprOld(rightExpr)
    case _ => st.startPosn.bad(typeStr -- "not found.")
  }

  /** Finds a setting with a key / code of type KT and a value of the type of this UnShow instance from a [Statement]. */
  def keySettingFromStatement[KT](settingCode: KT, st: Statement)(implicit evST: Unshow[KT]): ExcMon[T] = st match
  { case StatementNoneEmpty(AsignExpr(codeExpr, _, rightExpr), _) if evST.fromExpr(codeExpr) == Succ(settingCode) => fromExpr(rightExpr)
    case _ => st.failExc(typeStr -- "not found.")
  }

  /** Finds an identifier setting with a value type of this UnShow instance from an Arr[Statement]. */
  /*def settingFromStatementsOld(sts: RArr[Statement], settingStr: String): EMonOld[T] = sts match
  { case Arr0() => TextPosn.emptyErrorOld("No Statements")
    case Arr1(st1) => settingTFromStatementOld(settingStr, st1)
    case s2 => sts.map(settingTFromStatementOld(settingStr, _)).collect{ case g @ Good(_) => g } match
    { case Arr1(t) => t
      case Arr0() => sts.startPosn.bad(settingStr -- typeStr -- "Setting not found.")
      case s3 => sts.startPosn.bad(s3.length.toString -- "settings of" -- settingStr -- "of" -- typeStr -- "not found.")
    }
  }*/

  /** Finds an identifier setting with a value type of this UnShow instance from an Arr[Statement]. */
  def settingFromStatements(sts: RArr[Statement], settingStr: String): ExcMon[T] = sts match
  { case Arr0() => TextPosn.failEmpty// emptyError("No Statements")
    case Arr1(st1) => settingTFromStatement(settingStr, st1)
    case s2 => sts.map(settingTFromStatement(settingStr, _)).collect { case g @ Succ(_) => g } match
    {
      case Arr1(t) => t
      case Arr0() => sts.failExc(settingStr -- typeStr -- "Setting not found.")
      case s3 => sts.failExc(s3.length.toString -- "settings of" -- settingStr -- "of" -- typeStr -- "not found.")
    }
  }

  /** Finds a key setting with Key type KT of the type of this UnShow instance from an Arr[Statement]. */
  def keySettingFromStatementsOld[KT](sts: RArr[Statement], settingCode: KT)(implicit evST: Unshow[KT]): EMonOld[T] = sts match
  { case Arr0() => TextPosn.emptyErrorOld("No Statements")
    case Arr1(st1) => keySettingFromStatementOld(settingCode, st1)
    case s2 => sts.map(keySettingFromStatementOld(settingCode, _)).collect{ case g @ Good(_) => g } match
    { case Arr1(t) => t
      case Arr0() => sts.startPosn.bad(settingCode.toString -- typeStr -- "Setting not found.")
      case s3 => sts.startPosn.bad(s3.length.toString -- "settings of" -- settingCode.toString -- "of" -- typeStr -- "not found.")
    }
  }

  /** Finds a key setting with Key type KT of the type of this UnShow instance from an Arr[Statement]. */
  def keySettingFromStatements[KT](sts: RArr[Statement], settingCode: KT)(implicit evST: Unshow[KT]): ExcMon[T] = sts match
  { case Arr0() => TextPosn.failEmpty//("No Statements")
    case Arr1(st1) => keySettingFromStatement(settingCode, st1)
    case s2 => sts.map(keySettingFromStatement(settingCode, _)).collect { case g @ Succ(_) => g } match
    { case Arr1(t) => t
      case Arr0() => sts.failExc(settingCode.toString -- typeStr -- "Setting not found.")
      case s3 => sts.failExc(s3.length.toString -- "settings of" -- settingCode.toString -- "of" -- typeStr -- "not found.")
    }
  }

  def concat[TT >: T](operand: Unshow[TT], newTypeStr: String = typeStr): Unshow[TT] = operand match
  { case uSum: UnshowSum[TT] => UnshowSum[TT](newTypeStr, this %: uSum.elems)
    case op => UnshowSum[TT](newTypeStr, RArr(this, op))
  }
}

/** Companion object for the [[Unshow]] type class trait, contains implicit instances for common types. */
object Unshow extends UnshowPriority2
{
  /** Implicit [[Unshow]] instance for an [[Int]] in a standard format. */
  implicit val intEv: Unshow[Int] = new IntEvClass

  class IntEvClass extends Unshow[Int]
  { override def typeStr: String = "Int"
    override val useMultiple: Boolean = false
    
    override def fromExprOld(expr: Expr): EMonOld[Int] = expr match
    { case IntStdToken(i) => Good(i)
      case PreOpExpr(op, IntStdToken(i)) if op.srcStr == "+" => Good(i)
      case PreOpExpr(op, IntStdToken(i)) if op.srcStr == "-" => Good(-i)
      case _ => expr.exprParseErrOld[Int]
    }

    override def fromExpr(expr: Expr): ExcMon[Int] = expr match
    { case IntStdToken(i) => Succ(i)
      case PreOpExpr(op, IntStdToken(i)) if op.srcStr == "+" => Succ(i)
      case PreOpExpr(op, IntStdToken(i)) if op.srcStr == "-" => Succ(-i)
      case _ => expr.exprParseErr[Int]
    }
  }

  /** [[Unshow]] instance / evidence for natural, non-negative [[Int]] in a standard format. This must be passed explicitly. */
  val natEv: Unshow[Int] = new Unshow[Int]
  { override def typeStr: String = "Nat"
    override val useMultiple: Boolean = false

    override def fromExprOld(expr: Expr): EMonOld[Int] = expr match
    { case  NatStdToken(i) => Good(i)
      case _ => expr.exprParseErrOld[Int]
    }

    override def fromExpr(expr: Expr): ExcMon[Int] = expr match
    { case NatStdToken(i) => Succ(i)
      case _ => expr.exprParseErr[Int]
    }
  }

  /** [[Unshow]] instance for [[Int]] in hexadecimal format. This must be passed explicitly. */
  val hexaIntEv: Unshow[Int] = new Unshow[Int]
  { override def typeStr: String = "HexaInt"
    override val useMultiple: Boolean = false

    override def fromExprOld(expr: Expr): EMonOld[Int] = expr match
    { case ValidRawHexaIntToken(i) => Good(i)
      case PreOpExpr(op, ValidRawHexaIntToken(i)) if op.srcStr == "+" => Good(i)
      case PreOpExpr(op, ValidRawHexaIntToken(i)) if op.srcStr == "-" => Good(-i)
      case _ => expr.exprParseErrOld[Int]
    }

    override def fromExpr(expr: Expr): ExcMon[Int] = expr match
    { case ValidRawHexaIntToken(i) => Succ(i)
      case PreOpExpr(op, ValidRawHexaIntToken(i)) if op.srcStr == "+" => Succ(i)
      case PreOpExpr(op, ValidRawHexaIntToken(i)) if op.srcStr == "-" => Succ(-i)
      case _ => expr.exprParseErr[Int]
    }
  }

  /** [[Unshow]] instance for natural non negative [[Int]] in hexadecimal format. This evidence must be passed explicitly. */
  val hexaNatEv: Unshow[Int] = new Unshow[Int]
  {
    override def typeStr: String = "HexaNat"

    override def fromExprOld(expr: Expr): EMonOld[Int] = expr match
    { case ValidRawHexaNatToken(i) => Good(i)
      case _ => expr.exprParseErrOld[Int]
    }

    override def fromExpr(expr: Expr): ExcMon[Int] = expr match
    { case ValidRawHexaNatToken(i) => Succ(i)
      case _ => expr.exprParseErr[Int]
    }
  }

  /** [[Unshow]] instance for [[Int]] in base32 format. This must be passed explicitly. */
  val base32IntEv: Unshow[Int] = new Unshow[Int]
  {
    override def typeStr: String = "Base32Int"

    override def fromExprOld(expr: Expr): EMonOld[Int] = expr match
    { case ValidRawBase32IntToken(i) => Good(i)
      case PreOpExpr(op, ValidRawBase32IntToken(i)) if op.srcStr == "+" => Good(i)
      case PreOpExpr(op, ValidRawBase32IntToken(i)) if op.srcStr == "-" => Good(-i)
      case _ => expr.exprParseErrOld[Int]
    }

    override def fromExpr(expr: Expr): ExcMon[Int] = expr match
    { case ValidRawBase32IntToken(i) => Succ(i)
      case PreOpExpr(op, ValidRawBase32IntToken(i)) if op.srcStr == "+" => Succ(i)
      case PreOpExpr(op, ValidRawBase32IntToken(i)) if op.srcStr == "-" => Succ(-i)
      case _ => expr.exprParseErr[Int]
    }
  }

  /** [[Unshow]] instance for natural non-negative [[Int]] in base32 format. This evidence must be passed explicitly. */
  val base32NatEv: Unshow[Int] = new Unshow[Int]
  {
    override def typeStr: String = "Base32Nat"

    override def fromExprOld(expr: Expr): EMonOld[Int] = expr match
    { case ValidRawBase32NatToken(n) => Good(n)
      case _ => expr.exprParseErrOld[Int]
    }

    override def fromExpr(expr: Expr): ExcMon[Int] = expr match
    { case ValidRawBase32NatToken(n) => Succ(n)
      case _ => expr.exprParseErr[Int]
    }
  }

  def intSubset(pred: Int => Boolean): Unshow[Int] = new Unshow[Int]
  { override def typeStr: String = "Int"
    override def fromExprOld(expr: Expr): EMonOld[Int] = intEv.fromExprOld(expr).flatMap(i => ife(pred(i), Good(i), bad1(expr, s"$i does not fullfll predicate.")))

    override def fromExpr(expr: Expr): ExcMon[Int] =
      intEv.fromExpr(expr).flatMap(i => ife(pred(i), Succ(i), expr.startPosn.fail(s"$i does not fullfll predicate.")))
  }

  /** Implicit [[Unshow]] instance / evidence for [[Double]]. */
  implicit val doubleEv: Unshow[Double] = PersistBoth.doubleEv

  /** [[Unshow]] instance / evidence for positive, non-negative [[Double]]. This must be passed explicitly. */
  val posDoubleEv: Unshow[Double] = new Unshow[Double]
  { override def typeStr: String = "PosDFloat"

    override def fromExprOld(expr: Expr): EMonOld[Double] = expr match
    { case ValidPosFracToken(d) => Good(d)
      case PreOpExpr(op, ValidPosFracToken(d)) if op.srcStr == "+" => Good(d)
      case PreOpExpr(op, ValidPosFracToken(d)) if op.srcStr == "-" => Good(-d)
      case _ => expr.exprParseErrOld[Double]
    }

    override def fromExpr(expr: Expr): ExcMon[Double] = expr match
    { case ValidPosFracToken(d) => Succ(d)
      case PreOpExpr(op, ValidPosFracToken(d)) if op.srcStr == "+" => Succ(d)
      case PreOpExpr(op, ValidPosFracToken(d)) if op.srcStr == "-" => Succ(-d)
      case _ => expr.exprParseErr[Double]
    }
  }

  /** Implicit [[Unshow]] instance / evidence for [[Float]]. */
  implicit val floatEv: Unshow[Float] = new Unshow[Float]
  { override def typeStr: String = "SFloat"

    override def fromExprOld(expr: Expr): EMonOld[Float] = expr match
    { case NatBase10Token(_, i) => Good(i.toFloat)
      case PreOpExpr(op, NatBase10Token(_, i)) if op.srcStr == "+" => Good(i.toFloat)
      case PreOpExpr(op, NatBase10Token(_, i)) if op.srcStr == "-" => Good(-(i.toFloat))
      case intok: NegBase10Token => Good(intok.getIntStd.toFloat)
      case  _ => expr.exprParseErrOld[Float]
    }

    override def fromExpr(expr: Expr): ExcMon[Float] = expr match
    { case NatBase10Token(_, i) => Succ(i.toFloat)
      case PreOpExpr(op, NatBase10Token(_, i)) if op.srcStr == "+" => Succ(i.toFloat)
      case PreOpExpr(op, NatBase10Token(_, i)) if op.srcStr == "-" => Succ(-(i.toFloat))
      case intok: NegBase10Token => Succ(intok.getIntStd.toFloat)
      case _ => expr.exprParseErr[Float]
    }
  }

  /** Implicit [[Unshow]] instance / evidence for [[Long]]. */
  implicit val longEv: Unshow[Long] = new Unshow[Long]
  { override def typeStr = "Long"

    override def fromExprOld(expr: Expr): EMonOld[Long] = expr match
    { case NatBase10Token(_, i) => Good(i.toLong)
      case PreOpExpr(op, NatBase10Token(_, i)) if op.srcStr == "+" => Good(i.toLong)
      case PreOpExpr(op, NatBase10Token(_, i)) if op.srcStr == "-" => Good(-i.toLong)
      case  _ => expr.exprParseErrOld[Long]
    }

    override def fromExpr(expr: Expr): ExcMon[Long] = expr match
    {
      case NatBase10Token(_, i) => Succ(i.toLong)
      case PreOpExpr(op, NatBase10Token(_, i)) if op.srcStr == "+" => Succ(i.toLong)
      case PreOpExpr(op, NatBase10Token(_, i)) if op.srcStr == "-" => Succ(-i.toLong)
      case _ => expr.exprParseErr[Long]
    }
  }

  /** Implicit [[Unshow]] instance / evidence for [[Boolean]]. */
  implicit val booleanEv: Unshow[Boolean] = new Unshow[Boolean]
  { override def typeStr: String = "Bool"

    override def fromExprOld(expr: Expr): EMonOld[Boolean] = expr match
    { case IdentLowerToken(_, str) if str == "true" => Good(true)
      case IdentLowerToken(_, str) if str == "false" => Good(false)
      case _ => expr.exprParseErrOld[Boolean]
    }

    override def fromExpr(expr: Expr): ExcMon[Boolean] = expr match
    { case IdentLowerToken(_, str) if str == "true" => Succ(true)
      case IdentLowerToken(_, str) if str == "false" => Succ(false)
      case _ => expr.exprParseErr[Boolean]
    }
  }

  /** Implicit [[Unshow]] instance / evidence for [[String]]. */
  implicit val stringEv: Unshow[String] = new Unshow[String]
  { override def typeStr: String = "Str"

    override def fromExprOld(expr: Expr): EMonOld[String] = expr match
    { case StringToken(_, stringStr) => Good(stringStr)
      case  _ => expr.exprParseErrOld[String]
    }

    override def fromExpr(expr: Expr): ExcMon[String] = expr match
    { case StringToken(_, stringStr) => Succ(stringStr)
      case _ => expr.exprParseErr[String]
    }
  }

  /** Implicit [[Unshow]] instance / evidence for [[Char]]. */
  implicit val charEv: Unshow[Char] = new Unshow[Char]
  {
    override def typeStr: String = "Char"

    override def fromExprOld(expr: Expr): EMonOld[Char] = expr match
    { case CharToken(_, char) => Good(char)
      case  _ => expr.exprParseErrOld[Char]
    }

    override def fromExpr(expr: Expr): ExcMon[Char] = expr match
    { case CharToken(_, char) => Succ(char)
      case _ => expr.exprParseErr[Char]
    }
  }

  /** Implicit [[Unshow]] instance / evidence for [[Array]][Int]. */
  implicit val arrayIntImplicit: Unshow[Array[Int]] = UnshowSeq[Int, Array[Int]]()

  /** Implicit [[Unshow]] instance / evidence for [[Array]][A]. */
  implicit def arrayRefEv[A <: AnyRef](implicit evA: Unshow[A], ct: ClassTag[A]): Unshow[Array[A]] = UnshowSeq[A, Array[A]]()

  /** Implicit method for creating List[A: Persist] instances. */
  implicit def listImplicit[A, ArrA <: Arr[A]](implicit evIn: Unshow[A]): Unshow[List[A]] = UnshowSeq[A, List[A]]()

  /** [[Unshow]] type class instance for [[Option]] */
  implicit def optionEv[A](implicit evA: Unshow[A]): UnshowSum[Option[A]] = UnshowSum[Option[A]]("Opt", someUnShowImplicit[A](evA), noneUnEv)
}

trait UnshowPriority2 extends UnshowPriority3
{
  /** Implicit method for creating Vector[A: UnShow] instances. */
  implicit def vectorImplicit[A, ArrA <: Arr[A]](implicit evIn: Unshow[A], buildIn: BuilderArrMap[A, ArrA]): Unshow[Vector[A]] = new Unshow[Vector[A]]
  { val evA: Unshow[A] = evIn
    val build: BuilderArrMap[A, ArrA] = buildIn
    override def typeStr: String = "Seq" + evA.typeStr.enSquare

    override def fromExpr(expr: Expr): ExcMon[Vector[A]] = expr match
    { case _: EmptyExprToken => Succ(Vector[A]())
      case AlphaSquareParenth("Seq", ts, sts) => sts.mapErrBi(s => evA.fromExpr(s.expr))(build).map(_.toVector)
      case AlphaParenth("Seq", sts) => sts.mapErrBi(s => evA.fromExpr(s.expr))(build).map(_.toVector)
      case e => expr.failExc("Unknown Expression for Vector")
    }
  }

  /** Implicit [[Unshow]] type class instance / evidence method for [[Some]] objects. */
  implicit def someUnShowImplicit[A](implicit ev: Unshow[A]): Unshow[Some[A]] = new Unshow[Some[A]]
  { override def typeStr: String = "Some" + ev.typeStr.enSquare

    override def fromExprOld(expr: Expr): EMonOld[Some[A]] = expr match {
      case AlphaBracketExpr(IdentUpperToken(_, "Some"), Arr1(ParenthBlock(Arr1(hs), _, _))) => ev.fromExprOld(hs.expr).map(Some(_))
      case expr => ev.fromExprOld(expr).map(Some(_))
    }

    override def fromExpr(expr: Expr): ExcMon[Some[A]] = expr match
    { case AlphaBracketExpr(IdentUpperToken(_, "Some"), Arr1(ParenthBlock(Arr1(hs), _, _))) => ev.fromExpr(hs.expr).map(Some(_))
      case expr => ev.fromExpr(expr).map(Some(_))
    }
  }
}

trait UnshowPriority3
{
  implicit val noneUnEv: Unshow[None.type] = new Unshow[None.type]
  { override def typeStr: String = "None"

    override def fromExprOld(expr: Expr): EMonOld[None.type] = expr match
    { case IdentUpperToken(_, "None") => Good(None)
      case eet: EmptyExprToken => Good(None)
      case e => bad1(e, "None not found")
    }

    override def fromExpr(expr: Expr): ExcMon[None.type] = expr match
    { case IdentUpperToken(_, "None") => Succ(None)
      case eet: EmptyExprToken => Succ(None)
      case e => expr.exprParseErr
    }
  }
}