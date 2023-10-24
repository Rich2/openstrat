/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, annotation.unchecked.uncheckedVariance

/** The UnShow type class produces an object in memory or an error sequence from RSON syntax strings. */
trait Unshow[+T] extends PersistBase
{
  /** Tries to return a value of the type from an RSON expression [[Expr]] that has been parsed from a String or text file. This method must be
   *  implemented by all instances. */
  def fromExpr(expr: Expr): EMon[T]

  /** Trys to build an object of type T from the statement. */
  final def fromStatement(st: Statement): EMon[T] = fromExpr(st.expr)

  def fromSettingOrExpr(SettingStr: String, expr: Expr): EMon[T] = expr match
  { case AsignExpr(ColonExpr(IdentifierToken(SettingStr), _, IdentifierToken(_)), _, rExpr) => fromExpr(rExpr)
    case AsignExpr(IdentifierToken(SettingStr), _, rExpr) => fromExpr(rExpr)
    case e => fromExpr(e)
  }

  def fromAnySettingOrExpr(expr: Expr): EMon[T] = expr match
  { case AsignExpr(ColonExpr(IdentifierToken(_), _, IdentifierToken(_)), _, rExpr) => fromExpr(rExpr)
    case AsignExpr(IdentifierToken(_), _, rExpr) => fromExpr(rExpr)
    case e => fromExpr(e)
  }

  /** Produces an ArrImut of the UnShow type from Statements (Refs[Statement]. */
  def valuesFromStatements[ArrT <: Arr[T] @uncheckedVariance](sts: RArr[Statement])(implicit arrBuild: ArrMapBuilder[T, ArrT] @uncheckedVariance): ArrT =
    sts.mapCollectGoods(fromStatement)(arrBuild)

  /** Finds value of this UnShow type, returns error if more than one match. */
  def findUniqueTFromStatements[ArrT <: Arr[T] @uncheckedVariance](sts: RArr[Statement])(implicit arrBuild: ArrMapBuilder[T, ArrT] @uncheckedVariance):
    EMon[T] = valuesFromStatements(sts) match
  { case s if s.length == 0 => TextPosn.emptyError("No values of type found")
    case s if s.length == 1 => Good(s.head)
    case s3 => sts.startPosn.bad(s3.length.toString -- "values of" -- typeStr -- "found.")
  }

  /** Finds an identifier setting with a value of the type of this UnShow instance from a [Statement]. */
  def settingTFromStatement(settingStr: String, st: Statement): EMon[T] = st match
  { case StatementNoneEmpty(AsignExpr(IdentLowerToken(_, sym), _, rightExpr), _) if sym == settingStr => fromExpr(rightExpr)
    case _ => st.startPosn.bad(typeStr -- "not found.")
  }

  /** Finds a setting with a key / code of type KT and a value of the type of this UnShow instance from a [Statement]. */
  def keySettingFromStatement[KT](settingCode: KT, st: Statement)(implicit evST: Unshow[KT]): EMon[T] = st match
  { case StatementNoneEmpty(AsignExpr(codeExpr, _, rightExpr), _) if evST.fromExpr(codeExpr) == Good(settingCode) => fromExpr(rightExpr)
    case _ => st.startPosn.bad(typeStr -- "not found.")
  }

  /** Finds an identifier setting with a value type of this UnShow instance from an Arr[Statement]. */
  def settingFromStatements(sts: RArr[Statement], settingStr: String): EMon[T] = sts match
  { case Arr0() => TextPosn.emptyError("No Statements")
    case Arr1(st1) => settingTFromStatement(settingStr, st1)
    case s2 => sts.map(settingTFromStatement(settingStr, _)).collect{ case g @ Good(_) => g } match
    { case Arr1(t) => t
      case Arr0() => sts.startPosn.bad(settingStr -- typeStr -- "Setting not found.")
      case s3 => sts.startPosn.bad(s3.length.toString -- "settings of" -- settingStr -- "of" -- typeStr -- "not found.")
    }
  }

