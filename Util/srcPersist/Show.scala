/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.immutable.ArraySeq

/** Common super trait for [[TellDec]], [[Show]] and [[Unshow]]. All of which inherit the typeStr property. */
trait PersistBase extends Any
{ /** The RSON type of T. This the only data that a ShowT instance requires, that can't be implemented through delegation to an object of type
 * Show. */
  def typeStr: String
}

/** A type class for string, text and visual representation of objects. An alternative to toString. This trait has mor demanding ambitions Mostly you
 *  will want to use  Persist which not only gives the Show methods to String representation, but the methods to parse Strings back to objects of the
 *  type T. However it may often be useful to start with Show type class and upgrade it later to Persist[T]. The capabilities of decimal place
 *  precision and explicit typing for numbers are placed defined here and in the corresponding [[SHow]] type class although they have n meaning /
 *  purpose for many types, as separating them adds enormous complexity for very little gain. */
trait Show[-T] extends PersistBase
{
  /** Provides the standard string representation for the object. Its called ShowT to indicate this is a type class method that acts upon an object
   * rather than a method on the object being shown. */
  def strT(obj: T): String

  /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
   * should always be determined at compile time or if sometimes it should be determined at runtime. */
  def syntaxDepthT(obj: T): Int

  def showDecT(obj: T, style: ShowStyle, maxPlaces: Int, minPlaces: Int = -1): String

  def showT(obj: T, style: ShowStyle): String = showDecT(obj, style, -1, -1)
}

/* The companion object for the ShowT type class. Persist extends ShowT with UnShow. As its very unlikely that anyone would want to create an UnShow
   instance without a ShowT instance. Many Persist instances are placed inside the Show companion object. However type instances that themselves
   one or more Show type instances as parameters require a specific Show instance. The Persist instance for these types will require corresponding
   Persist type instances, and these will be placed in the Persist companion object. */
object Show
{ /** Implicit [[Persist]] type class instance /evidence for [[Int]]. */
  implicit val intPersistEv: Persist[Int] = new IntPersistClass
  class IntPersistClass extends Unshow.IntEvClass with Persist[Int] with ShowSimpleing[Int]
  { def strT(obj: Int): String = obj.toString
  }

  val hexadecimal: Show[Int] = new ShowSimpleing[Int]
  { override def typeStr: String = "Int"
    override def strT(obj: Int): String = obj.hexStr
  }

  val base32: Show[Int] = new ShowSimpleing[Int]
  { override def typeStr: String = "Int"
    override def strT(obj: Int): String = obj.base32
  }

  /** Implicit [[Persist]] instance / evidence for [[Double]]. */
  implicit val doublePersistEv: Persist[Double] = new Persist[Double]
  {
    override def typeStr: String = "DFloat"
    override def syntaxDepthT(obj: Double): Int = 1

    def strT(obj: Double): String = {
      val s1 = obj.toString
      ife(s1.last == '0', s1.dropRight(2), s1)
    }

    override def showDecT(obj: Double, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
    {
      val orig = obj.toString
      val len = orig.length
      val minPlacesAdj: Int = ife(maxPlaces < 0, minPlaces, minPlaces.min(maxPlaces))
      val dotIndex: Int = orig.indexOf('.')
      val fracLen: Int = len - dotIndex - 1

      val inner: String = None match
      { case _ if maxPlaces < 0 & minPlacesAdj <= 1 & orig.last == '0' => orig.dropRight(2)
        case _ if fracLen < minPlacesAdj => orig + (minPlacesAdj -fracLen).repeatChar('0')
        case _ if maxPlaces < 0 => orig
        case _ if maxPlaces == 0 => orig.dropRight(fracLen + 1)
        case _ if fracLen > maxPlaces => orig.dropRight(fracLen - maxPlaces)
        case _ => orig
      }

      style match {
        case ShowTyped => typeStr + inner.enParenth
        case _ => inner
      }
    }

    override def fromExpr(expr: Expr): EMon[Double] = expr match
    { case ValidFracToken(d) => Good(d)
      case PreOpExpr(op, ValidFracToken(d)) if op.srcStr == "+" => Good(d)
      case PreOpExpr(op, ValidFracToken(d)) if op.srcStr == "-" => Good(d)
      case _ => expr.exprParseErr[Double]
    }
  }

  implicit val longPersistImplicit: Persist[Long] = new PersistSimple[Long]("Long")
  { def strT(obj: Long): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Long] = expr match
    { case NatDeciToken(_, i) => Good(i.toLong)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "+" => Good(i.toLong)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "-" => Good(-i.toLong)
      case  _ => expr.exprParseErr[Long]
    }
  }

