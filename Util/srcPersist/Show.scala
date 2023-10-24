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
  def syntaxDepth(obj: T): Int

  def showDec(obj: T, style: ShowStyle, maxPlaces: Int, minPlaces: Int = -1): String

  def show(obj: T, style: ShowStyle): String = showDec(obj, style, -1, -1)
}

/* The companion object for the ShowT type class. Persist extends ShowT with UnShow. As its very unlikely that anyone would want to create an UnShow
   instance without a ShowT instance. Many Persist instances are placed inside the Show companion object. However type instances that themselves
   one or more Show type instances as parameters require a specific Show instance. The Persist instance for these types will require corresponding
   Persist type instances, and these will be placed in the Persist companion object. */
object Show
{ /** Implicit [[Show]] type class instance / evidence for [[Int]]. */
  implicit val intEv: Show[Int] = ShowSimple("Int", _.toString)

  val hexadecimal: Show[Int] = new ShowSimple[Int]
  { override def typeStr: String = "Int"
    override def strT(obj: Int): String = obj.hexStr
  }

  val base32: Show[Int] = new ShowSimple[Int]
  { override def typeStr: String = "Int"
    override def strT(obj: Int): String = obj.base32
  }

  /** Implicit [[Persist]] instance / evidence for [[Double]]. */
  implicit val doublePersistEv: Persist[Double] = new Persist[Double]
  {
    override def typeStr: String = "DFloat"
    override def syntaxDepth(obj: Double): Int = 1

    def strT(obj: Double): String =
    { val s1 = obj.toString
      ife(s1.last == '0', s1.dropRight(2), s1)
    }

    override def showDec(obj: Double, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
    { val orig = obj.toString
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

      style match
      { case ShowTyped => typeStr + inner.enParenth
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

  /** Implicit [[Show]] type class instance / evidence for [[Long]]. */
  implicit val longEv: Show[Long] = ShowSimple[Long]("Long", _.toString)

  /** Implicit [[Show]] type class instance / evidence for [[Long]]. */
  implicit val floatEv: Show[Float] = ShowSimple[Float]("SFloat", _.toString)

  /** Implicit [[Show]] type class instance / evidence for [[Boolean]]. */
  implicit val booleanEv: Show[Boolean] = ShowSimple[Boolean]("Bool", _.toString)

  /** Implicit [[Show]] type class instance / evidence for [[String]]. */
  implicit val stringEv: Show[String] = ShowSimple[String]("Str", _.enquote)

  /** Implicit [[Show]] type class instance / evidence for [[Char]]. */
  implicit val charEv: Show[Char] = ShowSimple[Char]("Char", _.toString.enquote1)

  class ShowIterableClass[A, R <: Iterable[A]](val evA: Show[A]) extends ShowIterable[A, R] with Show[R]{}

  implicit def ShowIterableImplicit[A](implicit evA: Show[A]): Show[Iterable[A]] = new ShowIterableClass[A, Iterable[A]](evA)
  implicit def ShowSeqImplicit[A](implicit evA: Show[A]): Show[Seq[A]] = new ShowIterableClass[A, Seq[A]](evA)

  /** Implicit method for creating List[A: Show] instances. */
  implicit def listImplicit[A](implicit ev: Show[A]): Show[List[A]] = new ShowIterableClass[A, List[A]](ev)

  /** Implicit method for creating ::[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  //implicit def consShowImplicit[A](implicit ev: ShowT[A]): ShowT[::[A]] = new PersistConsImplicit[A](ev)

  //implicit def nilPersistImplicit[A](implicit ev: Persist[A]): Persist[Nil.type] = new PersistNilImplicit[A](ev)

  implicit def vectorImplicit[A](implicit ev: Show[A]): Show[Vector[A]] = new ShowIterableClass[A, Vector[A]](ev)

  implicit val arrayIntImplicit: Show[Array[Int]] = new ShowSeq[Int, Array[Int]]
  {
    override def evA: Show[Int] = Show.intEv
    override def syntaxDepth(obj: Array[Int]): Int = 2

//    override def showDec(obj: Array[Int], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = "Unimplemented"

    override def showMap(obj: Array[Int])(f: Int => String): StrArr = obj.mapArr(f)
  }

  /** Implicit method for creating Arr[A <: Show] instances. This seems toRich have to be a method rather directly using an implicit class */
  implicit def arraySeqImplicit[A](implicit ev: Show[A]): Show[collection.immutable.ArraySeq[A]] = new ShowSeq[A, ArraySeq[A]]
  {
    override def syntaxDepth(obj: ArraySeq[A]): Int = ???
    override def evA: Show[A] = ev

    override def showMap(obj: ArraySeq[A])(f: A => String): StrArr = obj.mapArr(f)
  }

  implicit def somePersistImplicit[A](implicit ev: Persist[A]): Persist[Some[A]] = new Persist[Some[A]]
  {
    override def typeStr: String = "Some" + ev.typeStr.enSquare
    override def syntaxDepth(obj: Some[A]): Int = ev.syntaxDepth(obj.value)
    override def strT(obj: Some[A]): String = ev.strT(obj.value)

    override def showDec(obj: Some[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???

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

  implicit def optionEv[A](implicit evA: Persist[A]): Show[Option[A]] =
    ShowSum2[Option[A], Some[A], None.type]("Opt", somePersistImplicit[A](evA), nonePersistImplicit)

}

/** Extension methods for types with [[Show]] type class instances. */
class ShowingExtensions[-A](ev: Show[A], thisVal: A)
{ /** Provides the standard string representation for the object. */
  @inline def str: String = ev.strT(thisVal)

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  def show(style: ShowStyle = ShowStandard): String = ev.show(thisVal, style)

 /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  def showDec(style: ShowStyle = ShowStandard, decimalPlaces: Int): String = ev.showDec(thisVal, style, decimalPlaces, decimalPlaces)

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  def showDec(style: ShowStyle, decimalPlaces: Int, minPlaces: Int): String = ev.showDec(thisVal, style, decimalPlaces, minPlaces)

  /** Return the defining member values of the type as a series of comma separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 3. if it is 3 or greater return the full typed data. */
  @inline def strComma: String = ev.showDec(thisVal, ShowCommas, -1, 0)//ev.showComma(thisVal)

  def str2Comma: String = ev.showDec(thisVal, ShowCommas, 2, 0)

  /** Return the defining member values of the type as a series of semicolon separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 4. if it is 4 or greater return the full typed data. This method is not commonly needed but is useful
   *  for case classes with a single member. */
  @inline def strSemi: String = ev.showDec(thisVal, ShowSemis, -1, 0)

  @inline def strSemi(maxPlaces: Int, minPlaces: Int = 0): String =  ev.showDec(thisVal, ShowSemis, maxPlaces, minPlaces)

  /** For most objects showTyped will return the same value as persist, for PeristValues the value will be type enclosed. 4.showTyped
   * will return Int(4) */
  @inline def strTyped: String = ev.showDec(thisVal, ShowTyped, -1, 0)

  def str0: String = ev.showDec(thisVal, ShowStandard, 0, 0)
  def str1: String = ev.showDec(thisVal, ShowStandard, 1, 0)
  def str2: String = ev.showDec(thisVal, ShowStandard, 2, 0)
  def str3: String = ev.showDec(thisVal, ShowStandard, 3, 0)

  /** Shows this object with field names. */
  def showFields: String = ev.showDec(thisVal, ShowParamNames, -1, 0)

  def showTypedFields: String = ev.showDec(thisVal, ShowStdTypedFields, -1, 0)

  def showDepth: Int = ev.syntaxDepth(thisVal)
}