  /** Finds a key setting with Key type KT of the type of this UnShow instance from an Arr[Statement]. */
  def keySettingFromStatements[KT](sts: RArr[Statement], settingCode: KT)(implicit evST: Unshow[KT]): EMon[T] = sts match
  { case Arr0() => TextPosn.emptyError("No Statements")
    case Arr1(st1) => keySettingFromStatement(settingCode, st1)
    case s2 => sts.map(keySettingFromStatement(settingCode, _)).collect{ case g @ Good(_) => g } match
    { case Arr1(t) => t
      case Arr0() => sts.startPosn.bad(settingCode.toString -- typeStr -- "Setting not found.")
      case s3 => sts.startPosn.bad(s3.length.toString -- "settings of" -- settingCode.toString -- "of" -- typeStr -- "not found.")
    }
  }

  def ++[TT >: T] (operand: Unshow[TT])(implicit ev: Unshow[TT]): Unshow[TT] = operand match
  { case uSum: UnshowSum[TT] => UnshowSum[TT](ev.typeStr, this %: uSum.elems)
    case op => UnshowSum[TT](ev.typeStr, RArr(this, op))
  }
}

/** Companion object for the [[Unshow]] type class trait, contains implicit instances for common types. */
object Unshow extends UnshowPriority2
{
  /** Implicit [[Unshow]] instance for an [[Int]] in a standard format. */
  implicit val intEv: Unshow[Int] = new IntEvClass
  class IntEvClass extends Unshow[Int]
  { override def typeStr: String = "Int"

    override def fromExpr(expr: Expr): EMon[Int] = expr match {
      case IntStdToken(i) => Good(i)
      case PreOpExpr(op, IntStdToken(i)) if op.srcStr == "+" => Good(i)
      case PreOpExpr(op, IntStdToken(i)) if op.srcStr == "-" => Good(-i)
      case _ => expr.exprParseErr[Int]
    }
  }

  /** [[Unshow]] instance / evidence for natural, non-negative [[Int]] in a standard format. This must be passed explicitly. */
  val natEv: Unshow[Int] = new Unshow[Int]
  {
    override def typeStr: String = "Nat"

    override def fromExpr(expr: Expr): EMon[Int] = expr match
    { case  NatStdToken(i) => Good(i)
      case _ => expr.exprParseErr[Int]
    }
  }

  /** [[Unshow]] instance for [[Int]] in hexadecimal format. This must be passed explicitly. */
  val hexaIntEv: Unshow[Int] = new Unshow[Int]
  {
    override def typeStr: String = "HexaInt"

    override def fromExpr(expr: Expr): EMon[Int] = expr match
    { case ValidRawHexaIntToken(i) => Good(i)
      case PreOpExpr(op, ValidRawHexaIntToken(i)) if op.srcStr == "+" => Good(i)
      case PreOpExpr(op, ValidRawHexaIntToken(i)) if op.srcStr == "-" => Good(-i)
      case _ => expr.exprParseErr[Int]
    }
  }

  /** [[Unshow]] instance for natural non negative [[Int]] in hexadecimal format. This evidence must be passed explicitly. */
  val hexaNatEv: Unshow[Int] = new Unshow[Int]
  {
    override def typeStr: String = "HexaNat"

    override def fromExpr(expr: Expr): EMon[Int] = expr match
    { case ValidRawHexaNatToken(i) => Good(i)
      case _ => expr.exprParseErr[Int]
    }
  }

  /** [[Unshow]] instance for [[Int]] in base32 format. This must be passed explicitly. */
  val base32IntEv: Unshow[Int] = new Unshow[Int]
  {
    override def typeStr: String = "Base32Int"

    override def fromExpr(expr: Expr): EMon[Int] = expr match
    { case ValidRawBase32IntToken(i) => Good(i)
      case PreOpExpr(op, ValidRawBase32IntToken(i)) if op.srcStr == "+" => Good(i)
      case PreOpExpr(op, ValidRawBase32IntToken(i)) if op.srcStr == "-" => Good(-i)
      case _ => expr.exprParseErr[Int]
    }
  }

