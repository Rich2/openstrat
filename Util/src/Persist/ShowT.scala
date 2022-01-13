/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.immutable.ArraySeq, annotation.unchecked.uncheckedVariance

/** Common super trait for [[ShowPrec]], [[ShowPrecisionT]] and [[UnShow]]. All of which inherit the typeStr property. */
trait TypeStred
{ /** The RSON type of T. This the only data that a ShowT instance requires, that can't be implemented through delegation to an object of type
   * Show. */
  def typeStr: String
}

/** A type class for string, text and visual representation of objects. An alternative to toString. This trait has mor demanding ambitions Mostly you
 *  will want to use  Persist which not only gives the Show methods to String representation, but the methods to parse Strings back to objects of the
 *  type T. However it may often be useful to start with Show type class and upgrade it later to Persist[T]. */
trait ShowT[-T] extends TypeStred
{
  /** Provides the standard string representation for the object. Its called ShowT to indicate this is a type class method that acts upon an object
   * rather than a method on the object being shown. */
  def strT(obj: T): String

  /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
   * should always be determined at compile time or if sometimes it should be determined at runtime. */
  def syntaxDepthT(obj: T): Int

  def showT(obj: T, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String
}

/* The companion object for the ShowT type class. Persist extends ShowT with UnShow. As its very unlikely that anyone would want to create an UnShow
   instance without a ShowT instance. Many Persist instances are placed inside the Show companion object. However type instances that themselves
   one or more Show type instances as parameters require a specific Show instance. The Persist instance for these types will require corresponding
   Persist type instances, and these will be placed in the Persist companion object. */
object ShowT
{
  implicit val intPersistImplicit: PersistPrec[Int] = new PersistSimplePrecision[Int]("Int")
  {
    def strT(obj: Int): String = obj.toString

    override def fromExpr(expr: Expr): EMon[Int] = expr match {
      case IntDeciToken(i) => Good(i)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "+" => Good(i.toInt)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "-" => Good(-i.toInt)
      case _ => expr.exprParseErr[Int]
    }
  }

  val hexadecimal: ShowPrecisionT[Int] = new ShowSimplePrecisionT[Int]
  { override def typeStr: String = "Int"
    override def strT(obj: Int): String = obj.hexStr
  }

  val base32: ShowPrecisionT[Int] = new ShowSimplePrecisionT[Int]
  { override def typeStr: String = "Int"
    override def strT(obj: Int): String = obj.base32
  }

  implicit val doublePersistImplicit: PersistPrec[Double] = new PersistPrec[Double]
  {
    override def typeStr: String = "DFloat"
    override def syntaxDepthT(obj: Double): Int = 1

    def strT(obj: Double): String = {
      val s1 = obj.toString
      ife(s1.last == '0', s1.dropRight(2), s1)
    }

    override def showT(obj: Double, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
    {
      val s1 = obj.toString
      val len = s1.length
      val i = s1.indexOf('.')

      val inner = i match {
        case i if maxPlaces < 0 => s1
        case i if maxPlaces == 0 => s1.dropRight(len  - i - maxPlaces)
        case i if len > maxPlaces + i + 1 => s1.dropRight(len  - i - 1 - maxPlaces)
        case i if len - i - 1 < minPlaces => s1 + (minPlaces + i + i - len).repeatChar('0')
        case _ => s1
      }

      way match {
        case ShowTyped => typeStr + inner.enParenth
        case _ => inner
      }
    }

    override def fromExpr(expr: Expr): EMon[Double] = expr match
    { case IntDeciToken(i) => Good(i.toDouble)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "+" => Good(i.toDouble)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "-" => Good(-(i.toDouble))
      /* case FloatToken(_, _, d) => Good(d)
       case PreOpExpr(op, FloatToken(_, _, d)) if op.srcStr == "+" => Good(d)
       case PreOpExpr(op, FloatToken(_, _, d)) if op.srcStr == "-" => Good(-d)
     */  case  _ => expr.exprParseErr[Double]
    }
  }

  implicit val longPersistImplicit: PersistPrec[Long] = new PersistSimplePrecision[Long]("Long")
  { def strT(obj: Long): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Long] = expr match
    { case NatDeciToken(_, i) => Good(i.toLong)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "+" => Good(i.toLong)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "-" => Good(-i.toLong)
      case  _ => expr.exprParseErr[Long]
    }
  }

  implicit val floatImplicit: ShowPrecisionT[Float] = new ShowSimplePrecisionT[Float]
  { override def typeStr: String = "SFloat"
    def strT(obj: Float): String = obj.toString
  }

