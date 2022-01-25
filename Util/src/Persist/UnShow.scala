/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, annotation.unchecked.uncheckedVariance

/** The UnShow type class produces an object in memory or an error sequence from RSON syntax strings. */
trait UnShow[+T] extends ShowSelf
{
  def fromExpr(expr: Expr): EMon[T]
  
  /** Trys to build an object of type T from the statement. Not sure if this is useful. */
  final def fromStatement(st: Statement): EMon[T] = fromExpr(st.expr)

  /** Produces an ArrImut of the UnShow type from Statements (Refs[Statement]. */
  def valuesFromStatements[ArrT <: SeqImut[T] @uncheckedVariance](sts: Statements)(implicit arrBuild: ArrBuilder[T, ArrT] @uncheckedVariance): ArrT =
    sts.mapCollectGoods(fromStatement)(arrBuild)

  /** Produces a List of the UnShow type from List of Statements */
  def valueListFromStatements(l: Statements): List[T] = l.map(fromStatement(_)).collectList{ case Good(value) => value }

  /** Finds value of UnShow type, returns error if more than one match. */
  def findUniqueFromStatements(sts: Statements): EMon[T] = valueListFromStatements(sts) match
  { case Nil => TextPosn.emptyError("No values of type found")
    case h :: Nil => Good(h)
    case s3 => sts.startPosn.bad(s3.length.toString -- "values of" -- typeStr -- "found.")
  }

  /** Finds value of this UnShow type, returns error if more than one match. */
  def findUniqueTFromStatements[ArrT <: SeqImut[T] @uncheckedVariance](sts: Statements)(implicit arrBuild: ArrBuilder[T, ArrT] @uncheckedVariance):
    EMon[T] = valuesFromStatements(sts) match
  { case s if s.dataLength == 0 => TextPosn.emptyError("No values of type found")
    case s if s.dataLength == 1 => Good(s.head)
    case s3 => sts.startPosn.bad(s3.dataLength.toString -- "values of" -- typeStr -- "found.")
  }

  /** Finds an identifier setting with a value of the type of this UnShow instance from a [Statement]. */
  def settingTFromStatement(settingStr: String, st: Statement): EMon[T] = st match
  { case NonEmptyStatement(AsignExpr(IdentLowerToken(_, sym), _, rightExpr), _) if sym == settingStr => fromExpr(rightExpr)
    case _ => st.startPosn.bad(typeStr -- "not found.")
  }

  /** Finds a setting with a key / code of type KT and a value of the type of this UnShow instance from a [Statement]. */
  def keySettingFromStatement[KT](settingCode: KT, st: Statement)(implicit evST: UnShow[KT]): EMon[T] = st match
  { case NonEmptyStatement(AsignExpr(codeExpr, _, rightExpr), _) if evST.fromExpr(codeExpr) == Good(settingCode) => fromExpr(rightExpr)
    case _ => st.startPosn.bad(typeStr -- "not found.")
  }

  /** Finds an identifier setting with a value type of this UnShow instance from an Arr[Statement]. */
  def settingFromStatements(sts: Arr[Statement], settingStr: String): EMon[T] = sts match
  { case Arr0() => TextPosn.emptyError("No Statements")
    case Arr1(st1) => settingTFromStatement(settingStr, st1)
    case s2 => sts.map(settingTFromStatement(settingStr, _)).collect{ case g @ Good(_) => g } match
    { case Arr1(t) => t
      case Arr0() => sts.startPosn.bad(settingStr -- typeStr -- "Setting not found.")
      case s3 => sts.startPosn.bad(s3.dataLength.toString -- "settings of" -- settingStr -- "of" -- typeStr -- "not found.")
    }
  }

  /** Finds a key setting with Key type KT of the type of this UnShow instance from an Arr[Statement]. */
  def keySettingFromStatements[KT](sts: Arr[Statement], settingCode: KT)(implicit evST: UnShow[KT]): EMon[T] = sts match
  { case Arr0() => TextPosn.emptyError("No Statements")
    case Arr1(st1) => keySettingFromStatement(settingCode, st1)
    case s2 => sts.map(keySettingFromStatement(settingCode, _)).collect{ case g @ Good(_) => g } match
    { case Arr1(t) => t
      case Arr0() => sts.startPosn.bad(settingCode.toString -- typeStr -- "Setting not found.")
      case s3 => sts.startPosn.bad(s3.dataLength.toString -- "settings of" -- settingCode.toString -- "of" -- typeStr -- "not found.")
    }
  }
}

/** Companion object for the [[UnShow]] type class trait, contains implicit instances for common types. */
object UnShow
{
  //implicit def tuple2Implicit[A1, A2](implicit ev1: Persist[A1], ev2: Persist[A2], eq1: EqT[A1], eq2: EqT[A2]): Persist[Tuple2[A1, A2]] =
  // Persist2[A1, A2, (A1, A2)]("Tuple2", "_1", _._1, "_2", _._2, (a1, a2) => (a1, a2))

  implicit val intImplicit: UnShow[Int] = new UnShow[Int]
  {
    override def typeStr: String = "Int"

    override def fromExpr(expr: Expr): EMon[Int] = expr match {
      case IntDeciToken(i) => Good(i)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "+" => Good(i.toInt)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "-" => Good(-i.toInt)
      case _ => expr.exprParseErr[Int]
    }
  }

  implicit val doubleImplicit: UnShow[Double] = new UnShow[Double]
  { override def typeStr: String = "DFloat"

    override def fromExpr(expr: Expr): EMon[Double] = expr match {
      case dft @ DeciFracToken(_, _, _, _) => Good(dft.doubleValue)
      case NatDeciToken(_, i) => Good(i.toDouble)

      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "+" => Good(i.toDouble)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "-" => Good(-i.toDouble)

      case intok: NegDeciToken => Good(intok.getInt.toDouble)
      case _ => expr.exprParseErr[Double]
    }
  }