  /** [[Unshow]] instance for natural non negative [[Int]] in base32 format. This evidence must be passed explicitly. */
  val base32NatEv: Unshow[Int] = new Unshow[Int]
  {
    override def typeStr: String = "Base32Nat"

    override def fromExpr(expr: Expr): EMon[Int] = expr match
    { case ValidRawBase32NatToken(n) => Good(n)
      case _ => expr.exprParseErr[Int]
    }
  }

  /** Implicit [[Unshow]] instance / evidence for [[Double]]. */
  implicit val doubleEv: Unshow[Double] = new Unshow[Double]
  { override def typeStr: String = "DFloat"

    override def fromExpr(expr: Expr): EMon[Double] = expr match
    { case ValidFracToken(d) => Good(d)
      case PreOpExpr(op, ValidFracToken(d)) if op.srcStr == "+" => Good(d)
      case PreOpExpr(op, ValidFracToken(d)) if op.srcStr == "-" => Good(-d)
      case _ => expr.exprParseErr[Double]
    }
  }

  /** [[Unshow]] instance / evidence for positive, non-negative [[Double]]. This must be passed explicitly. */
  val posDoubleEv: Unshow[Double] = new Unshow[Double]
  { override def typeStr: String = "PosDFloat"

    override def fromExpr(expr: Expr): EMon[Double] = expr match
    { case ValidPosFracToken(d) => Good(d)
      case PreOpExpr(op, ValidPosFracToken(d)) if op.srcStr == "+" => Good(d)
      case PreOpExpr(op, ValidPosFracToken(d)) if op.srcStr == "-" => Good(-d)
      case _ => expr.exprParseErr[Double]
    }
  }

  /** Implicit [[Unshow]] instance / evidence for [[Float]]. */
  implicit val floatEv: Unshow[Float] = new Unshow[Float]
  { override def typeStr: String = "SFloat"

    override def fromExpr(expr: Expr): EMon[Float] = expr match
    { case NatDeciToken(_, i) => Good(i.toFloat)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "+" => Good(i.toFloat)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "-" => Good(-(i.toFloat))
      case intok: NegDeciToken => Good(intok.getIntStd.toFloat)
      case  _ => expr.exprParseErr[Float]
    }
  }

  /** Implicit [[Unshow]] instance / evidence for [[Long]]. */
  implicit val longEv: Unshow[Long] = new Unshow[Long]
  { override def typeStr = "Long"

    override def fromExpr(expr: Expr): EMon[Long] = expr match
    { case NatDeciToken(_, i) => Good(i.toLong)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "+" => Good(i.toLong)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "-" => Good(-i.toLong)
      case  _ => expr.exprParseErr[Long]
    }
  }

  /** Implicit [[Unshow]] instance / evidence for [[Boolean]]. */
  implicit val booleanEv: Unshow[Boolean] = new Unshow[Boolean]
  { override def typeStr: String = "Bool"

    override def fromExpr(expr: Expr): EMon[Boolean] = expr match
    { case IdentLowerToken(_, str) if str == "true" => Good(true)
      case IdentLowerToken(_, str) if str == "false" => Good(false)
      case _ => expr.exprParseErr[Boolean]
    }
  }

  /** Implicit [[Unshow]] instance / evidence for [[String]]. */
  implicit val stringEv: Unshow[String] = new Unshow[String]
  { override def typeStr: String = "Str"

    override def fromExpr(expr: Expr): EMon[String] = expr match
    { case StringToken(_, stringStr) => Good(stringStr)
      case  _ => expr.exprParseErr[String]
    }
  }

  /** Implicit [[Unshow]] instance / evidence for [[Char]]. */
  implicit val charEv: Unshow[Char] = new Unshow[Char]
  {
    override def typeStr: String = "Char"

    override def fromExpr(expr: Expr): EMon[Char] = expr match
    { case CharToken(_, char) => Good(char)
      case  _ => expr.exprParseErr[Char]
    }
  }