  implicit val booleanPersistImplicit: PersistPrec[Boolean] = new PersistSimplePrecision[Boolean]("Bool")
  { override def strT(obj: Boolean): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Boolean] = expr match
    { case IdentLowerToken(_, str) if str == "true" => Good(true)
      case IdentLowerToken(_, str) if str == "false" => Good(false)
      case _ => expr.exprParseErr[Boolean]
    }
  }

  implicit val stringPersistImplicit: PersistPrec[String] = new PersistSimplePrecision[String]("Str")
  { def strT(obj: String): String = obj.enquote
    override def fromExpr(expr: Expr): EMon[String] = expr match
    { case StringToken(_, stringStr) => Good(stringStr)
      case  _ => expr.exprParseErr[String]
    }
  }

  implicit val charImplicit: ShowPrecisionT[Char] = new ShowSimplePrecisionT[Char]
  { override def typeStr: String = "Char"
    def strT(obj: Char): String = obj.toString.enquote1
  }

  class ShowIterableClass[A, R <: Iterable[A]](val evA: ShowPrecisionT[A]) extends ShowIterable[A, R]{}

  implicit def ShowIterableImplicit[A](implicit evA: ShowPrecisionT[A]): ShowT[Iterable[A]] = new ShowIterableClass[A, Iterable[A]](evA)
  implicit def ShowSeqImplicit[A](implicit evA: ShowPrecisionT[A]): ShowT[Seq[A]] = new ShowIterableClass[A, Seq[A]](evA)

  /** Implicit method for creating List[A: Show] instances. */
  implicit def listImplicit[A](implicit ev: ShowPrecisionT[A]): ShowT[List[A]] = new ShowIterableClass[A, List[A]](ev)