  implicit val floatImplicit: UnShow[Float] = new UnShow[Float]
  { override def typeStr: String = "SFloat"

    override def fromExpr(expr: Expr): EMon[Float] = expr match
    { case NatDeciToken(_, i) => Good(i.toFloat)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "+" => Good(i.toFloat)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "-" => Good(-(i.toFloat))
      case intok: NegDeciToken => Good(intok.getInt.toFloat)
      case  _ => expr.exprParseErr[Float]
    }
  }

  implicit val longImplicit: UnShow[Long] = new UnShow[Long]
  { override def typeStr = "Long"

    override def fromExpr(expr: Expr): EMon[Long] = expr match
    { case NatDeciToken(_, i) => Good(i.toLong)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "+" => Good(i.toLong)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "-" => Good(-i.toLong)
      case  _ => expr.exprParseErr[Long]
    }
  }

  implicit val booleanImplicit: UnShow[Boolean] = new UnShow[Boolean]
  { override def typeStr: String = "Bool"

    override def fromExpr(expr: Expr): EMon[Boolean] = expr match
    { case IdentLowerToken(_, str) if str == "true" => Good(true)
      case IdentLowerToken(_, str) if str == "false" => Good(false)
      case _ => expr.exprParseErr[Boolean]
    }
  }

  implicit val stringImplicit: UnShow[String] = new UnShow[String]
  { override def typeStr: String = "Str"

    override def fromExpr(expr: Expr): EMon[String] = expr match
    { case StringToken(_, stringStr) => Good(stringStr)
      case  _ => expr.exprParseErr[String]
    }
  }

  implicit val charImplicit: UnShow[Char] = new UnShow[Char]
  {
    override def typeStr: String = "Char"

    override def fromExpr(expr: Expr): EMon[Char] = expr match
    { case CharToken(_, char) => Good(char)
      case  _ => expr.exprParseErr[Char]
    }
  }

  implicit val arrayIntImplicit: UnShow[Array[Int]] = new UnShow[Array[Int]]//(ShowT.intPersistImplicit)
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
  implicit def listImplicit[A](implicit evIn: UnShow[A]): UnShow[List[A]] = new UnShow[List[A]]// with ShowIterable[A, List[A]]
  { val evA: UnShow[A] = evIn
    override def typeStr: String = "Seq" + evA.typeStr.enSquare

    override def fromExpr(expr: Expr): EMon[List[A]] = expr match
    { case eet: EmptyExprToken => Good(List[A]())
      case AlphaSquareParenth("Seq", ts, sts) => ??? //sts.eMap(s => evA.fromExpr(s.expr)).toList
      case AlphaParenth("Seq", sts) => ??? // sts.eMap[A](_.errGet[A](evA))
      case e => bad1(expr, "Unknown Exoression for Seq")
    }
  }

  /** Implicit method for creating Vector[A: UnShow] instances. */
  implicit def vectorImplicit[A](implicit evIn: UnShow[A]): UnShow[Vector[A]] = new UnShow[Vector[A]]
  { val evA: UnShow[A] = evIn
    override def typeStr: String = "Seq" + evA.typeStr.enSquare

    override def fromExpr(expr: Expr): EMon[Vector[A]] = expr match
    { case eet: EmptyExprToken => Good(Vector[A]())
      case AlphaSquareParenth("Seq", ts, sts) => ??? //sts.eMap(s => evA.fromExpr(s.expr)).toList
      case AlphaParenth("Seq", sts) => ??? // sts.eMap[A](_.errGet[A](evA))
      case e => bad1(expr, "Unknown Exoression for Seq")
    }
  }

  implicit def someUnShowImplicit[A](implicit ev: UnShow[A]): UnShow[Some[A]] = new UnShow[Some[A]]
  { override def typeStr: String = "Some" + ev.typeStr.enSquare

    override def fromExpr(expr: Expr): EMon[Some[A]] = expr match
    { case AlphaBracketExpr(IdentUpperToken(_, "Some"), Arr1(ParenthBlock(Arr1(hs), _, _))) => ev.fromExpr(hs.expr).map(Some(_))
      case expr => ev.fromExpr(expr).map(Some(_))
    }
  }

  implicit def optionUnShowImplicit[A](implicit evA: UnShow[A]): UnShow[Option[A]] = new UnShowSum2[Option[A], Some[A], None.type]
  { override def typeStr: String = "Option" + evA.typeStr.enSquare
    override def ev1: UnShow[Some[A]] = someUnShowImplicit[A](evA)
    override def ev2: UnShow[None.type] = noneUnShowImplicit
  }

  implicit val noneUnShowImplicit: UnShow[None.type] = new UnShow[None.type]//("None")
  { override def typeStr: String = "None"

    def fromExpr(expr: Expr): EMon[None.type] = expr match
    { case IdentUpperToken(_, "None") => Good(None)
      case eet: EmptyExprToken => Good(None)
      case e => bad1(e, "None not found")
    }
  }
}

/** A convenience trait type class trait for persistence, that combines the [[ShowT]] and [[UnShow]] type classes. Most if not all final classes that
 * inherit from this trait will require type class instances of ShowT and UnShowT to implement [[Persist]]'s members. It is most important that these
 * implicit parameter instances be specified as separate ShowT and UnShowT parameters. Do not combine them into a Persist parameter. There are no
 * implicit instances for [[Int]], [[Double]], [[List]] etc in the [[Persist]] companion object, the Persist components for these standard types will
 * be found in the ShowT and UnShow companion objects. */
trait Persist[T] extends ShowT[T] with UnShow[T]