  implicit val arrayIntImplicit: Unshow[Array[Int]] = new Unshow[Array[Int]]//(ShowT.intPersistImplicit)
  {
    def typeStr: String = "Array[Int]"

    override def fromExpr(expr: Expr): EMon[Array[Int]] = expr match
    { case SemicolonToken(_) => Good(Array[Int]())
      case AlphaBracketExpr(IdentUpperToken(_, "Seq"), Arr2(SquareBlock(ts, _, _), ParenthBlock(sts, _, _))) => ???
      //sts.eMap[Int](_.errGet[Int](evA)).map(_.array)
      case e => bad1(expr, "Unknown Exoression for Seq")
    }
  }

  /** Implicit method for creating List[A: Persist] instances. */
  implicit def listImplicit[A, ArrA <: Arr[A]](implicit evIn: Unshow[A], buildIn: ArrMapBuilder[A, ArrA]): Unshow[List[A]] = new Unshow[List[A]]// with ShowIterable[A, List[A]]
  { val evA: Unshow[A] = evIn
    val build: ArrMapBuilder[A, ArrA] = buildIn
    override def typeStr: String = "Seq" + evA.typeStr.enSquare

    override def fromExpr(expr: Expr): EMon[List[A]] = expr match
    { case _: EmptyExprToken => Good(List[A]())
      case AlphaSquareParenth("Seq", _, sts) => sts.eMap(s => evA.fromExpr(s.expr))(build).map(_.toList)
      case AlphaParenth("Seq", sts) => sts.eMap(s => evA.fromExpr(s.expr))(build).map(_.toList)
      case e => bad1(expr, expr.toString + " unknown Expression for Seq")
    }
  }

  implicit def someUnShowImplicit[A](implicit ev: Unshow[A]): Unshow[Some[A]] = new Unshow[Some[A]]
  { override def typeStr: String = "Some" + ev.typeStr.enSquare

    override def fromExpr(expr: Expr): EMon[Some[A]] = expr match
    { case AlphaBracketExpr(IdentUpperToken(_, "Some"), Arr1(ParenthBlock(Arr1(hs), _, _))) => ev.fromExpr(hs.expr).map(Some(_))
      case expr => ev.fromExpr(expr).map(Some(_))
    }
  }

  implicit def optionEv[A](implicit evA: Unshow[A]): UnshowSum[Option[A]] = UnshowSum[Option[A]]("Opt", someUnShowImplicit[A](evA))

  def optionUnShowImplicit[A](implicit evA: Unshow[A]): Unshow[Option[A]] = new UnShowSum2[Option[A], Some[A], None.type]
  { override def typeStr: String = "Option" + evA.typeStr.enSquare
    override def ev1: Unshow[Some[A]] = someUnShowImplicit[A](evA)
    override def ev2: Unshow[None.type] = noneUnShowImplicit
  }
}

trait UnshowPriority2 extends UnshowPriority3
{
  /** Implicit method for creating Vector[A: UnShow] instances. */
  implicit def vectorImplicit[A, ArrA <: Arr[A]](implicit evIn: Unshow[A], buildIn: ArrMapBuilder[A, ArrA]): Unshow[Vector[A]] = new Unshow[Vector[A]]
  { val evA: Unshow[A] = evIn
    val build: ArrMapBuilder[A, ArrA] = buildIn
    override def typeStr: String = "Seq" + evA.typeStr.enSquare

    override def fromExpr(expr: Expr): EMon[Vector[A]] = expr match {
      case eet: EmptyExprToken => Good(Vector[A]())
      case AlphaSquareParenth("Seq", ts, sts) => sts.eMap(s => evA.fromExpr(s.expr))(build).map(_.toVector)
      case AlphaParenth("Seq", sts) => sts.eMap(s => evA.fromExpr(s.expr))(build).map(_.toVector)
      case e => bad1(expr, "Unknown Exoression for Seq")
    }
  }
}

trait UnshowPriority3
{
  implicit val noneUnShowImplicit: Unshow[None.type] = new Unshow[None.type]
  { override def typeStr: String = "None"

    def fromExpr(expr: Expr): EMon[None.type] = expr match
    { case IdentUpperToken(_, "None") => Good(None)
      case eet: EmptyExprToken => Good(None)
      case e => bad1(e, "None not found")
    }
  }
}