  implicit val floatImplicit: Show[Float] = new ShowSimpleing[Float]
  { override def typeStr: String = "SFloat"
    def strT(obj: Float): String = obj.toString
  }

  implicit val booleanPersistImplicit: Persist[Boolean] = new PersistSimple[Boolean]("Bool")
  { override def strT(obj: Boolean): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Boolean] = expr match
    { case IdentLowerToken(_, str) if str == "true" => Good(true)
      case IdentLowerToken(_, str) if str == "false" => Good(false)
      case _ => expr.exprParseErr[Boolean]
    }
  }

  /** Implicit [[Persist]] type class instance /evidence for [[String]]. */
  implicit val stringPersistEv: Persist[String] = new PersistSimple[String]("Str")
  { def strT(obj: String): String = obj.enquote
    override def fromExpr(expr: Expr): EMon[String] = expr match
    { case StringToken(_, stringStr) => Good(stringStr)
      case  _ => expr.exprParseErr[String]
    }
  }

  implicit val charImplicit: Show[Char] = new ShowSimpleing[Char]
  { override def typeStr: String = "Char"
    def strT(obj: Char): String = obj.toString.enquote1
  }

  class ShowIterableClass[A, R <: Iterable[A]](val evA: Show[A]) extends ShowIterable[A, R] with Show[R]{}

  implicit def ShowIterableImplicit[A](implicit evA: Show[A]): Show[Iterable[A]] = new ShowIterableClass[A, Iterable[A]](evA)
  implicit def ShowSeqImplicit[A](implicit evA: Show[A]): Show[Seq[A]] = new ShowIterableClass[A, Seq[A]](evA)

  /** Implicit method for creating List[A: Show] instances. */
  implicit def listImplicit[A](implicit ev: Show[A]): Show[List[A]] = new ShowIterableClass[A, List[A]](ev)

  /** Implicit method for creating ::[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  //implicit def consShowImplicit[A](implicit ev: ShowT[A]): ShowT[::[A]] = new PersistConsImplicit[A](ev)

  //implicit def nilPersistImplicit[A](implicit ev: Persist[A]): Persist[Nil.type] = new PersistNilImplicit[A](ev)

  implicit def vectorImplicit[A](implicit ev: Show[A]): Show[Vector[A]] = new ShowIterableClass[A, Vector[A]](ev)

  implicit val arrayIntImplicit: Show[Array[Int]] = new ShowTSeqLike[Int, Array[Int]]
  {
    override def evA: Show[Int] = Show.intPersistEv
    override def syntaxDepthT(obj: Array[Int]): Int = 2

    override def showDecT(obj: Array[Int], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = "Unimplemented"
  }

  class ArrRefPersist[A <: AnyRef](ev: Persist[A]) extends PersistSeqLike[A, ArraySeq[A]](ev)
  {
    override def syntaxDepthT(obj: ArraySeq[A]): Int = ???

    override def fromExpr(expr: Expr): EMon[ArraySeq[A]] =  expr match
    { case AlphaBracketExpr(IdentUpperToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? // fromParameterStatements(sts)
      case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
      case _ => ??? // expr.exprParseErr[A](this)
    }

    override def showDecT(obj: ArraySeq[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** Implicit method for creating Array[A <: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def arrayRefToPersist[A <: AnyRef](implicit ev: Persist[A]): Persist[Array[A]] = new ArrayRefPersist[A](ev)
  class ArrayRefPersist[A <: AnyRef](ev: Persist[A]) extends PersistSeqLike[A, Array[A]](ev)
  {
    override def syntaxDepthT(obj: Array[A]): Int = ???

    override def fromExpr(expr: Expr): EMon[Array[A]] =  expr match
    {
      case AlphaBracketExpr(IdentLowerToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? // fromParameterStatements(sts)
      case AlphaBracketExpr(IdentLowerToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
      case _ => ??? // expr.exprParseErr[A](this)
    }

    override def showDecT(obj: Array[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** Implicit method for creating Arr[A <: Show] instances. This seems toRich have to be a method rather directly using an implicit class */
  implicit def arraySeqImplicit[A](implicit ev: Show[A]): Show[collection.immutable.ArraySeq[A]] = new ShowTSeqLike[A, ArraySeq[A]]
  {
    override def syntaxDepthT(obj: ArraySeq[A]): Int = ???
    override def evA: Show[A] = ev

    /** Not fully correct yet. */
    override def showDecT(obj: ArraySeq[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
      obj.map(el => ev.showDecT(el, ShowStandard, maxPlaces, 0)).semiFold
  }

  implicit def somePersistImplicit[A](implicit ev: Persist[A]): Persist[Some[A]] = new Persist[Some[A]]
  {
    override def typeStr: String = "Some" + ev.typeStr.enSquare
    override def syntaxDepthT(obj: Some[A]): Int = ev.syntaxDepthT(obj.value)
    override def strT(obj: Some[A]): String = ev.strT(obj.value)

    override def showDecT(obj: Some[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???

    override def fromExpr(expr: Expr): EMon[Some[A]] = expr match
    { case AlphaBracketExpr(IdentUpperToken(_, "Some"), Arr1(ParenthBlock(Arr1(hs), _, _))) => ev.fromExpr(hs.expr).map(Some(_))
      case expr => ev.fromExpr(expr).map(Some(_))
    }
  }

  implicit val nonePersistImplicit: Persist[None.type] = new PersistSimple[None.type]("None")
  {
    override def strT(obj: None.type): String = ""

    def fromExpr(expr: Expr): EMon[None.type] = expr match
    { case IdentLowerToken(_, "None") => Good(None)
      case eet: EmptyExprToken => Good(None)
      case e => bad1(e, "None not found")
    }
  }

  implicit def optionPersistImplicit[A](implicit evA: Persist[A]): Persist[Option[A]] =
    new PersistSum2[Option[A], Some[A], None.type](somePersistImplicit[A](evA), nonePersistImplicit)
    { override def typeStr: String = "Option" + evA.typeStr.enSquare
      override def syntaxDepthT(obj: Option[A]): Int = obj.fld(1, evA.syntaxDepthT(_))
    }
}

sealed trait ShowTInstancesPriority2
{ /** Implicit method for creating Seq[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def seqPersistImplicit[T](implicit ev: Persist[T]): Persist[Seq[T]] = new PersistSeqImplicit[T](ev)
}

/** Extension methods for types with [[Show]] type class instances. */
class ShowingExtensions[-A](ev: Show[A], thisVal: A)
{ /** Provides the standard string representation for the object. */
  @inline def str: String = ev.strT(thisVal)

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  def show(style: ShowStyle = ShowStandard): String = ev.showT(thisVal, style)

 /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  def showDec(style: ShowStyle = ShowStandard, decimalPlaces: Int): String = ev.showDecT(thisVal, style, decimalPlaces, decimalPlaces)

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  def showDec(style: ShowStyle, decimalPlaces: Int, minPlaces: Int): String = ev.showDecT(thisVal, style, decimalPlaces, minPlaces)

  /** Return the defining member values of the type as a series of comma separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 3. if it is 3 or greater return the full typed data. */
  @inline def strComma: String = ev.showDecT(thisVal, ShowCommas, -1, 0)//ev.showComma(thisVal)

  def str2Comma: String = ev.showDecT(thisVal, ShowCommas, 2, 0)

  /** Return the defining member values of the type as a series of semicolon separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 4. if it is 4 or greater return the full typed data. This method is not commonly needed but is useful
   *  for case classes with a single member. */
  @inline def strSemi: String = ev.showDecT(thisVal, ShowSemis, -1, 0)

  @inline def strSemi(maxPlaces: Int, minPlaces: Int = 0): String =  ev.showDecT(thisVal, ShowSemis, maxPlaces, minPlaces)

  /** For most objects showTyped will return the same value as persist, for PeristValues the value will be type enclosed. 4.showTyped
   * will return Int(4) */
  @inline def strTyped: String = ev.showDecT(thisVal, ShowTyped, -1, 0)

  def str0: String = ev.showDecT(thisVal, ShowStandard, 0, 0)
  def str1: String = ev.showDecT(thisVal, ShowStandard, 1, 0)
  def str2: String = ev.showDecT(thisVal, ShowStandard, 2, 0)
  def str3: String = ev.showDecT(thisVal, ShowStandard, 3, 0)

  /** Shows this object with field names. */
  def showFields: String = ev.showDecT(thisVal, ShowParamNames, -1, 0)

  def showTypedFields: String = ev.showDecT(thisVal, ShowStdTypedFields, -1, 0)
}