  /** Implicit method for creating ::[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  //implicit def consShowImplicit[A](implicit ev: ShowT[A]): ShowT[::[A]] = new PersistConsImplicit[A](ev)

  //implicit def nilPersistImplicit[A](implicit ev: Persist[A]): Persist[Nil.type] = new PersistNilImplicit[A](ev)

  implicit def vectorImplicit[A](implicit ev: ShowPrecisionT[A]): ShowT[Vector[A]] = new ShowIterableClass[A, Vector[A]](ev)

  implicit val arrayIntImplicit: ShowT[Array[Int]] = new ShowTSeqLike[Int, Array[Int]]
  {
    override def evA: ShowT[Int] = ShowT.intPersistImplicit
    override def syntaxDepthT(obj: Array[Int]): Int = 2

    override def showT(obj: Array[Int], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  class ArrRefPersist[A <: AnyRef](ev: PersistPrec[A]) extends PersistSeqLike[A, ArraySeq[A]](ev)
  {
    override def syntaxDepthT(obj: ArraySeq[A]): Int = ???

    override def fromExpr(expr: Expr): EMon[ArraySeq[A]] =  expr match
    { case AlphaBracketExpr(IdentUpperToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? // fromParameterStatements(sts)
      case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
      case _ => ??? // expr.exprParseErr[A](this)
    }

    override def showT(obj: ArraySeq[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** Implicit method for creating Array[A <: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def arrayRefToPersist[A <: AnyRef](implicit ev: PersistPrec[A]): PersistPrec[Array[A]] = new ArrayRefPersist[A](ev)
  class ArrayRefPersist[A <: AnyRef](ev: PersistPrec[A]) extends PersistSeqLike[A, Array[A]](ev)
  {
    override def syntaxDepthT(obj: Array[A]): Int = ???

    override def fromExpr(expr: Expr): EMon[Array[A]] =  expr match
    {
      case AlphaBracketExpr(IdentLowerToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? // fromParameterStatements(sts)
      case AlphaBracketExpr(IdentLowerToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
      case _ => ??? // expr.exprParseErr[A](this)
    }

    override def showT(obj: Array[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** Implicit method for creating Arr[A <: Show] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def arraySeqImplicit[A](implicit ev: ShowPrecisionT[A]): ShowT[collection.immutable.ArraySeq[A]] = new ShowTSeqLike[A, ArraySeq[A]]
  {
    override def syntaxDepthT(obj: ArraySeq[A]): Int = ???
    override def evA: ShowPrecisionT[A] = ev

    /** Not fully correct yet. */
    override def showT(obj: ArraySeq[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
      obj.map(el => ev.showT(el, ShowStandard, maxPlaces, 0)).semiFold
  }

  implicit def somePersistImplicit[A](implicit ev: PersistPrec[A]): PersistPrec[Some[A]] = new PersistPrec[Some[A]]
  {
    override def typeStr: String = "Some" + ev.typeStr.enSquare
    override def syntaxDepthT(obj: Some[A]): Int = ev.syntaxDepthT(obj.value)
    override def strT(obj: Some[A]): String = ev.strT(obj.value)

    override def showT(obj: Some[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???

    override def fromExpr(expr: Expr): EMon[Some[A]] = expr match
    { case AlphaBracketExpr(IdentUpperToken(_, "Some"), Arr1(ParenthBlock(Arr1(hs), _, _))) => ev.fromExpr(hs.expr).map(Some(_))
      case expr => ev.fromExpr(expr).map(Some(_))
    }
  }

  implicit val nonePersistImplicit: PersistPrec[None.type] = new PersistSimplePrecision[None.type]("None")
  {
    override def strT(obj: None.type): String = ""

    def fromExpr(expr: Expr): EMon[None.type] = expr match
    { case IdentLowerToken(_, "None") => Good(None)
      case eet: EmptyExprToken => Good(None)
      case e => bad1(e, "None not found")
    }
  }

  implicit def optionPersistImplicit[A](implicit evA: PersistPrec[A]): PersistPrec[Option[A]] =
    new PersistSum2[Option[A], Some[A], None.type](somePersistImplicit[A](evA), nonePersistImplicit)
  { override def typeStr: String = "Option" + evA.typeStr.enSquare
      override def syntaxDepthT(obj: Option[A]): Int = obj.fld(1, evA.syntaxDepthT(_))
  }
}

sealed trait ShowInstancesPriority2
{
  /** Implicit method for creating Seq[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def seqPersistImplicit[T](implicit ev: PersistPrec[T]): PersistPrec[Seq[T]] = new PersistSeqImplicit[T](ev)
}

/** The stringer implicit class gives extension methods for Show methods from the implicit Show instance type A. */
class ShowTExtensions[-A](ev: ShowT[A], thisVal: A)// @uncheckedVariance)
{
  /** Provides the standard string representation for the object. */
  @inline def str: String = ev.strT(thisVal)

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowPrecisionT]] type class instances. */
  def show(style: ShowStyle = ShowStandard, decimalPlaces: Int = -1, minPlaces: Int = 0): String = ev.showT(thisVal, style, decimalPlaces, minPlaces)

  /** Return the defining member values of the type as a series of comma separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 3. if it is 3 or greater return the full typed data. */
  @inline def strComma: String = ev.showT(thisVal, ShowCommas, -1, 0)

  /** Return the defining member values of the type as a series of semicolon separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 4. if it is 4 or greater return the full typed data. This method is not commonly needed but is useful
   *  for case classes with a single member. */
  @inline def strSemi(maxPlaces: Int, minPlaces: Int = 0): String =  ev.showT(thisVal, ShowSemis, maxPlaces, minPlaces)

  /** For most objects showTyped will return the same value as persist, for PeristValues the value will be type enclosed. 4.showTyped
   * will return Int(4) */
  @inline def strTyped: String = ev.showT(thisVal, ShowTyped, -1, 0)

  def showFields: String = ev.showT(thisVal, ShowParamNames, 1, 0)
  def showTypedFields: String = ev.showT(thisVal, ShowStdTypedFields, 1, 0)
}

/** A type class for string, text and visual representation of objects. An alternative to toString. This trait has mor demanding ambitions Mostly you
 *  will want to use  Persist which not only gives the Show methods to String representation, but the methods to parse Strings back to objects of the
 *  type T. However it may often be useful to start with Show type class and upgrade it later to Persist[T]. */
trait ShowPrecisionT[-T] extends ShowT[T]
{
  //def showT(obj: T, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String
}


/** The stringer implicit class gives extension methods for Show methods from the implicit Show instance type A. */
class ShowPrecisionTExtensions[-A](ev: ShowPrecisionT[A], thisVal: A) //extends ShowTExtensions[A]
{
  def str2Comma: String = ev.showT(thisVal, ShowCommas, 2, 0)

  //@inline def strSemi: String = ev.showT(thisVal, ShowSemis, -1, 0)

  def str0: String = ev.showT(thisVal, ShowStandard, 0, 0)
  def str1: String = ev.showT(thisVal, ShowStandard, 1, 0)
  def str2: String = ev.showT(thisVal, ShowStandard, 2, 0)
  def str3: String = ev.showT(thisVal, ShowStandard, 3, 0)
}